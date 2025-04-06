<template>
  <div class="data-report">
    <h2>交通数据报表</h2>
    <el-card class="filter-card">
      <div class="filter-form">
        <el-form :inline="true" :model="queryParams" @submit.native.prevent>
          <el-form-item label="统计周期">
            <el-select v-model="queryParams.period" placeholder="请选择统计周期">
              <el-option label="日报表" value="day"></el-option>
              <el-option label="周报表" value="week"></el-option>
              <el-option label="月报表" value="month"></el-option>
              <el-option label="年报表" value="year"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="时间范围">
            <el-date-picker
              v-model="dateRange"
              :type="datePickerType"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd"
              @change="handleDateRangeChange"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="报表类型">
            <el-select v-model="queryParams.reportType" placeholder="请选择报表类型">
              <el-option label="流量报表" value="flow"></el-option>
              <el-option label="拥堵报表" value="congestion"></el-option>
              <el-option label="综合报表" value="comprehensive"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="fetchData">查询</el-button>
            <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>

    <div class="report-actions">
      <el-button-group>
        <el-button type="primary" icon="el-icon-download" @click="exportPDF">导出PDF</el-button>
        <el-button type="primary" icon="el-icon-download" @click="exportExcel">导出Excel</el-button>
        <el-button type="primary" icon="el-icon-printer" @click="printReport">打印报表</el-button>
      </el-button-group>
    </div>

    <el-card class="report-card" v-loading="loading">
      <div ref="reportContent">
        <div class="report-header">
          <h3>{{ getReportTitle() }}</h3>
          <p>生成时间: {{ getCurrentTime() }}</p>
        </div>

        <!-- 流量统计图表 -->
        <div v-if="queryParams.reportType === 'flow' || queryParams.reportType === 'comprehensive'">
          <h4>交通流量统计</h4>
          <div class="chart-container">
            <div class="chart" ref="flowChart"></div>
          </div>
          <el-table :data="flowData" border style="width: 100%" class="report-table">
            <el-table-column prop="date" label="日期"></el-table-column>
            <el-table-column prop="peakHourFlow" label="高峰小时流量(辆/小时)"></el-table-column>
            <el-table-column prop="avgFlow" label="平均流量(辆/小时)"></el-table-column>
            <el-table-column prop="totalFlow" label="总流量(辆)"></el-table-column>
          </el-table>
        </div>

        <!-- 拥堵统计图表 -->
        <div v-if="queryParams.reportType === 'congestion' || queryParams.reportType === 'comprehensive'" class="section">
          <h4>交通拥堵统计</h4>
          <div class="chart-container">
            <div class="chart" ref="congestionChart"></div>
          </div>
          <el-table :data="congestionData" border style="width: 100%" class="report-table">
            <el-table-column prop="date" label="日期"></el-table-column>
            <el-table-column prop="congestionIndex" label="拥堵指数"></el-table-column>
            <el-table-column prop="avgSpeed" label="平均车速(km/h)"></el-table-column>
            <el-table-column prop="congestionDuration" label="拥堵持续时间(小时)"></el-table-column>
          </el-table>
        </div>

        <!-- 交通事件统计 -->
        <div v-if="queryParams.reportType === 'comprehensive'" class="section">
          <h4>交通事件统计</h4>
          <div class="chart-container">
            <div class="chart" ref="eventChart"></div>
          </div>
          <el-table :data="eventData" border style="width: 100%" class="report-table">
            <el-table-column prop="date" label="日期"></el-table-column>
            <el-table-column prop="accidentCount" label="事故数量"></el-table-column>
            <el-table-column prop="breakdownCount" label="故障数量"></el-table-column>
            <el-table-column prop="constructionCount" label="施工数量"></el-table-column>
            <el-table-column prop="otherCount" label="其他事件"></el-table-column>
          </el-table>
        </div>

        <div class="report-footer">
          <p>本报表数据来源于交通监控系统，仅供参考</p>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import api from '@/api'
import * as echarts from 'echarts'

export default {
  name: 'DataReport',
  data() {
    return {
      loading: false,
      // 查询参数
      queryParams: {
        period: 'month',
        startDate: '',
        endDate: '',
        reportType: 'comprehensive'
      },
      dateRange: [],
      // 报表数据
      flowData: [],
      congestionData: [],
      eventData: [],
      // 图表实例
      flowChartInstance: null,
      congestionChartInstance: null,
      eventChartInstance: null
    }
  },
  computed: {
    datePickerType() {
      const typeMap = {
        day: 'daterange',
        week: 'daterange',
        month: 'monthrange',
        year: 'yearrange'
      }
      return typeMap[this.queryParams.period] || 'daterange'
    }
  },
  mounted() {
    this.fetchData()
  },
  methods: {
    // 获取报表标题
    getReportTitle() {
      const periodMap = {
        day: '日',
        week: '周',
        month: '月',
        year: '年'
      }
      const typeMap = {
        flow: '流量',
        congestion: '拥堵',
        comprehensive: '综合'
      }
      
      const period = periodMap[this.queryParams.period] || ''
      const type = typeMap[this.queryParams.reportType] || ''
      
      return `交通${type}${period}报表 (${this.queryParams.startDate} 至 ${this.queryParams.endDate})`
    },
    // 获取当前时间
    getCurrentTime() {
      const now = new Date()
      return now.toLocaleString()
    },
    // 初始化图表
    initCharts() {
      if (this.queryParams.reportType === 'flow' || this.queryParams.reportType === 'comprehensive') {
        this.flowChartInstance = echarts.init(this.$refs.flowChart)
        this.setFlowChartData()
      }
      
      if (this.queryParams.reportType === 'congestion' || this.queryParams.reportType === 'comprehensive') {
        this.congestionChartInstance = echarts.init(this.$refs.congestionChart)
        this.setCongestionChartData()
      }
      
      if (this.queryParams.reportType === 'comprehensive') {
        this.eventChartInstance = echarts.init(this.$refs.eventChart)
        this.setEventChartData()
      }
      
      window.addEventListener('resize', this.resizeCharts)
    },
    // 调整图表大小
    resizeCharts() {
      this.flowChartInstance && this.flowChartInstance.resize()
      this.congestionChartInstance && this.congestionChartInstance.resize()
      this.eventChartInstance && this.eventChartInstance.resize()
    },
    // 设置流量图表数据
    setFlowChartData() {
      const dates = this.flowData.map(item => item.date)
      const peakHourFlow = this.flowData.map(item => item.peakHourFlow)
      const avgFlow = this.flowData.map(item => item.avgFlow)
      
      const option = {
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['高峰小时流量', '平均流量']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: dates
        },
        yAxis: {
          type: 'value',
          name: '流量(辆/小时)'
        },
        series: [
          {
            name: '高峰小时流量',
            type: 'line',
            data: peakHourFlow
          },
          {
            name: '平均流量',
            type: 'line',
            data: avgFlow
          }
        ]
      }
      
      this.flowChartInstance.setOption(option)
    },
    // 设置拥堵图表数据
    setCongestionChartData() {
      const dates = this.congestionData.map(item => item.date)
      const congestionIndex = this.congestionData.map(item => item.congestionIndex)
      const avgSpeed = this.congestionData.map(item => item.avgSpeed)
      
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        legend: {
          data: ['拥堵指数', '平均车速']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            data: dates
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '拥堵指数',
            min: 0,
            max: 10,
            interval: 2
          },
          {
            type: 'value',
            name: '车速(km/h)',
            min: 0,
            max: 100,
            interval: 20
          }
        ],
        series: [
          {
            name: '拥堵指数',
            type: 'bar',
            data: congestionIndex
          },
          {
            name: '平均车速',
            type: 'line',
            yAxisIndex: 1,
            data: avgSpeed
          }
        ]
      }
      
      this.congestionChartInstance.setOption(option)
    },
    // 设置事件图表数据
    setEventChartData() {
      const dates = this.eventData.map(item => item.date)
      const accidentCount = this.eventData.map(item => item.accidentCount)
      const breakdownCount = this.eventData.map(item => item.breakdownCount)
      const constructionCount = this.eventData.map(item => item.constructionCount)
      const otherCount = this.eventData.map(item => item.otherCount)
      
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['事故', '故障', '施工', '其他']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: dates
        },
        yAxis: {
          type: 'value',
          name: '事件数量'
        },
        series: [
          {
            name: '事故',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: accidentCount
          },
          {
            name: '故障',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: breakdownCount
          },
          {
            name: '施工',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: constructionCount
          },
          {
            name: '其他',
            type: 'bar',
            stack: 'total',
            emphasis: {
              focus: 'series'
            },
            data: otherCount
          }
        ]
      }
      
      this.eventChartInstance.setOption(option)
    },
    // 获取数据
    fetchData() {
      this.loading = true
      api.analysis.getReportData(this.queryParams).then(res => {
        const data = res.data || {}
        
        // 处理流量数据
        this.flowData = data.flowData || []
        
        // 处理拥堵数据
        this.congestionData = data.congestionData || []
        
        // 处理事件数据
        this.eventData = data.eventData || []
        
        // 初始化图表
        this.$nextTick(() => {
          this.initCharts()
        })
      }).catch(err => {
        console.error('获取报表数据失败', err)
        this.$message.error('获取数据失败，请稍后重试')
      }).finally(() => {
        this.loading = false
      })
    },
    // 处理日期范围变化
    handleDateRangeChange(val) {
      if (val) {
        this.queryParams.startDate = val[0]
        this.queryParams.endDate = val[1]
      } else {
        this.queryParams.startDate = ''
        this.queryParams.endDate = ''
      }
    },
    // 重置查询条件
    resetQuery() {
      this.dateRange = []
      this.queryParams = {
        period: 'month',
        startDate: '',
        endDate: '',
        reportType: 'comprehensive'
      }
      this.fetchData()
    },
    // 导出PDF
    exportPDF() {
      this.$message.success('PDF导出功能开发中')
      // TODO: 实现PDF导出功能
    },
    // 导出Excel
    exportExcel() {
      this.$message.success('Excel导出功能开发中')
      // TODO: 实现Excel导出功能
    },
    // 打印报表
    printReport() {
      this.$message.success('打印功能开发中')
      // TODO: 实现打印功能
    }
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.resizeCharts)
    
    // 销毁图表实例
    this.flowChartInstance && this.flowChartInstance.dispose()
    this.congestionChartInstance && this.congestionChartInstance.dispose()
    this.eventChartInstance && this.eventChartInstance.dispose()
  }
}
</script>

<style scoped>
.data-report {
  padding: 20px;
}
.filter-card {
  margin-bottom: 20px;
}
.filter-form {
  padding: 10px 0;
}
.report-actions {
  margin-bottom: 20px;
  text-align: right;
}
.report-card {
  margin-bottom: 20px;
}
.report-header {
  text-align: center;
  margin-bottom: 30px;
}
.report-header h3 {
  margin-bottom: 5px;
}
.section {
  margin-top: 30px;
}
.chart-container {
  margin: 20px 0;
}
.chart {
  height: 300px;
}
.report-table {
  margin-bottom: 30px;
}
.report-footer {
  margin-top: 30px;
  text-align: center;
  color: #606266;
  font-size: 12px;
}
</style> 