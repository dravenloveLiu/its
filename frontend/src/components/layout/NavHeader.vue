<template>
  <div class="nav-header">
    <div class="logo">
      <!-- <img src="@/assets/logo.png" alt="Logo" width="40"> -->
      <i class="el-icon-s-platform" style="font-size: 28px;"></i>
      <span>智能交通系统</span>
    </div>
    <div class="user-info">
      <el-dropdown>
        <span class="el-dropdown-link">
          管理员<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>个人信息</el-dropdown-item>
          <el-dropdown-item>修改密码</el-dropdown-item>
          <el-dropdown-item divided>退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: 'NavHeader'
}
</script>

<style scoped>
.nav-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 100%;
  padding: 0 20px;
}

.logo {
  display: flex;
  align-items: center;
}

.logo span {
  margin-left: 10px;
  font-size: 18px;
  font-weight: bold;
}

.user-info {
  color: #fff;
  cursor: pointer;
}

.el-dropdown-link {
  color: #fff;
}
</style> 