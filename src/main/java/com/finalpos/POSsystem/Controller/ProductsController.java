package com.finalpos.POSsystem.Controller;

import com.finalpos.POSsystem.Config.FirebaseService;
import com.finalpos.POSsystem.Model.ProductModel;
import com.finalpos.POSsystem.Model.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.finalpos.POSsystem.Model.Package;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
@RestController
@ResponseBody
@RequestMapping("/api/products")
public class ProductsController {
    @Autowired
    private FirebaseService firebase;

    @Autowired
    ProductRepository db;

    @GetMapping("/")
    public Package index(@RequestParam Optional<String> page) {
        try{
            return new Package(0, "success", null);
        }
        catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @PostMapping("/add")
    public Package add(@RequestParam("barcode") String barcode,
                       @RequestParam("name") String name,
                       @RequestParam("description") String description,
                       @RequestParam("import_price") String import_price,
                       @RequestParam("retail_price") String retail_price,
                       @RequestParam("quantity") String quantity,
                       @RequestParam("category") String category,
                       @RequestParam("image") Optional<MultipartFile> image
                       ){
        try {
            if(barcode.isEmpty() || name.isEmpty() || description.isEmpty() || import_price.isEmpty() ||
                    retail_price.isEmpty() || quantity.isEmpty() || category.isEmpty() || image.isEmpty()) {
                return new Package(404, "Missing fields", null);
            } else {
                if(isNum(import_price) && isNum(import_price) && isNum(retail_price)) {
                    if(Integer.valueOf(import_price.trim()) < 0 || Integer.valueOf(retail_price.trim()) < 0)
                        return new Package(404, "Price should not be negative", null);
                    else if(Integer.valueOf(quantity.trim()) < 0)
                        return new Package(404, "Quantity should not be negative", null);
                    else if(Integer.valueOf(import_price.trim()) > Integer.valueOf(retail_price.trim()))
                        return new Package(404, "Import price should not be greater than retail price", null);
                    else if(db.findByBarcode(barcode.trim()) != null)
                        return new Package(404, "Barcode existed", null);
                    else {
                        String imageUrl = "";
                        if (image.isPresent()) {
                            imageUrl = firebase.uploadImage(image.get());
                        }

                        String creation_date = String.valueOf(java.time.LocalDateTime.now());
                        ProductModel result = create_product(barcode.trim(), name.trim(), Integer.valueOf(quantity.trim()), description.trim(),
                                Double.valueOf(import_price.trim()), Double.valueOf(retail_price.trim()), imageUrl, category.trim(),
                                creation_date, false);
                        if(result != null) {
                            return new Package(0, "Add product successfully", result);
                        }
                        return new Package(404, "Error saving product", null);
                    }
                } else {
                    return new Package(404, "Quantity, import price and retail price should be a number", null);
                }
            }
        }
        catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @GetMapping("/{barcode}")
    public Package get(@PathVariable("barcode") String barcode){
        try{
            return new Package(0, "success", null);
        }
        catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @PutMapping("/{barcode}")
    public Package update(@PathVariable("barcode") String barcode){
        try{
            return new Package(0, "success", null);
        }
        catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @PatchMapping("/{barcode}")
    public Package updatePatch(@PathVariable("barcode") String barcode){
        try{
            return new Package(0, "success", null);
        }
        catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @DeleteMapping("/{barcode}")
    public Package delete(@PathVariable("barcode") String barcode){
        try{
            return new Package(0, "success", null);
        }
        catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    private ProductModel create_product(String barcode, String name, int quantity, String description,
                           double import_price, double retail_price, String image,
                           String category, String creation_date, Boolean purchase) {
        try {
            ProductModel product = new ProductModel();
            product.setBarcode(barcode);
            product.setName(name);
            product.setQuantity(quantity);
            product.setDescription(description);
            product.setImport_price(import_price);
            product.setRetail_price(retail_price);
            product.setImage(image);
            product.setCategory(category);
            product.setCreation_date(creation_date);
            product.setPurchase(purchase);
            db.save(product);
            return product;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isNum(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }
}