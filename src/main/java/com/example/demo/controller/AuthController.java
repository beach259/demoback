package com.example.demo.controller;

import com.example.demo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Object user = authService.login(request.getUserId(), request.getPassword(), request.getUserType());
            
            if (user != null) {
                response.put("success", true);
                response.put("message", "登录成功");
                response.put("userType", authService.getUserDisplayName(request.getUserType()));
                response.put("user", user);
            } else {
                response.put("success", false);
                response.put("message", "用户名或密码错误");
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "登录失败：" + e.getMessage());
        }
        
        return response;
    }

    public static class LoginRequest {
        private String userId;
        private String password;
        private String userType;

        // Getters and setters
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }
    }
}