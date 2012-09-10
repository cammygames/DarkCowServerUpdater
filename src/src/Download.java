package src;


import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Calendar;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class Download {
	/**
	 * 
	 * @param url - url to use for downloading
	 * @param loc - location to save file too
	 * @param file - file name
	 * @return
	 */
	public static String downloadFromUrl(String url,String loc, String file)
	{
		try{
			Calendar cal = Calendar.getInstance();
			double timeI = cal.getTimeInMillis();
			System.out.print("Downloading "+file);
			URL website = new URL(url);
		    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		    FileOutputStream fos = new FileOutputStream(file);
		    fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		    double timeF = cal.getTimeInMillis();
		    double timeT = timeF - timeI;
		    System.out.print(file+" Downloaded in "+ timeT+"mills");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return "";
		
	}
	
	
	
	 
	 	//String serverName,String user,String pass,String file
	    public static void DownloadFromFtp() throws Exception 
	    {
	    	String  server = "frs.sourceforge.net";
	    	String  userName = "user";
	    	String  password = "pass";
	    	String  fileName = "/home/frs/project/uemodpack/files/MC.exe";
	    	System .out.println("Connecting to FTP server...");    
	 
	    	//Connection String
	    	//URL  url = new URL ("ftp://"+userName+":"+password+"@"+server+"/public_html/"+fileName+";type=i");
	    	URL url = new URL("ftp://"+userName+":"+password+"@"+server+"/"+fileName+";type=i");
	    	URLConnection  con = url.openConnection();
	 
	    	BufferedInputStream  in = new BufferedInputStream (con.getInputStream());
	 
	    	System .out.println("Downloading file.");
	 
	    	//Downloads the selected file to the C drive
	    	FileOutputStream  out = new FileOutputStream ("C:\\" + fileName);
	 
	    	int i = 0;
	    	byte[] bytesIn = new byte[1024];
	    	while ((i = in.read(bytesIn)) >= 0) 
	    	{
	    		out.write(bytesIn, 0, i);
	    	}
	    	out.close();
	    	in.close();
	    	System .out.println("File downloaded.");
	 
	    }
	    public static void SFTPDownload() {
	    	 JSch jsch = new JSch();
	         Session session = null;
	         String user = "user";//Like i would leave this here after testing
	         String pass = "pass";
	         String site = "frs.sourceforge.net";
	         try {
	        	 System.out.println("Downloading");
	             session = jsch.getSession(user, site, 22);
	             session.setConfig("StrictHostKeyChecking", "no");
	             session.setPassword(pass);
	             session.connect();

	             Channel channel = session.openChannel("sftp");
	             channel.connect();
	             ChannelSftp sftpChannel = (ChannelSftp) channel;
	             sftpChannel.cd("/home/frs/project/uemodpack/SteamPower/");
	             sftpChannel.get("MC.exe", "C:/Users/Rseifert/Desktop/MC.exe");
	             sftpChannel.exit();
	             session.disconnect();
	             System.out.println("Download Finished");
	         } catch (JSchException e) {
	             e.printStackTrace();  
	         } catch (SftpException e) {
	             e.printStackTrace();
	         }
	    }
}
