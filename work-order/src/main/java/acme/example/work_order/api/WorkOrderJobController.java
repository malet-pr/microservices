package acme.example.work_order.api;

import acme.example.work_order.workorderjob.WorkOrderJobDTO;
import acme.example.work_order.workorderjob.WorkOrderJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/workorderjobs")
public class WorkOrderJobController {

    @Autowired
    private WorkOrderJobService woJobService;

    @PostMapping()
    public ResponseEntity<Boolean> createWorkOrderJob(@RequestBody WorkOrderJobDTO dto) {
        try{
            boolean saved = woJobService.save(dto);
            return saved ? new ResponseEntity<>(true, HttpStatus.CREATED): new ResponseEntity<>(false,HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkOrderJobDTO> getWorkOrder(@PathVariable Long id) {
        try{
            WorkOrderJobDTO dto = woJobService.findById(id);
            return dto != null ? new ResponseEntity<>(dto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/codes")
    public ResponseEntity<List<WorkOrderJobDTO>> findByCodes(@RequestParam("codeList") List<String> codeList) {
        try {
            List<WorkOrderJobDTO> codes = woJobService.findByCodes(codeList);
            return new ResponseEntity<>(codes, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}

/*
    List<WorkOrderJobDTO> findByIds(List<Long> ids);
 */
