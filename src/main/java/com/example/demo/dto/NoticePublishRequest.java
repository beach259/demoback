package com.example.demo.dto;

import com.example.demo.entity.Notice;
import java.util.List;

/**
 * 通知发布请求DTO
 */
public class NoticePublishRequest {
    private Notice notice;
    private List<String> selectedStudents; // 选中的学生ID列表
    
    public NoticePublishRequest() {}
    
    public NoticePublishRequest(Notice notice, List<String> selectedStudents) {
        this.notice = notice;
        this.selectedStudents = selectedStudents;
    }
    
    public Notice getNotice() {
        return notice;
    }
    
    public void setNotice(Notice notice) {
        this.notice = notice;
    }
    
    public List<String> getSelectedStudents() {
        return selectedStudents;
    }
    
    public void setSelectedStudents(List<String> selectedStudents) {
        this.selectedStudents = selectedStudents;
    }
}