package org.acme.rules.drools.internal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleTypeDAO extends JpaRepository<RuleType,Long> {
    RuleType findByGrouping(String grouping);
}
