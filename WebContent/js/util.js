/* 计算已用空间所占百分比 */
function getUsedPercentage(bytes) {
    var percentage = bytes / 10737418240 * 100;
    return percentage + "%";
}
/* 把字节数转为易读的单位 */
function getReadableSize(bytes) {
    var s = ['Bytes', 'K', 'M', 'G', 'T', 'P'];
    var e = Math.floor(Math.log(bytes)/Math.log(1024));
    return (bytes/Math.pow(1024, Math.floor(e))).toFixed(1)+" "+s[e];
}

function getFilenameWithoutSuffix(filename) {
	var pos = filename.lastIndexOf(".");
	if (pos > 0 && pos < filename.length - 1) {
		return filename.substring(0, pos);
	}
	return filename;
}

function getSuffix(filename) {
	var suffix = "";
	var pos = filename.lastIndexOf(".");
	if (pos > 0 && pos < filename.length - 1) {
		suffix = filename.substring(pos + 1);
	}
	return suffix;
}

function getFileIcon(suffix) {
	var src = "img/icon/";
	switch(suffix) {
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

function getFormattedDateTime(date) {
	function addZero(num) {
		if (num < 10) {
			num = "0" + num;
		}
		return num;
	}

	var dateObj = new Date(date);

	var year = dateObj.getFullYear();
	var month = addZero(dateObj.getMonth());
	var day = addZero(dateObj.getDate());
	var hour = addZero(dateObj.getHours());
	var minute = addZero(dateObj.getMinutes());

	var formattedString = year+"-"+month+"-"+day+" "+hour+":"+minute; 
	return formattedString;
}

/*
 * 测试函数
 * 打印js对象
 */
function printObj(obj){
    var output = "";
    for(var i in obj){  
        var property = obj[i];  
        output += i + " = "+ property + "\n" ; 
    }  
    alert(output);
}