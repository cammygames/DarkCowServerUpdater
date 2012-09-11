package src;

import javax.swing.JFrame;

class Main {
	public final static String MasterUser = ""; //too prevent people from stealing only added on compile
	public final static String MasterPass = "";
	public static void main(String[] args)
	{
		
		GUIMain mainGui = new GUIMain();
		try {
			//test download
			//Download.SFTPDownload(MasterUser, MasterPass, "frs.sourceforge.net", "/home/frs/project/uemodpack/SteamPower/", "C:/Users/Rseifert/Desktop/", "MC.exe");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
