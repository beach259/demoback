package com.example.demo.controller;

import com.example.demo.dto.NoticePublishRequest;
import com.example.demo.entity.Notice;
import com.example.demo.service.NoticeService;
import com.example.demo.service.NoticeReadRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notices")
@CrossOrigin(origins = "*")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;
    
    @Autowired
    private NoticeReadRecordService noticeReadRecordService;

    /**
     * 更新通知并创建阅读记录
     */
    @PutMapping("/{id}/publish")
    public ResponseEntity<Map<String, Object>> updateNoticeWithRecords(@PathVariable Integer id, @RequestBody NoticePublishRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            System.out.println("收到更新通知请求，ID: " + id);
            System.out.println("请求数据: " + request);
            
            // 检查通知是否存在
            Notice existingNotice = noticeService.getNoticeById(id);
            if (existingNotice == null) {
                response.put("success", false);
                response.put("message", "通知不存在");
                return ResponseEntity.status(404).body(response);
            }

            Notice notice = request.getNotice();
            List<String> selectedStudents = request.getSelectedStudents();
            
            System.out.println("选中的学生列表: " + selectedStudents);
            
            // 设置ID
            notice.setId(id);
            
            // 基本验证
            if (notice.getTitle() == null || notice.getTitle().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "通知标题不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (notice.getContent() == null || notice.getContent().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "通知内容不能为空");
                return ResponseEntity.badRequest().body(response);
            }

            // 更新通知
            boolean noticeSuccess = noticeService.updateNotice(notice);
            if (!noticeSuccess) {
                response.put("success", false);
                response.put("message", "更新通知失败");
                return ResponseEntity.status(500).body(response);
            }
            
            // 如果选择了学生，先删除旧的阅读记录，再创建新的
            if (selectedStudents != null && !selectedStudents.isEmpty()) {
                // 删除该通知的所有阅读记录
                noticeReadRecordService.deleteReadRecordsByNoticeId(id);
                
                // 为选中的学生创建新的阅读记录
                boolean recordSuccess = noticeReadRecordService.createReadRecordsForStudents(id, selectedStudents);
                if (!recordSuccess) {
                    response.put("success", false);
                    response.put("message", "通知更新成功，但创建阅读记录失败");
                    return ResponseEntity.status(500).body(response);
                }
            } else if ("all".equals(notice.getTargetAudience()) || "students".equals(notice.getTargetAudience())) {
                // 删除该通知的所有阅读记录
                noticeReadRecordService.deleteReadRecordsByNoticeId(id);
                
                // 如果目标受众是所有人或学生，为所有学生创建记录
                boolean recordSuccess = noticeReadRecordService.createReadRecordsForAllStudents(id);
                if (!recordSuccess) {
                    response.put("success", false);
                    response.put("message", "通知更新成功，但创建阅读记录失败");
                    return ResponseEntity.status(500).body(response);
                }
            }
            
            response.put("success", true);
            response.put("data", notice);
            response.put("message", "更新通知成功");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "更新通知失败：" + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 获取所有通知
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllNotices() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Notice> notices = noticeService.getAllNotices();
            response.put("success", true);
            response.put("data", notices);
            response.put("message", "获取通知列表成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取通知列表失败：" + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 发布通知并创建阅读记录
     */
    @PostMapping("/publish")
    public ResponseEntity<Map<String, Object>> publishNoticeWithRecords(@RequestBody NoticePublishRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Notice notice = request.getNotice();
            List<String> selectedStudents = request.getSelectedStudents();
            
            // 基本验证
            if (notice.getTitle() == null || notice.getTitle().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "通知标题不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (notice.getContent() == null || notice.getContent().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "通知内容不能为空");
                return ResponseEntity.badRequest().body(response);
            }

            // 发布通知
            boolean noticeSuccess = noticeService.publishNotice(notice);
            if (!noticeSuccess) {
                response.put("success", false);
                response.put("message", "发布通知失败");
                return ResponseEntity.status(500).body(response);
            }
            
            // 根据目标受众创建阅读记录
            boolean recordSuccess = false;
            if (selectedStudents != null && !selectedStudents.isEmpty()) {
                // 如果选择了特定学生，为这些学生创建记录
                recordSuccess = noticeReadRecordService.createReadRecordsForStudents(notice.getId(), selectedStudents);
            } else if ("all".equals(notice.getTargetAudience()) || "students".equals(notice.getTargetAudience())) {
                // 如果目标受众是所有人或学生，为所有学生创建记录
                recordSuccess = noticeReadRecordService.createReadRecordsForAllStudents(notice.getId());
            } else {
                recordSuccess = true; // 其他情况不需要创建记录
            }
            
            if (!recordSuccess) {
                response.put("success", false);
                response.put("message", "通知发布成功，但创建阅读记录失败");
                return ResponseEntity.status(500).body(response);
            }
            
            response.put("success", true);
            response.put("data", notice);
            response.put("message", "发布通知成功");
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "发布通知失败：" + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 根据条件查询通知
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchNotices(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String publisherId) {
        
        Map<String, Object> response = new HashMap<>();
        try {
            List<Notice> notices = noticeService.getNoticesByConditions(type, status, priority, publisherId);
            response.put("success", true);
            response.put("data", notices);
            response.put("message", "查询通知成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "查询通知失败：" + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 获取有效通知（用于学生端）
     */
    @GetMapping("/active")
    public ResponseEntity<Map<String, Object>> getActiveNotices() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Notice> notices = noticeService.getActiveNotices();
            response.put("success", true);
            response.put("data", notices);
            response.put("message", "获取有效通知成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取有效通知失败：" + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 根据ID获取通知详情
     */
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getNoticeById(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Notice notice = noticeService.getNoticeById(id);
            if (notice != null) {
                response.put("success", true);
                response.put("data", notice);
                response.put("message", "获取通知详情成功");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "通知不存在");
                return ResponseEntity.status(404).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取通知详情失败：" + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 根据发布者ID获取通知列表
     */
    @GetMapping("/publisher/{publisherId}")
    public ResponseEntity<Map<String, Object>> getNoticesByPublisher(@PathVariable String publisherId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Notice> notices = noticeService.getNoticesByPublisherId(publisherId);
            response.put("success", true);
            response.put("data", notices);
            response.put("message", "获取发布者通知列表成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取发布者通知列表失败：" + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 创建新通知
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createNotice(@RequestBody Notice notice) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 基本验证
            if (notice.getTitle() == null || notice.getTitle().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "通知标题不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (notice.getContent() == null || notice.getContent().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "通知内容不能为空");
                return ResponseEntity.badRequest().body(response);
            }

            boolean success = noticeService.publishNotice(notice);
            if (success) {
                response.put("success", true);
                response.put("data", notice);
                response.put("message", "创建通知成功");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "创建通知失败");
                return ResponseEntity.status(500).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "创建通知失败：" + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 更新通知
     */
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateNotice(@PathVariable Integer id, @RequestBody Notice notice) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 检查通知是否存在
            Notice existingNotice = noticeService.getNoticeById(id);
            if (existingNotice == null) {
                response.put("success", false);
                response.put("message", "通知不存在");
                return ResponseEntity.status(404).body(response);
            }

            // 设置ID
            notice.setId(id);
            
            // 基本验证
            if (notice.getTitle() == null || notice.getTitle().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "通知标题不能为空");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (notice.getContent() == null || notice.getContent().trim().isEmpty()) {
                response.put("success", false);
                response.put("message", "通知内容不能为空");
                return ResponseEntity.badRequest().body(response);
            }

            boolean success = noticeService.updateNotice(notice);
            if (success) {
                response.put("success", true);
                response.put("data", notice);
                response.put("message", "更新通知成功");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "更新通知失败");
                return ResponseEntity.status(500).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "更新通知失败：" + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 更新通知状态
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Map<String, Object>> updateNoticeStatus(@PathVariable Integer id, @RequestBody Map<String, Integer> statusData) {
        Map<String, Object> response = new HashMap<>();
        try {
            Integer status = statusData.get("status");
            if (status == null) {
                response.put("success", false);
                response.put("message", "状态不能为空");
                return ResponseEntity.badRequest().body(response);
            }

            // 验证状态值是否有效（0-草稿，1-已发布）
            if (status != 0 && status != 1) {
                response.put("success", false);
                response.put("message", "状态值无效，只能是0（草稿）或1（已发布）");
                return ResponseEntity.badRequest().body(response);
            }

            boolean success = noticeService.updateNoticeStatus(id, status);
            if (success) {
                response.put("success", true);
                response.put("message", "更新通知状态成功");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "更新通知状态失败");
                return ResponseEntity.status(500).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "更新通知状态失败：" + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 获取学生未读通知
     */
    @GetMapping("/unread/{studentId}")
    public ResponseEntity<Map<String, Object>> getUnreadNotices(@PathVariable String studentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            // 获取未读通知ID列表
            List<Integer> unreadNoticeIds = noticeReadRecordService.getUnreadNoticeIds(studentId);
            
            // 根据ID获取完整通知信息
            List<Notice> unreadNotices = noticeService.getNoticesByIds(unreadNoticeIds);
            
            response.put("success", true);
            response.put("data", unreadNotices);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "获取未读通知失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 获取学生未读通知数量
     */
    @GetMapping("/unread-count/{studentId}")
    public ResponseEntity<Map<String, Object>> getUnreadNoticesCount(@PathVariable String studentId) {
        Map<String, Object> response = new HashMap<>();
        try {
            int count = noticeReadRecordService.getUnreadNoticesCount(studentId);
            response.put("success", true);
            response.put("count", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "获取未读通知数量失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 标记通知为已读
     */
    @PostMapping("/read")
    public ResponseEntity<Map<String, Object>> markNoticeAsRead(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Integer noticeId = (Integer) request.get("noticeId");
            String studentId = (String) request.get("studentId");
            
            if (noticeId == null || studentId == null) {
                response.put("success", false);
                response.put("message", "参数不完整");
                return ResponseEntity.badRequest().body(response);
            }
            
            boolean success = noticeReadRecordService.markAsReadWithTime(noticeId, studentId);
            
            if (success) {
                response.put("success", true);
                response.put("message", "通知已标记为已读");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "标记通知为已读失败");
                return ResponseEntity.status(500).body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("success", false);
            response.put("message", "标记通知为已读失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 批量删除通知
     */
    @DeleteMapping("/batch")
    public ResponseEntity<Map<String, Object>> deleteNotices(@RequestBody Map<String, List<Integer>> requestData) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<Integer> ids = requestData.get("ids");
            if (ids == null || ids.isEmpty()) {
                response.put("success", false);
                response.put("message", "请选择要删除的通知");
                return ResponseEntity.badRequest().body(response);
            }

            boolean success = noticeService.deleteNotices(ids);
            if (success) {
                response.put("success", true);
                response.put("message", "批量删除通知成功");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "批量删除通知失败");
                return ResponseEntity.status(500).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "批量删除通知失败：" + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 获取通知统计信息
     */
    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getNoticeStats(@RequestParam(required = false) String publisherId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Map<String, Object> stats = new HashMap<>();
            
            if (publisherId != null) {
                stats.put("totalCount", noticeService.getNoticeCountByPublisher(publisherId));
            }
            
            stats.put("activeCount", noticeService.getNoticeCountByStatus(1)); // 1表示已发布
            stats.put("inactiveCount", noticeService.getNoticeCountByStatus(0)); // 0表示草稿
            
            response.put("success", true);
            response.put("data", stats);
            response.put("message", "获取统计信息成功");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取统计信息失败：" + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}