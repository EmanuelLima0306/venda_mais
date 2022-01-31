/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author emanuel
 */
public class ConnectionFactor {
    
    public static Connection conexao = null;
    
    
    
    
    public  static void salvarPermanentemente() throws SQLException{
        conexao.commit();
        conexao.setAutoCommit(true);
    }
    public  static void comecarTransicao() throws SQLException{
        conexao.setAutoCommit(false);
    }
    public  static void naoSalvarPermanentemente() throws SQLException{
        conexao.rollback();
        conexao.setAutoCommit(true);
    }
    
}
