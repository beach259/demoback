package com.example.demo.service;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Staff;
import com.example.demo.entity.Student;
import com.example.demo.mapper.AdminMapper;
import com.example.demo.mapper.StaffMapper;
import com.example.demo.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    private StudentMapper studentMapper;
    
    @Autowired
    private AdminMapper adminMapper;
    
    @Autowired
    private StaffMapper staffMapper;
    
    public Object login(String userId, String password, String userType) {
        switch (userType) {
            case "student":
                Student student = studentMapper.findByStudentIdAndPassword(userId, password);
                return student;
            case "admin":
                Admin admin = adminMapper.findByAdminIdAndPassword(userId, password);
                return admin;
            case "teacher":
                Staff staff = staffMapper.findByStaffIdAndPassword(userId, password);
                return staff;
            default:
                return null;
        }
    }
    
    public String getUserDisplayName(String userType) {
        switch (userType) {
            case "student":
                return "学生";
            case "admin":
                return "管理员";
            case "teacher":
                return "教职工";
            default:
                return "未知用户";
        }
    }
}