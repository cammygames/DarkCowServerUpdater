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
	
	
	
	 
	 	
	    public static void DownloadFromFtp(String user,String pass,String host, String src,String des) throws Exception 
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
	    public static void SFTPDownload(String user,String pass,String host, String src,String des,String fileName) {
	    	 JSch jsch = new JSch();
	         Session session = null;
	         //String site = "frs.sourceforge.net";
	         try {
	        	 System.out.println("Downloading from "+src);
	             session = jsch.getSession(user, host, 22);
	             session.setConfig("StrictHostKeyChecking", "no");
	             session.setPassword(pass);
	             session.connect();

	             Channel channel = session.openChannel("sftp");
	             channel.connect();
	             ChannelSftp sftpChannel = (ChannelSftp) channel;
	             sftpChannel.cd(src);
	             sftpChannel.get(fileName, des+fileName);
	             sftpChannel.exit();
	             session.disconnect();
	             System.out.println("Downloaded "+ fileName);
	         } catch (JSchException e) {
	             e.printStackTrace();  
	         } catch (SftpException e) {
	             e.printStackTrace();
	         }
	    }
}
