package lwj.app.models.system.resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lwj.app.models.Base;
import lwj.app.models.business.menu.Menu.SubMenuView;
import lwj.app.models.business.menu.Menu.TopMenuView;

/**
 * 资源
 * @author LF
 * @version 1.0
 * @since JDK 8
 */
@Entity
@Table(name="SYS_RESOURCE")
@Data
@EqualsAndHashCode(callSuper=false)
@ToString(callSuper=false)
public class Resource extends Base {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 编码 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@JsonView({ SubMenuView.class })
	private int resourceCd;

	/** 名 */
	@Column(length=50, nullable=false)
	private String resourceNm;
	
	/** URL */
	@Column(length=250, nullable=false)
	@JsonView(TopMenuView.class)
	private String resourceUrl;

	/** 描述 */
	@Column(length=250)
	private String resourceDesc;
	
}