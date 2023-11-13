package com.finalpos.POSsystem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @Autowired
    UserRepository db;
    UserModel user = new UserModel("1","Tài khoản kiểm thử", "admin@gmail.com", "123456", "admin", "active", "2021-09-01 00:00:00", "admin");
    @GetMapping("/")
    @ResponseBody
    public String index() {

        db.save(user);
        return "index";
    }
}
