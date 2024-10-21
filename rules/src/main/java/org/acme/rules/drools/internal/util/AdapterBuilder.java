package org.acme.rules.drools.internal.util;

import org.acme.rules.drools.internal.adapter.RuleTypeAdapter;
import org.acme.rules.drools.internal.adapter.WoRuleAdapter;
import org.acme.rules.drools.internal.model.RuleType;
import org.acme.rules.grpc.woserviceconnect.WORuleDTO;
import org.acme.rules.grpc.woserviceconnect.WorkOrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

// TODO: use more appropriate name, move to an utils folder

@Component
public class AdapterBuilder {

    @Autowired
    WorkOrderUtils workOrderUtils;

    private static WoRuleMapper woRuleMapper;

    public static RuleTypeAdapter ruleTypeAdapterBuilder(RuleType type, WorkOrderDTO dto) {
        RuleTypeAdapter adapter = new RuleTypeAdapter();
        adapter.setWoInProcess(woRuleMapper.convertToAdapter(dto));
        return adapter;
    }

    public static WoRuleAdapter buildOTReglaAdapter(WorkOrderDTO dto) {
        WoRuleAdapter adapter = new WoRuleAdapter();
        /*
        List<String> codes = dto.getWoJobDTOs().stream().map(WorkOrderJobDTO::getJobCode).toList();
        List<String> jobCodeList = Collections.singletonList(jobService.findByCodesAndActiveStatus(codes, Constants.YES)
                .stream().map(JobDTO::getCode).collect(Collectors.joining(", ")));
        */
        List<String> jobCodeList = new ArrayList<>(); // TODO: this part will be covered by methods created in AsynncService
        adapter.setJobCodeList(jobCodeList);
        adapter.setState(dto.getState());  // TODO: figure out what this is
        if (dto.getWoCreationDate()!= null) {
            adapter.setWoCreationDate(dto.getWoCreationDate().toLocalDate());
        }
        adapter.setWoCompletionDate(dto.getWoCompletionDate().toLocalDate());
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
            adapter.setWoCreationDate(dto.getWoCreationDate().toLocalDate());
            adapter.setWoCreationDateTime(dto.getWoCreationDate());
        } else {
            System.out.println("WO creation date is missing: " + dto.getWoNumber());
        }
        if (dto.getWoCompletionDate() != null) {
            adapter.setWoCompletionDate(dto.getWoCompletionDate().toLocalDate());
            adapter.setWoCompletionDateTime(dto.getWoCompletionDate());
        } else {
            System.out.println("WO completion date is missing: " + dto.getWoNumber());
        }
        adapter.setWoNumber(dto.getWoNumber());
        adapter.setClientID(dto.getClientId());
        adapter.setJobCodeList(new ArrayList<String>());
        if (dto.getJobCodesList()!= null && !dto.getJobCodesList().isBlank()) {
            String[] jobCodeList = dto.getJobCodesList().split(";");
            adapter.getJobCodeList().addAll(Arrays.asList(jobCodeList));
        }
        return adapter;
    }

}