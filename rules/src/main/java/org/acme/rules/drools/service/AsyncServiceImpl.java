package org.acme.rules.drools.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.acme.rules.drools.internal.adapter.RuleAdapter;
import org.acme.rules.drools.internal.adapter.RuleTypeAdapter;
import org.acme.rules.drools.internal.model.RuleType;
import org.acme.rules.drools.internal.repository.RuleTypeDAO;
import org.acme.rules.drools.internal.util.AdapterBuilder;
import org.acme.rules.grpc.woserviceconnect.WorkOrderDTO;
import org.acme.rules.grpc.woserviceconnect.WorkOrderJobDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AsyncServiceImpl implements AsyncService {

    private static final Logger log = LoggerFactory.getLogger(AsyncService.class);
    
    @Autowired
    private DroolsRuleService droolsService;

    @Autowired
    private DroolsActionsService droolsActionsService;

    @Autowired
    private RuleTypeDAO ruleTypeDAO;

    private HashMap<RuleType, RuleAdapter> rules;

	/*
	@Autowired
    private WorkOrderService workOrderService;

	@Autowired
    private JobService jobService;

	 */

	// NOT NEEDED, THE DTO ALREADY BRINGS A LIST OF CODES
	// TODO: build a common method to convert the list in a concatenated String
	// TODO: move that method to an utils folderr
	/*
    private void woJobs(List<WoRuleAdapter> woList) {
        int i = 0;
        StringBuilder sb = new StringBuilder("\r\n");
        for (WoRuleAdapter wo : woList) {
            i++;
            sb.append("WO-").append(i).append(": ").append(wo.getWoNumber())
					.append(" - JOBS:  ").append(wo.getJobCodeList())
					.append("\r\n");
        }
        log.info(sb.toString());
    }
    */

	// NOT NEEDED, AT LEAST FOR NOW THAT RULES ARE RUN INDIVIDUALLY
	// TODO: remove for now
    @Override
    public HashMap<String,List<String>> runRules(List<WorkOrderDTO> woList, String ruleType) {
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
		for (WorkOrderDTO wo : woList) {
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

	// TODO: check if this is at all needed. It seams the only added value over te main method is the map (the rest is logs)
	@Override
	public Boolean runRule(WorkOrderDTO wo, String ruleType) {
		LocalDateTime iniDateTime = LocalDateTime.now();
		log.debug("Starting Rule : " + iniDateTime);
		HashMap<RuleType, RuleAdapter> rules = droolsService.getRulesByType(ruleType);
		List<RuleType> ruleTypes = new ArrayList<>(rules.keySet()).stream().toList();
		log.debug("Running WO: "+ wo.getWoNumber() + " - " + this.getJobsStr(wo));
		Boolean saved = this.runRules(ruleTypes, wo, rules);
		log.debug("Finished WO: "+ wo.getWoNumber() + " - "+ this.getJobsStr(wo));
		LocalDateTime endDateTime = LocalDateTime.now();
		long secs = ChronoUnit.SECONDS.between(iniDateTime,endDateTime);
		log.info("Finished run at: " + endDateTime + " - lasted : " + secs );
		return saved;
	}

	// TODO: in work-order, filter by activeStatus before sending the dto
	// use method created before to concatenate list of Strings
	// with all these, it is possible that this method becomes dead code
	private String getJobsStr(WorkOrderDTO wo) {
		ArrayList<String> acts = new ArrayList<String>();
		List<WorkOrderJobDTO> woJobDTOs = wo.getWoJobDTOs();
		List<String> codes = woJobDTOs.stream().map(WorkOrderJobDTO::getJobCode).toList();
		/*
		return jobService.findByCodesAndActiveStatus(codes, Constants.ACTIVE_Y)
				.stream().map(JobDTO::getCode).collect(Collectors.joining(", "));
		 */
		return "";
	}

	// THIS IS THE ACTUAL POINT OF ENTRY TO THE RULES
	// TODO: logs of time not needed having traces. Return void
	private Boolean runRules(List<RuleType> ruleTypeList, WorkOrderDTO wo, HashMap<RuleType, RuleAdapter> rules) {
        log.debug("start - {} - {}", wo.getWoNumber(), LocalDateTime.now());
		for (RuleType ruleT : ruleTypeList) {
			RuleTypeAdapter facts = AdapterBuilder.ruleTypeAdapterBuilder(ruleT, wo);
			RuleAdapter rule = rules.get(ruleT);
			droolsService.fireAllRules(rule, facts);
            log.debug(" - {} - {}", wo.getWoNumber(), LocalDateTime.now());
			wo = droolsActionsService.impactRules(facts);
		}
        log.info(" - {} - {}", wo.getWoNumber(), LocalDateTime.now());
		Boolean saved = false;
		try {
//            saved = workOrderService.save(wo);
			// TODO: send the result back to work-order to be saved
		} catch (Exception e) {
            log.error("Error trying to save WO: {}", e.getMessage());
			// TODO: handle error in both services
		}
        log.debug("end - {} - {}", wo.getWoNumber(), LocalDateTime.now());
		return saved;
	}

}
