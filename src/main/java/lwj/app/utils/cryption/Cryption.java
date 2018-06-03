package lwj.app.utils.cryption;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Cryption {

	/**
	 * @see 2进制转16进制
	 * @param b
	 * @return
	 */
	public static String byteToHex(byte[] bytes) {
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
	
	/**
	 * @see 取得硬件信息
	 * @return
	 */
	public static String getHardInfo() {
		
		return "00112233445566";
	}
	
	/**
	 * @see MD5
	 * @param source
	 */
	public static void md5(String source) {
		MessageDigest md5 = null;								// 此类实现生成摘要
		byte[] bytes = null;
		try {
			md5 = MessageDigest.getInstance("MD5");				// 声明MD5摘要
			
			bytes = md5.digest(source.getBytes("UTF-8"));
			
			System.out.println(new String(byteToHex(bytes)));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @see 3DES
	 * @param source
	 */
	public static void des(String source) {
		KeyGenerator keyGenerator = null;								// 提供对称密钥生成器的功能，支持各种算法
		SecretKey key = null;
		Cipher cipher = null;											// 负责完成加密或解密工作
		SecretKeySpec keys = null;
		byte[] byteFina = null;
		
		try {
			keyGenerator = KeyGenerator.getInstance("DESede");			// DES 密钥
			
			String hardInfo = getHardInfo();							// 获取硬件信息
			
			keyGenerator.init(new SecureRandom(hardInfo.getBytes()));	// 初始化密钥 SecureRandom类提供加密的强随机数
			
			key = keyGenerator.generateKey();							// 生成密钥
			
			cipher = Cipher.getInstance("DESede");						// 加密主体
			
			keys = new SecretKeySpec(key.getEncoded(), "DESede");
			
			cipher.init(Cipher.ENCRYPT_MODE, keys);						// 加密主体初始化
			
			byteFina = cipher.doFinal(source.getBytes("UTF-8"));		// 执行加密
			
			System.out.println("加密后==" + new String(byteToHex(byteFina)));
			
			cipher.init(Cipher.DECRYPT_MODE, keys);						// 解密主体初始化
			
			byteFina = cipher.doFinal(byteFina);						// 执行解密
			
			System.out.println("解密后==" + new String(byteFina));
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * @see AES
	 * @param source
	 */
	public static void aes(String source) {
		KeyGenerator keyGenerator = null;
		SecretKey key = null;
		SecretKeySpec keys = null;
		Cipher cipher = null;
		String hardInfo = getHardInfo();									// 获取硬件信息
		byte[] bytes = null;
		
		try {
			keyGenerator = KeyGenerator.getInstance("AES");					// AES密钥
			
			keyGenerator.init(128, new SecureRandom(hardInfo.getBytes()));	// 初始化密钥
			
			key = keyGenerator.generateKey();								// 取得密钥
			
			keys = new SecretKeySpec(key.getEncoded(), "AES");				// 生成AES密钥
			
			cipher = Cipher.getInstance("AES");								// 创建密码器
			
			cipher.init(Cipher.ENCRYPT_MODE, keys);							// 初始化加密
			
			bytes = cipher.doFinal(source.getBytes("UTF-8"));				// 执行加密
			
			System.out.println("加密后==" + byteToHex(bytes));
			
			cipher.init(Cipher.DECRYPT_MODE, keys);							// 初始化解密
			
			bytes = cipher.doFinal(bytes);									// 执行解密
			
			System.out.println("解密后==" + byteToHex(bytes));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @see DSA
	 * @param source
	 * @throws Exception 
	 */
	public static void dsa(String source) {
		KeyPairGenerator keyPairGenerator = null;
		KeyPair keys = null;
		PublicKey pubkey = null;
		PrivateKey prikey = null;
		ObjectOutputStream out = null;
		ObjectInputStream in = null;
		Signature signet = null;
		byte[] bytes = null;
		String hardInfo = getHardInfo();											// 获取硬件信息
		try {
			keyPairGenerator = KeyPairGenerator .getInstance("DSA");				// DSA 密钥
			
			keyPairGenerator.initialize(512, new SecureRandom(hardInfo.getBytes()));// 初始化密钥 SecureRandom类提供加密的强随机数
			
			keys = keyPairGenerator.generateKeyPair(); 								//生成密钥组 
			pubkey = keys.getPublic();												//生成密钥公钥 pubkey
			prikey = keys.getPrivate();												//生成密钥私钥 prikey
			
			System.out.println(pubkey);
			System.out.println(prikey);
			
			//分别保存在 myprikey.dat 和 mypubkey.dat 中 , 以便下次不在生成
			//( 生成密钥对的时间比较长
			out = new ObjectOutputStream(new FileOutputStream("D:/myprikey.txt"));
			out.writeObject(prikey);
			out.close();
			out = new ObjectOutputStream(new FileOutputStream("D:/mypubkey.txt"));
			out.writeObject(pubkey);
			out.close();
			
			in = new ObjectInputStream(new FileInputStream("D:/myprikey.txt"));		// 读入签名 
			prikey = (PrivateKey)in.readObject();
			in.close();
			
			signet = Signature.getInstance("DSA");									// 初始一个 Signature 对象 , 并用私钥对信息签名
			signet.initSign(prikey);
			signet.update(source.getBytes());
			bytes = signet.sign();
			
			System.out.println(byteToHex(bytes));
			
			// 把信息和签名保存在一个文件中 (myinfo.dat) 
			out = new ObjectOutputStream(new FileOutputStream("D:/myinfo.txt"));
			out.writeObject(source);												// 写入文本
			out.writeObject(bytes);													// 写入签名
			out.close();
			// === 把他的公钥的信息及签名发给其它用户 === 
			
			// === 检查签名 === 
			in = new ObjectInputStream(new FileInputStream("D:/mypubkey.txt"));			// 读入公钥
			pubkey = (PublicKey)in.readObject(); 
			in.close();
			 
			in = new ObjectInputStream(new FileInputStream("D:/myinfo.txt"));	// 读入签名和信息
			String info = (String)in.readObject();												// 读取文本内容
			byte[] signed = (byte[])in.readObject();											// 读取签名
			in.close();
			
			System.out.println(info);
			System.out.println(byteToHex(signed));
			
			signet.initVerify(pubkey);															// 初始化判断需要的公钥
			signet.update(info.getBytes());														// 设置签名
			
			if (signet.verify(signed)) {														// 判断签名
				System.out.println("签名正常");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
