package src;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GUIMain extends JFrame implements ActionListener {
	private JButton updateButton;
	private JButton Up;
	private JButton Down;
	private JTextArea consoleOut = new JTextArea(30,100);
	private JScrollPane area;
	public String[] display = new String[]{"1","2","3","4","5","6"};
	public List<String> disList = new ArrayList<String>();
	public int disCount = 0;
	Icon updateB = new ImageIcon(getClass().getResource("update.png"));
	Icon hovUpdateB = new ImageIcon(getClass().getResource("hovupdate.png"));
	private static final long serialVersionUID = 2696364157973172973L;
	public GUIMain()
	{
		super("UE Mod Downloader");
		setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800,600);
		setVisible(true);
		this.setResizable(false);
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		//console box
		consoleOut.setEditable(false);
		consoleOut.setWrapStyleWord(true);
		consoleOut.setText("Debug Console");
		disList.add("Starting up..");
		addTextBox(consoleOut,10, 470, 650, 100);
	     //update button
		updateButton = new JButton("",updateB);
		updateButton.setRolloverIcon(hovUpdateB);
		updateButton.setBorderPainted(false);
		addButton(updateButton,690, 470, 100, 100);
		Up = new JButton("U",updateB);
		addButton(Up,660, 470, 20, 40);
		Down = new JButton("D",updateB);
		addButton(Down,660, 530, 20, 40);
		repaint();
		
		
	}
	private void adjDisplay()
	{
		for(int i = 0; i<6;i++)
		{
			if(i+disCount < disList.size())
			{
				display[i] = disList.get(i+disCount);
			}
			else
			{
				display[i] = "";
			}
		}
		consoleOut.setText( display[0] + "\n" + display[1] + "\n" + display[2] + "\n" + display[3] + "\n" + display[4] + "\n" + display[5]) ;
		consoleOut.repaint();
	}
	private void addButton(JButton button,int x, int y, int h,int w)
	{
		button.setBounds(x,y,h,w);
		button.addActionListener(this);
		this.add(button);
	}
	private void addTextBox(Component box,int x, int y, int h,int w)
	{
		box.setBounds(x,y,h,w);
		this.add(box);
	}
	public void addToConsole(String msg)
	{
		this.disList.add(msg);
		if(disList.size() < 6)
		{
			disCount++;
		}
	}
		@Override
		public void actionPerformed(ActionEvent event) 
		{
			if(event.getSource() == updateButton)
			{
				JOptionPane.showMessageDialog(null, String.format("ckecking for Updates", event.getActionCommand()));
			}
			if(event.getSource() == Up)
			{
				if(disCount < disList.size())
				{
					disCount++;
					adjDisplay();
				}
			}
			if(event.getSource() == Down)
			{
				if(disCount >6)
				{
					disCount--;
					adjDisplay();
				}
			}
		}
		
	
}
