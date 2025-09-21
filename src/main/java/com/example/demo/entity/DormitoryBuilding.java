package com.example.demo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class DormitoryBuilding {
    private Integer buildingId;
    private String buildingName;
    private String buildingType; // male, female
    private Integer totalFloors;
    private Integer roomsPerFloor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}