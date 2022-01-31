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
public enum FormaPagamento {
    
    NUMERARIO("NUMERARIO"),
    MULTICAIXA("MULTICAIXA"),
    DEPOSITO("DEPOSITO"),
    TRANSFERENCIA("TRANSFERENCIA"),
    PAGAMENTODUPLO("PAGAMENTODUPLO"),
    CREDITO("CREDITO");
    
    private String designacao;
    
    FormaPagamento(String designacao){
        this.designacao = designacao;
    }
    
    
}
