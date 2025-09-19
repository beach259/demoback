package com.example.demo.controller;

import com.example.demo.entity.Staff;
import com.example.demo.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*")
public class StaffController {
    
    @Autowired
    private StaffService staffService;
    
    /**
     * 获取所有教职工列表
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllStaff() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Staff> staffList = staffService.getAllStaff();
            response.put("success", true);
            response.put("message", "获取教职工列表成功");
            response.put("data", staffList);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取教职工列表失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 根据ID获取教职工信息
     */
    @GetMapping("/{staffId}")
    public ResponseEntity<Map<String, Object>> getStaffById(@PathVariable String staffId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Staff staff = staffService.getStaffById(staffId);
            if (staff != null) {
                response.put("success", true);
                response.put("message", "获取教职工信息成功");
                response.put("data", staff);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "教职工不存在");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取教职工信息失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 添加教职工
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> addStaff(@RequestBody Staff staff) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 参数验证
            if (staff.getStaffId() == null || staff.getStaffId().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "教职工ID不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            if (staff.getPassword() == null || staff.getPassword().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "密码不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean success = staffService.addStaff(staff);
            if (success) {
                response.put("success", true);
                response.put("message", "添加教职工成功");
                response.put("data", staff);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "教职工ID已存在");
                return ResponseEntity.status(409).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "添加教职工失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 更新教职工信息
     */
    @PutMapping("/{staffId}")
    public ResponseEntity<Map<String, Object>> updateStaff(@PathVariable String staffId, @RequestBody Staff staff) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 设置路径参数中的ID
            staff.setStaffId(staffId);
            
            // 参数验证
            if (staff.getPassword() == null || staff.getPassword().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "密码不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean success = staffService.updateStaff(staff);
            if (success) {
                response.put("success", true);
                response.put("message", "更新教职工信息成功");
                response.put("data", staff);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "教职工不存在或更新失败");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "更新教职工信息失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 删除教职工
     */
    @DeleteMapping("/{staffId}")
    public ResponseEntity<Map<String, Object>> deleteStaff(@PathVariable String staffId) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = staffService.deleteStaff(staffId);
            if (success) {
                response.put("success", true);
                response.put("message", "删除教职工成功");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "教职工不存在或删除失败");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "删除教职工失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}