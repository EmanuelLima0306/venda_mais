/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.EmpresaModel;
import Model.EstadoModel;
import Model.TipoRegimeModel;
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
public class EmpresaController implements IController<EmpresaModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(EmpresaModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `empresa`\n"
                    + "("
                    + "`Nome`,"
                    + "`Nif`,Email,Contacto,Endereco,WebSite,InfoConta,IdTipoRegime,Loja, Logotipo)"
                    + "VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getNome());
            command.setString(2, obj.getNif());
            command.setString(3, obj.getEmail());
            command.setString(4, obj.getContacto());
            command.setString(5, obj.getEndereco());
            command.setString(6, obj.getWebSIte());
            command.setString(7, obj.getInfoConta());
            command.setInt(8, obj.getRegime().getId());
            command.setString(9, obj.getLocal());
            command.setString(10, obj.getLogotipo());

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EmpresaController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean update(EmpresaModel obj) {

        
        
        boolean result = false;
        try {
            String sql = "   UPDATE empresa"
                    + "   SET"
                    + "   `Nome` = ?,"
                    + "`Nif` = ?,Email = ?,Contacto = ?,Endereco = ?,WebSite=?,InfoConta=?,"
                    + "IdTipoRegime = ?,Loja=?, Logotipo=?"
                    + "   WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getNome());
            command.setString(2, obj.getNif());
            command.setString(3, obj.getEmail());
            command.setString(4, obj.getContacto());
            command.setString(5, obj.getEndereco());
            command.setString(6, obj.getWebSIte());
            command.setString(7, obj.getInfoConta());
            command.setInt(8, obj.getRegime().getId());
            command.setString(9, obj.getLocal());
            command.setString(10, obj.getLogotipo());
            command.setInt(11, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EmpresaController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(EmpresaModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public EmpresaModel getById(int id) {
        
         EmpresaModel modelo = new EmpresaModel();
        try {

            con = conFactory.open();
            String sql = " SELECT e.Id as Id,e.Nome as Nome,e.Contacto as Contacto,"
                    + "e.Endereco as Endereco,e.Email as Email,e.InfoConta as InfoConta,"
                    + "e.Nif as Nif,e.WebSite as WebSite, e.Logotipo as Logotipo,r.Id as IdRegime,"
                    + "r.Designacao as regime,r.cobraImposto,e.Loja"
                    + " FROM empresa e INNER JOIN tiposregime r ON r.Id = e.IdTipoRegime"
                    + " LIMIT 1 ;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                modelo.setNome(query.getString("Nome"));
                modelo.setId(query.getInt("Id"));
                modelo.setContacto(query.getString("Contacto"));
                modelo.setEmail(query.getString("Email"));
                modelo.setEndereco(query.getString("Endereco"));
                modelo.setInfoConta(query.getString("InfoConta"));
                modelo.setNif(query.getString("Nif"));
               
                modelo.setWebSIte(query.getString("WebSite"));
                modelo.setLocal(query.getString("Loja"));
                modelo.setLogotipo(query.getString("Logotipo"));
                
                modelo.setRegime(new TipoRegimeModel(query.getInt("IdRegime"), query.getString("regime"),query.getBoolean("cobraImposto")));
            }

        } catch (Exception ex) {
            Logger.getLogger(EmpresaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return modelo;
    }

    @Override
    public List<EmpresaModel> get() {

        List<EmpresaModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM empresa"
                    + " LIMIT 1 ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

//                lista.add(new EmpresaModel(query.getInt("Id"), query.getString("Designacao")));
            }

        } catch (Exception ex) {
            Logger.getLogger(EmpresaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public EmpresaModel getAll(String text) {

        EmpresaModel lista = new EmpresaModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `empresa` WHERE Designacao = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            while (query.next()) {

//                lista = new EmpresaModel(query.getInt("Id"),
//                        query.getString("Designacao"),
//                        new EstadoModel(query.getInt("IdEstado"), ""));
            }

        } catch (Exception ex) {
            Logger.getLogger(EmpresaController.class.getName()).log(Level.SEVERE, null, ex);
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
