package lwj.app.models.system.code;

import java.io.Serializable;

import lwj.app.models.BaseRepository;

/**
 * 共同代码
 * @author LF
 * @version 1.0
 * @since JDK 8
 */
public interface CodeRepository extends BaseRepository<Code, Serializable> {
	
	/**
	 * 通过代码名称取得代码（初始化时用，代码名是可以重复的）
	 * @param name
	 * @return 共同代码
	 */
	public Code findOneByName(String name);
}