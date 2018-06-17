package lwj.app.models.business.user;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import lwj.app.utils.system.LogUtil;

/**
 * 用户管理
 * @author LF
 *
 */
@RestController
@Transactional
@SessionAttributes({ "sa_account" })
@RequestMapping("/user")
public class UserController {

	private LogUtil logUtil = new LogUtil(getClass());
	
	@Resource
	private UserService userService;
	
	
	
	/**
	 * 通过账户ID，账户名称，使用与否查询对应账户信息列表
	 * @param accountId, accountNm, enabled
	 * @return 账户信息列表
	 */
	@RequestMapping("/search")
	public List<User> search(User user) throws Exception {
		
		logUtil.print(user);
		
		return userService.find();
	}
	
	
}