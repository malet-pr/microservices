package org.acme.rules.drools.internal.util;

import org.acme.rules.drools.internal.adapter.WoRuleAdapter;
import org.acme.rules.grpc.woserviceconnect.WorkOrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WoRuleMapper {
    private final ModelMapper modelMapper = new ModelMapper();
    public WoRuleAdapter convertToAdapter(WorkOrderDTO woDTO){
        if(woDTO == null){return null;}
        return modelMapper.map(woDTO, WoRuleAdapter.class);
    }
    public WorkOrderDTO convertToDto(WoRuleAdapter woRuleAdapter){
        if(woRuleAdapter == null){return null;}
        return modelMapper.map(woRuleAdapter, WorkOrderDTO.class);
    }
}
