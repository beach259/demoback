package com.example.demo.controller;

import com.example.demo.entity.DormitoryBuilding;
import com.example.demo.entity.DormitoryRoom;
import com.example.demo.entity.StudentDormitory;
import com.example.demo.service.DormitoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dormitory")
@CrossOrigin(origins = "*")
public class DormitoryController {
    
    @Autowired
    private DormitoryService dormitoryService;
    
    // ========== 管理员宿舍楼管理接口 ==========
    
    /**
     * 获取所有宿舍楼
     */
    @GetMapping("/buildings")
    public ResponseEntity<Map<String, Object>> getAllBuildings() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<DormitoryBuilding> buildings = dormitoryService.getAllBuildings();
            response.put("success", true);
            response.put("data", buildings);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取宿舍楼信息失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 添加宿舍楼
     */
    @PostMapping("/buildings")
    public ResponseEntity<Map<String, Object>> addBuilding(@RequestBody DormitoryBuilding building) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = dormitoryService.addBuilding(building);
            if (success) {
                response.put("success", true);
                response.put("message", "添加宿舍楼成功");
            } else {
                response.put("success", false);
                response.put("message", "添加宿舍楼失败");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "添加宿舍楼失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 更新宿舍楼信息
     */
    @PutMapping("/buildings/{buildingId}")
    public ResponseEntity<Map<String, Object>> updateBuilding(@PathVariable Integer buildingId, @RequestBody DormitoryBuilding building) {
        Map<String, Object> response = new HashMap<>();
        try {
            building.setBuildingId(buildingId);
            boolean success = dormitoryService.updateBuilding(building);
            if (success) {
                response.put("success", true);
                response.put("message", "更新宿舍楼成功");
            } else {
                response.put("success", false);
                response.put("message", "更新宿舍楼失败");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "更新宿舍楼失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 删除宿舍楼
     */
    @DeleteMapping("/buildings/{buildingId}")
    public ResponseEntity<Map<String, Object>> deleteBuilding(@PathVariable Integer buildingId) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = dormitoryService.deleteBuilding(buildingId);
            if (success) {
                response.put("success", true);
                response.put("message", "删除宿舍楼成功");
            } else {
                response.put("success", false);
                response.put("message", "删除宿舍楼失败");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "删除宿舍楼失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    // ========== 管理员房间管理接口 ==========
    
    /**
     * 获取所有房间
     */
    @GetMapping("/rooms")
    public ResponseEntity<Map<String, Object>> getAllRooms() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<DormitoryRoom> rooms = dormitoryService.getAllRooms();
            response.put("success", true);
            response.put("data", rooms);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取房间信息失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 添加房间
     */
    @PostMapping("/rooms")
    public ResponseEntity<Map<String, Object>> addRoom(@RequestBody DormitoryRoom room) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = dormitoryService.addRoom(room);
            if (success) {
                response.put("success", true);
                response.put("message", "添加房间成功");
            } else {
                response.put("success", false);
                response.put("message", "添加房间失败");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "添加房间失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 更新房间信息
     */
    @PutMapping("/rooms/{roomId}")
    public ResponseEntity<Map<String, Object>> updateRoom(@PathVariable Integer roomId, @RequestBody DormitoryRoom room) {
        Map<String, Object> response = new HashMap<>();
        try {
            room.setRoomId(roomId);
            boolean success = dormitoryService.updateRoom(room);
            if (success) {
                response.put("success", true);
                response.put("message", "更新房间成功");
            } else {
                response.put("success", false);
                response.put("message", "更新房间失败");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "更新房间失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 删除房间
     */
    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<Map<String, Object>> deleteRoom(@PathVariable Integer roomId) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = dormitoryService.deleteRoom(roomId);
            if (success) {
                response.put("success", true);
                response.put("message", "删除房间成功");
            } else {
                response.put("success", false);
                response.put("message", "删除房间失败");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "删除房间失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    // ========== 管理员分配管理接口 ==========
    
    /**
     * 获取所有宿舍分配信息
     */
    @GetMapping("/assignments")
    public ResponseEntity<Map<String, Object>> getAllAssignments() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<StudentDormitory> assignments = dormitoryService.getAllAssignments();
            response.put("success", true);
            response.put("data", assignments);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取分配信息失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 添加分配记录
     */
    @PostMapping("/assignments")
    public ResponseEntity<Map<String, Object>> addAssignment(@RequestBody StudentDormitory assignment) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = dormitoryService.addAssignment(assignment);
            if (success) {
                response.put("success", true);
                response.put("message", "添加分配记录成功");
            } else {
                response.put("success", false);
                response.put("message", "添加分配记录失败");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "添加分配记录失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 更新分配记录
     */
    @PutMapping("/assignments/{assignmentId}")
    public ResponseEntity<Map<String, Object>> updateAssignment(@PathVariable Integer assignmentId, @RequestBody StudentDormitory assignment) {
        Map<String, Object> response = new HashMap<>();
        try {
            assignment.setAssignmentId(assignmentId);
            boolean success = dormitoryService.updateAssignment(assignment);
            if (success) {
                response.put("success", true);
                response.put("message", "更新分配记录成功");
            } else {
                response.put("success", false);
                response.put("message", "更新分配记录失败");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "更新分配记录失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 删除分配记录
     */
    @DeleteMapping("/assignments/{assignmentId}")
    public ResponseEntity<Map<String, Object>> deleteAssignment(@PathVariable Integer assignmentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = dormitoryService.deleteAssignment(assignmentId);
            if (success) {
                response.put("success", true);
                response.put("message", "删除分配记录成功");
            } else {
                response.put("success", false);
                response.put("message", "删除分配记录失败");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "删除分配记录失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 更新房间状态
     */
    @PutMapping("/rooms/{roomId}/status")
    public ResponseEntity<Map<String, Object>> updateRoomStatus(
            @PathVariable Integer roomId, 
            @RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String status = request.get("status");
            if (status == null || status.trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "房间状态不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            
            dormitoryService.updateRoomStatus(roomId, status);
            response.put("success", true);
            response.put("message", "房间状态更新成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "更新房间状态失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 获取指定房间的可用床位
     */
    @GetMapping("/beds/available/{roomId}")
    public ResponseEntity<Map<String, Object>> getAvailableBeds(@PathVariable Integer roomId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Integer> availableBeds = dormitoryService.getAvailableBeds(roomId);
            response.put("success", true);
            response.put("data", availableBeds);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取可用床位失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    // ========== 学生端接口 ==========
    
    /**
     * 根据性别获取宿舍楼
     */
    @GetMapping("/buildings/gender/{gender}")
    public ResponseEntity<Map<String, Object>> getBuildingsByGender(@PathVariable String gender) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<DormitoryBuilding> buildings = dormitoryService.getBuildingsByGender(gender);
            response.put("success", true);
            response.put("data", buildings);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取宿舍楼信息失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 根据宿舍楼ID获取可用房间
     */
    @GetMapping("/rooms/available/{buildingId}")
    public ResponseEntity<Map<String, Object>> getAvailableRooms(@PathVariable Integer buildingId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<DormitoryRoom> rooms = dormitoryService.getAvailableRooms(buildingId);
            response.put("success", true);
            response.put("data", rooms);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取可用房间失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 获取学生宿舍分配信息
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<Map<String, Object>> getStudentAllocation(@PathVariable String studentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            StudentDormitory allocation = dormitoryService.getStudentAllocation(studentId);
            response.put("success", true);
            response.put("data", allocation);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取学生宿舍信息失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 学生选择宿舍
     */
    @PostMapping("/select")
    public ResponseEntity<Map<String, Object>> selectDormitory(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String studentId = (String) request.get("studentId");
            Integer roomId = (Integer) request.get("roomId");
            
            if (studentId == null || roomId == null) {
                response.put("success", false);
                response.put("message", "学生ID和房间ID不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean success = dormitoryService.selectDormitory(studentId, roomId);
            if (success) {
                response.put("success", true);
                response.put("message", "宿舍选择成功");
            } else {
                response.put("success", false);
                response.put("message", "宿舍选择失败，房间可能已满");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "选择宿舍失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 确认入住
     */
    @PostMapping("/checkin")
    public ResponseEntity<Map<String, Object>> confirmCheckIn(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String studentId = (String) request.get("studentId");
            if (studentId == null) {
                response.put("success", false);
                response.put("message", "学生ID不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean success = dormitoryService.confirmCheckIn(studentId);
            if (success) {
                response.put("success", true);
                response.put("message", "确认入住成功");
            } else {
                response.put("success", false);
                response.put("message", "确认入住失败");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "确认入住失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 退宿
     */
    @PostMapping("/checkout")
    public ResponseEntity<Map<String, Object>> checkOut(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String studentId = (String) request.get("studentId");
            if (studentId == null) {
                response.put("success", false);
                response.put("message", "学生ID不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean success = dormitoryService.checkOut(studentId);
            if (success) {
                response.put("success", true);
                response.put("message", "退宿成功");
            } else {
                response.put("success", false);
                response.put("message", "退宿失败");
            }
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "退宿失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}