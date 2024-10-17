package org.acme.rules.drools.internal.repository;

import org.acme.rules.drools.internal.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;


public interface RuleDAO extends JpaRepository<Rule,Long> {

	@Query(value="SELECT * FROM rule WHERE LOWER(name) = LOWER(?1) AND active_status = 'Y'", nativeQuery = true)
	Optional<Rule> findByNameIgnoreCaseAndActive(String name);

	List<Rule> findByActiveStatus(Character activeStatus);
	List<Rule> findByGrouping(String grouping);

}
