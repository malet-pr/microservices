package acme.example.work_order.api;

import acme.example.work_order.workorder.WorkOrderDTO;
import acme.example.work_order.workorder.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workorders")
public class WorkOrderController {

    @Autowired
    private WorkOrderService woService;

    @PostMapping()
    public ResponseEntity<Boolean> createWorkOrder(@RequestBody WorkOrderDTO dto) {
        try{
            boolean saved = woService.save(dto);
            return saved ? new ResponseEntity<>(true, HttpStatus.CREATED): new ResponseEntity<>(false,HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkOrderDTO> getWorkOrder(@PathVariable Long id) {
        try{
            WorkOrderDTO dto = woService.findById(id);
            return dto != null ? new ResponseEntity<>(dto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping()
    public ResponseEntity<WorkOrderDTO> getWorkOrderByNumber(@RequestParam("woNumber") String woNumber) {
        try{
            WorkOrderDTO dto = woService.findByWoNumber(woNumber);
            return dto != null ? new ResponseEntity<>(dto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}

