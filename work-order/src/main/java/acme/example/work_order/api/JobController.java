package acme.example.work_order.api;

import acme.example.work_order.job.JobDTO;
import acme.example.work_order.job.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    private static final Logger log = LoggerFactory.getLogger(JobController.class);

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJob(@PathVariable("id") Long id) {
        log.info("Call getJob with id {}", id);
        try {
            JobDTO job = jobService.findById(id);
            return job != null ? new ResponseEntity<>(job, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/codes/{code}")
    public ResponseEntity<JobDTO> getJobByCode(@PathVariable("code") String code) {
        log.info("Call getJobByCode with code {}", code);
        try {
            JobDTO job = jobService.findByCode(code);
            return job != null ? new ResponseEntity<>(job, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Boolean> createJob(@RequestBody JobDTO jobDTO) {
        try{
            boolean saved = jobService.save(jobDTO);
            return saved ? new ResponseEntity<>(true, HttpStatus.CREATED) : new ResponseEntity<>(false, HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/name")
    public ResponseEntity<String> getNameByJob(@PathVariable("id") Long id) {
        try {
            String name = jobService.getNameByJob(id);
            return !name.isBlank() ?  new ResponseEntity<>(name, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> getActiveStatus(@PathVariable("id") Long id) {
        try{
            Character status = jobService.getActiveStatusById(id);
            return status != null ? new ResponseEntity<>(String.valueOf(status), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }  catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/codes")
    public ResponseEntity<List<JobDTO>> findByCodes(@RequestParam("codeList") List<String> codeList) {
        try {
            List<JobDTO> codes = jobService.findByCodes(codeList);
            return new ResponseEntity<>(codes, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}

/*
    public JobDTO findByCodeAndActiveStatus(String code, Character activeStatus);
    public List<JobDTO> findByCodesAndActiveStatus(List<String> codes, Character activeStatus);
 */