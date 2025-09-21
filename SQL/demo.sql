/*
 Navicat Premium Dump SQL

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80040 (8.0.40)
 Source Host           : localhost:3306
 Source Schema         : demo

 Target Server Type    : MySQL
 Target Server Version : 80040 (8.0.40)
 File Encoding         : 65001

 Date: 21/09/2025 15:34:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `admin_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '管理员ID',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('A001', '123456');

-- ----------------------------
-- Table structure for dormitory_building
-- ----------------------------
DROP TABLE IF EXISTS `dormitory_building`;
CREATE TABLE `dormitory_building`  (
  `building_id` int NOT NULL AUTO_INCREMENT COMMENT '宿舍楼ID',
  `building_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '宿舍楼名称',
  `building_type` enum('male','female') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '宿舍楼类型（男生楼/女生楼）',
  `total_floors` int NOT NULL COMMENT '总楼层数',
  `rooms_per_floor` int NOT NULL COMMENT '每层房间数',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`building_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '宿舍楼信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dormitory_building
-- ----------------------------
INSERT INTO `dormitory_building` VALUES (1, '梧桐苑1号楼', 'male', 1, 5, '2025-09-20 15:27:23', '2025-09-21 14:03:04');
INSERT INTO `dormitory_building` VALUES (2, '梧桐苑2号楼', 'female', 1, 5, '2025-09-20 15:27:23', '2025-09-21 14:03:26');
INSERT INTO `dormitory_building` VALUES (3, '梧桐苑3号楼', 'male', 1, 1, '2025-09-20 15:27:23', '2025-09-21 14:03:37');

-- ----------------------------
-- Table structure for dormitory_room
-- ----------------------------
DROP TABLE IF EXISTS `dormitory_room`;
CREATE TABLE `dormitory_room`  (
  `room_id` int NOT NULL AUTO_INCREMENT COMMENT '房间ID',
  `building_id` int NOT NULL COMMENT '所属宿舍楼ID',
  `room_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '房间号',
  `floor_number` int NOT NULL COMMENT '楼层号',
  `bed_count` int NOT NULL DEFAULT 4 COMMENT '床位数',
  `occupied_count` int NOT NULL DEFAULT 0 COMMENT '已入住人数',
  `room_status` enum('available','full','maintenance') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'available' COMMENT '房间状态',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`room_id`) USING BTREE,
  UNIQUE INDEX `unique_room`(`building_id` ASC, `room_number` ASC) USING BTREE,
  CONSTRAINT `dormitory_room_ibfk_1` FOREIGN KEY (`building_id`) REFERENCES `dormitory_building` (`building_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '宿舍房间信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dormitory_room
-- ----------------------------
INSERT INTO `dormitory_room` VALUES (1, 1, '101', 1, 4, 0, 'available', '2025-09-20 15:27:23', '2025-09-20 17:54:01');
INSERT INTO `dormitory_room` VALUES (2, 1, '102', 1, 4, 0, 'available', '2025-09-20 15:27:23', '2025-09-20 15:27:23');
INSERT INTO `dormitory_room` VALUES (3, 1, '103', 1, 4, 0, 'available', '2025-09-20 15:27:23', '2025-09-20 15:27:23');
INSERT INTO `dormitory_room` VALUES (4, 1, '104', 1, 4, 0, 'available', '2025-09-20 15:27:23', '2025-09-20 15:27:23');
INSERT INTO `dormitory_room` VALUES (5, 1, '105', 1, 4, 0, 'available', '2025-09-20 15:27:23', '2025-09-20 15:27:23');
INSERT INTO `dormitory_room` VALUES (6, 2, '201', 2, 4, 1, 'available', '2025-09-20 15:27:23', '2025-09-21 15:09:00');
INSERT INTO `dormitory_room` VALUES (7, 2, '202', 2, 4, 0, 'available', '2025-09-20 15:27:23', '2025-09-20 15:27:23');
INSERT INTO `dormitory_room` VALUES (8, 2, '203', 2, 4, 0, 'available', '2025-09-20 15:27:23', '2025-09-20 15:27:23');
INSERT INTO `dormitory_room` VALUES (9, 2, '204', 2, 4, 0, 'available', '2025-09-20 15:27:23', '2025-09-20 15:27:23');
INSERT INTO `dormitory_room` VALUES (10, 2, '205', 2, 4, 0, 'available', '2025-09-20 15:27:23', '2025-09-20 15:27:23');
INSERT INTO `dormitory_room` VALUES (11, 3, '101', 1, 4, 0, 'available', '2025-09-20 17:54:29', '2025-09-20 17:54:29');

-- ----------------------------
-- Table structure for staff
-- ----------------------------
DROP TABLE IF EXISTS `staff`;
CREATE TABLE `staff`  (
  `staff_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '工作人员ID',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  PRIMARY KEY (`staff_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '工作人员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of staff
-- ----------------------------
INSERT INTO `staff` VALUES ('T001', '123456');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `student_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学生ID',
  `studentname` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学生姓名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '家庭住址',
  `age` int NULL DEFAULT NULL COMMENT '年龄',
  `gender` enum('male','female') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '性别',
  `info_completed` tinyint(1) NULL DEFAULT 0 COMMENT '信息是否完善',
  `modification_count` int NULL DEFAULT 0 COMMENT '信息修改次数',
  PRIMARY KEY (`student_id`) USING BTREE,
  UNIQUE INDEX `studentname`(`studentname` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生基本信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('S2025001', '张三', '123456', NULL, NULL, NULL, NULL, 'female', 0, 0);

-- ----------------------------
-- Table structure for student_dormitory
-- ----------------------------
DROP TABLE IF EXISTS `student_dormitory`;
CREATE TABLE `student_dormitory`  (
  `allocation_id` int NOT NULL AUTO_INCREMENT COMMENT '分配记录ID',
  `student_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '学生ID',
  `room_id` int NOT NULL COMMENT '房间ID',
  `bed_number` int NOT NULL COMMENT '床位号（1-4）',
  `check_in_date` date NULL DEFAULT NULL COMMENT '入住日期',
  `check_out_date` date NULL DEFAULT NULL COMMENT '退宿日期',
  `allocation_status` enum('pending','confirmed') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'pending' COMMENT '分配状态',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`allocation_id`) USING BTREE,
  UNIQUE INDEX `unique_student_allocation`(`student_id` ASC, `allocation_status` ASC) USING BTREE,
  UNIQUE INDEX `unique_bed_allocation`(`room_id` ASC, `bed_number` ASC, `allocation_status` ASC) USING BTREE,
  CONSTRAINT `student_dormitory_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`) ON DELETE CASCADE ON UPDATE RESTRICT,
  CONSTRAINT `student_dormitory_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `dormitory_room` (`room_id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生宿舍分配表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_dormitory
-- ----------------------------
INSERT INTO `student_dormitory` VALUES (3, 'S2025001', 6, 1, NULL, NULL, 'confirmed', '2025-09-21 15:09:00', '2025-09-21 15:31:06');

SET FOREIGN_KEY_CHECKS = 1;
