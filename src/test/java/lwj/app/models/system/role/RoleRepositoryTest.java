package lwj.app.models.system.role;

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
public class RoleRepositoryTest {

	@Autowired
    private RoleRepository repository;
	
	private Role role;
	
	private String roleNm = "junit-role";
	
	@Before
	public void setUp() throws Exception {
		// 插入测试数据
		role = new Role();
		role.setRoleNm(roleNm);
		role.setRoleDesc("desc");
		
		repository.save(role);
	}

	@Test
	public void testFindOneByRoleNm() {
		Role result = repository.findOneByRoleNm(roleNm);
		
		assertThat(result).isNotNull();
		assertThat(result.getRoleNm()).isEqualTo(roleNm);
	}

}
