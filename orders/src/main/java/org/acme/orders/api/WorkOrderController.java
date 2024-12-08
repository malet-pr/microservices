package org.acme.orders.api;

import org.acme.orders.order.OrderDTO;
import org.acme.orders.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/workorders")
public class WorkOrderController {

    @Autowired
    private OrderService woService;

    @PostMapping()
    public ResponseEntity<Boolean> createWorkOrder(@RequestBody OrderDTO dto) {
        try{
            boolean saved = woService.save(dto);
            return saved ? new ResponseEntity<>(true, HttpStatus.CREATED): new ResponseEntity<>(false,HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getWorkOrder(@PathVariable Long id) {
        try{
            OrderDTO dto = woService.findById(id);
            return dto != null ? new ResponseEntity<>(dto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping()
    public ResponseEntity<OrderDTO> getWorkOrderByNumber(@RequestParam("woNumber") String woNumber) {
        try{
            OrderDTO dto = woService.findByWoNumber(woNumber);
            return dto != null ? new ResponseEntity<>(dto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

}

