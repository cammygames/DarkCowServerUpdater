package common;

public class Main {
	
	public final static String MasterUser = ""; //too prevent people from stealing only added on compile
	public final static String MasterPass = "";
	
	public static void main(String[] args)
	{
		try {new GUIMain().setVisible(true);}catch (Exception e){ e.printStackTrace();}
	}
}
