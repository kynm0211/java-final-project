package com.finalpos.POSsystem.Controller;

import com.finalpos.POSsystem.Model.ProductModel;
import com.finalpos.POSsystem.Model.ProductRepository;
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

    ProductModel productModel;
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
        try{
            System.out.println(image);
            System.out.println(barcode);
            return new Package(0, "success", null);
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
}
