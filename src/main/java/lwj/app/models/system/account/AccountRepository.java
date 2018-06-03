package lwj.app.models.system.account;

import java.io.Serializable;
import java.util.List;

import lwj.app.models.BaseRepository;

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
	
}