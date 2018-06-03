package lwj.app.models.business.action;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import lwj.app.models.BaseRepository;

/**
 * 事件
 * @author LF
 *
 */
@Repository
public interface ActionRepository extends BaseRepository<Action, Serializable> {
	
}