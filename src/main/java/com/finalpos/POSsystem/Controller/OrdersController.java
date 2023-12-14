package com.finalpos.POSsystem.Controller;

import com.finalpos.POSsystem.Model.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.finalpos.POSsystem.Model.Package;

@Controller
@RestController
@ResponseBody
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    OrderRepository orderDb;
    @GetMapping("/{order_number}") // Đạt
    private Package getOrderByOrderNumber(@PathVariable("order_number") String order_number){
        try {
            return new Package(0, "success", null);
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @GetMapping("/") // Đạt xong roi
    private Package getAllOrders(){
        try {
            return new Package(0, "success", orderDb.findAll());
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }
}
