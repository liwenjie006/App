/*<![CDATA[*/
var util = {
	/* 图片选择并显示 */
	setPhoto: function (file, img) {
		if (!file.files || !file.files[0]) {
            return;
        }
		
		var reader = new FileReader();
        reader.onload = function (evt) {
        	$("#" + img).attr("src", evt.target.result);
        }
        reader.readAsDataURL(file.files[0]);
	}
}
/*]]>*/