package lwj.app.models.business.user;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
	
	@Query("SELECT u "
			+ "FROM User u "
			+ "WHERE u.accountId LIKE %:accountId% "
			+ "AND u.accountNm LIKE %:accountNm% "
			+ "AND u.enabled LIKE :enabled% "
			+ "AND u.nonLocked LIKE :nonLocked% "
			+ "AND u.nonExpired LIKE :nonExpired% "
			+ "AND u.credentialsNonExpired LIKE :credentialsNonExpired + '%' "
			)
	public List<User> findUserList(@Param("accountId") String accountId,
			@Param("accountNm") String accountNm, @Param("enabled") String enabled,
			@Param("nonLocked") String nonLocked, @Param("nonExpired") String nonExpired,
			@Param("credentialsNonExpired") String credentialsNonExpired);
}