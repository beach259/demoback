package com.example.demo.entity;

import java.time.LocalDateTime;

/**
 * 通知阅读记录实体类
 */
public class NoticeReadRecord {
    private Integer recordId;
    private Integer noticeId;
    private String readerId;
    private String readerType; // student, staff, admin
    private LocalDateTime readTime;
    private Boolean isRead;
    
    public NoticeReadRecord() {}
    
    public NoticeReadRecord(Integer noticeId, String readerId, String readerType) {
        this.noticeId = noticeId;
        this.readerId = readerId;
        this.readerType = readerType;
        this.readTime = LocalDateTime.now();
        this.isRead = true;
    }
    
    // Getter 和 Setter 方法
    public Integer getRecordId() {
        return recordId;
    }
    
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }
    
    public Integer getNoticeId() {
        return noticeId;
    }
    
    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }
    
    public String getReaderId() {
        return readerId;
    }
    
    public void setReaderId(String readerId) {
        this.readerId = readerId;
    }
    
    public String getReaderType() {
        return readerType;
    }
    
    public void setReaderType(String readerType) {
        this.readerType = readerType;
    }
    
    public LocalDateTime getReadTime() {
        return readTime;
    }
    
    public void setReadTime(LocalDateTime readTime) {
        this.readTime = readTime;
    }
    
    public Boolean getIsRead() {
        return isRead;
    }
    
    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }
}