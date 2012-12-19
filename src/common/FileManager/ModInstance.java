package common.FileManager;

public class ModInstance
{
    /**
     * mainly used to track the location where this mod needs to be installed
     */
    public enum fileType
    {
        General("mods","mods"), Core("coremods","coremods"), Jar("bin/minecraft.jar","jars");
        public String output;
        public String storage;

        private fileType(String outputMain, String outputStorage)
        {
            this.output = outputMain;
            this.storage= outputStorage;
        }
    }

    /**
     * used to find the download type to be used to download the file
     */
    public enum downloadType
    {
        URL, FTP, SFTP;
    }

    public String modName;
    public String url;
    public fileType type;
    public downloadType dType;

    /**
     * 
     * @param ModName
     *            - name that the mod will be installed or saved as
     * @param type
     *            - mod type that this mod is General(mods folder),
     *            Core(coremods folder), jar(inside minecraft.jar)
     * @param dType
     *            - how to download this mods URL, FTP, SFTP
     * @param url
     *            - location of the mods to be download from
     */
    public ModInstance(String ModName, fileType type, downloadType dType, String url)
    {
        this.modName = ModName;
        this.type = type;
        this.dType = dType;
        this.url = url;
    }
    /**
     * Converts the string form of a mod from the mod list
     * to a ModInstance for processing
     */
    public static ModInstance convertString(String line)
    {
        try
        {
            String[] modString = line.split(";");
            int var2 = Integer.parseInt(modString[1]);
            int var3 = Integer.parseInt(modString[2]);
            return new ModInstance(modString[0],fileType.values()[var2], downloadType.values()[var3], modString[3]);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
