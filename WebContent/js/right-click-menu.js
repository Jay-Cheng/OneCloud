var menu = new BootstrapMenu(".disk-file-item, .picture", {
	actionsGroups: [
		["download", "moveTo", "rename"],
		["moveToSafe"],
		["remove"]
	],
	actions: {
		share: {
			name: "分享",
			iconClass: "fa-share-alt",
			classNames: "right-click-menu",// 自定义的样式
			onClick: function() {
				alert("分享");
			}

		},
		download: {
			name: "下载",
			iconClass: "fa-cloud-download",
			classNames: "right-click-menu",
			onClick: function() {
				alert("下载");
			}
		},
		moveTo: {
			name: "移动到",
			classNames: "right-click-menu",
			onClick: function() {
				alert("移动到");
			},
            isShown: function() {
                return true;
            }
		},
		rename: {
			name: "重命名",
			classNames: "right-click-menu",
			onClick: rename,
            isShown: function() {
                var isMultiple = getSelectedItems().length != 1;
                if (isMultiple) {
                    return false;
                } else {
                    return true;
                }
            }
		},
		moveToSafe: {
			name: "移至保险箱",
			iconClass: "fa-shield",
			classNames: "right-click-menu",
			onClick: function() {
				alert("移至保险箱");
			}
		},
		remove: {
			name: "删除",
			iconClass: "fa-trash",
			classNames: "right-click-menu",
			onClick: function() {
				alert("删除");
			}
		}
	}
});
/* 获取被选中的节点 */
function getSelectedItems() {
    // TODO get selected items from other navs 
    var selectedItem = $("#all ul:visible .selected");
    return selectedItem;
}

function rename() {
    var selectedItem = getSelectedItems();
    if (selectedItem.find(".fileedit").length == 0) {// 防止插入多个文件名编辑框
        var originalNameTag = selectedItem.find(".file-name");
        originalNameTag.parent().hide();// hide .file-title
        /* 插入一个文件名编辑框 */
        var fileedit = $('<span class="fileedit"><input type="text" /></span>');
        fileedit.find("input").val(originalNameTag.text());
        fileedit.find("input").blur(confirmEditName);// 输入框失去焦点时即确认
        fileedit.find("input").keydown(confirmEditNameByKeyboard);// 按下回车键即确认
        fileedit.click(function(e){e.stopPropagation()});// 阻止冒泡，不触发上一级的单击事件

        fileedit.appendTo(selectedItem.find(".file-head"));
        fileedit.find("input").focus();
    } else {// 如果文件名编辑框已存在，focus
        selectedItem.find(".fileedit input").focus();
    }
}
function confirmEditNameByKeyboard(event) {
    if (event.keyCode == 13) {
        $(this).blur();
    }
}
function confirmEditName() {
    var itemTag = $(this).parent().parent().parent();
    var nameTag = $(this).parent().prev().find(".file-name");
    var originalName = nameTag.text();
    var newName = $(this).val();


    /* 需要交给服务器的数据 */
    var id;
    var localName;
    var localType = null;
    if (itemTag.attr("data-folder-id") != undefined) {
        id = itemTag.attr("data-folder-id");
        localName = newName;
    } else {
        id = itemTag.attr("data-file-id");
        localName = getFilenameWithoutSuffix(newName);
        localType = getSuffix(newName);
    }
    var renameData = {
        id: id,
        localName: localName,
        localType: localType
    };


    if (newName.length == 0) {
        // TO DO 判断是否全为空格
        alert("名字不能为空");
    } else if (originalName == newName) {
        // Do nothing
    } else if (checkDuplicateName(itemTag, newName)) {
        alert("文件名产生冲突");
    } else if (originalName != newName) {
        $.ajax({
            type: "POST",
            url: "RequestManageServlet?action=rename",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(renameData),
            success: function(result) {
                if (result.isSuccess == true) {
                    nameTag.text(newName);
                    itemTag.find(".file-time").text(getFormattedDateTime(result.ldt_modified));
                    if (localType != null) {
                        var src = getFileIcon(localType);
                        itemTag.find(".thumb img").attr("src", src);
                    }
                } else {
                    alert("重命名失败！");
                }
            }
        });
    }
    /* 移除文件名编辑框 */
    $(this).parent().remove();

    nameTag.parent().show();
}
/*
 * 检查修改后的文件名是否与当前文件夹下的其它文件名产生冲突
 * return true if 产生冲突；eles return false；
 */
function checkDuplicateName(itemTag, newName) {
    var siblingTags = itemTag.siblings();
    var isDuplicate = false;
    siblingTags.each(function() {
        if ($(this).find(".file-name").text() == newName) {
            isDuplicate = true;
        }
    });
    return isDuplicate;
}