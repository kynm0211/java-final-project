package com.finalpos.POSsystem.Controller;

import com.finalpos.POSsystem.Model.*;
import com.finalpos.POSsystem.Model.Package;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import static com.finalpos.POSsystem.Controller.AccountController.JWT_Key;
@Controller
@RestController
@ResponseBody
@RequestMapping("/api/pos")
public class POSController {
    @Value("${default.application.avatar}")
    private String url;
    @Autowired
    CustomerRepository cusDb;
    @Autowired
    ProductRepository proDb;
    @Autowired
    UserRepository userDb;
    @Autowired
    OrderRepository ordDb;
    @Autowired
    OrderDetailModelRepository ordDetailDb;
    @Autowired
    ProductCartRepository proCartDb;

    @GetMapping("/find-customer/{phone}") // An Nguyen
    private Package findCustomerByPhone(@PathVariable("phone") String phone){
        try {
            CustomerModel customer = cusDb.findByPhone(phone);
            if (customer != null) {
                return new Package(0, "User exist",customer);
            } else
                return new Package(0, "User not exist",customer);
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @PostMapping("/create-customer") // An Nguyen
    private Package createCustomer(@RequestParam String phone,
                                   @RequestParam String name,
                                   @RequestParam String address){
        try {
            CustomerModel customerModel = new CustomerModel(name,phone,address,url);

            if (cusDb.findByPhone(phone) != null) {
                return new Package(0, "User exist", customerModel);
            } else {
                cusDb.save(customerModel);
                return new Package(0, "success", customerModel);
            }
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @PostMapping("/create-a-bill") // Đạt
    private Package createABill(@RequestBody paymentModel payment) {
        try {
            // Get data from RequestBody
            ObjectMapper mapper = new ObjectMapper();

            // Customer session
            Map<String, Object> cusMap = mapper.readValue(mapper.writeValueAsString(payment.getCustomer()), Map.class);
            CustomerModel customerModel = cusDb.findByPhone(cusMap.get("phone").toString());
            if(customerModel == null) {
                if(!cusMap.get("phone").toString().isEmpty() &&
                        !cusMap.get("name").toString().isEmpty() &&
                        !cusMap.get("address").toString().isEmpty()) {
                    createCustomer(
                            (String) cusMap.get("phone"),
                            (String) cusMap.get("name"),
                            (String) cusMap.get("address")
                    );
                } else {
                    return new Package(403, "The data of the customer is not valid", null);
                }
            }

            // Cart session
            List<Map<String, Object>> cartMap = new ArrayList<>();
            for (int i = 0; i < payment.getCart().length; i++) {
                Map<String, Object> map = mapper.readValue(mapper.writeValueAsString(payment.getCart()[i]), Map.class);
                cartMap.add(map);
            }

            // Staff session
            Claims claims = Jwts.parser().setSigningKey(JWT_Key).parseClaimsJws(payment.getToken()).getBody();
            String _id = claims.get("_id", String.class);

            // Calculate bill
            int sub_total = 0, count = 0;
            ArrayList<ProductCartModel> productCartModels = new ArrayList<>();
            for(Map<String, Object> cart: cartMap) {
                ProductCartModel productCartModel = new ProductCartModel();
                ProductModel product = proDb.findByBarcode(cart.get("barcode").toString());
                int amount = Integer.parseInt(cart.get("amount").toString());
                double retail_price = product.getRetail_price();
                sub_total += (int) Math.round(amount * retail_price);
                count += amount;
                productCartModel.setName(product.getName());
                productCartModel.setQuantity(amount);
                productCartModel.setBarcode(product.getBarcode());
                productCartModel.setRetail_price((int) product.getRetail_price());
                productCartModel.setImport_price((int) product.getImport_price());
                productCartModel = proCartDb.save(productCartModel);
                productCartModels.add(productCartModel);
            }
            int tax_fee = sub_total * payment.getTaxrate() / 100;
            int total = sub_total + tax_fee;
            int change = payment.getCash() - total;
            String order_number = java.time.LocalTime.now()
                    .toString()
                    .replace(".", "")
                    .replace(":", "")
                    .substring(0, 12);

            if(change < 0) {
                return new Package(403, "The cash is not enough", null);
            }

            // Create a bill
            OrderModel order = new OrderModel();
            order.setOrderNumber(order_number);
            order.setCustomerId(cusDb.findByPhone(cusMap.get("phone").toString()).getId());
            order.setStaffId(_id);
            order.setTaxrate(payment.getTaxrate());
            order.setTaxfee(tax_fee);
            order.setSub_total(sub_total);
            order.setCash(payment.getCash());
            order.setChange(change);
            order.setTotal(total);
            order.setQuantity(count);
            order.setPaymentMethod((Integer) cusMap.get("paymentMethod"));
            order.setCreated_date(String.valueOf(java.time.LocalDateTime.now()));
            OrderModel result = ordDb.save(order);

            // Create a detail bill
            OrderDetailModel orderDetail = new OrderDetailModel();
            orderDetail.setOrder_id(order.getId());
            orderDetail.setOrder_number(order.getOrderNumber());
            orderDetail.setProducts(productCartModels);
            ordDetailDb.save(orderDetail);

            return new Package(0, "Success", result);
        } catch (Exception e) {
            return new Package(404, e.getMessage(), null);
        }
    }

    @GetMapping("/search-product/{barcode}") // An Nguyen
    private Package searchProduct(@PathVariable("barcode") String barcode){
        try {
            ProductModel product = proDb.findByBarcode(barcode);

            if (product != null) {
                return new Package(0, "Success", product);
            } else {
                return new Package(404, "Product not found", null);
            }
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @GetMapping("/search-products") // An Nguyen
    private Package searchProducts(@RequestParam("terms") String terms){
        try {
            List<ProductModel> productModelList = proDb.findByTerm(terms);
            return new Package(0, "success", productModelList);
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @Getter
    @Setter
    @ToString
    public static class paymentModel{
        private int taxrate;
        private Object customer = new Object(){
            private String name;
            private String phone;
            private String address;
            private int paymentMethod;
        };
        private Object[] cart = new Object[]{
                new Object(){
                    private int amount;
                    private String barcode;
                }
        };
        private int cash;
        private String token;
    }
}