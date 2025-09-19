package com.example.demo.mapper;

import com.example.demo.entity.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StaffMapper {
    Staff findByStaffIdAndPassword(@Param("staffId") String staffId, @Param("password") String password);
}