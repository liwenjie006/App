package lwj.app.config.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import lwj.app.models.system.code.CodeRepository;
import lwj.app.models.system.lan.LanService;
import lwj.app.utils.system.LogUtil;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomErrorAttributes implements ErrorAttributes, HandlerExceptionResolver, Ordered {

	private LogUtil log = new LogUtil(getClass());
	
	private static final String ERROR_ATTRIBUTE = CustomErrorAttributes.class.getName() + ".ERROR";
	
	@Resource
	private LanService service;
	
	@Resource
    private CodeRepository codeRepository;
	
	private final boolean includeException;

	/**
	 * Create a new {@link DefaultErrorAttributes} instance that does not include the
	 * "exception" attribute.
	 */
	public CustomErrorAttributes() {
		this(false);
	}

	/**
	 * Create a new {@link DefaultErrorAttributes} instance.
	 * @param includeException whether to include the "exception" attribute
	 */
	public CustomErrorAttributes(boolean includeException) {
		this.includeException = includeException;
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE;
	}

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		storeErrorAttributes(request, ex);
		return null;
	}

	private void storeErrorAttributes(HttpServletRequest request, Exception ex) {
		request.setAttribute(ERROR_ATTRIBUTE, ex);
	}

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest,
			boolean includeStackTrace) {
		Map<String, Object> errorAttributes = new LinkedHashMap<>();
		errorAttributes.put("timestamp", new Date());
		addStatus(errorAttributes, webRequest);
		addErrorDetails(errorAttributes, webRequest, includeStackTrace);
		addPath(errorAttributes, webRequest);
		return errorAttributes;
	}

	private void addStatus(Map<String, Object> errorAttributes,
			RequestAttributes requestAttributes) {
		Integer status = getAttribute(requestAttributes,
				"javax.servlet.error.status_code");
		if (status == null) {
			errorAttributes.put("status", 999);
			errorAttributes.put("error", "None");
			return;
		}
		errorAttributes.put("status", status);
		try {
			errorAttributes.put("error", HttpStatus.valueOf(status).getReasonPhrase());
		}
		catch (Exception ex) {
			// Unable to obtain a reason
			errorAttributes.put("error", "Http Status " + status);
		}
	}

	private void addErrorDetails(Map<String, Object> errorAttributes,
			WebRequest webRequest, boolean includeStackTrace) {
		Throwable error = getError(webRequest);
		if (error != null) {
			while (error instanceof ServletException && error.getCause() != null) {
				error = ((ServletException) error).getCause();
			}
			if (this.includeException) {
				errorAttributes.put("exception", error.getClass().getName());
			}
			addErrorMessage(errorAttributes, error);
			if (includeStackTrace) {
				addStackTrace(errorAttributes, error);
			}
		}
		Object message = getAttribute(webRequest, "javax.servlet.error.message");
		if ((!StringUtils.isEmpty(message) || errorAttributes.get("message") == null)
				&& !(error instanceof BindingResult)) {
			errorAttributes.put("message",
					StringUtils.isEmpty(message) ? "No message available" : message);
		}
	}

	private void addErrorMessage(Map<String, Object> errorAttributes, Throwable error) {
		BindingResult result = extractBindingResult(error);
		if (result == null) {
			errorAttributes.put("message", error.getMessage());
			return;
		}
		if (result.getErrorCount() > 0) {
			
			List<ObjectError> errors = result.getAllErrors();
			
			errors.forEach(e -> {
				log.print(e);
			});
			
			errorAttributes.put("errors", result.getAllErrors());
			errorAttributes.put("message",
					"Validation failed for object='" + result.getObjectName()
							+ "'. Error count: " + result.getErrorCount());
		}
		else {
			errorAttributes.put("message", "No errors");
		}
	}

	private BindingResult extractBindingResult(Throwable error) {
		if (error instanceof BindingResult) {
			return (BindingResult) error;
		}
		if (error instanceof MethodArgumentNotValidException) {
			return ((MethodArgumentNotValidException) error).getBindingResult();
		}
		return null;
	}

	private void addStackTrace(Map<String, Object> errorAttributes, Throwable error) {
		StringWriter stackTrace = new StringWriter();
		error.printStackTrace(new PrintWriter(stackTrace));
		stackTrace.flush();
		errorAttributes.put("trace", stackTrace.toString());
	}

	private void addPath(Map<String, Object> errorAttributes,
			RequestAttributes requestAttributes) {
		String path = getAttribute(requestAttributes, "javax.servlet.error.request_uri");
		if (path != null) {
			errorAttributes.put("path", path);
		}
	}

	@Override
	public Throwable getError(WebRequest webRequest) {
		Throwable exception = getAttribute(webRequest, ERROR_ATTRIBUTE);
		if (exception == null) {
			exception = getAttribute(webRequest, "javax.servlet.error.exception");
		}
		return exception;
	}

	@SuppressWarnings("unchecked")
	private <T> T getAttribute(RequestAttributes requestAttributes, String name) {
		return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}
	
}
