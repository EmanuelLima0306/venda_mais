/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Controller.ArmazenamentoController;
import Controller.ConnectionFactoryController;
import Model.ConnectioFactoryModel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author celso
 */
public class CopySecury {

    private static ConnectioFactoryModel modelo;

    private static String criarPasta() {

        File arquivo = new File("Backup/mysqldump");
        arquivo.mkdirs();
        return arquivo.getAbsolutePath() + "/backup.sh";
    }

    private static File criarArquivo() {

        try {

            File arquivo = new File("Backup/mysqldump/" + DataComponent.getDataBackup() + ".sql");

            if (!arquivo.exists()) {

                arquivo.createNewFile();

            }

            return arquivo;

        } catch (IOException ex) {
            Logger.getLogger(CopySecury.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static File criarArquivo(String nome) {

        try {

            File arquivo = new File("Backup/mysqldump/" + nome + "_" + DataComponent.getDataBackup() + ".sql");

            if (!arquivo.exists()) {

                arquivo.createNewFile();

            }

            return arquivo;

        } catch (IOException ex) {
            Logger.getLogger(CopySecury.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void backup() throws IOException {

        Runtime runtime = Runtime.getRuntime();

        try {
            ArmazenamentoController ficheiro = new ArmazenamentoController<ConnectioFactoryModel>("Connection");
            List<ConnectioFactoryModel> lista = ficheiro.getAll();
            if (lista != null) {

                modelo = lista.get(0);
            }
        } catch (IOException ex) {
            Logger.getLogger(CopySecury.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (modelo != null) {

            criarPasta();
            File arquivo = criarArquivo();
            Process process = runtime.exec("mysqldump --user=" + modelo.getUser() + " --password=" + modelo.getPassword() + " --opt  grest");
            InputStreamReader reader = new InputStreamReader(process.getInputStream());
            BufferedReader buffer = new BufferedReader(reader);
            String line;

            FileWriter fw = new FileWriter(arquivo, true);
//        BufferedWriter bw = new BufferedWriter(fw);
            while ((line = buffer.readLine()) != null) {
                System.out.println(line + "\n");
                fw.write(line + "\n");

//            fw.write("\n");
            }
            fw.close();
        }
    }

    public static void backup(File arquivo) throws IOException {

        Runtime runtime = Runtime.getRuntime();

        try {
            ArmazenamentoController ficheiro = new ArmazenamentoController<ConnectioFactoryModel>("Connection");
            List<ConnectioFactoryModel> lista = ficheiro.getAll();
            if (lista != null) {

                modelo = lista.get(0);
            }
        } catch (IOException ex) {
            Logger.getLogger(CopySecury.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (modelo != null) {

            Process process = runtime.exec("mysqldump --user=" + modelo.getUser() + " --password=" + modelo.getPassword() + " --opt  grest");
            InputStreamReader reader = new InputStreamReader(process.getInputStream());
            BufferedReader buffer = new BufferedReader(reader);
            String line;
            String append = "";

            FileWriter fw = new FileWriter(arquivo, true);

            while ((line = buffer.readLine()) != null) {

                fw.write(line + "\n");

            }

            fw.close();
        }

    }

    public static void backup(String baseDado, String user, String password) throws IOException {

        Runtime runtime = Runtime.getRuntime();

        criarPasta();
        File arquivo = criarArquivo();

        Process process = runtime.exec("mysqldump --user=" + user + " --password=" + password + " --opt  " + baseDado);
        InputStreamReader reader = new InputStreamReader(process.getInputStream());
        BufferedReader buffer = new BufferedReader(reader);
        String line;

        FileWriter fw = new FileWriter(arquivo);
        while ((line = buffer.readLine()) != null) {
            fw.write(line + "\n");
        }

    }

    public static void backup(String baseDado, String user, String password, String nomeAquivo) throws IOException {

        Runtime runtime = Runtime.getRuntime();

        criarPasta();
        File arquivo = criarArquivo(nomeAquivo);

        Process process = runtime.exec("mysqldump --user=" + user + " --password=" + password + " --opt  " + baseDado);
        InputStreamReader reader = new InputStreamReader(process.getInputStream());
        BufferedReader buffer = new BufferedReader(reader);
        String line;

        FileWriter fw = new FileWriter(arquivo);
        while ((line = buffer.readLine()) != null) {
            fw.write(line + "\n");
        }

    }

    public static void backupJFrame() {

        JFileChooser fileChooser = new JFileChooser();
        try {
            String arquivo, arquivo2, arquivo3 = null;
            fileChooser.setVisible(true);       //  JFileChooser , criado automaticamente no Netbeans            
            int result = fileChooser.showSaveDialog(null);
            if (result == fileChooser.APPROVE_OPTION) {
                arquivo = fileChooser.getSelectedFile().toString().concat(".sql");
                File file = new File(arquivo);
//                File file2 = new File(arquivo2);
                if (file.exists()) {
                    Object[] options = {"Sim", "Não"};
                    int opcao = JOptionPane.showOptionDialog(null, "Este arquivo já existe. Quer sobreescrever este arquivo?", "Atenção!!!",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if (opcao == JOptionPane.YES_OPTION) {
                        backup(file);
                        JOptionPane.showMessageDialog(null, "Ficheiro salvo com sucesso", "Tudo OK!", 1);
                    }
                } else {
                    backup(file);
                    JOptionPane.showMessageDialog(null, "Ficheiro salvo com sucesso. ", "Tudo OK!", 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e, "Erro!", 2);
        }
    }
}
