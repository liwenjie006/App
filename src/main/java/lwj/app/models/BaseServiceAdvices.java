package lwj.app.models;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lwj.app.models.system.account.Account;

/**
 * BaseService AOP
 * @author LF
 *
 */
@Component
@Aspect
public class BaseServiceAdvices {

	@Pointcut("execution (* lwj.app.models.BaseService.save(..))")  
    public void savePointCut(){}
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpSession session;
	
	@SuppressWarnings("unchecked")
	@Around("savePointCut()")
	public Object serviceAspect(ProceedingJoinPoint pjp) throws Throwable {
		Object[] args = pjp.getArgs();
		
		if (args[0] instanceof ArrayList) {
			List<Base> baseList = (List<Base>)args[0];
			for (Base base : baseList) {
				if (session.getAttribute("sa_account") != null) { // 当未登录的时候登录信息为空需要个别设置
					base.setInUser((Account)session.getAttribute("sa_account"));
					base.setUpUser((Account)session.getAttribute("sa_account"));
				}
				base.setInIp(request.getRemoteAddr());
				base.setUpIp(request.getRemoteAddr());
			}
		} else {
			Base base = (Base)args[0];
			if (session.getAttribute("sa_account") != null) {
				base.setInUser((Account)session.getAttribute("sa_account"));
				base.setUpUser((Account)session.getAttribute("sa_account"));
			}
			base.setInIp(request.getRemoteAddr());
			base.setUpIp(request.getRemoteAddr());
		}
		
		return pjp.proceed();
    }
	
}