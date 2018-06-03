package lwj.app.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;
import lwj.app.models.system.account.Account;

/**
 * 基础类
 * 基于代码复用和模型分离的思想，在项目开发中使用JPA的@MappedSuperclass注解将实体类的多个属性分别封装到不同的非实体类中。
 * 标注为@MappedSuperclass的类将不是一个完整的实体类，他将不会映射到数据库表，但是他的属性都将映射到其子类的数据库字段中。
 * 此外，这样的类还可以直接标注@EntityListeners实体监听器，他的作用范围仅在其所有继承类中，并且实体监听器同样可以保被其子类继承或重载。
 * 标注为@MappedSuperclass的类其属性最好设置为protected或default类型的，以保证其同一个包下的子类可以直接调用它的属性。便于实体监听器或带参数构造函数的操作。
 * @author LF
 * @version 1.0
 * @since JDK 8
 * 
 */
@MappedSuperclass
@Data
public class Base implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/** 是否使用 */
	@Column(nullable=false)
	protected boolean enabled = true;
	
	/** 登录用户 */
	@OneToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="inUser", updatable=false)
	protected Account inUser;
	
	/** 登录时间 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(updatable=false)
	protected Date inDtm;

	/** 登录IP */
	@Column(length=24, updatable=false)
	protected String inIp;
	
	/** 修改用户 */
	@OneToOne(cascade=CascadeType.REFRESH, fetch=FetchType.LAZY)
	@JoinColumn(name="upUser", insertable=false)
	protected Account upUser;
	
	/** 修改时间 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(insertable=false)
	protected Date upDtm;

	/** 修改IP */
	@Column(length=24, insertable=false)
	protected String upIp;
	
	@Transient
	protected String result;
	
}