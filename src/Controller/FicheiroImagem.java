package PointSoftGESCHOOL.Entity;

import java.awt.HeadlessException;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FicheiroImagem {

    private static String buscarFoto(JDialog tela, JLabel fotoLB) {

        String foto = null;
        try {

            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG & GIF Images", "jpg", "gif");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(tela);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                String pathDestino = f.getAbsolutePath();

                String urlImagem = chooser.getSelectedFile().getName();
                System.out.println("foto   " + pathDestino);
                ImageIcon image = new ImageIcon(pathDestino);

                fotoLB.setIcon(new ImageIcon(image.getImage().getScaledInstance(fotoLB.getWidth(), fotoLB.getHeight(), Image.SCALE_DEFAULT)));
//
                foto = f.getName();
                FicheiroGenericoComponet.copiarArquivos(f, new File("relatorios" + File.separator + "fotos" + File.separator + "" + f.getName()));
                foto = f.getName();
            }
        } catch (HeadlessException ex) {
            ex.printStackTrace();
            showMessageDialog(null, "Formato de imagem ou cor nao suportado!");
        } catch (IOException ex) {
            Logger.getLogger(FicheiroImagem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return foto;
    }

    private void buscarFicheiros(JDialog tela) {

//        try {
//
//            JFileChooser chooser = new JFileChooser();
////	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
////                "JPG & GIF Images", "jpg", "gif");
////	    chooser.setFileFilter(filter);
//            int returnVal = chooser.showOpenDialog(tela);
//
//            if (returnVal == JFileChooser.APPROVE_OPTION) {
//                File f = chooser.getSelectedFile();
//                String pathDestino = f.getAbsolutePath();
//
//                String urlImagem = chooser.getSelectedFile().getName();
//                System.out.println("foto   " + pathDestino);
//                ImageIcon image = new ImageIcon(pathDestino);
//
////		fotoLB.setIcon(new ImageIcon(image.getImage().getScaledInstance(fotoLB.getWidth(), fotoLB.getHeight(), Image.SCALE_DEFAULT)));
////
//                
//                try {
//                    CopFileUtil.copiarArquivos(f, new File("relatorios\\anexos\\" + f.getName()));
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
//        } catch (HeadlessException ex) {
//            ex.printStackTrace();
//            showMessageDialog(null, "Formato de imagem ou cor nao suportado!");
//        }
    }

}
