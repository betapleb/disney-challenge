package serverLogs;

import java.util.*;
import java.io.*;
import java.util.regex.*;


public class HostsAndRequests {
	
	private Map<String, Integer> map;

	
	public HostsAndRequests() {
		map = new HashMap<String, Integer>();
		try {
			FileReader fr = new FileReader("host_access_log.txt");
			BufferedReader br = new BufferedReader(fr);
			PrintWriter writer = new PrintWriter("records_host_access_log.txt", "UTF-8");
			String line;
			String url;
			while((line = br.readLine()) != null) {
				//unicomp6.unicomp.net 
				//URLs have 1+ chars, numbers, periods.
				//after the last period, there are series of chars w/ length 2-4.
				//[a-z0-9.-]+\\.[a-z]{2,4} 	this will include .gif and .html
				//regexChecker("^\\S*",line);	//match anything before first whitespace
				url = regexChecker("^\\S*",line);
				if (map.containsKey(url)) {
					map.put(url, map.get(url) + 1);
				} else {
					map.put(url, 1);
				}
			}
			for (String key : map.keySet()) {
				System.out.println(key + " " + map.get(key));
				writer.println(key + " " + map.get(key));
			}
			writer.close();
			fr.close();
			br.close();
				
		} catch (FileNotFoundException x) {
			System.out.println("No such file: " + x.getMessage());
		} catch (IOException x) {
			System.out.println("IO trouble: " + x.getMessage());
		}
	}
	
	
	public String regexChecker(String theRegex, String stringToCheck) {
		Pattern checkRegex = Pattern.compile(theRegex); //define regex using Pattern.
		Matcher regexMatcher = checkRegex.matcher(stringToCheck); //matcher object checks the string for anything that matches regex.
		String s = "";
		//int count = 0;
		//Cycle through the positive matches and print them to screen
		//Make sure string isn't empty and trim off any whitespace
		while (regexMatcher.find()) {				 //bool fine() attempts to find next matching pattern
			if(regexMatcher.group().length() != 0) {	 //group() returns string that matched
				//System.out.println(regexMatcher.group().trim());	//trim off whitespace
				s = regexMatcher.group();
			}
		}
		return s;
	}
	
	
	
	public static void main (String[] args) {
		HostsAndRequests log = new HostsAndRequests();
		
	}
	
}
