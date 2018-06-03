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
	public Account findOneAccountByAccountId(String accountId) {
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
	
	public List<Account> read() {
		return accountRepository.findAll();
	}
	
}