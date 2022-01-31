/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.awt.Component;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author celso
 */
public class Calculo {

    public static String formato = "#,##0.00";

    public static String converterCash(Double valor) {

        if (valor > 0) {
            DecimalFormat df = new DecimalFormat(formato, new DecimalFormatSymbols(new Locale("pt", "BR")));
            return df.format(valor);
        }
        return "0.0";
    }
    public static String converterSemCasa(Double valor) {

        if (valor > 0) {
            DecimalFormat df = new DecimalFormat("#,###", new DecimalFormatSymbols(new Locale("pt", "BR")));
            return df.format(valor);
        }
        return "0.0";
    }

    public static String converter(Double valor) {

        DecimalFormat df = new DecimalFormat("#,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        return df.format(valor);
    }

    public static double getValueNormal(String valor) {

        if (!valor.isEmpty()) {
            if (valor.contains(",")) {
                return Double.parseDouble(valor.trim().replace(".", "").replaceAll(",", "."));
            } else {
                return Double.parseDouble(valor);
            }
        }
        return 0.0;
    }

    private static boolean isDescontoValido(double desconto) {

        return desconto >= 0 && desconto <= 100;
    }

    private static boolean isDescontoValido(double desconto, double preco) {

        return desconto >= 0 && desconto <= preco;
    }

    public static double desconto(String value, double preco, Component frm) {

        double desconto = 0;
        if (!value.trim().equals(value.trim().replaceAll("%", ""))) {

            String vectorDesconto[] = value.split("%");
             desconto = !vectorDesconto[0].isEmpty() ? Double.parseDouble(vectorDesconto[0]) : Double.parseDouble(vectorDesconto[1]);

            if (isDescontoValido(desconto)) {
                return preco * desconto / 100;
            } else {
                JOptionPane.showMessageDialog(frm, "Valor do desconto invalido."
                        + "\nO valor deve ser maior ou igual do que zero(0)"
                        + " e menor ou igual do que cem(100)", "Aviso", JOptionPane.WARNING_MESSAGE);

            }
        } else {
            
            desconto = Double.parseDouble(value);
            
            if (isDescontoValido(desconto, preco)) {
                
                return desconto;
                
            } else {
                JOptionPane.showMessageDialog(frm, "Valor do desconto invalido."
                        + "\nO valor deve ser maior ou igual do que zero(0)"
                        + " e menor ou igual do que subtotal do produto", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
        return 0.0;
    }
    public static double retencao(String value, double preco, Component frm) {

        double desconto = 0;
        if (!value.trim().equals(value.trim().replaceAll("%", ""))) {

            String vectorDesconto[] = value.split("%");
             desconto = !vectorDesconto[0].isEmpty() ? Double.parseDouble(vectorDesconto[0]) : Double.parseDouble(vectorDesconto[1]);

            if (isDescontoValido(desconto)) {
                return preco * desconto / 100;
            } 
            
        } else {
            
            return retencao(value.concat("%"), preco, frm);
        }
        return 0.0;
    }
}
