package lwj.app.models.system.account;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import lwj.app.AppLogin;
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

	private LogUtil logUtil = new LogUtil(AppLogin.class);
	
	@Resource
	private AccountService accountService;
	
	
	
	/**
	 * 通过账户ID，账户名称，使用与否查询对应账户信息列表
	 * @param String accountCd
	 * @return
	 */
	@GetMapping
	public List<Account> read(String accountId, String accountNm, boolean enabled) throws Exception {
		return accountService.read(accountId, accountNm, enabled);
	}
	
	/**
	 * 通过账户CD查询单个账户
	 * @param String accountCd
	 * @return
	 */
	@GetMapping("/{accountCd:\\d+}")
	public Account get(@PathVariable int accountCd) throws Exception {
		return accountService.findOne(accountCd).get();
	}
	
	
}