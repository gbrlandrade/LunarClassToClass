package main;

import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipFile;

import javax.swing.JFileChooser;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

import io.github.gbrlandrade.lunar.Lunar;

/**
 * 
 * @author Gabriel
 *
 */
public class Main
{
	
	public static ZipFile zipFileToProgressBar;
	
	public static void main(String[] args) throws Exception 
	{		
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        
        JFileChooser fileChooser = createChooser();
        fileChooser.setDialogTitle("Select Lunar Jar File");
        fileChooser.setAcceptAllFileFilterUsed(false);      
        fileChooser.addChoosableFileFilter(new LunarFileFilter());
        
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
        {       
        	zipFileToProgressBar = new ZipFile(fileChooser.getSelectedFile());
    		new Lunar(new FileInputStream(fileChooser.getSelectedFile())).startProcess();
        }
        else 
        {
			System.exit(1);
		}
	}

	private static class LunarFileFilter extends FileFilter
	{
		
		public String lunarJarName = "lunar-prod";
		
		@Override
		public boolean accept(File f)
	    {
	        if(f.isDirectory()) return true;
	        else if(f.getName().contains(lunarJarName)) return true;
	        else return false;
	    }
	    
		@Override
		public String getDescription()
	    {
	        return "Lunar Jar File (" + lunarJarName + ")";
	    }
		
	}
	
	public static JFileChooser createChooser()
	{
        JFileChooser fileChooser = new JFileChooser();  
        fileChooser.setCurrentDirectory(new java.io.File("."));
        return fileChooser;
	}

}
