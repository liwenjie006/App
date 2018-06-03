package lwj.app.models.system.auth;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AuthRepositoryTest {

	@Autowired
    private AuthRepository repository;
	
	private Auth auth;
	
	private String authNm = "junit-auth";
	
	@Before
	public void setUp() throws Exception {
		// 插入测试数据
		auth = new Auth();
		auth.setAuthNm(authNm);
		auth.setAuthDesc("desc");
		
		repository.save(auth);
	}

	@Test
	public void testFindOneByAuthNm() {
		Auth result = repository.findOneByAuthNm(authNm);
		
		assertThat(result).isNotNull();
		assertThat(result.getAuthNm()).isEqualTo(authNm);
	}

}
