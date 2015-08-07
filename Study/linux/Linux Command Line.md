# Linux 常用命令

- getconf LONG_BIT 查看操作系统位数

- lsb_release -a 查看系统当前版本号


## 查看端口占用情况并杀掉

netstat junlp | grep 端口号

kill -9 进程号

scp -r share@192.168.225.109:.m2/repository .m2/

运行lombok安装到eclipse中： java -jar ~/Downloads/tar/lombok.jar

M2E - http://download.eclipse.org/technology/m2e/releases

红杏邀请链接：honx.in/_VX_To4kWGkiiyw28

## Set route to access extenal net via VPN

```shell
sudo route add -net 192.168.0.0 netmask 255.255.0.0 gw 192.168.225.1
```

**Note:** *Add the command into a shell and run it everytime system starts up is a good idea.*
