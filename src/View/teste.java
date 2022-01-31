/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 *
 * @author celso de sousa
 */
public class teste {
    
    public static void main(String[] args) {
      String enderecoMac = "";
      // PrintStream strem = new PrintStream(new File( "Configuracao"+File.separator+"MAC".concat(".config"));
        try {
            InetAddress address = InetAddress.getLocalHost();
            NetworkInterface ni = NetworkInterface.getByInetAddress(address);
            byte[] mac = ni.getHardwareAddress();
            for (int i = 0; i < mac.length; i++) {
              //  PrintStream format = System.out.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "");

             enderecoMac += (String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
               // System.out.println("aaaa>"+String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println("teste:"+enderecoMac);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
