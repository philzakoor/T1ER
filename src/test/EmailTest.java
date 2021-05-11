package test;

import beans.EmailVerification;
import beans.KeyGenerator;

public class EmailTest {
	public static void main(String[] args) {
		String to = "philzakooriii@gmail.com";
		String key = KeyGenerator.getInstance().key();
		
		EmailVerification.sendEmailVerifaction(to, key);
	}
}
