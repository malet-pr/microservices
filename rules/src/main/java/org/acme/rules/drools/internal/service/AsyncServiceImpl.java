package org.acme.rules.drools.internal.service;

import org.acme.rules.drools.AsyncService;
import org.acme.rules.drools.WorkOrderData;
import org.acme.rules.drools.MessageToWoData;
import org.acme.rules.drools.internal.util.Constants;
import org.acme.rules.grpc.WorkOrderConsumer;
import org.acme.rules.drools.internal.util.RuleAdapter;
import org.acme.rules.drools.internal.util.RuleTypeAdapter;
import org.acme.rules.drools.internal.model.RuleType;
import org.acme.rules.drools.internal.util.AdapterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service
public class AsyncServiceImpl implements AsyncService {

    private static final Logger log = LoggerFactory.getLogger(AsyncService.class);
    
    @Autowired
    private DroolsRuleService droolsService;

	@Autowired
	private DroolsActionsService droolsActionsService;

	@Autowired
	private MessageToWoData messageToWoData;

	@Autowired
	private WorkOrderConsumer consumer;

	@Autowired
	private AdapterBuilder adapterBuilder;

    private HashMap<RuleType, RuleAdapter> rules;

	// TODO: check if this is at all needed. It seams the only added value over te main method
	//  is the map (the rest is logs)
	@Override
	public Boolean runRule(WorkOrderData wo, String ruleType) {
		LocalDateTime iniDateTime = LocalDateTime.now();
		log.debug("Starting Rule : " + iniDateTime);
		HashMap<RuleType, RuleAdapter> rules = droolsService.getRulesByType(Constants.A);
		List<RuleType> ruleTypes = new ArrayList<>(rules.keySet()).stream().toList();
		log.debug("Running WO: "+ wo.getWoNumber() + " - " + this.getJobsStr(wo));
		Boolean saved = this.runRules(ruleTypes, wo, rules);
		log.debug("Finished WO: "+ wo.getWoNumber() + " - "+ this.getJobsStr(wo));
		LocalDateTime endDateTime = LocalDateTime.now();
		long secs = ChronoUnit.SECONDS.between(iniDateTime,endDateTime);
		log.info("Finished run at: " + endDateTime + " - lasted : " + secs );
		return saved;
	}

	@Override
	public Boolean runRule(WorkOrderData wo) {
		LocalDateTime iniDateTime = LocalDateTime.now();
		log.debug("Starting Rule : " + iniDateTime);
		HashMap<RuleType, RuleAdapter> rules = droolsService.getAllRules();
		List<RuleType> ruleTypes = new ArrayList<>(rules.keySet()).stream().toList();
		log.debug("Running WO: "+ wo.getWoNumber() + " - " + this.getJobsStr(wo));
		Boolean saved = this.runRules(ruleTypes, wo, rules);
		log.debug("Finished WO: "+ wo.getWoNumber() + " - "+ this.getJobsStr(wo));
		LocalDateTime endDateTime = LocalDateTime.now();
		long secs = ChronoUnit.SECONDS.between(iniDateTime,endDateTime);
		log.info("Finished run at: " + endDateTime + " - lasted : " + secs );
		return saved;
	}


	private String getJobsStr(WorkOrderData wo) {
		return String.join(", ", AdapterBuilder.getListOfCodes(wo));
	}

	// THIS IS THE ACTUAL POINT OF ENTRY TO THE RULES
	private Boolean runRules(List<RuleType> ruleTypeList, WorkOrderData wo, HashMap<RuleType, RuleAdapter> rules) {
        log.info("start - {} - {}", wo.getWoNumber(), LocalDateTime.now());
		WorkOrderData dto = new WorkOrderData();
		for (RuleType ruleT : ruleTypeList) {
			RuleTypeAdapter facts = AdapterBuilder.ruleTypeAdapterBuilder(ruleT, wo);
			RuleAdapter rule = rules.get(ruleT);
			droolsService.fireAllRules(rule, facts);
            log.info(" - {} - {}", wo.getWoNumber(), LocalDateTime.now());
			dto = droolsActionsService.impactRules(facts);
		}
        log.info(" - {} - {}", wo.getWoNumber(), LocalDateTime.now());
		Boolean sent = false;
		try {
			sent = consumer.callWorkOrder(dto);
			int i =0;
		} catch (Exception e) {
            log.error("Error sending WO: {}", e.getMessage());
		}
        log.debug("end - {} - {}", wo.getWoNumber(), LocalDateTime.now());
		return sent;
	}


	/*
	// NOT NEEDED, AT LEAST FOR NOW THAT RULES ARE RUN INDIVIDUALLY
    @Override
    public HashMap<String,List<String>> runRules(List<WorkOrderData> woList, String ruleType) {
		HashMap<String,List<String>> result = new HashMap<>();
		List<String> errors = new ArrayList<>();
		List<String> saved = new ArrayList<>();
		result.put("SAVED",saved);
		result.put("ERROR",errors);
		LocalDateTime iniDateTime = LocalDateTime.now();
		log.debug("Starting Rules : " + iniDateTime);
		log.debug("initial jobs: "+ woList.size());
		HashMap<RuleType, RuleAdapter> rules = droolsService.getRulesByType(ruleType);
		List<RuleType> ruleTypes = new ArrayList<RuleType>(rules.keySet()).stream().toList();
		for (WorkOrderData wo : woList) {
			log.info(woList.indexOf(wo) + " Running WO: "+ wo.getWoNumber() + " - " + this.getJobsStr(wo));
			Boolean ok = this.runRules(ruleTypes, wo, rules);
			if(ok) {
				saved.add(wo.getWoNumber());
			} else {
				errors.add(wo.getWoNumber());
			}
			log.info(woList.indexOf(wo) + " Finished WO: "+ wo.getWoNumber() + " - "+ this.getJobsStr(wo));
		}
		LocalDateTime endDateTime = LocalDateTime.now();
		long secs = ChronoUnit.SECONDS.between(iniDateTime,endDateTime);
		log.info("Finished run at: " + endDateTime + " - lasted : " + secs );
		return result;
    }
	*/


}
