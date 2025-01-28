package org.acme.rules.drools;

public class TestData {

    public static String imports =
            """
            import java.util.List
            import java.util.Set
            import java.util.Map
            import java.util.HashMap;
            import java.util.stream.Collectors
            import org.acme.rules.drools.internal.WoData;
            import org.acme.rules.drools.internal.WoJob;
            import org.acme.rules.drools.internal.RulesServiceImpl
            import org.acme.rules.drools.internal.Condition;
            import org.acme.rules.drools.internal.Action;
              
            dialect "mvel"      
            """;

    public static String rule1 =
            """
            rule "disable job for jobtype"
                when
                     $order: WoData(jobTypeCode == 'code1')
                     WoJob(jobCode == "job1") from $order.woJobs
                then
                     Action.disableJob($order,"job1","disable job for jobtype");
                     System.out.println($order.woNumber + " - applied rule: disable job1 for code1");
            end
            """;

    public static String rule2 =
            """
            rule "add job for jobtype"
                when
                     $order: WoData(jobTypeCode == 'code1b')
                then
                     Action.addJob($order,"job2",5,"add job for jobtype");
                     System.out.println($order.woNumber + " - applied rule: add job2 for code1b");
            end
            """;

    public static String rule3 =
            """
            rule "disable list of job for jobtype"
                when
                     $order: WoData(jobTypeCode == 'code1c')
                     WoJob(jobCode == "job1") from $order.woJobs
                     WoJob(jobCode == "job2") from $order.woJobs
                then
                     Action.disableJobs($order,List.of("job1","job2"),"disable list of jobs for jobtype");
                     System.out.println($order.woNumber + " - applied rule: disable job1,job2 for code1c");
            end
            """;

    public static String rule4 =
            """
            global java.util.Map map;
            
            rule "add list of job for jobtype"
                when
                     $order: WoData(jobTypeCode == 'code3')
                     WoJob(jobCode != "job1") from $order.woJobs
                     WoJob(jobCode != "job2") from $order.woJobs
                then
                     map = Map.of("job1",2,"job2",4);
                     Action.addJobs($order,map,"add list of jobs for jobtype");
                     System.out.println($order.woNumber + " - applied rule: add job1,job2 for code3");
            end
            """;
}

//String( this in ("code1", "code2" ) ) from $codes