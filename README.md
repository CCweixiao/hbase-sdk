<p align="center"><h3>hbase-sdk</h3></p>

<p align="center">基于hbase-client的相关API开发而来的一款轻量级的HBase ORM框架。 😋</p>

<p align="center">hbase-sdk分为spring-boot-starter-hbase和hbase-sdk-core两部分。</p>

<p align="center">SpringBoot项目中引入spring-boot-starter-hbase，在普通的Java项目中则可以使用hbase-sdk-core。</p>

<p align="center">
    🐾 <a href="#快速开始" target="_blank">快速开始</a> | 
    🎬 <a href="#" target="_blank">视频教程</a> | 
    🌚 <a href="https://github.com/CCweixiao/hbase-sdk/blob/master/README.md" target="_blank">官方文档</a> | 
    💰 <a href="https://www.jielongping.com" target="_blank">捐赠我们</a> |
    🌾 <a href="README.md">English</a>
</p>


***

## hbase-sdk

`hbase-sdk` 是一款轻量级的ORM框架，封装了对HBase的数据读写和对集群的运维管理等操作。
如果觉得这个项目不错可以 [star](https://github.com/CCweixiao/hbase-sdk/stargazers) 支持或者 [捐赠](https://www.jielongping.com) 它 :blush:

## 功能特性

* [x] 在保留原有hbase-client功能API的基础上提供了ORM的特性
* [x] 与SpringBoot集成，很好利用了SpringBoot的优良特性
* [x] JDK8+

## 快速开始

`hbase-sdk` 基于java8开发，所以你必须确定已经安装了Java8，另外，如果你想在本地进行开发调试，请确保本地存在一个可连通的HBase环境。如果你想快速搭建一个HBase的开发环境，请参考：
[https://www.jielongping.com/archives/dockerhbasetest](https://www.jielongping.com/archives/dockerhbasetest)

### 1. 普通项目

`Maven` 配置：

创建一个基础的 `Maven` 工程

```xml
<dependency>
    <groupId>com.github.CCweixiao</groupId>
    <artifactId>hbase-sdk-core</artifactId>
    <version>1.0.1</version>
</dependency>
```

目前的最新版本是`1.0.1`

### 2. 在SpringBoot项目中使用

`Maven` 配置：

创建一个基于`Maven`的spring boot工程。

```xml
<dependency>
    <groupId>com.github.CCweixiao</groupId>
    <artifactId>spring-boot-starter-hbase</artifactId>
    <version>1.0.1</version>
</dependency>
```

spring-boot-starter-hbase这个模块中已经包含了hbase-sdk-core。

### 3. 引入hbase-client的依赖

除了引入`hbase-sdk`的相关依赖之外，你还需要引入`hbase-client`的依赖，
`hbase-client`的版本目前建议为`1.2.x`、`1.4.x`、`2.1.x`。其实hbase-client新旧API有所差异。未来，`hbase-sdk`在对hbase的版本支持方面会更加完善。

```xml
<dependency>
    <groupId>org.apache.hbase</groupId>
    <artifactId>hbase-client</artifactId>
    <version>1.4.3</version>
</dependency>        
```

### 4. 配置HBase数据库连接

**普通项目**

```java
// 数据读写操作
HBaseTemplate hBaseTemplate = new HBaseTemplate("docker-hbase", "2181");
//集群管理操作
HBaseAdminTemplate hBaseAdminTemplate = new HBaseAdminTemplate("docker-hbase", "2181");
```

**spring boot项目**

application.yaml

```yaml
spring:
  data:
    hbase:
      quorum: docker-hbase
```



```java
@Service
public class UserService {
    @Autowired
    private HBaseTemplate hBaseTemplate;
    @Autowired
    private HBaseAdminTemplate hBaseAdminTemplate;
}
```


## Contents
- [**`集群管理`**](#集群管理)
    - [**`创建namespace`**](#创建namespace)
    - [**`创建表`**](#创建表)
    - [**`更多操作`**](#更多操作)
- [**`创建Model`**](#创建Model)
    - [**`Model约定`**](#Model约定)
    - [**`创建Model`**](#创建Model)
    - [**`Model配置`**](#Model配置)
- [**`查询数据`**](#查询数据)
    - [**`根据RowKey查询`**](#根据RowKey查询)
    - [**`scan查询`**](#scan查询)
- [**`保存数据`**](#保存数据)
- [**`更新和删除`**](#更新和删除)

## 集群管理

目前，HBaseAdminTemplate只提供了HBaseAdmin的常用操作，比如namespace的管理、表的管理等等，与原生HBaseAdmin的API相比，它的功能可能不是很全面，但以后一定会更加完善。

### 创建namespace

```java
  HBaseAdminTemplate hBaseAdminTemplate = new HBaseAdminTemplate("docker-hbase", "2181");

hBaseAdminTemplate.createNamespace("LEO_TEST");
```

### 创建表

```java

```

### 更多操作

## 创建Model

### Model约定

### 创建Model

### Model配置

## 查询数据

### 根据RowKey查询

### scan查询

## 保存数据

## 更新和删除


这一切看起来多么的简单，不过上面的功能可是冰山一角，查看文档和示例项目有更多惊喜:

+ [Blade Demos](https://github.com/lets-blade/blade-demos)
+ [Blade 资源列表](https://github.com/lets-blade/awesome-blade)

## 联系我们

- Twitter: [biezhi](https://twitter.com/biezhii)
- Mail: biezhi.me#gmail.com
- [TG 交流群](https://t.me/letsblade)

## 贡献者们

非常感谢下面的开发者朋友对本项目的帮助，如果你也愿意提交PR，非常欢迎！

![contributors.svg](https://opencollective.com/blade/contributors.svg?width=890&button=false)

## 开源协议

请查看 [Apache License](LICENSE)
