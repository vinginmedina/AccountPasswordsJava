package net.isageek.ving.accountpasswords;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;

import javax.crypto.Cipher;

import java.awt.event.*;

import javax.swing.*;

public class AccountPasswords {
	
	/**
	 * String to hold name of the encryption algorithm.
	 */
	public static final String ALGORITHM = "RSA";

	/**
	 * String to hold the name of the private key file.
	 */
	public static String PRIVATE_KEY_FILE;

	/**
	 * String to hold name of the public key file.
	 */
	public static String PUBLIC_KEY_FILE;
	
	/**
	 * Public Key
	 */
	public static PublicKey publicKey = null;
	
	/**
	 * Private Key
	 */
	private static PrivateKey privateKey = null;
	
	/**
	 * String to hold name of encrypted password file.
	 */
	public static String fileName;
	
	/**
	 * URL of the encrypted password file, if it isn't available locally
	 */
	public static final String fileURLVing = "http://ving.is-a-geek.net:8888/Data/AccountPasswords.crypt";
	public static final String fileURLEricsson = "http://www.tnds.telcordia.com/~wam/AccountPasswords.crypt";
	public static String urlToUse = "";
	public static final String postURLVing = "http://ving.is-a-geek.net:8888/postFile.php";
	public static final String postURLEricsson = "http://www.tnds.telcordia.com/cgi-bin/postWamFile";
	public static String urlToPost = "";
	
	/**
	 * Vector to hold all of the password information
	 */
	public static ArrayList<PasswordData> passwordData = null;
	
	/**
	 * Flag to tell if we are using a local file or the web.
	 */
	public static Boolean useFile;
	
	/**
	 * Flag to tell if data has been changed or not.
	 */
	public static Boolean dataChanged = false;
	
	/**
     * Encrypt the plain text using public key.
	 * 
	 * @param text
	 *          : original plain text
	 * @return Encrypted text
	 * @throws java.lang.Exception
	 */
	public static String encrypt(String text) {
		byte[] cipherText = null;
		try {
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
			// encrypt the plain text using the public key
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			cipherText = cipher.doFinal(text.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Arrays.toString(cipherText);
	}
	
	/**
	 * Decrypt text using private key.
	 * 
	 * @param text
	 *          :encrypted text
	 * @return plain text
	 * @throws java.lang.Exception
	 */
	public static String decrypt(String text) {
		byte[] decryptedText = null;
		String rtn = "";
		String[] byteValues = text.substring(1, text.length() - 1).split(",");
		byte[] bytes = new byte[byteValues.length];
		for (int i=0, len=bytes.length; i<len; i++) {
		   bytes[i] = Byte.valueOf(byteValues[i].trim());     
		}
		try {
			// get an RSA cipher object and print the provider
			final Cipher cipher = Cipher.getInstance(ALGORITHM);
		
			// decrypt the text using the private key
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			decryptedText = cipher.doFinal(bytes);
	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		if (decryptedText != null) {
			rtn = new String(decryptedText);
		}
	
		return rtn;
	}
	
	public static void readFile() {
		InputStream inp = null;
		URL url = null;
		URLConnection cnx = null;
		InputStreamReader ipsr = null;
		BufferedReader br = null;
		passwordData = new ArrayList<PasswordData>();
		File inpFile = new File(fileName);
		if (inpFile.canWrite()) {
			try {
				inp = new FileInputStream(fileName);
				ipsr = new InputStreamReader(inp);
				br = new BufferedReader(ipsr);
				useFile = true;
			} catch (IOException e) {
				System.out.println("Error opening "+fileName);
				e.printStackTrace();
				System.exit(1);
			}
		} else {
			try {
				url = new URL(urlToUse);
				cnx = url.openConnection();
				cnx.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
				cnx.setDoInput(true);
	            cnx.setDoOutput(true);
				ipsr = new InputStreamReader(cnx.getInputStream());
				br = new BufferedReader(ipsr);
				useFile = false;
			} catch (IOException e) {
				System.out.println("Error opening "+url.toExternalForm());
				System.out.println(e.getMessage());
				e.printStackTrace();
				System.exit(1);
			}
		}
		String line;
		String decodeText = "";
		try {
			while ((line=br.readLine())!=null){
				decodeText = decrypt(line);
				passwordData.add(new PasswordData(decodeText));
			}
		} catch (IOException e) {
			System.out.println("Error in reading data");
			e.printStackTrace();
			System.exit(1);
		}
		try {
			br.close();
			ipsr.close();
			if (useFile) {
				inp.close();
			}
		} catch (IOException e) {
			System.out.println("Error closing input stream");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void main(String[] args) throws IOException {
		String home = "";
		urlToUse = fileURLVing;
		urlToPost = postURLVing;
		if (System.getProperty("os.name").equals("Linux")) {
			home = System.getenv("HOME");
		} else if (System.getProperty("os.name").equals("Windows 7")) {
			home = System.getProperty("user.home");
			if (home.contains("eweslmi")) {
				urlToUse = fileURLEricsson;
				urlToPost = postURLEricsson;
			}
		}
		PRIVATE_KEY_FILE = home + "/.ssh/RSA.private.key";
		PUBLIC_KEY_FILE = home + "/.ssh/RSA.public.key";
		fileName = home + "/Ubuntu One/AccountPasswords.crypt";
		ObjectInputStream inputStream = null;
		inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
		try {
			publicKey = (PublicKey) inputStream.readObject();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		inputStream.close();
	    inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
		try {
			privateKey = (PrivateKey) inputStream.readObject();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			System.exit(1);
		}
		inputStream.close();
		readFile();
		JFrame f = new AccountWindow();
	}

}