package lwj.app.config.i18n;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
  * 多国语言工具
  * @author LF
  *
  */
@Service
public class ProMessage extends MessageSource {

	@Autowired
	private ApplicationContext context;
	
	/**
	 * 取得对应字符
	 * @param code String
	 * @param locale Locale
	 * @return String
	 */
	@Override
	public String getMessage(String code, Locale locale) throws Exception {
		return context.getMessage(code, null, "No information!", locale);
	}

	/**
	 * 取得对应字符
	 * @param code String
	 * @param args Object[]
	 * @param locale Locale
	 * @return String
	 */
	@Override
	public String getMessage(String code, Object[] args, Locale locale) throws Exception {
		return context.getMessage(code, args, "No information!", locale);
	}

	
}