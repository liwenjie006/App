package lwj.app.config.i18n;

import java.text.MessageFormat;
import java.util.Locale;

import javax.annotation.Resource;

import org.springframework.context.support.AbstractResourceBasedMessageSource;

import lwj.app.models.system.code.CodeRepository;
import lwj.app.models.system.lan.LanService;
import lwj.app.utils.system.LogUtil;

public class MessageResource extends AbstractResourceBasedMessageSource {

	private LogUtil log = new LogUtil(getClass());
	
	@Resource
	private LanService service;
	
	@Resource
    private CodeRepository codeRepository;
	
	@Override
	protected MessageFormat resolveCode(String code, Locale locale) {
		return new MessageFormat(service.getResource("登录", codeRepository.findOneByName(locale.getLanguage())));
	}

}
