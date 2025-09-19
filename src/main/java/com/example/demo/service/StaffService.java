package com.example.demo.service;

import com.example.demo.entity.Staff;
import com.example.demo.mapper.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    
    @Autowired
    private StaffMapper staffMapper;
    
    // 获取所有教职工
    public List<Staff> getAllStaff() {
        return staffMapper.findAll();
    }
    
    // 根据ID获取教职工
    public Staff getStaffById(String staffId) {
        return staffMapper.findByStaffId(staffId);
    }
    
    // 添加教职工
    public boolean addStaff(Staff staff) {
        // 检查教职工ID是否已存在
        if (staffMapper.findByStaffId(staff.getStaffId()) != null) {
            return false; // 教职工ID已存在
        }
        return staffMapper.insert(staff) > 0;
    }
    
    // 更新教职工信息
    public boolean updateStaff(Staff staff) {
        return staffMapper.update(staff) > 0;
    }
    
    // 删除教职工
    public boolean deleteStaff(String staffId) {
        return staffMapper.deleteByStaffId(staffId) > 0;
    }
    
    // 验证教职工登录
    public Staff validateStaff(String staffId, String password) {
        return staffMapper.findByStaffIdAndPassword(staffId, password);
    }
}