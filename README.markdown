# MiddlewareWeb

### 2017年12月19日
- 1、改动部分SELECT模型中异常处理，使之更为合理。

### 2017年12月14日
- 1、给服务器web添加样本下发功能，其实现与Middleware的TCP长连接，当web网页生成下发样本信息时，将其通过socket方式发送给middleware。
- 2、web网页实现可自定义生成下发样本。可以将web网页作为样本的源头，形成web-middleware-仪器三层架构。
- 3、已经完成了基本测试，功能顺利实现。下一步可以做web端项目编号设置等功能。或者从LIS端接收申请样本功能。
- 4、TCP连接处选用NIO的SELECT模型，非阻塞模式，一个线程即可监控多个客户端的TCP长连接。

### 2017年12月3日
- 1、更改监控页面服务器与客户端的数据交互方式，将轮询（非长轮询）方式更改为websocket的TCP长连接形式。不需要前端占用资源定时去请求数据，而是当存在新数据时，由服务器端（后台）主动发送给前端，减轻了客户端资源，但这个协议存在于HTML5中，不适用于老旧浏览器型号。

### 2017年11月28日
- 1、完成Middleware发送样本数据，WEB接收监控数据的全过程。
- 2、解决了json串传输过程中的中文乱码问题。

### 2017年11月27日
- 1、完善监控页面功能，将其分为两个模块，分别为样本监控和仪器监控。采用侧边栏显示。
- 2、对于仪器监控，新建device数据库表，用于存储某一仪器某天在哪些时刻做的样本数量，后续可以添加该仪器的样本准确率等信息。

### 2017年11月22日
- 1、完善样本数据查询功能，将其分为四个模块，分别为根据时间查询、根据样本查询、根据仪器查询和根据病人姓名查询，并采用侧边栏显示。
- 2、增加HTTP错误异常处理，添加400、404、500错误的定向页面。

### 2017年11月20日
- 1、完善样本数据查询功能，能够通过父子表的形式展示某一样本的详细信息。
- 2、添加监控页面，能够实现实时监控上传的样本数据。

### 2017年11月16日
- 1、初步确定建立web项目，其中选用Gradle作为构建工具。选用freemarker作为定义web视图，选用mybatis来连接MySQL数据库。
- 2、实现了用户登陆的页面和初步功能，但还没有注册功能。
- 3、实现了样本数据查询功能，能够在网页中查询样本信息。
