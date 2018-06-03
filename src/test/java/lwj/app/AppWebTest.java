package lwj.app;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lwj.app.models.system.account.Account;

/**
 * 测试Web主函数
 * @author LF
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class AppWebTest {

	@Resource
	private MockMvc mvc;

	@Resource
	private WebApplicationContext context;
	
	@Resource
    private MockHttpSession session;

    @Before // 在测试开始前初始化工作
    public void setup() {
        Account a = new Account();

        a.setAccountCd(1);
        a.setAccountId("admin");

        this.session.setAttribute("sa_account", a);
        
        mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
    }

    @Test
	@WithAnonymousUser
	public void testHome() throws Exception {
		this.mvc.perform(get("/").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk());
	}
	
	@Test
	@WithAnonymousUser
	public void testCss() throws Exception {
		this.mvc.perform(get("/css/main.css").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk());
	}

	@Test
    @WithAnonymousUser
	public void testMenu() throws Exception {
		this.mvc.perform(post("/mainMenu").with(csrf().asHeader()).session(this.session)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk());
	}

}