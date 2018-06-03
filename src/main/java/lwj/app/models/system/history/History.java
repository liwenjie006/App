package lwj.app.models.system.history;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lwj.app.models.Base;
import lwj.app.models.system.resource.Resource;
import lwj.app.models.system.role.Role;

/**
 * 操作记录
 * @author LF
 * @version 1.0
 * @since JDK 8
 */
@Entity
@Table(name="SYS_HISTORY")
@Data
@EqualsAndHashCode(callSuper=false, exclude={ "role", "resource" })
@ToString(callSuper=false, exclude={ "role", "resource" })
public class History extends Base {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 编码 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int historyCd;

	@Column(length=50)
	private String sessionId;
	
	@Column(length=100)
	private String logMsg;
	
	/** 角色 */
	@OneToOne(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
	private Role role;
	
	/** 资源 */
	@OneToOne(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
	private Resource resource;
	
}
