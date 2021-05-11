package beans;

import sendemail.EmailSpooler;

public class EmailPasswordRecovery {

	public static void sendPasswordRecoveryEmail(String to, String key) {
		String msgSubject = "T1ER Password Recovery";
		String msgBody = getMsgBody(key);
		EmailSpooler.getInstance().enqueue(to, msgBody, msgSubject);
		EmailSpooler.getInstance().shutdown();
	}
	
	private static String getMsgBody(String key) {
		String msgBody = "Please follow link to change your password: "
				+ "http://localhost:8080/T1ER/RecoveryController?kid=" + key;
		return msgBody;
	}
	
}
