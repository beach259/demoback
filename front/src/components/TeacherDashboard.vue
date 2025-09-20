<template>
  <div class="dashboard">
    <!-- 用户信息头部 -->
    <div class="header">
      <h1>教职工工作台</h1>
      <div class="user-info" @mouseenter="showLogout = true" @mouseleave="showLogout = false">
        <span class="user-tag">{{ userInfo.userId }} ({{ userInfo.userType }})</span>
        <div v-if="showLogout" class="logout-btn" @click="logout">
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
      activeTab: 'dashboard', // 当前激活的标签页
      userInfo: {
        userId: '',
        userType: '教职工'
      }
    }
  },
  mounted() {
    // 从localStorage获取用户信息
    const userInfo = localStorage.getItem('userInfo');
    if (userInfo) {
      this.userInfo = JSON.parse(userInfo);
    }
  },
  methods: {
    logout() {
      // 清除用户信息
      localStorage.removeItem('userInfo');
      // 跳转到登录页面
      this.$router.push('/');
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
}

.logout-btn:hover {
  background-color: #d32f2f;
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
</style>