package lwj.app.models.business.menu;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import lwj.app.models.BaseRepository;

/**
 * 菜单
 * @author LF
 *
 */
@Repository
public interface MenuRepository extends BaseRepository<Menu, Serializable> {

	/**
	 * 通过菜单名和级别查询菜单
	 * @param menuNm
	 * @param mlevel
	 * @return Menu
	 */
	public Menu findOneByMenuNmAndMlevel(String menuNm, int mlevel);
	
	/**
	 * 通过父菜单，菜单名，菜单级别，取得菜单信息
	 * @param menuCd
	 * @param menuNm
	 * @param mlevel
	 * @return Menu
	 */
	public Menu findOneByTopMenuMenuCdAndMenuNmAndMlevel(int menuCd, String menuNm, int mlevel);
	
}