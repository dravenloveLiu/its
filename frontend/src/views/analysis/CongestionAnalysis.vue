<template>
  <div class="congestion-analysis">
    <h2>交通拥堵分析</h2>
    <el-card class="filter-card">
      <div class="filter-form">
        <el-form :inline="true" :model="queryParams" @submit.native.prevent>
          <el-form-item label="时间范围">
            <el-date-picker
              v-model="dateRange"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              value-format="yyyy-MM-dd"
              @change="handleDateRangeChange"
            ></el-date-picker>
          </el-form-item>
          <el-form-item label="时段">
            <el-select v-model="queryParams.timeSlot" placeholder="请选择时段" clearable>
              <el-option label="早高峰(7:00-9:00)" value="morning"></el-option>
              <el-option label="晚高峰(17:00-19:00)" value="evening"></el-option>
              <el-option label="平峰时段" value="normal"></el-option>
              <el-option label="全天" value="all"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="拥堵等级">
            <el-select v-model="queryParams.congestionLevel" placeholder="请选择拥堵等级" clearable>
              <el-option label="畅通" value="smooth"></el-option>
              <el-option label="轻度拥堵" value="light"></el-option>
              <el-option label="中度拥堵" value="medium"></el-option>
              <el-option label="重度拥堵" value="heavy"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="fetchData">查询</el-button>
            <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>

    <el-row :gutter="20" class="map-container">
      <el-col :span="24">
        <el-card class="map-card">
          <div slot="header" class="clearfix">
            <span>拥堵热力图</span>
            <el-radio-group v-model="mapViewType" size="small" style="float: right;">
              <el-radio-button label="realtime">实时</el-radio-button>
              <el-radio-button label="historical">历史</el-radio-button>
              <el-radio-button label="prediction">预测</el-radio-button>
            </el-radio-group>
          </div>
          <div class="map" ref="congestionMap"></div>
          <div class="map-legend">
            <div class="legend-item">
              <span class="color-block smooth"></span>
              <span class="legend-text">畅通</span>
            </div>
            <div class="legend-item">
              <span class="color-block light"></span>
              <span class="legend-text">轻度拥堵</span>
            </div>
            <div class="legend-item">
              <span class="color-block medium"></span>
              <span class="legend-text">中度拥堵</span>
            </div>
            <div class="legend-item">
              <span class="color-block heavy"></span>
              <span class="legend-text">重度拥堵</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="12">
        <el-card class="stat-card">
          <div slot="header" class="clearfix">
            <span>拥堵路段统计</span>
          </div>
          <div class="chart" ref="roadChart"></div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="stat-card">
          <div slot="header" class="clearfix">
            <span>拥堵时段分布</span>
          </div>
          <div class="chart" ref="timeChart"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="table-card">
      <div slot="header" class="clearfix">
        <span>拥堵路段详情</span>
        <el-button
          style="float: right; padding: 3px 0"
          type="text"
          icon="el-icon-download"
          @click="exportData"
        >导出数据</el-button>
      </div>
      <el-table
        :data="tableData"
        border
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="date" label="日期" width="120"></el-table-column>
        <el-table-column prop="time" label="时间" width="120"></el-table-column>
        <el-table-column prop="roadName" label="道路名称" width="180"></el-table-column>
        <el-table-column prop="section" label="路段" width="180"></el-table-column>
        <el-table-column prop="congestionLevel" label="拥堵等级">
          <template slot-scope="scope">
            <el-tag :type="getCongestionLevelType(scope.row.congestionLevel)">
              {{ getCongestionLevelText(scope.row.congestionLevel) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="avgSpeed" label="平均速度(km/h)"></el-table-column>
        <el-table-column prop="duration" label="持续时间(分钟)"></el-table-column>
      </el-table>
      <div class="pagination-container">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="queryParams.pageNum"
          :page-sizes="[10, 20, 50, 100]"
          :page-size="queryParams.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
        ></el-pagination>
      </div>
    </el-card>
  </div>
</template>

<script>
import api from '@/api'
import * as echarts from 'echarts'

export default {
  name: 'CongestionAnalysis',
  data() {
    return {
      loading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        startDate: '',
        endDate: '',
        timeSlot: 'all',
        congestionLevel: ''
      },
      dateRange: [],
      mapViewType: 'realtime',
      // 表格数据
      tableData: [],
      total: 0,
      // 图表实例
      mapInstance: null,
      roadChartInstance: null,
      timeChartInstance: null
    }
  },
  mounted() {
    this.initCharts()
    this.fetchData()
  },
  methods: {
    // 初始化图表
    initCharts() {
      // 模拟一个地图实例，实际项目中可以使用高德地图、百度地图等第三方地图API
      this.mapInstance = document.getElementById('congestionMap')
      
      // 拥堵路段统计图表
      this.roadChartInstance = echarts.init(this.$refs.roadChart)
      // 拥堵时段分布图表
      this.timeChartInstance = echarts.init(this.$refs.timeChart)
      
      window.addEventListener('resize', this.resizeCharts)
    },
    // 调整图表大小
    resizeCharts() {
      this.roadChartInstance && this.roadChartInstance.resize()
      this.timeChartInstance && this.timeChartInstance.resize()
    },
    // 设置路段统计图表数据
    setRoadChartData(data) {
      const option = {
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: '拥堵路段',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '14',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: data
          }
        ]
      }
      
      this.roadChartInstance.setOption(option)
    },
    // 设置时段分布图表数据
    setTimeChartData(data) {
      const option = {
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: {
          type: 'category',
          data: ['0-2', '2-4', '4-6', '6-8', '8-10', '10-12', '12-14', '14-16', '16-18', '18-20', '20-22', '22-24']
        },
        yAxis: {
          type: 'value',
          name: '拥堵指数',
          nameTextStyle: {
            padding: [0, 0, 0, 40]
          }
        },
        series: [
          {
            name: '拥堵指数',
            type: 'bar',
            data: data
          }
        ],
        color: ['#FF9800']
      }
      
      this.timeChartInstance.setOption(option)
    },
    // 获取拥堵等级文字
    getCongestionLevelText(level) {
      const levelMap = {
        'smooth': '畅通',
        'light': '轻度拥堵',
        'medium': '中度拥堵',
        'heavy': '重度拥堵'
      }
      return levelMap[level] || '未知'
    },
    // 获取拥堵等级标签类型
    getCongestionLevelType(level) {
      const typeMap = {
        'smooth': 'success',
        'light': 'info',
        'medium': 'warning',
        'heavy': 'danger'
      }
      return typeMap[level] || 'info'
    },
    // 获取数据
    fetchData() {
      this.loading = true
      api.analysis.getCongestionData(this.queryParams).then(res => {
        this.tableData = res.data.list || []
        this.total = res.data.total || 0
        
        // 处理图表数据
        const roadChartData = res.data.roadStats || []
        this.setRoadChartData(roadChartData)
        
        const timeChartData = res.data.timeStats || []
        this.setTimeChartData(timeChartData)
      }).catch(err => {
        console.error('获取拥堵分析数据失败', err)
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
        pageNum: 1,
        pageSize: 10,
        startDate: '',
        endDate: '',
        timeSlot: 'all',
        congestionLevel: ''
      }
      this.fetchData()
    },
    // 处理分页大小变化
    handleSizeChange(val) {
      this.queryParams.pageSize = val
      this.fetchData()
    },
    // 处理页码变化
    handleCurrentChange(val) {
      this.queryParams.pageNum = val
      this.fetchData()
    },
    // 导出数据
    exportData() {
      this.$message.success('数据导出功能开发中')
      // TODO: 实现数据导出功能
    }
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.resizeCharts)
    this.roadChartInstance && this.roadChartInstance.dispose()
    this.timeChartInstance && this.timeChartInstance.dispose()
  }
}
</script>

<style scoped>
.congestion-analysis {
  padding: 20px;
}
.filter-card,
.map-card,
.stat-card,
.table-card {
  margin-bottom: 20px;
}
.filter-form {
  padding: 10px 0;
}
.map-container {
  margin: 20px 0;
}
.map {
  height: 400px;
  background-color: #f5f7fa;
  position: relative;
}
.map-legend {
  position: absolute;
  bottom: 20px;
  right: 20px;
  background-color: rgba(255, 255, 255, 0.8);
  padding: 10px;
  border-radius: 4px;
  display: flex;
  flex-direction: column;
}
.legend-item {
  display: flex;
  align-items: center;
  margin-bottom: 5px;
}
.color-block {
  width: 20px;
  height: 10px;
  margin-right: 5px;
}
.color-block.smooth {
  background-color: #67C23A;
}
.color-block.light {
  background-color: #409EFF;
}
.color-block.medium {
  background-color: #E6A23C;
}
.color-block.heavy {
  background-color: #F56C6C;
}
.chart {
  height: 300px;
}
.pagination-container {
  text-align: right;
  margin-top: 15px;
}
</style> 