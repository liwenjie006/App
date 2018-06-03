package lwj.app.config.security;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lwj.app.utils.system.LogUtil;

/**
 * 验证权限
 * @author LF
 *
 */
@Service
public class AppAccessDecisionManager implements AccessDecisionManager {

	private LogUtil logUtil = new LogUtil(AppAccessDecisionManager.class);
	
	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		
//		String url = ((FilterInvocation) object).getRequestUrl();
//
//		if (url.equals("/view/main-login")) {
//			return;
//		}
//		System.out.println(authentication);
//		System.out.println(object);
//		System.out.println(configAttributes);
		
		/*
		// 접속 주소 가져오기
		String url = ((FilterInvocation) object).getRequestUrl();
		
		// 여기서 디비 정보를 가져와서 자원 방문 권한이 있는지 체크한다
		// 로그인 사용자 Role 정보 가져올수 있음
		try {
			authentication.getAuthorities().stream().forEach(auth -> {
				if (!"ROLE_ANONYMOUS".equals(auth.getAuthority())) {
					Role role = roleRepository.findOne(auth.getAuthority());
					if (null != role) {
						role.getAuthSet().stream().forEach(authSet -> {
							authSet.getResourceSet().stream().forEach(resource -> {
								if (url.trim().equals(resource.getResourceUrl().trim())) {
									ignore++;
									return;
								}
							});
						});
					}
				}
			});
		} catch (Exception e) {
		}
		*/
		// 권한 없을 시 Exception 리턴 - 나중에 다국어 적용
//		throw new AccessDeniedException("No permissions!");
		
		
	}

	@Override
	public boolean supports(ConfigAttribute arg0) {
		return true;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}