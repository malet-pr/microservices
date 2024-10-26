package org.acme.rules.drools.internal.util;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import org.acme.rules.drools.internal.model.Rule;
import org.acme.rules.drools.internal.model.RuleType;
import org.kie.api.runtime.KieContainer;

@Data
@Builder
public class RuleAdapter {

	private RuleType type;
	private String header;
	private List<Rule> rules;
	private String fullRule;
	private KieContainer kieContainer;

}
