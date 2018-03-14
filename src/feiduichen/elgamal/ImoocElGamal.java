package feiduichen.elgamal;

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.spec.DHParameterSpec;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class ImoocElGamal {

	private static String src = "imooc security elgamal";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		bcElGamal();
	}

	public static void bcElGamal() {
		// 公钥加密，私钥解密
		Security.addProvider(new BouncyCastleProvider());
		try {
			// 1.初始化密钥
			AlgorithmParameterGenerator algorithmParameterGenerator = AlgorithmParameterGenerator
					.getInstance("ElGamal");
			algorithmParameterGenerator.init(512);
			AlgorithmParameters algorithmParameters = algorithmParameterGenerator.generateParameters();
			DHParameterSpec dhParameterSpec = (DHParameterSpec) algorithmParameters
					.getParameterSpec(DHParameterSpec.class);
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ElGamal");
			keyPairGenerator.initialize(dhParameterSpec, new SecureRandom());
			KeyPair keyPair = keyPairGenerator.generateKeyPair();
			PublicKey elGamalPublicKey = keyPair.getPublic();
			PrivateKey elGamalPrivateKey = keyPair.getPrivate();
			System.out.println("Public Key:" + Base64.encodeBase64String(elGamalPublicKey.getEncoded()));
			System.out.println("Private Key:" + Base64.encodeBase64String(elGamalPrivateKey.getEncoded()));
			System.out.println("---------------------------------------");

			// 2.私钥加密、公钥解密--加密
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(elGamalPrivateKey.getEncoded());
			KeyFactory keyFactory = KeyFactory.getInstance("ElGamal");
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			Cipher cipher = Cipher.getInstance("ElGamal");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			byte[] result = cipher.doFinal(src.getBytes());
			System.out.println("私钥加密、公钥解密--加密:" + Base64.encodeBase64String(result));

			// 3.私钥加密、公钥解密--解密
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(elGamalPublicKey.getEncoded());
			keyFactory = KeyFactory.getInstance("ElGamal");
			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			cipher = Cipher.getInstance("ElGamal");
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			result = cipher.doFinal(result);
			System.out.println("私钥加密、公钥解密--解密:" + new String(result));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
