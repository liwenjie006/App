package lwj.app.models.system.lan;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lwj.app.models.system.account.Account;
import lwj.app.models.system.account.AccountRepository;
import lwj.app.models.system.code.Code;
import lwj.app.models.system.code.CodeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LanRepositoryTest {

	@Resource
    private LanRepository repository;
	
	@Resource
    private CodeRepository codeRepository;
	
	@Resource
    private AccountRepository accountRepository;
	
	private String lanId = "测试";
	@Before
	public void setUp() throws Exception {
		// 插入测试数据
		Code code = codeRepository.findOneByName("中文");
		Account account = accountRepository.findOneByAccountId("admin");
		
		Lan lan = new Lan();
		lan.setInUser(account);
		lan.setInDtm(new Date());
		lan.setInIp("0.0.0.0");
		lan.setLanguage(code);
		lan.setLanId(lanId);
		lan.setLanNm(lanId);
		
		repository.save(lan);
	}
	
	/**
	 * 测试通过帐号ID取得帐号信息
	 */
	@Test
	public void testFindOneByLanId() {
		Lan result = repository.findOneByLanId(lanId);
		
		assertThat(result).isNotNull();
		assertThat(result.getLanId()).isEqualTo(lanId);
	}

	/**
	 * 测试通过帐号ID取得帐号信息
	 */
	@Test
	public void testFindOneByLanIdAndLanguage() {
		Lan result = repository.findOneByLanIdAndLanguage(lanId, codeRepository.findOneByName("中文"));
		
		assertThat(result).isNotNull();
		assertThat(result.getLanId()).isEqualTo(lanId);
	}
	
}
