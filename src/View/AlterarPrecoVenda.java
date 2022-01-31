/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.EntradaStockItemController;
import Controller.ProdutoController;
import Model.EntradaStockItemModel;
import Model.ProdutoModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author celso
 */
public class AlterarPrecoVenda extends javax.swing.JFrame {

    /**
     * Creates new form AlterarDataExpiracao
     */
    public AlterarPrecoVenda() {
        initComponents();
        produto("");
    }

    private void produto(String pesquisa) {

        ProdutoController controller = new ProdutoController();
        cboProduto.setModel(new DefaultComboBoxModel(controller.getProdutos(pesquisa).toArray()));
    }

    private void lote() {

        ProdutoModel produto = (ProdutoModel) cboProduto.getSelectedItem();
        EntradaStockItemController controller = new EntradaStockItemController();
        cboCodigoBarra.setModel(new DefaultComboBoxModel(controller.getLoteCodigoBarra(produto.getId()).toArray()));
    }

    private void lotePrecoVenda() {

        ProdutoModel produto = (ProdutoModel) cboProduto.getSelectedItem();
        EntradaStockItemController controller = new EntradaStockItemController();
        double precoVenda = controller.getLotePrecoVenda(produto.getId(), cboCodigoBarra.getSelectedItem().toString()).getPrecoVenda();
        txtPrecoVenda.setText(String.valueOf(precoVenda));

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
        cboProduto = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cboCodigoBarra = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btnGravar = new javax.swing.JButton();
        txtPrecoVenda = new javax.swing.JTextField();
        txtPesquisar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setTitle("Alterar Preço");
        setIconImage(new ImageIcon(getClass().getResource("/IMAGUENS/icon.png")).getImage());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Produto");

        cboProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProdutoActionPerformed(evt);
            }
        });

        jLabel2.setText("Código de Barra");

        cboCodigoBarra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCodigoBarraActionPerformed(evt);
            }
        });

        jLabel3.setText("Preço de Venda");

        btnGravar.setBackground(new java.awt.Color(255, 255, 255));
        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red.png"))); // NOI18N
        btnGravar.setText("Gravar");
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        jLabel4.setText("Pesquisar");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cboProduto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtPrecoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 213, Short.MAX_VALUE))
                    .addComponent(cboCodigoBarra, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboCodigoBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGravar)
                    .addComponent(txtPrecoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/BANNE.png"))); // NOI18N

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/ddd.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cboProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProdutoActionPerformed
        // TODO add your handling code here:
        ProdutoModel pModelo = (ProdutoModel) cboProduto.getSelectedItem();

        txtPrecoVenda.setText(String.valueOf(pModelo.getValorVenda()));

        lote();

    }//GEN-LAST:event_cboProdutoActionPerformed

    private void cboCodigoBarraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCodigoBarraActionPerformed
        // TODO add your handling code here:
        if (cboCodigoBarra.getSelectedIndex() >= 0) {
            lotePrecoVenda();
        }
    }//GEN-LAST:event_cboCodigoBarraActionPerformed
private void limpar(){
    
    txtPrecoVenda.setText("0");
}
    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        // TODO add your handling code here:

        EntradaStockItemController itemController = new EntradaStockItemController();
        EntradaStockItemModel itemModelo = new EntradaStockItemModel();
        //itemModelo.setPrecoVenda(Double.parseDouble(txtPrecoVenda.getText()));
        boolean result = false;

        if (cboCodigoBarra.getSelectedIndex() >= 0) {
            itemModelo.setCodBarra(cboCodigoBarra.getSelectedItem().toString());
            itemModelo.setPrecoVenda(Double.parseDouble(txtPrecoVenda.getText()));
            result = itemController.updatePreco(itemModelo);
        } else {

            ProdutoController pController = new ProdutoController();
            ProdutoModel pModelo = (ProdutoModel) cboProduto.getSelectedItem();
            pModelo.setValorVenda(Double.parseDouble(txtPrecoVenda.getText()));
            result = pController.updatePrecoVenda(pModelo);
        }

        if (result) {
            limpar();
            JOptionPane.showMessageDialog(this, "Preço de Venda alterado com sucesso");
        } else {
            JOptionPane.showMessageDialog(this, "Preço de Venda não foi alterado ", "ERRO", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnGravarActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        produto(txtPesquisar.getText());
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
            java.util.logging.Logger.getLogger(AlterarPrecoVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AlterarPrecoVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AlterarPrecoVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AlterarPrecoVenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AlterarPrecoVenda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGravar;
    private javax.swing.JComboBox<String> cboCodigoBarra;
    private javax.swing.JComboBox<String> cboProduto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtPrecoVenda;
    // End of variables declaration//GEN-END:variables
}