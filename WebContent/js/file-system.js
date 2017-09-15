$(function() {
    $("#all").on("click", "ul [data-folder-id]", enterFolder);
    $("#all").on("click", ".disk-file-path-wrapper .disk-file-path li", goBack);
    $("#mkfolder").click(mkfolder);

    /* 文件选择相关事件处理 */
    $("body").click(resetSelectedState);

    $("#select_all").click(selectAll);// 为全选绑定事件处理函数
    $("#all").on("click", "ul .disk-file-item .select", selectItem);
    $("#all").on("contextmenu", "ul .disk-file-item", rightClickSelectItem);

    $("#recycle").on("click", "ul .disk-file-item .select", selectItem);
    $("#recycle").on("contextmenu", "ul .disk-file-item", rightClickSelectItem);
    $("#nav_recycle").on("click", getRecycleItems);

    //$("#disk_file_path li").click(goBack);// 为初始面包屑节点绑定事件处理函数
    showFolderContents(1);// 约定初始文件夹ID=1
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
        url: "RequestManageServlet?action=enterFolder&userID=" + sessionStorage.getItem("user_id") + "&folderID=" + folderID,
        /* 
         * 在success的回调函数中访问不到外部的folderID，所以添加下面的参数 
         * 修正：访问不到函数的参数，但可以访问函数内部的变量！
         */  
        folderID: folderID,
        success: function(result) {
            var folders = result.folders;
            var files = result.files;
            /* 生成“选择路径”模态框的文件夹节点 */
            createDirNode(this.folderID, folders, !show);/* 必须在参数前面加上this. */
            /* 生成当前文件夹下所有文件夹节点 */
            for (var i = 0; i < folders.length; i++) {
                var folderID = folders[i].id;
                var folderName = folders[i].localName;
                var lastModifiedTime = getFormattedDateTime(folders[i].ldtModified);
                var folderNode = $('<li class="disk-file-item disk-item"></li>');
                folderNode.append('<div class="file-head"><div class="select"><input type="checkbox"></div><div class="thumb"><img src="img/icon/folder.png" class="thumb-icon"></div><div class="file-title"><span class="file-name">' + folderName + '</span></div></div>');
                folderNode.append('<div class="file-info"><span class="file-size"></span><span class="file-time">' + lastModifiedTime + '</span></div>');
                /* 设置文件夹ID */
                folderNode.attr("data-folder-id", folderID);
                /* 绑定事件处理函数 */
                // folderNode.on("click", enterFolder);
                // folderNode.on("contextmenu", rightClickSelectItem);
                // folderNode.find(".select").on("click", selectItem);
                /* 追加该节点到当前文件夹 */
                folderNode.appendTo(node);
            }
            // node.on("click", "[data-folder-id]", enterFolder);
            /* 生成当前文件夹下所有文件节点 */
            for (var i = 0; i < files.length; i++) {
                var fileType = files[i].localType;
                if (fileType != "") {
                    fileType = "." + fileType;
                }
                var fileID = files[i].id;
                var fileName = files[i].localName + fileType;
                var fileSize = getReadableSize(files[i].size);
                var lastModifiedTime = getFormattedDateTime(files[i].ldtModified);
                var fileImg = getFileIcon(getSuffix(fileName));
                var fileNode = $('<li class="disk-file-item disk-item"></li>');
                fileNode.append('<div class="file-head"><div class="select"><input type="checkbox"></div><div class="thumb"><img src="' + fileImg + '" class="thumb-icon"></div><div class="file-title"><span class="file-name">' + fileName + '</span></div></div>');
                fileNode.append('<div class="file-info"><span class="file-size">' + fileSize + '</span><span class="file-time">' + lastModifiedTime + '</span></div>');
                fileNode.attr("data-file-id", fileID);
                /* 绑定事件处理函数 */
                // fileNode.find(".select").on("click", selectItem);
                // fileNode.on("contextmenu", rightClickSelectItem);
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

        //breadNode.on("click", goBack);
        /* 移除之前面包屑节点的active效果，并把当前面包屑节点设置为active */
        $("#disk_file_path li").children().removeClass("active");
        breadNode.children().addClass("active");
        /* 追加该面包屑到系统路径 */
        breadNode.appendTo($("#disk_file_path"));
        /* 重置当前文件夹所有文件的选中状态 */
        //$("#all ul:visible .selected").find(".select input").click();
        /* 列出该文件夹下的所有文件 */ 
        showFolderContents($(this).attr("data-folder-id"));
    }    
}
/* 点击文件头部复选框时的事件处理函数 */
function selectItem(e) {
    /* 点击复选框的时候右键菜单不会自动隐藏 */
    $(".bootstrapMenu").hide();
    var itemTag = $(this).parent().parent();
    if ($(this).children().prop("checked")) {
        itemTag.addClass("selected");
        if (itemTag.hasClass("disk-item")) {
            /* 判断是否全选 */
            if (itemTag.parent().children("li:not(.selected)").length == 0) {
                $("#select_all input").prop("checked", true);
            }
        }
    } else {
        itemTag.removeClass("selected");
        if (itemTag.hasClass("disk-item")) {
            /* 只要有一个被移除了选中效果，就取消全选 */
            $("#select_all input").prop("checked", false);
        }
    }

    e.stopPropagation();
}
/* 在文件或文件夹上右击时的事件处理函数 */
function rightClickSelectItem() {
    if (!$(this).find(".select input").prop("checked")) {
        /* 取消所有原有的选中效果 */
    	$(this).parent().find(".selected .select input").click();
    	/* 选中当前的文件 */
        $(this).find(".select input").prop("checked", true);
        $(this).addClass("selected");
    }
}
/* 单击全选按钮的事件处理函数 */
function selectAll(e) {
    if ($(this).children().prop("checked")) {
        $("#all ul:visible li:not(.selected)").find(".select input").click();
    } else {
        $("#all ul:visible .selected").find(".select input").click();
    }
    e.stopPropagation();
}
/* 取消所有选中状态 */
function resetSelectedState() {
    $("#select_all input").prop("checked", false);
	$(".selected").find(".select input").click();
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

/*******************************************新建文件夹*******************************************/
function mkfolder() {
    var currentFolder = $("ul[data-folder-id]:visible");

    var folderTag = $('<li class="disk-file-item disk-item"></li>');
    folderTag.append('<div class="file-head"><div class="select"><input type="checkbox"></div><div class="thumb"><img src="img/icon/folder.png" class="thumb-icon"></div><div class="file-title" style="display: none;"><span class="file-name"></span></div><span class="fileedit"><input type="text" /></span></div>');
    folderTag.append('<div class="file-info"><span class="file-size"></span><span class="file-time"></span></div>');
    folderTag.find(".fileedit").click(function(e){e.stopPropagation()});
    folderTag.find(".fileedit input").blur(confirm);
    folderTag.find(".fileedit input").keydown(confirmByKeyborad);
    

    /* 需要获取的数据 */
    var id;





    folderTag.prependTo(currentFolder);
    folderTag.find(".fileedit input").focus();


    function confirm() {
        var itemTag = $(this).parent().parent().parent();
        var nameTag = itemTag.find(".file-name");
        var timeTag = itemTag.find(".file-time");

        /* 需要提交的数据 */
        var localName = $(this).val();
        var parent = itemTag.parent().attr("data-folder-id");
        var newFolder = {
            localName: localName,
            parent: parent
        }

        if (localName.length == 0) {
            // TO DO 判断是否全为空格
            alert("名字不能为空");
            itemTag.remove();
        } else if (checkDuplicateName(itemTag, localName)) {
            alert("文件名产生冲突");
            itemTag.remove();
        } else {
            $.ajax({
                type: "POST",
                url: "RequestManageServlet?action=addFolder",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(newFolder),
                success: function(result) {
                    if (result.isSuccess == true) {
                        itemTag.attr("data-folder-id", result.id);
                        timeTag.text(getFormattedDateTime(result.ldtModified));
                        nameTag.text(newFolder.localName);
                    } else {    
                        alert("新建文件夹失败");
                    }
                }
            })
        }
        /* 移除文件名编辑框 */
        $(this).parent().remove();

        nameTag.parent().show();
    }
    
    function confirmByKeyborad(event) {
    	if (event.keyCode == 13) {
    		$(this).blur();
    	}
    }
}

/*******************************************获取回收站文件*******************************************/
function getRecycleItems(){
    
}