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

创建一个基础的 `Maven` 工程，HBase SDK API开发时基于的HBase版本主要是1.4.3和2.1.0。

所以，如果你的HBase版本是1.x，可以使用如下依赖。

```xml
<dependency>
    <groupId>com.github.CCweixiao</groupId>
    <artifactId>hbase-sdk-core_1.4</artifactId>
    <version>1.0.5</version>
</dependency>
```

如果你的HBase版本是2.x，则可以使用如下依赖。

```xml
<dependency>
    <groupId>com.github.CCweixiao</groupId>
    <artifactId>hbase-sdk-core_2.1</artifactId>
    <version>1.0.5</version>
</dependency>
```

`hbase-sdk`目前最新的版本是`1.0.5`。你可以在maven仓库中搜索CCweixiao来获取hbase-sdk相关jar包的最新版本。
[https://mvnrepository.com/artifact/com.github.CCweixiao](https://mvnrepository.com/artifact/com.github.CCweixiao)

当然，如果你想重新编译，以适配你自己的HBase版本，也可以选择下载源码，修改项目pom.xml文件中的hbase.version来运行如下编译命令：

```shell script
git clone https://github.com/CCweixiao/hbase-sdk.git
git clone https://gitee.com/weixiaotome/hbase-sdk.git
cd hbase-sdk
mvn clean install -Dmaven.test.skip=true
```

### 2. 项目结构

![project](https://leo-jie-pic.oss-cn-beijing.aliyuncs.com/leo_blog/2020-09-06-131351.jpg)


### 3. 在SpringBoot项目中使用

`Maven` 配置：

创建一个基于`Maven`的spring boot工程。

```xml
<dependency>
    <groupId>com.github.CCweixiao</groupId>
    <artifactId>spring-boot-starter-hbase_1.4</artifactId>
    <version>1.0.3</version>
</dependency>
```

spring-boot-starter-hbase这个模块中已经包含了hbase-sdk-core。

### 4. 引入hbase-client的依赖

除了引入`hbase-sdk`的相关依赖之外，你还需要引入`hbase-client`的依赖，
`hbase-client`的版本目前建议为`1.2.x`、`1.4.x`、`2.1.x`。其实hbase-client新旧API有所差异。未来，`hbase-sdk`在对hbase的版本支持方面会更加完善。

```xml
<dependency>
    <groupId>org.apache.hbase</groupId>
    <artifactId>hbase-client</artifactId>
    <version>1.4.3</version>
</dependency>        
```

### 5. 配置HBase数据库连接

**普通项目**

```java
// 数据读写操作
HBaseTemplate hBaseTemplate = new HBaseTemplate("node1", "2181");
//集群管理操作
HBaseAdminTemplate hBaseAdminTemplate = new HBaseAdminTemplate("node1", "2181");
```

**spring boot项目**

application.yaml

```yaml
spring:
  data:
    hbase:
      quorum: node1
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
- [**`数据读写`**](#数据读写)
    - [**`创建数据模型类`**](#创建数据模型类)
    - [**`保存数据`**](#保存数据)
    - [**`批量保存数据`**](#批量保存数据)
- [**`查询数据`**](#查询数据)
    - [**`根据RowKey查询`**](#根据RowKey查询)
    - [**`scan查询`**](#scan查询)
- [**`删除数据`**](#删除数据)

## 集群管理

目前，HBaseAdminTemplate只提供了HBaseAdmin的常用操作，比如namespace的管理、表的管理等等，与原生HBaseAdmin的API相比，它的功能可能不是很全面，但以后一定会更加完善。

### 创建namespace

HBase管理员操作API与原API其实差异并不大。

```java
    @Test
    public void testCreateNamespace() {
        String namespaceName = "LEO_NS2";
        Map<String, String> para = new HashMap<>();
        para.put("tag", "测试命名空间");
        para.put("createBy", "leo");
        para.put("updateBy", "");
        NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(namespaceName)
                .addConfiguration(para)
                .build();
        hBaseTemplate.createNamespace(namespaceDescriptor);
    }
```

### 创建表

```java
    @Test
    public void testCreateTable() {
        String tableName = "LEO_NS:USER";

        HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));

        tableDescriptor.setValue("tag", "测试用户表");
        tableDescriptor.setValue("createUser", "leo");


        HColumnDescriptor columnDescriptor = new HColumnDescriptor("INFO");
        columnDescriptor.setScope(1);
        columnDescriptor.setCompressionType(Compression.Algorithm.SNAPPY);
        columnDescriptor.setTimeToLive(2147483647);
        columnDescriptor.setMaxVersions(3);

        HColumnDescriptor columnDescriptor2 = new HColumnDescriptor("INFO2");
        columnDescriptor2.setScope(0);
        columnDescriptor2.setTimeToLive(864000);
        columnDescriptor2.setMaxVersions(3);

        tableDescriptor.addFamily(columnDescriptor).addFamily(columnDescriptor2);

        hBaseTemplate.createTable(tableDescriptor, Bytes.toBytes(0), Bytes.toBytes(100), 10);
    }
```

### 更多操作

可以参考相关API

## 数据读写

类似于Hibernate，你也可以使用hbase-sdk框架所提供的ORM特性，来实现对HBase的读写操作。

### 创建数据模型类

```java
public class ModelEntity {
    private String createBy;
    private Long createTime;

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}
```

```java
@HBaseTable(schema = "TEST", name = "LEO_USER", uniqueFamily = "info1")
public class UserEntity extends ModelEntity{
    @HBaseRowKey
    private String userId;

    private String username;
    private int age;
    private List<String> addresses;
    private Map<String,Object> contactInfo;
    private Double pay;

    @HBaseColumn(name = "is_vip", family = "INFO2", toUpperCase = true)
    private boolean isVip;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public Map<String, Object> getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(Map<String, Object> contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Double getPay() {
        return pay;
    }

    public void setPay(Double pay) {
        this.pay = pay;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", addresses=" + addresses +
                ", contactInfo=" + contactInfo +
                ", pay=" + pay +
                ", isVip=" + isVip +
                '}';
    }
}
```

@HBaseTable(schema = "TEST", name = "LEO_USER", uniqueFamily = "info1")

HBaseTable注解用于定义HBase的表信息，schema用于定义该表的命名空间，如果不指定，默认是default，
name用于定义该表的表名，如果不指定，表名则为类名的组合单词拆分加'_'拼接，如：UserEntity对应的表名为：user_entity。
uniqueFamily用于定义如果所有的字段不特配置列簇名，则使用此处配置的列簇名。


@HBaseRowKey
private String userId;

该注解表示userId字段为rowKey字段。


@HBaseColumn(name = "is_vip", family = "INFO2", toUpperCase = true)
private boolean isVip;
该注解用于定义一个字段信息，name用于定义字段名，如果不指定，则默认使用字段名的组合单词拆分加'_'拼接，如：isVip，对应的字段名是：is_vip.
family用于定义该字段属于INFO2列簇，toUpperCase表示字段名是否转大写，默认false，不做操作。


### 保存数据

```java
   @Test
    public void testSaveUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId("10001");
        userEntity.setUsername("leo");
        userEntity.setAge(18);
        userEntity.setVip(true);
        userEntity.setAddresses(Arrays.asList("北京", "上海"));
        userEntity.setCreateBy("admin");
        userEntity.setCreateTime(System.currentTimeMillis());

        Map<String, Object> contactInfo = new HashMap<>(2);
        contactInfo.put("email", "2326130720@qq.com");
        contactInfo.put("phone", "18739577988");
        contactInfo.put("address", "浦东新区");

        userEntity.setContactInfo(contactInfo);
        userEntity.setPay(100000.0d);

        try {
            hBaseTemplate.save(userEntity);
            System.out.println("用户数据保存成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

除此之外，保存数据时也可以不必构造数据模型类，而直接构造map数据模型。

```java
    @Test
    public void testToSave() {
        Map<String, Object> data = new HashMap<>();
        data.put("info1:addresses", Arrays.asList("广州", "深圳"));
        data.put("info1:username", "leo");
        data.put("info1:age", 18);
        data.put("INFO2:IS_VIP", true);
        data.put("info1:pay", 10000.1d);
        data.put("info1:create_by", "tom");
        data.put("info1:create_time", System.currentTimeMillis());
        Map<String, Object> contactInfo = new HashMap<>(2);
        contactInfo.put("email", "2326130720@qq.com");
        contactInfo.put("phone", "18739577988");
        contactInfo.put("address", "浦东新区");
        data.put("info1:contact_info", contactInfo);
        hBaseTemplate.save("TEST:LEO_USER", "10002", data);
        System.out.println("用户数据保存成功！");
    }
```

### 批量保存数据

```java
    @Test
    public void testToSaveBatch() {
        Map<String, Map<String, Object>> data = new HashMap<>();

        Map<String, Object> data1 = new HashMap<>();
        data1.put("info1:username", "kangkang");
        data1.put("info1:age", 18);
        data1.put("INFO2:IS_VIP", true);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("info1:username", "jane");
        data2.put("info1:age", 18);
        data2.put("INFO2:IS_VIP", false);

        data.put("12003", data1);
        data.put("11004", data2);

        hBaseTemplate.saveBatch("TEST:LEO_USER", data);
        System.out.println("用户数据批量保存成功！");
    }
```

## 查询数据

### 根据RowKey查询

```java
    @Test
    public void testGet() {
        UserEntity userEntity = hBaseTemplate.getByRowKey("10001", UserEntity.class);
        final UserEntity userEntity1 = hBaseTemplate.getByRowKey("10002", UserEntity.class);
        System.out.println("用户数据获取成功！");
        System.out.println(userEntity);
    }
```

```java
    @Test
    public void testGetToMap() {
        Map<String, Object> userInfo = hBaseTemplate.getByRowKey("TEST:LEO_USER", "10001");
        System.out.println(Boolean.valueOf(userInfo.get("INFO2:IS_VIP").toString()));
        System.out.println(userInfo);
    }
```

### scan查询

```java
    @Test
    public void testFind() {
        final List<UserEntity> userEntities = hBaseTemplate.findAll(10, UserEntity.class);
        System.out.println(userEntities);
        System.out.println("用户数据批量查询");
    }

    @Test
    public void testFindByPrefix() {
        final List<UserEntity> userEntities = hBaseTemplate.findByPrefix("11", 10, UserEntity.class);
        System.out.println("用户数据批量查询");
    }
```

## 删除数据

```java
    @Test
    public void testDeleteData() {
        hBaseTemplate.delete("TEST:LEO_USER", "12003");
        hBaseTemplate.delete("TEST:LEO_USER", "11004", "INFO2");
        hBaseTemplate.delete("TEST:LEO_USER", "10001", "info1", "addresses");
        System.out.println("数据删除完成");
    }
```

```java
    @Test
    public void testDeleteBatch() {
        hBaseTemplate.deleteBatch("TEST:LEO_USER", Arrays.asList("10001", "10002"));
        hBaseTemplate.deleteBatch("TEST:LEO_USER", Collections.singletonList("10003"), "INFO2");
        hBaseTemplate.deleteBatch("TEST:LEO_USER", Collections.singletonList("10004"),
                "info1", "age", "username");
    }
```




