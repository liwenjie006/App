package lwj.app.utils.cryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @see MD5 信息摘要的实现
 * @author LF6
 *
 */
public class Md5 {
	
	/**
	 * @see MD5生成器
	 * @param source
	 * @return
	 */
	public static String md5(String source) {
		byte[] bytes = null;
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");			// 声明MD5摘要生成器
			bytes = md5.digest(source.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return new Hex().byteToHex(bytes);
	}
}
