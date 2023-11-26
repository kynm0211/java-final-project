package com.finalpos.POSsystem.Controller;

import com.finalpos.POSsystem.Config.FirebaseService;
import com.finalpos.POSsystem.Model.UserRepository;
import com.finalpos.POSsystem.Model.*;
import com.finalpos.POSsystem.Model.Package;
import com.google.api.client.json.webtoken.JsonWebToken;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.security.Key;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RestController
@RequestMapping("/api/account")
@ResponseBody
public class AccountController {
    @Autowired
    UserRepository db;

    @Autowired
    private FirebaseService firebase;

    PasswordEncoder passwordEndcoder = new BCryptPasswordEncoder();

    @Value("${default.application.avatar}")
    private String defaultAvatar;

    private static Key JWT_Key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    @PostMapping("/register_admin")

    public Package register_admin(@RequestBody UserModel model) {
        try{
            System.out.println(model);
            model.setRole("Administrator");
            model.setImage(defaultAvatar);
            model.setStatus("Active");
            model.setPassword(passwordEndcoder.encode(model.getPassword()));
            model.setCreated_at(java.time.LocalDateTime.now());
            db.save(model);
            return new Package(0, "success", model);
        }
        catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @PostMapping("/login")
    public Package login(@RequestParam("username") String username,
                         @RequestParam("password") String password) {
        try {
            UserModel userDB = db.findByUsername(username);
            if (userDB != null && passwordEndcoder.matches(password, userDB.getPassword())) {
                String tokenString = generateToken(userDB);

                userDB.setPassword(null);
                Object data = new Object() {
                    public final String token = tokenString;
                    public final UserModel user = userDB;
                };
                return new Package(0, "Login success", data);
            } else {
                return new Package(404, "Invalid username or password", null);
            }
        } catch (Exception e) {
            return new Package(500, e.getMessage(), null);
        }
    }

    @PostMapping("/change-password")
    public Package changePassword(
                                  @RequestParam("currentPassword") String currentPassword,
                                  @RequestParam("newPassword") String newPassword,
                                  @RequestParam("confirmPassword") String confirmPassword,
                                  @RequestHeader(name = "Authorization") String token){
        try{
            if (validateToken(token)) {
                Claims claims = Jwts.parser().setSigningKey(JWT_Key).parseClaimsJws(token).getBody();
                String username = claims.get("username", String.class);

                UserModel user = db.findByUsername(username);
                if (user != null && passwordEndcoder.matches(currentPassword, user.getPassword())) {
                    // Update user password
                    user.setPassword(passwordEndcoder.encode(newPassword));
                    UserModel result = db.save(user);

                    return new Package(0, "Changing password successfully", result);
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
    public Package profile(@RequestHeader("Authorization") String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(JWT_Key).parseClaimsJws(token).getBody();
            return new Package(0, "Retrieve the user’s information successfully", claims);
        } catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    @PatchMapping("/")
    public Package updateProfile(@RequestParam("username") String username,
                                 @RequestParam("name") String name,
                                 @RequestParam("file") MultipartFile multipartFile,
                                 @RequestHeader("Authorization") String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(JWT_Key).parseClaimsJws(token).getBody();
            UserModel user = db.findByUsername(username);
            // Upload image to firebase return URL
            String imageUrl = firebase.uploadImage(multipartFile);
            if (!user.getImage().equals(imageUrl)) {
                user.setImage(imageUrl);
                db.save(user);
                return new Package(0, "Update profile successfully", claims);
            } else if (!user.getName().equals(name)) {
                user.setName(name);
                db.save(user);
                return new Package(0, "Update profile successfully", claims);
            }
            return new Package(401, "Update profile failure", claims);
        } catch (Exception e){
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

    @PutMapping("/renew-password")
    public Package renewPassword(){
        try{
            return new Package(0, "success", null);
        }catch (Exception e){
            return new Package(404, e.getMessage(), null);
        }
    }

    public static String generateToken(UserModel user) {
        return Jwts.builder()
                .claim("username", user.getUsername())
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .claim("image", user.getImage())
                .claim("role", user.getRole())
                .claim("status", user.getStatus())
                .signWith(SignatureAlgorithm.HS256, JWT_Key)
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