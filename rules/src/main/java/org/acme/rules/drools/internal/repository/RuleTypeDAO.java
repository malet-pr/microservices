package org.acme.rules.drools.internal.repository;

import org.acme.rules.drools.internal.model.RuleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface RuleTypeDAO extends JpaRepository<RuleType,Long> {
	RuleType findByCode(String code);
	List<RuleType> findByGroupingOrderByPriorityAsc(String grouping);
	List<RuleType> findAll();
}