/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CategoriaModel;
import Model.EstadoModel;
import Model.PermanenteModel;
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
public class PermanenteController implements IController<PermanenteModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(PermanenteModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `permanente`\n"
                    + "("
                    + "`idCaixa`,"
                    + "`idUsuario`,"
                    + "`idCategoria`,"
                    + "`totalMulticaixa`,"
                    + "`totalNumerario`,"
                    + "`idEstado`)"
                    + "VALUES"
                    + "(?,?,?,?,?,?)";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getCaixa().getId());
            command.setInt(2, obj.getUsuario().getId());
            command.setInt(3, obj.getCategoria().getId());
            command.setDouble(4, obj.getTotalMulticaixa());
            command.setDouble(5, obj.getTotalNumerario());
            command.setInt(6, obj.getEstado().getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(PermanenteController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conFactory.close(con, command);
        }
        return result;
    }

    @Override
    public boolean update(PermanenteModel obj) {

        boolean result = false;
        try {
            String sql = "   UPDATE permanente"
                    + "   SET"
                    + "   idCaixa = ?,"
                    + "   idUsuario = ?,"
                    + "   idCategoria = ?,"
                    + "   totalMulticaixa = ?,"
                    + "   totalNumerario = ?,"
                    + "   idEstado = ?"
                    + "   WHERE Id = ?";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getCaixa().getId());
            command.setInt(2, obj.getUsuario().getId());
            command.setInt(3, obj.getCategoria().getId());
            command.setDouble(4, obj.getTotalMulticaixa());
            command.setDouble(5, obj.getTotalNumerario());
            command.setInt(6, obj.getEstado().getId());
            command.setInt(7, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(PermanenteController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conFactory.close(con, command);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(PermanenteModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public PermanenteModel getById(int id) {
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM permanente"
                    + " WHERE IdEstado = 1 AND Id = ? ORDER BY id;";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {

                return new PermanenteModel(query.getInt("Id"), new CaixaController().
                                   getById(query.getInt("idCaixa")),
                                   new UsuarioController().getById(query.getInt("idUsuario")),
                                   new CategoriaABController().getById(query.getInt("idCategoria")),
                                   new EstadoController().getById(query.getInt("idEstado")));
            }
            
            //con.close();
            command.close();
            query.close();
        } catch (Exception ex) {
            Logger.getLogger(PermanenteController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
        }
        return null;
    }

    @Override
    public List<PermanenteModel> get() {

        List<PermanenteModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM permanente"
                    + " WHERE IdEstado = 1 ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                lista.add(new PermanenteModel(query.getInt("Id"), new CaixaController().
                                            getById(query.getInt("idCaixa")),
                                            new UsuarioController().getById(query.getInt("idUsuario")),
                                            new CategoriaABController().getById(query.getInt("idCategoria")),
                                            new EstadoController().getById(query.getInt("idEstado"))));
            }
//            //conFactory.close(con, command, query);
            //con.close();
            command.close();
            query.close();
        } catch (Exception ex) {
            Logger.getLogger(PermanenteController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
        }
        return lista;
    }
    public List<PermanenteModel> get(String pesquisa) {

        List<PermanenteModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT p.Id,p.idCaixa,p.idUsuario,p.idCategoria, p.idEstado, p.totalMulticaixa, p.totalNumerario "
                    + " FROM permanente p inner join caixa c on c.id=p.idCaixa inner join usuario u on u.id=c.idUsuario "
                    + " WHERE date(c.dataAbertura) like '%"+pesquisa+"%' OR u.Nome like '%"+pesquisa+"%' AND p.IdEstado = 1 ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {
                PermanenteModel permanenteModel = new PermanenteModel(query.getInt("p.Id"), new CaixaController().
                                            getById(query.getInt("p.idCaixa")),
                                            new UsuarioController().getById(query.getInt("p.idUsuario")),
                                            new CategoriaABController().getById(query.getInt("p.idCategoria")),
                                            new EstadoController().getById(query.getInt("p.idEstado")));
                permanenteModel.setTotalMulticaixa(query.getDouble("p.totalMulticaixa"));
                permanenteModel.setTotalNumerario(query.getDouble("p.totalNumerario"));
                lista.add(permanenteModel);
            }
//            //conFactory.close(con, command, query);
            //con.close();
            command.close();
            query.close();
        } catch (Exception ex) {
            Logger.getLogger(PermanenteController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
        }
        return lista;
    }
    public boolean existe(String data, UsuarioModel usuario) {

        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM permanente p inner join caixa c on c.id=p.idCaixa where date(c.dataAbertura) = '"+data+"' "
                    + " AND c.idUsuario  = "+usuario.getId()
                    + " AND IdEstado = 1 ;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {
                return true;
            }
//            //conFactory.close(con, command, query);
            //con.close();
            command.close();
            query.close();
        } catch (Exception ex) {
            Logger.getLogger(PermanenteController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
        }
        return false;
    }
    
    public PermanenteModel getLastPermanente(UsuarioModel model) {

        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM permanente"
                    + " WHERE IdEstado = 1 AND IdUsuario = ? Order by Id DESC LIMIT 1 ;";

            command = con.prepareCall(sql);
            command.setInt(1, model.getId());
            query = command.executeQuery();
            if (query.last()) {

               return getById(query.getInt("Id"));

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
        }
        return null;
    }

    public CategoriaModel getAll(String text) {

        CategoriaModel lista = new CategoriaModel();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM `categoria` WHERE Designacao = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            while (query.next()) {

                lista = new CategoriaModel(query.getInt("Id"),
                        query.getString("Designacao"),
                        new EstadoModel(query.getInt("IdEstado"), ""));
            }

        } catch (Exception ex) {
            Logger.getLogger(PermanenteController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
        }
        return lista;
    }

    public void ireportCategoria() {

        String relatorio = "Relatorio/ListaCategoria.jasper";
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        
        
        
//        hashMap.put("LOGOTIPO", "Relatorio/logo.jpg");
//        hashMap.put("LOGOTIPO",  new File("Relatorio/logo.jpg").getAbsoluteFile());
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
