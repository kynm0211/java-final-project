package com.finalpos.POSsystem.Controller;

import com.finalpos.POSsystem.Model.*;
import com.finalpos.POSsystem.Model.Package;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/api/account")
@ResponseBody
public class AccountController {
    @Autowired
    UserRepository db;

    @Value("${default.application.avatar}")
    private String defaultAvatar;

    private static Key JWT_Key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @PostMapping("/register_admin")

    public Package register_admin(@RequestBody UserModel model) {
        try{
            System.out.println(model);
            model.setRole("administator");
            model.setImage(defaultAvatar);
            model.setCreated_at(java.time.LocalDateTime.now());
            db.save(model);
            return new Package(0, "success", model);
        }
        catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @PostMapping("/login")
    public Package login(@RequestBody UserModel loginUser) {
        try {
            UserModel user = db.findByUsername(loginUser.getUsername());
            if (user != null && user.getPassword().equals(loginUser.getPassword())) {
                String token = generateToken(user);
                LoginResponse loginResponse = new LoginResponse(token, user);
                return new Package(0, "Login success",loginResponse);
            } else {
                return new Package(404, "Invalid username or password", null);
            }
        } catch (Exception e) {
            return new Package(500, e.getMessage(), loginUser);
        }
    }

    @PostMapping("/change-password")
    public Package changePassword(@RequestBody ChangePasswordRequest changePasswordRequest,
                                  @RequestHeader(name = "Authorization") String token){
        try{
            if (validateToken(token)) {
                Claims claims = Jwts.parser().setSigningKey("yourSecretKey").parseClaimsJws(token).getBody();
                String username = claims.getSubject();

                UserModel user = db.findByUsername(username);
                if (user != null && user.getPassword().equals(changePasswordRequest.getCurrentPassword())) {
                    user.setPassword(changePasswordRequest.getNewPassword());
                    db.save(user);
                    return new Package(0, "Changing password successfully", null);
                } else {
                    return new Package(401, "Incorrect current password", null);
                }
            } else {
                return new Package(401, "Invalid token", null);
            }
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @GetMapping("/")
    public Package profile(){
        try{
            return new Package(0, "success", null);
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @PatchMapping("/")
    public Package updateProfile(){
        try{
            return new Package(0, "success", null);
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @PostMapping("/direct")
    public Package direct(){
        try{
            return new Package(0, "success", null);
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    public static String generateToken(UserModel user) {
        return Jwts.builder()
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .claim("image", user.getImage())
                .claim("role", user.getRole())
                .claim("status", user.getStatus())
                .signWith(SignatureAlgorithm.HS512, JWT_Key)
                .compact();
    }


    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(JWT_Key).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }


}
