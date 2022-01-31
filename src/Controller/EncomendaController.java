/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.EncomendaModel;
import Model.FormaPagamentoModel;
import Model.Motivo;
import Model.Taxa;
import Model.UsuarioModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import iva.CustomerIten;
import iva.Invoice;
import iva.Invoices;
import iva.LineItens;
import iva.ProdutItem;
import iva.TaxTableItem;

/**
 *
 * @author celso
 */
public class EncomendaController implements IController<EncomendaModel> {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    @Override
    public boolean save(EncomendaModel obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean update(EncomendaModel obj) {

        boolean result = false;
        try {
            String sql = "UPDATE `encomenda`\n"
                    + "SET `IdFormaPagamento` = ? , Troco = ? ,`ValorEntregue` = ?,\n"
                    + "`ValorMulticaixa` = ? WHERE Id = ?\n";
            con = conFactory.open();
            command = con.prepareCall(sql);

//            command.setInt(1, obj.getFormaPagamento().getId());
//            command.setDouble(2, obj.getTroco());
//            command.setDouble(3, obj.getValorEntregue());
//            command.setDouble(4, obj.getValorMulticaixa());
            command.setInt(5, obj.getId());

            result = command.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    public boolean updateValor(EncomendaModel obj) {

        boolean result = false;
        try {
            String sql = " UPDATE `encomenda`\n"
                    + " SET `TotalApagar` = ? , Troco = ? ,`TotalDesconto` = ?"
                    + " WHERE Id = ?\n";
            con = conFactory.open();
            command = con.prepareCall(sql);

//            command.setInt(1, obj.getFormaPagamento().getId());
//            command.setDouble(1, obj.getTotalApagar());
//            command.setDouble(2, obj.getTroco());
//            command.setDouble(3, obj.getTotalDesconto());
            command.setInt(4, obj.getId());

            result = command.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    public boolean eliminarFactura(EncomendaModel obj) {

        boolean result = false;
        try {
            String sql = "UPDATE `encomenda`\n"
                    + "SET `IdEstado` = ? , Obs = ?  WHERE Id = ?\n";
            con = conFactory.open();
            command = con.prepareCall(sql);

            command.setInt(1, 3);
//            command.setString(2, obj.getObs());

            command.setInt(3, obj.getId());

            result = command.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    @Override
    public boolean saveOrUpdate(EncomendaModel obj) {

        boolean result = false;
        try {
            String sql = "INSERT INTO `encomenda`\n"
                    + "("
                    + "`Nome`,\n"
                    + "`Contacto`,\n"
                    + "`LocalEntrega`,\n"
                    + "`Total`,\n"
                    + "`IdUsuario`,\n"
                    + "`IdCliente`,\n"
                    + "`Data`,IdEstado)\n"
                    + "VALUES\n"
                    + "("
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,\n"
                    + "?,?);";
            con = conFactory.open();
            command = con.prepareCall(sql);

            command.setString(1, obj.getNome());
            command.setString(2, obj.getContacto());
            command.setString(3, obj.getLocalEntrega());
            command.setDouble(4, obj.getTotal());
            command.setInt(6, obj.getCliente().getId());
            command.setInt(5, obj.getUsuario().getId());

            command.setInt(8, obj.getEstado().getId());
            command.setString(7, obj.getData());

            result = command.executeUpdate() > 0;

        } catch (SQLException ex) {
            Logger.getLogger(EntradaStockController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return result;
    }

    @Override
    public EncomendaModel getById(int id) {

        EncomendaModel modelo = new EncomendaModel();
        String sql = "SELECT * FROM encomenda f  WHERE Id = " + id;
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

//                modelo.setNomeCliente(query.getString("NomeCliente"));
//                modelo.setTroco(query.getDouble("Troco"));
//                modelo.setTotalDesconto(query.getDouble("TotalDesconto"));
//                modelo.setTotalApagar(query.getDouble("TotalApagar"));
//                modelo.setSubTotal(query.getDouble("SubTotal"));
//                modelo.setTotalIVA(query.getDouble("TotalIVA"));
//                modelo.setValorEntregue(query.getDouble("ValorEntregue"));
//                modelo.setValorMulticaixa(query.getDouble("ValorMulticaixa"));
//
//                FormaPagamentoModel formaPagamento = new FormaPagamentoModel();
//                formaPagamento.setId(query.getInt("IdFormaPagamento"));
//
//                modelo.setFormaPagamento(formaPagamento);
//                modelo.setId(query.getInt("Id"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(EncomendaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return modelo;
    }

    @Override
    public List<EncomendaModel> get() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<EncomendaModel> getFacturaDividaCliente(int codCliente) {

        List<EncomendaModel> lista = new ArrayList();
        String sql = "SELECT * FROM encomenda f where f.IdCliente = " + codCliente + " and TipoFactura ='Venda' and IdFormaPagamento = 4 and TotalApagar > 0";
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                EncomendaModel modelo = new EncomendaModel();
                modelo.setId(query.getInt("Id"));
//                modelo.setTotalApagar(query.getDouble("TotalApagar"));

                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EncomendaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public List<EncomendaModel> getCliente() {

        List<EncomendaModel> lista = new ArrayList();
        String sql = "SELECT NomeCliente FROM encomenda f ";
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                EncomendaModel modelo = new EncomendaModel();
//                modelo.setNomeCliente(query.getString("NomeCliente"));
                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EncomendaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public List<EncomendaModel> getFacturaData(String dataInicio, String dataFim) {

        List<EncomendaModel> lista = new ArrayList();
        String sql = "SELECT * FROM encomenda f where Data BETWEEN '" + dataInicio + "' and '" + dataFim + "'";
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                EncomendaModel modelo = new EncomendaModel();
                modelo.setId(query.getInt("Id"));
//                modelo.setTotalApagar(query.getDouble("TotalApagar"));

                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EncomendaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public boolean verificarTaxTableEntryExiste(List<TaxTableItem> list, String type) {
        for (TaxTableItem value : list) {
            if (value.getTaxTableEntry().getTaxType() == type) {
                return true;
            }
        }

        return false;
    }

    public double getTotalDebito(String data, String data1) {

        String sql = "select (sum(ft.SubTotal)-sum(ft.Iva)) as total "
                + " from encomenda f INNER JOIN encomendaitem ft ON ft.IdFactura = f.Id "
                + " where date(Data) between '" + data + "' and '" + data1 + "' and f.TipoFactura = 'Venda'";

        con = conFactory.open();

        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                return query.getDouble("total");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public String getSerie() {
        String codigo = "";
        String sql = "SELECT Designacao FROM serie where Status = 2";
        con = conFactory.open();

        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                codigo = query.getString("Designacao");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return codigo;
        }
        return codigo;
    }

    public int getNsequencial() {

        String sql = "SELECT Ano FROM serie where Status = 2";
        con = conFactory.open();

        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                return query.getInt("Ano");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public String getValorCasaDecimal(String str, String expressao) {
        String output = "";
        if (str.charAt(0) == ',') {
            output += "0";
        }

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '.') {
                output += str.charAt(i);
            }
        }

        System.out.println("---------\n str : " + str);

        System.out.println("output : " + output);
        return output;
    }

    public List<LineItens> getItemFatura(int codigoFatura, String dataFactura) {

        String sql = "SELECT f.*, p.* FROM encomendaitem f"
                + " inner join produto p on p.Id = f.IdProduto "
                + "  where f.IdFactura=" + codigoFatura;

        List<LineItens> lista = new ArrayList<>();

        con = conFactory.open();
        DecimalFormat df = new DecimalFormat("#,###.00");
        try {

            command = con.prepareCall(sql);

            query = command.executeQuery();

            Integer cont = 0;
            while (query.next()) {
                cont++;
                LineItens.LineItem item = new LineItens().getLine();
                item.setLineNumber(cont.toString());
                item.setProductCode(query.getString("p.Id"));

                item.setProductDescription(query.getString("p.Designacao"));
                item.setQuantity(query.getString("f.Qtd"));
                item.setUnitOfMeasure("UND");
                item.setUnitPrice(getValorCasaDecimal(df.format(query.getDouble("f.Preco")), ".").replace(',', '.'));
                item.setTaxPointDate(dataFactura);
                String descricao = query.getString("p.Descricao");
                if (!Objects.equals(descricao, null) && !Objects.equals(descricao, "")) {
                    item.setDescription(descricao);
                }

                item.setDebitAmount("0.00");
                item.setCreditAmount(getValorCasaDecimal(df.format(query.getDouble("f.Preco")), ".").replace(',', '.'));

                TaxaController taxaController = new TaxaController();

                Taxa modelo1 = taxaController.getById(query.getInt("p.IdTaxa"));
                modelo1.setId(query.getInt("p.IdTaxa"));

                if (modelo1.getTaxa() != 0) {

                    if (modelo1.getTaxa() == 14) {
                        item.getTax().setTaxType("IVA");
                        item.getTax().setTaxCode("NOR");

                    }

                    item.getTax().setTaxPercentage("" + modelo1.getTaxa());

                } else {

                    // int codigoMotivo = modelo1.getMotivo().getCodigo();
                    MotivoController motivoController = new MotivoController();
                    Motivo modelo = motivoController.getById(query.getInt("p.IdMotivo"));

                    item.getTax().setTaxType("NS");
                    item.getTax().setTaxCode("ISE");
                    item.setTaxExemptionReason(modelo.getDescricao());
                    item.setTaxExemptionCode(modelo.getCodigo());
//                    item.getTax().setTaxCode(modelo.getCodigoMotivo());
                    item.getTax().setTaxPercentage("0");
                }

                item.setSettlementAmount("0");
                LineItens linha = new LineItens();
                linha.setLine(item);
                lista.add(linha);

            }
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        return lista;
    }

    public List<Invoices> getFacturaGerais(String data, String data1) {

        String sql = "select * from encomenda f where date(Data) between '" + data + "' and '" + data1 + "'";
        // CRIAR UMA LISTA QUE ARMAZENA AS FATURAS
        List<Invoices> invoiceses = new ArrayList<>();
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();

            String dataFactura2 = "";
            while (query.next()) {

                String dataFactura = query.getString("Data").substring(0, 10);
                Integer codigoFatura = query.getInt("Id");

                Invoice element = new Invoice();
                element.setInvoiceNo(codigoFatura.toString() + " " + getSerie() + "/" + getNsequencial());
                element.setInvoiceDate(dataFactura);

//                element.setHash("" + query.getString("f.Hash"));
                String datav = dataFactura.substring(0, 19);
                String[] vet = datav.split(" ");
                element.setSystemEntryDate(vet[0] + "T" + vet[1]);

                element.setCustomerID(query.getString("IdCliente"));

                Date hora = new Date();
                SimpleDateFormat hora_formato = new SimpleDateFormat("dd/MM/yyyy");

                String vet2[] = hora_formato.format(hora).split("/");

                int mes = Integer.parseInt(vet2[1]);
                element.setPeriod(mes < 9 ? vet2[1].substring(1) : vet2[1]);

                if (!query.getString("TipoFactura").equalsIgnoreCase("Venda Credito")) {
                    element.setInvoiceType("FT");
                } else {
                    element.setInvoiceType("FR");
                }

                element.setLineItens(getItemFatura(codigoFatura, dataFactura));
                //  CRIAR UMA LISTA DE FATURA COM TODOS OS SEUS ITENS

                EncomendaModel encomenda = getById(query.getInt("Id"));
                DecimalFormat df = new DecimalFormat("#,###.00");

//                String str = df.format(encomenda.getTotalIVA()).replaceAll(".", "");
//                String str = getValorCasaDecimal(df.format(encomenda.getTotalIVA()), ".");

//                element.getDocumentTotals().setTaxPayable(str.replace(',', '.'));
//
//                str = getValorCasaDecimal(df.format(encomenda.getSubTotal()), ".");
//                element.getDocumentTotals().setNetTotal(str.replace(',', '.'));
//
//                str = getValorCasaDecimal(df.format(encomenda.getTotalApagar()), ".");
//                element.getDocumentTotals().setGrossTotal(str.replace(',', '.'));

                List<Invoice> lista = new ArrayList<>();
                lista.add(element);
                invoiceses.add(new Invoices(lista));
                System.out.println("faturas");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("ClassExpetion:" + ex.getClass() + ",\n MessageException : " + ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return invoiceses;
    }

    public List<CustomerIten> listaClientes(String data1, String data2) {
//        sql = "select count(Codigo) as total from encomenda where date(DataFactura) between '" + data + "' and '" + data1 + "' and not StatusFactura = 3";
        String sql = "select DISTINCT c.Id, c.Nif, c.Nome, c.Morada from encomenda f join cliente c  on c.Id = f.IdCliente where date(Data) between '" + data1 + "' and '" + data2 + "'";

        con = conFactory.open();

        List<CustomerIten> item = new ArrayList<>();

        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {
                CustomerIten modelo = new CustomerIten();
                CustomerIten.Customer ci = new CustomerIten().getCustomer();
                ci.setCustomerID(String.valueOf(query.getInt("c.Id")));
                String custormerTaxID = query.getString("c.Nif");

                if (custormerTaxID != null) {
                    custormerTaxID.trim();
                    if (custormerTaxID.equals("")) {
                        ci.setCustomerTaxID(custormerTaxID);
                    } else {
                        ci.setCustomerTaxID("Consumidor final");
                    }
                } else {
                    ci.setCustomerTaxID("Consumidor final");
                }

                ci.setCompanyName(query.getString("c.Nome"));
                ci.getBillingAddress().setAddressDetail(query.getString("c.Morada"));
                modelo.setCustomer(ci);
//                ci.getBillingAddress()
                item.add(modelo);
            }
            return item;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return item;
    }

    public List<ProdutItem> listaProdutosVendidos(String data1, String data2) {
//        sql = "select count(Codigo) as total from encomenda where date(DataFactura) between '" + data + "' and '" + data1 + "' and not StatusFactura = 3";
        String sql = "select DISTINCT  p.Id, p.Designacao from encomendaitem f "
                + "inner join produto p on p.Id = f.Id"
                + " inner join encomenda fa on fa.Id = f.IdFactura  "
                + " where date(Data) between '" + data1 + "' and '" + data2 + "'";

        con = conFactory.open();

        List<ProdutItem> pitem = new ArrayList<>();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                ProdutItem.Product produto = new ProdutItem().getProduct();
                produto.setProductCode(String.valueOf(query.getInt("p.Id")));
                produto.setProductDescription(query.getString("p.Designacao"));
                ProdutItem modelo = new ProdutItem();
                modelo.setProduct(produto);
                pitem.add(modelo);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pitem;
    }

    public double getTotalCredit(String data, String data1) {

        String sql = "select (sum(ft.SubTotal)-sum(ft.Iva)) as total "
                + " from encomenda f INNER JOIN encomendaitem ft ON ft.IdFactura = f.Id "
                + " where date(Data) between '" + data + "' and '" + data1 + "' and f.TipoFactura = 'Venda Credito'";

        con = conFactory.open();

        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                return query.getDouble("total");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public List<TaxTableItem> listaIVAIsencao(String data1, String data2) {

        String sql = " select DISTINCT t.Id, t.Descricao, t.Taxa "
                + " from encomendaitem f"
                + " inner join produto p on p.Id = f.IdProduto "
                + " inner join taxa t on t.Id = p.IdTaxa "
                + " inner join encomenda fa on fa.Id = f.IdFactura  "
                + " where date(fa.Data) between '" + data1 + "' and '" + data2 + "'";

        con = conFactory.open();

        List<TaxTableItem> pitem = new ArrayList<>();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                TaxTableItem.TaxTableEntry tax = new TaxTableItem().getTaxTableEntry();
                tax.setTaxPercentage(String.valueOf(query.getInt("t.taxa")));

                if (Integer.parseInt(tax.getTaxPercentage()) == 14) {
                    if (!verificarTaxTableEntryExiste(pitem, "IVA")) {
                        tax.setTaxType("IVA");
                        tax.setTaxCode("NOR");
                        tax.setDescription("Taxa Normal");
                        TaxTableItem titem = new TaxTableItem();
                        titem.setTaxTableEntry(tax);
                        pitem.add(titem);
                    }
                } /*else if(Integer.parseInt(tax.getTaxPercentage())== 7)
                {
                   tax.setTaxType("IVA");
                   tax.setTaxCode("NOR");
                   tax.setDescription("Normal");
                   tax.setTaxCountryRegion("AO");
                }*/ else {
                    if (!verificarTaxTableEntryExiste(pitem, "IS")) {
                        tax.setTaxType("IS");
                        tax.setTaxCode("ISE");
                        tax.setDescription("Isenta");
                        tax.setTaxAmount("0.00");
                        tax.setTaxPercentage("0.00");
                        TaxTableItem titem = new TaxTableItem();
                        titem.setTaxTableEntry(tax);

                        pitem.add(titem);
                    }
                }

            }

            System.out.println(" lista de IVAInsenção : " + pitem.size());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pitem;
    }

    public String getNentradasFacturas(String data, String data1) {

        con = conFactory.open();
        String sql = "select count(Id) as total from encomenda where date(Data) between '" + data + "' and '" + data1 + "' and  TipoFactura NOT IN('Perfoma')";

        try {

            command = con.prepareCall(sql);
            query = command.executeQuery();

            if (query.next()) {
                return String.valueOf(query.getInt("total"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "0";
    }

    public List<EncomendaModel> getFacturas() {

        List<EncomendaModel> lista = new ArrayList();
        String sql = "SELECT * FROM encomenda f where  IdEstado NOT IN(3)";
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                EncomendaModel modelo = new EncomendaModel();
                modelo.setId(query.getInt("Id"));
//                modelo.setTotalApagar(query.getDouble("TotalApagar"));

                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EncomendaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public List<EncomendaModel> getFacturas(String dataInicio, String dataFim) {

        List<EncomendaModel> lista = new ArrayList();
        String sql = "SELECT * FROM encomenda f where  IdEstado NOT IN(3)"
                + " and Data between '" + dataInicio + "' and '" + dataFim + "'";
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            while (query.next()) {

                EncomendaModel modelo = new EncomendaModel();
                modelo.setId(query.getInt("Id"));
//                modelo.setTotalApagar(query.getDouble("TotalApagar"));

                lista.add(modelo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EncomendaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;
    }

    public double getTotalDividaCliente(int codCliente) {

        double valorTotal = 0;
        String sql = "SELECT SUM(TotalApagar) AS TotalApagar FROM encomenda f "
                + "where  f.IdCliente = " + codCliente + " and TipoFactura ='Venda'"
                + " and IdFormaPagamento = 4 "
                + " and TotalApagar > 0";
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {

                valorTotal = query.getDouble("TotalApagar");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EncomendaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valorTotal;
    }

    public double getTotalFactura(int codFactura) {

        double valorTotal = 0;
        String sql = "SELECT TotalApagar AS TotalApagar FROM encomenda f "
                + "where  f.Id = " + codFactura;
        con = conFactory.open();
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {

                valorTotal = query.getDouble("TotalApagar");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EncomendaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return valorTotal;
    }

    public int getLastIdByUsuario(UsuarioModel usuario) {

        int id = 0;
        try {
            String sql = "SELECT Id From encomenda WHERE IdUsuario = ? Order By Id DESC LIMIT 1";
            con = conFactory.open();
            command = con.prepareCall(sql);
            command.setInt(1, usuario.getId());
            query = command.executeQuery();

            if (query.next()) {
                id = query.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EncomendaController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ////conFactory.close(con, command, query);
        }
        return id;
    }

}
