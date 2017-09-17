var recycle_menu = new BootstrapMenu(".recycle-item", {
	actionsGroups: [
	    ["restore", "shred"]
	],
	actions: {
		restore: {
		    name: "还原",
		    iconClass: "fa-reply",
		    classNames: "right-click-menu",
		    onClick: function(){
		        alert("还原");
		    }
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
		    	if (result.isSuccess == true) {
		    		if (type == "folder") {
		    			$('.treeNode-info[data-folder-id="' + id + '"]' ).parent().remove();
		    		}
		    		itemTag.remove();
		    	} else {
		    		alert("删除失败！");
		    	}
		    }
		});
	});
}
