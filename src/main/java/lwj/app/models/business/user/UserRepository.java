package lwj.app.models.business.user;

import java.io.Serializable;

import lwj.app.models.BaseRepository;

/**
 * 用户信息
 * @author LF
 * @version 1.0
 * @since JDK 8
 */
public interface UserRepository extends BaseRepository<User, Serializable> {
	
	/**
	 * 通过帐号ID 取得用户信息
	 * @param accountId
	 * @return 用户
	 */
	public User findOneByAccountId(String accountId);
	
}