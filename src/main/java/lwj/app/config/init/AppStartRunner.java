package lwj.app.config.init;

import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import lwj.app.models.business.menu.Menu;
import lwj.app.models.business.menu.MenuRepository;
import lwj.app.models.system.account.Account;
import lwj.app.models.system.account.AccountRepository;
import lwj.app.models.system.auth.Auth;
import lwj.app.models.system.auth.AuthRepository;
import lwj.app.models.system.code.Code;
import lwj.app.models.system.code.CodeRepository;
import lwj.app.models.system.lan.Lan;
import lwj.app.models.system.lan.LanRepository;
import lwj.app.models.system.resource.Resource;
import lwj.app.models.system.resource.ResourceRepository;
import lwj.app.models.system.role.Role;
import lwj.app.models.system.role.RoleRepository;
import lwj.app.utils.system.LogUtil;

/**
 * 服务器启动后运行的类，初始化系统最基础数据
 * @author LF
 *
 */
@Component
public class AppStartRunner implements ApplicationRunner {

	private LogUtil log = new LogUtil(getClass());

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private LanRepository lanRepository;
	
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

	private String IP = "0.0.0.0";
	
	private String SPLIT = " --- ";
	
	
	
	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		log.print("初始化系统数据。");

		// 管理员信息
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
			account.setInIp(IP);
			accountRepository.save(account);
		}

		
		
		// 登录 国际化 公用代码
		Code lanCode = codeRepository.findOneByName("国际化");
		if (null == lanCode) {
			lanCode = new Code();
			lanCode.setName("国际化");
			lanCode.setCodeDesc("系统中包含的所有国际化信息");
			lanCode.setInUser(account);
			lanCode.setInDtm(new Date());
			lanCode.setInIp(IP);
			codeRepository.save(lanCode);
		}
		
		// 中文
		Code zhCode = codeRepository.findOneByName("zh");
		if (null == zhCode) {
			zhCode = new Code();
			zhCode.setName("zh");
			zhCode.setCodeDesc("zh");
			zhCode.setInUser(account);
			zhCode.setInDtm(new Date());
			zhCode.setInIp(IP);
			zhCode.setTopCode(lanCode);
			codeRepository.save(zhCode);
		}
		
		// 英文
		Code enCode = codeRepository.findOneByName("en");
		if (null == enCode) {
			enCode = new Code();
			enCode.setName("en");
			enCode.setCodeDesc("en");
			enCode.setInUser(account);
			enCode.setInDtm(new Date());
			enCode.setInIp(IP);
			enCode.setTopCode(lanCode);
			codeRepository.save(enCode);
		}
		
		// 韩文
		Code krCode = codeRepository.findOneByName("ko");
		if (null == krCode) {
			krCode = new Code();
			krCode.setName("ko");
			krCode.setCodeDesc("ko");
			krCode.setInUser(account);
			krCode.setInDtm(new Date());
			krCode.setInIp(IP);
			krCode.setTopCode(lanCode);
			codeRepository.save(krCode);
		}
		
		// 登录国际化信息（只限中文）
		Yaml yaml = new Yaml();
		URL url = getClass().getResource("messages.yml");
        if (url != null) {
        	String lanText = yaml.load(new FileInputStream(url.getFile()));
        	String[] lans = lanText.split(SPLIT);
        	for (String lan : lans) {
        		Lan lanTmp = lanRepository.findOneByLanId(lan);
        		if (null == lanTmp) {
        			lanTmp = new Lan();
        			lanTmp.setLanId(lan);
        			lanTmp.setLanNm(lan);
        			lanTmp.setLanguage(zhCode);
        			lanTmp.setInUser(account);
        			lanTmp.setInDtm(new Date());
        			lanTmp.setInIp(IP);
        			
        			log.print(zhCode);
        			
        			log.print(lanTmp);
        			
        			lanRepository.save(lanTmp);
        		}
        	}
        }
		
				
		// 登录事件类型公用代码
		Code codeAction = codeRepository.findOneByName("事件类型");
		if (null == codeAction) {
			codeAction = new Code();
			codeAction.setName("事件类型");
			codeAction.setCodeDesc("系统中包含的所有事件类型");
			codeAction.setInUser(account);
			codeAction.setInDtm(new Date());
			codeAction.setInIp(IP);
			codeRepository.save(codeAction);
		}

		Code codeBtn = codeRepository.findOneByName("按钮");
		if (null == codeBtn) {
			codeBtn = new Code();
			codeBtn.setName("按钮");
			codeBtn.setCodeDesc("菜单中包含的所有按钮");
			codeBtn.setInUser(account);
			codeBtn.setInDtm(new Date());
			codeBtn.setInIp(IP);
			codeBtn.setTopCode(codeAction);
			codeRepository.save(codeBtn);
		}

		Code codeLink = codeRepository.findOneByName("连接");
		if (null == codeLink) {
			codeLink = new Code();
			codeLink.setName("连接");
			codeLink.setCodeDesc("系统中包含的所有连接");
			codeLink.setInUser(account);
			codeLink.setInDtm(new Date());
			codeLink.setInIp(IP);
			codeLink.setTopCode(codeAction);
			codeRepository.save(codeLink);
		}

		Code codeApi = codeRepository.findOneByName("接口");
		if (null == codeApi) {
			codeApi = new Code();
			codeApi.setName("接口");
			codeApi.setCodeDesc("系统中包含的所有接口");
			codeApi.setInUser(account);
			codeApi.setInDtm(new Date());
			codeApi.setInIp(IP);
			codeApi.setTopCode(codeAction);
			codeRepository.save(codeApi);
		}
		
		// 资源
		Resource resourceAccount = resourceRepository.findOneByResourceNm("账号管理");
		if (null == resourceAccount) {
			resourceAccount = new Resource();
			resourceAccount.setResourceNm("账号管理");
			resourceAccount.setResourceUrl("/v/system/account/list");
			resourceAccount.setResourceDesc("账号管理");
			resourceAccount.setInUser(account);
			resourceAccount.setInDtm(new Date());
			resourceAccount.setInIp(IP);
			resourceRepository.save(resourceAccount);
		}
		
		Resource resourceRole = resourceRepository.findOneByResourceNm("角色管理");
		if (null == resourceRole) {
			resourceRole = new Resource();
			resourceRole.setResourceNm("角色管理");
			resourceRole.setResourceUrl("/v/system/role/list");
			resourceRole.setResourceDesc("角色管理");
			resourceRole.setInUser(account);
			resourceRole.setInDtm(new Date());
			resourceRole.setInIp(IP);
			resourceRepository.save(resourceRole);
		}
		
		Resource resourceAuth = resourceRepository.findOneByResourceNm("权限管理");
		if (null == resourceAuth) {
			resourceAuth = new Resource();
			resourceAuth.setResourceNm("权限管理");
			resourceAuth.setResourceUrl("/v/system/auth/list");
			resourceAuth.setResourceDesc("权限管理");
			resourceAuth.setInUser(account);
			resourceAuth.setInDtm(new Date());
			resourceAuth.setInIp(IP);
			resourceRepository.save(resourceAuth);
		}
		
		Resource resourceRes = resourceRepository.findOneByResourceNm("资源管理");
		if (null == resourceRes) {
			resourceRes = new Resource();
			resourceRes.setResourceNm("资源管理");
			resourceRes.setResourceUrl("/v/system/resource/list");
			resourceRes.setResourceDesc("资源管理");
			resourceRes.setInUser(account);
			resourceRes.setInDtm(new Date());
			resourceRes.setInIp(IP);
			resourceRepository.save(resourceRes);
		}
		
		Resource resourceCode = resourceRepository.findOneByResourceNm("代码管理");
		if (null == resourceCode) {
			resourceCode = new Resource();
			resourceCode.setResourceNm("代码管理");
			resourceCode.setResourceUrl("/v/system/code/list");
			resourceCode.setResourceDesc("代码管理");
			resourceCode.setInUser(account);
			resourceCode.setInDtm(new Date());
			resourceCode.setInIp(IP);
			resourceRepository.save(resourceCode);
		}
		
		Resource resourceMenu = resourceRepository.findOneByResourceNm("菜单管理");
		if (null == resourceMenu) {
			resourceMenu = new Resource();
			resourceMenu.setResourceNm("菜单管理");
			resourceMenu.setResourceUrl("/v/system/menu/list");
			resourceMenu.setResourceDesc("菜单管理");
			resourceMenu.setInUser(account);
			resourceMenu.setInDtm(new Date());
			resourceMenu.setInIp(IP);
			resourceRepository.save(resourceMenu);
		}
		
		Resource resourceAction = resourceRepository.findOneByResourceNm("事件管理");
		if (null == resourceAction) {
			resourceAction = new Resource();
			resourceAction.setResourceNm("事件管理");
			resourceAction.setResourceUrl("/v/system/action/list");
			resourceAction.setResourceDesc("事件管理");
			resourceAction.setInUser(account);
			resourceAction.setInDtm(new Date());
			resourceAction.setInIp(IP);
			resourceRepository.save(resourceAction);
		}
		
		// 权限
		Auth auth = authRepository.findOneByAuthNm("超级管理员");
		if (null == auth) {
			auth = new Auth();
			auth.setAuthNm("超级管理员");
			auth.setAuthDesc("超级管理员");
			auth.setInUser(account);
			auth.setInDtm(new Date());
			auth.setInIp(IP);
			
			Set<Resource> resources = new HashSet<>();
			resources.add(resourceAccount);
			resources.add(resourceRole);
			resources.add(resourceAuth);
			resources.add(resourceRes);
			resources.add(resourceCode);
			resources.add(resourceMenu);
			resources.add(resourceAction);
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
			role.setInIp(IP);
			
			Set<Auth> auths = new HashSet<>();
			auths.add(auth);
			role.setAuths(auths);
			
			roleRepository.save(role);
		}
		
		// 菜单
		Menu menuSystem = menuRepository.findOneByMenuNmAndMlevel("系统管理", 1);
		if (null == menuSystem) {
			menuSystem = new Menu();
			menuSystem.setMenuNm("系统管理");
			menuSystem.setMlevel(1);
			menuSystem.setOrd(1);
			menuSystem.setInUser(account);
			menuSystem.setInDtm(new Date());
			menuSystem.setInIp(IP);
			menuRepository.save(menuSystem);
		}

		Menu menuAccount = menuRepository.findOneByTopMenuMenuCdAndMenuNmAndMlevel(menuSystem.getMenuCd(), "账号管理", 2);
		if (null == menuAccount) {
			menuAccount = new Menu();
			menuAccount.setTopMenu(menuSystem);
			menuAccount.setMenuNm("账号管理");
			menuAccount.setMenuUrl(resourceAccount);
			menuAccount.setMlevel(2);
			menuAccount.setOrd(1);
			menuAccount.setInUser(account);
			menuAccount.setInDtm(new Date());
			menuAccount.setInIp(IP);
			menuRepository.save(menuAccount);
		}

		Menu menuRole = menuRepository.findOneByTopMenuMenuCdAndMenuNmAndMlevel(menuSystem.getMenuCd(), "角色管理", 2);
		if (null == menuRole) {
			menuRole = new Menu();
			menuRole.setTopMenu(menuSystem);
			menuRole.setMenuNm("角色管理");
			menuRole.setMenuUrl(resourceRole);
			menuRole.setMlevel(2);
			menuRole.setOrd(2);
			menuRole.setInUser(account);
			menuRole.setInDtm(new Date());
			menuRole.setInIp(IP);
			menuRepository.save(menuRole);
		}

		Menu menuAuth = menuRepository.findOneByTopMenuMenuCdAndMenuNmAndMlevel(menuSystem.getMenuCd(), "权限管理", 2);
		if (null == menuAuth) {
			menuAuth = new Menu();
			menuAuth.setTopMenu(menuSystem);
			menuAuth.setMenuNm("权限管理");
			menuAuth.setMenuUrl(resourceAuth);
			menuAuth.setMlevel(2);
			menuAuth.setOrd(3);
			menuAuth.setInUser(account);
			menuAuth.setInDtm(new Date());
			menuAuth.setInIp(IP);
			menuRepository.save(menuAuth);
		}

		Menu menuResource = menuRepository.findOneByTopMenuMenuCdAndMenuNmAndMlevel(menuSystem.getMenuCd(), "资源管理", 2);
		if (null == menuResource) {
			menuResource = new Menu();
			menuResource.setTopMenu(menuSystem);
			menuResource.setMenuNm("资源管理");
			menuResource.setMenuUrl(resourceRes);
			menuResource.setMlevel(2);
			menuResource.setOrd(4);
			menuResource.setInUser(account);
			menuResource.setInDtm(new Date());
			menuResource.setInIp(IP);
			menuRepository.save(menuResource);
		}

		Menu menuCode = menuRepository.findOneByTopMenuMenuCdAndMenuNmAndMlevel(menuSystem.getMenuCd(), "代码管理", 2);
		if (null == menuCode) {
			menuCode = new Menu();
			menuCode.setTopMenu(menuSystem);
			menuCode.setMenuNm("代码管理");
			menuCode.setMenuUrl(resourceCode);
			menuCode.setMlevel(2);
			menuCode.setOrd(5);
			menuCode.setInUser(account);
			menuCode.setInDtm(new Date());
			menuCode.setInIp(IP);
			menuRepository.save(menuCode);
		}
		
		Menu menuMenu = menuRepository.findOneByTopMenuMenuCdAndMenuNmAndMlevel(menuSystem.getMenuCd(), "菜单管理", 2);
		if (null == menuMenu) {
			menuMenu = new Menu();
			menuMenu.setTopMenu(menuSystem);
			menuMenu.setMenuNm("菜单管理");
			menuMenu.setMenuUrl(resourceMenu);
			menuMenu.setMlevel(2);
			menuMenu.setOrd(6);
			menuMenu.setInUser(account);
			menuMenu.setInDtm(new Date());
			menuMenu.setInIp(IP);
			menuRepository.save(menuMenu);
		}
		
		Menu menuAction = menuRepository.findOneByTopMenuMenuCdAndMenuNmAndMlevel(menuSystem.getMenuCd(), "事件管理", 2);
		if (null == menuAction) {
			menuAction = new Menu();
			menuAction.setTopMenu(menuSystem);
			menuAction.setMenuNm("事件管理");
			menuAction.setMenuUrl(resourceAction);
			menuAction.setMlevel(2);
			menuAction.setOrd(7);
			menuAction.setInUser(account);
			menuAction.setInDtm(new Date());
			menuAction.setInIp(IP);
			menuRepository.save(menuAction);
		}

		Set<Role> roles = new HashSet<>();
		roles.add(role);
		account.setRoles(roles);
		
		List<Menu> menus = new ArrayList<>();
		menus.add(menuSystem);
		menus.add(menuAccount);
		menus.add(menuRole);
		menus.add(menuAuth);
		menus.add(menuResource);
		menus.add(menuCode);
		menus.add(menuMenu);
		menus.add(menuAction);
		
		account.setMenus(menus);
		
		accountRepository.save(account);
	}
	
}