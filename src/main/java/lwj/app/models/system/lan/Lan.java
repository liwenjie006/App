package lwj.app.models.system.lan;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lwj.app.models.Base;
import lwj.app.models.system.code.Code;

/**
 * 帐号
 * @author LF
 * @version 1.0
 * @since JDK 8
 */
@Entity
@Table(name="SYS_LAN", indexes= { @Index(name = "lanId",  columnList="lanId", unique = true) })
@Inheritance(strategy=InheritanceType.JOINED)
@Data
@EqualsAndHashCode(callSuper=false, exclude={ "language" })
@ToString(callSuper=false, exclude={ "language" })
public class Lan extends Base {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 编码 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int lanCd;

	/** ID */
	@Column(length=250, unique=true, nullable=false)
	@NotBlank(message="ID不能为空")
	private String lanId;
	
	/** 内容，中文时ID和内容一致 */
	@Column(length=250, nullable=false)
	@NotBlank(message="内容不能为空")
	private String lanNm;

	/** 语言 */
	@OneToOne(cascade={ CascadeType.REFRESH })
	private Code language;
	
}
