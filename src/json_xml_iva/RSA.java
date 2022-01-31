package json_xml_iva;

import Util.DataComponent;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthException;
import net.oauth.signature.OAuthSignatureMethod;
import static net.oauth.signature.OAuthSignatureMethod.decodeBase64;

public class RSA extends OAuthSignatureMethod {

    @Override
    protected void initialize(String name, OAuthAccessor accessor)
            throws OAuthException {
        super.initialize(name, accessor);
    }

    @Override
    protected String getSignature(String baseString) throws OAuthException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean isValid(String signature, String baseString) throws OAuthException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static PrivateKey getPrivateKeyFromPem(String privateKeyObject)
            throws GeneralSecurityException {
        KeyFactory fac = KeyFactory.getInstance("RSA");
        EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(decodeBase64(privateKeyObject));
        return fac.generatePrivate(privKeySpec);
    }

    public static String getHash(String Message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        PrivateKey priv = null;

        System.out.println("RSA >>>>> " + Message);

        try {
            priv = getPrivateKeyFromPem(ChaveIVA.PRIVATE_KEY);
            priv = getPrivateKeyFromPem("MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJuKjlcCLb9UGpr6\n"
                    + "9lZ6DOi2KhEONVFAZpWaiH125xPTQdL13cTSCkGpRo2NUN+/ZZgaEbOtbYGmg0G0\n"
                    + "qVk4/rHtxQl3OFZCuDPr9xUZA4Fldor1qhBpmkyWezSldbB3RqlwO7M/CfarMGy0\n"
                    + "Txl5v0Y5CpDaHhluzbsT6HWoT5LlAgMBAAECgYAQJyV9F7bUhdF1gtaOSIKKsFBS\n"
                    + "ER2sYHnG05OVJAH/ZxMQ64Oqav2xr/ipvqFFe0T7tMld1YbnzfkXm8FyiJ66hjpu\n"
                    + "DbvLtfRhZM9k3TJKXeZkeIaEJJ97ZxzEQC+EfSkDsyi3Nn6zfhV/ceIGB/62nf4n\n"
                    + "deVxn2dov+UfDoEOQQJBAMiZjeVPE46AMIcJjxBzMfSWtIItfWvppRCTaQ+257ao\n"
                    + "k4mGykiuT1CqMZ3yS1p2bd5dy+W7thzwvk6ozoLpoekCQQDGf1ncnjRFB12MKsVt\n"
                    + "oFmqK1o3MSu8VMzjjr/jihlyP7e1pH8SRU/CTSaSzyeROgCTERGI2ZEaAWRPXyM1\n"
                    + "8a+dAkALYJIosEx2p5SZBBTGJRJvQeDpBTV42l6PSx0JVCFePb9obGmqp6A9/fkk\n"
                    + "cSqO7eqbUwyOchAJIipZAb/8ss2JAkATl9yB52fXbHuya0JjqNFQ98iG7CaaB3DW\n"
                    + "AXA1gJs0aM+0cVFNt2PBFSZ6lVIdhrEp0yR88qTdAUgqgYSTPZENAkAD45tM1xAn\n"
                    + "MsBxML3sXMQ8exkRg0hghZsDXSV8yfgnAodz4d+xuPgANq4humH3OpZPNWDxpyZo\n"
                    + "SpEyHqxiBJ7x");
//            priv = getPrivateKeyFromPem("MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAN9KH2eEsGb8+A2e\n"
//                    + "Ce4DWFSOjJKQAxcYr0L6kn9sbcq3rzq8M/oPfXL8VYoKPCH+8Fx4S5dptOAoqvu2\n"
//                    + "KiCWLgM0Dzwnyjw+cUqtoE3zfMrJ9nvdodBd3zW/SrWXZS/S7iNRUaY1bzdS/McX\n"
//                    + "X3VMLU9igzsInbpL6EPRh1oVE/xNAgMBAAECgYEAszgRQMtVszPz/SoOGkYBDc1k\n"
//                    + "svZ4JH+hnIb2MuRas0z/joskxN7N+A7ksWlJnpe7YN0E/0DO9uRtaJ9r7L/dqSA9\n"
//                    + "N2mo1Pf+IEDbtljX+URri+xWjI46gMXfTCjaTnGy+bn85728aBIe1FrmOkAZUmwM\n"
//                    + "womPDEx9M4iUwLcw78ECQQD7x+pw/JWsfmHT7Egotsc+EkCRPN3Lo+jGpyVrctVa\n"
//                    + "ode2gNSilGYbFjF9HjFn5IuaLNTIW7Cffo1w83kXneo1AkEA4wf8NgTCLhGCqeR/\n"
//                    + "yefYy43f5tU/ezdw+7bfzOqjX6LgmZsNYfj49SJsZu/vvAxTvl/ZBssnZFvGIwWU\n"
//                    + "NnZMuQJBAKQBy4Mh5VVhVdQ3+6EZvpt+RDTXUxrNgFm2kzd0q+nHtr4ZGWl2BTTr\n"
//                    + "tfzsZ/5m71DUlx6UK28ZfoTu9BggM2kCQQCM57DWSf/GneGN1h6lznqUY3qahrG+\n"
//                    + "pLs/ztF6GgrJ2Yoya7RToTXK1tGW1cXh3XjASDana8EIHZmMtPK3FpcpAkAISTgP\n"
//                    + "9nMImB+V97JN5A16kgaFimJqoWVgH/gyuBN7k62gOSy6cxeWYSn5W23xQ6Fssmeh\n"
//                    + "v5NJFaf0cwWVXYi2");
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Prepare signature.
        Signature sign = Signature.getInstance("SHA1withRSA");
        sign.initSign(priv);
        sign.update(Message.getBytes());
        //Sign the data with private key.
        byte[] realSig = sign.sign();
        //encode Signature
        String encodedSig = Base64.getEncoder().encodeToString(realSig);
        return encodedSig;
    }

    private static String getData(String data) {

        String[] vet = getCompData(data);
        return vet[0];
    }

    private static String getDataTime(String data) {

//        String datav = data.substring(0, 19);
        String[] vet = getCompData(data);
        return vet[0] + "T" + getHora(vet[1]);
    }

    private static String getHora(String hora) {

        if (!hora.isEmpty()) {

            String part[] = hora.trim().split(":");

            if (part.length > 2) {
                String hh = part[0];
                String mm = part[1];
                String ss = part[2];
                System.out.println("segundos:::: "+ss);
                if(ss.contains(".0"))
                    ss = ss.replace(".0", "");
                System.out.println("segundos:::: "+ss);
                
                int hh_aux = Integer.parseInt(hh);
                int mm_aux = Integer.parseInt(mm);
                int ss_aux = Integer.parseInt(ss);

                if (hh_aux < 10) {
                    hh = "0" + hh_aux;
                }
                if (mm_aux < 10) {
                    mm = "0" + mm_aux;
                }
                if (ss_aux < 10) {
                    ss = "0" + ss_aux;
                }

                hora = hh + ":" + mm + ":" + ss;

                return hora;
            }
        }

        return hora;
    }

    private static String[] getCompData(String data) {

        String[] vet = data.split(" ");
        return vet;
    }

    public static String getTotal(String total) {

        if (!total.trim().isEmpty()) {

            int index = total.indexOf(".");

            String partDecimal = total.substring(index + 1);
            double totalFactura = Double.parseDouble(total);

            int part_inteira = (int) totalFactura;
//            double part_decimal = (totalFactura - part_inteira);
            
            if (partDecimal.contains("E")) {

                int expoente = Integer.parseInt(partDecimal.substring(partDecimal.indexOf("E") + 1));
                partDecimal = partDecimal.replace("E" + expoente, "");
                
                for (int i = partDecimal.length(); i < expoente; i++) {
                    partDecimal = partDecimal.concat("0");
                }

            }
//            double part_decimal = Double.parseDouble(partDecimal);
//            if (part_decimal < 10) {
//
//                String aux = String.valueOf(part_decimal);
//
////                String aux1 = aux.substring(2);
////
//////                if (parts.length > 1) {
//////
//////                    String aux1 = parts[1];
////                if (aux1.length() > 2) {
////
////                    aux = aux1.substring(0, 2);
////                } else {
////                    aux = aux1;
////                }
//                //  }
//                System.out.println("Pate decimal:::: "+aux);
//                System.out.println("Pate tamanho:::: "+aux.length());
//                if (aux.length() < 2) {
//
//                    total = part_inteira + "." + aux;
//
//                    return total;
//                }
//            }
            System.out.println("length::::::::: "+partDecimal.length());
        if (partDecimal.length() < 2) {
//
            partDecimal = partDecimal.concat("0");
            System.out.println("length::::::::: "+partDecimal.length());
        }
        //partDecimal = partDecimal.equals("0")? partDecimal.concat("0"):partDecimal;
        total = part_inteira + "." + partDecimal;
        return total;
        }

        return total;
    }

    public static String executeAlgRSA(String data, String referenciaFactura, String totalFactura, String hashAnterior) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        String dataRegisto = getData(data);
        String dataTime = getDataTime(data);

        referenciaFactura = OrganizarRefFactura.saft(referenciaFactura);
        totalFactura = getTotal(totalFactura);

        String input = null;
        if (hashAnterior != null) {
            input = dataRegisto + ";" + dataTime + ";" + referenciaFactura + ";" + totalFactura + ";" + hashAnterior;
        } else {
            input = dataRegisto + ";" + dataTime + ";" + referenciaFactura + ";" + totalFactura + ";";
        }

        System.out.println("INPUT >>>>> " + input);

        return getHash(input);

    }

    public static String executeAlgRSA1(String data, String hora, String referenciaFactura, String totalFactura, String hashAnterior) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {

        // totalFactura = getTotal(totalFactura);
        String input = null;
        if (hashAnterior != null) {
            if (hashAnterior.trim().isEmpty()) {
                input = data + ";" + data + "T" + hora + ";" + referenciaFactura + ";" + totalFactura;
            } else {
                input = data + ";" + data + "T" + hora + ";" + referenciaFactura + ";" + totalFactura + ";" + hashAnterior;
            }
        } else {
            input = data + ";" + data + "T" + hora + ";" + referenciaFactura + ";" + totalFactura;
        }

        System.out.println(input);

        return getHash(input);

    }

    public static String getValorCaracterHash(String str) {
        return "" + str.charAt(0) + str.charAt(10) + str.charAt(20) + str.charAt(30);
    }

    public static void main(String[] args) throws GeneralSecurityException, OAuthException {
//        //data da Factura +;+data e hora da factura separados po T+;+Numero da factura+;+Total da factura;Hash do documento anterior
//        String hash = getHash("2020-10-05;2020-10-05T04:14:51;NC AGT2020/5;5700.00;VhuGrQOVYJbMzcHCqOYYNPkwYtdGfFKmPmkV72JmvF2MbmmVDsqYhwFUP807KGkz8wKgi1MOuRka+Roed/QYGGEVmZX1KyNgvlHPAC36amSpDqTdlgYKE+XJbHcpAZmmU36GmSYHkEq548Hf+8fhEePGmQyAV+lUxIb4EZ6lyvY=");
//        //INPUT >>>>> 2020-09-24;2020-09-24T09:22:34;PP Z002020/2;0.00;cRnFDSoF1Bweg8r8FpVvafw8J4Ll+MC3ZtuR6GQyc5ApKfIgpcGn/QfCxS8xIytv7aDhYhSgUP5kDzulHUI5532RyCqE39EnKWplQgYywO7sjptZC2S31f2LYy94QMbSKKUXRyrsbydXGzHNwYYSOZIrjG7DJ1ptRaYlJU/6mtQ=
//        System.out.println(hash);

        String referencia = "2020-10-01;2020-10-01T10:59:47;FT 0002021/1;235669.83";

        String hash = getHash(referencia);
        System.out.println("referencia 1 >> " + referencia);
        System.out.println("hash  1>> " + hash);
        System.out.println("====================================================\n");

        String sharam = "KK/WdiuEc24+6wKH1T5jGTPPqoFF4BHuo79WaTTdvLxp/VLUmZwsSmXak8GG1bk2Hch1RDtHSxjD49tqpzDcu+pYQ7DvOF4+gzKv8h/LPvVhdDyC8yYDfyuP1t0ImP+Gl54+Xz5UBSf2JMfkw6bYg2+gD9FXiUXUTUy/5hYBNSU=";
//
        System.out.println("comparacao : " + sharam.trim().equals(hash));
//
//        System.out.println("====================================================\n");
//        referencia = "2020-10-02;2020-10-02T12:58:37;FT 0002021/2;26000.00;" + hash;
//        hash = getHash(referencia);
//        System.out.println("referencia 2 >> " + referencia);
//        System.out.println("hash 2 >> " + hash);
//        System.out.println("====================================================\n");
//
//        sharam = "Xs0imbJOmuo+P2kXAw71yjVZmoZI8j0XpEdCzcIEy2YsU2p/YQigD0YkmcemjDBHUytur758rsD8YmYh+bX5viL9tKy6IcOj0x1P9/Ulvp/XQhjWApxsNqjJs6vEfPDLeAsakoFpZroV5GXBbtYiSR+Jx4dxLyTU//niSCFJQlQ=";
//
//        System.out.println("comparacao : " + sharam.trim().equals(hash));
//
//        referencia = "2020-10-06;2020-10-06T08:13:46;FT 0002021/3;330944.00;" + hash;
//        hash = getHash(referencia);
//        System.out.println("referencia 3 >> " + referencia);
//        System.out.println("hash 3 >> " + hash);
//        System.out.println("====================================================\n");
//
//        sharam = "XMR/Uc0us3i2Jel7QdYoYAVmsJJDo+kC2eKwpBh+QiVv9Z5L6skJHPSE+syMguYR4AisUSW4RlNMXSp1fqftiCFX8j2mVG2B0aAxuoiAfd22K+rR9VhfPNOWL6rHVy6kg/6YNfIfAD96QzR9iuHI3HpehmNTP/AU47dBr6yecM0=";
//
//        System.out.println("comparacao : " + sharam.trim().equals(hash));

    }

}
