# 🚀 短链接生成平台 (Short Link)

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.6-6DB33F?style=for-the-badge&logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql)
![Redis](https://img.shields.io/badge/Redis-7.0-DC382D?style=for-the-badge&logo=redis)

## 📖 项目简介
这是一个基于 **Spring Boot 4.0.6** 和 **Java 21** 构建的高性能短链接生成服务平台。
项目实现了将冗长的原始 URL 转换为简短、易分享的短链接，并支持访问时的高效重定向。

> 💡 **项目定位**：作为我的第一个全栈实战项目，重点实践了 Spring 生态的最新特性、Redis 缓存穿透预防以及标准的 RESTful API 设计规范。

## ✨ 核心功能
- ✅ **短链生成**：支持输入任意合法 URL，生成唯一的 6-8 位短链 Key。
- ✅ **短链跳转**：访问短链接时，通过 Redis 缓存加速查询，实现毫秒级 302 重定向。
- ✅ **数据持久化**：使用 MySQL 存储长短链接映射关系，保证数据可靠性。
- ✅ **异常处理**：全局异常捕获，返回统一的 JSON 格式错误信息。

## 🛠️ 技术栈
| 类别 | 技术 | 版本 |
| :--- | :--- | :--- |
| **核心框架** | Spring Boot | **4.0.6** |
| **开发语言** | Java | 21 (LTS) |
| **ORM 框架** | MyBatis | Latest |
| **数据库** | MySQL | 8.0 |
| **缓存** | Redis | 7.0 |
| **构建工具** | Maven | 3.9+ |
| **API 风格** | RESTful | - |

## 🚀 本地快速启动

### 前置环境
请确保本地已安装并启动以下服务：
- **JDK 21** (必须)
- **MySQL 8.0**
- **Redis** (默认端口 6379)

### 1. 克隆项目
bash
git clone https://github.com/你的用户名/你的仓库名.git
cd shortlink

### 2. 数据库准备
在 MySQL 中创建数据库 `shortlink`，并执行以下建表语句：
CREATE TABLE t_link (
id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '主键ID',
long_url VARCHAR(2048) NOT NULL COMMENT '原始长链接',
short_key VARCHAR(20) NOT NULL UNIQUE COMMENT '短链Key',
create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短链接映射表';

### 3. 配置修改
打开 `src/main/resources/application.properties`，配置你的数据库连接信息（**请勿将真实密码提交到 GitHub**）：
properties

数据库配置
spring.datasource.url=jdbc:mysql://localhost:3306/shortlink?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=你的密码

Redis 配置
spring.data.redis.host=localhost
spring.data.redis.port=6379
### 4. 启动项目
运行 `com.example.shortlink.ShortlinkApplication` 类的 `main` 方法。
看到日志输出 `Started ShortlinkApplication in x seconds` 即启动成功。
### 5. 接口测试
- **生成短链**：
http
GET http://localhost:9999/generate?url=https://www.baidu.com
- **访问短链**：
http
GET http://localhost:9999/{生成的短码}

## 💡 项目亮点与技术细节
1. **前沿技术栈**：率先使用 **Spring Boot 4.0.6** 搭配 **Java 21**，体验了最新的框架性能优化和语法特性。
2. **缓存策略**：引入 Redis 作为一级缓存，极大降低了数据库 QPS 压力，提升了系统吞吐量。
3. **工程规范**：严格遵守 Git Flow 流程，配置了完善的 `.gitignore` 规则，保证仓库整洁。

## 🗺️ 未来规划
- [ ] **统计功能**：增加短链接访问次数（PV/UV）统计。
- [ ] **有效期**：支持设置短链接过期时间。
- [ ] **安全校验**：增加黑名单机制，防止生成违规网站的短链。
- [ ] **前端页面**：使用 Vue/React 开发一个简单的管理后台。

## 🙏 致谢
这是我编程之路上的第一个里程碑！感谢开源社区，也感谢坚持下来的自己。
欢迎各位大佬 Star ⭐ 和 Fork 🍴，如有问题请提 Issue！
