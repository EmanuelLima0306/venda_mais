/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author celso
 */
public class BackupAutomatico implements Runnable{

    @Override
    public void run() {
        
        try {
            CopySecury.backup();
        } catch (IOException ex) {
            Logger.getLogger(BackupAutomatico.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
