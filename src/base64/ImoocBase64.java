package base64;

import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class ImoocBase64 {

	private static String src = "imooc security base64";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			jdbBase64();
//			commonsCodesbase64();
//			bouncyCastleBase64();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// jdk方式
	public static void jdbBase64() throws IOException {
		BASE64Encoder encoder = new BASE64Encoder();
		String encode = encoder.encode(src.getBytes());
		System.out.println("----jdk方式----");
		System.out.println("密文encode:" + encode);

		BASE64Decoder decoder = new BASE64Decoder();
		System.out.println("明文decoder:" + new String(decoder.decodeBuffer(encode)));
	}

	// commons方式
	public static void commonsCodesbase64() {
		byte[] encodeBytes = Base64.encodeBase64(src.getBytes());
		System.out.println("----commons方式----");
		System.out.println("密文encode:" + new String(encodeBytes));

		byte[] decodeBytes = Base64.decodeBase64(encodeBytes);
		System.out.println("明文decoder:" + new String(decodeBytes));
	}

	// bouncyCastle方式
	public static void bouncyCastleBase64() {
		byte[] encodeBytes = org.bouncycastle.util.encoders.Base64.encode(src.getBytes());
		System.out.println("----bouncyCastle方式----");
		System.out.println("密文encode:" + new String(encodeBytes));

		byte[] decodeBytes = Base64.decodeBase64(encodeBytes);
		System.out.println("密文encode:" + new String(decodeBytes));
	}
}
