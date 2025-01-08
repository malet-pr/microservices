package org.acme.rules.drools.internal.service;

import org.acme.rules.drools.AsyncService;
import org.acme.rules.drools.MessageToWoData;
import org.acme.rules.drools.internal.util.Constants;
import org.acme.rules.grpc.WorkOrderConsumer;
import org.acme.rules.drools.internal.util.RuleAdapter;
import org.acme.rules.drools.internal.util.RuleTypeAdapter;
import org.acme.rules.drools.internal.model.RuleType;
import org.acme.rules.drools.internal.util.AdapterBuilder;
import org.acme.rules.grpc.woserviceconnect.Order;
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

    private HashMap<RuleType, RuleAdapter> ruleMap;

	// TODO: check if this is at all needed. It seams the only added value over te main method
	//  is the map (the rest is logs)
	@Override
	public Boolean runRule(Order order, String ruleType) {
		LocalDateTime iniDateTime = LocalDateTime.now();
		log.debug("Starting Rule : {}", iniDateTime);
		HashMap<RuleType, RuleAdapter> ruleList = droolsService.getRulesByType(Constants.A);
		List<RuleType> ruleTypes = new ArrayList<>(ruleList.keySet()).stream().toList();
		log.debug("Running Order: {} - {}", order.getWoNumber(),this.getJobsStr(order));
		Boolean saved = this.runRules(ruleTypes, order, ruleList);
		log.debug("Finished Order: {} - {}", order.getWoNumber(),this.getJobsStr(order));
		LocalDateTime endDateTime = LocalDateTime.now();
		long secs = ChronoUnit.SECONDS.between(iniDateTime,endDateTime);
		log.info("Finished run at: {} - lasted : {}", endDateTime, secs );
		return saved;
	}

	@Override
	public Boolean runRule(Order order) {
		LocalDateTime iniDateTime = LocalDateTime.now();
		log.debug("Starting Rule : {}", iniDateTime);
		HashMap<RuleType, RuleAdapter> ruleList = droolsService.getAllRules();
		List<RuleType> ruleTypes = new ArrayList<>(ruleList.keySet()).stream().toList();
		log.debug("Running Order: {} - {}", order.getWoNumber(),this.getJobsStr(order));
		Boolean saved = this.runRules(ruleTypes, order, ruleList);
		log.debug("Finished Order: {} - {}", order.getWoNumber(),this.getJobsStr(order));
		LocalDateTime endDateTime = LocalDateTime.now();
		long secs = ChronoUnit.SECONDS.between(iniDateTime,endDateTime);
		log.info("Finished run at: {} - lasted : {}", endDateTime, secs );
		return saved;
	}


	private String getJobsStr(Order order) {
		return String.join(", ", AdapterBuilder.getListOfCodes(order));
	}

	// THIS IS THE ACTUAL POINT OF ENTRY TO THE RULES
	private Boolean runRules(List<RuleType> ruleTypeList, Order order, HashMap<RuleType, RuleAdapter> rules) {
        log.info("start - {} - {}", order.getWoNumber(), LocalDateTime.now());
		Order dto = new Order();
		for (RuleType ruleT : ruleTypeList) {
			RuleTypeAdapter facts = AdapterBuilder.ruleTypeAdapterBuilder(ruleT, order);
			RuleAdapter rule = rules.get(ruleT);
			droolsService.fireAllRules(rule, facts);
            log.info(" - {} - {}", order.getWoNumber(), LocalDateTime.now());
			dto = droolsActionsService.impactRules(facts);
		}
        log.info(" - {} - {}", order.getWoNumber(), LocalDateTime.now());
		boolean sent = false;
		try {
			consumer.callWorkOrder(dto);
			sent = true;
		} catch (Exception e) {
            log.error("Error sending WO: {}", e.getMessage());
		}
        log.debug("end - {} - {}", order.getWoNumber(), LocalDateTime.now());
		return sent;
	}

}
