package com.example.demo.service;

import com.example.demo.entity.DormitoryBuilding;
import com.example.demo.entity.DormitoryRoom;
import com.example.demo.entity.Student;
import com.example.demo.entity.StudentDormitory;
import com.example.demo.mapper.DormitoryMapper;
import com.example.demo.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DormitoryService {
    
    @Autowired
    private DormitoryMapper dormitoryMapper;
    
    @Autowired
    private StudentMapper studentMapper;
    
    // ========== 管理员宿舍楼管理方法 ==========
    
    /**
     * 获取所有宿舍楼
     */
    public List<DormitoryBuilding> getAllBuildings() {
        return dormitoryMapper.findAllBuildings();
    }
    
    /**
     * 添加宿舍楼
     */
    @Transactional
    public boolean addBuilding(DormitoryBuilding building) {
        try {
            return dormitoryMapper.insertBuilding(building) > 0;
        } catch (Exception e) {
            throw new RuntimeException("添加宿舍楼失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新宿舍楼信息
     */
    @Transactional
    public boolean updateBuilding(DormitoryBuilding building) {
        try {
            return dormitoryMapper.updateBuilding(building) > 0;
        } catch (Exception e) {
            throw new RuntimeException("更新宿舍楼失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除宿舍楼
     */
    @Transactional
    public boolean deleteBuilding(Integer buildingId) {
        try {
            // 检查是否有房间关联
            List<DormitoryRoom> rooms = dormitoryMapper.findRoomsByBuildingId(buildingId);
            if (!rooms.isEmpty()) {
                throw new RuntimeException("该宿舍楼下还有房间，无法删除");
            }
            return dormitoryMapper.deleteBuilding(buildingId) > 0;
        } catch (Exception e) {
            throw new RuntimeException("删除宿舍楼失败: " + e.getMessage());
        }
    }
    
    // ========== 管理员房间管理方法 ==========
    
    /**
     * 获取所有房间
     */
    public List<DormitoryRoom> getAllRooms() {
        return dormitoryMapper.findAllRooms();
    }
    
    /**
     * 添加房间
     */
    @Transactional
    public boolean addRoom(DormitoryRoom room) {
        try {
            return dormitoryMapper.insertRoom(room) > 0;
        } catch (Exception e) {
            throw new RuntimeException("添加房间失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新房间信息
     */
    @Transactional
    public boolean updateRoom(DormitoryRoom room) {
        try {
            return dormitoryMapper.updateRoom(room) > 0;
        } catch (Exception e) {
            throw new RuntimeException("更新房间失败: " + e.getMessage());
        }
    }
    
    /**
     * 删除房间
     */
    @Transactional
    public boolean deleteRoom(Integer roomId) {
        try {
            // 检查是否有学生分配
            List<StudentDormitory> assignments = dormitoryMapper.findAssignmentsByRoomId(roomId);
            if (!assignments.isEmpty()) {
                throw new RuntimeException("该房间还有学生入住，无法删除");
            }
            return dormitoryMapper.deleteRoom(roomId) > 0;
        } catch (Exception e) {
            throw new RuntimeException("删除房间失败: " + e.getMessage());
        }
    }
    
    // ========== 管理员分配管理方法 ==========
    
    /**
     * 获取所有宿舍分配信息
     */
    public List<StudentDormitory> getAllAssignments() {
        return dormitoryMapper.findAllAssignments();
    }
    
    /**
     * 添加分配记录
     */
    @Transactional
    public boolean addAssignment(StudentDormitory assignment) {
        try {
            // 检查学生是否已有分配记录
            StudentDormitory existingAssignment = dormitoryMapper.findStudentCurrentAllocation(assignment.getStudentId());
            if (existingAssignment != null && 
                ("confirmed".equals(existingAssignment.getAllocationStatus()) || 
                 "checked_in".equals(existingAssignment.getAllocationStatus()))) {
                throw new RuntimeException("学生已有宿舍分配");
            }
            
            // 检查房间是否存在且可用
            DormitoryRoom room = dormitoryMapper.findRoomById(assignment.getRoomId());
            if (room == null) {
                throw new RuntimeException("房间不存在");
            }
            
            if ("maintenance".equals(room.getRoomStatus())) {
                throw new RuntimeException("房间正在维护中，无法分配");
            }
            
            // 性别匹配验证
            Student student = studentMapper.findById(assignment.getStudentId());
            if (student == null) {
                throw new RuntimeException("学生不存在");
            }
            
            DormitoryBuilding building = dormitoryMapper.findBuildingById(room.getBuildingId());
            if (building == null) {
                throw new RuntimeException("宿舍楼不存在");
            }
            
            // 验证性别匹配
            String studentGender = student.getGender();
            String buildingType = building.getBuildingType();
            
            if (("male".equals(buildingType) && !"男".equals(studentGender)) ||
                ("female".equals(buildingType) && !"女".equals(studentGender))) {
                throw new RuntimeException("学生性别与宿舍楼类型不匹配");
            }
            
            // 检查房间是否还有空位
            int currentOccupancy = dormitoryMapper.countRoomOccupancy(assignment.getRoomId());
            if (currentOccupancy >= room.getCapacity()) {
                throw new RuntimeException("房间已满");
            }
            
            // 分配可用床位号
            Integer bedNumber = assignment.getBedNumber();
            if (bedNumber == null) {
                // 自动分配床位
                for (int i = 1; i <= room.getCapacity(); i++) {
                    int bedOccupancy = dormitoryMapper.countBedOccupancy(assignment.getRoomId(), i);
                    if (bedOccupancy == 0) {
                        bedNumber = i;
                        break;
                    }
                }
            } else {
                // 验证指定床位是否可用
                int bedOccupancy = dormitoryMapper.countBedOccupancy(assignment.getRoomId(), bedNumber);
                if (bedOccupancy > 0) {
                    throw new RuntimeException("指定床位已被占用");
                }
            }
            
            if (bedNumber == null) {
                throw new RuntimeException("没有可用床位");
            }
            
            assignment.setBedNumber(bedNumber);
            assignment.setAllocationDate(LocalDate.now());
            if (assignment.getAllocationStatus() == null) {
                assignment.setAllocationStatus("confirmed");
            }
            
            // 插入分配记录
            boolean result = dormitoryMapper.insertAllocation(assignment) > 0;
            
            // 更新房间入住人数
            if (result) {
                dormitoryMapper.increaseRoomOccupancy(assignment.getRoomId());
                
                // 检查房间是否已满，如果已满则更新状态
                int newOccupancy = dormitoryMapper.countRoomOccupancy(assignment.getRoomId());
                if (newOccupancy >= room.getCapacity()) {
                    dormitoryMapper.updateRoomStatus(assignment.getRoomId(), "full");
                }
            }
            
            return result;
        } catch (Exception e) {
            throw new RuntimeException("添加分配记录失败: " + e.getMessage());
        }
    }

    /**
     * 更新分配记录
     */
    @Transactional
    public boolean updateAssignment(StudentDormitory assignment) {
        try {
            return dormitoryMapper.updateAllocation(assignment) > 0;
        } catch (Exception e) {
            throw new RuntimeException("更新分配记录失败: " + e.getMessage());
        }
    }

    /**
     * 删除分配记录
     */
    @Transactional
    public boolean deleteAssignment(Integer assignmentId) {
        try {
            return dormitoryMapper.deleteAssignment(assignmentId) > 0;
        } catch (Exception e) {
            throw new RuntimeException("删除分配记录失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新房间状态
     */
    @Transactional
    public void updateRoomStatus(Integer roomId, String status) {
        // 验证房间是否存在
        DormitoryRoom room = dormitoryMapper.findRoomById(roomId);
        if (room == null) {
            throw new RuntimeException("房间不存在");
        }
        
        // 验证状态值
        if (!Arrays.asList("available", "full", "maintenance").contains(status)) {
            throw new RuntimeException("无效的房间状态");
        }
        
        // 如果设置为维修中，需要检查是否有学生入住
        if ("maintenance".equals(status)) {
            List<StudentDormitory> allocations = dormitoryMapper.findAllocationsByRoomId(roomId);
            boolean hasActiveAllocations = allocations.stream()
                .anyMatch(allocation -> "confirmed".equals(allocation.getAllocationStatus()) 
                    || "checked_in".equals(allocation.getAllocationStatus()));
            
            if (hasActiveAllocations) {
                throw new RuntimeException("房间内有学生入住，无法设置为维修状态");
            }
        }
        
        // 更新房间状态
        dormitoryMapper.updateRoomStatus(roomId, status);
    }

    /**
     * 获取指定房间的可用床位
     */
    public List<Integer> getAvailableBeds(Integer roomId) {
        // 获取房间信息
        DormitoryRoom room = dormitoryMapper.findRoomById(roomId);
        if (room == null) {
            throw new RuntimeException("房间不存在");
        }
        
        // 获取已占用的床位
        List<Integer> occupiedBeds = dormitoryMapper.getOccupiedBeds(roomId);
        
        // 生成可用床位列表
        List<Integer> availableBeds = new ArrayList<>();
        for (int i = 1; i <= room.getBedCount(); i++) {
            if (!occupiedBeds.contains(i)) {
                availableBeds.add(i);
            }
        }
        
        return availableBeds;
    }

    // ========== 学生端方法 ==========
    
    /**
     * 根据性别获取宿舍楼
     */
    public List<DormitoryBuilding> getBuildingsByGender(String gender) {
        // 将性别转换为宿舍楼类型
        String buildingType = "男".equals(gender) ? "male" : "female";
        return dormitoryMapper.findBuildingsByType(buildingType);
    }
    
    /**
     * 获取指定宿舍楼的可用房间
     */
    public List<DormitoryRoom> getAvailableRooms(Integer buildingId) {
        return dormitoryMapper.findAvailableRoomsByBuilding(buildingId);
    }
    
    /**
     * 获取学生当前的宿舍分配信息
     */
    public StudentDormitory getStudentAllocation(String studentId) {
        return dormitoryMapper.findStudentCurrentAllocation(studentId);
    }
    
    /**
     * 学生选择宿舍
     */
    @Transactional
    public boolean selectDormitory(String studentId, Integer roomId) {
        try {
            // 检查学生是否已有分配记录
            StudentDormitory existingAssignment = dormitoryMapper.findStudentCurrentAllocation(studentId);
            if (existingAssignment != null && 
                ("confirmed".equals(existingAssignment.getAllocationStatus()) || 
                 "checked_in".equals(existingAssignment.getAllocationStatus()))) {
                throw new RuntimeException("您已有宿舍分配，无法重复选择");
            }
            
            // 检查房间是否存在且可用
            DormitoryRoom room = dormitoryMapper.findRoomById(roomId);
            if (room == null) {
                throw new RuntimeException("房间不存在");
            }
            
            if ("maintenance".equals(room.getRoomStatus())) {
                throw new RuntimeException("房间正在维护中，无法选择");
            }
            
            if ("full".equals(room.getRoomStatus())) {
                throw new RuntimeException("房间已满，无法选择");
            }
            
            // 性别匹配验证
            Student student = studentMapper.findById(studentId);
            if (student == null) {
                throw new RuntimeException("学生信息不存在");
            }
            
            DormitoryBuilding building = dormitoryMapper.findBuildingById(room.getBuildingId());
            if (building == null) {
                throw new RuntimeException("宿舍楼信息不存在");
            }
            
            // 验证性别匹配
            String studentGender = student.getGender();
            String buildingType = building.getBuildingType();
            
            boolean isMale = "男".equals(studentGender) || "male".equals(studentGender);
            boolean isFemale = "女".equals(studentGender) || "female".equals(studentGender);
            
            if (("male".equals(buildingType) && !isMale) ||
                ("female".equals(buildingType) && !isFemale)) {
                throw new RuntimeException("您的性别与该宿舍楼类型不匹配");
            }
            
            // 检查房间是否还有空位
            int currentOccupancy = dormitoryMapper.countRoomOccupancy(roomId);
            if (currentOccupancy >= room.getCapacity()) {
                throw new RuntimeException("房间已满，请选择其他房间");
            }
            
            // 自动分配床位
            Integer bedNumber = null;
            for (int i = 1; i <= room.getCapacity(); i++) {
                int bedOccupancy = dormitoryMapper.countBedOccupancy(roomId, i);
                if (bedOccupancy == 0) {
                    bedNumber = i;
                    break;
                }
            }
            
            if (bedNumber == null) {
                throw new RuntimeException("没有可用床位");
            }
            
            // 创建分配记录
            StudentDormitory allocation = new StudentDormitory();
            allocation.setStudentId(studentId);
            allocation.setRoomId(roomId);
            allocation.setBedNumber(bedNumber);
            allocation.setAllocationDate(LocalDate.now());
            allocation.setAllocationStatus("pending"); // 学生选择后状态为待确认
            
            // 插入分配记录
            boolean result = dormitoryMapper.insertAllocation(allocation) > 0;
            
            // 更新房间入住人数
            if (result) {
                dormitoryMapper.increaseRoomOccupancy(roomId);
                
                // 检查房间是否已满，如果已满则更新状态
                int newOccupancy = dormitoryMapper.countRoomOccupancy(roomId);
                if (newOccupancy >= room.getCapacity()) {
                    dormitoryMapper.updateRoomStatus(roomId, "full");
                }
            }
            
            return result;
        } catch (Exception e) {
            throw new RuntimeException("选择宿舍失败: " + e.getMessage());
        }
    }
    
    /**
     * 确认入住
     */
    @Transactional
    public boolean confirmCheckIn(String studentId) {
        try {
            StudentDormitory allocation = dormitoryMapper.findCurrentAllocationByStudentId(studentId);
            if (allocation == null || !"pending".equals(allocation.getAllocationStatus())) {
                return false; // 没有待确认的分配记录
            }
            
            allocation.setAllocationStatus("confirmed");
            allocation.setCheckInDate(LocalDate.now());
            
            return dormitoryMapper.updateAllocationStatus(allocation) > 0;
        } catch (Exception e) {
            throw new RuntimeException("确认入住失败: " + e.getMessage());
        }
    }
    
    /**
     * 退宿
     */
    @Transactional
    public boolean checkOut(String studentId) {
        try {
            StudentDormitory allocation = dormitoryMapper.findCurrentAllocationByStudentId(studentId);
            if (allocation == null) {
                return false; // 没有宿舍分配记录
            }
            
            // 直接删除分配记录
            int result = dormitoryMapper.deleteStudentAllocation(studentId);
            if (result > 0) {
                // 减少房间入住人数
                dormitoryMapper.decreaseRoomOccupancy(allocation.getRoomId());
                return true;
            }
            
            return false;
        } catch (Exception e) {
            throw new RuntimeException("退宿失败: " + e.getMessage());
        }
    }
}