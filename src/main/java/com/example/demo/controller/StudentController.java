package com.example.demo.controller;

import com.example.demo.entity.Student;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/students")
@CrossOrigin(origins = "*")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    /**
     * 获取所有学生列表
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllStudents() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Student> students = studentService.getAllStudents();
            response.put("success", true);
            response.put("data", students);
            response.put("message", "获取学生列表成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取学生列表失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 根据ID获取学生信息
     */
    @GetMapping("/{studentId}")
    public ResponseEntity<Map<String, Object>> getStudentById(@PathVariable String studentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Student student = studentService.getStudentById(studentId);
            if (student != null) {
                response.put("success", true);
                response.put("data", student);
                response.put("message", "获取学生信息成功");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "学生不存在");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取学生信息失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 添加学生
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> addStudent(@RequestBody Student student) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 验证必填字段
            if (student.getStudentId() == null || student.getStudentId().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "学生ID不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (student.getStudentname() == null || student.getStudentname().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "学生姓名不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean success = studentService.addStudent(student);
            if (success) {
                response.put("success", true);
                response.put("message", "添加学生成功");
                response.put("data", student);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "学生ID已存在");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "添加学生失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 更新学生信息
     */
    @PutMapping("/{studentId}")
    public ResponseEntity<Map<String, Object>> updateStudent(@PathVariable String studentId, @RequestBody Student student) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 确保路径参数和请求体中的学生ID一致
            student.setStudentId(studentId);
            
            // 验证必填字段
            if (student.getStudentname() == null || student.getStudentname().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "学生姓名不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean success = studentService.updateStudent(student);
            if (success) {
                response.put("success", true);
                response.put("message", "更新学生信息成功");
                response.put("data", student);
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "学生不存在");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "更新学生信息失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 删除学生
     */
    @DeleteMapping("/{studentId}")
    public ResponseEntity<Map<String, Object>> deleteStudent(@PathVariable String studentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = studentService.deleteStudent(studentId);
            if (success) {
                response.put("success", true);
                response.put("message", "删除学生成功");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "学生不存在");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "删除学生失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}