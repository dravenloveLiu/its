<template>
  <div class="nav-footer">
    <p>智能交通系统 &copy; {{ currentYear }} All Rights Reserved</p>
  </div>
</template>

<script>
export default {
  name: 'NavFooter',
  computed: {
    currentYear() {
      return new Date().getFullYear()
    }
  }
}
</script>

<style scoped>
.nav-footer {
  height: 100%;
  font-size: 14px;
}
</style> 