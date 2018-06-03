package lwj.app;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import lwj.app.models.business.menu.Menu;
import lwj.app.models.system.account.Account;
import lwj.app.models.system.account.AccountService;
import lwj.app.utils.system.LogUtil;

/**
 * Web主函数
 * @author LF
 *
 */
@Controller
@Transactional
@SessionAttributes({ "sa_account" })
public class AppWeb {

	private LogUtil logUtil = new LogUtil(AppWeb.class);
	
	@Autowired
	private AccountService accountService;
	
	
	
	/**
	 * 根路径配置
	 * @return index.html
	 */
	@RequestMapping("/")
	public String index() throws Exception {
		return "index";
	}

	/**
	 * 各种界面统一配置
	 * @return
	 */
	@RequestMapping("/v/{folder}/{type}")
	public String view(@PathVariable("folder") String folder, 
			@PathVariable("type") String type) throws Exception {
		logUtil.print("into -> " + folder + File.separator + type);
		
		return folder + File.separator + type;
	}
	
	/**
	 * 各种界面统一配置
	 * @return
	 */
	@RequestMapping("/v/{folder}/{model}/{type}")
	public String view(@PathVariable("folder") String folder, @PathVariable("model") String model, 
			@PathVariable("type") String type) throws Exception {
		
		logUtil.print("into -> " + folder + File.separator + model + File.separator + model + "_" + type);
		
		return folder + File.separator + model + File.separator + model + "_" + type;
	}
	
	/**
	 * Session超时配置
	 * @return
	 */
	@RequestMapping("/error/timeout")
	public void timeout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) { // ajax 超时处理
			response.getWriter().print("timeout");  //设置超时标识
			response.getWriter().close();
		} else {
			response.sendRedirect("/");
		}
	}
	
	/**
	 * 权限异常配置
	 * @return error/notPermissions.html
	 */
	@RequestMapping("/error/notPermissions")
	public void notPermissions(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) { // ajax 超时处理
			response.getWriter().print("not permissions");  //设置超时标识
			response.getWriter().close();
		} else {
			response.sendRedirect("/error/error");
		}
	}
	
	/**
	 * 菜单
	 * @return
	 */
	@RequestMapping(value="/mainMenu", method=RequestMethod.POST)
	public @ResponseBody List<Menu> mainMenu(@SessionAttribute("sa_account") Account account) throws Exception {
		logUtil.print(accountService.getMenu(account));
		
		return accountService.getMenu(account);
	}
	
}