package com.finalpos.POSsystem.Controller;

<<<<<<< Updated upstream
=======
import com.finalpos.POSsystem.Model.OrderModel;
>>>>>>> Stashed changes
import com.finalpos.POSsystem.Model.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.finalpos.POSsystem.Model.Package;

import java.util.List;
import java.util.Optional;

@Controller
@RestController
@ResponseBody
@RequestMapping("/api/orders")
public class OrdersController {
//    @Autowired
//    OrderRepository db;

    @Autowired
    OrderRepository orderDb;
    @GetMapping("/{order_number}") // Đạt
    private Package getOrderByOrderNumber(@PathVariable("order_number") String order_number){
        try {
//            OrderModel orderModels = db.findByOrder_number(order_number);
            return new Package(0, "success", null);
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @GetMapping("/") // Đạt xong roi
    private Package getAllOrders(){
        try {
<<<<<<< Updated upstream
            return new Package(0, "success", orderDb.findAll());
=======
//            List<OrderModel> orderModels = db.findAll();
            return new Package(0, "success", null);
>>>>>>> Stashed changes
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }
}
