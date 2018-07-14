package lwj.app.models.system.lan;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lwj.app.models.BaseService;
import lwj.app.models.system.code.Code;
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
	private LanRepository repository;
	
	/**
	 * 取得国际化信息
	 * @param msg
	 * @param language
	 * @return
	 */
	public String getResource(String msg, Code language) {
		Lan lan = repository.findOneByLanIdAndLanguage(msg, language);
		return null != lan && null != lan.getLanNm() && !"".equals(lan.getLanNm()) ? lan.getLanNm() : msg;
	}
	
}