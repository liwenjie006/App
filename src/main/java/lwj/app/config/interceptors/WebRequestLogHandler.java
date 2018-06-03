package lwj.app.config.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lwj.app.utils.system.LogUtil;

/**
 * Web request log handler
 */
@Component
public class WebRequestLogHandler implements HandlerInterceptor {

    private LogUtil logUtil = new LogUtil(WebRequestLogHandler.class);

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        logUtil.print(request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }
    
}