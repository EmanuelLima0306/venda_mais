/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.ao.ascemil_tecnologias.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;

/**
 *
 * @author celso
 */
public class ImagemUtil {
 
    
    public static byte [] converterByte(String caminho) throws IOException{
        
        Path path = (Path) FileSystems.getDefault().getPath(caminho);
    
        return Files.readAllBytes( path);
//        return Files.readAllBytes( FileSystems.getDefault().getPath(caminho));
    }
    
    public static BufferedImage mostrarImagem(byte [] imagem) throws IOException{
        
       return ImageIO.read(new ByteArrayInputStream(imagem));
    }
    
}
