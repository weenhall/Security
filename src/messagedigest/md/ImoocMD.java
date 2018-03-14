package messagedigest.md;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD4Digest;
import org.bouncycastle.crypto.digests.MD5Digest;

public class ImoocMD {

	private static String src = "imooc security md";

	public static void main(String[] args) {
		//cc简化了jdk的一些操作,而Jdk是一些底层的操作
		//bc提供了另一种方式
		jdkMD2();
		ccMD2();
		jdkMD5();
		bcMD5();
		ccMD5();
		bcMD4();
	}

	public static void jdkMD5() {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] md5Bytes = md.digest(src.getBytes());
			System.out.println("JDKMD5:" + Hex.encodeHexString(md5Bytes));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void bcMD5() {
		Digest digest = new MD5Digest();
		digest.update(src.getBytes(), 0, src.getBytes().length);
		byte[] md5Bytes = new byte[digest.getDigestSize()];
		digest.doFinal(md5Bytes, 0);
		System.out.println("bcMD5:" + org.bouncycastle.util.encoders.Hex.toHexString(md5Bytes));
	}
	
	public static void ccMD5(){
		System.out.println("ccMD5:"+DigestUtils.md5Hex(src.getBytes()));
	}

	public static void jdkMD2() {
		try {
			MessageDigest md = MessageDigest.getInstance("MD2");
			byte[] md5Bytes = md.digest(src.getBytes());
			System.out.println("JDKMD2:" + Hex.encodeHexString(md5Bytes));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void ccMD2(){
		System.out.println("ccMD2"+DigestUtils.md2Hex(src.getBytes()));
	}

	public static void bcMD4() {
		Digest digest = new MD4Digest();
		digest.update(src.getBytes(), 0, src.getBytes().length);
		byte[] md4Bytes = new byte[digest.getDigestSize()];
		digest.doFinal(md4Bytes, 0);
		System.out.println("bcMD4:" + org.bouncycastle.util.encoders.Hex.toHexString(md4Bytes));
	}
}
