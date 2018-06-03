package lwj.app.models.system.resource;

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
public class ResourceRepositoryTest {

	@Autowired
    private ResourceRepository repository;
	
	private Resource resource;
	
	private String resourceNm = "junit-resource";
	
	@Before
	public void setUp() throws Exception {
		// 插入测试数据
		resource = new Resource();
		resource.setResourceNm(resourceNm);
		resource.setResourceDesc("desc");
		resource.setResourceUrl("junit-url");
		
		repository.save(resource);
	}

	@Test
	public void testFindOneByResourceNm() {
		Resource result = repository.findOneByResourceNm(resourceNm);
		
		assertThat(result).isNotNull();
		assertThat(result.getResourceNm()).isEqualTo(resourceNm);
	}

}
