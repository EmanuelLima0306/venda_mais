/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enum;

/**
 *
 * @author celso
 */
public enum Status {
    
    ACTIVADO("ACTIVADO"),
    ELIMINADO("ELIMINADO"),
    DESACTIVADO("DESACTIVADO"),
    TRANSFERIDO("DESACTIVADO");
    
    private String designacao;
    
    Status(String designacao){
        this.designacao = designacao;
    }
    
    
}
