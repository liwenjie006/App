package lwj.app.models.system.account;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lwj.app.models.business.menu.Menu;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {

	@Resource
	private AccountService service;
	
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
	public void testRead() {
		List<Account> result = service.read("admin", "超级管理员", true);
		
		assertThat(result).isNotNull();
		assertThat(result).asList();
	}

}
