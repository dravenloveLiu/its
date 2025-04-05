import axios from 'axios'

// 创建axios实例
const request = axios.create({
  baseURL: 'http://localhost:8080/api',
  timeout: 5000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    const res = response.data
    // 如果返回的状态码不是200，说明接口请求失败
    if (res.code !== 200) {
      // 处理错误信息
      console.error(res.message || '请求失败')
      
      // 401: Token失效
      if (res.code === 401) {
        // 清除用户登录状态
        localStorage.removeItem('token')
        localStorage.removeItem('userInfo')
        // 重定向到登录页
        location.reload()
      }
      
      return Promise.reject(new Error(res.message || '请求失败'))
    } else {
      return res
    }
  },
  error => {
    console.error('请求错误: ' + error)
    return Promise.reject(error)
  }
)

// API接口
const api = {
  // 用户登录
  login(data) {
    return request({
      url: '/auth/login',
      method: 'post',
      data
    })
  },
  
  // 用户登出
  logout() {
    return request({
      url: '/auth/logout',
      method: 'post'
    })
  },
  
  // 获取用户信息
  getUserInfo() {
    return request({
      url: '/user/info',
      method: 'get'
    })
  },
  
  // 设备相关接口
  device: {
    // 获取设备列表
    getList(params) {
      return request({
        url: '/device/list',
        method: 'get',
        params
      })
    },
    
    // 获取设备详情
    getDetail(id) {
      return request({
        url: `/device/${id}`,
        method: 'get'
      })
    },
    
    // 添加设备
    add(data) {
      return request({
        url: '/device',
        method: 'post',
        data
      })
    },
    
    // 更新设备
    update(id, data) {
      return request({
        url: `/device/${id}`,
        method: 'put',
        data
      })
    },
    
    // 删除设备
    delete(id) {
      return request({
        url: `/device/${id}`,
        method: 'delete'
      })
    }
  },
  
  // 交通监控相关接口
  monitor: {
    // 获取实时监控数据
    getRealtime() {
      return request({
        url: '/monitor/realtime',
        method: 'get'
      })
    },
    
    // 获取历史监控数据
    getHistory(params) {
      return request({
        url: '/monitor/history',
        method: 'get',
        params
      })
    },
    
    // 获取报警列表
    getAlarms(params) {
      return request({
        url: '/monitor/alarms',
        method: 'get',
        params
      })
    },
    
    // 处理报警
    handleAlarm(id, data) {
      return request({
        url: `/monitor/alarm/${id}`,
        method: 'put',
        data
      })
    }
  },
  
  // 信号控制相关接口
  signal: {
    // 获取信号灯状态
    getStatus(id) {
      return request({
        url: `/signal/${id}/status`,
        method: 'get'
      })
    },
    
    // 控制信号灯
    control(id, data) {
      return request({
        url: `/signal/${id}/control`,
        method: 'post',
        data
      })
    },
    
    // 获取控制方案列表
    getPlans() {
      return request({
        url: '/signal/plans',
        method: 'get'
      })
    },
    
    // 添加控制方案
    addPlan(data) {
      return request({
        url: '/signal/plan',
        method: 'post',
        data
      })
    }
  },
  
  // 交通分析相关接口
  analysis: {
    // 获取流量分析数据
    getFlowData(params) {
      return request({
        url: '/analysis/flow',
        method: 'get',
        params
      })
    },
    
    // 获取拥堵分析数据
    getCongestionData(params) {
      return request({
        url: '/analysis/congestion',
        method: 'get',
        params
      })
    },
    
    // 获取报表数据
    getReportData(params) {
      return request({
        url: '/analysis/report',
        method: 'get',
        params
      })
    }
  },
  
  // 系统管理相关接口
  system: {
    // 获取用户列表
    getUserList(params) {
      return request({
        url: '/system/users',
        method: 'get',
        params
      })
    },
    
    // 添加用户
    addUser(data) {
      return request({
        url: '/system/user',
        method: 'post',
        data
      })
    },
    
    // 更新用户
    updateUser(id, data) {
      return request({
        url: `/system/user/${id}`,
        method: 'put',
        data
      })
    },
    
    // 删除用户
    deleteUser(id) {
      return request({
        url: `/system/user/${id}`,
        method: 'delete'
      })
    },
    
    // 获取角色列表
    getRoleList() {
      return request({
        url: '/system/roles',
        method: 'get'
      })
    },
    
    // 获取系统日志
    getLogs(params) {
      return request({
        url: '/system/logs',
        method: 'get',
        params
      })
    }
  }
}

export default api 