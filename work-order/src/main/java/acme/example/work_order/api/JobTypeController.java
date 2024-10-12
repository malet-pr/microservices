package acme.example.work_order.api;

import acme.example.work_order.jobtype.JobTypeDTO;
import acme.example.work_order.jobtype.JobTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobtypes")
public class JobTypeController {

    @Autowired
    private JobTypeService typeService;

    @GetMapping("/{id}")
    public ResponseEntity<JobTypeDTO> getJobType(@PathVariable("id") Long id) {
        try {
            JobTypeDTO type = typeService.findById(id);
            return type != null ? new ResponseEntity<>(type, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping()
    public ResponseEntity<Boolean> createJobType(@RequestBody JobTypeDTO typeDTO) {
        try{
            boolean saved = typeService.save(typeDTO);
            return saved ? new ResponseEntity<>(true, HttpStatus.CREATED) : new ResponseEntity<>(false, HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<String> getActiveStatus(@PathVariable("id") Long id) {
        try{
            Character status = typeService.getActiveStatusById(id);
            return status != null ? new ResponseEntity<>(String.valueOf(status), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }  catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/codes/{code}")
    public ResponseEntity<JobTypeDTO> getJobByCode(@PathVariable("code") String code) {
        try {
            JobTypeDTO type = typeService.findByCode(code);
            return type != null ? new ResponseEntity<>(type, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}

/*
    JobTypeDTO findByCodeAndActiveStatus(String code, Character activeStatus);
    List<JobTypeDTO> findByCodesAndActiveStatus(List<String> codes, Character activeStatus);
    List<Long> findByCodes(List<String> codes);
 */