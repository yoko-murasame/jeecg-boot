[返回](../)

# Nginx配置示例

[组件描述]

组件路径: [Nginx配置示例](https://github.com/yoko-murasame/jeecg-boot/blob/yoko-3.4.3last/docs/DevOps/nginx)

Nginx示例:
```nginx
# 1.前端必须改代码，这个无法绕过，所有的PUT/DELETE请求，都要求改成POST；
# 所有的PUT请求，要携带Header：X-HTTP-Method-Override: PUT
# 所有的DELETE请求，要携带Header：X-HTTP-Method-Override: DELETE
# 注意修改的Header中是小横杠-，而Nginx中配置读取到会转换成下划线_
server {
  listen 2333;
  server_name localhost;
  #index index.php index.html index.htm default.php default.htm default.html;
  gzip on;
  gzip_static on;     # 需要http_gzip_static_module 模块
  gzip_min_length 1k;
  gzip_comp_level 4;
  gzip_proxied any;
  gzip_types text/plain text/xml text/css;
  gzip_vary on;
  gzip_disable "MSIE [1-6]\.(?!.*SV1)";

  add_header Access-Control-Allow-Origin *;
  add_header Access-Control-Allow-Headers X-Requested-With;
  add_header Access-Control-Allow-Methods GET,PUT,POST,DELETE,OPTIONS;

  # 在nginx的server中添加
  set $method $request_method;
  if ($http_X_HTTP_Method_Override ~* 'DELETE') {
    set $method DELETE;
  }

  if ($http_X_HTTP_Method_Override ~* 'PUT') {
    set $method PUT;
  }
  proxy_method $method;

  # 避免端点安全问题
  if ($request_uri ~* "/actuator") {
    return 403;
  }

  # 放行options
  if ($method = 'OPTIONS') {
    return 204;
  }

  # 测试代理到本地前端服务
  location / {
    proxy_pass http://localhost:3000;
  }
  location ^~ /jeecg-boot/ {
    proxy_pass http://localhost:8081/jeecg-boot/;
    proxy_set_header Host $host;
    proxy_set_header X-Real-IP $remote_addr;
    proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    # socket
    proxy_http_version 1.1;
    proxy_set_header Upgrade $http_upgrade;
    proxy_set_header Connection "upgrade";
    proxy_connect_timeout 60s;
    proxy_read_timeout 7200s;
    proxy_send_timeout 60s;
  }

}


```

修改历史:
* 2023-07-17: 新增
