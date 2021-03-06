/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ArmazemController;
import Controller.EntradaStockItemController;
import Controller.ProdutoController;
import Model.ArmazemModel;
import Model.EntradaStockItemModel;
import Model.ProdutoModel;
import Model.UsuarioModel;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author celso
 */
public class TransferenciaProdutoView extends javax.swing.JFrame {

    /**
     * Creates new form ActualizacaoStockView
     */
    public TransferenciaProdutoView() {
        initComponents();
    }

    TransferenciaProdutoView(UsuarioModel usuario) {

        initComponents();
        inicializar();
    }

    private void inicializar() {
        Armazem();
        produto();
        limpar();
        ArmazemDestino();
    }

    private void limpar() {

        txtExistencia.setText("");
        quantidade.setValue(1);

    }

    private void Armazem() {

        ArmazemController controller = new ArmazemController();
        cboArmazem.setModel(new DefaultComboBoxModel(controller.get().toArray()));

    }

    private void ArmazemDestino() {

        ArmazemModel armazem = (ArmazemModel) cboArmazem.getSelectedItem();
        ArmazemController controller = new ArmazemController();
        cboArmazemDestino.setModel(new DefaultComboBoxModel(controller.get(armazem.getDesignacao()).toArray()));

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
        jLabel4 = new javax.swing.JLabel();
        cboArmazemDestino = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtExistencia = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        quantidade = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        btnGravar = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        cboLote = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Transfer??ncia de Produto");
        setIconImage(new ImageIcon(getClass().getResource("/IMAGUENS/icon.png")).getImage());
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Pesquisar Produto");

        txtPesquisar.setForeground(new java.awt.Color(17, 13, 13));
        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        jLabel2.setText("Produto");

        cboArmazem.setForeground(new java.awt.Color(255, 255, 255));
        cboArmazem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboArmazemActionPerformed(evt);
            }
        });

        jLabel3.setText("Armaz??m ( Origem )");

        cboProduto.setForeground(new java.awt.Color(255, 255, 255));
        cboProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProdutoActionPerformed(evt);
            }
        });

        jLabel4.setText("Armaz??m ( Destino )");

        cboArmazemDestino.setForeground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("Exist??ncia ");

        txtExistencia.setEditable(false);

        jLabel6.setText("Exist??ncia Tranferir");

        quantidade.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setForeground(new java.awt.Color(255, 255, 255));

        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red.png"))); // NOI18N
        btnGravar.setText("Gravar");
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/relact_1.png"))); // NOI18N
        jButton4.setText("L.Transfer??ncia");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(281, Short.MAX_VALUE)
                .addComponent(jButton4)
                .addGap(18, 18, 18)
                .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setText("Lote(C??digo de Barra)");

        cboLote.setForeground(new java.awt.Color(255, 255, 255));
        cboLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboLoteActionPerformed(evt);
            }
        });

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/ddd.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(quantidade))
                            .addComponent(cboArmazemDestino, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboLote, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboProduto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboArmazem, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
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
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(cboLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cboArmazemDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtExistencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/BANNE.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cboLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboLoteActionPerformed
        // TODO add your handling code here:

        txtExistencia.setText("0");
        EntradaStockItemController controller = new EntradaStockItemController();
        ArmazemModel a = (ArmazemModel) cboArmazem.getSelectedItem();
        ProdutoModel p = (ProdutoModel) cboProduto.getSelectedItem();
        txtExistencia.setText(String.valueOf(controller.getQtd(a, p, cboLote.getSelectedItem().toString())));
    }//GEN-LAST:event_cboLoteActionPerformed

    private void cboArmazemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboArmazemActionPerformed
        // TODO add your handling code here:
        ArmazemDestino();
        produto();
    }//GEN-LAST:event_cboArmazemActionPerformed

    private void cboProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProdutoActionPerformed
        // TODO add your handling code here:
        lote();
    }//GEN-LAST:event_cboProdutoActionPerformed
    private boolean reduzir(List<EntradaStockItemModel> lista, double quantidade, EntradaStockItemController controller) {

        boolean flag = false;
        double qtd = 0;
        for (EntradaStockItemModel m : lista) {

            if (quantidade > 0) {
                if (quantidade - m.getQtd() < 0) {

                    quantidade = m.getQtd() - quantidade;
                    m.setQtd(quantidade);

                } else {

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
private void limparCampo(){
    
    quantidade.setValue(1);
    txtExistencia.setText("0");
    txtPesquisar.setText("");
}
    private void gravarEntradaStock() {
        // TODO add your handling code here:
        EntradaStockItemController controller = new EntradaStockItemController();
        EntradaStockItemModel modelo = new EntradaStockItemModel();

        modelo.setProduto((ProdutoModel) cboProduto.getSelectedItem());
        modelo.setArmazem((ArmazemModel) cboArmazem.getSelectedItem());

        List<EntradaStockItemModel> lista = controller.getIdEntradaItem2(modelo.getArmazem(), modelo.getProduto(), cboLote.getSelectedItem().toString());
        if (lista.size() > 0) {

            EntradaStockItemModel modelo1 = lista.get(0);
            modelo1.setProduto(modelo.getProduto());
            modelo1.setArmazem((ArmazemModel) cboArmazemDestino.getSelectedItem());
            if (Double.parseDouble(txtExistencia.getText()) >= Double.parseDouble(quantidade.getValue().toString())) {
                if (reduzir(lista, Double.parseDouble(quantidade.getValue().toString()), controller)) {

                    modelo1.setQtd(Double.parseDouble(quantidade.getValue().toString()));

                    if (controller.save(modelo1)) {
                        limparCampo();
                        JOptionPane.showMessageDialog(this, "Opera????o realizada com sucesso");
                    }

                }
            } else {
                JOptionPane.showMessageDialog(this, "A quantidade a transferir deve ser menor ou igual", "Alerta", JOptionPane.WARNING_MESSAGE);
            }
        }

    }
    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        gravarEntradaStock();
    }//GEN-LAST:event_btnGravarActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        produto();
    }//GEN-LAST:event_txtPesquisarKeyReleased

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
            java.util.logging.Logger.getLogger(TransferenciaProdutoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TransferenciaProdutoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TransferenciaProdutoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TransferenciaProdutoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransferenciaProdutoView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGravar;
    private javax.swing.JComboBox<String> cboArmazem;
    private javax.swing.JComboBox<String> cboArmazemDestino;
    private javax.swing.JComboBox<String> cboLote;
    private javax.swing.JComboBox<String> cboProduto;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSpinner quantidade;
    private javax.swing.JTextField txtExistencia;
    private javax.swing.JTextField txtPesquisar;
    // End of variables declaration//GEN-END:variables
}
