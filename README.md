# OneCloud
## 一、简介
基于Java Web的网盘单页应用（SPA），采取前后端分离的架构，前后端只通过JSON进行数据交换（**未使用JSP**）。前端UI参考了[微云](https://www.weiyun.com/)和[百度网盘](https://pan.baidu.com/)。

- 开发环境
    - OS：Ubuntu 16.04LTS
    - IDE：Eclipse Oxygen.1a Release (4.7.1a)
    - JDK：v1.8.0_144
    - Web Server：Apache Tomcat 8.5.23
    - DB：MySQL Server 5.7.19
- 前端框架
    - jQuery v3.2.1
    - Bootstrap v3.3.7
- 后端框架
    - Hibernate v5.2.10
- 其它
    - Maven v3.5.0
    - [右键菜单](https://github.com/dgoguerra/bootstrap-menu)
    - [browser-md5-file](https://github.com/forsigner/browser-md5-file)
    - [jQuery-File-Upload](https://github.com/blueimp/jQuery-File-Upload)
## 二、功能
- 文件的增删改查/排序/分类
- 上传/下载/MD5校验/断点续传
- 待实现
    - 分享
    - 安全：校验前端参数/...
    - 统计在线人数，访问量
- 不打算实现
    - 分类文件/搜索结果文件的右键菜单
    - 保险箱
    - 块状视图
- 已知bug
    - 多用户同时上传MD5值相同的文件
- 可优化
    - 一个文件夹下有文件更新，如果该文件夹已经生成，删除生成的节点（下次触发“进入文件夹事件”时会重新生成）
## 三、部署

1. 安装上述环境
2. 设置MySQL字符集为UTF-8，然后运行init.sql
3. 在Eclipse中把Server的Server Location设置为Use Tomcat installation, Deploy path设置为webapps
4. 在tomcat的安装目录下的webapps文件夹下新建一个文件夹onecloud_files，用于放置用户上传的文件（见com.zhengzijie.onecloud.manager包下的ContextInitializer.java），注意这一项会影响前端用户上传图片的显示
5. 直接登录，注册功能暂不开放
## 四、实现
// TDDO