[返回](../)

# CommonController

系统统一上传接口.

组件路径:
* [org.jeecg.modules.system.controller.CommonController](https://github.com/yoko-murasame/jeecg-boot/blob/yoko-3.4.3last/jeecg-module-system/jeecg-system-biz/src/main/java/org/jeecg/modules/system/controller/CommonController.java)
* [3.4.3文件模块扩展.sql](https://github.com/yoko-murasame/jeecg-boot/blob/yoko-3.4.3last/db/增量SQL/3.4.3文件模块扩展.sql)

**阿里云OSS转换到本地接口**

* 支持任意表、任意字段扫描
* 支持分页数据处理
* 支持流量限制（超出指定流量就不会继续上传）
* 支持Postgres JSON数据类型
* 定时任务类：[org.jeecg.modules.system.job.TransferOssToLocalJob](https://github.com/yoko-murasame/jeecg-boot/blob/yoko-3.4.3last/org/jeecg/modules/system/job/TransferOssToLocalJob.java)

请求示例：
```http request
POST /sys/common/transferOssToLocal HTTP/1.1
Host: 127.0.0.1
Content-Type: application/json
X-Access-Token: xxx

[
  {
    "tableName": "deal_service_gsba",
    "idField": "id",
    "fields": [
      "lxpfwj_file",
      "kcpfwj_file"
    ],
    "fieldTypes": [
      "varchar",
      "varchar"
    ],
    "page": 1,
    "pageSize": 2,
    "limitMb": 1,
    "bizPath": "gsba",
    "deleteSource": false,
    "endpoint": null,
    "accessKeyId": null,
    "accessKeySecret": null,
    "bucketName": null
  },
  {
    "tableName": "technical_file",
    "idField": "id",
    "fields": [
      "oss_file"
    ],
    "fieldTypes": [
      "json"
    ],
    "jsonPaths": [
      "url"
    ],
    "page": 1,
    "pageSize": 5,
    "limitMb": 1,
    "bizPath": "technical",
    "deleteSource": false,
    "endpoint": null,
    "accessKeyId": null,
    "accessKeySecret": null,
    "bucketName": null
  }
]
```

修改历史:
* 2023-09-11: 重构默认上传接口，添加服务层统一逻辑，添加MD5值校验，防止文件重复上传。
* 2023-10-12: 添加阿里云oss转换到本地文件接口。添加相关定时任务：
