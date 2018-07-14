package lwj.app.models.system.account;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import lwj.app.models.BaseRepository;
import lwj.app.models.business.menu.Menu;

/**
 * 帐号
 * @author LF
 * 
 */
public interface AccountRepository extends BaseRepository<Account, Serializable> {
	
	/**
	 * 通过帐号ID取得帐号信息
	 * @param accountId
	 * @return 帐号
	 */
	public Account findOneByAccountId(String accountId);
	
	/**
	 * 通过电话取得帐号信息
	 * @param tel
	 * @return 帐号
	 */
	public Account findOneByTel(String tel);
	
	/**
	 * 通过邮箱取得帐号信息
	 * @param email
	 * @return 帐号
	 */
	public Account findOneByEmail(String email);
	
	/**
	 * 通过账户ID，账户名称，使用与否查询对应账户信息列表
	 * @param accountId
	 * @param accountNm
	 * @param enabled
	 * @return 账户列表
	 */
	public List<Account> findByAccountIdOrAccountNmOrEnabledOrderByAccountId(String accountId, String accountNm, boolean enabled);
	
	/**
	 * 通过账户ID，菜单级别取得菜单列表
	 * @param accountId
	 * @param mlevel
	 * @return 菜单列表
	 */
	@Query("SELECT m FROM Account a JOIN a.menus m "
			+ "WHERE a.accountCd = :accountCd AND (m.topMenu.menuCd = :menuCd OR 0 = :menuCd) AND m.mlevel = :mlevel")
	public List<Menu> findSubMenus(@Param("accountCd") int accountCd, @Param("menuCd") int menuCd, @Param("mlevel") int mlevel);
	
	/**
	 * 用户检索
	 * @param accountId
	 * @param accountNm
	 * @param enabled
	 * @param nonLocked
	 * @param nonExpired
	 * @param credentialsNonExpired
	 * @return 用户列表
	 */
	@Query("SELECT a "
			+ "FROM Account a "
			+ "WHERE a.accountId LIKE %:accountId% "
			+ "AND a.accountNm LIKE %:accountNm% "
			+ "AND a.enabled LIKE :enabled% "
			+ "AND a.nonLocked LIKE :nonLocked% "
			+ "AND a.nonExpired LIKE :nonExpired% "
			+ "AND a.credentialsNonExpired LIKE :credentialsNonExpired% "
			)
	public List<Account> findAccountList(@Param("accountId") String accountId,
			@Param("accountNm") String accountNm, @Param("enabled") String enabled,
			@Param("nonLocked") String nonLocked, @Param("nonExpired") String nonExpired,
			@Param("credentialsNonExpired") String credentialsNonExpired);
	
}