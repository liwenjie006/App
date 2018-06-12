/*<![CDATA[*/
var axiosUtil = {
	loading: null,
		
    // 创建实例时设置配置的默认值
    _axios: axios.create({
        method: "post",
        headers: { 'X-Requested-With': 'XMLHttpRequest' },
        xsrfHeaderName: appCsrf.csfr_header,
        timeout: 30 * 1000,
        // `maxContentLength` 定义允许的响应内容的最大尺寸
        maxContentLength: 2000
    }),
    
    /**
     * post
     */
    post: function (url, data, fun, vue, useProgress, sync) {
    	if (null == sync || undefined == sync) {
    		sync = true;
    	}
    	
    	if (useProgress) {
    		// 初始化Loading
			loading = vue.$loading({
                lock: true,
                text: appMsg.loadingMsg,
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
    	}
    	
        // 头部加上Spring CSRF 信息
        this._axios.defaults.headers.common[appCsrf.csrf_header] = appCsrf.csrf_token;

        this._axios.post(url, data)
            .then(function (response) {
                /*
                  data: {}, 由服务器提供的响应
                  status: 200, 来自服务器响应的 HTTP 状态码
                  statusText: 'OK', 来自服务器响应的 HTTP 状态信息
                  headers: {},服务器响应的头
                  config: {}是为请求提供的配置信息
                 */
                console.log(response);

                if ("timeout" == response.data) { // 超时处理

                } else if ("not permissions" == response.data) { // 权限不足

                } else {
                    fun(response.data);
                }
                loading.close();
            })
            .catch(function (error) {
                if (error.response) {
                    // 请求已发出，但服务器响应的状态码不在 2xx 范围内
                    console.log(error.response.data);
                    console.log(error.response.status);
                    console.log(error.response.headers);
                } else {
                    // Something happened in setting up the request that triggered an Error
                    console.log('Error', error.message);
                }
                console.log(error);
                loading.close();
            });
    },

    /**
     * post all

    post_all: function (urls, datas, funs, useProgres) {
        if (false != useProgres) {
            ajax.openProgresBarWindow();
        }

        axios.post(url, data)
            .then(function (response) {
                console.log(response);

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

            })
            .catch(function (error) {
                console.log(error);
            });
    }
     */
}
/*]]>*/