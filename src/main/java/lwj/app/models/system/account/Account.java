package lwj.app.models.system.account;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lwj.app.models.Base;
import lwj.app.models.business.action.Action;
import lwj.app.models.business.menu.Menu;
import lwj.app.models.system.role.Role;

/**
 * 帐号
 * @author LF
 * @version 1.0
 * @since JDK 8
 */
@Entity
@Table(name="SYS_ACCOUNT")
@Inheritance(strategy=InheritanceType.JOINED)
@Data
@EqualsAndHashCode(callSuper=false, exclude={ "roles", "menus", "actions" })
@ToString(callSuper=false, exclude={ "roles", "menus", "actions" })
public class Account extends Base {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/*
	@Column(
		name = 可选，列名（默认值为属性名）。
		unique = 可选，是否在该列上设置唯一约束（默认false）。
		nullable = 可选，是否设置该列的值可以为空（默认true）。
		insertable = 可选，该列是否作为生成的insert语句中的一列（默认true）。
		updateable = 可选，该列是否作为生成的update语句中的一列（默认true）。
		length  = 可选，列长度（默认255）。
		precision = 可选，列十进制精度（默认0）。
		scale = 可选，如果列十进制数值范围可用，在此设置（默认0）。
	)*/

	/** 编码 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int accountCd;

	/** 登录ID */
	@Column(length=50, unique=true, nullable=false)
	@NotBlank(message="帐号ID不能为空")
	private String accountId;

	/** 登录密码 */
	@Column(length=60, nullable=false)
	@NotBlank(message="帐号密码不能为空")
	private String accountPw;

	/** 名 */
	@Column(length=50, nullable=false)
	@NotBlank(message="帐号名称不能为空")
	private String accountNm;

	/** 未锁定 */
	@Column(nullable=false)
	private boolean nonLocked = true;

	/** 未过期 */
	@Column(nullable=false)
	private boolean nonExpired = true;

	/** 凭证未过期 */
	@Column(nullable=false)
	private boolean credentialsNonExpired = true;

	/** 手机 */
	@Column(length=20, unique=true, nullable=false)
	@NotBlank(message="手机号码不能为空")
	private String tel;
	
	/** 邮箱 */
	@Column(length=200, unique=true, nullable=false)
	@NotBlank(message="邮箱不能为空")
	private String email;
	
	
	/**
	 * 1>只有OneToOne，OneToMany，ManyToMany上才有mappedBy属性，ManyToOne不存在该属性；(主方拥有 mappedBy)
	 * 2>mappedBy标签一定是定义在被拥有方的，他指向拥有方；
	 * 3>mappedBy的含义，应该理解为，拥有方能够自动维护跟被拥有方的关系，当然，如果从被拥有方，通过手工强行来维护拥有方的关系也是可以做到的；
	 * 4>mappedBy跟joinColumn/JoinTable总是处于互斥的一方，可以理解为正是由于拥有方的关联被拥有方的字段存在，拥有方才拥有了被拥有方。mappedBy这方定义JoinColumn/JoinTable总是失效的，不会建立对应的字段或者表。
	 */
	
	/** 角色集合 */
	@ManyToMany(cascade=CascadeType.REFRESH)
	@JoinTable(
			name="SYS_ACCOUNT_X_ROLE",
			joinColumns=@JoinColumn(name="accountCd"),
			inverseJoinColumns=@JoinColumn(name="roleCd"))
	@JsonIgnore
	private Set<Role> roles;
	
	/** 菜单集合 */
	@OneToMany(cascade=CascadeType.REFRESH)
	@JoinTable(
			name="SYS_ACCOUNT_X_MENU",
			joinColumns=@JoinColumn(name="accountCd"),
			inverseJoinColumns=@JoinColumn(name="menuCd"))
	@JsonIgnore
	@OrderBy("mlevel, ord")
	private List<Menu> menus;
	
	/** 事件集合 */
	@OneToMany(cascade=CascadeType.REFRESH)
	@JoinTable(
			name="SYS_ACCOUNT_X_ACTION",
			joinColumns=@JoinColumn(name="accountCd"),
			inverseJoinColumns=@JoinColumn(name="actionCd"))
	@JsonIgnore
	private List<Action> actions;

}
