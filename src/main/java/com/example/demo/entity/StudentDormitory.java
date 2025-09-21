package com.example.demo.entity;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class StudentDormitory {
    private Integer allocationId;
    private String studentId;
    private Integer roomId;
    private Integer bedNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private String allocationStatus; // pending, confirmed, checked_in, checked_out
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 关联信息
    private Student student;
    private DormitoryRoom room;
    
    // 查询时的额外字段
    private String roomNumber;
    private Integer floorNumber;
    private Integer buildingId;  // 添加缺少的buildingId属性
    private String buildingName;
    private String buildingType;
    private String studentName;
    private String studentClass;
    
    // 为了兼容性添加的方法
    public void setAllocationDate(LocalDate allocationDate) {
        this.createdAt = allocationDate.atStartOfDay();
    }
    
    public void setStatus(String status) {
        this.allocationStatus = status;
    }
    
    public void setAssignmentId(Integer assignmentId) {
        this.allocationId = assignmentId;
    }
    
    public Integer getAssignmentId() {
        return this.allocationId;
    }
}