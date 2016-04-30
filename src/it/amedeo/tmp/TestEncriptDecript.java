package it.amedeo.tmp;

public class TestEncriptDecript {
	private static String str="1234";
	public static void main(String[] args) {
		try {
			System.out.println("-originale="  + str);
			StringEncrypter encrypter =  new StringEncrypter(StringEncrypter.DES_ENCRYPTION_SCHEME, StringEncrypter.DEFAULT_ENCRYPTION_KEY );
			String encryptedString = encrypter.encrypt(str);
			
			str = encryptedString;
			System.out.println("-encrypted="  + str);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		try {
			StringEncrypter decrypter =  new StringEncrypter(StringEncrypter.DES_ENCRYPTION_SCHEME, StringEncrypter.DEFAULT_ENCRYPTION_KEY );
			String decryptedString = decrypter.decrypt(str);

			String albe = decryptedString;
			System.out.println("-decrypted="  + albe);
		}
		catch (Exception e) {
		}
	}
}
