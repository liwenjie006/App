package lwj.app.models.system.code;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lwj.app.models.Base;

/**
 * 共同代码
 * @author LF
 * @version 1.0
 * @since JDK 8
 */
@Entity
@Table(name="SYS_CODE")
@Data
@EqualsAndHashCode(callSuper=false, exclude={ "topCode", "codes" })
@ToString(callSuper=false, exclude={ "topCode", "codes" })
public class Code extends Base {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 编码 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int code;

	/** 名 */
	@Column(length=50, nullable=false)
	private String name;

	/** 描述 */
	@Column(length=250)
	private String codeDesc;
	
	/** 额外信息1 */
	@Column(length=50)
	private String ref1;
	
	/** 额外信息2*/
	@Column(length=50)
	private String ref2;
	
	/** 上级代码 */
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="topCode")
	private Code topCode;
	
	/** 集合 */
	@OneToMany(cascade=CascadeType.REFRESH, mappedBy="topCode")
	private Set<Code> codes;

}