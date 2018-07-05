package lwj.app.config.i18n;

import java.text.MessageFormat;
import java.util.Locale;

import org.springframework.context.support.AbstractResourceBasedMessageSource;

public class MessageResource extends AbstractResourceBasedMessageSource {

	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		
		System.out.println(code);
		
		code = "asdasf";
		
		return new MessageFormat(code);
	}

}
