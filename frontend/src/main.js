import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'

Vue.config.productionTip = false

// 使用Element UI
Vue.use(ElementUI)

// 配置axios
// 当后端API可用时，取消注释以下行并配置正确的baseURL
// axios.defaults.baseURL = 'http://localhost:8080/api'
Vue.prototype.$http = axios

// 添加一些初始状态到store
store.commit('setDeviceCount', 78)
store.commit('setAlarmCount', 12)
store.commit('setTrafficData', {
  flow: [120, 132, 101, 134, 90, 230, 210],
  congestion: [0.3, 0.4, 0.5, 0.3, 0.8, 0.7, 0.5],
  times: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app') 