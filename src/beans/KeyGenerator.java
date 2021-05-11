package beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KeyGenerator {

    private static KeyGenerator generator;
    private static boolean initialized = false;
    private static List<String> keys;
    
    private KeyGenerator() {
    }

    private void init() {
    	keys = new ArrayList<String>();
    }

    public static synchronized KeyGenerator getInstance() {

        if (initialized) {
            return generator;
        }
        generator = new KeyGenerator();
        generator.init();
        initialized = true;
        return generator;

    }
	
	  public String key() {

	        String shortcode;
	        do {
	            shortcode = randomChars(3) + "-" + randomChars(3) + "-" + randomChars(3);
	        } while(keys.contains(shortcode));
	        keys.add(shortcode);
	        
	        return shortcode;
	    }

	    private String randomChars(int n) {
	        String randomchars = "";
	        String chars = "abcdefghijklmnopqrstuvwxyz1234567890";
	        Random rnd = new Random();
	        for (int i = 0; i < n; i++) {
	            randomchars += chars.charAt(rnd.nextInt(chars.length()));
	        }
	        return randomchars;
	    }
	
}
