package io.github.gbrlandrade.lunar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

import main.Main;

/**
 * 
 * @author Gabriel
 *
 */
public class Lunar 
{

	private ZipInputStream jarFile;	
	
	public Lunar(InputStream jarFile) throws ZipException, IOException
	{
		this.jarFile = new ZipInputStream(jarFile);
	}
	
	/**
	 * Start Process
	 * @throws IOException 
	 */
	public void startProcess() throws IOException
	{				
		Path path = Files.createTempDirectory("lunar-");
		
		unzipJarTo(path);
		renamelClass(path);
		
		createFolder(path);
	    
		FileUtils.deleteDirectory(path.toFile());
	}
	
	/**
	 * Unzip Jar
	 * @param path
	 * @throws IOException
	 */
	private void unzipJarTo(Path path) throws IOException
	{		
		LunarProgressBar lunarProgressBar = new LunarProgressBar();
		lunarProgressBar.create();
		lunarProgressBar.jProgressBar.setMaximum(Main.zipFileToProgressBar.size());
		
		byte[] bytes = new byte[1024];
        ZipEntry zipEntry = getFile().getNextEntry();
        
        int i = 0;
        
        while(zipEntry != null)
        {
            String fileName = zipEntry.getName();
            lunarProgressBar.setTitle(fileName);
            lunarProgressBar.jProgressBar.setValue(i);
            
            File newFile = new File(path.toFile(),fileName);
            
            System.out.println("Unzipping to " + newFile.getAbsolutePath());

            new File(newFile.getParent()).mkdirs();
            
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            int len;
            
            while ((len = getFile().read(bytes)) > 0) 
            {
            	fileOutputStream.write(bytes, 0, len);
            }
            fileOutputStream.close();

            getFile().closeEntry();
            zipEntry = getFile().getNextEntry();
            i++;
        }
        lunarProgressBar.dispose();
	}
	
	/**
	 * Rename .lclass to .class
	 * @param path
	 * @throws IOException
	 */
	private void renamelClass(Path path) throws IOException 
	{		
		LunarProgressBar lunarProgressBar = new LunarProgressBar();
		lunarProgressBar.create();
		lunarProgressBar.jProgressBar.setMaximum(Main.zipFileToProgressBar.size());
		
		int i = 0;
		
		for(File f : FileUtils.listFiles(
				path.toFile(), new String[] {"lclass"}, true))
		{			 
			File file = new File(
					f.getAbsolutePath().replace(f.getName(), ""),
					f.getName().replace(".lclass", ".class"));
			f.renameTo(f);	
		    FileUtils.moveFile(FileUtils.getFile(f), FileUtils.getFile(file));
		    
		    lunarProgressBar.setTitle("Renaming " + f.getName());
		    lunarProgressBar.jProgressBar.setValue(i);
			System.out.println("Renaming to " + file.getAbsolutePath());
			
			i++;
		}
		
		lunarProgressBar.dispose();
	}
	
	private void createFolder(Path path) throws IOException
	{
        JFileChooser fileChooser = Main.createChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) 
        {       
        	FileUtils.copyDirectory(
        			path.toFile(), 
        			new File(fileChooser.getSelectedFile(),"Lunar"));
        	JOptionPane.showMessageDialog(null, "The directory was created successfully!", "Successful", 
        			JOptionPane.INFORMATION_MESSAGE);
        	System.exit(1);
        }
        else 
        {
			System.exit(-1);
		}
	}
	
	public ZipInputStream getFile()
	{
		return jarFile;
	}
	
}
