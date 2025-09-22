package com.example.demo.service;

import com.example.demo.entity.NoticeReadRecord;
import com.example.demo.entity.Student;
import com.example.demo.mapper.NoticeReadRecordMapper;
import com.example.demo.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NoticeReadRecordService {
    
    @Autowired
    private NoticeReadRecordMapper noticeReadRecordMapper;
    
    @Autowired
    private StudentMapper studentMapper;
    
    /**
     * 为所有学生创建通知阅读记录（用于target_audience为all或students的通知）
     */
    public boolean createReadRecordsForAllStudents(Integer noticeId) {
        try {
            // 获取所有学生ID
            List<Student> allStudents = studentMapper.findAll();
            List<String> studentIds = new ArrayList<>();
            for (Student student : allStudents) {
                studentIds.add(student.getStudentId());
            }
            
            return createReadRecordsForStudents(noticeId, studentIds);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 为指定学生创建通知阅读记录
     */
    public boolean createReadRecordsForStudents(Integer noticeId, List<String> studentIds) {
        try {
            if (studentIds == null || studentIds.isEmpty()) {
                return true; // 没有学生需要创建记录，返回成功
            }
            
            List<NoticeReadRecord> records = new ArrayList<>();
            
            for (String studentId : studentIds) {
                // 检查是否已存在记录
                NoticeReadRecord existingRecord = noticeReadRecordMapper.findByNoticeAndReader(noticeId, studentId);
                if (existingRecord == null) {
                    NoticeReadRecord record = new NoticeReadRecord();
                    record.setNoticeId(noticeId);
                    record.setReaderId(studentId);
                    record.setReaderType("student");
                    record.setReadTime(null); // 阅读时间设为空
                    record.setIsRead(false); // 是否已读设为false（数据库不允许为null）
                    records.add(record);
                }
            }
            
            if (!records.isEmpty()) {
                int result = noticeReadRecordMapper.batchInsert(records);
                return result > 0;
            }
            
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 标记通知为已读
     */
    public boolean markAsRead(Integer noticeId, String readerId) {
        try {
            NoticeReadRecord existingRecord = noticeReadRecordMapper.findByNoticeAndReader(noticeId, readerId);
            if (existingRecord != null) {
                // 更新现有记录
                int result = noticeReadRecordMapper.updateReadStatus(noticeId, readerId, true);
                return result > 0;
            } else {
                // 创建新记录
                NoticeReadRecord record = new NoticeReadRecord(noticeId, readerId, "student");
                record.setIsRead(true);
                int result = noticeReadRecordMapper.insert(record);
                return result > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 获取通知的所有阅读记录
     */
    public List<NoticeReadRecord> getReadRecordsByNoticeId(Integer noticeId) {
        return noticeReadRecordMapper.findByNoticeId(noticeId);
    }
    
    /**
     * 获取用户的所有阅读记录
     */
    public List<NoticeReadRecord> getReadRecordsByReaderId(String readerId) {
        return noticeReadRecordMapper.findByReaderId(readerId);
    }
    
    /**
     * 获取学生未读通知
     */
    public List<Integer> getUnreadNoticeIds(String studentId) {
        return noticeReadRecordMapper.findUnreadNoticeIdsByReader(studentId);
    }
    
    /**
     * 获取学生未读通知数量
     */
    public int getUnreadNoticesCount(String studentId) {
        return noticeReadRecordMapper.countUnreadNotices(studentId);
    }
    
    /**
     * 获取学生所有通知的阅读状态
     */
    public List<Map<String, Object>> getAllNoticesWithReadStatus(String studentId) {
        return noticeReadRecordMapper.findAllNoticesWithReadStatus(studentId);
    }
    
    /**
     * 标记通知为已读并设置阅读时间
     */
    public boolean markAsReadWithTime(Integer noticeId, String readerId) {
        try {
            NoticeReadRecord existingRecord = noticeReadRecordMapper.findByNoticeAndReader(noticeId, readerId);
            if (existingRecord != null) {
                // 更新现有记录，设置阅读时间和已读状态
                LocalDateTime now = LocalDateTime.now();
                int result = noticeReadRecordMapper.updateReadStatusWithTime(noticeId, readerId, true, now);
                return result > 0;
            } else {
                // 创建新记录
                NoticeReadRecord record = new NoticeReadRecord(noticeId, readerId, "student");
                record.setIsRead(true);
                record.setReadTime(LocalDateTime.now());
                int result = noticeReadRecordMapper.insert(record);
                return result > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 删除指定通知的所有阅读记录
     */
    public boolean deleteReadRecordsByNoticeId(Integer noticeId) {
        try {
            int result = noticeReadRecordMapper.deleteByNoticeId(noticeId);
            return result >= 0; // 即使没有记录删除也返回成功
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}