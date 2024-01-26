#!/bin/bash

swap_location="/swap"
swap_name="swapfile"
swap_bs="1M"
swap_size="16384"

# Initialize swapfile
mkdir -p $swap_location
swapfile="$swap_location/$swap_name"

# 检查 swap 是否已经存在
if [ ! -f $swapfile ]; then
    # 创建 $swapfile
    dd if=/dev/zero of=$swapfile bs=$swap_bs count=$swap_size
    mkswap -f $swapfile
    chmod 600 $swapfile
    # mkswap $swapfile
fi

# 启用 swapfile
swapon $swapfile

# 检查是否成功启用
if [ "$(swapon -s | grep $swapfile)" == "" ]; then
    echo "$swapfile 启用失败"
else
    echo "$swapfile 启用成功"
fi

# 将 $swapfile 加入到开机启动项中
echo "将 $swapfile 加入到开机启动项中"
echo "$swapfile none swap sw 0 0" >> /etc/fstab
echo "cat /proc/swaps"
cat /proc/swaps
echo "free -h"
free -h
echo "交换区使用权重"
cat /proc/sys/vm/swappiness
# 修改权重
# sudo sysctl vm.swappiness=10
# vi /etc/sysctl.conf
# vm.swappiness = 10
