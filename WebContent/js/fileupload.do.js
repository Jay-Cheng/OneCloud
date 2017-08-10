/* 实现文件上传功能 */
$(function() {
	$("#fileupload").fileupload({
		dataType: "json",
		add: function(e, data) {
			var count = $(".loading_mission_count");
			if (count.first().text() == "") {
				count.text(1);
			} else {
				count.text(parseInt(count.first().text()) + 1); 
			}
			var node = $("<li></li>");
			node.append('<div class="mission-head"><div class="mission-icon-wrapper"><span class="glyphicon glyphicon-upload" style="color: #337ab7;"></span></div><div class="thumb"><img src="img/icon/file.png" class="thumb-icon"></div><div class="mission-info"><span class="mission-file-name">' + data.files[0].name + '</span><span class="mission-file-size">' + getReadableSize(data.files[0].size) + '</span></div></div>');
			node.append('<div class="progress mission-progress"><div class="progress-bar progress-bar-striped progress-bar-primary active" role="progressbar" style="width: 0%">0%</div></div>');
			node.append('<div class="mission-control"><a><span class="glyphicon glyphicon-pause"></span></a><a><span class="glyphicon glyphicon-remove"></span></a></div>');
			data.context = node;
			node.appendTo("#loading_mission_list");
			data.submit();
		},

		progress: function(e, data) {
			var progress = parseInt(data.loaded / data.total * 100, 10);
			data.context.find(".progress-bar").css("width", progress + "%").text(progress + "%");
		},

		done: function(e, data) {
			var loadingCount = $(".loading_mission_count");
			if (parseInt(loadingCount.first().text()) - 1 == 0) {
				loadingCount.empty();
			} else {
				loadingCount.text(parseInt(loadingCount.first().text()) - 1);
			}
			data.context.remove();
			var completeCount = $("#complete_mission_count");
			completeCount.text(parseInt(completeCount.text()) + 1);
			var node = $("<li></li>");
			node.append('<div class="mission-head"><div class="mission-icon-wrapper"><span class="glyphicon glyphicon-upload" style="color: #337ab7;"></span></div><div class="thumb"><img src="img/icon/file.png" class="thumb-icon"></div><div class="mission-info"><span class="mission-file-name">' + data.files[0].name + '</span><span class="mission-file-size">' + getReadableSize(data.files[0].size) + '</span></div></div>');
			node.append('<div class="mission-complete"></div>');
			node.append('<div class="mission-control"><a><span class="glyphicon glyphicon-remove"></span></a></div>');
			node.appendTo("#complete_mission_list");
		}
	});
});
function getReadableSize(bytes) {
    var s = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB'];
    var e = Math.floor(Math.log(bytes)/Math.log(1024));
    return (bytes/Math.pow(1024, Math.floor(e))).toFixed(2)+" "+s[e];
}