package lwj.app.models.business.user;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lwj.app.models.business.dept.Dept;
import lwj.app.models.system.account.Account;

/**
 * 用户信息
 * @author LF
 * @version 1.0
 * @since JDK 8
 */
@Entity
@Table(name="TBL_USER")
@PrimaryKeyJoinColumn(name="accountCd")
@Data
@EqualsAndHashCode(callSuper=false, exclude={ "dept" })
@ToString(callSuper=false, exclude={ "dept" })
public class User extends Account {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 描述 */
	@Column(length=250, nullable=false)
	private String userDesc;
	
	/** 照片 */
	@Column(length=250)
	private String userPhoto;
	
	/** 生日 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	@Past
	private Date userBirthday;
	
	/** 部门 */
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name="deptCd")
	private Dept dept;
	
}