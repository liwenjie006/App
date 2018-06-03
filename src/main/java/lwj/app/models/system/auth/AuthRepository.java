package lwj.app.models.system.auth;

import java.io.Serializable;

import lwj.app.models.BaseRepository;

/**
 * 权限
 * @author LF
 * 
 */
public interface AuthRepository extends BaseRepository<Auth, Serializable> {
	
	/**
	 * 通过权限名取得权限信息
	 * @param authNm
	 * @return 权限
	 */
	public Auth findOneByAuthNm(String authNm);
	
}