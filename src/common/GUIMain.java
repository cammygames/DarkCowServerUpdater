package common;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.*;

import common.FileManager.FileManager;
import common.FileManager.ListProcessor;

@SuppressWarnings("all")
public class GUIMain extends JFrame implements ActionListener 
{

	private JButton downloadMods;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JTextArea consoleOut;
    private JButton minecraft;
    private JButton update;
    private JEditorPane webPane;
    private JLabel jLabel1;

    private boolean canUpdate = false;
    private boolean modsUpdate = false;
    private boolean canLaunch = false;
	private static final long serialVersionUID = 2696364157973172973L;

    public GUIMain() 
    {
    	
        initComponents();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    @Override
	public void actionPerformed(ActionEvent event) 
	{
		if(event.getSource() == update)
		{
			if(!canUpdate)
			{
				update.setEnabled(false);
				updateCheck();
				update.setEnabled(true);
				update.setText("Update Now");
				if(FileManager.dir.endsWith(".minecraft"))
				{
					canUpdate = true;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Updater needs to be in .minecraft directory to function");
					update.setEnabled(false);
				}
			}
			else
			{	
				if(!modsUpdate)
				{
					update.setEnabled(false);
					boolean pp = triggerUpdate();
					modsUpdate = true;
					update.setEnabled(true);
					canLaunch = pp;
				}
				else
				{
					//TODO add logic to start minecraftLauncher.jar
				}
			}
		}
		if(event.getSource() == minecraft)
		{
				openMC();
		}
	}   
    
    public void openMC()
    {
    	File file = new File(FileManager.updaterDir+"/Launcher.jar");
    	if(file.exists())
    	{
    		this.addToConsole("Attempting to launch Minecraft");
    		try {
    			ProcessBuilder pb = new ProcessBuilder("java","-Xmx1024M", "-Xms512M", "-cp", FileManager.updaterDir+"/Launcher.jar", "net.minecraft.LauncherFrame");
    			Process process = pb.start();
    		} catch (IOException e) {
    			this.addToConsole("Failed to launch Minecraft");
    			e.printStackTrace();
    			}	
    		
    	}
    	else
    	{
    		Download.downloadFile("http://s3.amazonaws.com/MinecraftDownload/launcher/minecraft.jar", FileManager.updaterDir, "/Launcher.jar", "url");
    		if(file.exists())
        	{
    			openMC();
        	}
    		
    	}
    }
    
    public void updateCheck()
    {
    	//check to make sure we have a base folder system to work out of
    	//Normally will generate files it need if not something is wrong
    	
    	addToConsole("Checking File System");
		Boolean fileExist = FileManager.rootFileCheck();
    	if(fileExist)
		{

			if(FileManager.errors.size() > 0)
			{
				addToConsole("Main file check Debug:");
				for(int i = 0; i < FileManager.errors.size(); i++)
				{
					addToConsole("   "+FileManager.errors.get(i));
				}
			}
			//Check for the update list that is used to manage mods
			Boolean uc = FileManager.updateList();
			if(!uc)
			{
				addToConsole("Critical: Failed To Get List");
				if(new File(FileManager.updaterDir+"/ModList.list").exists())
				{
					addToConsole("Defaulting to old list");
				}
				else
				{
					addToConsole("Critical: No Update List Found.");
				}
			}
		}
		else
		{
			addToConsole("Critical: Main file check failed");
			if(FileManager.errors.size() > 0)
			{
				addToConsole("Main File Check Debug:");
				for(int i = 0; i < FileManager.errors.size(); i++)
				{
					addToConsole("   "+FileManager.errors.get(i));
				}
			}
		}	
    }
    
    public boolean triggerUpdate()
    {
    	
			addToConsole("Starting Updater");
			boolean updatedAll = ListProcessor.ProcessorUpdateList(); //processes the update list and downloads missing mods
			if(ListProcessor.debug.size() > 0)
			{
				addToConsole("Starting Update Processor");
				for(int i = 0; i < ListProcessor.debug.size(); i++)
				{
					addToConsole(ListProcessor.debug.get(i));
				}
			}
			return updatedAll;
    }
    
    public void addToConsole(String msg)
	{
    	String content = consoleOut.getText();
    	consoleOut.setText(content + "\n"+ msg);
	}
    
    private void initComponents() 
    {

        jScrollPane1 = new JScrollPane();
        webPane = new JEditorPane();
        update = new JButton();
        jScrollPane2 = new JScrollPane();
        consoleOut = new JTextArea();
        minecraft = new JButton();
        downloadMods = new JButton();
        jLabel1 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(102, 102, 102));

        webPane.setEditable(false);
        jScrollPane1.setViewportView(webPane);
    	try 
    	{
    		webPane.setContentType("text/html");
    		webPane.setPage(FileManager.webURl);
    	}catch (Exception e)
    	{
    		e.printStackTrace();
        }

        update.setText("Update Check");
        update.addActionListener(this);
        
        consoleOut.setEditable(false);
        consoleOut.setColumns(20);
        consoleOut.setRows(5);
        jScrollPane2.setViewportView(consoleOut);

        minecraft.setText("Launch Minecraft");
        minecraft.addActionListener(this);
        
        downloadMods.setText("Download Mods");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/common/update.png")));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 404, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(update, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(minecraft, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(downloadMods, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 310, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(update, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                            .addComponent(minecraft, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(downloadMods, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
    }

	private URL getResource(String string) 
	{
		return null;
	}
}