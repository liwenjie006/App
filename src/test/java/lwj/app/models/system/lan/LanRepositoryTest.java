package lwj.app.models.system.lan;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lwj.app.models.system.account.Account;
import lwj.app.models.system.account.AccountRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LanRepositoryTest {

	@Autowired
    private LanRepository repository;
	
	private Lan lan;
	
	@Before
	public void setUp() throws Exception {
		// 插入测试数据
		lan = new Lan();
		
		repository.save(lan);
	}
	
	/**
	 * 测试通过帐号ID取得帐号信息
	 */
	@Test
	public void testFindOneByLanId() {
//		Account result = repository.findOneByAccountId(id);
//		
//		assertThat(result).isNotNull();
//		assertThat(result.getAccountId()).isEqualTo(id);
	}

}
