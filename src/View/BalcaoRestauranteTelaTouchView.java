/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.CaixaController;
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
import Controller.PedidoItemController;
import Controller.PedidoTesteController;
import Controller.TaxaController;
import Ireport.CaixaIreport;
import Ireport.FacturaIreport;
import ItemTabela.ItemProduto;
import Model.CaixaModel;
import Model.ClienteModel;
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
import Util.Calculo;
import Util.CopyFileUtil;
import com.sun.prism.image.ViewPort;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JScrollPane;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;
import json_xml_iva.RSA;

/**
 *
 * @author celso & Emanuel
 */
public class BalcaoRestauranteTelaTouchView extends javax.swing.JPanel {

    /**
     * Creates new form BalcaoView
     */
    private UsuarioModel usuario;
    private DefaultTableModel tbDefault;
    private double qtdAntesClick;
    private double qtdAdicionada;
    private double preco = 0, totalAnterior = 0;
    private double qtd = 0;
    private double totalIVA = 0;
    private double descontoGlobal = 0;
    private double retencaoGlobal = 0;
    private boolean isAdicionado = false, isAdicionadoExist = false;
    private boolean enviarPedidoCozinha = true;
    private int idPedido = 0, idArmazem = 0;
    private CaixaModel caixaModel;
    private String tipofactura;
    private MesaModel mesa;
    private boolean permitirFecho = false;
    private JFrame pedidoMesaView;
    EntradaStockItemModel entradaDefault;
    JLabel qtdSelecionada;

    public BalcaoRestauranteTelaTouchView() {
        initComponents();
        this.setName("BalcaoView");

    }

    public BalcaoRestauranteTelaTouchView(UsuarioModel usuario, MesaModel mesa, JFrame pedidoMesaView) {
        initComponents();
        this.setName("BalcaoView");
        this.getName();
        this.usuario = usuario;
        this.mesa = mesa;
        this.pedidoMesaView = pedidoMesaView;
        inicializar();
        tbDefault = (DefaultTableModel) tbPedido.getModel();
        this.armazemPrincipal();
        this.moduloSistema();
        initCaixa();

        carregarPedido();

        // lblOperador.setText(usuario.getNome());
    }

    public BalcaoRestauranteTelaTouchView(UsuarioModel usuario, boolean permitirFecho, JFrame pedidoMesaView) {
        initComponents();
        this.setName("BalcaoView");
        this.permitirFecho = permitirFecho;
        this.pedidoMesaView = pedidoMesaView;
        this.usuario = usuario;
        inicializar();
        tbDefault = (DefaultTableModel) tbPedido.getModel();
        this.armazemPrincipal();
        this.moduloSistema();

        controladorCampo(permitirFecho);

        if (permitirFecho) {

            tabulacao.remove(3);
        }

        // lblOperador.setText(usuario.getNome());
    }

    public BalcaoRestauranteTelaTouchView(UsuarioModel usuario, MesaModel mesa, boolean permitirFecho, JFrame pedidoMesaView) {
        initComponents();
        this.setName("BalcaoView");
        this.usuario = usuario;
        this.pedidoMesaView = pedidoMesaView;
        inicializar();
        tbDefault = (DefaultTableModel) tbPedido.getModel();
        // lblOperador.setText(usuario.getNome());
        this.mesa = mesa;

        carregarPedido();
        this.armazemPrincipal();
        controladorCampo(permitirFecho);

        if (permitirFecho) {

            tabulacao.remove(3);
        }
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
            factura.setSubTotal(p.getSubTotal());
            factura.setRetencao(p.getRetencao());
            factura.setLote(p.getCoBarra());
            factura.setTotal(p.getTotal());
            tbDefault.addRow(new Object[]{
                factura,
                p.getCoBarra(),
                p.getQtd(),
                Calculo.converterCash(p.getPreco()),
                Calculo.converterCash(p.getSubTotal()),
                Calculo.converterCash(p.getIva()),
                Calculo.converterCash(p.getDesconto()),
                Calculo.converterCash(p.getRetencao()),
                Calculo.converterCash(p.getTotal())

            });
        }
        tbPedido.setModel(tbDefault);
        calcular();

    }

    private void initCaixa() {

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            CaixaController caixaController = new CaixaController();

//            caixaModel = caixaController.getByDateAndUsuario(dateFormat.format(new Date()), usuario.getId(), "Aberto");
            caixaModel = caixaController.getLastByUsuario(usuario.getId(), "Aberto");

            controladorCampo(caixaModel != null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void moduloSistema() {

        ParamentroController controller = new ParamentroController();
        ParamentroModel modelo = controller.getById(3); //MODULO DO SISTEMA  / 

        if (modelo.getValor() == 1) {//(1) - VENDA MAS

            enviarPedidoCozinha = false;

        } else if (modelo.getValor() == 2) {//(2) - MREST
            enviarPedidoCozinha = true;
            jScrollPane2.setVerticalScrollBar(jScrollBar1);
        }

    }

    private void armazemPrincipal() {

        ParamentroController controller = new ParamentroController();
        ParamentroModel modelo = controller.getById(6);

        if (modelo.getValor() > 0) {//remover

            idArmazem = modelo.getValor();

        } else {
            
            btnFacturar.setEnabled(false);
            btnImprimir.setEnabled(false);
            chkPerfoma.setEnabled(false);
            txtValorEntregue.setEnabled(false);
            txtMulticaixa.setEnabled(false);
            tbDefault.setRowCount(0);
            JOptionPane.showMessageDialog(this, "Não foi definido armazem Principal");
        }

    }

    public Moeda getMoeda() {
        Moeda moeda = (Moeda) cboMoeda.getSelectedItem();
        return moeda;
    }

    private void calcularCambio() {

        try {

            if (!txtValorEntregueMoeda.getText().trim().isEmpty()) {

//                double moedaEstrangeira = Double.parseDouble(txtMoedaCambio.getText());
//                double moedaAOA = Double.parseDouble(txtMoedaAOA.getText());
                double valorEntregue = Double.parseDouble(txtValorEntregueMoeda.getText());

                double valorConvertido = (getMoeda().getCambioDiario().getValor() * valorEntregue);
                txtConvertido.setText(String.valueOf(valorConvertido));
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Valor digitado esta no formato errado");
        }
    }

    private void inicializar() {

        carregarCliente();
        carregarFormaPagamento();
//        carregarProduto();
        carregarMoeda();

    }

    private void carregarCliente() {

//        ClienteController controller = new ClienteController();
//        List<ClienteModel> lista = controller.get();
//        cboCliente.setModel(new DefaultComboBoxModel(lista.toArray()));
    }

    public String getNextFacturaSimples(String tipoDoc, int nFact) {
        DocumentoController12 docController = new DocumentoController12();
        int last = docController.getLastInsertAno(tipoDoc);
        int next = last;
        FacturaController controller = new FacturaController();
        String serie = controller.getSerie().trim();

        System.out.println("serie :::: " + serie);

        return serie + DataComponent.getAnoActual() + "/" + next;
//        return "Z00" + DataComponent.getAnoActual() + "/" + nFact;
//        return "AGT" + DataComponent.getAnoActual() + "/" + next;
    }

    private String updateNumeracao(int numFacturaLast) {

        FormaPagamentoModel formaPagamento = (FormaPagamentoModel) cboFormaPagamento.getSelectedItem();
        String designacao, prefixo;

        if (chkPerfoma.isSelected()) {
            designacao = "PROFORMA";
            prefixo = "PP ";
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

    private void carregarFormaPagamento() {

        FormaPagamentoController controller = new FormaPagamentoController();
        List<FormaPagamentoModel> lista = controller.get();
        cboFormaPagamento.setModel(new DefaultComboBoxModel(lista.toArray()));

        selecionadoForma();
    }

    private void selecionadoForma() {

        FormaPagamentoModel selecionado = (FormaPagamentoModel) cboFormaPagamento.getSelectedItem();

        txtValorEntregue.setText("0");
        txtMulticaixa.setText("0");
        if (selecionado != null) {
//            txtValorEntregue.setEnabled(selecionado.isCash());
//            txtMulticaixa.setEnabled(selecionado.isMulticaixa());

            txtMulticaixa.setText(selecionado.isMulticaixa() ? String.valueOf(Calculo.getValueNormal(lblTotal.getText())) : "0");
            txtValorEntregue.setText(selecionado.isCash() ? txtValorEntregue.getText() : "0");

            txtMulticaixa.setFocusable(selecionado.isMulticaixa());
            txtValorEntregue.setFocusable(selecionado.isCash());
        }

    }

    private void carregarMoeda() {

        MoedaController controller = new MoedaController();
        List<Moeda> lista = controller.get();
        cboMoeda.setModel(new DefaultComboBoxModel(lista.toArray()));
    }

    public List<EntradaStockItemModel> getStockProduto() {

        EntradaStockItemController controller = new EntradaStockItemController();
        return controller.getStock(1);
    }

    public List<EntradaStockItemModel> getStockProduto(String pesquisar) {

        EntradaStockItemController controller = new EntradaStockItemController();
        return controller.getStock(idArmazem, pesquisar);
    }

    private void carregarProdutoByPesquisa() {

        //*************** LIMPA A TABELA ******************************
        //DefaultTableModel tbDefault = (DefaultTableModel) tbProduto.getModel();
        //tbDefault.setRowCount(0);
        //*************************************************************
        List<EntradaStockItemModel> stock = getStockProduto(txtPesquisar.getText());
        tbProduto.setLayout(new java.awt.GridLayout((stock.size() / 4), 4, 5, 5));//definir o numero de linhas de acordo ao numero de entradas retornadas
        tbProduto.removeAll();
        tbProduto.updateUI();
        
        for (EntradaStockItemModel e : stock) {

            crearProduto(e,false);
        }

    }

    public void crearProduto(EntradaStockItemModel e, boolean adicionarDirecto) {

        ItemProduto item = new ItemProduto();
        JScrollPane scroll = (JScrollPane) item.getComponent(0);
        JViewport jView = (JViewport) scroll.getComponent(0);
        JLabel designacao = (JLabel) jView.getComponent(0);
        JLabel imagem = (JLabel) item.getComponent(4);
        JLabel qtd = (JLabel) item.getComponent(2);
        JLabel preco = (JLabel) item.getComponent(1);
        JLabel btnAdicionar = (JLabel) item.getComponent(3);
        
        System.out.println("imagem >>> "+imagem.getWidth());

        btnAdicionar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                adicionarProduto(e, qtd);

            }
        });

        imagem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                adicionarProduto(e, qtd);
            }
        });
        item.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                adicionarProduto(e, qtd);
            }
        });
        
            File file = new File("Relatorio" + File.separator + "imagensProdutos" + File.separator + e.getProduto().getUrlImage());
            String pathFinal = file.getAbsolutePath();
            ImageIcon image = new ImageIcon(pathFinal);
            
        if(imagem.getWidth() == 0 || imagem.getHeight() == 0){
            
                imagem.setIcon(new ImageIcon(image.getImage().getScaledInstance(200, 141, Image.SCALE_DEFAULT)));
        }else{
                imagem.setIcon(new ImageIcon(image.getImage().getScaledInstance(imagem.getWidth(), imagem.getHeight(), Image.SCALE_DEFAULT)));
        }
        

        // tratamentos de imagem
//     
        btnAdicionar.setForeground(Color.white);
        preco.setText(Calculo.converterCash(e.getPrecoVenda()) + " Kz");
        qtd.setText(Calculo.converter(e.getQtd()) + " Unid");
        designacao.setText(e.getProduto().getDesignacao());
        tbProduto.add(item);
        tbProduto.updateUI();
        
        if(adicionarDirecto)
            adicionarProduto(e, qtd);
}

    public void adicionarProduto(EntradaStockItemModel e, JLabel lbQtd) {

        this.adicionar(e,lbQtd);
        ProdutoModel p = e.getProduto();
        if (isAdicionado && p.isStocavel()) {

//            if (!chkPerfoma.isSelected()) {
//                Double qtdActual = Calculo.getValueNormal(lbQtd.getText().replaceAll(" Unid", ""));
//                Double qtd = qtdActual - Double.parseDouble(quantidade.getValue().toString());
//                lbQtd.setText(String.valueOf(qtd) + " Unid");
//                lbQtd.updateUI();
//                tbProduto.updateUI();
//            }

        }

        lbQtd.updateUI();
        tbProduto.updateUI();
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
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        quantidade = new javax.swing.JSpinner();
        tabulacao = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbProduto = new javax.swing.JPanel();
        jScrollBar1 = new javax.swing.JScrollBar();
        jPanel9 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        cboMoeda = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtMoedaCambio = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        txtValorEntregueMoeda = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtConvertido = new javax.swing.JTextField();
        btnCalcularCambio = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        btnFecharCaixa = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        txtLocalEntrega = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtCarga = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        chkImprimirGuiaRemessa = new javax.swing.JCheckBox();
        jPanel15 = new javax.swing.JPanel();
        dataTransporte = new com.toedter.calendar.JDateChooser();
        hora = new javax.swing.JSpinner();
        minuto = new javax.swing.JSpinner();
        jLabel28 = new javax.swing.JLabel();
        chkImprimirGuiaTransporte = new javax.swing.JCheckBox();
        jLabel29 = new javax.swing.JLabel();
        txtObservacao = new javax.swing.JTextField();
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
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        lblSubTotal = new javax.swing.JLabel();
        lblIVA = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        lblTroco = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        btnFacturar = new javax.swing.JButton();
        cboFormaPagamento = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtValorEntregue = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtMulticaixa = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        chkPerfoma = new javax.swing.JCheckBox();
        jPanel7 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnMesas = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPedido = new javax.swing.JTable();
        btnRemover = new javax.swing.JButton();
        jPanel16 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        chkTermica = new javax.swing.JCheckBox();
        chkA4 = new javax.swing.JCheckBox();
        chkCarta = new javax.swing.JCheckBox();
        chkA5 = new javax.swing.JCheckBox();

        setName("BalcaoView"); // NOI18N
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                formKeyTyped(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setBackground(new java.awt.Color(30, 19, 0));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Buscar");

        txtPesquisar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtPesquisarCaretUpdate(evt);
            }
        });
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

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Qtd");

        quantidade.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/mauro.png"))); // NOI18N

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        tbProduto.setLayout(new java.awt.GridLayout(20, 4, 5, 5));
        jScrollPane2.setViewportView(tbProduto);

        jScrollBar1.setUnitIncrement(16);
        jScrollBar1.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
            public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                jScrollBar1AdjustmentValueChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 599, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                    .addComponent(jScrollBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        tabulacao.addTab("Produto", jPanel8);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/mauro.png"))); // NOI18N

        cboMoeda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMoedaActionPerformed(evt);
            }
        });

        jLabel18.setText("Moeda");

        jLabel19.setText("Cambio(Moeda Estrangeira)");

        txtMoedaCambio.setEditable(false);
        txtMoedaCambio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMoedaCambioKeyReleased(evt);
            }
        });

        jLabel26.setText("Valor Entregue (Moeda Estrangeira )");

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

        jLabel20.setText("Valor Convertido em AOA");

        txtConvertido.setEnabled(false);

        btnCalcularCambio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red.png"))); // NOI18N
        btnCalcularCambio.setText("Aplicar");
        btnCalcularCambio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularCambioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 1024, Short.MAX_VALUE)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMoedaCambio, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboMoeda, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtValorEntregueMoeda, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtConvertido, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnCalcularCambio))
                    .addComponent(jLabel20))
                .addContainerGap(468, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel23)
                .addGap(27, 27, 27)
                .addComponent(jLabel18)
                .addGap(7, 7, 7)
                .addComponent(cboMoeda, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addGap(9, 9, 9)
                .addComponent(txtMoedaCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addContainerGap(214, Short.MAX_VALUE))
        );

        tabulacao.addTab("Cambio", jPanel9);

        btnFecharCaixa.setText("Fechar o Caixa");
        btnFecharCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharCaixaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(btnFecharCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(btnFecharCaixa)
                .addContainerGap(506, Short.MAX_VALUE))
        );

        tabulacao.addTab("Caixa", jPanel12);

        jPanel14.setBorder(javax.swing.BorderFactory.createTitledBorder("Informação da(s) Guia(s)"));

        txtLocalEntrega.setEnabled(false);

        jLabel25.setText("Local Entrega");

        txtCarga.setEnabled(false);

        jLabel27.setText("Local de Carga");

        buttonGroup3.add(chkImprimirGuiaRemessa);
        chkImprimirGuiaRemessa.setText("Imprimir  Remessa ?");
        chkImprimirGuiaRemessa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkImprimirGuiaRemessaActionPerformed(evt);
            }
        });

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Transporte"));

        dataTransporte.setEnabled(false);

        hora.setModel(new javax.swing.SpinnerNumberModel(0, 0, 23, 1));
        hora.setEnabled(false);

        minuto.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));
        minuto.setEnabled(false);

        jLabel28.setText(":");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dataTransporte, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hora, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(minuto, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dataTransporte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hora)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(minuto))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        buttonGroup3.add(chkImprimirGuiaTransporte);
        chkImprimirGuiaTransporte.setText("Imprimir Transporte ?");
        chkImprimirGuiaTransporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkImprimirGuiaTransporteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCarga)
                    .addComponent(txtLocalEntrega)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(chkImprimirGuiaRemessa)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(chkImprimirGuiaTransporte))
                            .addComponent(jLabel27)
                            .addComponent(jLabel25))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLocalEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel27)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkImprimirGuiaRemessa)
                    .addComponent(chkImprimirGuiaTransporte))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jLabel29.setText("Observação da factura");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel29)
                    .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtObservacao))
                .addContainerGap(164, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabulacao.addTab("Outras Informações", jPanel13);

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

        buttonGroup2.add(rdA4);
        rdA4.setText("A4");

        buttonGroup2.add(rdTermica);
        rdTermica.setSelected(true);
        rdTermica.setText("Termica");

        buttonGroup2.add(rdA5);
        rdA5.setText("A5");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 615, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                    .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                            .addComponent(btnBuscar)
                            .addComponent(DataFim, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dataInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImprimir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdA4)
                    .addComponent(rdTermica)
                    .addComponent(rdA5))
                .addContainerGap())
        );

        jScrollPane3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

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
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)))
        );

        tabulacao.addTab("Reemprimir - Factura", jPanel10);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(tabulacao, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabulacao, javax.swing.GroupLayout.PREFERRED_SIZE, 621, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(83, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.LINE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(30, 19, 0));

        lblSubTotal.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblSubTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblSubTotal.setText("0.0");

        lblIVA.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblIVA.setForeground(new java.awt.Color(255, 255, 255));
        lblIVA.setText("0.0");

        lblTotal.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTotal.setText("0.0");

        lblTroco.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblTroco.setForeground(new java.awt.Color(255, 255, 255));
        lblTroco.setText("0.0");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Sub.Total");

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("IVA");

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Troco");

        btnFacturar.setBackground(new java.awt.Color(255, 255, 255));
        btnFacturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/faturaç╞o.png"))); // NOI18N
        btnFacturar.setText("Facturar");
        btnFacturar.setFocusable(false);
        btnFacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturarActionPerformed(evt);
            }
        });
        btnFacturar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btnFacturarKeyTyped(evt);
            }
        });

        cboFormaPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Numerário", "Multicaixa", "Depósito", "Transferência", "P.Duplo" }));
        cboFormaPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFormaPagamentoActionPerformed(evt);
            }
        });

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Forma");

        txtValorEntregue.setText("0");
        txtValorEntregue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValorEntregueKeyReleased(evt);
            }
        });

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Cash");

        txtMulticaixa.setText("0");
        txtMulticaixa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMulticaixaKeyReleased(evt);
            }
        });

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Multicaixa");

        chkPerfoma.setBackground(new java.awt.Color(255, 255, 255));
        chkPerfoma.setForeground(new java.awt.Color(254, 254, 254));
        chkPerfoma.setText("Performa");
        chkPerfoma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPerfomaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtValorEntregue, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                            .addComponent(txtMulticaixa))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 271, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblIVA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnFacturar)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTroco, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(chkPerfoma, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSubTotal)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblIVA)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTroco, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnFacturar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addComponent(chkPerfoma)
                        .addGap(29, 29, 29))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cboFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtValorEntregue, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMulticaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel3.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jButton1.setText("Enviar Pedido");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Imprimir Conta");

        jButton3.setText("TransFerir Pedido");

        btnMesas.setText("Mesas");
        btnMesas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMesasActionPerformed(evt);
            }
        });

        tbPedido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Produto", "Cod.Barra", "Qtd", "Preço", "Sub.Total", "IVA", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, false, false, false
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

        btnRemover.setBackground(new java.awt.Color(255, 255, 255));
        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/cancel_40px.png"))); // NOI18N
        btnRemover.setText("Remover Produto");
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMesas, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(194, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMesas, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemover))
                .addGap(17, 17, 17))
        );

        jPanel3.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(30, 19, 0));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        chkTermica.setBackground(java.awt.Color.white);
        buttonGroup1.add(chkTermica);
        chkTermica.setForeground(new java.awt.Color(254, 254, 254));
        chkTermica.setSelected(true);
        chkTermica.setText("Ticket");
        chkTermica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTermicaActionPerformed(evt);
            }
        });

        chkA4.setBackground(java.awt.Color.white);
        buttonGroup1.add(chkA4);
        chkA4.setForeground(new java.awt.Color(254, 254, 254));
        chkA4.setText("A4");

        chkCarta.setForeground(new java.awt.Color(254, 254, 254));
        chkCarta.setText("Carta de Garantia");

        buttonGroup1.add(chkA5);
        chkA5.setForeground(new java.awt.Color(254, 254, 254));
        chkA5.setText("A5");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkTermica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkA4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkA5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(chkCarta))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkTermica)
                    .addComponent(chkA4)
                    .addComponent(chkCarta)
                    .addComponent(chkA5))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addGap(0, 652, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel16, java.awt.BorderLayout.PAGE_END);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void chkPerfomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPerfomaActionPerformed
        // TODO add your handling code here:
        tbDefault.setRowCount(0);
        cboFormaPagamento.setSelectedIndex(0);
        limpar();
    }//GEN-LAST:event_chkPerfomaActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        if (!txtPesquisar.getText().trim().isEmpty()) {
            carregarProdutoByPesquisa();
        }
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void cboFormaPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFormaPagamentoActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        selecionadoForma();


    }//GEN-LAST:event_cboFormaPagamentoActionPerformed

    private void addItemExistente(FacturaItemModel factura, int index, ProdutoModel produto, Taxa ipc) {

        factura.setQtd(factura.getQtd() + Double.parseDouble(tbPedido.getValueAt(index, 2).toString()));
        double preco = Calculo.getValueNormal(tbPedido.getValueAt(index, 3).toString());
        double ipcValor = 0;
        double desconto = Calculo.getValueNormal(tbPedido.getValueAt(index, 6).toString());
        factura.setPreco(preco);
        factura.setDesconto(desconto);
        factura.setSubTotal(factura.getQtd() * factura.getPreco());
        tbPedido.setValueAt(factura.getQtd(), index, 2);
        tbPedido.setValueAt(factura.getSubTotal(), index, 4);
        if (produto.isIpc()) {

            ipcValor = factura.getSubTotal() * ipc.getTaxa() / 100;
            factura.setIva(ipcValor);

        }
        factura.setTotal(factura.getSubTotal() + ipcValor - factura.getDesconto());
        // insere envia o pedido para ser atualizado
        insertPedidoItem(factura);
        tbPedido.setValueAt(Calculo.converterCash(factura.getIva()), index, 5);
        tbPedido.setValueAt(Calculo.converterCash(factura.getTotal()), index, 8);

    }


    private void txtValorEntregueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorEntregueKeyReleased
        // TODO add your handling code here:
        lblTroco.setText(Calculo.converterCash(calcularTroco()));
    }//GEN-LAST:event_txtValorEntregueKeyReleased

    private void txtMulticaixaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMulticaixaKeyReleased
        // TODO add your handling code here:
        lblTroco.setText(Calculo.converterCash(calcularTroco()));
    }//GEN-LAST:event_txtMulticaixaKeyReleased
    private double calcularTroco() {

        if (!txtValorEntregue.getText().trim().isEmpty()
                && !txtMulticaixa.getText().trim().isEmpty()
                && !lblTotal.getText().trim().isEmpty()) {
            try {
                double v = Double.parseDouble(txtValorEntregue.getText()) + Double.parseDouble(txtMulticaixa.getText()) - Calculo.getValueNormal(lblTotal.getText());
                return v;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }    private float quantidade(String lote) {

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

    private void insertPedido() {

        PedidoModel pedido = new PedidoModel();
        pedido.setMesa(mesa);

        String nome = "DIVERSO";
        pedido.setNome(nome);
        pedido.setData(DataComponent.getDataActual());
        EstadoModel estado = new EstadoModel(1, "");
        pedido.setEstado(estado);

        PedidoTesteController controller = new PedidoTesteController();
        controller.save(pedido);

    }

    private boolean insertPedidoItem(FacturaItemModel model) {

        if (idPedido <= 0) {
            insertPedido();
        }

        PedidoModel pedido = new PedidoModel(getLastIdPedido());

        if (pedido.getId() > 0) {

            idPedido = pedido.getId();
//            int row = tbProduto.getRowCount();
//            if (row >= 0) {

            PedidoItemTesteModel item = new PedidoItemTesteModel();

            System.out.println("quantidade nova " + model.getQtd());
            item.setPedido(pedido);
            item.setProduto(model.getProduto());
            item.setCoBarra(model.getLote());
            item.setQtd(model.getQtd());
            item.setPreco(model.getPreco());
            item.setIva(model.getIva());
            item.setDesconto(model.getDesconto());
            item.setSubTotal(model.getSubTotal());
            item.setRetencao(model.getRetencao());
            item.setTotal(model.getTotal());
            item.setEstado(new EstadoModel(1, ""));

            item.setUsuario(usuario);

            PedidoItemController controller = new PedidoItemController();
            item.setId(controller.get(mesa.getId(), item.getProduto().getId()).getId());
            return controller.saveOrUpdate(item);
//            }
        }
        return false;
    }

    private void adicionar(EntradaStockItemModel entrada, JLabel lbQtd) {

        ProdutoModel produto = entrada.getProduto();
        FacturaItemModel model = new FacturaItemModel();

        model.setProduto(produto);
        model.setPreco(produto.getValorVenda());
        model.setQtd(Double.parseDouble(quantidade.getValue().toString()));
        model.setSubTotal(model.getQtd() * model.getPreco());

        TaxaController taxaController = new TaxaController();
        Taxa ipc = taxaController.getById(produto.getTaxa().getId());

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
                model.setLote(entrada.getCodBarra());
                if (qtd - qtdAdicionada > 0) {

                    totalIVA += model.getIva();
                    if (index == -1) {

                        if (model.getQtd() <= qtd) {
                            reduzirQtd(qtd - model.getQtd(), lbQtd); // reduz da label o valor que vai ser adicionado
                            if (insertPedidoItem(model)) {

                                tbDefault.addRow(new Object[]{
                                    model,
                                    entrada.getCodBarra(),
                                    model.getQtd(),
                                    Calculo.converterCash(entrada.getPrecoVenda()),
                                    Calculo.converterCash(model.getSubTotal()),
                                    Calculo.converterCash(model.getIva()),
                                    Calculo.converterCash(model.getDesconto()),
                                    Calculo.converterCash(model.getRetencao()),
                                    Calculo.converterCash(model.getTotal())

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
                            reduzirQtd(qtd - qtdTotal, lbQtd); // reduz da label o valor que vai ser adicionado
                            addItemExistente(model, index, produto, ipc);
                            calcular();
                            isAdicionado = true;
                        } else {
                            isAdicionado = false;
                            JOptionPane.showMessageDialog(this, "Quantidade Indispunivel no Stock");
                        }
                    }
                } else {
                    reduzirQtd(0, lbQtd); // reduz da label o valor que vai ser adicionado
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
                totalIVA += model.getIva();
                model.setLote(entrada.getCodBarra());
                if (insertPedidoItem(model)) {
                    tbDefault.addRow(new Object[]{
                        model,
                        entrada.getCodBarra(),
                        model.getQtd(),
                        Calculo.converterCash(model.getPreco()),
                        Calculo.converterCash(model.getSubTotal()),
                        Calculo.converterCash(model.getIva()),
                        Calculo.converterCash(model.getDesconto()),
                        Calculo.converterCash(model.getRetencao()),
                        Calculo.converterCash(model.getTotal())

                    });
                    calcular();
                    isAdicionado = true;
                }
            } else {
                addItemExistente(model, index, produto, ipc);
                calcular();
                isAdicionado = true;
            }
        }
//        controlerQuant();

    }
    
    public void reduzirQtd(double qtd, JLabel lbQtd){
        lbQtd.setText(String.valueOf(qtd) + " Unid");
        lbQtd.updateUI();
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

        double subTotal = 0, desconto = 0, iva = 0, total = 0, retencao = 0;

        for (int i = 0; i < tbPedido.getRowCount(); i++) {

            retencao += 0;

            subTotal += Calculo.getValueNormal(tbPedido.getValueAt(i, 4).toString());
            total += Calculo.getValueNormal(tbPedido.getValueAt(i, 6).toString());
            desconto += 0;

            iva += Calculo.getValueNormal(tbPedido.getValueAt(i, 5).toString());
            //subTotal += Double.parseDouble(tbPedido.getValueAt(i, 3).toString());

        }
        if (desconto == 0.0) {
            desconto = new Double(000);
        }
        lblTroco.setText(Calculo.converterCash(new Double(000)));
        lblSubTotal.setText(Calculo.converterCash(subTotal));
        lblTotal.setText(Calculo.converterCash(total));
        lblIVA.setText(Calculo.converterCash(iva));

    }

    private FacturaModel getFactura() {

        FacturaModel facturaModel = new FacturaModel();

        facturaModel.setFormaPagamento((FormaPagamentoModel) cboFormaPagamento.getSelectedItem());

        Moeda moeda = (Moeda) cboMoeda.getSelectedItem();
        facturaModel.setMoeda(moeda);
        facturaModel.setEstado(new EstadoModel(1, ""));
        facturaModel.setData(DataComponent.getDataActual());
        facturaModel.setUsuario(usuario);
        facturaModel.setTroco(Calculo.getValueNormal(lblTroco.getText()));
        facturaModel.setValorEntregue(Double.parseDouble(txtValorEntregue.getText()));
        facturaModel.setValorMulticaixa(Double.parseDouble(txtMulticaixa.getText()));
        facturaModel.setTotalApagar(Calculo.getValueNormal(lblTotal.getText()));
        facturaModel.setSubTotal(Calculo.getValueNormal(lblSubTotal.getText()));
        facturaModel.setTotalIVA(Calculo.getValueNormal(lblIVA.getText()));
        facturaModel.setTotalDesconto(descontoGlobal);
        facturaModel.setTotalRetencao(0);

        facturaModel.setLocalDescarga(txtLocalEntrega.getText());
        facturaModel.setLocalCarga(txtCarga.getText());
        facturaModel.setObs(txtObservacao.getText());

        if (chkImprimirGuiaTransporte.isSelected()) {

            Date dataEntrega = this.dataTransporte.getDate();

            Integer hora = Integer.parseInt(String.valueOf(this.hora.getValue()));
            Integer minuto = Integer.parseInt(String.valueOf(this.minuto.getValue()));

            facturaModel.setDataTransporte(DataComponent.getData(dataTransporte, hora, minuto));

        }

        facturaModel.setCaixaModel(caixaModel);

        ParamentroController paramentroController = new ParamentroController();
        ParamentroModel moduloFormacao = paramentroController.getById(7);
        facturaModel.setCriada_modulo_formacao(moduloFormacao.getValor() == 1);

        if (!txtMoedaCambio.getText().isEmpty()) {

            facturaModel.setCambio(Double.parseDouble(txtMoedaCambio.getText()));
        }

        if (!txtValorEntregueMoeda.getText().isEmpty()) {

            facturaModel.setValorMoedaEstrangeiro(Double.parseDouble(txtValorEntregueMoeda.getText()));
        }

        FormaPagamentoModel forma = facturaModel.getFormaPagamento();
        String tipo = chkPerfoma.isSelected() ? "Perfoma" : forma.getDesignacao().equals("CRÉDITO") ? "Venda Credito" : "Venda";

        facturaModel.setTipoFacturas(tipo);
        facturaModel.setCliente(new ClienteModel(1, "DIVERSO"));
        facturaModel.setNomeCliente("DIVERSO");

        if (!facturaModel.isEmpty()) {

            if (facturaModel.getFormaPagamento().isCash() || facturaModel.getFormaPagamento().isMulticaixa()) {
                if (Calculo.getValueNormal(lblTroco.getText()) < 0 && !chkPerfoma.isSelected()) {
                    JOptionPane.showMessageDialog(this, "Valor pago insuficiente");
                    return null;
                }
            }
        }
        return facturaModel;
    }

    int lastIdFactura(FacturaController controller, FacturaModel factura) {

        if (chkPerfoma.isSelected()) {

            return controller.getLastIdFP();
        } else if (factura.getTipoFacturas().equals("Venda Credito")) {

            return controller.getLastIdFT();
        }
        return controller.getLastIdFR();
    }

    public static boolean DataConforme() // verifica se a data actual é maior ou igual que a data da ultima factura
    {
        FacturaController facturaController = new FacturaController();
        return DataComponent.compareDataLastFactura(facturaController.getDataLastFactura());
    }

    public void alteraCarteiraCliente(FacturaModel facturaModel) {

        ClienteModel cliente = new ClienteModel(1, "DIVERSO");

        if (cliente != null) {

            if (!cliente.getNome().trim().equals("DIVERSO")) {

                // Atribuir o valor da divida na carteira do cliente
                FormaPagamentoModel forma = (FormaPagamentoModel) cboFormaPagamento.getSelectedItem();

                if (forma != null && !chkPerfoma.isSelected()) {

                    if (forma.getDesignacao().equals("CRÉDITO")) {

                        double saldoCliente = cliente.getValorCarteira();
                        saldoCliente = saldoCliente - facturaModel.getTotalApagar();
                        cliente.setValorCarteira(saldoCliente);
                        ClienteController clienteController = new ClienteController();
                        clienteController.updateCarteira(cliente);
                    }
                }

            }
        }
    }

    private void btnFacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturarActionPerformed
        // TODO add your handling code here:

        if (DataConforme()) {

            if (tbPedido.getRowCount() > 0) {

                boolean resultGuia = true;

                if (chkImprimirGuiaRemessa.isSelected() || chkImprimirGuiaTransporte.isSelected()) {

                    if (txtCarga.getText().trim().isEmpty() || txtLocalEntrega.getText().trim().isEmpty()) {

                        resultGuia = false;
                    }

                    if (chkImprimirGuiaTransporte.isSelected()) {

                        if (dataTransporte.getDate() == null || hora.getValue() == null || minuto == null) {
                            resultGuia = false;
                        }
                    }

                }

                if (resultGuia) {

                    boolean result = false;

                    FacturaModel facturaModel = getFactura();

                    if (!facturaModel.isEmpty()) {

                        ClienteModel cliente = new ClienteModel(1, "DIVERSO");

                        if (cliente != null) {

                            if (cliente.getNome().trim().equals("DIVERSO")) {

                                FormaPagamentoModel forma = (FormaPagamentoModel) cboFormaPagamento.getSelectedItem();

                                if (forma != null && !chkPerfoma.isSelected()) {

                                    if (forma.getDesignacao().equals("CRÉDITO")) {

                                        JOptionPane.showMessageDialog(this, "Não é possivel fazer venda a credito com cliente diverso",
                                                "Aviso", JOptionPane.WARNING_MESSAGE);
                                        return;
                                    }
                                }
                            }
                        }

                        if (facturaModel.getTipoFacturas().equals("Venda") && !facturaModel.isValorValido()) {

                            JOptionPane.showMessageDialog(this, "Valor entregue insuficiente");
                            return;
                        }
                        //            if (facturaModel.getTroco() >= 0) {
                        FacturaController controller = new FacturaController();

                        //                Integer idUltimaFactura = controller.getLastId();
                        Integer idUltimaFactura = lastIdFactura(controller, facturaModel);
                        Integer idSerie = controller.getIdSerie();
                        facturaModel.setIdSerie(idSerie);
                        if (controller.saveOrUpdate(facturaModel)) {

                            try {

                                alteraCarteiraCliente(facturaModel); // Actualizar o valor da carteira do cliente se for venda a crédito

                                facturaModel.setId(controller.getLastIdByUsuario(usuario));
                                //                    String hashcode = SHA.getSHA(idUltimaFactura);
                                //                    String hashcode = SHA.getSHA(facturaModel.getId());
                                //                    String facturaRef = SHA.getValorCaracterHash(hashcode);
                                FacturaModel facturaAnterior = controller.getById(idUltimaFactura);
                                String hashAnterior = null;
                                if (facturaAnterior != null) {

                                    hashAnterior = facturaAnterior.getHash();

                                }

                                String referencia = updateNumeracao(facturaModel.getId());
                                String hashcode = RSA.executeAlgRSA(facturaModel.getData(), referencia, String.valueOf(facturaModel.getTotalApagar()), hashAnterior);
                                String facturaRef = RSA.getValorCaracterHash(hashcode);

                                if (controller.updateHashReference(hashcode, facturaRef, facturaModel.getId()));
                                {

                                    for (int i = 0; i < tbPedido.getRowCount(); i++) {

                                        FacturaItemModel item = (FacturaItemModel) tbPedido.getValueAt(i, 0);

                                        FacturaItemModel facturaItemModel = new FacturaItemModel();
                                        facturaItemModel.setFactura(facturaModel);
                                        facturaItemModel.setProduto(item.getProduto());
                                        facturaItemModel.setLote(tbPedido.getValueAt(i, 1).toString());
                                        facturaItemModel.setQtd(Double.parseDouble(tbPedido.getValueAt(i, 2).toString()));
                                        facturaItemModel.setPreco(Calculo.getValueNormal(tbPedido.getValueAt(i, 3).toString()));
                                        facturaItemModel.setSubTotal(Calculo.getValueNormal(tbPedido.getValueAt(i, 4).toString()));
                                        facturaItemModel.setIva(Calculo.getValueNormal(tbPedido.getValueAt(i, 5).toString()));
                                        facturaItemModel.setTotal(Calculo.getValueNormal(tbPedido.getValueAt(i, 6).toString()));
                                        facturaItemModel.setDesconto(0);
                                        facturaItemModel.setRetencao(0);

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
                                }
                            } catch (NoSuchAlgorithmException ex) {
                                Logger.getLogger(BalcaoRestauranteTelaTouchView.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InvalidKeyException ex) {
                                Logger.getLogger(BalcaoRestauranteTelaTouchView.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SignatureException ex) {
                                Logger.getLogger(BalcaoRestauranteTelaTouchView.class.getName()).log(Level.SEVERE, null, ex);
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

                        if (chkPerfoma.isSelected()) {
                            FacturaIreport.performa(facturaModel, tipoFactura(), true);
                        } else {
                            
                            if (chkTermica.isSelected()) {
                                JOptionPane.showMessageDialog(this, "Venda efectuada com sucesso termica");
                                FacturaIreport.termica(facturaModel, tipoFactura(), true);
                            } else if (chkA4.isSelected()) {
                                FacturaIreport.A4(facturaModel, tipoFactura(), true);
                            } else if (chkA5.isSelected()) {
                                FacturaIreport.A5(facturaModel, tipoFactura(), true);
                            }

                        }
                        if (enviarPedidoCozinha) {
                            //FacturaIreport.pedidoCozinhaBalcao(facturaModel.getId());
                        }
                        if (chkCarta.isSelected()) {
                            FacturaIreport.CartaGarantiaA4(facturaModel.getId());
                        }
                        if (chkImprimirGuiaRemessa.isSelected()) {
                            FacturaIreport.GuiaRemessaA4(facturaModel.getId(), true);
                        }
                        if (chkImprimirGuiaTransporte.isSelected()) {
                            FacturaIreport.GuiaTransporteA4(facturaModel.getId(), true);
                        }

                        liquidarPedido();
                        limpar();
                        limparGuia();
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Preencha a inofrmação da guia", "Alerta", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Adicione produto na factura", "Alerta", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Acerte a data do seu Computador");
        }
    }//GEN-LAST:event_btnFacturarActionPerformed

    public void liquidarPedido() {
        PedidoTesteController pedido = new PedidoTesteController();
        if (pedido.liquidar(idPedido)) {
            idPedido = 0;
        }
    }

    private String tipofacturaByTipo() {

        if (tipofactura.trim().equals("Venda")) {
            return "Factura/Recibo";
        } else if (tipofactura.trim().equals("Perfoma")) {
            return "Factura Pró-forma";
        } else {
            return "Factura Crédito";
        }
    }

    private String tipoFactura() {

        String valor = cboFormaPagamento.getSelectedItem().toString();
        if (valor.equals("NUMERARIO")
                || valor.equals("MULTICAIXA")
                || valor.equals("PAGAMENTO DUPLO")) {
            return "Factura/Recibo";
        } else if (chkPerfoma.isSelected()) {
            return "Factura Pró-forma";
        } else if (valor.equals("CRÉDITO")) {
            return "Factura Crédito";
        }
        return null;
    }

    private void limpar() {

        tbDefault.setRowCount(0);

        //   txtMulticaixa.setText("0");
        quantidade.setValue(1);

        //   txtValorEntregue.setText("0");
        txtPesquisar.setText("");
        txtObservacao.setText(" ");
        lblTroco.setText("0");
        lblSubTotal.setText("0");
        lblTotal.setText("0");
        lblIVA.setText("0");
        txtValorEntregue.setText("0");
        txtMulticaixa.setText("0");
//        inicializar();
    }

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
    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        init2();
    }//GEN-LAST:event_btnBuscarActionPerformed
    public void init2() {

        FacturaController controller = new FacturaController();
        List<FacturaModel> lista = controller.getFacturaData(DataComponent.getData(dataInicio), DataComponent.getData(DataFim));

        DefaultListModel listDefault = new DefaultListModel();
        for (FacturaModel f : lista) {

            listDefault.addElement(f);
        }
        listaFactura.setModel(listDefault);
    }

    public FacturaModel getSelectItemId() throws Exception {

        try {
            DefaultListModel listDefault = (DefaultListModel) listaFactura.getModel();

            FacturaModel modelo = (FacturaModel) listDefault.getElementAt(listaFactura.getSelectedIndex());

            return modelo;
        } catch (Exception ex) {

            throw ex;
        }

    }

    public boolean isFacturaProforma() throws Exception {

        try {
            DefaultListModel listDefault = (DefaultListModel) listaFactura.getModel();

            FacturaModel modelo = (FacturaModel) listDefault.getElementAt(listaFactura.getSelectedIndex());

            return modelo.getTipoFacturas().equalsIgnoreCase("Perfoma");

        } catch (Exception ex) {

            throw ex;
        }

    }
    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // TODO add your handling code here:

        try {

            if (isFacturaProforma()) {
                FacturaIreport.performa(getSelectItemId(), tipofacturaByTipo(), false);
            } else {
                if (rdTermica.isSelected()) {
                    FacturaIreport.termica(getSelectItemId(), tipofacturaByTipo(), false);
                } else if (rdA4.isSelected()) {
                    FacturaIreport.A4(getSelectItemId(), tipofacturaByTipo(), false);
                } else if (rdA5.isSelected()) {
                    FacturaIreport.A5(getSelectItemId(), tipofacturaByTipo(), false);
                }
            }
        } catch (Exception ex) {

            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void listaFacturaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaFacturaMouseClicked
        // TODO add your handling code here:
        SelectItem();
    }//GEN-LAST:event_listaFacturaMouseClicked

    public void SelectItem() {

        DefaultListModel listDefault = (DefaultListModel) listaFactura.getModel();

        FacturaModel modelo = (FacturaModel) listDefault.getElementAt(listaFactura.getSelectedIndex());
        tipofactura = modelo.getTipoFacturas();
        txtItemFactura.setText("Factura --------------------------> " + modelo.getNextFactura() + "\n"
                + "Cliente --------------------------> " + modelo.getNomeCliente() + "\n"
                + "Total a Pagar --------------------> " + modelo.getTotalApagar() + "\n"
                + "Total de Desconto ----------------> " + modelo.getTotalDesconto() + "\n"
                + "SubTotal -------------------------> " + modelo.getSubTotal() + "\n"
                + "Valor Entregue -------------------> " + modelo.getValorEntregue() + "\n"
                + "Valor Multicaixa -----------------> " + modelo.getValorMulticaixa() + "\n"
                + "Troco ----------------------------> " + modelo.getTroco());

    }

    private void tbPedidoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPedidoMouseClicked
        // TODO add your handling code here:
        int index = tbPedido.getSelectedRow();

        preco = Calculo.getValueNormal(tbPedido.getValueAt(index, 3).toString());

        totalAnterior = Calculo.getValueNormal(tbPedido.getValueAt(index, 4).toString());
        qtd = Double.parseDouble(tbPedido.getValueAt(index, 2).toString());
    }//GEN-LAST:event_tbPedidoMouseClicked
    private boolean verificarQtd(String codBarraQtd, double qtdRetirada) {

//            ProdutoModel aux = (ProdutoModel) tbProduto.getValueAt(i, 0);
        double qtd = Calculo.getValueNormal(this.qtdSelecionada.getText()) - qtdRetirada;
        return qtd >= 0;
    }

    private void descontarQtd(String codBarraQtd, double qtdRetirada) {

        //for (int i = 0; i < tbProduto.getRowCount(); i++) {
//            ProdutoModel aux = (ProdutoModel) tbProduto.getValueAt(i, 0);
        //String codBarra = tbProduto.getValueAt(i, 1).toString();
        //if (codBarraQtd.equals(codBarra)) {
        double qtd = Calculo.getValueNormal(this.qtdSelecionada.getText()) - qtdRetirada;
        this.qtdSelecionada.setText(Calculo.converterCash(qtd));
        tbProduto.updateUI();
        return;
        //}
        //}

    }
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

                    double preco = Calculo.getValueNormal(tbPedido.getValueAt(index, 3).toString());
                    String descontoValue = "0";
                    String retencaoValue = "0";

                    if (!descontoValue.contains("%")) {
                        descontoValue = String.valueOf(Calculo.getValueNormal(descontoValue));
                    }
                    if (!retencaoValue.contains("%")) {
                        retencaoValue = String.valueOf(Calculo.getValueNormal(retencaoValue));
                    }

                    //                    double desconto = Double.parseDouble(tbPedido.getValueAt(index, 6).toString());
                    if (preco >= 0) {
                        //  double totalAnterior = Double.parseDouble(tbPedido.getValueAt(index, 4).toString());
                        double qtd = Double.parseDouble(tbPedido.getValueAt(index, 2).toString());

                        if (qtd >= 1) {

                            double desconto = Calculo.desconto(descontoValue, preco * qtd, this);
                            double retencao = Calculo.retencao(retencaoValue, ((preco * qtd) - desconto), this);

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
                            /*
                            preco = 12000
                            qtd = 1
                            subtotal = 12000
                            desconto = 1000
                            iva = ((subtotal - desconto) * 14) = ((12000 - 1000)*14%) = 11000 * 14% = 1540
                            
                             */
//                            preco = ((preco * qtd) - desconto) / qtd;

                            double subTotal = preco * qtd;
                            double novoSubtotal = (subTotal - desconto);
                            double iva = Calculo.getValueNormal(tbPedido.getValueAt(index, 5).toString());
//                            iva = this.totalAnterior <= 0 ? 0 : 14;
                            if (iva > 0) {
                                iva = (((preco * qtd) - desconto) * produto.getTaxa().getTaxa()) / 100;
                            }
                            double total = (subTotal + iva - desconto) - retencao;
                            tbPedido.setValueAt(Calculo.converterCash(subTotal), index, 4);
                            tbPedido.setValueAt(Calculo.converterCash(preco), index, 3);
                            //  tbPedido.setValueAt(getValor(desconto), index, 6);

                            tbPedido.setValueAt(Calculo.converterCash(iva), index, 5);

                            tbPedido.setValueAt(Calculo.converterCash(total), index, 6);

                            // actualizar o pedido
                            modelo.setQtd(Calculo.getValueNormal(tbPedido.getValueAt(index, 2).toString()));
                            modelo.setPreco(Calculo.getValueNormal(tbPedido.getValueAt(index, 3).toString()));
                            modelo.setSubTotal(Calculo.getValueNormal(tbPedido.getValueAt(index, 4).toString()));
                            modelo.setIva(Calculo.getValueNormal(tbPedido.getValueAt(index, 5).toString()));
                            modelo.setTotal(Calculo.getValueNormal(tbPedido.getValueAt(index, 6).toString()));

                            insertPedidoItem(modelo);

                            calcular();

                        } else {
                            JOptionPane.showMessageDialog(this, "Quantidade e sempre maior que zero", "Alerta", JOptionPane.WARNING_MESSAGE);
                            tbPedido.setValueAt(Calculo.converterCash(this.preco), index, 3);
                            tbPedido.setValueAt(this.qtd, index, 2);
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "O preco de um produto e sempre maior do que Zero", "Alerta", JOptionPane.WARNING_MESSAGE);

                        tbPedido.setValueAt(Calculo.converterCash(this.preco), index, 3);
                        tbPedido.setValueAt(this.qtd, index, 2);

                    }
                }

            }
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(this, "Valor invalido", "Erro de Digitacao", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_tbPedidoKeyReleased

    private void txtMoedaCambioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMoedaCambioKeyReleased
        // TODO add your handling code here:
        this.calcularCambio();
    }//GEN-LAST:event_txtMoedaCambioKeyReleased

    private void txtValorEntregueMoedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorEntregueMoedaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtValorEntregueMoedaActionPerformed

    private void txtValorEntregueMoedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorEntregueMoedaKeyReleased
        // TODO add your handling code here:
        this.calcularCambio();
    }//GEN-LAST:event_txtValorEntregueMoedaKeyReleased

    private void btnCalcularCambioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularCambioActionPerformed
        // TODO add your handling code here:
        if (!txtConvertido.getText().trim().isEmpty()) {

            txtValorEntregue.setText(txtConvertido.getText());
            if (cboFormaPagamento.getSelectedIndex() != 2 && cboFormaPagamento.getSelectedIndex() != 3) {

                cboFormaPagamento.setSelectedIndex(2);
            }
            lblTroco.setText(Calculo.converterCash(calcularTroco()));

        } else {

            JOptionPane.showMessageDialog(this, "Não foi encontrado nenhum valor convertido para AOA");
        }
    }//GEN-LAST:event_btnCalcularCambioActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // TODO add your handling code here:

        DefaultTableModel tb = (DefaultTableModel) tbPedido.getModel();

        int linhas[] = tbPedido.getSelectedRows();
        for (int i = linhas.length - 1; i >= 0; i--) {

            int linha = linhas[i];
            FacturaItemModel model = (FacturaItemModel) tbPedido.getValueAt(linha, 0);
            tb.removeRow(linha);
            removerPedidoItem(model);
        }
        calcular();
    }//GEN-LAST:event_btnRemoverActionPerformed

    public void removerPedidoItem(FacturaItemModel facturaItem) {

        ProdutoModel produto = facturaItem.getProduto();
        PedidoItemController controller = new PedidoItemController();
        PedidoTesteController pedidoController = new PedidoTesteController();
        PedidoModel pedido = pedidoController.getById(idPedido);
        pedido.setEstado(new EstadoModel(3, ""));
        PedidoItemTesteModel pedidoItem = controller.getByProdutoPedido(produto, new PedidoModel(idPedido));

        pedidoItem.setEstado(new EstadoModel(3, ""));
        pedidoItem.setUsuario(usuario);

        if (tbPedido.getRowCount() <= 0) {
            pedidoController.saveOrUpdate(pedido);
        } else {
            controller.saveOrUpdate(pedidoItem);
        }

    }

    private void txtPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisarActionPerformed

        //Integer index = tbProduto.getRowCount();
        if (!txtPesquisar.getText().trim().isEmpty()) {

            EntradaStockItemController controller = new EntradaStockItemController();
            List<EntradaStockItemModel> stock = getStockProduto(txtPesquisar.getText());

            if (stock != null) {

                if (stock.size() > 0) {
                    EntradaStockItemModel entrada = stock.get(0);
                    //this.adicionar(entrada);
                    tbProduto.removeAll();
                    this.crearProduto(entrada, true);
                    tbProduto.updateUI();
                    quantidade.setValue(1);
                    txtPesquisar.setText("");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione o produto");
        }
    }//GEN-LAST:event_txtPesquisarActionPerformed

    private void btnFacturarKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnFacturarKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_btnFacturarKeyTyped

    private void formKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyTyped
        // TODO add your handling code here:
//        if(evt.getKeyCode()== KeyEvent.VK_ENTER){
//            
//            JOptionPane.showMessageDialog(this, "TESTE");
//        }
    }//GEN-LAST:event_formKeyTyped

    private void txtPesquisarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtPesquisarCaretUpdate
        // TODO add your handling code here:
        if (!txtPesquisar.getText().trim().isEmpty()) {
            carregarProdutoByPesquisa();
        }
    }//GEN-LAST:event_txtPesquisarCaretUpdate

    private void cboMoedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMoedaActionPerformed
        // TODO add your handling code here:
        txtMoedaCambio.setText("" + getMoeda().getCambioDiario().getValor());
        this.calcularCambio();
    }//GEN-LAST:event_cboMoedaActionPerformed

    private void controladorCampo(boolean flag) {

        txtConvertido.setEnabled(flag);
        txtMoedaCambio.setEnabled(flag);
        txtMulticaixa.setEnabled(flag);
        txtPesquisar.setEnabled(flag);
        txtValorEntregue.setEnabled(flag);
        txtValorEntregueMoeda.setEnabled(flag);

        
        btnFecharCaixa.setEnabled(flag);
        btnRemover.setEnabled(flag);
        btnFacturar.setEnabled(flag);
        btnCalcularCambio.setEnabled(flag);

        cboFormaPagamento.setEnabled(flag);
        cboMoeda.setEnabled(flag);

        quantidade.setEnabled(flag);

        chkPerfoma.setEnabled(flag);
        chkA4.setEnabled(flag);
        chkA5.setEnabled(flag);
        chkCarta.setEnabled(flag);

    }

    private void btnFecharCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharCaixaActionPerformed

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            CaixaController caixaController = new CaixaController();
            caixaModel.setDataFecho(dateFormat.format(new Date()));

//            CaixaModel caixaModel = caixaController.getByDateAndUsuario(dateFormat.format(new Date()), usuario.getId(), "Aberto");
            caixaModel.setEstado("Fechado");

            caixaController.update(caixaModel);
            controladorCampo(false);

            CaixaIreport.fechoCaixa(caixaModel);
            caixaModel = null;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_btnFecharCaixaActionPerformed

    private void chkImprimirGuiaRemessaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkImprimirGuiaRemessaActionPerformed
        // TODO add your handling code here:

        txtCarga.setEnabled(chkImprimirGuiaRemessa.isSelected());
        txtLocalEntrega.setEnabled(chkImprimirGuiaRemessa.isSelected());

        limparGuia();


    }//GEN-LAST:event_chkImprimirGuiaRemessaActionPerformed
    private void limparGuia() {

        txtCarga.setText("");
        txtLocalEntrega.setText("");
        dataTransporte.setDate(null);
        hora.setValue(0);
        minuto.setValue(0);
    }
    private void chkImprimirGuiaTransporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkImprimirGuiaTransporteActionPerformed
        // TODO add your handling code here:

        dataTransporte.setEnabled(chkImprimirGuiaTransporte.isSelected());
        hora.setEnabled(chkImprimirGuiaTransporte.isSelected());
        minuto.setEnabled(chkImprimirGuiaTransporte.isSelected());
        txtCarga.setEnabled(chkImprimirGuiaTransporte.isSelected());
        txtLocalEntrega.setEnabled(chkImprimirGuiaTransporte.isSelected());

        limparGuia();
    }//GEN-LAST:event_chkImprimirGuiaTransporteActionPerformed

    private void btnMesasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMesasActionPerformed
        // TODO add your handling code here:
        PedidoMesaVIew pedido = (PedidoMesaVIew) pedidoMesaView;
        pedido.init();
//        if(permitirFecho)
//            new PedidoMesaVIew(usuario, permitirFecho).setVisible(true);
//        else
//            new PedidoMesaVIew(usuario).setVisible(true);
    }//GEN-LAST:event_btnMesasActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        FacturaIreport.termicaPedidoCozinha(mesa.getId());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jScrollBar1AdjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {//GEN-FIRST:event_jScrollBar1AdjustmentValueChanged
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jScrollBar1AdjustmentValueChanged

    private void chkTermicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTermicaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkTermicaActionPerformed

    /*
    //imprimir o item seleccionado
    public void imprimirPedido() {
        ImprimirPedidoController imprimirPedidoController = new ImprimirPedidoController(conexao);

        String produto;
        int linhaSeleccionada = tabela.getSelectedRow();

        if (linhaSeleccionada >= 0) {
            produto = String.valueOf(tabela.getValueAt(linhaSeleccionada, 1));
            int codigoProdutoSeleccionado = produtosController.getCodigoProdutos(produto);
            float quantidade = Float.parseFloat(String.valueOf(tabela.getValueAt(linhaSeleccionada, 3)));

            String mesaActiva = getMesaActiva();

//            if (listaMesas.getSelectedIndex() >= 0) {
                //1. salvar o pedido
                ImprimirPedidoModelo modelo = new ImprimirPedidoModelo(
                        getCodigoUtilizador(),
                        codigoProdutoSeleccionado, quantidade,
                        BK.getDataActual(), mesaActiva, "pedido", "VENDAS");

                if (imprimirPedidoController.create(modelo)) {
                    int codigoPedido = imprimirPedidoController.getlastCodigo(getCodigoUtilizador());
                    ContaMesaRecibo.mostrarReciboProcurado(codigoPedido, false, "");
                    imprimirPedidoController.actualizarStatus(codigoPedido, "pedido enviado");
                    JOptionPane.showMessageDialog(null, "Pedido Enviado!");
                }
//            } else {
//                JOptionPane.showMessageDialog(null, "Seleccione a Mesa do Pedido");
//            }
        } else {
            JOptionPane.showMessageDialog(null, "Seleccione 1 item na Factura!");
        }
    }
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DataFim;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCalcularCambio;
    private javax.swing.JButton btnFacturar;
    private javax.swing.JButton btnFecharCaixa;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnMesas;
    private javax.swing.JButton btnRemover;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JComboBox<String> cboFormaPagamento;
    private javax.swing.JComboBox<String> cboMoeda;
    private javax.swing.JCheckBox chkA4;
    private javax.swing.JCheckBox chkA5;
    private javax.swing.JCheckBox chkCarta;
    private javax.swing.JCheckBox chkImprimirGuiaRemessa;
    private javax.swing.JCheckBox chkImprimirGuiaTransporte;
    private javax.swing.JCheckBox chkPerfoma;
    private javax.swing.JCheckBox chkTermica;
    private com.toedter.calendar.JDateChooser dataInicio;
    private com.toedter.calendar.JDateChooser dataTransporte;
    private javax.swing.JSpinner hora;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblIVA;
    private javax.swing.JLabel lblSubTotal;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblTroco;
    private javax.swing.JList<String> listaFactura;
    private javax.swing.JSpinner minuto;
    private javax.swing.JSpinner quantidade;
    private javax.swing.JCheckBox rdA4;
    private javax.swing.JCheckBox rdA5;
    private javax.swing.JCheckBox rdTermica;
    private javax.swing.JTabbedPane tabulacao;
    private javax.swing.JTable tbPedido;
    private javax.swing.JPanel tbProduto;
    private javax.swing.JTextField txtCarga;
    private javax.swing.JTextField txtConvertido;
    private javax.swing.JTextArea txtItemFactura;
    private javax.swing.JTextField txtLocalEntrega;
    private javax.swing.JTextField txtMoedaCambio;
    private javax.swing.JTextField txtMulticaixa;
    private javax.swing.JTextField txtObservacao;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtValorEntregue;
    private javax.swing.JTextField txtValorEntregueMoeda;
    // End of variables declaration//GEN-END:variables
}
