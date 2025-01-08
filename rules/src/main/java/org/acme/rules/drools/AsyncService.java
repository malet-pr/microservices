package org.acme.rules.drools;

import org.acme.rules.grpc.woserviceconnect.Order;

public interface AsyncService {

	Boolean runRule(Order order, String ruleType);
	Boolean runRule(Order order);

}
