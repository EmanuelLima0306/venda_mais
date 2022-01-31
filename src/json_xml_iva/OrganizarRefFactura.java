/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package json_xml_iva;

/**
 *
 * @author celso
 */
public class OrganizarRefFactura {

    public static String saft(String referencia) {

        referencia = referencia.trim();

        if (!referencia.isEmpty()) {

            if (referencia.indexOf("-REF") > 0) {

                referencia = getRef(referencia);
                System.out.println("referencia >>>" + referencia);
            }

        }

        return referencia;
    }

    public static String getRef(String referencia) {

        String[] separador = referencia.split("-REF");

        System.out.println("separador.length  >>>" + separador.length);
        if (separador.length > 0) {

            return separador[0];
        }

        return referencia;
    }
}
