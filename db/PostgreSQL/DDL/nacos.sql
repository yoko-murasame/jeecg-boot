drop table if exists config_info cascade;

drop table if exists config_info_aggr cascade;

drop table if exists config_info_beta cascade;

drop table if exists config_info_tag cascade;

drop table if exists config_tags_relation cascade;

drop table if exists group_capacity cascade;

drop table if exists his_config_info cascade;

drop table if exists permissions cascade;

drop table if exists roles cascade;

drop table if exists tenant_capacity cascade;

drop table if exists tenant_info cascade;

drop table if exists users cascade;

create table if not exists config_info
(
    id                 bigserial
        primary key,
    data_id            varchar(255) not null,
    group_id           varchar(255),
    content            text         not null,
    md5                varchar(32),
    gmt_create         timestamp(6) not null,
    gmt_modified       timestamp(6) not null,
    src_user           text,
    src_ip             varchar(20),
    app_name           varchar(128),
    tenant_id          varchar(128),
    c_desc             varchar(256),
    c_use              varchar(64),
    effect             varchar(64),
    type               varchar(64),
    c_schema           text,
    encrypted_data_key text         not null
);

comment on table config_info is 'config_info';

comment on column config_info.id is 'id';

comment on column config_info.data_id is 'data_id';

comment on column config_info.content is 'content';

comment on column config_info.md5 is 'md5';

comment on column config_info.gmt_create is '创建时间';

comment on column config_info.gmt_modified is '修改时间';

comment on column config_info.src_user is 'source user';

comment on column config_info.src_ip is 'source ip';

comment on column config_info.tenant_id is '租户字段';

comment on column config_info.encrypted_data_key is '秘钥';

create unique index if not exists uk_configinfo_datagrouptenant
    on config_info (data_id, group_id, tenant_id);

create table if not exists config_info_aggr
(
    id           bigserial
        primary key,
    data_id      varchar(255) not null,
    group_id     varchar(255) not null,
    datum_id     varchar(255) not null,
    content      text         not null,
    gmt_modified timestamp(6) not null,
    app_name     varchar(128),
    tenant_id    varchar(128)
);

comment on table config_info_aggr is '增加租户字段';

comment on column config_info_aggr.id is 'id';

comment on column config_info_aggr.data_id is 'data_id';

comment on column config_info_aggr.group_id is 'group_id';

comment on column config_info_aggr.datum_id is 'datum_id';

comment on column config_info_aggr.content is '内容';

comment on column config_info_aggr.gmt_modified is '修改时间';

comment on column config_info_aggr.tenant_id is '租户字段';

create unique index if not exists uk_configinfoaggr_datagrouptenantdatum
    on config_info_aggr (data_id, group_id, tenant_id, datum_id);

create table if not exists config_info_beta
(
    id                 bigserial
        primary key,
    data_id            varchar(255) not null,
    group_id           varchar(128) not null,
    app_name           varchar(128),
    content            text         not null,
    beta_ips           varchar(1024),
    md5                varchar(32),
    gmt_create         timestamp(6) not null,
    gmt_modified       timestamp(6) not null,
    src_user           text,
    src_ip             varchar(20),
    tenant_id          varchar(128),
    encrypted_data_key text         not null
);

comment on table config_info_beta is 'config_info_beta';

comment on column config_info_beta.id is 'id';

comment on column config_info_beta.data_id is 'data_id';

comment on column config_info_beta.group_id is 'group_id';

comment on column config_info_beta.app_name is 'app_name';

comment on column config_info_beta.content is 'content';

comment on column config_info_beta.beta_ips is 'betaIps';

comment on column config_info_beta.md5 is 'md5';

comment on column config_info_beta.gmt_create is '创建时间';

comment on column config_info_beta.gmt_modified is '修改时间';

comment on column config_info_beta.src_user is 'source user';

comment on column config_info_beta.src_ip is 'source ip';

comment on column config_info_beta.tenant_id is '租户字段';

comment on column config_info_beta.encrypted_data_key is '秘钥';

create unique index if not exists uk_configinfobeta_datagrouptenant
    on config_info_beta (data_id, group_id, tenant_id);

create table if not exists config_info_tag
(
    id           bigserial
        primary key,
    data_id      varchar(255) not null,
    group_id     varchar(128) not null,
    tenant_id    varchar(128),
    tag_id       varchar(128) not null,
    app_name     varchar(128),
    content      text         not null,
    md5          varchar(32),
    gmt_create   timestamp(6) not null,
    gmt_modified timestamp(6) not null,
    src_user     text,
    src_ip       varchar(20)
);

comment on table config_info_tag is 'config_info_tag';

comment on column config_info_tag.id is 'id';

comment on column config_info_tag.data_id is 'data_id';

comment on column config_info_tag.group_id is 'group_id';

comment on column config_info_tag.tenant_id is 'tenant_id';

comment on column config_info_tag.tag_id is 'tag_id';

comment on column config_info_tag.app_name is 'app_name';

comment on column config_info_tag.content is 'content';

comment on column config_info_tag.md5 is 'md5';

comment on column config_info_tag.gmt_create is '创建时间';

comment on column config_info_tag.gmt_modified is '修改时间';

comment on column config_info_tag.src_user is 'source user';

comment on column config_info_tag.src_ip is 'source ip';

create unique index if not exists uk_configinfotag_datagrouptenanttag
    on config_info_tag (data_id, group_id, tenant_id, tag_id);

create table if not exists config_tags_relation
(
    id        bigserial,
    tag_name  varchar(128) not null,
    tag_type  varchar(64),
    data_id   varchar(255) not null,
    group_id  varchar(128) not null,
    tenant_id varchar(128),
    nid       bigserial
        primary key
);

comment on table config_tags_relation is 'config_tag_relation';

comment on column config_tags_relation.id is 'id';

comment on column config_tags_relation.tag_name is 'tag_name';

comment on column config_tags_relation.tag_type is 'tag_type';

comment on column config_tags_relation.data_id is 'data_id';

comment on column config_tags_relation.group_id is 'group_id';

comment on column config_tags_relation.tenant_id is 'tenant_id';

create index if not exists idx_tenant_id
    on config_tags_relation (tenant_id);

create unique index if not exists uk_configtagrelation_configidtag
    on config_tags_relation (id, tag_name, tag_type);

create table if not exists group_capacity
(
    id                bigserial
        primary key,
    group_id          varchar(128) not null,
    quota             integer      not null,
    usage             integer      not null,
    max_size          integer      not null,
    max_aggr_count    integer      not null,
    max_aggr_size     integer      not null,
    max_history_count integer      not null,
    gmt_create        timestamp(6) not null,
    gmt_modified      timestamp(6) not null
);

comment on table group_capacity is '集群、各Group容量信息表';

comment on column group_capacity.id is '主键ID';

comment on column group_capacity.group_id is 'Group ID，空字符表示整个集群';

comment on column group_capacity.quota is '配额，0表示使用默认值';

comment on column group_capacity.usage is '使用量';

comment on column group_capacity.max_size is '单个配置大小上限，单位为字节，0表示使用默认值';

comment on column group_capacity.max_aggr_count is '聚合子配置最大个数，，0表示使用默认值';

comment on column group_capacity.max_aggr_size is '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值';

comment on column group_capacity.max_history_count is '最大变更历史数量';

comment on column group_capacity.gmt_create is '创建时间';

comment on column group_capacity.gmt_modified is '修改时间';

create unique index if not exists uk_group_id
    on group_capacity (group_id);

create table if not exists his_config_info
(
    id                 bigint                                                                  not null,
    nid                bigserial
        primary key,
    data_id            varchar(255)                                                            not null,
    group_id           varchar(128)                                                            not null,
    app_name           varchar(128),
    content            text                                                                    not null,
    md5                varchar(32),
    gmt_create         timestamp(6) default '2010-05-05 00:00:00'::timestamp without time zone not null,
    gmt_modified       timestamp(6)                                                            not null,
    src_user           text,
    src_ip             varchar(20),
    op_type            char(10),
    tenant_id          varchar(128),
    encrypted_data_key text                                                                    not null
);

comment on table his_config_info is '多租户改造';

comment on column his_config_info.app_name is 'app_name';

comment on column his_config_info.tenant_id is '租户字段';

comment on column his_config_info.encrypted_data_key is '秘钥';

create index if not exists idx_did
    on his_config_info (data_id);

create index if not exists idx_gmt_create
    on his_config_info (gmt_create);

create index if not exists idx_gmt_modified
    on his_config_info (gmt_modified);

create table if not exists permissions
(
    role     varchar(50)  not null,
    resource varchar(512) not null,
    action   varchar(8)   not null
);

comment on table permissions is '角色表';

create unique index if not exists uk_role_permission
    on permissions (role, resource, action);

create table if not exists roles
(
    username varchar(50) not null,
    role     varchar(50) not null
);

create unique index if not exists uk_username_role
    on roles (username, role);

create table if not exists tenant_capacity
(
    id                bigserial
        primary key,
    tenant_id         varchar(128) not null,
    quota             integer      not null,
    usage             integer      not null,
    max_size          integer      not null,
    max_aggr_count    integer      not null,
    max_aggr_size     integer      not null,
    max_history_count integer      not null,
    gmt_create        timestamp(6) not null,
    gmt_modified      timestamp(6) not null
);

comment on table tenant_capacity is '租户容量信息表';

comment on column tenant_capacity.id is '主键ID';

comment on column tenant_capacity.tenant_id is 'Tenant ID';

comment on column tenant_capacity.quota is '配额，0表示使用默认值';

comment on column tenant_capacity.usage is '使用量';

comment on column tenant_capacity.max_size is '单个配置大小上限，单位为字节，0表示使用默认值';

comment on column tenant_capacity.max_aggr_count is '聚合子配置最大个数';

comment on column tenant_capacity.max_aggr_size is '单个聚合数据的子配置大小上限，单位为字节，0表示使用默认值';

comment on column tenant_capacity.max_history_count is '最大变更历史数量';

comment on column tenant_capacity.gmt_create is '创建时间';

comment on column tenant_capacity.gmt_modified is '修改时间';

create unique index if not exists uk_tenant_id
    on tenant_capacity (tenant_id);

create table if not exists tenant_info
(
    id            bigserial,
    kp            varchar(128) not null,
    tenant_id     varchar(128),
    tenant_name   varchar(128),
    tenant_desc   varchar(256),
    create_source varchar(32),
    gmt_create    bigint       not null,
    gmt_modified  bigint       not null
);

comment on table tenant_info is 'tenant_info';

comment on column tenant_info.id is 'id';

comment on column tenant_info.kp is 'kp';

comment on column tenant_info.tenant_id is 'tenant_id';

comment on column tenant_info.tenant_name is 'tenant_name';

comment on column tenant_info.tenant_desc is 'tenant_desc';

comment on column tenant_info.create_source is 'create_source';

comment on column tenant_info.gmt_create is '创建时间';

comment on column tenant_info.gmt_modified is '修改时间';

create unique index if not exists uk_tenant_info_kptenantid
    on tenant_info (kp, tenant_id);

create table if not exists users
(
    username varchar(50)  not null,
    password varchar(500) not null,
    enabled  boolean      not null
);

comment on table users is '用户表';

insert into config_info (id, data_id, group_id, content, md5, gmt_create, gmt_modified, src_user, src_ip, app_name, tenant_id, c_desc, c_use, effect, type, c_schema, encrypted_data_key)
values  (1, 'jeecg-dev.yaml', 'DEFAULT_GROUP', 'spring:
  datasource:
    druid:
      stat-view-servlet:
        enabled: true
        loginUsername: admin
        loginPassword: 123456
        allow:
      web-stat-filter:
        enabled: true
    dynamic:
      druid:
        initial-size: 5
        min-idle: 5
        maxActive: 100
        maxWait: 60000
        timeBetweenEvictionRunsMillis: 60000
        minEvictableIdleTimeMillis: 300000
        validationQuery: SELECT 1 FROM DUAL
        testWhileIdle: true
        testOnBorrow: false
        testOnReturn: false
        poolPreparedStatements: true
        maxPoolPreparedStatementPerConnectionSize: 20
        filters: stat,wall,slf4j
        connectionProperties: druid.stat.mergeSql\=true;druid.stat.slowSqlMillis\=5000
      datasource:
        master:
          url: jdbc:mysql://jeecg-boot-mysql:33062/logistics_estate_resources?characterEncoding=UTF-8&useUnicode=true&useSSL=false&tinyInt1isBit=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai
          username: root
          password: dsjy@123
          driver-class-name: com.mysql.cj.jdbc.Driver
  redis:
    database: 0
    host: jeecg-boot-redis
    password: dsjy@123
    port: 63791
  rabbitmq:
    host: jeecg-boot-rabbitmq
    username: guest
    password: guest
    port: 5672
    publisher-confirms: true
    publisher-returns: true
    virtual-host: /
    listener:
      simple:
        acknowledge-mode: manual
        concurrency: 1
        max-concurrency: 1
        retry:
          enabled: true
minidao:
  base-package: org.jeecg.modules.jmreport.*,org.jeecg.modules.drag.*
jeecg:
  signatureSecret: dd05f1c54d63749eda95f9fa6d49v442a
  signUrls: /sys/dict/getDictItems/*,/sys/dict/loadDict/*,/sys/dict/loadDictOrderByValue/*,/sys/dict/loadDictItem/*,/sys/dict/loadTreeData,/sys/api/queryTableDictItemsByCode,/sys/api/queryFilterTableDictInfo,/sys/api/queryTableDictByKeys,/sys/api/translateDictFromTable,/sys/api/translateDictFromTableByKeys
  uploadType: local
  domainUrl:
    pc: http://localhost:3100
    app: http://localhost:8051
  path:
    upload: /opt/upFiles
    webapp: /opt/webapp
  shiro:
    excludeUrls: /test/jeecgDemo/demo3,/test/jeecgDemo/redisDemo/**,/category/**,/visual/**,/map/**,/jmreport/bigscreen2/**
  oss:
    endpoint: oss-cn-beijing.aliyuncs.com
    accessKey: ??
    secretKey: ??
    bucketName: jeecgdev
    staticDomain: ??
  elasticsearch:
    cluster-name: jeecg-ES
    cluster-nodes: jeecg-boot-es:9200
    check-enabled: false
  file-view-domain: 127.0.0.1:8012
  minio:
    minio_url: http://minio.jeecg.com
    minio_name: ??
    minio_pass: ??
    bucketName: otatest
  jmreport:
    mode: dev
    is_verify_token: false
    verify_methods: remove,delete,save,add,update
  wps:
    domain: https://wwo.wps.cn/office/
    appid: ??
    appsecret: ??
  xxljob:
    enabled: false
    adminAddresses: http://jeecg-boot-xxljob:9080/xxl-job-admin
    appname: ${spring.application.name}
    accessToken: ''''
    logPath: logs/jeecg/job/jobhandler/
    logRetentionDays: 30
  redisson:
    address: jeecg-boot-redis:63791
    password: dsjy@123
    type: STANDALONE
    enabled: true
  queryRule: LIKE
logging:
  level:
    org.jeecg.modules.system.mapper : debug
cas:
  prefixUrl: http://localhost:8888/cas
knife4j:
  production: false
  basic:
    enable: false
    username: jeecg
    password: jeecg1314
justauth:
  enabled: true
  type:
    GITHUB:
      client-id: ??
      client-secret: ??
      redirect-uri: http://sso.test.com:8080/jeecg-boot/thirdLogin/github/callback
    WECHAT_ENTERPRISE:
      client-id: ??
      client-secret: ??
      redirect-uri: http://sso.test.com:8080/jeecg-boot/thirdLogin/wechat_enterprise/callback
      agent-id: ??
    DINGTALK:
      client-id: ??
      client-secret: ??
      redirect-uri: http://sso.test.com:8080/jeecg-boot/thirdLogin/dingtalk/callback
  cache:
    type: default
    prefix: ''demo::''
    timeout: 1h
third-app:
  enabled: false
  type:
    WECHAT_ENTERPRISE:
      enabled: false
      client-id: ??
      client-secret: ??
      agent-id: ??
    DINGTALK:
      enabled: false
      client-id: ??
      client-secret: ??
      agent-id: ??', 'b061a1c2ca5b7700e232e22d3319d669', '2024-07-31 18:26:01.690000', '2024-07-31 18:26:01.690000', 'nacos', '0:0:0:0:0:0:0:1', '', '', null, null, null, 'yaml', null, ''),
        (2, 'jeecg.yaml', 'DEFAULT_GROUP', 'server:
  tomcat:
    max-swallow-size: -1
  error:
    include-exception: true
    include-stacktrace: ALWAYS
    include-message: ALWAYS
  compression:
    enabled: true
    min-response-size: 1024
    mime-types: application/javascript,application/json,application/xml,text/html,text/xml,text/plain,text/css,image/*
management:
  health:
    mail:
      enabled: false
  endpoints:
    web:
      exposure:
        include: "*"
    health:
      sensitive: true
  endpoint:
    health:
      show-details: ALWAYS
spring:
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB
  mail:
    host: smtp.163.com
    username: jeecgos@163.com
    password: ??
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
  quartz:
    job-store-type: jdbc
    initialize-schema: embedded
    auto-startup: false
    startup-delay: 1s
    overwrite-existing-jobs: true
    properties:
      org:
        quartz:
          scheduler:
            instanceName: MyScheduler
            instanceId: AUTO
          jobStore:
            class: org.springframework.scheduling.quartz.LocalDataSourceJobStore
            driverDelegateClass: org.quartz.impl.jdbcjobstore.StdJDBCDelegate
            tablePrefix: QRTZ_
            isClustered: true
            misfireThreshold: 12000
            clusterCheckinInterval: 15000
          threadPool:
            class: org.quartz.simpl.SimpleThreadPool
            threadCount: 10
            threadPriority: 5
            threadsInheritContextClassLoaderOfInitializingThread: true
  jackson:
    date-format:   yyyy-MM-dd HH:mm:ss
    time-zone:   GMT+8
  aop:
    proxy-target-class: true
  activiti:
    check-process-definitions: false
    async-executor-activate: false
    job-executor-activate: false
  jpa:
    open-in-view: false
  freemarker:
    suffix: .ftl
    content-type: text/html
    charset: UTF-8
    cache: false
    prefer-file-system-access: false
    template-loader-path:
      - classpath:/templates
  mvc:
    static-path-pattern: /**
    pathmatch:
      matching-strategy: ant_path_matcher
  resource:
    static-locations: classpath:/static/,classpath:/public/
  autoconfigure:
    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure
mybatis-plus:
  mapper-locations: classpath*:org/jeecg/modules/**/xml/*Mapper.xml,classpath*:mapper/**/*.xml
  global-config:
    banner: false
    db-config:
      id-type: ASSIGN_ID
      table-underline: true
  configuration:
    call-setters-on-nulls: true', 'b2b82b7d3b4d6eb28182f401dcb3e954', '2024-07-31 18:26:16.560000', '2024-07-31 18:26:16.560000', 'nacos', '0:0:0:0:0:0:0:1', '', '', null, null, null, 'yaml', null, ''),
        (4, 'jeecg-gateway-yoko.yaml', 'DEFAULT_GROUP', 'spring:
  redis:
    database: 0
    host: 172.17.0.1
    port: 63791
    password: 123456
jeecg:
  route:
    config:
      data-type: database
      group: DEFAULT_GROUP
      data-id: jeecg-gateway-router
', '498d307ca235090c6a7e1e4ccdcbff2a', '2024-07-31 18:26:49.092000', '2024-07-31 18:26:49.092000', 'nacos', '0:0:0:0:0:0:0:1', '', '', null, null, null, 'yaml', null, ''),
        (3, 'jeecg-gateway-dev.yaml', 'DEFAULT_GROUP', 'jeecg:
  route:
    config:
      #type:database nacos yml，换成nacos可以直接从JSON里读取配置
      data-type: yml
      group: DEFAULT_GROUP
      # jeecg-gateway-router-dubbo # 可选测试Dubbo的配置JSON
      data-id: jeecg-gateway-router-dubbo
spring:
  redis:
    database: 0
    host: jeecg-boot-redis
    port: 63791
    password: dsjy@123
  cloud:
    gateway:
      routes:
        # 房地微服务路由
        - id: resources-service
          uri: lb://resources-service
          predicates:
            - Path=/system/**,/resources/**,/inspection/**,/graphic/**,/exchange/**,/documents/**,/assetCard/**,/certificate/**,/housePark/**,/houseRoom/**,/quickQuery/**,/certificateHouse/**,/certificateRealestate/**
        - id: test-service
          uri: lb://test-service
          predicates:
            - Path=/test/testOne/**
        - id: jeecg-demo-dubbo
          uri: lb://jeecg-demo-dubbo
          predicates:
            - Path=/mock/**,/test/**,/bigscreen/template1/**,/bigscreen/template2/**
        - id: jeecg-demo-dubbo-websocket
          uri: lb:ws://jeecg-demo-dubbo
          predicates:
            - Path=/vxeSocket/**
        - id: jeecg-system-dubbo
          uri: lb://jeecg-system-dubbo
          predicates:
            - Path=/sys/**,/eoa/plan/**,/email/**,/oa/im/**,/metting/**,/filemanage/**,/officialdoc/**,/sign/**,/oa/im/**,/cms/**,/chat/eoaCmsMenu/**,/filedisk/**,/im/**,/joa/**,/online/**,/bigscreen/**,/jmreport/**,/design/report/**,/desform/**,/process/**,/act/**,/plug-in/**,/generic/**,/workflow/**,/sakuga/**,/technical/**,/ztb/**,/alone/**,/actuator/**,/druid/**
        - id: jeecg-system-dubbo-websocket
          uri: lb:ws://jeecg-system-dubbo
          predicates:
            - Path=/websocket/**,/eoaSocket/**
', 'a7bc32277b0d7aa043afc92a56b27ec3', '2024-07-31 18:26:25.810000', '2024-07-31 18:26:53.801000', 'nacos', '0:0:0:0:0:0:0:1', '', '', '', '', '', 'yaml', '', ''),
        (6, 'jeecg-gateway-router-dubbo.json', 'DEFAULT_GROUP', '[
  {
    "id": "jeecg-system-dubbo",
    "order": 0,
    "predicates": [
      {
        "name": "Path",
        "args": {
          "_genkey_0": "/sys/**",
          "_genkey_1": "/jmreport/**",
          "_genkey_3": "/online/**",
          "_genkey_4": "/generic/**",
          "_genkey_5": "/workflow/**",
          "_genkey_6": "/sakuga/**",
          "_genkey_7": "/technical/**",
          "_genkey_8": "/ztb/**",
          "_genkey_10": "/alone/**"
        }
      }
    ],
    "filters": [],
    "uri": "lb://jeecg-system-dubbo"
  },
  {
    "id": "jeecg-demo-dubbo",
    "order": 1,
    "predicates": [
      {
        "name": "Path",
        "args": {
          "_genkey_0": "/mock/**",
          "_genkey_1": "/test/**",
          "_genkey_2": "/bigscreen/template1/**",
          "_genkey_3": "/bigscreen/template2/**"
        }
      }
    ],
    "filters": [],
    "uri": "lb://jeecg-demo-dubbo"
  },
  {
    "id": "jeecg-system-dubbo-websocket",
    "order": 2,
    "predicates": [
      {
        "name": "Path",
        "args": {
          "_genkey_0": "/websocket/**",
          "_genkey_1": "/newsWebsocket/**"
        }
      }
    ],
    "filters": [],
    "uri": "lb:ws://jeecg-system-dubbo"
  },
  {
    "id": "jeecg-demo-dubbo-websocket",
    "order": 3,
    "predicates": [
      {
        "name": "Path",
        "args": {
          "_genkey_0": "/vxeSocket/**"
        }
      }
    ],
    "filters": [],
    "uri": "lb:ws://jeecg-demo-dubbo"
  },
  {
    "id": "jeecg-cloud-dubbo-test",
    "order": 4,
    "predicates": [
      {
        "name": "Path",
        "args": {
          "_genkey_1": "/test/**"
        }
      }
    ],
    "filters": [],
    "uri": "lb://jeecg-cloud-dubbo-test"
  }
]
', '1a264a70d68a90dfbb1cf1ed864fbd94', '2024-07-31 18:27:26.413000', '2024-07-31 18:27:26.413000', 'nacos', '0:0:0:0:0:0:0:1', '', '', null, null, null, 'json', null, '');;;;;;insert into his_config_info (id, nid, data_id, group_id, app_name, content, md5, gmt_create, gmt_modified, src_user, src_ip, op_type, tenant_id, encrypted_data_key)
values  (0, 1, 'jeecg-dev.yaml', 'DEFAULT_GROUP', '', 'spring:
  datasource:
    druid:
      stat-view-servlet:
        enabled: true
        loginUsername: admin
        loginPassword: 123456
        allow:
      web-stat-filter:
        enabled: true
    dynamic:
      druid:
        initial-size: 5
        min-idle: 5
        maxActive: 100
        maxWait: 60000
        timeBetweenEvictionRunsMillis: 60000
        minEvictableIdleTimeMillis: 300000
        validationQuery: SELECT 1 FROM DUAL
        testWhileIdle: true
        testOnBorrow: false
        testOnReturn: false
        poolPreparedStatements: true
        maxPoolPreparedStatementPerConnectionSize: 20
        filters: stat,wall,slf4j
        connectionProperties: druid.stat.mergeSql\=true;druid.stat.slowSqlMillis\=5000
      datasource:
        master:
          url: jdbc:mysql://jeecg-boot-mysql:33062/logistics_estate_resources?characterEncoding=UTF-8&useUnicode=true&useSSL=false&tinyInt1isBit=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai
          username: root
          password: dsjy@123
          driver-class-name: com.mysql.cj.jdbc.Driver
  redis:
    database: 0
    host: jeecg-boot-redis
    password: dsjy@123
    port: 63791
  rabbitmq:
    host: jeecg-boot-rabbitmq
    username: guest
    password: guest
    port: 5672
    publisher-confirms: true
    publisher-returns: true
    virtual-host: /
    listener:
      simple:
        acknowledge-mode: manual
        concurrency: 1
        max-concurrency: 1
        retry:
          enabled: true
minidao:
  base-package: org.jeecg.modules.jmreport.*,org.jeecg.modules.drag.*
jeecg:
  signatureSecret: dd05f1c54d63749eda95f9fa6d49v442a
  signUrls: /sys/dict/getDictItems/*,/sys/dict/loadDict/*,/sys/dict/loadDictOrderByValue/*,/sys/dict/loadDictItem/*,/sys/dict/loadTreeData,/sys/api/queryTableDictItemsByCode,/sys/api/queryFilterTableDictInfo,/sys/api/queryTableDictByKeys,/sys/api/translateDictFromTable,/sys/api/translateDictFromTableByKeys
  uploadType: local
  domainUrl:
    pc: http://localhost:3100
    app: http://localhost:8051
  path:
    upload: /opt/upFiles
    webapp: /opt/webapp
  shiro:
    excludeUrls: /test/jeecgDemo/demo3,/test/jeecgDemo/redisDemo/**,/category/**,/visual/**,/map/**,/jmreport/bigscreen2/**
  oss:
    endpoint: oss-cn-beijing.aliyuncs.com
    accessKey: ??
    secretKey: ??
    bucketName: jeecgdev
    staticDomain: ??
  elasticsearch:
    cluster-name: jeecg-ES
    cluster-nodes: jeecg-boot-es:9200
    check-enabled: false
  file-view-domain: 127.0.0.1:8012
  minio:
    minio_url: http://minio.jeecg.com
    minio_name: ??
    minio_pass: ??
    bucketName: otatest
  jmreport:
    mode: dev
    is_verify_token: false
    verify_methods: remove,delete,save,add,update
  wps:
    domain: https://wwo.wps.cn/office/
    appid: ??
    appsecret: ??
  xxljob:
    enabled: false
    adminAddresses: http://jeecg-boot-xxljob:9080/xxl-job-admin
    appname: ${spring.application.name}
    accessToken: ''''
    logPath: logs/jeecg/job/jobhandler/
    logRetentionDays: 30
  redisson:
    address: jeecg-boot-redis:63791
    password: dsjy@123
    type: STANDALONE
    enabled: true
  queryRule: LIKE
logging:
  level:
    org.jeecg.modules.system.mapper : debug
cas:
  prefixUrl: http://localhost:8888/cas
knife4j:
  production: false
  basic:
    enable: false
    username: jeecg
    password: jeecg1314
justauth:
  enabled: true
  type:
    GITHUB:
      client-id: ??
      client-secret: ??
      redirect-uri: http://sso.test.com:8080/jeecg-boot/thirdLogin/github/callback
    WECHAT_ENTERPRISE:
      client-id: ??
      client-secret: ??
      redirect-uri: http://sso.test.com:8080/jeecg-boot/thirdLogin/wechat_enterprise/callback
      agent-id: ??
    DINGTALK:
      client-id: ??
      client-secret: ??
      redirect-uri: http://sso.test.com:8080/jeecg-boot/thirdLogin/dingtalk/callback
  cache:
    type: default
    prefix: ''demo::''
    timeout: 1h
third-app:
  enabled: false
  type:
    WECHAT_ENTERPRISE:
      enabled: false
      client-id: ??
      client-secret: ??
      agent-id: ??
    DINGTALK:
      enabled: false
      client-id: ??
      client-secret: ??
      agent-id: ??', 'b061a1c2ca5b7700e232e22d3319d669', '2010-05-05 00:00:00.000000', '2024-07-31 18:26:01.690000', 'nacos', '0:0:0:0:0:0:0:1', 'I         ', '', ''),
        (0, 2, 'jeecg.yaml', 'DEFAULT_GROUP', '', 'server:
  tomcat:
    max-swallow-size: -1
  error:
    include-exception: true
    include-stacktrace: ALWAYS
    include-message: ALWAYS
  compression:
    enabled: true
    min-response-size: 1024
    mime-types: application/javascript,application/json,application/xml,text/html,text/xml,text/plain,text/css,image/*
management:
  health:
    mail:
      enabled: false
  endpoints:
    web:
      exposure:
        include: "*"
    health:
      sensitive: true
  endpoint:
    health:
      show-details: ALWAYS
spring:
  servlet:
    multipart:
      max-file-size: 10MB
      max-request-size: 10MB
  mail:
    host: smtp.163.com
    username: jeecgos@163.com
    password: ??
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true
            required: true
  quartz:
    job-store-type: jdbc
    initialize-schema: embedded
    auto-startup: false
    startup-delay: 1s
    overwrite-existing-jobs: true
    properties:
      org:
        quartz:
          scheduler:
            instanceName: MyScheduler
            instanceId: AUTO
          jobStore:
            class: org.springframework.scheduling.quartz.LocalDataSourceJobStore
            driverDelegateClass: org.quartz.impl.jdbcjobstore.StdJDBCDelegate
            tablePrefix: QRTZ_
            isClustered: true
            misfireThreshold: 12000
            clusterCheckinInterval: 15000
          threadPool:
            class: org.quartz.simpl.SimpleThreadPool
            threadCount: 10
            threadPriority: 5
            threadsInheritContextClassLoaderOfInitializingThread: true
  jackson:
    date-format:   yyyy-MM-dd HH:mm:ss
    time-zone:   GMT+8
  aop:
    proxy-target-class: true
  activiti:
    check-process-definitions: false
    async-executor-activate: false
    job-executor-activate: false
  jpa:
    open-in-view: false
  freemarker:
    suffix: .ftl
    content-type: text/html
    charset: UTF-8
    cache: false
    prefer-file-system-access: false
    template-loader-path:
      - classpath:/templates
  mvc:
    static-path-pattern: /**
    pathmatch:
      matching-strategy: ant_path_matcher
  resource:
    static-locations: classpath:/static/,classpath:/public/
  autoconfigure:
    exclude: com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure
mybatis-plus:
  mapper-locations: classpath*:org/jeecg/modules/**/xml/*Mapper.xml,classpath*:mapper/**/*.xml
  global-config:
    banner: false
    db-config:
      id-type: ASSIGN_ID
      table-underline: true
  configuration:
    call-setters-on-nulls: true', 'b2b82b7d3b4d6eb28182f401dcb3e954', '2010-05-05 00:00:00.000000', '2024-07-31 18:26:16.560000', 'nacos', '0:0:0:0:0:0:0:1', 'I         ', '', ''),
        (0, 3, 'jeecg-gateway-dev.yaml', 'DEFAULT_GROUP', '', 'jeecg:
  route:
    config:
      #type:database nacos yml，换成nacos可以直接从JSON里读取配置
      data-type: yml
      group: DEFAULT_GROUP
      # jeecg-gateway-router-dubbo # 可选测试Dubbo的配置JSON
      data-id: jeecg-gateway-router-dubbo
spring:
  redis:
    database: 0
    host: jeecg-boot-redis
    port: 63791
    password: dsjy@123
  cloud:
    gateway:
      routes:
        # 房地微服务路由
        - id: resources-service
          uri: lb://resources-service
          predicates:
            - Path=/system/**,/resources/**,/inspection/**,/graphic/**,/exchange/**,/documents/**,/assetCard/**,/certificate/**,/housePark/**,/houseRoom/**,/quickQuery/**,/certificateHouse/**,/certificateRealestate/**
        - id: test-service
          uri: lb://test-service
          predicates:
            - Path=/test/testOne/**
        - id: jeecg-demo-dubbo
          uri: lb://jeecg-demo-dubbo
          predicates:
            - Path=/mock/**,/test/**,/bigscreen/template1/**,/bigscreen/template2/**
        - id: jeecg-demo-dubbo-websocket
          uri: lb:ws://jeecg-demo-dubbo
          predicates:
            - Path=/vxeSocket/**
        - id: jeecg-system-dubbo
          uri: lb://jeecg-system-dubbo
          predicates:
            - Path=/sys/**,/eoa/plan/**,/email/**,/oa/im/**,/metting/**,/filemanage/**,/officialdoc/**,/sign/**,/oa/im/**,/cms/**,/chat/eoaCmsMenu/**,/filedisk/**,/im/**,/joa/**,/online/**,/bigscreen/**,/jmreport/**,/design/report/**,/desform/**,/process/**,/act/**,/plug-in/**,/generic/**,/workflow/**,/sakuga/**,/technical/**,/ztb/**,/alone/**,/actuator/**,/druid/**
        - id: jeecg-system-dubbo-websocket
          uri: lb:ws://jeecg-system-dubbo
          predicates:
            - Path=/websocket/**,/eoaSocket/**
', '3aca84c1b6fd18850357d56434490d75', '2010-05-05 00:00:00.000000', '2024-07-31 18:26:25.810000', 'nacos', '0:0:0:0:0:0:0:1', 'I         ', '', ''),
        (0, 4, 'jeecg-gateway-yoko.yaml', 'DEFAULT_GROUP', '', 'spring:
  redis:
    database: 0
    host: 172.17.0.1
    port: 63791
    password: 123456
jeecg:
  route:
    config:
      data-type: database
      group: DEFAULT_GROUP
      data-id: jeecg-gateway-router
', '498d307ca235090c6a7e1e4ccdcbff2a', '2010-05-05 00:00:00.000000', '2024-07-31 18:26:49.092000', 'nacos', '0:0:0:0:0:0:0:1', 'I         ', '', ''),
        (3, 5, 'jeecg-gateway-dev.yaml', 'DEFAULT_GROUP', '', 'jeecg:
  route:
    config:
      #type:database nacos yml，换成nacos可以直接从JSON里读取配置
      data-type: yml
      group: DEFAULT_GROUP
      # jeecg-gateway-router-dubbo # 可选测试Dubbo的配置JSON
      data-id: jeecg-gateway-router-dubbo
spring:
  redis:
    database: 0
    host: jeecg-boot-redis
    port: 63791
    password: dsjy@123
  cloud:
    gateway:
      routes:
        # 房地微服务路由
        - id: resources-service
          uri: lb://resources-service
          predicates:
            - Path=/system/**,/resources/**,/inspection/**,/graphic/**,/exchange/**,/documents/**,/assetCard/**,/certificate/**,/housePark/**,/houseRoom/**,/quickQuery/**,/certificateHouse/**,/certificateRealestate/**
        - id: test-service
          uri: lb://test-service
          predicates:
            - Path=/test/testOne/**
        - id: jeecg-demo-dubbo
          uri: lb://jeecg-demo-dubbo
          predicates:
            - Path=/mock/**,/test/**,/bigscreen/template1/**,/bigscreen/template2/**
        - id: jeecg-demo-dubbo-websocket
          uri: lb:ws://jeecg-demo-dubbo
          predicates:
            - Path=/vxeSocket/**
        - id: jeecg-system-dubbo
          uri: lb://jeecg-system-dubbo
          predicates:
            - Path=/sys/**,/eoa/plan/**,/email/**,/oa/im/**,/metting/**,/filemanage/**,/officialdoc/**,/sign/**,/oa/im/**,/cms/**,/chat/eoaCmsMenu/**,/filedisk/**,/im/**,/joa/**,/online/**,/bigscreen/**,/jmreport/**,/design/report/**,/desform/**,/process/**,/act/**,/plug-in/**,/generic/**,/workflow/**,/sakuga/**,/technical/**,/ztb/**,/alone/**,/actuator/**,/druid/**
        - id: jeecg-system-dubbo-websocket
          uri: lb:ws://jeecg-system-dubbo
          predicates:
            - Path=/websocket/**,/eoaSocket/**
', '3aca84c1b6fd18850357d56434490d75', '2010-05-05 00:00:00.000000', '2024-07-31 18:26:53.801000', 'nacos', '0:0:0:0:0:0:0:1', 'U         ', '', ''),
        (0, 6, 'jeecg-gateway-router-dubbo.json', 'DEFAULT_GROUP', '', '[
  {
    "id": "jeecg-system-dubbo",
    "order": 0,
    "predicates": [
      {
        "name": "Path",
        "args": {
          "_genkey_0": "/sys/**",
          "_genkey_1": "/jmreport/**",
          "_genkey_3": "/online/**",
          "_genkey_4": "/generic/**",
          "_genkey_5": "/workflow/**",
          "_genkey_6": "/sakuga/**",
          "_genkey_7": "/technical/**",
          "_genkey_8": "/ztb/**",
          "_genkey_10": "/alone/**"
        }
      }
    ],
    "filters": [],
    "uri": "lb://jeecg-system-dubbo"
  },
  {
    "id": "jeecg-demo-dubbo",
    "order": 1,
    "predicates": [
      {
        "name": "Path",
        "args": {
          "_genkey_0": "/mock/**",
          "_genkey_1": "/test/**",
          "_genkey_2": "/bigscreen/template1/**",
          "_genkey_3": "/bigscreen/template2/**"
        }
      }
    ],
    "filters": [],
    "uri": "lb://jeecg-demo-dubbo"
  },
  {
    "id": "jeecg-system-dubbo-websocket",
    "order": 2,
    "predicates": [
      {
        "name": "Path",
        "args": {
          "_genkey_0": "/websocket/**",
          "_genkey_1": "/newsWebsocket/**"
        }
      }
    ],
    "filters": [],
    "uri": "lb:ws://jeecg-system-dubbo"
  },
  {
    "id": "jeecg-demo-dubbo-websocket",
    "order": 3,
    "predicates": [
      {
        "name": "Path",
        "args": {
          "_genkey_0": "/vxeSocket/**"
        }
      }
    ],
    "filters": [],
    "uri": "lb:ws://jeecg-demo-dubbo"
  },
  {
    "id": "jeecg-cloud-dubbo-test",
    "order": 4,
    "predicates": [
      {
        "name": "Path",
        "args": {
          "_genkey_1": "/test/**"
        }
      }
    ],
    "filters": [],
    "uri": "lb://jeecg-cloud-dubbo-test"
  }
]
', '1a264a70d68a90dfbb1cf1ed864fbd94', '2010-05-05 00:00:00.000000', '2024-07-31 18:27:26.413000', 'nacos', '0:0:0:0:0:0:0:1', 'I         ', '', '');;insert into roles (username, role)
values  ('nacos', 'ROLE_ADMIN');;insert into tenant_info (id, kp, tenant_id, tenant_name, tenant_desc, create_source, gmt_create, gmt_modified)
values  (1, '1', 'ac14ab82-51f8-4f0c-aa5b-25fb8384bfb6', 'jeecg', 'jeecg 测试命名空间', 'nacos', 1722421483319, 1722421483319);insert into users (username, password, enabled)
values  ('nacos', '$2a$10$EuWPZHzz32dJN7jexM34MOeYirDdFAZm2kuWj7VEOJhhZkDrxfvUu', true);
