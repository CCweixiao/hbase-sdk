-- 插入单条语句，指定时间戳
insert into test:table(f:id,f:name,f:age, f2:pay)values('1001','张三', 23.32)
where ts = 12234243;

-- 插入单条语句，不指定时间戳
insert into test:table(f:id,f:name,f:age, f2:pay)values('1001','张三', 23.32);

-- 插入多条语句，不指定时间戳
insert into test:table (f:id,f:name,f:age, f2:pay) VALUES('1001','张三', 23.32 )
        , ( '1002' , '李四' , 23.32);

-- 插入多条语句，指定时间戳
insert into test:table (f:id,f:name,f:age, f2:pay) VALUES('1001','张三', 23.32 )
        , ( '1002' , '李四' , 23.32)where ts = 12234243;

-- 空值插入
insert into test:table (f:id,f:name,f:age, f2:pay) VALUES('1001','张三', 23.32 )
        , ( '1002' , '李四' , 23.32),( '1003' , 'null' , null);



-- 根据row查询单条数据
select * from test:table where rowkey = '1001'
select f:id,f:name, f:age from test:table where rowkey = '1001'

-- 根据row key in查询
select f:id,f:name, f:age from test:table where rowkey in ('ds')


select f:id,f:name, f:age from test:table where STARTKEY='start_row' AND ENDKEY='end_row'
select f:id,f:name, f:age from test:table where STARTKEY = 'start'
select f:id,f:name, f:age from test:table where ENDKEY = 'end'
select f:id,f:name, f:age from test:table where rowKey in ('1001','1002',md5('abc'))

select f:id,f:name, f:age from test:table
where rowKey in ('1001','1002',md5(we,'abc', 'dsd', '23'))



select f:id,f:name, f:age from test:table where rowKey = 23


-- 多版本查询
select f:id,f:name, f:age from test:table where rowkey = '1001'
                                            and ts = 121 limit 10;

select f:id,f:name, f:age from test:table where rowkey = '1001'
                                            and maxversion =12 limit 10;



select f:id,f:name, f:age from test:table where STARTKEY='start_row' AND ENDKEY='end_row'
                                            and ( f1:name = 'sd' or ( f1:age = 12 and f1:address = '上海')) and maxversion = 3


delete from test:table where rowkey = '1231ew' and (f1:name = 'dew' and  f1:age= 12) and ts = 21332312313


/**
多行注释
**/
-- 单行注释
insert into
-- 注释
    test:table ( f:id , f:name,
                 f:age, f2:pay, f3:detail) VALUES('1001','张三', 23.32,null )
        , ( 'null' , '李四' , 23.32, '{\"name\": \"leo\",\"age\": 18}');


/**
多行注释
**/
-- 单行注释
insert into
-- 注释
    test:table ( f:id , f:name,
                 f:age, f2:pay, f3:detail) VALUES('1001','张三', 23.32,null )
        , ( 'null' , '[{"rule_id":"反弹shell","format_output":"进程 pname 反向连接到 %dest_ip%:%dest_port%","info":{"process_events":{"pid":21,"pname":"nginx","cmdline":"curl www.cfda.com","ppid":7,"ppname":"bash"},"proc_trees":[{"pid":21,"pname":"nginx","cmdline":"curl www.cfda.com","ppid":7,"ppname":"bash"}],"containers":{"container_id":"fef4636d8403871c2e56e06e51d609554564adbbf8284dd914a0f61130558bdf","container_name":"nginx","image_id":"4eb8f7c43909449dbad801c50d9dccc7dc86631e54f28b1a4b13575729065be8","status":"Running"},"sockets":{"src_ip":"127.0.0.1","src_port":"8080","type":"1","in_out":"0","dest_ip":"localhost","dest_port":"80"}}}]' , 23.32, 'ds   d');