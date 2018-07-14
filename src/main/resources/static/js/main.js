var appMsg = {
    loadingMsg: "[[#{请求处理中}]]",
    systemAlterTitle: "[[#{系统提示}]]",
    systemAlterButton: "[[#{返回登录界面}]]",
    systemAlterTimeoutMsg: "[[#{服务器连接超时，请重新登录}]]",
    systemAlterNoPermissionsMsg: "[[#{您因权限不足，无法访问该资源}]]",
    systemBtnOk: "[[#{确认}]]",
    systemBtnClose: "[[#{关闭}]]"
}

/* 共同代码 */
var code = {
	enabled: [{label: "[[#{可用}]]", value: "Y"}, {label: "[[#{不可用}]]", value: "N"}],
	nonLocked: [{label: "[[#{未锁定}]]", value: "Y"}, {label: "[[#{锁定}]]", value: "N"}],
	nonExpired: [{label: "[[#{未过期}]]", value: "Y"}, {label: "[[#{过期}]]", value: "N"}],
	credentialsNonExpired: [{label: "[[#{未过期}]]", value: "Y"}, {label: "[[#{过期}]]", value: "N"}]
}

Vue.prototype.$axios = axios.create({
    method: "post",
    headers: { [csrf_header]: csrf_token },
    timeout: 30 * 1000,
    // 允许为上传处理进度事件
    onUploadProgress: function (progressEvent) {
      // 对原生进度事件的处理
    },
    // 允许为下载处理进度事件
    onDownloadProgress: function (progressEvent) {
      // 对原生进度事件的处理
    }
});

var main = new Vue({
	el: '#app',
	data: function() {
		return {
			menuList: null,	// 菜单列表数据
			tabs: [],		// 标签列表数据
			activiTab: ''	// 当前标签
		}
	},
	created: function () {
		// 取得菜单
		this.post("/mainMenu", null, function (result) {
			this.menuList = result;
		}.bind(this), true);
	},
	methods: {
		// 登出
		logout() { location.href = '/logout'; },
		// 选择菜单事件
		handleSelect(menu) { this.addTab(menu.menuCd, menu.menuNm, menu.menuUrl.resourceUrl); },
		// 添加标签事件
		addTab(menuCd, menuNm, menuUrl) {
			// 判断是否为已打开的标签，已打开的标签直接选择标签
			let isOpend = false;
			this.tabs.forEach((tab, index) => { if (menuCd === tab.name) isOpend = true; });
			
			// 未打开时添加
			if (!isOpend) {
				// 取得菜单
				this.tabs.push({
					title: menuNm,
					name: menuCd,
					content: '<iframe src="' + menuUrl + '" id="' + menuCd + '" scrolling="auto" width="100%" height="100%" '
							+ 'frameborder="0" marginheight="0" marginwidth="0" />'
				});
			}
			// 保存当前标签
			this.activiTab = menuCd;
		},
		// 删除标签事件
		removeTab(key) {
			let tabs = this.tabs;
			let activiTab = this.activiTab;
			if (key === this.activiTab) {
				tabs.forEach((tab, index) => {
					if (key === tab.name) {
						let nextTab = tabs[index + 1] || tabs[index - 1];
						if (nextTab) activiTab = nextTab.name;
					}
				});
			}
	        this.activiTab = activiTab;
	        this.tabs = tabs.filter(tab => tab.name !== key);
		},
		// Post
		post(url, data, fun, useProgress, sync) {
	    	if (null == sync || undefined == sync) {
	    		sync = true;
	    	}
	    	
	    	let loading;
	    	
	    	if (useProgress) {
	    		// 初始化Loading
	    		loading = this.$loading({
	                lock: true,
	                text: appMsg.loadingMsg,
	                spinner: 'el-icon-loading',
	                background: 'rgba(0, 0, 0, 0.7)'
	            });
	    	}
	    	
	        this.$axios.post(url, data)
	            .then(function (response) {
	                console.log(response);
	                fun(response.data);
	                loading.close();
	            }.bind(this))
	            .catch(function (error) {
	                console.log(error);
	                loading.close();
	            }.bind(this));
	    }
    }
});