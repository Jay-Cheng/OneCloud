$(function(){
	/* 为根节点绑定事件处理函数 */
	$(".dirbox-body .treeNode-info").click(showSubDir);
});
/*
 * 单击模态框的文件夹节点时绑定的事件处理函数
 */
function showSubDir() {
	var dirID = $(this).attr("data-folder-id");
	/* 把操作路径的文件夹ID设为当前点击文件夹的ID */
	$("#dirbox_path").attr("data-folder-id", dirID);

	/* 判断节点是否存在，如果不存在则生成，存在则显示 */
	if (getFolderNode(dirID) == null ) {
		createFolderNode(dirID, false);
	} else {
		toggleExistSubDir($(this));
	}
}
/*
 * 显示/隐藏已经生成过的子文件夹并更新操作路径
 */
function toggleExistSubDir(dir) {
	/* 更新文件夹图标 */
	var icon = dir.find(".glyphicon");
	if (icon.hasClass("glyphicon-folder-open")) {
		icon.removeClass("glyphicon-folder-open");
		icon.addClass("glyphicon-folder-close");
	} else {
		icon.removeClass("glyphicon-folder-close");
		icon.addClass("glyphicon-folder-open");			
	}
	/* 显示或隐藏子文件夹 */
	dir.next().slideToggle("fast");
	/* 更新操作路径 */
	var path = dir.find(".treeNode-info-name").text();
	if (path != "我的网盘") {
		var parentNode = dir;
		do {
			parentNode = parentNode.parent().parent().prev();
			var parent = parentNode.find(".treeNode-info-name").text();
			path = parent + " \\ " + path;
		} while (parent != "我的网盘");
	}
	$("#dirbox_path").text(path);
}
/*
 * 供file-system.js中的createFolderNode()函数调用
 * 生成主页面文件夹节点的同时会生成模态框文件夹节点
 */ 
function createDirNode(folderID, folders) {
	var dir = $('div[data-folder-id="' + folderID + '"]');
	var paddingParam = parseInt(dir.css("padding-left"), 10) + 20 + "px";
	var sub = dir.next();
	sub.hide();
	for (var i = 0; i < folders.length; i++) {
        var id = folders[i].id;
        var folderName = folders[i].localName;
		var subNode = $("<li></li>");
		subNode.append('<div class="treeNode-info"><span class="glyphicon glyphicon-folder-close"></span><span class="treeNode-info-name">' + folderName + '</span></div>');
		subNode.append('<ul></ul>');
		subNode.find(".treeNode-info").css("padding-left", paddingParam);
		subNode.find(".treeNode-info").attr("data-folder-id", id);
		subNode.find(".treeNode-info").click(showSubDir);
		subNode.appendTo(sub);
	}
	toggleExistSubDir(dir);
}