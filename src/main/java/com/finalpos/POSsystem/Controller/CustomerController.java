package com.finalpos.POSsystem.Controller;

<<<<<<< Updated upstream
import com.finalpos.POSsystem.Model.CustomerRepository;
=======
import com.finalpos.POSsystem.Config.FirebaseService;
import com.finalpos.POSsystem.Model.*;
import com.finalpos.POSsystem.Model.Package;
import jakarta.persistence.criteria.Order;
>>>>>>> Stashed changes
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RestController
@ResponseBody
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
<<<<<<< Updated upstream
    CustomerRepository cusDb;
    @GetMapping("/")  // Đạt xong roi
    private Package getAllCustomers(){
        try {
            return new Package(0, "success", cusDb.findAll());
=======
    CustomerRepository db;

//    @Autowired
//    OrderRepository db1;

    @GetMapping("/")  // Đạt
    private Package getAllCustomers(@RequestParam Optional<String> page){
        try {
            int pageSize = 10;
            int pageNumber = 1;
            if(!page.isEmpty() && page.get() != "null") {
                pageNumber = Integer.parseInt(page.get());
            }
            int skipAmount = (pageNumber - 1) * pageSize;
            int totalUsers = (int) db.count();
            int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

            List<CustomerModel> customerLists = db.findAll();
            List<CustomerModel> customer = new ArrayList<>();

            int endIdx = Math.min(skipAmount + pageSize, customerLists.size());
            for (int i = skipAmount; i < endIdx; i++) {
                customer.add(customerLists.get(i));
            }
            Object data = new Object() {
                public final List<CustomerModel> customers = customer;
                public final int divider = totalPages;
            };
            return new Package(0, "success", data);
>>>>>>> Stashed changes
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @GetMapping("/{id}") // Đạt xong roi
    private Package getCustomerById(@PathVariable("id") String id){
        try {
<<<<<<< Updated upstream
            return new Package(0, "success", cusDb.findById(id));
=======
            Optional<CustomerModel> userModel = db.findById(id);
            return new Package(0, "success", userModel);
>>>>>>> Stashed changes
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @GetMapping("/{id}/transactions") // Đạt
    private Package getTransactionsByCustomerId(@PathVariable("id") String id){
        try {
//            List<OrderModel> orderModel = db1.findByCustomer_id(id);
//            return new Package(0, "success", orderModel);
            return new Package(0, "success", null);

        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }
}
