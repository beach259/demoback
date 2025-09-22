<template>
  <div class="dashboard">
    <!-- 用户信息头部 -->
    <div class="header">
      <h1>教职工工作台</h1>
      <div class="user-info" @mouseenter="showLogout = true" @mouseleave="hideLogoutWithDelay">
        <span class="user-tag">{{ userInfo.userId }} ({{ userInfo.userType }})</span>
        <div v-if="showLogout" class="logout-btn" @click="logout" @mouseenter="clearHideTimeout" @mouseleave="hideLogoutWithDelay">
          退出登录
        </div>
      </div>
    </div>

    <!-- 主要布局区域 -->
    <div class="main-layout">
      <!-- 左侧标签栏 -->
      <div class="sidebar">
        <div class="tab-list">
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'dashboard' }"
            @click="activeTab = 'dashboard'"
          >
            <i class="icon">🏠</i>
            <span>首页</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'courses' }"
            @click="activeTab = 'courses'"
          >
            <i class="icon">📚</i>
            <span>课程管理</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'students' }"
            @click="activeTab = 'students'"
          >
            <i class="icon">👥</i>
            <span>学生管理</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'grades' }"
            @click="activeTab = 'grades'"
          >
            <i class="icon">📊</i>
            <span>成绩录入</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'resources' }"
            @click="activeTab = 'resources'"
          >
            <i class="icon">📁</i>
            <span>教学资源</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'attendance' }"
            @click="activeTab = 'attendance'"
          >
            <i class="icon">✅</i>
            <span>考勤管理</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'profile' }"
            @click="activeTab = 'profile'"
          >
            <i class="icon">👤</i>
            <span>个人中心</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'schedule' }"
            @click="activeTab = 'schedule'"
          >
            <i class="icon">📅</i>
            <span>课程表</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'notice' }"
            @click="activeTab = 'notice'"
          >
            <i class="icon">📢</i>
            <span>通知管理</span>
          </div>
        </div>
      </div>

      <!-- 右侧内容区域 -->
      <div class="content-area">
        <!-- 首页内容 -->
        <div v-if="activeTab === 'dashboard'" class="tab-content">
          <div class="welcome-section">
            <h2>欢迎，{{ userInfo.userId }}老师！</h2>
            <p>这是教职工专用工作台</p>
          </div>

          <!-- 快捷功能卡片 -->
          <div class="quick-actions">
            <h3>快捷功能</h3>
            <div class="feature-cards">
              <div class="card" @click="activeTab = 'courses'">
                <h4>课程管理</h4>
                <p>管理您负责的课程和教学计划</p>
              </div>
              <div class="card" @click="activeTab = 'students'">
                <h4>学生管理</h4>
                <p>查看和管理您的学生信息</p>
              </div>
              <div class="card" @click="activeTab = 'grades'">
                <h4>成绩录入</h4>
                <p>录入和管理学生考试成绩</p>
              </div>
              <div class="card" @click="activeTab = 'resources'">
                <h4>教学资源</h4>
                <p>上传和管理教学材料资源</p>
              </div>
            </div>
          </div>
        </div>

        <!-- 课程管理页面 -->
        <div v-if="activeTab === 'courses'" class="tab-content">
          <div class="page-header">
            <h2>课程管理</h2>
            <button class="add-btn">添加课程</button>
          </div>
          <div class="placeholder-content">
            <p>课程管理功能正在开发中...</p>
          </div>
        </div>

        <!-- 学生管理页面 -->
        <div v-if="activeTab === 'students'" class="tab-content">
          <div class="page-header">
            <h2>学生管理</h2>
          </div>
          <div class="placeholder-content">
            <p>学生管理功能正在开发中...</p>
          </div>
        </div>

        <!-- 成绩录入页面 -->
        <div v-if="activeTab === 'grades'" class="tab-content">
          <div class="page-header">
            <h2>成绩录入</h2>
          </div>
          <div class="placeholder-content">
            <p>成绩录入功能正在开发中...</p>
          </div>
        </div>

        <!-- 教学资源页面 -->
        <div v-if="activeTab === 'resources'" class="tab-content">
          <div class="page-header">
            <h2>教学资源</h2>
            <button class="add-btn">上传资源</button>
          </div>
          <div class="placeholder-content">
            <p>教学资源管理功能正在开发中...</p>
          </div>
        </div>

        <!-- 考勤管理页面 -->
        <div v-if="activeTab === 'attendance'" class="tab-content">
          <div class="page-header">
            <h2>考勤管理</h2>
          </div>
          <div class="placeholder-content">
            <p>考勤管理功能正在开发中...</p>
          </div>
        </div>

        <!-- 个人中心页面 -->
        <div v-if="activeTab === 'profile'" class="tab-content">
          <div class="page-header">
            <h2>个人中心</h2>
          </div>
          <div class="profile-content">
            <div class="profile-section">
              <h3>个人信息</h3>
              <div class="info-item">
                <label>工号：</label>
                <span>{{ userInfo.userId }}</span>
              </div>
              <div class="info-item">
                <label>职位：</label>
                <span>{{ userInfo.userType }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 课程表页面 -->
        <div v-if="activeTab === 'schedule'" class="tab-content">
          <div class="page-header">
            <h2>课程表</h2>
          </div>
          <div class="placeholder-content">
            <p>课程表功能正在开发中...</p>
          </div>
        </div>

        <!-- 通知管理页面 -->
        <div v-if="activeTab === 'notice'" class="tab-content">
          <div class="page-header">
            <h2>通知管理</h2>
            <button class="add-btn" @click="showNoticeModal = true">发布通知</button>
          </div>
          
          <!-- 通知列表 -->
          <div class="notice-list">
            <div class="notice-filters">
              <select v-model="filterType" @change="filterNotices">
                <option value="">全部类型</option>
                <option value="general">一般通知</option>
                <option value="dormitory">宿舍通知</option>
                <option value="academic">学术通知</option>
                <option value="activity">活动通知</option>
                <option value="emergency">紧急通知</option>
              </select>
              <select v-model="filterPriority" @change="filterNotices">
                <option value="">全部优先级</option>
                <option value="low">低</option>
                <option value="normal">普通</option>
                <option value="high">高</option>
                <option value="urgent">紧急</option>
              </select>
            </div>

            <div class="notice-table">
              <table>
                <thead>
                  <tr>
                    <th>标题</th>
                    <th>类型</th>
                    <th>优先级</th>
                    <th>目标受众</th>
                    <th>发布时间</th>
                    <th>查看次数</th>
                    <th>操作</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="notice in filteredNotices" :key="notice.id">
                    <td>{{ notice.title }}</td>
                    <td>
                      <span class="notice-type" :class="notice.type">
                        {{ getNoticeTypeText(notice.type) }}
                      </span>
                    </td>
                    <td>
                      <span class="priority" :class="notice.priority">
                        {{ getPriorityText(notice.priority) }}
                      </span>
                    </td>
                    <td>{{ getAudienceText(notice.targetAudience) }}</td>
                    <td>{{ formatDate(notice.publishTime) }}</td>
                    <td>{{ notice.viewCount }}</td>
                    <td>
                      <button class="btn-small" @click="viewNotice(notice)">查看</button>
                      <button class="btn-small edit" @click="editNotice(notice)">编辑</button>
                      <button class="btn-small delete" @click="deleteNotice(notice.id)">删除</button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 发布/编辑通知模态框 -->
    <div v-if="showNoticeModal" class="modal-overlay" @click="closeNoticeModal">
      <div class="modal-content" @click.stop>
        <div class="modal-header">
          <h3>{{ isEditMode ? '编辑通知' : '发布通知' }}</h3>
          <button class="close-btn" @click="closeNoticeModal">×</button>
        </div>
        <div class="modal-body">
          <form @submit.prevent="submitNotice">
            <div class="form-group">
              <label>通知标题 *</label>
              <input 
                type="text" 
                v-model="noticeForm.title" 
                required 
                maxlength="200"
                placeholder="请输入通知标题"
              >
            </div>
            
            <div class="form-row">
              <div class="form-group">
                <label>通知类型 *</label>
                <select v-model="noticeForm.type" required>
                  <option value="general">一般通知</option>
                  <option value="dormitory">宿舍通知</option>
                  <option value="academic">学术通知</option>
                  <option value="activity">活动通知</option>
                  <option value="emergency">紧急通知</option>
                </select>
              </div>
              
              <div class="form-group">
                <label>优先级 *</label>
                <select v-model="noticeForm.priority" required>
                  <option value="low">低</option>
                  <option value="normal">普通</option>
                  <option value="high">高</option>
                  <option value="urgent">紧急</option>
                </select>
              </div>
            </div>

            <div class="form-row">
              <div class="form-group">
                <label>目标受众 *</label>
                <select v-model="noticeForm.targetAudience" required @change="onTargetAudienceChange">
                  <option value="all">全体</option>
                  <option value="students">学生</option>
                  <option value="teachers">教师</option>
                </select>
              </div>
              
              <div class="form-group">
                <label>过期时间</label>
                <input 
                  type="datetime-local" 
                  v-model="noticeForm.expireTime"
                  placeholder="选择过期时间（可选）"
                >
              </div>
            </div>

            <!-- 学生选择区域 -->
            <div v-if="noticeForm.targetAudience === 'students'" class="form-group student-selection">
              <label>选择学生 *</label>
              <div class="student-selection-controls">
                <button type="button" class="btn-select-all" @click="selectAllStudents">全选</button>
                <button type="button" class="btn-clear-all" @click="clearAllStudents">清空</button>
                <span class="selected-count">已选择: {{ selectedStudents.length }} 人</span>
              </div>
              <div class="student-list" v-if="students.length > 0">
                <div 
                  v-for="student in students" 
                  :key="student.studentId" 
                  class="student-item"
                >
                  <label class="student-checkbox">
                    <input 
                      type="checkbox" 
                      :value="student.studentId"
                      v-model="selectedStudents"
                    >
                    <span class="checkmark"></span>
                    <div class="student-info">
                      <span class="student-name">{{ student.name }}</span>
                      <span class="student-id">({{ student.studentId }})</span>
                      <span class="student-class">{{ student.className || '未分班' }}</span>
                    </div>
                  </label>
                </div>
              </div>
              <div v-else class="no-students">
                <p>暂无学生数据</p>
                <button type="button" class="btn-refresh" @click="loadStudents">刷新学生列表</button>
              </div>
            </div>
            <div class="form-group">
              <label>通知内容 *</label>
              <textarea 
                v-model="noticeForm.content" 
                required 
                rows="8"
                placeholder="请输入通知内容"
              ></textarea>
            </div>

            <div class="form-group">
              <label>附件链接</label>
              <input 
                type="url" 
                v-model="noticeForm.attachmentUrl"
                placeholder="请输入附件链接（可选）"
              >
            </div>

            <div class="form-actions">
              <button type="button" class="btn-cancel" @click="closeNoticeModal">取消</button>
              <button type="submit" class="btn-submit">{{ isEditMode ? '更新通知' : '发布通知' }}</button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- 查看通知模态框 -->
    <div v-if="showViewModal" class="modal-overlay" @click="closeViewModal">
      <div class="modal-content view-modal" @click.stop>
        <div class="modal-header">
          <h3>{{ viewingNotice.title }}</h3>
          <button class="close-btn" @click="closeViewModal">×</button>
        </div>
        <div class="modal-body">
          <div class="notice-meta">
            <div class="meta-item">
              <strong>类型：</strong>
              <span class="notice-type" :class="viewingNotice.type">
                {{ getNoticeTypeText(viewingNotice.type) }}
              </span>
            </div>
            <div class="meta-item">
              <strong>优先级：</strong>
              <span class="priority" :class="viewingNotice.priority">
                {{ getPriorityText(viewingNotice.priority) }}
              </span>
            </div>
            <div class="meta-item">
              <strong>目标受众：</strong>{{ getAudienceText(viewingNotice.targetAudience) }}
            </div>
            <div class="meta-item">
              <strong>发布时间：</strong>{{ formatDate(viewingNotice.publishTime) }}
            </div>
            <div class="meta-item" v-if="viewingNotice.expireTime">
              <strong>过期时间：</strong>{{ formatDate(viewingNotice.expireTime) }}
            </div>
            <div class="meta-item">
              <strong>查看次数：</strong>{{ viewingNotice.viewCount }}
            </div>
          </div>
          
          <div class="notice-content">
            <h4>通知内容：</h4>
            <div class="content-text">{{ viewingNotice.content }}</div>
          </div>
          
          <div v-if="viewingNotice.attachmentUrl" class="notice-attachment">
            <h4>附件：</h4>
            <a :href="viewingNotice.attachmentUrl" target="_blank" class="attachment-link">
              查看附件
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'TeacherDashboard',
  data() {
    return {
      showLogout: false,
      hideTimeout: null, // 添加延迟隐藏的定时器
      activeTab: 'dashboard', // 当前激活的标签页
      userInfo: {
        userId: '',
        userType: '教职工'
      },
      // 通知管理相关数据
      notices: [],
      filteredNotices: [],
      filterType: '',
      filterPriority: '',
      showNoticeModal: false,
      showViewModal: false,
      isEditMode: false,
      viewingNotice: {},
      noticeForm: {
        title: '',
        content: '',
        notice_type: 'general',
        priority: 'normal',
        target_audience: 'students',
        expire_time: '',
        attachment_url: ''
      },
      // 学生选择相关数据
      students: [],
      selectedStudents: []
    }
  },
  watch: {
    activeTab(newTab) {
      console.log('activeTab changed to:', newTab);
      if (newTab === 'notice') {
        console.log('切换到通知管理页面，重新加载通知列表');
        this.loadNotices();
      }
    }
  },
  mounted() {
    console.log('TeacherDashboard mounted - 开始初始化');
    // 从localStorage获取用户信息
    const userInfo = localStorage.getItem('userInfo');
    if (userInfo) {
      this.userInfo = JSON.parse(userInfo);
      console.log('TeacherDashboard mounted - 用户信息已加载:', this.userInfo);
    } else {
      console.log('TeacherDashboard mounted - 未找到用户信息');
    }
    // 加载通知列表
    console.log('TeacherDashboard mounted - 准备调用loadNotices');
    this.loadNotices();
  },
  methods: {
    logout() {
      // 清除用户信息
      localStorage.removeItem('userInfo');
      // 跳转到登录页面
      this.$router.push('/');
    },
    
    // 延迟隐藏退出登录按钮
    hideLogoutWithDelay() {
      this.hideTimeout = setTimeout(() => {
        this.showLogout = false;
      }, 300); // 300ms延迟
    },
    
    // 清除隐藏定时器
    clearHideTimeout() {
      if (this.hideTimeout) {
        clearTimeout(this.hideTimeout);
        this.hideTimeout = null;
      }
    },
    
    // 通知管理方法
    async loadNotices() {
      console.log('loadNotices方法开始执行');
      try {
        // 获取当前用户信息
        const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}');
        const user = JSON.parse(localStorage.getItem('user') || '{}');
        
        // 调试输出
        console.log('调试信息 - userInfo:', userInfo);
        console.log('调试信息 - user:', user);
        
        // 使用search API按publisherId筛选通知，教师使用staffId
        const publisherId = user.staffId || userInfo.userId;
        console.log('调试信息 - publisherId:', publisherId);
        
        // 调用后端API获取通知列表
        const response = await fetch('/api/notices/search?' + new URLSearchParams({
          publisherId: publisherId
        }));
        
        console.log('调试信息 - API响应状态:', response.status);
        
        if (response.ok) {
          const result = await response.json();
          console.log('调试信息 - API响应数据:', result);
          if (result.success) {
            this.notices = result.data || [];
            this.filteredNotices = [...this.notices];
          } else {
            console.error('获取通知列表失败:', result.message);
            // 如果API调用失败，使用模拟数据
            this.loadMockNotices();
          }
        } else {
          console.error('API调用失败:', response.status);
          // 如果API调用失败，使用模拟数据
          this.loadMockNotices();
        }
      } catch (error) {
        console.error('加载通知列表出错:', error);
        // 如果出现错误，使用模拟数据
        this.loadMockNotices();
      }
    },

    loadMockNotices() {
            // 模拟数据作为后备方案
            this.notices = [
                {
                    id: 1,
                    title: '宿舍卫生检查通知',
                    content: '本周五将进行宿舍卫生检查，请各位同学做好准备。',
                    type: 'dormitory',
                    priority: 'normal',
                    publisherName: '张老师',
                    publishTime: '2024-01-15 10:00:00',
                    effectiveTime: '2024-01-15 10:00:00',
                    expiryTime: '2024-01-20 23:59:59',
                    status: 'active'
                },
                {
                    id: 2,
                    title: '期末考试安排',
                    content: '期末考试将于下周开始，请同学们合理安排复习时间。',
                    type: 'academic',
                    priority: 'high',
                    publisherName: '李老师',
                    publishTime: '2024-01-14 14:30:00',
                    effectiveTime: '2024-01-14 14:30:00',
                    expiryTime: '2024-01-25 23:59:59',
                    status: 'active'
                }
            ];
            this.filteredNotices = [...this.notices];
        },

    filterNotices() {
      this.filteredNotices = this.notices.filter(notice => {
        const typeMatch = !this.filterType || notice.type === this.filterType;
        const priorityMatch = !this.filterPriority || notice.priority === this.filterPriority;
        return typeMatch && priorityMatch;
      });
    },

    viewNotice(notice) {
      this.viewingNotice = notice;
      this.showViewModal = true;
    },

    editNotice(notice) {
      this.noticeForm = { ...notice };
      this.isEditMode = true;
      this.showNoticeModal = true;
    },

    async deleteNotice(noticeId) {
            if (!confirm('确定要删除这条通知吗？')) {
                return;
            }
            
            try {
                // 调用后端API删除通知
                const response = await fetch(`/api/notices/${noticeId}`, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                });
                
                if (response.ok) {
                    const result = await response.json();
                    if (result.success) {
                        // 从本地数组中删除
                        this.notices = this.notices.filter(notice => notice.id !== noticeId);
                        this.filterNotices();
                        alert('通知删除成功');
                    } else {
                        alert('删除失败：' + result.message);
                    }
                } else {
                    alert('删除失败，请稍后重试');
                }
            } catch (error) {
                console.error('删除通知失败:', error);
                alert('删除通知失败，请稍后重试');
            }
        },

    async submitNotice() {
      try {
        // 获取用户信息
        const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}');
        const user = JSON.parse(localStorage.getItem('user') || '{}');
        
        const formData = {
          ...this.noticeForm,
          publisherId: user.staffId || userInfo.userId,
          publisherName: user.staffId || userInfo.userId // 这里应该是真实姓名
        };

        if (this.isEditMode) {
          // 更新通知 - 根据是否选择了学生使用不同的API
          let apiUrl = `/api/notices/${formData.id}`;
          let requestData = formData;
          
          if (this.noticeForm.targetAudience === 'students' && this.selectedStudents.length > 0) {
            // 使用新的更新发布API，包含选中的学生
            apiUrl = `/api/notices/${formData.id}/publish`;
            requestData = {
              notice: formData,
              selectedStudents: this.selectedStudents
            };
          }
          
          const response = await fetch(apiUrl, {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
          });
          
          if (response.ok) {
            const result = await response.json();
            if (result.success) {
              // 更新本地数据
              const index = this.notices.findIndex(n => n.id === formData.id);
              if (index !== -1) {
                this.notices[index] = { ...this.notices[index], ...formData };
              }
              alert('通知更新成功');
            } else {
              alert('更新失败：' + result.message);
              return;
            }
          } else {
            alert('更新失败，请稍后重试');
            return;
          }
        } else {
          // 创建新通知 - 根据是否选择了学生使用不同的API
          let apiUrl = '/api/notices';
          let requestData = formData;
          
          if (this.noticeForm.target_audience === 'students' && this.selectedStudents.length > 0) {
            // 使用新的发布API，包含选中的学生
            apiUrl = '/api/notices/publish';
            requestData = {
              notice: formData,
              selectedStudents: this.selectedStudents
            };
          }
          
          const response = await fetch(apiUrl, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
          });
          
          if (response.ok) {
            const result = await response.json();
            if (result.success) {
              // 重新加载通知列表
              await this.loadNotices();
              alert('通知发布成功');
            } else {
              alert('发布失败：' + result.message);
              return;
            }
          } else {
            alert('发布失败，请稍后重试');
            return;
          }
        }

        this.closeNoticeModal();
        this.filterNotices();
      } catch (error) {
        console.error('提交通知失败:', error);
        alert('操作失败，请稍后重试');
      }
    },

    closeNoticeModal() {
      this.showNoticeModal = false;
      this.isEditMode = false;
      this.noticeForm = {
        title: '',
        content: '',
        notice_type: 'general',
        priority: 'normal',
        target_audience: 'students',
        expire_time: '',
        attachment_url: ''
      };
      // 清空学生选择
      this.selectedStudents = [];
    },

    resetNoticeForm() {
      this.noticeForm = {
        id: null,
        title: '',
        content: '',
        type: 'general',
        priority: 'medium',
        targetAudience: 'all',
        expireTime: '',
        attachmentUrl: ''
      };
      this.isEditMode = false;
    },

    closeViewModal() {
      this.showViewModal = false;
      this.viewingNotice = {};
    },

    getNoticeTypeText(type) {
      const typeMap = {
        general: '一般通知',
        dormitory: '宿舍通知',
        academic: '学术通知',
        activity: '活动通知',
        emergency: '紧急通知'
      };
      return typeMap[type] || type;
    },

    getPriorityText(priority) {
      const priorityMap = {
        low: '低',
        normal: '普通',
        high: '高',
        urgent: '紧急'
      };
      return priorityMap[priority] || priority;
    },

    getAudienceText(audience) {
      const audienceMap = {
        students: '学生',
        staff: '教职工',
        all: '全部'
      };
      return audienceMap[audience] || audience;
    },

    formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      return date.toLocaleString('zh-CN');
    },

    // 学生选择相关方法
    async loadStudents() {
      try {
        const response = await fetch('/api/students');
        if (response.ok) {
          const result = await response.json();
          if (result.success) {
            this.students = result.data || [];
          } else {
            console.error('获取学生列表失败:', result.message);
            alert('获取学生列表失败: ' + result.message);
          }
        } else {
          console.error('获取学生列表失败:', response.status);
          alert('获取学生列表失败，请稍后重试');
        }
      } catch (error) {
        console.error('获取学生列表异常:', error);
        alert('获取学生列表失败，请稍后重试');
      }
    },

    onTargetAudienceChange() {
      // 当目标受众改变时，如果选择了学生，则加载学生列表
      if (this.noticeForm.targetAudience === 'students') {
        this.loadStudents();
        this.selectedStudents = []; // 清空之前的选择
      }
    },

    selectAllStudents() {
      this.selectedStudents = this.students.map(student => student.studentId);
    },

    clearAllStudents() {
      this.selectedStudents = [];
    }
  }
}
</script>

<style scoped>
.dashboard {
  min-height: 100vh;
  background-color: #f5f5f5;
}

.header {
  background-color: #FF9800;
  color: white;
  padding: 1rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.header h1 {
  margin: 0;
  font-size: 1.5rem;
}

.user-info {
  position: relative;
  cursor: pointer;
}

.user-tag {
  background-color: rgba(255,255,255,0.2);
  padding: 0.5rem 1rem;
  border-radius: 20px;
  font-size: 0.9rem;
}

.logout-btn {
  position: absolute;
  top: 100%;
  right: 0;
  background-color: #f44336;
  color: white;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  margin-top: 0.5rem;
  white-space: nowrap;
  box-shadow: 0 2px 8px rgba(0,0,0,0.2);
  z-index: 1000;
  cursor: pointer;
  user-select: none;
  transition: all 0.2s ease;
}

.logout-btn:hover {
  background-color: #d32f2f;
  transform: translateY(-1px);
}

/* 主布局样式 */
.main-layout {
  display: flex;
  height: calc(100vh - 60px);
}

/* 左侧标签栏样式 */
.sidebar {
  width: 250px;
  background-color: #e65100;
  color: white;
  display: flex;
  flex-direction: column;
}

.tab-list {
  display: flex;
  flex-direction: column;
  padding: 0;
}

.tab-item {
  display: flex;
  align-items: center;
  padding: 15px 20px;
  cursor: pointer;
  transition: all 0.3s ease;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.tab-item:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.tab-item.active {
  background-color: #ff9800;
  border-left: 4px solid #f57c00;
}

.tab-item .icon {
  margin-right: 10px;
  font-size: 16px;
}

.tab-item span {
  font-size: 14px;
  font-weight: 500;
}

/* 右侧内容区域样式 */
.content-area {
  flex: 1;
  background-color: #f8f9fa;
  overflow-y: auto;
}

.tab-content {
  padding: 20px;
  height: 100%;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding: 20px;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.page-header h2 {
  margin: 0;
  color: #2c3e50;
}

.add-btn {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 10px 20px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
}

.add-btn:hover {
  background-color: #45a049;
}

/* 快捷功能卡片样式 */
.quick-actions {
  margin-top: 20px;
}

.quick-actions h3 {
  color: #2c3e50;
  margin-bottom: 15px;
}

.feature-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 20px;
}

.card {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  transition: all 0.3s ease;
  cursor: pointer;
}

.card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.15);
}

.card h4 {
  color: #2c3e50;
  margin-bottom: 10px;
  font-size: 16px;
}

.card p {
  color: #7f8c8d;
  margin: 0;
  font-size: 14px;
}

/* 占位内容样式 */
.placeholder-content {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  padding: 40px;
  text-align: center;
}

.placeholder-content p {
  color: #7f8c8d;
  font-size: 16px;
  margin: 0;
}

/* 通知管理样式 */
.notice-list {
  margin-top: 1rem;
}

.notice-filters {
  display: flex;
  gap: 1rem;
  margin-bottom: 1rem;
  padding: 1rem;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.notice-filters select {
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 0.9rem;
}

.notice-table {
  background-color: white;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.notice-table table {
  width: 100%;
  border-collapse: collapse;
}

.notice-table th,
.notice-table td {
  padding: 1rem;
  text-align: left;
  border-bottom: 1px solid #eee;
}

.notice-table th {
  background-color: #f8f9fa;
  font-weight: 600;
  color: #333;
}

.notice-table tr:hover {
  background-color: #f8f9fa;
}

.notice-type {
  padding: 0.25rem 0.5rem;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: 500;
}

.notice-type.general {
  background-color: #e3f2fd;
  color: #1976d2;
}

.notice-type.dormitory {
  background-color: #f3e5f5;
  color: #7b1fa2;
}

.notice-type.academic {
  background-color: #e8f5e8;
  color: #388e3c;
}

.notice-type.activity {
  background-color: #fff3e0;
  color: #f57c00;
}

.notice-type.emergency {
  background-color: #ffebee;
  color: #d32f2f;
}

.priority {
  padding: 0.25rem 0.5rem;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: 500;
}

.priority.low {
  background-color: #f1f8e9;
  color: #689f38;
}

.priority.normal {
  background-color: #e3f2fd;
  color: #1976d2;
}

.priority.high {
  background-color: #fff3e0;
  color: #f57c00;
}

.priority.urgent {
  background-color: #ffebee;
  color: #d32f2f;
}

.btn-small {
  padding: 0.25rem 0.5rem;
  margin: 0 0.25rem;
  border: none;
  border-radius: 4px;
  font-size: 0.8rem;
  cursor: pointer;
  background-color: #2196f3;
  color: white;
}

.btn-small:hover {
  opacity: 0.8;
}

.btn-small.edit {
  background-color: #ff9800;
}

.btn-small.delete {
  background-color: #f44336;
}

/* 模态框样式 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0,0,0,0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background-color: white;
  border-radius: 8px;
  width: 90%;
  max-width: 600px;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: 0 4px 20px rgba(0,0,0,0.3);
}

.modal-content.view-modal {
  max-width: 700px;
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 1.5rem;
  border-bottom: 1px solid #eee;
  background-color: #f8f9fa;
}

.modal-header h3 {
  margin: 0;
  color: #333;
}

.close-btn {
  background: none;
  border: none;
  font-size: 1.5rem;
  cursor: pointer;
  color: #666;
  padding: 0;
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.close-btn:hover {
  color: #333;
}

.modal-body {
  padding: 1.5rem;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: 500;
  color: #333;
}

.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 0.9rem;
  box-sizing: border-box;
}

.form-group textarea {
  resize: vertical;
  min-height: 120px;
}

.form-row {
  display: flex;
  gap: 1rem;
}

.form-row .form-group {
  flex: 1;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-top: 2rem;
  padding-top: 1rem;
  border-top: 1px solid #eee;
}

.btn-cancel {
  padding: 0.75rem 1.5rem;
  border: 1px solid #ddd;
  background-color: white;
  color: #666;
  border-radius: 4px;
  cursor: pointer;
}

.btn-cancel:hover {
  background-color: #f8f9fa;
}

.btn-submit {
  padding: 0.75rem 1.5rem;
  border: none;
  background-color: #2196f3;
  color: white;
  border-radius: 4px;
  cursor: pointer;
}

.btn-submit:hover {
  background-color: #1976d2;
}

/* 查看通知模态框样式 */
.notice-meta {
  margin-bottom: 1.5rem;
}

.meta-item {
  display: flex;
  align-items: center;
  margin-bottom: 0.5rem;
  gap: 0.5rem;
}

.meta-item strong {
  min-width: 80px;
  color: #333;
}

.notice-content {
  margin-bottom: 1.5rem;
}

.notice-content h4 {
  margin-bottom: 0.5rem;
  color: #333;
}

.content-text {
  background-color: #f8f9fa;
  padding: 1rem;
  border-radius: 4px;
  line-height: 1.6;
  white-space: pre-wrap;
}

.notice-attachment h4 {
  margin-bottom: 0.5rem;
  color: #333;
}

.attachment-link {
  display: inline-block;
  padding: 0.5rem 1rem;
  background-color: #2196f3;
  color: white;
  text-decoration: none;
  border-radius: 4px;
}

.attachment-link:hover {
  background-color: #1976d2;
}

/* 个人中心样式 */
.profile-content {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  padding: 20px;
}

.profile-section {
  margin-bottom: 30px;
}

.profile-section h3 {
  color: #2c3e50;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 2px solid #ecf0f1;
}

.info-item {
  display: flex;
  padding: 10px 0;
  border-bottom: 1px solid #ecf0f1;
}

.info-item label {
  font-weight: 500;
  color: #2c3e50;
  width: 100px;
}

.info-item span {
  color: #7f8c8d;
}

.welcome-section {
  background-color: white;
  padding: 2rem;
  border-radius: 8px;
  margin-bottom: 2rem;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.welcome-section h2 {
  color: #FF9800;
  margin-bottom: 1rem;
}

/* 学生选择样式 */
.student-selection {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 1rem;
  background-color: #f9f9f9;
}

.student-selection-controls {
  display: flex;
  align-items: center;
  gap: 1rem;
  margin-bottom: 1rem;
  padding-bottom: 0.5rem;
  border-bottom: 1px solid #ddd;
}

.btn-select-all, .btn-clear-all, .btn-refresh {
  padding: 0.25rem 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: white;
  cursor: pointer;
  font-size: 0.875rem;
}

.btn-select-all:hover, .btn-clear-all:hover, .btn-refresh:hover {
  background-color: #f0f0f0;
}

.selected-count {
  font-size: 0.875rem;
  color: #666;
  margin-left: auto;
}

.student-list {
  max-height: 300px;
  overflow-y: auto;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: white;
}

.student-item {
  border-bottom: 1px solid #eee;
}

.student-item:last-child {
  border-bottom: none;
}

.student-checkbox {
  display: flex;
  align-items: center;
  padding: 0.75rem;
  cursor: pointer;
  transition: background-color 0.2s;
}

.student-checkbox:hover {
  background-color: #f5f5f5;
}

.student-checkbox input[type="checkbox"] {
  margin-right: 0.75rem;
  transform: scale(1.2);
}

.student-info {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.student-name {
  font-weight: 500;
  color: #333;
}

.student-id {
  color: #666;
  font-size: 0.875rem;
}

.student-class {
  color: #888;
  font-size: 0.875rem;
  background-color: #f0f0f0;
  padding: 0.125rem 0.5rem;
  border-radius: 12px;
}

.no-students {
  text-align: center;
  padding: 2rem;
  color: #666;
}

.no-students p {
  margin-bottom: 1rem;
}
</style>