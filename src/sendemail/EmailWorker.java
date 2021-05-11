package sendemail;

public class EmailWorker implements Runnable {
	
	private final String msgBody, msgSubject;
	private final EmailAPI email;
	
	public EmailWorker(String to, String msgBody, String msgSubject) {
		this.msgBody = msgBody;
		this.msgSubject = msgSubject;
		email = new EmailAPI(to);
	}

	@Override
	public void run() {
		email.sendEmail(msgBody, msgSubject);
	}

}
