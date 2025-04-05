<template>
  <div id="app">
    <!-- 登录页不显示导航结构，直接显示登录组件 -->
    <template v-if="$route.path === '/login'">
      <router-view />
    </template>
    
    <!-- 登录后显示完整的系统布局 -->
    <template v-else>
      <el-container v-if="isAuthenticated">
        <el-header>
          <nav-header />
        </el-header>
        <el-container>
          <el-aside width="200px">
            <nav-menu />
          </el-aside>
          <el-main>
            <router-view />
          </el-main>
        </el-container>
        <el-footer>
          <nav-footer />
        </el-footer>
      </el-container>
    </template>
  </div>
</template>

<script>
import NavHeader from '@/components/layout/NavHeader.vue'
import NavMenu from '@/components/layout/NavMenu.vue'
import NavFooter from '@/components/layout/NavFooter.vue'
import { mapGetters } from 'vuex'

export default {
  name: 'App',
  components: {
    NavHeader,
    NavMenu,
    NavFooter
  },
  computed: {
    ...mapGetters(['isAuthenticated'])
  }
}
</script>

<style>
#app {
  font-family: 'Microsoft YaHei', Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  height: 100vh;
  margin: 0;
  padding: 0;
}

body {
  margin: 0;
  padding: 0;
}

.el-header, .el-footer {
  background-color: #409EFF;
  color: #fff;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
  line-height: 200px;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
}
</style> 