package lwj.app.models.system.role;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lwj.app.models.Base;
import lwj.app.models.system.auth.Auth;

/**
 * 帐号
 * @author LF
 * @version 1.0
 * @since JDK 8
 */
@Entity
@Table(name="SYS_ROLE")
@Data
@EqualsAndHashCode(callSuper=false, exclude={ "auths" })
@ToString(callSuper=false, exclude={ "auths" })
public class Role extends Base {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 编码 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int roleCd;

	/** 名 */
	@Column(length=50, nullable=false)
	private String roleNm;

	/** 描述 */
	@Column(length=250)
	private String roleDesc;
	
	/** 权限集合 */
	@ManyToMany(cascade=CascadeType.REFRESH)
	@JoinTable(
			name="SYS_ROLE_X_AUTH",
			joinColumns=@JoinColumn(name="roleCd"),
			inverseJoinColumns=@JoinColumn(name="authCd"))
	private Set<Auth> auths;


}