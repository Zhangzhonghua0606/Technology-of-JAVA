# Shell Command Summary

## 读取用户终端输入

|   命令格式  |                  描述                  |
|-------------|----------------------------------------|
| read answer | 从标准输入读取输入并赋值给变量answer。 |
|read -p 'prompt'|打印提示，等待输入，并将输入存储在REPLY中。|
|read -r line|允许输入包含反斜杠|

Example:

```shell
#! /bin/bash
read -p 'Are you sure to do that(y/n)?'
```

## 状态判断

- 使用`test expression`或`[ expression ]`进行状态判断，结果为0表示成功，否则表示失败，需要注意的是这种方式不支持`Shell`中提供的通配符

Example:

```shell
#! /bin/bash
$name = byron
# 以下两种表达方式是等价的
# 1
test $name = byron
# 2
[ $name = byron ]
```

- 若想使用`Shell`中的通配符，可以使用*复合命令操作符*`[[ expession ]]`这种方式, 同时`expression`也可以包含，`&&`， `||`之类的操作符

Example:

```shell
#! /bin/bash
$name = byron

[[ $name == [Bb]yron ]]
```

- 通配符： `[Nn]o`表示匹配`No`和`no`, `?(can|cant)`表示匹配1个或0个(`can`或`cant`)

##  流程控制语句

- Example1:

```shell
#! /bin/bash
if [[ expression ]]; then
    ...
elif [[ expression ]]; then
    ...
else
    ...
fi
```

- Example2:

```shell
#! /bin/bash
case variable in
    value1)
        ...
        ;;
    value2)
        ...
        ;;
esac
```

## 循环语句

- Example1：

```shell
#! /bin/bash
for variable in ${list}
do
    ...
done
```

- Example2:

```shell
#! /bin/bash
while expression
do
    ...
done
```

- Example3:

```shell
#! /bin/bash
#其判断条件和while正好相反，即expression返回非0，或条件为假时执行循环体内的命令。
until expression
do
    ...
done
```

## 重定向

- 一个很有用的功能是将一个标准文件句柄重定向到另一个。最流行的一种用法是将标准错误输出融合到标准输出中去，这样错误信息可以和其他普通的输出信息一起处理。[参考这里](https://en.wikipedia.org/wiki/Redirection_(computing))

```shell
# 1.
command > file 2>&1
# 2.
command &> file
# 3.
command >& file
```

## Best Practice

- 字符串的比较用=, !=之类的，数字的比较用eq, gt之类的
- 字符串的“等于“比较，为了与POSIX一致，在[]中请用”=”.（尽管”==”也可以可以用的）
- 字符串的>, <比较运算符，一般放在[[ ]]之中，而不是test ("[]")
- 字符串的>, <比较的结果，与本地的locale有关，是按照其字典序列进行比较的

## 正则表达式

- 标准正则表达式

    `.` `*` `[]` `[^]` `[-]` `/{m,n/}`
- 扩展正则表达式

    `?` `()`

## 截取字符串

- cut
    - -f

    ```sh
    # cut -f 1,3 -d ":"
    ```
- awk (printf, print)

    ```sh
    # awk 'BEGIN{动作}ACTION{动作}ACTION{动作}...END{动作}'
    ```

- sed
