package org.acme.simulator.simulations.internal;

import net.datafaker.Faker;
import org.acme.simulator.api.KafkaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class WorkOrderSimulator {

    private static final Logger log = LoggerFactory.getLogger(KafkaController.class);

    Faker faker = new Faker(Locale.ENGLISH);
    Random random = new Random();
    List<String> types = List.of("259HT4","055BE1","599HL8","703TV2","337PA8","659RP5","037NJ6");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    public List<WorkOrderJob> simulateWOJ(String woNumber, int n){
        List<WorkOrderJob> wjList = new ArrayList<>();
        while(n > 0){
            String jc = String.valueOf(JobCode.values()[new Random().nextInt(JobCode.values().length)]);
            int q = random.nextInt(1,11);
            wjList.add(new WorkOrderJob(woNumber,jc,q,'Y',null));
            n--;
        }
        return wjList;
    }

    public WorkOrder simulate() {
        String woNum = faker.expression("#{bothify '??##########','true'}");
        List<WorkOrderJob> wjList = simulateWOJ(woNum, random.nextInt(1,5));
        String jobTypeCode = faker.options().nextElement(types);
        String address = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String strDate = faker.date().past(random.nextInt(1,6), TimeUnit.DAYS, "MM-dd-YYYY HH:mm:ss");
        LocalDateTime woCreationDate = LocalDateTime.parse(strDate, formatter);
        LocalDateTime woCompletionDate = woCreationDate.plusDays(random.nextLong(1,4));
        String clientId = faker.expression("#{numerify '########'}");
        return new WorkOrder(woNum,wjList,jobTypeCode,address,city,state,woCreationDate,woCompletionDate,clientId,null);
    }

}
