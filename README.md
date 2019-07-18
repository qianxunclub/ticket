# 12306抢票
这是一个牛逼的全自动购票系统，该系统为 `Spring Boot` 编写的后端服务，就不需要天天盯着 `12306` 官网查询余票了，用起来很爽，保证不收集任何敏感信息，真的。

# 功能介绍
- 自动识别验证码
- 多账号同步购票
- 定时刷新监控余票
- 设置多座位优先抢票
- 自动下单
- 下单成功短息通知


# 基础环境
- JDK8
- maven
- python3

# python 依赖安装
我这里使用的是虚拟环境：
```
cd ticket/python

python3 -m venv venv

sudo pip install -r requirements.txt

```

# 验证码识别测试
如果使用的是虚拟环境，需要先执行：
```
cd ticket/python

source venv/bin/activate
```

测试验证码是否能正确的识别，一般服务提示验证码错误，都是因为依赖没有安装全导致的：  

```
cd ticket/python


python3 main.py ../temp/index.jpg
```

# 配置说明
## 短信配置
短信使用的是阿里云短信服务，这个很便宜，申请一个就好了：[点击申请](https://www.aliyun.com/product/sms?spm=5176.8142029.cloudEssentials.57.e9396d3edQ9wXL)  
修改配置文件：[application-sms.yml](src/main/resources/application-sms.yml)  
```
notice:
  accessKeyId: "阿里云获取"
  accessSecret: "阿里云获取"
  templateCode: "阿里云获取"
  signName: "阿里云获取"
```

## 默认用户配置
默认用户是指在项目启动的时候，直接开始抢购对应的配置购票信息。  
配置文件：[application-sms.yml](src/main/resources/application-user.yml)  

# 开发者讨论
有什么好建议或者想法的，可以添加QQ群一起讨论：852214454

# 感谢
- [胖大海]()：提供 `RAIL_EXPIRATION` 和 `RAIL_DEVICEID` 两个 cookie 获取方式。