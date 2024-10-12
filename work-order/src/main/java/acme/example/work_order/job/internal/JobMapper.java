package acme.example.work_order.job.internal;

import acme.example.work_order.job.JobDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

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
