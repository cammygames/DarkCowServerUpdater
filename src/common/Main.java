package common;


import java.io.File;
import java.io.IOException;


public class Main {
	public final static String MasterUser = ""; //too prevent people from stealing only added on compile
	public final static String MasterPass = "";
	public static File modList = null;
	public static void main(String[] args)
	{
		//open main GUI
		try {GUIMain mainGui = new GUIMain();} catch (Exception e) {e.printStackTrace();}
		//check if main files exist if not we can't go on.		
	}
}
