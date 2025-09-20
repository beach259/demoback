-- 学生表
DROP TABLE IF EXISTS student;
CREATE TABLE student (
    student_id VARCHAR(20) PRIMARY KEY COMMENT '学生ID',
    studentname VARCHAR(10) UNIQUE NOT NULL COMMENT '学生姓名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    phone VARCHAR(20) COMMENT '手机号',
    email VARCHAR(100) COMMENT '邮箱',
    address VARCHAR(255) COMMENT '家庭住址',
    age INT COMMENT '年龄',
    info_completed BOOLEAN DEFAULT FALSE COMMENT '信息是否完善',
    modification_count INT DEFAULT 0 COMMENT '信息修改次数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生基本信息表';

INSERT INTO student (student_id, studentname, password, phone, email, address, age, info_completed, modification_count)
VALUES ('S2025001', '张三', '123456', NULL, NULL ,NULL , NULL, FALSE, 0);


-- 管理员表
DROP TABLE IF EXISTS admin;
CREATE TABLE admin (
    admin_id VARCHAR(20) PRIMARY KEY COMMENT '管理员ID',
    password VARCHAR(255) NOT NULL COMMENT '密码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';

INSERT INTO admin (admin_id, password)
VALUES ('A001', '123456');


-- 教职工/迎新工作人员表
DROP TABLE IF EXISTS staff;
CREATE TABLE staff (
    staff_id VARCHAR(20) PRIMARY KEY COMMENT '工作人员ID',
    password VARCHAR(255) NOT NULL COMMENT '密码'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工作人员表';

INSERT INTO staff (staff_id, password)
VALUES ('T001', 'staff123');
