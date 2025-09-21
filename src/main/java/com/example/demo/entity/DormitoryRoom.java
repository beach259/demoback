package com.example.demo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DormitoryRoom {
    private Integer roomId;
    private Integer buildingId;
    private String roomNumber;
    private Integer floorNumber;
    private Integer bedCount;
    private Integer occupiedCount;
    private String roomStatus; // available, full, maintenance
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 关联的宿舍楼信息
    private DormitoryBuilding building;
    
    // 宿舍楼名称（用于查询时的映射）
    private String buildingName;
    private String buildingType;
    
    // 获取房间容量（床位数）
    public Integer getCapacity() {
        return this.bedCount;
    }
}