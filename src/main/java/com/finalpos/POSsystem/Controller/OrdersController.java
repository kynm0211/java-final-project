package com.finalpos.POSsystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.finalpos.POSsystem.Model.Package;

@Controller
@RestController
@ResponseBody
@RequestMapping("/api/orders")
public class OrdersController {

    @GetMapping("/{order_number}") // Đạt
    private Package getOrderByOrderNumber(@PathVariable("order_number") String order_number){
        try {
            return new Package(0, "success", null);
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @GetMapping("/") // Đạt
    private Package getAllOrders(){
        try {
            return new Package(0, "success", null);
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }
}
