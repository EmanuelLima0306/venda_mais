/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ArmazemController;
import Controller.ArmazenamentoController;
import Util.DataComponent;
import Controller.EntradaStockItemController;
import Controller.ProdutoController;
import Ireport.StockIreport;
import Model.ArmazemModel;
import Model.EntradaStockItemModel;
import Model.EntradaStockModel;
import Model.EstadoModel;
import Model.LicencaModel;
import Model.LogModel;
import Model.ProdutoModel;
import Model.UsuarioModel;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author celso
 */
public class ActualizacaoStockView extends javax.swing.JFrame {

    /**
     * Creates new form ActualizacaoStockView
     */
    private UsuarioModel usuario;

    public ActualizacaoStockView() {
        initComponents();

        inicializar();

    }

    ActualizacaoStockView(UsuarioModel usuario) {
        initComponents();
        Armazem();
        produto();
    }

    private void inicializar() {
        Armazem();
        produto();
        limpar();
    }

    private void limpar() {

        txtExistencia.setText("");
        quantidade.setText("1");

    }

    private void Armazem() {

        ArmazemController controller = new ArmazemController();
        cboArmazem.setModel(new DefaultComboBoxModel(controller.get().toArray()));

    }

    private void produto() {

        ArmazemModel armazem = (ArmazemModel) cboArmazem.getSelectedItem();
        ProdutoController controller = new ProdutoController();
        cboProduto.setModel(new DefaultComboBoxModel(controller.get(armazem, txtPesquisar.getText()).toArray()));
    }

    private void lote() {

        ArmazemModel armazem = (ArmazemModel) cboArmazem.getSelectedItem();
        ProdutoModel produto = (ProdutoModel) cboProduto.getSelectedItem();
        EntradaStockItemController controller = new EntradaStockItemController();
        cboLote.setModel(new DefaultComboBoxModel(controller.get(armazem, produto).toArray()));
    }

    private boolean reduzir(List<EntradaStockItemModel> lista, double quantidade, EntradaStockItemController controller) {

        boolean flag = false;
        double qtd = 0;
        for (EntradaStockItemModel m : lista) {

            if (quantidade > 0) {
                if (quantidade - m.getQtd() < 0) {
                    if(m.getQtdTotal() == m.getQtd()){
                        
                        m.setQtdTotal(m.getQtdTotal() - quantidade);
                    }
                    quantidade = (Double) (m.getQtd() - quantidade);
                    m.setQtd(quantidade);

                } else {

                    if(m.getQtdTotal() == m.getQtd()){
                        
                        m.setQtdTotal(quantidade - m.getQtdTotal());
                    }
                    quantidade = quantidade - m.getQtd();
                    m.setQtd(quantidade);
                }

                if (controller.saveActualizacao(m)) {
                    flag = true;
                } else {
                    JOptionPane.showMessageDialog(this, "Ocorreu um erro ao actualizar o stock", "ERRO 02-ASCEMIL/20", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            } else {
                return flag;
            }

        }
        return flag;
    }

    private boolean gravarEntradaItem(double qtd, int isAdd) {
        
        Object[] opcao = {"Qtd Stock", "Qtd Entrada", "Ambos", "Nenhum"};
        int escolha = 0;
        boolean flag = false;
        EntradaStockItemController controller = new EntradaStockItemController();
        EntradaStockItemModel modelo = new EntradaStockItemModel();
        EntradaStockItemModel modeloAux = new EntradaStockItemModel();

        modelo.setProduto((ProdutoModel) cboProduto.getSelectedItem());
        modelo.setArmazem((ArmazemModel) cboArmazem.getSelectedItem());
        modeloAux = controller.getQtdEntradaItem(modelo.getArmazem(), modelo.getProduto(), cboLote.getSelectedItem().toString());
        
        if(modeloAux == null)
            modeloAux = controller.getLastQtdEntradaItem(modelo.getArmazem(), modelo.getProduto(), cboLote.getSelectedItem().toString());
       
        modelo = modeloAux;
        
        if (isAdd == 1) {
            escolha = JOptionPane.showOptionDialog(this, "Onde deseja Aumentar ?\nAs alterações serão feitas na Entrada Activada", " Alerta ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcao, opcao[0]);
            System.out.println("escolha :::::: "+escolha);
            if ( escolha == 0) {
            modelo.setQtd(modelo.getQtd() + qtd);
            }else{
                if(escolha == 1)
                    modelo.setQtdTotal(modelo.getQtdTotal() + qtd);
                else
                    if(escolha == 2){
                        modelo.setQtd(modelo.getQtd() + qtd);
                        modelo.setQtdTotal(modelo.getQtdTotal() + qtd);
                    }
            }
        } else if (isAdd == 2) {
            escolha = JOptionPane.showOptionDialog(this, "Onde deseja Reduzir ? \nAs alterações serão feitas na Entrada Activada", " Alerta ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcao, opcao[0]);
            System.out.println("escolha :::::: "+escolha);
            if ( escolha == 0) {
            modelo.setQtd(modelo.getQtd() - qtd);
            }else{
                if(escolha == 1)
                    modelo.setQtdTotal(modelo.getQtdTotal() - qtd);
                else
                    if(escolha == 2){
                        modelo.setQtd(modelo.getQtd() - qtd);
                        modelo.setQtdTotal(modelo.getQtdTotal() - qtd);
                    }
            }
        } else {
            escolha = JOptionPane.showOptionDialog(this, "Que quantidade deseja Substituir ?\nAs alterações serão feitas na Entrada Activada", " Alerta ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcao, opcao[0]);
            System.out.println("escolha :::::: "+escolha);
            if ( escolha == 0) {
            modelo.setQtd(qtd);
            }else{
                if(escolha == 1)
                    modelo.setQtdTotal(qtd);
                else
                    if(escolha == 2){
                        modelo.setQtd(qtd);
                        modelo.setQtdTotal(qtd);
                    }
            }
            
        }

        if (modelo.getQtd() >= 0) {
            
            modelo.setEstado(new EstadoModel(modelo.getQtd() <= 0 ? 12 : 1, ""));
            if (controller.saveActualizacao(modelo)) {
                flag = true;
            } else {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro ao actualizar o stock", "ERRO 02-ASCEMIL/20", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Quantidade não pode ser menor que zero", "Alerta", JOptionPane.WARNING_MESSAGE);
        }
        return flag;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cboArmazem = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cboProduto = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtExistencia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnAdicinar = new javax.swing.JButton();
        btnReduzir = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnProdutoStock = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cboLote = new javax.swing.JComboBox<>();
        quantidade = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Actualização de Stock");
        setIconImage(new ImageIcon(getClass().getResource("/IMAGUENS/icon.png")).getImage());
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("PesquisarProduto");

        txtPesquisar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtPesquisarCaretUpdate(evt);
            }
        });

        jLabel2.setText("Produto");

        cboArmazem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboArmazemActionPerformed(evt);
            }
        });

        jLabel3.setText("Armazém ( Origem )");

        cboProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProdutoActionPerformed(evt);
            }
        });

        jLabel5.setText("Existência ");

        jLabel6.setText("Nova Existência");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnAdicinar.setBackground(new java.awt.Color(255, 255, 255));
        btnAdicinar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/addd2.png"))); // NOI18N
        btnAdicinar.setText("Adicionar");
        btnAdicinar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicinarActionPerformed(evt);
            }
        });

        btnReduzir.setBackground(new java.awt.Color(255, 255, 255));
        btnReduzir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/redu.png"))); // NOI18N
        btnReduzir.setText("Reduzir");
        btnReduzir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReduzirActionPerformed(evt);
            }
        });

        btnActualizar.setBackground(new java.awt.Color(255, 255, 255));
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/ggg.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnProdutoStock.setBackground(new java.awt.Color(255, 255, 255));
        btnProdutoStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/printer_error_24px.png"))); // NOI18N
        btnProdutoStock.setText("Lista de Existência");
        btnProdutoStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdutoStockActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAdicinar, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnReduzir, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProdutoStock, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btnReduzir, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnProdutoStock, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAdicinar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jLabel4.setText("Lote(Código de Barra)");

        cboLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(2, 2, 2)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtExistencia)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cboProduto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboLote, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPesquisar)
                            .addComponent(cboArmazem, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(30, 30, 30))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboArmazem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cboLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/BANNE.png"))); // NOI18N

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/ddd.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
private void logSistema(LogModel modelo) {

        try {
            ArmazenamentoController<LogModel> ficheiro = new ArmazenamentoController<>("Log_sistema");
            ficheiro.create(modelo);
        } catch (IOException ex) {
            Logger.getLogger(ActualizacaoStockView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void logSistemaErro(LogModel modelo) {

        try {
            ArmazenamentoController<LogModel> ficheiro = new ArmazenamentoController<>("Log_erro");
            ficheiro.create(modelo);
        } 
         catch (IOException ex) {
            Logger.getLogger(ActualizacaoStockView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void btnAdicinarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicinarActionPerformed
        // TODO add your handling code here:

        if (!quantidade.getText().isEmpty()) {
            double qtd = Double.parseDouble(quantidade.getText());
            
            if (gravarEntradaItem(qtd, 1)) {
                ProdutoModel p = (ProdutoModel) cboProduto.getSelectedItem();
//                logSistema(new LogModel("Adicionar quantidade",
//                        p.getDesignacao() + "-Codigo de Barra"
//                        + cboLote.getSelectedItem().toString(),
//                        "",
//                        DataComponent.getDataActual()));
                JOptionPane.showMessageDialog(this, "Operação realizada com sucesso");
                inicializar();
            }
        }
    }//GEN-LAST:event_btnAdicinarActionPerformed

    private void btnReduzirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReduzirActionPerformed
        // TODO add your handling code here:
        if (!quantidade.getText().isEmpty()) {
            double qtd = Double.parseDouble(quantidade.getText());
            if (gravarEntradaItem(qtd, 2)) {

                ProdutoModel p = (ProdutoModel) cboProduto.getSelectedItem();
//                logSistema(new LogModel("Reduzir quantidade",
//                        p.getDesignacao() + "-Codigo de Barra"
//                        + cboLote.getSelectedItem().toString(), usuario.getNome(), DataComponent.getDataActual()));
                JOptionPane.showMessageDialog(this, "Operação realizada com sucesso");
                inicializar();
            }
        }
    }//GEN-LAST:event_btnReduzirActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        if (!quantidade.getText().isEmpty()) {
            double qtd = Double.parseDouble(quantidade.getText());
            if (gravarEntradaItem(qtd, 0)) {
                ProdutoModel p = (ProdutoModel) cboProduto.getSelectedItem();
//                logSistema(new LogModel("Actualizar quantidade",
//                        p.getDesignacao() + "-Codigo de Barra"
//                        + cboLote.getSelectedItem().toString(), usuario.getNome(), DataComponent.getDataActual()));
                JOptionPane.showMessageDialog(this, "Operação realizada com sucesso");
                inicializar();
            }
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void txtPesquisarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtPesquisarCaretUpdate
        txtExistencia.setText("");
        produto();
    }//GEN-LAST:event_txtPesquisarCaretUpdate

    private void cboArmazemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboArmazemActionPerformed
        // TODO add your handling code here:
        produto();
    }//GEN-LAST:event_cboArmazemActionPerformed

    private void cboProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProdutoActionPerformed

        lote();
    }//GEN-LAST:event_cboProdutoActionPerformed

    private void cboLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoteActionPerformed
        // TODO add your handling code here:

        txtExistencia.setText("0");
        EntradaStockItemController controller = new EntradaStockItemController();
        ArmazemModel a = (ArmazemModel) cboArmazem.getSelectedItem();
        ProdutoModel p = (ProdutoModel) cboProduto.getSelectedItem();
        txtExistencia.setText(String.valueOf(controller.getQtd(a, p, cboLote.getSelectedItem().toString())));
    }//GEN-LAST:event_cboLoteActionPerformed

    private void btnProdutoStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdutoStockActionPerformed
        // TODO add your handling code here:
        ArmazemModel armazem = (ArmazemModel) cboArmazem.getSelectedItem();
        StockIreport.ExistenciPorArmazem(armazem.getId());
    }//GEN-LAST:event_btnProdutoStockActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ActualizacaoStockView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ActualizacaoStockView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ActualizacaoStockView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ActualizacaoStockView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ActualizacaoStockView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnAdicinar;
    private javax.swing.JButton btnProdutoStock;
    private javax.swing.JButton btnReduzir;
    private javax.swing.JComboBox<String> cboArmazem;
    private javax.swing.JComboBox<String> cboLote;
    private javax.swing.JComboBox<String> cboProduto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField quantidade;
    private javax.swing.JTextField txtExistencia;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
