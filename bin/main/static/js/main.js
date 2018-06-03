/* 设定界面高度 */
var frameHeight = $(window).height() - 130;

function settingTabsWidth() {
	var totalWidth = 0;
	$.each($(".nav-tabs li"), function(key, item) {
		totalWidth += $(item).width();
	});
	if (totalWidth < 300) {
		totalWidth = 300;
	}
	$(".nav-tabs").width(totalWidth + 5);
}

function settingDocumentSize() {
	$("#leftMenu").css("height", $(window).height() - 50);
	$("#content").css("height", $(window).height() - 50);

	/* 设置tabs 宽度 */
	var content_width = parseInt($(".panel-body").css("width").replace('px', ''));
	var leftbackward_width = parseInt($(".leftbackward").css("width").replace('px', ''));
	$(".middletab").css("width", content_width - leftbackward_width * 2);
	
	frameHeight = $(window).height() - 130;
	
	settingTabsWidth();
}

settingDocumentSize();

$(window).resize(function() {
	settingDocumentSize();
});

var mask = new ax5.ui.mask();
var modal = null;

/* 从服务器取得菜单信息并动态绘制菜单 */
ajax.post("/mainMenu", null, settingMenu, false);

function settingMenu(data) {
	var ul = $("#menu-list");
	var li, subUl, nextData, nowData;

	for (var i = 0, len = data.length; i < len; i++) {
		if (i+1 == len) {
			nowData = data[i];

			li = $('<li><a href="javascript: addTab(' +
					'\'' + nowData.menuCd + '\',' +
					'\'' + nowData.menuNm + '\',' +
					'\'' + nowData.actions[0].actionUrl + '\');">' + nowData.menuNm + '</a></li>');
			
			if (nowData.mlevel == 1) {
				ul.append(li);
			} else {
				subUl.append(li);
			}
		} else {
			nowData = data[i];
			nextData = data[i+1];
			
			if (nextData.mlevel == nowData.mlevel) {
				li = $('<li><a href="javascript: addTab(' +
						'\'' + nowData.menuCd + '\',' +
						'\'' + nowData.menuNm + '\',' +
						'\'' + nowData.actions[0].actionUrl + '\');">' + nowData.menuNm + '</a></li>');
			} else if (nextData.mlevel > nowData.mlevel) {
				li = $('<li><a href="#">' + nowData.menuNm + '</a></li>');
				subUl = $('<ul class="submenu"></ul>');
				li.append(subUl);
			} else {
				li = $('<li><a href="javascript: addTab(' +
						'\'' + nowData.menuCd + '\',' +
						'\'' + nowData.menuNm + '\',' +
						'\'' + nowData.actions[0].actionUrl + '\');">' + nowData.menuNm + '</a></li>');
			}
			
			if (nowData.mlevel == 1) {
				ul.append(li);
			} else {
				subUl.append(li);
			}
		}
	}
	
	/* 初始化菜单控件 */
	$("#leftMenu").jqueryAccordionMenu();
}


/* 通过菜单添加一个Tab */
function addTab(cd, name, url) {
	// 取得所有Tab
	var lis = $("#tab_title").find("li");
	var divs = $("#tab_contents").find("div");
	var len = lis.length;
	var li, div;
	// 判断是否已加载过菜单
	for (var i = 0; i < len; i++) {
		if ("tab_li_" + cd === lis[i].id) {
			li = $(lis[i]);
			div = $(divs[i]);
			break;
		}
	}
	// 删除活动状态的Tab
	$("#tab_title").find("li").removeClass("active");
	$("#tab_contents").find("div").removeClass("in active");
	
	// 如果未加载菜单则新加载
	if (null == li) {
		li = $('<li id="tab_li_' + cd + '" class="active">' +
				'<a href="#tab_div_' + cd + '" data-toggle="tab" aria-expanded="false">' +
					'<i class="glyphicon glyphicon-home tab_icon"/>' + name +
					'<i class="glyphicon glyphicon-remove-sign tab_x" onClick="javascript: delTab(\'' + cd + '\');" />' +
				'</a>' +
			'</li>');
		
		div = $('<div id="tab_div_' + cd + '" class="tab-pane fade in active" style="overflow: hidden; height: ' + frameHeight + '; padding: 9px 15px 15px 15px;">' +
				'<iframe marginwidth="0" width="100%" marginheight="0" scrolling="no" allowtransparency="yes" frameborder="no" border="0" ' +
				'height="' + frameHeight + '" name="' + cd + '" src="' + url + '"></iframe>' + 
			'</div>');
		$("#tab_title").append(li);
		$("#tab_contents").append(div);
	} else {
		li.addClass("active");
		div.addClass("in active");
	}
	
	settingTabsWidth();
	
	setCurrentTabs(li);
}

/* 删除一个Tab */
function delTab(cd) {
	// 如果移除的是当前界面则选择其他界面表示
	if ($("#tab_li_" + cd + "[class=active]").length > 0) {
		var li = $("#tab_li_" + cd).prev();
		var div = $("#tab_div_" + cd).prev();
		
		if (null == li || li.length == 0) {
			li = $("#tab_li_" + cd).next();
			div = $("#tab_div_" + cd).next();
		}
		
		if (null != li && li.length > 0) {
			li.addClass("active");
			div.addClass("in active");
		}
	}
	
	$("#tab_li_" + cd).remove();
	$("#tab_div_" + cd).remove();
	
	settingTabsWidth();
}

/* 删除当前Tab */
function closeTab() {
	
	if ($("#tab_title li[class=active]").length > 0) {
		var li = $("#tab_title li[class=active]").prev();
		var div = $("#tab_contents div[class=active]").prev();
		
		if (null == li || li.length == 0) {
			li = $("#tab_title li[class=active]").next();
			div = $("#tab_contents div[class=active]").next();
		}
		
		if (null != li && li.length > 0) {
			li.addClass("active");
			div.addClass("in active");
		}
	}
	
	$("#tab_title li[class=active]").remove();
	$("#tab_contents div[class=active]").remove();
	
	settingTabsWidth();
}

// tab页向左移动
$('.nav-my-tab .leftbackward').click(function() {
	$(".nav-my-tab .middletab .nav-tabs").stop();
	
	var strLeft = $('.nav-my-tab .middletab .nav-tabs').css('left');
	var iLeft;
	
	if (strLeft == "auto") {
		iLeft = 0;
	} else {
		iLeft = parseInt(strLeft.replace('px', ''));
	}
	
	if (iLeft >= 0) {
		return;
	} else {
		var totalWidth=0;
		var lis = $(".nav-tabs li");
		for (var i = 0; i < lis.length; i++) {
			var item = lis[i];
			totalWidth -= $(item).width();
			if (iLeft > totalWidth) {
				iLeft+=$(item).width();
				break;
			}
		};
		if (iLeft > 0) {
			iLeft = 0;
		}
		
		$(".nav-my-tab .middletab .nav-tabs").animate({left: iLeft}, 200);
	}
});
// tab页向右移动
$('.nav-my-tab .rightforward').click(function() {
	$(".nav-my-tab .middletab .nav-tabs").stop();
	
	var strLeft = $('.nav-my-tab .middletab .nav-tabs').css('left');
	var iLeft;
	
	if (strLeft == "auto") {
		iLeft = 0;
	} else {
		iLeft = parseInt(strLeft.replace('px', ''));
	}
	
	var totalWidth = 0;
	
	$.each($(".nav-tabs li"),function(key, item) {
		totalWidth += $(item).width();
	});
	
	var tabsWidth = $(".nav-my-tab .middletab").width();
	
	if (totalWidth > tabsWidth) {
		if (totalWidth - tabsWidth <= Math.abs(iLeft)) {
			return;
		}
		
		var lis = $(".nav-tabs li");
		
		totalWidth = 0;
		
		for (var i=0; i<lis.length; i++) {
			var item = lis[i];
			totalWidth -= $(item).width();
			if (iLeft > totalWidth) {
				iLeft -= $(item).width();
				break;
			}
		};
		
		$(".nav-my-tab .middletab .nav-tabs").animate({left: iLeft}, 200);
	}
});
// tab页向当前页面移动
function setCurrentTabs(li) {
	
	$(".nav-my-tab .middletab .nav-tabs").stop();
	
	var strLeft = $('.nav-my-tab .middletab .nav-tabs').css('left');
	var iLeft;
	
	if (strLeft == "auto") {
		iLeft = 0;
	} else {
		iLeft = parseInt(strLeft.replace('px', ''));
	}
	
	var totalWidth = 0;
	
	$.each($(".nav-tabs li"),function(key, item) {
		totalWidth += $(item).width();
	});
	
	var tabsWidth = $(".nav-my-tab .middletab").width();
	
	if (totalWidth > tabsWidth) {
		if (totalWidth - tabsWidth <= Math.abs(iLeft)) {
			return;
		}
		
		var lis = $(".nav-tabs li");
		
		totalWidth = 0;
		
		for (var i = 0; i < lis.length; i++) {
			var item = lis[i];
			totalWidth -= $(item).width();
			if (iLeft > totalWidth) {
				iLeft -= $(item).width();
				break;
			}
			
			if ($(item)[0].id == li[0].id) {
				break;
			}
		};
		
		$(".nav-my-tab .middletab .nav-tabs").animate({left: iLeft}, 200);
	}
}
/*]]>*/