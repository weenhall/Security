package duichen.des;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class ImoocDES {

	private static String src = "imooc security des";

	public static void main(String[] args) {
		jdkDES();
		bcDES();
	}

	// ʹ��jdk�ķ�ʽʵ��
	public static void jdkDES() {
		try {
			// ����key
			KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
			keyGenerator.init(56);
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] bytesKey = secretKey.getEncoded();

			// KEYת��
			DESKeySpec desKeySpec = new DESKeySpec(bytesKey);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
			Key convertSecretKey = factory.generateSecret(desKeySpec);

			// ����
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
			byte[] result = cipher.doFinal(src.getBytes());
			System.out.println("jdk-des-encrypt:" + Hex.encodeHexString(result));

			// ����
			cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
			result = cipher.doFinal(result);
			System.out.println("jdk-des-decrypt:" + new String(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ʹ��bc��ʽʵ��
	public static void bcDES() {
		try {
			Security.addProvider(new BouncyCastleProvider());

			// ����key
			KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
			keyGenerator.init(56);
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] bytesKey = secretKey.getEncoded();

			// KEYת��
			DESKeySpec desKeySpec = new DESKeySpec(bytesKey);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
			Key convertSecretKey = factory.generateSecret(desKeySpec);

			// ����
			Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey);
			byte[] result = cipher.doFinal(src.getBytes());
			System.out.println("bc-des-encrypt:" + Hex.encodeHexString(result));

			// ����
			cipher.init(Cipher.DECRYPT_MODE, convertSecretKey);
			result = cipher.doFinal(result);
			System.out.println("bc-des-decrypt:" + new String(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
