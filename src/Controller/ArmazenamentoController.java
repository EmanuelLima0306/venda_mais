package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ArmazenamentoController<T> {

    private File arquivo;

    public ArmazenamentoController(String nomeArquivo) throws IOException {

        arquivo = new File("Configuracao" + File.separator + nomeArquivo.concat(".config"));
//        String comando = "C:\\WINDOWS\\System32\\ATTRIB.EXE +H "+nomeArquivo.concat(".config");
//
//        Runtime.getRuntime().exec(comando);
//        Runtime.getRuntime().exec("cmd /c attrib +h +s +r C:/Configuracao");
        if (!arquivo.exists()) {

            try {
                arquivo.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ArmazenamentoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public ArmazenamentoController(File arquivo) throws IOException {

        this.arquivo = arquivo;
//        String comando = "C:\\WINDOWS\\System32\\ATTRIB.EXE +H "+nomeArquivo.concat(".config");
//
//        Runtime.getRuntime().exec(comando);
//        Runtime.getRuntime().exec("cmd /c attrib +h +s +r C:/Configuracao");
        if (!arquivo.exists()) {

            try {
                arquivo.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(ArmazenamentoController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean removerArquivo() {

        return arquivo.delete();

    }

    /*
     OBJECTIVO: Verificar se o Objecto existe no arquivo binario
     RECEBE: Obejecto(Classe)
     RETORNA: Boolean(Verdadeiro ou Falso)
     */
    private boolean isExist(T obj) throws StreamCorruptedException {

        List<T> lista = getAll();
        return lista.contains(obj);
    }

    /*
     OBJECTIVO: Escrever no Arquivo Binario
     RECEBE: Lista de Objecto(Classe)
     RETORNA: Boolean(Verdadeiro ou Falso)
     */
    public boolean escreverFile(List<T> lista) {
        try {

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(arquivo));//Instancia do Objecto
            out.writeObject(lista);//Escreve no arquivo
            out.close();//Fecha o Arquivo
            return true;

        } catch (IOException ex) {
            Logger.getLogger(ArmazenamentoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /*
     OBJECTIVO: Adicionar  um Objecto  do Arquivo Binario
     RECEBE: Objecto(Classe)
     RETORNA: Boolean(Verdadeiro ou Falso)
     */
    public boolean create(T obj) throws StreamCorruptedException {

        if (!isExist(obj)) {

            List<T> lista = getAll();
            lista.add(obj);
            return escreverFile(lista);
        }
        return false;
    }

    /*
     OBJECTIVO: remover  um Objecto  do Arquivo Binario
     RECEBE: Obejecto(Classe)
     RETORNA: Boolean(Verdadeiro ou Falso)
     */
    public boolean delete(T obj) throws StreamCorruptedException {

        List<T> lista = (List<T>) getAll();

        int index = (Integer) lista.indexOf(obj);
        lista.remove(index);
        return escreverFile(lista);

    }

    public boolean delete(int index) throws StreamCorruptedException {

        List<T> lista = (List<T>) getAll();

        // int index = (Integer)lista.indexOf(obj);
        lista.remove(index);
        return escreverFile(lista);

    }


    /*
     OBJECTIVO: remover  um Objecto  do Arquivo Binario
     RECEBE: Obejecto(Classe)
     RETORNA: Boolean(Verdadeiro ou Falso)
     */
    public boolean actualizar(T obj, int index) throws StreamCorruptedException {

        List<T> lista = getAll();
        lista.set(index, obj);
        return escreverFile(lista);
        // lista.set(obj);

    }

    public List<T> getAll() throws StreamCorruptedException {
        System.out.println("ARQUIVO:::::: "+arquivo);
        List<T> lista = new ArrayList<>();
        if (arquivo.length() > 0) {

            try {
                ObjectInputStream input = new ObjectInputStream(new FileInputStream(arquivo));
                lista = (List<T>) input.readObject();
                input.close();
            } catch (IOException ex) {
                Logger.getLogger(ArmazenamentoController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ArmazenamentoController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return (List<T>) lista;
    }
}
