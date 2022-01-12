package hashForPassword;

import java.security.*;

public class PassHash {
	public static String getSecurePassword(String passwordToHash, byte[] salt) throws NoSuchAlgorithmException, NoSuchProviderException {
		String generatedPassword = null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(salt);
			byte[] bytes = md.digest(passwordToHash.getBytes());
			
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < bytes.length; i++) {
				sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			generatedPassword = sb.toString();
		}
		catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return generatedPassword;
	}
	
	public static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException{
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		
		return salt;
	}
}
