# 智能交通系统

这是一个前后端分离的智能交通系统项目，前端基于Vue 2，后端基于Spring Boot + MyBatis Plus + MySQL。

## 项目结构

```
intelligent-transportation-system/
├── frontend/             # 前端项目（Vue 2）
├── backend/              # 后端项目（Spring Boot）
└── README.md             # 项目说明文档
```

## 前端部分

前端采用Vue 2 + Element UI + Axios技术栈，主要功能包括：

- 用户认证: 登录/登出
- 交通监控: 实时监控、历史记录、报警管理
- 信号控制: 信号灯控制、控制方案
- 交通分析: 流量分析、拥堵分析、数据报表
- 设备管理: 设备列表、设备维护
- 系统管理: 用户管理、角色管理、系统日志

## 后端部分

后端采用Spring Boot + MyBatis Plus + MySQL技术栈，主要功能包括：

- 用户认证: 登录/登出、令牌管理
- 交通监控: 实时数据采集、历史数据存储、报警管理
- 信号控制: 信号灯状态查询与控制、控制方案管理
- 交通分析: 流量数据分析、拥堵情况分析、数据报表生成
- 设备管理: 设备信息管理、设备状态监控、设备维护
- 系统管理: 用户管理、角色权限管理、系统日志

## 快速启动

### 前端启动

```bash
# 进入前端目录
cd frontend

# 安装依赖
npm install

# 启动开发服务器
npm run serve
```

### 后端启动

```bash
# 进入后端目录
cd backend

# 使用Maven编译
mvn clean package

# 运行项目
java -jar target/intelligent-transportation-0.0.1-SNAPSHOT.jar
```

## 系统要求

- Node.js 12+
- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+ 