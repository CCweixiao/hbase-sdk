<p align="center"><h3>hbase-sdk</h3></p>

<p align="center">基于HBase Client的相关API开发而来的一款轻量级的HBase ORM框架。提供SQL查询功能，以类SQL的方式——HQL读写HBase数据。 😋</p>

<p align="center">针对HBase 1.x和2.xAPI的不同之处，在最上层做了统一的封装。</p>

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

`hbase-sdk` 是一款轻量级的ORM框架，封装了对HBase数据表的读写操作和对集群的运维管理，并针对HBase1.x的API和2.xAPI的差异，做了统一的定义，
提供更加方便的调用API。同时，HQL的功能也已上线，提供了类SQL读写数据的能力，这将大大降低HBase Client API的使用门槛。
。API文档地址: [https://weixiaotome.gitee.io/hbase-sdk/](https://weixiaotome.gitee.io/hbase-sdk/)
如果觉得这个项目不错可以 [star](https://github.com/CCweixiao/hbase-sdk/stargazers) 支持或者 [捐赠](https://www.jielongping.com) 它 :blush:

## 功能特性

* [x] 消除不同版本API的差异，重新定义接口规范
* [x] 优良的ORM特性，数据查询结果集自动映射Java实体类
* [x] HQL，以类SQL的形式读写HBase的表中数据
* [x] 利用spring-boot-starter-hbase无缝与SpringBoot集成
* [x] HBatis，类似于myBatis，提供配置文件管理HQL的功能（规划中）
* [x] JDK8+


## 快速开始

`hbase-sdk` 的每个版本经过测试完成之后，都会编译打包至各个模块，最后发布到maven中央仓库之中，只是最新版本的代码有一定的延迟。如果你想在第一时间体验该项目，
可以选择在Gitee或Github中克隆源码，在本地编译并运行测试用例。

`hbase-sdk` 基于java8开发，如果你想自己编译或体验，请确保已经安装了Java8和maven3+。 此外，如果你想在本地进行开发调试，建议在本地存在一个可连通的HBase环境。
如果你想快速搭建一个HBase的开发环境，请参考：[https://www.jielongping.com/archives/dockerhbasetest](https://www.jielongping.com/archives/dockerhbasetest)

`hbase-sdk` 开发所用的工具为IDEA，所以也极力推荐导入项目到idea中。

### 1. 普通项目

`Maven` 配置：

创建一个基础的 `Maven` 工程，HBase SDK API开发时基于的HBase版本主要是1.4.3和2.1.0。

所以，如果你的HBase版本是1.x，可以使用如下依赖。


```xml
<dependency>
    <groupId>com.github.CCweixiao</groupId>
    <artifactId>hbase-sdk-core_1.4</artifactId>
    <version>2.0.6</version>
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

`hbase-sdk`目前最新的版本是`2.0.6`。你可以在maven仓库中搜索CCweixiao来获取hbase-sdk相关jar包的最新版本。
[https://mvnrepository.com/artifact/com.github.CCweixiao](https://mvnrepository.com/artifact/com.github.CCweixiao)

当然，如果你想重新编译，以适配你自己的HBase版本，也可以选择下载源码，修改项目根pom.xml文件中的`hbase.version`，之后运行如下编译命令：

```shell script
git clone https://github.com/CCweixiao/hbase-sdk.git  or
git clone https://gitee.com/weixiaotome/hbase-sdk.git
cd hbase-sdk
mvn clean install -Dmaven.test.skip=true
```

### 2. 项目结构

![project](https://leo-jie-pic.oss-cn-beijing.aliyuncs.com/leo_blog/2020-11-29-114449.jpg)

API核心类继承结构示意图：
![api-project](https://leo-jie-pic.oss-cn-beijing.aliyuncs.com/leo_blog/2020-11-29-120043.jpg)


### 3. 在SpringBoot项目中使用

`Maven` 配置：

创建一个基于`Maven`的spring boot工程。

```xml
<dependency>
    <groupId>com.github.CCweixiao</groupId>
    <artifactId>spring-boot-starter-hbase_1.4</artifactId>
    <version>2.0.6</version>
</dependency>
```

or

```xml
<dependency>
    <groupId>com.github.CCweixiao</groupId>
    <artifactId>spring-boot-starter-hbase_2.1</artifactId>
    <version>2.0.6</version>
</dependency>
```

spring-boot-starter-hbase这个模块中已经包含了hbase-sdk-core。

### 4. 引入hbase-client的依赖

除了引入`hbase-sdk`的相关依赖之外，你还需要引入`hbase-client`的依赖，`hbase-client`的版本目前建议为`1.2.x`、`1.4.x`、`2.1.x`。
hbase-client1.x和2.x的新旧API有所差异。未来，`hbase-sdk`会持续完善该依赖的版本兼容。

```xml
<dependency>
    <groupId>org.apache.hbase</groupId>
    <artifactId>hbase-client</artifactId>
    <version>1.4.3</version>
</dependency>        
```

or

```xml
<dependency>
    <groupId>org.apache.hbase</groupId>
    <artifactId>hbase-client</artifactId>
    <version>2.1.0</version>
</dependency>        
```

### 5. 配置HBase数据库连接

**普通项目**

```java
// 数据读写API
HBaseTemplate hBaseTemplate = new HBaseTemplate("node1", "2181");
// 管理员API
HBaseAdminTemplate hBaseAdminTemplate = new HBaseAdminTemplate("node1", "2181");
// HQL操作API
HBaseSqlTemplate hBaseSqlTemplate = new HBaseSqlTemplate("localhost", "2181");
```

**spring boot项目**

application.yaml

```yaml
spring:
  data:
    hbase:
      quorum: node1,node2,node3
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
    - [**`根据RowKey查询`**](#根据RowKey查询)
    - [**`scan查询`**](#scan查询)
    - [**`删除数据`**](#删除数据)
- [**`HQL`**](#HQL) 
    - [**`insert`**](#insert)
    - [**`select`**](#select)
    - [**`delete`**](#delete)
    
    
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

        hBaseTemplate.createNamespace(namespaceDesc);
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

        hBaseTemplate.createTable(tableDesc, false);
    }
```

### 更多操作

可以参考相关API文档或测试用例

## 数据读写

类似于Hibernate，你也可以使用hbase-sdk框架所提供的ORM特性，来实现对HBase的数据读写操作。

![api-data](https://leo-jie-pic.oss-cn-beijing.aliyuncs.com/leo_blog/2020-11-29-121009.jpg)


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

`hbase-sdk` 从2.0.6版本开始，开始通过HQL，一种以类SQL的方式读写集群数据，降低API的使用难度。HQL的操作依赖`HBaseSqlTemplate`来完成，
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

```sql
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






