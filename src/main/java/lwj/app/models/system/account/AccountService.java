package lwj.app.models.system.account;

import java.util.ArrayList;
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
	
	private List<Menu> tmpMenus;
	private List<Menu> subMenu;
	
	/**
	 * 通过账户ID取得菜单信息
	 * @param account
	 * @return List<Menu>
	 */
	public List<Menu> getMenu(Account account) {
		account = accountRepository.findOneByAccountId(account.getAccountId());
		
		List<Menu> menus = new ArrayList<Menu>();
		
		tmpMenus = new ArrayList<Menu>();
		subMenu = new ArrayList<Menu>();
		
		account.getMenus().forEach(menu -> {
			if (menu.isEnabled()) {
				// 1级菜单
				if (1 == menu.getMlevel()) {
					menus.add(menu);
					tmpMenus.add(menu);
				// 2+级菜单
				} else {
					if (tmpMenus.get(0).getMlevel()+1 == menu.getMlevel()) {
						tmpMenus.stream()
								.filter(tmp -> tmp.getMenuCd() == menu.getTopMenu().getMenuCd())
								.findFirst()
								.get().getSubMenu().add(menu);
					} else {
						tmpMenus = new ArrayList<Menu>();
						subMenu = new ArrayList<Menu>();
						tmpMenus.add(menu);
					}
				}
			}
		});
		
		log.print(menus);
		
		return menus;
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