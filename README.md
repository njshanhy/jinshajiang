# Spring Boot Flowable 请假审批系统

这是一个基于Spring Boot和Flowable 6.8.1的工作流管理系统，实现了完整的请假审批流程。

## 技术栈

- Spring Boot 2.7.0
- Flowable 6.8.1
- JPA/Hibernate
- H2 Database (开发环境)
- Maven
- Java 8+

## 功能特性

1. **请假申请**：员工可以提交请假申请
2. **流程管理**：基于Flowable的工作流程引擎
3. **审批流程**：
   - 请假天数 ≤ 3天：直接由HR审批
   - 请假天数 > 3天：先由部门领导审批，再由HR审批
4. **任务管理**：审批人可以查看和处理待办任务
5. **状态跟踪**：实时查看请假申请状态

## 流程设计

请假审批流程包括以下节点：
1. 员工提交请假申请
2. 系统根据请假天数自动路由到不同审批路径
3. 部门领导审批（仅适用于长假）
4. HR审批
5. 审批结果反馈

## 项目结构

```
src/main/java/com/example/leave/
├── SpringBootFlowableLeaveApplication.java  # 主启动类
├── controller/
│   ├── LeaveController.java                 # 请假相关API
│   └── ViewController.java                  # 视图控制器
├── entity/
│   └── Leave.java                           # 请假实体
├── dto/
│   ├── LeaveRequestDTO.java                 # 请假请求数据传输对象
│   └── ApprovalRequestDTO.java              # 审批请求数据传输对象
├── service/
│   ├── LeaveService.java                    # 请假业务逻辑
│   └── ProcessManagementService.java        # 流程管理服务
├── repository/
│   └── LeaveRepository.java                 # 请假数据访问层
└── config/
    └── FlowableConfig.java                  # Flowable配置
```

## 快速开始

1. 克隆项目
2. 确保安装了JDK 8+ 和 Maven
3. 运行 `mvn clean install`
4. 运行 `mvn spring-boot:run`
5. 访问 `http://localhost:8080`

## API 接口

### 提交请假申请
- POST `/api/leave/submit`
- 请求体：LeaveRequestDTO

### 获取用户待办任务
- GET `/api/leave/tasks/{userId}`

### 审批请假申请
- POST `/api/leave/approve`
- 请求体：ApprovalRequestDTO

### 查询用户请假记录
- GET `/api/leave/user/{userId}`

### 查询所有请假记录
- GET `/api/leave/all`

## 数据库

默认使用H2内存数据库，也可以在application.yml中配置MySQL等其他数据库。

## 前端界面

系统提供了一个简单的HTML界面，可以通过浏览器访问和操作请假审批流程。