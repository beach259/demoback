package com.example.demo.mapper;

import com.example.demo.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
    Admin findByAdminIdAndPassword(@Param("adminId") String adminId, @Param("password") String password);
}