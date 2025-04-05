import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// 懒加载路由组件
const Login = () => import('@/views/auth/Login.vue')
const Dashboard = () => import('@/views/dashboard/Dashboard.vue')
const RealtimeMonitor = () => import('@/views/monitor/RealtimeMonitor.vue')
const HistoryMonitor = () => import('@/views/monitor/HistoryMonitor.vue')
const AlarmManagement = () => import('@/views/monitor/AlarmManagement.vue')
const SignalControl = () => import('@/views/signal/SignalControl.vue')
const SignalPlan = () => import('@/views/signal/SignalPlan.vue')
const FlowAnalysis = () => import('@/views/analysis/FlowAnalysis.vue')
const CongestionAnalysis = () => import('@/views/analysis/CongestionAnalysis.vue')
const DataReport = () => import('@/views/analysis/DataReport.vue')
const DeviceList = () => import('@/views/device/DeviceList.vue')
const DeviceMaintenance = () => import('@/views/device/DeviceMaintenance.vue')
const UserManagement = () => import('@/views/system/UserManagement.vue')
const RoleManagement = () => import('@/views/system/RoleManagement.vue')
const SystemLog = () => import('@/views/system/SystemLog.vue')

// 路由配置
const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: { requiresAuth: false }
  },
  {
    path: '/',
    redirect: '/dashboard',
    meta: { requiresAuth: true }
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: Dashboard,
    meta: { requiresAuth: true }
  },
  {
    path: '/monitor/realtime',
    name: 'RealtimeMonitor',
    component: RealtimeMonitor,
    meta: { requiresAuth: true }
  },
  {
    path: '/monitor/history',
    name: 'HistoryMonitor',
    component: HistoryMonitor,
    meta: { requiresAuth: true }
  },
  {
    path: '/monitor/alarm',
    name: 'AlarmManagement',
    component: AlarmManagement,
    meta: { requiresAuth: true }
  },
  {
    path: '/signal/control',
    name: 'SignalControl',
    component: SignalControl,
    meta: { requiresAuth: true }
  },
  {
    path: '/signal/plan',
    name: 'SignalPlan',
    component: SignalPlan,
    meta: { requiresAuth: true }
  },
  {
    path: '/analysis/flow',
    name: 'FlowAnalysis',
    component: FlowAnalysis,
    meta: { requiresAuth: true }
  },
  {
    path: '/analysis/congestion',
    name: 'CongestionAnalysis',
    component: CongestionAnalysis,
    meta: { requiresAuth: true }
  },
  {
    path: '/analysis/report',
    name: 'DataReport',
    component: DataReport,
    meta: { requiresAuth: true }
  },
  {
    path: '/device/list',
    name: 'DeviceList',
    component: DeviceList,
    meta: { requiresAuth: true }
  },
  {
    path: '/device/maintenance',
    name: 'DeviceMaintenance',
    component: DeviceMaintenance,
    meta: { requiresAuth: true }
  },
  {
    path: '/system/user',
    name: 'UserManagement',
    component: UserManagement,
    meta: { requiresAuth: true }
  },
  {
    path: '/system/role',
    name: 'RoleManagement',
    component: RoleManagement,
    meta: { requiresAuth: true }
  },
  {
    path: '/system/log',
    name: 'SystemLog',
    component: SystemLog,
    meta: { requiresAuth: true }
  },
  // 所有未匹配的路由重定向到登录页
  {
    path: '*',
    redirect: '/login'
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 全局路由守卫
router.beforeEach((to, from, next) => {
  const isAuthenticated = !!localStorage.getItem('token')
  
  if (to.matched.some(record => record.meta.requiresAuth)) {
    // 需要认证的路由
    if (!isAuthenticated) {
      // 如果未登录，重定向到登录页
      next({
        path: '/login',
        query: { redirect: to.fullPath } // 保存目标路由，便于登录后跳转
      })
    } else {
      next() // 已登录，继续访问
    }
  } else {
    // 不需要认证的路由
    if (isAuthenticated && to.path === '/login') {
      // 如果已登录且访问的是登录页，重定向到首页
      next({ path: '/' })
    } else {
      next() // 继续访问
    }
  }
})

export default router 