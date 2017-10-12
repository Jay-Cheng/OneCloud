$(function(){
	$("#clear_recycle").click(function(){
		$("#recycle_folder .recycle-item").addClass("selected");
		shred();
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

	var dataArr = new Array();
	selectedItem.each(function(index) {
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
		dataArr[index] = {
		    id: id,
		    type: type
		};
	});


	$.ajax({
	    type: "POST",
	    url: "RequestManageServlet?action=shred",
	    contentType: "application/json;charset=utf-8",
	    data: JSON.stringify({shred:dataArr}),
	    success: function(result) {
	    	var resultArr = result.resultArr;
	    	for (var i = 0; i < resultArr.length; i++) {
	    		var id = resultArr[i].id;
	    		if (resultArr[i].status == 1) {
	    			if (resultArr[i].type == "folder") {
	    				$('.treeNode-info[data-folder-id="' + id + '"]' ).parent().remove();
	    				$('#recycle_folder .recycle-item[data-folder-id="' + id + '"]').remove();
	    			} else if (resultArr[i].type == "file") {
	    				$('#recycle_folder .recycle-item[data-file-id="' + id + '"]').remove();
	    			} else {
	    				alert("未知错误");
	    			}
	    		} else {
	    			alert("删除失败！");
	    		}
	    	}

	    	/* 更新用户存储空间 */
	    	var cap = result.cap;
	    	localStorage.setItem("user_usedCapacity", cap);
	    	var percentage = getUsedPercentage(cap);
	    	$("#user_capacity").css("width", percentage).text(percentage);
	    }
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