/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ClienteController;
import Util.DataComponent;
import Controller.EntradaStockItemController;
import Controller.EncomendaController;
import Controller.EncomendaItemController;
import Controller.IPCController;
import Ireport.FacturaIreport;
import Model.ClienteModel;
import Model.EncomendaItemModel;
import Model.EntradaStockItemModel;
import Model.EncomendaModel;
import Model.EstadoModel;
import Model.FormaPagamentoModel;
import Model.IPCModel;
import Model.ProdutoModel;
import Model.Taxa;
import Model.UsuarioModel;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author MAC JUNIOR
 */
public class Encomenda extends javax.swing.JFrame {

    /**
     * Creates new form Encomenda
     */
    private UsuarioModel usuario;

    private DefaultTableModel tbDefault;
    private double qtdAntesClick;
    private double qtdAdicionada;
    private double preco = 0, totalAnterior = 0;
    private double qtd = 0;
    private boolean isAdicionado = false;

    public Encomenda() {
        initComponents();
    }

    public Encomenda(UsuarioModel usuario) {

        initComponents();
        this.usuario = usuario;
        inicializar();
        tbDefault = (DefaultTableModel) tbPedido.getModel();
    }

    private void inicializar() {

        carregarCliente();
//        carregarFormaPagamento();
        carregarProduto();

    }

    public List<EntradaStockItemModel> getStockProduto() {

        EntradaStockItemController controller = new EntradaStockItemController();
        return controller.getStock(1);
    }

    public List<EntradaStockItemModel> getStockProduto(String pesquisar) {

        EntradaStockItemController controller = new EntradaStockItemController();
        return controller.getStock(1, pesquisar);
    }

    private void carregarCliente() {

        ClienteController controller = new ClienteController();
        List<ClienteModel> lista = controller.get();
        cboCliente.setModel(new DefaultComboBoxModel(lista.toArray()));
    }

    private void carregarProduto() {

        DefaultTableModel tbDefault = (DefaultTableModel) tbProduto.getModel();
        tbDefault.setRowCount(0);

        List<EntradaStockItemModel> stock = getStockProduto(txtPesquisar.getText());

        for (EntradaStockItemModel e : stock) {

            tbDefault.addRow(new Object[]{
                e.getProduto(),
                e,
                e.getQtd(),
                getValor(e.getPrecoVenda())
            });
        }
//        cboProduto.setSelectedIndex(-1);

    }

    private String getValor(double valor) {

        DecimalFormat d = new DecimalFormat("0,000.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        return d.format(valor);
    }

    private float quantidade(String lote) {

        EntradaStockItemController controller = new EntradaStockItemController();
        return (float) (controller.getQtdByCodBarra(lote) - controller.getStockMinimo(lote));
    }

    private int getIndexStocavel(String lote) {

        qtdAdicionada = 0;
        for (int i = 0; i < tbPedido.getRowCount(); i++) {

            String loteTb = tbPedido.getValueAt(i, 1).toString();

            if (loteTb.equals(lote)) {

                qtdAdicionada = Double.parseDouble(tbPedido.getValueAt(i, 2).toString());
                return i;
            }
        }
        return -1;
    }

    private void calculaIVA(ProdutoModel produto, EncomendaItemModel model) {

        if (produto.isIpc()) {

            Taxa taxa = produto.getTaxa();
            if (taxa != null) {
                double iva = (model.getSubTotal() * taxa.getTaxa()) / 100;
                model.setIva(iva);
            }

        }

    }

    private void calcular() {

        double subTotal = 0, desconto = 0, iva = 0, total = 0;

        for (int i = 0; i < tbPedido.getRowCount(); i++) {

            subTotal += getValueNormal(tbPedido.getValueAt(i, 4).toString());
            total += getValueNormal(tbPedido.getValueAt(i, 7).toString());
            desconto += getValueNormal(tbPedido.getValueAt(i, 6).toString());
            iva += getValueNormal(tbPedido.getValueAt(i, 5).toString());
            //subTotal += Double.parseDouble(tbPedido.getValueAt(i, 3).toString());

        }

//        lblTroco.setText(getValor(0));
//        lblSubTotal.setText(getValor(subTotal));
        txtTotal.setText(getValor(total));
//        lblIVA.setText(getValor(iva));
//        lblDesconto.setText(getValor(desconto));
//        lblTotal.setText(String.valueOf(total));
//        lblSubTotal.setText(String.valueOf(subTotal));
//        lblDesconto.setText(String.valueOf(desconto));
//        lblIva.setText(String.valueOf(iva));

    }

    private double getValueNormal(String valor) {
        if (!valor.isEmpty()) {
            if (valor.contains(",")) {
                return Double.parseDouble(valor.trim().replace(".", "").replaceAll(",", "."));
            } else {
                return Double.parseDouble(valor);
            }
        }
        return 0.0;
    }

    private void addItemExistente(EncomendaItemModel factura, int index, ProdutoModel produto, IPCModel ipc) {

        factura.setQtd(factura.getQtd() + Double.parseDouble(tbPedido.getValueAt(index, 2).toString()));
        double preco = getValueNormal(tbPedido.getValueAt(index, 3).toString());
        double ipcValor = 0;
        double desconto = getValueNormal(tbPedido.getValueAt(index, 6).toString());
        factura.setPreco(preco);
        factura.setDesconto(desconto);
        factura.setSubTotal(factura.getQtd() * factura.getPreco());
        tbPedido.setValueAt(factura.getQtd(), index, 2);
        tbPedido.setValueAt(factura.getSubTotal(), index, 4);
        if (produto.isIpc()) {

            ipcValor = factura.getSubTotal() * Double.parseDouble(ipc.getValor()) / 100;
            factura.setIva(ipcValor);

        }
        factura.setTotal(factura.getSubTotal() + ipcValor - factura.getDesconto());
        tbPedido.setValueAt(getValor(factura.getIva()), index, 5);
        tbPedido.setValueAt(getValor(factura.getTotal()), index, 7);
    }

    private void adicionar(EntradaStockItemModel entrada) {

        ProdutoModel produto = entrada.getProduto();
        EncomendaItemModel model = new EncomendaItemModel();

        model.setProduto(produto);
        model.setPreco(produto.getValorVenda());
        model.setQtd(Double.parseDouble(quantidade.getValue().toString()));
        model.setSubTotal(model.getQtd() * model.getPreco());

        IPCController ipcController = new IPCController();
        IPCModel ipc = ipcController.getIPC();

        if (produto.isStocavel()) {

            double qtd = 0;

            //qtd = Double.parseDouble(lblQuatidade.getText().replace(".", "").replaceAll(",", "."));
            qtd = quantidade(entrada.getCodBarra());
            if (qtd == -1) {
                isAdicionado = false;
                return;
            }

            if (entrada != null) {

                // int index = getIndex(produto);
                int index = getIndexStocavel(entrada.getCodBarra());
                // EntradaStockItemModel eModel = (EntradaStockItemModel) cboLote.getSelectedItem();
                model.setSubTotal(model.getQtd() * entrada.getPrecoVenda());
                calculaIVA(produto, model);
                model.setTotal(model.getQtd() * entrada.getPrecoVenda() + model.getIva() - model.getDesconto());
                if (qtd - qtdAdicionada > 0) {

                    if (index == -1) {

                        if (model.getQtd() <= qtd) {

                            tbDefault.addRow(new Object[]{
                                model,
                                entrada.getCodBarra(),
                                model.getQtd(),
                                getValor(entrada.getPrecoVenda()),
                                getValor(model.getSubTotal()),
                                getValor(model.getIva()),
                                getValor(model.getDesconto()),
                                getValor(model.getTotal())

                            });

                            calcular();
                            isAdicionado = true;
                        } else {
                            isAdicionado = false;
                            JOptionPane.showMessageDialog(this, "Quantidade Indispunivel no Stock");
                        }
                    } else {
                        double qtdTotal = model.getQtd() + (double) tbPedido.getValueAt(index, 2);
                        if (qtdTotal <= qtd) {
                            addItemExistente(model, index, produto, ipc);
                            calcular();
                            isAdicionado = true;
                        } else {
                            isAdicionado = false;
                            JOptionPane.showMessageDialog(this, "Quantidade Indispunivel no Stock");
                        }
                    }
                } else {
                    isAdicionado = false;
                    JOptionPane.showMessageDialog(this, "Quantidade indisponivel no stock");
                }
            } else {
                isAdicionado = false;
                JOptionPane.showMessageDialog(this, "Não existe lote indisponivel para este produto");
            }

        } else {

            calculaIVA(produto, model);
            model.setTotal(model.getQtd() * model.getPreco() + model.getIva() - model.getDesconto());
            int index = getIndex(produto);
            if (index == -1) {
                tbDefault.addRow(new Object[]{
                    model,
                    entrada.getCodBarra(),
                    model.getQtd(),
                    getValor(model.getPreco()),
                    getValor(model.getSubTotal()),
                    getValor(model.getIva()),
                    getValor(model.getDesconto()),
                    getValor(model.getTotal())

                });
                calcular();
                isAdicionado = true;
            } else {
                addItemExistente(model, index, produto, ipc);
                calcular();
                isAdicionado = true;
            }
        }
//        controlerQuant();

    }

    private int getIndex(ProdutoModel produto) {

        for (int i = 0; i < tbPedido.getRowCount(); i++) {

            EncomendaItemModel factura = (EncomendaItemModel) tbPedido.getValueAt(i, 0);

            if (factura.getProduto().getId() == produto.getId()) {
                return i;
            }
        }
        return -1;
    }

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:

        int row = tbProduto.getSelectedRow();
        if (row >= 0) {
            EntradaStockItemModel entrada = (EntradaStockItemModel) tbProduto.getValueAt(row, 1);
            this.adicionar(entrada);
            ProdutoModel p = entrada.getProduto();
            if (isAdicionado && p.isStocavel()) {

                Double qtdActual = Double.parseDouble(tbProduto.getValueAt(row, 2).toString());
                Double qtd = qtdActual - Double.parseDouble(quantidade.getValue().toString());
                tbProduto.setValueAt(qtd, row, 2);

            }
            quantidade.setValue(1);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione o produto");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtContacto = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtLocal = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbProduto = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPedido = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        btnGravar = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        cboCliente = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        quantidade = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                panelKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Nome");

        jLabel2.setText("Total");

        txtTotal.setEditable(false);

        jLabel3.setText("Contacto");

        jLabel4.setText("Local de Entrega");

        tbProduto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Produto", "Cod.Barra", "Qtd Stock", "Preco"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbProduto.setToolTipText("Para Adicionar o Produto no Carrinho ou na factura click duas vezes");
        jScrollPane2.setViewportView(tbProduto);

        tbPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Produto", "Cod.Barra", "Qtd", "Preço", "Sub.Total", "IVA", "Desconto", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbPedido.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPedidoMouseClicked(evt);
            }
        });
        tbPedido.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPedidoKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbPedido);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btnGravar.setBackground(new java.awt.Color(255, 255, 255));
        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red.png"))); // NOI18N
        btnGravar.setText("Gravar");
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(255, 255, 255));
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/addd2.png"))); // NOI18N
        btnAdd.setText("Adicionar");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/mmmm.png"))); // NOI18N
        jButton6.setText("Remover");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(77, 77, 77)
                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                    .addComponent(btnGravar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/BANNE.png"))); // NOI18N

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/ddd.png"))); // NOI18N

        jLabel10.setText("Pesquisar Produto");

        cboCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel11.setText("cliente");

        jLabel12.setText("Quantidade");

        quantidade.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 781, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel10)
                                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel11)
                                            .addGap(0, 0, Short.MAX_VALUE))
                                        .addComponent(cboCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel4)
                                        .addComponent(txtLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2)
                                .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addComponent(quantidade))
                            .addGap(6, 6, 6))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtContacto, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtLocal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(2, 2, 2)
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(2, 2, 2)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(16, 16, 16)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Registar", jPanel1);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("Pesquisar");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Cliente", "Contacto", "Local de Entrega", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(jTable1);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/entras.png"))); // NOI18N
        jButton3.setText("Entregar ( Pagamento)");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/printer_error_24px.png"))); // NOI18N
        jButton4.setText("Imprimir");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/BANNE.png"))); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/ddd.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 781, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Lista de Encomenda", jPanel2);

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        getContentPane().add(panel, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void panelKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_panelKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_panelKeyPressed

    private void tbPedidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPedidoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPedidoMouseClicked

    private void tbPedidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPedidoKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tbPedidoKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        int row = tbProduto.getSelectedRow();
        if (row >= 0) {
            EntradaStockItemModel entrada = (EntradaStockItemModel) tbProduto.getValueAt(row, 1);
            this.adicionar(entrada);
            ProdutoModel p = entrada.getProduto();
            if (isAdicionado && p.isStocavel()) {

                Double qtdActual = Double.parseDouble(tbProduto.getValueAt(row, 2).toString());
                Double qtd = qtdActual - Double.parseDouble(quantidade.getValue().toString());
                tbProduto.setValueAt(qtd, row, 2);

            }
            quantidade.setValue(1);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione o produto");
        }
    }//GEN-LAST:event_btnAddActionPerformed
    private EncomendaModel getFactura() {

        EncomendaModel encomendaModel = new EncomendaModel();

//        encomendaModel.setFormaPagamento((FormaPagamentoModel) cboFormaPagamento.getSelectedItem());
        encomendaModel.setEstado(new EstadoModel(1, ""));
        encomendaModel.setData(DataComponent.getDataActual());
        encomendaModel.setUsuario(usuario);
//        encomendaModel.setTroco(getValueNormal(lblTroco.getText()));
//        encomendaModel.setValorEntregue(Double.parseDouble(txtValorEntregue.getText()));
//        encomendaModel.setValorMulticaixa(Double.parseDouble(txtMulticaixa.getText()));
        encomendaModel.setTotal(getValueNormal(txtTotal.getText()));
//        encomendaModel.setTotalDesconto(getValueNormal(lblDesconto.getText()));
//        encomendaModel.setTipoFacturas(chkPerfoma.isSelected() ? "Perfoma" : "Venda");
        encomendaModel.setCliente((ClienteModel) cboCliente.getSelectedItem());
        if (!txtNome.getText().isEmpty()) {

            encomendaModel.setNome(txtNome.getText());
            encomendaModel.setContacto(txtContacto.getText());
            encomendaModel.setLocalEntrega(txtLocal.getText());

        } else if (cboCliente.getSelectedIndex() >= 0) {

            encomendaModel.setNome(encomendaModel.getCliente().getNome());
            encomendaModel.setContacto(encomendaModel.getCliente().getContacto());
            encomendaModel.setLocalEntrega(txtLocal.getText());

        } else {
            JOptionPane.showMessageDialog(this, "Selecione o cliente ou adicione o nome");
            return null;
        }

        return encomendaModel;
    }
    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        // TODO add your handling code here:
        if (tbPedido.getRowCount() > 0) {
            boolean result = false;

            EncomendaModel encomendaModel = getFactura();

            if (encomendaModel != null) {

                //            if (encomendaModel.getTroco() >= 0) {
                EncomendaController controller = new EncomendaController();
                if (controller.saveOrUpdate(encomendaModel)) {

                    encomendaModel.setId(controller.getLastIdByUsuario(usuario));
                    for (int i = 0; i < tbPedido.getRowCount(); i++) {

                        EncomendaItemModel item = (EncomendaItemModel) tbPedido.getValueAt(i, 0);

                        EncomendaItemModel encomendaItemModel = new EncomendaItemModel();
                        encomendaItemModel.setFactura(encomendaModel);
                        encomendaItemModel.setProduto(item.getProduto());
                        encomendaItemModel.setLote(tbPedido.getValueAt(i, 1).toString());
                        encomendaItemModel.setQtd(Double.parseDouble(tbPedido.getValueAt(i, 2).toString()));
                        encomendaItemModel.setPreco(getValueNormal(tbPedido.getValueAt(i, 3).toString()));
                        encomendaItemModel.setIva(getValueNormal(tbPedido.getValueAt(i, 5).toString()));
                        encomendaItemModel.setDesconto(getValueNormal(tbPedido.getValueAt(i, 6).toString()));
                        encomendaItemModel.setTotal(getValueNormal(tbPedido.getValueAt(i, 7).toString()));
                        encomendaItemModel.setSubTotal(getValueNormal(tbPedido.getValueAt(i, 4).toString()));

                        EncomendaItemController fController = new EncomendaItemController();
                        if (fController.saveOrUpdate(encomendaItemModel)) {

                            if (!encomendaItemModel.getLote().trim().isEmpty()) {
                                if (true) {
                                    reduzirQtd(encomendaItemModel.getLote(), encomendaItemModel.getQtd());
                                }

                            }
                            result = true;
                        } else {
                            result = false;
                            JOptionPane.showMessageDialog(this, "Ocorreu um erro regista o item da factura", "ERRO", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possivel salvar a factura", "ERRO", JOptionPane.ERROR_MESSAGE);
                }

                //            } else {
                //                JOptionPane.showMessageDialog(this, "Valor pago insuficiente");
                //            }
            } else {
                JOptionPane.showMessageDialog(this, "Selecione a forma de pagamento");
            }

            if (result) {

                JOptionPane.showMessageDialog(this, "Venda efectuada com sucesso");
                if (true) {
                    FacturaIreport.termicaEcomenda(encomendaModel.getId());
                }
                FacturaIreport.pedidoCozinhaBalcao(encomendaModel.getId());
                limpar();

            }
        } else {
            JOptionPane.showMessageDialog(this, "Adicione produto na factura", "Alerta", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnGravarActionPerformed
     private List<EntradaStockItemModel> getEntradaItem(String codBarra) {

        EntradaStockItemController controller = new EntradaStockItemController();
        return controller.getQtd(codBarra,1);

    }
    private void reduzirQtd(String codBarra, double qtd) {

        double qtdNova = qtd;
        List<EntradaStockItemModel> lista = getEntradaItem(codBarra);
        EntradaStockItemController controller = new EntradaStockItemController();
        for (EntradaStockItemModel e : lista) {

            if (qtdNova > e.getQtd()) {

                qtdNova -= e.getQtd();
                e.setQtd(0);
                controller.updateItem(e);
            } else {

                e.setQtd(e.getQtd() - qtdNova);
                controller.updateItem(e);
                return;
            }

        }

    }

    private void limpar() {

        tbDefault.setRowCount(0);

        //   txtMulticaixa.setText("0");
        quantidade.setValue(1);

        //   txtValorEntregue.setText("0");
        txtPesquisar.setText("");

        txtTotal.setText("0");

        inicializar();
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
            java.util.logging.Logger.getLogger(Encomenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Encomenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Encomenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Encomenda.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Encomenda().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnGravar;
    private javax.swing.JComboBox<String> cboCliente;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JPanel panel;
    private javax.swing.JSpinner quantidade;
    private javax.swing.JTable tbPedido;
    private javax.swing.JTable tbProduto;
    private javax.swing.JTextField txtContacto;
    private javax.swing.JTextField txtLocal;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
