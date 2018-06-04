package lwj.app;

import java.io.File;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.fasterxml.jackson.annotation.JsonView;

import lwj.app.models.business.menu.Menu;
import lwj.app.models.business.menu.Menu.SubMenuView;
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
	 * 菜单
	 * @return
	 */
	@RequestMapping(value="/mainMenu", method=RequestMethod.POST)
	@JsonView(SubMenuView.class)
	public @ResponseBody List<Menu> mainMenu(@SessionAttribute("sa_account") Account account) throws Exception {
		return accountService.getMenu(account);
	}
	
}