package lwj.app.models.system.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lwj.app.models.business.menu.Menu;
import lwj.app.models.business.menu.MenuRepository;
import lwj.app.utils.system.LogUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccountServiceTest {

	@Resource
	private AccountService service;
	
	@Resource
	private MenuRepository menuRepository;
	
	private LogUtil log = new LogUtil(getClass());
	
	private String id = "junit-" + UUID.randomUUID();
	private String tel = "01063138710";
	private String email = "liwenjie006@gmail.com";
	
	@Test
	public void testFindOneByAccountId() {
		Account result = service.findOneByAccountId("admin");
		
		assertThat(result).isNotNull();
		assertThat(result.getAccountId()).isEqualTo("admin");
	}

	@Test
	public void testGetMenu() {
		Account account = service.findOneByAccountId("admin");
		List<Menu> result = service.getMenu(account);
		
		assertThat(result).isNotNull();
		assertThat(result).asList();
	}
	
	@Test
	public void testRecursionSubMenu() {
		Menu menu = menuRepository.findById(1).get();
		Account account = service.findOneByAccountId("admin");
		
		service.recursionSubMenu(menu, account.getAccountCd());
		
		assertThat(menu).isNotNull();
		assertThat(menu.getSubMenu()).asList();
	}

	@Test
	public void testFind() {
		List<Account> result = service.find("admin", "超级管理员", true);
		
		assertThat(result).isNotNull();
		assertThat(result).asList();
	}
	
	@Test
	public void testSave() throws Exception {
		Account account = new Account();
		
		account.setAccountId(id);
		account.setAccountNm("一二三四五六七八九十");
		account.setAccountPw("12345");
		account.setTel(tel);
		account.setEmail(email);
		
		Account result = service.save(account);
		
		assertThat(result).isNotNull();
		assertThat(result.getAccountId()).isEqualTo(id);
	}

}
