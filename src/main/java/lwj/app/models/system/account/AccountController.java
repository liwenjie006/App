package lwj.app.models.system.account;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import lwj.app.utils.system.LogUtil;

/**
 * 账户管理
 * @author LF
 *
 */
@RestController
@Transactional
@SessionAttributes({ "sa_account" })
@RequestMapping("/account")
public class AccountController {

	private LogUtil logUtil = new LogUtil(getClass());
	
	@Resource
	private AccountService accountService;
	
	
	
	/**
	 * 通过账户ID，账户名称，使用与否查询对应账户信息列表
	 * @param accountId, accountNm, enabled
	 * @return 账户信息列表
	 */
	@RequestMapping("/search")
	public List<Account> search(@RequestBody Account account) throws Exception {
		return accountService.findAccountList(account);
	}
	
	
}