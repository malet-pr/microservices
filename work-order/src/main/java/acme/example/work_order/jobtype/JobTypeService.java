package acme.example.work_order.jobtype;


import java.util.List;

public interface JobTypeService {
    JobTypeDTO findById(Long id);
    boolean save(JobTypeDTO job);
    Character getActiveStatusById(Long id);
    JobTypeDTO findByCode(String code);
    JobTypeDTO findByCodeAndActiveStatus(String code, Character activeStatus);
    List<JobTypeDTO> findByCodesAndActiveStatus(List<String> codes, Character activeStatus);
    List<Long> findByCodes(List<String> codes);
}
