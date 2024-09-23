#!/bin/bash

# 请注意脚本中行分隔符的印象，Linux是LF，Windows是CRLF！拿VS Code或者IDEA等工具可以直接修改！

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
