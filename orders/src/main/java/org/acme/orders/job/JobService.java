package org.acme.orders.job;

import java.util.List;

public interface JobService {
    public JobDTO findById(Long id);
    public boolean save(JobDTO job);
    public String getNameByJob(Long id);
    public Character getActiveStatusById(Long id);
    public JobDTO findByCode(String code);
    public JobDTO findByCodeAndActiveStatus(String code, Character activeStatus);
    public List<JobDTO> findByCodesAndActiveStatus(List<String> codes, Character activeStatus);
    public List<JobDTO> findByCodes(List<String> codes);
    public Long findIdByCode(String code);
}
