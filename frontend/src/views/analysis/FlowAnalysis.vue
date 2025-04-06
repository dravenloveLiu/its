<template>
  <div class="flow-analysis">
    <h2>交通流量分析</h2>
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
          <el-form-item label="道路名称">
            <el-select v-model="queryParams.roadId" placeholder="请选择道路" clearable>
              <el-option
                v-for="item in roadOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="数据类型">
            <el-select v-model="queryParams.type" placeholder="请选择数据类型" clearable>
              <el-option label="车流量" value="flow"></el-option>
              <el-option label="平均速度" value="speed"></el-option>
              <el-option label="通行时间" value="time"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="fetchData">查询</el-button>
            <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-card>

    <el-row :gutter="20" class="chart-container">
      <el-col :span="24">
        <el-card class="chart-card">
          <div slot="header" class="clearfix">
            <span>交通流量趋势图</span>
            <el-radio-group v-model="chartViewType" size="small" style="float: right;">
              <el-radio-button label="day">日视图</el-radio-button>
              <el-radio-button label="week">周视图</el-radio-button>
              <el-radio-button label="month">月视图</el-radio-button>
            </el-radio-group>
          </div>
          <div class="chart" ref="flowChart"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="table-card">
      <div slot="header" class="clearfix">
        <span>交通流量数据表</span>
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
        <el-table-column prop="date" label="日期" width="180"></el-table-column>
        <el-table-column prop="roadName" label="道路名称" width="180"></el-table-column>
        <el-table-column prop="flowCount" label="车流量(辆/小时)"></el-table-column>
        <el-table-column prop="avgSpeed" label="平均速度(km/h)"></el-table-column>
        <el-table-column prop="avgTime" label="平均通行时间(分钟)"></el-table-column>
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
  name: 'FlowAnalysis',
  data() {
    return {
      loading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        startDate: '',
        endDate: '',
        roadId: '',
        type: 'flow'
      },
      dateRange: [],
      // 道路选项
      roadOptions: [
        { label: '中山北路', value: '1' },
        { label: '延安西路', value: '2' },
        { label: '南京东路', value: '3' },
        { label: '人民大道', value: '4' },
        { label: '世纪大道', value: '5' }
      ],
      chartViewType: 'day',
      // 表格数据
      tableData: [],
      total: 0,
      // 图表实例
      chartInstance: null
    }
  },
  mounted() {
    this.initChart()
    this.fetchData()
  },
  methods: {
    // 初始化图表
    initChart() {
      this.chartInstance = echarts.init(this.$refs.flowChart)
      window.addEventListener('resize', this.resizeChart)
    },
    // 调整图表大小
    resizeChart() {
      this.chartInstance && this.chartInstance.resize()
    },
    // 设置图表数据
    setChartData(data) {
      const xAxisData = data.map(item => item.timePoint)
      const seriesData = data.map(item => item.value)
      
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
          data: xAxisData
        },
        yAxis: {
          type: 'value',
          name: this.getYAxisName(),
          nameTextStyle: {
            padding: [0, 0, 0, 40]
          }
        },
        series: [
          {
            name: this.getSeriesName(),
            type: 'line',
            data: seriesData,
            smooth: true,
            markPoint: {
              data: [
                { type: 'max', name: '最大值' },
                { type: 'min', name: '最小值' }
              ]
            }
          }
        ],
        color: ['#409EFF']
      }
      
      this.chartInstance.setOption(option)
    },
    // 获取Y轴名称
    getYAxisName() {
      const typeMap = {
        flow: '车流量(辆/小时)',
        speed: '平均速度(km/h)',
        time: '通行时间(分钟)'
      }
      return typeMap[this.queryParams.type] || '车流量(辆/小时)'
    },
    // 获取系列名称
    getSeriesName() {
      const typeMap = {
        flow: '车流量',
        speed: '平均速度',
        time: '通行时间'
      }
      return typeMap[this.queryParams.type] || '车流量'
    },
    // 获取数据
    fetchData() {
      this.loading = true
      api.analysis.getFlowData(this.queryParams).then(res => {
        this.tableData = res.data.list || []
        this.total = res.data.total || 0
        
        // 处理图表数据
        const chartData = res.data.chartData || []
        this.setChartData(chartData)
      }).catch(err => {
        console.error('获取流量分析数据失败', err)
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
        roadId: '',
        type: 'flow'
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
    window.removeEventListener('resize', this.resizeChart)
    this.chartInstance && this.chartInstance.dispose()
  }
}
</script>

<style scoped>
.flow-analysis {
  padding: 20px;
}
.filter-card,
.chart-card,
.table-card {
  margin-bottom: 20px;
}
.filter-form {
  padding: 10px 0;
}
.chart-container {
  margin: 20px 0;
}
.chart {
  height: 400px;
}
.pagination-container {
  text-align: right;
  margin-top: 15px;
}
</style> 