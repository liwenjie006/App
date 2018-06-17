package lwj.app.models.system.account;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lwj.app.models.BaseService;
import lwj.app.models.business.menu.Menu;
import lwj.app.utils.system.LogUtil;


/**
 * 帐号
 * @author LF
 *
 */
@Service
@Transactional
public class AccountService extends BaseService<Account> {

	private LogUtil log = new LogUtil(getClass());
	
	@Resource
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
		List<Menu> menus = accountRepository.findSubMenus(account.getAccountCd(), 0, 1);
		
		menus.forEach(menu -> {
			recursionSubMenu(menu, account.getAccountCd());
		});
		
		return menus;
	}
	
	public void recursionSubMenu(Menu menu, int accountCd) {
		List<Menu> menuList = accountRepository.findSubMenus(accountCd, menu.getMenuCd(), menu.getMlevel()+1);
		if (null != menuList && menuList.size() > 0) {
			menuList.forEach(m -> {
				recursionSubMenu(m, accountCd);
			});
			menu.setSubMenu(menuList);
		}
	}
	
}