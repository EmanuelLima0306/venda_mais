/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ireport;

import java.io.FileOutputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author ASCEMIL-TECNOLOGIAS
 */
public class AbrirExecel {
    
     public static void XLSX(String jasperFilename, Map<String, Object> parameters, java.sql.Connection dataSource)
        {
            try
            {
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperFilename, parameters, dataSource);
                int indexOfPonto = jasperFilename.indexOf('.');
                String file = jasperFilename.substring(0, indexOfPonto) + ".xlsx";

                FileOutputStream output = new FileOutputStream(file);

                JRXlsxExporter docxExporter = new JRXlsxExporter();
                docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                docxExporter.setParameter(JRXlsAbstractExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output);
                docxExporter.setParameter(JRXlsAbstractExporterParameter.IS_DETECT_CELL_TYPE, Boolean.FALSE);
                docxExporter.exportReport();

                Runtime rt = Runtime.getRuntime();
                System.out.println(file);

                rt.exec("RunDLL32.EXE shell32.dll,ShellExec_RunDLL " + file);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
}
