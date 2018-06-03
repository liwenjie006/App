package lwj.app.models.business.menu;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lwj.app.models.Base;
import lwj.app.models.system.resource.Resource;

/**
 * 菜单
 * @author LF
 * @version 1.0
 * @since JDK 8
 */
@Entity
@Table(name="SYS_MENU")
@Data
@EqualsAndHashCode(callSuper=false, exclude={ "topMenu", "subMenu" })
@ToString(callSuper=false, exclude={ "topMenu", "subMenu" })
public class Menu extends Base {

	public interface TopMenuView {};
	public interface SubMenuView {};
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 编码 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int menuCd;

	/** 名 */
	@Column(length=100, nullable=false)
	private String menuNm;

	/** 图标 */
	@Column(length=100)
	private String menuIcon;

	/** 类型 */
	@Column(length=1, nullable=false)
	private int mlevel = 1;

	/** 顺序 */
	@Column(length=2, nullable=false)
	private int ord = 1;

	/** 上级菜单 */
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="TOP_MENU_CD")
	@JsonView(TopMenuView.class)
	private Menu topMenu;

	/** 下级菜单 */
	@OneToMany(cascade= { CascadeType.REFRESH, CascadeType.REMOVE }, mappedBy="topMenu")
	@JsonView(SubMenuView.class)
	private List<Menu> subMenu;

	@OneToOne(cascade= { CascadeType.REFRESH })
	@JoinColumn(name="MENU_URL")
	private Resource menuUrl;
	
}