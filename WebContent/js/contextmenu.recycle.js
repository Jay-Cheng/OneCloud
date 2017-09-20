$(function(){
	$("#clear_recycle").click(function(){
		alert("全部清空");
	});
});
var recycle_menu = new BootstrapMenu(".recycle-item", {
	actionsGroups: [
	    ["restore", "shred"]
	],
	actions: {
		restore: {
		    name: "还原",
		    iconClass: "fa-reply",
		    classNames: "right-click-menu",
		    onClick: restore
		},
		shred: {
		    name: "永久删除",
		    iconClass: "fa-minus-square",
		    classNames: "right-click-menu",
		    onClick: shred
		}
	}
});

function shred() {
	var selectedItem = getSelectedItems();
	selectedItem.each(function() {
		var itemTag = $(this);
		/* 需要提交的数据 */
		var id;
		var type;
		if (itemTag.attr("data-folder-id") != undefined) {
		    id = itemTag.attr("data-folder-id");
		    type = "folder";
		} else {
		    id = itemTag.attr("data-file-id");
		    type = "file";
		}
		var shredData = {
		    id: id,
		    type: type
		};

		$.ajax({
		    type: "POST",
		    url: "RequestManageServlet?action=shred",
		    contentType: "application/json;charset=utf-8",
		    data: JSON.stringify(shredData),
		    success: function(result){
		    	if (result.status == 1) {
		    		if (type == "folder") {
		    			$('.treeNode-info[data-folder-id="' + id + '"]' ).parent().remove();
		    		}
		    		/* 更新用户容量 */
		    		var cap = result.cap;
		    		sessionStorage.setItem("user_usedCapacity", cap);
		    		var percentage = getUsedPercentage(cap);
		    		$("#user_capacity").css("width", percentage).text(percentage);

		    		itemTag.remove();
		    	} else {
		    		alert("删除失败！");
		    	}
		    }
		});
	});
}

function restore() {
	var selectedItem = getSelectedItems();
	selectedItem.each(function() {
		var itemTag = $(this);
		/* 需要提交的数据 */
		var id;
		var type;
		if (itemTag.attr("data-folder-id") != undefined) {
		    id = itemTag.attr("data-folder-id");
		    type = "folder";
		} else {
		    id = itemTag.attr("data-file-id");
		    type = "file";
		}
		var restoreData = {
		    id: id,
		    type: type,
		    moveTo: 1
		};

		$.ajax({
		    type: "POST",
		    url: "RequestManageServlet?action=move",
		    contentType: "application/json;charset=utf-8",
		    data: JSON.stringify(restoreData),
		    success: function(result){
		    	if (result.status == 1) {
		    		if (type == "folder") {
		    			$('.treeNode-info[data-folder-id="' + id + '"]' ).parent().show();
		    		}
		    		
		    		itemTag.removeClass("recycle-item");
		    		itemTag.addClass("disk-item");
		    		itemTag.find(".file-time").text(getFormattedDateTime(result.ldtModified));
		    		itemTag.appendTo($('ul[data-folder-id="1"]'));
		    	} else {
		    		alert("还原失败！");
		    	}
		    }
		});
	});
}