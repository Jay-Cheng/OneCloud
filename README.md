# OneCloud
## 一、简介
这是一个基于Spring MVC、Hibernate的网盘单页应用（[SPA](https://en.wikipedia.org/wiki/Single-page_application)）。项目采用了前后端分离的架构，前端发送AJAX请求，后端提供[REST](https://en.wikipedia.org/wiki/Representational_state_transfer)ful风格的API返回JSON数据。Web端UI参考了[微云](https://www.weiyun.com/)和[百度网盘](https://pan.baidu.com/)。

- 开发环境
    - OS：Ubuntu 16.04LTS
    - IDE：Eclipse Oxygen.1a Release (4.7.1a)
    - JDK：v1.8.0_144
    - Web Server：Apache Tomcat 8.5.23
    - DB：MySQL Server 5.7.19
    - Browser：Google Chrome 61.0.3163.100 (Official Build) (64-bit)  
    没有做浏览器兼容，不保证在其它浏览器上显示正常
- 前端框架
    - jQuery v3.2.1
    - Bootstrap v3.3.7
- 后端框架
    - Spring v5.0.1
    - Hibernate v5.2.10
- 其它
    - Maven v3.5.0
    - [右键菜单](https://github.com/dgoguerra/bootstrap-menu)
    - [browser-md5-file](https://github.com/forsigner/browser-md5-file)
    - [jQuery-File-Upload](https://github.com/blueimp/jQuery-File-Upload)
## 二、功能
基本实现了主流网盘的大部分功能

- 用户认证与安全  
  登录时前端将用户名、密码提交到服务器后，服务器会根据用户信息生成一个[JSON Web Token](https://github.com/jwtk/jjwt)返回给前端，之后前端发送请求访问用户个人受保护资源时都必须带上这个Token，经过服务器校验成功后，服务器才会返回数据给前端。
- 上传/多文件打包下载/MD5文件查重/断点续传  
  上传时会先计算待上传文件的MD5值，检查该文件是否被其他用户上传过，如果服务器已经存在该MD5值对应的文件，则用户不需要再上传，服务器会直接生成一个该文件的代理（类似于快捷方式）供用户使用。
- 文件及文件夹的（批量）增删改查/排序/分类/防止文件命名冲突/限制用户存储空间等  
- 待实现功能  
    - 分享
    - 统计在线人数，访问量...
- 暂不实现  
    - 校验前端参数
    - 分类文件/搜索结果文件的右键菜单（功能重复）
    - 保险箱（功能重复）
    - 块状视图（要写大量CSS）

## 三、部署

1. 安装必要的环境（JDK1.8+,Maven,Tomcat,MySQL等）
2. 设置MySQL字符集为UTF-8，然后运行init.sql
3. 在Eclipse中把Server的Server Location设置为Use Tomcat installation, Deploy path设置为webapps
4. 在tomcat的安装目录下的webapps文件夹下新建一个文件夹OneCloud_Upload（用于放置用户上传的文件），并在FileService接口下修改对应的路径（见com.zhengzijie.onecloud.service包下的FileService接口，注意该项会影响图片显示）
5. 直接登录，注册功能暂不实现

界面大概长这个样子

![示例图片](https://github.com/zhengzijie96/OneCloud/blob/master/preview.png)

## 四、实现
// TDDO





