/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CambioModel;
import Model.CertificacaoModel;
import Model.Moeda;
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
public class CertificacaoController implements IController<CertificacaoModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(CertificacaoModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `certificacao`\n"
                    + "("
                    + "`NomeEmpresa`,"
                    + "`NifProdutorSistema`,"
                    + "`NifRepresentanteLegal`,"
                    + "`Endereco`,"
                    + "`PontoReferenca`,"
                    + "`Email`,"
                    + "`NomePrograma`,"
                    + "`VersaoPrograma`,"
                    + "`NumeroValidacao`,"
                    + "`DataValidacao`,"
                    + "`DataExpirar`)"
                    + "VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getNomeEmpresa());
            command.setString(2, obj.getNifProdutorSistema());
            command.setString(3, obj.getNifRepresentanteLegal());
            command.setString(4, obj.getEndereco());
            command.setString(5, obj.getPontoReferenca());
            command.setString(6, obj.getEmail());
            command.setString(7, obj.getNomePrograma());
            command.setString(8, obj.getVersaoPrograma());
            command.setString(9, obj.getNumeroValidacao());
            command.setString(10, obj.getDataValidacao());
            command.setString(11, obj.getDataExpirar());
            
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(CertificacaoController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean update(CertificacaoModel obj) {

        boolean result = false;
        try {
            String sql = "   UPDATE certificacao"
                    + "   SET"
                    
                   + " NomeEmpresa = ?,"
                   + " NifProdutorSistema = ?,"
                   + " NifRepresentanteLegal = ?,"
                   + " Endereco = ?,"
                   + " PontoReferenca = ?,"
                   + " Email = ?,"
                   + " NomePrograma = ?,"
                   + " VersaoPrograma = ?,"
                   + " NumeroValidacao = ?,"
                   + " DataValidacao = ?,"
                   + " DataExpirar = ?"
                   + "   WHERE Codigo = ?";
            
            con = conFactory.open();
           command = con.prepareCall(sql);
           command.setString(1, obj.getNomeEmpresa());
           command.setString(2, obj.getNifProdutorSistema());
           command.setString(3, obj.getNifRepresentanteLegal());
           command.setString(4, obj.getEndereco());
           command.setString(5, obj.getPontoReferenca());
           command.setString(6, obj.getEmail());
           command.setString(7, obj.getNomePrograma());
           command.setString(8, obj.getVersaoPrograma());
           command.setString(9, obj.getNumeroValidacao());
           command.setString(10, obj.getDataValidacao());
           command.setString(11, obj.getDataExpirar());
           command.setInt(12, obj.getCodigo());
           
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(CertificacaoController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(CertificacaoModel obj) {

        if (obj.getCodigo()> 0) {
            return update(obj);
        }
        return save(obj);
    }

//    @Override
//    public CambioModel getById(int id) {
//        
//    }

//    @Override
//    public List<CambioModel> get() {
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
//    public CambioModel getAll(String text) {
//
//        CambioModel lista = new CambioModel();
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

    @Override
    public List<CertificacaoModel> get() {
    
        List<CertificacaoModel> certificados = new ArrayList<>();
        CertificacaoModel certificado = new CertificacaoModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `certificacao`;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {
                
                certificado.setCodigo(query.getInt("Codigo"));
                certificado.setNomeEmpresa(query.getString("NomeEmpresa"));
                certificado.setNifProdutorSistema(query.getString("NifProdutorSistema"));
                certificado.setNifRepresentanteLegal(query.getString("NifRepresentanteLegal"));
                certificado.setEndereco(query.getString("Endereco"));
                certificado.setPontoReferenca(query.getString("PontoReferenca"));
                certificado.setEmail(query.getString("Email"));
                certificado.setNomePrograma(query.getString("NomePrograma"));
                certificado.setVersaoPrograma(query.getString("VersaoPrograma"));
                certificado.setNumeroValidacao(query.getString("NumeroValidacao"));
                certificado.setDataValidacao(query.getString("DataValidacao"));
                certificado.setDataExpirar(query.getString("DataExpirar"));
                certificados.add(certificado);
            }

        } catch (Exception ex) {
            Logger.getLogger(CertificacaoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return certificados;
    }

    @Override
    public CertificacaoModel getById(int codigo) {
    
        CertificacaoModel certificado = new CertificacaoModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `certificacao` WHERE Codigo = ?;";

            command = con.prepareCall(sql);
            command.setInt(1, codigo);
            query = command.executeQuery();
            if (query.next()) {
                
                certificado.setCodigo(query.getInt("Codigo"));
                certificado.setNomeEmpresa(query.getString("NomeEmpresa"));
                certificado.setNifProdutorSistema(query.getString("NifProdutorSistema"));
                certificado.setNifRepresentanteLegal(query.getString("NifRepresentanteLegal"));
                certificado.setEndereco(query.getString("Endereco"));
                certificado.setPontoReferenca(query.getString("PontoReferenca"));
                certificado.setEmail(query.getString("Email"));
                certificado.setNomePrograma(query.getString("NomePrograma"));
                certificado.setVersaoPrograma(query.getString("VersaoPrograma"));
                certificado.setNumeroValidacao(query.getString("NumeroValidacao"));
                certificado.setDataValidacao(query.getString("DataValidacao"));
                certificado.setDataExpirar(query.getString("DataExpirar"));
                
                return certificado;
            }

        } catch (Exception ex) {
            Logger.getLogger(CertificacaoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return certificado;
    }
}
