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

    <!-- 主要内容区域 -->
    <div class="content">
      <div class="welcome-section">
        <h2>欢迎，{{ userInfo.userId }}老师！</h2>
        <p>这是教职工专用工作台</p>
      </div>

      <div class="feature-cards">
        <div class="card">
          <h3>课程管理</h3>
          <p>管理您负责的课程和教学计划</p>
        </div>
        <div class="card">
          <h3>学生管理</h3>
          <p>查看和管理您的学生信息</p>
        </div>
        <div class="card">
          <h3>成绩录入</h3>
          <p>录入和管理学生考试成绩</p>
        </div>
        <div class="card">
          <h3>教学资源</h3>
          <p>上传和管理教学材料资源</p>
        </div>
        <div class="card">
          <h3>考勤管理</h3>
          <p>记录和查看学生出勤情况</p>
        </div>
        <div class="card">
          <h3>个人中心</h3>
          <p>管理个人信息和工作安排</p>
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

.content {
  padding: 2rem;
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

.feature-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 1.5rem;
}

.card {
  background-color: white;
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  transition: transform 0.2s;
}

.card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0,0,0,0.15);
}

.card h3 {
  color: #333;
  margin-bottom: 0.5rem;
}

.card p {
  color: #666;
  margin: 0;
}
</style>