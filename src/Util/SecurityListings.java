/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.security.Provider;
import java.security.Security;

/**
 *
 * @author celso de sousa
 */
public class SecurityListings {

    public static void main(String[] args) {
//        for (Provider provider : Security.getProviders()) {
//            System.out.println("Provider: " + provider.getName());
//            for (Provider.Service service : provider.getServices()) {
//                System.out.println(" Algorithm: " + service.getAlgorithm());
//            }
//        }

       
//          System.out.println("private>>>" + keyPrivate());
//          System.out.println("public>>>" + keyPublic());
    }
    public static String keyPublic() {

        KeyPairGen t = new KeyPairGen();
        byte[] keyPublic = t.getPub().getEncoded();
        String key = "";
        for (byte aux : keyPublic) {

            key += aux;
        }
        return key;
    }
    public static String keyPrivate() {

        KeyPairGen t = new KeyPairGen();
        byte[] keyPrivate = t.getPriv().getEncoded();
        String key = "";
        for (byte aux : keyPrivate) {

            key += aux;
        }
        return key;
    }
}
