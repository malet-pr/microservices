package org.acme.rules.drools.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RuleDAO extends JpaRepository<Rule,Long> {

	List<Rule> findByActiveStatus(Character activeStatus);
	List<Rule> findByGrouping(String grouping);

}
