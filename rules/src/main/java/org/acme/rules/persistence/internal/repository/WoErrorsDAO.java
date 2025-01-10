package org.acme.rules.persistence.internal.repository;

import org.acme.rules.persistence.internal.model.WoErrors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WoErrorsDAO extends JpaRepository<WoErrors,Long> {
}