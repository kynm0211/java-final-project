package com.finalpos.POSsystem.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@ResponseBody
@RequestMapping("/api/pos")
public class POSController {
    @GetMapping("/find-customer/{phone}")
    private Package findCustomerByPhone(@PathVariable("phone") String phone){
        try {
            return new Package(0, "success", null);
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @PostMapping("/create-customer")
    private Package createCustomer(@RequestParam String phone,
                                   @RequestParam String name,
                                   @RequestParam String address){
        try {
            return new Package(0, "success", null);
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @PostMapping("/create-a-bill")
    private Package createABill(@RequestParam String customer_id,
                                @RequestParam String staff_id,
                                @RequestParam int taxrate,
                                @RequestParam int taxfee,
                                @RequestParam int sub_total,
                                @RequestParam int cash,
                                @RequestParam int change,
                                @RequestParam int total,
                                @RequestParam int quantity,
                                @RequestParam int paymentMethod){
        try {
            return new Package(0, "success", null);
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @GetMapping("/search-product/{barcode}")
    private Package searchProduct(@PathVariable("barcode") String barcode){
        try {
            return new Package(0, "success", null);
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @GetMapping("/search-products")
    private Package searchProducts(@RequestParam("terms") String terms){
        try {
            return new Package(0, "success", null);
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }
}
