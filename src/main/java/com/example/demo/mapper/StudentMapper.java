package com.example.demo.mapper;

import com.example.demo.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    @Select("SELECT student_id, studentname, password, phone, email, address, age, info_completed, modification_count FROM student WHERE student_id = #{studentId} AND password = #{password}")
    @Results({
        @Result(property = "studentId", column = "student_id"),
        @Result(property = "infoCompleted", column = "info_completed"),
        @Result(property = "modificationCount", column = "modification_count")
    })
    Student findByStudentIdAndPassword(@Param("studentId") String studentId, @Param("password") String password);
    
    // 查询所有学生
    @Select("SELECT student_id, studentname, password, phone, email, address, age, info_completed, modification_count FROM student")
    @Results({
        @Result(property = "studentId", column = "student_id"),
        @Result(property = "infoCompleted", column = "info_completed"),
        @Result(property = "modificationCount", column = "modification_count")
    })
    List<Student> findAll();
    
    // 根据学生ID查询学生
    @Select("SELECT student_id, studentname, password, phone, email, address, age, info_completed, modification_count FROM student WHERE student_id = #{studentId}")
    @Results({
        @Result(property = "studentId", column = "student_id"),
        @Result(property = "infoCompleted", column = "info_completed"),
        @Result(property = "modificationCount", column = "modification_count")
    })
    Student findByStudentId(@Param("studentId") String studentId);
    
    // 添加学生
    @Insert("INSERT INTO student (student_id, studentname, password, phone, email, address, age, info_completed, modification_count) " +
            "VALUES (#{studentId}, #{studentname}, #{password}, #{phone}, #{email}, #{address}, #{age}, #{infoCompleted}, #{modificationCount})")
    int insert(Student student);
    
    // 更新学生信息
    @Update("UPDATE student SET studentname = #{studentname}, password = #{password}, " +
            "phone = #{phone}, email = #{email}, address = #{address}, age = #{age} " +
            "WHERE student_id = #{studentId}")
    int update(Student student);
    
    // 更新学生信息完善状态和修改次数
    @Update("UPDATE student SET phone = #{phone}, email = #{email}, address = #{address}, age = #{age}, " +
            "info_completed = #{infoCompleted}, modification_count = #{modificationCount} " +
            "WHERE student_id = #{studentId}")
    int updateStudentInfo(Student student);
    
    // 删除学生
    @Delete("DELETE FROM student WHERE student_id = #{studentId}")
    int deleteByStudentId(@Param("studentId") String studentId);
}