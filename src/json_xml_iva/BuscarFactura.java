/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json_xml_iva;

import Controller.ConnectionFactoryController;
import Controller.MotivoController;
import Controller.MovimentoController;
import Controller.MovimentoItemController;
import Model.ClienteModel;
import Model.EstadoModel;
import Model.FacturaModel;
import Model.FormaPagamentoModel;
import Model.Motivo;
import Model.Movimento;
import Model.MovimentoItemModel;
import Model.Taxa;
import Model.UsuarioModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

/**
 *
 * @author celso
 */
public class BuscarFactura {

    private ConnectionFactoryController conFactory = new ConnectionFactoryController();
    private Connection con;
    private PreparedStatement command;
    private ResultSet query;

    public BuscarFactura() {

        con = conFactory.open();
    }

    public List<TaxTableItem> listaIsencao(String data1, String data2) {

        List<TaxTableItem> pitem = new ArrayList<>();
        System.out.println("data ::: " + data1 + " :::: " + data2);
        List<ProdutItem> todosProdutosVendidos = getTodosProdutosVendidos(data1, data2);

        for (ProdutItem produtItem : todosProdutosVendidos) {

            int productTaxa = getTaxaProdutoByCodigo(Integer.parseInt(produtItem.getProduct().getProductCode()));

            TaxTableItem.TaxTableEntry tax = new TaxTableItem().getTaxTableEntry();
            tax.setTaxPercentage(String.valueOf(productTaxa));

            if (Integer.parseInt(tax.getTaxPercentage()) == 14) {

                if (!verificarTaxTableEntryExiste(pitem, "NOR")) {

                    tax.setTaxType("IVA");
                    tax.setTaxCode("NOR");
                    tax.setDescription("Taxa Normal");
                    TaxTableItem titem = new TaxTableItem();
                    titem.setTaxTableEntry(tax);
                    pitem.add(titem);
                }

            } else if (Integer.parseInt(tax.getTaxPercentage()) == 5) {

                if (!verificarTaxTableEntryExiste(pitem, "RED")) {

                    tax.setTaxType("IVA");
                    tax.setTaxCode("RED");
                    tax.setDescription("Taxa Reduzida");
                    TaxTableItem titem = new TaxTableItem();
                    titem.setTaxTableEntry(tax);
                    pitem.add(titem);
                }

            } else {

                if (!verificarTaxTableEntryExiste(pitem, "NS")) {

                    tax.setTaxType("NS");
                    tax.setTaxCode("NS");
                    tax.setDescription("Isenta");
                    //tax.setTaxAmount("0.00");
                    tax.setTaxPercentage("0.00");
                    TaxTableItem titem = new TaxTableItem();
                    titem.setTaxTableEntry(tax);

                    pitem.add(titem);
                }
            }

        }

        return pitem;
    }

    public int getTaxaProdutoByCodigo(int codigo) {

        con = conFactory.open();
        String sql = "select t.Taxa from produto p join taxa t on t.Id = p.IdTaxa  where p.Id = " + codigo;

        try {
            command = con.prepareCall(sql);
            query = command.executeQuery();
            if (query.next()) {
                return query.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //conFactory.close(con, command, query);
        }
        return 0;
    }

    public String totalFactura(String data, String data1) {

//        Connection con = conFactory.open();
        String sql = "select count(Id) as total "
                + "from factura "
                + "where (TipoFactura ='Venda' or TipoFactura='Venda Credito') and date(Data) between '" + data + "' and '" + data1 + "'";

        String valor = "0";
        try {

            command = con.prepareCall(sql);
            query = command.executeQuery();

            if (query.next()) {
                valor = String.valueOf(query.getInt("total"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        finally {
//            conFactory.close(con, command, query);
//        }
        return valor;
    }

    public String totalFacturaProforma(String data, String data1) {

        //con = conFactory.open();
        String sql = "select count(Id) as total "
                + "from factura "
                + "where TipoFactura = 'Perfoma' and date(Data) between '" + data + "' and '" + data1 + "'";

        String valor = "0";
        try {

            command = con.prepareCall(sql);
            query = command.executeQuery();

            if (query.next()) {
                valor = String.valueOf(query.getInt("total"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        finally {
//            conFactory.close(con, command, query);
//        }
        return valor;
    }

    public Double totalCreditoProforma(String data, String data1) {

        //  con = conFactory.open();
        String sql = "SELECT sum(Total) total FROM factura f inner join  facturaitem fi  on fi.IdFactura = f.Id "
                + " where TipoFactura = 'Perfoma' and date(Data) between '" + data + "' and '" + data1 + "'";
//        String sql = "select sum(SubTotal),sum(qtd) as total "
//                + " from factura "
//                + " where TipoFactura = 'Perfoma' and date(Data) between '" + data + "' and '" + data1 + "'";

        String valor = "0";
        try {

            command = con.prepareCall(sql);
            query = command.executeQuery();

            if (query.next()) {
                valor = String.valueOf(query.getInt("total"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        finally {
//            conFactory.close(con, command, query);
//        }
        return Double.parseDouble(valor);
    }

    public List<ProdutItem> listaProdutosVendidos(String data1, String data2) {
//        sql = "select count(Codigo) as total from factura where date(DataFactura) between '" + data + "' and '" + data1 + "' and not StatusFactura = 3";
        String sql = "select DISTINCT  p.Id, p.Designacao from facturaitem f "
                + "inner join produto p on p.Id = f.IdProduto"
                + " inner join factura fa on fa.Id = f.IdFactura  "
                + " where date(fa.Data) between '" + data1 + "' and '" + data2 + "'";

        System.out.println("tete 1 ::: " + sql);
        //   con = conFactory.open();
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
//        finally {
//            conFactory.close(con, command, query);
//        }

        return pitem;
    }

    public List<CustomerIten> listaClientes(String data1, String data2) {
//        sql = "select count(Codigo) as total from factura where date(DataFactura) between '" + data + "' and '" + data1 + "' and not StatusFactura = 3";
        String sql = "select DISTINCT c.Id, c.Nif, c.Nome, c.Morada from "
                + "factura f join cliente c  on c.Id = f.IdCliente "
                + "where c.Nif NOT IN ('999999999') AND date(f.Data) between '" + data1 + "' and '" + data2 + "'";

        //  con = conFactory.open();
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
                    custormerTaxID = custormerTaxID.trim();
                    if (!custormerTaxID.equals("")
                            && !custormerTaxID.equals("999999999")) {
                        ci.setCustomerTaxID(custormerTaxID);
                    } else {
                        ci.setCustomerTaxID("999999999");
                    }
                } else {
                    ci.setCustomerTaxID("999999999");

                }

                ci.setCompanyName(query.getString("c.Nome"));

                if (ci.getCompanyName().trim().equalsIgnoreCase("DIVERSO")) {

                    ci.setCompanyName("Consumidor final");
                }

                ci.getBillingAddress().setAddressDetail(query.getString("c.Morada"));
                modelo.setCustomer(ci);
//                ci.getBillingAddress()
                item.add(modelo);
            }
            
            item.add(getClienteDIVERSO( con));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        finally {
//            conFactory.close(con, command, query);
//        }
        return item;
    }
    public CustomerIten getClienteDIVERSO(Connection con) {
//        sql = "select count(Codigo) as total from factura where date(DataFactura) between '" + data + "' and '" + data1 + "' and not StatusFactura = 3";
        String sql = "select DISTINCT c.Id, c.Nif, c.Nome, c.Morada from  cliente c  ";

        //  con = conFactory.open();
        CustomerIten modelo = null;

        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                 modelo = new CustomerIten();
                CustomerIten.Customer ci = new CustomerIten().getCustomer();
                ci.setCustomerID(String.valueOf(query.getInt("c.Id")));
                String custormerTaxID = query.getString("c.Nif");

                if (custormerTaxID != null) {
                    custormerTaxID = custormerTaxID.trim();
                    if (!custormerTaxID.equals("")
                            && !custormerTaxID.equals("999999999")) {
                        ci.setCustomerTaxID(custormerTaxID);
                    } else {
                        ci.setCustomerTaxID("999999999");
                    }
                } else {
                    ci.setCustomerTaxID("999999999");

                }

                ci.setCompanyName(query.getString("c.Nome"));

                if (ci.getCompanyName().trim().equalsIgnoreCase("DIVERSO")) {

                    ci.setCompanyName("Consumidor final");
                }

                ci.getBillingAddress().setAddressDetail(query.getString("c.Morada"));
                modelo.setCustomer(ci);

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        finally {
//            conFactory.close(con, command, query);
//        }
        return modelo;
    }

    public List<ProdutItem> getTodosProdutosVendidos(String data, String data1) {

        List<ProdutItem> productItem = new ArrayList<>();
        productItem = listaProdutosVendidos(data, data1);

        return productItem;
    }

    public boolean verificarTaxTableEntryExiste(List<TaxTableItem> list, String code) {
        for (TaxTableItem value : list) {
            if (value.getTaxTableEntry().getTaxCode()== code) {
                return true;
            }
        }

        return false;
    }

    /*    public List<ProdutItem> listaProdutosVendidos(String data1, String data2) {

        String sql = "select Distinct  p.Id, p.Designacao "
                + "from facturaitem fi, produto p,factura f "
                + "where (p.Id = fi.IdProduto) and (date(f.Data) between '" + data1 + "' and '" + data2 + "')";

        List<ProdutItem> pitem = new ArrayList<>();
        try {
            con = conFactory.open();
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
    }*/
    public double getTotalCredit(String data, String data1) {

        String sql = "SELECT sum(Total) total,sum(Iva) imposto FROM facturaitem fi \n"
                + " WHERE fi.IdFactura IN (SELECT f.Id FROM factura f WHERE f.IdEstado IN(1,3) \n"
                + "                      AND date(Data) between '" + data + "' and '" + data1 + "'\n"
                + "                      and (f.TipoFactura ='Venda' or f.TipoFactura='Venda Credito'));";
        double valor = 0;
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                valor = query.getDouble("total") - query.getDouble("imposto");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        finally {
//            conFactory.close(con, command, query);
//        }
        return valor;
    }

    public double getTotalCreditPerfoma(String data, String data1) {

        String sql = "SELECT sum(Total) total,sum(Iva) imposto FROM facturaitem fi \n"
                + " WHERE fi.IdFactura IN (SELECT f.Id FROM factura f WHERE f.IdEstado IN(1,3) \n"
                + "                      AND date(Data) between '" + data + "' and '" + data1 + "'\n"
                + "                      and f.TipoFactura ='Perfoma');";
        double valor = 0;
        try {
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                valor = query.getDouble("total") - query.getDouble("imposto");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        finally {
//            conFactory.close(con, command, query);
//        }
        return valor;
    }

    public double getTotalDebito(String data, String data1) {

        String sql = "SELECT sum(mi.Total) total,sum(mi.Iva) imposto FROM `movimento` m \n"
                + "inner join factura f  on f.Id = m.IdFactura\n"
                + "inner join movimentoitem mi on mi.IdMovimento = m.Id where TipoMovimento = 'C' AND Date(m.Data) Between '" + data + "' and  '" + data1 + "'";

        double total = 0;

        try {
            con = conFactory.open();
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                total = query.getDouble("total") - query.getDouble("imposto");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        finally {
//            conFactory.close(con, command, query);
//        }
        return total;
    }

    public double getTotalCreditoND(String data, String data1) {

        String sql = "SELECT sum(mi.Total) total,sum(mi.Iva) imposto FROM `movimento` m \n"
                + "inner join factura f  on f.Id = m.IdFactura\n"
                + "inner join movimentoitem mi on mi.IdMovimento = m.Id where TipoMovimento = 'D' AND Date(m.Data) Between '" + data + "' and  '" + data1 + "'";

        double total = 0;

        try {
            con = conFactory.open();
            command = con.prepareCall(sql);

            query = command.executeQuery();
            if (query.next()) {
                total = query.getDouble("total") - query.getDouble("imposto");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        finally {
//            conFactory.close(con, command, query);
//        }
        return total;
    }

    public List<Invoices> getDocumentosCormercias(String data, String data1) {
        List<Invoices> invoiceses = new ArrayList<>();
        invoiceses = getFacturaGerais(data, data1);
        for (Invoices invoicese : getNotasDeCreditoGerais(data, data1)) {
            invoiceses.add(invoicese);
        }
//        for (Invoices invoicese : getNotasDebitoGerais(data, data1)) {
//            invoiceses.add(invoicese);
//        }
        return invoiceses;
    }

    public List<LineItens> getItemNC(int codigoFatura, String dataFactura, String motivo, int codigoMovimento, Connection con) {

        MovimentoItemController controller = new MovimentoItemController();

        List<MovimentoItemModel> itemsNC = controller.getItemByIdMovimento(codigoMovimento, con);

        List<LineItens> lista = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00#", new DecimalFormatSymbols(new Locale("pt", "BR")));
        Integer cont = 0;

        for (MovimentoItemModel notaCreditoItem : itemsNC) {
            cont++;

            LineItens.LineItem item = new LineItens().getLine();
            item.setLineNumber(cont.toString());
            item.setProductCode(notaCreditoItem.getFacturaItemModel().getProduto().getId() + "");
            item.setProductDescription(notaCreditoItem.getFacturaItemModel().getProduto().getDesignacao());
            item.setQuantity(df.format(notaCreditoItem.getFacturaItemModel().getQtd()).replace(',', '.'));
            item.setUnitOfMeasure("UND");
//            item.setUnitOfMeasure(notaCreditoItem.getUnidade());
            item.setUnitPrice(getValorCasaDecimal(df.format((notaCreditoItem.getFacturaItemModel().getTotal() - notaCreditoItem.getFacturaItemModel().getIva()) / notaCreditoItem.getFacturaItemModel().getQtd()), ".").replace(',', '.'));
            item.setTaxPointDate(dataFactura);

            List<LineItensReferences.References> listaReferences = new ArrayList<>();
            LineItensReferences.References references = new LineItensReferences().getLine();
//            references.setReference(notaCreditoItem.getMovimento().getNext());
            references.setReference(OrganizarRefFactura.saft(notaCreditoItem.getMovimento().getFactura().getNextFactura()));

            references.setReason(motivo);
            if (motivo.length() >= 49) {
                references.setReason(motivo.substring(0, 49));
            }

            listaReferences.add(references);
            item.setDebitAmount(getValorCasaDecimal(df.format(controller.getTotalMovimento(notaCreditoItem.getMovimento().getId(), con)), ".").replace(',', '.'));

            item.setReferences(listaReferences);
            Taxa modelo1 = notaCreditoItem.getFacturaItemModel().getProduto().getTaxa();

            if (modelo1.getTaxa() != 0) {

                if (modelo1.getTaxa() == 14) {
                    item.getTax().setTaxType("IVA");
                    item.getTax().setTaxCode("NOR");
                }
                if (modelo1.getTaxa() == 5) {
                    item.getTax().setTaxType("IVA");
                    item.getTax().setTaxCode("RED");
                }

                item.getTax().setTaxPercentage("" + modelo1.getTaxa());

            } else {
                Motivo modelo = notaCreditoItem.getFacturaItemModel().getProduto().getMotivo();

                item.getTax().setTaxType("NS");
                item.getTax().setTaxCode("NS");
                item.setTaxExemptionReason(modelo.getDescricao());
                item.setTaxExemptionCode(modelo.getCodigo());
                item.getTax().setTaxPercentage("0");
            }

            item.setSettlementAmount(df.format(controller.getTotalDescontoMovimento(notaCreditoItem.getMovimento().getId(), con)).replaceAll(",", "."));
            LineItens linha = new LineItens();
            linha.setLine(item);
            lista.add(linha);
        }
        return lista;
    }

    public List<LineItens> getItemNC(int codigoFatura, String dataFactura, String motivo, int codigoMovimento) {

        MovimentoItemController controller = new MovimentoItemController();
        List<MovimentoItemModel> itemsNC = controller.getItemByIdMovimento(codigoMovimento);

        List<LineItens> lista = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00#", new DecimalFormatSymbols(new Locale("pt", "BR")));
        Integer cont = 0;
        for (MovimentoItemModel notaCreditoItem : itemsNC) {

            cont++;

            LineItens.LineItem item = new LineItens().getLine();
            item.setLineNumber(cont.toString());
            item.setProductCode(notaCreditoItem.getFacturaItemModel().getProduto().getId() + "");
            item.setProductDescription(notaCreditoItem.getFacturaItemModel().getProduto().getDesignacao());
            item.setQuantity(df.format(notaCreditoItem.getFacturaItemModel().getQtd()).replace(',', '.'));
            item.setUnitOfMeasure("UND");

            double novoPreco = (notaCreditoItem.getFacturaItemModel().getTotal() - notaCreditoItem.getFacturaItemModel().getIva()) / notaCreditoItem.getFacturaItemModel().getQtd();

            item.setUnitPrice(getValorCasaDecimal(df.format(novoPreco), ".").replace(',', '.'));
            item.setTaxPointDate(dataFactura);

            List<LineItensReferences.References> listaReferences = new ArrayList<>();
            LineItensReferences.References references = new LineItensReferences().getLine();
//            references.setReference(notaCreditoItem.getMovimento().getNext());
            references.setReference(OrganizarRefFactura.saft(notaCreditoItem.getMovimento().getFactura().getNextFactura()));
            references.setReason(motivo);
            if (motivo.length() >= 49) {
                references.setReason(motivo.substring(0, 49));
            }
            listaReferences.add(references);

            double novoSubTotal = novoPreco * notaCreditoItem.getFacturaItemModel().getQtd();

            item.setDebitAmount(getValorCasaDecimal(df.format(novoSubTotal), ".").replace(',', '.'));

            item.setReferences(listaReferences);
            Taxa modelo1 = notaCreditoItem.getFacturaItemModel().getProduto().getTaxa();

            if (modelo1.getTaxa() != 0) {

                if (modelo1.getTaxa() == 14) {
                    item.getTax().setTaxType("IVA");
                    item.getTax().setTaxCode("NOR");
                }
                if (modelo1.getTaxa() == 5) {
                    item.getTax().setTaxType("IVA");
                    item.getTax().setTaxCode("RED");
                }

                item.getTax().setTaxPercentage("" + modelo1.getTaxa());

            } else {

                Motivo modelo = notaCreditoItem.getFacturaItemModel().getProduto().getMotivo();

                item.getTax().setTaxType("NS");
                item.getTax().setTaxCode("NS");
                item.setTaxExemptionReason(modelo.getDescricao());
                item.setTaxExemptionCode(modelo.getCodigo());
                item.getTax().setTaxPercentage("0");
            }

            item.setSettlementAmount(df.format(notaCreditoItem.getFacturaItemModel().getDesconto()).replaceAll(",", "."));
            LineItens linha = new LineItens();
            linha.setLine(item);
            lista.add(linha);
        }
        return lista;
    }

    public List<LineItens> getItemND(int codigoFatura, String dataFactura, String motivo, int codigoMovimento) {

        MovimentoItemController controller = new MovimentoItemController();
        List<MovimentoItemModel> itemsNC = controller.getItemByIdMovimento(codigoMovimento);

        List<LineItens> lista = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("0.00#", new DecimalFormatSymbols(new Locale("pt", "BR")));
        Integer cont = 0;
        for (MovimentoItemModel notaCreditoItem : itemsNC) {

            cont++;

            LineItens.LineItem item = new LineItens().getLine();
            item.setLineNumber(cont.toString());
            item.setProductCode(notaCreditoItem.getFacturaItemModel().getProduto().getId() + "");
            item.setProductDescription(notaCreditoItem.getFacturaItemModel().getProduto().getDesignacao());
            item.setQuantity(df.format(notaCreditoItem.getFacturaItemModel().getQtd()).replace(',', '.'));
            item.setUnitOfMeasure("UND");

            double novoPreco = (notaCreditoItem.getFacturaItemModel().getTotal() - notaCreditoItem.getFacturaItemModel().getIva()) / notaCreditoItem.getFacturaItemModel().getQtd();

            item.setUnitPrice(getValorCasaDecimal(df.format(novoPreco), ".").replace(',', '.'));
            item.setTaxPointDate(dataFactura);

            List<LineItensReferences.References> listaReferences = new ArrayList<>();
            LineItensReferences.References references = new LineItensReferences().getLine();
//            references.setReference(notaCreditoItem.getMovimento().getNext());
            references.setReference(OrganizarRefFactura.saft(notaCreditoItem.getMovimento().getFactura().getNextFactura()));
            references.setReason(motivo);
            if (motivo.length() >= 49) {
                references.setReason(motivo.substring(0, 49));
            }
            listaReferences.add(references);

            double novoSubTotal = novoPreco * notaCreditoItem.getFacturaItemModel().getQtd();

            item.setCreditAmount(getValorCasaDecimal(df.format(novoSubTotal), ".").replace(',', '.'));

            item.setReferences(listaReferences);
            Taxa modelo1 = notaCreditoItem.getFacturaItemModel().getProduto().getTaxa();

            if (modelo1.getTaxa() != 0) {

                if (modelo1.getTaxa() == 14) {
                    item.getTax().setTaxType("IVA");
                    item.getTax().setTaxCode("NOR");
                }
                if (modelo1.getTaxa() == 5) {
                    item.getTax().setTaxType("IVA");
                    item.getTax().setTaxCode("RED");
                }

                item.getTax().setTaxPercentage("" + modelo1.getTaxa());

            } else {

                Motivo modelo = notaCreditoItem.getFacturaItemModel().getProduto().getMotivo();

                item.getTax().setTaxType("NS");
                item.getTax().setTaxCode("NS");
                item.setTaxExemptionReason(modelo.getDescricao());
                item.setTaxExemptionCode(modelo.getCodigo());
                item.getTax().setTaxPercentage("0");
            }

            item.setSettlementAmount(df.format(notaCreditoItem.getFacturaItemModel().getDesconto()).replaceAll(",", "."));
            LineItens linha = new LineItens();
            linha.setLine(item);
            lista.add(linha);
        }
        return lista;
    }

    public Double totalIvaMovimento(int id, Connection con) throws Exception {

        String sql = "SELECT sum(Iva) iva FROM movimentoitem where IdMovimento =" + id;

        PreparedStatement command = con.prepareCall(sql);

        ResultSet query = command.executeQuery();

        if (query.next()) {

            return query.getDouble(1);
        }
        return 0.0;

    }

    public Double totalDescontoMovimento(int id, Connection con) throws Exception {

        String sql = "SELECT sum(Desconto) iva FROM movimentoitem where IdMovimento =" + id;

        PreparedStatement command = con.prepareCall(sql);

        ResultSet query = command.executeQuery();

        if (query.next()) {

            return query.getDouble(1);
        }
        return 0.0;

    }

    public Double totalSubTotalMovimento(int id, Connection con) throws Exception {

        String sql = "SELECT sum(subTotal) iva FROM movimentoitem where IdMovimento =" + id;

        PreparedStatement command = con.prepareCall(sql);

        ResultSet query = command.executeQuery();

        if (query.next()) {

            return query.getDouble(1);
        }
        return 0.0;

    }

    public Double totalApagarMovimento(int id, Connection con) throws Exception {

        String sql = "SELECT sum(Total) iva FROM movimentoitem where IdMovimento =" + id;

        PreparedStatement command = con.prepareCall(sql);

        ResultSet query = command.executeQuery();

        if (query.next()) {

            return query.getDouble(1);
        }
        return 0.0;

    }

    public Double totalQtdMovimento(int id, Connection con) throws Exception {

        String sql = "SELECT sum(Qtd) iva FROM movimentoitem where IdMovimento =" + id;

        PreparedStatement command = con.prepareCall(sql);

        ResultSet query = command.executeQuery();

        if (query.next()) {

            return query.getDouble(1);
        }
        return 0.0;

    }

    public List<Invoices> getNotasDeCreditoGerais(String data, String data1) {

        String sql = "select * from movimento m "
                + " inner join factura f on f.Id = m.IdFactura"
                + " inner join cliente c on c.Id = f.IdCliente"
                + " where  TipoMovimento = 'C' "
                + "and (f.NextFactura Like 'FR%' or f.NextFactura Like 'FT%') "
                + " and date(m.Data) between '" + data + "' and '" + data1 + "' ";
        // CRIAR UMA LISTA QUE ARMAZENA AS FATURAS
        List<Invoices> invoiceses = new ArrayList<>();

        try {
            con = conFactory.open();
            command = con.prepareCall(sql);

            query = command.executeQuery();
            String dataFactura2 = "";
            while (query.next()) {
                Integer codigoFatura = Integer.parseInt(query.getString("IdFactura"));
                int codigoNC = query.getInt("m.Id");
                FacturaModel factura = new FacturaModel();
                //  FacturaModel valoresTotalNC = new FacturaModel();
                factura = findFacturaByFactura(codigoFatura, con);

                MovimentoItemController miController = new MovimentoItemController();

                factura.setTotalApagar(miController.getTotalMovimento(codigoNC, con));
                factura.setTotalIVA(miController.getTotalIvaMovimento(codigoNC, con));
                factura.setSubTotal(miController.getSubTotalMovimento(codigoNC, con));
//                factura.setValorAPagar(valoresTotalNC.getValorAPagar());
                factura.setTotalDesconto(miController.getSubTotalDescontoMovimento(codigoNC, con));
                factura.setCliente(new ClienteModel(query.getInt("m.IdCliente"), ""));
                // factura.setrV(valoresTotalNC.getrV());

                MovimentoController mController = new MovimentoController();

                Movimento modelo = mController.getById(codigoNC, con);
//                FacturaItemController fiController = new FacturaItemController(new BDConexao());
                String dataFactura = modelo.getData().substring(0, 10);
                String dataNotaCredito = modelo.getData().substring(0, 10);

                Invoice element = new Invoice();
                element.setInvoiceDate(dataNotaCredito);
                element.setHash("" + modelo.getHash());

                String datav = modelo.getData().substring(0, 19);
                String[] vet = datav.split(" ");
                element.setSystemEntryDate(vet[0] + "T" + vet[1]);

                if (query.getString("c.Nif").equals("999999999")) {

                    element.setCustomerID("1");

                } else {
                    element.setCustomerID(factura.getCliente().getId() + "");
                }

                Date hora = new Date();
                SimpleDateFormat hora_formato = new SimpleDateFormat("dd/MM/yyyy");

                String vet2[] = hora_formato.format(hora).split("/");

                int mes = Integer.parseInt(vet2[1]);
                element.setPeriod(mes < 9 ? vet2[1].substring(1) : vet2[1]);

                element.setInvoiceType("NC");
                element.setInvoiceNo(OrganizarRefFactura.saft(modelo.getNext()));
                //  CRIAR UMA LISTA DE FATURA COM TODOS OS SEUS ITENS
                DecimalFormat df = new DecimalFormat("#,##0.00#", new DecimalFormatSymbols(new Locale("pt", "BR")));

                /* if (facturaOriginalController.getDocumentoRectificativos(codigoFatura).equalsIgnoreCase(rs.getString("NextMovimento"))) {
                    element.getDocumentStatus().setInvoiceStatus("N");
                } else {
                    element.getDocumentStatus().setInvoiceStatus("A");
                }
                 */
                element.getDocumentStatus().setInvoiceStatusDate(vet[0] + "T" + vet[1]);
                element.getDocumentStatus().setSourceID(modelo.getUsuario().getId() + "");
                element.setSourceID(modelo.getUsuario().getId() + "");

                element.setLineItens(getItemNC(codigoFatura, dataFactura, modelo.getObs(), codigoNC));

                Double taxPayable = totalIvaMovimento(codigoNC, con);

                String str = getValorCasaDecimal(df.format(taxPayable), ".");
                element.getDocumentTotals().setTaxPayable(str.replace(',', '.'));

                double total = totalApagarMovimento(codigoNC, con);
                double imposto = taxPayable;
                double qtd = totalQtdMovimento(codigoNC, con);
//                double desconto = totalDescontoMovimento(codigoNC, con);

//              Double[] valores = getValores(codigoFatura, dataFactura);
                double novoPreco = 0;
                double novoSubTotal = 0;

                novoPreco = (total - imposto) / qtd;
                novoSubTotal = novoPreco * qtd;

                str = getValorCasaDecimal(df.format(novoSubTotal), ".");
                element.getDocumentTotals().setNetTotal(str.replace(',', '.'));

                Double netTotal = novoSubTotal;
                Double grossTotal = netTotal + taxPayable;

                str = getValorCasaDecimal(df.format(grossTotal), ".");
                element.getDocumentTotals().setGrossTotal(str.replace(',', '.'));

                str = getValorCasaDecimal(df.format(total), ".");
                element.getDocumentTotals().getPayment().setPaymentAmount(str.replace(',', '.'));

                element.getDocumentTotals().getPayment().setPaymentDate(vet[0]);

                if (modelo.getFactura().getFormaPagamento().getId() == 1 || modelo.getFactura().getFormaPagamento().getId() == 5) {
                    element.getDocumentTotals().getPayment().setPaymentMechanism("NU");
                } else if (modelo.getFactura().getFormaPagamento().getId() == 4) {
                    element.getDocumentTotals().getPayment().setPaymentMechanism("TB");
                } else if (modelo.getFactura().getFormaPagamento().getId() == 2) {
                    element.getDocumentTotals().getPayment().setPaymentMechanism("CD");
                } else if (modelo.getFactura().getFormaPagamento().getId() == 3) {
                    element.getDocumentTotals().getPayment().setPaymentMechanism("CC");
                } else {
                    element.getDocumentTotals().getPayment().setPaymentMechanism("OU");
                }

                List<LineItensWithholding.WithholdingTax> listaWithholdingTax = new ArrayList<>();
                /* if (factura.getrV() > 0) {
                    LineItensWithholding.WithholdingTax withholdingTax = new LineItensWithholding().getLine();
                    if (StringVector.haveString(factura.getNif() + "")) {
                        withholdingTax.setWithholdingTaxType("II");
                    } else {
                        withholdingTax.setWithholdingTaxType("IRT");
                    }
                    str = getValorCasaDecimal(df.format(factura.getrV()), ".");
                    withholdingTax.setWithholdingTaxAmount(str.replace(',', '.'));
                    listaWithholdingTax.add(withholdingTax);
                }*/
                element.setWithholdingTax(listaWithholdingTax);

                List<Invoice> lista = new ArrayList<>();
                lista.add(element);
                invoiceses.add(new Invoices(lista));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return invoiceses;
    }

    public List<Invoices> getNotasDebitoGerais(String data, String data1) {

        String sql = "select * from movimento m  "
                + "inner join factura f on f.Id = m.IdFactura"
                + " inner join cliente c on c.Id = f.IdCliente"
                + " where  TipoMovimento = 'D' "
                + "and (f.NextFactura Like 'FR%' or f.NextFactura Like 'FT%') "
                + " and date(m.Data) between '" + data + "' and '" + data1 + "' ";
        // CRIAR UMA LISTA QUE ARMAZENA AS FATURAS
        List<Invoices> invoiceses = new ArrayList<>();

        try {
            con = conFactory.open();
            command = con.prepareCall(sql);

            query = command.executeQuery();
            String dataFactura2 = "";
            while (query.next()) {
                Integer codigoFatura = Integer.parseInt(query.getString("IdFactura"));
                int codigoNC = query.getInt("m.Id");
                FacturaModel factura = new FacturaModel();
                //  FacturaModel valoresTotalNC = new FacturaModel();
                factura = findFacturaByFactura(codigoFatura, con);

                MovimentoItemController miController = new MovimentoItemController();

                factura.setTotalApagar(miController.getTotalMovimento(codigoNC, con));
                factura.setTotalIVA(miController.getTotalIvaMovimento(codigoNC, con));
                factura.setSubTotal(miController.getSubTotalMovimento(codigoNC, con));
//                factura.setValorAPagar(valoresTotalNC.getValorAPagar());
                factura.setTotalDesconto(miController.getSubTotalDescontoMovimento(codigoNC, con));
                factura.setCliente(new ClienteModel(query.getInt("m.IdCliente"), ""));
                // factura.setrV(valoresTotalNC.getrV());

                MovimentoController mController = new MovimentoController();

                Movimento modelo = mController.getById(codigoNC, con);
//                FacturaItemController fiController = new FacturaItemController(new BDConexao());
                String dataFactura = modelo.getData().substring(0, 10);
                String dataNotaCredito = modelo.getData().substring(0, 10);

                Invoice element = new Invoice();
                element.setInvoiceDate(dataNotaCredito);
                element.setHash("" + modelo.getHash());

                String datav = modelo.getData().substring(0, 19);
                String[] vet = datav.split(" ");
                element.setSystemEntryDate(vet[0] + "T" + vet[1]);

             
                  if (query.getString("c.Nif").equals("999999999")) {

                    element.setCustomerID("1");

                } else {
                    element.setCustomerID(factura.getCliente().getId() + "");
                }
                

                Date hora = new Date();
                SimpleDateFormat hora_formato = new SimpleDateFormat("dd/MM/yyyy");

                String vet2[] = hora_formato.format(hora).split("/");

                int mes = Integer.parseInt(vet2[1]);
                element.setPeriod(mes < 9 ? vet2[1].substring(1) : vet2[1]);

                element.setInvoiceType("ND");
                element.setInvoiceNo(OrganizarRefFactura.saft(modelo.getNext()));
                //  CRIAR UMA LISTA DE FATURA COM TODOS OS SEUS ITENS
                DecimalFormat df = new DecimalFormat("#,##0.00#", new DecimalFormatSymbols(new Locale("pt", "BR")));

                /* if (facturaOriginalController.getDocumentoRectificativos(codigoFatura).equalsIgnoreCase(rs.getString("NextMovimento"))) {
                    element.getDocumentStatus().setInvoiceStatus("N");
                } else {
                    element.getDocumentStatus().setInvoiceStatus("A");
                }
                 */
                element.getDocumentStatus().setInvoiceStatusDate(vet[0] + "T" + vet[1]);
                element.getDocumentStatus().setSourceID(modelo.getUsuario().getId() + "");
                element.setSourceID(modelo.getUsuario().getId() + "");

                element.setLineItens(getItemND(codigoFatura, dataFactura, modelo.getObs(), codigoNC));

                Double taxPayable = totalIvaMovimento(codigoNC, con);

                String str = getValorCasaDecimal(df.format(taxPayable), ".");
                element.getDocumentTotals().setTaxPayable(str.replace(',', '.'));

                double total = totalApagarMovimento(codigoNC, con);
                double imposto = taxPayable;
                double qtd = totalQtdMovimento(codigoNC, con);
//                double desconto = totalDescontoMovimento(codigoNC, con);

//              Double[] valores = getValores(codigoFatura, dataFactura);
                double novoPreco = 0;
                double novoSubTotal = 0;

                novoPreco = (total - imposto) / qtd;
                novoSubTotal = novoPreco * qtd;

                str = getValorCasaDecimal(df.format(novoSubTotal), ".");
                element.getDocumentTotals().setNetTotal(str.replace(',', '.'));

                Double netTotal = novoSubTotal;
                Double grossTotal = netTotal + taxPayable;

                str = getValorCasaDecimal(df.format(grossTotal), ".");
                element.getDocumentTotals().setGrossTotal(str.replace(',', '.'));

                str = getValorCasaDecimal(df.format(total), ".");
                element.getDocumentTotals().getPayment().setPaymentAmount(str.replace(',', '.'));

                element.getDocumentTotals().getPayment().setPaymentDate(vet[0]);

                if (modelo.getFactura().getFormaPagamento().getId() == 1 || modelo.getFactura().getFormaPagamento().getId() == 5) {
                    element.getDocumentTotals().getPayment().setPaymentMechanism("NU");
                } else if (modelo.getFactura().getFormaPagamento().getId() == 4) {
                    element.getDocumentTotals().getPayment().setPaymentMechanism("TB");
                } else if (modelo.getFactura().getFormaPagamento().getId() == 2) {
                    element.getDocumentTotals().getPayment().setPaymentMechanism("CD");
                } else if (modelo.getFactura().getFormaPagamento().getId() == 3) {
                    element.getDocumentTotals().getPayment().setPaymentMechanism("CC");
                } else {
                    element.getDocumentTotals().getPayment().setPaymentMechanism("OU");
                }

                List<LineItensWithholding.WithholdingTax> listaWithholdingTax = new ArrayList<>();
                /* if (factura.getrV() > 0) {
                    LineItensWithholding.WithholdingTax withholdingTax = new LineItensWithholding().getLine();
                    if (StringVector.haveString(factura.getNif() + "")) {
                        withholdingTax.setWithholdingTaxType("II");
                    } else {
                        withholdingTax.setWithholdingTaxType("IRT");
                    }
                    str = getValorCasaDecimal(df.format(factura.getrV()), ".");
                    withholdingTax.setWithholdingTaxAmount(str.replace(',', '.'));
                    listaWithholdingTax.add(withholdingTax);
                }*/
                element.setWithholdingTax(listaWithholdingTax);

                List<Invoice> lista = new ArrayList<>();
                lista.add(element);
                invoiceses.add(new Invoices(lista));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return invoiceses;
    }

    public List<WorkDocuments> getProformas(String data, String data1) {

        String sql = "select * from factura f "
                + " INNER JOIN cliente c ON c.Id = f.IdCliente "
                + " where date(f.Data) between '" + data + "' and '" + data1 + "' and TipoFactura = 'Perfoma'";
        // CRIAR UMA LISTA QUE ARMAZENA AS FATURAS
        List<WorkDocuments> workDocumentses = new ArrayList<>();
        String hashAnterior = "";
        String hash = "";
        try {

            //con = conFactory.open();
            command = con.prepareCall(sql);

            query = command.executeQuery();
            String dataFactura2 = "";
            while (query.next()) {

                String dataFactura = query.getString("f.Data").substring(0, 10);
                Integer codigoFatura = query.getInt("f.Id");

                FacturaModel factura = new FacturaModel();
                factura = findFacturaByFactura(query.getInt("f.Id"), con);

                hash = query.getString("Hash");
                WorkDocument element = new WorkDocument();
                element.setWorkDate(dataFactura);

//                if (hashAnterior.trim().isEmpty()) {
//                    element.setHash("" + hash);
//                }
                String datav = query.getString("f.Data").substring(0, 19);
                String[] vet = datav.split(" ");
                element.setSystemEntryDate(vet[0] + "T" + vet[1]);

                if (query.getString("c.Nif").equals("999999999")) {

                    element.setCustomerID("1");

                } else {
                    element.setCustomerID(query.getString("IdCliente"));
                }

                Date hora = new Date();
                SimpleDateFormat hora_formato = new SimpleDateFormat("dd/MM/yyyy");

                String vet2[] = hora_formato.format(hora).split("/");

                int mes = Integer.parseInt(vet2[1]);
                element.setPeriod(mes < 9 ? vet2[1].substring(1) : vet2[1]);

                Double totalCredito = getTotalCreditoByFactura(codigoFatura);
                element.setLineItens(getItemFaturaProforma(codigoFatura, dataFactura, totalCredito));

                element.setWorkType("PP");
                element.setDocumentNumber("PP " + OrganizarRefFactura.saft(query.getString("nextFactura")).trim().split(" ")[1]);

                //  CRIAR UMA LISTA DE FATURA COM TODOS OS SEUS ITENS
                DecimalFormat df = new DecimalFormat("#,##0.00#", new DecimalFormatSymbols(new Locale("pt", "BR")));

                String str = getValorCasaDecimal(df.format(factura.getTotalIVA()), ".");
                element.getDocumentTotals().setTaxPayable(str.replace(',', '.'));
                element.getDocumentStatus().setWorkStatus("N");
                element.getDocumentStatus().setWorkStatusDate(vet[0] + "T" + vet[1]);
                element.getDocumentStatus().setSourceID(factura.getUsuario().getId() + "");
                element.setSourceID(factura.getUsuario().getId() + "");

                String workDate = vet[0];

                Double[] valores = getValores(codigoFatura, dataFactura);

                double novoPreco = 0;
                double novoSubTotal = 0;
                if (valores.length > 0) {

                    novoPreco = (valores[0] - valores[1]) / valores[2];
                    novoSubTotal = novoPreco * valores[2];

                }

                str = getValorCasaDecimal(df.format(novoSubTotal), ".");
                element.getDocumentTotals().setNetTotal(str.replace(',', '.'));

                str = getValorCasaDecimal(df.format(factura.getTotalApagar()), ".");
//                str = getValorCasaDecimal(df.format(factura.getTotalApagar() + factura.getrV()), ".");
                //str = getValorCasaDecimal(df.format(factura.getValorAPagar() + factura.getDesconto() + fiController.getTotalDescontoItem(codigoFatura) + factura.getrV()), ".");
                element.getDocumentTotals().setGrossTotal(str.replace(',', '.'));

//                hash = RSA.getHash(element.getWorkDate()
//                        + ";" + element.getDocumentStatus().getWorkStatusDate() + ";"
//                        + element.getDocumentNumber() + ";"
//                        + element.getDocumentTotals().getGrossTotal() + ";" + hashAnterior);
                element.setHash("" + hash);

                List<WorkDocument> lista = new ArrayList<>();
                lista.add(element);
//                hashAnterior = hash;
                workDocumentses.add(new WorkDocuments(lista));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return workDocumentses;
    }

    public List<LineItensWorkDocument> getItemFaturaProforma(int codigoFatura, String dataFactura, Double totalCredito) {

        String sql = "SELECT f.*, p.*,t.*,mo.* FROM facturaitem f"
                + " join produto p on p.Id = f.IdProduto "
                + " join taxa t on t.Id = p.IdTaxa "
                + " join motivo mo on mo.Id = p.IdMotivo "
                + "  where f.IdFactura=" + codigoFatura;
        List<LineItensWorkDocument> lista = new ArrayList<>();
        totalCredito = 0.0;
        DecimalFormat df = new DecimalFormat("0.00#", new DecimalFormatSymbols(new Locale("pt", "BR")));
        try {
            // Connection con = conFactory.open();
            PreparedStatement cmd = con.prepareCall(sql);
            ResultSet rs = cmd.executeQuery();
            Integer cont = 0;
            while (rs.next()) {
                cont++;

                LineItensWorkDocument.LineItem item = new LineItensWorkDocument().getLine();
                item.setLineNumber(cont.toString());
                item.setProductCode(rs.getString("p.Id"));

                item.setProductDescription(rs.getString("p.Designacao"));
                item.setQuantity(df.format(rs.getDouble("f.Qtd")).replace(',', '.'));
                item.setUnitOfMeasure("UND");

                double total = rs.getDouble("f.Total");
                double imposto = rs.getDouble("f.Iva");
                double qtd = rs.getDouble("f.Qtd");
                double desconto = rs.getDouble("f.Desconto");

                double novoPreco = (total - imposto) / qtd;
                double novoSubTotal = novoPreco * qtd;

                item.setUnitPrice(getValorCasaDecimal(df.format(novoPreco), ".").replace(',', '.'));
                item.setTaxPointDate(dataFactura);

                //List<LineItensReferences.References> listaReferences = new ArrayList<>();
                item.setCreditAmount(getValorCasaDecimal(df.format(novoSubTotal), ".").replace(',', '.'));
                //item.setReferences(listaReferences);
                totalCredito += Double.parseDouble(getValorCasaDecimal(df.format(novoSubTotal), ".").replace(',', '.'));

                Taxa modelo1 = new Taxa(rs.getInt("t.Id"), rs.getString("t.Descricao"), rs.getDouble("t.Taxa"));

                if (modelo1.getTaxa() != 0) {

                    if (modelo1.getTaxa() == 14) {
                        item.getTax().setTaxType("IVA");
                        item.getTax().setTaxCode("NOR");
                    } else if (modelo1.getTaxa() == 5) {
                        item.getTax().setTaxType("IVA");
                        item.getTax().setTaxCode("RED");
                    }

                    item.getTax().setTaxPercentage("" + modelo1.getTaxa());

                } else {

                    Motivo modelo = new Motivo(rs.getInt("mo.Id"), rs.getString("mo.Descricao"), rs.getString("mo.Codigo"));

                    item.getTax().setTaxType("NS");
                    item.getTax().setTaxCode("NS");
                    item.setTaxExemptionReason(modelo.getDescricao());
                    item.setTaxExemptionCode(modelo.getCodigo());
                    item.getTax().setTaxPercentage("0");
                }

                item.setSettlementAmount(rs.getString("f.Desconto"));
                LineItensWorkDocument linha = new LineItensWorkDocument();
                linha.setLine(item);
                lista.add(linha);
            }
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        return lista;
    }

    public double getTotalCreditoByFactura(int codigoFatura) {

        String sql = "SELECT sum(f.Total) total,sum(f.Iva) imposto,sum(f.Qtd) qtd FROM facturaitem f"
                + "  where f.IdFactura=" + codigoFatura;

        double totalCredito = 0.0;
        DecimalFormat df = new DecimalFormat("0.00#", new DecimalFormatSymbols(new Locale("pt", "BR")));
        try {
            // Connection con = conFactory.open();
            PreparedStatement cmd = con.prepareCall(sql);
            ResultSet rs = cmd.executeQuery();
            Integer cont = 0;
            if (rs.next()) {

                double total = rs.getDouble("total");
                double imposto = rs.getDouble("imposto");
                double qtd = rs.getDouble("qtd");

                double novoPreco = (total - imposto) / qtd;
                double novoSubTotal = novoPreco * qtd;

                totalCredito = Double.parseDouble(getValorCasaDecimal(df.format(novoSubTotal), ".").replace(',', '.'));

            }
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        return totalCredito;
    }

    public Double[] getValores(int codigoFatura, String dataFactura) {

        String sql = "SELECT sum(f.Total) total,sum(f.Iva) imposto,"
                + "          sum(f.Qtd) qtd,sum(f.Desconto) desconto FROM facturaitem f"
                + "  where f.IdFactura=" + codigoFatura;

        Double[] valores = new Double[4];

        DecimalFormat df = new DecimalFormat("0.00#", new DecimalFormatSymbols(new Locale("pt", "BR")));
        try {
            // Connection con = conFactory.open();
            PreparedStatement cmd = con.prepareCall(sql);
            ResultSet rs = cmd.executeQuery();
            Integer cont = 0;
            if (rs.next()) {

                double total = rs.getDouble("total");
                double imposto = rs.getDouble("imposto");
                double qtd = rs.getDouble("qtd");
                double desconto = rs.getDouble("desconto");

                double novoPreco = (total - imposto) / qtd;
                double novoSubTotal = novoPreco * qtd;

                valores[0] = total;
                valores[1] = imposto;
                valores[2] = qtd;
                valores[3] = desconto;

            }
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        return valores;
    }

    public int getNsequencial(Connection con) {

        String sql = "SELECT ano FROM serie where status = 2";

        try {
//               Connection con = ConexaoMysql.open();
            PreparedStatement cmd = con.prepareCall(sql);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                return rs.getInt("ano");
            }
//            ConexaoMysql.close(con, cmd, rs);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public String getSerie(Connection con) {
        String codigo = "";
        String sql = "SELECT designacao FROM serie where status = 2";

        try {

            PreparedStatement cmd = con.prepareCall(sql);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                codigo = rs.getString("designacao");
            }
//            ConexaoMysql.close(con, cmd, rs);
        } catch (Exception ex) {
            ex.printStackTrace();
            return codigo;
        }
        return codigo;
    }

    public String getSerie(int id) {
        String codigo = "";
        String sql = "SELECT Designacao FROM serie where codigo = " + id;

        try {

            //  con = conFactory.open();
            PreparedStatement cmd = con.prepareCall(sql);
            ResultSet rs = cmd.executeQuery();

            if (rs.next()) {
                codigo = rs.getString("Designacao");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return codigo;
        }
        return codigo;
    }

    public int getNsequencial(int codigo) {

        String sql = "SELECT Ano FROM serie where codigo =" + codigo;

        try {
            // con = conFactory.open();
            PreparedStatement cmd = con.prepareCall(sql);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                return rs.getInt("Ano");
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

        return output;
    }

    public Taxa findTaxa(int codigo, Connection con) {

        String sql = "SELECT * FROM taxa where Id = " + codigo;

        try {
            PreparedStatement cmd = con.prepareCall(sql);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                Taxa tipo = new Taxa();
                tipo.setId(rs.getInt("Id"));
                tipo.setTaxa(rs.getDouble("Taxa"));
                //  tipo.setMotivo(new MotivoModelo(rs1.getInt(3)));
                tipo.setStatus(new EstadoModel(rs.getInt("IdEstado"), ""));
                tipo.setDescricao(rs.getString("Descricao"));
                return tipo;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public List<LineItens> getItemFatura(int codigoFatura, String dataFactura, Connection con) {
        // String serieNSequencial = getSerie(2) + "/" + getNsequencial(2);

        String sql = "SELECT f.*, p.*"
                + " FROM facturaitem f join produto p on p.id = f.IdProduto where f.IdFactura=" + codigoFatura;
        List<LineItens> lista = new ArrayList<>();

        DecimalFormat df = new DecimalFormat("0.00#", new DecimalFormatSymbols(new Locale("pt", "BR")));
        try {

            PreparedStatement cmd = con.prepareCall(sql);
            ResultSet rs = cmd.executeQuery();
            Integer cont = 0;
            while (rs.next()) {
                cont++;

                LineItens.LineItem item = new LineItens().getLine();
                item.setLineNumber(cont.toString());
                item.setProductCode(rs.getString("p.Id"));

                item.setProductDescription(rs.getString("p.Designacao"));
                item.setQuantity(df.format(rs.getDouble("f.Qtd")).replace(',', '.'));
                item.setUnitOfMeasure("UND");
                //  item.setUnitOfMeasure(rs1.getString("unidadeMedida"));

                double total = rs.getDouble("f.Total");
                double imposto = rs.getDouble("f.Iva");
                double qtd = rs.getDouble("f.Qtd");
                double desconto = rs.getDouble("f.Desconto");

                double novoPreco = (total - imposto) / qtd;
                double novoSubTotal = novoPreco * qtd;

                item.setUnitPrice(getValorCasaDecimal(df.format(novoPreco), ".").replace(',', '.'));
//                item.setUnitPrice(getValorCasaDecimal(df.format(rs.getDouble("f.Total") / rs.getDouble("f.Qtd")), ".").replace(',', '.'));
                item.setTaxPointDate(dataFactura);
//                String descricao = rs.getString("p.DescricaoProduto");

                List<LineItensReferences.References> listaReferences = new ArrayList<>();

                item.setCreditAmount(getValorCasaDecimal(df.format(novoSubTotal), ".").replace(',', '.'));
//                item.setCreditAmount(getValorCasaDecimal(df.format(rs.getDouble("f.Total")), ".").replace(',', '.'));
                item.setReferences(listaReferences);

                Taxa modelo1 = findTaxa(rs.getInt("p.IdTaxa"), con);

                if (modelo1.getTaxa() != 0) {

                    if (modelo1.getTaxa() == 14) {
                        item.getTax().setTaxType("IVA");
                        item.getTax().setTaxCode("NOR");
                    } else if (modelo1.getTaxa() == 5) {

                        item.getTax().setTaxType("IVA");
                        item.getTax().setTaxCode("RED");
                    }

                    item.getTax().setTaxPercentage("" + modelo1.getTaxa());

                } else {
                    int codigoMotivo = rs.getInt("p.IdMotivo");
                    MotivoController motivoController = new MotivoController();
                    Motivo modelo = motivoController.getById(codigoMotivo, con);

                    item.getTax().setTaxType("NS");
                    item.getTax().setTaxCode("NS");
                    item.setTaxExemptionReason(modelo.getDescricao());
                    item.setTaxExemptionCode(modelo.getCodigo());
                    item.getTax().setTaxPercentage("0");
                }

                item.setSettlementAmount(rs.getString("f.Desconto"));
                LineItens linha = new LineItens();
                linha.setLine(item);
                lista.add(linha);
            }
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        return lista;
    }

    public FacturaModel findFacturaByFactura(int codigo, Connection con) {
        String sql = "SELECT * from factura where Id = " + codigo;

        try {

            PreparedStatement cmd = con.prepareCall(sql);
            ResultSet rs = cmd.executeQuery();
            if (rs.next()) {
                FacturaModel factura = new FacturaModel();

                factura.setId(rs.getInt("Id"));
                factura.setData(rs.getString("Data"));
                factura.setTotalApagar(rs.getDouble("TotalApagar"));
                factura.setTotalIVA(rs.getDouble("totalIVA"));
                factura.setSubTotal(rs.getDouble("SubTotal"));
                factura.setTotalDesconto(rs.getDouble("TotalDesconto"));
                //factura.setrV(rs.getDouble("retencaoV"));
                factura.setEstado(new EstadoModel(rs.getInt("IdEstado"), ""));
                // factura.setNif(rs.getString("nifCliente"));
                factura.setUsuario(new UsuarioModel(rs.getInt("IdUsuario"), ""));
                factura.setCliente(new ClienteModel(rs.getInt("IdCliente"), ""));
                factura.setNextFactura(rs.getString("NextFactura"));
                factura.setFormaPagamento(new FormaPagamentoModel(rs.getInt("IdFormaPagamento")));
                return factura;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public List<Invoices> getFacturaGerais(String data, String data1) {

        String sql = "select * from factura f"
                + " INNER JOIN cliente c ON c.Id = f.IdCliente"
                + " where TipoFactura NOT IN('Perfoma') AND date(f.Data) between '" + data + "' and '" + data1 + "'";
        // CRIAR UMA LISTA QUE ARMAZENA AS FATURAS
        List<Invoices> invoiceses = new ArrayList<>();

        System.out.println("sql ::: "+sql);
        try {

//            con = conFactory.open();
            PreparedStatement cmd = con.prepareCall(sql);

            // String serieNSequencial = getSerie(2) + "/" + getNsequencial(2);
            ResultSet rs = cmd.executeQuery();
            String dataFactura2 = "";
            while (rs.next()) {

                String dataAux = rs.getString("f.data");
                String dataFactura = dataAux.substring(0, 10);

                Integer codigoFatura = rs.getInt("f.id");

                Invoice element = new Invoice();
                element.setInvoiceDate(dataFactura);
                element.setHash("" + rs.getString("hash"));

                String datav = rs.getString("f.data").substring(0, 19);
                String[] vet = datav.split(" ");
                element.setSystemEntryDate(vet[0] + "T" + vet[1]);

                if (rs.getString("c.Nif").equals("999999999")) {

                    element.setCustomerID("1");

                } else {
                    element.setCustomerID(rs.getString("IdCliente"));
                }

                Date hora = new Date();
                SimpleDateFormat hora_formato = new SimpleDateFormat("dd/MM/yyyy");

                String vet2[] = hora_formato.format(hora).split("/");

                int mes = Integer.parseInt(vet2[1]);
                element.setPeriod(mes < 9 ? vet2[1].substring(1) : vet2[1]);

                FacturaModel factura = new FacturaModel();

//                if (rs.getString("retificada").equals("SIM")) {
//                    
//                    element.setLineItens(getItemFaturaOriginal(codigoFatura, dataFactura));
//                    factura = findFacturaOriginalByFactura(rs.getInt("codigo"));
//                    
//                } else {
                element.setLineItens(getItemFatura(codigoFatura, dataFactura, con));
                factura = findFacturaByFactura(rs.getInt("f.id"), con);
                // }
                String tipoFactura = rs.getString("TipoFactura");
                if (tipoFactura.equals("Venda")) {
                    element.setInvoiceType("FR");
                } else {
                    element.setInvoiceType("FT");
                }
                element.setInvoiceNo(OrganizarRefFactura.saft(rs.getString("nextFactura").trim()));
//                element.setInvoiceNo(element.getInvoiceType() + " AGT " + rs.getString("nextFactura").split(" ")[0]);
//                element.setInvoiceNo(element.getInvoiceType() + " AGT" + rs.getString("nextFactura").split(" ")[0]);
                //  CRIAR UMA LISTA DE FATURA COM TODOS OS SEUS ITENS
                DecimalFormat df = new DecimalFormat("#,##0.00#", new DecimalFormatSymbols(new Locale("pt", "BR")));

                String str = getValorCasaDecimal(df.format(factura.getTotalIVA()), ".");
                element.getDocumentTotals().setTaxPayable(str.replace(',', '.'));
                if (rs.getInt("f.IdEstado") == 2) {
                    element.getDocumentStatus().setInvoiceStatus("A");
                } else {
                    element.getDocumentStatus().setInvoiceStatus("N");
                }
                element.getDocumentStatus().setInvoiceStatusDate(vet[0] + "T" + vet[1]);
                element.getDocumentStatus().setSourceID(factura.getUsuario().getId() + "");
                element.setSourceID(factura.getUsuario().getId() + "");

                Double[] valores = getValores(codigoFatura, dataFactura);

                double novoPreco = 0;
                double novoSubTotal = 0;
                if (valores.length > 0) {

                    novoPreco = (valores[0] - valores[1]) / valores[2];
                    novoSubTotal = novoPreco * valores[2];

                }

                //str = getValorCasaDecimal(df.format(novoSubTotal), ".");
                str = getValorCasaDecimal(df.format(factura.getSubTotal()-factura.getTotalDesconto()), ".");
                element.getDocumentTotals().setNetTotal(str.replace(',', '.'));

               //  str = getValorCasaDecimal(df.format(factura.getTotalApagar()), ".");
                str = getValorCasaDecimal(df.format(factura.getSubTotal()+factura.getTotalIVA()-factura.getTotalDesconto()), ".");
                //str = getValorCasaDecimal(df.format(factura.getValorAPagar() + factura.getDesconto() + fiController.getTotalDescontoItem(codigoFatura) + factura.getrV()), ".");
                element.getDocumentTotals().setGrossTotal(str.replace(',', '.'));

                str = getValorCasaDecimal(df.format(factura.getTotalApagar()), ".");
                element.getDocumentTotals().getPayment().setPaymentAmount(str.replace(',', '.'));
                element.getDocumentTotals().getPayment().setPaymentDate(vet[0]);
                if (factura.getFormaPagamento().getId() == 1) {
                    element.getDocumentTotals().getPayment().setPaymentMechanism("NU");
                } else if (factura.getFormaPagamento().getId() == 4) {
                    element.getDocumentTotals().getPayment().setPaymentMechanism("TB");
                } else if (factura.getFormaPagamento().getId() == 2) {
                    element.getDocumentTotals().getPayment().setPaymentMechanism("CD");
                } else if (factura.getFormaPagamento().getId() == 3) {
                    element.getDocumentTotals().getPayment().setPaymentMechanism("CC");
                } else {
                    element.getDocumentTotals().getPayment().setPaymentMechanism("OU");
                }

                List<LineItensWithholding.WithholdingTax> listaWithholdingTax = new ArrayList<>();
                /* if (factura.getrV() > 0) {
                    LineItensWithholding.WithholdingTax withholdingTax = new LineItensWithholding().getLine();
                    if (StringVector.haveString(factura.getNif() + "")) {
                        withholdingTax.setWithholdingTaxType("II");
                    } else {
                        withholdingTax.setWithholdingTaxType("IRT");
                    }
                    str = getValorCasaDecimal(df.format(factura.getrV()), ".");
                    withholdingTax.setWithholdingTaxAmount(str.replace(',', '.'));
                    listaWithholdingTax.add(withholdingTax);
                }*/
                //element.setWithholdingTax(listaWithholdingTax);

                List<Invoice> lista = new ArrayList<>();
                lista.add(element);
                invoiceses.add(new Invoices(lista));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return invoiceses;
    }

}
