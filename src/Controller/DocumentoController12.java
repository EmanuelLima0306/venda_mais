/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Documento;
import Model.EstadoModel;
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
public class DocumentoController12 implements IController<Documento> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(Documento obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `numeracao_documento`\n"
                    + "("
                    + "`Designacao`,"
                    + "`Next`)"
                    + "VALUES"
                    + "(?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDesignacao());
            command.setString(2, obj.getNext());

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(DocumentoController12.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean update(Documento obj) {

        boolean result = false;
        try {
            String sql = "   UPDATE numeracao_documento"
                    + "   SET"
                    + "   Designacao = ?,"
                    + "   Next = ?"
                    + "   WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDesignacao());
            command.setString(2, obj.getNext());
            command.setInt(3, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(DocumentoController12.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(Documento obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    public int getLastInsertAno(String designacao) {
        String sql = "select Next as last from numeracao_documento where Designacao ='" + designacao + "'";
        con = conFactory.open();
int codigo = 0;
        try {
            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {
                codigo = query.getInt("last");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
            conFactory.close(con, command,query);
        }
        return codigo;
    }

    @Override
    public Documento getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Documento> get() {

        List<Documento> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM numeracao_documento ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                lista.add(new Documento(query.getInt("Id"), query.getString("Designacao"), query.getString("Next")));
            }

        } catch (Exception ex) {
            Logger.getLogger(DocumentoController12.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public boolean updateNextNumDoc(String designacao) {
        String sql = "UPDATE numeracao_documento AS ps,(SELECT Next FROM numeracao_documento where designacao ='" + designacao + "') AS p SET ps.Next = p.Next + 1 where designacao ='" + designacao + "'";
        con = conFactory.open();
        boolean flag = false;
        try {
            command = con.prepareCall(sql);
            flag = command.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DocumentoController12.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return flag;
    }

    public Documento getAll(String text) {

        Documento lista = new Documento();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `numeracao_documento` WHERE Designacao = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            while (query.next()) {

                lista = new Documento(query.getInt("Id"), query.getString("Designacao"), query.getString("Next"));
            }

        } catch (Exception ex) {
            Logger.getLogger(DocumentoController12.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public void ireportCategoria() {

        String relatorio = "Relatorio/ListaCategoria.jasper";
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("LOGOTIPO", "Relatorio/logo.jpg");
        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            if (jasperPrint.getPages().size() >= 1) {

                JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                jasperViewer.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "Folha vazia !...");
            }
        } catch (JRException jex) {
            jex.printStackTrace();
            //System.out.println("aqui");
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR  gráfico!...");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR gráfico !...");
        }

    }
}
