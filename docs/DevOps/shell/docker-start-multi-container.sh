#!/bin/bash

# 配置容器和镜像名称
container_names=(
  "redis"
  "postgre-14"
  "nginx"
  # "app-separate" # 镜像已传到DockerHUB，非超图版本也可以走单独的脚本部署形式
  # "app-supermap" # 镜像已传到DockerHUB，超图iObjects Java Docker镜像打包请参考 https://blog.csdn.net/SerikaOnoe/article/details/130337989
)

# 下面镜像如果有公网将会自动拉取
image_names=(
  "redis:latest"
  "kuluseky/postgres-zhparser-postgis:13" # 可选标签 12 13 14
  "nginx:latest"
  # "kuluseky/app-separate:jdk8x86" # 可选标签 jdk8x86 jdk11x86
  # "kuluseky/app-supermap:1110jdk8x86" # 可选标签 1110jdk8x86(iObjects 11i JDK8 X86平台)
)

# 创建容器命令数组，请注意nginx/log目录需要提升权限(chmod 777 /home/nginx/log)
create_container_cmds=(
  "docker run -di --restart=unless-stopped -p 63791:63791 -v /home/redis/data:/data --name ${container_names[0]} ${image_names[0]} --requirepass "123456" --port "63791" --appendonly "yes""
  "docker run -di --restart=unless-stopped -e POSTGRES_PASSWORD=123456 -e PGDATA=/var/lib/postgresql/data/pgdata -p 54321:5432 -v /home/postgres/pgdata14:/var/lib/postgresql/data/pgdata --name ${container_names[1]} ${image_names[1]}"
  "docker run -di --restart=unless-stopped --network=host --privileged -v /home/nginx/nginx.conf:/etc/nginx/nginx.conf -v /home/nginx/log:/var/log/nginx -v /home/nginx/conf.d:/etc/nginx/conf.d -v /home/nginx/html:/etc/nginx/html --name ${container_names[2]} ${image_names[2]}"
  # "docker run -di --restart=unless-stopped -p 8888:8888 -v /home/project/app:/app -v /home/project/upFiles:/opt/upFiles --name ${container_names[3]} ${image_names[3]}"
  # "docker run -di --restart=unless-stopped -p 8889:8888 -v /home/project/app:/app -v /home/project/upFiles:/opt/upFiles -v /home/project/supermap/Bin:/supermap/Bin -v /home/project/supermap/License:/opt/SuperMap/License --name ${container_names[4]} ${image_names[4]}"
)

# 启动容器函数
start_container() {
    local container_name="$1"
    echo "启动 $container_name 容器..."
    docker start "$container_name"
    echo "$container_name 容器已启动。"
}

# 停止容器函数
stop_container() {
    local container_name="$1"
    echo "停止 $container_name 容器..."
    docker stop "$container_name"
    echo "$container_name 容器已停止。"
}

# 重启容器函数
restart_container() {
    local container_name="$1"
    echo "重启 $container_name 容器..."
    docker restart "$container_name"
    echo "$container_name 容器已重启。"
}

# 检查容器状态 -f '\{\{.State.Status\}\}'
container_status() {
    local container_name="$1"
    # docker container inspect --format='{{.State.Status}}' $container_name
    docker inspect -f '{{.State.Status}}' "$container_name" 2>/dev/null
}

# 根据用户输入的参数调用相应的功能
if [ "$1" == "start" ]; then
    # 启动容器
    for ((i=0; i<${#container_names[@]}; i++)); do
        container_name="${container_names[i]}"
        image_name="${image_names[i]}"
        create_container_cmd="${create_container_cmds[i]}"

        if [ -z "$(container_status "$container_name")" ]; then
            echo "创建 $container_name 容器..."
            eval "$create_container_cmd"
            echo "$container_name 容器已创建并启动。"
        elif [ "$(container_status "$container_name")" == "exited" ]; then
            start_container "$container_name"
        elif [ "$(container_status "$container_name")" == "running" ]; then
            echo "$container_name 容器已经在运行中。"
        fi
    done
elif [ "$1" == "stop" ]; then
    # 停止容器
    for container_name in "${container_names[@]}"; do
        if [ -n "$(container_status "$container_name")" ] && [ "$(container_status "$container_name")" == "running" ]; then
            stop_container "$container_name"
        else
            echo "$container_name 容器未运行。"
        fi
    done
elif [ "$1" == "restart" ]; then
    # 重启容器
    for container_name in "${container_names[@]}"; do
        if [ -n "$(container_status "$container_name")" ] && [ "$(container_status "$container_name")" == "running" ]; then
            restart_container "$container_name"
        else
            echo "$container_name 容器未运行，无法重启。"
        fi
    done
else
    echo "无效的参数。请使用 'start'、'stop' 或 'restart'。"
fi
