package org.acme.rules.drools;


public interface RuleCRUD {
    void delete(String name);
    void recover(String name);
}
