/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ArmazemModel;
import Model.CategoriaModel;
import Model.EntradaStockItemModel;
import Model.EntradaStockModel;
import Model.EstadoModel;
import Model.ParamentroModel;
import Model.ProdutoModel;
import Model.Taxa;
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
public class EntradaStockItemController implements IController<EntradaStockItemModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(EntradaStockItemModel obj) {

        if (obj.getDataExpiracao().isEmpty()) {
            return saveSemData(obj);
        }
        boolean result = false;
        try {
            
            System.out.println("Lote>>>>> "+obj.getLote());
            String sql = "INSERT INTO `entradaprodutoitem`\n"
                    + "(\n"
                    + "`IdEntrada`,\n"
                    + "`IdProduto`,\n"
                    + "`Qtd`,\n"
                    + "`PrecoVenda`,\n"
                    + "`CodBara`,\n"
                    + "`DataExpiracao`,\n"
                    + "`IdArmazem`,\n"
                    + "`PrecoCompra`,`QtdControler`, `IdEstado`,`QtdTotal`,"
                    + "`precoUnitarioCompra`,`lucro`,`margemLucro`,`Lote`)\n"
                    + "VALUES\n"
                    + "(\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,?, ?, ?,?,?,?,?);";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getEntrada().getId());
            command.setInt(2, obj.getProduto().getId());
            command.setDouble(3, obj.getQtd());
            command.setDouble(4, obj.getPrecoVenda());
            command.setString(5, obj.getCodBarra());
            command.setString(6, obj.getDataExpiracao());
            command.setInt(7, obj.getArmazem().getId());
            command.setDouble(8, obj.getPrecoCompra());
            command.setDouble(9, obj.getQtd());
            command.setDouble(10, obj.getEstado().getId());
            command.setDouble(11, obj.getQtd());
            command.setDouble(12, obj.getPrecoUnitarioCompra());
            command.setDouble(13, obj.getLucro());
            command.setDouble(14, obj.getMargemLucro());
            command.setString(15, obj.getLote());
            // command.setDouble(9, obj.getQtd());
                    System.out.println("sql:::"+obj.getDataExpiracao());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }
    
    
    public List<EntradaStockItemModel> getAll() {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM entradaprodutoitem"
                    + " WHERE IdEstado in (1,13);";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
                modelo = new EntradaStockItemModel();
                modelo.setId(query.getInt("Id"));
                modelo.setQtd(query.getInt("Qtd"));
                modelo.setQtdTotal(query.getDouble("QtdTotal"));
                modelo.setPrecoCompra(query.getDouble("PrecoCompra"));
                modelo.setPrecoVenda(query.getDouble("PrecoVenda"));
                modelo.setDataExpiracao(query.getString("DataExpiracao"));
                modelo.setCodBarra(query.getString("CodBara"));
                modelo.setLote(query.getString("Lote"));
                modelo.setEstado(new EstadoModel(query.getInt("IdEstado"), ""));
                modelo.setArmazem(new ArmazemController().getById(query.getInt("IdArmazem")));
                modelo.setEntrada(new EntradaStockController().getById(query.getInt("IdEntrada")));
                modelo.setProduto(new ProdutoController().getById(query.getInt("IdProduto")));
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    
    
    public boolean updateLoteItem(EntradaStockItemModel obj) {

        boolean result = false;
        try {
            String sql = "  UPDATE `entradaprodutoitem`\n"
                    + " SET\n"
                    + " `Lote` =? \n"
                    + " WHERE `Id` = ?;";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getLote());
            command.setInt(2, obj.getId());

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }
    
    public double getQtdExistencia(String codBarra, int idArmazem) {

        try {

            con = conFactory.open();
            String sql = " SELECT sum(Qtd) "
                    + " FROM entradaprodutoitem"
                    + " WHERE IdArmazem = " + idArmazem + " AND CodBara = '" + codBarra + "' AND IdEstado IN(1,13) ;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                return query.getInt("sum(Qtd)");
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
        }
        return 0;
    }
    
    public double qtdSuficiente(ProdutoModel produto) {

        try {

            con = conFactory.open();
            String sql = " SELECT sum(e.Qtd) as qtd FROM produto p \n"
                    + "INNER JOIN entradaprodutoitem e ON p.Id = e.IdProduto\n"
                    + "WHERE p.Id = ? AND e.IdEstado IN (1,13) ORDER BY e.Id desc";

            command = con.prepareCall(sql);
            command.setInt(1, produto.getId());
            query = command.executeQuery();
            if (query.next()) {

                return query.getDouble("qtd");

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
        }
        return 0;
    }

    public boolean saveActualizacao(EntradaStockItemModel obj) {

        boolean result = false;
        try {
            String sql = "Update `entradaprodutoitem`\n"
                    + " SET \n"
                    + " Qtd= " + obj.getQtd()+", QtdTotal = "+obj.getQtdTotal()
                    +", IdEstado = "+obj.getEstado().getId()
                    + " WHERE  Id = " + obj.getId();
            con = conFactory.open();
            command = con.prepareCall(sql);

//            command.setInt(3, obj.getProduto().getId());
//            command.setString(4, obj.getCodBarra());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }

    public boolean saveSemData(EntradaStockItemModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `entradaprodutoitem`\n"
                    + "(\n"
                    + "`IdEntrada`,\n"
                    + "`IdProduto`,\n"
                    + "`Qtd`,\n"
                    + "`PrecoVenda`,\n"
                    + "`CodBara`,\n"
                    + "`IdArmazem`,\n"
                    + "`PrecoCompra`,\n"
                    + "`QtdTotal`,\n"
                    + "`IdEstado`,\n"
                    + "`Lote`)\n"
                    + "VALUES\n"
                    + "(\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,"
                    + "?);";
            
              System.out.println("sql ::: "+sql);
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, obj.getEntrada().getId());
            command.setInt(2, obj.getProduto().getId());
            command.setDouble(3, obj.getQtd());
            command.setDouble(4, obj.getPrecoVenda());
            command.setString(5, obj.getCodBarra());
            command.setInt(6, obj.getArmazem().getId());
            command.setDouble(7, obj.getPrecoCompra());
            command.setDouble(8, obj.getQtd());
            command.setInt(9, obj.getEstado().getId());
            command.setString(10, obj.getLote());

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }

    @Override
    public boolean update(EntradaStockItemModel obj) {
        
        boolean result = false;
        if(obj.getDataExpiracao() == null)
            return result = updateSemData(obj);
        else
            if(obj.getDataExpiracao().isEmpty())
                return result = updateSemData(obj);
        
        try {
            String sql = "  UPDATE `entradaprodutoitem`\n"
                    + " SET\n"
                    + "\n"
                    + "`IdProduto` = ?,\n"
                    + "`Qtd` =?,\n"
                    + "`PrecoVenda` =?,\n"
                    + "`CodBara` = ?,\n"
                    + "`DataExpiracao` =?,\n"
                    + "`IdArmazem` = ?,\n"
                    + "`PrecoCompra` = ?,\n"
                    + "`IdEstado` = ?\n"
                    + "WHERE `Id` = ?;";
            
            System.out.println("sql ::: "+sql);
            con = conFactory.open();
            command = con.prepareCall(sql);

            command.setInt(1, obj.getProduto().getId());
            command.setDouble(2, obj.getQtd());
            command.setDouble(3, obj.getPrecoVenda());
            command.setString(4, obj.getCodBarra());
            command.setString(5, obj.getDataExpiracao());
            System.out.println("armazem: "+obj.getArmazem());
            command.setInt(6, obj.getArmazem().getId());
            command.setDouble(7, obj.getPrecoCompra());
            command.setInt(8, obj.getEstado().getId());
            command.setInt(9, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }
    
    public boolean updateSemData(EntradaStockItemModel obj) {
        
        boolean result = false;
        try {
            String sql = "  UPDATE `entradaprodutoitem`\n"
                    + "SET\n"
                    + "\n"
                    + "`IdProduto` = ?,\n"
                    + "`Qtd` =?,\n"
                    + "`PrecoVenda` =?,\n"
                    + "`CodBara` = ?,\n"
                    + "`IdArmazem` = ?,\n"
                    + "`PrecoCompra` = ?,\n"
                    + "`IdEstado` = ?\n"
                    + "WHERE `Id` = ?;";
            
            System.out.println("sql ::: "+sql);
            con = conFactory.open();
            command = con.prepareCall(sql);

            command.setInt(1, obj.getProduto().getId());
            command.setDouble(2, obj.getQtd());
            command.setDouble(3, obj.getPrecoVenda());
            command.setString(4, obj.getCodBarra());
            command.setInt(5, obj.getArmazem().getId());
            command.setDouble(6, obj.getPrecoCompra());
            command.setInt(7, obj.getEstado().getId());
            command.setInt(8, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }
    public boolean updateExistenciaPreco(EntradaStockItemModel obj) {

        boolean result = false;
        try {
            String sql = "  UPDATE `entradaprodutoitem`\n"
                    + "SET\n"
                    + "\n"
                    + "`Qtd` =?,\n"
                    + "`PrecoVenda` =?,\n"
                    + "`CodBara` = ?,\n"
                    + "`PrecoCompra` = ?\n"
                    + "WHERE `Id` = ?;";
            con = conFactory.open();
            command = con.prepareCall(sql);

            command.setDouble(1, obj.getQtd());
            command.setDouble(2, obj.getPrecoVenda());
            command.setString(3, obj.getCodBarra());
            command.setDouble(4, obj.getPrecoCompra());
            command.setInt(5, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }
    
    public EntradaStockItemModel getLast(ProdutoModel produto) {

        EntradaStockItemModel modelo = null;
        try {

            con = conFactory.open();
            String sql = " SELECT e.Id FROM produto p \n"
                    + "INNER JOIN entradaprodutoitem e ON p.Id = e.IdProduto\n"
                    + "WHERE p.Id = ?  ORDER BY e.Id desc";

            command = con.prepareCall(sql);
            command.setInt(1, produto.getId());
            query = command.executeQuery();
            if (query.next()) {

                modelo = new EntradaStockItemModel();
                modelo = getById(query.getInt("e.Id"));
                return modelo;

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return modelo;
    }
    
    public boolean getCodBarraExistOutroProduto( ProdutoModel pm, String codBarra) {

        boolean result = false;
        try {

            con = conFactory.open();
            String sql = " SELECT COUNT(Id) as Id "
                    + " FROM entradaprodutoitem"
                    + " WHERE  CodBara = '" + codBarra + "' AND IdProduto NOT IN("+pm.getId()+") ;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                result = query.getInt(1) > 0;

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }

    public boolean updatePreco(EntradaStockItemModel obj) {

        boolean result = false;
        try {
            String sql = "  UPDATE `entradaprodutoitem`\n"
                    + "SET\n"
                    + "`PrecoVenda` =?\n"
                    + "WHERE `CodBara` = ?;";
            con = conFactory.open();
            command = con.prepareCall(sql);

            command.setDouble(1, obj.getPrecoVenda());
            command.setString(2, obj.getCodBarra());

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }
    public boolean updateAllEstadoProdutoActivo(EntradaStockItemModel obj, EstadoModel estado) {

        boolean result = false;
        try {
            String sql = "  UPDATE `entradaprodutoitem`\n "
                    + " SET\n "
                    + " `IdEstado` = ? \n"
                    + " WHERE `IdProduto` = ? AND `IdEstado` = 1 ;";
            con = conFactory.open();
            command = con.prepareCall(sql);

            command.setInt(1, estado.getId());
            command.setInt(2, obj.getProduto().getId());

            result = command.executeUpdate() > 0;
            System.out.println("executou "+result);
        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }

    public boolean updateDataExpiracao(EntradaStockItemModel obj) {

        boolean result = false;
        try {
            String sql = "  UPDATE `entradaprodutoitem`\n"
                    + " SET\n"
                    + " `DataExpiracao` =?,\n"
                    + " `IdEstado` =?\n"
                    + " WHERE `CodBara` = ? AND Id IN ( SELECT Id FROM ( SELECT MAX(e.Id) FROM entradaprodutoitem e WHERE e.CodBara = ? ) AS t);";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDataExpiracao());
            command.setInt(2, obj.getEstado().getId());
            command.setString(3, obj.getCodBarra());
            command.setString(4, obj.getCodBarra());

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }
    public boolean updateLote(EntradaStockItemModel obj) {

        boolean result = false;
        try {
            String sql = "  UPDATE `entradaprodutoitem`\n"
                    + " SET\n"
                    + " `Lote` =? \n"
                    + " WHERE `CodBara` = ? AND IdEstado = ?;";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getLote());
            command.setString(2, obj.getCodBarra());
            command.setInt(3, 1);

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }

    public boolean updateCodBarra(String codBarraActual, String codBarraNovo) {

        boolean result = false;
        try {
            String sql = "  UPDATE `entradaprodutoitem`\n"
                    + "SET\n"
                    + "`CodBara` =?\n"
                    + "WHERE `CodBara` = ?;";
            con = conFactory.open();
            command = con.prepareCall(sql);

            command.setString(1, codBarraNovo);
            command.setString(2, codBarraActual);

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }

    public boolean updateItem(EntradaStockItemModel obj) {

        boolean result = false;
        try {
            String sql = "  UPDATE `entradaprodutoitem`\n"
                    + "SET\n"
                    + "`Qtd` =?\n"
                    + " WHERE  `Id` = ?;";
            con = conFactory.open();
            command = con.prepareCall(sql);
            //IdArmazem = ? AND IdProduto = ? AND CodBara` = ? AND

            command.setDouble(1, obj.getQtd());
            command.setInt(2, obj.getId());
            result = command.executeUpdate() > 0;
            System.out.println("actualizar quantidade::::::::: "+obj.getQtd()+" Codigo: "+obj.getProduto().getId()  );
        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }

    public boolean updateItemLoja(EntradaStockItemModel obj) {

        boolean result = false;
        try {
            String sql = "  UPDATE `entradaprodutoitem`\n"
                    + "SET\n"
                    + "`Qtd` =?\n"
                    + " WHERE  `Id` = ?;";
            con = conFactory.open();
            command = con.prepareCall(sql);
            //IdArmazem = ? AND IdProduto = ? AND CodBara` = ? AND

            command.setDouble(1, obj.getQtd());
            command.setInt(2, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(EntradaStockItemModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public EntradaStockItemModel getById(int id) {
        EntradaStockItemModel modelo = null;
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM entradaprodutoitem  "
                    + " WHERE Id = ?;";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {
                
                modelo = new EntradaStockItemModel();
                modelo.setId(query.getInt("Id"));
                modelo.setQtd(query.getInt("Qtd"));
                modelo.setQtdTotal(query.getDouble("QtdTotal"));
                modelo.setPrecoCompra(query.getDouble("PrecoCompra"));
                modelo.setPrecoVenda(query.getDouble("PrecoVenda"));
                modelo.setDataExpiracao(query.getString("DataExpiracao"));
                modelo.setCodBarra(query.getString("CodBara"));
                modelo.setLote(query.getString("Lote"));
                modelo.setEstado(new EstadoModel(query.getInt("IdEstado"), ""));
                modelo.setArmazem(new ArmazemController().getById(query.getInt("IdArmazem")));
                modelo.setEntrada(new EntradaStockController().getById(query.getInt("IdEntrada")));
                modelo.setProduto(new ProdutoController().getById(query.getInt("IdProduto")));
                

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////////conFactory.close(con, command, query);
        }
        return modelo;
    }

    @Override
    public List<EntradaStockItemModel> get() {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM entradaprodutoitem"
                    + " WHERE IdEstado = 1 ORDER BY 2;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
                modelo = getById(query.getInt("Id"));
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<EntradaStockItemModel> getQtd(String codBarra, int idArmazem) {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM entradaprodutoitem"
                    + " WHERE IdArmazem = " + idArmazem + " AND CodBara = '" + codBarra + "' AND IdEstado = 1 ;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
                //modelo.setId(query.getInt("Id"));
                modelo = getById(query.getInt("Id"));
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }
    
    public List<EntradaStockItemModel> getQtdLote(String codBarra, int idArmazem) {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM entradaprodutoitem"
                    + " WHERE IdArmazem = " + idArmazem + " AND Lote = '" + codBarra + "' AND IdEstado = 1 ;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
                //modelo.setId(query.getInt("Id"));
                modelo = getById(query.getInt("Id"));
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    
    public EntradaStockItemModel getQtdLastUsad(String codBarra, int idArmazem) {

        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM entradaprodutoitem"
                    + " WHERE IdArmazem = " + idArmazem + " AND CodBara = '" + codBarra + "' AND IdEstado = 12 ORDER BY Id ASC ;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.last()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
                //modelo.setId(query.getInt("Id"));
                modelo = getById(query.getInt("Id"));
                return modelo;
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return null;
    }

    public List<EntradaStockItemModel> getQtdLoja(String codBarra) {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM entradaprodutoitem"
                    + " WHERE IdArmazem = 1 AND  CodBara = '" + codBarra + "' AND IdEstado = 1;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
                modelo.setId(query.getInt("Id"));
                modelo.setCodBarra(query.getString("CodBara"));
                modelo.setQtd(query.getInt("Qtd"));
                ArmazemModel aModelo = new ArmazemModel();
                aModelo.setId(query.getInt("IdArmazem"));
                modelo.setArmazem(aModelo);

                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }

    public double getQtdByCodBarra(String codBarra) {

        double qtd = 0;
        try {

            con = conFactory.open();
            String sql = " SELECT SUM(Qtd) "
                    + " FROM entradaprodutoitem  "
                    + " WHERE IdArmazem = 1 and  CodBara = '" + codBarra + "' AND IdEstado = 1;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                qtd = query.getDouble(1);

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return qtd;
    }
    public int getId(int idProduto,int idArmazem) {

        int qtd = 0;
        try {

            con = conFactory.open();
            String sql = " SELECT Id "
                    + " FROM entradaprodutoitem  "
                    + " WHERE IdArmazem = "+idArmazem+" and  IdProduto = " + idProduto + " ;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                qtd = query.getInt(1);

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return qtd;
    }
    public int getQTD(int idProduto,int idArmazem) {

        int qtd = 0;
        try {

            con = conFactory.open();
            String sql = " SELECT Qtd "
                    + " FROM entradaprodutoitem  "
                    + " WHERE IdArmazem = "+idArmazem+" and  IdProduto = " + idProduto + " AND IdEstado = 1;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                qtd = query.getInt(1);

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return qtd;
    }
    public EntradaStockItemModel getByProdutoArmazem(int idProduto,int idArmazem) {

        EntradaStockItemModel modelo = null;
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM entradaprodutoitem  "
                    + " WHERE IdArmazem = "+idArmazem+" and  IdProduto = " + idProduto + " AND IdEstado = 1;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {
                
                modelo = new EntradaStockItemModel();
                modelo = getById(query.getInt("Id"));

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return modelo;
    }
    public List<EntradaStockItemModel> getByProdutoArmazems(int idProduto,int idArmazem) {

        List<EntradaStockItemModel> modelos = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM entradaprodutoitem  "
                    + " WHERE IdArmazem = "+idArmazem+" and  IdProduto = " + idProduto + " ;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {
                EntradaStockItemModel modelo = new EntradaStockItemModel();
                modelo = new EntradaStockItemModel();
                modelo = getById(query.getInt("Id"));
               
                
                modelos.add(modelo);

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return modelos;
    }
    public EntradaStockItemModel getByProdutoArmazemCodBarra(int idProduto,int idArmazem,String codBar) {

        EntradaStockItemModel modelo = null;
        try {

            con = conFactory.open();
            String sql = " SELECT * "
                    + " FROM entradaprodutoitem  "
                    + " WHERE IdArmazem = "+idArmazem+" and  IdProduto = " + idProduto +" and CodBara = '"+ codBar+"' ;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {
                
                modelo = new EntradaStockItemModel();
                modelo.setId(query.getInt("Id"));
                modelo.setQtd(query.getInt("Qtd"));
                modelo.setPrecoCompra(query.getDouble("PrecoCompra"));
                modelo.setPrecoVenda(query.getDouble("PrecoVenda"));
                modelo.setDataExpiracao(query.getString("DataExpiracao"));
                modelo.setCodBarra(query.getString("CodBara"));

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return modelo;
    }

    public int getStockMinimo(String codBarra) {

        int qtd = 0;
        try {

            con = conFactory.open();
            String sql = " SELECT p.StockMinimo "
                    + " FROM entradaprodutoitem e INNER JOIN produto p ON e.IdProduto = p.Id"
                    + " WHERE  CodBara = '" + codBarra + "' LIMIT 1;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                qtd = query.getInt(1);

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return qtd;
    }

    public double getPrecoVendaByCodBarra(String codBarra) {

        double preco = 0;
        try {

            con = conFactory.open();
            String sql = " SELECT PrecoVenda "
                    + " FROM entradaprodutoitem"
                    + " WHERE  CodBara = '" + codBarra + "' AND IdEstado = 1;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                preco = query.getDouble(1);

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return preco;
    }

    public boolean getCodBarraExist(String codBarra) {

        boolean result = false;
        try {

            con = conFactory.open();
            String sql = " SELECT COUNT(Id) as Id "
                    + " FROM entradaprodutoitem"
                    + " WHERE  CodBara = '" + codBarra + "' ;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                result = query.getInt(1) > 0;

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }

    public List<EntradaStockItemModel> getLote(int idProduto) {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT CodBara,Qtd,PrecoVenda FROM produto p \n"
                    + " INNER JOIN entradaprodutoitem e ON p.Id = e.IdProduto\n"
                    + " WHERE p.Id = " + idProduto + " and  p.Expira = true\n"
                    + " group by e.CodBara, p.Id ";
//                    + " WHERE Qtd > 0 AND IdProduto = " + idProduto+ " GROUP BY IdProduto,CodBara ";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
                //  modelo.setId(query.getInt("Id"));
                modelo.setCodBarra(query.getString("CodBara"));
                modelo.setQtd(query.getInt(2));
                modelo.setPrecoVenda(query.getDouble("PrecoVenda"));
                modelo.setQtd(query.getDouble("Qtd"));
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<EntradaStockItemModel> getLote(int idProduto, int armazem) {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT CodBara,Qtd,PrecoVenda FROM produto p \n"
                    + " INNER JOIN entradaprodutoitem e ON p.Id = e.IdProduto\n"
                    + " WHERE e.IdArmazem = " + armazem + " AND p.Id = " + idProduto + " and  p.Expira = true\n"
                    + " group by e.CodBara, p.Id ";
//                    + " WHERE Qtd > 0 AND IdProduto = " + idProduto+ " GROUP BY IdProduto,CodBara ";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
                //  modelo.setId(query.getInt("Id"));
                modelo.setCodBarra(query.getString("CodBara"));
                modelo.setQtd(query.getInt(2));
                modelo.setPrecoVenda(query.getDouble("PrecoVenda"));
                modelo.setQtd(query.getDouble("Qtd"));
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<EntradaStockItemModel> getStock(int armazem) {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT distinctrow CodBara,Qtd,PrecoVenda,p.Id as PId,\n"
                    + "p.Designacao as produto,p.ValorVenda as preco,p.Stocavel as stocavel,e.Lote \n"
                    + "FROM produto p INNER JOIN entradaprodutoitem e  ON p.Id = e.IdProduto\n"
                    + "WHERE e.IdArmazem = " + armazem + " and p.IdEstado=1 group by e.Id,p.Id,e.CodBara Order by p.Designacao";
//                    + " WHERE Qtd > 0 AND IdProduto = " + idProduto+ " GROUP BY IdProduto,CodBara ";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
//                modelo.setId(query.getInt("EID"));
                modelo.setCodBarra(query.getString("CodBara"));

                boolean stocavel = query.getBoolean("stocavel");
                modelo.setQtd(query.getDouble("Qtd"));
                modelo.setLote(query.getString("e.Lote"));
                
                ProdutoModel p = new ProdutoModel(query.getInt("PId"), query.getString("produto"));
                p.setStocavel(stocavel);
                p.setValorVenda(query.getDouble("preco"));
                modelo.setProduto(p);

                if (stocavel) {
                    modelo.setPrecoVenda(query.getDouble("PrecoVenda"));
                } else {
                    modelo.setPrecoVenda(query.getDouble("preco"));
                }
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<EntradaStockItemModel> getStock(int armazem, String pesquisar) {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {
            
            //in (1,13) para somar todas qtd

            con = conFactory.open();
            String sql = "";
            ParamentroModel parametroModel = new ParamentroController().getById(14);
            if(parametroModel.getValor() != 1){
                
                    sql = " SELECT distinctrow CodBara,Qtd as totalExistencia,PrecoVenda,p.Id as PId, \n"
                    +" p.Imagem as imagem, p.IsCozinha as IsCozinha, IdArmazem, DataExpiracao, PrecoCompra, e.IdEstado, e.Id, "
                    + "p.Designacao as produto,p.ValorVenda as preco,p.Stocavel as stocavel,"
                    + "t.Taxa taxa, t.Id taxaId,p.iva AS aplicarIVA,p.StockMinimo as StockMinimo,e.Lote \n"
                    + " FROM produto p INNER JOIN entradaprodutoitem e  ON p.Id = e.IdProduto"
                    + " INNER JOIN taxa t ON t.Id = p.IdTaxa\n"
                    + " WHERE e.IdArmazem = " + armazem + " and p.IdEstado=1 and IsMenuDia = 1 AND e.IdEstado = 1 "
                    + " AND (p.Designacao LIKE '%" + pesquisar + "%' OR CodBara LIKE '%" + pesquisar + "%') group by e.Id,p.Id,e.CodBara Order by p.Designacao limit 18";
//                    + " WHERE Qtd > 0 AND IdProduto = " + idProduto+ " GROUP BY IdProduto,CodBara ";
            }else{
                
                    
                    sql = " SELECT distinctrow CodBara,Qtd,PrecoVenda,p.Id as PId, \n"
                            +" p.Imagem as imagem, p.IsCozinha as IsCozinha, IdArmazem, DataExpiracao, PrecoCompra, e.IdEstado, e.Id, "
                            + "p.Designacao as produto,p.ValorVenda as preco,p.Stocavel as stocavel,"
                            + "t.Taxa taxa, t.Id taxaId,p.iva AS aplicarIVA,p.StockMinimo as StockMinimo,e.Lote,\n"
                            + "(SELECT sum(e2.Qtd) FROM entradaprodutoitem e2 "
                            + " WHERE e2.IdProduto = PId AND e2.IdArmazem = IdArmazem AND e2.CodBara = CodBara and e2.IdEstado in (1,13)) as totalExistencia\n"
                            + "FROM produto p INNER JOIN entradaprodutoitem e  ON p.Id = e.IdProduto"
                            + " INNER JOIN taxa t ON t.Id = p.IdTaxa\n"
                            + " WHERE e.IdArmazem = " + armazem + " and p.IdEstado=1 and IsMenuDia = 1 AND e.IdEstado = 1 "
                            + " AND (p.Designacao LIKE '%" + pesquisar + "%' OR CodBara LIKE '%" + pesquisar + "%') group by e.Id,p.Id,e.CodBara Order by p.Designacao limit 18";
                }
            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
                modelo.setId(query.getInt("e.Id"));
                modelo.setCodBarra(query.getString("CodBara"));
                modelo.setDataExpiracao(query.getString("DataExpiracao"));
                modelo.setArmazem(new ArmazemController().getById(query.getInt("IdArmazem")));
                modelo.setPrecoCompra(query.getDouble("PrecoCompra"));
                modelo.setEstado(new EstadoModel(query.getInt("e.IdEstado"),""));
                modelo.setLote(query.getString("e.Lote"));
                
                Taxa taxa = new Taxa(query.getInt("taxaId"), "", query.getDouble("taxa"));

                boolean stocavel = query.getBoolean("stocavel");
//                double qtd = query.getDouble("Qtd") - query.getInt("StockMinimo");
               int stockMinimo = query.getInt("StockMinimo");
               
                

                ProdutoModel p = new ProdutoModel(query.getInt("PId"), query.getString("produto"));
                p = new ProdutoController().getById(query.getInt("PId"));
                p.setIpc(query.getBoolean("aplicarIVA"));
                p.setStocavel(stocavel);
                p.setValorVenda(query.getDouble("preco"));
                p.setTaxa(taxa);
                p.setUrlImage(query.getString("imagem"));
                p.setIsCozinha(query.getBoolean("IsCozinha"));
                modelo.setProduto(p);
      
                if (stocavel) {
                    modelo.setPrecoVenda(query.getDouble("PrecoVenda"));
                } else {
                    modelo.setPrecoVenda(query.getDouble("preco"));
                }
                
                double existencia = query.getDouble("totalExistencia");
               double qtd = existencia - stockMinimo;
                qtd = qtd > 0 ? qtd : 0;
                modelo.setQtd(qtd);
                
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }
    
    // função para mostrar apenas a quantidade da entrada em uso
    /*public List<EntradaStockItemModel> getStock(int armazem, String pesquisar) {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT distinctrow CodBara,Qtd,PrecoVenda,p.Id as PId, \n"
                    +" p.Imagem as imagem, p.IsCozinha as IsCozinha, IdArmazem, DataExpiracao, PrecoCompra, e.IdEstado, e.Id, "
                    + "p.Designacao as produto,p.ValorVenda as preco,p.Stocavel as stocavel,"
                    + "t.Taxa taxa, t.Id taxaId,p.iva AS aplicarIVA,p.StockMinimo as StockMinimo,e.Lote \n"
                    + "FROM produto p INNER JOIN entradaprodutoitem e  ON p.Id = e.IdProduto\n"
                    + " INNER JOIN taxa t ON t.Id = p.IdTaxa\n"
                    + " WHERE e.IdArmazem = " + armazem + " and p.IdEstado=1 and IsMenuDia = 1 AND e.IdEstado = 1 "
                    + " AND (p.Designacao LIKE '%" + pesquisar + "%' OR CodBara LIKE '%" + pesquisar + "%') group by e.Id,p.Id,e.CodBara Order by p.Designacao limit 10";
//                    + " WHERE Qtd > 0 AND IdProduto = " + idProduto+ " GROUP BY IdProduto,CodBara ";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
                modelo.setId(query.getInt("e.Id"));
                modelo.setCodBarra(query.getString("CodBara"));
                modelo.setDataExpiracao(query.getString("DataExpiracao"));
                modelo.setArmazem(new ArmazemController().getById(query.getInt("IdArmazem")));
                modelo.setPrecoCompra(query.getDouble("PrecoCompra"));
                modelo.setEstado(new EstadoModel(query.getInt("e.IdEstado"),""));
                modelo.setLote(query.getString("e.Lote"));
                
                Taxa taxa = new Taxa(query.getInt("taxaId"), "", query.getDouble("taxa"));

                boolean stocavel = query.getBoolean("stocavel");
                double qtd = query.getDouble("Qtd") - query.getInt("StockMinimo");
                qtd = qtd > 0 ? qtd : 0;
                modelo.setQtd(qtd);

                ProdutoModel p = new ProdutoModel(query.getInt("PId"), query.getString("produto"));
                p = new ProdutoController().getById(query.getInt("PId"));
                p.setIpc(query.getBoolean("aplicarIVA"));
                p.setStocavel(stocavel);
                p.setValorVenda(query.getDouble("preco"));
                p.setTaxa(taxa);
                p.setUrlImage(query.getString("imagem"));
                p.setIsCozinha(query.getBoolean("IsCozinha"));
                modelo.setProduto(p);

                if (stocavel) {
                    modelo.setPrecoVenda(query.getDouble("PrecoVenda"));
                } else {
                    modelo.setPrecoVenda(query.getDouble("preco"));
                }
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }*/
    
    
    public List<EntradaStockItemModel> getStockCodBarra(int armazem, String pesquisar) {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = "";
            ParamentroModel parametroModel = new ParamentroController().getById(14);
            if(parametroModel.getValor()!=1){
                
                sql = " SELECT distinctrow CodBara,Qtd as totalExistencia,PrecoVenda,p.Id as PId, \n"
                +" p.Imagem as imagem, p.IsCozinha as IsCozinha, IdArmazem, DataExpiracao, PrecoCompra, e.IdEstado, e.Id, "
                + "p.Designacao as produto,p.ValorVenda as preco,p.Stocavel as stocavel,"
                + "t.Taxa taxa, t.Id taxaId,p.iva AS aplicarIVA,p.StockMinimo as StockMinimo,e.Lote \n"
                + "FROM produto p INNER JOIN entradaprodutoitem e  ON p.Id = e.IdProduto\n"
                + " INNER JOIN taxa t ON t.Id = p.IdTaxa\n"
                + " WHERE e.IdArmazem = " + armazem + " and p.IdEstado=1 and IsMenuDia = 1 AND e.IdEstado = 1 "
                + " AND (CodBara LIKE '%" + pesquisar + "%') group by e.Id,p.Id,e.CodBara Order by p.Designacao limit 1";
            }else{
            
                    sql = " SELECT distinctrow CodBara,Qtd,PrecoVenda,p.Id as PId, \n"
                            +" p.Imagem as imagem, p.IsCozinha as IsCozinha, IdArmazem, DataExpiracao, PrecoCompra, e.IdEstado, e.Id, "
                            + "p.Designacao as produto,p.ValorVenda as preco,p.Stocavel as stocavel,"
                            + "t.Taxa taxa, t.Id taxaId,p.iva AS aplicarIVA,p.StockMinimo as StockMinimo,e.Lote,\n"
                            + "(SELECT sum(e2.Qtd) FROM entradaprodutoitem e2 "
                            + " WHERE e2.IdProduto = PId AND e2.IdArmazem = IdArmazem AND e2.CodBara = CodBara and e2.IdEstado in (1,13)) as totalExistencia\n"
                            + "FROM produto p INNER JOIN entradaprodutoitem e  ON p.Id = e.IdProduto"
                            + " INNER JOIN taxa t ON t.Id = p.IdTaxa\n"
                            + " WHERE e.IdArmazem = " + armazem + " and p.IdEstado=1 and IsMenuDia = 1 AND e.IdEstado = 1 "
                            + " AND (p.Designacao LIKE '%" + pesquisar + "%' OR CodBara LIKE '%" + pesquisar + "%') group by e.Id,p.Id,e.CodBara Order by p.Designacao limit 18";
               }    
//                    + " WHERE Qtd > 0 AND IdProduto = " + idProduto+ " GROUP BY IdProduto,CodBara ";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {
                
                EntradaStockItemModel modelo = new EntradaStockItemModel();
                modelo.setId(query.getInt("e.Id"));
                modelo.setCodBarra(query.getString("CodBara"));
                modelo.setDataExpiracao(query.getString("DataExpiracao"));
                modelo.setArmazem(new ArmazemController().getById(query.getInt("IdArmazem")));
                modelo.setPrecoCompra(query.getDouble("PrecoCompra"));
                modelo.setEstado(new EstadoModel(query.getInt("e.IdEstado"),""));
                modelo.setLote(query.getString("e.Lote"));
                
                Taxa taxa = new Taxa(query.getInt("taxaId"), "", query.getDouble("taxa"));

                boolean stocavel = query.getBoolean("stocavel");
                double qtd = query.getDouble("totalExistencia") - query.getInt("StockMinimo");
                qtd = qtd > 0 ? qtd : 0;
                modelo.setQtd(qtd);

                ProdutoModel p = new ProdutoModel(query.getInt("PId"), query.getString("produto"));
                p = new ProdutoController().getById(query.getInt("PId"));
                p.setIpc(query.getBoolean("aplicarIVA"));
                p.setStocavel(stocavel);
                p.setValorVenda(query.getDouble("preco"));
                p.setTaxa(taxa);
                p.setUrlImage(query.getString("imagem"));
                p.setIsCozinha(query.getBoolean("IsCozinha"));
                modelo.setProduto(p);
                
                if (stocavel) {
                    modelo.setPrecoVenda(query.getDouble("PrecoVenda"));
                } else {
                    modelo.setPrecoVenda(query.getDouble("preco"));
                }
                
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }
    
    
    public List<EntradaStockItemModel> getStockCategoria(int armazem, String pesquisar, CategoriaModel categoria) {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {
            
            //in (1,13) somar todas qtd
            con = conFactory.open();
            String sql = "";
            ParamentroModel parametroModel = new ParamentroController().getById(14);
            if(parametroModel.getValor()!=1){
                
                sql = " SELECT distinctrow CodBara,Qtd as totalExistencia,PrecoVenda,p.Id as PId, \n"
                +" p.Imagem as imagem,  p.IsCozinha as IsCozinha, IdArmazem, DataExpiracao, PrecoCompra, e.IdEstado, e.Id, "
                + "p.Designacao as produto,p.ValorVenda as preco,p.Stocavel as stocavel,"
                + "t.Taxa taxa, t.Id taxaId,p.iva AS aplicarIVA,p.StockMinimo as StockMinimo,e.Lote \n"
                + " FROM produto p INNER JOIN entradaprodutoitem e  ON p.Id = e.IdProduto\n"
                + " INNER JOIN taxa t ON t.Id = p.IdTaxa\n"
                + " WHERE e.IdArmazem = " + armazem + " and p.IdEstado=1 AND p.IdCategoria = "+categoria.getId()+" AND IsMenuDia = 1 AND e.IdEstado = 1 "
                + " AND (p.Designacao LIKE '%" + pesquisar + "%' OR CodBara LIKE '%" + pesquisar + "%') group by e.Id,p.Id,e.CodBara Order by p.Designacao LIMIT 18"; //limit 12
            }else{
                
                    sql = " SELECT distinctrow CodBara,Qtd,PrecoVenda,p.Id as PId, \n"
                            +" p.Imagem as imagem,  p.IsCozinha as IsCozinha, IdArmazem, DataExpiracao, PrecoCompra, e.IdEstado, e.Id, "
                            + "p.Designacao as produto,p.ValorVenda as preco,p.Stocavel as stocavel,"
                            + "t.Taxa taxa, t.Id taxaId,p.iva AS aplicarIVA,p.StockMinimo as StockMinimo,e.Lote,\n"
                            + "(SELECT sum(e2.Qtd) FROM entradaprodutoitem e2 "
                            + " WHERE e2.IdProduto = PId AND e2.IdArmazem = IdArmazem AND e2.CodBara = CodBara and e2.IdEstado in (1,13)) as totalExistencia\n"
                            + "FROM produto p INNER JOIN entradaprodutoitem e  ON p.Id = e.IdProduto\n"
                            + " INNER JOIN taxa t ON t.Id = p.IdTaxa\n"
                            + " WHERE e.IdArmazem = " + armazem + " and p.IdEstado=1 AND p.IdCategoria = "+categoria.getId()+" AND IsMenuDia = 1 AND e.IdEstado = 1 "
                            + " AND (p.Designacao LIKE '%" + pesquisar + "%' OR CodBara LIKE '%" + pesquisar + "%') group by e.Id,p.Id,e.CodBara Order by p.Designacao LIMIT 18"; //limit 12
            }        
//                   

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
                modelo.setId(query.getInt("e.Id"));
                modelo.setCodBarra(query.getString("CodBara"));
                modelo.setDataExpiracao(query.getString("DataExpiracao"));
                modelo.setArmazem(new ArmazemController().getById(query.getInt("IdArmazem")));
                modelo.setPrecoCompra(query.getDouble("PrecoCompra"));
                modelo.setEstado(new EstadoModel(query.getInt("e.IdEstado"),""));
                modelo.setLote(query.getString("e.Lote"));
                
                Taxa taxa = new Taxa(query.getInt("taxaId"), "", query.getDouble("taxa"));

                boolean stocavel = query.getBoolean("stocavel");
//                double qtd = query.getDouble("Qtd") - query.getInt("StockMinimo");
                int stockMinimo = query.getInt("StockMinimo");

                ProdutoModel p = new ProdutoModel(query.getInt("PId"), query.getString("produto"));
                p = new ProdutoController().getById(query.getInt("PId"));
                p.setIpc(query.getBoolean("aplicarIVA"));
                p.setStocavel(stocavel);
                p.setValorVenda(query.getDouble("preco"));
                p.setTaxa(taxa);
                p.setUrlImage(query.getString("imagem"));
                p.setIsCozinha(query.getBoolean("IsCozinha"));
                modelo.setProduto(p);
                if (stocavel) {
                    modelo.setPrecoVenda(query.getDouble("PrecoVenda"));
                } else {
                    modelo.setPrecoVenda(query.getDouble("preco"));
                }
                
                double existencia = query.getDouble("totalExistencia");
               double qtd = existencia - stockMinimo;
                qtd = qtd > 0 ? qtd : 0;
                modelo.setQtd(qtd);
                
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }
    
    //mostra a quantidade da entrada de acordo a categoria
    /*public List<EntradaStockItemModel> getStockCategoria(int armazem, String pesquisar, CategoriaModel categoria) {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT distinctrow CodBara,Qtd,PrecoVenda,p.Id as PId, \n"
                    +" p.Imagem as imagem,  p.IsCozinha as IsCozinha, IdArmazem, DataExpiracao, PrecoCompra, e.IdEstado, e.Id, "
                    + "p.Designacao as produto,p.ValorVenda as preco,p.Stocavel as stocavel,"
                    + "t.Taxa taxa, t.Id taxaId,p.iva AS aplicarIVA,p.StockMinimo as StockMinimo\n"
                    + "FROM produto p INNER JOIN entradaprodutoitem e  ON p.Id = e.IdProduto\n"
                    + " INNER JOIN taxa t ON t.Id = p.IdTaxa\n"
                    + " WHERE e.IdArmazem = " + armazem + " and p.IdEstado=1 AND p.IdCategoria = "+categoria.getId()+" AND IsMenuDia = 1 AND e.IdEstado = 1 "
                    + " AND (p.Designacao LIKE '%" + pesquisar + "%' OR CodBara LIKE '%" + pesquisar + "%') group by e.Id,p.Id,e.CodBara Order by p.Designacao LIMIT 10"; //limit 12
//                    + " WHERE Qtd > 0 AND IdProduto = " + idProduto+ " GROUP BY IdProduto,CodBara ";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
                modelo.setId(query.getInt("e.Id"));
                modelo.setCodBarra(query.getString("CodBara"));
                modelo.setDataExpiracao(query.getString("DataExpiracao"));
                modelo.setArmazem(new ArmazemController().getById(query.getInt("IdArmazem")));
                modelo.setPrecoCompra(query.getDouble("PrecoCompra"));
                modelo.setEstado(new EstadoModel(query.getInt("e.IdEstado"),""));
                
                Taxa taxa = new Taxa(query.getInt("taxaId"), "", query.getDouble("taxa"));

                boolean stocavel = query.getBoolean("stocavel");
                double qtd = query.getDouble("Qtd") - query.getInt("StockMinimo");
                qtd = qtd > 0 ? qtd : 0;
                modelo.setQtd(qtd);

                ProdutoModel p = new ProdutoModel(query.getInt("PId"), query.getString("produto"));
                p = new ProdutoController().getById(query.getInt("PId"));
                p.setIpc(query.getBoolean("aplicarIVA"));
                p.setStocavel(stocavel);
                p.setValorVenda(query.getDouble("preco"));
                p.setTaxa(taxa);
                p.setUrlImage(query.getString("imagem"));
                p.setIsCozinha(query.getBoolean("IsCozinha"));
                modelo.setProduto(p);

                if (stocavel) {
                    modelo.setPrecoVenda(query.getDouble("PrecoVenda"));
                } else {
                    modelo.setPrecoVenda(query.getDouble("preco"));
                }
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }*/

    public boolean getLoteExpirado() {

        boolean result = false;
        try {

            con = conFactory.open();
            String sql = " SELECT\n" +
"     SUM(entradaprodutoitem.`Qtd`) AS entradaprodutoitem_Qtd,\n" +
"     Date(entradaprodutoitem.`DataExpiracao`) AS entradaprodutoitem_DataExpiracao,\n" +
"     empresa.`Nome` AS empresa_Nome,\n" +
"     empresa.`Nif` AS empresa_Nif,\n" +
"     empresa.`Email` AS empresa_Email,\n" +
"     empresa.`Contacto` AS empresa_Contacto,\n" +
"     empresa.`Endereco` AS empresa_Endereco,\n" +
"     empresa.`WebSite` AS empresa_WebSite,\n" +
"     empresa.`InfoConta` AS empresa_InfoConta,\n" +
"     produto.`Designacao` AS produto_Designacao,\n" +
"     produto.`Referencia` AS produto_Referencia,\n" +
"     entradaprodutoitem.`IdProduto` AS entradaprodutoitem_IdProduto,\n" +
"     entradaprodutoitem.`PrecoVenda` AS entradaprodutoitem_PrecoVenda,\n" +
"     entradaprodutoitem.`CodBara` AS entradaprodutoitem_CodBara,\n" +
"     entradaprodutoitem.`PrecoCompra` AS entradaprodutoitem_PrecoCompra,\n" +
"     armazem.`Designacao` AS armazem_Designacao,\n" +
"     empresa.`Logotipo` AS empresa_Logotipo\n" +
"FROM\n" +
"     `produto` produto INNER JOIN `entradaprodutoitem` entradaprodutoitem ON produto.`Id` = entradaprodutoitem.`IdProduto`\n" +
"     INNER JOIN `armazem` armazem ON entradaprodutoitem.`IdArmazem` = armazem.`Id`,\n" +
"     `empresa` empresa\n" +
"WHERE\n" +
"     produto.Expira = true\n" +
"     and datediff(entradaprodutoitem.DataExpiracao,curdate()) <= 0 and entradaprodutoitem.IdEstado In (1,13)\n" +
"GROUP BY\n" +
"     armazem.Id,\n" +
"     entradaprodutoitem.`CodBara`,\n" +
"     empresa.Id,\n" +
"     produto.Id,\n" +
"     entradaprodutoitem.Id\n" +
"ORDER BY\n" +
"     armazem.`Designacao` ASC,\n" +
"     produto.`Designacao` ASC";
//            String sql = " SELECT count(e.Id)from produto p INNER JOIN entradaprodutoitem e ON p.Id = e.IdProduto\n"
//                    + "WHERE  p.IdEstado = 1 AND  p.Expira = true and e.IdEstado IN (1,13) AND datediff(e.DataExpiracao,curdate()) ";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                result = query.getInt(1) > 0;
            }
            command.close();
            ////con.close();
            query.close();
            //////conFactory.close(con, command, query);
        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }
    public boolean getLotePresteExpirar() {

        boolean result = false;
        try {

            con = conFactory.open();
            String sql = " SELECT\n" +
"	datediff(entradaprodutoitem.DataExpiracao,curdate()) AS tempfalta,\n" +
"     SUM(entradaprodutoitem.`Qtd`) AS entradaprodutoitem_Qtd,\n" +
"     Date(entradaprodutoitem.`DataExpiracao`) AS entradaprodutoitem_DataExpiracao,\n" +
"     empresa.`Nome` AS empresa_Nome,\n" +
"     empresa.`Nif` AS empresa_Nif,\n" +
"     empresa.`Email` AS empresa_Email,\n" +
"     empresa.`Contacto` AS empresa_Contacto,\n" +
"     empresa.`Endereco` AS empresa_Endereco,\n" +
"     empresa.`WebSite` AS empresa_WebSite,\n" +
"     empresa.`InfoConta` AS empresa_InfoConta,\n" +
"     produto.`Designacao` AS produto_Designacao,\n" +
"     produto.`Referencia` AS produto_Referencia,\n" +
"     entradaprodutoitem.`IdProduto` AS entradaprodutoitem_IdProduto,\n" +
"     entradaprodutoitem.`PrecoVenda` AS entradaprodutoitem_PrecoVenda,\n" +
"     entradaprodutoitem.`CodBara` AS entradaprodutoitem_CodBara,\n" +
"     entradaprodutoitem.`PrecoCompra` AS entradaprodutoitem_PrecoCompra,\n" +
"     armazem.`Designacao` AS armazem_Designacao,\n" +
"     empresa.`Logotipo` AS empresa_Logotipo\n" +
"FROM\n" +
"     `produto` produto INNER JOIN `entradaprodutoitem` entradaprodutoitem ON produto.`Id` = entradaprodutoitem.`IdProduto`\n" +
"     INNER JOIN `armazem` armazem ON entradaprodutoitem.`IdArmazem` = armazem.`Id`,\n" +
"     `empresa` empresa\n" +
"WHERE\n" +
"     produto.Expira = true\n" +
"     AND entradaprodutoitem .IdEstado  in (1,13) AND (datediff(entradaprodutoitem.DataExpiracao,curdate()) - produto.AlertaExpiracao) <= 0 AND datediff(entradaprodutoitem.DataExpiracao,curdate()) > 0\n" +
"GROUP BY\n" +
"     armazem.Id,\n" +
"     entradaprodutoitem.`CodBara`,\n" +
"     empresa.Id,\n" +
"     produto.Id,\n" +
"     entradaprodutoitem.Id\n" +
"ORDER BY\n" +
"     armazem.`Designacao` ASC,\n" +
"     produto.`Designacao` ASC";
//            String sql = " SELECT count(e.Id)from produto p INNER JOIN entradaprodutoitem e ON p.Id = e.IdProduto\n"
//                    + "WHERE  p.IdEstado = 1 AND  p.Expira = true and e.IdEstado IN (1,13) and datediff(e.DataExpiracao,curdate()) <= p.AlertaExpiracao ";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                result = query.getInt(1) > 0;
            }
            command.close();
            ////con.close();
            query.close();
            //////conFactory.close(con, command, query);
        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }

    public boolean getQtdCritica() {

        boolean result = false;
        try {

            con = conFactory.open();
            String sql = " SELECT count(e.Id) from produto p INNER JOIN entradaprodutoitem e ON p.Id = e.IdProduto\n"
                    + "WHERE p.IdEstado = 1 and e.IdEstado IN (1,13) AND  p.AlertaQuantidade = e.Qtd GROUP BY p.Id";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                result = query.getInt(1) > 0;
            }
            command.close();
            ////con.close();
            query.close();
            //////conFactory.close(con, command, query);
        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return result;
    }

    public List<EntradaStockItemModel> getLoteCodigoBarra(int idProduto) {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT CodBara FROM produto p \n"
                    + "INNER JOIN entradaprodutoitem e ON p.Id = e.IdProduto\n"
                    + "WHERE p.Id = " + idProduto + "\n"
                    + "group by e.CodBara, p.Id;";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
                //  modelo.setId(query.getInt("Id"));
                modelo.setCodBarra(query.getString("CodBara"));
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }

    private String orderBy(String descricao) {

        if (descricao.equals("ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)")) {
            return "ORDER BY e.Id ASC";
        }
        if (descricao.equals("ORDEM DE CHEGADA( ULTIMO A  ENTRAR PRIMEIRO A SAIR)")) {
            return "ORDER BY e.Id DESC";
        }
        return "ORDER BY e.DataExpiracao";
    }

    public List<EntradaStockItemModel> getCodigoBarraLoja(ProdutoModel p) {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT CodBara FROM produto p \n"
                    + " INNER JOIN entradaprodutoitem e ON p.Id = e.IdProduto"
                    + " INNER JOIN armazem a ON e.IdArmazem = a.Id\n"
                    + " WHERE a.Id = 1 and  p.Id = " + p.getId() + "\n"
                    + " " + orderBy(p.getOrganizacao());

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
                //  modelo.setId(query.getInt("Id"));
                modelo.setCodBarra(query.getString("CodBara"));
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }
    public List<EntradaStockItemModel> getPendentes(ProdutoModel p) {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT e.Id, e.IdEntrada FROM produto p \n"
                    + "INNER JOIN entradaprodutoitem e ON p.Id = e.IdProduto\n"
                    + "WHERE p.Id = ?  and e.IdEstado = ? Order BY e.Id ";

            command = con.prepareCall(sql);
            command.setInt(1, p.getId());
            command.setInt(2, 13);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
                //  modelo.setId(query.getInt("Id"));
                modelo = getById(query.getInt("e.Id"));
                modelo.setProduto(p);
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }
    public List<EntradaStockItemModel> getUsoAndPendentes(String pesquisa) {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT e.IdProduto, e.IdArmazem, e.IdEstado, e.CodBara, e.DataExpiracao, e.PrecoVenda, e.PrecoCompra, e.QtdTotal, e.Qtd, e.Id, e.IdEntrada FROM produto p \n"
                    + "INNER JOIN entradaprodutoitem e ON p.Id = e.IdProduto\n"
                    +" INNER JOIN entradaproduto ep ON e.IdEntrada = ep.Id "
                    + " WHERE p.IdEstado = ?  and e.IdEstado IN (?,?) AND p.Designacao like '%"+pesquisa+"%' Order BY ep.Data, p.Designacao limit 12 ";
    
            command = con.prepareCall(sql);
            command.setInt(1, 1);
            command.setInt(2, 13);
            command.setInt(3, 1);
            query = command.executeQuery();
            while(query.next()) {
                
                EntradaStockItemModel modelo = new EntradaStockItemModel();
//                modelo = getById(query.getInt("e.Id"));
                modelo.setId(query.getInt("e.Id"));
                modelo.setQtd(query.getInt("e.Qtd"));
                modelo.setQtdTotal(query.getDouble("e.QtdTotal"));
                modelo.setPrecoCompra(query.getDouble("e.PrecoCompra"));
                modelo.setPrecoVenda(query.getDouble("e.PrecoVenda"));
                modelo.setDataExpiracao(query.getString("e.DataExpiracao"));
                modelo.setCodBarra(query.getString("e.CodBara"));
                modelo.setEstado(new EstadoModel(query.getInt("e.IdEstado"), ""));
                modelo.setArmazem(new ArmazemController().getById(query.getInt("e.IdArmazem")));
                modelo.setEntrada(new EntradaStockController().getById(query.getInt("e.IdEntrada")));
                modelo.setProduto(new ProdutoController().getById(query.getInt("e.IdProduto")));
                
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }

    public EntradaStockItemModel getLoteDataExpiracao(int idProduto, String CodBarra) {

        EntradaStockItemModel modelo = null;
        try {

            con = conFactory.open();
            String sql = " SELECT DataExpiracao,Lote FROM produto p \n"
                    + "INNER JOIN entradaprodutoitem e ON p.Id = e.IdProduto\n"
                    + "WHERE p.Id = " + idProduto + " and CodBara = '" + CodBarra + "'";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                modelo = new EntradaStockItemModel();
                //  modelo.setId(query.getInt("Id"));
                modelo.setDataExpiracao(query.getString("DataExpiracao"));
                modelo.setLote(query.getString("Lote"));
                
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return modelo;
    }
    public EntradaStockItemModel getUso(ProdutoModel produto) {

        EntradaStockItemModel modelo = null;
        try {

            con = conFactory.open();
            String sql = " SELECT e.Id FROM produto p \n"
                    + "INNER JOIN entradaprodutoitem e ON p.Id = e.IdProduto\n"
                    + "WHERE p.Id = ?  and e.IdEstado = ?";

            command = con.prepareCall(sql);
            command.setInt(1, produto.getId());
            command.setInt(2, 1);
            query = command.executeQuery();
            if (query.next()) {

                modelo = new EntradaStockItemModel();
                modelo = getById(query.getInt("e.Id"));

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return modelo;
    }

    public EntradaStockItemModel getLotePrecoVenda(int idProduto, String CodBarra) {

        EntradaStockItemModel modelo = null;
        try {

            con = conFactory.open();
            String sql = " SELECT PrecoVenda FROM produto p \n"
                    + "INNER JOIN entradaprodutoitem e ON p.Id = e.IdProduto\n"
                    + "WHERE p.Id = " + idProduto + " and CodBara = '" + CodBarra + "'";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.last()) {

                modelo = new EntradaStockItemModel();
                //  modelo.setId(query.getInt("Id"));
                modelo.setPrecoVenda(query.getDouble("PrecoVenda"));

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return modelo;
    }

    public double getQtd(ArmazemModel a, ProdutoModel p, String codBarra) {

        double total = 0;
        try {

            con = conFactory.open();
            String sql = " SELECT SUM(e.Qtd) FROM "
                    + " entradaprodutoitem e "
                    + " WHERE IdProduto = " + p.getId() + " "
                    + " AND IdArmazem = " + a.getId() + ""
                    + " AND CodBara ='" + codBarra + "' AND e.IdEstado = 1";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                total = query.getDouble(1);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }

        return total;
    }

    public int getIdEntradaItem(ArmazemModel a, ProdutoModel p, String codBarra) {

        int total = 0;
        try {

            con = conFactory.open();
            String sql = " SELECT Id FROM "
                    + " entradaprodutoitem e "
                    + " WHERE IdProduto = " + p.getId() + " "
                    + " AND IdArmazem = " + a.getId() + ""
                    + " AND CodBara ='" + codBarra + "'";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                total = query.getInt(1);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        System.out.println(">>>" + total);
        return total;
    }

    public List<EntradaStockItemModel> getIdEntradaItem1(ArmazemModel a, ProdutoModel p, String codBarra) {

        List<EntradaStockItemModel> lista = new ArrayList();
        try {

            con = conFactory.open();
            String sql = " SELECT Id,Qtd FROM "
                    + " entradaprodutoitem e "
                    + " WHERE IdProduto = " + p.getId() + " "
                    + " AND IdArmazem = " + a.getId() + ""
                    + " AND CodBara ='" + codBarra + "' AND Qtd > 0 AND e.IdEstado = 1";
            System.out.println("sql::::" + sql);
            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
                modelo = getById(query.getInt(1));
                //modelo.setQtd(query.getInt(2));
                System.out.println("QTD:::" + modelo.getQtd());
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }

        return lista;
    }

    public List<EntradaStockItemModel> getIdEntradaItem2(ArmazemModel a, ProdutoModel p, String codBarra) {

        List<EntradaStockItemModel> lista = new ArrayList();
        try {
            /*
            
            
             */
            con = conFactory.open();
            String sql = " SELECT `IdEntrada`,\n"
                    + "`IdProduto`,\n"
                    + "`Qtd`,\n"
                    + "`PrecoVenda`,\n"
                    + "`CodBara`,\n"
                    + "`DataExpiracao`,\n"
                    + "`IdArmazem`,\n"
                    + "`PrecoCompra`,Id FROM "
                    + " entradaprodutoitem e "
                    + " WHERE IdProduto = " + p.getId() + " "
                    + " AND IdArmazem = " + a.getId() + ""
                    + " AND CodBara ='" + codBarra + "' AND Qtd > 0";
            System.out.println("sql::::" + sql);
            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();
                modelo.setId(query.getInt("Id"));
                modelo.setQtd(query.getInt("Qtd"));
                EntradaStockModel entrada = new EntradaStockModel();
                entrada.setId(query.getInt("IdEntrada"));
                modelo.setPrecoCompra(query.getDouble("PrecoCompra"));
                modelo.setPrecoVenda(query.getDouble("PrecoVenda"));
                modelo.setCodBarra(query.getString("CodBara"));
                modelo.setDataExpiracao(query.getString("DataExpiracao") == null ? "" : query.getString("DataExpiracao"));
                modelo.setEntrada(entrada);
                System.out.println("QTD:::" + modelo.getQtd());
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }

        return lista;
    }

    public EntradaStockItemModel getQtdEntradaItem(ArmazemModel a, ProdutoModel p, String codBarra) {

        EntradaStockItemModel entradaItem = null;
        try {

            con = conFactory.open();
            String sql = " SELECT e.Id FROM "
                    + " entradaprodutoitem e "
                    + " WHERE IdProduto = " + p.getId() + " "
                    + " AND IdArmazem = " + a.getId() + ""
                    + " AND CodBara ='" + codBarra + "' AND e.IdEstado = 1";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                entradaItem = getById(query.getInt(1));
                return entradaItem;
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        //System.out.println("qtd_dentro >>>" + entradaItem.getQtd());
        return entradaItem;
    }
    public EntradaStockItemModel getLastQtdEntradaItem(ArmazemModel a, ProdutoModel p, String codBarra) {

        EntradaStockItemModel entradaItem = null;
        try {

            con = conFactory.open();
            String sql = " SELECT MAX(e.Id) FROM "
                    + " entradaprodutoitem e "
                    + " WHERE IdProduto = " + p.getId() + " "
                    + " AND IdArmazem = " + a.getId() + ""
                    + " AND CodBara ='" + codBarra + "' AND e.IdEstado NOT IN (3,11)";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {

                entradaItem = getById(query.getInt(1));
                return entradaItem;
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        //System.out.println("qtd_dentro >>>" + entradaItem.getQtd());
        return entradaItem;
    }

    public List<EntradaStockItemModel> getByArmazem(ArmazemModel a, String flitro) {

        List<EntradaStockItemModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * FROM entradaprodutoitem e \n"
                    + "inner JOIN produto p ON p.Id = e.IdProduto\n"
                    + "inner join armazem a on a.Id = e.IdArmazem\n"
                    + "where p.IdEstado = 1 \n"
                    + "And a.Id = " + a.getId() + "\n"
                    + "And p.Designacao like '" + flitro + "%'";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                ProdutoModel modelo = new ProdutoModel(query.getInt("Id"), query.getString("Designacao"));
//                modelo.setCategoria(new CategoriaModel(query.getInt("categoria_Id"), query.getString("categoria_Designacao")));
//                modelo.setFabricante(new FabricanteModel(query.getInt("fabricante_Id"), query.getString("fabricante_Nome")));
                modelo.setDescricao(query.getString("Descricao"));
                modelo.setReferencia(query.getString("Referencia"));
                modelo.setExpira(query.getBoolean("Expira"));
                modelo.setIpc(query.getBoolean("iva"));
                modelo.setStocavel(query.getBoolean(""));
                modelo.setDiaAlerta(query.getInt("AlertaExpiracao"));
                modelo.setStockMinimo(query.getInt("StockMinimo"));
                modelo.setDiaDevolucao(query.getInt("PrasoDevolucao"));

                EntradaStockItemModel item = new EntradaStockItemModel();
                item.setArmazem(a);
                item.setProduto(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }

    public boolean getAll(String text) {

        List<EntradaStockItemModel> lista = new ArrayList();
        try {

            con = conFactory.open();
            String sql = " SELECT COUNT(Id) "
                    + " FROM entradaprodutoitem WHERE CodBara = ?;";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            while (query.next()) {

                return query.getInt(1) > 0;

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return false;
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

    public List<String> get(ArmazemModel armazem, ProdutoModel produto) {

        List<String> lista = new ArrayList();
        try {

            con = conFactory.open();
            String sql = " SELECT DISTINCT CodBara "
                    + " FROM entradaprodutoitem WHERE IdProduto =" + produto.getId() + " AND IdArmazem = " + armazem.getId();

            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                lista.add(query.getString(1));

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<String> getByProduto(ProdutoModel produto) {

        List<String> lista = new ArrayList();
        try {

            con = conFactory.open();
            String sql = " SELECT DISTINCT CodBara "
                    + " FROM entradaprodutoitem WHERE IdProduto =" + produto.getId();

            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                lista.add(query.getString(1));

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }
    public List<String> getByProdutoActivo(ProdutoModel produto) {

        List<String> lista = new ArrayList();
        try {

            con = conFactory.open();
            String sql = " SELECT DISTINCT CodBara "
                    + " FROM entradaprodutoitem WHERE IdEstado = 1 AND IdProduto =" + produto.getId();

            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                lista.add(query.getString(1));

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<String> getByProduto(ProdutoModel produto, int idArmazem) {

        List<String> lista = new ArrayList();
        try {

            con = conFactory.open();
            String sql = " SELECT DISTINCT CodBara "
                    + " FROM entradaprodutoitem WHERE IdProduto =" + produto.getId();

            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                lista.add(query.getString(1));

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return lista;
    }

    public int get(int id) {

        int qtd = 0;
        try {
            con = conFactory.open();
            String sql = " SELECT SUM(Qtd) "
                    + " FROM entradaprodutoitem WHERE IdProduto =" + id;

            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {

                qtd = query.getInt(1);

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return qtd;
    }

    public double getTotal(int id) {

        double total = 0;
        try {
            con = conFactory.open();
            String sql = " SELECT SUM(PrecoCompra) "
                    + " FROM entradaprodutoitem WHERE IdEntrada =" + id;

            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {

                total = query.getDouble(1);

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return total;
    }

    public double getTodasDividas(int idFornecedor) {

        double total = 0;
        try {
            con = conFactory.open();
            String sql = "SELECT SUM(PrecoCompra)\n"
                    + "FROM entradaprodutoitem ei\n"
                    + "INNER  JOIN entradaproduto e ON ei.IdEntrada = e.Id \n"
                    + "WHERE  e.IdFornecedor = " + idFornecedor + " and e.IdFormaPagamento = 4";

            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {

                total = query.getDouble(1);

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return total;
    }

    public double getPrecoVenda(int id) {

        double preco = 0;
        try {
            con = conFactory.open();
            String sql = " SELECT PrecoVenda "
                    + " FROM entradaprodutoitem WHERE IdProduto =" + id;

            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {

                preco = query.getDouble(1);

            }

        } catch (Exception ex) {
            Logger.getLogger(EntradaStockItemController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //////conFactory.close(con, command, query);
        }
        return preco;
    }
}
