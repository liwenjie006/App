package lwj.app.models.business.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(false)
public class UserRepositoryTest {

	@Autowired
    private UserRepository repository;
	
	private User user;
	
	private String id = "junit-" + UUID.randomUUID();
	private String tel = "01063138710";
	private String email = "liwenjie006@gmail.com";
	
	@Before
	public void setUp() throws Exception {
		// 插入测试数据
		user = new User();
		
		user.setAccountId(id);
		user.setAccountNm("一二三四五六七八九十");
		user.setAccountPw("12345");
		user.setTel(tel);
		user.setEmail(email);
		user.setUserBirthday(new Date());
		user.setUserDesc("desc");
		
		repository.save(user);
	}
	
	/**
	 * 测试通过帐号ID取得帐号信息
	 */
	@Test
	public void testFindOneByAccountId() {
		User result = repository.findOneByAccountId(id);
		
		assertThat(result).isNotNull();
		assertThat(result.getAccountId()).isEqualTo(id);
	}

}
