package org.acme.rules.drools.service;

import org.acme.rules.grpc.woserviceconnect.WorkOrderDTO;

import java.util.HashMap;
import java.util.List;


public interface AsyncService {

	HashMap<String,List<String>> runRules(List<WorkOrderDTO> woList, String ruleType);
	Boolean runRule(WorkOrderDTO wo, String ruleType);

}
