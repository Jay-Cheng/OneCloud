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
    showDirContents("home");
    $(".disk-file-item").click(function() {
        if ($(this).attr("data-enter") != undefined) {
            var node = $('<li></li>');
            var dirName = $(this).find(".file-name").text();
            node.append('<a href="javascript:void(0)">' + dirName + '</a>');
            node.attr("data-enter", $(this).attr("data-enter"));
            node.appendTo($("#disk_file_path"));
            showDirContents($(this).attr("data-enter"));
        }
    });
});
/*
 * 通用函数：动态生成某个文件夹下的所有文件
 * dir是文件夹的路径   
 */
function showDirContents(dir) {
    $("#all ul").hide();
    alert(dir);
    var node = $('[data-dir="' + dir + '"]');
    alert("here");
    if (node.length != 0) {
        node.show();
        return;
    }
    node = $("<ul></ul>");
    node.attr("data-dir", dir);
    for (var i = 0;i < files.length; i++) {
        if (files[i].localURL == dir) {
            /* 生成一个该文件的<li>节点 */
            var fileImg = "img/icon/";
            var fileName = files[i].localName + "." + files[i].localType;
            var fileSize = getReadableSize(files[i].size);
            var lastModified = files[i].lastModified;
            switch(files[i].localType) {
                case "dir": 
                fileName = files[i].localName;
                fileSize = "";
                fileImg += "folder";
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
            /* 如果该节点是文件夹，设置其data-enter属性为该节点的路径 */
            if (files[i].localType == "dir" && files[i].size == null) {
                fileNode.attr("data-enter", dir + "/" + files[i].localName);
            }
            fileNode.appendTo(node);
        }
    }
    node.appendTo($("#all"));
}