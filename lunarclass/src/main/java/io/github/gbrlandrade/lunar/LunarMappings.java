package io.github.gbrlandrade.lunar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * 
 * @author Gabriel
 *
 */
public class LunarMappings 
{
	
	private File mappingsFile;
	
	// Lunar Class Name, Notch Class Name
	private HashMap<String, String> v1_8mappings = new HashMap<String, String>();
	
	public LunarMappings(File mappingsFile)
	{
		this.mappingsFile = mappingsFile;
		
		try 
		{
			readTxt();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void readTxt() throws FileNotFoundException
	{
		Scanner sc = new Scanner(mappingsFile);
		
        while (sc.hasNextLine()) 
        {
        	String ln = sc.nextLine();
        	
        	if (ln == null || ln.equals("")) 
        	{
        		//IGNORE
        	}
        	else
        	{
        		String[] split = ln.split(" ");
        		v1_8mappings.put(split[0], split[1]);
			}      	
        }
        
        sc.close();	
	}
	
	public String getNotchName(String lunarName)
	{
		return v1_8mappings.get(lunarName);
	}
	
}
