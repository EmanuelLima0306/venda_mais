/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ArmazenamentoController;
import Controller.CategoriaABController;
import Controller.ClienteController;
import Util.DataComponent;
import Controller.FabricanteController;
import Controller.FacturaController;
import Controller.FormaPagamentoController;
import Controller.ProdutoController;
import Controller.UsuarioController;
import Ireport.FacturaIreport;
import Model.CategoriaModel;
import Model.ClienteModel;
import Model.FabricanteModel;
import Model.FacturaModel;
import Model.FormaPagamentoModel;
import Model.LogModel;
import Model.ProdutoModel;
import Model.UsuarioModel;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

/**
 *
 * @author celso
 */
public class FacturacaoView extends javax.swing.JFrame {

    /**
     * Creates new form ActualizacaoStockView
     */
    private UsuarioModel usuario;

    public FacturacaoView() {
        initComponents();
        //data2.setMaxSelectableDate(new Date());
        data1.setMaxSelectableDate(new Date());

    }

    FacturacaoView(UsuarioModel usuario) {
        initComponents();
        this.usuario = usuario;
        inicializar();
        //data2.setMaxSelectableDate(new Date());
        data1.setMaxSelectableDate(new Date());
    }

    private void inicializar() {

        produto();
        categoria();
        formaPagamento();
        cliente();
        fabricante();
        usuario();

    }

    private void enableChk() {

        desableChk();
        cboUsuario.setEnabled(rdUsuario.isSelected());
        cboCategoria.setEnabled(rdCategoria.isSelected());
        cboCliente.setEnabled(rdCliente.isSelected());
        cboFabricante.setEnabled(rdFabricante.isSelected());
        cboFormaPagamento.setEnabled(rdFormaPagamento.isSelected());
        cboProduto.setEnabled(rdProduto.isSelected());

    }

    private void desableChk() {

        cboUsuario.setEnabled(false);
        cboCategoria.setEnabled(false);
        cboCliente.setEnabled(false);
        cboFabricante.setEnabled(false);
        cboFormaPagamento.setEnabled(false);
        cboProduto.setEnabled(false);

    }

    private void usuario() {

        UsuarioController controller = new UsuarioController();
        cboUsuario.setModel(new DefaultComboBoxModel(controller.get().toArray()));

    }

    private void fabricante() {

        FabricanteController controller = new FabricanteController();
        cboFabricante.setModel(new DefaultComboBoxModel(controller.get().toArray()));

    }

    private void produto() {

        ProdutoController controller = new ProdutoController();
        cboProduto.setModel(new DefaultComboBoxModel(controller.get().toArray()));
    }

    private void categoria() {

        CategoriaABController controller = new CategoriaABController();
        cboCategoria.setModel(new DefaultComboBoxModel(controller.get().toArray()));

    }

    private void cliente() {

        ClienteController controller = new ClienteController();
        List<ClienteModel> lista = controller.getClienteFactura();

        cboCliente.setModel(new DefaultComboBoxModel(lista.toArray()));
    }

    private void formaPagamento() {

        FormaPagamentoController controller = new FormaPagamentoController();
        cboFormaPagamento.setModel(new DefaultComboBoxModel(controller.get().toArray()));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel5 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        data1 = new com.toedter.calendar.JDateChooser();
        data2 = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        rdUsuario = new javax.swing.JRadioButton();
        rdFormaPagamento = new javax.swing.JRadioButton();
        rdCliente = new javax.swing.JRadioButton();
        rdProduto = new javax.swing.JRadioButton();
        rdCategoria = new javax.swing.JRadioButton();
        rdNenhum = new javax.swing.JRadioButton();
        rdFabricante = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        cboUsuario = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cboFormaPagamento = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        cboCliente = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cboProduto = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cboCategoria = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cboFabricante = new javax.swing.JComboBox<>();
        btnPdf = new javax.swing.JButton();
        btnExecel = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        rdDetalhada = new javax.swing.JRadioButton();
        rdSintetica = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();

        jLabel5.setText("jLabel5");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lista de Factura????o");
        setIconImage(new ImageIcon(getClass().getResource("/IMAGUENS/icon.png")).getImage());
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Periodo de Factura????o"));

        jLabel1.setText("De");

        jLabel2.setText("??te");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(data1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(data2, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(data2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(data1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Modo de Pesquisa"));

        rdUsuario.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdUsuario);
        rdUsuario.setText("Usu??rio");
        rdUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdUsuarioActionPerformed(evt);
            }
        });

        rdFormaPagamento.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdFormaPagamento);
        rdFormaPagamento.setText("Forma de Pagamento");
        rdFormaPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdFormaPagamentoActionPerformed(evt);
            }
        });

        rdCliente.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdCliente);
        rdCliente.setText("Cliente");
        rdCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdClienteActionPerformed(evt);
            }
        });

        rdProduto.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdProduto);
        rdProduto.setText("Produto");
        rdProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdProdutoActionPerformed(evt);
            }
        });

        rdCategoria.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdCategoria);
        rdCategoria.setText("Categoria");
        rdCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdCategoriaActionPerformed(evt);
            }
        });

        rdNenhum.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdNenhum);
        rdNenhum.setSelected(true);
        rdNenhum.setText("Nenhum");
        rdNenhum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdNenhumActionPerformed(evt);
            }
        });

        rdFabricante.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdFabricante);
        rdFabricante.setText("Fabricante");
        rdFabricante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdFabricanteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdFormaPagamento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rdProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdCategoria)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdFabricante)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 62, Short.MAX_VALUE)
                .addComponent(rdNenhum)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdUsuario)
                    .addComponent(rdFormaPagamento)
                    .addComponent(rdCliente)
                    .addComponent(rdProduto)
                    .addComponent(rdCategoria)
                    .addComponent(rdNenhum)
                    .addComponent(rdFabricante))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("Usu??rio");

        cboUsuario.setEnabled(false);

        jLabel4.setText("Forma de Pagamento");

        cboFormaPagamento.setEnabled(false);

        jLabel6.setText("Cliente");

        cboCliente.setEnabled(false);

        jLabel9.setText("Produto");

        cboProduto.setEnabled(false);

        jLabel10.setText("Categoria");

        cboCategoria.setEnabled(false);
        cboCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboCategoriaActionPerformed(evt);
            }
        });

        jLabel11.setText("Fabricante");

        cboFabricante.setEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel10))
                            .addGap(169, 169, 169))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                            .addComponent(cboUsuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(34, 34, 34)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboFormaPagamento, 0, 188, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel11)
                    .addComponent(cboFabricante, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9)
                    .addComponent(jLabel6)
                    .addComponent(cboCliente, 0, 303, Short.MAX_VALUE)
                    .addComponent(cboProduto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        btnPdf.setBackground(new java.awt.Color(255, 255, 255));
        btnPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/relact.png"))); // NOI18N
        btnPdf.setText("PDF");
        btnPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfActionPerformed(evt);
            }
        });

        btnExecel.setBackground(new java.awt.Color(255, 255, 255));
        btnExecel.setText("Excel");
        btnExecel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExecelActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de Folha"));

        rdDetalhada.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(rdDetalhada);
        rdDetalhada.setSelected(true);
        rdDetalhada.setText("Detalhada");

        rdSintetica.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup2.add(rdSintetica);
        rdSintetica.setText("Sintetica");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdDetalhada)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdSintetica)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdDetalhada)
                    .addComponent(rdSintetica))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExecel, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(20, 20, 20))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPdf, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExecel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/BANNE.png"))); // NOI18N

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/ddd.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rdUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdUsuarioActionPerformed
        // TODO add your handling code here:
        enableChk();
    }//GEN-LAST:event_rdUsuarioActionPerformed

    private void rdFormaPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdFormaPagamentoActionPerformed
        // TODO add your handling code here:
        enableChk();
    }//GEN-LAST:event_rdFormaPagamentoActionPerformed

    private void rdClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdClienteActionPerformed
        // TODO add your handling code here:
        enableChk();
    }//GEN-LAST:event_rdClienteActionPerformed

    private void rdProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdProdutoActionPerformed
        // TODO add your handling code here:
        enableChk();
    }//GEN-LAST:event_rdProdutoActionPerformed

    private void rdCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdCategoriaActionPerformed
        // TODO add your handling code here:
        enableChk();
    }//GEN-LAST:event_rdCategoriaActionPerformed

    private void rdFabricanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdFabricanteActionPerformed
        // TODO add your handling code here:
        enableChk();
    }//GEN-LAST:event_rdFabricanteActionPerformed

    private void rdNenhumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdNenhumActionPerformed
        // TODO add your handling code here:
        enableChk();
    }//GEN-LAST:event_rdNenhumActionPerformed

    private void btnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfActionPerformed
        // TODO add your handling code here:

        boolean flag = !rdDetalhada.isSelected() ? rdSintetica.isSelected() : false;
        relatorio(false, flag);


    }//GEN-LAST:event_btnPdfActionPerformed

    private void btnExecelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExecelActionPerformed
        // TODO add your handling code here:
        boolean flag = !rdDetalhada.isSelected() ? rdSintetica.isSelected() : false;
        relatorio(true, flag);
    }//GEN-LAST:event_btnExecelActionPerformed

    private void cboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboCategoriaActionPerformed
    private void relatorio(boolean isExecel, boolean isSintetica) {
        if (rdCliente.isSelected()) {

            ClienteModel modelo = (ClienteModel) cboCliente.getSelectedItem();

            FacturaIreport.facturacao(modelo.getNome(), 1, DataComponent.getData(data1), DataComponent.getData(data2), isSintetica, isExecel);
        } else if (rdUsuario.isSelected()) {

            UsuarioModel modelo = (UsuarioModel) cboUsuario.getSelectedItem();
            FacturaIreport.facturacao(String.valueOf(modelo.getId()), 2, DataComponent.getData(data1), DataComponent.getData(data2), isSintetica, isExecel);
        } else if (rdFormaPagamento.isSelected()) {

            FormaPagamentoModel modelo = (FormaPagamentoModel) cboFormaPagamento.getSelectedItem();
            FacturaIreport.facturacao(String.valueOf(modelo.getId()), 3, DataComponent.getData(data1), DataComponent.getData(data2), isSintetica, isExecel);
        } else if (rdProduto.isSelected()) {

            ProdutoModel modelo = (ProdutoModel) cboProduto.getSelectedItem();
            FacturaIreport.facturacao(String.valueOf(modelo.getId()), 4, DataComponent.getData(data1), DataComponent.getData(data2), isSintetica, isExecel);

        } else if (rdCategoria.isSelected()) {

            CategoriaModel modelo = (CategoriaModel) cboCategoria.getSelectedItem();
            FacturaIreport.facturacao(String.valueOf(modelo.getId()), 5, DataComponent.getData(data1), DataComponent.getData(data2), isSintetica, isExecel);
        } else if (rdFabricante.isSelected()) {

            FabricanteModel modelo = (FabricanteModel) cboFabricante.getSelectedItem();
            FacturaIreport.facturacao(String.valueOf(modelo.getId()), 6, DataComponent.getData(data1), DataComponent.getData(data2), isSintetica, isExecel);
        } else {
            FacturaIreport.facturacao("0", 7, DataComponent.getData(data1), DataComponent.getData(data2), isSintetica, isExecel);
        }

    }

    private void logSistema(LogModel modelo) {

        try {
            ArmazenamentoController<LogModel> ficheiro = new ArmazenamentoController<>("Log_sistema");
            ficheiro.create(modelo);
        } catch (IOException ex) {
            Logger.getLogger(FacturacaoView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void logSistemaErro(LogModel modelo) {

        try {
            ArmazenamentoController<LogModel> ficheiro = new ArmazenamentoController<>("Log_erro");
            ficheiro.create(modelo);
        } catch (IOException ex) {
            Logger.getLogger(FacturacaoView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

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
            java.util.logging.Logger.getLogger(FacturacaoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FacturacaoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FacturacaoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FacturacaoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FacturacaoView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExecel;
    private javax.swing.JButton btnPdf;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboCategoria;
    private javax.swing.JComboBox<String> cboCliente;
    private javax.swing.JComboBox<String> cboFabricante;
    private javax.swing.JComboBox<String> cboFormaPagamento;
    private javax.swing.JComboBox<String> cboProduto;
    private javax.swing.JComboBox<String> cboUsuario;
    private com.toedter.calendar.JDateChooser data1;
    private com.toedter.calendar.JDateChooser data2;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton rdCategoria;
    private javax.swing.JRadioButton rdCliente;
    private javax.swing.JRadioButton rdDetalhada;
    private javax.swing.JRadioButton rdFabricante;
    private javax.swing.JRadioButton rdFormaPagamento;
    private javax.swing.JRadioButton rdNenhum;
    private javax.swing.JRadioButton rdProduto;
    private javax.swing.JRadioButton rdSintetica;
    private javax.swing.JRadioButton rdUsuario;
    // End of variables declaration//GEN-END:variables
}
