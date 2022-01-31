/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Controller.SerieController;
import Model.SerieModel;
import com.toedter.calendar.JDateChooser;
import static java.lang.Integer.parseInt;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import static java.util.Calendar.getInstance;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class DataComponent {

    public static Date stringParaData(String data) {
        if (data == null) {
            return null;
        }
        Date dataF = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long timestamp = dateFormat.parse(data).getTime();
            dataF = new Date(timestamp);
        } catch (ParseException pe) {
            try {
                throw pe;
            } catch (ParseException ex) {
                Logger.getLogger(DataComponent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dataF;
    }

    public static String getYear(Date data) {

        String dataAux[] = data.toString().split(" ");
        return dataAux[dataAux.length - 1].toUpperCase();
    }

    public static String getAnoActual() {
        Calendar calendario = getInstance();
        return calendario.get(Calendar.YEAR) + "";

    }
    public static int getAnoActualInteito() {
        Calendar calendario = getInstance();
        return calendario.get(Calendar.YEAR);

    }

    public static Date getData() {

        Date dataF = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long timestamp = dateFormat.parse(new Date().toString()).getTime();
            dataF = new Date(timestamp);
        } catch (ParseException pe) {
            try {
                throw pe;
            } catch (ParseException ex) {
                Logger.getLogger(DataComponent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return dataF;
    }

    public static LocalDate getDate(String data) {
        if (data != null) {
            int dia = parseInt(data.split("-")[2]);
            int mes = parseInt(data.split("-")[1]);
            int ano = parseInt(data.split("-")[0]);
            return LocalDate.of(ano, mes, dia);

        }
        return null;
    }
    
    
    public static boolean compareDataLastFactura(String dataLastFactura) {

        if (dataLastFactura != null) {
            //---------------  LAST FACTURA -----------------
            int[] dataFactura = getSeparacao(separaData(dataLastFactura), '-');
            int[] horaFactura = getSeparacao(separaHora(dataLastFactura), ':');
            //---------------- SISTEMA --------------------------------
            int[] dataSitema = getSeparacao(separaData(getDataActual()), '-');
            int[] horaSistema = getSeparacao(separaHora(getDataActual()), ':');

            if (dataSitema[0] > dataFactura[0]) {

                return true;
            } else {

                if (dataSitema[0] == dataFactura[0]) {

                    if (dataSitema[1] > dataFactura[1]) {
                        return true;
                    } else {

                        if (dataSitema[1] == dataFactura[1]) {

                            if (dataSitema[2] > dataFactura[2]) {
                                return true;
                            } else {
                                if (dataSitema[2] == dataFactura[2]) {

                                    if (horaSistema[0] > horaFactura[0]) {
                                        return true;
                                    } else {

                                        if (horaSistema[0] == horaFactura[0]) {

                                            if (horaSistema[1] == horaFactura[1]) {

                                                if (horaSistema[2] >= horaFactura[2]) {
                                                    
                                                    return true;
                                                } else {
                                                    return false;
                                                }

                                            } else if (horaSistema[1] > horaFactura[1]) {
                                                return true;
                                            }

                                        } else {
                                            return false;
                                        }
                                    }

                                } else {
                                    return false;
                                }
                            }

                        } else {
                            return false;
                        }
                    }

                } else {
                    return false;
                }

            }
            return false;
        }

        return true;
    }
    public static boolean compareDataExpiracao(String dataLastEntrada) {

        if (dataLastEntrada != null) {
            //---------------  LAST FACTURA -----------------
            int[] dataEntrada = getSeparacao(separaData(dataLastEntrada), '-');
            //int[] horaFactura = getSeparacao(separaHora(dataLastFactura), ':');
            //---------------- SISTEMA --------------------------------
            int[] dataSitema = getSeparacao(separaData(getDataActual()), '-');
            //int[] horaSistema = getSeparacao(separaHora(getDataActual()), ':');

            if (dataSitema[0] < dataEntrada[0]) {

                return true;
            } else {

                if (dataSitema[0] == dataEntrada[0]) {

                    if (dataSitema[1] < dataEntrada[1]) {
                        return true;
                    } else {

                        if (dataSitema[1] == dataEntrada[1]) {

                            if (dataSitema[2] < dataEntrada[2]) {
                                return true;
                            } else {
                                if (dataSitema[2] == dataEntrada[2]) {
                                    return false;

                                } else {
                                    return false;
                                }
                            }

                        } else {
                            return false;
                        }
                    }

                } else {
                    return false;
                }
            }
        }

        return true;
    }
    public static boolean compareData(String dataMaior, String dataMenor) {

        if (dataMaior != null && dataMenor != null) {
            //---------------  LAST FACTURA -----------------
            int[] dataEntrada = getSeparacao(separaData(dataMaior), '-');
            //int[] horaFactura = getSeparacao(separaHora(dataLastFactura), ':');
            //---------------- SISTEMA --------------------------------
            int[] dataSitema = getSeparacao(separaData(dataMenor), '-');
            //int[] horaSistema = getSeparacao(separaHora(getDataActual()), ':');

            if (dataSitema[0] < dataEntrada[0]) {

                return true;
            } else {

                if (dataSitema[0] == dataEntrada[0]) {

                    if (dataSitema[1] < dataEntrada[1]) {
                        return true;
                    } else {

                        if (dataSitema[1] == dataEntrada[1]) {

                            if (dataSitema[2] < dataEntrada[2]) {
                                return true;
                            } else {
                                if (dataSitema[2] == dataEntrada[2]) {
                                    return false;

                                } else {
                                    return false;
                                }
                            }

                        } else {
                            return false;
                        }
                    }

                } else {
                    return false;
                }
            }
        }

        return true;
    }

    private static int[] getSeparacao(String valor, char separador) {

        String[] aux = valor.split(String.valueOf(separador));
//        System.out.println("::::" + valor);
        int[] saida = new int[3];
        if (aux.length > 0) {

            saida[0] = Integer.parseInt(aux[0]);
            saida[1] = Integer.parseInt(aux[1]);
            if (separador == ':') {

//                String[] seg = aux[2].split(".");
//                if(seg.length > 0){
//                    System.out.println("seg[0]"+seg[0]);
//                    System.out.println("seg[1]"+seg[1]);
//                    aux[2] = seg[0];
//                }
                saida[2] = 0;
            } else {
                saida[2] = Integer.parseInt(aux[2]);
            }

        }
        return saida;
    }

    private static String separaData(String data) {

        String[] aux = data.split(" ");
        return aux.length > 0 ? aux[0] : "";
    }

    private static String separaHora(String data) {

        String[] aux = data.split(" ");
//        System.out.println("DATA :::" + aux[1]);
        return aux.length > 1 ? aux[1] : "";
    }

    public static String getDataActual() {
        Calendar calendario = getInstance();

        //buscar data
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int mes = calendario.get(Calendar.MONTH);
        int ano = calendario.get(Calendar.YEAR);

        int hora = calendario.get(Calendar.HOUR + 1);
        int minuto = calendario.get(Calendar.MINUTE);
        int segundo = calendario.get(Calendar.SECOND);

        mes++;
        String d, m;
        if (dia < 10) {
            d = "0" + dia;
        } else {
            d = "" + dia;
        }
        if (mes < 10) {
            m = "0" + mes;
        } else {
            m = "" + mes;
        }

        return "" + ano + "-" + m + "-" + d + " " + hora + ":" + minuto + ":" + segundo;

    }
    public static String gethoras() {
        Calendar calendario = getInstance();

        //buscar data
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int mes = calendario.get(Calendar.MONTH);
        int ano = calendario.get(Calendar.YEAR);

        int hora = calendario.get(Calendar.HOUR + 1);
        int minuto = calendario.get(Calendar.MINUTE);
        int segundo = calendario.get(Calendar.SECOND);

        mes++;
        String d, m;
        if (dia < 10) {
            d = "0" + dia;
        } else {
            d = "" + dia;
        }
        if (mes < 10) {
            m = "0" + mes;
        } else {
            m = "" + mes;
        }

        return " " + hora + ":" + minuto + ":" + segundo;

    }

    public static String getDataBackup() {
        Calendar calendario = getInstance();

        //buscar data
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int mes = calendario.get(Calendar.MONTH);
        int ano = calendario.get(Calendar.YEAR);

        int hora = calendario.get(Calendar.HOUR);
        int minuto = calendario.get(Calendar.MINUTE);
        int segundo = calendario.get(Calendar.SECOND);

        mes++;
        String d, m;
        if (dia < 10) {
            d = "0" + dia;
        } else {
            d = "" + dia;
        }
        if (mes < 10) {
            m = "0" + mes;
        } else {
            m = "" + mes;
        }

        return "" + ano + "" + m + "" + d + "" + hora + "" + minuto + "" + segundo;

    }

    public static String getData1() {
        Calendar calendario = getInstance();

        //buscar data
        int dia = calendario.get(Calendar.DAY_OF_MONTH);
        int mes = calendario.get(Calendar.MONTH);
        int ano = calendario.get(Calendar.YEAR);
        mes++;
        String d, m;
        if (dia < 10) {
            d = "0" + dia;
        } else {
            d = "" + dia;
        }
        if (mes < 10) {
            m = "0" + mes;
        } else {
            m = "" + mes;
        }

        return "" + ano + "-" + m + "-" + d;

    }

    public static String getData(JDateChooser data) {

        if (data.getDate() != null) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data.getDate());

            String dataSelecionada = gc.get(GregorianCalendar.YEAR) + "-"
                    + (gc.get(GregorianCalendar.MONTH) + 1) + "-"
                    + gc.get(GregorianCalendar.DATE);

            return dataSelecionada;
        }
        return "";
    }
    public static String getData(Date data) {

        if (data != null) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data);
            
        int dia = gc.get(GregorianCalendar.DATE);
        int mes = (gc.get(GregorianCalendar.MONTH) + 1);
        int ano = gc.get(GregorianCalendar.YEAR);    
            
            String d, m;
        if (dia < 10) {
            d = "0" + dia;
        } else {
            d = "" + dia;
        }
        if (mes < 10) {
            m = "0" + mes;
        } else {
            m = "" + mes;
        }

            return "" + ano + "-" + m + "-" + d;
        }
        return "";
    }

    public static String getData(JDateChooser data, int hora, int minuto) {

        if (data.getDate() != null) {
            GregorianCalendar gc = new GregorianCalendar();
            gc.setTime(data.getDate());

            String dataSelecionada = gc.get(GregorianCalendar.YEAR) + "-"
                    + (gc.get(GregorianCalendar.MONTH) + 1) + "-"
                    + gc.get(GregorianCalendar.DATE);

            System.out.println("dataSelecionada ::: " + dataSelecionada + " " + hora + ":" + minuto);

            return dataSelecionada + " " + hora + ":" + minuto;

        }
        return "";
    }

}
