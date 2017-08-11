/* 实现文件上传功能 */
$(function() {
	$("#fileupload").fileupload({
		dataType: "json",
		maxChunkSize: 1024000,
		add: function(e, data) {
			var marker = this;
			updateLoadingCount(true);
			var node = $("<li></li>");
			node.append('<div class="mission-head"><div class="mission-icon-wrapper"><span class="glyphicon glyphicon-upload" style="color: #337ab7;"></span></div><div class="thumb"><img src="img/icon/file.png" class="thumb-icon"></div><div class="mission-info"><span class="mission-file-name">' + data.files[0].name + '</span><span class="mission-file-size">' + getReadableSize(data.files[0].size) + '</span></div></div>');
			node.append('<div class="progress mission-progress"><div class="progress-bar progress-bar-striped progress-bar-primary active" role="progressbar" style="width: 0%">0%</div></div>');
			node.append('<div class="mission-control"><a class="upload-pause"><span class="glyphicon glyphicon-pause"></span></a><a class="upload-cancel"><span class="glyphicon glyphicon-remove"></span></a></div>');
			data.context = node;

			/* 取消上传逻辑实现 */
			data.context.find(".upload-cancel").click(function() {
				updateLoadingCount(false);
                data.abort();
                $.ajax({
                	type: "GET",
                	url: "UploadServlet?delfile=" + data.files[0].name,
                	success: function(message){
                		data.context.remove();
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
					$.ajax({
                		type: "GET",
                		url: "UploadServlet?resfile=" + data.files[0].name,
                		success: function (result) {
                			data.uploadedBytes = Number(result);
                			$.blueimp.fileupload.prototype.options.add.call(marker, e, data);
                		}
					});
				}
			});


			node.appendTo("#loading_mission_list");
			data.submit();
		},

		progress: function(e, data) {
			var progress = parseInt(data.loaded / data.total * 100, 10);
			data.context.find(".progress-bar").css("width", progress + "%").text(progress + "%");
		},

		done: function(e, data) {
			updateLoadingCount(false);
			data.context.remove();
			updateCompleteCount(true);
			var node = $("<li></li>");
			node.append('<div class="mission-head"><div class="mission-icon-wrapper"><span class="glyphicon glyphicon-upload" style="color: #337ab7;"></span></div><div class="thumb"><img src="img/icon/file.png" class="thumb-icon"></div><div class="mission-info"><span class="mission-file-name">' + data.files[0].name + '</span><span class="mission-file-size">' + getReadableSize(data.files[0].size) + '</span></div></div>');
			node.append('<div class="mission-complete"></div>');
			node.append('<div class="mission-control"><a class="remove-record"><span class="glyphicon glyphicon-remove"></span></a></div>');
			data.context = node;

			data.context.find(".remove-record").click(function() {
                data.context.remove();
                updateCompleteCount(false);
			});

			node.appendTo("#complete_mission_list");
		}
	});
});
function getReadableSize(bytes) {
    var s = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB'];
    var e = Math.floor(Math.log(bytes)/Math.log(1024));
    return (bytes/Math.pow(1024, Math.floor(e))).toFixed(2)+" "+s[e];
}

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

function updateCompleteCount(plus) {
	var completeCount = $("#complete_mission_count");
	if (plus) {
		completeCount.text(parseInt(completeCount.text()) + 1);
	} else {
		completeCount.text(parseInt(completeCount.text()) - 1);
	}
}

/* temp function */
function printObj(obj) {
 	var description = ""; 
 	for(var i in obj){ 
 		var property = obj[i]; 
 		description += i + " = " + property + "\n"; 
 	} 
 	console.log(description);
}