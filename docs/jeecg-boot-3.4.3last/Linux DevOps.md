[返回](../)

# Linux DevOps

## CentOS 安装Redis

### 有外网

```shell
# 参考：https://www.digitalocean.com/community/tutorials/how-to-install-secure-redis-centos-7
sudo yum install epel-release
sudo yum install redis -y
sudo systemctl start redis.service
sudo systemctl enable redis
sudo systemctl status redis.service
redis-cli ping
# 配置
sudo vi /etc/redis.conf
# 防火墙
sudo firewall-cmd --permanent --new-zone=redis
sudo firewall-cmd --permanent --zone=redis --add-port=6379/tcp
sudo firewall-cmd --permanent --zone=redis --add-source=<client_server_private_IP(需要接入的ip)>
sudo firewall-cmd --reload
# 旧防火墙
sudo iptables -A INPUT -i lo -j ACCEPT
sudo iptables -A INPUT -m conntrack --ctstate ESTABLISHED,RELATED -j ACCEPT
sudo iptables -A INPUT -p tcp -s client_servers_private_IP/32 --dport 6379 -m conntrack --ctstate NEW,ESTABLISHED -j ACCEPT
sudo iptables -P INPUT DROP
```

### 没有外网

[Linux安装部署Redis(超级详细) ](https://www.cnblogs.com/hunanzp/p/12304622.html)

#### 安装

**解压**

下载完成后需要将压缩文件解压，输入以下命令解压到当前目录

```
tar -zvxf redis-5.0.7.tar.gz
```

**移动redis目录**

一般都会将redis目录放置到 /usr/local/redis目录，所以这里输入下面命令将目前在/root目录下的redis-5.0.7文件夹更改目录，同时更改文件夹名称为redis。

```
mv /root/redis-5.0.7 /usr/local/redis
```

**编译**

cd到/usr/local/redis目录，输入命令make执行编译命令，接下来控制台会输出各种编译过程中输出的内容。

```
make
```

**安装**

输入以下命令

```
make PREFIX=/usr/local/redis install
```

这里多了一个关键字 **`PREFIX=`** 这个关键字的作用是编译的时候用于指定程序存放的路径。比如我们现在就是指定了redis必须存放在/usr/local/redis目录。假设不添加该关键字Linux会将可执行文件存放在/usr/local/bin目录，

库文件会存放在/usr/local/lib目录。配置文件会存放在/usr/local/etc目录。其他的资源文件会存放在usr/local/share目录。这里指定号目录也方便后续的卸载，后续直接rm -rf /usr/local/redis 即可删除redis。

#### 启动redis

根据上面的操作已经将redis安装完成了。在目录/usr/local/redis 输入下面命令启动redis

```
./bin/redis-server& ./redis.conf
```

上面的启动方式是采取后台进程方式,下面是采取显示启动方式(如在配置文件设置了daemonize属性为yes则跟后台进程方式启动其实一样)。

```
./bin/redis-server ./redis.conf
```

两种方式区别无非是有无带符号&的区别。 redis-server 后面是配置文件，目的是根据该配置文件的配置启动redis服务。redis.conf配置文件允许自定义多个配置文件，通过启动时指定读取哪个即可。

#### redis.conf配置文件

在目录/usr/local/redis下有一个redis.conf的配置文件。我们上面启动方式就是执行了该配置文件的配置运行的。我么可以通过cat、vim、less等Linux内置的读取命令读取该文件。

也可以通过redis-cli命令进入redis控制台后通过CONFIG GET * 的方式读取所有配置项。 如下：

```
redis-cli
CONFIG GET *
```

![img](https://yoko-typora.oss-cn-hangzhou.aliyuncs.com/typora/20221124195431.png)

回车确认后会将所有配置项读取出来，如下图

这里列举下比较重要的配置项

| 配置项名称     | 配置项值范围                    | 说明                                                         |
| -------------- | ------------------------------- | ------------------------------------------------------------ |
| daemonize      | yes、no                         | yes表示启用守护进程，默认是no即不以守护进程方式运行。其中Windows系统下不支持启用守护进程方式运行 |
| port           |                                 | 指定 Redis 监听端口，默认端口为 6379                         |
| bind           |                                 | 绑定的主机地址,如果需要设置远程访问则直接将这个属性备注下或者改为bind * 即可,这个属性和下面的protected-mode控制了是否可以远程访问 。 |
| protected-mode | yes 、no                        | 保护模式，该模式控制外部网是否可以连接redis服务，默认是yes,所以默认我们外网是无法访问的，如需外网连接rendis服务则需要将此属性改为no。 |
| timeout        | 300                             | 当客户端闲置多长时间后关闭连接，如果指定为 0，表示关闭该功能 |
| loglevel       | debug、verbose、notice、warning | 日志级别，默认为 notice                                      |
| databases      | 16                              | 设置数据库的数量，默认的数据库是0。整个通过客户端工具可以看得到 |
| rdbcompression | yes、no                         | 指定存储至本地数据库时是否压缩数据，默认为 yes，Redis 采用 LZF 压缩，如果为了节省 CPU 时间，可以关闭该选项，但会导致数据库文件变的巨大。 |
| dbfilename     | dump.rdb                        | 指定本地数据库文件名，默认值为 dump.rdb                      |
| dir            |                                 | 指定本地数据库存放目录                                       |
| requirepass    |                                 | 设置 Redis 连接密码，如果配置了连接密码，客户端在连接 Redis 时需要通过 AUTH <password> 命令提供密码，默认关闭 |
| maxclients     | 0                               | 设置同一时间最大客户端连接数，默认无限制，Redis 可以同时打开的客户端连接数为 Redis 进程可以打开的最大文件描述符数，如果设置 maxclients 0，表示不作限制。当客户端连接数到达限制时，Redis 会关闭新的连接并向客户端返回 max number of clients reached 错误信息。 |
| maxmemory      | XXX <bytes>                     | 指定 Redis 最大内存限制，Redis 在启动时会把数据加载到内存中，达到最大内存后，Redis 会先尝试清除已到期或即将到期的 Key，当此方法处理 后，仍然到达最大内存设置，将无法再进行写入操作，但仍然可以进行读取操作。Redis 新的 vm 机制，会把 Key 存放内存，Value 会存放在 swap 区。配置项值范围列里XXX为数值。 |

这里我要将daemonize改为yes，不然我每次启动都得在redis-server命令后面加符号&，不这样操作则只要回到Linux控制台则redis服务会自动关闭，同时也将bind注释，将protected-mode设置为no。
这样启动后我就可以在外网访问了。

更改方式:

```
vim /usr/local/redis/redis.conf
```

通过 /daemonize 查找到属性，默认是no，更改为yes即可。 (通过/关键字查找出现多个结果则使用 n字符切换到下一个即可，查找到结果后输入:noh退回到正常模式)

####  查看Redis是否正在运行

**1、采取查看进程方式**

```
ps -aux | grep redis
```

**2、采取端口监听查看方式**

```
netstat -lanp | grep 6379
```

#### redis-cli

`redis-cli`是连接本地redis服务的一个命令，通过该命令后可以既然怒redis的脚本控制台。

#### 关闭运行中的Redis服务

输入`redis-cli` 进入控制台后输入命令`shutdown`即可关闭运行中的Redis服务了。

#### 远程连接不上问题

如下图，已经开放了Redis服务的ip不为127.0.0.1,理论上远程客户端应该可以连接了，而且云服务器的端口号也在安全组里开放了

后面发现是启动命令的问题，因为我比较偷懒，启动redis我都是直接输入命令
`redis-server` 或 `redis-server&` 这两种方式都是直接读取默认的配置文件启动，无非前者是显示启动后者是作为后台应用启动。我其实也很纳闷，因为我修改的就是默认的配置文件啊，我并没有重新生成新的配置文件，但是确实我输入命令 `redis-server /usr/local/redis/etc/redis.conf` 就是能成功，而且我输入命令`redis-server& /usr/local/redis/etc/redis.conf`也是远程登录失败。
关于直接输入`redis-server`不行的问题我还怀疑是不是Linux缓存问题，我重启服务器尝试下。结果还是一样的。。。哎先不纠结了 后续再去找原因吧


## CentOS CVE查询

1. 访问 https://access.redhat.com/security/security-updates/cve
2. 查询CVE漏洞
3. 点击对应漏洞 找到 errata 对应的 RHSA-XXXX:XXXX
4. 如 https://access.redhat.com/errata/RHSA-2023:4329
5. 点击 Updated Packages
6. 查询对应系统版本 `cat /etc/os-release` 的已解决漏洞的版本号
7. 使用 `yum update` 更新即可
8. 最后查询对应软件包版本 `yum list installed | grep openss`
9. 如果版本号低于修复版本号，需要更新软件包源，请自行解决。

这是解决方案[原文](https://access.redhat.com/discussions/6395141)：

```markdown
You *could*, but then your OpenSSH 8.0 would not be supported, which isn't a great situation for you to be in.

Just plain upstream version numbers how RHEL packages work. You'll notice the RHEL package version is `openssh-7.4p1-21.el7`. That implies we've taken upstream `7.4p1` and made at least 21 additional releases, where each release might fix one or multiple bugs or CVEs.

You can see those changes with `rpm -q --changelog openssh`

The correct way to do this would be for your security team to quantify exactly which CVEs they are concerned with. You can look up CVEs on our [CVE Database](https://access.redhat.com/security/security-updates/#/cve).

For example, [CVE-2017-15906](https://access.redhat.com/security/cve/cve-2017-15906) affected all OpenSSH before version 7.6, but we have fixed this in our OpenSSH 7.4-based package.

You can see the CVE page lists [Errata RHSA-2018:0980](https://access.redhat.com/errata/RHSA-2018:0980) which provides `openssh-7.4p1-16.el7`.

So if you're running that OpenSSH version or later, you are not vulnerable to this CVE, even though the major version of OpenSSH is "before 7.6".

This overall process is explained at bit more at: [What is backporting and how does it affect Red Hat Enterprise Linux (RHEL)?](https://access.redhat.com/solutions/57665)

```


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
