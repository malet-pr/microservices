package acme.example.work_order.jobtype.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface JobTypeDAO extends JpaRepository<JobType,Long> {
    JobType findByCode(String code);
    JobType findByCodeAndActiveStatus(String code, Character activeStatus);
    @Query(value="select * from JOB_TYPE where code in ?1",nativeQuery = true)
    List<JobType> findByCodes(List<String> codes);
    @Query(value="select * from JOB_TYPE where code in ?1 and active_status = ?2",nativeQuery = true)
    List<JobType> findByCodesAndActiveStatus(List<String> codes, Character activeStatus);
}
