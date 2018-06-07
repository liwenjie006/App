package lwj.app.models.system.account;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lwj.app.models.BaseService;
import lwj.app.models.business.menu.Menu;


/**
 * 帐号
 * @author LF
 *
 */
@Service
@Transactional
public class AccountService extends BaseService<Account> {

	@Autowired
	private AccountRepository accountRepository;
	
	/**
	 * 通过账户ID取得帐号信息
	 * @param accountId
	 * @return Account
	 */
	public Account findOneByAccountId(String accountId) {
		return accountRepository.findOneByAccountId(accountId);
	}
	
	/**
	 * 通过账户ID取得菜单信息
	 * @param account
	 * @return List<Menu>
	 */
	public List<Menu> getMenu(Account account) {
		account = accountRepository.findOneByAccountId(account.getAccountId());
		return account.getMenus();
	}
	
	/**
	 * 通过账户ID，账户名称，使用与否查询对应账户信息列表
	 * @param accountId
	 * @param accountNm
	 * @param enabled
	 * @return 账户列表
	 */
	public List<Account> find(String accountId, String accountNm, boolean enabled) {
		return accountRepository.findByAccountIdOrAccountNmOrEnabledOrderByAccountId(accountId, accountNm, enabled);
	}
	
}