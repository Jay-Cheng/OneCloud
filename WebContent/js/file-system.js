/* 模拟获取数据 */
/* 服务端传来的JSON数据解析后获得files对象 */
var dir1 = {
    id: 0,
    localName: "dir1",
    localType: "dir",
    size: null,
    lastModified: "2017-07-07 07:07",
    localURL: "home"                
};
var dir2 = {
    id: 1,
    localName: "dir2",
    localType: "dir",
    size: null,
    lastModified: "2017-07-07 07:07",
    localURL: "home"                
};
var file1 = {
    id: 2,
    localName: "file1",
    localType: "jpg",
    size: 222222,
    lastModified: "2017-07-07 07:07",
    localURL: "home"                
};
var dir3 = {
    id: 3,
    localName: "dir3",
    localType: "dir",
    size: null,
    lastModified: "2017-07-07 07:07",
    localURL: "home/dir1"                
};
var file2 = {
    id: 4,
    localName: "file2",
    localType: "mp4",
    size: 444444,
    lastModified: "2017-07-07 07:07",
    localURL: "home/dir1"                
};
var dir4 = {
    id: 5,
    localName: "dir4",
    localType: "dir",
    size: null,
    lastModified: "2017-07-07 07:07",
    localURL: "home/dir1"                
};
var file3 = {
    id: 6,
    localName: "file3",
    localType: "txt",
    size: 666666,
    lastModified: "2017-07-07 07:07",
    localURL: "home/dir2"                
};
var file4 = {
    id: 7,
    localName: "file4",
    localType: "dir",
    size: 777777,
    lastModified: "2017-07-07 07:07",
    localURL: "home/dir1/dir3"                
};
var file5 = {
    id: 8,
    localName: "file5",
    localType: "zip",
    size: 888888,
    lastModified: "2017-07-07 07:07",
    localURL: "home/dir1/dir3"                
};
var files = [dir1, file1, file2, file3, dir2, dir3, dir4, file4, file5];

$(function() {
    $("#disk_file_path li").click(goBack);
    showDirContents("home");
});
/*
 * 通用函数：动态生成某个文件夹下的所有文件
 * dir是文件夹的路径   
 */
function showDirContents(dir) {
    /* 隐藏当前文件夹内容 */
    $("#all ul").hide();
    /* 如果需展示的文件夹之前已经生成过，则显示对应节点，返回 */
    var node = $('[data-dir="' + dir + '"]');
    if (node.length != 0) {
        node.show();
        return;
    }
    /* 如果需展示的文件夹不存在 */
    /* 生成一个文件夹节点<ul>，设置data-dir属性，该属性是该文件夹节点的路径*/
    node = $("<ul></ul>");
    node.attr("data-dir", dir);
    for (var i = 0;i < files.length; i++) {
        if (files[i].localURL == dir) {
            /* 如果文件存在于该文件夹下 */
            /* 生成一个该文件的<li>节点 */
            var fileImg = "img/icon/";
            var fileName = files[i].localName + "." + files[i].localType;
            var fileSize = files[i].size;
            if (fileSize != null) {
                fileSize = getReadableSize(files[i].size);
            }
            var lastModified = files[i].lastModified;
            switch(files[i].localType) {
                case "dir":
                if (fileSize == null) {
                    fileName = files[i].localName;
                    fileSize = "";
                    fileImg += "folder";                    
                } else {
                    // 文件后缀为dir的情况
                    fileImg += "file";
                }
                break;
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
            fileNode.append('<div class="file-info"><span class="file-size">' + fileSize + '</span><span class="file-time">' + lastModified + '</span></div>');
            /* 如果该节点是文件夹，设置其data-enter属性为该节点的路径，并绑定相应事件 */
            if (files[i].localType == "dir" && files[i].size == null) {
                fileNode.attr("data-enter", dir + "/" + files[i].localName);
                fileNode.on("click", enterDir);
            }
            fileNode.appendTo(node);
        }
    }
    node.appendTo($("#all"));
}

/* 
 * 单击文件夹后，更新面包屑导航并列出该文件夹下的所有文件
 * 该函数绑定到文件夹<li>节点的click事件上
 */
function enterDir() {
    /* 判断该文件是否是文件夹 */
    if ($(this).attr("data-enter") != undefined) {
        /* 生成一个该文件夹的面包屑导航节点 */
        var node = $('<li></li>');
        var dirName = $(this).find(".file-name").text();
        node.append('<a href="javascript:void(0)">' + dirName + '</a>');
        node.attr("data-enter", $(this).attr("data-enter"));
        node.on("click", goBack);
        $("#disk_file_path li").children().removeClass("active");
        node.children().addClass("active");
        node.appendTo($("#disk_file_path"));
        /* 列出该文件夹下的所有文件 */ 
        showDirContents($(this).attr("data-enter"));
    }    
}

/*
 * 面包屑导航click时绑定的事件处理函数
 */
function goBack() {
    /* 把本节点之后的sibling全部移除 */
    $(this).nextAll().remove();
    $("#disk_file_path li").children().removeClass("active");
    $(this).children().addClass("active");
    showDirContents($(this).attr("data-enter"));
}