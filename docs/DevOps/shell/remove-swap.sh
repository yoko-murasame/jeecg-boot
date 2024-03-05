#!/bin/bash

# chmod +x remove-swap.sh
# sudo ./remove-swap.sh
# 请在这里输入你的swap文件路径
SWAPFILE="/path/to/your/swapfile"

# 关闭swap文件
sudo swapoff $SWAPFILE

# 删除/etc/fstab中的相关条目
sudo sed -i.bak "/$(basename $SWAPFILE)/d" /etc/fstab

# 删除swap文件
sudo rm -f $SWAPFILE

echo "Swap file has been removed successfully."
