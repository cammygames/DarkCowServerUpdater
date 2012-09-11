package src;

class Main {
	public final static String MasterUser = ""; //too prevent people from stealing only added on compile
	public final static String MasterPass = "";
	public static void main(String[] args)
	{
		
		
		try {
			GUIMain mainGui = new GUIMain();
			//test download
			//Download.SFTPDownload(MasterUser, MasterPass, "frs.sourceforge.net", "/home/frs/project/uemodpack/SteamPower/", "C:/Users/Rseifert/Desktop/", "MC.exe");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
