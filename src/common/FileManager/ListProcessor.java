package common.FileManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import common.Download;

public class ListProcessor
{
    public static List<ModInstance> modProcessList = new ArrayList<ModInstance>();
    public static List<String> debug = new ArrayList<String>();
    public static List<File> installedMods = new ArrayList<File>();
    public static List<File> installedCoreMods = new ArrayList<File>();

    /**
     * Triggers the updater to process the mod list and begin the downloading
     */
    public static boolean ProcessorUpdateList()
    {
        File list = FileManager.modList;
        installedMods = FileManager.installedMods;
        installedCoreMods = FileManager.installedCoreMods;

        if (list != null && list.exists())
        {
            try
            {
                List<String> mods = FileWriter.readSmallTextFile(FileManager.updaterDir + "/ModList.list");
                modProcessList.clear();

                for (String mod : mods)
                {
                    ModInstance modI = ModInstance.convertString(mod);
                    if (modI != null && !modProcessList.contains(modI))
                    {
                        modProcessList.add(modI);
                    }
                }

                for (ModInstance instance : modProcessList)
                {
                    ListProcessor.processDownlaod(instance);
                }

                ListProcessor.cleanModFolder();

                debug.add("");
                debug.add("All mods updated");
                debug.add("TODO::Launch MinecraftLauncher.jar");
                return true;
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else
        {
            System.out.print("Processor can't find list");
            debug.add("Updater List not found");
        }
        return false;
    }

    /**
     * Looks at the modInstance from the download/install list copies or
     * downloads the mods to the correct folders
     */
    public static void processDownlaod(ModInstance modInstance) throws IOException
    {
        File currentMod = new File(FileManager.dir + "/" + modInstance.type.output + "/" + modInstance.modName);
        File storedMod = new File(FileManager.updaterDir + "/" + modInstance.type.storage + "/" + modInstance.modName);

        if (!currentMod.exists() || !FileManager.installedMods.contains(currentMod))
        {
            debug.add("File " + modInstance.modName + " not Found in mods folder");

            if (storedMod.exists() && FileManager.modsStored.contains(storedMod))
            {
                boolean ccMod = FileManager.copyFile(storedMod, currentMod);
                if (ccMod)
                {
                    debug.add("File " + modInstance.modName + " copied to mods folder");
                    removeFromList(currentMod);
                }
            }
            else
            {

                File downloaded = Download.downloadFile(modInstance.url, FileManager.updaterDir + "/" + modInstance.type.storage, modInstance.modName, "url");
                if (downloaded != null && downloaded.exists())
                {
                    debug.add("Downloaded " + modInstance.modName + " to updater/mods");
                    boolean ctc = FileManager.copyFile(storedMod, currentMod);
                    if (ctc)
                    {
                        removeFromList(currentMod);
                    }
                }
                else
                {
                    debug.add("Failed to download file " + modInstance.modName);
                    debug.add("If you have this file you can manualy add it by placing it in the updater/mods folder and running installer again");
                }
            }
        }
        else
        {

            removeFromList(currentMod);
        }
    }

    /**
     * removes extra mods from the mods folder and backs them up to the updater
     * folder
     */
    public static void cleanModFolder() throws IOException
    {
        List<String> extraMods = new ArrayList<String>();
        List<String> extraCoreMods = new ArrayList<String>();
        boolean modRemoved = false;
        for (int red = 0; red < 5; red++)
        {
            // add all remaining mods to the extra mod list for
            // processing
            for (int b = 0; b < installedMods.size(); b++)
            {
                String s = installedMods.get(b).getName();
                extraMods.add(s);
                installedMods.remove(b);
                debug.add("Extra File: " + s);
            }
            for (int b = 0; b < installedCoreMods.size(); b++)
            {
                String s = installedCoreMods.get(b).getName();
                extraCoreMods.add(s);
                installedCoreMods.remove(b);
                debug.add("Extra File: " + s);
            }
            // now time to remove extra mods from mods folder, these are
            // most likely old mod versions

            while (extraMods.size() > 0)
            {
                // same process as installing mod but it will delete the
                // mod when its done
                for (int i = 0; i < extraMods.size(); i++)
                {
                    File oldMod = new File(FileManager.modsDir + "/" + extraMods.get(i));
                    File modCheck = new File(FileManager.modTemp + "/" + extraMods.get(i));
                    if (modCheck.exists())
                    {
                        extraMods.remove(i);
                        oldMod.delete();
                        debug.add("File Removed");
                        modRemoved = true;
                    }
                    else
                    {
                        boolean cTo = FileManager.copyFile(oldMod, modCheck);
                        if (cTo)
                        {
                            extraMods.remove(i);
                            oldMod.delete();
                            debug.add("File Removed");
                            modRemoved = false;
                        }
                    }
                }
            }
            while (extraCoreMods.size() > 0)
            {
                // same process as installing mod but it will delete the
                // mod when its done
                for (int i = 0; i < extraCoreMods.size(); i++)
                {
                    File oldMod = new File(FileManager.coremodsDir + "/" + extraCoreMods.get(i));
                    File modCheck = new File(FileManager.coremodTemp + "/" + extraCoreMods.get(i));
                    if (modCheck.exists())
                    {
                        extraCoreMods.remove(i);
                        oldMod.delete();
                        debug.add("File Removed");
                        modRemoved = true;
                    }
                    else
                    {
                        boolean cTo = FileManager.copyFile(oldMod, modCheck);
                        if (cTo)
                        {
                            extraCoreMods.remove(i);
                            oldMod.delete();
                            debug.add("File Removed");
                            modRemoved = false;
                        }
                    }
                }
            }
        }
        if (modRemoved)
        {
            debug.add("Some mods were removed from mods Folder, and backed up to updater/mods folder");
            debug.add("The updater only installs mods on its list, and will remove all other mods");
            debug.add("If you wish to keep these mods manualy copy them to mods folder");
            debug.add("A mod Ignore list will be added later to prevent this from happening");
        }
    }

    /**
     * cheap/simple way to remove an installed mod from the global install list.
     * This is done for the ModCLeanup so it doesn't actual remove a mod that is
     * needed.
     */
    public static void removeFromList(File file)
    {
        if (installedMods.contains(file))
        {
            installedMods.remove(file);
        }
        if (installedCoreMods.contains(file))
        {
            installedCoreMods.remove(file);
        }
    }
}
