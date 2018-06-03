package lwj.app.models.system.history;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lwj.app.models.BaseService;

/**
 * 操作记录
 * @author LF
 *
 */
@Service
public class HistoryService extends BaseService<History> {

	@Autowired
	private HistoryRepository historyRepository;
	
}