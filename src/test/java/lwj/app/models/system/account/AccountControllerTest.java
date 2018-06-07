package lwj.app.models.system.account;

import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

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

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class AccountControllerTest {

	private String id = "junit-" + UUID.randomUUID();
	private String tel = "01063138710";
	private String email = "liwenjie006@gmail.com";
	
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
	public void testFind() throws Exception {
    	mvc.perform(get("/account").with(csrf().asHeader()).session(this.session)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk());
	}
    
    @Test
	@WithAnonymousUser
	public void testGet() throws Exception {
    	mvc.perform(get("/account/1").with(csrf().asHeader()).session(this.session)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE)).andExpect(status().isOk());
	}
    
    @Test
	@WithAnonymousUser
	public void testSave() throws Exception {
    	String content = "{"
    			+ "\"accountId\": \"" + id + "\","
    			+ "\"accountNm\": \"name\","
    			+ "\"accountPw\": \"12345\","
    			+ "\"tel\": \"" + tel + "\","
    			+ "\"email\": \"" + email + "\""
    			+ "}";
    	mvc.perform(put("/account/1").with(csrf().asHeader()).session(this.session)
    			.content(content)
    			.contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
    			.andExpect(status().isOk());
	}
    
    @Test
	@WithAnonymousUser
	public void testDel() throws Exception {
    	mvc.perform(delete("/account/1").with(csrf().asHeader()).session(this.session)
				.accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
    			.andExpect(status().isOk());
	}

}
