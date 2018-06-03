package lwj.app;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lwj.app.utils.system.LogUtil;

/**
 * 统一异常处理
 * @author LF
 *
 */
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class AppErrorController extends AbstractErrorController {

	private LogUtil logUtil = new LogUtil(AppErrorController.class);
	
	public AppErrorController(ErrorAttributes errorAttributes) {
		 super(errorAttributes);
	}

	@Override
	public String getErrorPath() {
		return null;
	}
	
	/**
	 * 一般Html异常处理
	 * @param request
	 * @param response
	 * @return 错误页面路径
	 */
	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
		HttpStatus status = getStatus(request);
		logUtil.print(status);
		
		return new ModelAndView("error/error");
	}

	/**
	 * Json异常处理
	 * @param request
	 * @return
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		return new ResponseEntity<Map<String, Object>>(getErrorAttributes(request, false), getStatus(request));
	}
	
	/**
	 * 默认异常处理
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	public ModelAndView errorDefault(HttpServletRequest request, HttpServletResponse response) {
		HttpStatus status = getStatus(request);
		logUtil.print(status);
		
		return new ModelAndView("error/error");
	}

}