package com.finalpos.POSsystem.Controller;

import com.finalpos.POSsystem.Model.UserModel;
import com.finalpos.POSsystem.Model.UserRepository;
import com.finalpos.POSsystem.Model.Package;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static com.finalpos.POSsystem.Controller.AccountController.generateToken;

@Controller
@RestController
@ResponseBody
@RequestMapping("/api/users")

public class UsersController {
    @Autowired
    UserRepository db;
    @Value("${default.application.avatar}")
    private String defaultAvatar;

    PasswordEncoder passwordEndcoder = new BCryptPasswordEncoder();

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
    public Package register(@RequestParam("name") String name,
                            @RequestParam("email") String email) {
        try {
            String username = email.split("@")[0];

            UserModel newUser = new UserModel();
            newUser.setName(name);
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setRole("User");
            newUser.setImage(defaultAvatar);
            newUser.setStatus("Active");
            newUser.setCreated_at(java.time.LocalDateTime.now());

            String defaultPassword = username;

            newUser.setPassword(passwordEndcoder.encode(defaultPassword));

            db.save(newUser);

            String tokenString = generateToken(newUser);

            Object data = new Object() {
                public final String token = tokenString;
                public final UserModel user = newUser;
            };

            sendRegistrationEmail(newUser, tokenString);

            return new Package(0, "Registration success", data);
        } catch (Exception e) {
            return new Package(404, e.getMessage(), null);
        }
    }

    private void sendRegistrationEmail(UserModel user, String token) {
        try {
            String stringSenderEmail = "vate202@gmail.com";
            String stringReceiverEmail = user.getEmail();
            String stringPasswordSenderEmail = "lktyqjjjbiyefldc";

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("Subject: Java App email");
            mimeMessage.setText("Hello " + user.getName() + ",\n\nYour registration was successful. Welcome to the Programmer World!");

            Transport.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }



    @PostMapping("/resend")
    public Package resendEmail(@RequestParam("email") String email) {
        try {
            UserModel user = db.findByEmail(email);

            if (user != null) {
                String token = generateToken(user);

                sendRegistrationEmail(user, token);

                return new Package(0, "Resend email successfully", null);
            } else {
                return new Package(404, "User not found", null);
            }
        } catch (Exception e) {
            return new Package(500, e.getMessage(), null);
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
