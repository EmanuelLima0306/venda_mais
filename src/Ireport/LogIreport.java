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
public class LogIreport {

    private static ConnectionFactoryController conFactory = new ConnectionFactoryController();

    

    public static void acessos(String descricao, int op, String data1, String data2, boolean isSintetica, boolean isExecel) {

//        String relatorio = "Relatorio/FacturacaoA4.jasper";
        String relatorio = "";
        Connection connection = conFactory.open();
        HashedMap hashMap = new HashedMap();
        switch (op) {
            case 1: {
                if (isSintetica) {
                    //relatorio = "Relatorio/FacturacaoClienteSintentica.jasper";
                } else {
                    relatorio = "Relatorio/LogAcessoUsuarioTipo.jasper";
                }
                String[] dados;
                
                dados = descricao.split(";");
                
                
                System.out.println(dados[0]+"\n"+dados[1]);
                hashMap.put("COD_USUARIO", dados[0]);
                hashMap.put("TIPO", dados[1]);
                break;
            }
            case 2: {
                if (isSintetica) {
                    //relatorio = "Relatorio/FacturacaoUsuarioSintetica.jasper";
                } else {
                    relatorio = "Relatorio/LogAcessoUsuario.jasper";
                }
                hashMap.put("COD_USUARIO", descricao);
                break;
            }
            case 3: {
                if (isSintetica) {
                    //relatorio = "Relatorio/LogAcessoTipo.jasper";

                } else {
                    relatorio = "Relatorio/LogAcessoTipo.jasper";
                }
                hashMap.put("TIPO", descricao);
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
