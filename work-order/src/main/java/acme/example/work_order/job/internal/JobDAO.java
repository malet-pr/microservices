package acme.example.work_order.job.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobDAO extends JpaRepository<Job, Long> {
    Job findByCode(String job);
    Job findByCodeAndActiveStatus(String job, Character activeStatus);
    @Query(value="select * from JOB where code in ?1",nativeQuery = true)
    List<Job> findByCodes(List<String> codes);
    @Query(value="select * from JOB where code in ?1 and active_status = ?2",nativeQuery = true)
    List<Job> findByCodesAndActiveStatus(List<String> codes, Character activeStatus);
}
