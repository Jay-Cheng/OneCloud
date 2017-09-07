/* 实现文件上传功能 */
$(function() {
	$("#fileupload").fileupload({
		dataType: "json",
		maxChunkSize: 1024000,
		add: function(e, data) {
			$("#modal_btn_submit").click(addFile.bind(null, e, data, this));/* 调用带参函数的正确写法 */
		},

		progress: function(e, data) {
			var progress = parseInt(data.loaded / data.total * 100, 10);
			data.context.find(".progress-bar").css("width", progress + "%").text(progress + "%");
		},

		done: function(e, data) {
			/* 更新“进行中”区域 */
			data.context.remove();
			updateLoadingCount(false);
            if ($(".loading_mission_count").first().text() == "") {
                $("#dynamic_title_loading").css("display", "none");
            }
            /* 更新“已完成”区域 */
			updateCompleteCount(true);
			$("#dynamic_title_complete").css("display", "block");
			var node = $("<li></li>");
			node.append('<div class="mission-head"><div class="mission-icon-wrapper"><span class="glyphicon glyphicon-upload" style="color: #337ab7;"></span></div><div class="thumb"><img src="img/icon/file.png" class="thumb-icon"></div><div class="mission-info"><span class="mission-file-name">' + data.files[0].name + '</span><span class="mission-file-size">' + getReadableSize(data.files[0].size) + '</span></div></div>');
			node.append('<div class="mission-complete"></div>');
			node.append('<div class="mission-control"><a class="remove-record"><span class="glyphicon glyphicon-remove"></span></a></div>');
			data.context = node;
			/* 移除记录按钮功能实现 */
			data.context.find(".remove-record").click(function() {
                data.context.remove();
                updateCompleteCount(false);
                if ($("#complete_mission_count").text() == "0") {
                	$("#dynamic_title_complete").css("display", "none");
                }
			});

			node.appendTo("#complete_mission_list");
		}
	});
	/* 取消全部按钮功能实现 */
	$("#cancel_all_mission").click(function() {
		$("#loading_mission_list").find(".upload-cancel").click();
	}); 
	/* 清空记录按钮功能实现 */
	$("#remove_all_record").click(function() {
		$("#complete_mission_list").find(".remove-record").click();
	}); 
});
/* 把字节数转为易读的单位 */
function getReadableSize(bytes) {
    var s = ['Bytes', 'K', 'M', 'G', 'T', 'P'];
    var e = Math.floor(Math.log(bytes)/Math.log(1024));
    return (bytes/Math.pow(1024, Math.floor(e))).toFixed(1)+" "+s[e];
}
/* 更新进行中任务的计数 */
function updateLoadingCount(plus) {
	var loadingCount = $(".loading_mission_count");
	if (plus) {
		if (loadingCount.first().text() == "") {
			loadingCount.text(1);
		} else {
			loadingCount.text(parseInt(loadingCount.first().text()) + 1); 
		}
	} else {
		if (parseInt(loadingCount.first().text()) - 1 == 0) {
			loadingCount.empty();
		} else {
			loadingCount.text(parseInt(loadingCount.first().text()) - 1);
		}	
	}
}
/* 更新已完成任务的计数 */
function updateCompleteCount(plus) {
	var completeCount = $("#complete_mission_count");
	if (plus) {
		completeCount.text(parseInt(completeCount.text()) + 1);
	} else {
		completeCount.text(parseInt(completeCount.text()) - 1);
	}
}
/* 
 * 绑定在模态框上传按钮click上的事件处理函数
 */
function addFile(e, data, marker) {
			var node = $("<li></li>");
			node.append('<div class="mission-head"><div class="mission-icon-wrapper"><span class="glyphicon glyphicon-upload" style="color: #337ab7;"></span></div><div class="thumb"><img src="img/icon/file.png" class="thumb-icon"></div><div class="mission-info"><span class="mission-file-name">' + data.files[0].name + '</span><span class="mission-file-size">' + getReadableSize(data.files[0].size) + '</span></div></div>');
			node.append('<div class="progress mission-progress"><div class="progress-bar progress-bar-striped progress-bar-primary active" role="progressbar" style="width: 0%">0%</div></div>');
			node.append('<div class="mission-control"><a class="upload-pause"><span class="glyphicon glyphicon-pause"></span></a><a class="upload-cancel"><span class="glyphicon glyphicon-remove"></span></a></div>');
			data.context = node;
			updateLoadingCount(true);
			$("#dynamic_title_loading").css("display", "block");

			/* 取消上传逻辑实现 */
			data.context.find(".upload-cancel").click(function() {
                data.abort();
                $.ajax({
                	type: "GET",
                	url: "UploadServlet?delfile=" + data.files[0].name,
                	success: function(message){
                		data.context.remove();
                		updateLoadingCount(false);
                		/* 如果没有正在进行的任务，则隐藏标题栏 */
                		if ($(".loading_mission_count").first().text() == "") {
                			$("#dynamic_title_loading").css("display", "none");
                		}
                	}
                });
			});

			/* 断点续传逻辑实现 */
			data.context.find(".upload-pause").click(function() {
				var icon = $(this).children();
				if (icon.hasClass("glyphicon-pause")) {
					/* 暂停逻辑 */
					icon.removeClass("glyphicon-pause");
					icon.addClass("glyphicon-play");
					data.abort();
				} else {
					/* 继续逻辑 */
					icon.removeClass("glyphicon-play");
					icon.addClass("glyphicon-pause");
					/* 获取已经上传的字节数，从断点继续 */
					$.ajax({
                		type: "GET",
                		url: "UploadServlet?resfile=" + data.files[0].name,
                		success: function (result) {
                			/* 断点续传的核心代码 */
                			data.uploadedBytes = Number(result);// 获取断点
                			$.blueimp.fileupload.prototype.options.add.call(marker, e, data);// 续传
                		}
					});
				}
			});

			node.appendTo("#loading_mission_list");
			data.submit();
}
/*
 * 显示文件上传模态框
 */
function showFileUploadModal(firstFileName, nums) {
	/* 显示要上传的文件信息和数量 */
	$("#modal_file_thumb").attr("src", getFileIcon(firstFileName));
	$("#modal_file_name").text(firstFileName);
	if (nums == 1) {
		$("#modal_addtional_info").hide();
	} else {
		$("#modal_addtional_info span").text(nums);
		$("#modal_addtional_info").show();
	}

	$("#file_upload_modal").modal("show");
	$("#modal_btn_cancel").click(removeAddedMission);
	$("#modal_btn_close").click(removeAddedMission);
	$("#modal_btn_submit").click(function() {
		$("#file_upload_modal").modal("hide");
		/* 上传按钮单击后移除所有绑定的事件 */
		$("#modal_btn_submit").off("click");
	});

}
/*
 * 模态框取消按钮和x按钮绑定的事件处理函数
 */ 
function removeAddedMission() {
	/* 移除所有可操作按钮绑定的事件 */
	$("#modal_btn_submit").off("click");
	$("#modal_btn_cancel").off("click");
	$("#modal_btn_close").off("click");
}




/*
 * 工具函数
 * 获取文件对应的Icon
 */
function getFileIcon(filename) {
	var type = "";
	var pos = filename.lastIndexOf(".");
	if (pos > 0 && pos < filename.length - 1) {
		type = filename.substring(pos + 1);
	}
	var src = "img/icon/";
	switch(type) {
    	case "mp4": 
        src += "video";
        break;
        case "jpg":
        case "png": 
        src += "picture";
        break;
        default: 
        src += "file";
	}
	src += ".png";
	return src;
}