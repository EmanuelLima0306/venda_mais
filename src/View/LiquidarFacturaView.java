/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ClienteController;
import Util.DataComponent;
import Controller.DocumentoController12;
import Controller.FacturaController;
import Controller.FacturaItemController;
import Controller.LiquidarDividaController;
import Controller.MovimentoController;
import Controller.MovimentoItemController;
import Enum.TipoLog;
import Ireport.LiquidarFacturaIreport;
import Ireport.MovimentoIreport;
import Model.ClienteModel;
import Model.EstadoModel;
import Model.FacturaItemModel;
import Model.FacturaModel;
import Model.LiquidarDividaModel;
import Model.Movimento;
import Model.MovimentoItemModel;
import Model.UsuarioModel;
import Util.Calculo;
import Util.LogUtil;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Emanuel Lima
 */
public class LiquidarFacturaView extends javax.swing.JFrame {

    /**
     * Creates new form LiquidarFacturaView
     */
    private UsuarioModel usuario;
    double valorEmFalta = 0.0, valorPago = 0.0, valorFactura = 0.0;
    
    public LiquidarFacturaView() {
        initComponents();
        carregarCliente();
    }

    public LiquidarFacturaView(UsuarioModel usuario) {
        initComponents();
        carregarCliente();
        dataOperacao.setDate(new Date());
        this.usuario = usuario;
    }

    private void carregarCliente() {

        ClienteController controller = new ClienteController();
        List<ClienteModel> lista = controller.get(txtPesquisar.getText());
        cboCliente.setModel(new DefaultComboBoxModel(lista.toArray()));
    }

    private void carregarFactura() {

        FacturaController fController = new FacturaController();
        ClienteModel modelo = (ClienteModel) cboCliente.getSelectedItem();
        List<FacturaModel> lista = fController.getFacturaDebitadaNaoLiquidada(modelo.getId(), txtPesquisarFactura.getText());
        cboFactura.setModel(new DefaultComboBoxModel(lista.toArray()));
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
        txtPesquisar = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cboFactura = new javax.swing.JComboBox<>();
        txtSaldo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnReimprimir = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtValor = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtObs = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cboCliente = new javax.swing.JComboBox<>();
        dataOperacao = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        btnGravar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtValorFactura = new javax.swing.JTextField();
        txtPesquisarFactura = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtValorPago = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtValorEmFalta = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Liquidação de Devidas");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        jLabel7.setText("Saldo do Cliente");

        cboFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFacturaActionPerformed(evt);
            }
        });

        txtSaldo.setEnabled(false);

        jLabel3.setText("Nº Factura");

        btnReimprimir.setText("Reimprimir");
        btnReimprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReimprimirActionPerformed(evt);
            }
        });

        jLabel4.setText("Valor");

        jLabel5.setText("Descrição");

        jLabel6.setText("Data da Operação");

        cboCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboClienteActionPerformed(evt);
            }
        });

        jLabel1.setText("Cliente");

        btnGravar.setText("Gravar");
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });

        jLabel2.setText("Pesquisar");

        jButton2.setText("Lista Debito");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/BANNE.png"))); // NOI18N

        txtValorFactura.setToolTipText("Valor Total Retido na Factura");
        txtValorFactura.setEnabled(false);

        txtPesquisarFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarFacturaKeyReleased(evt);
            }
        });

        jLabel9.setText("Pesquisar");

        txtValorPago.setToolTipText("Valor Pago até o momento");
        txtValorPago.setEnabled(false);

        jLabel10.setText("Total a Pagar");

        jLabel11.setText("Total Pago:");

        txtValorEmFalta.setToolTipText("Valor em Falta para Liquidar a Factura");
        txtValorEmFalta.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(168, 168, 168)
                        .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnReimprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(395, 395, 395)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtValorEmFalta, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtValorPago, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtPesquisarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel10)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataOperacao, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtObs, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cboFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValorFactura, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cboCliente, javax.swing.GroupLayout.Alignment.LEADING, 0, 368, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtPesquisarFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValorFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValorPago, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValorEmFalta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtObs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(dataOperacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReimprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cboClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboClienteActionPerformed
        // TODO add your handling code here:
        carregarFactura();
        FacturaController fController = new FacturaController();
        ClienteModel modelo = (ClienteModel) cboCliente.getSelectedItem();
        txtSaldo.setText(Calculo.converter(modelo.getValorCarteira()));

    }//GEN-LAST:event_cboClienteActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        carregarCliente();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void cboFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFacturaActionPerformed
        // TODO add your handling code here:
//        FacturaController fController = new FacturaController();
        FacturaModel fmodel = (FacturaModel) cboFactura.getSelectedItem();
        valorFactura = fmodel.getTotalApagar();
        txtValorFactura.setText(Calculo.converterCash(valorFactura));
        
        LiquidarDividaController liquidarDividaController = new LiquidarDividaController();
        valorPago = liquidarDividaController.getTotalPagoByIdFactura(fmodel);
        txtValorPago.setText(Calculo.converterCash(valorPago));
        
        valorEmFalta = fmodel.getTotalApagar() - liquidarDividaController.getTotalPagoByIdFactura(fmodel);
        txtValorEmFalta.setText(Calculo.converterCash(valorEmFalta));

    }//GEN-LAST:event_cboFacturaActionPerformed
    public String getNextFacturaSimples(String status, int nFact) {
//        DocumentoController12 docController = new DocumentoController12();
//        int last = docController.getLastInsertAno(status);
//        int next = last;
//        return next + "/"+""+facturaController.getSerie()+"/"+ Data.getAnoActual() +" -REF " + nFact;
        return "Z02"+DataComponent.getAnoActual() + " / " + nFact;
    }

    private void limpar() {

        txtObs.setText("");
        txtSaldo.setText("");
        txtValor.setText("");
        txtValorFactura.setText("");
        txtValorEmFalta.setText("");
        txtValorPago.setText("");
        cboFactura.setModel(new DefaultComboBoxModel());

    }

    private void updateNumeracao() {

        MovimentoController fController = new MovimentoController();
        String designacao, prefixo;

        designacao = "NOTA DEBITO";
        prefixo = "ND ";
        int numFacturaLast = fController.getLastIdByUsuario(usuario);
//        String next = getNextFactura(designacao);
//        System.out.println("next >>>>"+next);
        String nextSimples = getNextFacturaSimples(designacao, numFacturaLast);
        nextSimples = prefixo + nextSimples;

//        Documento modelo = docController.getAll(designacao);
//        modelo.setNext(String.valueOf(numFacturaLast));
        fController.updateNextFactura(nextSimples, numFacturaLast);

        DocumentoController12 docController = new DocumentoController12();
        docController.updateNextNumDoc(designacao);
    }
    
    public double getSaldo()
    {
        if(!txtSaldo.getText().isEmpty())
           return Calculo.getValueNormal(txtSaldo.getText());
        return Calculo.getValueNormal("0");
    }
    public double getValorFactura()
    {
        if(!txtSaldo.getText().isEmpty())
           return Calculo.getValueNormal(txtValorEmFalta.getText());
        return Calculo.getValueNormal("0");
    }
    
    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        // TODO add your handling code here:

        try {

            ClienteModel cliente = (ClienteModel) cboCliente.getSelectedItem();
            FacturaModel factura = (FacturaModel) cboFactura.getSelectedItem();
            factura.setEstado(new EstadoModel(8, ""));
            double valor = Double.parseDouble(txtValor.getText().trim().isEmpty()?"0":txtValor.getText().trim());
            double valorFalta = valorEmFalta > 0? valorEmFalta - valor: 0;
            String obs = txtObs.getText() + " " + factura.getId();
            String data = DataComponent.getDataActual();
            String dataOperacao = DataComponent.getData(this.dataOperacao);
            EstadoModel estado = new EstadoModel( valor + valorPago >= valorFactura?Integer.parseInt("10"):Integer.parseInt("9"), "");
            
            double saldoActual = getSaldo();
            double valorFactura = getValorFactura();

            LiquidarDividaModel modelo = new LiquidarDividaModel(0, obs, valor,valorFalta, cliente, factura, data, dataOperacao, usuario, estado);
            if (!modelo.isEmpty()) {
                LiquidarDividaController controller = new LiquidarDividaController();

                if (valor > 0 && valor <= valorFactura) {
                    if (controller.save(modelo)) {

                        ClienteController clienteController = new ClienteController();

                        saldoActual = saldoActual > 0? saldoActual - valor: saldoActual + valor;
                        cliente.setValorCarteira(saldoActual);
                        if (clienteController.updateCarteira(cliente)) {

                            controller.updateStatus(modelo);
                            JOptionPane.showMessageDialog(this, "Operacao realizada com sucesso");
                            LiquidarFacturaIreport.getById(controller.getLastIdByUsuario(usuario));
                            LogUtil.log.salvarLog(TipoLog.INFO, " Liquidou a factura ( "+modelo.getFactura().getNextFactura()+" ( "+modelo.getValor()+") )");
                            limpar();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Valor a debitar deve ser maior do "
                            + "que zero e menor ou igual ao valor total da factura");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preenche o espaço em branco");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor digitado incorrecto");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnGravarActionPerformed

    public void saveMovimentoItems(Movimento movimento)
    {
        MovimentoItemModel modelo = new MovimentoItemModel();
        modelo.setMovimento(movimento);
        
        MovimentoItemController movimentoItemController = new MovimentoItemController();
        FacturaItemController facturaItemController = new FacturaItemController();
        
        for(FacturaItemModel item : facturaItemController.getItemByIdFactura(movimento.getFactura().getId()))
        {
            modelo.setFacturaItemModel(item);
            movimentoItemController.saveOrUpdate(modelo);
        }
    }
    
    private void btnReimprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReimprimirActionPerformed
        // TODO add your handling code here:
        new BuscarFacturaView().setVisible(true);
    }//GEN-LAST:event_btnReimprimirActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
         new ListaMovimentoView("D").setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtPesquisarFacturaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarFacturaKeyReleased
        // TODO add your handling code here:
        carregarFactura();
    }//GEN-LAST:event_txtPesquisarFacturaKeyReleased

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
            java.util.logging.Logger.getLogger(LiquidarFacturaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LiquidarFacturaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LiquidarFacturaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LiquidarFacturaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LiquidarFacturaView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnReimprimir;
    private javax.swing.JComboBox<String> cboCliente;
    private javax.swing.JComboBox<String> cboFactura;
    private com.toedter.calendar.JDateChooser dataOperacao;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField txtObs;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtPesquisarFactura;
    private javax.swing.JTextField txtSaldo;
    private javax.swing.JTextField txtValor;
    private javax.swing.JTextField txtValorEmFalta;
    private javax.swing.JTextField txtValorFactura;
    private javax.swing.JTextField txtValorPago;
    // End of variables declaration//GEN-END:variables
}
