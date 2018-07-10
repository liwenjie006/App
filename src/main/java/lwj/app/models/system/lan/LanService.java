package lwj.app.models.system.lan;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lwj.app.models.BaseService;
import lwj.app.utils.system.LogUtil;


/**
 * 国际化
 * @author LF
 *
 */
@Service
@Transactional
public class LanService extends BaseService<Lan> {

	private LogUtil log = new LogUtil(getClass());
	
	@Resource
	private LanRepository lanRepository;
	
	
	
}