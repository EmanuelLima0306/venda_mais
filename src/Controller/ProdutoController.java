/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ArmazemModel;
import Model.CategoriaModel;
import Model.EstadoModel;
import Model.FabricanteModel;
import Model.Motivo;
import Model.ProdutoModel;
import Model.Taxa;
import com.mysql.jdbc.MysqlDataTruncation;
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
public class ProdutoController implements IController<ProdutoModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(ProdutoModel obj) {

        boolean result = false;

        try {
            String sql = "INSERT INTO produto(\n"
                    + "`Designacao`,\n"
                    + "`Descricao`,\n"
                    + "`Referencia`,\n"
                    + "`IdCategoria`,\n"
                    + "`IdFabricante`,\n"
                    + "`Expira`,\n"
                    + "`Stocavel`,\n"
                    + "`iva`,\n"
                    + "`Data`,\n"
                    + "`IdUsuario`,\n"
                    + "`StockMinimo`,\n"
                    + "`AlertaExpiracao`,\n"
                    + "`AlertaQuantidade`,\n"
                    + "`PrasoDevolucao`,\n"
                    + "`IdEstado`,ValorVenda,Organizacao,IdMotivo,IdTaxa,IsMenuDia,Garantia,Imagem,IsCozinha)\n"
                    + "VALUES(\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,?,?,?,?,?,?,?,?);";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDesignacao());
            command.setString(2, obj.getDescricao());
            command.setString(3, obj.getReferencia());
            command.setInt(4, obj.getCategoria().getId());
            command.setInt(5, obj.getFabricante().getId());
            command.setBoolean(6, obj.isExpira());
            command.setBoolean(7, obj.isStocavel());
            command.setBoolean(8, obj.isIpc());
            command.setString(9, obj.getData());
            command.setInt(10, obj.getUsuario().getId());
            command.setInt(11, obj.getStockMinimo());
            command.setInt(12, obj.getDiaAlerta());
            command.setInt(13, obj.getQuantCritica());
            command.setInt(14, obj.getDiaDevolucao());
            command.setInt(15, obj.getEstado().getId());
            command.setDouble(16, obj.getValorVenda());
            command.setString(17, obj.getOrganizacao());
            command.setInt(18, obj.getMotivo().getId());
            command.setInt(19, obj.getTaxa().getId());
            command.setBoolean(20, obj.isIsMenuDia());
            command.setInt(21, obj.getGarantia());
            command.setString(22, obj.getUrlImage());
            command.setBoolean(23, obj.isIsCozinha());

            result = command.executeUpdate() > 0;

        } catch (MysqlDataTruncation ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean update(ProdutoModel obj) {

        boolean result = false;
        try {
            String sql = " UPDATE `produto`\n"
                    + "SET\n"
                    + "`Designacao` = ?,\n"
                    + "`Descricao` = ?,\n"
                    + "`Referencia` = ?,\n"
                    + "`IdCategoria` = ?,\n"
                    + "`IdFabricante` = ?,\n"
                    + "`Expira` = ?,\n"
                    + "`Stocavel` = ?,\n"
                    + "`iva` = ?,\n"
                    + "`StockMinimo` = ?,\n"
                    + "`AlertaExpiracao` =?,\n"
                    + "`AlertaQuantidade` = ?,\n"
                    + "`PrasoDevolucao` = ?,\n"
                    + "`IdEstado` = ?, ValorVenda = ?,Organizacao = ?"
                    + ",IdMotivo = ?,IdTaxa = ?,IsMenuDia=?,Garantia=?, Imagem = ?, IsCozinha = ? \n"
                    + " WHERE `Id` = ?;";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setString(1, obj.getDesignacao());
            command.setString(2, obj.getDescricao());
            command.setString(3, obj.getReferencia());
            command.setInt(4, obj.getCategoria().getId());
            command.setInt(5, obj.getFabricante().getId());
            command.setBoolean(6, obj.isExpira());
            command.setBoolean(7, obj.isStocavel());
            command.setBoolean(8, obj.isIpc());
            command.setInt(9, obj.getStockMinimo());
            command.setInt(10, obj.getDiaAlerta());
            command.setInt(11, obj.getQuantCritica());
            command.setInt(12, obj.getDiaDevolucao());
            command.setInt(13, obj.getEstado().getId());
            command.setDouble(14, obj.getValorVenda());
            command.setString(15, obj.getOrganizacao());
            command.setInt(16, obj.getMotivo().getId());
            command.setInt(17, obj.getTaxa().getId());
            command.setBoolean(18, obj.isIsMenuDia());
             command.setInt(19, obj.getGarantia());
            command.setString(20, obj.getUrlImage());
            command.setBoolean(21, obj.isIsCozinha());
            command.setInt(22, obj.getId());
           
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    public boolean updatePrecoVenda(ProdutoModel obj) {

        boolean result = false;
        try {
            String sql = " UPDATE `produto`\n"
                    + "SET\n"
                    + " ValorVenda = ?\n"
                    + "WHERE `Id` = ?;";
            con = conFactory.open();
            command = con.prepareCall(sql);

            command.setDouble(1, obj.getValorVenda());
            command.setInt(2, obj.getId());
            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            conFactory.close(con, command,query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(ProdutoModel obj) {

        if (obj.getId() > 0) {
            return update(obj);
        }
        return save(obj);
    }

    @Override
    public ProdutoModel getById(int id) {
        
        ProdutoModel lista = new ProdutoModel();
        try {

            con = conFactory.open();
            String sql = " SELECT "
                    + "     produto.`Id` AS produto_Id,\n"
                    + "     produto.`Imagem` AS produto_UrlImagem,\n"
                    + "     produto.`IsCozinha` AS produto_IsCozinha,\n"
                    + "     produto.`Designacao` AS produto_Designacao,\n"
                    + "     produto.`Descricao` AS produto_Descricao,\n"
                    + "     produto.`Referencia` AS produto_Referencia,\n"
                    + "     produto.`IdCategoria` AS produto_IdCategoria,\n"
                    + "     produto.`IdFabricante` AS produto_IdFabricante,\n"
                    + "     produto.`Expira` AS produto_Expira,\n"
                    + "     produto.`Stocavel` AS produto_Stocavel,\n"
                    + "     produto.`iva` AS produto_iva,\n"
                    + "     produto.`StockMinimo` AS produto_StockMinimo,\n"
                    + "     produto.`AlertaExpiracao` AS produto_AlertaExpiracao,\n"
                    + "     produto.`AlertaQuantidade` AS produto_AlertaQuantidade,\n"
                    + "     produto.`PrasoDevolucao` AS produto_PrasoDevolucao,"
                    + "     produto.IdEstado AS Estado, "
                    + "     produto.IdTaxa,"
                    + "     Organizacao\n"
                    + "     FROM\n"
                    + "     `produto` produto "
                    + "  WHERE  produto.`Id` = ?";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            System.out.println(sql);
            if (query.next()) {

                ProdutoModel modelo = new ProdutoModel(query.getInt("produto_Id"), query.getString("produto_Designacao"));
                // modelo.setCategoria(new CategoriaModel(query.getInt("categoria_Id"), query.getString("categoria_Designacao")));
                //  modelo.setFabricante(new FabricanteModel(query.getInt("fabricante_Id"), query.getString("fabricante_Nome")));
                modelo.setDescricao(query.getString("produto_Descricao"));
                modelo.setReferencia(query.getString("produto_Referencia"));
                modelo.setExpira(query.getBoolean("produto_Expira"));
                modelo.setIpc(query.getBoolean("produto_iva"));
                modelo.setStocavel(query.getBoolean("produto_Stocavel"));
                modelo.setDiaAlerta(query.getInt("produto_AlertaExpiracao"));
                modelo.setStockMinimo(query.getInt("produto_StockMinimo"));
                modelo.setDiaDevolucao(query.getInt("produto_PrasoDevolucao"));
                modelo.setEstado(new EstadoModel(query.getInt("Estado"), ""));
                //modelo.setTaxa(new TaxaController().getById(query.getInt("Taxa")));
                Taxa taxa = new Taxa();
                TaxaController taxaController = new TaxaController();                
                System.out.println("IDTAXA::::: "+query.getInt("produto.IdTaxa"));
                
                //taxa = taxaController.getById(query.getInt("produto.IdTaxa"));
                modelo.setTaxa(taxa);
                modelo.setOrganizacao(query.getString("Organizacao"));
                modelo.setUrlImage(query.getString("produto_UrlImagem"));
                modelo.setIsCozinha(query.getBoolean("produto_IsCozinha"));
                return modelo;
            }

        } catch (Exception ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    
    }
    public ProdutoModel getById(int id,Connection con) {
        
        ProdutoModel lista = new ProdutoModel();
        try {

    
            String sql = " SELECT\n"
                    //                    + "     fabricante.`Nome` AS fabricante_Nome,\n"
                    //                    + "     fabricante.`Id` AS fabricante_Id,\n"
                    + "     produto.`Id` AS produto_Id,\n"
                    + "     produto.`Designacao` AS produto_Designacao,\n"
                    + "     produto.`Descricao` AS produto_Descricao,\n"
                    + "     produto.`Referencia` AS produto_Referencia,\n"
                    + "     produto.`IdCategoria` AS produto_IdCategoria,\n"
                    + "     produto.`IdFabricante` AS produto_IdFabricante,\n"
                    + "     produto.`Expira` AS produto_Expira,\n"
                    + "     produto.`Stocavel` AS produto_Stocavel,\n"
                    + "     produto.`iva` AS produto_iva,\n"
                    + "     produto.`StockMinimo` AS produto_StockMinimo,\n"
                    + "     produto.`AlertaExpiracao` AS produto_AlertaExpiracao,\n"
                    + "     produto.`AlertaQuantidade` AS produto_AlertaQuantidade,\n"
                    + "     produto.`PrasoDevolucao` AS produto_PrasoDevolucao,"
                    + "     produto.IdEstado AS Estado, produto.IdTaxa, Organizacao\n"
                    //                    + "     categoria.`Id` AS categoria_Id,\n"
                    //                    + "     categoria.`Designacao` AS categoria_Designacao\n"
                    + "  FROM\n"
                    + "     `produto` produto"
                    + "  WHERE  produto.`Id` = ? \n";

            command = con.prepareCall(sql);
            command.setInt(1, id);
            query = command.executeQuery();
            if (query.next()) {

                ProdutoModel modelo = new ProdutoModel(query.getInt("produto_Id"), query.getString("produto_Designacao"));
                // modelo.setCategoria(new CategoriaModel(query.getInt("categoria_Id"), query.getString("categoria_Designacao")));
                //  modelo.setFabricante(new FabricanteModel(query.getInt("fabricante_Id"), query.getString("fabricante_Nome")));
                modelo.setDescricao(query.getString("produto_Descricao"));
                modelo.setReferencia(query.getString("produto_Referencia"));
                modelo.setExpira(query.getBoolean("produto_Expira"));
                modelo.setIpc(query.getBoolean("produto_iva"));
                modelo.setStocavel(query.getBoolean("produto_Stocavel"));
                modelo.setDiaAlerta(query.getInt("produto_AlertaExpiracao"));
                modelo.setStockMinimo(query.getInt("produto_StockMinimo"));
                modelo.setDiaDevolucao(query.getInt("produto_PrasoDevolucao"));
                modelo.setEstado(new EstadoModel(query.getInt("Estado"), ""));
                modelo.setTaxa(new TaxaController().getById(query.getInt("IdTaxa")));
                modelo.setOrganizacao(query.getString("Organizacao"));
                return modelo;
            }

        } catch (Exception ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return lista;
    
    }

    @Override
    public List<ProdutoModel> get() {

        List<ProdutoModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT DISTINCT\n"
                    + "     fabricante.`Nome` AS fabricante_Nome,\n"
                    + "     fabricante.`Id` AS fabricante_Id,\n"
                    + "     produto.`Id` AS produto_Id,\n"
                    + "     produto.`IdTaxa` AS taxa,\n"
                    + "     produto.`Designacao` AS produto_Designacao,\n"
                    + "     produto.`Descricao` AS produto_Descricao,\n"
                    + "     produto.`Referencia` AS produto_Referencia,\n"
                    + "     produto.`IdCategoria` AS produto_IdCategoria,\n"
                    + "     produto.`IdFabricante` AS produto_IdFabricante,\n"
                    + "     produto.`Expira` AS produto_Expira,\n"
                    + "     produto.`Stocavel` AS produto_Stocavel,\n"
                    + "     produto.`iva` AS produto_iva,\n"
                    + "     produto.`StockMinimo` AS produto_StockMinimo,\n"
                    + "     produto.`AlertaExpiracao` AS produto_AlertaExpiracao,\n"
                    + "     produto.`AlertaQuantidade` AS produto_AlertaQuantidade,\n"
                    + "     produto.`PrasoDevolucao` AS produto_PrasoDevolucao,\n"
                    + "     produto.`IsCozinha` AS produto_IsCozinha,\n"
                    + "     categoria.`Id` AS categoria_Id,\n"
                    + "     categoria.`Designacao` AS categoria_Designacao,"
                    + "     produto.ValorVenda AS ValorVenda\n"
                    + "FROM\n"
                    + "     `produto` produto INNER JOIN `fabricante` fabricante ON produto.`IdFabricante` = fabricante.`Id`\n"
                    + "     INNER JOIN `categoria` categoria ON produto.`IdCategoria` = categoria.`Id`,\n"
                    + "     `fornecedor` fornecedor,\n"
                    + "     `fabricante` fabricante_A\n"
                    + "WHERE produto.IdEstado = 1 And produto.`Stocavel` = true ORDER BY produto.`Designacao` ";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                ProdutoModel modelo = new ProdutoModel(query.getInt("produto_Id"), query.getString("produto_Designacao"));
                modelo.setCategoria(new CategoriaModel(query.getInt("categoria_Id"), query.getString("categoria_Designacao")));
                modelo.setFabricante(new FabricanteModel(query.getInt("fabricante_Id"), query.getString("fabricante_Nome")));
                modelo.setDescricao(query.getString("produto_Descricao"));
                modelo.setReferencia(query.getString("produto_Referencia"));
                modelo.setExpira(query.getBoolean("produto_Expira"));
                modelo.setIpc(query.getBoolean("produto_iva"));
                modelo.setStocavel(query.getBoolean("produto_Stocavel"));
                modelo.setDiaAlerta(query.getInt("produto_AlertaExpiracao"));
                modelo.setStockMinimo(query.getInt("produto_StockMinimo"));
                modelo.setDiaDevolucao(query.getInt("produto_PrasoDevolucao"));
                modelo.setValorVenda(query.getDouble("ValorVenda"));
                modelo.setIsCozinha(query.getBoolean("produto_IsCozinha"));
                modelo.setTaxa(new TaxaController().getById(query.getInt("taxa")));
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    

    public List<ProdutoModel> getProdutos() {

        List<ProdutoModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT DISTINCT\n"
                    + "     fabricante.`Nome` AS fabricante_Nome,\n"
                    + "     fabricante.`Id` AS fabricante_Id,\n"
                    + "     produto.`Id` AS produto_Id,\n"
                    + "     produto.`Designacao` AS produto_Designacao,\n"
                    + "     produto.`Descricao` AS produto_Descricao,\n"
                    + "     produto.`Referencia` AS produto_Referencia,\n"
                    + "     produto.`IdCategoria` AS produto_IdCategoria,\n"
                    + "     produto.`IdFabricante` AS produto_IdFabricante,\n"
                    + "     produto.`Expira` AS produto_Expira,\n"
                    + "     produto.`Stocavel` AS produto_Stocavel,\n"
                    + "     produto.`iva` AS produto_iva,\n"
                    + "     produto.`StockMinimo` AS produto_StockMinimo,\n"
                    + "     produto.`AlertaExpiracao` AS produto_AlertaExpiracao,\n"
                    + "     produto.`AlertaQuantidade` AS produto_AlertaQuantidade,\n"
                    + "     produto.`PrasoDevolucao` AS produto_PrasoDevolucao,\n"
                    + "     produto.`IsCozinha` AS produto_IsCozinha,\n"
                    + "     categoria.`Id` AS categoria_Id,\n"
                    + "     categoria.`Designacao` AS categoria_Designacao,"
                    + "     produto.ValorVenda AS ValorVenda,produto.Organizacao AS Organizacao\n"
                    + "FROM\n"
                    + "     `produto` produto INNER JOIN `fabricante` fabricante ON produto.`IdFabricante` = fabricante.`Id`\n"
                    + "     INNER JOIN `categoria` categoria ON produto.`IdCategoria` = categoria.`Id`,\n"
                    + "     `fornecedor` fornecedor,\n"
                    + "     `fabricante` fabricante_A\n"
                    + "WHERE produto.IdEstado = 1 ORDER BY produto.`Designacao` ";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                ProdutoModel modelo = new ProdutoModel(query.getInt("produto_Id"), query.getString("produto_Designacao"));
                modelo.setCategoria(new CategoriaModel(query.getInt("categoria_Id"), query.getString("categoria_Designacao")));
                modelo.setFabricante(new FabricanteModel(query.getInt("fabricante_Id"), query.getString("fabricante_Nome")));
                modelo.setDescricao(query.getString("produto_Descricao"));
                modelo.setReferencia(query.getString("produto_Referencia"));
                modelo.setExpira(query.getBoolean("produto_Expira"));
                modelo.setIpc(query.getBoolean("produto_iva"));
                modelo.setOrganizacao(query.getString("Organizacao"));
                modelo.setStocavel(query.getBoolean("produto_Stocavel"));
                modelo.setDiaAlerta(query.getInt("produto_AlertaExpiracao"));
                modelo.setStockMinimo(query.getInt("produto_StockMinimo"));
                modelo.setDiaDevolucao(query.getInt("produto_PrasoDevolucao"));
                modelo.setValorVenda(query.getDouble("ValorVenda"));
                modelo.setSerieModel(new ServicoController().getByProduto(modelo));
                modelo.setIsCozinha(query.getBoolean("produto_IsCozinha"));
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<ProdutoModel> getProdutos(String flitro) {

        List<ProdutoModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * FROM produto \n"
                    + "where IdEstado = 1 \n"
                    + "AND (Designacao LIKE '" + flitro + "%' OR Id IN(SELECT IdProduto FROM entradaprodutoitem \n"
                    + "                                   WHERE CodBara LIKE '" + flitro + "%' OR Referencia LIKE '" + flitro + "%') ) ORDER BY 2; ";

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
                modelo.setStocavel(query.getBoolean("Stocavel"));
                modelo.setDiaAlerta(query.getInt("AlertaExpiracao"));
                modelo.setStockMinimo(query.getInt("StockMinimo"));
                modelo.setDiaDevolucao(query.getInt("PrasoDevolucao"));
                modelo.setValorVenda(query.getDouble("ValorVenda"));
                modelo.setOrganizacao(query.getString("Organizacao"));
                modelo.setOrganizacao(query.getString("Organizacao"));
                modelo.setIsCozinha(query.getBoolean("IsCozinha"));
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    public List<ProdutoModel> getProdutosStock(String flitro) {

        List<ProdutoModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * FROM produto \n"
                    + "where IdEstado = 1 \n AND produto.`Stocavel` = true "
                    + "AND (Designacao LIKE '" + flitro + "%' OR Id IN(SELECT e.IdProduto FROM entradaprodutoitem e Inner JOIN produto p on p.Id=e.IdProduto \n"
                    + "                                   WHERE CodBara LIKE '" + flitro + "%' OR Referencia LIKE '" + flitro + "%' AND p.Stocavel = true ) ) ORDER BY 2; ";

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
                modelo.setStocavel(query.getBoolean("Stocavel"));
                modelo.setDiaAlerta(query.getInt("AlertaExpiracao"));
                modelo.setStockMinimo(query.getInt("StockMinimo"));
                modelo.setDiaDevolucao(query.getInt("PrasoDevolucao"));
                modelo.setValorVenda(query.getDouble("ValorVenda"));
                modelo.setOrganizacao(query.getString("Organizacao"));
                modelo.setOrganizacao(query.getString("Organizacao"));
                modelo.setIsCozinha(query.getBoolean("IsCozinha"));
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<ProdutoModel> get(ArmazemModel armazem, String flitro) {

        List<ProdutoModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT * FROM produto"
                    + " WHERE Designacao LIKE'" + flitro + "%'  AND IdEstado = 1 AND Stocavel = true"
                    + " AND Id IN(select IdProduto "
                    + "          FROM entradaprodutoitem "
                    + "          WHERE IdArmazem = " + armazem.getId() + ") ORDER BY Designacao; ";

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

                modelo.setDiaAlerta(query.getInt("AlertaExpiracao"));
                modelo.setStockMinimo(query.getInt("StockMinimo"));
                modelo.setDiaDevolucao(query.getInt("PrasoDevolucao"));
                modelo.setIsCozinha(query.getBoolean("IsCozinha"));
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }
    
    public List<ProdutoModel> getByCategoria(CategoriaModel categoria) {

        List<ProdutoModel> lista = new ArrayList<>();
        try {

            con = conFactory.open();
            String sql = " SELECT DISTINCT\n"
                    + "     fabricante.`Nome` AS fabricante_Nome,\n"
                    + "     fabricante.`Id` AS fabricante_Id,\n"
                    + "     produto.`Id` AS produto_Id,\n"
                    + "     produto.`Designacao` AS produto_Designacao,\n"
                    + "     produto.`Descricao` AS produto_Descricao,\n"
                    + "     produto.`Referencia` AS produto_Referencia,\n"
                    + "     produto.`IdCategoria` AS produto_IdCategoria,\n"
                    + "     produto.`IdFabricante` AS produto_IdFabricante,\n"
                    + "     produto.`Expira` AS produto_Expira,\n"
                    + "     produto.`Stocavel` AS produto_Stocavel,\n"
                    + "     produto.`iva` AS produto_iva,\n"
                    + "     produto.`StockMinimo` AS produto_StockMinimo,\n"
                    + "     produto.`AlertaExpiracao` AS produto_AlertaExpiracao,\n"
                    + "     produto.`AlertaQuantidade` AS produto_AlertaQuantidade,\n"
                    + "     produto.`PrasoDevolucao` AS produto_PrasoDevolucao,\n"
                    + "     produto.`IsCozinha` AS produto_IsCozinha,\n"
                    + "     produto.`IdTaxa` AS id_taxa,\n"
                    + "     categoria.`Id` AS categoria_Id,\n"
                    + "     categoria.`Designacao` AS categoria_Designacao,"
                    + "     produto.ValorVenda AS ValorVenda\n"
                    + "FROM\n"
                    + "     `produto` produto INNER JOIN `fabricante` fabricante ON produto.`IdFabricante` = fabricante.`Id`\n"
                    + "     INNER JOIN `categoria` categoria ON produto.`IdCategoria` = categoria.`Id`,\n"
                    + "     `fornecedor` fornecedor,\n"
                    + "     `fabricante` fabricante_A\n"
                    + "WHERE produto.IdEstado = 1 And produto.`Stocavel` = true And produto.`IdCategoria` = ? ORDER BY produto.`Designacao` ";

            command = con.prepareCall(sql);
            command.setInt(1, categoria.getId());
            query = command.executeQuery();
            while (query.next()) {

                ProdutoModel modelo = new ProdutoModel(query.getInt("produto_Id"), query.getString("produto_Designacao"));
                modelo.setCategoria(new CategoriaModel(query.getInt("categoria_Id"), query.getString("categoria_Designacao")));
                modelo.setFabricante(new FabricanteModel(query.getInt("fabricante_Id"), query.getString("fabricante_Nome")));
                modelo.setDescricao(query.getString("produto_Descricao"));
                modelo.setReferencia(query.getString("produto_Referencia"));
                modelo.setExpira(query.getBoolean("produto_Expira"));
                modelo.setIpc(query.getBoolean("produto_iva"));
                modelo.setStocavel(query.getBoolean("produto_Stocavel"));
                modelo.setDiaAlerta(query.getInt("produto_AlertaExpiracao"));
                modelo.setStockMinimo(query.getInt("produto_StockMinimo"));
                modelo.setDiaDevolucao(query.getInt("produto_PrasoDevolucao"));
                modelo.setValorVenda(query.getDouble("ValorVenda"));
                modelo.setIsCozinha(query.getBoolean("produto_IsCozinha"));
                modelo.setTaxa(new TaxaController().getById(query.getInt("id_taxa")));
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<ProdutoModel> get(String designacao) {

        List<ProdutoModel> lista = new ArrayList<>();

        try {

            con = conFactory.open();
            String sql = " SELECT DISTINCT"
                    + "     fabricante.`Nome` AS fabricante_Nome,\n"
                    + "     fabricante.`Id` AS fabricante_Id,\n"
                    + "     produto.`Id` AS produto_Id,"
                    + "     produto.Organizacao,\n"
                    + "     produto.`Designacao` AS produto_Designacao,\n"
                    + "     produto.`Descricao` AS produto_Descricao,\n"
                    + "     produto.`Referencia` AS produto_Referencia,\n"
                    + "     produto.`IdCategoria` AS produto_IdCategoria,\n"
                    + "     produto.`IdFabricante` AS produto_IdFabricante,\n"
                    + "     produto.`Expira` AS produto_Expira,\n"
                    + "     produto.`Stocavel` AS produto_Stocavel,\n"
                    + "     produto.`iva` AS produto_iva,\n"
                    + "     produto.`StockMinimo` AS produto_StockMinimo,\n"
                    + "     produto.`AlertaExpiracao` AS produto_AlertaExpiracao,\n"
                    + "     produto.`AlertaQuantidade` AS produto_AlertaQuantidade,\n"
                    + "     produto.`PrasoDevolucao` AS produto_PrasoDevolucao,\n"
                    + "     produto.`IsCozinha` AS produto_IsCozinha,\n"
                    + "     produto.`IsMenuDia` AS produto_IsMenuDia,\n"
                    + "     produto.`Imagem` AS produto_Imagem,\n"
                    + "     categoria.`Id` AS categoria_Id,"
                    + "     produto.`Garantia` AS produto_Garantia,\n"
                    + "     categoria.`Designacao` AS categoria_Designacao,"
                    + "     produto.ValorVenda as ValorVenda,taxa.Id,taxa.Descricao,taxa.Taxa,motivo.Id,motivo.Descricao,motivo.Codigo \n"
                    + "  FROM\n"
                    + "     `produto` produto LEFT JOIN `fabricante` fabricante ON produto.`IdFabricante` = fabricante.`Id` AND fabricante.IdEstado = 1\n"
                    + "     LEFT JOIN `categoria` categoria ON produto.`IdCategoria` = categoria.`Id` AND categoria.IdEstado = 1 "
                    + "     INNER JOIN taxa taxa ON taxa.Id = produto.IdTaxa"
                    + "     INNER JOIN motivo motivo ON motivo.Id = produto.IdMotivo\n"
                    + "WHERE produto.IdEstado = 1 AND (produto.`Designacao` LIKE '" + designacao + "%' OR produto.`Referencia` LIKE '" + designacao + "%')";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                Taxa taxa = new Taxa(query.getInt("taxa.Id"),
                        query.getString("taxa.Descricao"),
                        query.getDouble("taxa.Taxa"));
                Motivo motivo = new Motivo(query.getInt("motivo.Id"),
                        query.getString("motivo.Descricao"),
                        query.getString("motivo.Codigo"));

                ProdutoModel modelo = new ProdutoModel(query.getInt("produto_Id"), query.getString("produto_Designacao"));
                modelo.setCategoria(new CategoriaModel(query.getInt("categoria_Id"), query.getString("categoria_Designacao")));
                modelo.setFabricante(new FabricanteModel(query.getInt("fabricante_Id"), query.getString("fabricante_Nome")));
                modelo.setDescricao(query.getString("produto_Descricao"));
                modelo.setReferencia(query.getString("produto_Referencia"));
                modelo.setExpira(query.getBoolean("produto_Expira"));
                modelo.setIpc(query.getBoolean("produto_iva"));
                modelo.setStocavel(query.getBoolean("produto_Stocavel"));
                modelo.setDiaAlerta(query.getInt("produto_AlertaExpiracao"));
                modelo.setStockMinimo(query.getInt("produto_StockMinimo"));
                modelo.setDiaDevolucao(query.getInt("produto_PrasoDevolucao"));
                modelo.setOrganizacao(query.getString("produto.Organizacao"));
                modelo.setValorVenda(query.getDouble("ValorVenda"));
                modelo.setGarantia(query.getInt("produto_Garantia"));
                modelo.setIsCozinha(query.getBoolean("produto_IsCozinha"));
                modelo.setIsMenuDia(query.getBoolean("produto_IsMenuDia"));
                modelo.setUrlImage(query.getString("produto_Imagem"));
                
                
                modelo.setTaxa(taxa);
                modelo.setMotivo(motivo);
                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public List<ProdutoModel> getByDesignacao(String designacao) {

        List<ProdutoModel> lista = new ArrayList<>();

        try {

            con = conFactory.open();
            String sql = " SELECT DISTINCT"
                    + "     produto.`Id` AS produto_Id,\n"
                    + "     produto.`Designacao` AS produto_Designacao\n"
                    + "FROM\n"
                    + "     `produto` produto "
                    + "WHERE produto.IdEstado = 1 AND produto.Stocavel = true AND produto.`Designacao` LIKE '" + designacao + "%'";

            command = con.prepareCall(sql);
            query = command.executeQuery();
            while (query.next()) {

                ProdutoModel modelo = new ProdutoModel(query.getInt("produto_Id"), query.getString("produto_Designacao"));

                lista.add(modelo);
            }

        } catch (Exception ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public ProdutoModel getAll(String text) {

        ProdutoModel lista = new ProdutoModel();
        try {

            con = conFactory.open();
            String sql = " SELECT\n"
                    //                    + "     fabricante.`Nome` AS fabricante_Nome,\n"
                    //                    + "     fabricante.`Id` AS fabricante_Id,\n"
                    + "     produto.`Id` AS produto_Id,\n"
                    + "     produto.`Designacao` AS produto_Designacao,\n"
                    + "     produto.`Descricao` AS produto_Descricao,\n"
                    + "     produto.`Referencia` AS produto_Referencia,\n"
                    + "     produto.`IdCategoria` AS produto_IdCategoria,\n"
                    + "     produto.`IdFabricante` AS produto_IdFabricante,\n"
                    + "     produto.`Expira` AS produto_Expira,\n"
                    + "     produto.`Stocavel` AS produto_Stocavel,\n"
                    + "     produto.`iva` AS produto_iva,\n"
                    + "     produto.`StockMinimo` AS produto_StockMinimo,\n"
                    + "     produto.`AlertaExpiracao` AS produto_AlertaExpiracao,\n"
                    + "     produto.`AlertaQuantidade` AS produto_AlertaQuantidade,\n"
                    + "     produto.`PrasoDevolucao` AS produto_PrasoDevolucao,"
                    + "     produto.`IsCozinha` AS produto_IsCozinha,"
                    + "     produto.IdEstado AS Estado\n"
                    //                    + "     categoria.`Id` AS categoria_Id,\n"
                    //                    + "     categoria.`Designacao` AS categoria_Designacao\n"
                    + "  FROM\n"
                    + "     `produto` produto"
                    + "  WHERE  produto.`Designacao` = ? \n";

            command = con.prepareCall(sql);
            command.setString(1, text);
            query = command.executeQuery();
            if (query.next()) {

                ProdutoModel modelo = new ProdutoModel(query.getInt("produto_Id"), query.getString("produto_Designacao"));
                // modelo.setCategoria(new CategoriaModel(query.getInt("categoria_Id"), query.getString("categoria_Designacao")));
                //  modelo.setFabricante(new FabricanteModel(query.getInt("fabricante_Id"), query.getString("fabricante_Nome")));
                modelo.setDescricao(query.getString("produto_Descricao"));
                modelo.setReferencia(query.getString("produto_Referencia"));
                modelo.setExpira(query.getBoolean("produto_Expira"));
                modelo.setIpc(query.getBoolean("produto_iva"));
                modelo.setStocavel(query.getBoolean("produto_Stocavel"));
                modelo.setDiaAlerta(query.getInt("produto_AlertaExpiracao"));
                modelo.setStockMinimo(query.getInt("produto_StockMinimo"));
                modelo.setDiaDevolucao(query.getInt("produto_PrasoDevolucao"));
                modelo.setIsCozinha(query.getBoolean("produto_IsCozinha"));
                modelo.setEstado(new EstadoModel(query.getInt("Estado"), ""));
                lista = modelo;
            }

        } catch (Exception ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public ProdutoModel getLast() {

        ProdutoModel lista = new ProdutoModel();
        try {

            con = conFactory.open();
            String sql = " SELECT\n"
                    //                    + "     fabricante.`Nome` AS fabricante_Nome,\n"
                    //                    + "     fabricante.`Id` AS fabricante_Id,\n"
                    + "     produto.`Id` AS produto_Id,\n"
                    + "     produto.`Designacao` AS produto_Designacao,\n"
                    + "     produto.`Descricao` AS produto_Descricao,\n"
                    + "     produto.`ValorVenda` AS valor_Venda,\n"
                    + "     produto.`Referencia` AS produto_Referencia,\n"
                    + "     produto.`IdCategoria` AS produto_IdCategoria,\n"
                    + "     produto.`IdFabricante` AS produto_IdFabricante,\n"
                    + "     produto.`Expira` AS produto_Expira,\n"
                    + "     produto.`Stocavel` AS produto_Stocavel,\n"
                    + "     produto.`iva` AS produto_iva,\n"
                    + "     produto.`StockMinimo` AS produto_StockMinimo,\n"
                    + "     produto.`AlertaExpiracao` AS produto_AlertaExpiracao,\n"
                    + "     produto.`AlertaQuantidade` AS produto_AlertaQuantidade,\n"
                    + "     produto.`PrasoDevolucao` AS produto_PrasoDevolucao,"
                    + "     produto.`IsCozinha` AS produto_IsCozinha,"
                    + "     produto.IdEstado AS Estado\n"
                    //                    + "     categoria.`Id` AS categoria_Id,\n"
                    //                    + "     categoria.`Designacao` AS categoria_Designacao\n"
                    + "  FROM\n"
                    + "     `produto` produto"
                    + "   order by Id desc Limit 1\n";

            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {

                ProdutoModel modelo = new ProdutoModel(query.getInt("produto_Id"), query.getString("produto_Designacao"));
                // modelo.setCategoria(new CategoriaModel(query.getInt("categoria_Id"), query.getString("categoria_Designacao")));
                //  modelo.setFabricante(new FabricanteModel(query.getInt("fabricante_Id"), query.getString("fabricante_Nome")));
                modelo.setDescricao(query.getString("produto_Descricao"));
                modelo.setReferencia(query.getString("produto_Referencia"));
                modelo.setExpira(query.getBoolean("produto_Expira"));
                modelo.setIpc(query.getBoolean("produto_iva"));
                modelo.setStocavel(query.getBoolean("produto_Stocavel"));
                modelo.setDiaAlerta(query.getInt("produto_AlertaExpiracao"));
                modelo.setStockMinimo(query.getInt("produto_StockMinimo"));
                modelo.setDiaDevolucao(query.getInt("produto_PrasoDevolucao"));
                modelo.setEstado(new EstadoModel(query.getInt("Estado"), ""));
                modelo.setIsCozinha(query.getBoolean("produto_IsCozinha"));
                modelo.setValorVenda(query.getDouble("valor_Venda"));
                lista = modelo;
            }

        } catch (Exception ex) {
            Logger.getLogger(ProdutoController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return lista;
    }

    public void ireportProduto() {

        String relatorio = "Relatorio/ListaProduto.jasper";
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
