package lwj.app.utils.cryption;

/**
 * 进制转换
 * @author LF6
 *
 */
public class Hex {
	
	/**
	 * @see 2进制转16进制
	 * @param bytes
	 * @return
	 */
	public String byteToHex(byte[] bytes) {
		String returnStr = "";									// 返回字符串
		String tempStr = "";									// 临时字符串
		
		for (int n = 0; n < bytes.length; n++) {				// 便利字节数组
			
			tempStr = (Integer.toHexString(bytes[n] & 0XFF));	// 2进制转16进制字符
			
			if (tempStr.length() == 1) {						// 16进制字符长度小于2时补零
				returnStr = returnStr + "0" + tempStr;
			} else {
				returnStr = returnStr + tempStr;
			}
		}
		
		return returnStr;
	}
}
