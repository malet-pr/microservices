package org.acme.rules.drools;

public class TestData {

    public static String imports =
            """
            import java.util.List
            import java.util.Set
            import java.util.stream.Collectors
            import org.acme.rules.drools.internal.WoData;
            import org.acme.rules.drools.internal.WoJob;
            import org.acme.rules.drools.internal.RulesServiceImpl
            import org.acme.rules.drools.internal.RulesServiceImpl;
              
            dialect "mvel"      
            """;

    public static String rule =
            """
            rule "disable job for jobtype"
                when
                     $order: WoData(jobTypeCode == 'code1')
                     WoJob(jobCode == "job1") from $order.woJobs
                then
                     RulesServiceImpl.disableJob($order,"job1","disable job for jobtype");
                     System.out.println($order.woNumber + " - applied rule: disable job1 for code1");
            end
            """;



}
