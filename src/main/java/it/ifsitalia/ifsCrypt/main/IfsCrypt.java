package it.ifsitalia.ifsCrypt.main;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class IfsCrypt {
	
	
	
	private static SecretKeySpec secretKey;
	private static byte[] key;
	
	public static void main(String[] args) {
		final String secretKey = "bPeShVmYq3t6w9z$B&E)H@McQfTjWnZr";
		
		if (args.length<2) {
			System.out.println("Occorre passare al tool 2 parametri. Il primo per indicare se ci vuole criptare (C) o decriptare (D) la stringa, il secondo la stringa stessa");
		}else if(args.length==2) {
			String sType = args[0];
			String text = args[1];
			if (sType.contains("C")) {
				System.out.println(IfsCrypt.encrypt(text, secretKey));
			}else if (sType.contains("D")) {
				System.out.println(IfsCrypt.decrypt(text, secretKey));
			}else {
				System.out.println("I parametri ammessi sono C per cryptare la stringa e D per decrittare una stringa già criptata");
			}
		}else {
			System.out.println("Occorre passare al tool 2 parametri. Il primo per indicare se ci vuole criptare (C) o decriptare (D) la stringa, il secondo la stringa stessa");
		}
	}
	
	public static void setKey(final String myKey) {
	    MessageDigest sha = null;
	    try {
	      key = myKey.getBytes("UTF-8");
	      sha = MessageDigest.getInstance("SHA-1");
	      key = sha.digest(key);
	      key = Arrays.copyOf(key, 16);
	      secretKey = new SecretKeySpec(key, "AES");
	    } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
	      e.printStackTrace();
	    }
	  }

	  public static String encrypt(final String strToEncrypt, final String secret) {
	    try {
	      setKey(secret);
	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	      cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	      return Base64.getEncoder()
	        .encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
	    } catch (Exception e) {
	      System.out.println("Error while encrypting: " + e.toString());
	    }
	    return null;
	  }

	  public static String decrypt(final String strToDecrypt, final String secret) {
	    try {
	      setKey(secret);
	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
	      cipher.init(Cipher.DECRYPT_MODE, secretKey);
	      return new String(cipher.doFinal(Base64.getDecoder()
	        .decode(strToDecrypt)));
	    } catch (Exception e) {
	      System.out.println("Error while decrypting: " + e.toString());
	    }
	    return null;
	  }
}
