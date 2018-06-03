package lwj.app.models.system.code;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

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
public class CodeRepositoryTest {

	@Autowired
    private CodeRepository repository;
	
	private Code code;
	
	private String name = "junit-" + UUID.randomUUID();
	
	@Before
	public void setUp() throws Exception {
		// 插入测试数据
		code = new Code();
		code.setName(name);
		
		repository.save(code);
	}
	
	/**
	 * 保存测试
	 */
	@Test
	public void testSave() {
		Code tcd = new Code();
		tcd.setName("topCode");
		
		Code scd1 = new Code();
		scd1.setName("subCode1");
		Code scd2 = new Code();
		scd2.setName("subCode2");
		
		repository.save(tcd);
		
		scd1.setTopCode(tcd);
		scd2.setTopCode(tcd);
		
		repository.save(scd1);
		repository.save(scd2);
		
		assertThat(tcd.getCode()).isNotNull();
		assertThat(scd1.getCode()).isNotNull();
		assertThat(scd2.getCode()).isNotNull();
	}

	/**
	 * 过代码名称取得代码测试
	 */
	@Test
	public void testFindOneByName() {
		Code result = repository.findOneByName(name);
		
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo(name);
	}
}
