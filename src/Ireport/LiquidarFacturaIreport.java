/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ireport;

import Controller.ConnectionFactoryController;
import Util.ConnectionFactory;
import java.io.File;
import java.sql.Connection;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.collections.map.HashedMap;

/**
 *
 * @author Emanuel Lima
 */
public class LiquidarFacturaIreport {
    
    public static void getById(int id) {

//        String relatorio = "Relatorio/Movimento.jasper";
        String relatorio;
            relatorio = "Relatorio/Recibo.jasper";
        
        ConnectionFactoryController conFactory = new ConnectionFactoryController();
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        System.out.println(id);
        hashMap.put("CODIGO_FACTURA", id);
        hashMap.put("ORIGINAL", "ORIGINAL");
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
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR !...");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR !...");
        }

    }
    public static void geral() {

        String relatorio = "Relatorio/MovimentoGeral.jasper";
        ConnectionFactoryController conFactory = new ConnectionFactoryController();
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
//        hashMap.put("IDMOVIMENTO", id);
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
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR !...");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR !...");
        }

    }
    public static void getByTipo(String tipo,String data1,String data2) {

        String relatorio = "Relatorio/MovimentoTipoData.jasper";
        ConnectionFactoryController conFactory = new ConnectionFactoryController();
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("DATA1", data1);
        hashMap.put("DATA2", data2);
        hashMap.put("TIPO", tipo);
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
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR !...");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR !...");
        }

    }
    public static void data(String data1,String data2) {

        String relatorio = "Relatorio/MovimentoData.jasper";
        ConnectionFactoryController conFactory = new ConnectionFactoryController();
        Connection connection = conFactory.open();

        File file = new File(relatorio).getAbsoluteFile();
        String obterCaminho = file.getAbsolutePath();
        HashedMap hashMap = new HashedMap();
        hashMap.put("DATA1", data1);
        hashMap.put("DATA2", data2);

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
            JOptionPane.showMessageDialog(null, "FALHA AO TENTAR MOSTRAR !...");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "ERRO AO TENTAR MOSTRAR !...");
        }

    }
}
