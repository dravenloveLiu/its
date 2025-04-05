# 智能交通系统前端

这是一个基于Vue 2的智能交通系统前端项目。

## 项目结构

```
frontend/
├── public/                  # 静态资源
│   └── index.html           # HTML模板
├── src/                     # 源代码
│   ├── api/                 # API接口
│   ├── assets/              # 图片等资源
│   ├── components/          # 通用组件
│   │   └── layout/          # 布局组件
│   ├── router/              # 路由配置
│   ├── store/               # Vuex存储
│   ├── views/               # 页面视图
│   │   ├── auth/            # 认证相关页面
│   │   ├── dashboard/       # 仪表盘页面
│   │   └── ...              # 其他页面模块
│   ├── App.vue              # 根组件
│   └── main.js              # 入口文件
└── package.json             # 项目依赖
```

## 功能模块

- 用户认证: 登录/登出
- 交通监控: 实时监控、历史记录、报警管理
- 信号控制: 信号灯控制、控制方案
- 交通分析: 流量分析、拥堵分析、数据报表
- 设备管理: 设备列表、设备维护
- 系统管理: 用户管理、角色管理、系统日志

## 项目安装

```bash
# 安装依赖
npm install

# 启动开发服务器
npm run serve

# 构建生产环境版本
npm run build
```

## 技术栈

- Vue 2
- Vuex
- Vue Router
- Element UI
- Axios 