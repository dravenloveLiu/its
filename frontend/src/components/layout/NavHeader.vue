<template>
  <div class="nav-header">
    <div class="logo">
      <!-- <img src="@/assets/logo.png" alt="Logo" width="40"> -->
      <i class="el-icon-s-platform" style="font-size: 28px;"></i>
      <span>智能交通系统</span>
    </div>
    <div class="user-info">
      <el-dropdown @command="handleCommand">
        <span class="el-dropdown-link">
          {{ username }}<i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="profile">个人信息</el-dropdown-item>
          <el-dropdown-item command="password">修改密码</el-dropdown-item>
          <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import api from '@/api'
import { mapGetters, mapActions } from 'vuex'

export default {
  name: 'NavHeader',
  computed: {
    ...mapGetters(['userInfo']),
    username() {
      return this.userInfo ? this.userInfo.username : '管理员'
    }
  },
  methods: {
    ...mapActions(['logout']),
    handleCommand(command) {
      if (command === 'logout') {
        this.handleLogout();
      } else if (command === 'profile') {
        // 跳转到个人信息页面
        this.$message.info('功能开发中');
      } else if (command === 'password') {
        // 跳转到修改密码页面
        this.$message.info('功能开发中');
      }
    },
    handleLogout() {
      this.$confirm('确定要退出登录吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用登出API
        api.logout().then(() => {
          // 清除本地存储的用户信息
          this.logout();
          
          // 显示成功消息
          this.$message.success('登出成功');
          
          // 跳转到登录页
          this.$router.push('/login');
        }).catch(error => {
          console.error('登出失败:', error);
          // 即使API调用失败，也清除本地存储的用户信息并跳转
          this.logout();
          this.$router.push('/login');
        });
      }).catch(() => {
        // 用户取消登出
      });
    }
  }
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