package lwj.app.models.business.dept;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lwj.app.models.Base;
import lwj.app.models.business.comp.Comp;
import lwj.app.models.business.user.User;

/**
 * 部门
 * @author LF
 * @version 1.0
 * @since JDK 8
 */
@Entity
@Table(name="TBL_DEPT")
@Data
@EqualsAndHashCode(callSuper=false, exclude={ "comp" })
@ToString(callSuper=false, exclude={ "comp" })
public class Dept extends Base {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 编码 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int deptCd;

	/** 名 */
	@Column(length=50, nullable=false)
	private String deptNm;
	
	/** 描述 */
	@Column(length=250, nullable=false)
	private String deptDesc;
	
	/** 公司 */
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="compCd", nullable=false)
	private Comp comp;
	
	/** 部门管理员 */
	@OneToOne(cascade= CascadeType.REFRESH)
	@JoinColumn(name="accountCd", unique=true)
	private User mng;
	
}