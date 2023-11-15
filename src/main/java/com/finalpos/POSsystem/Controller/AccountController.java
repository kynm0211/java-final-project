package com.finalpos.POSsystem.Controller;

import com.finalpos.POSsystem.Model.Package;
import com.finalpos.POSsystem.Model.UserModel;
import com.finalpos.POSsystem.Model.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Value;

@Controller
@RequestMapping("/api/account")
@ResponseBody
public class AccountController {
    @Autowired
    UserRepository db;

    @Value("${default.application.avatar}")
    private String defaultAvatar;

    @PostMapping("/register_admin")

    public Package register_admin(@RequestBody UserModel model) {
        System.out.println(model);
        model.setRole("administator");
        model.setImage(defaultAvatar);
        model.setCreated_at(java.time.LocalDateTime.now());
        db.save(model);
        return new Package(0, "success", model);
    }
}
