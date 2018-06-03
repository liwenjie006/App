package lwj.app.models.system.auth;

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
import lwj.app.models.system.resource.Resource;

/**
 * 权限
 * @author LF
 * @version 1.0
 * @since JDK 8
 */
@Entity
@Table(name="SYS_AUTH")
@Data
@EqualsAndHashCode(callSuper=false, exclude={ "resources" })
@ToString(callSuper=false, exclude={ "resources" })
public class Auth extends Base {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 编码 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int authCd;

	/** 名 */
	@Column(length=50, nullable=false)
	private String authNm;

	/** 描述 */
	@Column(length=250)
	private String authDesc;
	
	/** 资源集合 */
	@ManyToMany(cascade=CascadeType.REFRESH)
	@JoinTable(
			name="SYS_AUTH_X_RESOURCE",
			joinColumns=@JoinColumn(name="authCd"),
			inverseJoinColumns=@JoinColumn(name="resourceCd"))
	private Set<Resource> resources;


}