package org.acme.rules.drools.internal.util;

import org.acme.rules.grpc.woserviceconnect.Order;
import org.acme.rules.grpc.woserviceconnect.WoJob;
import org.acme.rules.drools.internal.dto.WoRuleAdapter;
import org.acme.rules.drools.internal.model.RuleType;
import org.acme.rules.grpc.woserviceconnect.WORuleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;


@Component
public class AdapterBuilder {

    Logger log = LoggerFactory.getLogger(AdapterBuilder.class);

    @Autowired
    WorkOrderUtils workOrderUtils;

    private static WoRuleMapper woRuleMapper;

    // ARREGLAR ESTE MÈTODO
    public static RuleTypeAdapter ruleTypeAdapterBuilder(RuleType type, Order dataAdapter) {
        RuleTypeAdapter adapter = new RuleTypeAdapter();
        adapter.setWoInProcess(dataAdapter);
        return adapter;
    }

    public static List<String> getListOfCodes(Order order){
        /*
        return order.getWoJobs().stream()
                .filter(j -> j.getActiveStatus().equals('Y'))
                .map(WoJob::getJobCode)
                .toList();
         */
        return List.of("YY171T");
    }

    public static WoRuleAdapter buildOTReglaAdapter(Order dto) {
        WoRuleAdapter adapter = new WoRuleAdapter();
        adapter.setJobCodeList(getListOfCodes(dto));
        adapter.setState(dto.getState());  // TODO: figure out what this is
        adapter.setWoCreationDateTime(dto.getWoCreationDate());
        adapter.setWoCompletionDateTime(dto.getWoCompletionDate());
        adapter.setWoNumber(dto.getWoNumber());
        return adapter;
    }

    public WoRuleAdapter buildOTReglaAdapter(WORuleDTO dto) {
        WoRuleAdapter adapter = new WoRuleAdapter();
        if (dto.getJobCodesList() != null && !dto.getJobCodesList() .isBlank()) {
            adapter.setJobCodeList(Arrays.asList((dto.getJobCodesList().split(";"))));
        }
        adapter.setState(dto.getState());
        adapter.setCity(dto.getCity());
        adapter.setAddress(dto.getAddress());
        if (dto.getWoCreationDate() != null) {
            adapter.setWoCreationDateTime(dto.getWoCreationDate());
        } else {
            log.warn("Order creation date is missing: {}", dto.getWoNumber());
        }
        if (dto.getWoCompletionDate() != null) {
            adapter.setWoCompletionDateTime(dto.getWoCompletionDate());
        } else {
            log.warn("Order completion date is missing: {}", dto.getWoNumber());
        }
        adapter.setWoNumber(dto.getWoNumber());
        adapter.setClientID(dto.getClientId());
        adapter.setJobCodeList(new ArrayList<>());
        if (dto.getJobCodesList()!= null && !dto.getJobCodesList().isBlank()) {
            String[] jobCodeList = dto.getJobCodesList().split(";");
            adapter.getJobCodeList().addAll(Arrays.asList(jobCodeList));
        }
        return adapter;
    }

}