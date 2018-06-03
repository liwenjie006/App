/*<![CDATA[*/
var util = {
		/* 自定义alert */
		alert: function (title, msg, buttonLabel, fun) {
			new ax5.ui.dialog().alert({
	            title: title,
	            msg: msg,
	            width: 200,
	            lang: {
	            	ok: buttonLabel
	            }
	        }, fun);
		},
		
		/* 自定义confirm */
		confirm: function (title, msg, btnCancelLabel, btnOKLabel, okFun, cancelFun) {
			new ax5.ui.dialog().setConfig({
	            title: title,
	            msg: msg,
	            width: 200,
	            lang: {
	            	ok: btnOKLabel,
	            	cancel: btnCancelLabel
	            }
	        }, function () {
	        	if(this.key == "ok") {
	        		if (null != okFun && undefined != okFun) {
	        			okFun();
			        }
                } else if (this.key == "cancel") {
                	if (null != cancelFun && undefined != cancelFun) {
                		cancelFun();
			        }
                }
	        });
		},
		
		/* 设置窗口宽度，高度 */
		setModelWH: function (h) {
			if (null == h) {
				h = 0;
			}
			var input_height = 0;
			if ($(".input2").length > 0) {
				input_height = $(".input2").height();
			}
			if ($(".input3").length > 0) {
				input_height = $(".input3").height();
			}
			
			this.getModel().setModalConfig({
				width: window.screen.availWidth / 2,
				height: input_height + $(".input_buttons").height() + 60 + h
			});
		},
		
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