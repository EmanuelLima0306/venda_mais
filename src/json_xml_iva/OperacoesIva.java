/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json_xml_iva;

import Controller.EmpresaController;
import Controller.FacturaController;
import Controller.MovimentoController;
import Controller.MovimentoItemController;
import Model.EmpresaModel;
import Util.DataComponent;
import Util.Definicoes;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Ramossoft
 */
public class OperacoesIva {

    /*  public BDConexao conexao;
    public EmpresaController controller;
    public FacturaController facturaController;
    public MovimentosController movimentosController;
    public FacturaItemController facturaItemController;
    public FornecedoresController fornecedoresController;*/
    public DadosFacturaIva dadosFacturaIva;
    public String dataInicio, dataFinal;

    public OperacoesIva() {
        System.setProperty("file.encoding", "UTF-8");
        /*this.conexao = conexao;
        dadosFacturaIva = new DadosFacturaIva();
        facturaItemController = new FacturaItemController(conexao);
        controller = new EmpresaController(conexao);
        movimentosController = new MovimentosController(1, Integer.parseInt(Data.getAnoActual()));
        facturaController = new FacturaController(conexao);
        fornecedoresController = new FornecedoresController(conexao);*/

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
        System.out.println("output : " + output);
        return output;
    }

    public void createXML(String output) {
        try {
            String value = Definicoes.file.getAbsolutePath();
            String pathDestinoXSD = value.substring(0, value.lastIndexOf(System.getProperty("file.separator")));

            Runtime rt = Runtime.getRuntime();
            String str = "\"" + pathDestinoXSD + "\"";

            //rt.exec("xcopy C:\\arquivos_xml\\SAFTAO1.01_01.xsd  " + str + "  /y");
            //System.out.println("Local aonde salvou-se o ficheiro :" + pathDestinoXSD);
            Path path = Paths.get(Definicoes.file.getAbsolutePath());

            Files.write(path, output.getBytes("UTF8"), StandardOpenOption.CREATE);

            //FileWriter writer = new FileWriter(Definicoes.file.getAbsolutePath());
            //writer.write(output);
            File file2 = new File(Definicoes.file.getAbsolutePath()).getAbsoluteFile();
            // writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dadosOperacoes() {

        LocalDate date = LocalDate.now();

        BuscarFactura factura = new BuscarFactura();

        EmpresaController empresaController = new EmpresaController();
        EmpresaModel empresa = empresaController.getById(1);

        String numFactura = factura.totalFactura(getDataInicio(), getDataFinal());

        MovimentoItemController mc = new MovimentoItemController();

        if (!numFactura.equals("0")) {

            String numNota = mc.totalNota(getDataInicio(), getDataFinal());

            Long numDoc = Long.parseLong(numNota) + Long.parseLong(numFactura);
            numFactura = String.valueOf(numDoc);

            dadosFacturaIva = new DadosFacturaIva();
            //Classe Header
            dadosFacturaIva.getAuditFile().getHeader().setCompanyName(empresa.getNome());
            dadosFacturaIva.getAuditFile().getHeader().setBusinessName(empresa.getNome());
            dadosFacturaIva.getAuditFile().getHeader().setTaxRegistrationNumber(empresa.getNif());
            dadosFacturaIva.getAuditFile().getHeader().setFiscalYear("" + DataComponent.getYear(new Date()));
            dadosFacturaIva.getAuditFile().getHeader().setStartDate(getDataInicio());
            dadosFacturaIva.getAuditFile().getHeader().setEndDate(getDataFinal());
            dadosFacturaIva.getAuditFile().getHeader().setDateCreated(date.toString());
            dadosFacturaIva.getAuditFile().getHeader().setEmail(empresa.getEmail());
            dadosFacturaIva.getAuditFile().getHeader().setWebsite(empresa.getWebSIte());

            dadosFacturaIva.getAuditFile().getHeader().setTelephone(empresa.getContacto());
            //Classe Header End
            dadosFacturaIva.getAuditFile().getHeader().getCompanyAddress().setAddressDetail(empresa.getEndereco());
            dadosFacturaIva.getAuditFile().getSourceDocuments().getSalesInvoices().setNumberOfEntries("" + numFactura);
            dadosFacturaIva.getAuditFile().getHeader().getCompanyAddress().setCity(empresa.getLocal());
            dadosFacturaIva.getAuditFile().getHeader().getCompanyAddress().setAddressDetail(empresa.getEndereco());

            int totalFactura = Integer.parseInt(numFactura);
            int totalNotaCredito = Integer.parseInt("0");

            dadosFacturaIva.getAuditFile().getSourceDocuments().getSalesInvoices().setNumberOfEntries("" + (totalFactura + totalNotaCredito));

            List<TaxTableItem> items = factura.listaIsencao(dataInicio, dataFinal);
            dadosFacturaIva.getAuditFile().getMasterFiles().getTaxTable().setTaxTableItems(items);

            DecimalFormat df = new DecimalFormat("#,##0.00#", new DecimalFormatSymbols(new Locale("pt", "BR")));;
            double value = factura.getTotalDebito(getDataInicio(), getDataFinal());
//            double valueND = factura.getTotalCreditoND(getDataInicio(), getDataFinal());

            String totalDebit = getValorCasaDecimal(df.format(value), ".").replace(',', '.');
            //em analise
            value = factura.getTotalCredit(getDataInicio(), getDataFinal());

            String totalCredit = getValorCasaDecimal(df.format(value), ".").replace(',', '.');
            dadosFacturaIva.getAuditFile().getSourceDocuments().getSalesInvoices().setTotalDebit(totalDebit);
            dadosFacturaIva.getAuditFile().getSourceDocuments().getSalesInvoices().setTotalCredit(totalCredit);
            dadosFacturaIva.getAuditFile().getSourceDocuments().getSalesInvoices().setInvoices(factura.getDocumentosCormercias(getDataInicio(), getDataFinal()));

            /* dadosFacturaIva.getAuditFile().getSourceDocuments().getMovementOfGoods().setStockMovements(facturaController.getGuias(getDataInicio(), getDataFinal()));
            dadosFacturaIva.getAuditFile().getSourceDocuments().getMovementOfGoods().setNumberOfMovementLines(facturaController.getTotalLinhasGuias(getDataInicio(), getDataFinal()) + "");
            dadosFacturaIva.getAuditFile().getSourceDocuments().getMovementOfGoods().setTotalQuantityIssued(getValorCasaDecimal(df.format(facturaController.getQuantidadesItensGuias(getDataInicio(), getDataFinal())), ".").replace(',', '.'));
             */
            dadosFacturaIva.getAuditFile().getSourceDocuments().getWorkingDocuments().setWorkDocuments(factura.getProformas(getDataInicio(), getDataFinal()));
            dadosFacturaIva.getAuditFile().getSourceDocuments().getWorkingDocuments().setNumberOfEntries(factura.totalFacturaProforma(getDataInicio(), getDataFinal()) + "");
            dadosFacturaIva.getAuditFile().getSourceDocuments().getWorkingDocuments().setTotalCredit(getValorCasaDecimal(df.format(factura.getTotalCreditPerfoma(getDataInicio(), getDataFinal())), ".").replace(',', '.') + "");

            /*dadosFacturaIva.getAuditFile().getSourceDocuments().getAllPayments().setPayment(movimentosController.getReibosGerais(getDataInicio(), getDataFinal()));
            dadosFacturaIva.getAuditFile().getSourceDocuments().getAllPayments().setNumberOfEntries(movimentosController.getTotalReibosGerais(getDataInicio(), getDataFinal()) + "");
            dadosFacturaIva.getAuditFile().getSourceDocuments().getAllPayments().setTotalCredit(getValorCasaDecimal(df.format(movimentosController.getValorTotalReibosGerais(getDataInicio(), getDataFinal())), ".").replace(',', '.') + "");
             */
            dadosFacturaIva.getAuditFile().getMasterFiles().setCustomerIten(factura.listaClientes(getDataInicio(), getDataFinal()));
            //dadosFacturaIva.getAuditFile().getMasterFiles().setSupplierIten(fornecedoresController.getDadosFornecedor());
            dadosFacturaIva.getAuditFile().getMasterFiles().setProdutItem(factura.getTodosProdutosVendidos(getDataInicio(), getDataFinal()));
            XStream xstream = new XStream(new DomDriver());
            DadosFacturaIva.AuditFile file = dadosFacturaIva.getAuditFile();
            String xml = xstream.toXML(file);
            String output = estruturarAquivoXML(xml);

//            if (verificaCamposObrigatorios(output)) {
                createXML(output);
                System.out.println(output);
                JOptionPane.showMessageDialog(null, "Ficheiro salvo com sucesso. ", "Tudo OK!", 1);
//            } else {
//                JOptionPane.showMessageDialog(null, "Ficheiro não pode ser gerado, existem campos obrigatórios que não foram preenchidos. ", "Erro!", 1);
//            }

        } else {
            JOptionPane.showMessageDialog(null, "Não Existe Documentos neste periodo", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    public boolean verificaCamposObrigatorios(String output) {
        boolean falseTrue = true;
        if (output.contains("<CompanyName></CompanyName>")) {
            falseTrue = false;
        }
        if (output.contains("<AddressDetail></AddressDetail>")) {
            falseTrue = false;
        }
        if (output.contains("<City></City>")) {
            falseTrue = false;
        }
        if (output.contains("<TaxRegistrationNumber></TaxRegistrationNumber>")) {
            falseTrue = false;
        }
        return falseTrue;
    }

    public String estruturarAquivoXML(String xml) {

        String mxlns = " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                + "  \n "
                + "xsi:schemaLocation=\"urn:OECD:StandardAuditFile-Tax:AO_1.01_01 \\SAFTAO1.01_01.xsd\" xmlns=\"urn:OECD:StandardAuditFile-Tax:AO_1.01_01\"";

        String limpeza = xml.replaceFirst("<json__xml__iva.DadosFacturaIva_-AuditFile>", "<AuditFile>");
        limpeza = limpeza.replaceFirst("</json__xml__iva.DadosFacturaIva_-AuditFile>", "</AuditFile>");
        limpeza = limpeza.replaceFirst("<AuditFile>", "<AuditFile " + mxlns + ">");

         limpeza = limpeza.replaceFirst("<Header>", " <Header>");
//        String xml3 = xml2.replaceFirst("<MasterFiles>", " <Header>");
         limpeza = limpeza;
        limpeza = limpeza.replaceAll("</json__xml__iva.Invoice>", "");
        limpeza = limpeza.replaceAll("<Invoices>", "");
        limpeza = limpeza.replaceAll("</Invoices>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.Invoices>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.Invoices>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.Line>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.Line>", "");

        limpeza = limpeza.replaceAll("<outer-class reference=\"../..\"/>", "");
        limpeza = limpeza.replaceAll("<outer-class>", "");
        limpeza = limpeza.replaceAll("</outer-class>", "");
        limpeza = limpeza.replaceAll("<AuditFile reference=\"../..\"/>", "");
        limpeza = limpeza.replaceAll("<LineItens>", "");
        limpeza = limpeza.replaceAll("</LineItens>", "");
        limpeza = limpeza.replaceAll("<LineItens/>", "");

        limpeza = limpeza.replaceAll("<json__xml__iva.LineItens>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.LineItens>", "");
        limpeza = limpeza.replaceAll(" <Line reference=\"../..\"/>", "");
        limpeza = limpeza.replaceAll("<DebitAmount>0.00</DebitAmount>", "");
        limpeza = limpeza.replaceAll("<CreditAmount>0.00</CreditAmount>", "");
        limpeza = limpeza.replaceAll(" <json__xml__iva.Invoice>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.ProdutItem>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.ProdutItem>", "");
        limpeza = limpeza.replaceAll("<produtItem>", "");
        limpeza = limpeza.replaceAll("</produtItem>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.DadosFacturaIva_-AuditFile_-MasterFiles_-ProdutItem>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.DadosFacturaIva_-AuditFile_-MasterFiles_-ProdutItem>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.DadosFacturaIva_-AuditFile_-MasterFiles_-ProdutItem_-Product>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.DadosFacturaIva_-AuditFile_-MasterFiles_-ProdutItem_-Product>", "");
        limpeza = limpeza.replaceAll("<outer-class reference=\"../../..\"/>", "");
        limpeza = limpeza.replaceAll("<outer-class reference=\"../../../..\"/>", "");
        limpeza = limpeza.replaceAll("<customerIten>", "");
        limpeza = limpeza.replaceAll("</customerIten>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.DadosFacturaIva_-AuditFile_-MasterFiles_-CustomerIten>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.DadosFacturaIva_-AuditFile_-MasterFiles_-CustomerIten>", "");

        limpeza = limpeza.replaceAll("<json__xml__iva.CustomerIten>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.CustomerIten>", "");

        limpeza = limpeza.replaceAll("<Customer reference=\"../..\"/>", "");
        limpeza = limpeza.replaceAll("<Customer reference=\"../..\"/>", "");

        limpeza = limpeza.replaceAll("<Product reference=\"../..\"/>", "");
        limpeza = limpeza.replaceAll("<taxTableItems>", "");
        limpeza = limpeza.replaceAll("</taxTableItems>", "");
        limpeza = limpeza.replaceAll("<taxTableItems/>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.TaxTableItem>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.TaxTableItem>", "");

        limpeza = limpeza.replaceAll("<TaxTableEntry reference=\"../..\"/>", "");
        limpeza = limpeza.replaceAll("<TaxAmount>0</TaxAmount>", "");

        limpeza = limpeza.replaceAll("</supplierIten>", "");
        limpeza = limpeza.replaceAll("<supplierIten/>", "");
        limpeza = limpeza.replaceAll("<supplierIten>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.SupplierIten>", "");
        limpeza = limpeza.replaceAll("<Supplier reference=\"../..\"/>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.SupplierIten>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.Payment>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.Payment>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.LineItensPayment>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.LineItensPayment>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.LineItensWithholding_-WithholdingTax>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.LineItensWithholding_-WithholdingTax>", "");
        limpeza = limpeza.replaceAll("<WithholdingTax/>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.Payments>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.Payments>", "");
        limpeza = limpeza.replaceAll("<References/>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.LineItensReferences_-References>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.LineItensReferences_-References>", "");
        limpeza = limpeza.replaceAll("<PaymentItem/>", "");
        limpeza = limpeza.replaceAll("<Invoices/>", "");
        limpeza = limpeza.replaceAll("<PaymentItem>", "");
        limpeza = limpeza.replaceAll("</PaymentItem>", "");
        limpeza = limpeza.replaceAll("<TaxExemptionReason></TaxExemptionReason>", "");
        limpeza = limpeza.replaceAll("<TaxExemptionCode></TaxExemptionCode>", "");
        limpeza = limpeza.replaceAll("<StockMovements>", "");
        limpeza = limpeza.replaceAll("</StockMovements>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.StockMovements>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.StockMovements>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.StockMovement>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.StockMovement>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.LineItensStockMovement>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.LineItensStockMovement>", "");
        limpeza = limpeza.replaceAll("<StockMovements/>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.WorkDocuments>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.WorkDocuments>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.WorkDocument>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.WorkDocument>", "");
        limpeza = limpeza.replaceAll("<json__xml__iva.LineItensWorkDocument>", "");
        limpeza = limpeza.replaceAll("</json__xml__iva.LineItensWorkDocument>", "");
        limpeza = limpeza.replaceAll("<WorkDocuments>", "");
        limpeza = limpeza.replaceAll("</WorkDocuments>", "");
        limpeza = limpeza.replaceAll("<WorkDocuments/>", "");

//        if(removeJsonReferencesfinish.contains("<Payments/>")){
//            removeJsonReferences.replaceAll("<Payments/>", "");
//            removeJsonReferences.replaceAll("<Payments>", "");
//            removeJsonReferences.replaceAll("<Payments>", "");
//        }
        //JOptionPane.showMessageDialog(null, removeTaxAmountInit);
//        limpeza = limpeza.replaceAll("<TotalDebit>0.00</TotalDebit>","");
//        String removeComparebleCredit = removeComparebleDebit.replaceAll("<TotalCredit>0.00</TotalCredit>","");
        String novaXML = limpeza;

        String vet[] = novaXML.split("\n");

        limpeza = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"+limpeza;
//        for (String valor : vet) {
//            if (!verificarCaracteresVazioString(valor)) {
//                output += valor + "\n";
//            }
//        }
        return limpeza;
    }

    public boolean verificarCaracteresVazioString(String valor) {
        int cont = 0;
        for (int i = 0; i < valor.length(); i++) {
            if (valor.charAt(i) == ' ') {
                cont++;
            }
        }

        if (cont == valor.length()) {
            return true;
        }

        return false;
    }

    public JSONObject getReadJSonFile() {
        JSONObject jsonObject = null;
        try {
            //Cria o parse de tratamento
            JSONParser parser = new JSONParser();
            //Salva no objeto JSONObject o que o parse tratou do arquivo
            jsonObject = (JSONObject) parser.parse(new FileReader("file_2019-04-26_Saft.json"));
        } //Trata as exceptions que podem ser lançadas no decorrer do processo
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return jsonObject;
    }

    public DadosFacturaIva getDadosFacturaIva() {
        return dadosFacturaIva;
    }

    public void setDadosFacturaIva(DadosFacturaIva dadosFacturaIva) {
        this.dadosFacturaIva = dadosFacturaIva;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public String getDataFinal() {
        return dataFinal;
    }

//    public static void main(String[] args) {
//      
//        }
}
