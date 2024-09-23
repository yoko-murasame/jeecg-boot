#!/bin/bash

# 请注意脚本中行分隔符的印象，Linux是LF，Windows是CRLF！拿VS Code或者IDEA等工具可以直接修改！

#安装包路径 https://download.docker.com/linux/static/stable/x86_64/
package_path="/home/package"
#应用安装路径（默认docker的存储路径为/data/docker0）
install_path="/home/docker"
#docker安装包名
docker_install_name=$(find . -name "*docker*.tgz" | grep -oE '[^/]+\.tgz$')

# 检查是否有root权限
if [ "$EUID" -ne 0 ]
  then echo "请使用root权限运行此脚本"
  exit
fi

cd $package_path

# 安装docker
if command -v docker &> /dev/null; then
    echo "----------Docker 已经安装----------"
else
    DOCKER_FILE_PATH="$install_path"
    echo "----------开始安装docker----------"
    cd $package_path
    tar -xvf $docker_install_name -C $package_path
    cp docker/* /usr/bin/
    rm -rf $package_path/docker
    # [Unit]
    echo "[Unit]" >> /etc/systemd/system/docker.service
    echo "Description=Docker Application Container Engine" >> /etc/systemd/system/docker.service
    echo "Documentation=https://docs.docker.com" >> /etc/systemd/system/docker.service
    echo "After=network-online.target firewalld.service" >> /etc/systemd/system/docker.service
    echo "Wants=network-online.target" >> /etc/systemd/system/docker.service
    echo "" >> /etc/systemd/system/docker.service
    # [Service]
    echo "[Service]" >> /etc/systemd/system/docker.service
    echo "Type=notify" >> /etc/systemd/system/docker.service
    # https://stackoverflow.com/questions/68776387/docker-library-initialization-failed-unable-to-allocate-file-descriptor-tabl
    # --graph $DOCKER_FILE_PATH 用于指定 Docker 的数据存储路径
    # -graph 参数已经在 Docker 1.13.0 版本被弃用，现在推荐使用 --data-root 参数来替代。
    # --data-root 参数的作用是指定 Docker 的工作目录，所有的 Docker 数据（包括镜像和容器）都会存储在这个目录下
    # 如果需要暴露TLS连接请加参数：-H unix:///var/run/docker.sock -H tcp://0.0.0.0:2375
    echo "ExecStart=/usr/bin/dockerd --default-ulimit nofile=65536:65536 -H unix:///var/run/docker.sock -H tcp://0.0.0.0:2375 --data-root $DOCKER_FILE_PATH --config-file $DOCKER_FILE_PATH/daemon.json" >> /etc/systemd/system/docker.service
    echo "ExecReload=/bin/kill -s HUP \$MAINPID" >> /etc/systemd/system/docker.service
    echo "LimitNOFILE=infinity" >> /etc/systemd/system/docker.service
    echo "LimitNPROC=infinity" >> /etc/systemd/system/docker.service
    echo "LimitCORE=infinity" >> /etc/systemd/system/docker.service
    echo "TimeoutStartSec=0" >> /etc/systemd/system/docker.service
    echo "Delegate=yes" >> /etc/systemd/system/docker.service
    echo "KillMode=process" >> /etc/systemd/system/docker.service
    echo "Restart=on-failure" >> /etc/systemd/system/docker.service
    echo "StartLimitBurst=3" >> /etc/systemd/system/docker.service
    echo "StartLimitInterval=60s" >> /etc/systemd/system/docker.service
    # [Install]
    echo "[Install]" >> /etc/systemd/system/docker.service
    echo "WantedBy=multi-user.target" >> /etc/systemd/system/docker.service
    # Reload systemd daemon and restart Docker
    echo "----------注册docker服务----------"
    chmod +x /etc/systemd/system/docker.service
    systemctl daemon-reload
    echo "----------启动docker服务----------"
    systemctl start docker.service
    echo "----------设置服务开机自启----------"
    systemctl enable docker.service
    echo "----------docker安装完毕----------"
fi

# 限制日志文件大小、配置镜像，默认路径是 /etc/docker/daemon.json，可使用 dockerd --config-file=xxx 指定
cat > $DOCKER_FILE_PATH/daemon.json <<EOF
{
    "log-opts": {
        "max-size": "20m",
        "max-file": "3"
    },
    "registry-mirrors": ["http://hub-mirror.c.163.com", "https://registry.docker-cn.com", "https://docker.mirrors.ustc.edu.cn"],
    "dns": ["8.8.8.8", "114.114.114.114"]
}
EOF

## 更多配置参考：增加一段自定义内网 IPv6 地址、开启容器的 IPv6 功能、限制日志文件大小
#{
#    "log-driver": "json-file",
#    "log-opts": {
#        "max-size": "20m",
#        "max-file": "3"
#    },
#    "ipv6": true,
#    "fixed-cidr-v6": "fd00:dead:beef:c0::/80",
#    "experimental":true,
#    "ip6tables":true
#}
