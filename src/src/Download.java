package src;


import java.io.BufferedInputStream;
import java.io.File;
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
	
	public static File downloadFile(String url,String loc,String file,String access)
	{try {
		if(access == "ftp")	{return  DownloadFromFtp("","","", url,loc,file);}//TODO fix
		if(access == "url"){ return downloadFromUrl(url, loc, file);}
		//TODO add SFTP
		} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;		
	}
	/**
	 * 
	 * @param url - url to use for downloading
	 * @param loc - location to save file too
	 * @param file - file name
	 * @return
	 */
	public static File downloadFromUrl(String url,String loc, String file)
	{
		try{
			Calendar cal = Calendar.getInstance();
			double timeI = cal.getTimeInMillis();
			System.out.print("Downloading "+file);
			URL website = new URL(url);
		    ReadableByteChannel rbc = Channels.newChannel(website.openStream());
		    FileOutputStream fos = new FileOutputStream(loc+"/"+file);
		    fos.getChannel().transferFrom(rbc, 0, 1 << 24);
		    double timeF = cal.getTimeInMillis();
		    double timeT = timeF - timeI;
		    System.out.print(file+" Downloaded in "+ timeT+"mills");
		    File dFile = new File(loc+"/"+file);
		    return dFile.exists() ? dFile : null ;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	
	 
	 	
	    public static File DownloadFromFtp(String user,String pass,String host, String src,String des,String fileName) throws Exception 
	    {	    	
	    	System .out.println("Connecting to FTP server...");    
	 
	    	//Connection String
	    	//URL  url = new URL ("ftp://"+userName+":"+password+"@"+server+"/public_html/"+fileName+";type=i");
	    	URL url = new URL("ftp://"+user+":"+pass+"@"+host+"/"+fileName+";type=i");
	    	URLConnection  con = url.openConnection();
	 
	    	BufferedInputStream  in = new BufferedInputStream (con.getInputStream());
	 
	    	System .out.println("Downloading file.");
	 
	    	//Downloads the selected file to the C drive
	    	FileOutputStream  out = new FileOutputStream (des + fileName);
	 
	    	int i = 0;
	    	byte[] bytesIn = new byte[1024];
	    	while ((i = in.read(bytesIn)) >= 0) 
	    	{
	    		out.write(bytesIn, 0, i);
	    	}
	    	out.close();
	    	in.close();
	    	System .out.println("File downloaded.");
	    	File dFile = new File(des + fileName);
	    	return dFile.exists() ? dFile : null ;
	 
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
