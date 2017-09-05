$(function() {
    $("#disk_file_path li").click(goBack);// 为初始面包屑节点绑定事件处理函数
    showFolderContents(0);// 约定初始文件夹ID=0
});

function showFolderContents(folderID) {
    /* 隐藏当前文件夹内容 */
    $("#all ul").hide();
    /* 如果需展示的文件夹之前已经生成过，则显示对应节点，返回 */
    var node = $('ul[data-folder-id="' + folderID + '"]');// ！！标签 + 属性选择器 ！！
    if (node.length != 0) {
        node.show();
        return;
    }
    /* 如果需展示的文件夹不存在 */
    /* 生成一个文件夹节点<ul>，设置data-folder-id属性，该属性是该文件夹在数据库存储的ID */
    node = $("<ul></ul>");
    node.attr("data-folder-id", folderID);
    /* 向服务器发送异步请求，获取对应folderID的内容 */
    $.ajax({
        type: "GET",
        url: "GetFolderContentsServlet?userID=" + sessionStorage.getItem("user_id") + "&folderID=" + folderID,
        success: function(result) {
            var folders = result.folders;
            var files = result.files;

            /* 生成当前文件夹下所有文件夹节点 */
            for (var i = 0; i < folders.length; i++) {
                var folderID = folders[i].id;
                var folderName = folders[i].localName;
                var lastModifiedTime = folders[i].gmtModified;
                var folderNode = $('<li class="disk-file-item"></li>');
                folderNode.append('<div class="file-head"><div class="select"><input type="checkbox"></div><div class="thumb"><img src="img/icon/folder.png" class="thumb-icon"></div><div class="file-title"><span class="file-name">' + folderName + '</span></div></div>');
                folderNode.append('<div class="file-info"><span class="file-size"></span><span class="file-time">' + lastModifiedTime + '</span></div>');
                /* 设置文件夹ID，绑定事件处理函数 */
                folderNode.attr("data-folder-id", folderID);
                folderNode.on("click", enterFolder);
                /* 追加该节点到当前文件夹 */
                folderNode.appendTo(node);
            }

            /* 生成当前文件夹下所有文件节点 */
            for (var i = 0; i < files.length; i++) {
                var fileName = files[i].localName + "." + files[i].localType;
                var fileSize = getReadableSize(files[i].size);
                var lastModifiedTime = files[i].gmtModified;
                /* 根据文件类型设置icon */
                var fileImg = "img/icon/";
                switch(files[i].localType) {
                    case "mp4": 
                    fileImg += "video";
                    break;
                    case "jpg": 
                    fileImg += "picture";
                    break;
                    default: 
                    fileImg += "file";
                }
                fileImg += ".png";

                var fileNode = $('<li class="disk-file-item"></li>');
                fileNode.append('<div class="file-head"><div class="select"><input type="checkbox"></div><div class="thumb"><img src="' + fileImg + '" class="thumb-icon"></div><div class="file-title"><span class="file-name">' + fileName + '</span></div></div>');
                fileNode.append('<div class="file-info"><span class="file-size">' + fileSize + '</span><span class="file-time">' + lastModifiedTime + '</span></div>');
                /* 追加该节点到当前文件夹 */
                fileNode.appendTo(node);                
            }

            node.appendTo($("#all"));
        }
    });
}

/* 
 * 单击文件夹后，更新面包屑导航并列出该文件夹下的所有文件
 * 该函数绑定到文件夹<li>节点的click事件上
 */
function enterFolder() {
    /* 判断该文件是否是文件夹 */
    if ($(this).attr("data-folder-id") != undefined) {
        /* 生成一个该文件夹的面包屑导航节点 */
        var breadNode = $('<li></li>');
        /* 设置面包屑节点的名字 */
        var folderName = $(this).find(".file-name").text();
        breadNode.append('<a href="javascript:void(0)">' + folderName + '</a>');
        /* 设置面包屑节点对应的文件夹ID */
        breadNode.attr("data-folder-id", $(this).attr("data-folder-id"));

        breadNode.on("click", goBack);
        /* 移除之前面包屑节点的active效果，并把当前面包屑节点设置为active */
        $("#disk_file_path li").children().removeClass("active");
        breadNode.children().addClass("active");
        /* 追加该面包屑到系统路径 */
        breadNode.appendTo($("#disk_file_path"));
        /* 列出该文件夹下的所有文件 */ 
        showFolderContents($(this).attr("data-folder-id"));
    }    
}

/*
 * 面包屑导航click时绑定的事件处理函数
 */
function goBack() {
    $(this).nextAll().remove();// 把本节点之后的sibling全部移除
    $("#disk_file_path li").children().removeClass("active");// ！！！！！！！！！！！！测试这行代码是否可以删除
    $(this).children().addClass("active");// 把当前单击的面包屑节点设置为active
    showFolderContents($(this).attr("data-folder-id"));// 显示该面包屑节点对应的文件夹
}

function alertObj(obj){
    var output = "";
    for(var i in obj){  
        var property = obj[i];  
        output += i + " = "+ property + "\n" ; 
    }  
    alert(output);
}