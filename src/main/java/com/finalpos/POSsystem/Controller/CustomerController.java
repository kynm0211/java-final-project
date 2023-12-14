package com.finalpos.POSsystem.Controller;

import com.finalpos.POSsystem.Model.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.finalpos.POSsystem.Model.Package;

@Controller
@RestController
@ResponseBody
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    CustomerRepository cusDb;
    @GetMapping("/")  // Đạt xong roi
    private Package getAllCustomers(){
        try {
            return new Package(0, "success", cusDb.findAll());
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @GetMapping("/{id}") // Đạt xong roi
    private Package getCustomerById(@PathVariable("id") String id){
        try {
            return new Package(0, "success", cusDb.findById(id));
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @GetMapping("/{id}/transactions") // Đạt
    private Package getTransactionsByCustomerId(@PathVariable("id") String id){
        try {
            return new Package(0, "success", null);
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }
}
