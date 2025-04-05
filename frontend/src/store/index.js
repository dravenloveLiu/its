import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: localStorage.getItem('token') || '',
    userInfo: JSON.parse(localStorage.getItem('userInfo')) || {},
    deviceCount: 0,
    alarmCount: 0,
    trafficData: null
  },
  mutations: {
    setToken(state, token) {
      state.token = token
      localStorage.setItem('token', token)
    },
    clearToken(state) {
      state.token = ''
      localStorage.removeItem('token')
    },
    setUserInfo(state, userInfo) {
      state.userInfo = userInfo
      localStorage.setItem('userInfo', JSON.stringify(userInfo))
    },
    clearUserInfo(state) {
      state.userInfo = {}
      localStorage.removeItem('userInfo')
    },
    setDeviceCount(state, count) {
      state.deviceCount = count
    },
    setAlarmCount(state, count) {
      state.alarmCount = count
    },
    setTrafficData(state, data) {
      state.trafficData = data
    }
  },
  actions: {
    login({ commit }, { token, userInfo }) {
      commit('setToken', token)
      commit('setUserInfo', userInfo)
    },
    logout({ commit }) {
      commit('clearToken')
      commit('clearUserInfo')
    },
    fetchDeviceCount({ commit }) {
      // 模拟异步请求
      setTimeout(() => {
        commit('setDeviceCount', 78)
      }, 500)
    },
    fetchAlarmCount({ commit }) {
      // 模拟异步请求
      setTimeout(() => {
        commit('setAlarmCount', 12)
      }, 500)
    },
    fetchTrafficData({ commit }) {
      // 模拟异步请求
      setTimeout(() => {
        const mockData = {
          flow: [120, 132, 101, 134, 90, 230, 210],
          congestion: [0.3, 0.4, 0.5, 0.3, 0.8, 0.7, 0.5],
          times: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
        }
        commit('setTrafficData', mockData)
      }, 500)
    }
  },
  getters: {
    isAuthenticated: state => !!state.token,
    username: state => state.userInfo.username || '游客',
    deviceCount: state => state.deviceCount,
    alarmCount: state => state.alarmCount,
    trafficData: state => state.trafficData
  }
}) 