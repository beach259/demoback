package com.example.demo.mapper;

import com.example.demo.entity.Notice;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoticeMapper {

    // 查询所有通知
    @Select("SELECT notice_id as id, title, content, notice_type as type, priority, publisher_id as publisherId, " +
            "publisher_name as publisherName, publish_time as publishTime, expire_time as expiryTime, " +
            "attachment_url as attachment, is_published as status, created_at as createdAt, updated_at as updatedAt " +
            "FROM notice ORDER BY publish_time DESC")
    List<Notice> findAll();

    // 根据ID查询通知
    @Select("SELECT notice_id as id, title, content, notice_type as type, priority, publisher_id as publisherId, " +
            "publisher_name as publisherName, publish_time as publishTime, expire_time as expiryTime, " +
            "attachment_url as attachment, is_published as status, created_at as createdAt, updated_at as updatedAt " +
            "FROM notice WHERE notice_id = #{id}")
    Notice findById(Integer id);

    // 根据发布者ID查询通知
    @Select("SELECT notice_id as id, title, content, notice_type as type, priority, publisher_id as publisherId, " +
            "publisher_name as publisherName, publish_time as publishTime, expire_time as expiryTime, " +
            "attachment_url as attachment, is_published as status, created_at as createdAt, updated_at as updatedAt " +
            "FROM notice WHERE publisher_id = #{publisherId} ORDER BY publish_time DESC")
    List<Notice> findByPublisherId(String publisherId);

    // 根据类型查询通知
    @Select("SELECT notice_id as id, title, content, notice_type as type, priority, publisher_id as publisherId, " +
            "publisher_name as publisherName, publish_time as publishTime, expire_time as expiryTime, " +
            "attachment_url as attachment, is_published as status, created_at as createdAt, updated_at as updatedAt " +
            "FROM notice WHERE notice_type = #{type} ORDER BY publish_time DESC")
    List<Notice> findByType(String type);

    // 根据状态查询通知
    @Select("SELECT notice_id as id, title, content, notice_type as type, priority, publisher_id as publisherId, " +
            "publisher_name as publisherName, publish_time as publishTime, expire_time as expiryTime, " +
            "attachment_url as attachment, is_published as status, created_at as createdAt, updated_at as updatedAt " +
            "FROM notice WHERE is_published = #{status} ORDER BY publish_time DESC")
    List<Notice> findByStatus(String status);

    // 根据ID列表查询通知
    @Select("<script>" +
            "SELECT notice_id as id, title, content, notice_type as type, priority, publisher_id as publisherId, " +
            "publisher_name as publisherName, publish_time as publishTime, expire_time as expiryTime, " +
            "attachment_url as attachment, is_published as status, created_at as createdAt, updated_at as updatedAt " +
            "FROM notice WHERE notice_id IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            " ORDER BY publish_time DESC" +
            "</script>")
    List<Notice> findByIds(@Param("ids") List<Integer> ids);
    
    // 根据多个条件查询通知
    @Select("<script>" +
            "SELECT notice_id as id, title, content, notice_type as type, priority, publisher_id as publisherId, " +
            "publisher_name as publisherName, publish_time as publishTime, expire_time as expiryTime, " +
            "attachment_url as attachment, is_published as status, created_at as createdAt, updated_at as updatedAt " +
            "FROM notice WHERE 1=1" +
            "<if test='type != null and type != \"\"'> AND notice_type = #{type}</if>" +
            "<if test='status != null and status != \"\"'> AND is_published = #{status}</if>" +
            "<if test='priority != null and priority != \"\"'> AND priority = #{priority}</if>" +
            "<if test='publisherId != null'> AND publisher_id = #{publisherId}</if>" +
            " ORDER BY publish_time DESC" +
            "</script>")
    List<Notice> findByConditions(@Param("type") String type, 
                                  @Param("status") String status, 
                                  @Param("priority") String priority, 
                                  @Param("publisherId") String publisherId);

    // 查询有效的通知（当前时间在生效时间和过期时间之间）
    @Select("SELECT notice_id as id, title, content, notice_type as type, priority, publisher_id as publisherId, " +
            "publisher_name as publisherName, publish_time as publishTime, expire_time as expiryTime, " +
            "attachment_url as attachment, is_published as status, created_at as createdAt, updated_at as updatedAt " +
            "FROM notice WHERE is_published = 1 " +
            "AND (expire_time IS NULL OR expire_time >= NOW()) " +
            "ORDER BY priority DESC, publish_time DESC")
    List<Notice> findActiveNotices();

    // 插入通知
    @Insert("INSERT INTO notice (title, content, notice_type, priority, publisher_id, publisher_name, " +
            "publish_time, expire_time, attachment_url, is_published, created_at, updated_at) " +
            "VALUES (#{title}, #{content}, #{type}, #{priority}, #{publisherId}, #{publisherName}, " +
            "#{publishTime}, #{expiryTime}, #{attachment}, #{status}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Notice notice);

    // 更新通知
    @Update("UPDATE notice SET title = #{title}, content = #{content}, notice_type = #{type}, " +
            "priority = #{priority}, expire_time = #{expiryTime}, " +
            "attachment_url = #{attachment}, is_published = #{status}, updated_at = #{updatedAt} " +
            "WHERE notice_id = #{id}")
    int update(Notice notice);

    // 更新通知状态
    @Update("UPDATE notice SET is_published = #{status}, updated_at = NOW() WHERE notice_id = #{id}")
    int updateStatus(@Param("id") Integer id, @Param("status") Integer status);

    // 根据ID删除通知
    @Delete("DELETE FROM notice WHERE notice_id = #{id}")
    int deleteById(Integer id);

    // 批量删除通知
    @Delete("<script>" +
            "DELETE FROM notice WHERE notice_id IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>" +
            "#{id}" +
            "</foreach>" +
            "</script>")
    int deleteByIds(@Param("ids") List<Integer> ids);

    // 统计发布者的通知数量
    @Select("SELECT COUNT(*) FROM notice WHERE publisher_id = #{publisherId}")
    int countByPublisherId(String publisherId);

    // 根据状态统计通知数量
    @Select("SELECT COUNT(*) FROM notice WHERE is_published = #{status}")
    int countByStatus(Integer status);
}