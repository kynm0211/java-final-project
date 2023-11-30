package com.finalpos.POSsystem.Controller;

import com.finalpos.POSsystem.Model.UserModel;
import com.finalpos.POSsystem.Model.UserRepository;
import com.finalpos.POSsystem.Model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RestController
@ResponseBody
@RequestMapping("/api/users")

public class UsersController {
    @Autowired
    UserRepository db;

    @GetMapping("/")
    //    Get list of users
    public Package index(@RequestParam("page") int page) {
        try {
            int pageSize, skipAmount, totalUsers, totalPages;
            pageSize = 10;
            skipAmount = (page - 1) * pageSize;
            totalUsers = (int) db.count();
            totalPages = (int) Math.ceil((double)totalUsers / pageSize);

            List<UserModel> userList = db.findAll();
            List<UserModel> user = new ArrayList<>();

            // Check num of user in last page
            // It will continue() when page + 1 (skipAmount > size()) -> reduce run time
            if(skipAmount <= userList.size() - 1) {
                for (int i = 0; i < userList.size(); i++) {
                    if(i >= skipAmount && i < (skipAmount + 10)) {
                        user.add(userList.get(i));
                    }
                }
            }
            Object data = new Object() {
                public final List<UserModel> users = user;
                public final int devider = totalPages;
            };
            return new Package(0, "success", data);
        }
        catch (Exception e){
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
    public Package update(@PathVariable("userId") String userId){
        try{
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
