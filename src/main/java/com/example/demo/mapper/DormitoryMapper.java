package com.example.demo.mapper;

import com.example.demo.entity.DormitoryBuilding;
import com.example.demo.entity.DormitoryRoom;
import com.example.demo.entity.StudentDormitory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DormitoryMapper {
    
    // ========== 宿舍楼相关操作 ==========
    
    @Select("SELECT building_id, building_name, building_type, total_floors, rooms_per_floor, created_at, updated_at " +
            "FROM dormitory_building ORDER BY building_id")
    @Results({
        @Result(property = "buildingId", column = "building_id"),
        @Result(property = "buildingName", column = "building_name"),
        @Result(property = "buildingType", column = "building_type"),
        @Result(property = "totalFloors", column = "total_floors"),
        @Result(property = "roomsPerFloor", column = "rooms_per_floor"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    List<DormitoryBuilding> findAllBuildings();
    
    @Select("SELECT building_id, building_name, building_type, total_floors, rooms_per_floor, created_at, updated_at " +
            "FROM dormitory_building WHERE building_type = #{buildingType}")
    @Results({
        @Result(property = "buildingId", column = "building_id"),
        @Result(property = "buildingName", column = "building_name"),
        @Result(property = "buildingType", column = "building_type"),
        @Result(property = "totalFloors", column = "total_floors"),
        @Result(property = "roomsPerFloor", column = "rooms_per_floor"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    List<DormitoryBuilding> findBuildingsByType(@Param("buildingType") String buildingType);
    
    // 管理员宿舍楼管理方法
    @Insert("INSERT INTO dormitory_building (building_name, building_type, total_floors, rooms_per_floor) " +
            "VALUES (#{buildingName}, #{buildingType}, #{totalFloors}, #{roomsPerFloor})")
    @Options(useGeneratedKeys = true, keyProperty = "buildingId")
    int insertBuilding(DormitoryBuilding building);
    
    @Update("UPDATE dormitory_building SET building_name = #{buildingName}, building_type = #{buildingType}, " +
            "total_floors = #{totalFloors}, rooms_per_floor = #{roomsPerFloor}, updated_at = CURRENT_TIMESTAMP " +
            "WHERE building_id = #{buildingId}")
    int updateBuilding(DormitoryBuilding building);
    
    @Delete("DELETE FROM dormitory_building WHERE building_id = #{buildingId}")
    int deleteBuilding(@Param("buildingId") Integer buildingId);
    
    @Select("SELECT room_id, building_id, room_number, floor_number, bed_count, occupied_count, " +
            "room_status, created_at, updated_at FROM dormitory_room WHERE building_id = #{buildingId}")
    @Results({
        @Result(property = "roomId", column = "room_id"),
        @Result(property = "buildingId", column = "building_id"),
        @Result(property = "roomNumber", column = "room_number"),
        @Result(property = "floorNumber", column = "floor_number"),
        @Result(property = "bedCount", column = "bed_count"),
        @Result(property = "occupiedCount", column = "occupied_count"),
        @Result(property = "roomStatus", column = "room_status"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    List<DormitoryRoom> findRoomsByBuildingId(@Param("buildingId") Integer buildingId);
    
    // ========== 宿舍房间相关操作 ==========
    
    @Select("SELECT r.room_id, r.building_id, r.room_number, r.floor_number, r.bed_count, r.occupied_count, " +
            "r.room_status, r.created_at, r.updated_at, " +
            "b.building_name, b.building_type " +
            "FROM dormitory_room r " +
            "LEFT JOIN dormitory_building b ON r.building_id = b.building_id " +
            "WHERE r.building_id = #{buildingId} AND r.room_status = 'available' " +
            "ORDER BY r.room_number")
    @Results({
        @Result(property = "roomId", column = "room_id"),
        @Result(property = "buildingId", column = "building_id"),
        @Result(property = "roomNumber", column = "room_number"),
        @Result(property = "floorNumber", column = "floor_number"),
        @Result(property = "bedCount", column = "bed_count"),
        @Result(property = "occupiedCount", column = "occupied_count"),
        @Result(property = "roomStatus", column = "room_status"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at"),
        @Result(property = "building.buildingName", column = "building_name"),
        @Result(property = "building.buildingType", column = "building_type")
    })
    List<DormitoryRoom> findAvailableRoomsByBuilding(@Param("buildingId") Integer buildingId);
    
    // 管理员房间管理方法
    @Select("SELECT r.room_id, r.building_id, r.room_number, r.floor_number, r.bed_count, r.occupied_count, " +
            "r.room_status, r.created_at, r.updated_at, " +
            "b.building_name, b.building_type " +
            "FROM dormitory_room r " +
            "LEFT JOIN dormitory_building b ON r.building_id = b.building_id " +
            "ORDER BY r.building_id, r.room_number")
    @Results({
        @Result(property = "roomId", column = "room_id"),
        @Result(property = "buildingId", column = "building_id"),
        @Result(property = "roomNumber", column = "room_number"),
        @Result(property = "floorNumber", column = "floor_number"),
        @Result(property = "bedCount", column = "bed_count"),
        @Result(property = "occupiedCount", column = "occupied_count"),
        @Result(property = "roomStatus", column = "room_status"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at"),
        @Result(property = "buildingName", column = "building_name"),
        @Result(property = "buildingType", column = "building_type")
    })
    List<DormitoryRoom> findAllRooms();
    
    @Insert("INSERT INTO dormitory_room (building_id, room_number, floor_number, bed_count, room_status) " +
            "VALUES (#{buildingId}, #{roomNumber}, #{floorNumber}, #{bedCount}, #{roomStatus})")
    @Options(useGeneratedKeys = true, keyProperty = "roomId")
    int insertRoom(DormitoryRoom room);
    
    @Update("UPDATE dormitory_room SET building_id = #{buildingId}, room_number = #{roomNumber}, " +
            "floor_number = #{floorNumber}, bed_count = #{bedCount}, room_status = #{roomStatus}, " +
            "updated_at = CURRENT_TIMESTAMP WHERE room_id = #{roomId}")
    int updateRoom(DormitoryRoom room);
    
    @Delete("DELETE FROM dormitory_room WHERE room_id = #{roomId}")
    int deleteRoom(@Param("roomId") Integer roomId);
    
    @Select("SELECT room_id, building_id, room_number, floor_number, bed_count, occupied_count, " +
            "room_status, created_at, updated_at FROM dormitory_room WHERE room_id = #{roomId}")
    @Results({
        @Result(property = "roomId", column = "room_id"),
        @Result(property = "buildingId", column = "building_id"),
        @Result(property = "roomNumber", column = "room_number"),
        @Result(property = "floorNumber", column = "floor_number"),
        @Result(property = "bedCount", column = "bed_count"),
        @Result(property = "occupiedCount", column = "occupied_count"),
        @Result(property = "roomStatus", column = "room_status"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    DormitoryRoom findRoomById(@Param("roomId") Integer roomId);
    
    @Update("UPDATE dormitory_room SET occupied_count = occupied_count + 1, " +
            "room_status = CASE WHEN occupied_count + 1 >= bed_count THEN 'full' ELSE 'available' END " +
            "WHERE room_id = #{roomId}")
    int increaseRoomOccupancy(@Param("roomId") Integer roomId);
    
    @Update("UPDATE dormitory_room SET occupied_count = occupied_count - 1, " +
            "room_status = CASE WHEN occupied_count - 1 < bed_count THEN 'available' ELSE room_status END " +
            "WHERE room_id = #{roomId}")
    int decreaseRoomOccupancy(@Param("roomId") Integer roomId);
    
    /**
     * 更新房间状态
     */
    @Update("UPDATE dormitory_room SET room_status = #{status}, updated_at = NOW() WHERE room_id = #{roomId}")
    int updateRoomStatus(@Param("roomId") Integer roomId, @Param("status") String status);

    /**
     * 获取指定房间已占用的床位号
     */
    @Select("SELECT bed_number FROM student_dormitory WHERE room_id = #{roomId} AND allocation_status IN ('confirmed', 'checked_in')")
    List<Integer> getOccupiedBeds(@Param("roomId") Integer roomId);

    // ========== 学生宿舍分配相关操作 ==========
    
    @Select("SELECT sd.allocation_id, sd.student_id, sd.room_id, sd.bed_number, sd.check_in_date, " +
            "sd.check_out_date, sd.allocation_status, sd.created_at, sd.updated_at, " +
            "r.room_number, r.floor_number, " +
            "b.building_name, b.building_type " +
            "FROM student_dormitory sd " +
            "LEFT JOIN dormitory_room r ON sd.room_id = r.room_id " +
            "LEFT JOIN dormitory_building b ON r.building_id = b.building_id " +
            "WHERE sd.student_id = #{studentId} AND sd.allocation_status IN ('pending', 'confirmed', 'checked_in')")
    @Results({
        @Result(property = "allocationId", column = "allocation_id"),
        @Result(property = "studentId", column = "student_id"),
        @Result(property = "roomId", column = "room_id"),
        @Result(property = "bedNumber", column = "bed_number"),
        @Result(property = "checkInDate", column = "check_in_date"),
        @Result(property = "checkOutDate", column = "check_out_date"),
        @Result(property = "allocationStatus", column = "allocation_status"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at"),
        @Result(property = "room.roomNumber", column = "room_number"),
        @Result(property = "room.floorNumber", column = "floor_number"),
        @Result(property = "room.building.buildingName", column = "building_name"),
        @Result(property = "room.building.buildingType", column = "building_type")
    })
    StudentDormitory findCurrentAllocationByStudentId(@Param("studentId") String studentId);
    
    // 管理员分配管理方法
    @Select("SELECT sd.allocation_id, sd.student_id, sd.room_id, sd.bed_number, sd.check_in_date, " +
            "sd.check_out_date, sd.allocation_status, sd.created_at, sd.updated_at, " +
            "r.room_number, r.floor_number, " +
            "b.building_name, b.building_type, " +
            "s.studentname as student_name, s.gender as student_class " +
            "FROM student_dormitory sd " +
            "LEFT JOIN dormitory_room r ON sd.room_id = r.room_id " +
            "LEFT JOIN dormitory_building b ON r.building_id = b.building_id " +
            "LEFT JOIN student s ON sd.student_id = s.student_id " +
            "WHERE sd.allocation_status IN ('pending', 'confirmed', 'checked_in') " +
            "ORDER BY sd.created_at DESC")
    @Results({
        @Result(property = "allocationId", column = "allocation_id"),
        @Result(property = "studentId", column = "student_id"),
        @Result(property = "roomId", column = "room_id"),
        @Result(property = "bedNumber", column = "bed_number"),
        @Result(property = "checkInDate", column = "check_in_date"),
        @Result(property = "checkOutDate", column = "check_out_date"),
        @Result(property = "allocationStatus", column = "allocation_status"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at"),
        @Result(property = "roomNumber", column = "room_number"),
        @Result(property = "floorNumber", column = "floor_number"),
        @Result(property = "buildingName", column = "building_name"),
        @Result(property = "buildingType", column = "building_type"),
        @Result(property = "studentName", column = "student_name"),
        @Result(property = "studentClass", column = "student_class")
    })
    List<StudentDormitory> findAllAssignments();
    
    @Select("SELECT sd.allocation_id, sd.student_id, sd.room_id, sd.bed_number, sd.check_in_date, " +
            "sd.check_out_date, sd.allocation_status, sd.created_at, sd.updated_at " +
            "FROM student_dormitory sd " +
            "WHERE sd.room_id = #{roomId} AND sd.allocation_status IN ('pending', 'confirmed', 'checked_in')")
    @Results({
        @Result(property = "allocationId", column = "allocation_id"),
        @Result(property = "studentId", column = "student_id"),
        @Result(property = "roomId", column = "room_id"),
        @Result(property = "bedNumber", column = "bed_number"),
        @Result(property = "checkInDate", column = "check_in_date"),
        @Result(property = "checkOutDate", column = "check_out_date"),
        @Result(property = "allocationStatus", column = "allocation_status"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    List<StudentDormitory> findAssignmentsByRoomId(@Param("roomId") Integer roomId);
    
    @Delete("DELETE FROM student_dormitory WHERE allocation_id = #{assignmentId}")
    int deleteAssignment(@Param("assignmentId") Integer assignmentId);
    
    @Select("SELECT COUNT(*) FROM student_dormitory WHERE room_id = #{roomId} AND bed_number = #{bedNumber} " +
            "AND allocation_status IN ('pending', 'confirmed', 'checked_in')")
    int countBedOccupancy(@Param("roomId") Integer roomId, @Param("bedNumber") Integer bedNumber);
    
    @Select("SELECT COUNT(*) FROM student_dormitory WHERE room_id = #{roomId} " +
            "AND allocation_status IN ('pending', 'confirmed', 'checked_in')")
    int countRoomOccupancy(@Param("roomId") Integer roomId);
    
    @Insert("INSERT INTO student_dormitory (student_id, room_id, bed_number, allocation_status) " +
            "VALUES (#{studentId}, #{roomId}, #{bedNumber}, #{allocationStatus})")
    @Options(useGeneratedKeys = true, keyProperty = "allocationId")
    int insertAllocation(StudentDormitory allocation);
    
    @Update("UPDATE student_dormitory SET student_id = #{studentId}, room_id = #{roomId}, " +
            "bed_number = #{bedNumber}, allocation_status = #{allocationStatus}, " +
            "check_in_date = #{checkInDate}, check_out_date = #{checkOutDate}, " +
            "updated_at = CURRENT_TIMESTAMP WHERE allocation_id = #{allocationId}")
    int updateAllocation(StudentDormitory allocation);

    @Update("UPDATE student_dormitory SET allocation_status = #{allocationStatus}, " +
            "check_in_date = #{checkInDate}, updated_at = CURRENT_TIMESTAMP " +
            "WHERE allocation_id = #{allocationId}")
    int updateAllocationStatus(StudentDormitory allocation);
    
    @Update("UPDATE student_dormitory SET allocation_status = 'checked_out', " +
            "check_out_date = CURRENT_DATE, updated_at = CURRENT_TIMESTAMP " +
            "WHERE student_id = #{studentId} AND allocation_status IN ('pending', 'confirmed', 'checked_in')")
    int checkOutStudent(@Param("studentId") String studentId);
    
    @Delete("DELETE FROM student_dormitory " +
            "WHERE student_id = #{studentId} AND allocation_status IN ('pending', 'confirmed', 'checked_in')")
    int deleteStudentAllocation(@Param("studentId") String studentId);
    
    // ========== 新增缺失的方法 ==========
    
    @Select("SELECT sd.allocation_id, sd.student_id, sd.room_id, sd.bed_number, " +
            "sd.allocation_status, sd.check_in_date, sd.check_out_date, sd.created_at, sd.updated_at, " +
            "r.room_number, r.floor_number, r.building_id, " +
            "b.building_name, b.building_type " +
            "FROM student_dormitory sd " +
            "JOIN dormitory_room r ON sd.room_id = r.room_id " +
            "JOIN dormitory_building b ON r.building_id = b.building_id " +
            "WHERE sd.student_id = #{studentId} AND sd.allocation_status IN ('pending', 'confirmed', 'checked_in')")
    @Results({
        @Result(property = "allocationId", column = "allocation_id"),
        @Result(property = "studentId", column = "student_id"),
        @Result(property = "roomId", column = "room_id"),
        @Result(property = "bedNumber", column = "bed_number"),
        @Result(property = "allocationStatus", column = "allocation_status"),
        @Result(property = "checkInDate", column = "check_in_date"),
        @Result(property = "checkOutDate", column = "check_out_date"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at"),
        @Result(property = "roomNumber", column = "room_number"),
        @Result(property = "floorNumber", column = "floor_number"),
        @Result(property = "buildingId", column = "building_id"),
        @Result(property = "buildingName", column = "building_name"),
        @Result(property = "buildingType", column = "building_type")
    })
    StudentDormitory findStudentCurrentAllocation(@Param("studentId") String studentId);
    
    @Select("SELECT building_id, building_name, building_type, total_floors, rooms_per_floor, created_at, updated_at " +
            "FROM dormitory_building WHERE building_id = #{buildingId}")
    @Results({
        @Result(property = "buildingId", column = "building_id"),
        @Result(property = "buildingName", column = "building_name"),
        @Result(property = "buildingType", column = "building_type"),
        @Result(property = "totalFloors", column = "total_floors"),
        @Result(property = "roomsPerFloor", column = "rooms_per_floor"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    DormitoryBuilding findBuildingById(@Param("buildingId") Integer buildingId);
    
    @Select("SELECT sd.allocation_id, sd.student_id, sd.room_id, sd.bed_number, " +
            "sd.allocation_status, sd.check_in_date, sd.check_out_date, sd.created_at, sd.updated_at " +
            "FROM student_dormitory sd " +
            "WHERE sd.room_id = #{roomId} AND sd.allocation_status IN ('pending', 'confirmed', 'checked_in')")
    @Results({
        @Result(property = "allocationId", column = "allocation_id"),
        @Result(property = "studentId", column = "student_id"),
        @Result(property = "roomId", column = "room_id"),
        @Result(property = "bedNumber", column = "bed_number"),
        @Result(property = "allocationStatus", column = "allocation_status"),
        @Result(property = "checkInDate", column = "check_in_date"),
        @Result(property = "checkOutDate", column = "check_out_date"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at")
    })
    List<StudentDormitory> findAllocationsByRoomId(@Param("roomId") Integer roomId);
}