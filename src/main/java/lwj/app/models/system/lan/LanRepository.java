package lwj.app.models.system.lan;

import java.io.Serializable;

import lwj.app.models.BaseRepository;

/**
 * 国际化
 * @author LF
 * 
 */
public interface LanRepository extends BaseRepository<Lan, Serializable> {
	
	/**
	 * 通过ID取得国际化信息
	 * @param lanId
	 * @return 国际化
	 */
	public Lan findOneByLanId(String lanId);
}