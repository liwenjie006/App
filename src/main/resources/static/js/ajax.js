/*<![CDATA[*/
var ajax = {
	/* 로딩바 오픈 */
	openProgresBarWindow: function () {
		$("body").mLoading({ text: loadingMsg });
	},
	
	/* 로딩바 닫기 */
	closeProgresBarWindow: function () {
		$("body").mLoading("hide");
	},

	/**
	 * post
	 */
	post: function (url, data, fun, useProgres) {
		if (false != useProgres) {
			ajax.openProgresBarWindow();
		}
		
		$.ajax({
			url: url,
			data: JSON.stringify(data),
			contentType: "application/json;charset=UTF-8",
			dataType: "json",
			type: "POST",
            beforeSend: function(xhr) {
				// 使用Spring security csrf时POST请求需要在头部加上csrf
				xhr.setRequestHeader($("meta[name='_csrf_header']").attr("content"), $("meta[name='_csrf_token']").attr("content"));
		    },
		    success: function(result) {
		    	console.log(result);

		    	if ("timeout" == result) { // 超时处理
                    util.alert(systemAlterTitle, systemAlterTimeoutMsg, systemAlterButton, function () {
                        window.top.location.href = "/";
					});
				} else if ("not permissions" == result) { // 权限不足
                    util.alert(systemAlterTitle, systemAlterNoPermissionsMsg, systemAlterButton, function () {
                        window.top.location.href = "/";
                    });
				} else {
                    fun(result);
				}
		    },
		    error: function(XMLHttpRequest, textStatus, errorThrown) {
		    	console.log(XMLHttpRequest);
		    	console.log(textStatus);
		    	console.log(errorThrown);
		    },
		    complete: function (XMLHttpRequest, textStatus) {
		    	if (false != useProgres) {
					ajax.closeProgresBarWindow();
				}
		    }
		});
	},
	
	/**
	 * jquery form
	 */
	ajaxSubmit: function (id, fun, useProgres) {
		if (false != useProgres) {
			ajax.openProgresBarWindow();
		}
		
		$("#" + id).ajaxSubmit(function (data) {
			if (false != useProgres) {
				ajax.closeProgresBarWindow();
			}
			
			fun(data);
		});
	}
	
}
/*]]>*/