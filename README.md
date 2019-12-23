# 12306抢票
这是一个牛逼的全自动购票系统，该系统为 `Spring Boot` 编写的后端服务，就不需要天天盯着 `12306` 官网查询余票了，用起来很爽，保证不收集任何敏感信息，真的。

> 马云 Gitee：https://gitee.com/qianxunclub/ticket  
> GitHub：https://github.com/qianxunclub/ticket
# 功能介绍
- 自动识别验证码
- 多账号同步购票
- 动态添加抢票账号
- 定时刷新监控余票
- 设置多座位优先抢票
- 自动下单
- 下单成功短息通知
- 接口文档：http://localhost:9998/swagger-ui.html
- 配置代理
- 支持Mac、Linux、Windows


# 基础环境
- JDK8
- maven
- python3.6
- 安装 Chrome 浏览器

# python 依赖安装
Mac 或者 Linux 用户使用虚拟环境：
```
cd ticket/python

python3 -m venv venv

source venv/bin/activate

sudo pip install -r requirements.txt
或者
sudo pip install -r requirements.txt -i https://pypi.tuna.tsinghua.edu.cn/simple

```
Windows 用户使用本地当前环境：
```
cd ticket/python

pip install -r requirements.txt
或者
pip install -r requirements.txt -i https://pypi.tuna.tsinghua.edu.cn/simple

```
# 验证码识别测试
Mac 或者 Linux 用户使用虚拟环境：
```
cd ticket/python

source venv/bin/activate

python3 main.py ../temp/index.jpg
```

Windows 用户使用本地当前环境： 

```
cd ticket/python


python main.py ../temp/index.jpg
```

# 使用说明

## 默认用户配置
默认用户是指在项目启动的时候，直接开始抢购对应的配置购票信息。  
配置文件：[application-user.yml](src/main/resources/application-user.yml)  

## COOKIES 配置
配置文件：[application-cookie.yml](src/main/resources/application-cookie.yml) 
```
cookies:
  # 是否使用这个 cookie，如果启用，不会自动获取最新 cookie，linux 上面为 true，因为不能打开网页获取😁
  enable: false
  rail_expiration: "1576330253758"
  rail_deviceid: "D0vKZrOYYR8LWwpDIMmErxMPQ_weK4SG8vBGv_hk-Hl7iOEpGACn8QqbxAPren7my5aAozndcRPaNV0lhBepXDUVe_AEWyYmahcm75ZViUV_Ty6NbfVO20fWgQhNPSkAj5anYugDWT1drqVO9GRLv6vfHrVSbGJE"

``` 
获取方式：执行测试类 [`GetLogdeviceTest`](src/test/java/com/qianxunclub/ticket/GetLogdeviceTest.java)

## 代理配置
请求可配置代理，配置文件：[application.yml](src/main/resources/application.yml) 
```
config:
  # 是否启用代理
  enableProxy: false
  # 代理 HOST
  proxyHost: 127.0.0.1
  # 代理端口
  proxyPort: 12639
``` 

## 短信配置
短信使用的是阿里云短信服务，这个很便宜，申请一个就好了：[点击申请](https://www.aliyun.com/product/sms?spm=5176.8142029.cloudEssentials.57.e9396d3edQ9wXL)
模板如下：
```$xslt
模版内容:
您好${name}，下单成功，订单号为：${orderId}，请尽快登录并支付，账号：${username}，密码：${password}。

变量属性:
name-其他；orderId-其他号码；username-其他；password-其他号码；
```  

修改配置文件：[application-sms.yml](src/main/resources/application-sms.yml)  
```
notice:
  accessKeyId: "阿里云获取"
  accessSecret: "阿里云获取"
  templateCode: "阿里云获取"
  signName: "阿里云获取"
```

# 在线接口文档
项目集成了 `swagger` ，可以在线直接调用接口使用。  
项目启动成功后，输入地址：
```
http://localhost:9998/swagger-ui.html
```

# 常见问题
无

# 开发者讨论
有什么好建议或者想法的，可以添加QQ群一起讨论：852214454
