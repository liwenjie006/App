package lwj.app.models.system.account;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	@GetMapping
	public List<Account> find(String accountId, String accountNm, boolean enabled) throws Exception {
		return accountService.find(accountId, accountNm, enabled);
	}
	
	/**
	 * 通过账户CD查询单个账户
	 * @param 账户信息
	 * @return
	 */
	@GetMapping("/{accountCd:\\d+}")
	public Account get(@PathVariable int accountCd) throws Exception {
		return accountService.findOne(accountCd).get();
	}
	
	/**
	 * 保存账户信息
	 * @param account
	 * @return 保存后的账户信息
	 * @throws Exception
	 */
	@PostMapping
	public Account post(@Valid @RequestBody Account account) throws Exception {
		return accountService.save(account);
	}
	
	/**
	 * 修改账户信息
	 * @param account
	 * @param accountCd
	 * @return 修改后的账户信息
	 * @throws Exception
	 */
	@PutMapping("/{accountCd:\\d+}")
	public Account put(@Valid @RequestBody Account account, @PathVariable int accountCd) throws Exception {
		account.setAccountCd(accountCd);
		return accountService.save(account);
	}
	
	@DeleteMapping("/{accountCd:\\d+}")
	public void del(@PathVariable int accountCd) throws Exception {
		accountService.del(accountCd);
	}
	
}