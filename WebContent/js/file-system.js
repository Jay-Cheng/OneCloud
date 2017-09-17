$(function() {
    $("#all").on("click", "ul [data-folder-id]", enterFolder);
    $("#all").on("click", ".disk-file-path-wrapper .disk-file-path li", goBack);
    $("#mkfolder").click(mkfolder);

    /* �ļ�ѡ������¼����� */
    $("body").click(resetSelectedState);

    $("#select_all").click(selectAll);// Ϊȫѡ���¼�������
    $("#all").on("click", "ul .disk-file-item .select", selectItem);
    $("#all").on("contextmenu", "ul .disk-file-item", rightClickSelectItem);

    $("#recycle").on("click", "ul .disk-file-item .select", selectItem);
    $("#recycle").on("contextmenu", "ul .disk-file-item", rightClickSelectItem);
    getRecycleItems();
    //$("#disk_file_path li").click(goBack);// Ϊ��ʼ���м�ڵ���¼�������
    showFolderContents(1);// Լ����ʼ�ļ���ID=1
});

function showFolderContents(folderID) {
    /* ���ص�ǰ�ļ������� */
    $("#all ul").hide();
    /* �����չʾ���ļ���֮ǰ�Ѿ����ɹ�������ʾ��Ӧ�ڵ㣬���� */
    var node = getFolderNode(folderID);
    if (node != null) {
        node.show();
        return;
    } else {
        createFolderNode(folderID, true);
    }
}
/*
 * ��������������������(1)��������Ҫ��ʾ���ļ��нڵ㲻����ʱ(2)��ѡ��·����ģ̬����ļ��нڵ㲻����ʱ
 * �����������ļ��нڵ㣨�ں��ļ��ڵ㣩��ͬʱ�����ɡ�ѡ��·����ģ̬����ļ��нڵ㣨�����ļ��ڵ㣩
 * ���show=true�����������������ʾ���ɵ��ļ��нڵ㣬����ֻ���ɲ���ʾ
 * ---------------------------------------------------------------------------------------------------
 * ע�������ajax success�¼��������з���һ��ֵ�������������ã�����ֻ��undefined���������Ϊ�������첽�ģ�
 * ����ͬ������async=false������(1)deprecated(2)����
 * ---------------------------------------------------------------------------------------------------
 * ԭ��˼·�����ɽڵ��ʼ��hide�����ڵ㷵�ص����õ���һ��������һ�������Ƿ�Ҫ��ʾ�ýڵ�
 * �����������첽���ᵼ����һ�����"undefined"�����
 */
function createFolderNode(folderID, show) {
    /* ����һ���ļ��нڵ�<ul>������data-folder-id���ԣ��������Ǹ��ļ��������ݿ�洢��ID */
    var node = $("<ul></ul>");
    node.attr("data-folder-id", folderID);
    /* ������������첽���󣬻�ȡ��ӦfolderID������ */
    $.ajax({
        type: "GET",
        url: "RequestManageServlet?action=enterFolder&userID=" + sessionStorage.getItem("user_id") + "&folderID=" + folderID,
        /* 
         * ��success�Ļص������з��ʲ����ⲿ��folderID�������������Ĳ��� 
         * ���������ʲ��������Ĳ����������Է��ʺ����ڲ��ı�����
         */  
        folderID: folderID,
        success: function(result) {
            var folders = result.folders;
            var files = result.files;
            /* ���ɡ�ѡ��·����ģ̬����ļ��нڵ� */
            createDirNode(this.folderID, folders, !show);/* �����ڲ���ǰ�����this. */
            /* ���ɵ�ǰ�ļ����������ļ��нڵ� */
            for (var i = 0; i < folders.length; i++) {
                var folderID = folders[i].id;
                var folderName = folders[i].localName;
                var lastModifiedTime = getFormattedDateTime(folders[i].ldtModified);
                var folderNode = $('<li class="disk-file-item disk-item"></li>');
                folderNode.append('<div class="file-head"><div class="select"><input type="checkbox"></div><div class="thumb"><img src="img/icon/folder.png" class="thumb-icon"></div><div class="file-title"><span class="file-name">' + folderName + '</span></div></div>');
                folderNode.append('<div class="file-info"><span class="file-size"></span><span class="file-time">' + lastModifiedTime + '</span></div>');
                /* �����ļ���ID */
                folderNode.attr("data-folder-id", folderID);
                /* ���¼������� */
                // folderNode.on("click", enterFolder);
                // folderNode.on("contextmenu", rightClickSelectItem);
                // folderNode.find(".select").on("click", selectItem);
                /* ׷�Ӹýڵ㵽��ǰ�ļ��� */
                folderNode.appendTo(node);
            }
            // node.on("click", "[data-folder-id]", enterFolder);
            /* ���ɵ�ǰ�ļ����������ļ��ڵ� */
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
                /* ���¼������� */
                // fileNode.find(".select").on("click", selectItem);
                // fileNode.on("contextmenu", rightClickSelectItem);
                /* ׷�Ӹýڵ㵽��ǰ�ļ��� */
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
 * �����ļ��к󣬸������м�������г����ļ����µ������ļ�
 * �ú����󶨵��ļ���<li>�ڵ��click�¼���
 */
function enterFolder() {
    /* �жϸ��ļ��Ƿ����ļ��� */
    if ($(this).attr("data-folder-id") != undefined) {
        /* ����һ�����ļ��е����м�����ڵ� */
        var breadNode = $('<li></li>');
        /* �������м�ڵ������ */
        var folderName = $(this).find(".file-name").text();
        breadNode.append('<a href="javascript:void(0)">' + folderName + '</a>');
        /* �������м�ڵ��Ӧ���ļ���ID */
        breadNode.attr("data-folder-id", $(this).attr("data-folder-id"));

        //breadNode.on("click", goBack);
        /* �Ƴ�֮ǰ���м�ڵ��activeЧ�������ѵ�ǰ���м�ڵ�����Ϊactive */
        $("#disk_file_path li").children().removeClass("active");
        breadNode.children().addClass("active");
        /* ׷�Ӹ����м��ϵͳ·�� */
        breadNode.appendTo($("#disk_file_path"));
        /* ���õ�ǰ�ļ��������ļ���ѡ��״̬ */
        //$("#all ul:visible .selected").find(".select input").click();
        /* �г����ļ����µ������ļ� */ 
        showFolderContents($(this).attr("data-folder-id"));
    }    
}
/* ����ļ�ͷ����ѡ��ʱ���¼������� */
function selectItem(e) {
    /* �����ѡ���ʱ���Ҽ��˵������Զ����� */
    $(".bootstrapMenu").hide();
    var itemTag = $(this).parent().parent();
    if ($(this).children().prop("checked")) {
        itemTag.addClass("selected");
        if (itemTag.hasClass("disk-item")) {
            /* �ж��Ƿ�ȫѡ */
            if (itemTag.parent().children("li:not(.selected)").length == 0) {
                $("#select_all input").prop("checked", true);
            }
        }
    } else {
        itemTag.removeClass("selected");
        if (itemTag.hasClass("disk-item")) {
            /* ֻҪ��һ�����Ƴ���ѡ��Ч������ȡ��ȫѡ */
            $("#select_all input").prop("checked", false);
        }
    }

    e.stopPropagation();
}
/* ���ļ����ļ������һ�ʱ���¼������� */
function rightClickSelectItem() {
    if (!$(this).find(".select input").prop("checked")) {
        /* ȡ������ԭ�е�ѡ��Ч�� */
    	$(this).parent().find(".selected .select input").click();
    	/* ѡ�е�ǰ���ļ� */
        $(this).find(".select input").prop("checked", true);
        $(this).addClass("selected");
    }
}
/* ����ȫѡ��ť���¼������� */
function selectAll(e) {
    if ($(this).children().prop("checked")) {
        $("#all ul:visible li:not(.selected)").find(".select input").click();
    } else {
        $("#all ul:visible .selected").find(".select input").click();
    }
    e.stopPropagation();
}
/* ȡ������ѡ��״̬ */
function resetSelectedState() {
    $("#select_all input").prop("checked", false);
	$(".selected").find(".select input").click();
}
/*
 * �������м����ʱ�󶨵��¼�������
 */
function goBack() {
    $(this).nextAll().remove();// �ѱ��ڵ�֮���siblingȫ���Ƴ�
    $(this).children().addClass("active");// �ѵ�ǰ���������м�ڵ�����Ϊactive
    showFolderContents($(this).attr("data-folder-id"));// ��ʾ�����м�ڵ��Ӧ���ļ���
}
/*
 * �ж�folderID��Ӧ���ļ��нڵ��Ƿ����
 * ���򷵻ظýڵ�
 * ���򷵻�null
 */
function getFolderNode(folderID){
    var node = $('ul[data-folder-id="' + folderID + '"]');// ������ǩ + ����ѡ���� ����
    if (node.length != 0) {
        return node;
    } else {
        return null;
    }
}

/*******************************************�½��ļ���*******************************************/
function mkfolder() {
    var currentFolder = $("ul[data-folder-id]:visible");

    var folderTag = $('<li class="disk-file-item disk-item"></li>');
    folderTag.append('<div class="file-head"><div class="select"><input type="checkbox"></div><div class="thumb"><img src="img/icon/folder.png" class="thumb-icon"></div><div class="file-title" style="display: none;"><span class="file-name"></span></div><span class="fileedit"><input type="text" /></span></div>');
    folderTag.append('<div class="file-info"><span class="file-size"></span><span class="file-time"></span></div>');
    folderTag.find(".fileedit").click(function(e){e.stopPropagation()});
    folderTag.find(".fileedit input").blur(confirm);
    folderTag.find(".fileedit input").keydown(confirmByKeyborad);
    

    /* ��Ҫ��ȡ������ */
    var id;





    folderTag.prependTo(currentFolder);
    folderTag.find(".fileedit input").focus();


    function confirm() {
        var inputTag = $(this);
        var itemTag = inputTag.parent().parent().parent();
        var nameTag = itemTag.find(".file-name");
        var timeTag = itemTag.find(".file-time");

        /* ��Ҫ�ύ������ */
        var localName = inputTag.val();
        var parent = itemTag.parent().attr("data-folder-id");
        var newFolder = {
            localName: localName,
            parent: parent
        }

        if (localName.length == 0) {
            // TO DO �ж��Ƿ�ȫΪ�ո�
            alert("���ֲ���Ϊ��");
            itemTag.remove();
        } else if (checkDuplicateName(itemTag, localName)) {
            alert("�ļ���������ͻ");
            itemTag.remove();
        } else {
            $.ajax({
                type: "POST",
                url: "RequestManageServlet?action=addFolder",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(newFolder),
                success: function(result) {
                    if (result.isSuccess == true) {
                        /* ģ̬��ͬ�� */
                        var folder = [{
                            id: result.id,
                            localName: newFolder.localName
                        }];
                        createDirNode(parent, folder, false);
                        /* �½��ļ��нڵ� */
                        itemTag.attr("data-folder-id", result.id);
                        timeTag.text(getFormattedDateTime(result.ldtModified));
                        nameTag.text(newFolder.localName);
                    } else {    
                        alert("�½��ļ���ʧ��");
                    }
                    /* �Ƴ��ļ����༭�� */
                    inputTag.parent().remove();

                    nameTag.parent().show();
                }
            });
        }
    }
    
    function confirmByKeyborad(event) {
    	if (event.keyCode == 13) {
    		$(this).blur();
    	}
    }
}

/*******************************************��ȡ����վ�ļ�*******************************************/
function getRecycleItems(){
    $.ajax({
        type: "GET",
        url: "RequestManageServlet?action=enterFolder&userID=" + sessionStorage.getItem("user_id") + "&folderID=" + 3,
        success: function(result) {
            var node = $("#recycle_folder");

            var folders = result.folders;
            var files = result.files;

            for (var i = 0; i < folders.length; i++) {
                var folderID = folders[i].id;
                var folderName = folders[i].localName;
                var lastModifiedTime = getFormattedDateTime(folders[i].ldtModified);
                var folderNode = $('<li class="disk-file-item recycle-item"></li>');
                folderNode.append('<div class="file-head"><div class="select"><input type="checkbox"></div><div class="thumb"><img src="img/icon/folder.png" class="thumb-icon"></div><div class="file-title"><span class="file-name">' + folderName + '</span></div></div>');
                folderNode.append('<div class="file-info"><span class="file-size"></span><span class="file-time">' + "ʣ��7��" + '</span></div>');
                folderNode.attr("data-folder-id", folderID);
                folderNode.appendTo(node);
            }

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
                var fileNode = $('<li class="disk-file-item recycle-item"></li>');
                fileNode.append('<div class="file-head"><div class="select"><input type="checkbox"></div><div class="thumb"><img src="' + fileImg + '" class="thumb-icon"></div><div class="file-title"><span class="file-name">' + fileName + '</span></div></div>');
                fileNode.append('<div class="file-info"><span class="file-size">' + fileSize + '</span><span class="file-time">' + "ʣ��7��" + '</span></div>');
                fileNode.attr("data-file-id", fileID);
                
                fileNode.appendTo(node);                
            }
            node.appendTo($("#recycle"));
        }
    });
}