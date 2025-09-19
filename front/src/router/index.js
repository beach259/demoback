import { createRouter, createWebHistory } from 'vue-router'
import Login from '../components/Login.vue'
import HelloWorld from '../components/HelloWorld.vue'
import StudentDashboard from '../components/StudentDashboard.vue'
import AdminDashboard from '../components/AdminDashboard.vue'
import TeacherDashboard from '../components/TeacherDashboard.vue'

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/home',
    name: 'Home',
    component: HelloWorld
  },
  {
    path: '/student',
    name: 'StudentDashboard',
    component: StudentDashboard
  },
  {
    path: '/admin',
    name: 'AdminDashboard',
    component: AdminDashboard
  },
  {
    path: '/teacher',
    name: 'TeacherDashboard',
    component: TeacherDashboard
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router