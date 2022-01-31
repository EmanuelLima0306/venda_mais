
package Util;


import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;


public class JComboBoxComponent {
    
     public static void selectItem(JComboBox cbo, Object modelo) {

        DefaultComboBoxModel defaultModelo = (DefaultComboBoxModel) cbo.getModel();
        defaultModelo.setSelectedItem(modelo);
    }
}
