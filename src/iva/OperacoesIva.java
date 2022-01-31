/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iva;

import Util.DataComponent;
import Controller.EmpresaController;
import Controller.FacturaController;
import Controller.FacturaItemController;
import Controller.MovimentoController;
import Model.EmpresaModel;
import Util.Calculo;
import Util.Definicoes;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Ramossoft
 */
public class OperacoesIva {

    public DadosFacturaIva dadosFacturaIva = new DadosFacturaIva();
    public String dataInicio, dataFinal;

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
            FileWriter writer = new FileWriter(Definicoes.file.getAbsolutePath());

            writer.write(output);
            File file2 = new File(Definicoes.file.getAbsolutePath()).getAbsoluteFile();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dadosOperacoes() {
        LocalDate date = LocalDate.now();

        FacturaController factura = new FacturaController();

        EmpresaController empresaController = new EmpresaController();
        EmpresaModel empresa = empresaController.getById(1);

        String numFactura = factura.getNentradasFacturas(getDataInicio(), getDataFinal());
        if (!numFactura.equals("0")) {

            Date data = new Date();
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
            dadosFacturaIva.getAuditFile().getMasterFiles().getTaxTable().setTaxTableItems(factura.listaIVAIsencao(getDataInicio(), getDataFinal()));

//        double value = factura.getTotalDebito(getDataInicio(), getDataFinal());
//            MovimentoController movimentoController = new MovimentoController();
//             value = movimentoController.totalCredito(getDataInicio(), getDataFinal());
//            String totalDebit = getValorCasaDecimal(Calculo.converterCash(value), ".").replace(',', '.');
            String totalDebit = getValorCasaDecimal(Calculo.converterCash(new Double(0)), ".");
         double   value = factura.getTotalCredit(getDataInicio(), getDataFinal());

//            value += factura.getTotalFacturaCredito(getDataInicio(), getDataFinal());
//            value -= factura.getTotalFacturaDebita(getDataInicio(), getDataFinal());
//            value += movimentoController.totalDebito(getDataInicio(), getDataFinal());

            String totalCredit = getValorCasaDecimal(Calculo.converterCash(value), ".").replace(',', '.');

            dadosFacturaIva.getAuditFile().getSourceDocuments().getSalesInvoices().setTotalDebit(totalDebit);
            dadosFacturaIva.getAuditFile().getSourceDocuments().getSalesInvoices().setTotalCredit(totalCredit);

            List<Invoices> facturaEmitida = factura.getFacturaGerais(getDataInicio(), getDataFinal());
//            facturaEmitida = factura.getNotasGerais(getDataInicio(), getDataFinal(),facturaEmitida);

            dadosFacturaIva.getAuditFile().getSourceDocuments().getSalesInvoices().setInvoices(facturaEmitida);

            dadosFacturaIva.getAuditFile().getMasterFiles().setCustomerIten(factura.listaClientes(getDataInicio(), getDataFinal()));
            dadosFacturaIva.getAuditFile().getMasterFiles().setProdutItem(factura.listaProdutosVendidos(getDataInicio(), getDataFinal()));

// Dados dos clientea
//        dadosFacturaIva.getAuditFile().getMasterFiles().getCustomer().setCustomerTaxID(""+controller.getNifEmpresa());
//                AuditFile obj = new AuditFile();
            XStream xstream = new XStream(new DomDriver());

            DadosFacturaIva.AuditFile file = dadosFacturaIva.getAuditFile();
            String xml = xstream.toXML(file);

            String output = estruturarAquivoXML(xml);
            createXML(output);
//        new SaveFileXMLDesktop().save();
            System.out.println(output);
        } else {
            JOptionPane.showMessageDialog(null, "Não Existe Facturas neste periodo", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    public String estruturarAquivoXML(String xml) {

        String mxlns = " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n"
                + "  \n "
                + "xsi:schemaLocation=\"urn:OECD:StandardAuditFile-Tax:AO_1.01_01 \\SAFTAO1.01_01.xsd\" xmlns=\"urn:OECD:StandardAuditFile-Tax:AO_1.01_01\"";

        String strAuditFileFirst = xml.replaceFirst("<json__xml__iva.DadosFacturaIva_-AuditFile>", "<AuditFile>");
        String strAuditFileFinish = strAuditFileFirst.replaceFirst("</json__xml__iva.DadosFacturaIva_-AuditFile>", "</AuditFile>");
        String strAuditFile = strAuditFileFinish.replaceFirst("<AuditFile>", "<AuditFile " + mxlns + ">");

        String xml2 = strAuditFile.replaceFirst("<Header>", " <Header>");
//        String xml3 = xml2.replaceFirst("<MasterFiles>", " <Header>");
        String strInvoiceHead = xml2;
        String strInvoiceTail = strInvoiceHead.replaceAll("</json__xml__iva.Invoice>", "");
        String strInvoicesHead = strInvoiceTail.replaceAll("<Invoices>", "");
        String strInvoicesTail = strInvoicesHead.replaceAll("</Invoices>", "");
        String strJsonXMLIvaInvoicesHead = strInvoicesTail.replaceAll("<json__xml__iva.Invoices>", "");
        String strJsonXMLIvaInvoicesTail = strJsonXMLIvaInvoicesHead.replaceAll("</json__xml__iva.Invoices>", "");
        String strLineHead = strJsonXMLIvaInvoicesTail.replaceAll("<json__xml__iva.Line>", "");
        String strLineTail = strLineHead.replaceAll("</json__xml__iva.Line>", "");

        String removeOuterClass = strLineTail.replaceAll("<outer-class reference=\"../..\"/>", "");
        String removeOuterClass2 = removeOuterClass.replaceAll("<outer-class>", "");
        String removeOuterClass3 = removeOuterClass2.replaceAll("</outer-class>", "");
        String removeAuditFile = removeOuterClass3.replaceAll("<AuditFile reference=\"../..\"/>", "");
        String removeLineItensHead = removeAuditFile.replaceAll("<LineItens>", "");
        String removeLineItensTail = removeLineItensHead.replaceAll("</LineItens>", "");
        String removeLineItensTail2 = removeLineItensTail.replaceAll("<LineItens/>", "");

        String removeLineItens2Head = removeLineItensTail2.replaceAll("<json__xml__iva.LineItens>", "");
        String removeLineItens2Tail = removeLineItens2Head.replaceAll("</json__xml__iva.LineItens>", "");
        String removeLineItensReferences = removeLineItens2Tail.replaceAll(" <Line reference=\"../..\"/>", "");
        String removeAmountDebit = removeLineItensReferences.replaceAll("<DebitAmount>0.00</DebitAmount>", "");
        String removeCreditAmount = removeAmountDebit.replaceAll("<CreditAmount>0.00</CreditAmount>", "");
        String removerIvaIonvoice = removeCreditAmount.replaceAll(" <json__xml__iva.Invoice>", "");
        String removerprodutCollectionInit = removerIvaIonvoice.replaceAll("<json__xml__iva.ProdutItem>", "");
        String removerprodutCollectionFinish = removerprodutCollectionInit.replaceAll("</json__xml__iva.ProdutItem>", "");
        String removerprodutItemInit = removerprodutCollectionFinish.replaceAll("<produtItem>", "");
        String removerprodutItemFinish = removerprodutItemInit.replaceAll("</produtItem>", "");
        String removerMasterFilesProdutItemInit = removerprodutItemFinish.replaceAll("<json__xml__iva.DadosFacturaIva_-AuditFile_-MasterFiles_-ProdutItem>", "");
        String removerMasterFilesProdutItemFinish = removerMasterFilesProdutItemInit.replaceAll("</json__xml__iva.DadosFacturaIva_-AuditFile_-MasterFiles_-ProdutItem>", "");
        String removerMasterFilesProdutInit = removerMasterFilesProdutItemFinish.replaceAll("<json__xml__iva.DadosFacturaIva_-AuditFile_-MasterFiles_-ProdutItem_-Product>", "");
        String removerMasterFilesProdutFinish = removerMasterFilesProdutInit.replaceAll("</json__xml__iva.DadosFacturaIva_-AuditFile_-MasterFiles_-ProdutItem_-Product>", "");
        String removerOuterClassReference3Transversais = removerMasterFilesProdutFinish.replaceAll("<outer-class reference=\"../../..\"/>", "");
        String removerOuterClassReference4Transversais = removerOuterClassReference3Transversais.replaceAll("<outer-class reference=\"../../../..\"/>", "");
        String removeCustomerItenInit = removerOuterClassReference4Transversais.replaceAll("<customerIten>", "");
        String removeCustomerItenFinish = removeCustomerItenInit.replaceAll("</customerIten>", "");
        String removeAuditFileMasterFilesCustomerItenInit = removeCustomerItenFinish.replaceAll("<json__xml__iva.DadosFacturaIva_-AuditFile_-MasterFiles_-CustomerIten>", "");
        String removeAuditFileMasterFilesCustomerItenFinish = removeAuditFileMasterFilesCustomerItenInit.replaceAll("</json__xml__iva.DadosFacturaIva_-AuditFile_-MasterFiles_-CustomerIten>", "");

        String removeCustomerIten2Init = removeAuditFileMasterFilesCustomerItenFinish.replaceAll("<json__xml__iva.CustomerIten>", "");
        String removeCustomerIten2Finish = removeCustomerIten2Init.replaceAll("</json__xml__iva.CustomerIten>", "");

        String removeCustomerInit = removeCustomerIten2Finish.replaceAll("<Customer reference=\"../..\"/>", "");
        String removeCustomerFinish = removeCustomerInit.replaceAll("<Customer reference=\"../..\"/>", "");

        String removeReferenced = removeCustomerInit.replaceAll("<Product reference=\"../..\"/>", "");
        String removetaxTableItemsInit = removeReferenced.replaceAll("<taxTableItems>", "");
        String removetaxTableItemsFinish = removetaxTableItemsInit.replaceAll("</taxTableItems>", "");
        String removetaxTableItemsInit2 = removetaxTableItemsFinish.replaceAll("<taxTableItems/>", "");
        String removeTaxTableItemInit = removetaxTableItemsInit2.replaceAll("<json__xml__iva.TaxTableItem>", "");
        String removeTaxTableItemFinish = removeTaxTableItemInit.replaceAll("</json__xml__iva.TaxTableItem>", "");
        String removeTaxTableEntryFinish = removeTaxTableItemFinish.replaceAll("<TaxTableEntry reference=\"../..\"/>", "");
        String removeTaxAmountInit = removeTaxTableEntryFinish.replaceAll("<TaxAmount>0</TaxAmount>", "");

//        String removeComparebleDebit  = removeTaxTableEntryFinish.replaceAll("<TotalDebit>0.00</TotalDebit>","");
//        String removeComparebleCredit = removeComparebleDebit.replaceAll("<TotalCredit>0.00</TotalCredit>","");
        String novaXML = removeTaxAmountInit;

        String vet[] = novaXML.split("\n");

        String output = "<?xml version=\"1.0\" encoding=\"windows-1252\"?>\n";
        for (String valor : vet) {
            if (!verificarCaracteresVazioString(valor)) {
                output += valor + "\n";
            }
        }
        return output;
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
