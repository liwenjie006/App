package lwj.app.models.business.user;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lwj.app.models.BaseService;
import lwj.app.utils.system.LogUtil;


/**
 * 用户
 * @author LF
 *
 */
@Service
@Transactional
public class UserService extends BaseService<User> {

	private LogUtil log = new LogUtil(getClass());
	
	@Resource
	private UserRepository userRepository;
	
	/**
	 * 通过账户ID取得菜单信息
	 * @param account
	 * @return List<Menu>
	 */
	public List<User> find() {
		return userRepository.findAll();
	}
	
}