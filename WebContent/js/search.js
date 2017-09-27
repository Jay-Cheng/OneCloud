$(function() {
	$("#btn_search").click(function() {
		$('[role="presentation"][class="active"]').removeClass("active");

		var input = $('#search_control input').val();
		if (input.length == 0) {
			$("#search .fixed-title span span").text("0");
			$("#search ul").remove();
			return;
		}
		$.ajax({
			type: "GET",
			url: "RequestManageServlet?action=search&input=" + input,
			success: function(result){
				var folders = result.folders;
				var files = result.files;

				$("#search .fixed-title span span").text(folders.length + files.length);
				var node = $("<ul></ul>");
				generateFolderNode(folders, node);
				generateFileNode(files, node);
				node.find(".disk-file-item").removeClass("disk-item");
				node.find(".file-name").each(function(){
					var filename = $(this).text();
					$(this).html(filename.replace(input, '<span class="searched-word">'+ input + '</span>'));
				});
				$("#search ul").remove();
				node.appendTo($("#search"));
			}
		});

	});
});