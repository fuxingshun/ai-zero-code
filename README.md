# AI Zero Code

AI Zero Code 是一个面向「一句话生成应用」的零代码平台。用户输入自然语言需求后，系统会自动选择合适的生成模式，流式生成前端代码，并提供在线预览、部署访问、源码下载、应用管理和精选案例展示等能力。

项目采用 Spring Boot + Vue 3 前后端分离架构，后端集成 LangChain4j、LangGraph4j、DeepSeek / 通义千问兼容模型、Redis、MySQL、腾讯云 COS、Prometheus 监控等组件，适合作为 AI 应用生成平台、AI Agent 工作流和低代码/零代码系统的学习与二次开发项目。

## 功能特性

- 自然语言创建应用：输入一句需求，自动创建应用并进入生成流程
- 智能生成模式路由：根据需求自动选择原生 HTML、多文件项目或 Vue 工程模式
- 流式 AI 对话生成：基于 SSE 实时返回生成过程和模型输出
- 工程化代码落盘：自动解析、保存生成代码到本地项目目录
- Vue 工程构建：对 Vue 项目执行构建，生成可部署产物
- 一键部署预览：将生成结果发布到本地部署目录，并生成访问链接
- 应用源码下载：支持将生成项目打包为 zip 下载
- 应用封面截图：部署后异步截图并上传对象存储，作为作品封面
- 用户体系：注册、登录、登出、当前用户信息、权限校验
- 管理后台：用户管理、应用管理、对话记录管理
- 精选案例广场：展示高优先级应用，便于其他用户浏览作品
- 限流与缓存：基于 Redisson 的用户级限流，Redis 缓存精选应用分页
- 监控能力：Actuator + Prometheus 指标暴露，可接入 Grafana

## 技术栈

**后端**

- Java 21
- Spring Boot 3.5.x
- MyBatis-Flex
- MySQL
- Redis / Spring Session / Spring Cache
- LangChain4j
- LangGraph4j
- Reactor / SSE
- Redisson
- Knife4j / OpenAPI
- Selenium / WebDriverManager
- 腾讯云 COS
- Micrometer + Prometheus

**前端**

- Vue 3
- TypeScript
- Vite
- Vue Router
- Pinia
- Ant Design Vue
- Axios
- Markdown-it / Highlight.js

## 项目结构

```text
ai-zero-code
├── ai-zero-code-frontend/        # Vue 3 前端项目
├── ai-zero-code-microservice/    # 微服务拆分版本实验目录
├── sql/                          # 数据库建表脚本
├── src/main/java/                # Spring Boot 后端源码
│   └── com/fxs/aizerocode/
│       ├── ai/                   # AI 服务、模型路由、提示词护栏
│       ├── controller/           # REST API 与 SSE 接口
│       ├── core/                 # 代码解析、保存、构建核心流程
│       ├── langgraph4j/          # LangGraph4j 工作流
│       ├── monitor/              # AI 调用监控上下文与指标
│       ├── ratelimit/            # 限流注解与切面
│       └── service/              # 业务服务
├── src/main/resources/
│   ├── mapper/                   # MyBatis-Flex XML
│   ├── prompt/                   # AI 系统提示词
│   └── application*.yml          # 应用配置
└── tmp/                          # 生成代码与部署产物目录
```

## 快速开始

### 1. 克隆项目

```bash
git clone https://github.com/fuxingshun/ai-zero-code.git
cd ai-zero-code
```

### 2. 准备环境

请先安装并启动：

- JDK 21+
- Maven 3.9+
- Node.js 22+
- MySQL 8.x
- Redis 6.x+

### 3. 初始化数据库

执行建表脚本：

```bash
mysql -u root -p < sql/create_table.sql
```

默认数据库名为 `ai_zero_code`，如需修改请同步调整后端配置。

### 4. 配置后端

后端主配置位于：

- `src/main/resources/application.yml`
- `src/main/resources/application-local.yml`
- `src/main/resources/application-prod.yml`

本地运行前，请至少配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ai_zero_code?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: your_mysql_user
    password: your_mysql_password
  data:
    redis:
      host: localhost
      port: 6379
      password:

langchain4j:
  open-ai:
    chat-model:
      base-url: your_model_base_url
      api-key: your_api_key
      model-name: your_model_name
    streaming-chat-model:
      base-url: your_model_base_url
      api-key: your_api_key
      model-name: your_streaming_model_name
    reasoning-streaming-chat-model:
      base-url: your_model_base_url
      api-key: your_api_key
      model-name: your_reasoning_model_name
    routing-chat-model:
      base-url: your_model_base_url
      api-key: your_api_key
      model-name: your_routing_model_name
```

可选能力：

- `cos.client.*`：用于上传应用截图封面
- `pexels.api-key`：用于图片素材检索
- `dashscope.*`：用于通义千问路由模型或图像生成
- `code.deploy-host`：生成应用的部署访问域名

> 注意：请不要将真实数据库密码、模型 API Key、云厂商密钥提交到公开仓库。建议使用环境变量、私有配置文件或部署平台密钥管理功能注入。

### 5. 启动后端

```bash
./mvnw spring-boot:run
```

Windows 可使用：

```bash
mvnw.cmd spring-boot:run
```

默认后端地址：

```text
http://localhost:8123/api
```

接口文档地址：

```text
http://localhost:8123/api/doc.html
```

Prometheus 指标地址：

```text
http://localhost:8123/api/actuator/prometheus
```

### 6. 配置并启动前端

```bash
cd ai-zero-code-frontend
npm install
npm run dev
```

默认前端开发服务由 Vite 启动，接口请求会通过 `vite.config.ts` 代理到 `http://localhost:8123`。

前端环境变量示例：

```env
VITE_DEPLOY_DOMAIN=http://localhost
VITE_API_BASE_URL=/api
```

## 常用命令

后端：

```bash
# 启动后端
./mvnw spring-boot:run

# 运行测试
./mvnw test

# 打包
./mvnw clean package
```

前端：

```bash
cd ai-zero-code-frontend

# 开发环境
npm run dev

# 类型检查 + 构建
npm run build

# 仅构建
npm run pure-build

# 代码格式化
npm run format

# ESLint 修复
npm run lint

# 根据 OpenAPI 生成前端接口类型
npm run openapi2ts
```

## 主要页面

- `/`：首页、提示词输入、我的作品、精选案例
- `/user/login`：用户登录
- `/user/register`：用户注册
- `/app/chat/:id`：应用 AI 对话生成页
- `/app/edit/:id`：应用编辑页
- `/admin/userManage`：用户管理
- `/admin/appManage`：应用管理
- `/admin/chatManage`：对话记录管理

## 核心流程

1. 用户在首页输入应用需求并创建应用
2. 后端保存应用信息，并调用模型路由服务选择生成类型
3. 用户进入对话页，通过 SSE 发起 AI 代码生成
4. 后端调用 LangChain4j 服务流式生成代码
5. 系统解析生成结果并保存到 `tmp/code_output`
6. 用户点击部署后，系统复制或构建产物到 `tmp/code_deploy`
7. 部署成功后返回访问地址，并异步生成封面截图
8. 用户可以继续对话迭代、预览作品或下载源码

## 生成类型

| 类型 | 值 | 说明 |
| --- | --- | --- |
| 原生 HTML | `html` | 适合单页静态页面 |
| 原生多文件 | `multi_file` | 适合 HTML/CSS/JS 分离的前端项目 |
| Vue 工程 | `vue_project` | 适合更复杂的组件化 Vue 应用 |

## API 概览

部分核心接口：

- `POST /api/user/register`：用户注册
- `POST /api/user/login`：用户登录
- `GET /api/user/get/login`：获取当前登录用户
- `POST /api/app/add`：创建应用
- `GET /api/app/chat/gen/code`：SSE 流式生成代码
- `POST /api/app/deploy`：部署应用
- `GET /api/app/download/{appId}`：下载应用源码
- `POST /api/app/my/list/page/vo`：分页查询我的应用
- `POST /api/app/good/list/page/vo`：分页查询精选应用
- `POST /api/app/admin/list/page/vo`：管理员分页查询应用
- `GET /api/workflow/execute-sse`：工作流 SSE 演示接口

更完整的接口请查看 Knife4j 文档：

```text
http://localhost:8123/api/doc.html
```

## 部署说明

前端生产构建：

```bash
cd ai-zero-code-frontend
npm run build
```

后端生产打包：

```bash
./mvnw clean package -DskipTests
```

运行 jar：

```bash
java -jar target/ai-zero-code-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

生产环境建议：

- 使用 Nginx 托管前端 `dist`
- 使用 Nginx 反向代理 `/api` 到 Spring Boot 服务
- 将 `tmp/code_deploy` 暴露为生成应用访问目录
- 使用环境变量或配置中心管理敏感配置
- 配置 Redis、MySQL、对象存储和模型服务的高可用访问地址
- 接入 Prometheus + Grafana 观察接口和模型调用指标

## 二次开发建议

- 新增生成类型：扩展 `CodeGenTypeEnum`、提示词、解析器和保存器
- 替换模型供应商：调整 LangChain4j OpenAI-compatible 配置即可接入兼容接口
- 强化工作流：在 `langgraph4j` 模块中增加节点，例如需求澄清、代码审查、自动测试
- 接入更多部署方式：可扩展部署服务，将产物发布到对象存储、容器平台或 Serverless
- 完善安全策略：为 AI 工具调用增加更细粒度的目录限制、内容审核和审计日志

## 许可证

当前仓库暂未声明开源许可证。若计划开放给其他用户使用或贡献，建议补充 `LICENSE` 文件并在此处说明许可证类型。
