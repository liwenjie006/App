package lwj.app;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

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

	private LogUtil log = new LogUtil(AppWeb.class);
	
	@Resource
	private AccountService accountService;
	
	/**
	 * 根路径配置
	 * @return index.html
	 */
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	/**
	 * 根路径配置
	 * @return index.html
	 */
	@RequestMapping("/main")
	public String main() {
		return "main/main";
	}

	/**
	 * 各种界面统一配置
	 * @return
	 */
	@RequestMapping("/v/{folder}/{type}")
	public ModelAndView view(@PathVariable("folder") String folder, @PathVariable("type") String type,
			@RequestParam Map<String, Object> params, ModelAndView mav) {
		
		log.print("into -> " + folder + File.separator + type +
				", params -> " + params);
		
		mav.addAllObjects(params);
		mav.setViewName(folder + File.separator + type);
		
		return mav;
	}
	
	/**
	 * 各种界面统一配置
	 * @return
	 */
	@RequestMapping("/v/{folder}/{model}/{type}")
	public ModelAndView view(@PathVariable("folder") String folder, @PathVariable("model") String model,
			@PathVariable("type") String type, @RequestParam Map<String, Object> params, ModelAndView mav) {
		
		log.print("into -> " + folder + File.separator + model + File.separator + model + "_" + type +
				", params -> " + params);
		
		mav.addAllObjects(params);
		mav.setViewName(folder + File.separator + model + File.separator + model + "_" + type);
		
		return mav;
	}
	
	/**
	 * 菜单
	 * @return
	 */
	@RequestMapping(value="/mainMenu", method=RequestMethod.POST)
	@JsonView(SubMenuView.class)
	public @ResponseBody List<Menu> mainMenu(@SessionAttribute("sa_account") Account account) {
		return accountService.getMenu(account);
	}
	
}