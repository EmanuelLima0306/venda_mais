package Util;

import Controller.LogController;
import Enum.TipoLog;
import Model.LogModel;
import View.LoginView;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author emanuel
 */
public class LogUtil {
    public static LogUtil log = new LogUtil();
    LogController controller = new LogController();

    public LogUtil() {
    }
    
    public void salvarLog(TipoLog designacao, String descricao){
        System.out.println("chegou");
        LogModel modelo = new LogModel(designacao, descricao, LoginView.usuario, DataComponent.getDataActual());
        controller.save(modelo);
    }
    
    
    
}
