package it.amedeo.tmp;

public class EncryptDecryptPass {
	public static void main(String[] args) {
		try {
			String password = "S";
			System.out.println("plain pass=" + password);
			String encryptedPassword = AESCrypt.encrypt(password);
			System.out.println("encrypted pass=" + encryptedPassword);
			String decryptedPassword = AESCrypt.decrypt(encryptedPassword);
			System.out.println("decrypted pass=" + decryptedPassword);
		} catch (Exception e) {
			System.out.println("bug" + e.getMessage());
		}
	}
}
