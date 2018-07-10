package lwj.app.models.system.lan;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import lwj.app.utils.system.LogUtil;

/**
 * 国际化管理
 * @author LF
 *
 */
@RestController
@Transactional
@SessionAttributes({ "sa_account" })
@RequestMapping("/lan")
public class LanController {

	private LogUtil logUtil = new LogUtil(getClass());
	
	@Resource
	private LanService lanService;
	
	
	
	/**
	 * 通过账户ID，账户名称，使用与否查询对应账户信息列表
	 * @param accountId, accountNm, enabled
	 * @return 账户信息列表
	 */
	@RequestMapping("/search")
	public List<Lan> search(@RequestBody Lan account) throws Exception {
		return null;//lanService.findAccountList(account);
	}
	
	
}