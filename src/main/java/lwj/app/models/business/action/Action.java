package lwj.app.models.business.action;

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
import lwj.app.models.system.resource.Resource;

/**
 * 事件
 * @author LF
 * @version 1.0
 * @since JDK 8
 */
@Entity
@Table(name="SYS_ACTION")
@Data
@EqualsAndHashCode(callSuper=false)
@ToString(callSuper=false)
public class Action extends Base {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 编码 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int actionCd;

	/** 名 */
	@Column(length=100, nullable=false)
	private String actionNm;

	/** URL */
	@OneToOne(cascade= { CascadeType.REFRESH })
	@JoinColumn(name="ACTION_URL")
	private Resource resource;
	
}