package lwj.app.config.i18n;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.MessageFormat;
import java.util.Locale;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageResourceTest {

	@Resource
	private MessageResource messageResource;
		
	@Test
	public void testResolveCodeStringLocale() {
		Locale locale = Locale.getDefault();
		
		MessageFormat result = messageResource.resolveCode("登录", locale);
		
		assertThat(result).isNotNull();
		assertThat(result.format(null)).isEqualTo("登录");
	}

}
