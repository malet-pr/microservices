package org.acme.rules.drools.internal.util;

import org.acme.rules.drools.internal.dto.WoRuleAdapter;
import org.acme.rules.grpc.woserviceconnect.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class WoRuleMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public WoRuleAdapter convertToAdapter(Order woDTO){
        if(woDTO == null){return null;}
        return modelMapper.map(woDTO, WoRuleAdapter.class);
    }
    public Order convertToDto(Order woData){
        if(woData == null){return null;}
        return modelMapper.map(woData, Order.class);
    }
}
