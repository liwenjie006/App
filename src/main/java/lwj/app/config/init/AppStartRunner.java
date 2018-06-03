package lwj.app.config.init;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import lwj.app.models.business.menu.Menu;
import lwj.app.models.business.menu.MenuRepository;
import lwj.app.models.system.account.Account;
import lwj.app.models.system.account.AccountRepository;
import lwj.app.models.system.auth.Auth;
import lwj.app.models.system.auth.AuthRepository;
import lwj.app.models.system.code.Code;
import lwj.app.models.system.code.CodeRepository;
import lwj.app.models.system.resource.Resource;
import lwj.app.models.system.resource.ResourceRepository;
import lwj.app.models.system.role.Role;
import lwj.app.models.system.role.RoleRepository;

/**
 * 服务器启动后运行的类，初始化系统最基础数据
 * @author LF
 *
 */
@Component
public class AppStartRunner implements ApplicationRunner {

	private Logger logger = LoggerFactory.getLogger(AppStartRunner.class);

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private CodeRepository codeRepository;
	
	@Autowired
	private ResourceRepository resourceRepository;
	
	@Autowired
	private AuthRepository authRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private MenuRepository menuRepository;


	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		logger.info("初始化系统数据。");

		// 用户信息
		Account account = accountRepository.findOneByAccountId("admin");
		if (null == account) {
			account = new Account();
			account.setAccountId("admin");
			account.setAccountPw(new BCryptPasswordEncoder().encode("1234"));
			account.setAccountNm("超级管理员");
			account.setTel("010-6313-8710");
			account.setEmail("liwenjie1024@gmail.com");
			account.setInUser(account);
			account.setInDtm(new Date());
			account.setInIp("0.0.0.0");
			accountRepository.save(account);
		}

		
		// 登录公用代码-事件类型
		Code code_1 = codeRepository.findOneByName("事件类型");
		if (null == code_1) {
			code_1 = new Code();
			code_1.setName("事件类型");
			code_1.setCodeDesc("系统中包含的所有事件类型");
			code_1.setInUser(account);
			code_1.setInDtm(new Date());
			code_1.setInIp("0.0.0.0");
			codeRepository.save(code_1);
		}

		Code code_2 = codeRepository.findOneByName("按钮");
		if (null == code_2) {
			code_2 = new Code();
			code_2.setName("按钮");
			code_2.setCodeDesc("菜单中包含的所有按钮");
			code_2.setInUser(account);
			code_2.setInDtm(new Date());
			code_2.setInIp("0.0.0.0");
			code_2.setTopCode(code_1);
			codeRepository.save(code_2);
		}

		Code code_3 = codeRepository.findOneByName("连接");
		if (null == code_3) {
			code_3 = new Code();
			code_3.setName("连接");
			code_3.setCodeDesc("系统中包含的所有连接");
			code_3.setInUser(account);
			code_3.setInDtm(new Date());
			code_3.setInIp("0.0.0.0");
			code_3.setTopCode(code_1);
			codeRepository.save(code_3);
		}

		Code code_4 = codeRepository.findOneByName("接口");
		if (null == code_4) {
			code_4 = new Code();
			code_4.setName("接口");
			code_4.setCodeDesc("系统中包含的所有接口");
			code_4.setInUser(account);
			code_4.setInDtm(new Date());
			code_4.setInIp("0.0.0.0");
			code_4.setTopCode(code_1);
			codeRepository.save(code_4);
		}
		
		// 资源
		Resource resource_1 = resourceRepository.findOneByResourceNm("账号管理");
		if (null == resource_1) {
			resource_1 = new Resource();
			resource_1.setResourceNm("账号管理");
			resource_1.setResourceUrl("/v/system/account/list");
			resource_1.setResourceDesc("账号管理");
			resource_1.setInUser(account);
			resource_1.setInDtm(new Date());
			resource_1.setInIp("0.0.0.0");
			resourceRepository.save(resource_1);
		}
		
		Resource resource_2 = resourceRepository.findOneByResourceNm("角色管理");
		if (null == resource_2) {
			resource_2 = new Resource();
			resource_2.setResourceNm("角色管理");
			resource_2.setResourceUrl("/v/system/role/list");
			resource_2.setResourceDesc("角色管理");
			resource_2.setInUser(account);
			resource_2.setInDtm(new Date());
			resource_2.setInIp("0.0.0.0");
			resourceRepository.save(resource_2);
		}
		
		Resource resource_3 = resourceRepository.findOneByResourceNm("权限管理");
		if (null == resource_3) {
			resource_3 = new Resource();
			resource_3.setResourceNm("权限管理");
			resource_3.setResourceUrl("/v/system/auth/list");
			resource_3.setResourceDesc("权限管理");
			resource_3.setInUser(account);
			resource_3.setInDtm(new Date());
			resource_3.setInIp("0.0.0.0");
			resourceRepository.save(resource_3);
		}
		
		Resource resource_4 = resourceRepository.findOneByResourceNm("资源管理");
		if (null == resource_4) {
			resource_4 = new Resource();
			resource_4.setResourceNm("资源管理");
			resource_4.setResourceUrl("/v/system/resource/list");
			resource_4.setResourceDesc("资源管理");
			resource_4.setInUser(account);
			resource_4.setInDtm(new Date());
			resource_4.setInIp("0.0.0.0");
			resourceRepository.save(resource_4);
		}
		
		Resource resource_5 = resourceRepository.findOneByResourceNm("代码管理");
		if (null == resource_5) {
			resource_5 = new Resource();
			resource_5.setResourceNm("代码管理");
			resource_5.setResourceUrl("/v/system/code/list");
			resource_5.setResourceDesc("代码管理");
			resource_5.setInUser(account);
			resource_5.setInDtm(new Date());
			resource_5.setInIp("0.0.0.0");
			resourceRepository.save(resource_5);
		}
		
		Resource resource_6 = resourceRepository.findOneByResourceNm("菜单管理");
		if (null == resource_6) {
			resource_6 = new Resource();
			resource_6.setResourceNm("菜单管理");
			resource_6.setResourceUrl("/v/system/menu/list");
			resource_6.setResourceDesc("菜单管理");
			resource_6.setInUser(account);
			resource_6.setInDtm(new Date());
			resource_6.setInIp("0.0.0.0");
			resourceRepository.save(resource_6);
		}
		
		Resource resource_7 = resourceRepository.findOneByResourceNm("事件管理");
		if (null == resource_7) {
			resource_7 = new Resource();
			resource_7.setResourceNm("事件管理");
			resource_7.setResourceUrl("/v/system/action/list");
			resource_7.setResourceDesc("事件管理");
			resource_7.setInUser(account);
			resource_7.setInDtm(new Date());
			resource_7.setInIp("0.0.0.0");
			resourceRepository.save(resource_7);
		}
		
		// 权限
		Auth auth = authRepository.findOneByAuthNm("超级管理员");
		if (null == auth) {
			auth = new Auth();
			auth.setAuthNm("超级管理员");
			auth.setAuthDesc("超级管理员");
			auth.setInUser(account);
			auth.setInDtm(new Date());
			auth.setInIp("0.0.0.0");
			
			Set<Resource> resources = new HashSet<Resource>();
			resources.add(resource_1);
			resources.add(resource_2);
			resources.add(resource_3);
			resources.add(resource_4);
			resources.add(resource_5);
			resources.add(resource_6);
			resources.add(resource_7);
			auth.setResources(resources);
			
			authRepository.save(auth);
		}
		
		// 角色
		Role role = roleRepository.findOneByRoleNm("超级管理员");
		if (null == role) {
			role = new Role();
			role.setRoleNm("超级管理员");
			role.setRoleDesc("超级管理员");
			role.setInUser(account);
			role.setInDtm(new Date());
			role.setInIp("0.0.0.0");
			
			Set<Auth> auths = new HashSet<Auth>();
			auths.add(auth);
			role.setAuths(auths);
			
			roleRepository.save(role);
		}
		
		// 菜单
		Menu menu_1 = menuRepository.findOneByMenuNmAndMlevel("系统管理", 1);
		if (null == menu_1) {
			menu_1 = new Menu();
			menu_1.setMenuNm("系统管理");
			menu_1.setMlevel(1);
			menu_1.setOrd(1);
			menu_1.setInUser(account);
			menu_1.setInDtm(new Date());
			menu_1.setInIp("0.0.0.0");
			menuRepository.save(menu_1);
		}

		Menu menu_2 = menuRepository.findOneByTopMenuMenuCdAndMenuNmAndMlevel(menu_1.getMenuCd(), "账号管理", 2);
		if (null == menu_2) {
			menu_2 = new Menu();
			menu_2.setTopMenu(menu_1);
			menu_2.setMenuNm("账号管理");
			menu_2.setMenuUrl(resource_1);
			menu_2.setMlevel(2);
			menu_2.setOrd(1);
			menu_2.setInUser(account);
			menu_2.setInDtm(new Date());
			menu_2.setInIp("0.0.0.0");
			menuRepository.save(menu_2);
		}

		Menu menu_3 = menuRepository.findOneByTopMenuMenuCdAndMenuNmAndMlevel(menu_1.getMenuCd(), "角色管理", 2);
		if (null == menu_3) {
			menu_3 = new Menu();
			menu_3.setTopMenu(menu_1);
			menu_3.setMenuNm("角色管理");
			menu_3.setMenuUrl(resource_2);
			menu_3.setMlevel(2);
			menu_3.setOrd(2);
			menu_3.setInUser(account);
			menu_3.setInDtm(new Date());
			menu_3.setInIp("0.0.0.0");
			menuRepository.save(menu_3);
		}

		Menu menu_4 = menuRepository.findOneByTopMenuMenuCdAndMenuNmAndMlevel(menu_1.getMenuCd(), "权限管理", 2);
		if (null == menu_4) {
			menu_4 = new Menu();
			menu_4.setTopMenu(menu_1);
			menu_4.setMenuNm("权限管理");
			menu_4.setMenuUrl(resource_3);
			menu_4.setMlevel(2);
			menu_4.setOrd(3);
			menu_4.setInUser(account);
			menu_4.setInDtm(new Date());
			menu_4.setInIp("0.0.0.0");
			menuRepository.save(menu_4);
		}

		Menu menu_5 = menuRepository.findOneByTopMenuMenuCdAndMenuNmAndMlevel(menu_1.getMenuCd(), "资源管理", 2);
		if (null == menu_5) {
			menu_5 = new Menu();
			menu_5.setTopMenu(menu_1);
			menu_5.setMenuNm("资源管理");
			menu_5.setMenuUrl(resource_4);
			menu_5.setMlevel(2);
			menu_5.setOrd(4);
			menu_5.setInUser(account);
			menu_5.setInDtm(new Date());
			menu_5.setInIp("0.0.0.0");
			menuRepository.save(menu_5);
		}

		Menu menu_6 = menuRepository.findOneByTopMenuMenuCdAndMenuNmAndMlevel(menu_1.getMenuCd(), "代码管理", 2);
		if (null == menu_6) {
			menu_6 = new Menu();
			menu_6.setTopMenu(menu_1);
			menu_6.setMenuNm("代码管理");
			menu_6.setMenuUrl(resource_5);
			menu_6.setMlevel(2);
			menu_6.setOrd(5);
			menu_6.setInUser(account);
			menu_6.setInDtm(new Date());
			menu_6.setInIp("0.0.0.0");
			menuRepository.save(menu_6);
		}
		
		Menu menu_7 = menuRepository.findOneByTopMenuMenuCdAndMenuNmAndMlevel(menu_1.getMenuCd(), "菜单管理", 2);
		if (null == menu_7) {
			menu_7 = new Menu();
			menu_7.setTopMenu(menu_1);
			menu_7.setMenuNm("菜单管理");
			menu_7.setMenuUrl(resource_6);
			menu_7.setMlevel(2);
			menu_7.setOrd(6);
			menu_7.setInUser(account);
			menu_7.setInDtm(new Date());
			menu_7.setInIp("0.0.0.0");
			menuRepository.save(menu_7);
		}
		
		Menu menu_8 = menuRepository.findOneByTopMenuMenuCdAndMenuNmAndMlevel(menu_1.getMenuCd(), "事件管理", 2);
		if (null == menu_8) {
			menu_8 = new Menu();
			menu_8.setTopMenu(menu_1);
			menu_8.setMenuNm("事件管理");
			menu_8.setMenuUrl(resource_7);
			menu_8.setMlevel(2);
			menu_8.setOrd(7);
			menu_8.setInUser(account);
			menu_8.setInDtm(new Date());
			menu_8.setInIp("0.0.0.0");
			menuRepository.save(menu_8);
		}

		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		account.setRoles(roles);
		
		List<Menu> menus = new ArrayList<Menu>();
		menus.add(menu_1);
		menus.add(menu_2);
		menus.add(menu_3);
		menus.add(menu_4);
		menus.add(menu_5);
		menus.add(menu_6);
		menus.add(menu_7);
		menus.add(menu_8);
		
		account.setMenus(menus);
		
		accountRepository.save(account);
	}

}