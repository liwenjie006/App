package lwj.app;

import javax.annotation.PreDestroy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lwj.app.utils.system.LogUtil;

/**
 * App 初始化
 * @author LF
 *
 */
/* @SpringBootApplication 相当于同时指定@Configuration @EnableAutoConfiguration @ComponentScan 3个注解*/
@SpringBootApplication
public class AppApplication {

	private LogUtil logUtil = new LogUtil(AppApplication.class);
	
	/**
	 * 项目启动函数
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}
	
	/**
	 * 项目关闭时执行的函数
	 */
	@PreDestroy
	public void closeApp() {
		logUtil.print("App closed!");
	}
	
}