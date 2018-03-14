package messagedigest.hmac;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

public class ImoocHmac {

	private static String src = "imooc security hamc";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		jdkHmacMD5();
		bcHmacMD5();
	}

	public static void jdkHmacMD5() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");// 初始化KeyGenerator
			@SuppressWarnings("unused")
			SecretKey secretKey = keyGenerator.generateKey();// 产生密钥
			//byte[] key = secretKey.getEncoded();// 获得密钥
			byte[] key =Hex.decodeHex(new char[]{'a','a','a','a','a','a','a','a','a','a'});
			SecretKey restorSecertKey = new SecretKeySpec(key, "HmacMD5");// 还原密钥
			Mac mac = Mac.getInstance(restorSecertKey.getAlgorithm());// 实例化MAC
			mac.init(restorSecertKey);// 初始化Mac
			byte[] hmacMD5Bytes = mac.doFinal(src.getBytes());// 执行摘要
			System.out.println("jdk hmacMD5:" + Hex.encodeHexString(hmacMD5Bytes));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void bcHmacMD5(){
		HMac hmac=new HMac(new MD5Digest());
		hmac.init(new KeyParameter(org.bouncycastle.util.encoders.Hex.decode("aaaaaaaaaa")));
		hmac.update(src.getBytes(),0,src.getBytes().length);
		
		byte[] hmacMD5Bytes=new byte[hmac.getMacSize()];
		hmac.doFinal(hmacMD5Bytes, 0);
		System.out.println("bc hmacMD5:"+org.bouncycastle.util.encoders.Hex.toHexString(hmacMD5Bytes));
	}
}
