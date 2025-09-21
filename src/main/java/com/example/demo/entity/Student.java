package com.example.demo.entity;

import lombok.Data;

@Data
public class Student {
    private String studentId;
    private String studentname;
    private String password;
    private String phone;
    private String email;
    private String address;
    private Integer age;
    private String gender; // male, female
    private Boolean infoCompleted;
    private Integer modificationCount;
}