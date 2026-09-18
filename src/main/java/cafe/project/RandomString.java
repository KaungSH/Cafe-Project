package cafe.project;

import java.util.Random;

public class RandomString {
	
	private static final Random random = new Random();
	
	public static String generate() {
		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
	            	   "abcdefghijklmnopqrstuvwxyz" +
	            	   "0123456789";
		
		 StringBuilder result = new StringBuilder();
		 
		 for (int i = 0; i < 10; i++) {
			 int x = random.nextInt(chars.length());
			 result.append(chars.charAt(x));
			 
			
		 }
		 
		 return result.toString();
	}
}
