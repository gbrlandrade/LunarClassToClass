package io.github.gbrlandrade.lunar;

import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class LunarProgressBar extends JFrame
{

	private static final long serialVersionUID = 1L;
	public JProgressBar jProgressBar = new JProgressBar();

	public LunarProgressBar() 
	{
		super();		
		this.setSize(500, 80);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.setType(Window.Type.POPUP);
		this.add(jProgressBar);
	}

	public void create()
	{
		SwingUtilities.invokeLater(new Runnable() 
		{			
			@Override
			public void run() 
			{
				setLocationRelativeTo(null);
				setVisible(true);
			}
		});
	}
	
}
