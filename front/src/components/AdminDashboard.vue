<template>
  <div class="dashboard">
    <!-- 用户信息头部 -->
    <div class="header">
      <h1>管理员控制台</h1>
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
            :class="{ 'active': activeTab === 'students' }"
            @click="activeTab = 'students'"
          >
            <i class="icon">👥</i>
            <span>学生管理</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'dormitory' }"
            @click="activeTab = 'dormitory'"
          >
            <i class="icon">🏠</i>
            <span>宿舍管理</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'staff' }"
            @click="activeTab = 'staff'"
          >
            <i class="icon">👨‍🏫</i>
            <span>教职工管理</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'system' }"
            @click="activeTab = 'system'"
          >
            <i class="icon">⚙️</i>
            <span>系统设置</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'statistics' }"
            @click="activeTab = 'statistics'"
          >
            <i class="icon">📊</i>
            <span>数据统计</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'permissions' }"
            @click="activeTab = 'permissions'"
          >
            <i class="icon">🔐</i>
            <span>权限管理</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'logs' }"
            @click="activeTab = 'logs'"
          >
            <i class="icon">📋</i>
            <span>日志监控</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'backup' }"
            @click="activeTab = 'backup'"
          >
            <i class="icon">💾</i>
            <span>备份恢复</span>
          </div>
          <div 
            class="tab-item" 
            :class="{ 'active': activeTab === 'notices' }"
            @click="activeTab = 'notices'"
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
            <h2>欢迎，管理员 {{ userInfo.userId }}！</h2>
            <p>这是管理员专用控制台</p>
          </div>

          <!-- 快捷功能卡片 -->
          <div class="quick-actions">
            <h3>快捷功能</h3>
            <div class="feature-cards">
              <div class="card" @click="activeTab = 'students'">
                <h4>学生管理</h4>
                <p>管理系统中的所有学生信息</p>
              </div>
              <div class="card" @click="activeTab = 'dormitory'">
                <h4>宿舍管理</h4>
                <p>管理宿舍楼栋、房间和学生分配</p>
              </div>
              <div class="card" @click="activeTab = 'staff'">
                <h4>教职工管理</h4>
                <p>管理系统中的所有教职工信息</p>
              </div>
              <div class="card" @click="activeTab = 'system'">
                <h4>系统设置</h4>
                <p>配置系统参数和功能设置</p>
              </div>
              <div class="card" @click="activeTab = 'statistics'">
                <h4>数据统计</h4>
                <p>查看系统使用情况和数据报表</p>
              </div>
            </div>
          </div>
        </div>

        <!-- 学生管理页面 -->
        <div v-if="activeTab === 'students'" class="tab-content">
          <div class="page-header">
            <h2>学生管理</h2>
            <button class="add-btn" @click="showAddModal = true">添加学生</button>
          </div>
          
          <!-- 学生列表 -->
          <div class="student-list">
            <div class="list-header">
              <span>学生ID</span>
              <span>姓名</span>
              <span>邮箱</span>
              <span>手机号</span>
              <span>性别</span>
              <span>操作</span>
            </div>
            <div v-for="student in students" :key="student.studentId" class="student-item">
              <span>{{ student.studentId }}</span>
              <span>{{ student.studentname }}</span>
              <span>{{ student.email || '未填写' }}</span>
              <span>{{ student.phone || '未填写' }}</span>
              <span>{{ student.gender === 'male' ? '男' : student.gender === 'female' ? '女' : '未填写' }}</span>
              <div class="actions">
                <button class="edit-btn" @click="editStudent(student)">编辑</button>
                <button class="delete-btn" @click="deleteStudent(student.studentId)">删除</button>
              </div>
            </div>
          </div>
        </div>

        <!-- 宿舍管理页面 -->
        <div v-if="activeTab === 'dormitory'" class="tab-content">
          <div class="page-header">
            <h2>宿舍管理</h2>
            <div class="header-actions">
              <button class="add-btn" @click="showAddBuildingModal = true">添加宿舍楼</button>
              <button class="add-btn" @click="showAddRoomModal = true">添加房间</button>
            </div>
          </div>

          <!-- 宿舍管理子标签 -->
          <div class="sub-tabs">
            <button 
              class="sub-tab" 
              :class="{ 'active': dormitorySubTab === 'buildings' }"
              @click="dormitorySubTab = 'buildings'"
            >
              宿舍楼管理
            </button>
            <button 
              class="sub-tab" 
              :class="{ 'active': dormitorySubTab === 'rooms' }"
              @click="dormitorySubTab = 'rooms'"
            >
              房间管理
            </button>
            <button 
              class="sub-tab" 
              :class="{ 'active': dormitorySubTab === 'allocations' }"
              @click="dormitorySubTab = 'allocations'"
            >
              分配管理
            </button>
          </div>

          <!-- 宿舍楼管理 -->
          <div v-if="dormitorySubTab === 'buildings'" class="buildings-section">
            <div class="buildings-table-container">
              <table class="buildings-table">
                <thead>
                  <tr>
                    <th>宿舍楼ID</th>
                    <th>宿舍楼名称</th>
                    <th>类型</th>
                    <th>楼层数</th>
                    <th>每层房间数</th>
                    <th>总房间数</th>
                    <th>可用房间</th>
                    <th>操作</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="building in buildings" :key="building.buildingId">
                    <td>{{ building.buildingId }}</td>
                    <td>{{ building.buildingName }}</td>
                    <td>{{ building.buildingType === 'male' ? '男生宿舍' : '女生宿舍' }}</td>
                    <td>{{ building.totalFloors }}层</td>
                    <td>{{ building.roomsPerFloor }}间</td>
                    <td>{{ building.totalFloors * building.roomsPerFloor }}间</td>
                    <td>{{ getBuildingAvailableRooms(building.buildingId) }}间</td>
                    <td class="actions-cell">
                      <button class="edit-btn" @click="editBuilding(building)">编辑</button>
                      <button class="delete-btn" @click="deleteBuilding(building.buildingId)">删除</button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>

          <!-- 房间管理 -->
          <div v-if="dormitorySubTab === 'rooms'" class="rooms-section">
            <div class="filter-section">
              <select v-model="selectedBuildingFilter" @change="filterRooms">
                <option value="">所有宿舍楼</option>
                <option v-for="building in buildings" :key="building.buildingId" :value="building.buildingId">
                  {{ building.buildingName }}
                </option>
              </select>
              <select v-model="roomStatusFilter" @change="filterRooms">
                <option value="">所有状态</option>
                <option value="available">可用</option>
                <option value="full">已满</option>
                <option value="maintenance">维护中</option>
              </select>
            </div>
            
            <div class="buildings-table-container">
              <table class="buildings-table">
                <thead>
                  <tr>
                    <th>房间ID</th>
                    <th>房间号</th>
                    <th>所属楼栋</th>
                    <th>楼层</th>
                    <th>床位数</th>
                    <th>已入住</th>
                    <th>状态</th>
                    <th>操作</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="room in filteredRooms" :key="room.roomId">
                    <td>{{ room.roomId }}</td>
                    <td>{{ room.roomNumber }}</td>
                    <td>{{ room.buildingName }}</td>
                    <td>{{ room.floorNumber }}层</td>
                    <td>{{ room.bedCount }}</td>
                    <td>{{ room.occupiedCount }}</td>
                    <td>
                      <span :class="'status-' + room.roomStatus">
                        {{ getRoomStatusText(room.roomStatus) }}
                      </span>
                    </td>
                    <td class="actions-cell">
                      <button class="edit-btn" @click="editRoom(room)">编辑</button>
                      <button class="delete-btn" @click="deleteRoom(room.roomId)">删除</button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>

          <!-- 分配管理 -->
          <div v-if="dormitorySubTab === 'allocations'" class="allocations-section">
            <div class="buildings-table-container">
              <table class="buildings-table">
                <thead>
                  <tr>
                    <th>分配ID</th>
                    <th>学生ID</th>
                    <th>学生姓名</th>
                    <th>宿舍楼</th>
                    <th>房间号</th>
                    <th>床位号</th>
                    <th>入住时间</th>
                    <th>状态</th>
                    <th>操作</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="allocation in allocations" :key="allocation.allocationId">
                    <td>{{ allocation.allocationId }}</td>
                    <td>{{ allocation.studentId }}</td>
                    <td>{{ allocation.studentName }}</td>
                    <td>{{ allocation.buildingName }}</td>
                    <td>{{ allocation.roomNumber }}</td>
                    <td>{{ allocation.bedNumber }}</td>
                    <td>{{ allocation.checkInDate || '未入住' }}</td>
                    <td>
                      <span :class="'status-' + allocation.allocationStatus">
                        {{ getAllocationStatusText(allocation.allocationStatus) }}
                      </span>
                    </td>
                    <td class="actions-cell">
                      <button class="edit-btn" @click="editAllocation(allocation)">编辑</button>
                      <button class="delete-btn" @click="removeAllocation(allocation.allocationId)">
                        取消分配
                      </button>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>

        <!-- 教职工管理页面 -->
        <div v-if="activeTab === 'staff'" class="tab-content">
          <div class="page-header">
            <h2>教职工管理</h2>
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

        <!-- 系统设置页面 -->
        <div v-if="activeTab === 'system'" class="tab-content">
          <div class="page-header">
            <h2>系统设置</h2>
          </div>
          <div class="settings-content">
            <div class="setting-section">
              <h3>基本设置</h3>
              <div class="setting-item">
                <label>系统名称</label>
                <input type="text" value="学生管理系统" class="setting-input">
              </div>
              <div class="setting-item">
                <label>系统版本</label>
                <span>v1.0.0</span>
              </div>
            </div>
            <div class="setting-section">
              <h3>安全设置</h3>
              <div class="setting-item">
                <label>密码复杂度要求</label>
                <input type="checkbox" checked>
              </div>
              <div class="setting-item">
                <label>登录失败锁定</label>
                <input type="checkbox" checked>
              </div>
            </div>
          </div>
        </div>

        <!-- 数据统计页面 -->
        <div v-if="activeTab === 'statistics'" class="tab-content">
          <div class="page-header">
            <h2>数据统计</h2>
          </div>
          <div class="statistics-content">
            <div class="stat-cards">
              <div class="stat-card">
                <h4>学生总数</h4>
                <div class="stat-number">{{ students.length }}</div>
              </div>
              <div class="stat-card">
                <h4>教职工总数</h4>
                <div class="stat-number">{{ staffList.length }}</div>
              </div>
              <div class="stat-card">
                <h4>今日登录</h4>
                <div class="stat-number">0</div>
              </div>
              <div class="stat-card">
                <h4>系统运行天数</h4>
                <div class="stat-number">1</div>
              </div>
            </div>
          </div>
        </div>

        <!-- 权限管理页面 -->
        <div v-if="activeTab === 'permissions'" class="tab-content">
          <div class="page-header">
            <h2>权限管理</h2>
          </div>
          <div class="placeholder-content">
            <p>权限管理功能正在开发中...</p>
          </div>
        </div>

        <!-- 日志监控页面 -->
        <div v-if="activeTab === 'logs'" class="tab-content">
          <div class="page-header">
            <h2>日志监控</h2>
          </div>
          <div class="placeholder-content">
            <p>日志监控功能正在开发中...</p>
          </div>
        </div>

        <!-- 备份恢复页面 -->
        <div v-if="activeTab === 'backup'" class="tab-content">
          <div class="page-header">
            <h2>备份恢复</h2>
          </div>
          <div class="placeholder-content">
            <p>备份恢复功能正在开发中...</p>
          </div>
        </div>

        <!-- 通知管理页面 -->
        <div v-if="activeTab === 'notices'" class="tab-content">
          <div class="page-header">
            <h2>通知管理</h2>
            <button class="add-btn" @click="showNoticeModal = true">发布通知</button>
          </div>

          <!-- 通知状态筛选 -->
          <div class="notice-filters">
            <select v-model="noticeStatusFilter" @change="filterNotices">
              <option value="">全部状态</option>
              <option value="1">已发布</option>
              <option value="0">草稿</option>
            </select>
            <select v-model="noticeTypeFilter" @change="filterNotices">
              <option value="">全部类型</option>
              <option value="general">一般通知</option>
              <option value="dormitory">宿舍通知</option>
              <option value="academic">学术通知</option>
              <option value="activity">活动通知</option>
              <option value="emergency">紧急通知</option>
            </select>
          </div>

          <!-- 通知列表 -->
          <div class="notice-list">
            <div class="notice-table">
              <table>
                <thead>
                  <tr>
                    <th>标题</th>
                    <th>类型</th>
                    <th>状态</th>
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
                      <span class="notice-status" :class="notice.status === 1 ? 'published' : 'draft'">
                        {{ notice.status === 1 ? '已发布' : '草稿' }}
                      </span>
                    </td>
                    <td>{{ formatDate(notice.publishTime) }}</td>
                    <td>{{ notice.viewCount || 0 }}</td>
                    <td>
                      <button class="btn-small" @click="viewNotice(notice)">查看</button>
                      <button class="btn-small edit" @click="editNotice(notice)">编辑</button>
                      <button v-if="notice.status === 0" class="btn-small publish" @click="publishNotice(notice.id)">发布</button>
                      <button v-if="notice.status === 1" class="btn-small unpublish" @click="unpublishNotice(notice.id)">撤回</button>
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
          <div class="form-group">
            <label>性别:</label>
            <select v-model="newStudent.gender">
              <option value="">请选择性别</option>
              <option value="male">男</option>
              <option value="female">女</option>
            </select>
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
          <div class="form-group">
            <label>性别:</label>
            <select v-model="editingStudent.gender">
              <option value="">请选择性别</option>
              <option value="male">男</option>
              <option value="female">女</option>
            </select>
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

  <!-- 添加宿舍楼模态框 -->
  <div v-if="showAddBuildingModal" class="modal-overlay" @click="closeAddBuildingModal">
    <div class="modal building-modal" @click.stop>
      <div class="modal-header">
        <h3>添加宿舍楼</h3>
        <button @click="closeAddBuildingModal" class="close-btn">&times;</button>
      </div>
      <div class="modal-body">
        <form @submit.prevent="addBuilding">
          <div class="form-group">
            <label>宿舍楼名称:</label>
            <input v-model="newBuilding.buildingName" type="text" placeholder="请输入宿舍楼名称" required>
          </div>
          <div class="form-group">
            <label>宿舍楼类型:</label>
            <select v-model="newBuilding.buildingType" required>
              <option value="">请选择宿舍楼类型</option>
              <option value="male">男生楼</option>
              <option value="female">女生楼</option>
            </select>
          </div>
          <div class="form-group">
            <label>总楼层数:</label>
            <input v-model="newBuilding.totalFloors" type="number" min="1" placeholder="请输入总楼层数" required>
          </div>
          <div class="form-group">
            <label>每层房间数:</label>
            <input v-model="newBuilding.roomsPerFloor" type="number" min="1" placeholder="请输入每层房间数" required>
          </div>
          <div class="form-actions">
            <button type="button" @click="closeAddBuildingModal" class="cancel-btn">取消</button>
            <button type="submit" class="submit-btn">添加</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- 编辑宿舍楼模态框 -->
  <div v-if="showEditBuildingModal" class="modal-overlay" @click="closeEditBuildingModal">
    <div class="modal building-modal" @click.stop>
      <div class="modal-header">
        <h3>编辑宿舍楼</h3>
        <button @click="closeEditBuildingModal" class="close-btn">&times;</button>
      </div>
      <div class="modal-body">
        <form @submit.prevent="updateBuilding">
          <div class="form-group">
            <label>宿舍楼ID:</label>
            <input v-model="editingBuilding.buildingId" type="text" readonly>
          </div>
          <div class="form-group">
            <label>宿舍楼名称:</label>
            <input v-model="editingBuilding.buildingName" type="text" placeholder="请输入宿舍楼名称" required>
          </div>
          <div class="form-group">
            <label>宿舍楼类型:</label>
            <select v-model="editingBuilding.buildingType" required>
              <option value="">请选择宿舍楼类型</option>
              <option value="male">男生楼</option>
              <option value="female">女生楼</option>
            </select>
          </div>
          <div class="form-group">
            <label>总楼层数:</label>
            <input v-model="editingBuilding.totalFloors" type="number" min="1" placeholder="请输入总楼层数" required>
          </div>
          <div class="form-group">
            <label>每层房间数:</label>
            <input v-model="editingBuilding.roomsPerFloor" type="number" min="1" placeholder="请输入每层房间数" required>
          </div>
          <div class="form-actions">
            <button type="button" @click="closeEditBuildingModal" class="cancel-btn">取消</button>
            <button type="submit" class="submit-btn">保存</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- 添加房间模态框 -->
  <div v-if="showAddRoomModal" class="modal-overlay" @click="closeAddRoomModal">
    <div class="modal" @click.stop>
      <div class="modal-header">
        <h3>添加房间</h3>
        <button @click="closeAddRoomModal" class="close-btn">&times;</button>
      </div>
      <div class="modal-body">
        <form @submit.prevent="addRoom">
          <div class="form-group">
            <label>所属宿舍楼:</label>
            <select v-model="newRoom.buildingId" required>
              <option value="">请选择宿舍楼</option>
              <option v-for="building in buildings" :key="building.buildingId" :value="building.buildingId">
                {{ building.buildingName }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>房间号:</label>
            <input v-model="newRoom.roomNumber" type="text" placeholder="请输入房间号" required>
          </div>
          <div class="form-group">
            <label>楼层号:</label>
            <input v-model="newRoom.floorNumber" type="number" min="1" placeholder="请输入楼层号" required>
          </div>
          <div class="form-group">
            <label>床位数:</label>
            <input v-model="newRoom.bedCount" type="number" min="1" max="8" placeholder="请输入床位数" required>
          </div>
          <div class="form-group">
            <label>房间状态:</label>
            <select v-model="newRoom.roomStatus" required>
              <option value="available">可用</option>
              <option value="maintenance">维护中</option>
            </select>
          </div>
          <div class="form-actions">
            <button type="button" @click="closeAddRoomModal" class="cancel-btn">取消</button>
            <button type="submit" class="submit-btn">添加</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- 编辑房间模态框 -->
  <div v-if="showEditRoomModal" class="modal-overlay" @click="closeEditRoomModal">
    <div class="modal" @click.stop>
      <div class="modal-header">
        <h3>编辑房间</h3>
        <button @click="closeEditRoomModal" class="close-btn">&times;</button>
      </div>
      <div class="modal-body">
        <form @submit.prevent="updateRoom">
          <div class="form-group">
            <label>房间ID:</label>
            <input v-model="editingRoom.roomId" type="text" readonly>
          </div>
          <div class="form-group">
            <label>所属宿舍楼:</label>
            <select v-model="editingRoom.buildingId" required>
              <option value="">请选择宿舍楼</option>
              <option v-for="building in buildings" :key="building.buildingId" :value="building.buildingId">
                {{ building.buildingName }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>房间号:</label>
            <input v-model="editingRoom.roomNumber" type="text" placeholder="请输入房间号" required>
          </div>
          <div class="form-group">
            <label>楼层号:</label>
            <input v-model="editingRoom.floorNumber" type="number" min="1" placeholder="请输入楼层号" required>
          </div>
          <div class="form-group">
            <label>床位数:</label>
            <input v-model="editingRoom.bedCount" type="number" min="1" max="8" placeholder="请输入床位数" required>
          </div>
          <div class="form-group">
            <label>房间状态:</label>
            <select v-model="editingRoom.roomStatus" required>
              <option value="available">可用</option>
              <option value="full">已满</option>
              <option value="maintenance">维护中</option>
            </select>
          </div>
          <div class="form-actions">
            <button type="button" @click="closeEditRoomModal" class="cancel-btn">取消</button>
            <button type="submit" class="submit-btn">保存</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- 添加分配模态框 -->
  <div v-if="showAddAllocationModal" class="modal-overlay" @click="closeAddAllocationModal">
    <div class="modal" @click.stop>
      <div class="modal-header">
        <h3>新增分配</h3>
        <button @click="closeAddAllocationModal" class="close-btn">&times;</button>
      </div>
      <div class="modal-body">
        <form @submit.prevent="addAllocation">
          <div class="form-group">
            <label>学生ID:</label>
            <input v-model="newAllocation.studentId" type="text" placeholder="请输入学生ID" required>
          </div>
          <div class="form-group">
            <label>宿舍楼:</label>
            <select v-model="newAllocation.buildingId" @change="loadAvailableRooms" required>
              <option value="">请选择宿舍楼</option>
              <option v-for="building in buildings" :key="building.buildingId" :value="building.buildingId">
                {{ building.buildingName }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>房间:</label>
            <select v-model="newAllocation.roomId" @change="loadAvailableBeds" required>
              <option value="">请选择房间</option>
              <option v-for="room in availableRooms" :key="room.roomId" :value="room.roomId">
                {{ room.roomNumber }} ({{ room.bedCount - room.occupiedCount }}个空床位)
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>床位号:</label>
            <select v-model="newAllocation.bedNumber" required>
              <option value="">请选择床位</option>
              <option v-for="bed in availableBeds" :key="bed" :value="bed">
                床位 {{ bed }}
              </option>
            </select>
          </div>
          <div class="form-actions">
            <button type="button" @click="closeAddAllocationModal" class="cancel-btn">取消</button>
            <button type="submit" class="submit-btn">分配</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- 编辑分配模态框 -->
  <div v-if="showEditAllocationModal" class="modal-overlay" @click="closeEditAllocationModal">
    <div class="modal" @click.stop>
      <div class="modal-header">
        <h3>编辑分配</h3>
        <button @click="closeEditAllocationModal" class="close-btn">&times;</button>
      </div>
      <div class="modal-body">
        <div class="info-tip">
          <p><strong>提示：</strong>您可以单独修改分配状态，也可以修改宿舍信息。可以部分修改宿舍信息字段。</p>
        </div>
        <form @submit.prevent="updateAllocation">
          <div class="form-group">
            <label>分配ID:</label>
            <input v-model="editingAllocation.allocationId" type="text" readonly>
          </div>
          <div class="form-group">
            <label>学生ID:</label>
            <input v-model="editingAllocation.studentId" type="text" readonly>
          </div>
          <div class="form-group">
            <label>宿舍楼:</label>
            <select v-model="editingAllocation.buildingId" @change="loadAvailableRoomsForEdit">
              <option value="">请选择宿舍楼</option>
              <option v-for="building in buildings" :key="building.buildingId" :value="building.buildingId">
                {{ building.buildingName }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>房间:</label>
            <select v-model="editingAllocation.roomId" @change="loadAvailableBedsForEdit">
              <option value="">请选择房间</option>
              <option v-for="room in availableRoomsForEdit" :key="room.roomId" :value="room.roomId">
                {{ room.roomNumber }} ({{ room.bedCount - room.occupiedCount }}个空床位)
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>床位号:</label>
            <select v-model="editingAllocation.bedNumber">
              <option value="">请选择床位</option>
              <option v-for="bed in availableBedsForEdit" :key="bed" :value="bed">
                床位 {{ bed }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label>分配状态:</label>
            <select v-model="editingAllocation.allocationStatus" required>
              <option value="pending">待确认</option>
              <option value="confirmed">已确认</option>

            </select>
          </div>
          <div class="form-actions">
            <button type="button" @click="closeEditAllocationModal" class="cancel-btn">取消</button>
            <button type="submit" class="submit-btn">保存</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- 通知详情查看模态框 -->
  <div v-if="showViewNoticeModal" class="modal-overlay" @click="closeViewNoticeModal">
    <div class="modal notice-view-modal" @click.stop>
      <div class="modal-header">
        <h3>{{ viewingNotice.title }}</h3>
        <button class="close-btn" @click="closeViewNoticeModal">×</button>
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
            <strong>状态：</strong>
            <span class="notice-status" :class="viewingNotice.isPublished === 1 ? 'published' : 'draft'">
              {{ viewingNotice.isPublished === 1 ? '已发布' : '草稿' }}
            </span>
          </div>
          <div class="meta-item">
            <strong>发布时间：</strong>{{ formatDate(viewingNotice.publishTime) }}
          </div>
          <div class="meta-item">
            <strong>查看次数：</strong>{{ viewingNotice.viewCount || 0 }}
          </div>
        </div>
        
        <div class="notice-content">
          <h4>通知内容：</h4>
          <div class="content-text">{{ viewingNotice.content }}</div>
        </div>
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
      hideTimeout: null, // 添加延迟隐藏的定时器
      activeTab: 'dashboard', // 当前激活的标签页
      dormitorySubTab: 'buildings', // 宿舍管理子标签页
      showAddModal: false,
      showEditModal: false,
      showAddStaffModal: false,
      showEditStaffModal: false,
      showAddBuildingModal: false,
      showEditBuildingModal: false,
      showAddRoomModal: false,
      showEditRoomModal: false,
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
        age: null,
        gender: ''
      },
      editingStudent: {
        studentId: '',
        studentname: '',
        password: '',
        phone: '',
        email: '',
        address: '',
        age: null,
        gender: ''
      },
      newStaff: {
        staffId: '',
        password: ''
      },
      editingStaff: {
        staffId: '',
        password: ''
      },
      // 宿舍管理相关数据
      buildings: [],
      rooms: [],
      assignments: [],
      allocations: [],
      newBuilding: {
        buildingName: '',
        buildingType: '',
        totalFloors: null,
        roomsPerFloor: null
      },
      editingBuilding: {
        buildingId: null,
        buildingName: '',
        buildingType: '',
        totalFloors: null,
        roomsPerFloor: null
      },
      newRoom: {
        buildingId: null,
        roomNumber: '',
        floorNumber: null,
        bedCount: 4,
        roomStatus: 'available'
      },
      editingRoom: {
        roomId: null,
        buildingId: null,
        roomNumber: '',
        floorNumber: null,
        bedCount: 4,
        roomStatus: 'available'
      },
      // 分配管理相关数据
      showAddAllocationModal: false,
      showEditAllocationModal: false,
      newAllocation: {
        studentId: '',
        buildingId: null,
        roomId: null,
        bedNumber: null
      },
      editingAllocation: {
        allocationId: null,
        studentId: '',
        buildingId: null,
        roomId: null,
        bedNumber: null,
        allocationStatus: 'pending'
      },
      availableRooms: [],
      availableBeds: [],
      availableRoomsForEdit: [],
      availableBedsForEdit: [],
      // 房间筛选相关属性
      selectedBuildingFilter: '',
      roomStatusFilter: '',
      filteredRooms: [],
      // 通知管理相关数据
      notices: [],
      filteredNotices: [],
      noticeStatusFilter: '',
      noticeTypeFilter: '',
      showNoticeModal: false,
      showViewNoticeModal: false,
      isEditMode: false,
      viewingNotice: {},
      noticeForm: {
        id: null,
        title: '',
        content: '',
        type: 'general',
        isPublished: 0,
        publishTime: null,
        viewCount: 0
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
    // 加载宿舍相关数据
    this.loadBuildings();
    this.loadRooms();
    this.loadAssignments();
    // 加载通知列表
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
        age: null,
        gender: ''
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
        age: null,
        gender: ''
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
    },
    
    // 宿舍管理相关方法
    async loadBuildings() {
      try {
        const response = await axios.get('/api/dormitory/buildings');
        if (response.data.success) {
          this.buildings = response.data.data;
          console.log('Loaded buildings:', this.buildings);
        } else {
          console.error('加载宿舍楼列表失败:', response.data.message);
        }
      } catch (error) {
        console.error('加载宿舍楼列表失败:', error);
      }
    },
    
    // 计算指定宿舍楼的可用房间数
    getBuildingAvailableRooms(buildingId) {
      if (!this.rooms || this.rooms.length === 0) {
        console.log(`No rooms data available for building ${buildingId}`);
        return 0;
      }
      const availableRooms = this.rooms.filter(room => 
        room.buildingId === buildingId && room.roomStatus === 'available'
      );
      console.log(`Building ${buildingId} available rooms:`, availableRooms.length, 'out of', this.rooms.filter(r => r.buildingId === buildingId).length);
      return availableRooms.length;
    },
    
    async loadRooms() {
      try {
        const response = await axios.get('/api/dormitory/rooms');
        if (response.data.success) {
          this.rooms = response.data.data;
          this.filteredRooms = this.rooms; // 初始化筛选结果
          console.log('Loaded rooms:', this.rooms);
        } else {
          console.error('加载房间列表失败:', response.data.message);
        }
      } catch (error) {
        console.error('加载房间列表失败:', error);
      }
    },
    
    async loadAssignments() {
      try {
        const response = await axios.get('/api/dormitory/assignments');
        if (response.data.success) {
          this.allocations = response.data.data;
        } else {
          console.error('加载分配信息失败:', response.data.message);
          alert('加载分配信息失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('加载分配信息失败:', error);
        alert('加载分配信息失败，请检查网络连接');
      }
    },
    
    // 宿舍楼管理
    async addBuilding() {
      try {
        const response = await axios.post('/api/dormitory/buildings', this.newBuilding);
        if (response.data.success) {
          alert('添加宿舍楼成功');
          this.closeAddBuildingModal();
          this.loadBuildings();
        } else {
          alert('添加宿舍楼失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('添加宿舍楼失败:', error);
        alert('添加宿舍楼失败，请检查网络连接');
      }
    },
    
    editBuilding(building) {
      this.editingBuilding = { ...building };
      this.showEditBuildingModal = true;
    },
    
    async updateBuilding() {
      try {
        const response = await axios.put(`/api/dormitory/buildings/${this.editingBuilding.buildingId}`, this.editingBuilding);
        if (response.data.success) {
          alert('更新宿舍楼成功');
          this.closeEditBuildingModal();
          this.loadBuildings();
        } else {
          alert('更新宿舍楼失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('更新宿舍楼失败:', error);
        alert('更新宿舍楼失败，请检查网络连接');
      }
    },
    
    async deleteBuilding(buildingId) {
      if (confirm('确定要删除这个宿舍楼吗？这将删除该楼下的所有房间和分配信息。')) {
        try {
          const response = await axios.delete(`/api/dormitory/buildings/${buildingId}`);
          if (response.data.success) {
            alert('删除宿舍楼成功');
            this.loadBuildings();
            this.loadRooms();
            this.loadAssignments();
          } else {
            alert('删除宿舍楼失败: ' + response.data.message);
          }
        } catch (error) {
          console.error('删除宿舍楼失败:', error);
          alert('删除宿舍楼失败，请检查网络连接');
        }
      }
    },
    
    // 房间管理
    async addRoom() {
      try {
        const response = await axios.post('/api/dormitory/rooms', this.newRoom);
        if (response.data.success) {
          alert('添加房间成功');
          this.closeAddRoomModal();
          this.loadRooms();
        } else {
          alert('添加房间失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('添加房间失败:', error);
        alert('添加房间失败，请检查网络连接');
      }
    },
    
    editRoom(room) {
      this.editingRoom = { ...room };
      this.showEditRoomModal = true;
    },
    
    async updateRoom() {
      try {
        const response = await axios.put(`/api/dormitory/rooms/${this.editingRoom.roomId}`, this.editingRoom);
        if (response.data.success) {
          alert('更新房间成功');
          this.closeEditRoomModal();
          this.loadRooms();
        } else {
          alert('更新房间失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('更新房间失败:', error);
        alert('更新房间失败，请检查网络连接');
      }
    },
    
    async deleteRoom(roomId) {
      if (confirm('确定要删除这个房间吗？这将删除该房间的所有分配信息。')) {
        try {
          const response = await axios.delete(`/api/dormitory/rooms/${roomId}`);
          if (response.data.success) {
            alert('删除房间成功');
            this.loadRooms();
            this.loadAssignments();
          } else {
            alert('删除房间失败: ' + response.data.message);
          }
        } catch (error) {
          console.error('删除房间失败:', error);
          alert('删除房间失败，请检查网络连接');
        }
      }
    },
    
    // 房间筛选方法
    filterRooms() {
      let filtered = this.rooms;
      
      // 按宿舍楼筛选
      if (this.selectedBuildingFilter) {
        filtered = filtered.filter(room => room.buildingId == this.selectedBuildingFilter);
      }
      
      // 按状态筛选
      if (this.roomStatusFilter) {
        filtered = filtered.filter(room => room.roomStatus === this.roomStatusFilter);
      }
      
      this.filteredRooms = filtered;
    },
    
    // 获取房间状态文本
    getRoomStatusText(status) {
      const statusMap = {
        'available': '可用',
        'full': '已满',
        'maintenance': '维护中'
      };
      return statusMap[status] || status;
    },
    
    // 获取下一个状态文本
    getNextStatusText(currentStatus) {
      const nextStatusMap = {
        'available': '设为维护',
        'full': '设为维护',
        'maintenance': '设为可用'
      };
      return nextStatusMap[currentStatus] || '切换状态';
    },
    
    // 切换房间状态
    async toggleRoomStatus(room) {
      const nextStatus = room.roomStatus === 'maintenance' ? 'available' : 'maintenance';
      try {
        const response = await axios.put(`/api/dormitory/rooms/${room.roomId}/status`, {
          roomStatus: nextStatus
        });
        if (response.data.success) {
          alert('房间状态更新成功');
          this.loadRooms();
        } else {
          alert('房间状态更新失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('房间状态更新失败:', error);
        alert('房间状态更新失败，请检查网络连接');
      }
    },
    
    // 分配管理
    async removeAllocation(allocationId) {
      if (confirm('确定要取消这个分配吗？')) {
        try {
          const response = await axios.delete(`/api/dormitory/assignments/${allocationId}`);
          if (response.data.success) {
            alert('取消分配成功');
            this.loadAssignments();
            this.loadRooms();
          } else {
            alert('取消分配失败: ' + response.data.message);
          }
        } catch (error) {
          console.error('取消分配失败:', error);
          alert('取消分配失败，请检查网络连接');
        }
      }
    },
    
    async deleteAssignment(assignmentId) {
      if (confirm('确定要删除这个分配记录吗？')) {
        try {
          const response = await axios.delete(`/api/dormitory/assignments/${assignmentId}`);
          if (response.data.success) {
            alert('删除分配记录成功');
            this.loadAssignments();
            this.loadRooms();
          } else {
            alert('删除分配记录失败: ' + response.data.message);
          }
        } catch (error) {
          console.error('删除分配记录失败:', error);
          alert('删除分配记录失败，请检查网络连接');
        }
      }
    },
    
    // 模态框控制
    closeAddBuildingModal() {
      this.showAddBuildingModal = false;
      this.newBuilding = {
        buildingName: '',
        buildingType: '',
        totalFloors: null,
        roomsPerFloor: null
      };
    },
    
    closeEditBuildingModal() {
      this.showEditBuildingModal = false;
      this.editingBuilding = {
        buildingId: null,
        buildingName: '',
        buildingType: '',
        totalFloors: null,
        roomsPerFloor: null
      };
    },
    
    closeAddRoomModal() {
      this.showAddRoomModal = false;
      this.newRoom = {
        buildingId: null,
        roomNumber: '',
        floorNumber: null,
        bedCount: 4,
        roomStatus: 'available'
      };
    },
    
    closeEditRoomModal() {
      this.showEditRoomModal = false;
      this.editingRoom = {
        roomId: null,
        buildingId: null,
        roomNumber: '',
        floorNumber: null,
        bedCount: 4,
        roomStatus: 'available'
      };
    },
    
    // 分配管理方法
    async addAllocation() {
      try {
        const response = await axios.post('/api/dormitory/assignments', this.newAllocation);
        if (response.data.success) {
          alert('分配成功');
          this.closeAddAllocationModal();
          this.loadAssignments();
          this.loadRooms();
        } else {
          alert('分配失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('分配失败:', error);
        alert('分配失败，请检查网络连接');
      }
    },
    
    editAllocation(allocation) {
      this.editingAllocation = { ...allocation };
      this.showEditAllocationModal = true;
      // 加载可用房间和床位
      this.loadAvailableRoomsForEdit();
      // 如果已经有房间ID，也加载对应的床位
      if (this.editingAllocation.roomId) {
        this.loadAvailableBedsForEdit();
      }
    },
    
    async updateAllocation() {
      try {
        const response = await axios.put(`/api/dormitory/assignments/${this.editingAllocation.allocationId}`, this.editingAllocation);
        if (response.data.success) {
          alert('更新分配成功');
          this.closeEditAllocationModal();
          this.loadAssignments();
          this.loadRooms();
        } else {
          alert('更新分配失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('更新分配失败:', error);
        alert('更新分配失败，请检查网络连接');
      }
    },
    
    closeAddAllocationModal() {
      this.showAddAllocationModal = false;
      this.newAllocation = {
        studentId: '',
        buildingId: null,
        roomId: null,
        bedNumber: null
      };
      this.availableRooms = [];
      this.availableBeds = [];
    },
    
    closeEditAllocationModal() {
      this.showEditAllocationModal = false;
      this.editingAllocation = {
        allocationId: null,
        studentId: '',
        buildingId: null,
        roomId: null,
        bedNumber: null,
        allocationStatus: 'pending'
      };
      this.availableRoomsForEdit = [];
      this.availableBedsForEdit = [];
    },
    
    // 加载可用房间和床位
    async loadAvailableRooms() {
      if (!this.newAllocation.buildingId) {
        this.availableRooms = [];
        return;
      }
      
      try {
        const response = await axios.get(`/api/dormitory/rooms/available/${this.newAllocation.buildingId}`);
        if (response.data.success) {
          this.availableRooms = response.data.data;
        } else {
          console.error('加载可用房间失败:', response.data.message);
        }
      } catch (error) {
        console.error('加载可用房间失败:', error);
      }
    },
    
    async loadAvailableBeds() {
      if (!this.newAllocation.roomId) {
        this.availableBeds = [];
        return;
      }
      
      try {
        const response = await axios.get(`/api/dormitory/beds/available/${this.newAllocation.roomId}`);
        if (response.data.success) {
          this.availableBeds = response.data.data;
        } else {
          console.error('加载可用床位失败:', response.data.message);
        }
      } catch (error) {
        console.error('加载可用床位失败:', error);
      }
    },
    
    async loadAvailableRoomsForEdit() {
      if (!this.editingAllocation.buildingId) {
        this.availableRoomsForEdit = [];
        return;
      }
      
      try {
        const response = await axios.get(`/api/dormitory/rooms/available/${this.editingAllocation.buildingId}`);
        if (response.data.success) {
          this.availableRoomsForEdit = response.data.data;
        } else {
          console.error('加载可用房间失败:', response.data.message);
        }
      } catch (error) {
        console.error('加载可用房间失败:', error);
      }
    },
    
    async loadAvailableBedsForEdit() {
      if (!this.editingAllocation.roomId) {
        this.availableBedsForEdit = [];
        return;
      }
      
      try {
        const response = await axios.get(`/api/dormitory/beds/available/${this.editingAllocation.roomId}`);
        if (response.data.success) {
          this.availableBedsForEdit = response.data.data;
        } else {
          console.error('加载可用床位失败:', response.data.message);
        }
      } catch (error) {
        console.error('加载可用床位失败:', error);
      }
    },
    
    // 获取宿舍楼名称
    getBuildingName(buildingId) {
      const building = this.buildings.find(b => b.buildingId === buildingId);
      return building ? building.buildingName : '未知';
    },
    
    // 获取房间号
    getRoomNumber(roomId) {
      const room = this.rooms.find(r => r.roomId === roomId);
      return room ? room.roomNumber : '未知';
    },
    
    // 获取学生姓名
    getStudentName(studentId) {
      const student = this.students.find(s => s.studentId === studentId);
      return student ? student.studentname : '未知';
    },
    

    
    getAllocationStatusText(status) {
      const statusMap = {
        'pending': '待确认',
        'confirmed': '已确认',
        'checked_in': '已入住',
        'checked_out': '已退宿'
      };
      return statusMap[status] || status;
    },

    // 通知管理方法
    async loadNotices() {
      try {
        // 获取当前登录用户信息
        const userInfo = JSON.parse(localStorage.getItem('userInfo') || '{}');
        const user = JSON.parse(localStorage.getItem('user') || '{}');
        
        // 使用search API按publisherId筛选通知，管理员使用adminId
        const publisherId = user.adminId || userInfo.userId;
        
        const response = await axios.get('/api/notices/search', {
          params: {
            publisherId: publisherId
          }
        });
        
        if (response.data.success) {
          this.notices = response.data.data || [];
          this.filteredNotices = [...this.notices];
        } else {
          console.error('获取通知列表失败:', response.data.message);
          this.loadMockNotices();
        }
      } catch (error) {
        console.error('加载通知列表出错:', error);
        this.loadMockNotices();
      }
    },

    loadMockNotices() {
      // 模拟通知数据
      this.notices = [
        {
          id: 1,
          title: '宿舍卫生检查通知',
          content: '本周将进行宿舍卫生检查，请各位同学做好准备。',
          type: 'dormitory',
          isPublished: 1,
          publishTime: '2024-01-15 10:00:00',
          viewCount: 156
        },
        {
          id: 2,
          title: '期末考试安排',
          content: '期末考试将于下月开始，请同学们合理安排复习时间。',
          type: 'academic',
          isPublished: 1,
          publishTime: '2024-01-14 14:30:00',
          viewCount: 89
        },
        {
          id: 3,
          title: '新学期活动预告',
          content: '新学期将举办多项精彩活动，敬请期待。',
          type: 'activity',
          isPublished: 0,
          publishTime: null,
          viewCount: 0
        }
      ];
      this.filteredNotices = [...this.notices];
    },

    filterNotices() {
      this.filteredNotices = this.notices.filter(notice => {
        const statusMatch = !this.noticeStatusFilter || notice.status.toString() === this.noticeStatusFilter;
        const typeMatch = !this.noticeTypeFilter || notice.type === this.noticeTypeFilter;
        return statusMatch && typeMatch;
      });
    },

    viewNotice(notice) {
      this.viewingNotice = notice;
      this.showViewNoticeModal = true;
    },

    editNotice(notice) {
      this.noticeForm = { ...notice };
      this.isEditMode = true;
      this.showNoticeModal = true;
    },

    async publishNotice(noticeId) {
      try {
        const response = await axios.put(`/api/notices/${noticeId}/status`, {
          status: 1
        });
        if (response.data.success) {
          const notice = this.notices.find(n => n.id === noticeId);
          if (notice) {
            notice.status = 1;
            notice.publishTime = new Date().toISOString().slice(0, 19).replace('T', ' ');
          }
          this.filterNotices();
          alert('通知发布成功');
        } else {
          alert('发布失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('发布通知失败:', error);
        alert('发布通知失败，请稍后重试');
      }
    },

    async unpublishNotice(noticeId) {
      if (!confirm('确定要撤回这条通知吗？')) {
        return;
      }
      try {
        const response = await axios.put(`/api/notices/${noticeId}/status`, {
          status: 0
        });
        if (response.data.success) {
          const notice = this.notices.find(n => n.id === noticeId);
          if (notice) {
            notice.status = 0;
          }
          this.filterNotices();
          alert('通知撤回成功');
        } else {
          alert('撤回失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('撤回通知失败:', error);
        alert('撤回通知失败，请稍后重试');
      }
    },

    async deleteNotice(noticeId) {
      if (!confirm('确定要删除这条通知吗？')) {
        return;
      }
      try {
        const response = await axios.delete(`/api/notices/${noticeId}`);
        if (response.data.success) {
          this.notices = this.notices.filter(notice => notice.id !== noticeId);
          this.filterNotices();
          alert('通知删除成功');
        } else {
          alert('删除失败: ' + response.data.message);
        }
      } catch (error) {
        console.error('删除通知失败:', error);
        alert('删除通知失败，请稍后重试');
      }
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

    formatDate(dateString) {
      if (!dateString) return '未发布';
      return new Date(dateString).toLocaleString('zh-CN');
    },

    closeViewNoticeModal() {
      this.showViewNoticeModal = false;
      this.viewingNotice = {};
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
  cursor: pointer;
  user-select: none;
  transition: all 0.2s ease;
}

.logout-btn:hover {
  background-color: #d32f2f;
  transform: translateY(-1px);
}

.content {
  padding: 2rem;
}

/* 主布局样式 */
.main-layout {
  display: flex;
  height: calc(100vh - 60px);
}

/* 左侧标签栏样式 */
.sidebar {
  width: 250px;
  background-color: #2c3e50;
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
  background-color: #3498db;
  border-left: 4px solid #2980b9;
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

/* 设置页面样式 */
.settings-content {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  padding: 20px;
}

.setting-section {
  margin-bottom: 30px;
}

.setting-section h3 {
  color: #2c3e50;
  margin-bottom: 15px;
  padding-bottom: 10px;
  border-bottom: 2px solid #ecf0f1;
}

.setting-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 0;
  border-bottom: 1px solid #ecf0f1;
}

.setting-item label {
  font-weight: 500;
  color: #2c3e50;
}

.setting-input {
  padding: 8px 12px;
  border: 1px solid #ddd;
  border-radius: 4px;
  width: 200px;
}

/* 统计页面样式 */
.statistics-content {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  padding: 20px;
}

.stat-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
}

.stat-card {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 20px;
  border-radius: 8px;
  text-align: center;
}

.stat-card h4 {
  margin: 0 0 10px 0;
  font-size: 14px;
  opacity: 0.9;
}

.stat-number {
  font-size: 32px;
  font-weight: bold;
  margin: 0;
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
  vertical-align: middle;
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

.actions-cell {
  white-space: nowrap;
}

.actions-cell .edit-btn,
.actions-cell .delete-btn {
  margin-right: 8px;
  padding: 6px 12px;
  font-size: 12px;
  border-radius: 4px;
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
/* 宿舍楼模态框特殊样式 */
.building-modal {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  position: relative;
  overflow: hidden;
}

.building-modal::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: 
    radial-gradient(circle at 20% 80%, rgba(120, 119, 198, 0.3) 0%, transparent 50%),
    radial-gradient(circle at 80% 20%, rgba(255, 255, 255, 0.15) 0%, transparent 50%),
    radial-gradient(circle at 40% 40%, rgba(120, 119, 198, 0.2) 0%, transparent 50%);
  pointer-events: none;
}

.building-modal .modal-header {
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid rgba(255, 255, 255, 0.2);
  position: relative;
  z-index: 1;
}

.building-modal .modal-body {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  position: relative;
  z-index: 1;
  padding: 2rem;
}

.building-modal .form-group select {
  width: 100%;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
  box-sizing: border-box;
  background-color: white;
}

.building-modal .form-group select:focus {
  outline: none;
  border-color: #667eea;
  box-shadow: 0 0 0 2px rgba(102, 126, 234, 0.2);
}

/* 宿舍楼表格样式 */
.buildings-table-container {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  overflow: hidden;
  margin-top: 20px;
}

.buildings-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
}

.buildings-table thead {
  background-color: #f8f9fa;
}

.buildings-table th {
  padding: 15px 12px;
  text-align: left;
  font-weight: 600;
  color: #495057;
  border-bottom: 2px solid #dee2e6;
  white-space: nowrap;
}

.buildings-table td {
  padding: 12px;
  border-bottom: 1px solid #dee2e6;
  vertical-align: middle;
}

/* 房间状态按钮样式 */
.status-btn {
  padding: 0.4rem 0.8rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.85rem;
  margin: 0 0.2rem;
  transition: all 0.3s ease;
}

.status-btn-available {
  background-color: #ff9800;
  color: white;
}

.status-btn-available:hover {
  background-color: #f57c00;
}

.status-btn-full {
  background-color: #ff9800;
  color: white;
}

.status-btn-full:hover {
  background-color: #f57c00;
}

.status-btn-maintenance {
  background-color: #4caf50;
  color: white;
}

.status-btn-maintenance:hover {
  background-color: #388e3c;
}

/* 房间状态标签样式 */
.status-available {
  color: #4caf50;
  font-weight: bold;
}

.status-full {
  color: #f44336;
  font-weight: bold;
}

.status-maintenance {
  color: #ff9800;
  font-weight: bold;
}

.buildings-table tbody tr:hover {
  background-color: #f8f9fa;
}

.buildings-table tbody tr:last-child td {
  border-bottom: none;
}

.actions-cell {
  white-space: nowrap;
}

.actions-cell .edit-btn,
.actions-cell .delete-btn {
  margin-right: 8px;
  padding: 6px 12px;
  font-size: 12px;
  border-radius: 4px;
  border: none;
  cursor: pointer;
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

/* 提示信息样式 */
.info-tip {
  background-color: #e3f2fd;
  border: 1px solid #2196f3;
  border-radius: 4px;
  padding: 12px;
  margin-bottom: 20px;
}

.info-tip p {
  margin: 0;
  color: #1976d2;
  font-size: 14px;
  line-height: 1.4;
}

/* 通知管理样式 */
.notice-filters {
  display: flex;
  gap: 1rem;
  margin-bottom: 1rem;
  padding: 1rem;
  background-color: #f8f9fa;
  border-radius: 8px;
}

.notice-filters select {
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 0.9rem;
}

.notice-list {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
  overflow: hidden;
}

.notice-table {
  width: 100%;
}

.notice-table table {
  width: 100%;
  border-collapse: collapse;
}

.notice-table th,
.notice-table td {
  padding: 12px;
  text-align: left;
  border-bottom: 1px solid #dee2e6;
}

.notice-table th {
  background-color: #f8f9fa;
  font-weight: 600;
  color: #495057;
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

.notice-status {
  padding: 0.25rem 0.5rem;
  border-radius: 12px;
  font-size: 0.8rem;
  font-weight: 500;
}

.notice-status.published {
  background-color: #e8f5e8;
  color: #388e3c;
}

.notice-status.draft {
  background-color: #fff3e0;
  color: #f57c00;
}

.btn-small {
  padding: 0.25rem 0.5rem;
  margin: 0 0.2rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.8rem;
  transition: all 0.3s ease;
}

.btn-small:hover {
  transform: translateY(-1px);
}

.btn-small.edit {
  background-color: #ff9800;
  color: white;
}

.btn-small.edit:hover {
  background-color: #f57c00;
}

.btn-small.publish {
  background-color: #4caf50;
  color: white;
}

.btn-small.publish:hover {
  background-color: #388e3c;
}

.btn-small.unpublish {
  background-color: #ff9800;
  color: white;
}

.btn-small.unpublish:hover {
  background-color: #f57c00;
}

.btn-small.delete {
  background-color: #f44336;
  color: white;
}

.btn-small.delete:hover {
  background-color: #d32f2f;
}

/* 通知详情模态框样式 */
.notice-view-modal {
  max-width: 600px;
}

.notice-meta {
  margin-bottom: 1.5rem;
}

.meta-item {
  margin-bottom: 0.5rem;
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.notice-content {
  border-top: 1px solid #dee2e6;
  padding-top: 1rem;
}

.notice-content h4 {
  margin-bottom: 0.5rem;
  color: #495057;
}

.content-text {
  background-color: #f8f9fa;
  padding: 1rem;
  border-radius: 4px;
  line-height: 1.6;
  white-space: pre-wrap;
}

</style>