/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author celso
 */
public class KeyCodeComponente {

    private static List<Integer> listNumero;

    public static String getValue(KeyEvent evt) {

        addLerta();

        return listNumero.contains(evt.getKeyCode()) ? String.valueOf(evt.getKeyChar()) : "";

    }

    private static void addLerta() {

        listNumero = new ArrayList();
        listNumero.add(KeyEvent.VK_0);
        listNumero.add(KeyEvent.VK_1);
        listNumero.add(KeyEvent.VK_2);
        listNumero.add(KeyEvent.VK_3);
        listNumero.add(KeyEvent.VK_4);
        listNumero.add(KeyEvent.VK_5);
        listNumero.add(KeyEvent.VK_6);
        listNumero.add(KeyEvent.VK_7);
        listNumero.add(KeyEvent.VK_8);
        listNumero.add(KeyEvent.VK_9);

        listNumero.add(KeyEvent.VK_BACK_SPACE);
        listNumero.add(46);

    }

}
