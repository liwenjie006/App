package lwj.app.config.i18n;

import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
  * 多国语言
  * @author LF
  *
  */
@Service
public class MessageSource {

	@Resource
	private ApplicationContext context;
	
	private Locale locale = Locale.CHINESE;
	
	/**
	 * 设定国际编码
	 * @param locale
	 */
	public void setLocale(Locale lc) {
		locale = lc;
	}
	
	/**
	 * 取得现设置的国际编码
	 * @return
	 */
	public Locale getLocale() {
		return locale;
	}
	
	/**
	 * 取得对应字符
	 * @param code String
	 * @param locale Locale
	 * @return String
	 */
	public String getMessage(String code, Locale locale) throws Exception {
		return context.getMessage(code, null, "Code name is not exist", locale);
	}
	
	/**
	 * 取得对应字符
	 * @param code String
	 * @param args Object[]
	 * @param locale Locale
	 * @return String
	 */
	public String getMessage(String code, Object[] args, Locale locale) throws Exception {
		return context.getMessage(code, args, "Code name is not exist", locale);
	}
	
	/**
	 * 取得对应字符, 使用默认设置的国际编码
	 * @param code String
	 * @return String
	 */
	public String getMessage(String code) throws Exception {
		return getMessage(code, locale);
	};
	
	/**
	 * 取得对应字符, 使用默认设置的国际编码
	 * @param code String
	 * @param args Object[]
	 * @return String
	 */
	public String getMessage(String code, Object[] args) throws Exception {
		return getMessage(code, args, locale);
	};
	
}