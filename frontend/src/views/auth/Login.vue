<template>
  <div class="login-container">
    <div class="login-form-container">
      <div class="login-header">
        <div class="logo-container">
          <i class="el-icon-s-platform logo-icon"></i>
        </div>
        <h1 class="login-title">智能交通系统</h1>
        <p class="login-subtitle">欢迎登录系统，请输入您的账号信息</p>
      </div>
      
      <el-form :model="loginForm" :rules="loginRules" ref="loginForm" class="login-form">
        <el-form-item prop="username">
          <el-input 
            v-model="loginForm.username" 
            prefix-icon="el-icon-user" 
            placeholder="用户名" 
            class="custom-input">
          </el-input>
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input 
            v-model="loginForm.password" 
            prefix-icon="el-icon-lock" 
            type="password" 
            placeholder="密码" 
            @keyup.enter.native="handleLogin"
            class="custom-input">
          </el-input>
        </el-form-item>
        
        <el-form-item prop="captcha">
          <div class="captcha-container">
            <el-input 
              v-model="loginForm.captcha" 
              prefix-icon="el-icon-key" 
              placeholder="验证码" 
              class="custom-input captcha-input"
              @keyup.enter.native="handleLogin">
            </el-input>
            <div class="captcha-image" @click="refreshCaptcha">
              <img :src="captchaImage" alt="验证码" />
              <div class="captcha-refresh-tip">点击刷新</div>
            </div>
          </div>
        </el-form-item>
        
        <el-form-item>
          <el-button 
            type="primary" 
            :loading="loading" 
            @click="handleLogin" 
            class="login-button">
            {{ loading ? '登录中...' : '登录' }}
          </el-button>
        </el-form-item>
      </el-form>
      
      <div class="login-footer">
        <p>智能交通管控平台 &copy; {{ new Date().getFullYear() }}</p>
      </div>
    </div>
  </div>
</template>

<script>
// 导入api
import api from '@/api'
import { mapActions } from 'vuex'

export default {
  name: 'Login',
  data() {
    return {
      loginForm: {
        username: '',
        password: '',
        captcha: ''
      },
      loginRules: {
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
        ],
        captcha: [
          { required: true, message: '请输入验证码', trigger: 'blur' },
          { min: 4, max: 6, message: '验证码长度不正确', trigger: 'blur' }
        ]
      },
      loading: false,
      redirect: undefined,
      captchaImage: '',
      correctCaptcha: '' // 在实际项目中，这个验证会在后端进行
    }
  },
  created() {
    // 获取重定向地址
    this.redirect = this.$route.query.redirect
    // 初始化验证码
    this.refreshCaptcha()
  },
  methods: {
    ...mapActions(['login']),
    // 生成随机验证码
    generateCaptcha() {
      // 生成4-6位随机字符（数字和字母）
      const characters = 'ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789';
      const length = Math.floor(Math.random() * 3) + 4; // 4-6位
      let result = '';
      for (let i = 0; i < length; i++) {
        result += characters.charAt(Math.floor(Math.random() * characters.length));
      }
      return result;
    },
    // 刷新验证码
    refreshCaptcha() {
      // 生成随机验证码
      this.correctCaptcha = this.generateCaptcha();
      
      // 在实际项目中，这里应该调用后端接口获取验证码图片
      // 这里使用canvas模拟生成验证码图片
      this.captchaImage = this.drawCaptcha(this.correctCaptcha);
      
      // 清空用户输入的验证码
      this.loginForm.captcha = '';
    },
    // 绘制验证码图片
    drawCaptcha(code) {
      const canvas = document.createElement('canvas');
      canvas.width = 120;
      canvas.height = 40;
      const ctx = canvas.getContext('2d');
      
      // 背景
      ctx.fillStyle = '#f7f9fa';
      ctx.fillRect(0, 0, canvas.width, canvas.height);
      
      // 干扰线
      for (let i = 0; i < 5; i++) {
        ctx.strokeStyle = this.getRandomColor(150, 200);
        ctx.beginPath();
        ctx.moveTo(Math.random() * canvas.width, Math.random() * canvas.height);
        ctx.lineTo(Math.random() * canvas.width, Math.random() * canvas.height);
        ctx.stroke();
      }
      
      // 干扰点
      for (let i = 0; i < 50; i++) {
        ctx.fillStyle = this.getRandomColor(150, 200);
        ctx.beginPath();
        ctx.arc(Math.random() * canvas.width, Math.random() * canvas.height, 1, 0, 2 * Math.PI);
        ctx.fill();
      }
      
      // 绘制验证码文字
      ctx.font = 'bold 24px Arial';
      ctx.textBaseline = 'middle';
      for (let i = 0; i < code.length; i++) {
        ctx.fillStyle = this.getRandomColor(50, 100);
        ctx.save();
        ctx.translate(20 * i + 15, 20);
        ctx.rotate((Math.random() - 0.5) * 0.3);
        ctx.fillText(code[i], 0, 0);
        ctx.restore();
      }
      
      return canvas.toDataURL('image/png');
    },
    // 获取随机颜色
    getRandomColor(min, max) {
      const r = min + Math.floor(Math.random() * (max - min));
      const g = min + Math.floor(Math.random() * (max - min));
      const b = min + Math.floor(Math.random() * (max - min));
      return `rgb(${r},${g},${b})`;
    },
    // 验证验证码
    validateCaptcha() {
      // 在实际项目中，验证码校验通常在后端进行
      // 这里简单地进行大小写不敏感的比较
      return this.loginForm.captcha.toLowerCase() === this.correctCaptcha.toLowerCase();
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          // 验证验证码
          if (!this.validateCaptcha()) {
            this.$message.error('验证码错误');
            this.refreshCaptcha(); // 刷新验证码
            return;
          }
          
          this.loading = true;
          
          // 实际使用时调用接口
          api.login({
            username: this.loginForm.username,
            password: this.loginForm.password,
            captcha: this.loginForm.captcha,
            captchaId: 'temp-captcha-id' // 临时值，实际项目中应该是从后端获取的验证码ID
          }).then(res => {
            // 登录成功
            this.login({
              token: res.data.token,
              userInfo: {
                username: res.data.username,
                role: 'admin' // 简化处理，应该从响应中获取角色
              }
            });
            this.$message.success('登录成功');
            
            // 登录成功后的跳转处理
            this.redirectToTargetRoute();
          }).catch(error => {
            console.error(error);
            this.$message.error('登录失败：' + (error.message || '未知错误'));
            this.refreshCaptcha(); // 刷新验证码
          }).finally(() => {
            this.loading = false;
          });
        }
      })
    },
    // 跳转到目标路由
    redirectToTargetRoute() {
      if (this.redirect) {
        // 如果有重定向地址，则跳转到该地址
        this.$router.push(this.redirect)
      } else {
        // 默认跳转到首页
        this.$router.push('/')
      }
    }
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(120deg, #2b5876, #4e4376);
  overflow: hidden;
}

.login-form-container {
  width: 400px;
  padding: 40px;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 12px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.2);
  backdrop-filter: blur(10px);
  transition: all 0.3s ease;
}

.login-form-container:hover {
  transform: translateY(-5px);
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3);
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.logo-container {
  margin-bottom: 15px;
}

.logo-icon {
  font-size: 50px;
  color: #409EFF;
  background-color: rgba(64, 158, 255, 0.1);
  padding: 15px;
  border-radius: 50%;
}

.login-title {
  margin: 0;
  font-size: 24px;
  font-weight: 600;
  color: #303133;
}

.login-subtitle {
  margin-top: 8px;
  color: #909399;
  font-size: 14px;
}

.login-form {
  margin-top: 30px;
}

.custom-input {
  margin-bottom: 10px;
}

.custom-input >>> .el-input__inner {
  height: 50px;
  border-radius: 8px;
  border: 1px solid #e0e0e0;
  padding-left: 45px;
  transition: all 0.3s;
}

.custom-input >>> .el-input__inner:focus,
.custom-input >>> .el-input__inner:hover {
  border-color: #409EFF;
  box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
}

.custom-input >>> .el-input__prefix {
  left: 15px;
  color: #909399;
}

.captcha-container {
  display: flex;
  align-items: center;
}

.captcha-input {
  flex: 1;
  margin-right: 10px;
}

.captcha-image {
  width: 120px;
  height: 50px;
  cursor: pointer;
  border-radius: 8px;
  overflow: hidden;
  position: relative;
  box-shadow: 0 3px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s;
}

.captcha-image:hover {
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
}

.captcha-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.captcha-refresh-tip {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  font-size: 10px;
  text-align: center;
  padding: 2px 0;
  transform: translateY(100%);
  transition: transform 0.3s;
}

.captcha-image:hover .captcha-refresh-tip {
  transform: translateY(0);
}

.login-button {
  width: 100%;
  height: 50px;
  font-size: 16px;
  font-weight: 500;
  border-radius: 8px;
  margin-top: 10px;
  background: linear-gradient(to right, #409EFF, #36D1DC);
  border: none;
  transition: all 0.3s ease;
}

.login-button:hover {
  background: linear-gradient(to right, #36D1DC, #409EFF);
  transform: translateY(-2px);
  box-shadow: 0 7px 14px rgba(64, 158, 255, 0.3);
}

.login-footer {
  margin-top: 30px;
  text-align: center;
  color: #909399;
  font-size: 12px;
}

/* 响应式设计 */
@media screen and (max-width: 480px) {
  .login-form-container {
    width: 90%;
    padding: 30px;
  }
  
  .captcha-container {
    flex-direction: column;
    align-items: stretch;
  }
  
  .captcha-input {
    margin-right: 0;
    margin-bottom: 10px;
  }
  
  .captcha-image {
    width: 100%;
  }
}
</style> 