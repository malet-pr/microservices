package org.acme.rules.drools.service;

public interface WoErrorsService {
    void add(String woNumber, String grouping, RuntimeException error);
    void add(String woNumber, RuntimeException error);
}
