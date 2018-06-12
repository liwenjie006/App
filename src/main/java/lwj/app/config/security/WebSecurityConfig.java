package lwj.app.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
		// 设置登录界面属性
		http.formLogin()
			.loginPage("/v/login").permitAll()			// 登录界面地址
			.usernameParameter("accountId")				// 自定义登录界面ID
			.passwordParameter("accountPw")				// 自定义登录界面密码
			.loginProcessingUrl("/login")				// 登录事件地址
			.defaultSuccessUrl("/loginSuccess")			// 登录成功时调用的地址
			.failureUrl("/loginFailure");				// 登录失败时调用的地址
		
		// 设置登出属性
		http.logout()
			.logoutUrl("/logout").permitAll()		// 退出地址
			.logoutSuccessUrl("/v/login")			// 退出成功后显示登录界面
			.deleteCookies("JSESSIONID")			// 退出成功后删除Cookies
			.invalidateHttpSession(true);			// 登出时清空Session
		
		// 设置 Session
		http.sessionManagement()
			.invalidSessionUrl("/error/timeout")	// 超时处理
			.sessionFixation().newSession()			// 登录成功后用新的Session
			.maximumSessions(1)						// 同时存在的Session数量设置为1
			.sessionRegistry(sessionRegistry());	// 注册Session监听器
		
		// 设置访问权限
		http.authorizeRequests()
			.accessDecisionManager(appAccessDecisionManager)	// 权限具体处理类
			.anyRequest().authenticated()						// 其他路径都都检测
			.and()
			.exceptionHandling()
			.accessDeniedPage("/error/notPermissions");			// 权限异常时访问的路径
		
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
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