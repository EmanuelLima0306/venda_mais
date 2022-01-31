/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ireport;

import Controller.ConnectionFactoryController;
import Controller.ParamentroController;
import Model.CaixaModel;
import Model.FacturaModel;
import Model.ParamentroModel;
import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.collections.map.HashedMap;

/**
 *
 * @author celso
 */
public class CaixaIreport {

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

    public static void Caixa(String descricao,double valor1, double valor2, int op, String data1, String data2, boolean isSintetica, boolean isExecel) {

//        String relatorio = "Relatorio/FacturacaoA4.jasper";
        String relatorio = "";
        Connection connection = conFactory.open();
        HashedMap hashMap = new HashedMap();
        switch (op) {
            
            case 1: { // Usuário e valor de abertura
                if (isSintetica) {
                    relatorio = "Relatorio/CaixaUsuarioValorAberturaSintetica.jasper";
                    System.out.println("valores: "+valor1+" "+valor2);
                } else {
                    relatorio = "Relatorio/CaixaUsuarioValorAbertura.jasper";
                }
                hashMap.put("COD_USUARIO", descricao);
                hashMap.put("VALOR1", valor1);
                hashMap.put("VALOR2", valor2);
                break;
            }
            case 2: { // Usuário
                if (isSintetica) {
                    relatorio = "Relatorio/CaixaUsuarioSintetica.jasper";
                } else {
                    relatorio = "Relatorio/CaixaUsuario.jasper";
                }
                hashMap.put("COD_USUARIO", descricao);
                break;
            }
            case 3: {// caixa valor abertura
                if (isSintetica) {
                    relatorio = "Relatorio/CaixaValorAberturaSintetica.jasper";

                } else {
                    relatorio = "Relatorio/CaixaValorAbertura.jasper";
                }
                
                hashMap.put("VALOR1", valor1);
                hashMap.put("VALOR2", valor2);
                break;
            }
            case 4: {
                if (isSintetica) {
                    relatorio = "Relatorio/CaixaTodosSintetica.jasper";
                } else {
                    relatorio = "Relatorio/CaixaTodos.jasper";
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
}
