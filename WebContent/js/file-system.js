$(function() {
    $("#disk_file_path li").click(goBack);// 为初始面包屑节点绑定事件处理函数
    showFolderContents(0);// 约定初始文件夹ID=0
});

function showFolderContents(folderID) {
    /* 隐藏当前文件夹内容 */
    $("#all ul").hide();
    /* 如果需展示的文件夹之前已经生成过，则显示对应节点，返回 */
    var node = getFolderNode(folderID);
    if (node != null) {
        node.show();
        return;
    } else {
        createFolderNode(folderID, true);
    }
}
/*
 * 本函数会在两处被调用(1)主界面需要显示的文件夹节点不存在时(2)“选择路径”模态框的文件夹节点不存在时
 * 生成主界面文件夹节点（内含文件节点）的同时会生成“选择路径”模态框的文件夹节点（不含文件节点）
 * 如果show=true则会立刻在主界面显示生成的文件夹节点，否则只生成不显示
 * ---------------------------------------------------------------------------------------------------
 * 注意如果在ajax success事件处理函数中返回一个值供其它函数调用，会出现获得undefined的情况（因为请求是异步的）
 * 考虑同步请求即async=false，但是(1)deprecated(2)阻塞
 * ---------------------------------------------------------------------------------------------------
 * 原先思路：生成节点后始终hide，将节点返回到调用的上一级，由上一级决定是否要显示该节点
 * 但由于请求异步，会导致上一级获得"undefined"的情况
 */
function createFolderNode(folderID, show) {
    /* 生成一个文件夹节点<ul>，设置data-folder-id属性，该属性是该文件夹在数据库存储的ID */
    var node = $("<ul></ul>");
    node.attr("data-folder-id", folderID);
    /* 向服务器发送异步请求，获取对应folderID的内容 */
    $.ajax({
        type: "GET",
        url: "GetFolderContentsServlet?userID=" + sessionStorage.getItem("user_id") + "&folderID=" + folderID,
        /* 
         * 在success的回调函数中访问不到外部的folderID，所以添加下面的参数 
         * 修正：访问不到函数的参数，但可以访问函数内部的变量！
         */  
        folderID: folderID,
        success: function(result) {
            var folders = result.folders;
            var files = result.files;
            /* 生成“选择路径”模态框的文件夹节点 */
            createDirNode(this.folderID, folders);/* 必须在参数前面加上this. */
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
                var fileType = files[i].localType;
                if (fileType != "") {
                    fileType = "." + fileType;
                }
                var fileName = files[i].localName + fileType;
                var fileSize = getReadableSize(files[i].size);
                var lastModifiedTime = files[i].gmtModified;
                var fileID = files[i].fileID;
                var fileImg = getFileIcon(fileName);
                var fileNode = $('<li class="disk-file-item"></li>');
                fileNode.append('<div class="file-head"><div class="select"><input type="checkbox"></div><div class="thumb"><img src="' + fileImg + '" class="thumb-icon"></div><div class="file-title"><span class="file-name">' + fileName + '</span></div></div>');
                fileNode.append('<div class="file-info"><span class="file-size">' + fileSize + '</span><span class="file-time">' + lastModifiedTime + '</span></div>');
                fileNode.attr("data-file-id", fileID);
                /* 追加该节点到当前文件夹 */
                fileNode.appendTo(node);                
            }
            if (!show) {
                node.hide();
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
 * 单击面包屑导航时绑定的事件处理函数
 */
function goBack() {
    $(this).nextAll().remove();// 把本节点之后的sibling全部移除
    $(this).children().addClass("active");// 把当前单击的面包屑节点设置为active
    showFolderContents($(this).attr("data-folder-id"));// 显示该面包屑节点对应的文件夹
}
/*
 * 判断folderID对应的文件夹节点是否存在
 * 是则返回该节点
 * 否则返回null
 */
function getFolderNode(folderID){
    var node = $('ul[data-folder-id="' + folderID + '"]');// ！！标签 + 属性选择器 ！！
    if (node.length != 0) {
        return node;
    } else {
        return null;
    }
}

/*
 * 测试函数
 * 打印js对象
 */
function printObj(obj){
    var output = "";
    for(var i in obj){  
        var property = obj[i];  
        output += i + " = "+ property + "\n" ; 
    }  
    alert(output);
}