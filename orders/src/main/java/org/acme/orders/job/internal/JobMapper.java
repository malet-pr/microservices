package org.acme.orders.job.internal;

import org.acme.orders.job.JobDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class JobMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public JobDTO convertToDto(Job job) {
        if(job == null) {return null;}
        return modelMapper.map(job, JobDTO.class);
    }

    public Job convertToEntity(JobDTO jobDTO) {
        if(jobDTO == null) {return null;}
        return modelMapper.map(jobDTO, Job.class);
    }


}
