package sendemail;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailSpooler {

	private static ExecutorService executor = create();
	private static EmailSpooler spooler;
	private static boolean initialized = false;
	
	public EmailSpooler() {
	}
	
	private void init() {
	}
	
    public static synchronized EmailSpooler getInstance() {

        if (initialized) {
        	checkExecutor();
            return spooler;
        }
        spooler = new EmailSpooler();
        spooler.init();
        initialized = true;
        return spooler;
    }
	
	public synchronized void enqueue(String to, String msgBody, String msgSubject) {
		Runnable worker = new EmailWorker(to, msgBody, msgSubject);
		executor.execute(worker);
	}
	
	private static void checkExecutor() {
		if (executor == null) {
			executor = create();
		}
	}
	
	public synchronized void shutdown() {
		if (executor != null) {
			executor.shutdown();
			executor = null;}
	}
	
	private static ExecutorService create() {
		return Executors.newFixedThreadPool(5);
	}
	
}
