package lwj.app.models.system.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import lwj.app.models.business.menu.Menu;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccountRepositoryTest {

	@Autowired
    private AccountRepository repository;
	
	private Account account;
	
	private String id = "junit-" + UUID.randomUUID();
	private String tel = "01063138710";
	private String email = "liwenjie006@gmail.com";
	
	@Before
	public void setUp() throws Exception {
		// 插入测试数据
		account = new Account();
		
		account.setAccountId(id);
		account.setAccountNm("一二三四五六七八九十");
		account.setAccountPw("12345");
		account.setTel(tel);
		account.setEmail(email);
		
		repository.save(account);
	}
	
	/**
	 * 测试通过帐号ID取得帐号信息
	 */
	@Test
	public void testFindOneByAccountId() {
		Account result = repository.findOneByAccountId(id);
		
		assertThat(result).isNotNull();
		assertThat(result.getAccountId()).isEqualTo(id);
	}
	
	/**
	 * 测试通过电话取得帐号信息
	 */
	@Test
	public void testFindOneByTel() {
		Account result = repository.findOneByTel(tel);
		
		assertThat(result).isNotNull();
		assertThat(result.getAccountId()).isEqualTo(id);
	}
	
	/**
	 * 测试通过邮箱取得帐号信息
	 */
	@Test
	public void testFindOneByEmail() {
		Account result = repository.findOneByEmail(email);
		
		assertThat(result).isNotNull();
		assertThat(result.getAccountId()).isEqualTo(id);
	}
	
	/**
	 * 测试通过邮箱取得帐号信息
	 */
	@Test
	public void testFindByAccountIdOrAccountNmOrEnabledOrderByAccountId() {
		List<Account> result = repository.findByAccountIdOrAccountNmOrEnabledOrderByAccountId("admin", "超级管理员", true);
		
		assertThat(result).isNotNull();
		assertThat(result).asList();
	}

	/**
	 * 测试通过邮箱取得帐号信息
	 */
	@Test
	public void testFindSubMenus() {
		List<Menu> result = repository.findSubMenus(1, 1, 2);
		
		assertThat(result).isNotNull();
		assertThat(result).asList();
	}
}
