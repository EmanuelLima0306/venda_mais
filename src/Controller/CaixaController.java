/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CaixaModel;
import Model.CaixaModel;
import Model.Moeda;
import Model.EstadoModel;
import Model.UsuarioModel;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.collections.map.HashedMap;

/**
 *
 * @author celso
 */
public class CaixaController implements IController<CaixaModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(CaixaModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO caixa\n"
                    + "(idUsuario, dataAbertura, valorInicial, estado)\n"
                    + "VALUES(?, ?, ?, ?);";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getUsuario().getId());
            command.setString(2, obj.getDataAbertura());
            command.setDouble(3, obj.getValorInicial());
            command.setString(4, obj.getEstado());

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(CaixaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    @Override
    public boolean update(CaixaModel obj) {

        boolean result = false;
        try {
            String sql = "  UPDATE caixa\n"
                    + "SET idUsuario=?, dataAbertura=?, dataFecho=?, valorInicial=?, estado=?\n"
                    + "WHERE id=?;";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getUsuario().getId());
            command.setString(2, obj.getDataAbertura());
            command.setString(3, obj.getDataFecho());
            command.setDouble(4, obj.getValorInicial());
            command.setString(5, obj.getEstado());
            command.setInt(6, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(CaixaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(CaixaModel obj) {

        if (obj.getId()> 0) {
            return update(obj);
        }
        return save(obj);
    }

//    @Override
//    public CaixaModel getById(int id) {
//        
//    }
//    @Override
//    public List<CaixaModel> get() {
//
//        List<Moeda> lista = new ArrayList<>();
//        try {
//
//            con = conFactory.open();
//            String sql = " SELECT * "
//                    + " FROM moeda"
//                    + "  ORDER BY 2;";
//
//            command = con.prepareCall(sql);
//            query = command.executeQuery();
//            while (query.next()) {
//
//                lista.add(new Moeda(query.getInt("Id"), query.getString("Designacao")));
//            }
//
//        } catch (Exception ex) {
//            Logger.getLogger(CambioController.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            ////conFactory.close(con, command, query);
//        }
//        return lista;
//    }
//
//    public CaixaModel getAll(String text) {
//
//        CaixaModel lista = new CaixaModel();
//        try {
//
//            con = conFactory.open();
//            String sql = " SELECT * "
//                    + " FROM `moeda` WHERE Designacao = ?;";
//
//            command = con.prepareCall(sql);
//            command.setString(1, text);
//            query = command.executeQuery();
//            while (query.next()) {
//
//                lista = new Moeda(query.getInt("Id"),
//                        query.getString("Designacao"));
//            }
//
//        } catch (Exception ex) {
//            Logger.getLogger(CambioController.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            ////conFactory.close(con, command, query);
//        }
//        return lista;
//    }
//
//    public void ireportCategoria() {
//
//        
//        String relatorio = "Relatorio/ListaCategoria.jasper";
//        Connection connection = conFactory.open();
//        
//        File file = new File(relatorio).getAbsoluteFile();
//        String obterCaminho = file.getAbsolutePath();
//        HashedMap hashMap = new HashedMap();
//        hashMap.put("LOGOTIPO", "Relatorio/logo.jpg");
//        try 
//        {
//            JasperFillManager.fillReport(obterCaminho,hashMap, connection);
//
//            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);
//
//            if (jasperPrint.getPages().size() >= 1) 
//            {                
//                
//                
//                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
//                    jasperViewer.setVisible(true);
//                
//            } 
//            else 
//            {
//                JOptionPane.showMessageDialog(null, "Folha vazia !...");
//            }
//        } 
//        catch (JRException jex) 
//        {
//            jex.printStackTrace();
//            //System.out.println("aqui");
//            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR  gráfico!...");
//        } 
//        catch (Exception ex) 
//        {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR gráfico !...");
//        }
//        
//    }
    public CaixaModel getById(Moeda moeda) {

        CaixaModel modelo = new CaixaModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `modelo` WHERE IdMoeda = ?;";

            command = con.prepareCall(sql);
            command.setInt(1, moeda.getId());
            query = command.executeQuery();
            if (query.next()) {

//                modelo = new CaixaModel();
//                modelo.setCodigo(query.getInt("Codigo"));
//                modelo.setValor(query.getDouble("Valor"));
//                modelo.setMoeda(moeda);
                return modelo;
            }

        } catch (Exception ex) {
            Logger.getLogger(CaixaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return modelo;
    }
    public CaixaModel getByDateAndUsuario(String dataActual, int idUsuario,String status) {

        CaixaModel modelo = null;
        try {

            con = conFactory.open();
            String sql = " SELECT * FROM caixa c where date(c.dataAbertura) = '"+dataActual+"' "
                    + "and c.idUsuario  = "+idUsuario+" AND  c.estado ='"+status+"' ORDER BY c.dataAbertura DESC LIMIT 1 ";

            command = con.prepareCall(sql);
   
            query = command.executeQuery();
            if (query.next()) {

                 modelo = new CaixaModel();
                 modelo.setId(query.getInt("c.id"));
                 modelo.setDataAbertura(query.getString("c.dataAbertura"));
                 modelo.setUsuario(new UsuarioModel(idUsuario, ""));
                 modelo.setValorInicial(query.getDouble("c.valorInicial"));
                 modelo.setEstado(status);
                return modelo;
            }

        } catch (Exception ex) {
            Logger.getLogger(CaixaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return modelo;
    }
    
    public CaixaModel getLastByUsuario(int idUsuario,String status) {

        CaixaModel modelo = null;
        try {

            con = conFactory.open();
            String sql = " SELECT * FROM caixa c where "
                    + " c.idUsuario  = "+idUsuario+" AND  c.estado ='"+status+"' ORDER BY c.dataAbertura DESC LIMIT 1 ";

            command = con.prepareCall(sql);
   
            query = command.executeQuery();
            if (query.next()) {

                 modelo = new CaixaModel();
                 modelo.setId(query.getInt("c.id"));
                 modelo.setDataAbertura(query.getString("c.dataAbertura"));
                 modelo.setDataFecho(query.getString("c.dataFecho"));
                 modelo.setUsuario(new UsuarioModel(idUsuario, ""));
                 modelo.setValorInicial(query.getDouble("c.valorInicial"));
                 modelo.setEstado(status);
                return modelo;
            }

        } catch (Exception ex) {
            Logger.getLogger(CaixaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return modelo;
    }
    public int getTotal(int idUsuario) {

        try {

            con = conFactory.open();
            String sql = " SELECT count(id) FROM caixa c where "
                    + " c.idUsuario  = "+idUsuario+" ORDER BY c.dataAbertura DESC LIMIT 1 ";

            command = con.prepareCall(sql);
   
            query = command.executeQuery();
            if (query.next()) {

                return query.getInt("count(id)");
            }

        } catch (Exception ex) {
            Logger.getLogger(CaixaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return 0;
    }
    
    public CaixaModel getByDateAndUsuario(String dataActual, UsuarioModel usuario) {

        CaixaModel modelo = null;
        try {

            con = conFactory.open();
            String sql = " SELECT * FROM caixa c where date(c.dataAbertura) = '"+dataActual+"' "
                    + "and c.idUsuario  = "+usuario.getId()+" ORDER BY c.dataAbertura DESC LIMIT 1 ";

            command = con.prepareCall(sql);
   
            query = command.executeQuery();
            if (query.last()) {

                 modelo = new CaixaModel();
                 modelo.setId(query.getInt("c.id"));
                 modelo.setDataAbertura(query.getString("c.dataAbertura"));
                 modelo.setUsuario(usuario);
                 modelo.setValorInicial(query.getDouble("c.valorInicial"));
                 modelo.setEstado(query.getString("c.estado"));
                return modelo;
            }

        } catch (Exception ex) {
            Logger.getLogger(CaixaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
        }
        return modelo;
    }

    @Override
    public List<CaixaModel> get() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CaixaModel getById(int id) {
        
        CaixaModel modelo = null;
        try {

            con = conFactory.open();
            String sql = " SELECT * FROM caixa c where "
                    + "  c.id ='"+id+"' ORDER BY c.dataAbertura DESC LIMIT 1 ";

            command = con.prepareCall(sql);
   
            query = command.executeQuery();
            if (query.next()) {

                 modelo = new CaixaModel();
                 modelo.setId(query.getInt("c.id"));
                 modelo.setDataAbertura(query.getString("c.dataAbertura"));
                 modelo.setDataFecho(query.getString("c.dataFecho"));
                 modelo.setUsuario(new UsuarioController().getById(query.getInt("c.idUsuario")));
                 modelo.setValorInicial(query.getDouble("c.valorInicial"));
                 modelo.setEstado(query.getString("c.estado"));
                return modelo;
            }

        } catch (Exception ex) {
            Logger.getLogger(CaixaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
        }
        return modelo;
    
    }
}
