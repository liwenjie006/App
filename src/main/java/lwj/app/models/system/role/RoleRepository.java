package lwj.app.models.system.role;

import java.io.Serializable;

import lwj.app.models.BaseRepository;

/**
 * 角色
 * @author LF
 * @version 1.0
 * @since JDK 8
 */
public interface RoleRepository extends BaseRepository<Role, Serializable> {
	
	/**
	 * 通过角色名取得角色信息
	 * @param roleNm
	 * @return 角色
	 */
	public Role findOneByRoleNm(String roleNm);
	
}