package com.finalpos.POSsystem.Controller;

import com.finalpos.POSsystem.Model.UserModel;
import com.finalpos.POSsystem.Model.UserRepository;
import com.finalpos.POSsystem.Model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RestController
@ResponseBody
@RequestMapping("/api/users")

public class UsersController {
    @Autowired
    UserRepository db;

    @GetMapping("/")
    //    Get list of users
    public Package index(@RequestParam Optional<String> page) {
        try {
            int pageSize = 10;
            int pageNumber = 1;
            if(!page.isEmpty() && page.get() != "null") {
                pageNumber = Integer.parseInt(page.get());
            }
            int skipAmount = (pageNumber - 1) * pageSize;
            int totalUsers = (int) db.count();
            int totalPages = (int) Math.ceil((double) totalUsers / pageSize);

            List<UserModel> userList = db.findAll();
            List<UserModel> user = new ArrayList<>();

            // Check num of users in the last page
            // It will continue() when page + 1 (skipAmount > size()) -> reduce run time
            int endIdx = Math.min(skipAmount + pageSize, userList.size());
            for (int i = skipAmount; i < endIdx; i++) {
                user.add(userList.get(i));
            }

            Object data = new Object() {
                public final List<UserModel> users = user;
                public final int devider = totalPages;
            };
            return new Package(0, "success", data);
        }
        catch (Exception e) {
            return new Package(404, e.getMessage(), null);
        }
    }




    @PostMapping("/register")
    public Package registerSale(){
        try{
            return new Package(0, "success", null);
        }
        catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @PostMapping("resend")
    public Package resend(){
        try{
            return new Package(0, "success", null);
        }
        catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @PutMapping("/{userId}")
    public Package update(@PathVariable("userId") String userId,
                          @RequestParam String email,
                          @RequestParam String role,
                          @RequestParam String status){
        try{
            // Status: Active, InActive, Lock
            // Role: Administrator, Sale Person
            System.out.println(userId);
            System.out.println(email);
            System.out.println(role);
            System.out.println(status);


            return new Package(0, "success", null);
        }
        catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @DeleteMapping("/{userId}")
    public Package delete(@PathVariable("userId") String userId){
        try{
            return new Package(0, "success", null);
        }
        catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }
}
