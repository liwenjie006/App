package lwj.app;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import lwj.app.models.system.account.Account;
import lwj.app.models.system.account.AccountService;
import lwj.app.models.system.history.History;
import lwj.app.models.system.history.HistoryService;
import lwj.app.utils.system.LogUtil;

/**
 * Web主函数
 * @author LF
 *
 */
@Controller
@Transactional
@SessionAttributes({ "sa_account" })
public class AppLogin {

	private LogUtil logUtil = new LogUtil(AppLogin.class);
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private HistoryService historyService;
	
	
	
	/**
	 * 登录失败
	 * @return
	 */
	@RequestMapping("/loginFailure")
	public String loginFailure(Model model) throws Exception {
		logUtil.print("loginFailure");
		model.addAttribute("failure", "Y");
		return "main/login";
	}
	
	/**
	 * 登录成功
	 * @return
	 */
	@RequestMapping("/loginSuccess")
	public String loginSuccess(History history, Model model) throws Exception {
		logUtil.print("loginSuccess");
		// 取得登录信息
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
		Account account = accountService.findOneByAccountId(userDetails.getUsername());
		
		history.setSessionId(webAuthenticationDetails.getSessionId());	// 使用Spring security时生成的SessionId更HttpSession里的不一样
		history.setLogMsg("Login success!");
		history.setInUser(account);
		history.setInIp(webAuthenticationDetails.getRemoteAddress());
		
		historyService.save(history);
		
		// 设置Session
		model.addAttribute("sa_account", account);
		
		return "redirect:/v/main/main";
	}
	
	/**
	 * 登出
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout(History history, @SessionAttribute("sa_account") Account account) throws Exception {
		logUtil.print("loginSuccess");
		// 取得登录信息
		WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails)SecurityContextHolder.getContext().getAuthentication().getDetails();
		
		history.setSessionId(webAuthenticationDetails.getSessionId());	// 使用Spring security时生成的SessionId更HttpSession里的不一样
		history.setLogMsg("Login success!");
		history.setInUser(account);
		history.setInIp(webAuthenticationDetails.getRemoteAddress());
		
		historyService.save(history);
		
		return "redirect:/v/main/main";
	}
	
}