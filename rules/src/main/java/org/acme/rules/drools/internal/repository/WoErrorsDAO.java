package org.acme.rules.drools.internal.repository;

import org.acme.rules.drools.internal.model.WoErrors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WoErrorsDAO extends JpaRepository<WoErrors,Long> {
}