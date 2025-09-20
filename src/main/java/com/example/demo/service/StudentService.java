package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    
    @Autowired
    private StudentMapper studentMapper;
    
    /**
     * 获取所有学生列表
     */
    public List<Student> getAllStudents() {
        return studentMapper.findAll();
    }
    
    /**
     * 根据学生ID获取学生信息
     */
    public Student getStudentById(String studentId) {
        return studentMapper.findByStudentId(studentId);
    }
    
    /**
     * 添加学生
     */
    public boolean addStudent(Student student) {
        // 检查学生ID是否已存在
        if (studentMapper.findByStudentId(student.getStudentId()) != null) {
            return false; // 学生ID已存在
        }
        
        // 设置默认密码（如果未提供）
        if (student.getPassword() == null || student.getPassword().trim().isEmpty()) {
            student.setPassword("123456"); // 默认密码
        }
        
        // 设置默认值
        if (student.getInfoCompleted() == null) {
            student.setInfoCompleted(false);
        }
        if (student.getModificationCount() == null) {
            student.setModificationCount(0);
        }
        
        return studentMapper.insert(student) > 0;
    }
    
    /**
     * 更新学生信息
     */
    public boolean updateStudent(Student student) {
        // 检查学生是否存在
        if (studentMapper.findByStudentId(student.getStudentId()) == null) {
            return false; // 学生不存在
        }
        
        return studentMapper.update(student) > 0;
    }
    
    /**
     * 学生信息完善（只能修改一次）
     */
    public boolean updateStudentInfo(Student student) {
        // 检查学生是否存在
        Student existingStudent = studentMapper.findByStudentId(student.getStudentId());
        if (existingStudent == null) {
            return false; // 学生不存在
        }
        
        // 检查是否已经完善过信息
        if (existingStudent.getInfoCompleted() != null && existingStudent.getInfoCompleted()) {
            return false; // 信息已完善，不能再次修改
        }
        
        // 设置信息完善状态和修改次数
        student.setInfoCompleted(true);
        student.setModificationCount(1);
        
        return studentMapper.updateStudentInfo(student) > 0;
    }
    
    /**
     * 删除学生
     */
    public boolean deleteStudent(String studentId) {
        // 检查学生是否存在
        if (studentMapper.findByStudentId(studentId) == null) {
            return false; // 学生不存在
        }
        
        return studentMapper.deleteByStudentId(studentId) > 0;
    }
    
    /**
     * 验证学生登录
     */
    public Student validateStudent(String studentId, String password) {
        return studentMapper.findByStudentIdAndPassword(studentId, password);
    }
}