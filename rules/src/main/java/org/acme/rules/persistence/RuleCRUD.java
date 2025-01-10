package org.acme.rules.persistence;


public interface RuleCRUD {
    void delete(String name);
    void recover(String name);
}
