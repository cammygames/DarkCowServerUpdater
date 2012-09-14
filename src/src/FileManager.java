package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FileManager {
String dir = getDir();
String mods = dir+"/mods";
String modTemp = dir+"/Updater/mods";

public boolean rootFileCheck()
{
	List<String> errors = new ArrayList<String>();
	if(!folderCreator(dir, "Updater")){errors.add("Missing Update folder");return false;}
	else
	{
		//checks for updates/mods folder which is used to temp store mods
		if(!folderCreator(dir+"/Updater/", "mods")){errors.add("Missing update/mods folder");}
		else
		{
			
		}
		//checks for updates/jar folder which stores minecraft.jar backup
		if(!folderCreator(dir+"/Updater/", "jars")){errors.add("Missing Update/jars folder");}
		else
		{
		
		}
		//checks for update/jarMods folder which stores mod that are injected into the client
		if(!folderCreator(dir+"/Updater/", "jarMods")){errors.add("Missing Update/jarMods folder");}
		else
		{
		
		}
	}
	return false;	
}
/**
 * Used to see if a fileExists
 * @param loc - director location
 * @param file - file too look fire
 * @return true if it is found
 */
public File fileExist(String loc,String file)
{
	if(loc == null)	{loc = dir;}
	if(file == null){return null;}
	if(loc != null && file != null)
	{
		String sFile = loc + file;
		File aFile = new File(sFile);
		if(aFile.exists())
		{
			return aFile;
		}
	}
	return null;
}
/**
 * Used to manage file deletion with hook for backing up the file
 * @param loc - location to look for file
 * @param file - file too look for and delete
 * @param backup should backup
 * @return true if the file was deleted
 */
public boolean deleteFile(String loc,String file,boolean backup)
{
	File del = fileExist(loc,file);
	if(del != null)
	{
		if(!backup)
		{
			return del.delete();
		}
		else
		{
			File bac = fileExist(modTemp,file);
			if(bac == null)
			{
				try {
					boolean c = copyFile(del,bac);
					if(c)
					{
						return del.delete();	
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	return false;
}
/**
 * Copies a file from one spot to another
 * @param sourceFile
 * @param destFile
 * @throws IOException
 */
private static boolean copyFile(File sourceFile, File destFile) throws IOException 
{
	if (!sourceFile.exists()) {
	        return false;
	}
	if (!destFile.exists()) {
	        destFile.createNewFile();
	}
	FileChannel source = null;
	FileChannel destination = null;
	source = new FileInputStream(sourceFile).getChannel();
	destination = new FileOutputStream(destFile).getChannel();
	if (destination != null && source != null) 
	{
	        destination.transferFrom(source, 0, source.size());
	        source.close();
	        destination.close();
	        return true;
	}
	return false;


}
//TBH this is about useless but i copied it over from my guardsman mod and used it anyways. At least i'll always have the correct path dir
public String getDir()
{
	try
	{
		String s = Main.class.getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		return s.substring(0,s.lastIndexOf('/'));
	}
	catch(URISyntaxException urisyntaxexception)
	{
		return null;
	}
	
	
}
/**
 * Creates folders nothing more nothing less
 * @param dest
 * @param name
 * @return true if folder is created
 */
public static boolean folderCreator(String dest, String name)
{
	 File fileMain = new File(dest + name);
	 if(!fileMain.exists())
	 {
		 try
		 {
			 if(fileMain.mkdir())
			 {
				 System.out.println("Folder "+name+" Created");
				 return true;
			 }
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
	 }	
	else
	{
		return true;
	}
	 System.out.println("Folder creation failed for " + name);
	return false;
}

}
