package com.example.demo.mapper;

import com.example.demo.entity.Staff;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StaffMapper {
    // 登录验证
    @Select("SELECT staff_id, password FROM staff WHERE staff_id = #{staffId} AND password = #{password}")
    @Results({
        @Result(property = "staffId", column = "staff_id")
    })
    Staff findByStaffIdAndPassword(@Param("staffId") String staffId, @Param("password") String password);
    
    // 查询所有教职工
    @Select("SELECT staff_id, password FROM staff")
    @Results({
        @Result(property = "staffId", column = "staff_id")
    })
    List<Staff> findAll();
    
    // 根据教职工ID查询
    @Select("SELECT staff_id, password FROM staff WHERE staff_id = #{staffId}")
    @Results({
        @Result(property = "staffId", column = "staff_id")
    })
    Staff findByStaffId(@Param("staffId") String staffId);
    
    // 添加教职工
    @Insert("INSERT INTO staff (staff_id, password) VALUES (#{staffId}, #{password})")
    int insert(Staff staff);
    
    // 更新教职工信息
    @Update("UPDATE staff SET password = #{password} WHERE staff_id = #{staffId}")
    int update(Staff staff);
    
    // 删除教职工
    @Delete("DELETE FROM staff WHERE staff_id = #{staffId}")
    int deleteByStaffId(@Param("staffId") String staffId);
}