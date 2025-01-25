package org.acme.orders.api;

import org.acme.orders.orderjob.OrderJobDTO;
import org.acme.orders.orderjob.OrderJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workorderjobs")
public class OrderJobController {

    @Autowired
    private OrderJobService woJobService;

    @PostMapping()
    public ResponseEntity<Boolean> createWorkOrderJob(@RequestBody OrderJobDTO dto) {
        try{
            boolean saved = woJobService.save(dto);
            return saved ? new ResponseEntity<>(true, HttpStatus.CREATED): new ResponseEntity<>(false,HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderJobDTO> getWorkOrder(@PathVariable Long id) {
        try{
            OrderJobDTO dto = woJobService.findById(id);
            return dto != null ? new ResponseEntity<>(dto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/codes")
    public ResponseEntity<List<OrderJobDTO>> findByCodes(@RequestParam("codeList") List<String> codeList) {
        try {
            List<OrderJobDTO> codes = woJobService.findByCodes(codeList);
            return new ResponseEntity<>(codes, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}

/*
    List<OrderJobDTO> findByIds(List<Long> ids);
 */
