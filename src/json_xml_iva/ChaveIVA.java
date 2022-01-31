/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json_xml_iva;

import iva.*;

/**
 *
 * @author celso
 */
public class ChaveIVA {
    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDH7RAcCUUNT8yfHFunVruNstVi\n" +
"dc1oIYo2mnIPGftVbEOxtUN803vHLaSQCii3/4mPii0znJFDXskQZRrs4Igzr6u0\n" +
"+fwk0VqvkDpwx1nEnMd793xjvVnF0VlSSFNe1z0HyNerB6TwNMxmOK4WpNWCUoJQ\n" +
"KZHcpRAxpeS5dkJFMwIDAQAB" +
//    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCbio5XAi2/VBqa+vZWegzotioR" +
//"DjVRQGaVmoh9ducT00HS9d3E0gpBqUaNjVDfv2WYGhGzrW2BpoNBtKlZOP6x7cUJ" +
//"dzhWQrgz6/cVGQOBZXaK9aoQaZpMlns0pXWwd0apcDuzPwn2qzBstE8Zeb9GOQqQ" +
"2h4Zbs27E+h1qE+S5QIDAQAB";
    public static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJuKjlcCLb9UGpr6\n" +
"9lZ6DOi2KhEONVFAZpWaiH125xPTQdL13cTSCkGpRo2NUN+/ZZgaEbOtbYGmg0G0\n" +
"qVk4/rHtxQl3OFZCuDPr9xUZA4Fldor1qhBpmkyWezSldbB3RqlwO7M/CfarMGy0\n" +
"Txl5v0Y5CpDaHhluzbsT6HWoT5LlAgMBAAECgYAQJyV9F7bUhdF1gtaOSIKKsFBS\n" +
"ER2sYHnG05OVJAH/ZxMQ64Oqav2xr/ipvqFFe0T7tMld1YbnzfkXm8FyiJ66hjpu\n" +
"DbvLtfRhZM9k3TJKXeZkeIaEJJ97ZxzEQC+EfSkDsyi3Nn6zfhV/ceIGB/62nf4n\n" +
"deVxn2dov+UfDoEOQQJBAMiZjeVPE46AMIcJjxBzMfSWtIItfWvppRCTaQ+257ao\n" +
"k4mGykiuT1CqMZ3yS1p2bd5dy+W7thzwvk6ozoLpoekCQQDGf1ncnjRFB12MKsVt\n" +
"oFmqK1o3MSu8VMzjjr/jihlyP7e1pH8SRU/CTSaSzyeROgCTERGI2ZEaAWRPXyM1\n" +
"8a+dAkALYJIosEx2p5SZBBTGJRJvQeDpBTV42l6PSx0JVCFePb9obGmqp6A9/fkk\n" +
"cSqO7eqbUwyOchAJIipZAb/8ss2JAkATl9yB52fXbHuya0JjqNFQ98iG7CaaB3DW\n" +
"AXA1gJs0aM+0cVFNt2PBFSZ6lVIdhrEp0yR88qTdAUgqgYSTPZENAkAD45tM1xAn\n" +
"MsBxML3sXMQ8exkRg0hghZsDXSV8yfgnAodz4d+xuPgANq4humH3OpZPNWDxpyZo\n" +
"SpEyHqxiBJ7x";
//    public static final String PRIVATE_KEY = "MIICWwIBAAKBgQCbio5XAi2/VBqa+vZWegzotioRDjVRQGaVmoh9ducT00HS9d3E" +
//"0gpBqUaNjVDfv2WYGhGzrW2BpoNBtKlZOP6x7cUJdzhWQrgz6/cVGQOBZXaK9aoQ" +
//"aZpMlns0pXWwd0apcDuzPwn2qzBstE8Zeb9GOQqQ2h4Zbs27E+h1qE+S5QIDAQAB" +
//"AoGAECclfRe21IXRdYLWjkiCirBQUhEdrGB5xtOTlSQB/2cTEOuDqmr9sa/4qb6h" +
//"RXtE+7TJXdWG5835F5vBcoieuoY6bg27y7X0YWTPZN0ySl3mZHiGhCSfe2ccxEAv" +
//"hH0pA7MotzZ+s34Vf3HiBgf+tp3+J3XlcZ9naL/lHw6BDkECQQDImY3lTxOOgDCH" +
//"CY8QczH0lrSCLX1r6aUQk2kPtue2qJOJhspIrk9QqjGd8ktadm3eXcvlu7Yc8L5O" +
//"qM6C6aHpAkEAxn9Z3J40RQddjCrFbaBZqitaNzErvFTM446/44oZcj+3taR/EkVP" +
//"wk0mks8nkToAkxERiNmRGgFkT18jNfGvnQJAC2CSKLBMdqeUmQQUxiUSb0Hg6QU1" +
//"eNpej0sdCVQhXj2/aGxpqqegPf35JHEqju3qm1MMjnIQCSIqWQG//LLNiQJAE5fc" +
//"gedn12x7smtCY6jRUPfIhuwmmgdw1gFwNYCbNGjPtHFRTbdjwRUmepVSHYaxKdMk" +
//"fPKk3QFIKoGEkz2RDQJAA+ObTNcQJzLAcTC97FzEPHsZEYNIYIWbA10lfMn4JwKH" +
//"c+Hfsbj4ADauIbph9zqWTzVg8acmaEqRMh6sYgSe8Q==";
}
