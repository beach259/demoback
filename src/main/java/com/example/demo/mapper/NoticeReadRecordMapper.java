package com.example.demo.mapper;

import com.example.demo.entity.NoticeReadRecord;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeReadRecordMapper {
    
    /**
     * 插入阅读记录
     */
    @Insert("INSERT INTO notice_read_record (notice_id, reader_id, reader_type, read_time, is_read) " +
            "VALUES (#{noticeId}, #{readerId}, #{readerType}, #{readTime}, #{isRead})")
    @Options(useGeneratedKeys = true, keyProperty = "recordId")
    int insert(NoticeReadRecord record);
    
    /**
     * 批量插入阅读记录
     */
    @Insert("<script>" +
            "INSERT INTO notice_read_record (notice_id, reader_id, reader_type, read_time, is_read) VALUES " +
            "<foreach collection='records' item='record' separator=','>" +
            "(#{record.noticeId}, #{record.readerId}, #{record.readerType}, #{record.readTime}, #{record.isRead})" +
            "</foreach>" +
            "</script>")
    int batchInsert(@Param("records") List<NoticeReadRecord> records);
    
    /**
     * 根据通知ID和阅读者ID查询记录
     */
    @Select("SELECT record_id as recordId, notice_id as noticeId, reader_id as readerId, " +
            "reader_type as readerType, read_time as readTime, is_read as isRead " +
            "FROM notice_read_record WHERE notice_id = #{noticeId} AND reader_id = #{readerId}")
    NoticeReadRecord findByNoticeAndReader(@Param("noticeId") Integer noticeId, @Param("readerId") String readerId);
    
    /**
     * 根据通知ID查询所有阅读记录
     */
    @Select("SELECT record_id as recordId, notice_id as noticeId, reader_id as readerId, " +
            "reader_type as readerType, read_time as readTime, is_read as isRead " +
            "FROM notice_read_record WHERE notice_id = #{noticeId}")
    List<NoticeReadRecord> findByNoticeId(Integer noticeId);
    
    /**
     * 根据阅读者ID查询所有阅读记录
     */
    @Select("SELECT record_id as recordId, notice_id as noticeId, reader_id as readerId, " +
            "reader_type as readerType, read_time as readTime, is_read as isRead " +
            "FROM notice_read_record WHERE reader_id = #{readerId}")
    List<NoticeReadRecord> findByReaderId(String readerId);
    
    /**
     * 更新阅读状态
     */
    @Update("UPDATE notice_read_record SET is_read = #{isRead} " +
            "WHERE notice_id = #{noticeId} AND reader_id = #{readerId}")
    int updateReadStatus(@Param("noticeId") Integer noticeId, @Param("readerId") String readerId, @Param("isRead") Boolean isRead);
    
    /**
     * 更新阅读状态和阅读时间
     */
    @Update("UPDATE notice_read_record SET is_read = 1, read_time = #{readTime} " +
            "WHERE notice_id = #{noticeId} AND reader_id = #{readerId}")
    int updateReadStatusWithTime(@Param("noticeId") Integer noticeId, @Param("readerId") String readerId, 
                               @Param("isRead") Boolean isRead, @Param("readTime") LocalDateTime readTime);
    
    /**
     * 查询学生未读通知ID列表
     * 直接从notice_read_record表中查找is_read=0的记录，然后获取对应的notice_id
     */
    @Select("SELECT notice_id FROM notice_read_record " +
            "WHERE reader_id = #{readerId} AND is_read = 0 " +
            "ORDER BY record_id DESC")
    List<Integer> findUnreadNoticeIdsByReader(String readerId);
    
    /**
     * 查询学生的所有通知（包括已读和未读）
     * 返回通知ID和阅读状态
     */
    @Select("SELECT n.notice_id as noticeId, " +
            "CASE WHEN nrr.is_read = 1 THEN 'read' ELSE 'unread' END as readStatus, " +
            "nrr.read_time as readTime " +
            "FROM notice n " +
            "LEFT JOIN notice_read_record nrr ON n.notice_id = nrr.notice_id AND nrr.reader_id = #{readerId} " +
            "WHERE (n.target_audience = 'all' OR n.target_audience = 'students') " +
            "AND n.is_published = 1 " +
            "AND (n.expire_time IS NULL OR n.expire_time > NOW()) " +
            "ORDER BY n.priority DESC, n.publish_time DESC")
    List<Map<String, Object>> findAllNoticesWithReadStatus(String readerId);

    /**
     * 获取学生未读通知数量
     */
    @Select("SELECT COUNT(*) FROM notice n " +
            "LEFT JOIN notice_read_record nrr ON n.notice_id = nrr.notice_id AND nrr.reader_id = #{readerId} " +
            "WHERE (n.target_audience = 'all' OR n.target_audience = 'students') " +
            "AND n.is_published = 1 " +
            "AND (n.expire_time IS NULL OR n.expire_time > NOW()) " +
            "AND (nrr.record_id IS NULL OR nrr.is_read = false)")
    int countUnreadNotices(String readerId);

    /**
     * 删除阅读记录
     */
    @Delete("DELETE FROM notice_read_record WHERE record_id = #{recordId}")
    int deleteById(Integer recordId);
    
    /**
     * 根据通知ID删除所有相关记录
     */
    @Delete("DELETE FROM notice_read_record WHERE notice_id = #{noticeId}")
    int deleteByNoticeId(Integer noticeId);
}