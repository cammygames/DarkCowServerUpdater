package common;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

@SuppressWarnings("all")
public class GUIMain extends JFrame implements ActionListener {
	
    private ScrollPane consoleOut;
    private JPanel infoPanel;
    private JMenuBar menuBar;
    private JMenu menuButton1;
    private JMenu menuButton2;
    private JMenu menuButton3;
    private JButton update;
    private JPanel webPanel;
    private JTextArea consoleOut1;
    
    public String[] display = new String[]{"1","2","3","4","5","6"};
    
	public String[] disList = new String[300];	
	
	public int disCount = 0;
	
	private static final long serialVersionUID = 2696364157973172973L;
	
    public GUIMain() {
    	
        initComponents();
        this.setResizable(false);
    }
    @Override
	public void actionPerformed(ActionEvent event) 
	{
		if(event.getSource() == update)
		{
			addToConsole("Checking for Updates");
		}
	}
    public void addToConsole(String msg)
	{
		for(int i = 0; i < disList.length; i++)
		{
			if(disList[i] == null && disList[i+1] == null)
			{
				disList[i] = msg;
				break;
			}
		}
		if(disList[7] != null)
		{
			disCount++;
		}
		adjDisplay();
	}
    private void adjDisplay()
	{
		for(int i = 0; i<6;i++)
		{
			if(disList[i+disCount] != null)
			{
				display[i] = disList[i+disCount];
			}
			else
			{
				display[i] = "";
			}
		}
		consoleOut1.setText( "\n" + display[0] + "\n" + display[1] + "\n" + display[2] + "\n" + display[3] + "\n" + display[4] + "\n" + display[5]) ;
		consoleOut1.repaint();
	}
    private void initComponents() {

        consoleOut = new ScrollPane();
        update = new JButton();
        webPanel = new JPanel();
        infoPanel = new JPanel();
        menuBar = new JMenuBar();
        menuButton1 = new JMenu();
        menuButton2 = new JMenu();
        menuButton3 = new JMenu();
        consoleOut1 = new JTextArea(30,100);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Ue Mod Downloader");
        setBackground(new java.awt.Color(0, 75, 223));
        setBounds(new java.awt.Rectangle(0, 0, 200, 520));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        update.addActionListener(this);
        update.setText("Update");
        
        consoleOut1.setEditable(false);
        consoleOut1.setText("Debug Console");
        
        consoleOut.add(consoleOut1);
        
        webPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout webPanelLayout = new javax.swing.GroupLayout(webPanel);
        webPanel.setLayout(webPanelLayout);
        webPanelLayout.setHorizontalGroup(
            webPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        webPanelLayout.setVerticalGroup(
            webPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 345, Short.MAX_VALUE)
        );

        infoPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        menuButton1.setText("Download Mods");
        menuBar.add(menuButton1);

        menuButton2.setText("Download Minecraft");
        menuBar.add(menuButton2);

        menuButton3.setText("Credits");
        menuBar.add(menuButton3);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(webPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(consoleOut, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(infoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(update, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(webPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(infoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(consoleOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(update, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );

        pack();
    }
}
