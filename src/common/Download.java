package common;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Calendar;

public class Download {
	
	public static File downloadFile(String url,String loc,String file,String access)
	{try {
		if(access == "ftp")	{return  DownloadFromFtp("","","", url,loc,file);}//TODO fix
		if(access == "url"){ return downloadFromUrl(url, loc, file);}
		return downloadFromUrl(url, loc, file);
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
			Calendar cal = Calendar.getInstance();//get the current time
			double timeI = cal.getTimeInMillis();
			System.out.print("\n Downloading "+file);
			
			URL website = new URL(url);//turn the url string into a url
		    ReadableByteChannel rbc = Channels.newChannel(website.openStream());//open a channel to start streaming data
		    FileOutputStream fos = new FileOutputStream(loc+file);//starts streaming data
		    fos.getChannel().transferFrom(rbc, 0, 1 << 24);//streams the data
		    
		    //calcs the time it took to download which for some reason allway seens to be 0.0mills
		    double timeF = cal.getTimeInMillis();
		    double timeT = timeF - timeI;		    
		    File dFile = new File(loc+file);
		    if(dFile.exists())
		    {
		    System.out.print("\n"+file+" Downloaded in "+ timeT+"mills");
		    return dFile;
		    }
		    else
		    {
		    	System.out.print("\n"+file+" failed to download");
		    }
		    
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
	    /**
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
	    **/
}
