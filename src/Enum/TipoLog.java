/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enum;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author emanuel
 */
public enum TipoLog {
    LOGIN("ACESSO AO SISTEMA"),
    LOGOUT("SAÍDA DO SISTEMA"),
    INFO("OPERACÃO REALIZADA");
    
    private String designacao;

    private TipoLog(String designacao) {
        this.designacao = designacao;
    }

    @Override
    public String toString() {
        return designacao;
    }
    
    

    public String getDesignacao() {
        return designacao;
    }
    
    public static List<TipoLog> get(){
        List<TipoLog> tipoLogs = new ArrayList<TipoLog>();
        
        tipoLogs.add(TipoLog.LOGIN);
        tipoLogs.add(TipoLog.LOGOUT);
        tipoLogs.add(TipoLog.INFO);
        
        return tipoLogs;
    }
    
}
