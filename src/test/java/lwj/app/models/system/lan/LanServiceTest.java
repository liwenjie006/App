package lwj.app.models.system.lan;

import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lwj.app.models.system.code.CodeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LanServiceTest {

	@Resource
	private LanService service;
	
	@Resource
    private CodeRepository codeRepository;
	
	@Test
	public void testGetResource() {
		String result = service.getResource("登录", codeRepository.findOneByName("中文"));
		
		assertThat(result).isNotNull();
		assertThat(result).isEqualTo("登录");
	}

}
