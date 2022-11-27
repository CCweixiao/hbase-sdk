## hbase-sdk介绍

<p align="center">hbase-sdk是基于HBase的Client和Thrift原生API封装的一款轻量级的HBase ORM框架。 针对HBase各版本API（1.x~2.x）之间的差异，在其上剥离出了一层统一的抽象。并提供了以类SQL的方式来读写HBase表中的数据。</p>

<p align="center">在普通的java项目和Spring Boot项目中，你可以分别依赖hbase-sdk-template_${hbase.adapter.version}和spring-boot-starter-hbase_${hbase.adapter.version}两个模块来使用hbase-sdk封装的统一API。其中hbase.adapter.version暂只支持：hbase-1.2、hbase-1.4、hbase-2.2</p>

<p align="center">
    🐾 <a href="#快速开始" target="_blank">快速开始</a> | 
    🎬 <a href="#" target="_blank">视频教程</a> | 
    🌚 <a href="https://github.com/CCweixiao/hbase-sdk/blob/master/README.md" target="_blank">官方文档</a> | 
    💰 <a href="https://www.jielongping.com" target="_blank">捐赠我们</a> |
    🌾 <a href="README.md">English</a>
</p>


***

##  hbase-sdk的优势

`hbase-sdk` 基于HBase的原生API，封装了对HBase表及其数据的DML和DDL操作，同时，也是一款轻量级的ORM框架，提供了模型类绑定HBase表实体的能力，与原生的客户端API相比，其优势如下：

1. 屏蔽了HBase各版本原生API的差异，当你的集群版本升级时，在业务层面只需对应升级hbase-client的版本即可。
2. 提供ORM能力，java类通过简单的注解就可以定义HBase的表模型，简化了表数据的DDL操作。
3. 对hbase的原生thrift api进行了池化封装，类似于Jedis-pool，增强了thrift api生产环境中使用的稳定性。
4. 使用spring-boot-starter-hbase可无缝与SpringBoot集成。
5. 提供了类SQL的方式——HQL读写HBase表中的数据，简化了原生API的使用门槛（但目前不建议直接用于生产环境）。


API文档地址: [https://weixiaotome.gitee.io/hbase-sdk/](https://weixiaotome.gitee.io/hbase-sdk/)
如果觉得这个项目不错可以 [star](https://github.com/CCweixiao/hbase-sdk/stargazers) 支持或者 [捐赠](https://www.jielongping.com) 它 :blush:

## 功能特性

* [x] 定义了统一的接口规范，消除了HBase不同版本原生API之间的差异
* [x] ORM特性，数据查询结果集自动映射Java实体类
* [x] 对HBase的原生thrift API进行池化封装，增强了其生产环境中使用的稳定性
* [x] HQL，以类SQL的形式读写HBase的表中数据（不建议直接用于生产环境）
* [x] 利用spring-boot-starter-hbase无缝与SpringBoot集成
* [x] HBatis，类似于myBatis，提供配置文件管理HQL的功能（规划中）
* [x] 熔断能力，提供客户端API级别的主备集群切换，保障服务的高可用（规划中）
* [x] thrift 连接池中连接数的动态扩所容能力（规划中）

## 仓库地址

https://github.com/CCweixiao/hbase-sdk

https://gitee.com/weixiaotome/hbase-sdk

两边仓库地址是同步更新的

## 编译指南

克隆项目到本地，导入到IDEA中，首次加载项目，会从远程仓库拉取项目所需的依赖，请耐心等待。

cd hbase-sdk

```shell
mvn clean install -Phbase-1.2 # hbase-client:1.2.x
mvn clean install -Phbase-1.4 # hbase-client:1.4.x
mvn clean install -Phbase-2.2 # hbase-client:2.x.x
```

`hbase-sdk`的``hbase-sdk-adapter`模块下的各个子模块中已引入了具体的hbase-shaded-client的依赖，如有需要可以自行更改你想使用的hbase的版本。

## 快速开始

`hbase-sdk` 的各个版本完成开发测试之后，都会发布到maven中央仓库之中，只是最新版本的代码有一定的延迟。如果你想在第一时间体验该项目，可以选择克隆Gitee或Github仓库中的源码，在本地编译并运行测试用例。

`hbase-sdk` 如果你想在本地进行开发，请确保已经安装了Java8和maven3.6+。同时建议在本地部署一个可连通的HBase环境。建议使用docker来快速搭建一个HBase的开发环境，请参考：https://blog.csdn.net/feinifi/article/details/121174846

`hbase-sdk` 开发所用的工具为IDEA，所以也极力推荐导入项目到idea中。

### 1. 普通项目

`Maven` 配置：

创建一个基础的 `Maven` 工程，HBase SDK 已适配hbase-client的1.2/1.4/2.x版本。

所以，如果你的HBase版本是1.2.x，可以使用如下依赖。


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

当然，如果你想重新编译，扩展你需要的功能，也可以选择下载源码，修改项目根pom.xml文件中的`hbase.version`，按照编译指南中的编译命令来操作。

### 2. 项目结构

![project](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/4nj24.jpg)

API核心类继承结构示意图：
![api-project](https://leo-jie-pic.oss-cn-beijing.aliyuncs.com/leo_blog/2020-11-29-120043.jpg)


### 3. 在SpringBoot项目中使用

`Maven` 配置：

创建一个基于`Maven`的spring boot工程。

```xml
<dependency>
    <groupId>com.github.CCweixiao</groupId>
    <artifactId>spring-boot-starter-hbase_1.2</artifactId>
    <version>3.0.0-SNAPSHOT</version>
</dependency>
```

or

```xml
<dependency>
    <groupId>com.github.CCweixiao</groupId>
    <artifactId>spring-boot-starter-hbase_1.4</artifactId>
    <version>3.0.0-SNAPSHOT</version>
</dependency>
```

or

```xml
<dependency>
    <groupId>com.github.CCweixiao</groupId>
    <artifactId>spring-boot-starter-hbase_2.2</artifactId>
    <version>3.0.0-SNAPSHOT</version>
</dependency>
```

spring-boot-starter-hbase这个模块中已经包含了hbase-sdk-adapter_${hbase.adapter.version}。

### 4. 引入hbase-client的依赖

除了引入`hbase-sdk`的相关依赖之外，你还需要引入`hbase-client`的依赖，`hbase-client`的版本目前支持`1.2.x`、`1.4.x`、`2.2.x`，请按需引入，（并且，建议使用hbase-shaded-client）。

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

### 5. 配置HBase数据库连接

**普通java项目**

```java
Properties properties = new Properties();
properties.setProperty("hbase.zookeeper.quorum", "myhbase");
properties.setProperty("hbase.zookeeper.property.clientPort", "2181");
// 请按需引入其他所需hbase的client的配置

// 数据读写API的操作模版类
IHBaseTableTemplate tableTemplate = new HBaseAdminTemplateImpl.Builder()
                .properties(properties).build();
// 管理员操作模版类
IHBaseAdminTemplate adminTemplate = new HBaseAdminTemplateImpl.Builder()
                .properties(properties).build();
// HQL操作模版类
IHBaseSqlTemplate sqlTemplate = new HBaseSqlTemplateImpl.Builder()
                .properties(properties).build()
```

**spring boot项目**

application.yaml

```yaml
spring:
  data:
    hbase:
      quorum: myhbase
      node-parent: /hbase
      zk-client-port: 2181
      root-dir: /hbase
      client-properties: hbase.client.retries.number=3
```

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


## Contents
- [**`集群管理`**](#集群管理)
  - [**`创建namespace`**](#创建namespace)
  - [**`创建表`**](#创建表)
  - [**`更多操作`**](#更多操作)
- [**`数据读写`**](#数据读写)
  - [**`创建数据模型类`**](#创建数据模型类)
  - [**`保存数据`**](#保存数据)
  - [**`批量保存数据`**](#批量保存数据)
  - [**`根据RowKey查询`**](#根据RowKey查询)
  - [**`scan查询`**](#scan查询)
  - [**`删除数据`**](#删除数据)
- [**`HQL`**](#HQL)
  - [**`insert`**](#insert)
  - [**`select`**](#select)
  - [**`delete`**](#delete)
- [**`HBaseThriftAPI`**](#HBaseThriftAPI)
  - [**`创建HBaseThriftService连接池`**](#创建HBaseThriftService连接池)

## 集群管理

HBaseAdminTemplate封装了HBaseAdmin的常用操作，比如namespace的管理、表的管理、以及快照管理等等，后续这些API将会更加完善。

![admin-api](https://leo-jie-pic.oss-cn-beijing.aliyuncs.com/leo_blog/2020-11-29-120523.jpg)

### 创建namespace


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

### 创建表

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

### 更多操作

可以参考相关API文档或测试用例

## 数据读写

类似于Hibernate，你也可以使用hbase-sdk框架所提供的ORM特性，来实现对HBase的数据读写操作。

![api-data](http://leo-jie-pic.oss-cn-beijing.aliyuncs.com/blog/gwjtl.jpg)


### 创建数据模型类

```java
public class CityTag {
    private String tagName;

    public CityTag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public String toString() {
        return "CityTag{" +
                "tagName='" + tagName + '\'' +
                '}';
    }
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

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public Integer getCityArea() {
        return cityArea;
    }

    public void setCityArea(Integer cityArea) {
        this.cityArea = cityArea;
    }

    public Integer getTotalPopulation() {
        return totalPopulation;
    }

    public void setTotalPopulation(Integer totalPopulation) {
        this.totalPopulation = totalPopulation;
    }

    public List<CityTag> getCityTagList() {
        return cityTagList;
    }

    public void setCityTagList(List<CityTag> cityTagList) {
        this.cityTagList = cityTagList;
    }

    @Override
    public String toString() {
        return "CityModel{" +
                "cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", cityAddress='" + cityAddress + '\'' +
                ", cityArea=" + cityArea +
                ", totalPopulation=" + totalPopulation +
                ", cityTagList=" + cityTagList +
                '}';
    }
}
```

```java
@HBaseTable(namespaceName = "default", tableName = "t2", defaultFamilyName = "info")
```

`@HBaseTable`注解用于定义HBase的表信息，namespaceName用于定义该表的命名空间，如果不指定，默认是default，tableName用于定义该表的表名，如果不指定，表名则为类名的组合单词拆分加'_'拼接，如：CityModel对应的表名为：city_model。
defaultFamilyName用于定义如果所有的字段不特配置列簇名，则使用此处配置的列簇名。

`@HBaseRowKey`注解用于标识某一个属性字段是用作存储rowKey数据的，是必须要设置的，如：

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


familyName用于定义列簇名称，如果不指定，则使用defaultFamilyName配置的列簇名。

columnName指定列名，不指定则默认使用字段名的组合单词拆分加'_'拼接，如：isVip，对应的字段名是：is_vip.

toUpperCase表示字段名是否转大写，默认false，不做转换。


### 保存数据

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
       System.out.println(city);
      
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

### 根据RowKey查询

```java
    @Test
    public void testGetJavaBean() {
        Optional<CityModel> a10001 = tableTemplate.getRow("a10001", CityModel.class);
        Optional<CityModel> a10001F = tableTemplate.getRow("a10001", "info", CityModel.class);
        System.out.println(a10001.orElse(new CityModel()));
        System.out.println(a10001F);
    }
```

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

### scan查询

```java
    @Test
    public void testScan() {
        ScanQueryParamsBuilder scanQueryParamsBuilder = new ScanQueryParamsBuilder.Builder()
                .familyName("info")
                .columnNames(Arrays.asList("city_name", "city_address", "cityTagList"))
                .startRow("a10001")
                .stopRow("a10002")
                .build();
        List<CityModel> cityModels = tableTemplate.scan(scanQueryParamsBuilder, CityModel.class);
        System.out.println(cityModels);
    }
```

### 删除数据

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

## HQL

`hbase-sdk` 从2.0.6版本开始，开始提供HQL功能，一种以类SQL的方式读写HBase集群的数据，降低API的使用复杂度。HQL的操作依赖`HBaseSqlTemplate`来完成，
因此使用之前，必须构造好`HBaseSqlTemplate`的对象实例。

![hql](https://leo-jie-pic.oss-cn-beijing.aliyuncs.com/leo_blog/2020-11-29-121658.jpg)

构造HBaseSqlTemplate的示例。

```java
    private HBaseSqlTemplate hBaseSqlTemplate;

    @Before
    public void testInitHBaseSqlTemplate() {
        hBaseSqlTemplate = new HBaseSqlTemplate("localhost", "2181");

        List<HBaseColumnSchema> hBaseColumnSchemas = createHBaseColumnSchemaList();
        HBaseTableSchema hBaseTableSchema = new HBaseTableSchema();
        hBaseTableSchema.setTableName("LEO_USER");
        hBaseTableSchema.setDefaultFamily("g");
        //hBaseTableSchema.setRowKeyHandlerName("string");

        HBaseTableConfig hBaseTableConfig = new DefaultHBaseTableConfig(hBaseTableSchema, hBaseColumnSchemas);

        hBaseSqlTemplate.setHBaseTableConfig(hBaseTableConfig);
    }

        public List<HBaseColumnSchema> createHBaseColumnSchemaList() {
            List<HBaseColumnSchema> hBaseColumnSchemas = new ArrayList<>();
    
            HBaseColumnSchema hBaseColumnSchema1 = new HBaseColumnSchema();
            hBaseColumnSchema1.setFamily("g");
            hBaseColumnSchema1.setQualifier("id");
            hBaseColumnSchema1.setTypeName("string");
    
            HBaseColumnSchema hBaseColumnSchema2 = new HBaseColumnSchema();
            hBaseColumnSchema2.setFamily("g");
            hBaseColumnSchema2.setQualifier("name");
            hBaseColumnSchema2.setTypeName("string");
    
            HBaseColumnSchema hBaseColumnSchema3 = new HBaseColumnSchema();
            hBaseColumnSchema3.setFamily("g");
            hBaseColumnSchema3.setQualifier("age");
            hBaseColumnSchema3.setTypeName("int");
    
            HBaseColumnSchema hBaseColumnSchema4 = new HBaseColumnSchema();
            hBaseColumnSchema4.setFamily("g");
            hBaseColumnSchema4.setQualifier("address");
            hBaseColumnSchema4.setTypeName("string");
    
            hBaseColumnSchemas.add(hBaseColumnSchema1);
            hBaseColumnSchemas.add(hBaseColumnSchema2);
            hBaseColumnSchemas.add(hBaseColumnSchema3);
            hBaseColumnSchemas.add(hBaseColumnSchema4);
    
            return hBaseColumnSchemas;
        }
```
构造hBaseSqlTemplate示例需要先构造HBaseTableConfig，HBaseTableConfig的两个成员变量，

```java
    protected HBaseTableSchema hBaseTableSchema;
    protected  List<HBaseColumnSchema> hBaseColumnSchemaList;
```

分别用来表的Schema信息和HBase表对应列的元数据信息。

针对HBase表列的数据类型转换，目前内置的实现有：

Boolean、Byte、Char、Date、Double、Float、Hex、Int、Long、Short、String

通过实现`LiteralInterpreter`接口，你可以定义自己的列数据类型转换实现。


```json
{
 "tableName":"TEST:USER",
 "defaultFamily":"INFO",
 "columnSchema":[
  {
   "family":"INFO",
   "qualifier":"name",
   "typeName":"string"
  },
  {
   "family":"INFO2",
   "qualifier":"age",
   "typeName":"int"
  }
 ]
}
```

通过实现相应的接口，你可以选择加载HBase表、列元数据信息的方式。如：类型myBatis在XML文件中加载。

HBaseSqlTemplate的实例准备好之后，就可以使用HQL来进行数据读写。


### insert

```sql
insert into LEO_USER ( g:id , g:name , g:age , g:address ) values ( '10001', 'leo1' , '18', 'shanghai' ) where rowKey is stringkey ( 'a10002' ) ts is '1604160000000'

insert into LEO_USER ( g:id , g:name , g:age , g:address ) values ( '10002', 'leo2' , '17', 'beijing' ) where rowKey is stringkey ( 'a10002' )


```

```java
    @Test
    public void testInsertSql() {
        String sql = "insert into LEO_USER ( g:id , g:name , g:age , g:address ) values ( '10001', 'leo' , '18', 'shanghai' ) where rowKey is stringkey ( 'a10002' ) ts is '1604160000000'";
        hBaseSqlTemplate.insert(sql);
        System.out.println("insert successfully!");
    }
```

### select

```sql
select ( g:id , g:name , g:age , g:address ) from LEO_USER where startKey is stringkey ( 'a10001' ) , endKey is stringkey ( 'a10002' ) ( ( name equal 'leo' and age less '12' ) or ( id greater '10000' ) )  maxversion is 2  startTS is '1604160000000' , endTS is '1604160000001' limit 1, 10 

select * from LEO_USER where startKey is stringkey ( 'a10001' ) , endKey is stringkey ( 'a10002' ) ( ( name equal 'leo1' and age less '20' ) or ( id greater '10000' ) )  maxversion is 2  startTS is '1604160000000' , endTS is '1604160000001' limit 10
```

```java
    @Test
    public void testSelectSql() {
        String sql = "select ( g:id , g:name , g:age , g:address ) from LEO_USER where startKey is stringkey ( 'a10001' ) , endKey is stringkey ( 'a10002' ) ( ( name equal 'leo' and age less '12' ) or ( id greater '10000' ) )  maxversion is 2  startTS is '1604160000000' , endTS is '1604160000001' limit 10 ";
        sql = "select * from LEO_USER where startKey is stringkey ( 'a10001' ) , endKey is stringkey ( 'a10002' ) ( ( name equal 'leo' and age less '12' ) or ( id greater '10000' ) )  maxversion is 2  startTS is '1604160000000' , endTS is '1604160000001' limit 10 ";

        final List<List<HBaseCellResult>> listList = hBaseSqlTemplate.select(sql);

        listList.forEach(dataList -> {
            dataList.forEach(data -> {
                System.out.println(data.getRowKey());
                System.out.println(data.getFamilyStr());
                System.out.println(data.getQualifierStr());
                System.out.println(data.getTsDate());
                System.out.println("########################################");

            });
        });
    }
```

### delete

```sql
delete ( id , name ) from LEO_USER where startKey is stringkey ( 'a10001' ) , endKey is stringkey ( 'a10003' ) ( ( name equal 'leo' and age less '12' ) or ( id greater '10000' ) ) ts is '1604160000000'
delete * from LEO_USER where startKey is stringkey ( 'a10001' ) , endKey is stringkey ( 'a10003' ) ( ( name equal 'leo' and age less '12' ) or ( id greater '10000' ) ) ts is '1604160000000'
delete * from LEO_USER where rowKey is stringkey ( 'a10002' ) ( name equal 'leo2' or age less '21' ) ts is '1604160000000'
```

```java
    @Test
    public void testDeleteSql(){
        String sql = "delete ( id , name ) from LEO_USER where startKey is stringkey ( 'a10001' ) , endKey is stringkey ( 'a10003' ) ( ( name equal 'leo' and age less '12' ) or ( id greater '10000' ) ) ts is '1604160000000'";
        sql = "delete * from LEO_USER where startKey is stringkey ( 'a10001' ) , endKey is stringkey ( 'a10003' ) ( ( name equal 'leo' and age less '12' ) or ( id greater '10000' ) ) ts is '1604160000000'";
        sql = "delete * from LEO_USER where rowKey is stringkey ( 'a10002' ) ( name equal 'leo' or age less '21' ) ts is '1604160000000'";

        hBaseSqlTemplate.delete(sql);
    }
```

## HBaseThriftAPI

HBase常用的客户端API会直接连接zookeeper，如果api使用不当，产生BUG，会造成zookeeper的连接耗尽；HBaseThriftApi不仅有跨平台特性，
同时也会在底层避免我们直接连接zk。

如果直接使用hbase thrift的api，你可能会遇到以下几种情况：
1. 频繁创建TSocket连接，不必要的开销增加
2. 某一时间段内可能频繁创建过多的TSocket，造成本地短连接过多
3. 创建完一个TSocket，间隔时间过长不使用，会被服务端主动断开

### 创建HBaseThriftService连接池

为了解决上述问题，所以采取连接池的实现方式。HBase Thrift API 连接池的实现基于commons-pool2，类似jedis。

连接池的使用也非常简单

```java
HBaseThriftService hBaseThriftService = HBaseThriftServiceHolder.getInstance("localhost", 9090);
HBaseThriftService hBaseThriftService = HBaseThriftServiceHolder.getInstance("localhost", 9090, 10);
List<String> allTableNames = hBaseThriftService.getTableNames();

```

更多API的使用可以参考源码中测试用例以及相关的API文档。

## 特别鸣谢

HQL的语法设计以及antlr4的语法解析，有参考alibaba的开源项目 `simplehbase`，在此特别感谢。simplehbase感觉是一个被遗弃的项目，针对的HBase版本是0。94，
已经有超过6年没有维护了。

`hbase-sdk` 在simplehbase的基础上进行重组和解耦，以兼容`hbase-sdk`原有的框架设计，并便于以后的扩展。

## hbase-sdk 目前的不足

非HQL的数据读写API还不丰富，特别是数据过滤的查询API。

HQL的antlr4解析功能不太完善，比如，目前HQL对中文要求不太好，同时，HQL对语法的要求比较严格，多一个空格少一个空格貌似都会引起语法错误。
后续会针对这些缺点一一优化。

## 未来计划

- HBatis，类似于MyBatis的ORM框架，以XML管理SQL的方式维护集群数据的读写操作
- 集成Hystrix熔断框架，实现API层面的主备集群自动切换功能
- 还有更多

## 更新日志

### v3.0.0 2022-11-27

- 对hbase-sdk项目做了大重构，使API抽象程度更高，同时丰富了API的功能
- 基于reflectasm重构反射工具类，提升ORM映射字段的效率
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





