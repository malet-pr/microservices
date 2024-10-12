package acme.example.work_order.jobtype.internal;


import acme.example.work_order.jobtype.JobTypeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class JobTypeMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public JobTypeDTO convertToDto(JobType jobType) {
        if(jobType == null) {return null;}
        return modelMapper.map(jobType, JobTypeDTO.class);
    }

    public JobType convertToEntity(JobTypeDTO jobTypeDTO) {
        if(jobTypeDTO == null) {return null;}
        return modelMapper.map(jobTypeDTO, JobType.class);
    }


}
