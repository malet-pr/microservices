package org.acme.rules.drools.internal.repository;

import org.acme.rules.drools.internal.model.RuleLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleLogDAO extends JpaRepository<RuleLog,Long> {

}
