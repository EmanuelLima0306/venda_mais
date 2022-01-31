/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ireport;

import Controller.ConnectionFactoryController;
import Controller.ParamentroController;
import Model.CaixaModel;
import Model.FacturaItemModel;
import Model.FacturaModel;
import Model.MesaModel;
import Model.ParamentroModel;
import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.collections.map.HashedMap;

/**
 *
 * @author celso
 */
public class FacturaIreport {

    private static ConnectionFactoryController conFactory = new ConnectionFactoryController();

    public static void termica(FacturaModel factura, String tipo, boolean original) {

        String relatorio = "Relatorio/Factura_termica.jasper";
        Connection connection = conFactory.open();

        //parametros
        ParamentroController pController = new ParamentroController();
        List<ParamentroModel> lista = pController.get();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("CODIGO_FACTURA", factura.getId());
        hashMap.put("TIPO_FACTURA", tipo);
        hashMap.put("ORIGINAL", original ? "ORIGINAL" : " 2º via documento em conformidade com a original");
        //hashMap.put("FORMACAO", lista.get(6).getValor());

        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);
            int numPagina = jasperPrint.getPages().size();

//            ParamentroController pController = new ParamentroController();
//            List<ParamentroModel> lista = pController.get();
            if (lista.size() > 0) {

                ParamentroModel pModelo = lista.get(0);
                if (numPagina >= 1) {

                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

                    if (pModelo.getValor() == 0) {
                        jasperViewer.setVisible(true);
                    } else {
                        for (int i = 0; i < 2; i++) {
                            JasperPrintManager.printPages(jasperPrint, 0, numPagina - 1, false);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Folha vazia !...");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Parametro invalido consulte o administrador do sistema");
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
    public static void fechoCaixa(CaixaModel model) {

        String relatorio = "Relatorio/fechoCaixa.jasper";
        Connection connection = conFactory.open();

        //parametros
        ParamentroController pController = new ParamentroController();
        List<ParamentroModel> lista = pController.get();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("CAIXA", model.getId());


        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);
            int numPagina = jasperPrint.getPages().size();

//            ParamentroController pController = new ParamentroController();
//            List<ParamentroModel> lista = pController.get();
            if (lista.size() > 0) {

                ParamentroModel pModelo = lista.get(0);
                if (numPagina >= 1) {

                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

                    if (pModelo.getValor() == 0) {
                        jasperViewer.setVisible(true);
                    } else {
                        for (int i = 0; i < 2; i++) {
                            JasperPrintManager.printPages(jasperPrint, 0, numPagina - 1, false);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Não foi efectuado nenhuma venda nesse dia");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Parametro invalido consulte o administrador do sistema");
            }
        } catch (JRException jex) {
            jex.printStackTrace();
            //System.out.println("aqui");
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR  !...");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR  !...");
        }

    }

    public static void performa(FacturaModel factura, String tipo, boolean original) {

        String relatorio = "Relatorio/FacturaPreformaA4.jasper";
        Connection connection = conFactory.open();

        //paramentro
        ParamentroController pController = new ParamentroController();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("CODIGO_FACTURA", factura.getId());
        hashMap.put("TIPO_FACTURA", tipo);
        hashMap.put("ORIGINAL", original ? "ORIGINAL" : " 2º via documento em conformidade com a original");

        ParamentroModel parametroFormacao = pController.getById(7);

        boolean flag = false;
        if (original) {

            flag = parametroFormacao.getValor() == 1;

        } else {

            flag = factura.getCriada_modulo_formacao();
        }

        if (flag) {

            File fileFormacao = new File("Relatorio/moduloFormacao.png");
            hashMap.put("FORMACAO", fileFormacao.getAbsolutePath());
        }

        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);
            int numPagina = jasperPrint.getPages().size();

            ParamentroModel parametroImprimir = pController.getById(1);
            if (parametroImprimir != null) {

                ParamentroModel pModelo = parametroImprimir;
                if (numPagina >= 1) {

                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

                    if (pModelo.getValor() == 0) {
                        jasperViewer.setVisible(true);
                    } else {
                        for (int i = 0; i < 2; i++) {
                            JasperPrintManager.printPages(jasperPrint, 0, numPagina - 1, false);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Folha vazia !...");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Parametro invalido consulte o administrador do sistema");
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

    public static void termicaEcomenda(int idFactura) {

        String relatorio = "Relatorio/Encomenda_termica.jasper";
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("CODIGO_FACTURA", idFactura);

        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);
            int numPagina = jasperPrint.getPages().size();

            ParamentroController pController = new ParamentroController();
            List<ParamentroModel> lista = pController.get();

            if (lista.size() > 0) {

                ParamentroModel pModelo = lista.get(0);
                if (numPagina >= 1) {

                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

                    if (pModelo.getValor() == 0) {
                        jasperViewer.setVisible(true);
                    } else {
                        for (int i = 0; i < 2; i++) {
                            JasperPrintManager.printPages(jasperPrint, 0, numPagina - 1, false);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Folha vazia !...");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Parametro invalido consulte o administrador do sistema");
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

    public static void saidaProduto(String data1, String data2) {

        String relatorio = "Relatorio/ListaSaidaProduto.jasper";
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("DATA1", data1);
        hashMap.put("DATA2", data2);

        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);
            int numPagina = jasperPrint.getPages().size();

            ParamentroController pController = new ParamentroController();
            List<ParamentroModel> lista = pController.get();

            if (lista.size() > 0) {

                ParamentroModel pModelo = lista.get(0);
                if (numPagina >= 1) {

                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

                    if (pModelo.getValor() == 0) {
                        jasperViewer.setVisible(true);
                    } else {
                        for (int i = 0; i < 2; i++) {
                            JasperPrintManager.printPages(jasperPrint, 0, numPagina - 1, false);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Folha vazia !...");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Parametro invalido consulte o administrador do sistema");
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

    public static void termicaContaMesa(int idMesa) {

        String relatorio = "Relatorio/contaClienteMesa.jasper";
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("CODIGO_MESA", idMesa);
        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);
            int numPagina = jasperPrint.getPages().size();

            ParamentroController pController = new ParamentroController();
            List<ParamentroModel> lista = pController.get();

            if (lista.size() > 0) {

                ParamentroModel pModelo = lista.get(0);
                if (numPagina >= 1) {

                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

                    if (pModelo.getValor() == 0) {
                        jasperViewer.setVisible(true);
                    } else {
                        for (int i = 0; i < 2; i++) {
                            JasperPrintManager.printPages(jasperPrint, 0, numPagina - 1, false);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Folha vazia !...");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Parametro invalido consulte o administrador do sistema");
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

    public static void termicaPedidoCozinha(int idMesa) {

        String relatorio = "Relatorio/pedidoCozinha.jasper";
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("CODIGO_MESA", idMesa);
        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);
            int numPagina = jasperPrint.getPages().size();

            ParamentroController pController = new ParamentroController();
            List<ParamentroModel> lista = pController.get();

            if (lista.size() > 0) {

                ParamentroModel pModelo = lista.get(0);
                if (numPagina >= 1) {

                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

                    if (pModelo.getValor() == 1) {
                        jasperViewer.setVisible(true);
                    } else {
//                        for (int i = 0; i < 2; i++) {
//                            JasperPrintManager.printPages(jasperPrint, 0, numPagina - 1, false);
//                        }
                          ParamentroModel impressoraCuzinha = pController.getImpressoraCozinha();
                          printDirectlyToPrinter(impressoraCuzinha.getDescricao(), jasperPrint);
                    
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Folha vazia !...");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Parametro invalido consulte o administrador do sistema");
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

    public static void pedidoCozinhaBalcao(int idFactura) {

        String relatorio = "Relatorio/pedidoBalcaoCozinha.jasper";
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("CODIGO_MESA", idFactura);
        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);
            int numPagina = jasperPrint.getPages().size();

            ParamentroController pController = new ParamentroController();
            List<ParamentroModel> lista = pController.get();

            if (lista.size() > 0) {

                ParamentroModel pModelo = lista.get(0);
                if (numPagina >= 1) {

                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

                    if (pModelo.getValor() == 1) {
                        jasperViewer.setVisible(true);
                    } else {
                        for (int i = 0; i < 2; i++) {
                            JasperPrintManager.printPages(jasperPrint, 0, numPagina - 1, false);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Folha vazia !...");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Parametro invalido consulte o administrador do sistema");
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
    public static void pedidoCozinhaBalcao(FacturaItemModel facturaItemModel, double qtd, MesaModel mesa) {

        String relatorio = "Relatorio/pedidoCozinhaDirecto.jasper";
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("MESA", mesa.getDesignacao());
        hashMap.put("PRODUTO", facturaItemModel.getProduto().getDesignacao());
        hashMap.put("QTD", qtd);
        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);
            int numPagina = jasperPrint.getPages().size();

            ParamentroController pController = new ParamentroController();
            List<ParamentroModel> lista = pController.get();

            if (lista.size() > 0) {

                ParamentroModel pModelo = lista.get(0);
                if (numPagina >= 1) {

                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

                    if (pModelo.getValor() == 0) {
                        jasperViewer.setVisible(true);
                    } else {
//                        for (int i = 0; i < 2; i++) {
//                            JasperPrintManager.printPages(jasperPrint, 0, numPagina - 1, false);
//                        }
                        ParamentroModel impressoraCuzinha = pController.getImpressoraCozinha();
                        printDirectlyToPrinter(impressoraCuzinha.getDescricao(), jasperPrint);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Folha vazia !...");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Parametro invalido consulte o administrador do sistema");
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
    
    private static void printDirectlyToPrinter(String printerName, JasperPrint jasperPrint)  
    {     
        try 
        {  
            //Lista todas impressoras até encontrar a enviada por parametro  
            PrintService serviceFound = null;  
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);  

            for(PrintService service:services)
            {  
                if(service.getName().trim().equals(printerName.trim()))  
                    serviceFound = service;  
            }  

            if (serviceFound == null)  
                throw new Exception("Impressora não encontrada !");  

            JRExporter exporter = new JRPrintServiceExporter();  
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);  
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, serviceFound.getAttributes());  
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);  
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);  
            exporter.exportReport();  

            //JasperPrintManager.printPage(jasperPrint, 0, false);  
        } catch (JRException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }

    public static void fechoCaixaSintetico(int idUsuario, String data) {

        String relatorio = "Relatorio/FechoSintetico.jasper";
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("COD_USUARIO", idUsuario);
        hashMap.put("DATA", data);
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

    public static void balancoAnual(int ano, int ano1) {

        String relatorio = "Relatorio/GraficoBalancoVenda.jasper";
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("ANO_1", ano);
        hashMap.put("ANO_2", ano1);
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

    public static void balancoAnualMensal(int ano) {

        String relatorio = "Relatorio/GraficoBalancoVendaMensal.jasper";
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("ANO_1", ano);

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

    public static void facturacao(String descricao, int op, String data1, String data2, boolean isSintetica, boolean isExecel) {

//        String relatorio = "Relatorio/FacturacaoA4.jasper";
        String relatorio = "";
        Connection connection = conFactory.open();
        HashedMap hashMap = new HashedMap();
        switch (op) {
            case 1: {
                if (isSintetica) {
                    relatorio = "Relatorio/FacturacaoClienteSintentica.jasper";
                } else {
                    relatorio = "Relatorio/FacturacaoClienteA4.jasper";
                }
                hashMap.put("CODIGO_CLIENTE", descricao);
                break;
            }
            case 2: {
                if (isSintetica) {
                    relatorio = "Relatorio/FacturacaoUsuarioSintetica.jasper";
                } else {
                    relatorio = "Relatorio/FacturacaoUsuarioA4.jasper";
                }
                hashMap.put("COD_USUARIO", descricao);
                break;
            }
            case 3: {
                if (isSintetica) {
                    relatorio = "Relatorio/FacturacaoFormaPagamentoSintetica.jasper";

                } else {
                    relatorio = "Relatorio/FacturacaoFormaPagamentoA4.jasper";
                }
                hashMap.put("COD_FORMA_PAGAMENTO", descricao);
                break;
            }
            case 4: {
                if (isSintetica) {
                    relatorio = "Relatorio/FacturacaoProdutooSintetica.jasper";
                } else {
                    relatorio = "Relatorio/FacturacaoProdutooA4.jasper";
                }
                hashMap.put("COD_PRODUTO", descricao);
                break;
            }
            case 5: {
                if (isSintetica) {
                    relatorio = "Relatorio/FacturacaoCategoriaSintetica.jasper";
                } else {
                    relatorio = "Relatorio/FacturacaoCategoriaA4.jasper";
                }
                hashMap.put("COD_CATEGORIA", descricao);
                break;
            }
            case 6: {
                if (isSintetica) {
                    relatorio = "Relatorio/FacturacaoFabricanteSintetica.jasper";
                } else {
                    relatorio = "Relatorio/FacturacaoFabricanteA4.jasper";
                }
                hashMap.put("FABRICANTE", descricao);
                break;
            }
            case 7: {
                if (isSintetica) {
                    relatorio = "Relatorio/FacturacaoSintetica.jasper";
                } else {
                    relatorio = "Relatorio/FacturacaoA4.jasper";
                }
                hashMap.put("FABRICANTE", descricao);
                break;
            }
            default:
                break;
        }

        try {
            File file = new File(relatorio).getAbsoluteFile();
            String obterCaminho = file.getAbsolutePath();

            hashMap.put("DATA1", data1);
            hashMap.put("DATA2", data2);

            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            if (jasperPrint.getPages().size() >= 1) {

                if (!isExecel) {
                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                    jasperViewer.setVisible(true);
                } else {
                    AbrirExecel.XLSX(obterCaminho, hashMap, connection);
                }

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

    public static void DividaCliente(int idCliente) {

        String relatorio = "Relatorio/FacturaDividaA4.jasper";
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("CODIGO_CLIENTE", idCliente);
        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            if (jasperPrint.getPages().size() >= 1) {

                JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                jasperViewer.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "Cliente nao tem divida !...");
            }
        } catch (JRException jex) {
            jex.printStackTrace();
            //System.out.println("aqui");
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR  DIVIDA!...");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR DIVIDA !...");
        }

    }

    public static void pagasCliente(int idCliente) {

        String relatorio = "Relatorio/FacturaPagasPorClienteA4.jasper";
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("CODIGO_CLIENTE", idCliente);
        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            if (jasperPrint.getPages().size() >= 1) {

                JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                jasperViewer.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "Cliente nao tem divida !...");
            }
        } catch (JRException jex) {
            jex.printStackTrace();
            //System.out.println("aqui");
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR  DIVIDA!...");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR DIVIDA !...");
        }

    }

    public static void pagasTodas() {

        String relatorio = "Relatorio/FacturaPagasTodasA4.jasper";
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();

        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            if (jasperPrint.getPages().size() >= 1) {

                JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                jasperViewer.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "Cliente nao tem divida !...");
            }
        } catch (JRException jex) {
            jex.printStackTrace();
            //System.out.println("aqui");
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR  DIVIDA!...");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR DIVIDA !...");
        }

    }

    public static void graficoVendaProduto() {

        String relatorio = "Relatorio/GraficoVendaProdutoPie.jasper";
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();

        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            if (jasperPrint.getPages().size() >= 1) {

                JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                jasperViewer.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "Cliente nao tem divida !...");
            }
        } catch (JRException jex) {
            jex.printStackTrace();
            //System.out.println("aqui");
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR  DIVIDA!...");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR DIVIDA !...");
        }

    }

    public static void graficoVendaCliente() {

        String relatorio = "Relatorio/GraficoVendaClientePie.jasper";
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();

        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            if (jasperPrint.getPages().size() >= 1) {

                JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                jasperViewer.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "Cliente nao tem divida !...");
            }
        } catch (JRException jex) {
            jex.printStackTrace();
            //System.out.println("aqui");
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR  DIVIDA!...");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR DIVIDA !...");
        }

    }

    public static void A4(FacturaModel factura, String tipo, boolean original) {

        String relatorio = "Relatorio/FacturaA4.jasper";
        Connection connection = conFactory.open();

        // paramentros
        ParamentroController pController = new ParamentroController();
        ParamentroModel parametroFormacao = pController.getById(7);

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("CODIGO_FACTURA", factura.getId());
        System.out.println("Tipo Factura===== "+tipo);
        hashMap.put("TIPO_FACTURA", tipo);
        boolean flag = false;
        if (original) {

            flag = parametroFormacao.getValor() == 1;

        } else {

            flag = factura.getCriada_modulo_formacao();
        }

        if (flag) {

            File fileFormacao = new File("Relatorio/moduloFormacao.png");
            hashMap.put("FORMACAO", fileFormacao.getAbsolutePath());
        }

//        hashMap.put("PAGINA", tipo);
        hashMap.put("ORIGINAL", original ? "ORIGINAL" : " 2º via documento em conformidade com a original");
        try {

            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);
            int numPagina = jasperPrint.getPages().size();

//            List<ParamentroModel> lista = pController.get();
            ParamentroModel pModelo = pController.getById(1);
            if (pModelo != null) {

                if (numPagina >= 1) {

                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

                    if (pModelo.getValor() == 0) {
                        jasperViewer.setVisible(true);
                    } else {
                        for (int i = 0; i < 2; i++) {
                            JasperPrintManager.printPages(jasperPrint, 0, numPagina - 1, false);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Folha vazia !...");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Parametro invalido consulte o administrador do sistema");
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

    public static void CartaGarantiaA4(int id) {

        String relatorio = "Relatorio/CartaDeGarantia.jasper";
        Connection connection = conFactory.open();

        // paramentros
        ParamentroController pController = new ParamentroController();
        List<ParamentroModel> lista = pController.get();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("CODIGO_FACTURA", id);
        hashMap.put("FORMACAO", lista.get(6).getValor());

        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);
            int numPagina = jasperPrint.getPages().size();

//            ParamentroController pController = new ParamentroController();
//            List<ParamentroModel> lista = pController.get();
            if (lista.size() > 0) {

                ParamentroModel pModelo = lista.get(0);
                if (numPagina >= 1) {

                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

                    if (pModelo.getValor() == 0) {
                        jasperViewer.setVisible(true);
                    } else {
                        for (int i = 0; i < 2; i++) {
                            JasperPrintManager.printPages(jasperPrint, 0, numPagina - 1, false);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Folha vazia !...");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Parametro invalido consulte o administrador do sistema");
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
    public static void GuiaRemessaA4(int id,boolean isOriginal) {

        String relatorio = "Relatorio/GuiaRemessa.jasper";
        Connection connection = conFactory.open();

        // paramentros
        ParamentroController pController = new ParamentroController();
        List<ParamentroModel> lista = pController.get();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("CODIGO_FACTURA", id);
        hashMap.put("ORIGINAL", isOriginal ? "ORIGINAL" : "2º via documento");
        hashMap.put("FORMACAO", lista.get(6).getValor());

        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);
            int numPagina = jasperPrint.getPages().size();

//            ParamentroController pController = new ParamentroController();
//            List<ParamentroModel> lista = pController.get();
            if (lista.size() > 0) {

                ParamentroModel pModelo = lista.get(0);
                if (numPagina >= 1) {

                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

                    if (pModelo.getValor() == 0) {
                        jasperViewer.setVisible(true);
                    } else {
                        for (int i = 0; i < 2; i++) {
                            JasperPrintManager.printPages(jasperPrint, 0, numPagina - 1, false);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Folha vazia !...");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Parametro invalido consulte o administrador do sistema");
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
    public static void GuiaTransporteA4(int id,boolean isOriginal) {

        String relatorio = "Relatorio/GuiaTransporte.jasper";
        Connection connection = conFactory.open();

        // paramentros
        ParamentroController pController = new ParamentroController();
        List<ParamentroModel> lista = pController.get();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("CODIGO_FACTURA", id);
        hashMap.put("ORIGINAL", isOriginal ? "ORIGINAL" : "2º via documento");
        hashMap.put("FORMACAO", lista.get(6).getValor());

        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);
            int numPagina = jasperPrint.getPages().size();

//            ParamentroController pController = new ParamentroController();
//            List<ParamentroModel> lista = pController.get();
            if (lista.size() > 0) {

                ParamentroModel pModelo = lista.get(0);
                if (numPagina >= 1) {

                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

                    if (pModelo.getValor() == 0) {
                        jasperViewer.setVisible(true);
                    } else {
                        for (int i = 0; i < 2; i++) {
                            JasperPrintManager.printPages(jasperPrint, 0, numPagina - 1, false);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Folha vazia !...");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Parametro invalido consulte o administrador do sistema");
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

    public static void ProformaA4(int id) {

        String relatorio = "Relatorio/FacturaProformaA4.jasper";
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("CODIGO_FACTURA", id);
        hashMap.put("TIPO_FACTURA", "Factura Pró-forma");
        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);
            int numPagina = jasperPrint.getPages().size();

            ParamentroController pController = new ParamentroController();
            List<ParamentroModel> lista = pController.get();

            if (lista.size() > 0) {

                ParamentroModel pModelo = lista.get(0);
                if (numPagina >= 1) {

                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

                    if (pModelo.getValor() == 0) {
                        jasperViewer.setVisible(true);
                    } else {
                        for (int i = 0; i < 2; i++) {
                            JasperPrintManager.printPages(jasperPrint, 0, numPagina - 1, false);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Folha vazia !...");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Parametro invalido consulte o administrador do sistema");
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

    public static void A5(FacturaModel factura, String tipo, boolean original) {

        String relatorio = "Relatorio/FacturaA5.jasper";
        Connection connection = conFactory.open();

        //parametro
        ParamentroController pController = new ParamentroController();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("CODIGO_FACTURA", factura.getId());
        hashMap.put("TIPO_FACTURA", tipo);
        hashMap.put("ORIGINAL", original ? "ORIGINAL" : " 2º via documento em conformidade com a original");

        ParamentroModel parametroFormacao = pController.getById(7);
        boolean flag = false;
        if (original) {

            flag = parametroFormacao.getValor() == 1;

        } else {

            flag = factura.getCriada_modulo_formacao();
        }

        if (flag) {

            File fileFormacao = new File("Relatorio/moduloFormacao.png");
            hashMap.put("FORMACAO", fileFormacao.getAbsolutePath());
        }

        try {
            JasperFillManager.fillReport(obterCaminho, hashMap, connection);

            JasperPrint jasperPrint = JasperFillManager.fillReport(obterCaminho, hashMap, connection);
            int numPagina = jasperPrint.getPages().size();

//            ParamentroController pController = new ParamentroController();
//            List<ParamentroModel> lista = pController.get();
            ParamentroModel parametroImprimir = pController.getById(1);
            if (parametroImprimir != null) {

                ParamentroModel pModelo = parametroImprimir;
                if (numPagina >= 1) {

                    JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);

                    if (pModelo.getValor() == 0) {
                        jasperViewer.setVisible(true);
                    } else {
                        for (int i = 0; i < 2; i++) {
                            JasperPrintManager.printPages(jasperPrint, 0, numPagina - 1, false);
                        }
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Folha vazia !...");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Parametro invalido consulte o administrador do sistema");
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
