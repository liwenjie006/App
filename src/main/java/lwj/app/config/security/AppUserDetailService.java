package lwj.app.config.security;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lwj.app.models.system.account.Account;
import lwj.app.models.system.account.AccountRepository;

/**
 * 用户登陆时提取用户信息
 * 因为是使用Spring Security 所以要继承 UserDetailsService
 * @author LFs
 *
 */
@Service
public class AppUserDetailService implements UserDetailsService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	/**
	 * 取得用户信息并返回给Spring Security处理
	 */
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Collection<GrantedAuthority> grantedAuthority = new HashSet<GrantedAuthority>();
		
		Account account = accountRepository.findOneByAccountId(username);
		
		// 如果用户不存在将抛出异常给Spring Security处理
		if (null != account) {
			account.getRoles().stream().forEach(role -> {
				role.getAuths().stream().forEach(auth -> {
					grantedAuthority.add(() -> String.valueOf(auth.getResources()));
				});
			});
		} else {
			throw new UsernameNotFoundException("用户信息不存在！");
		}
		
		return new User(username, account.getAccountPw(), 
				account.getEnabled().equals("Y") ? true : false,
				account.getNonExpired().equals("Y") ? true : false,
				account.getCredentialsNonExpired().equals("Y") ? true : false,
				account.getNonLocked().equals("Y") ? true : false, grantedAuthority);
		
	}
	
}