package lwj.app.models.system.resource;

import java.io.Serializable;

import lwj.app.models.BaseRepository;

/**
 * 资源
 * @author LF
 * @version 1.0
 * @since JDK 8
 */
public interface ResourceRepository extends BaseRepository<Resource, Serializable> {
	
	/**
	 * 通过资源名取得资源信息
	 * @param resourceNm
	 * @return 资源
	 */
	public Resource findOneByResourceNm(String resourceNm);
	
}