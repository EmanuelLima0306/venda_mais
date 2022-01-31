/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ParamentroModel;
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
public class ParamentroController implements IController<ParamentroModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(ParamentroModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `parametro`\n"
                    + "("
                    + "`Descricao`,"
                    + "`Valor`)"
                    + "VALUES"
                    + "(?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDescricao());
            command.setInt(2, obj.getValor());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ParamentroController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean update(ParamentroModel obj) {

        boolean result = false;
        try {
            String sql = "   UPDATE parametro"
                    + "   SET"
                    + "   Descricao = ?,"
                    + "   Valor = ?"
                    + "   WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDescricao());
            command.setInt(2, obj.getValor());
            command.setInt(3, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ParamentroController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(ParamentroModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public ParamentroModel getById(int id) {
       
        ParamentroModel modelo =null;
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM parametro WHERE Id = ?";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {

                modelo = new ParamentroModel(query.getInt("Id"), query.getString("Descricao"),query.getInt("Valor"));
            }
            command.close();
            ////con.close();
            //query.close();
            ////conFactory.close(con, command, query);

        } catch (Exception ex) {
            Logger.getLogger(ParamentroController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return modelo;
    }

    @Override
    public List<ParamentroModel> get() {

        List<ParamentroModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM parametro";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                lista.add(new ParamentroModel(query.getInt("Id"), query.getString("Descricao"),query.getInt("Valor")));
            }

        } catch (Exception ex) {
            Logger.getLogger(ParamentroController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public ParamentroModel getAll(String text) {

        ParamentroModel lista = new ParamentroModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `parametro` WHERE Descricao = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            while (query.next()) {

                lista = new ParamentroModel(query.getInt("Id"),
                        query.getString("Descricao"),
                       query.getInt("Valor"));
            }

        } catch (Exception ex) {
            Logger.getLogger(ParamentroController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    
    
    public ParamentroModel getImpressoraCozinha()
    {
        
        ParamentroModel parametro = getById(11);
        if(parametro.getValor() == 1){
            return parametro;
        }else{
            parametro.setDescricao("Microsoft XPS Document Writer");
            return parametro;
        }
    }

    public void ireportCategoria() {

        
        String relatorio = "Relatorio/ListaCategoria.jasper";
        Connection connection = conFactory.open();
        
        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("LOGOTIPO", "Relatorio/logo.jpg");
        try 
        {
            JasperFillManager.fillReport(obterCaminho,hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            if (jasperPrint.getPages().size() >= 1) 
            {                
                
                
                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                    jasperViewer.setVisible(true);
                
            } 
            else 
            {
                JOptionPane.showMessageDialog(null, "Folha vazia !...");
            }
        } 
        catch (JRException jex) 
        {
            jex.printStackTrace();
            //System.out.println("aqui");
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR  gráfico!...");
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR gráfico !...");
        }
        
    }
}
