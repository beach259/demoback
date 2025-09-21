<template>
  <div class="dashboard">
    <!-- 用户信息头部 -->
    <div class="header">
      <h1>学生管理系统</h1>
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
            :class="{ 'active': activeTab === 'profile' }"
            @click="activeTab = 'profile'"
          >
            <i class="icon">👤</i>
            <span>个人信息</span>
            <span v-if="!studentInfo.infoCompleted" class="badge">!</span>
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
            :class="{ 'active': activeTab === 'dormitory' }"
            @click="activeTab = 'dormitory'"
          >
            <i class="icon">🏠</i>
            <span>宿舍选择</span>
            <span v-if="!dormitoryInfo.hasAllocation" class="badge">!</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'grades' }"
            @click="activeTab = 'grades'"
          >
            <i class="icon">📊</i>
            <span>成绩查询</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'settings' }"
            @click="activeTab = 'settings'"
          >
            <i class="icon">⚙️</i>
            <span>系统设置</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'help' }"
            @click="activeTab = 'help'"
          >
            <i class="icon">❓</i>
            <span>帮助中心</span>
          </div>
        </div>
      </div>

      <!-- 右侧内容区域 -->
      <div class="content-area">
        <!-- 首页内容 -->
        <div v-if="activeTab === 'dashboard'" class="tab-content">
          <div class="welcome-section">
            <h2>欢迎，{{ userInfo.userId }}同学！</h2>
            <p>这是学生专用界面</p>
          </div>

          <!-- 个人信息完善提醒 -->
          <div v-if="!studentInfo.infoCompleted" class="info-reminder">
            <h3>⚠️ 请完善您的个人信息</h3>
            <p>为了更好地为您提供服务，请填写完整的个人信息。注意：信息只能修改一次，请仔细填写。</p>
            <button class="complete-info-btn" @click="activeTab = 'profile'">立即完善</button>
          </div>

          <!-- 快捷功能卡片 -->
          <div class="quick-actions">
            <h3>快捷功能</h3>
            <div class="feature-cards">
              <div class="card" @click="activeTab = 'courses'">
                <h4>课程管理</h4>
                <p>查看和管理您的课程信息</p>
              </div>
              <div class="card" @click="activeTab = 'grades'">
                <h4>成绩查询</h4>
                <p>查看您的考试成绩和学习进度</p>
              </div>
              <div class="card" @click="activeTab = 'profile'" :class="{ 'disabled': studentInfo.infoCompleted }">
                <h4>个人信息</h4>
                <p v-if="!studentInfo.infoCompleted">完善您的个人资料和设置</p>
                <p v-else>查看您的个人资料（已完善）</p>
              </div>
            </div>
          </div>
        </div>

        <!-- 个人信息页面 -->
        <div v-if="activeTab === 'profile'" class="tab-content">
          <div class="page-header">
            <h2>个人信息管理</h2>
            <button v-if="!studentInfo.infoCompleted" class="complete-info-btn" @click="showInfoModal = true">
              完善信息
            </button>
          </div>

          <!-- 个人信息展示 -->
          <div class="info-display">
            <div class="info-grid">
              <div class="info-item">
                <label>学生ID:</label>
                <span>{{ studentInfo.studentId || userInfo.userId }}</span>
              </div>
              <div class="info-item">
                <label>姓名:</label>
                <span>{{ studentInfo.studentname || '未填写' }}</span>
              </div>
              <div class="info-item">
                <label>手机号:</label>
                <span>{{ studentInfo.phone || '未填写' }}</span>
              </div>
              <div class="info-item">
                <label>邮箱:</label>
                <span>{{ studentInfo.email || '未填写' }}</span>
              </div>
              <div class="info-item">
                <label>地址:</label>
                <span>{{ studentInfo.address || '未填写' }}</span>
              </div>
              <div class="info-item">
                <label>年龄:</label>
                <span>{{ studentInfo.age || '未填写' }}</span>
              </div>
              <div class="info-item">
                <label>性别:</label>
                <span>{{ studentInfo.gender === 'male' ? '男' : studentInfo.gender === 'female' ? '女' : '未填写' }}</span>
              </div>
              <div class="info-item">
                <label>信息状态:</label>
                <span :class="studentInfo.infoCompleted ? 'status-completed' : 'status-incomplete'">
                  {{ studentInfo.infoCompleted ? '已完善' : '未完善' }}
                </span>
              </div>
            </div>
          </div>
        </div>

        <!-- 课程管理页面 -->
        <div v-if="activeTab === 'courses'" class="tab-content">
          <div class="page-header">
            <h2>课程管理</h2>
          </div>
          <div class="placeholder-content">
            <p>课程管理功能正在开发中...</p>
          </div>
        </div>

        <!-- 宿舍选择页面 -->
        <div v-if="activeTab === 'dormitory'" class="tab-content">
          <div class="page-header">
            <h2>宿舍选择</h2>
          </div>
          
          <!-- 当前宿舍信息 -->
          <div v-if="dormitoryInfo.hasAllocation" class="current-dormitory">
            <h3>当前宿舍信息</h3>
            <div class="dormitory-card current">
              <div class="dormitory-info">
                <h4>宿舍楼名称：{{ dormitoryInfo.buildingName }}</h4>
                <p>房间号：{{ dormitoryInfo.roomNumber }}</p>
                <p>床位号：{{ dormitoryInfo.bedNumber || '未分配' }}</p>
                <p>分配状态：{{ getStatusText(dormitoryInfo.status) }}</p>
              </div>
              <div class="dormitory-actions">
                <button v-if="dormitoryInfo.status === 'selected'" 
                        @click="confirmCheckIn" 
                        class="btn btn-primary">
                  确认入住
                </button>
                <button @click="checkOut" 
                        :disabled="dormitoryInfo.status === 'confirmed'"
                        class="btn btn-danger"
                        :class="{ 'btn-disabled': dormitoryInfo.status === 'confirmed' }">
                  退宿
                </button>
              </div>
            </div>
          </div>

          <!-- 宿舍选择 -->
          <div v-else class="dormitory-selection">
            <h3>选择宿舍</h3>
            
            <!-- 宿舍楼选择 -->
            <div class="building-selection">
              <h4>选择宿舍楼</h4>
              <div class="building-grid">
                <div v-for="building in availableBuildings" 
                     :key="building.buildingId"
                     class="building-card"
                     :class="{ 'selected': selectedBuilding?.buildingId === building.buildingId }"
                     @click="selectBuilding(building)">
                  <h5>{{ building.buildingName }}</h5>
                  <p>类型：{{ building.buildingType }}</p>
                  <p>可用房间：{{ building.availableRooms }}间</p>
                </div>
              </div>
            </div>

            <!-- 房间选择 -->
            <div v-if="selectedBuilding" class="room-selection">
              <h4>选择房间</h4>
              <div class="room-grid">
                <div v-for="room in availableRooms" 
                     :key="room.roomId"
                     class="room-card"
                     :class="{ 'selected': selectedRoom?.roomId === room.roomId }"
                     @click="selectRoom(room)">
                  <h5>{{ room.roomNumber }}</h5>
                  <p>类型：{{ room.roomType }}</p>
                  <p>已入住：{{ room.currentOccupancy }}/{{ room.capacity }}人</p>
                  <p>床位费：¥{{ room.bedFee }}/学期</p>
                </div>
              </div>
            </div>

            <!-- 确认选择 -->
            <div v-if="selectedRoom" class="selection-confirm">
              <div class="selection-summary">
                <h4>选择确认</h4>
                <p>宿舍楼：{{ selectedBuilding.buildingName }}</p>
                <p>房间号：{{ selectedRoom.roomNumber }}</p>
                <p>房间类型：{{ selectedRoom.roomType }}</p>
                <p>床位费：¥{{ selectedRoom.bedFee }}/学期</p>
              </div>
              <button @click="confirmSelection" class="btn btn-primary">确认选择</button>
            </div>
          </div>
        </div>

        <!-- 成绩查询页面 -->
        <div v-if="activeTab === 'grades'" class="tab-content">
          <div class="page-header">
            <h2>成绩查询</h2>
          </div>
          <div class="placeholder-content">
            <p>成绩查询功能正在开发中...</p>
          </div>
        </div>

        <!-- 系统设置页面 -->
        <div v-if="activeTab === 'settings'" class="tab-content">
          <div class="page-header">
            <h2>系统设置</h2>
          </div>
          <div class="settings-content">
            <div class="setting-section">
              <h3>账户设置</h3>
              <div class="setting-item">
                <label>修改密码</label>
                <button class="setting-btn">修改</button>
              </div>
              <div class="setting-item">
                <label>绑定邮箱</label>
                <button class="setting-btn">设置</button>
              </div>
            </div>
            <div class="setting-section">
              <h3>通知设置</h3>
              <div class="setting-item">
                <label>成绩通知</label>
                <input type="checkbox" checked>
              </div>
              <div class="setting-item">
                <label>课程提醒</label>
                <input type="checkbox" checked>
              </div>
            </div>
          </div>
        </div>

        <!-- 帮助中心页面 -->
        <div v-if="activeTab === 'help'" class="tab-content">
          <div class="page-header">
            <h2>帮助中心</h2>
          </div>
          <div class="help-content">
            <div class="help-section">
              <h3>常见问题</h3>
              <div class="faq-item">
                <h4>如何完善个人信息？</h4>
                <p>点击左侧"个人信息"标签，然后点击"完善信息"按钮填写相关信息。注意：信息只能修改一次。</p>
              </div>
              <div class="faq-item">
                <h4>如何查看成绩？</h4>
                <p>点击左侧"成绩查询"标签即可查看您的所有考试成绩和学习进度。</p>
              </div>
              <div class="faq-item">
                <h4>忘记密码怎么办？</h4>
                <p>请联系系统管理员或通过"系统设置"中的"修改密码"功能重置密码。</p>
              </div>
            </div>
            <div class="help-section">
              <h3>联系我们</h3>
              <p>如有其他问题，请联系：</p>
              <p>邮箱：support@example.com</p>
              <p>电话：400-123-4567</p>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 个人信息完善模态框 -->
    <div v-if="showInfoModal" class="modal-overlay" @click="closeInfoModal">
      <div class="modal" @click.stop>
        <div class="modal-header">
          <h3>{{ studentInfo.infoCompleted ? '个人信息' : '完善个人信息' }}</h3>
          <button class="close-btn" @click="closeInfoModal">&times;</button>
        </div>
        
        <div v-if="studentInfo.infoCompleted" class="info-view">
          <div class="form-group">
            <label>学生ID:</label>
            <span>{{ studentInfo.studentId }}</span>
          </div>
          <div class="form-group">
            <label>姓名:</label>
            <span>{{ studentInfo.studentname }}</span>
          </div>
          <div class="form-group">
            <label>手机号:</label>
            <span>{{ studentInfo.phone }}</span>
          </div>
          <div class="form-group">
            <label>邮箱:</label>
            <span>{{ studentInfo.email }}</span>
          </div>
          <div class="form-group">
            <label>地址:</label>
            <span>{{ studentInfo.address }}</span>
          </div>
          <div class="form-group">
            <label>年龄:</label>
            <span>{{ studentInfo.age }}</span>
          </div>
          <div class="form-group">
            <label>性别:</label>
            <span>{{ studentInfo.gender === 'male' ? '男' : studentInfo.gender === 'female' ? '女' : '未填写' }}</span>
          </div>
          <p class="info-notice">信息已完善，无法再次修改</p>
        </div>

        <form v-else @submit.prevent="completeInfo" class="info-form">
          <div class="form-group">
            <label>学生ID:</label>
            <input v-model="editingInfo.studentId" type="text" readonly>
          </div>
          <div class="form-group">
            <label>姓名:</label>
            <input v-model="editingInfo.studentname" type="text" readonly>
          </div>
          <div class="form-group">
            <label>手机号: <span class="required">*</span></label>
            <input v-model="editingInfo.phone" type="text" placeholder="请输入手机号" required>
          </div>
          <div class="form-group">
            <label>邮箱: <span class="required">*</span></label>
            <input v-model="editingInfo.email" type="email" placeholder="请输入邮箱" required>
          </div>
          <div class="form-group">
            <label>地址: <span class="required">*</span></label>
            <input v-model="editingInfo.address" type="text" placeholder="请输入地址" required>
          </div>
          <div class="form-group">
            <label>年龄: <span class="required">*</span></label>
            <input v-model="editingInfo.age" type="number" placeholder="请输入年龄" min="1" max="150" required>
          </div>
          <div class="form-group">
            <label>性别: <span class="required">*</span></label>
            <select v-model="editingInfo.gender" required>
              <option value="">请选择性别</option>
              <option value="male">男</option>
              <option value="female">女</option>
            </select>
          </div>
          <div class="warning-notice">
            <p>⚠️ 重要提醒：个人信息只能修改一次，提交后将无法再次更改，请仔细核对后提交！</p>
          </div>
          <div class="form-actions">
            <button type="button" @click="closeInfoModal" class="cancel-btn">取消</button>
            <button type="submit" class="submit-btn">确认提交</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'StudentDashboard',
  data() {
    return {
      showLogout: false,
      showInfoModal: false,
      activeTab: 'dashboard', // 当前激活的标签页
      userInfo: {
        userId: '',
        userType: '学生'
      },
      studentInfo: {
        studentId: '',
        studentname: '',
        phone: '',
        email: '',
        address: '',
        age: null,
        gender: '',
        infoCompleted: false,
        modificationCount: 0
      },
      editingInfo: {
        studentId: '',
        studentname: '',
        phone: '',
        email: '',
        address: '',
        age: null,
        gender: ''
      },
      // 宿舍相关数据
      dormitoryInfo: {
        hasAllocation: false,
        buildingName: '',
        roomNumber: '',
        roomType: '',
        checkInDate: '',
        status: '' // 'selected', 'confirmed'
      },
      availableBuildings: [],
      availableRooms: [],
      selectedBuilding: null,
      selectedRoom: null
    }
  },
  mounted() {
    // 从localStorage获取用户信息
    const userInfo = localStorage.getItem('userInfo');
    if (userInfo) {
      this.userInfo = JSON.parse(userInfo);
      // 加载学生详细信息
      this.loadStudentInfo();
      // 初始加载宿舍信息（不加载可用宿舍楼，等用户点击宿舍选择时再加载）
      this.loadDormitoryInfo();
    }
  },
  methods: {
    setActiveTab(tab) {
      // 如果切换到宿舍选择页面，先检查是否已有分配
      if (tab === 'dormitory') {
        this.checkDormitoryAllocation();
      }
      this.activeTab = tab;
    },
    
    async checkDormitoryAllocation() {
      try {
        const response = await axios.get(`/api/dormitory/student/${this.userInfo.userId}`);
        if (response.data.success && response.data.data) {
          // 已有宿舍分配，更新宿舍信息
          this.dormitoryInfo = {
            hasAllocation: true,
            buildingName: response.data.data.buildingName,
            roomNumber: response.data.data.roomNumber,
            bedNumber: response.data.data.bedNumber,
            roomType: response.data.data.roomType,
            checkInDate: response.data.data.checkInDate,
            status: response.data.data.allocationStatus
          };
        } else {
          // 没有宿舍分配，显示选择界面
          this.dormitoryInfo.hasAllocation = false;
          // 加载可用宿舍楼
          this.loadAvailableBuildings();
        }
      } catch (error) {
        console.error('检查宿舍分配失败:', error);
        // 出错时默认显示选择界面
        this.dormitoryInfo.hasAllocation = false;
        this.loadAvailableBuildings();
      }
    },
    async loadStudentInfo() {
      try {
        const response = await axios.get(`/api/students/${this.userInfo.userId}`);
        if (response.data.success) {
          this.studentInfo = response.data.data;
          // 初始化编辑表单
          this.editingInfo = {
            studentId: this.studentInfo.studentId,
            studentname: this.studentInfo.studentname,
            phone: this.studentInfo.phone || '',
            email: this.studentInfo.email || '',
            address: this.studentInfo.address || '',
            age: this.studentInfo.age || null,
            gender: this.studentInfo.gender || ''
          };
        } else {
          console.error('加载学生信息失败:', response.data.message);
        }
      } catch (error) {
        console.error('加载学生信息失败:', error);
      }
    },
    async completeInfo() {
      try {
        // 确认对话框
        if (!confirm('确认提交个人信息？提交后将无法再次修改！')) {
          return;
        }

        const response = await axios.put(`/api/students/${this.userInfo.userId}/complete-info`, this.editingInfo);
        if (response.data.success) {
          alert('个人信息完善成功！');
          this.closeInfoModal();
          // 重新加载学生信息
          await this.loadStudentInfo();
        } else {
          alert('完善信息失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('完善信息失败:', error);
        alert('完善信息失败，请检查网络连接');
      }
    },
    closeInfoModal() {
      this.showInfoModal = false;
    },
    logout() {
      // 清除用户信息
      localStorage.removeItem('userInfo');
      // 跳转到登录页面
      this.$router.push('/');
    },
    
    // 宿舍相关方法
    async loadDormitoryInfo() {
      try {
        const response = await axios.get(`/api/dormitory/student/${this.userInfo.userId}`);
        if (response.data.success && response.data.data) {
          this.dormitoryInfo = {
            hasAllocation: true,
            buildingName: response.data.data.buildingName,
            roomNumber: response.data.data.roomNumber,
            bedNumber: response.data.data.bedNumber,
            roomType: response.data.data.roomType,
            checkInDate: response.data.data.checkInDate,
            status: response.data.data.allocationStatus
          };
        }
      } catch (error) {
        console.error('加载宿舍信息失败:', error);
      }
    },
    
    async loadAvailableBuildings() {
      try {
        // 获取学生性别信息
        const studentResponse = await axios.get(`/api/students/${this.userInfo.userId}`);
        if (studentResponse.data.success) {
          const gender = studentResponse.data.data.gender || '男'; // 默认男性
          const response = await axios.get(`/api/dormitory/buildings/gender/${gender}`);
          if (response.data.success) {
            this.availableBuildings = response.data.data;
          }
        }
      } catch (error) {
        console.error('加载宿舍楼信息失败:', error);
      }
    },
    
    async selectBuilding(building) {
      this.selectedBuilding = building;
      this.selectedRoom = null;
      this.availableRooms = [];
      
      try {
        const response = await axios.get(`/api/dormitory/rooms/available/${building.buildingId}`);
        if (response.data.success) {
          this.availableRooms = response.data.data;
        }
      } catch (error) {
        console.error('加载房间信息失败:', error);
        alert('加载房间信息失败');
      }
    },
    
    selectRoom(room) {
      this.selectedRoom = room;
    },
    
    async confirmSelection() {
      if (!this.selectedRoom) {
        alert('请选择房间');
        return;
      }
      
      try {
        const response = await axios.post('/api/dormitory/select', {
          studentId: this.userInfo.userId,
          roomId: this.selectedRoom.roomId
        });
        
        if (response.data.success) {
          alert('宿舍选择成功！');
          this.checkDormitoryAllocation(); // 重新检查宿舍分配状态
          this.selectedBuilding = null;
          this.selectedRoom = null;
          this.availableRooms = [];
        } else {
          alert(response.data.message || '选择失败');
        }
      } catch (error) {
        console.error('选择宿舍失败:', error);
        alert('选择宿舍失败，请重试');
      }
    },
    
    async confirmCheckIn() {
      try {
        const response = await axios.post('/api/dormitory/checkin', {
          studentId: this.userInfo.userId
        });
        
        if (response.data.success) {
          alert('确认入住成功！');
          this.checkDormitoryAllocation(); // 重新检查宿舍分配状态
        } else {
          alert(response.data.message || '确认入住失败');
        }
      } catch (error) {
        console.error('确认入住失败:', error);
        alert('确认入住失败，请重试');
      }
    },
    
    async checkOut() {
      if (!confirm('确定要退宿吗？此操作不可撤销。')) {
        return;
      }
      
      try {
        const response = await axios.post('/api/dormitory/checkout', {
          studentId: this.userInfo.userId
        });
        
        if (response.data.success) {
          alert('退宿成功！');
          this.checkDormitoryAllocation(); // 重新检查宿舍分配状态
        } else {
          alert(response.data.message || '退宿失败');
        }
      } catch (error) {
        console.error('退宿失败:', error);
        alert('退宿失败，请重试');
      }
    },
    
    // 格式化状态文本
    getStatusText(status) {
      switch(status) {
        case 'selected':
          return '已选择，待确认入住';
        case 'confirmed':
          return '已确认入住';
        case 'checked_in':
          return '已入住';
        default:
          return '未知状态';
      }
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
  background-color: #4CAF50;
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
  height: calc(100vh - 80px);
}

.sidebar {
  width: 280px;
  background-color: #2c3e50;
  color: white;
  display: flex;
  flex-direction: column;
  box-shadow: 2px 0 5px rgba(0,0,0,0.1);
}

.tab-list {
  flex: 1;
  padding: 1rem 0;
}

.tab-item {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 1.2rem 1.5rem;
  border: none;
  background: none;
  text-align: left;
  cursor: pointer;
  transition: all 0.3s ease;
  color: #ecf0f1;
  font-size: 1rem;
  border-left: 4px solid transparent;
  position: relative;
}

.tab-item:hover {
  background-color: #34495e;
  border-left-color: #3498db;
}

.tab-item.active {
  background-color: #3498db;
  color: white;
  border-left-color: #2980b9;
  box-shadow: inset 0 0 10px rgba(0,0,0,0.1);
}

.tab-item .icon {
  font-size: 1.2rem;
  margin-right: 0.8rem;
  width: 24px;
  text-align: center;
}

.tab-item span {
  flex: 1;
}

.badge {
  background-color: #e74c3c;
  color: white;
  border-radius: 50%;
  width: 20px;
  height: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.8rem;
  font-weight: bold;
  margin-left: auto;
}

.content-area {
  flex: 1;
  background-color: #ecf0f1;
  overflow-y: auto;
}

.tab-content {
  padding: 2rem;
  height: 100%;
  background-color: white;
  margin: 1rem;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
  padding-bottom: 1rem;
  border-bottom: 2px solid #ecf0f1;
}

.page-header h2 {
  color: #2c3e50;
  margin: 0;
  font-size: 1.8rem;
}

/* 快捷功能卡片样式 */
.quick-actions {
  margin-top: 2rem;
}

.quick-actions h3 {
  color: #2c3e50;
  margin-bottom: 1.5rem;
}

.feature-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 1.5rem;
}

.card {
  background-color: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
  cursor: pointer;
  transition: all 0.3s ease;
  border-left: 4px solid #3498db;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(0,0,0,0.15);
}

.card.disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.card h4 {
  color: #2c3e50;
  margin-bottom: 0.5rem;
  font-size: 1.2rem;
}

.card p {
  color: #7f8c8d;
  margin: 0;
}

/* 设置页面样式 */
.settings-content {
  max-width: 800px;
}

.setting-section {
  background-color: #f8f9fa;
  padding: 1.5rem;
  border-radius: 8px;
  margin-bottom: 1.5rem;
}

.setting-section h3 {
  color: #2c3e50;
  margin-bottom: 1rem;
  font-size: 1.2rem;
}

.setting-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 0;
  border-bottom: 1px solid #dee2e6;
}

.setting-item:last-child {
  border-bottom: none;
}

.setting-item label {
  font-weight: 500;
  color: #495057;
}

.setting-btn {
  background-color: #3498db;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.setting-btn:hover {
  background-color: #2980b9;
}

/* 帮助中心样式 */
.help-content {
  max-width: 800px;
}

.help-section {
  background-color: #f8f9fa;
  padding: 1.5rem;
  border-radius: 8px;
  margin-bottom: 1.5rem;
}

.help-section h3 {
  color: #2c3e50;
  margin-bottom: 1rem;
  font-size: 1.2rem;
}

.faq-item {
  margin-bottom: 1.5rem;
  padding: 1rem;
  background-color: white;
  border-radius: 6px;
  border-left: 4px solid #3498db;
}

.faq-item h4 {
  color: #2c3e50;
  margin-bottom: 0.5rem;
  font-size: 1.1rem;
}

.faq-item p {
  color: #7f8c8d;
  margin: 0;
  line-height: 1.6;
}

/* 占位内容样式 */
.placeholder-content {
  text-align: center;
  padding: 3rem;
  color: #7f8c8d;
  background-color: #f8f9fa;
  border-radius: 8px;
  border: 2px dashed #dee2e6;
}

.placeholder-content p {
  font-size: 1.1rem;
  margin: 0;
}

.welcome-section {
  background-color: white;
  padding: 2rem;
  border-radius: 8px;
  margin-bottom: 2rem;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.welcome-section h2 {
  color: #4CAF50;
  margin-bottom: 1rem;
}

/* 信息完善提醒样式 */
.info-reminder {
  background-color: #fff3cd;
  border: 1px solid #ffeaa7;
  border-radius: 8px;
  padding: 1.5rem;
  margin-bottom: 2rem;
  text-align: center;
}

.info-reminder h3 {
  color: #856404;
  margin-bottom: 1rem;
}

.info-reminder p {
  color: #856404;
  margin-bottom: 1rem;
}

.complete-info-btn {
  background-color: #ffc107;
  color: #212529;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 4px;
  cursor: pointer;
  font-weight: bold;
  transition: background-color 0.2s;
}

.complete-info-btn:hover {
  background-color: #e0a800;
}

/* 个人信息展示样式 */
.info-display {
  background-color: white;
  padding: 2rem;
  border-radius: 8px;
  margin-bottom: 2rem;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.info-display h3 {
  color: #4CAF50;
  margin-bottom: 1.5rem;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 1rem;
}

.info-item {
  display: flex;
  justify-content: space-between;
  padding: 0.5rem 0;
  border-bottom: 1px solid #eee;
}

.info-item label {
  font-weight: bold;
  color: #333;
}

.info-item span {
  color: #666;
}

.status-completed {
  color: #4CAF50 !important;
  font-weight: bold;
}

.status-incomplete {
  color: #f44336 !important;
  font-weight: bold;
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

.card.disabled {
  opacity: 0.7;
  cursor: not-allowed;
}

.card.disabled:hover {
  transform: none;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.card h3 {
  color: #333;
  margin-bottom: 0.5rem;
}

.card p {
  color: #666;
  margin: 0;
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
  max-width: 600px;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
}

.modal-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.5rem;
  border-bottom: 1px solid #eee;
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
  color: #999;
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

.info-view {
  padding: 1.5rem;
}

.info-form {
  padding: 1.5rem;
}

.form-group {
  margin-bottom: 1.5rem;
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
  border-color: #4CAF50;
  box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
}

.form-group input[readonly] {
  background-color: #f5f5f5;
  cursor: not-allowed;
}

.required {
  color: #f44336;
}

.warning-notice {
  background-color: #fff3cd;
  border: 1px solid #ffeaa7;
  border-radius: 4px;
  padding: 1rem;
  margin-bottom: 1.5rem;
}

.warning-notice p {
  margin: 0;
  color: #856404;
  font-weight: bold;
}

.info-notice {
  text-align: center;
  color: #666;
  font-style: italic;
  margin-top: 1rem;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 1rem;
  margin-top: 2rem;
}

.cancel-btn {
  background-color: #6c757d;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.cancel-btn:hover {
  background-color: #5a6268;
}

.submit-btn {
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 0.75rem 1.5rem;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}

.submit-btn:hover {
  background-color: #45a049;
}

/* 宿舍选择相关样式 */
.current-dormitory {
  margin-bottom: 2rem;
}

.dormitory-card {
  background-color: white;
  border-radius: 8px;
  padding: 1.5rem;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.dormitory-card.current {
  border-left: 4px solid #4CAF50;
}

.dormitory-info h4 {
  color: #333;
  margin-bottom: 0.5rem;
}

.dormitory-info p {
  margin: 0.25rem 0;
  color: #666;
}

.dormitory-actions {
  display: flex;
  gap: 1rem;
}

.building-selection, .room-selection {
  margin-bottom: 2rem;
}

.building-selection h4, .room-selection h4 {
  color: #333;
  margin-bottom: 1rem;
}

.building-grid, .room-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 1rem;
}

.building-card, .room-card {
  background-color: white;
  border: 2px solid #e0e0e0;
  border-radius: 8px;
  padding: 1.5rem;
  cursor: pointer;
  transition: all 0.3s ease;
}

.building-card:hover, .room-card:hover {
  border-color: #4CAF50;
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0,0,0,0.15);
}

.building-card.selected, .room-card.selected {
  border-color: #4CAF50;
  background-color: #f8fff8;
}

.building-card h5, .room-card h5 {
  color: #333;
  margin-bottom: 0.5rem;
  font-size: 1.1rem;
}

.building-card p, .room-card p {
  margin: 0.25rem 0;
  color: #666;
  font-size: 0.9rem;
}

.selection-confirm {
  background-color: white;
  border-radius: 8px;
  padding: 1.5rem;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  margin-top: 2rem;
}

.selection-summary {
  margin-bottom: 1.5rem;
}

.selection-summary h4 {
  color: #333;
  margin-bottom: 1rem;
}

.selection-summary p {
  margin: 0.5rem 0;
  color: #666;
}

.btn {
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 1rem;
  transition: background-color 0.2s;
}

.btn-primary {
  background-color: #4CAF50;
  color: white;
}

.btn-primary:hover {
  background-color: #45a049;
}

.btn-danger {
  background-color: #f44336;
  color: white;
}

.btn-danger:hover:not(:disabled) {
  background-color: #d32f2f;
}

.btn-disabled,
.btn:disabled {
  background-color: #cccccc !important;
  color: #666666 !important;
  cursor: not-allowed !important;
  opacity: 0.6;
}

.btn:disabled:hover {
  background-color: #cccccc !important;
}
</style>