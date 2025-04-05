# 智能交通系统后端

这是一个基于Spring Boot + MyBatis Plus + MySQL的智能交通系统后端项目。

## 项目结构

```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── example/
│   │   │           └── intelligenttransportation/
│   │   │               ├── config/           # 配置类
│   │   │               ├── controller/       # 控制器
│   │   │               ├── entity/           # 实体类
│   │   │               ├── mapper/           # MyBatis映射接口
│   │   │               ├── service/          # 服务接口
│   │   │               │   └── impl/         # 服务实现类
│   │   │               ├── util/             # 工具类
│   │   │               ├── vo/               # 视图对象
│   │   │               └── IntelligentTransportationApplication.java # 主应用类
│   │   ├── resources/
│   │   │   ├── mapper/          # MyBatis XML映射文件
│   │   │   └── application.yml  # 应用配置
│   │   └── webapp/
│   └── test/                    # 测试代码
└── pom.xml                      # Maven配置
```

## 功能模块

- 用户认证: 登录/登出、令牌管理
- 交通监控: 实时数据采集、历史数据存储、报警管理
- 信号控制: 信号灯状态查询与控制、控制方案管理
- 交通分析: 流量数据分析、拥堵情况分析、数据报表生成
- 设备管理: 设备信息管理、设备状态监控、设备维护
- 系统管理: 用户管理、角色权限管理、系统日志

## 技术栈

- Spring Boot 2.5
- Spring Security
- MyBatis Plus
- MySQL
- Redis
- JWT
- Swagger

## 开发环境搭建

1. 安装JDK 1.8+
2. 安装Maven 3.6+
3. 安装MySQL 5.7+
4. 安装Redis (可选)
5. 导入项目到IDE (推荐使用IntelliJ IDEA)
6. 创建数据库intelligent_transportation
7. 运行项目

## 项目启动

```bash
# 使用Maven编译
mvn clean package

# 运行项目
java -jar target/intelligent-transportation-0.0.1-SNAPSHOT.jar
```

访问API文档: http://localhost:8080/api/swagger-ui/index.html 