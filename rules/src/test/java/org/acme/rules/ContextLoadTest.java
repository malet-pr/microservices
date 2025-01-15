package org.acme.rules;

import org.acme.rules.drools.internal.RuleDAO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ContextLoadTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void testRuleDAOBeanExists() {
        assertNotNull(context.getBean(RuleDAO.class), "RuleDAO bean is not registered");
    }
}