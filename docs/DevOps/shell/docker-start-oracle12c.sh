#!/bin/bash

# 请注意脚本中行分隔符的印象，Linux是LF，Windows是CRLF！拿VS Code或者IDEA等工具可以直接修改！

# 配置容器和镜像名称
container_names=("oracle-12c")
image_names=("oracle-12c")

# 创建容器命令数组
mkdir -p /home/oracledata
create_container_cmds=(
"docker run -d -p 38888:8080 -p 15211:1521 -v /home/oracledata:/u01/app/oracle --name ${container_names[0]} ${image_names[0]}"
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

# 检查容器状态
container_status() {
    local container_name="$1"
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
