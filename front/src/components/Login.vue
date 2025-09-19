<template>
  <div class="login-container">
    <div class="login-form">
      <h2 class="login-title">用户登录</h2>
      <form @submit.prevent="handleLogin">
        <div class="form-group">
          <label for="userType">我是：</label>
          <select
            id="userType"
            v-model="loginForm.userType"
            required
          >
            <option value="">请选择身份</option>
            <option value="student">学生</option>
            <option value="teacher">教职工</option>
            <option value="admin">管理员</option>
          </select>
        </div>
        <div class="form-group">
          <label for="username">用户ID</label>
          <input
            type="text"
            id="username"
            v-model="loginForm.username"
            placeholder="请输入用户ID"
            required
          />
        </div>
        <div class="form-group">
          <label for="password">密码</label>
          <input
            type="password"
            id="password"
            v-model="loginForm.password"
            placeholder="请输入密码"
            required
          />
        </div>
        <div class="form-group">
          <button type="submit" class="login-btn" :disabled="loading">
            {{ loading ? '登录中...' : '登录' }}
          </button>
        </div>
      </form>
      <div v-if="errorMessage" class="error-message">
        {{ errorMessage }}
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'LoginPage',
  data() {
    return {
      loginForm: {
        userType: '',
        username: '',
        password: ''
      },
      loading: false,
      errorMessage: ''
    }
  },
  methods: {
    async handleLogin() {
      this.loading = true;
      this.errorMessage = '';
      
      try {
          const response = await axios.post('/api/auth/login', {
            userType: this.loginForm.userType,
            userId: this.loginForm.username,
            password: this.loginForm.password
          });
        
        if (response.data.success) {
          // 保存用户信息到localStorage
          const userInfo = {
            userId: this.loginForm.username,
            userType: this.getUserTypeDisplay(this.loginForm.userType)
          };
          localStorage.setItem('userInfo', JSON.stringify(userInfo));
          localStorage.setItem('token', response.data.token);
          localStorage.setItem('user', JSON.stringify(response.data.user));
          
          alert('登录成功！');
          // 根据用户类型跳转到对应界面
          this.redirectToUserDashboard(this.loginForm.userType);
        } else {
          this.errorMessage = response.data.message;
        }
        
      } catch (error) {
        console.error('登录错误:', error);
        if (error.response && error.response.data) {
          this.errorMessage = error.response.data.message || '登录失败，请检查用户名和密码';
        } else {
          this.errorMessage = '网络错误，请稍后重试';
        }
      } finally {
        this.loading = false;
      }
    },
    getUserTypeDisplay(userType) {
      const typeMap = {
        'student': '学生',
        'teacher': '教职工',
        'admin': '管理员'
      };
      return typeMap[userType] || userType;
    },
    redirectToUserDashboard(userType) {
      const routeMap = {
        'student': '/student',
        'teacher': '/teacher',
        'admin': '/admin'
      };
      const route = routeMap[userType] || '/home';
      this.$router.push(route);
    }
  }
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-form {
  background: white;
  padding: 40px;
  border-radius: 10px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
  width: 100%;
  max-width: 400px;
}

.login-title {
  text-align: center;
  margin-bottom: 30px;
  color: #333;
  font-size: 28px;
  font-weight: 600;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  margin-bottom: 8px;
  color: #555;
  font-weight: 500;
}

.form-group input,
.form-group select {
  width: 100%;
  padding: 12px 16px;
  border: 2px solid #e1e5e9;
  border-radius: 6px;
  font-size: 16px;
  transition: border-color 0.3s ease;
  box-sizing: border-box;
}

.form-group input:focus,
.form-group select:focus {
  outline: none;
  border-color: #667eea;
}

.login-btn {
  width: 100%;
  padding: 14px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  border: none;
  border-radius: 6px;
  font-size: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.login-btn:hover:not(:disabled) {
  transform: translateY(-2px);
}

.login-btn:disabled {
  opacity: 0.7;
  cursor: not-allowed;
  transform: none;
}

.error-message {
  margin-top: 15px;
  padding: 10px;
  background-color: #fee;
  color: #c33;
  border: 1px solid #fcc;
  border-radius: 4px;
  text-align: center;
}
</style>