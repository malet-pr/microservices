package acme.example.work_order.jobtype.internal;

import acme.example.work_order.jobtype.JobTypeDTO;
import acme.example.work_order.jobtype.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobTypeServiceImpl implements JobTypeService {
    
    @Autowired
    private JobTypeDAO jobTypeDAO;

    @Autowired
    private JobTypeMapper jobTypeMapper = new JobTypeMapper();
    
    @Override
    public JobTypeDTO findById(Long id) {
        JobType entity =  jobTypeDAO.findById(id).orElse(null);
        if (entity == null) { return null; }
        return jobTypeMapper.convertToDto(entity);
    }

    @Override
    public boolean save(JobTypeDTO jobType) {
        JobType entity = jobTypeMapper.convertToEntity(jobType);
        try {
            jobTypeDAO.save(entity);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public Character getActiveStatusById(Long id) {
        JobType entity =  jobTypeDAO.findById(id).orElse(null);
        return entity != null ? entity.getActiveStatus() : null;
    }

    @Override
    public JobTypeDTO findByCode(String code) {
        JobType entity = jobTypeDAO.findByCode(code);
        return jobTypeMapper.convertToDto(entity);
    }

    @Override
    public JobTypeDTO findByCodeAndActiveStatus(String code, Character activeStatus) {
        JobType entity = jobTypeDAO.findByCodeAndActiveStatus(code,activeStatus);
        return jobTypeMapper.convertToDto(entity);
    }

    @Override
    public List<JobTypeDTO> findByCodesAndActiveStatus(List<String> codes, Character activeStatus) {
        List<JobType> actividades = jobTypeDAO.findByCodesAndActiveStatus(codes,activeStatus);
        return actividades.stream().map(jobTypeMapper::convertToDto).toList();
    }

    @Override
    public List<Long> findByCodes(List<String> codes) {
        List<JobType> list = jobTypeDAO.findByCodes(codes);
        return list.stream().map(JobType::getId).toList();
    }
    
}
