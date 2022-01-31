/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author celso de sousa
 */
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

public class KeyPairGen {

    private PrivateKey priv;
    private PublicKey pub;

    public PrivateKey getPriv() {
        return priv;
    }

    public PublicKey getPub() {
        return pub;
    }

    public KeyPairGen() {
        try {

            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "MD5");
            final SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "MD5");
            keyGen.initialize(1024, random);
            KeyPair pair = keyGen.generateKeyPair();
            this.priv = pair.getPrivate();
            this.pub = pair.getPublic();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
