[返回](../)

# Linux DevOps

## CentOS 7 密码策略

* https://www.cnblogs.com/xiongzaiqiren/p/17027924.html
* https://docs.azure.cn/zh-cn/articles/azure-operations-guide/virtual-machines/linux/aog-virtual-machines-linux-howto-site-linux-password-security-policy-settings
* https://blog.csdn.net/Ahuuua/article/details/125333088

```shell
# 这些设置要求用户每6个月改变他们的密码，并且会提前7天提醒用户密码快到期了。
sudo vi /etc/login.defs
PASSMAXDAYS 150 
PASSMINDAYS 0 
PASSWARNAGE 7

# 在 /etc/security/pwquality.conf 中添加以下:
vim /etc/security/pwquality.conf

# 密码至少包含 8 个字符。
minlen = 8
dcredit = 0
ucredit = 0
lcredit = 0
ocredit = 0

# 密码需要由 3 个类别（数字，小写字母，大写字母，其他）的字符组成。
minclass  = 3

# 记录过去使用过的 10 个密码（不可重复使用）
# 在 /etc/pam.d/system-auth 和 /etc/pam.d/password-auth 中添加以下（在 pam_pwquality.so 这一行之后）：
password    requisite     pam_pwhistory.so remember=10 use_authtok
```

**说明：**
Password aging controls:

PASS_MAX_DAYS Maximum number of days a password may be used.
（密码可以使用的最大天数。）
PASS_MIN_DAYS Minimum number of days allowed between password changes.
（密码更改之间允许的最小天数。一般不要设置）
PASS_MIN_LEN Minimum acceptable password length.
（可接受的最小密码长度。）
PASS_WARN_AGE Number of days warning given before a password expires.
（密码过期前发出警告的天数。）

```shell
# 默认值
PASS_MAX_DAYS   99999
PASS_MIN_DAYS   0
PASS_MIN_LEN    5
PASS_WARN_AGE   7

# 建议值
PASS_MAX_DAYS   90
PASS_MIN_DAYS   0
PASS_MIN_LEN    6
PASS_WARN_AGE   7
```

可以通过sed命令设置：

`sed -r -i 's/(PASS_MAX_DAYS)\s+([0-9]+)/\1 90/' /etc/login.defs`
以上将 PASS_MAX_DAYS 99999 设置为 PASS_MAX_DAYS 90（密码有效期90天）

`sed -r -i 's/(PASS_MIN_LEN)\s+([0-9]+)/\1 13/' /etc/login.defs`
以上将 PASS_MIN_LEN 5 设置为 PASS_MIN_LEN 13（密码字符长度13位）

**配置参数：**

retry=N：定义登录/修改密码失败时，可以重试的次数

difok=N：新密码必需与旧密码不同的位数 difok=3 新密码必须与旧密码有3位不同

minlen=N：新密码的最小长度

dcredit=N：当N>0时表示新密码中数字出现的最多次数；当N<0时表示新密码中数字出现最少次数；

ucredit=N: 当N>0时表示新密码中大写字母出现的最多次数；当N<0时表示新密码中大写字母出现最少次数；

lcredit=N: 当N>0时表示新密码中小写字母出现的最多次数；当N<0时表示新密码中小写字母出现最少次数；

ocredit=N：当N>0时表示新密码中特殊字符出现的最多次数；当N<0时表示新密码中特殊字符出现最少次数；

maxrepeat=N：拒绝包含多于N个相同连续字符的密码。 默认值为0表示禁用此检查

maxsequence=N：拒绝包含长于N的单调字符序列的密码。默认值为0表示禁用此检查。实例是'12345'或'fedcb'。除非序列只是密码的一小部分，否则大多数此类密码都不会通过简单检查。

enforce_for_root: 如果用户更改密码是root，则模块将在失败检查时返回错误。默认情况下，此选项处于关闭状态，只打印有关失败检查的消息，但root仍可以更改密码。不要求root用户输入旧密码，因此不会执行比较旧密码和新密码的检查
