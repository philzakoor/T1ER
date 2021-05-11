package beans;

import sendemail.EmailSpooler;

public class EmailVerification {
	
	public static void sendEmailVerifaction(String to, String key) {
		String msgSubject = "Email Verifaction";
		String msgBody = getMsgBody(key);
		EmailSpooler.getInstance().enqueue(to, msgBody, msgSubject);
		EmailSpooler.getInstance().shutdown();
	}
	
	private static String getMsgBody(String key) {
		String msgBody = "Please follow link to verify account: "
				+ " http://localhost:8080/T1ER/RegisterController?kid=" + key;
		return msgBody;
	}
}
