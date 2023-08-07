-- 进入容器
docker exec -it <container> bash
-- 连接psql
psql -U postgres -h 127.0.0.1
-- 如果需要创建数据库
psql -d <database>;
-- 查看数据库列表
\l
-- 选择进入数据库
\c <database>
-- 为数据库执行分词脚本，见下文

-- 执行脚本 BEGIN --
-- 创建分词扩展
CREATE EXTENSION zhparser;
CREATE TEXT SEARCH CONFIGURATION chinese (PARSER = zhparser);
ALTER TEXT SEARCH CONFIGURATION chinese
    ADD MAPPING FOR a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z
        WITH simple;
-- 新创建的库会自动执行下面的步骤，等待一会儿就行
-- 1.创建模式zhparser
-- 2.创建分词函数
-- 执行脚本 END --


-- 测试
SELECT to_tsvector('chinese', '人生苦短，乘早摸鱼，Good Morning~');
to_tsvector
--------------------------------------------------------
'good':8 'morning':9 '乘':4 '人生':1 '摸':6 '早':5 '短':3 '苦':2 '鱼':7

-- 添加自定义词典
insert into zhparser.zhprs_custom_word values ('摸鱼');
insert into zhparser.zhprs_custom_word values ('荒天帝');
insert into zhparser.zhprs_custom_word values ('独断万古');
insert into zhparser.zhprs_custom_word values ('人生苦短');
-- 词典生效
select sync_zhprs_custom_word();

-- test
SELECT * FROM ts_parse('zhparser', '人生苦短，爆炸吧，小宇宙，独断万古荒天帝，摸鱼ing，Good Morning~');
SELECT to_tsquery('chinese', '荒天帝石昊');
SELECT to_tsvector('chinese', '人生苦短，爆炸吧，小宇宙，独断万古荒天帝，摸鱼ing，Good Morning~');
