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
    				classNames: "right-click-menu",
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
    				onClick: function() {
    					alert("重命名");
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