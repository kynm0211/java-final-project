package com.finalpos.POSsystem.Controller;

import com.finalpos.POSsystem.Model.ProductModel;
import com.finalpos.POSsystem.Model.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.finalpos.POSsystem.Model.Package;
@Controller
@RestController
@ResponseBody
@RequestMapping("/api/products")
public class ProductsController {

    ProductModel productModel;
    ProductRepository db;

    @GetMapping("/")
    public Package index() {
        try{
            return new Package(0, "success", null);
        }
        catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @PostMapping("/add")
    public Package add(){
        try{
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
