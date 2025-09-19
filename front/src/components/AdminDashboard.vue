<template>
  <div class="dashboard">
    <!-- 用户信息头部 -->
    <div class="header">
      <h1>管理员控制台</h1>
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
        <h2>欢迎，管理员 {{ userInfo.userId }}！</h2>
        <p>这是管理员专用控制台</p>
      </div>

      <!-- 学生管理模块 -->
      <div v-if="currentView === 'students'" class="student-management">
        <div class="management-header">
          <h3>学生管理</h3>
          <button class="add-btn" @click="showAddModal = true">添加学生</button>
        </div>
        
        <!-- 学生列表 -->
        <div class="student-list">
          <div class="list-header">
            <span>学生ID</span>
            <span>姓名</span>
            <span>邮箱</span>
            <span>手机号</span>
            <span>操作</span>
          </div>
          <div v-for="student in students" :key="student.studentId" class="student-item">
            <span>{{ student.studentId }}</span>
            <span>{{ student.studentname }}</span>
            <span>{{ student.email || '未填写' }}</span>
            <span>{{ student.phone || '未填写' }}</span>
            <div class="actions">
              <button class="edit-btn" @click="editStudent(student)">编辑</button>
              <button class="delete-btn" @click="deleteStudent(student.studentId)">删除</button>
            </div>
          </div>
        </div>
      </div>

      <!-- 教职工管理模块 -->
      <div v-if="currentView === 'staff'" class="staff-management">
        <div class="management-header">
          <h3>教职工管理</h3>
          <button class="add-btn" @click="showAddStaffModal = true">添加教职工</button>
        </div>
        
        <!-- 教职工列表 -->
        <div class="staff-list">
          <div class="list-header">
            <span>教职工ID</span>
            <span>密码</span>
            <span>操作</span>
          </div>
          <div v-for="staff in staffList" :key="staff.staffId" class="staff-item">
            <span>{{ staff.staffId }}</span>
            <span>{{ staff.password ? '******' : '未设置' }}</span>
            <div class="actions">
              <button class="edit-btn" @click="editStaff(staff)">编辑</button>
              <button class="delete-btn" @click="deleteStaff(staff.staffId)">删除</button>
            </div>
          </div>
        </div>
      </div>

      <!-- 默认功能卡片 -->
      <div v-else class="feature-cards">
        <div class="card" @click="currentView = 'students'">
          <h3>学生管理</h3>
          <p>管理系统中的所有学生信息</p>
        </div>
        <div class="card" @click="currentView = 'staff'">
          <h3>教职工管理</h3>
          <p>管理系统中的所有教职工信息</p>
        </div>
        <div class="card">
          <h3>系统设置</h3>
          <p>配置系统参数和功能设置</p>
        </div>
        <div class="card">
          <h3>数据统计</h3>
          <p>查看系统使用情况和数据报表</p>
        </div>
        <div class="card">
          <h3>权限管理</h3>
          <p>管理用户角色和访问权限</p>
        </div>
        <div class="card">
          <h3>日志监控</h3>
          <p>监控系统运行状态和操作日志</p>
        </div>
        <div class="card">
          <h3>备份恢复</h3>
          <p>数据备份和系统恢复功能</p>
        </div>
      </div>

      <!-- 返回按钮 -->
      <div v-if="currentView !== 'dashboard'" class="back-section">
        <button class="back-btn" @click="currentView = 'dashboard'">返回主页</button>
      </div>
    </div>

    <!-- 添加学生模态框 -->
    <div v-if="showAddModal" class="modal-overlay" @click="closeModal">
      <div class="modal" @click.stop>
        <div class="modal-header">
          <h3>添加学生</h3>
          <button class="close-btn" @click="closeModal">&times;</button>
        </div>
        <form @submit.prevent="addStudent" class="student-form">
          <div class="form-group">
            <label>学生ID:</label>
            <input v-model="newStudent.studentId" type="text" placeholder="请输入学生ID" required>
          </div>
          <div class="form-group">
            <label>姓名:</label>
            <input v-model="newStudent.studentname" type="text" placeholder="请输入学生姓名" required>
          </div>
          <div class="form-group">
            <label>密码:</label>
            <input v-model="newStudent.password" type="password" placeholder="请输入密码（默认123456）">
          </div>
          <div class="form-group">
            <label>手机号:</label>
            <input v-model="newStudent.phone" type="text" placeholder="请输入手机号">
          </div>
          <div class="form-group">
            <label>邮箱:</label>
            <input v-model="newStudent.email" type="email" placeholder="请输入邮箱">
          </div>
          <div class="form-group">
            <label>地址:</label>
            <input v-model="newStudent.address" type="text" placeholder="请输入地址">
          </div>
          <div class="form-group">
            <label>年龄:</label>
            <input v-model="newStudent.age" type="number" placeholder="请输入年龄">
          </div>
          <div class="form-actions">
            <button type="button" @click="closeModal" class="cancel-btn">取消</button>
            <button type="submit" class="submit-btn">添加</button>
          </div>
        </form>
      </div>
    </div>

    <!-- 编辑学生模态框 -->
    <div v-if="showEditModal" class="modal-overlay" @click="closeEditModal">
      <div class="modal" @click.stop>
        <div class="modal-header">
          <h3>修改学生信息</h3>
          <button class="close-btn" @click="closeEditModal">&times;</button>
        </div>
        <form @submit.prevent="updateStudent" class="student-form">
          <div class="form-group">
            <label>学生ID:</label>
            <input v-model="editingStudent.studentId" type="text" readonly>
          </div>
          <div class="form-group">
            <label>姓名:</label>
            <input v-model="editingStudent.studentname" type="text" placeholder="请输入学生姓名" required>
          </div>
          <div class="form-group">
            <label>密码:</label>
            <input v-model="editingStudent.password" type="password" placeholder="请输入密码">
          </div>
          <div class="form-group">
            <label>手机号:</label>
            <input v-model="editingStudent.phone" type="text" placeholder="请输入手机号">
          </div>
          <div class="form-group">
            <label>邮箱:</label>
            <input v-model="editingStudent.email" type="email" placeholder="请输入邮箱">
          </div>
          <div class="form-group">
            <label>地址:</label>
            <input v-model="editingStudent.address" type="text" placeholder="请输入地址">
          </div>
          <div class="form-group">
            <label>年龄:</label>
            <input v-model="editingStudent.age" type="number" placeholder="请输入年龄">
          </div>
          <div class="form-actions">
            <button type="button" @click="closeEditModal" class="cancel-btn">取消</button>
            <button type="submit" class="submit-btn">保存</button>
          </div>
        </form>
      </div>
    </div>

    <!-- 添加教职工模态框 -->
    <div v-if="showAddStaffModal" class="modal-overlay" @click="closeStaffModal">
      <div class="modal" @click.stop>
        <div class="modal-header">
          <h3>添加教职工</h3>
          <button class="close-btn" @click="closeStaffModal">&times;</button>
        </div>
        <form @submit.prevent="addStaff" class="staff-form">
          <div class="form-group">
            <label>教职工ID:</label>
            <input v-model="newStaff.staffId" type="text" placeholder="请输入教职工ID" required>
          </div>
          <div class="form-group">
            <label>密码:</label>
            <input v-model="newStaff.password" type="password" placeholder="请输入密码" required>
          </div>
          <div class="form-actions">
            <button type="button" @click="closeStaffModal" class="cancel-btn">取消</button>
            <button type="submit" class="submit-btn">添加</button>
          </div>
        </form>
      </div>
    </div>

    <!-- 编辑教职工模态框 -->
    <div v-if="showEditStaffModal" class="modal-overlay" @click="closeEditStaffModal">
      <div class="modal" @click.stop>
        <div class="modal-header">
          <h3>修改教职工信息</h3>
          <button class="close-btn" @click="closeEditStaffModal">&times;</button>
        </div>
        <form @submit.prevent="updateStaff" class="staff-form">
          <div class="form-group">
            <label>教职工ID:</label>
            <input v-model="editingStaff.staffId" type="text" readonly>
          </div>
          <div class="form-group">
            <label>密码:</label>
            <input v-model="editingStaff.password" type="password" placeholder="请输入密码" required>
          </div>
          <div class="form-actions">
            <button type="button" @click="closeEditStaffModal" class="cancel-btn">取消</button>
            <button type="submit" class="submit-btn">保存</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'AdminDashboard',
  data() {
    return {
      showLogout: false,
      currentView: 'dashboard', // 'dashboard', 'students', 或 'staff'
      showAddModal: false,
      showEditModal: false,
      showAddStaffModal: false,
      showEditStaffModal: false,
      userInfo: {
        userId: '',
        userType: '管理员'
      },
      students: [],
      staffList: [],
      newStudent: {
        studentId: '',
        studentname: '',
        password: '',
        phone: '',
        email: '',
        address: '',
        age: null
      },
      editingStudent: {
        studentId: '',
        studentname: '',
        password: '',
        phone: '',
        email: '',
        address: '',
        age: null
      },
      newStaff: {
        staffId: '',
        password: ''
      },
      editingStaff: {
        staffId: '',
        password: ''
      }
    }
  },
  mounted() {
    // 从localStorage获取用户信息
    const userInfo = localStorage.getItem('userInfo');
    if (userInfo) {
      this.userInfo = JSON.parse(userInfo);
    }
    // 加载学生列表
    this.loadStudents();
    // 加载教职工列表
    this.loadStaff();
  },
  methods: {
    logout() {
      // 清除用户信息
      localStorage.removeItem('userInfo');
      // 跳转到登录页面
      this.$router.push('/');
    },
    async loadStudents() {
      try {
        const response = await axios.get('/api/students');
        if (response.data.success) {
          this.students = response.data.data;
        } else {
          alert('加载学生列表失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('加载学生列表失败:', error);
        alert('加载学生列表失败，请检查网络连接');
      }
    },
    async addStudent() {
      try {
        const response = await axios.post('/api/students', this.newStudent);
        if (response.data.success) {
          alert('添加学生成功');
          this.closeModal();
          this.loadStudents(); // 重新加载学生列表
        } else {
          alert('添加学生失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('添加学生失败:', error);
        alert('添加学生失败，请检查网络连接');
      }
    },
    editStudent(student) {
      this.editingStudent = { ...student };
      this.showEditModal = true;
    },
    async updateStudent() {
      try {
        const response = await axios.put(`/api/students/${this.editingStudent.studentId}`, this.editingStudent);
        if (response.data.success) {
          alert('更新学生信息成功');
          this.closeEditModal();
          this.loadStudents(); // 重新加载学生列表
        } else {
          alert('更新学生信息失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('更新学生信息失败:', error);
        alert('更新学生信息失败，请检查网络连接');
      }
    },
    async deleteStudent(studentId) {
      if (!confirm('确定要删除这个学生吗？')) {
        return;
      }
      
      try {
        const response = await axios.delete(`/api/students/${studentId}`);
        if (response.data.success) {
          alert('删除学生成功');
          this.loadStudents(); // 重新加载学生列表
        } else {
          alert('删除学生失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('删除学生失败:', error);
        alert('删除学生失败，请检查网络连接');
      }
    },
    closeModal() {
      this.showAddModal = false;
      this.newStudent = {
        studentId: '',
        studentname: '',
        password: '',
        phone: '',
        email: '',
        address: '',
        age: null
      };
    },
    closeEditModal() {
      this.showEditModal = false;
      this.editingStudent = {
        studentId: '',
        studentname: '',
        password: '',
        phone: '',
        email: '',
        address: '',
        age: null
      };
    },
    
    // 教职工管理相关方法
    async loadStaff() {
      try {
        const response = await axios.get('/api/staff');
        if (response.data.success) {
          this.staffList = response.data.data;
        } else {
          alert('加载教职工列表失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('加载教职工列表失败:', error);
        alert('加载教职工列表失败，请检查网络连接');
      }
    },
    async addStaff() {
      try {
        const response = await axios.post('/api/staff', this.newStaff);
        if (response.data.success) {
          alert('添加教职工成功');
          this.closeStaffModal();
          this.loadStaff(); // 重新加载教职工列表
        } else {
          alert('添加教职工失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('添加教职工失败:', error);
        alert('添加教职工失败，请检查网络连接');
      }
    },
    editStaff(staff) {
      this.editingStaff = { ...staff };
      this.showEditStaffModal = true;
    },
    async updateStaff() {
      try {
        const response = await axios.put(`/api/staff/${this.editingStaff.staffId}`, this.editingStaff);
        if (response.data.success) {
          alert('更新教职工信息成功');
          this.closeEditStaffModal();
          this.loadStaff(); // 重新加载教职工列表
        } else {
          alert('更新教职工信息失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('更新教职工信息失败:', error);
        alert('更新教职工信息失败，请检查网络连接');
      }
    },
    async deleteStaff(staffId) {
      if (!confirm('确定要删除这个教职工吗？')) {
        return;
      }
      
      try {
        const response = await axios.delete(`/api/staff/${staffId}`);
        if (response.data.success) {
          alert('删除教职工成功');
          this.loadStaff(); // 重新加载教职工列表
        } else {
          alert('删除教职工失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('删除教职工失败:', error);
        alert('删除教职工失败，请检查网络连接');
      }
    },
    closeStaffModal() {
      this.showAddStaffModal = false;
      this.newStaff = {
        staffId: '',
        password: ''
      };
    },
    closeEditStaffModal() {
      this.showEditStaffModal = false;
      this.editingStaff = {
        staffId: '',
        password: ''
      };
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
  background-color: #2196F3;
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
  color: #2196F3;
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
  cursor: pointer;
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

/* 学生管理样式 */
.student-management {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  overflow: hidden;
}

/* 教职工管理样式 */
.staff-management {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  overflow: hidden;
}

.management-header {
  background-color: #2196F3;
  color: white;
  padding: 1rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.management-header h3 {
  margin: 0;
}

.add-btn {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.9rem;
}

.add-btn:hover {
  background-color: #45a049;
}

.student-list {
  padding: 0;
}

.staff-list {
  padding: 0;
}

.list-header {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 120px;
  gap: 1rem;
  padding: 1rem 2rem;
  background-color: #f8f9fa;
  font-weight: bold;
  border-bottom: 1px solid #dee2e6;
}

.staff-list .list-header {
  grid-template-columns: 1fr 1fr 120px;
}

.student-item {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr 1fr 120px;
  gap: 1rem;
  padding: 1rem 2rem;
  border-bottom: 1px solid #dee2e6;
  align-items: center;
}

.staff-item {
  display: grid;
  grid-template-columns: 1fr 1fr 120px;
  gap: 1rem;
  padding: 1rem 2rem;
  border-bottom: 1px solid #dee2e6;
  align-items: center;
}

.student-item:hover, .staff-item:hover {
  background-color: #f8f9fa;
}

.actions {
  display: flex;
  gap: 0.5rem;
}

.edit-btn, .delete-btn {
  padding: 0.25rem 0.5rem;
  border: none;
  border-radius: 3px;
  cursor: pointer;
  font-size: 0.8rem;
}

.edit-btn {
  background-color: #FF9800;
  color: white;
}

.edit-btn:hover {
  background-color: #F57C00;
}

.delete-btn {
  background-color: #f44336;
  color: white;
}

.delete-btn:hover {
  background-color: #d32f2f;
}

.back-section {
  margin-top: 2rem;
  text-align: center;
}

.back-btn {
  background-color: #6c757d;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}

.back-btn:hover {
  background-color: #5a6268;
}

/* 模态框样式 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal {
  background-color: white;
  border-radius: 8px;
  width: 90%;
  max-width: 500px;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
}

.modal-header {
  background-color: #2196F3;
  color: white;
  padding: 1rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.modal-header h3 {
  margin: 0;
}

.close-btn {
  background: none;
  border: none;
  color: white;
  font-size: 1.5rem;
  cursor: pointer;
  padding: 0;
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.close-btn:hover {
  background-color: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
}

.student-form, .staff-form {
  padding: 2rem;
}

.form-group {
  margin-bottom: 1rem;
}

.form-group label {
  display: block;
  margin-bottom: 0.5rem;
  font-weight: bold;
  color: #333;
}

.form-group input {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
  box-sizing: border-box;
}

.form-group input:focus {
  outline: none;
  border-color: #2196F3;
  box-shadow: 0 0 0 2px rgba(33, 150, 243, 0.2);
}

.form-actions {
  display: flex;
  gap: 1rem;
  justify-content: flex-end;
  margin-top: 2rem;
}

.cancel-btn, .submit-btn {
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
}

.cancel-btn {
  background-color: #6c757d;
  color: white;
}

.cancel-btn:hover {
  background-color: #5a6268;
}

.submit-btn {
  background-color: #2196F3;
  color: white;
}

.submit-btn:hover {
  background-color: #1976D2;
}
</style>