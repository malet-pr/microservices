package org.acme.rules.drools.internal.util;

import org.acme.rules.drools.dto.RuleDTO;
import org.acme.rules.drools.internal.model.Rule;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RuleMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public Rule convertToEntity(RuleDTO ruleDTO){
        if(ruleDTO == null){return null;}
        return modelMapper.map(ruleDTO, Rule.class);
    }
    public RuleDTO convertToDto(Rule rule){
        if(rule == null){return null;}
        return modelMapper.map(rule, RuleDTO.class);
    }

}
