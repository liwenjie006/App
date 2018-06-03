package lwj.app.models.business.comp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lwj.app.models.Base;
import lwj.app.models.business.user.User;

/**
 * 公司
 * @author LF
 * @version 1.0
 * @since JDK 8
 */
@Entity
@Table(name="TBL_COMP")
@Data
@EqualsAndHashCode(callSuper=false)
@ToString(callSuper=false)
public class Comp extends Base {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 编码 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int compCd;

	/** 名 */
	@Column(length=50, unique=true, nullable=false)
	private String compNm;
	
	/** 描述 */
	@Column(length=250, nullable=false)
	private String compDesc;
	
	/** 公司管理员 */
	@OneToOne(cascade= CascadeType.REFRESH)
	@JoinColumn(name="accountCd", unique=true)
	private User mng;
	
}