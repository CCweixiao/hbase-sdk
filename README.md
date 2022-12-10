## 1. hbase-sdk介绍

<p align="center">hbase-sdk是基于hbase-client和hbase-thrift的原生API封装的一款轻量级的HBase ORM框架。 针对HBase各版本API（1.x~2.x）间的差异，在其上剥离出了一层统一的抽象。并提供了以类SQL的方式来读写HBase表中的数据。</p>

<p align="center">在普通的java项目和Spring Boot项目中，你可以分别依赖hbase-sdk-template_${hbase.adapter.version}和spring-boot-starter-hbase_${hbase.adapter.version}两个模块来使用hbase-sdk封装的统一API。其中hbase.adapter.version暂只支持：1.2、1.4、2.2</p>

<p align="center">
    🐾 <a href="#快速开始" target="_blank">快速开始</a> | 
    🎬 <a href="#" target="_blank">视频教程</a> | 
    🌚 <a href="https://github.com/CCweixiao/hbase-sdk/blob/master/README.md" target="_blank">官方文档</a> | 
    💰 <a href="https://www.jielongping.com" target="_blank">捐赠我们</a> |
    🌾 <a href="README.md">English</a>
</p>


***

##  2. hbase-sdk的优势

`hbase-sdk` 基于HBase的原生API，封装了对HBase表及其数据的DML和DDL操作，同时，也是一款轻量级的ORM框架，提供了数据模型类绑定HBase表实体的能力，与原生的API相比，其优势如下：

1. 基于原生API中Get/Put/Scan等功能，重新定义了统一的操作接口，屏蔽了底层HBase各版本原生API间的差异，在面对集群跨大版本升级时，业务伙伴只需对应升级自己项目中的`hbase-client`的版本即可。
2. 简化了原生API较为复杂的调用方式，在ORM特性的加持之下，没有HBase API调用经验的开发伙伴，也能快速完成对HBase表数据的读写业务。
3. 对HBase的原生thrift api进行了池化封装，类似于Jedis-pool，增强了thrift api生产环境中使用的稳定性。
4. 使用spring-boot-starter-hbase可快速与SpringBoot无缝集成。
5. 提供了类SQL的方式——HQL来读写HBase表中的数据，进一步简化了原生API的使用方式。

API文档地址: [https://weixiaotome.gitee.io/hbase-sdk/](https://weixiaotome.gitee.io/hbase-sdk/)
如果觉得这个项目不错可以 [star](https://github.com/CCweixiao/hbase-sdk/stargazers) 支持或者 [捐赠](https://www.jielongping.com) 它 :blush:

## 3. 功能特性

* [x] 定义了统一的接口规范，消除了HBase不同版本原生API之间的差异
* [x] ORM特性，以注解方式快速实现表、列簇、字段模型与java实体类进行绑定
* [x] 对HBase的原生thrift API进行池化封装，提供了HBaseThriftPool的功能
* [x] HQL，以类SQL的形式读写HBase的表中数据
* [x] 利用spring-boot-starter-hbase无缝与SpringBoot集成
* [ ] HBatis，类似于myBatis，提供配置文件管理HQL的功能（规划中）
* [ ] 客户端熔断，提供客户端API级别的主备集群切换，保障请求HBase接口服务的高可用（规划中）
* [ ] thrift 连接池中连接数的动态扩所容能力（规划中）

## 4. 仓库地址

https://github.com/CCweixiao/hbase-sdk

https://gitee.com/weixiaotome/hbase-sdk

两边仓库地址是同步更新的，欢迎各位大佬点个star

## 5. 编译指南

克隆项目到本地，导入到IDEA中，首次加载项目，会从远程仓库拉取项目所需的依赖，还请耐心等待。

```shell
cd hbase-sdk
mvn clean install -Phbase-1.2 # hbase-client:1.2.x
mvn clean install -Phbase-1.4 # hbase-client:1.4.x
mvn clean install -Phbase-2.2 # hbase-client:2.x.x

或者

mvn clean package -Dmaven.test.skip=true -Dfindbugs.skip -Dcheckstyle.skip -Dmaven.javadoc.skip -Phbase-1.2

mvn clean package -Dmaven.test.skip=true -Dfindbugs.skip -Dcheckstyle.skip -Dmaven.javadoc.skip -Phbase-1.4

mvn clean package -Dmaven.test.skip=true -Dfindbugs.skip -Dcheckstyle.skip -Dmaven.javadoc.skip -Phbase-2.2
```

`hbase-sdk`的``hbase-sdk-adapter`模块下的各个子模块中已引入了具体的hbase-shaded-client的依赖，如有需要可以自行更改你想使用的hbase-client版本。

## 6. 项目结构概览

![project](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/4nj24.jpg)

项目结构说明，主要介绍核心模块的作用

```shell
├── hbase-sdk-adapter 						# HBase各版本不兼容API的adapter
│   ├── hbase-sdk-adapter-common
│   ├── hbase-sdk-adapter_1.2
│   ├── hbase-sdk-adapter_1.4
│   ├── hbase-sdk-adapter_2.2
│   └── pom.xml
├── hbase-sdk-common							# 通用工具或接口
├── hbase-sdk-dsl									# HBase SQL
├── hbase-sdk-examples						
│   ├── hbase-sdk-example
│   └── spring-boot-starter-hbase-example
├── hbase-sdk-template						# hbase操作模版类API
├── hbase-sdk-thrift							# HBase thrift API
└── spring-boot-starter-hbase			# spring-boot-starter-hbase 
```

`hbase-sdk`UML类图：

![api-project](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/8fp9s.png)

## 7. 快速开始

`hbase-sdk` 的各个版本完成开发测试之后，都会发布到maven中央仓库之中，只是最新版本的代码有一定的延迟。如果你想在第一时间体验新功能，可以选择克隆Gitee或Github仓库中的源码，在本地编译并运行测试用例。

`hbase-sdk` 如果你想在本地进行开发，请确保已经安装了Java8和maven3.6+。同时建议在本地部署一个可连通的HBase开发环境。建议使用docker来快速搭建一个HBase的单机环境，可以参考博客：https://blog.csdn.net/feinifi/article/details/121174846

`hbase-sdk` 开发所用的工具为IDEA，所以也极力推荐导入项目到IDEA中。

### 7.1 在普通项目中使用hbase-sdk

`Maven` 配置：

创建一个基础的 `Maven` 工程，HBase SDK 已适配了hbase-client的1.2/1.4/2.x版本API。

如果你的HBase版本是1.2.x，可以使用如下依赖。


```xml
<dependency>
    <groupId>com.github.CCweixiao</groupId>
    <artifactId>hbase-sdk-template_1.2</artifactId>
    <version>3.0.0-SNAPSHOT</version>
</dependency>
```

如果你的HBase版本是1.4.x，则可以使用如下依赖。

```xml
<dependency>
    <groupId>com.github.CCweixiao</groupId>
    <artifactId>hbase-sdk-template_1.4</artifactId>
    <version>3.0.0-SNAPSHOT</version>
</dependency>
```

如果你的HBase版本是2.x.x，则可以使用如下依赖。

```xml
<dependency>
    <groupId>com.github.CCweixiao</groupId>
    <artifactId>hbase-sdk-template_2.2</artifactId>
    <version>3.0.0-SNAPSHOT</version>
</dependency>
```

`hbase-sdk`目前最新的版本是`3.0.0-SNAPSHOT`。你可以在maven中央仓库中搜索CCweixiao来获取hbase-sdk相关jar包的最新版本。
https://mvnrepository.com/artifact/com.github.CCweixiao

或者在git仓库中查看最新的release版本。

当然，如果你想重新编译，扩展你需要的功能，也可以选择下载源码，修改项目根pom.xml文件中的`hbase.version`，按照编译指南中的编译命令来操作。

### 7.2 在SpringBoot项目中使用

`Maven` 配置：

创建一个基于`Maven`的spring boot工程，在项目pom.xml中加入spring-boot-starter-hbase的依赖。

如果你的HBase版本是1.2.x，可以使用如下依赖。

```xml
<dependency>
    <groupId>com.github.CCweixiao</groupId>
    <artifactId>spring-boot-starter-hbase_1.2</artifactId>
    <version>3.0.0-SNAPSHOT</version>
</dependency>
```

如果你的HBase版本是1.4.x，可以使用如下依赖。

```xml
<dependency>
    <groupId>com.github.CCweixiao</groupId>
    <artifactId>spring-boot-starter-hbase_1.4</artifactId>
    <version>3.0.0-SNAPSHOT</version>
</dependency>
```

如果你的HBase版本是2.x.x，可以使用如下依赖。

```xml
<dependency>
    <groupId>com.github.CCweixiao</groupId>
    <artifactId>spring-boot-starter-hbase_2.2</artifactId>
    <version>3.0.0-SNAPSHOT</version>
</dependency>
```

### 7.3 引入hbase-client的依赖

`hbase-sdk`没有把hbase-client的依赖打到自己的包中，所以，除了引入`hbase-sdk`的相关依赖之外，你还需要引入`hbase-client`的依赖，`hbase-client`的版本目前支持`1.2.x`、`1.4.x`、`2.2.x`，请按需引入。（建议使用hbase-shaded-client）。

```xml
<dependency>
    <groupId>org.apache.hbase</groupId>
    <artifactId>hbase-shaded-client</artifactId>
    <version>1.2.0</version>
</dependency>  
```

or

```xml
<dependency>
    <groupId>org.apache.hbase</groupId>
    <artifactId>hbase-shaded-client</artifactId>
    <version>1.4.3</version>
</dependency>        
```

or

```xml
<dependency>
    <groupId>org.apache.hbase</groupId>
    <artifactId>hbase-shaded-client</artifactId>
    <version>2.2.6</version>
</dependency>        
```

### 7.5 HBase连接配置

**普通Java项目**

**普通认证**

```java
// 普通认证
Properties properties = new Properties();
properties.setProperty("hbase.zookeeper.quorum", "myhbase");
properties.setProperty("hbase.zookeeper.property.clientPort", "2181");
// 请按需引入一些额外所需的客户端配置
properties.put("hbase.client.retries.number", "3");
```

**Kerberos认证**

```java
Properties properties = new Properties();
properties.put("hbase.zookeeper.quorum", "zk_host1,zk_host1,zk_host1");
properties.put("hbase.zookeeper.property.clientPort", "2181");
properties.put("hbase.security.authentication", "kerberos");
properties.put("kerberos.principal", "hbase@HADOOP.LEO.COM");
properties.put("keytab.file", "/etc/hbase/conf/hbase.keytab");
properties.put("hbase.regionserver.kerberos.principal", "hbase/_HOST@HADOOP.LEO.COM");
properties.put("hbase.master.kerberos.principal", "hbase/_HOST@HADOOP.LEO.COM");
// 指定kdc服务相关的配置方式有如下两种：
// 方式一：指定krb5.conf路径
properties.put("java.security.krb5.conf", "/etc/krb5.conf");
// 方式二：指定java.security.krb5.realm和java.security.krb5.kdc
properties.put("java.security.krb5.realm", "HADOOP.LEO.COM");
properties.put("java.security.krb5.kdc", "你自己的kdc服务地址");
// 一些额外的客户端参数
properties.put("hbase.client.retries.number", "3");

IHBaseAdminTemplate adminTemplate adminTemplate = new HBaseAdminTemplateImpl.Builder().properties(properties).build();
System.out.println(adminTemplate.listTableNames());
```



**Spring Boot项目**

**普通认证**

application.yaml

```yaml
spring:
  datasource:
    hbase:
      zk-host-list: zk_host1,zk_host2,zk_host3
      zk-client-port: 2181 # (可选，默认2181)
      dfs-root-dir: /hbase # (可选，默认/hbase)
      zk-node-parent: /hbase  # (可选，默认/hbase)
      security-auth-way: simple # (可选，默认simple)
      client-properties: hbase.client.retries.number=3;key1=value2
server:
  port: 8088
```

**Kerberos认证**

```yaml
spring:
  datasource:
    hbase:
      zk-host-list: zk_host1,zk_host2,zk_host3
      zk-client-port: 2181 # (可选，默认2181)
      dfs-root-dir: /hbase # (可选，默认/hbase)
      zk-node-parent: /hbase  # (可选，默认/hbase)
      security-auth-way: kerberos 
      kerberos-principal: hbase@HADOOP.LEO.COM
      keytab-file-path: /etc/hbase/conf/hbase.keytab
      rs-kerberos-principal: hbase/_HOST@HADOOP.LEO.COM
      master-kerberos-principal: hbase/_HOST@HADOOP.LEO.COM
      # krb5-conf-path和krb5-realm、krb5-kdc-server-addr任选一种配置KDC的方式
      krb5-conf-path: /etc/krb5.conf
      krb5-realm:
      krb5-kdc-server-addr:
      client-properties: hbase.client.retries.number=3;key1=value2
server:
  port: 8088
```

### 7.6 使用hbase-template模块中的模版实现类

**普通项目**

```java
// 数据读写API的操作模版类
IHBaseTableTemplate tableTemplate = new HBaseAdminTemplateImpl.Builder()
                .properties(properties).build();
// Admin操作模版类
IHBaseAdminTemplate adminTemplate = new HBaseAdminTemplateImpl.Builder()
                .properties(properties).build();
// HQL操作模版类
IHBaseSqlTemplate sqlTemplate = new HBaseSqlTemplateImpl.Builder()
                .properties(properties).build()
```

**SpringBoot项目**

@Autowired 依赖注入

```java
@Service
public class UserService {
    @Autowired
    private IHBaseTableTemplate tableTemplate;
    @Autowired
    private IHBaseAdminTemplate adminTemplate;
    @Autowired
    private IHBaseSqlTemplate sqlTemplate;
}
```

## 8. 集群管理

HBaseAdminTemplate封装了HBaseAdmin的常用操作，比如namespace的管理、表的管理、以及快照管理等等，后续这些API将会更加完整。

![admin-api](https://leo-jie-pic.oss-cn-beijing.aliyuncs.com/leo_blog/2020-11-29-120523.jpg)

### 8.1 创建namespace


```java
@Test
public void testCreateNamespace() {
    String namespaceName = "LEO_NS";
    
    NamespaceDesc namespaceDesc = new NamespaceDesc();
    namespaceDesc.setNamespaceName(namespaceName);
    // 为namespace添加属性
    namespaceDesc = namespaceDesc.addNamespaceProp("desc", "测试命名空间")
                .addNamespaceProp("createBy", "leo").addNamespaceProp("updateBy", "admin");

    adminTemplate.createNamespace(namespaceDesc);
}
```

### 8.2 创建表

```java
@Test
public void testCreateTable() {
    String tableName = "LEO_NS:USER";

    TableDesc tableDesc = new TableDesc();
    tableDesc.setTableName(tableName);

    tableDesc = tableDesc.addProp("tag", "测试用户表").addProp("createUser", "leo");

    FamilyDesc familyDesc1 = new FamilyDesc.Builder()
            .familyName("INFO")
            .replicationScope(1)
            .compressionType("NONE")
            .timeToLive(2147483647)
            .maxVersions(1).build();

    FamilyDesc familyDesc2 = new FamilyDesc.Builder()
            .familyName("INFO2")
            .replicationScope(0)
            .compressionType("SNAPPY")
            .timeToLive(864000)
            .maxVersions(3).build();

    tableDesc = tableDesc.addFamilyDesc(familyDesc1).addFamilyDesc(familyDesc2);

    adminTemplate.createTable(tableDesc, false);
}
```

### 8.3 更多操作

可以参考相关API文档或`hbase-template`模块下的测试用例

## 9. 数据读写

类似于Hibernate，你也可以使用hbase-sdk框架所提供的ORM特性，来实现对HBase的数据读写操作。

![api-data](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/gwjtl.jpg)

### 9.1 创建数据模型类

```java
public class CityTag {
    private String tagName;

    public CityTag(String tagName) {
        this.tagName = tagName;
    }
		// 省略Getter/Setter/toString
}
```

```java
@HBaseTable(namespaceName = "default", tableName = "t2", defaultFamilyName = "info")
public class CityModel {
    @HBaseRowKey
    private String cityId;
    private String cityName;
    private String cityAddress;
    @HBaseColumn(familyName = "detail")
    private Integer cityArea;
    @HBaseColumn(familyName = "detail", toUpperCase = true)
    private Integer totalPopulation;
    @HBaseColumn(familyName = "detail", columnName = "cityTagList")
    private List<CityTag> cityTagList;
  	// 省略Getter/Setter/toString
}
```

`@HBaseTable`注解用于定义HBase的表信息

```java
@HBaseTable(namespaceName = "default", tableName = "t2", defaultFamilyName = "info")
```

1）namespaceName：用于指定该表的命名空间，默认：default

2）tableName：用于指定该表的表名，如果不指定，表名则为类名的组合单词拆分转小写加'_'拼接，如：CityModel对应的表名为：city_model。
3）defaultFamilyName：用于定义如果有字段不特配置（@HBaseRowKey注解中的familyName）列簇名，则使用此处配置的列簇名。

`@HBaseRowKey`注解用于定义某一个属性字段是用作存储rowKey数据的，是必须要设置的，如：

```java
@HBaseRowKey
private String cityId;
```

该注解表示cityId字段为rowKey。

`@HBaseColumn`注解用于定义HBase的列簇和列名信息，如：

```java
@HBaseColumn(familyName = "detail", columnName = "TOTAL_POPULATION",  toUpperCase = true)
private Integer totalPopulation;
```

1）familyName：指定列簇名，不指定，则使用defaultFamilyName配置的列簇名。

2）columnName：指定列名，不指定则默认使用字段名的组合单词拆分转小写加'_'拼接，如：isVip，对应的字段名是：is_vip

3）toUpperCase：定义字段名是否转大写，如：isVip -> IS_VIP，默认值：false，不做转换。

### 9.2 保存数据

```java
   @Test
    public void testSaveUser() {
        CityModel cityModel = new CityModel();
        cityModel.setCityId("10001");
        cityModel.setCityName("上海");
        cityModel.setCityAddress("上海市");
        cityModel.setCityArea(10000);
        cityModel.setTotalPopulation(200000);    		    					cityModel.setCityTagList(tagNameList.stream().map(CityTag::new).collect(Collectors.toList()));
       CityModel city = tableTemplate.save(cityModel);
    }
```

除此之外，保存数据时也可以不必构造数据模型类，而选择直接构造map数据模型。

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

### 9.3 批量保存数据

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

### 9.4 根据RowKey查询

```java
@Test
public void testGetJavaBean() {
    Optional<CityModel> a10001 = tableTemplate.getRow("a10001", CityModel.class);
    Optional<CityModel> a10001F = tableTemplate.getRow("a10001", "info", CityModel.class);
    System.out.println(a10001.orElse(new CityModel()));
    System.out.println(a10001F);
}
```

查询数据返回Map

```java
@Test
public void testGetRowToMap() {
    Map<String, String> d1 = tableTemplate.getRowToMap("t1", "1001", true);
    JSONArray objects = JSON.parseArray(d1.get("f3:tags"));
    Map<String, String> d2 = tableTemplate.getRowToMap("t1", "1002", false);
    List<String> rows = new ArrayList<>(2);
    rows.add("1001");
    rows.add("1002");
    Map<String, Map<String, String>> d3 = tableTemplate.getRowsToMap("t1", rows, true);
        System.out.println(d1);
        System.out.println(d2);
        System.out.println(d3);
}
```

### 9.5 scan查询

**普通scan查询**

```java
@Test
public void testScanWithLimit() {
    ScanQueryParamsBuilder scanQueryParamsBuilder = new ScanQueryParamsBuilder.Builder()
                .familyName("info")
                .columnNames(Arrays.asList("city_name", "city_address", "cityTagList"))
                .limit(2)
                .build();
    List<CityModel> cityModels = tableTemplate.scan(scanQueryParamsBuilder, CityModel.class);
    System.out.println(cityModels);
}
```

**根据起止row查询数据**

```java
@Test
public void testScanWithStartAndEndRow() {
    // 不包含endRow的数据
    ScanQueryParamsBuilder scanQueryParamsBuilder = new ScanQueryParamsBuilder.Builder()
                .startRow("a10001")
                .stopRow("a10002")
                .build();
    List<CityModel> cityModels = tableTemplate.scan(scanQueryParamsBuilder, CityModel.class);
    System.out.println(cityModels);
}
```

**指定过滤器的scan查询**

```java
@Test
public void testScanWithFilter() {
    ScanQueryParamsBuilder scanQueryParamsBuilder = new ScanQueryParamsBuilder.Builder()
            .filter(new IHBaseFilter<Filter>() {
                @Override
                public Filter customFilter() {
                    List<Filter> filters = new ArrayList<>(2);
                    // 筛选row key 大于b20001的数据
                    Filter rowFilter = new RowFilter(CompareFilter.CompareOp.GREATER,
                            new BinaryComparator("b20001".getBytes()));
                    // 筛选列前缀city_address的数据
                    ColumnPrefixFilter columnPrefixFilter = new ColumnPrefixFilter(Bytes.toBytes("city_address"));
                    // 筛选列值与深圳市相等的数据
                    ValueFilter valueFilter = new ValueFilter(CompareFilter.CompareOp.EQUAL, new BinaryComparator(Bytes.toBytes("深圳市")));
                    // 多过滤器，注意顺序
                    filters.add(rowFilter);
                    filters.add(columnPrefixFilter);
                    filters.add(valueFilter);
                    // 需所有条件全部通过
                    FilterList andFilterList = new FilterList(FilterList.Operator.MUST_PASS_ALL, filters);
                    // 满足其中一个条件即可
                    FilterList orFilterList = new FilterList(FilterList.Operator.MUST_PASS_ONE, filters);
                    return orFilterList;
                }
            })
            .build();
    List<CityModel> cityModels = tableTemplate.scan(scanQueryParamsBuilder, CityModel.class);
    System.out.println(cityModels);
}
```



### 9.6 删除数据

```java
@Test
public void testDeleteData() {
    hBaseTemplate.delete("TEST:LEO_USER", "12003");
    hBaseTemplate.delete("TEST:LEO_USER", "11004", "INFO2");
    hBaseTemplate.delete("TEST:LEO_USER", "10001", "info1", "addresses");
    System.out.println("数据删除完成");
}
```

批量删除数据

```java
@Test
public void testDeleteBatch() {
    hBaseTemplate.deleteBatch("TEST:LEO_USER", Arrays.asList("10001", "10002"));
    hBaseTemplate.deleteBatch("TEST:LEO_USER", Collections.singletonList("10003"), "INFO2");
    hBaseTemplate.deleteBatch("TEST:LEO_USER", Collections.singletonList("10004"),
            "info1", "age", "username");
}
```

## 10. HQL

`hbase-sdk` 从2.0.6版本开始，开始提供HQL功能，并在3.0.0版本中得到极大优化，一种以类SQL的方式读写HBase集群的数据，进一步降低了普通API的使用复杂度。HQL的操作依赖`HBaseSqlTemplate`来完成，
因此在使用之前，需先构造好`HBaseSqlTemplate`的对象实例。

![hql](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/0qmhh.jpg)

### 10.1 构造HBaseSqlTemplate的示例

```java
// 1. 创建HBase SQL操作的模版类HBaseSqlTemplate
private HBaseSqlTemplate hBaseSqlTemplate = new HBaseTableTemplateImpl.Builder()
                .properties(getProperties()).build();

//  把HBase的连接配置信息存储在Properties中
Properties getProperties() {
    Properties properties = new Properties();
    properties.setProperty("hbase.zookeeper.quorum", "myhbase");
    properties.setProperty("hbase.zookeeper.property.clientPort", "2181");
    return properties;
}  
```
### 10.2 创建并注册HBaseTableSchema

```java
// 2. 创建HBaseTableSchema
HBaseTableSchema tableSchema = new HBaseTableSchema.Builder("test:test_sql")
        .addColumn("f1", "id")
        .addColumn("f1", "name")
        .addColumn("f1", "age", ColumnType.IntegerType)
        .addColumn("f2", "address")
        .addRow("row_key")
        .scanBatch(100)
        .scanCaching(1000)
        .deleteBatch(100)
        .scanCacheBlocks(false)
        .build();

// 3. 注册HBaseTableSchema至HBaseSqlContext中
HBaseSqlContext.registerTableSchema(tableSchema);

// tableSchema.printSchema();
```

打印schema

![printSchema](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/ela83.png)

### 10.3 Insert

插入一条数据

```sql
insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10001' , 'a_leo' , 15 , 'bj' ) where rowKey = 'a10001'
```

调用insert保存数据

```java
@Test
public void testInsertSql() {
   String hql = "insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '10001' , 'a_leo' , 15 , 'bj' ) where rowKey = 'a10001'";
	 sqlTemplate.insert(hql);
}
```

插入数据时还可以指定一些内置的rowkey function

```sql
-- 对rowKey进行md5
insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '11111' , 'a_leo' , 15 , 'bj' ) where rowKey = md5 ( 'a1111' )

-- 对rowKey md5取前4位作为前缀用|与原row拼接后形成新的rowKey
insert into test:test_sql ( f1:id , f1:name , f1:age , f2:address ) values ( '11111' , 'a_leo' , 15 , 'bj' ) where rowKey = md5_prefix ( 'a1111' )
```

row key function 暂时还不支持对参数列表的解析，暂时只能使用function ( 'row key值' )的形式。

查看保存的数据

```sql
select * from test:test_sql where rowKey = md5 ( 'a1111' )
select * from test:test_sql where rowKey = md5_prefix ( 'a1111' )
```

![row_function](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/vjz1y.png)

### 10.4 Select

select的调用方法如下：

```java
String hsql = "select * from test:test_sql where rowKey = md5_prefix ( 'a1111' )";
HBaseDataSet dataSet2 = sqlTemplate.select(hsql);
```



**1. 根据rowKey查询数据**

```sql
select * from test:test_sql where rowKey = 'a10001'
```

![select_by_row](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/jz8pd.png)

**2. 查询一批数据**，in row keys

```sql
select * from test:test_sql where rowKey in ( 'a10001' , 'a10002' , 'a10003' )
```

![in_row_keys](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/7cnqd.png)

**3. 根据startRowKey和endRowKey扫描数据**

```sql
select * from test:test_sql where ( startKey = 'a10001' , endKey = 'b20006' )
```

![select_limit](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/ixghh.png)

**4. 查询不同版本数据**

先保存三个版本数据

![save_data_3_version](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/u8x8x.png)

查询多版本数据

```sql
select f1:name from test:test_sql where rowKey = 'row_1000' and maxVersion = 3
```

![select-some-versions](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/gzxfr.png)

**3. 判断某一列值**

查询时需指定row过滤规则

```sql
select * from test:test_sql where ( startKey = 'a10001' , endKey = 'a10006' ) and f1:age <= 18
```

![select_by_filter_col](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/auaz3.png)

**4. limit**

```sql
select * from test:test_sql where ( startKey = 'a10001' , endKey = 'a10006' ) and f1:age <= 18 limit 2
```

![select_limit](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/dgtex.png)

**5. 查询一批rowkey数据**

```sql
select * from test:test_sql where rowKey in ( 'a10001' , 'a10002' , 'a10003' )
```

![select-in-rows](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/ruxbz.png)

### 10.5 delete

原始数据

![source-data](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/yit1x.png)

**删除某个row key的某个字段**

```shell
delete f1:age from test:test_sql where rowKey = 'b20004'
```

![delete-one-col](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/c2g7j.png)

**指定时间戳删除数据**

原始数据如下：

![原始数据](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/y835o.png)

指定时间戳来删除数据，由图示可知，时间戳为1670579504803的数据已被删除

```shell
delete f1:age from test:test_sql where rowKey = 'row_10001' and ts = 1670579504803
```

![delete_data](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/7ds2z.png)

```java
    @Test
    public void testDeleteSql(){
        String sql = "delete ( id , name ) from LEO_USER where startKey is stringkey ( 'a10001' ) , endKey is stringkey ( 'a10003' ) ( ( name equal 'leo' and age less '12' ) or ( id greater '10000' ) ) ts is '1604160000000'";
        sql = "delete * from LEO_USER where startKey is stringkey ( 'a10001' ) , endKey is stringkey ( 'a10003' ) ( ( name equal 'leo' and age less '12' ) or ( id greater '10000' ) ) ts is '1604160000000'";
        sql = "delete * from LEO_USER where rowKey is stringkey ( 'a10002' ) ( name equal 'leo' or age less '21' ) ts is '1604160000000'";

        hBaseSqlTemplate.delete(sql);
    }
```

## 11. HBaseThriftAPI

`hbase-client`中的API会直接连接zookeeper，如果客户端对Connection滥用，可能会造成zookeeper的连接耗尽。

`hbase-thrift`不仅有跨平台特性，同时也会在底层避免我们直接连接zk。

但如果直接使用hbase thrift的api，你可能会遇到以下几种情况：
1. 频繁创建TSocket连接，增加不必要的开销
2. 某一时间段内可能频繁创建过多的TSocket，造成本地短连接过多
3. 创建完一个TSocket，间隔时间过长不使用，会被服务端主动断开

为了解决上述问题，可以采取对hbase-thrift中的TSocket进行连接池封装。

### 11.1 创建thrift api模版操作类HBaseThriftTemplate

在`hbase-sdk`中HBase Thrift API 连接池的实现基于commons-pool2，类似jedis-pool，代码在`hbase-sdk-thrift`模块中。

使用thrift api之前，请先创建HBaseThriftTemplate的对象

```java
HBaseThriftTemplate thriftTemplate = HBaseThriftTemplateFactory.getInstance("localhost", 9090);

HBaseThriftTemplate thriftTemplate = HBaseThriftTemplateFactory.getInstance("localhost", 9090, 10);

HBaseThriftTemplate thriftTemplate = HBaseThriftTemplateFactory.getInstance("localhost", 9090, config);
```

HBaseThriftTemplate可接受的参数类型：

1. thrift server host
2. thrift server port
3. poolSize 连接池大小，设置后，连接池中核心和最大参数都将是此值
4. HBaseThriftPoolConfig config

config为连接池配置类，其默认配置如下，可按需修改：

```java
public class HBaseThriftPoolConfig extends GenericObjectPoolConfig {
    public HBaseThriftPoolConfig() {
        // 连接池中的最大连接数，默认1，根据服务端可以容纳的最大连接数和当前并发数进行合理设置
        setMaxTotal(1);
        // 连接池中确保的最少空闲连接数
        setMinIdle(1);
        // 连接池中允许的最大空闲连接数
        setMaxIdle(1);
        // 连接池用尽后，调用者是否等待，为true时，maxWaitMillis才生效
        setBlockWhenExhausted(true);
        // 连接池用尽后，调用者的最大等待时间，毫秒，默认-1，表示永不超时
        setMaxWaitMillis(6000);
        // 每次从资源池中拿/归还连接是否校验连接的有效性，默认false，避免每次使用或归还连接与服务端进行一次连接开销
        setTestOnBorrow(false);
        setTestOnReturn(false);
        // 开启JMX监控
        setJmxEnabled(true);
        // 是否开启空闲连接检测，默认false，建议true
        setTestWhileIdle(true);
        // 空闲连接的检测周期，毫秒，默认-1不进行检测，此处周期设置为3分钟
        setTimeBetweenEvictionRunsMillis(60 * 1000);
        // 空闲连接检测时，每次检测资源的个数，设置为-1，就是对所有连接进行检测
        setNumTestsPerEvictionRun(-1);
        // 连接池中连接的最小空闲时间，默认60000毫秒，6分钟
        setMinEvictableIdleTimeMillis(60 * 1000);
        //硬闲置  3秒没有占用设置为闲置, 检测线程直接剔除闲置，保持的最小空闲数，会被剔除且重新生成 硬闲置设置之后，软闲置设置无效
        //setMinEvictableIdleTimeMillis(3000);
        //软闲置  3秒没有占用设置为闲置, 当空闲连接>最小空闲数，才执行剔除闲置连接，否则维持最小空闲数，即使闲置了也不会剔除
        //setSoftMinEvictableIdleTimeMillis(3000);
    }
}
```

### 11.3 保存数据

**构造数据模型类**

```java
@HBaseTable(namespaceName = "test", tableName = "t1", defaultFamilyName = "info")
public class UserModel {
    @HBaseRowKey
    private String userId;
    @HBaseColumn()
    private String nickName;
    @HBaseColumn(familyName = "detail", columnName = "detailAddress")
    private String detailAddress;
    @HBaseColumn(familyName = "detail", toUpperCase = true)
    private double detailPay;
  	// 省略getter、setter
}
```

**或者直接保存Map中的数据**

```java
Map<String, Object> data = new HashMap<>();
data.put("info:nick_name", "会飞的猪");
data.put("detail:DETAIL_PAY", 1234.5);
data.put("detail:detailAddress", "上海黄浦区");
thriftTemplate.save("test:t1", "u10002", data);
```

### 11.4 查询数据

```java
@Test
public void testGetRow() {
    Optional<UserModel> userModel = thriftTemplate.getRow("u10001", UserModel.class);
    System.out.println(userModel);
    Map<String, String> data = thriftTemplate.getRowToMap("test:t1", "u10002", false);
    System.out.println(data);
}
```

查询多条row key

```java
@Test
public void testGetRows() {
    thriftTemplate.getRows(Arrays.asList("u10001", "u21000", "u22000"), UserModel.class);

    thriftTemplate.getRows(Arrays.asList("u10001", "u21000", "u22000"), "detail", UserModel.class);

    thriftTemplate.getRows(Arrays.asList("u10001", "u21000", "u22000"), "detail", Collections.singletonList("detailAddress"), UserModel.class);
}
```

Map结构数据保存时，会统一把数据转换为字符串类型，所以，当用java实体类绑定时，可能出现报错情况。

### 11.5 Scan数据

全表扫描所有数据，并设置limit

```java
@Test
public void testScanWithLimit() {
    ScanQueryParamsBuilder queryParams = new ScanQueryParamsBuilder.Builder()
            .limit(2)
            .build();
    // Map 保存的数据，与模型类保存的数据，非string类型不能互通
    List<UserModel> userModelList = thriftTemplate.scan(queryParams, UserModel.class);
    System.out.println(userModelList);
}
```

根据起止row key扫描数据，不包含stopRow

```java
@Test
public void testScanWithStarAndRow() {
    ScanQueryParamsBuilder queryParams = new ScanQueryParamsBuilder.Builder()
            .startRow("u10001")
            .stopRow("u21000")
            .build();

    List<UserModel> userModelList = thriftTemplate.scan(queryParams, UserModel.class);
    System.out.println(userModelList);
}
```

设置过滤器扫描，列名为nick_前缀，且列对应值ascii码比：不会飞的猪2大的被筛选出

```java
@Test
public void testScanWithFilter() {
    // 设置过滤器扫描，列名为nick_前缀，且列对应值ascii码比：不会飞的猪2大的被筛选出
    ScanQueryParamsBuilder queryParams = new ScanQueryParamsBuilder.Builder()
            .filter(new IHBaseFilter<String>() {
                @Override
                public String customFilter() {
                    return "ColumnPrefixFilter('nick_') AND ValueFilter(>=, 'binary:不会飞的猪2')";
                    }
                })
                .build();

    List<UserModel> userModelList = thriftTemplate.scan(queryParams, UserModel.class);
    System.out.println(userModelList);
}
```

更多API的使用可以参考源码中的测试用例以及相关的API文档。

## 12. 特别鸣谢

HQL的语法设计以及antlr4的语法解析，有参考alibaba的开源项目 `simplehbase`，在此特别感谢。simplehbase感觉是一个被遗弃的项目，针对的HBase版本是0。94，
已经有超过6年没有维护了。

`hbase-sdk` 在simplehbase的基础上进行重组和解耦，以兼容`hbase-sdk`原有的框架设计，并便于以后的扩展。

## 13. hbase-sdk 目前的不足

HQL的antlr4解析功能不太完善，对语法的要求比较严格，多一个空格少一个空格貌似都会引起语法错误。
后续会针对这些缺点一一优化。

## 14. 未来计划

- HBatis，类似于MyBatis的ORM框架，以XML管理SQL的方式维护集群数据的读写操作
- 集成Hystrix熔断框架，实现API层面的主备集群自动切换功能
- Thrift 连接池自动扩所容的能力
- 还有更多

## 15. 更新日志

### v3.0.0 2022-12-10

- 对hbase-sdk项目做了大重构，使API抽象程度更高，同时丰富了API的功能，也修复和完善了诸多BUG
- 基于reflectasm重构反射工具类，提升了ORM映射字段的效率
- HQL功能优化
- 工具类优化

### v2.0.7 2020-12-30

- HBase Thrift API上线，以及提供Thrift API 的连接池实现

### v2.0.6 2020-11-29

- HQL功能上线

### v2.0.5 2020-11-14

- 新增功能与代码优化

### v2.0.3 2020-10-08

- 大量重构和优化

### v1.0.5 2020-09-07

- 完善基础API的功能
- 完成ORM特性
- 模块拆分
- ......