$(function() {
    $("#user_photo").attr("src", sessionStorage.getItem("user_photoURL"));
    $("#user_nickname").text(sessionStorage.getItem("user_nickname"));
    var percentage = getUsedPercentage(sessionStorage.getItem("user_usedCapacity"));
    $("#user_capacity").css("width", percentage).text(percentage);
});

function getUsedPercentage(bytes) {
	var percentage = bytes / 10737418240 * 100;
	return percentage + "%";
}