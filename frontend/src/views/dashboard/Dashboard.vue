<template>
  <div class="dashboard-container">
    <el-row :gutter="20">
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="card-header">
            <span>设备状态</span>
          </div>
          <div class="card-content">
            <el-row>
              <el-col :span="12">
                <div class="status-item">
                  <div class="status-number">{{ deviceCount }}</div>
                  <div class="status-label">设备总数</div>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="status-item">
                  <div class="status-number">{{ onlineDeviceCount }}</div>
                  <div class="status-label">在线设备</div>
                </div>
              </el-col>
            </el-row>
            <el-progress :percentage="onlineRate" :format="formatOnlineRate"></el-progress>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="card-header">
            <span>交通状态</span>
          </div>
          <div class="card-content">
            <el-row>
              <el-col :span="12">
                <div class="status-item">
                  <div class="status-number">{{ trafficFlow }}</div>
                  <div class="status-label">当前流量(辆/h)</div>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="status-item">
                  <div class="status-number">{{ congestionIndex }}</div>
                  <div class="status-label">拥堵指数</div>
                </div>
              </el-col>
            </el-row>
            <div class="congestion-status">
              <el-tag :type="congestionTagType">{{ congestionStatus }}</el-tag>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="card-header">
            <span>报警信息</span>
          </div>
          <div class="card-content">
            <el-row>
              <el-col :span="12">
                <div class="status-item">
                  <div class="status-number">{{ alarmCount }}</div>
                  <div class="status-label">报警总数</div>
                </div>
              </el-col>
              <el-col :span="12">
                <div class="status-item">
                  <div class="status-number">{{ unhandledAlarmCount }}</div>
                  <div class="status-label">未处理报警</div>
                </div>
              </el-col>
            </el-row>
            <el-button type="danger" plain size="small" @click="goToAlarmPage">查看报警</el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>
    
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="16">
        <el-card class="box-card">
          <div slot="header" class="card-header">
            <span>交通流量趋势</span>
          </div>
          <div class="chart-container">
            <div id="trafficChart" style="width: 100%; height: 300px;"></div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="card-header">
            <span>系统通知</span>
          </div>
          <div class="notification-container">
            <el-timeline>
              <el-timeline-item
                v-for="(notification, index) in notifications"
                :key="index"
                :timestamp="notification.time"
                :type="notification.type">
                {{ notification.content }}
              </el-timeline-item>
            </el-timeline>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
// 模拟引入图表库
// import * as echarts from 'echarts'

export default {
  name: 'Dashboard',
  data() {
    return {
      chart: null,
      onlineDeviceCount: 65,
      trafficFlow: 1250,
      congestionIndex: 0.65,
      unhandledAlarmCount: 5,
      notifications: [
        { content: '路口A发生交通拥堵，已自动调整信号灯', time: '10分钟前', type: 'warning' },
        { content: '设备ID:12345上线', time: '30分钟前', type: 'success' },
        { content: '系统完成流量数据分析', time: '1小时前', type: 'info' },
        { content: '摄像头C离线，需要检修', time: '2小时前', type: 'danger' }
      ]
    }
  },
  computed: {
    ...mapGetters([
      'deviceCount',
      'alarmCount',
      'trafficData'
    ]),
    onlineRate() {
      return this.deviceCount > 0 ? (this.onlineDeviceCount / this.deviceCount * 100) : 0
    },
    congestionStatus() {
      if (this.congestionIndex < 0.4) return '通畅'
      if (this.congestionIndex < 0.6) return '轻度拥堵'
      if (this.congestionIndex < 0.8) return '中度拥堵'
      return '严重拥堵'
    },
    congestionTagType() {
      if (this.congestionIndex < 0.4) return 'success'
      if (this.congestionIndex < 0.6) return ''
      if (this.congestionIndex < 0.8) return 'warning'
      return 'danger'
    }
  },
  methods: {
    ...mapActions([
      'fetchDeviceCount',
      'fetchAlarmCount',
      'fetchTrafficData'
    ]),
    formatOnlineRate(percentage) {
      return `${percentage.toFixed(0)}%`
    },
    goToAlarmPage() {
      this.$router.push('/monitor/alarm')
    },
    initChart() {
      // 模拟图表加载
      console.log('加载交通流量图表')
    }
  },
  mounted() {
    // 获取数据
    this.fetchDeviceCount()
    this.fetchAlarmCount()
    this.fetchTrafficData()
    
    // 初始化图表
    this.$nextTick(() => {
      this.initChart()
    })
  }
}
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-content {
  padding: 10px 0;
}

.status-item {
  text-align: center;
  padding: 10px 0;
}

.status-number {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
}

.status-label {
  font-size: 14px;
  color: #666;
  margin-top: 5px;
}

.congestion-status {
  text-align: center;
  margin-top: 15px;
}

.chart-container {
  height: 300px;
}

.notification-container {
  height: 300px;
  overflow-y: auto;
}
</style> 