package acme.example.work_order.job.internal;


import acme.example.work_order.api.JobController;
import acme.example.work_order.job.JobDTO;
import acme.example.work_order.job.JobService;
import io.micrometer.observation.annotation.Observed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    JobDAO jobDAO;

    @Autowired
    JobMapper jobMapper = new JobMapper();

    private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

    @Override
    public JobDTO findById(Long id) {
        log.info("Getting data for job with id <{}>", id);
        Job entity = jobDAO.findById(id).orElse(null);
        return jobMapper.convertToDto(entity);
    }

    @Override
    public boolean save(JobDTO job){
        try{
            Job entity = jobMapper.convertToEntity(job);
            jobDAO.save(entity);
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public String getNameByJob(Long id) {
        Optional<Job> entity = jobDAO.findById(id);
        return entity.map(Job::getName).orElse("");
    }

    @Override
    public Character getActiveStatusById(Long id) {
        Optional<Job> entity = jobDAO.findById(id);
        return entity.map(Job::getActiveStatus).orElse(null);
    }

    @Override
    public JobDTO findByCode(String code) {
        Job entity = jobDAO.findByCode(code);
        return jobMapper.convertToDto(entity);
    }

    @Override
    public List<JobDTO> findByCodes(List<String> codes) {
        List<Job> jobs = jobDAO.findByCodes(codes);
        return jobs.stream().map(jobMapper::convertToDto).toList();
    }

    @Override
    public JobDTO findByCodeAndActiveStatus(String code, Character activeStatus) {
        Job entity = jobDAO.findByCodeAndActiveStatus(code,activeStatus);
        return jobMapper.convertToDto(entity);
    }

    @Override
    public List<JobDTO> findByCodesAndActiveStatus(List<String> codes, Character activeStatus) {
        List<Job> jobs = jobDAO.findByCodesAndActiveStatus(codes,activeStatus);
        return jobs.stream().map(jobMapper::convertToDto).toList();
    }

}

