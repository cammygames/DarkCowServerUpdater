package common.FileManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import common.Download;


public class ListProcessor {
public static String[][] ModList = new String[50][4];
public static List<String> debug =  new ArrayList<String>();
	public static boolean ProcessorUpdateList()
	{
		File list = FileManager.modList;
		if(list != null)
		{
			try {
				List<String> mods = FileWriter.readSmallTextFile(FileManager.updaterDir+ "/ModList.list");
				//turns the list into usable info
				for(int i = 0; i<mods.size();i++)
				{
					String[] modString = mods.get(i).toLowerCase().split(";");
					ModList[i][0] = modString[0];//Mod file Name
					ModList[i][1] = modString[1];//Mod type jar or mod Folder
					ModList[i][2] = modString[2];//How to download it ftp, url, sftp
					ModList[i][3] = modString[3];//download url
				}
				//find if files are in mods folder
				//List<String> missingMods = new ArrayList<String>();
				List<String> extraMods = new ArrayList<String>();
				List<File> installedMods = FileManager.installedMods;
				for(int f = 0; f<mods.size();f++)
				{
					File cMod = new File(FileManager.modsDir+"/"+ModList[f][0]);
					File uMod = new File(FileManager.modTemp+"/"+ModList[f][0]);
					
					//check to see if the mod is already installed in the mods folder
					if(!cMod.exists() || !installedMods.contains(cMod))
					{
						String modStr = ModList[f][0]+";"+ModList[f][1]+";"+ModList[f][2]+";"+ModList[f][3];
						//missingMods.add(modStr);
						debug.add("File " + ModList[f][0]+" not Found in mods folder");
						//looks for the mod in the mod storage file
						if(uMod.exists() && FileManager.modsStored.contains(uMod))
						{
							boolean ccMod = FileManager.copyFile(uMod, cMod);
							if(ccMod)
							{
								debug.add("File " + ModList[f][0]+" copied to mods folder");
								if(installedMods.contains(cMod))
								{
									installedMods.remove(cMod);
								}
							}
						}
						else
						{
							//if it can't find it in storage file it will try to download file
							String type = "mods";
							if(ModList[f][1] == "1")
							{
								type = "jars";
							}
							File downloaded = Download.downloadFile(ModList[f][3], FileManager.updaterDir+type, ModList[f][0], ModList[f][2]);
							if(downloaded.exists())
							{
								debug.add("Downloaded " + ModList[f][0]+" to updater/mods");
								boolean ctc = FileManager.copyFile(downloaded, cMod);
								if(installedMods.contains(cMod) && ctc)
								{
									installedMods.remove(cMod);
								}
							}
							else
							{
								debug.add("Failed to get file " + ModList[f][0]);
								debug.add("If you have this file you can manualy add it by placing it in the updater/mods folder and running installer again");
							}
						}
					}
					else
					{
						
						if(installedMods.contains(cMod))
						{
							installedMods.remove(cMod);
						}
					}
				}
				
				//add all remaining mods to the extra mod list for processing
				for(int b = 0; b < installedMods.size(); b++)
				{
					String s = installedMods.get(b).toString();
					extraMods.add(s.substring(0,installedMods.toString().lastIndexOf('/')));
					installedMods.remove(b);
				}
				//now time to remove extra mods from mods folder, these are most likely old mod versions
				boolean modRemoved = false;
				for(int o = 0; 0 < extraMods.size(); o++)
				{
					//same process as installing mod but it will delete the mod when its done
					for(int i = 0; i<extraMods.size();i++)
					{						
						File oldMod = new File(FileManager.modsDir+"/"+extraMods.get(i));
						File modCheck =  new File(FileManager.modTemp+"/"+extraMods.get(i));
						if(modCheck.exists())
						{
							extraMods.remove(i);
							oldMod.delete();
							debug.add("File " + extraMods.get(i)+" Removed from mods folder");
							modRemoved = true;
						}
						else
						{
							boolean cTo = FileManager.copyFile(oldMod, modCheck);
							if(cTo)
							{
								extraMods.remove(i);
								oldMod.delete();
								debug.add("File " + extraMods.get(i)+" Removed from mods folder");
								modRemoved = false;
							}
						}
					}
				}
				if(modRemoved)
				{
					debug.add("Some mods were removed from mods Folder, and backed up to updater/mods folder");
					debug.add("The updater only installs mods on its list, and will remove all other mods");
					debug.add("If you wish to keep these mods manualy copy them to mods folder");
					debug.add("A mod Ignore list will be added later to prevent this from happening");
				}
				debug.add("");
				debug.add("All mods updated");
				debug.add("TODO::Launch MinecraftLauncher.jar");
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			System.out.print("Processor can't find list");
			debug.add("Can't process an empty List");
		}
		return false;
	}
}
