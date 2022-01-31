/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ClienteController;
import Util.DataComponent;
import Controller.DocumentoController12;
import Controller.EntradaStockItemController;
import Controller.FacturaController;
import Controller.FacturaItemController;
import Controller.FormaPagamentoController;
import Controller.IPCController;
import Controller.MoedaController;
import Controller.ParamentroController;
import Controller.PedidoTesteController;
import Controller.PedidoItemController;
import Controller.ProdutoController;
import Ireport.FacturaIreport;
import Model.ClienteModel;
import Model.Documento;
import Model.EntradaStockItemModel;
import Model.EstadoModel;
import Model.FacturaItemModel;
import Model.FacturaModel;
import Model.FormaPagamentoModel;
import Model.IPCModel;
import Model.MesaModel;
import Model.Moeda;
import Model.ParamentroModel;
import Model.PedidoItemTesteModel;
import Model.PedidoModel;
import Model.ProdutoModel;
import Model.Taxa;
import Model.UsuarioModel;
import java.awt.event.KeyEvent;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import json_xml_iva.RSA;

/**
 *
 * @author celso
 */
public class FacturacaoNovaView extends javax.swing.JFrame {

    /**
     * Creates new form FacturacaoView
     */
    private UsuarioModel usuario;
    private MesaModel mesa;
    private DefaultTableModel tbDefault;
    private double qtdAntesClick;
    private double qtdAdicionada;
    private double preco = 0, totalAnterior = 0;
    private double qtd = 0;
    private boolean isAdicionado = false;
    private int idPedido = 0, idArmazem = 0;
    private double descontoGlobal = 0;

    public FacturacaoNovaView() {
        initComponents();

        setExtendedState(JFrame.MAXIMIZED_BOTH);

    }

    public FacturacaoNovaView(UsuarioModel usuario) {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.usuario = usuario;
        inicializar();
        tbDefault = (DefaultTableModel) tbPedido.getModel();
        this.armazemPrincipal();
        // lblOperador.setText(usuario.getNome());

    }

    private void armazemPrincipal() {

        ParamentroController controller = new ParamentroController();
        ParamentroModel modelo = controller.getById(6);

        if (modelo.getValor() > 0) {//remover

            idArmazem = modelo.getValor();

        } else {
            btnAdicionar.setEnabled(false);
            btnFacturar.setEnabled(false);
            btnImprimir.setEnabled(false);
            chkPerfoma.setEnabled(false);
            txtValorEntregue.setEnabled(false);
            txtMulticaixa.setEnabled(false);
            tbDefault.setRowCount(0);
            JOptionPane.showMessageDialog(this, "Não foi definido armazem Principal");
        }

    }

    public FacturacaoNovaView(UsuarioModel usuario, MesaModel mesa) {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.usuario = usuario;
        inicializar();
        tbDefault = (DefaultTableModel) tbPedido.getModel();
        this.setTitle(mesa.getDesignacao());
        // lblOperador.setText(usuario.getNome());
        this.mesa = mesa;

        carregarPedido();
        this.armazemPrincipal();
    }

    private void inicializar() {

        carregarCliente();
        carregarFormaPagamento();
        carregarProduto();
        carregarMoeda();

    }

    private String getValor(double valor) {

        DecimalFormat d = new DecimalFormat("0,000.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        return d.format(valor);
    }

    private void carregarCliente() {

        ClienteController controller = new ClienteController();
        List<ClienteModel> lista = controller.get();
        cboCliente.setModel(new DefaultComboBoxModel(lista.toArray()));
    }

    private void carregarFormaPagamento() {

        FormaPagamentoController controller = new FormaPagamentoController();
        List<FormaPagamentoModel> lista = controller.get();
        cboFormaPagamento.setModel(new DefaultComboBoxModel(lista.toArray()));
    }

    private void carregarProduto() {

        carregarProdutoByPesquisa();
//        DefaultTableModel tbDefault = (DefaultTableModel) tbProduto.getModel();
//        tbDefault.setRowCount(0);
//
//        List<EntradaStockItemModel> stock = getStockProduto();
//
//        for (EntradaStockItemModel e : stock) {
//
//            tbDefault.addRow(new Object[]{
//                e.getProduto(),
//                e,
//                e.getQtd(),
//                getValor(e.getPrecoVenda())
//            });
//        }
//        cboProduto.setSelectedIndex(-1);

    }

    private void carregarProdutoByPesquisa() {

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

    private void calculaIVA(ProdutoModel produto, FacturaItemModel model) {

        if (produto.isIpc()) {

            Taxa taxa = produto.getTaxa();
            if (taxa != null) {
                double iva = (model.getSubTotal() * taxa.getTaxa()) / 100;
                model.setIva(iva);
            }

        }

    }

    private double calculaIVA(ProdutoModel produto, Double preco, double qtd) {

        if (produto.isIpc()) {

            Taxa taxa = produto.getTaxa();
            if (taxa != null) {
                return (preco * qtd * taxa.getTaxa()) / 100;

            }

        }
        return 0;

    }

    private void adicionar(EntradaStockItemModel entrada) {

        ProdutoModel produto = entrada.getProduto();
        FacturaItemModel model = new FacturaItemModel();

        System.out.println("STATUS>>>>>>true");
        model.setProduto(produto);
        model.setPreco(produto.getValorVenda());
        model.setQtd(Double.parseDouble(quantidade.getValue().toString()));
        model.setSubTotal(model.getQtd() * model.getPreco());

        IPCController ipcController = new IPCController();
        IPCModel ipc = ipcController.getIPC();

        if (produto.isStocavel()) {

            double qtd = 0;
            if (!chkPerfoma.isSelected()) {
                //qtd = Double.parseDouble(lblQuatidade.getText().replace(".", "").replaceAll(",", "."));
                qtd = quantidade(entrada.getCodBarra());
                if (qtd == -1) {
                    isAdicionado = false;
                    return;
                }

            } else {
                qtd += 100;
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

                            if (insertPedidoItem()) {
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
                            }
                        } else {
                            isAdicionado = false;
                            JOptionPane.showMessageDialog(this, "Quantidade Indispunivel no Stock");
                        }
                    } else {
                        double qtdTotal = model.getQtd() + (double) tbPedido.getValueAt(index, 2);
                        if (qtdTotal <= qtd) {
                            alterItemExist(index);
//                            addItemExistente(model, index, produto, ipc);
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
                if (insertPedidoItem()) {
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
                }
            } else {
                alterItemExist(index);
//                addItemExistente(model, index, produto, ipc);
                calcular();
                isAdicionado = true;
            }
        }
//        controlerQuant();

    }

    private int getIndex(ProdutoModel produto) {

        for (int i = 0; i < tbPedido.getRowCount(); i++) {

            FacturaItemModel factura = (FacturaItemModel) tbPedido.getValueAt(i, 0);

            if (factura.getProduto().getId() == produto.getId()) {
                return i;
            }
        }
        return -1;
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

        lblTroco.setText(getValor(0));
        lblSubTotal.setText(getValor(subTotal));
        lblTotal.setText(getValor(total));
        lblIVA.setText(getValor(iva));
        lblDesconto.setText(getValor(desconto));
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

    private void addItemExistente(FacturaItemModel factura, int index, ProdutoModel produto, IPCModel ipc) {

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

    public List<EntradaStockItemModel> getStockProduto() {

        EntradaStockItemController controller = new EntradaStockItemController();
        return controller.getStock(1);
    }

    public List<EntradaStockItemModel> getStockProduto(String pesquisar) {

        EntradaStockItemController controller = new EntradaStockItemController();
        return controller.getStock(idArmazem, pesquisar);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        chkPerfoma = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        cboCliente = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cboFormaPagamento = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        txtValorEntregue = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtMulticaixa = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        quantidade = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        lblSubTotal = new javax.swing.JLabel();
        lblDesconto = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblTroco = new javax.swing.JLabel();
        btnAdicionar = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        lblIVA = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtDesconto = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        btnFacturar = new javax.swing.JButton();
        btnProfoma = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        chkTermica = new javax.swing.JCheckBox();
        chkA4 = new javax.swing.JCheckBox();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbProduto = new javax.swing.JTable();
        jLabel21 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        cboMoeda = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        txtMoedaCambio = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtConvertido = new javax.swing.JTextField();
        btnCalcularCambio = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        txtMoedaAOA = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtValorEntregueMoeda = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        dataInicio = new com.toedter.calendar.JDateChooser();
        DataFim = new com.toedter.calendar.JDateChooser();
        jLabel17 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        rdA4 = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        rdTermica = new javax.swing.JCheckBox();
        rdA5 = new javax.swing.JCheckBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtItemFactura = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        listaFactura = new javax.swing.JList<>();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPedido = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Facturação");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(153, 0, 0));

        chkPerfoma.setBackground(new java.awt.Color(255, 255, 255));
        chkPerfoma.setText("Proforma");
        chkPerfoma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPerfomaActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Painel Calculo");
        jLabel1.setOpaque(true);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Buscar");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Cliente");
        jLabel2.setOpaque(true);

        txtPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPesquisarActionPerformed(evt);
            }
        });
        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Sub.Total");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Total");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Desconto");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Nome");

        cboCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Cliente");

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Pagamento dos Artigos");
        jLabel10.setOpaque(true);

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Forma");

        cboFormaPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Numerário", "Multicaixa", "Depósito", "Transferência", "P.Duplo" }));
        cboFormaPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFormaPagamentoActionPerformed(evt);
            }
        });

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Cash");

        txtValorEntregue.setText("0");
        txtValorEntregue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValorEntregueKeyReleased(evt);
            }
        });

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Multicaixa");

        txtMulticaixa.setText("0");
        txtMulticaixa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMulticaixaKeyReleased(evt);
            }
        });

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Qtd");

        quantidade.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Troco");

        lblSubTotal.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblSubTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblSubTotal.setText("0.0");

        lblDesconto.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblDesconto.setForeground(new java.awt.Color(255, 255, 255));
        lblDesconto.setText("0.0");

        lblTotal.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTotal.setText("0.0");

        lblTroco.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblTroco.setForeground(new java.awt.Color(255, 255, 255));
        lblTroco.setText("0.0");

        btnAdicionar.setBackground(new java.awt.Color(255, 255, 255));
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/ttt.png"))); // NOI18N
        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("IVA");

        lblIVA.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblIVA.setForeground(new java.awt.Color(255, 255, 255));
        lblIVA.setText("0.0");

        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Desconto");

        txtDesconto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescontoKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCliente)
                            .addComponent(cboCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtPesquisar)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(chkPerfoma)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE))
                            .addComponent(quantidade, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblIVA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTroco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDesconto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSubTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtValorEntregue, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtMulticaixa, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cboFormaPagamento, 0, 243, Short.MAX_VALUE)
                    .addComponent(txtDesconto))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPesquisar)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkPerfoma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSubTotal))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDesconto))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(lblIVA))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotal))
                        .addGap(1, 1, 1)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblTroco, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCliente))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtValorEntregue, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMulticaixa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(302, 302, 302))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel5.setBackground(new java.awt.Color(153, 0, 0));

        btnFacturar.setBackground(new java.awt.Color(255, 255, 255));
        btnFacturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/faturaç╞o1.png"))); // NOI18N
        btnFacturar.setText("Facturar");
        btnFacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturarActionPerformed(evt);
            }
        });

        btnProfoma.setBackground(new java.awt.Color(255, 255, 255));
        btnProfoma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/resty.png"))); // NOI18N
        btnProfoma.setText("Performa");
        btnProfoma.setEnabled(false);
        btnProfoma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfomaActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/cancel_40px.png"))); // NOI18N
        jButton3.setText("Remover Produto");

        jPanel4.setBackground(new java.awt.Color(230, 187, 144));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        chkTermica.setBackground(java.awt.Color.white);
        buttonGroup2.add(chkTermica);
        chkTermica.setSelected(true);
        chkTermica.setText("Ticket");

        chkA4.setBackground(java.awt.Color.white);
        buttonGroup2.add(chkA4);
        chkA4.setText("A4");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkTermica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkA4)
                .addContainerGap(134, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkTermica)
                    .addComponent(chkA4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnFacturar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProfoma, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnFacturar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnProfoma, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel5, java.awt.BorderLayout.PAGE_END);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.BorderLayout());

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

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

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/mauro.png"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE)
            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel21)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(118, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Produto", jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel18.setText("Moeda");

        jLabel19.setText("Valor(Moeda Estrangeira)");

        txtMoedaCambio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMoedaCambioKeyReleased(evt);
            }
        });

        jLabel20.setText("Valor Convertido em AOA");

        txtConvertido.setEnabled(false);

        btnCalcularCambio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red.png"))); // NOI18N
        btnCalcularCambio.setText("Aplicar");
        btnCalcularCambio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularCambioActionPerformed(evt);
            }
        });

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/mauro.png"))); // NOI18N

        txtMoedaAOA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMoedaAOAKeyReleased(evt);
            }
        });

        jLabel25.setText("AOA( KZ )");

        txtValorEntregueMoeda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorEntregueMoedaActionPerformed(evt);
            }
        });
        txtValorEntregueMoeda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValorEntregueMoedaKeyReleased(evt);
            }
        });

        jLabel26.setText("Valor Entregue (Moeda Estrangeira )");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel9Layout.createSequentialGroup()
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addComponent(txtMoedaCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel25)
                                    .addComponent(txtMoedaAOA, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(cboMoeda, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtValorEntregueMoeda, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtConvertido, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(18, 18, 18)
                        .addComponent(btnCalcularCambio))
                    .addComponent(jLabel20))
                .addContainerGap(231, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel18)
                .addGap(7, 7, 7)
                .addComponent(cboMoeda, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(9, 9, 9)
                        .addComponent(txtMoedaCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addGap(9, 9, 9)
                        .addComponent(txtMoedaAOA, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValorEntregueMoeda, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCalcularCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtConvertido, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(157, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Cambio", jPanel9);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel11.setForeground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Data");

        jLabel17.setText("Até");

        btnBuscar.setBackground(new java.awt.Color(255, 255, 255));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/spinner_frame_6_24px_1.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnImprimir.setBackground(new java.awt.Color(255, 255, 255));
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/printer_error_24px.png"))); // NOI18N
        btnImprimir.setText("Reimprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        rdA4.setText("A4");

        rdTermica.setSelected(true);
        rdTermica.setText("Termica");

        rdA5.setText("A5");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(4, 4, 4)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(rdA4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdTermica)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdA5))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(dataInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 34, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(jLabel17))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel11Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(DataFim, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dataInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdA4)
                    .addComponent(rdTermica)
                    .addComponent(rdA5))
                .addContainerGap())
        );

        jScrollPane3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        txtItemFactura.setEditable(false);
        txtItemFactura.setColumns(20);
        txtItemFactura.setRows(5);
        jScrollPane3.setViewportView(txtItemFactura);

        listaFactura.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaFacturaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(listaFactura);

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/mauro.png"))); // NOI18N

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Reemprimir - Factura", jPanel10);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        jPanel6.add(jPanel7, java.awt.BorderLayout.PAGE_START);

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

        jPanel6.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void insertPedido() {

        PedidoModel pedido = new PedidoModel();
        pedido.setMesa(mesa);

        String nome = txtCliente.getText().trim().isEmpty() ? cboCliente.getSelectedItem().toString() : txtCliente.getText();
        pedido.setNome(nome);
        pedido.setData(DataComponent.getDataActual());
        EstadoModel estado = new EstadoModel(1, "");
        pedido.setEstado(estado);

        PedidoTesteController controller = new PedidoTesteController();
        controller.save(pedido);

    }

    private int getLastIdPedido() {

        PedidoTesteController controller = new PedidoTesteController();
        return controller.getLastId(mesa.getId());
    }

    private void carregarPedido() {

        PedidoItemController controller = new PedidoItemController();
        List<PedidoItemTesteModel> lista = controller.get(mesa.getId());

        idPedido = getLastIdPedido();

//       tbDefault = (DefaultTableModel) tbPedido.getModel();
//        tbDefault.setRowCount(0);
        for (PedidoItemTesteModel p : lista) {

            EntradaStockItemModel model = new EntradaStockItemModel();
            model.setCodBarra(p.getCoBarra());
            model.setQtd(p.getQtd());
            model.setPrecoVenda(p.getPreco());

            idPedido = p.getPedido().getId();
            FacturaItemModel factura = new FacturaItemModel();

            factura.setDesconto(0);
            factura.setIva(p.getIva());
            factura.setProduto(p.getProduto());
            factura.setPreco(p.getPreco());
            factura.setSubTotal(p.getPreco() * p.getQtd());
            factura.setLote(p.getCoBarra());
            factura.setTotal(p.getPreco() * p.getQtd() + p.getIva() - p.getDesconto());
            tbDefault.addRow(new Object[]{
                factura,
                p.getCoBarra(),
                p.getQtd(),
                getValor(p.getPreco()),
                getValor(p.getPreco() * p.getQtd()),
                getValor(p.getIva()),
                getValor(p.getDesconto()),
                getValor(p.getPreco() * p.getQtd() + p.getIva() - p.getDesconto())

            });
        }
        tbPedido.setModel(tbDefault);
        calcular();

    }

    private boolean insertPedidoItem() {

        if (idPedido <= 0) {
            insertPedido();
        }

        PedidoModel pedido = new PedidoModel(getLastIdPedido());

        if (pedido.getId() > 0) {

            idPedido = pedido.getId();
            int row = tbProduto.getSelectedRow();
            if (row >= 0) {
                EntradaStockItemModel entrada = (EntradaStockItemModel) tbProduto.getValueAt(row, 1);

                PedidoItemTesteModel item = new PedidoItemTesteModel();
                item.setPedido(pedido);
                item.setProduto(entrada.getProduto());
                item.setCoBarra(entrada.getCodBarra());
                item.setQtd(Double.parseDouble(quantidade.getValue().toString()));
                item.setPreco(entrada.getPrecoVenda());
                item.setIva(calculaIVA(entrada.getProduto(), entrada.getPrecoVenda(), item.getQtd()));
                item.setDesconto(new Double(0));
                EstadoModel estado = new EstadoModel(1, "");

                item.setEstado(estado);

                item.setUsuario(usuario);

                PedidoItemController controller = new PedidoItemController();
                return controller.save(item);
            }
        }
        return false;
    }

    private void carregarMoeda() {

        MoedaController controller = new MoedaController();
        List<Moeda> lista = controller.get();
        cboMoeda.setModel(new DefaultComboBoxModel(lista.toArray()));
    }

    private PedidoItemTesteModel getPedido(ProdutoModel produto) {

        PedidoItemController controller = new PedidoItemController();
        return controller.get(mesa.getId(), produto.getId());

    }

    private void alterItemExist(int row) {

        EntradaStockItemModel entrada = (EntradaStockItemModel) tbProduto.getValueAt(tbProduto.getSelectedRow(), 1);

        if (entrada != null) {

            ProdutoModel produto = entrada.getProduto();
            double qtd = getValueNormal(tbPedido.getValueAt(row, 2).toString()) + Double.parseDouble(quantidade.getValue().toString());
            double preco = getValueNormal(tbPedido.getValueAt(row, 3).toString());
            double subTotal = qtd * preco;

            double iva = calculaIVA(entrada.getProduto(), preco, qtd);

            PedidoItemTesteModel pedido = getPedido(produto);

            if (pedido != null) {

                pedido.setQtd(qtd);
                pedido.setIva(iva);

                PedidoItemController controller = new PedidoItemController();
                if (controller.update(pedido)) {

                    tbPedido.setValueAt(qtd, row, 2);
                    tbPedido.setValueAt(getValor(subTotal), row, 4);
                    tbPedido.setValueAt(getValor(iva), row, 5);
                    tbPedido.setValueAt(getValor(subTotal) + iva, row, 7);
                }
            }
        }
    }

    private boolean AlterPedidoItem() {

        PedidoModel pedido = new PedidoModel(getLastIdPedido());

        if (pedido.getId() > 0) {

            int row = tbProduto.getSelectedRow();
            if (row >= 0) {
                EntradaStockItemModel entrada = (EntradaStockItemModel) tbProduto.getValueAt(row, 1);

                PedidoItemTesteModel item = new PedidoItemTesteModel();
                item.setPedido(pedido);
                item.setProduto(entrada.getProduto());
                item.setCoBarra(entrada.getCodBarra());
                item.setQtd(Double.parseDouble(quantidade.getValue().toString()));
                item.setPreco(entrada.getPrecoVenda());
                item.setIva(calculaIVA(entrada.getProduto(), entrada.getPrecoVenda(), item.getQtd()));
                item.setDesconto(new Double(0));
                EstadoModel estado = new EstadoModel(1, "");

                item.setEstado(estado);

                item.setUsuario(usuario);

                PedidoItemController controller = new PedidoItemController();
                return controller.save(item);
            }
        }
        return false;
    }

    private double getDesconto(String value, double preco) {

        if (!value.trim().equals(value.trim().replaceAll("%", ""))) {

            String vectorDesconto[] = value.split("%");

            return !vectorDesconto[0].isEmpty() ? preco * Double.parseDouble(vectorDesconto[0]) / 100 : preco * Double.parseDouble(vectorDesconto[1]) / 100;
        }
        return Double.parseDouble(value);
    }

    private void descontarQtd(String codBarraQtd, double qtdRetirada) {

        for (int i = 0; i < tbProduto.getRowCount(); i++) {

//            ProdutoModel aux = (ProdutoModel) tbProduto.getValueAt(i, 0);
            String codBarra = tbProduto.getValueAt(i, 1).toString();

            if (codBarraQtd.equals(codBarra)) {

                double qtd = Integer.parseInt(tbProduto.getValueAt(i, 2).toString()) - qtdRetirada;
                tbProduto.setValueAt(qtd, i, 2);
                return;
            }
        }

    }

    public String getNextFactura(String status) {
        DocumentoController12 docController = new DocumentoController12();
        int last = docController.getLastInsertAno(status);
        int next = last;
        return DataComponent.getAnoActual() + "/" + next;
    }

    private boolean verificarQtd(String codBarraQtd, double qtdRetirada) {

        for (int i = 0; i < tbProduto.getRowCount(); i++) {

//            ProdutoModel aux = (ProdutoModel) tbProduto.getValueAt(i, 0);
            String codBarra = tbProduto.getValueAt(i, 1).toString();

            if (codBarraQtd.equals(codBarra)) {

                double qtd = Integer.parseInt(tbProduto.getValueAt(i, 2).toString()) - qtdRetirada;
                return qtd >= 0;
            }
        }
        return false;
    }

    public String getNextFacturaSimples(String status, int nFact) {
        DocumentoController12 docController = new DocumentoController12();
        int last = docController.getLastInsertAno(status);
        int next = last;
//        return next + "/"+""+facturaController.getSerie()+"/"+ Data.getAnoActual() +" -REF " + nFact;
        return DataComponent.getAnoActual() + " / " + next + " - REF " + nFact;
    }

    private String updateNumeracao(int numFacturaLast) {

        System.out.println("numFacturaLast>>>>>" + numFacturaLast);
        FormaPagamentoModel formaPagamento = (FormaPagamentoModel) cboFormaPagamento.getSelectedItem();
        String designacao, prefixo;

        if (chkPerfoma.isSelected()) {
            designacao = "PROFORMA";
            prefixo = "FT ";
        } else {

            if (formaPagamento.getDesignacao().equals("NUMERARIO")
                    || formaPagamento.getDesignacao().equals("MULTICAIXA")
                    || formaPagamento.getDesignacao().equals("PAGAMENTO DUPLO")) {

                designacao = "FACTURA RECIBO";
                prefixo = "FR ";
            } else {
                designacao = "FACTURA CREDITO";
                prefixo = "FT ";
            }
        }

//        String next = getNextFactura(designacao);
//        System.out.println("next >>>>"+next);
        String nextSimples = getNextFacturaSimples(designacao, numFacturaLast);
        nextSimples = prefixo + nextSimples;
        System.out.println("nextSimples >>>>" + nextSimples);
//        Documento modelo = docController.getAll(designacao);
//        modelo.setNext(String.valueOf(numFacturaLast));

        FacturaController fController = new FacturaController();
        fController.updateNextFactura(nextSimples, numFacturaLast);

        DocumentoController12 docController = new DocumentoController12();
        docController.updateNextNumDoc(designacao);

        return nextSimples;
    }

    private FacturaModel getFactura() {

        FacturaModel facturaModel = new FacturaModel();
        Moeda moeda = (Moeda) cboMoeda.getSelectedItem();
        facturaModel.setMoeda(moeda);
        facturaModel.setFormaPagamento((FormaPagamentoModel) cboFormaPagamento.getSelectedItem());
        facturaModel.setEstado(new EstadoModel(1, ""));
        facturaModel.setData(DataComponent.getDataActual());
        facturaModel.setUsuario(usuario);
        facturaModel.setTroco(getValueNormal(lblTroco.getText()));
        facturaModel.setValorEntregue(Double.parseDouble(txtValorEntregue.getText()));
        facturaModel.setValorMulticaixa(Double.parseDouble(txtMulticaixa.getText()));
        facturaModel.setTotalApagar(getValueNormal(lblTotal.getText()));
        facturaModel.setSubTotal(getValueNormal(lblSubTotal.getText()));
        facturaModel.setTotalIVA(getValueNormal(lblIVA.getText()));
        facturaModel.setTotalDesconto(descontoGlobal);
        facturaModel.setTipoFacturas(chkPerfoma.isSelected() ? "Perfoma" : cboFormaPagamento.getSelectedItem().toString().equalsIgnoreCase("CRÉDITO") ? "Venda Credito" : "Venda");
        facturaModel.setCliente((ClienteModel) cboCliente.getSelectedItem());
        if (!txtCliente.getText().isEmpty()) {

            facturaModel.setNomeCliente(txtCliente.getText());
        } else if (cboCliente.getSelectedIndex() >= 0) {

            facturaModel.setNomeCliente(cboCliente.getSelectedItem().toString());

        } else {
            JOptionPane.showMessageDialog(this, "Selecione o cliente ou adicione o nome");
            return null;
        }

        if (!facturaModel.isEmpty()) {

            if (facturaModel.getFormaPagamento().isCash() || facturaModel.getFormaPagamento().isMulticaixa()) {
                if (getValueNormal(lblTroco.getText()) < 0 && !chkPerfoma.isSelected()) {
                    JOptionPane.showMessageDialog(this, "Valor pago insuficiente");
                    return null;
                }
            }
        }
        return facturaModel;
    }

    public void init() {

        FacturaController controller = new FacturaController();
        List<FacturaModel> lista = controller.getFacturas();

        DefaultListModel listDefault = new DefaultListModel();
        for (FacturaModel f : lista) {

            listDefault.addElement(f);
        }
        listaFactura.setModel(listDefault);
    }

    public void init2() {

        FacturaController controller = new FacturaController();
        List<FacturaModel> lista = controller.getFacturaData(DataComponent.getData(dataInicio), DataComponent.getData(DataFim));

        DefaultListModel listDefault = new DefaultListModel();
        for (FacturaModel f : lista) {

            listDefault.addElement(f);
        }
        listaFactura.setModel(listDefault);
    }

    public FacturaModel getSelectItemId() {

        DefaultListModel listDefault = (DefaultListModel) listaFactura.getModel();
        
        FacturaModel modelo = (FacturaModel) listDefault.getElementAt(listaFactura.getSelectedIndex());
        
        return modelo;

    }

    public void carregarItem(int id) {

        FacturaItemController controller = new FacturaItemController();
        //controller.getById(id)
    }
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        new PedidoMesaVIew(usuario).setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void chkPerfomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPerfomaActionPerformed
        // TODO add your handling code here:
        tbDefault.setRowCount(0);
        btnFacturar.setEnabled(!chkPerfoma.isSelected());
        btnProfoma.setEnabled(chkPerfoma.isSelected());
        chkA4.setSelected(chkPerfoma.isSelected());
        chkTermica.setEnabled(!chkPerfoma.isSelected());
        // limpar();
    }//GEN-LAST:event_chkPerfomaActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        carregarProdutoByPesquisa();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void cboFormaPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFormaPagamentoActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        FormaPagamentoModel modelo = (FormaPagamentoModel) cboFormaPagamento.getSelectedItem();

        txtMulticaixa.setText(modelo.isMulticaixa() ? String.valueOf(getValueNormal(lblTroco.getText())) : "0");
        txtValorEntregue.setText(modelo.isCash() ? txtValorEntregue.getText() : "0");

        txtMulticaixa.setFocusable(modelo.isMulticaixa());
        txtValorEntregue.setFocusable(modelo.isCash());
    }//GEN-LAST:event_cboFormaPagamentoActionPerformed

    private void txtValorEntregueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorEntregueKeyReleased
        // TODO add your handling code here:
        lblTroco.setText(getValor(calcularTroco()));
    }//GEN-LAST:event_txtValorEntregueKeyReleased

    private void txtMulticaixaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMulticaixaKeyReleased
        // TODO add your handling code here:
        lblTroco.setText(getValor(calcularTroco()));
    }//GEN-LAST:event_txtMulticaixaKeyReleased

    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed
        // TODO add your handling code here:

        int row = tbProduto.getSelectedRow();
        if (row >= 0) {
            EntradaStockItemModel entrada = (EntradaStockItemModel) tbProduto.getValueAt(row, 1);
            this.adicionar(entrada);
            ProdutoModel p = entrada.getProduto();
            if (isAdicionado && p.isStocavel()) {

                if (!chkPerfoma.isSelected()) {
                    Double qtdActual = Double.parseDouble(tbProduto.getValueAt(row, 2).toString());
                    Double qtd = qtdActual - Double.parseDouble(quantidade.getValue().toString());
                    tbProduto.setValueAt(qtd, row, 2);
                }

            }
            quantidade.setValue(1);
        } else {
            JOptionPane.showMessageDialog(this, "Selecione o produto");
        }
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void btnFacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturarActionPerformed
        // TODO add your handling code here:
        if (tbPedido.getRowCount() > 0) {
            boolean result = false;

            FacturaModel facturaModel = getFactura();

            if (!facturaModel.isEmpty()) {

                //            if (facturaModel.getTroco() >= 0) {
                FacturaController controller = new FacturaController();
                Integer idUltimaFactura = controller.getLastId();
                if (controller.saveOrUpdate(facturaModel)) {

                    try {
                        facturaModel.setId(controller.getLastIdByUsuario(usuario));
//                    facturaModel.setId(controller.getLastIdByUsuario(usuario));

                       FacturaModel facturaAnterior =  controller.getById(idUltimaFactura);
                       String hashAnterior = "";
                       if(facturaAnterior != null){
                           
                           hashAnterior = facturaAnterior.getHash();
                           
                       }
                        String referencia = updateNumeracao(facturaModel.getId());
                        String hashcode = RSA.executeAlgRSA(facturaModel.getData(), referencia, String.valueOf(facturaModel.getTotalApagar()),hashAnterior);
                        String facturaRef = RSA.getValorCaracterHash(hashcode);

                        if (controller.updateHashReference(hashcode, facturaRef, facturaModel.getId())) {
                            updateNumeracao(facturaModel.getId());

                            for (int i = 0; i < tbPedido.getRowCount(); i++) {

                                FacturaItemModel item = (FacturaItemModel) tbPedido.getValueAt(i, 0);

                                FacturaItemModel facturaItemModel = new FacturaItemModel();
                                facturaItemModel.setFactura(facturaModel);
                                facturaItemModel.setProduto(item.getProduto());
                                facturaItemModel.setLote(tbPedido.getValueAt(i, 1).toString());
                                facturaItemModel.setQtd(Double.parseDouble(tbPedido.getValueAt(i, 2).toString()));
                                facturaItemModel.setPreco(getValueNormal(tbPedido.getValueAt(i, 3).toString()));
                                facturaItemModel.setIva(getValueNormal(tbPedido.getValueAt(i, 5).toString()));
                                facturaItemModel.setDesconto(getValueNormal(tbPedido.getValueAt(i, 6).toString()));
                                facturaItemModel.setTotal(getValueNormal(tbPedido.getValueAt(i, 7).toString()));
                                facturaItemModel.setSubTotal(getValueNormal(tbPedido.getValueAt(i, 4).toString()));

                                FacturaItemController fController = new FacturaItemController();
                                if (fController.saveOrUpdate(facturaItemModel)) {

                                    PedidoTesteController pController = new PedidoTesteController();
                                    pController.liquidar(idPedido);

                                    if (!facturaItemModel.getLote().trim().isEmpty()) {
                                        if (!chkPerfoma.isSelected()) {
                                            reduzirQtd(facturaItemModel.getLote(), facturaItemModel.getQtd());
                                        }

                                    }
                                    result = true;
                                } else {
                                    result = false;
                                    JOptionPane.showMessageDialog(this, "Ocorreu um erro regista o item da factura", "ERRO", JOptionPane.ERROR_MESSAGE);
                                    return;
                                }

                            }
                        }
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(FacturacaoNovaView.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvalidKeyException ex) {
                        Logger.getLogger(FacturacaoNovaView.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SignatureException ex) {
                        Logger.getLogger(FacturacaoNovaView.class.getName()).log(Level.SEVERE, null, ex);
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
                if (chkTermica.isSelected()) {
                    FacturaIreport.termica(facturaModel, tipoFactura(), true);
                } else if (chkA4.isSelected()) {
                    FacturaIreport.A4(facturaModel, tipoFactura(), true);
                } else {
                    FacturaIreport.A5(facturaModel, tipoFactura(), true);
                }
                FacturaIreport.pedidoCozinhaBalcao(facturaModel.getId());
                limpar();

            }
        } else {
            JOptionPane.showMessageDialog(this, "Adicione produto na factura", "Alerta", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnFacturarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        init2();
    }//GEN-LAST:event_btnBuscarActionPerformed
    private String tipoFactura() {

        String valor = cboFormaPagamento.getSelectedItem().toString();
        if (valor.equals("NUMERARIO")
                || valor.equals("MULTICAIXA")
                || valor.equals("PAGAMENTO DUPLO")) {
            return "Factura Recibo";
        } else if (valor.equals("CRÉDITO")) {
            return "Factura Crédito";
        } else {
            return "Factura Pró-forma";
        }
    }
    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:

        if (rdTermica.isSelected()) {
            FacturaIreport.termica(getSelectItemId(), tipoFactura(), false);
        } else if (rdA4.isSelected()) {
            FacturaIreport.A4(getSelectItemId(), tipoFactura(), false);
        } else if (rdA5.isSelected()) {
            FacturaIreport.A5(getSelectItemId(), tipoFactura(), false);
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void listaFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaFacturaMouseClicked
        // TODO add your handling code here:
        selectItem();
    }//GEN-LAST:event_listaFacturaMouseClicked

    public void selectItem() {

        DefaultListModel listDefault = (DefaultListModel) listaFactura.getModel();
        
        FacturaModel modelo = (FacturaModel) listDefault.getElementAt(listaFactura.getSelectedIndex());
        txtItemFactura.setText("Factura --------------------------> "+modelo.getNextFactura()+"\n"+
                               "Cliente --------------------------> "+modelo.getNomeCliente()+"\n"+
                               "Total a Pagar --------------------> "+modelo.getTotalApagar()+"\n"+
                               "Total de Desconto ----------------> "+modelo.getTotalDesconto()+"\n"+
                               "SubTotal -------------------------> "+modelo.getSubTotal()+"\n"+
                               "Valor Entregue -------------------> "+modelo.getValorEntregue()+"\n"+
                               "Valor Multicaixa -----------------> "+modelo.getValorMulticaixa()+"\n"+
                               "Troco ----------------------------> "+modelo.getTroco());

    }
    
    private void tbPedidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPedidoMouseClicked
        // TODO add your handling code here:
        int index = tbPedido.getSelectedRow();
        preco = getValueNormal(tbPedido.getValueAt(index, 3).toString());

        totalAnterior = getValueNormal(tbPedido.getValueAt(index, 4).toString());
        qtd = Double.parseDouble(tbPedido.getValueAt(index, 2).toString());
    }//GEN-LAST:event_tbPedidoMouseClicked

    private void tbPedidoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPedidoKeyReleased
        // TODO add your handling code here:
        int index = tbPedido.getSelectedRow();
        double qtdStock = 0;
        try {

            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

                if (index >= 0) {

                    FacturaItemModel modelo = (FacturaItemModel) tbPedido.getValueAt(index, 0);
                    ProdutoModel produto = modelo.getProduto();
                    String codBarra = tbPedido.getValueAt(index, 1).toString();

                    double preco = getValueNormal(tbPedido.getValueAt(index, 3).toString());
                    String descontoValue = tbPedido.getValueAt(index, 6).toString();

                    if (!descontoValue.contains("%")) {
                        descontoValue = String.valueOf(getValueNormal(descontoValue));
                    }

                    //                    double desconto = Double.parseDouble(tbPedido.getValueAt(index, 6).toString());
                    if (preco >= 0) {
                        //  double totalAnterior = Double.parseDouble(tbPedido.getValueAt(index, 4).toString());
                        double qtd = Double.parseDouble(tbPedido.getValueAt(index, 2).toString());
                        double desconto = getDesconto(descontoValue, preco * qtd);
                        if (qtd >= 1) {

                            if (produto.isStocavel()) {
                                if (!chkPerfoma.isSelected()) {
                                    qtdStock = quantidade(codBarra);
                                    if (qtd > qtdStock && verificarQtd(codBarra, qtd)) {
                                        JOptionPane.showMessageDialog(this, "Quantidade Indisponivel", "Alerta", JOptionPane.WARNING_MESSAGE);
                                        tbPedido.setValueAt(1, index, 2);
                                        descontarQtd(codBarra, qtd);
                                        return;
                                    }
                                }
                            }

                            double total = preco * qtd - desconto;
                            double iva = getValueNormal(tbPedido.getValueAt(index, 5).toString());
                            iva = this.totalAnterior <= 0 ? 0 : 14;
                            if (iva == 14) {
                                iva = (total * iva) / 100;
                            }
                            tbPedido.setValueAt(getValor(preco * qtd), index, 4);
                            tbPedido.setValueAt(getValor(preco), index, 3);
                            //  tbPedido.setValueAt(getValor(desconto), index, 6);

                            tbPedido.setValueAt(getValor(iva), index, 5);
                            tbPedido.setValueAt(getValor(desconto), index, 6);
                            tbPedido.setValueAt(getValor(iva + total), index, 7);

                            calcular();
                            //                            controlerQuant();
                        } else {
                            JOptionPane.showMessageDialog(this, "Quantidade e sempre maior que zero", "Alerta", JOptionPane.WARNING_MESSAGE);
                            tbPedido.setValueAt(getValor(this.preco), index, 3);
                            tbPedido.setValueAt(this.qtd, index, 2);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "O preco de um produto e sempre maior ou igual a zero", "Alerta", JOptionPane.WARNING_MESSAGE);

                        tbPedido.setValueAt(getValor(this.preco), index, 3);
                        tbPedido.setValueAt(this.qtd, index, 2);

                    }

                }
            }
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this, "Valor invalido", "Erro de Digitacao", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tbPedidoKeyReleased

    private void btnProfomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfomaActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        if (tbPedido.getRowCount() > 0) {
            boolean result = false;

            FacturaModel facturaModel = getFactura();

            if (!facturaModel.isEmpty()) {

                //            if (facturaModel.getTroco() >= 0) {
                FacturaController controller = new FacturaController();
                if (controller.saveOrUpdate(facturaModel)) {

                    facturaModel.setId(controller.getLastIdByUsuario(usuario));
                    updateNumeracao(facturaModel.getId());
                    for (int i = 0; i < tbPedido.getRowCount(); i++) {

                        FacturaItemModel item = (FacturaItemModel) tbPedido.getValueAt(i, 0);

                        FacturaItemModel facturaItemModel = new FacturaItemModel();
                        facturaItemModel.setFactura(facturaModel);
                        facturaItemModel.setProduto(item.getProduto());
                        facturaItemModel.setLote(tbPedido.getValueAt(i, 1).toString());
                        facturaItemModel.setQtd(Double.parseDouble(tbPedido.getValueAt(i, 2).toString()));
                        facturaItemModel.setPreco(getValueNormal(tbPedido.getValueAt(i, 3).toString()));
                        facturaItemModel.setIva(getValueNormal(tbPedido.getValueAt(i, 5).toString()));
                        facturaItemModel.setDesconto(getValueNormal(tbPedido.getValueAt(i, 6).toString()));
                        facturaItemModel.setTotal(getValueNormal(tbPedido.getValueAt(i, 7).toString()));
                        facturaItemModel.setSubTotal(getValueNormal(tbPedido.getValueAt(i, 4).toString()));

                        FacturaItemController fController = new FacturaItemController();
                        if (fController.saveOrUpdate(facturaItemModel)) {

                            if (!facturaItemModel.getLote().trim().isEmpty()) {
                                if (!chkPerfoma.isSelected()) {
                                    reduzirQtd(facturaItemModel.getLote(), facturaItemModel.getQtd());
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

                if (chkTermica.isSelected()) {
                    FacturaIreport.termica(facturaModel, tipoFactura(), true);
                } else if (chkA4.isSelected()) {
                    FacturaIreport.ProformaA4(facturaModel.getId());
                } else {
                    FacturaIreport.A5(facturaModel, tipoFactura(), true);
                }

                limpar();

            }
        } else {
            JOptionPane.showMessageDialog(this, "Adicione produto na factura", "Alerta", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnProfomaActionPerformed

    private void txtDescontoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescontoKeyReleased

        int index = txtDesconto.getText().trim().indexOf("%");
        String descontoString = txtDesconto.getText().trim().replaceAll("%", "");

        calcular();

        double descontoActual = getValueNormal(lblDesconto.getText());
        double subTotal = getValueNormal(lblSubTotal.getText());

        double total = 0;

        if (index >= 0) {

            descontoGlobal = (subTotal * Double.parseDouble(txtDesconto.getText().trim())) / 100;

        } else {
            descontoGlobal = Double.parseDouble(txtDesconto.getText().trim());
        }
        double totalDesconto = descontoActual + descontoGlobal;
        total = subTotal - totalDesconto;
        lblTotal.setText(getValor(total));
        lblDesconto.setText(getValor(totalDesconto));

        txtValorEntregue.setText("0");
        txtMulticaixa.setText("0");

    }//GEN-LAST:event_txtDescontoKeyReleased

    private void txtValorEntregueMoedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorEntregueMoedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorEntregueMoedaActionPerformed
    private void calcularCambio() {

        try {

            if (!txtMoedaCambio.getText().trim().isEmpty()
                    && !txtMoedaAOA.getText().trim().isEmpty()
                    && !txtValorEntregueMoeda.getText().trim().isEmpty()) {
                double moedaEstrangeira = Double.parseDouble(txtMoedaCambio.getText());
                double moedaAOA = Double.parseDouble(txtMoedaAOA.getText());
                double valorEntregue = Double.parseDouble(txtValorEntregueMoeda.getText());

                double valorConvertido = (moedaAOA * valorEntregue) / moedaEstrangeira;
                txtConvertido.setText(String.valueOf(valorConvertido));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Valor digitado esta no formato errado");
        }
    }
    private void btnCalcularCambioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularCambioActionPerformed
        // TODO add your handling code here:
        if (!txtConvertido.getText().trim().isEmpty()) {

            txtValorEntregue.setText(txtConvertido.getText());
            lblTroco.setText(getValor(calcularTroco()));

        } else {

            JOptionPane.showMessageDialog(this, "Não foi encontrado nenhum valor convertido para AOA");
        }
    }//GEN-LAST:event_btnCalcularCambioActionPerformed

    private void txtMoedaCambioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMoedaCambioKeyReleased
        // TODO add your handling code here:
        this.calcularCambio();
    }//GEN-LAST:event_txtMoedaCambioKeyReleased

    private void txtMoedaAOAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMoedaAOAKeyReleased
        // TODO add your handling code here:
        this.calcularCambio();
    }//GEN-LAST:event_txtMoedaAOAKeyReleased

    private void txtValorEntregueMoedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorEntregueMoedaKeyReleased
        // TODO add your handling code here:
        this.calcularCambio();
    }//GEN-LAST:event_txtValorEntregueMoedaKeyReleased

    private void txtPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisarActionPerformed

        if (!txtPesquisar.getText().isEmpty()) {

            EntradaStockItemController controller = new EntradaStockItemController();
            List<EntradaStockItemModel> stock = getStockProduto(txtPesquisar.getText());

            if (stock != null) {

                EntradaStockItemModel entrada = stock.get(0);
                this.adicionar(entrada);

                quantidade.setValue(1);
                txtPesquisar.setText("");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione o produto");
        }
    }//GEN-LAST:event_txtPesquisarActionPerformed
    private List<EntradaStockItemModel> getEntradaItem(String codBarra) {

        EntradaStockItemController controller = new EntradaStockItemController();
        return controller.getQtd(codBarra, idArmazem);

    }

    private void reduzirQtd(String codBarra, double qtd) {

        double qtdNova = qtd;
        List<EntradaStockItemModel> lista = getEntradaItem(codBarra);
        EntradaStockItemController controller = new EntradaStockItemController();
        for (EntradaStockItemModel e : lista) {

            if (idArmazem > 0) {
                if (idArmazem == e.getArmazem().getId()) {
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
            } else {
                JOptionPane.showMessageDialog(this, "Não foi selecionado armazem principal");
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
        lblTroco.setText("0");
        lblDesconto.setText("0");
        lblDesconto.setText("0");
        lblSubTotal.setText("0");
        lblTotal.setText("0");
        txtValorEntregue.setText("0");
        txtMulticaixa.setText("0");
//        inicializar();
    }

    private double calcularTroco() {

        if (!txtValorEntregue.getText().trim().isEmpty()
                && !txtMulticaixa.getText().trim().isEmpty()
                && !lblTotal.getText().trim().isEmpty()) {
            try {
                double v = Double.parseDouble(txtValorEntregue.getText()) + Double.parseDouble(txtMulticaixa.getText()) - getValueNormal(lblTotal.getText());
                return v;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return 0;
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
            java.util.logging.Logger.getLogger(FacturacaoNovaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FacturacaoNovaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FacturacaoNovaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FacturacaoNovaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FacturacaoNovaView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DataFim;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCalcularCambio;
    private javax.swing.JButton btnFacturar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnProfoma;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cboCliente;
    private javax.swing.JComboBox<String> cboFormaPagamento;
    private javax.swing.JComboBox<String> cboMoeda;
    private javax.swing.JCheckBox chkA4;
    private javax.swing.JCheckBox chkPerfoma;
    private javax.swing.JCheckBox chkTermica;
    private com.toedter.calendar.JDateChooser dataInicio;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblDesconto;
    private javax.swing.JLabel lblIVA;
    private javax.swing.JLabel lblSubTotal;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTroco;
    private javax.swing.JList<String> listaFactura;
    private javax.swing.JSpinner quantidade;
    private javax.swing.JCheckBox rdA4;
    private javax.swing.JCheckBox rdA5;
    private javax.swing.JCheckBox rdTermica;
    private javax.swing.JTable tbPedido;
    private javax.swing.JTable tbProduto;
    private javax.swing.JTextField txtCliente;
    private javax.swing.JTextField txtConvertido;
    private javax.swing.JTextField txtDesconto;
    private javax.swing.JTextArea txtItemFactura;
    private javax.swing.JTextField txtMoedaAOA;
    private javax.swing.JTextField txtMoedaCambio;
    private javax.swing.JTextField txtMulticaixa;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtValorEntregue;
    private javax.swing.JTextField txtValorEntregueMoeda;
    // End of variables declaration//GEN-END:variables
}
