package com.finalpos.POSsystem.Controller;

<<<<<<< Updated upstream
import com.finalpos.POSsystem.Model.*;
import com.finalpos.POSsystem.Model.Package;
=======
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalpos.POSsystem.Model.*;
import com.finalpos.POSsystem.Model.Package;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.shaded.json.parser.ParseException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
>>>>>>> Stashed changes
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< Updated upstream
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
=======
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;

>>>>>>> Stashed changes

@Controller
@RestController
@ResponseBody
@RequestMapping("/api/pos")
public class POSController {
<<<<<<< Updated upstream
    @Value("${default.application.avatar}")
    private String url;
    @Autowired
    CustomerRepository cusDb;
    @Autowired
    ProductRepository proDb;
=======

    @Autowired
    CustomerRepository db1;

    @Autowired
    UserRepository db2;

    @Autowired
    ProductRepository db3;
    protected static Key JWT_Key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
>>>>>>> Stashed changes
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
    private Object createABill(@RequestBody paymentModel payment){
        try {
            // Get data from RequestBody
            ObjectMapper mapper = new ObjectMapper();

            // Customer session
            Map<String, Object> map = mapper.readValue(mapper.writeValueAsString(payment.customer), Map.class);
            CustomerModel customerModel = db1.findByPhone(map.get("phone").toString());
            if(customerModel == null) {
                if(!map.get("phone").toString().isEmpty() &&
                    !map.get("name").toString().isEmpty() &&
                    !map.get("address").toString().isEmpty()) {
                    createCustomer(
                            (String) map.get("phone"),
                            (String) map.get("name"),
                            (String) map.get("address")
                    );
                } else {
                    return new Package(403, "The data of the customer is not valid", null);
                }
            }

            // Cart session
            List<Map<String, Object>> map3 = new ArrayList<>();
            for(int i = 0; i < payment.cart.length; i++) {
                Map<String, Object> map2 = mapper.readValue(mapper.writeValueAsString(payment.cart[i]), Map.class);
                map3.add(map2);
            }

            // Staff session
            String token = payment.token.trim();
            try {
                // Parse the token
                SignedJWT signedJWT = SignedJWT.parse(token);

                // Create a verifier with the secret key
                JWSVerifier verifier = new MACVerifier(Keys.secretKeyFor(SignatureAlgorithm.HS512));

                // Verify the signature
                JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();
                Map<String, Object> map4 = mapper.readValue(mapper.writeValueAsString(claimsSet.toJSONObject()), Map.class);
            } catch (Exception e) {
                return new Package(404, "Error verifying the token", null);
            }
            return new Package(0, "success", null);
        }catch (Exception e){
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

