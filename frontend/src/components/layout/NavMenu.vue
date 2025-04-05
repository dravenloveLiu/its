<template>
  <div class="nav-menu">
    <el-menu
      :default-active="activeIndex"
      class="el-menu-vertical"
      background-color="#D3DCE6"
      text-color="#333"
      active-text-color="#409EFF"
      router>
      <el-submenu index="1">
        <template slot="title">
          <i class="el-icon-location"></i>
          <span>交通监控</span>
        </template>
        <el-menu-item index="/monitor/realtime">实时监控</el-menu-item>
        <el-menu-item index="/monitor/history">历史记录</el-menu-item>
        <el-menu-item index="/monitor/alarm">报警管理</el-menu-item>
      </el-submenu>
      <el-submenu index="2">
        <template slot="title">
          <i class="el-icon-s-platform"></i>
          <span>信号控制</span>
        </template>
        <el-menu-item index="/signal/control">信号灯控制</el-menu-item>
        <el-menu-item index="/signal/plan">控制方案</el-menu-item>
      </el-submenu>
      <el-submenu index="3">
        <template slot="title">
          <i class="el-icon-s-data"></i>
          <span>交通分析</span>
        </template>
        <el-menu-item index="/analysis/flow">流量分析</el-menu-item>
        <el-menu-item index="/analysis/congestion">拥堵分析</el-menu-item>
        <el-menu-item index="/analysis/report">数据报表</el-menu-item>
      </el-submenu>
      <el-submenu index="4">
        <template slot="title">
          <i class="el-icon-s-grid"></i>
          <span>设备管理</span>
        </template>
        <el-menu-item index="/device/list">设备列表</el-menu-item>
        <el-menu-item index="/device/maintenance">设备维护</el-menu-item>
      </el-submenu>
      <el-submenu index="5">
        <template slot="title">
          <i class="el-icon-setting"></i>
          <span>系统管理</span>
        </template>
        <el-menu-item index="/system/user">用户管理</el-menu-item>
        <el-menu-item index="/system/role">角色管理</el-menu-item>
        <el-menu-item index="/system/log">系统日志</el-menu-item>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
export default {
  name: 'NavMenu',
  data() {
    return {
      activeIndex: '/monitor/realtime'
    }
  }
}
</script>

<style scoped>
.nav-menu {
  height: 100%;
}

.el-menu-vertical {
  height: 100%;
  border-right: none;
}
</style> 