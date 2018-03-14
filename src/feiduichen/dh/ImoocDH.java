package feiduichen.dh;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class ImoocDH {
	private static String src = "imooc security dh";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		jdkDH();
	}

	public static void jdkDH() {
		try {
			// 1.��ʼ�����ͷ���Կ
			KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			senderKeyPairGenerator.initialize(512);
			KeyPair senderKeypair = senderKeyPairGenerator.generateKeyPair();
			byte[] senderPublicKeyEnc = senderKeypair.getPublic().getEncoded();// ���ͷ���Կ�����͸����ܷ������硢�ļ���������

			// 2.��ʼ�����ܷ���Կ
			KeyFactory receiverkeyFactory = KeyFactory.getInstance("DH");
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEnc);
			PublicKey receiverPublicKey = receiverkeyFactory.generatePublic(x509EncodedKeySpec);
			DHParameterSpec dhParameterSpec = ((DHPublicKey) receiverPublicKey).getParams();
			KeyPairGenerator reKeyPairGenerator = KeyPairGenerator.getInstance("DH");
			reKeyPairGenerator.initialize(dhParameterSpec);
			KeyPair receiverKeyPair = reKeyPairGenerator.generateKeyPair();
			PrivateKey receiverPrivateKey = receiverKeyPair.getPrivate();
			byte[] receiverPublicKeyEnc = receiverKeyPair.getPublic().getEncoded();

			// 3.��Կ����
			KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
			receiverKeyAgreement.init(receiverPrivateKey);
			receiverKeyAgreement.doPhase(receiverPublicKey, true);
			SecretKey receiverDesKey = receiverKeyAgreement.generateSecret("DES");

			KeyFactory senderKeyFactory = KeyFactory.getInstance("DH");
			x509EncodedKeySpec = new X509EncodedKeySpec(receiverPublicKeyEnc);
			PublicKey senderPublicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec);
			KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
			senderKeyAgreement.init(senderKeypair.getPrivate());
			senderKeyAgreement.doPhase(senderPublicKey, true);

			SecretKey senderDesKey = senderKeyAgreement.generateSecret("DES");
			if (Objects.equals(receiverDesKey, senderDesKey)) {
				System.out.println("˫����Կ��ͬ");
			}

			// 4.����
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, senderDesKey);
			byte[] result = cipher.doFinal(src.getBytes());
			System.out.println("jdk-dh-eccrypt:" + Base64.encodeBase64String(result));

			// 5.����
			cipher.init(Cipher.DECRYPT_MODE, senderDesKey);
			result = cipher.doFinal(result);
			System.out.println("jdk-dh-eccrypt:" + new String(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
