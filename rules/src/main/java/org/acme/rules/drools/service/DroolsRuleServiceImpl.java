package org.acme.rules.drools.service;

import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import org.acme.rules.drools.internal.adapter.RuleAdapter;
import org.acme.rules.drools.internal.adapter.RuleTypeAdapter;
import org.acme.rules.drools.internal.model.Rule;
import org.acme.rules.drools.internal.model.RuleType;
import org.acme.rules.drools.internal.repository.RuleDAO;
import org.acme.rules.drools.internal.repository.RuleTypeDAO;
import org.acme.rules.drools.internal.util.AdapterBuilder;
import org.acme.rules.drools.internal.util.Constants;
import org.acme.rules.grpc.woserviceconnect.WorkOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.util.FileCopyUtils;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

// TODO: this is the part that should be adapted to the use of more modern rules
// 1- figure out how to read rules from database and/or from mongo
// 2- find out how to fire rules in the current version of ruleUnit
// 3- refactor accordingly

@Service
public class DroolsRuleServiceImpl implements DroolsRuleService{
    
    @Autowired
    private DroolsActionsService droolsActionsService;

    @Autowired
    private RuleDAO ruleDAO;

    @Autowired
    private RuleTypeDAO ruleTypeDAO;

    @Autowired
    private WoErrorsServiceImpl errorOtsService;

    static KieServices kieServices = KieServices.Factory.get();
    static KieContainer kContainer  = kieServices.newKieClasspathContainer();

    @Override
    public Rule getRule(String ruleName) {
        return ruleDAO.findByNameIgnoreCaseAndActive(ruleName).orElse(null);
    }

    @Override
    public HashMap<RuleType, RuleAdapter> getRulesByType(String grouping){
        HashMap<RuleType, RuleAdapter> rules = new HashMap<RuleType, RuleAdapter>();
        List<RuleType> typeList = ruleTypeDAO.findByGroupingOrderByPriorityAsc(Constants.A);
        for (RuleType type : typeList) {
            RuleAdapter rule = this.getRuleAdapter(type);
            rules.put(type, rule);
        }
        return rules;
    }

    @Override
    public String getFullRule(String header, List<Rule> rules){
        StringBuilder sb = new StringBuilder();
        sb.append(header);
        for (Rule rule : rules) {
            sb.append(System.lineSeparator());
            sb.append(rule.getDrl());
        }
        return sb.toString();
    }

    @Override
    public String readAllText(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public String getHeader(RuleType type) {
        String header = "";
        if (ruleTypeDAO.findByCode(type.getCode()) != null
                &&  !ruleTypeDAO.findByCode(type.getCode()).getHeaderDefault().isBlank()) {
            Resource res = ResourceFactory.newClassPathResource(ruleTypeDAO.findByCode(type.getCode()).getHeader());
            header = readAllText(res);
        } else {
            header = ruleTypeDAO.findByCode(type.getCode()).getHeaderDefault();
        }
        return header;
    }

    @Override
    public KieContainer getKieContainer(String key, String val) {
        KieServices kieServices = KieServices.Factory.get();
        KieBuilder kieBuilder = getKieBuilder(key, val);
        KieModule kieModule = kieBuilder.getKieModule();
        System.out.println("RELEASEID====================="+kieModule.getReleaseId());
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }

    @Override
    public KieBuilder getKieBuilder(String key, String val) {
        KieServices kieServices = KieServices.Factory.get();
        KieFileSystem kfs = kieServices.newKieFileSystem();
        kfs.write("src/main/resources/" + key, val);
        return kieServices.newKieBuilder(kfs).buildAll();
    }

    @Override
    public KieContainer getKieContainer(String key, String val, String threadName) {
        KieServices kieServices = KieServices.Factory.get();
        KieBuilder kieBuilder = getKieBuilder(key, val, threadName);
        KieModule kieModule = kieBuilder.getKieModule();
        System.out.println("RELEASEID====================="+kieModule.getReleaseId());
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }

    @Override
	public KieBuilder getKieBuilder(String key, String val, String threadName) {
    	KieServices kieServices = KieServices.Factory.get();
    	KieFileSystem kfs = kieServices.newKieFileSystem();
    	kfs.write("src/main/resources/" + key, val);
    	ReleaseId rId = kieServices.newReleaseId("com.rules", threadName,"1.0.0");
    	kfs.generateAndWritePomXML(rId);
        return kieServices.newKieBuilder(kfs).buildAll();
	}

    @Override
    public Map<String, String> getRuleMetaData(String metaData) {
        return Arrays.stream(metaData.split(","))
                .map(s -> s.split("="))
                .collect(Collectors.toMap(
                        a -> a[0].trim(),
                        a -> a[1].trim()
                ));
    }

    @Override
    public List<RuleType> getRuleTypes(String grouping) {
        return ruleTypeDAO.findByGroupingOrderByPriorityAsc(grouping);
    }


    // REVISAR SI ESTO FUNCIONA COMO ESTÁ PREVISTO
    @Override
    public RuleAdapter getRuleAdapter(RuleType type) {
        String header = getHeader(type);
        List<Rule> rules = ruleDAO.findByGrouping(type.getGrouping());
        String fullRule = getFullRule(header, rules);
        KieContainer kieContainer = getKieContainer(type.getHeader(), fullRule);
        return RuleAdapter.builder()
                .type(type)
                .header(header)
                .rules(rules)
                .fullRule(fullRule)
                .kieContainer(kieContainer)
                .build();
    }


    // REVISAR SI ESTO FUNCIONA COMO ESTÁ PREVISTO
    @Override
    public void fireAllRules(RuleAdapter adapter, RuleTypeAdapter facts) {
        KieSession kieSession = kContainer.newKieSession();
        try {
            kieSession.insert(facts);
            kieSession.getAgenda().getAgendaGroup(adapter.getType().getCode()).setFocus();
            fireAllRules(adapter, kieSession);
        } finally {
            kieSession.dispose();
        }
    }


    // REVISAR SI ESTO FUNCIONA COMO ESTÁ PREVISTO
    @Override
    public void fireAllRules(RuleAdapter adapter, KieSession kieSession) {
        kieSession.fireAllRules(match -> {
            String agendaGroup = getRuleMetaData(match.getRule().toString()).get("agendaGroup");
            return adapter.getType().getCode().equals(agendaGroup);
        });
    }

    @Override
    public boolean addWoToRun(WorkOrderDTO wo, String grouping) {
        List<WorkOrderDTO> woList = new ArrayList<>();
        woList.add(wo);
        return runRules(grouping, woList);
    }

    @Override
    public boolean runRules(String grouping, List<WorkOrderDTO> woList) {
        woList = woList.stream()
                .filter(wo -> wo != null && !wo.getWoJobDTOs().isEmpty())
                .toList();
        HashMap<RuleType, RuleAdapter> rules = this.getRulesByType(grouping);
        List<RuleType> ruleTypes = new ArrayList<RuleType>(rules.keySet())
                .stream().sorted(Comparator.comparing(RuleType::getPriority)).toList();
        for (WorkOrderDTO wo : woList) {
            WorkOrderDTO woInProcess = wo;
            System.out.println(" \r\n ### Processing WO: " + wo.getWoNumber());
            getWoJobs(wo);
            for (RuleType rt  : ruleTypes) {
                RuleTypeAdapter facts = AdapterBuilder.ruleTypeAdapterBuilder(rt, woInProcess);
                RuleAdapter rule = rules.get(rt);
                this.fireAllRules(rule, facts);
                woInProcess = droolsActionsService.impactRules(facts);
            }
    //        woService.save(woInProcess);
            getWoJobs(wo);
        }
        return true;
    }

    @Override
    public boolean recover(RuntimeException e, String grouping, List<WorkOrderDTO> woList) {
        woList.forEach(wo -> {
            errorOtsService.add(wo.getWoNumber(), grouping, e);
        });
        return false;
    }

    @Override
    public boolean recover(RuntimeException e, WorkOrderDTO wo) {
        errorOtsService.add(wo.getWoNumber(), e);
        return false;
    }

    @Override
    public void getWoJobs(WorkOrderDTO wo) {
        /*
        List<String> codes = wo.getWoJobDTOs().stream().map(WorkOrderJobDTO::getJobCode).toList();
        List<String> jobs = jobService.findByCodesAndActiveStatus(codes, Constants.ACTIVE_Y)
                .stream().map(JobDTO::getCode).toList();
        System.out.println("\r\n" + " WO: " + wo.getWoNumber() + " - JOBS:  " + jobs + "\r\n");
         */
    }

    @Override
	public RuleAdapter getRuleAdapter(RuleType type, String threadName) {
        String header = getHeader(type);
        List<Rule> rules = ruleDAO.findByGrouping(type.getGrouping());
        String fullRule = getFullRule(header, rules);
        KieContainer kieContainer = getKieContainer(type.getHeader(), fullRule, threadName);
        return RuleAdapter.builder()
                .type(type)
                .header(header)
                .rules(rules)
                .fullRule(fullRule)
                .kieContainer(kieContainer)
                .build();
    }

}



