package lwj.app.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

/**
  * SpringWebSecurityAdapter - Spring 安全框架
  * @author LF
  *
  */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserDetailsService appUserDetailService;
	
	@Autowired
    private AppAccessDecisionManager appAccessDecisionManager;
	
	@Override
    public void init(WebSecurity web) throws Exception {
		super.init(web);
		// 设置不拦截规则  
        web.ignoring().antMatchers("/css/**", "/img/**", "/js/**", "/webjars/**", "/libs/**", "/favicon.ico", "/error/**");
    }

	/**
	 * 配置HttpSecurity
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 设置访问权限
		http.authorizeRequests()
			.accessDecisionManager(appAccessDecisionManager)	// 权限具体处理类
			.anyRequest().authenticated()						// 其他路径都都检测
			.and()
			.exceptionHandling()
			.accessDeniedPage("/error/notPermissions");			// 权限异常时访问的路径
		
		// 设置登录界面属性
		http.formLogin()
			.loginPage("/v/main/login").permitAll()	// 登录界面地址
			.usernameParameter("accountId")				// 自定义登录界面ID
			.passwordParameter("accountPw")				// 自定义登录界面密码
			.loginProcessingUrl("/loginDo")				// 登录事件地址
			.defaultSuccessUrl("/loginSuccess")			// 登录成功时调用的地址
			.failureUrl("/loginFailure");				// 登录失败时调用的地址
		
		// 设置登出属性
		http.logout()
			.logoutUrl("/logout").permitAll()		// 登出地址
			.invalidateHttpSession(true);			// 登出时清空Session
		
		// 设置 Session
		http.sessionManagement()
			.invalidSessionUrl("/error/timeout")	// 超时处理
			.sessionFixation().newSession()			// 登录成功后用新的Session
			.maximumSessions(1)						// 同时存在的Session数量设置为1
			.sessionRegistry(sessionRegistry());	// 注册Session监听器

		// Iframe 设置
		http.headers().frameOptions().sameOrigin();
	}

	/**
	 * 设置登陆时用户信息提取类
	 * 设置密码形式
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(appUserDetailService).passwordEncoder(new BCryptPasswordEncoder(16));
	}

	/**
	 * Spring Security 想要取到所有的Session信息需要注册SessionRegistry
	 * @return SessionRegistry
	 */
	@Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

	/**
	 * Spring Security 想要取到所有的Session信息需要再次注册监听器ServletListenerRegistrationBean
	 * @return ServletListenerRegistrationBean
	 */
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }

}