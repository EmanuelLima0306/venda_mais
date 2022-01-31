/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PointSoftGESCHOOL.Entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 *
 * @author celso
 */
public class FicheiroGenericoComponet {
    
    public static void copiarArquivos(File source, File destination) throws IOException 
    {     
	 if (destination.exists())    
	     destination.delete();    

	 FileChannel sourceChannel = null;    
	 FileChannel destinationChannel = null;    

	 try 
	 {    
	     sourceChannel = new FileInputStream(source).getChannel();    
	     destinationChannel = new FileOutputStream(destination).getChannel();    
	     sourceChannel.transferTo(0, sourceChannel.size(),    
		     destinationChannel);    
	 } 
	 finally 
	 {    
	     if (sourceChannel != null && sourceChannel.isOpen())    
		 sourceChannel.close();    
	     if (destinationChannel != null && destinationChannel.isOpen())    
		 destinationChannel.close();    
	}    
    }   
    public static void abrir(String path) {
		try {			
			Runtime.getRuntime().exec("explorer.exe " + path);
		} catch (IOException e1) {			
			e1.printStackTrace();
		}
	}

}
