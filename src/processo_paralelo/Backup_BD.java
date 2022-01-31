/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processo_paralelo;

import Util.BackupAutomatico;
import javafx.concurrent.Service;
import javafx.concurrent.Task;



/**
 *
 * @author celso
 */
public class Backup_BD extends Service<Void>{

     @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                
           
                @Override
                protected Void call() {
                   
                     new BackupAutomatico().run();
                    return null;

                }

                @Override
                protected void succeeded() {
                    super.succeeded();
                    System.out.println("BACKUP PARALELO EFECTUADO COM SUCESSO");
                   
                }

                @Override
                protected void failed() {
                    super.failed();
                    System.out.println("Processo paralelo falho por favor verifica o backup do bd");
                }
            };

        };
    
}
