package common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class NetWork {
/**
 * Used to get external ip address for varies reasons
 * @return external ip address for this machine
 */
	public static String externalIP()
	{
		try {
			//http://api.externalip.net/ip/
			//http://automation.whatismyip.com/n09230945.asp
				URL whatismyip = new URL("http://api.externalip.net/ip/");
				BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));
				String ip;
				ip = in.readLine();
				return ip;
				
			} catch (Exception e) {
				e.printStackTrace();
				return "Error";
			} 
				
	}
//End of file	
}
