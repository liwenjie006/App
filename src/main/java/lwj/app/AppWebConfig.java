package lwj.app;

import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import lwj.app.utils.system.LogUtil;

/**
 * Web Config
 * @author LF
 *
 */
@Configuration
public class AppWebConfig implements WebMvcConfigurer {

	private LogUtil logUtil = new LogUtil(AppWebConfig.class);

	@Autowired(required=false)
	private Set<HandlerInterceptor> handlers;

	/**
	 * CORS (跨来资源访问)
	 * @return
	 */
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**");
    }
	
	/**
	 * 多国语言设置 - 以Cookie值为准
	 * @return
	 */
	@Bean
    public LocaleResolver localeResolver() {
		CookieLocaleResolver slr = new CookieLocaleResolver();
        slr.setDefaultLocale(Locale.CHINESE);
        return slr;
    }

	/**
	 * 添加拦截器
	 */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	// 添加国际化拦截器
        registry.addInterceptor(new LocaleChangeInterceptor());

		handlers.stream().forEach(handler -> {
			logUtil.print(handler);
			registry.addInterceptor(handler);
		});
    }


}