package lwj.app.models;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础服务
 * @author LF
 * @version 1.0
 * @since JDK 8
 *
 */
@Transactional
public abstract class BaseService<T extends Base> {

	@Autowired
	public BaseRepository<T, Serializable> repository;
	
	/**
	 * 查询所有数据
	 * @param Map<String, Object> param
	 * @return List<Base>
	 */
	public List<T> findAll() throws Exception {
		return repository.findAll();
	};
	
	/**
	 * 通过ID查询一条数据
	 * @param Serializable id
	 * @return Base
	 */
	public Optional<T> findOne(Serializable id) throws Exception {
		return repository.findById(id);
	};
	
	/**
	 * 保存多个实体
	 * @param List<Base> baseList
	 * @return List<Base>
	 */
	public List<T> save(List<T> baseList) throws Exception {
		return repository.saveAll(baseList);
	};
	
	/**
	 * 保存单个实体
	 * @param Base base
	 * @return Base
	 */
	public T save(T base) throws Exception {
		return repository.save(base);
	};
	
	/**
	 * 删除多个实体
	 * @param List<Base> baseList
	 */
	public void del(List<T> baseList) throws Exception {
		repository.deleteInBatch(baseList);
	};
	
	/**
	 * 删除单个实体
	 * @param Base base
	 */
	public void del(T base) throws Exception {
		repository.delete(base);
	};
	
	/**
	 * 通过ID删除单个实体
	 * @param Base base
	 */
	public void del(Serializable id) throws Exception {
		repository.deleteById(id);
	};
	
}