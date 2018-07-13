package lwj.app;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
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
		return "error";
	}
	
	/**
	 * 一般Html异常处理
	 * @param request
	 * @param response
	 * @return 错误页面路径
	 */
	@RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("error/error");
		mav.addAllObjects(getErrorAttributes(request, false));
		return mav;
	}

	/**
	 * Json异常处理
	 * @param request
	 * @return
	 */
	@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		return new ResponseEntity<>(getErrorAttributes(request, false), getStatus(request));
	}
	
	/**
	 * Session超时处理
	 * @throws IOException 
	 */
	@RequestMapping("/timeout")
	public void timeout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) { // ajax 超时处理
			response.getWriter().print("timeout"); // 设置超时标识
			response.getWriter().close();
		} else {
			response.sendRedirect("/error/timeoutPage");
		}
	}
	
	/**
	 * Session超时界面
	 * 
	 * @return error/timeout
	 */
	@RequestMapping("/timeoutPage")
	public ModelAndView timeoutPage() {
		return new ModelAndView("error/timeout");
	}

	/**
	 * 权限异常处理
	 * 
	 * @return error/notPermissions
	 * @throws IOException 
	 */
	@RequestMapping("/notPermissions")
	public void notPermissions(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) { // ajax 超时处理
			response.getWriter().print("not permissions"); // 设置超时标识
			response.getWriter().close();
		} else {
			response.sendRedirect("/error/notPermissions");
		}
	}
	
	/**
	 * 权限异常界面
	 * 
	 * @return error/notPermissions.html
	 */
	@RequestMapping("/notPermissionsPage")
	public ModelAndView notPermissionsPage() {
		return new ModelAndView("error/notPermissions");
	}

}