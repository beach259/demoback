package com.example.demo.entity;

import java.time.LocalDateTime;

public class Notice {
    private Integer id;
    private String title;
    private String content;
    private String type;
    private String priority;
    private String publisherId;
    private String publisherName;
    private LocalDateTime publishTime;
    private LocalDateTime effectiveTime;
    private LocalDateTime expiryTime;
    private String attachment;
    private String targetAudience;
    private Integer status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 构造函数
    public Notice() {}

    public Notice(String title, String content, String type, String priority, 
                  String publisherId, String publisherName, LocalDateTime effectiveTime, 
                  LocalDateTime expiryTime, String attachment) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.priority = priority;
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.effectiveTime = effectiveTime;
        this.expiryTime = expiryTime;
        this.attachment = attachment;
        this.status = 1;
        this.publishTime = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getter 和 Setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public LocalDateTime getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(LocalDateTime effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getTargetAudience() {
        return targetAudience;
    }

    public void setTargetAudience(String targetAudience) {
        this.targetAudience = targetAudience;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", priority='" + priority + '\'' +
                ", publisherId=" + publisherId +
                ", publisherName='" + publisherName + '\'' +
                ", publishTime=" + publishTime +
                ", effectiveTime=" + effectiveTime +
                ", expiryTime=" + expiryTime +
                ", attachment='" + attachment + '\'' +
                ", targetAudience='" + targetAudience + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}