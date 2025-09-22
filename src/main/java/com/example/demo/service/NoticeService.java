package com.example.demo.service;

import com.example.demo.entity.Notice;
import com.example.demo.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 根据ID列表获取通知
     */
    public List<Notice> getNoticesByIds(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return new ArrayList<>();
        }
        return noticeMapper.findByIds(ids);
    }
    
    /**
     * 获取所有通知
     */
    public List<Notice> getAllNotices() {
        return noticeMapper.findAll();
    }

    /**
     * 根据ID获取通知
     */
    public Notice getNoticeById(Integer id) {
        return noticeMapper.findById(id);
    }

    /**
     * 根据发布者ID获取通知列表
     */
    public List<Notice> getNoticesByPublisherId(String publisherId) {
        return noticeMapper.findByPublisherId(publisherId);
    }

    /**
     * 根据条件查询通知
     */
    public List<Notice> getNoticesByConditions(String type, String status, String priority, String publisherId) {
        return noticeMapper.findByConditions(type, status, priority, publisherId);
    }

    /**
     * 获取有效的通知（用于学生端显示）
     */
    public List<Notice> getActiveNotices() {
        return noticeMapper.findActiveNotices();
    }

    /**
     * 发布新通知
     */
    public boolean publishNotice(Notice notice) {
        try {
            // 设置发布时间和创建时间
            LocalDateTime now = LocalDateTime.now();
            notice.setPublishTime(now);
            notice.setCreatedAt(now);
            notice.setUpdatedAt(now);
            
            // 设置默认状态
            if (notice.getStatus() == null) {
                notice.setStatus(1); // 1表示已发布
            }
            
            int result = noticeMapper.insert(notice);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新通知
     */
    public boolean updateNotice(Notice notice) {
        try {
            // 更新修改时间
            notice.setUpdatedAt(LocalDateTime.now());
            
            int result = noticeMapper.update(notice);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 更新通知状态
     */
    public boolean updateNoticeStatus(Integer id, Integer status) {
        try {
            int result = noticeMapper.updateStatus(id, status);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除通知
     */
    public boolean deleteNotice(Integer id) {
        try {
            int result = noticeMapper.deleteById(id);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 批量删除通知
     */
    public boolean deleteNotices(List<Integer> ids) {
        try {
            if (ids == null || ids.isEmpty()) {
                return false;
            }
            int result = noticeMapper.deleteByIds(ids);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 停用通知（软删除）
     */
    public boolean deactivateNotice(Integer id) {
        return updateNoticeStatus(id, 0); // 0表示草稿/未发布
    }

    /**
     * 激活通知
     */
    public boolean activateNotice(Integer id) {
        return updateNoticeStatus(id, 1); // 1表示已发布
    }

    /**
     * 获取发布者通知统计
     */
    public int getNoticeCountByPublisher(String publisherId) {
        return noticeMapper.countByPublisherId(publisherId);
    }

    /**
     * 获取各状态通知统计
     */
    public int getNoticeCountByStatus(Integer status) {
        return noticeMapper.countByStatus(status);
    }

    /**
     * 检查通知是否有效
     */
    public boolean isNoticeActive(Notice notice) {
        if (notice == null || !Integer.valueOf(1).equals(notice.getStatus())) {
            return false;
        }
        
        LocalDateTime now = LocalDateTime.now();
        
        // 检查生效时间
        if (notice.getEffectiveTime() != null && now.isBefore(notice.getEffectiveTime())) {
            return false;
        }
        
        // 检查过期时间
        if (notice.getExpiryTime() != null && now.isAfter(notice.getExpiryTime())) {
            return false;
        }
        
        return true;
    }
}