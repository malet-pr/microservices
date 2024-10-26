package org.acme.rules.drools.internal.util;

import org.acme.rules.drools.WorkOrderData;
import org.acme.rules.drools.internal.dto.WoRuleAdapter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WoRuleMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public WoRuleAdapter convertToAdapter(WorkOrderData woDTO){
        if(woDTO == null){return null;}
        return modelMapper.map(woDTO, WoRuleAdapter.class);
    }
    public WorkOrderData convertToDto(WorkOrderData woData){
        if(woData == null){return null;}
        return modelMapper.map(woData, WorkOrderData.class);
    }
}
