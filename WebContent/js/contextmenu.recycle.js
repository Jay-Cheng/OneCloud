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
		    onClick: function(){
		        alert("永久删除");
		    }
		}
	}
});
