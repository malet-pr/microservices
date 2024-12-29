package org.acme.simulator.simulations.internal;

import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class OrderSimulator {

    private static final Logger log = LoggerFactory.getLogger(OrderSimulator.class);
    Faker faker = new Faker(Locale.ENGLISH);
    Random random = new Random();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    public List<OrderJob> simulateWOJ(String woNumber, String jobTypeCode, int n){
        List<OrderJob> wjList = new ArrayList<>();
        List<String> allCodes = JobCode.BY_JOB_TYPE.get(jobTypeCode).stream().map(JobCode::name).toList();
        while(n > 0){
            String jc = allCodes.get(random.nextInt(allCodes.size()));
            int q = random.nextInt(1,11);
            wjList.add(new OrderJob(woNumber,jc,q,'Y',null));
            n--;
        }
        return wjList;
    }

    public Order simulate(String woNum) {
        String jobTypeCode = JobType.NAMES.get(random.nextInt(0,JobType.NAMES.size()));
        List<OrderJob> wjList = simulateWOJ(woNum, jobTypeCode, random.nextInt(1,5));
        String address = faker.address().streetAddress();
        String city = faker.address().city();
        String state = faker.address().state();
        String strDate = faker.date().past(random.nextInt(1,6), TimeUnit.DAYS, "MM-dd-YYYY HH:mm:ss");
        LocalDateTime woCreationDate = LocalDateTime.parse(strDate, formatter);
        LocalDateTime woCompletionDate = woCreationDate.plusDays(random.nextLong(1,4));
        String clientId = faker.expression("#{numerify '########'}");
        return new Order(woNum,wjList,jobTypeCode,address,city,state,woCreationDate,woCompletionDate,clientId,false);
    }

}
