package org.acme.rules.drools;

import java.util.HashMap;
import java.util.List;


public interface AsyncService {

	//HashMap<String,List<String>> runRules(List<WorkOrderData> woList, String ruleType);
	Boolean runRule(WorkOrderData wo, String ruleType);
	Boolean runRule(WorkOrderData wo);

}
