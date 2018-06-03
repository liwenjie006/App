package lwj.app.config.i18n;

import java.util.Locale;

/**
  * 多国语言
  * @author LF
  *
  */
public abstract class MessageSource {

	public Locale locale = Locale.CHINESE;
	
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
	public abstract String getMessage(String code, Locale locale) throws Exception;
	
	/**
	 * 取得对应字符
	 * @param code String
	 * @param args Object[]
	 * @param locale Locale
	 * @return String
	 */
	public abstract String getMessage(String code, Object[] args, Locale locale) throws Exception;
	
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