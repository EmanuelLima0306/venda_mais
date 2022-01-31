/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Facturacao;

import Controller.ArmazenamentoController;
import Controller.CategoriaABController;
import Controller.ClienteController;
import Controller.EntradaStockItemController;
import Controller.FacturaController;
import Controller.FormaPagamentoController;
import Controller.MoedaController;
import Controller.ParamentroController;
import Controller.PedidoItemController;
import Controller.PedidoTesteController;
import Controller.TaxaController;
import ItemTabela.ItemProduto;
import Model.CaixaModel;
import Model.CategoriaModel;
import Model.ClienteModel;
import Model.EntradaStockItemModel;
import Model.EstadoModel;
import Model.FacturaItemModel;
import Model.FacturaModel;
import Model.FormaPagamentoModel;
import Model.MesaModel;
import Model.Moeda;
import Model.ParamentroModel;
import Model.PedidoItemTesteModel;
import Model.PedidoModel;
import Model.ProdutoModel;
import Model.Taxa;
import Model.UsuarioModel;
import Util.Calculo;
import Util.DataComponent;
import View.PedidoMesaVIew;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;
import Ireport.FacturaIreport;
import java.text.SimpleDateFormat;
import Controller.CaixaController;
import Controller.DocumentoController12;
import Controller.FacturaItemController;
import Ireport.CaixaIreport;
import Model.LicencaModel;
import View.BalcaoView;
import static View.BalcaoView.DataConforme;
import View.CaixaView;
import java.awt.Frame;
import static java.awt.Frame.getFrames;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import json_xml_iva.RSA;

/**
 *
 * @author emanuel
 */
public class ModeloView extends javax.swing.JPanel {

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
    private List<FacturaItemModel> carrinho = new ArrayList<>();
    private FacturaItemModel removerCarrinho = null;
    EntradaStockItemModel entradaDefault;
    JLabel qtdSelecionada;

    // Variaveis para Categoria
    private int salto = 9;
    private HashMap<Integer, Integer> historico;

    private int countMaxClick = 0;
    private int countClick = 1;
    private int num_item = 10;
    private int resto = 0;

    // Variaveis para Os Produtos
    private int saltoProduto = 11;

    private int countMaxClickProduto = 0;
    private int countClickProduto = 1;
    private int num_itemProduto = 12;
    private int restoProduto = 0;
    private List<EntradaStockItemModel> listaProduto;
    private List<CategoriaModel> lista;
    private String fotoProduto = "";
    private boolean estadoRemessa = false, estadoTransport = false, telaRest = true;

    public ModeloView() {
        initComponents();

        this.setName("BalcaoView");
        this.usuario = usuario;
        this.mesa = mesa;
        this.pedidoMesaView = pedidoMesaView;
        inicializar();
        this.armazemPrincipal();
        this.moduloSistema();

        mostraProduto("", null, false);
        carregarPedido();
        initCaixa();

    }

    public ModeloView(UsuarioModel usuario, JFrame pedidoView, boolean telaRest) {
        initComponents();

        this.setName("BalcaoView");
        this.usuario = usuario;
        this.mesa = mesa;
        this.pedidoMesaView = pedidoMesaView;
        this.telaRest = telaRest;
        inicializar();
        this.armazemPrincipal();
        this.moduloSistema();

        mostraProduto("", null, false);
        initCaixa();
        focoTextPesquisar();
    }

    public ModeloView(UsuarioModel usuario, MesaModel mesa, boolean permitirFecho, JFrame pedidoMesaView) {
        initComponents();

        this.setName("BalcaoView");
        this.usuario = usuario;
        this.mesa = mesa;
        this.pedidoMesaView = pedidoMesaView;
        inicializar();
        this.armazemPrincipal();
        this.moduloSistema();

        mostraProduto("", null, false);
        carregarPedido();
        initCaixa();
        focoTextPesquisar();
        

    }

    public ModeloView(UsuarioModel usuario, MesaModel mesa, JFrame pedidoMesaView) {
        initComponents();

        this.setName("BalcaoView");
        this.usuario = usuario;
        this.mesa = mesa;
        this.pedidoMesaView = pedidoMesaView;
        inicializar();
        this.armazemPrincipal();
        this.moduloSistema();

        mostraProduto("", null, false);
        carregarPedido();
        initCaixa();
        focoTextPesquisar();

    }
    
    public void focoTextPesquisar(){
        txtPesquisar.setFocusable(true);
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

            // desablitar as funções do restaurante
            enviarPedidoCozinha = false;
            // deixa apenas as pesquisas
            confRestaurante(false);

        } else if (modelo.getValor() == 2) {//(2) - MREST
            enviarPedidoCozinha = true;
            confRestaurante(telaRest);
        }

    }

    public void confRestaurante(boolean reataurante) {
        if (!reataurante) {

//            btnPesquisar.setVisible(false);
            jPanel11.remove(btnPesquisar);
            jPanelTopo.removeAll();
            jPanelTopo.add(jPanelTopo2);
            jPanelTopo.updateUI();
            jPanel11.updateUI();
        }
    }

    private void armazemPrincipal() {

        ParamentroController controller = new ParamentroController();
        ParamentroModel modelo = controller.getById(6);

        if (modelo.getValor() > 0) {//remover

            idArmazem = modelo.getValor();

        } else {

            //btnFacturar.setEnabled(false);
            // btnImprimir.setEnabled(false);
            chkPerfoma.setEnabled(false);
            txtValorEntregue.setEnabled(false);
            txtMulticaixa.setEnabled(false);
            tbDefault.setRowCount(0);
            JOptionPane.showMessageDialog(this, "Não foi definido armazem Principal");
        }

    }

    private void inicializar() {

        jScrollPane4.getVerticalScrollBar().setUnitIncrement(16);
        carregarCliente();
        carregarFormaPagamento();
////        carregarProduto();
        carregarMoeda();
        carregarCategoria();

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
            factura.setQtd(p.getQtd());
            carrinho.add(factura);
        }
        addItem();
        calcular();

    }

    private void carregarMoeda() {

        MoedaController controller = new MoedaController();
        List<Moeda> lista = controller.get();
        cboMoeda.setModel(new DefaultComboBoxModel(lista.toArray()));
    }

    private void carregarFormaPagamento() {

        FormaPagamentoController controller = new FormaPagamentoController();
        List<FormaPagamentoModel> lista = controller.get();
        cboFormaPagamento.setModel(new DefaultComboBoxModel(lista.toArray()));

        selecionadoForma();
    }

    private void carregarCliente() {

        ClienteController controller = new ClienteController();
        List<ClienteModel> lista = controller.get();
        cboCliente.setModel(new DefaultComboBoxModel(lista.toArray()));
    }

    public void carregarCategoria() {
        panelCategoria.setLayout(new GridLayout(2, 5, 5, 5));
        calcClick();
        next();
    }

    private List<EntradaStockItemModel> getProduto(String pesquisar, CategoriaModel categoria) {

        List<EntradaStockItemModel> stock = getStockProduto(pesquisar, categoria);

        return stock;

    }

    public List<EntradaStockItemModel> getStockProduto(String pesquisar, CategoriaModel categoria) {

        EntradaStockItemController controller = new EntradaStockItemController();
        if (categoria == null) {
            return controller.getStock(idArmazem, pesquisar);
        } else {
            return controller.getStockCategoria(idArmazem, "", categoria);
        }
    }

    private void mostraProduto(String pesquisar, CategoriaModel categoria, boolean adicionarDirecto) {

        List<EntradaStockItemModel> lista = getProduto(pesquisar, categoria);
        this.listaProduto = lista;

        panelProduto.setLayout(new GridLayout(3, 4, 5, 5));
        panelProduto.removeAll();

        calcClickProduto();
        countClickProduto = 1;
        nextProduto(adicionarDirecto);

        panelProduto.updateUI();

    }

    private void createLblProduto(EntradaStockItemModel e, boolean adicionarDirecto) {

        ItemProduto item = new ItemProduto();
        JScrollPane scroll = (JScrollPane) item.getComponent(0);
        JViewport jView = (JViewport) scroll.getComponent(0);
        JLabel designacao = (JLabel) jView.getComponent(0);
        JLabel imagem = (JLabel) item.getComponent(4);
        JLabel qtd = (JLabel) item.getComponent(2);
        JLabel preco = (JLabel) item.getComponent(1);
        JLabel btnAdicionar = (JLabel) item.getComponent(3);

        btnAdicionar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {

                    System.out.println("width" + imagem.getWidth() + "\nHeight " + imagem.getHeight());
                    lblDescricaoProduto.setText(e.getProduto().getDesignacao() + " - " + Calculo.converterCash(e.getPrecoVenda()));
                    fotoProduto = e.getProduto().getUrlImage();
                    updateImagemProduto(e.getProduto().getUrlImage());// actualiza a foto do produto na lateral

                } else {

                    //adicionar produto no carrinho
                    adicionarProduto(e, qtd);
                }
            }
        });

        imagem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {

                    lblDescricaoProduto.setText(e.getProduto().getDesignacao() + " - " + Calculo.converterCash(e.getPrecoVenda()));
                    fotoProduto = e.getProduto().getUrlImage();
                    updateImagemProduto(e.getProduto().getUrlImage());// actualiza a foto do produto na lateral
                } else {

                    //adicionar produto no carrinho
                    adicionarProduto(e, qtd);
                }
            }
        });
        item.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 1) {

                    lblDescricaoProduto.setText(e.getProduto().getDesignacao() + " - " + Calculo.converterCash(e.getPrecoVenda()));
                    fotoProduto = e.getProduto().getUrlImage();
                    updateImagemProduto(e.getProduto().getUrlImage());// actualiza a foto do produto na lateral
                } else {

                    //adicionar produto no carrinho
                    adicionarProduto(e, qtd);
                }
            }
        });

        File file = new File("Relatorio" + File.separator + "imagensProdutos" + File.separator + e.getProduto().getUrlImage());
        String pathFinal = file.getAbsolutePath();
        ImageIcon image = new ImageIcon(pathFinal);

        if (imagem.getWidth() == 0 || imagem.getHeight() == 0) {
            imagem.setIcon(new ImageIcon(image.getImage().getScaledInstance(143, 64, Image.SCALE_DEFAULT)));
        } else {
            imagem.setIcon(new ImageIcon(image.getImage().getScaledInstance(imagem.getWidth(), imagem.getHeight(), Image.SCALE_DEFAULT)));
        }

        // tratamentos de imagem
//     
        btnAdicionar.setForeground(Color.white);
        preco.setText(Calculo.converterCash(e.getPrecoVenda()) + " Kz");
        qtd.setText(Calculo.converter(e.getQtd()) + " Unid");
        designacao.setText(e.getProduto().getDesignacao());
        panelProduto.add(item);
        panelProduto.updateUI();

        if (adicionarDirecto) {
            adicionarProduto(e, qtd);
        }

    }

    public void updateImagemProduto(String foto) {
        if (!foto.isEmpty()) {

            File file = new File("Relatorio" + File.separator + "imagensProdutos" + File.separator + foto);
            String pathFinal = file.getAbsolutePath();
            ImageIcon image = new ImageIcon(pathFinal);

            if (lbFotoProduto.getWidth() == 0 || lbFotoProduto.getHeight() == 0) {

                lbFotoProduto.setIcon(new ImageIcon(image.getImage().getScaledInstance(200, 141, Image.SCALE_DEFAULT)));
            } else {
                lbFotoProduto.setIcon(new ImageIcon(image.getImage().getScaledInstance(lbFotoProduto.getWidth(), lbFotoProduto.getHeight(), Image.SCALE_DEFAULT)));
            }
        } else {
            lbFotoProduto.setIcon(null);
        }
    }

    public void adicionarProduto(EntradaStockItemModel e, JLabel lbQtd) {

        this.adicionar(e, lbQtd);
        ProdutoModel p = e.getProduto();
        if (isAdicionado && p.isStocavel()) {

        }
        if (isAdicionado) {
            fotoProduto = "";
            updateImagemProduto("");
            focoTextPesquisar();
        }

        lbQtd.updateUI();
        //panelItemProduto.updateUI();
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
                            if (telaRest) {

                                if (insertPedidoItem(model)) {

                                    FacturaItemModel facturaItemModel = model;
                                    facturaItemModel.setLote(entrada.getCodBarra());
                                    facturaItemModel.setPreco(entrada.getPrecoVenda());

                                    carrinho.add(facturaItemModel);

                                    calcular();
                                    isAdicionado = true;
                                }
                            } else {

                                FacturaItemModel facturaItemModel = model;
                                facturaItemModel.setLote(entrada.getCodBarra());
                                facturaItemModel.setPreco(entrada.getPrecoVenda());

                                carrinho.add(facturaItemModel);

                                calcular();
                                isAdicionado = true;
                            }
                        } else {
                            isAdicionado = false;
                            JOptionPane.showMessageDialog(this, "Quantidade Indispunivel no Stock");
                        }
                    } else {
                        double qtdTotal = model.getQtd() + carrinho.get(index).getQtd();

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
                if (telaRest) {
                    if (insertPedidoItem(model)) {

                        FacturaItemModel facturaItemModel = model;
                        facturaItemModel.setLote(entrada.getCodBarra());
                        carrinho.add(facturaItemModel);

                        calcular();
                        isAdicionado = true;
                    }
                } else {
                    FacturaItemModel facturaItemModel = model;
                    facturaItemModel.setLote(entrada.getCodBarra());
                    carrinho.add(facturaItemModel);

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

    private int getIndex(ProdutoModel produto) {

        for (int i = 0; i < carrinho.size(); i++) {

            FacturaItemModel factura = carrinho.get(i);

            if (factura.getProduto().getId() == produto.getId()) {
                return i;
            }
        }
        return -1;
    }

    private void addItemExistente(FacturaItemModel factura, int index, ProdutoModel produto, Taxa ipc) {

        factura.setQtd(factura.getQtd() + carrinho.get(index).getQtd());
        double preco = carrinho.get(index).getPreco();
        double ipcValor = 0;
        double desconto = carrinho.get(index).getDesconto();
        factura.setPreco(preco);
        factura.setDesconto(desconto);
        factura.setSubTotal(factura.getQtd() * factura.getPreco());
        carrinho.get(index).setQtd(factura.getQtd());
        carrinho.get(index).setSubTotal(factura.getSubTotal());
        carrinho.get(index).setDesconto(factura.getDesconto());
        if (produto.isIpc()) {

            ipcValor = factura.getSubTotal() * ipc.getTaxa() / 100;
            factura.setIva(ipcValor);

        }
        factura.setTotal(factura.getSubTotal() + ipcValor - factura.getDesconto());
        // insere envia o pedido para ser atualizado quando a tela for de restaurante
        if (telaRest) {
            insertPedidoItem(factura);
        }
        carrinho.get(index).setIva(factura.getIva());
        carrinho.get(index).setTotal(factura.getTotal());

    }

    private void calcular() {

        double subTotal = 0, desconto = 0, iva = 0, total = 0, retencao = 0;

        for (int i = 0; i < carrinho.size(); i++) {

            retencao += 0;

            subTotal += carrinho.get(i).getSubTotal();
            total += carrinho.get(i).getTotal();
            desconto += carrinho.get(i).getDesconto();

            iva += carrinho.get(i).getIva();
            //subTotal += Double.parseDouble(tbPedido.getValueAt(i, 3).toString());

        }
        if (desconto == 0.0) {
            desconto = new Double(000);
        }
        lblTroco.setText(Calculo.converterCash(new Double(000)));
        lblSubTotal.setText(Calculo.converterCash(subTotal));
        lblTotal.setText(Calculo.converterCash(total));
        lblIVA.setText(Calculo.converterCash(iva));
        lblDesconto.setText(Calculo.converterCash(desconto));

        addItem();
    }

    private int getLastIdPedido() {

        PedidoTesteController controller = new PedidoTesteController();
        return controller.getLastId(mesa.getId());
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

            if (controller.saveOrUpdate(item)) {
                ParamentroController pc = new ParamentroController();
                ParamentroModel p = pc.getById(11);
                if (enviarPedidoCozinha && p.getValor() == 1) {
                    if (model.getProduto().isIsCozinha()) {
                        FacturaIreport.pedidoCozinhaBalcao(model, Double.parseDouble(quantidade.getValue().toString()), mesa);
                    }
                }
                return true;
            }
            return false;
//            }
        }
        return false;
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

    public void reduzirQtd(double qtd, JLabel lbQtd) {
        lbQtd.setText(String.valueOf(qtd) + " Unid");
        lbQtd.updateUI();
    }

    private int getIndexStocavel(String lote) {

        qtdAdicionada = 0;
        for (int i = 0; i < carrinho.size(); i++) {

            String loteTb = carrinho.get(i).getLote();

            if (loteTb.equals(lote)) {

                qtdAdicionada = carrinho.get(i).getQtd();
                return i;
            }
        }
        return -1;
    }

    private float quantidade(String lote) {

        EntradaStockItemController controller = new EntradaStockItemController();
        return (float) (controller.getQtdByCodBarra(lote) - controller.getStockMinimo(lote));
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
    }

    private void limpar() {

        //   txtMulticaixa.setText("0");
        quantidade.setValue(1);

        //   txtValorEntregue.setText("0");
        txtPesquisar.setText("");
        //txtObservacao.setText(" ");
        lblTroco.setText("0");
        lblDesconto.setText("0");
        lblDesconto.setText("0");
        //lblRetencao.setText("0");
        lblSubTotal.setText("0");
        lblTotal.setText("0");
        lblIVA.setText("0");
        txtValorEntregue.setText("0");
        txtMulticaixa.setText("0");
        carrinho.clear();
        panelItems1.removeAll();
        panelItems1.updateUI();
    }

    private void addItem() {

        panelItems1.removeAll();
        panelItems1.setLayout(new java.awt.GridLayout(carrinho.size() >= 10 ? carrinho.size() : carrinho.size() + 10, 0, 0, 5));

        for (FacturaItemModel f : carrinho) {

            ItemFactura item = new ItemFactura(f.getProduto().getDesignacao(), f.getQtd(), f.getPreco());
            JPanel panel = (JPanel) item.getComponent(0);
            JLabel label1 = (JLabel) panel.getComponent(0);
            JLabel label2 = (JLabel) panel.getComponent(1);
            JLabel label3 = (JLabel) panel.getComponent(2);

            item.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    removerCarrinho = f;
                }
            });

            label1.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    removerCarrinho = f;
                }
            });

            label2.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    removerCarrinho = f;
                }
            });

            label3.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    removerCarrinho = f;
                }
            });

            panelItems1.add(item);
        }
        panelItems1.updateUI();

    }

    private void calcClick() {

        this.lista = getCategoria();
        this.countMaxClick = lista.size() / 10;

        this.resto = lista.size() % num_item;

        if (resto > 0) {

            this.countMaxClick += 1;

        }

    }

    private void calcClickProduto() {

        this.countMaxClickProduto = listaProduto.size() / 12;

        this.restoProduto = listaProduto.size() % num_itemProduto;

        if (restoProduto > 0) {

            this.countMaxClickProduto += 1;

        }

    }

    private void next() {

        if (countMaxClick > 0 && countClick <= countMaxClick) {

            int indexFim = (num_item * countClick);
            int indexInicial = num_item * (countClick - 1);

            if (indexFim > this.lista.size()) {
                if (this.resto > 0) {
                    indexFim = indexInicial + this.resto;
                }
            }

            createBtnCategoria(indexInicial, indexFim);

        } else {
            this.countClick = countMaxClick;
        }

    }

    private void nextProduto(boolean adicionarDirecto) {

        if (countMaxClickProduto > 0 && countClickProduto <= countMaxClickProduto) {

            int indexFim = (num_itemProduto * countClickProduto);
            int indexInicial = num_itemProduto * (countClickProduto - 1);

            if (indexFim > this.listaProduto.size()) {
                if (this.restoProduto > 0) {
                    indexFim = indexInicial + this.restoProduto;
                }
            }

            createProduto(indexInicial, indexFim, adicionarDirecto);

        } else {
            this.countClickProduto = countMaxClickProduto;
        }

    }

    private void prev() {

        if ((countMaxClick > 0 && countClick <= countMaxClick) && countClick > 0) {

            int indexFim = (num_item * countClick);
            int indexInicial = indexFim - num_item;

            createBtnCategoria(indexInicial, indexFim);

        } else {
            this.countClick = 1;
        }

    }

    private void prevProduto(boolean adicionarDirecto) {

        if ((countMaxClickProduto > 0 && countClickProduto <= countMaxClickProduto) && countClickProduto > 0) {

            int indexFim = (num_itemProduto * countClickProduto);
            int indexInicial = indexFim - num_itemProduto;

            createProduto(indexInicial, indexFim, adicionarDirecto);

        } else {
            this.countClickProduto = 1;
        }

    }

    private void createBtnCategoria(int indexInicial, int indexFim) {

        List<CategoriaModel> lista = getCategoria();
        if (indexFim <= lista.size()) {
            panelCategoria.removeAll();
            while (indexInicial < indexFim) {

                createBtn(lista.get(indexInicial), this.panelCategoria);
                indexInicial++;

            }
            panelCategoria.updateUI();
        }

    }

    private void createProduto(int indexInicial, int indexFim, boolean adicionarDirecto) {

        int totIndex = indexFim - indexInicial;
        List<EntradaStockItemModel> lista = this.listaProduto;
        if (indexFim <= lista.size()) {
            panelProduto.removeAll();
            while (indexInicial < indexFim) {

                createLblProduto(lista.get(indexInicial), adicionarDirecto);
                indexInicial++;

            }

            while (totIndex + 1 < 12) {

                JLabel label = new JLabel("");
                panelProduto.add(label);
                totIndex++;
            }
            panelProduto.updateUI();
        }

    }

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
            java.util.logging.Logger.getLogger(Teste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Teste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Teste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Teste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ModeloView().setVisible(true);
            }
        });
    }

    private void createBtn(CategoriaModel categoria, JPanel panel) {

        JButton btn = new JButton(categoria.getDesignacao());
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                mostraProduto("", categoria, false);
            }
        });
        panel.add(btn);
        panel.updateUI();

    }

    private List<CategoriaModel> getCategoria() {

        CategoriaABController controller = new CategoriaABController();
        List<CategoriaModel> categorias = controller.get();

        return categorias;

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTopo2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        quantidade = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        chkPerfoma = new javax.swing.JCheckBox();
        jPanelBaixo2 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtCliente = new javax.swing.JTextField();
        txtDesconto = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        txtRetencao = new javax.swing.JTextField();
        cboCliente = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        cboFormaPagamento = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        txtValorEntregue = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtMulticaixa = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        chkTermica = new javax.swing.JCheckBox();
        chkA4 = new javax.swing.JCheckBox();
        chkCarta = new javax.swing.JCheckBox();
        chkA5 = new javax.swing.JCheckBox();
        jPanel14 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        btnTerminarFactura = new javax.swing.JButton();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jPanelBaixo3 = new javax.swing.JPanel();
        tabulacao = new javax.swing.JTabbedPane();
        jPanel17 = new javax.swing.JPanel();
        cboMoeda = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtMoedaCambio = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtValorEntregueMoeda = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txtConvertido = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        btnCalcularCambio = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        dataInicio = new com.toedter.calendar.JDateChooser();
        DataFim = new com.toedter.calendar.JDateChooser();
        jLabel33 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        rdA4 = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        rdTermica = new javax.swing.JCheckBox();
        rdA5 = new javax.swing.JCheckBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtItemFactura = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        listaFactura = new javax.swing.JList<>();
        jButton13 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        btnFecharCaixa = new javax.swing.JButton();
        btnReforcarCaixa = new javax.swing.JButton();
        btnNovaTela = new javax.swing.JButton();
        btnAbrirCaixa = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        txtLocalEntrega = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        txtCarga = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        chkImprimirGuiaRemessa = new javax.swing.JCheckBox();
        jPanel23 = new javax.swing.JPanel();
        dataTransporte = new com.toedter.calendar.JDateChooser();
        hora = new javax.swing.JSpinner();
        minuto = new javax.swing.JSpinner();
        jLabel37 = new javax.swing.JLabel();
        chkImprimirGuiaTransporte = new javax.swing.JCheckBox();
        jLabel38 = new javax.swing.JLabel();
        txtObservacao = new javax.swing.JTextField();
        jButton12 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanelTopo = new javax.swing.JPanel();
        jPanelTopo1 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jPanelBaixo = new javax.swing.JPanel();
        jPanelBaixo1 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        btnFacturar = new javax.swing.JButton();
        btnPesquisar = new javax.swing.JButton();
        btnRemover = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        panelItems1 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        lblTroco = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblSubTotal = new javax.swing.JLabel();
        lblIVA = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblDesconto = new javax.swing.JLabel();
        lbFotoProduto = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel15 = new javax.swing.JPanel();
        panelCategoria = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        panelContainer = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        lblDescricaoProduto = new javax.swing.JLabel();
        panelProduto = new javax.swing.JPanel();

        jPanelTopo2.setBackground(new java.awt.Color(36, 36, 39));
        jPanelTopo2.setName("jPanelTopo2"); // NOI18N
        jPanelTopo2.setPreferredSize(new java.awt.Dimension(489, 81));

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Buscar");

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

        quantidade.setModel(new javax.swing.SpinnerNumberModel(1, 1, null, 1));

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Qtd");

        chkPerfoma.setBackground(new java.awt.Color(255, 255, 255));
        chkPerfoma.setForeground(new java.awt.Color(254, 254, 254));
        chkPerfoma.setText("Performa");
        chkPerfoma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPerfomaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelTopo2Layout = new javax.swing.GroupLayout(jPanelTopo2);
        jPanelTopo2.setLayout(jPanelTopo2Layout);
        jPanelTopo2Layout.setHorizontalGroup(
            jPanelTopo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopo2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelTopo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTopo2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPesquisar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addComponent(chkPerfoma, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanelTopo2Layout.setVerticalGroup(
            jPanelTopo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopo2Layout.createSequentialGroup()
                .addGroup(jPanelTopo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelTopo2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelTopo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTopo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkPerfoma))
        );

        jPanelBaixo2.setBackground(new java.awt.Color(36, 36, 39));
        jPanelBaixo2.setForeground(new java.awt.Color(254, 254, 254));
        jPanelBaixo2.setName("jPanelBaixo2"); // NOI18N

        jLabel11.setBackground(new java.awt.Color(0, 102, 51));
        jLabel11.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(254, 254, 254));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Cliente");
        jLabel11.setOpaque(true);

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Nome");
        jLabel12.setPreferredSize(new java.awt.Dimension(70, 17));

        txtDesconto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescontoKeyReleased(evt);
            }
        });

        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Desconto");
        jLabel24.setPreferredSize(new java.awt.Dimension(70, 17));

        txtRetencao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtRetencaoKeyReleased(evt);
            }
        });

        cboCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione" }));

        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Retenção");
        jLabel30.setPreferredSize(new java.awt.Dimension(70, 17));

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Cliente");
        jLabel13.setPreferredSize(new java.awt.Dimension(70, 17));

        jLabel18.setBackground(new java.awt.Color(0, 102, 51));
        jLabel18.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(254, 254, 254));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Pagamento dos Artigos");
        jLabel18.setOpaque(true);

        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Forma");
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 17));

        cboFormaPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Numerário", "Multicaixa", "Depósito", "Transferência", "P.Duplo" }));
        cboFormaPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFormaPagamentoActionPerformed(evt);
            }
        });

        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Cash");
        jLabel20.setPreferredSize(new java.awt.Dimension(70, 17));

        txtValorEntregue.setText("0");
        txtValorEntregue.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtValorEntregueKeyReleased(evt);
            }
        });

        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Multicaixa");

        txtMulticaixa.setText("0");
        txtMulticaixa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMulticaixaKeyReleased(evt);
            }
        });

        jPanel10.setBackground(new java.awt.Color(36, 36, 39));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        chkTermica.setBackground(java.awt.Color.white);
        buttonGroup1.add(chkTermica);
        chkTermica.setForeground(new java.awt.Color(254, 254, 254));
        chkTermica.setSelected(true);
        chkTermica.setText("Ticket");

        chkA4.setBackground(java.awt.Color.white);
        buttonGroup1.add(chkA4);
        chkA4.setForeground(new java.awt.Color(254, 254, 254));
        chkA4.setText("A4");

        chkCarta.setForeground(new java.awt.Color(254, 254, 254));
        chkCarta.setText("Carta de Garantia");

        buttonGroup1.add(chkA5);
        chkA5.setForeground(new java.awt.Color(254, 254, 254));
        chkA5.setText("A5");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chkTermica)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkA4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkA5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                .addComponent(chkCarta))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkTermica)
                    .addComponent(chkA4)
                    .addComponent(chkCarta)
                    .addComponent(chkA5)))
        );

        jPanel14.setBackground(new java.awt.Color(36, 36, 39));
        jPanel14.setLayout(new java.awt.GridLayout(1, 0));

        jButton9.setBackground(new java.awt.Color(0, 102, 51));
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/prev.png"))); // NOI18N
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel14.add(jButton9);

        btnTerminarFactura.setBackground(new java.awt.Color(0, 102, 51));
        btnTerminarFactura.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/Print_45px.png"))); // NOI18N
        btnTerminarFactura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTerminarFacturaActionPerformed(evt);
            }
        });
        jPanel14.add(btnTerminarFactura);

        javax.swing.GroupLayout jPanelBaixo2Layout = new javax.swing.GroupLayout(jPanelBaixo2);
        jPanelBaixo2.setLayout(jPanelBaixo2Layout);
        jPanelBaixo2Layout.setHorizontalGroup(
            jPanelBaixo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanelBaixo2Layout.createSequentialGroup()
                .addGroup(jPanelBaixo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelBaixo2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(jPanelBaixo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtCliente)))
                    .addGroup(jPanelBaixo2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanelBaixo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBaixo2Layout.createSequentialGroup()
                                .addGroup(jPanelBaixo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, 0)
                                .addGroup(jPanelBaixo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDesconto)
                                    .addComponent(txtRetencao)))))
                    .addGroup(jPanelBaixo2Layout.createSequentialGroup()
                        .addGroup(jPanelBaixo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, 0)
                        .addGroup(jPanelBaixo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtValorEntregue, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtMulticaixa, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cboFormaPagamento, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBaixo2Layout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelBaixo2Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanelBaixo2Layout.setVerticalGroup(
            jPanelBaixo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBaixo2Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBaixo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBaixo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBaixo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBaixo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRetencao, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBaixo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBaixo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtValorEntregue, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelBaixo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMulticaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jPanelBaixo3.setBackground(new java.awt.Color(36, 36, 39));
        jPanelBaixo3.setName("jPanelBaixo3"); // NOI18N
        jPanelBaixo3.setLayout(new java.awt.BorderLayout());

        tabulacao.setBackground(new java.awt.Color(36, 36, 39));
        tabulacao.setForeground(new java.awt.Color(254, 254, 254));
        tabulacao.setPreferredSize(new java.awt.Dimension(436, 514));

        jPanel17.setBackground(new java.awt.Color(36, 36, 39));

        cboMoeda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMoedaActionPerformed(evt);
            }
        });

        jLabel26.setForeground(new java.awt.Color(254, 254, 254));
        jLabel26.setText("Moeda");

        jLabel27.setForeground(new java.awt.Color(254, 254, 254));
        jLabel27.setText("Cambio(Moeda Estrangeira)");

        txtMoedaCambio.setEditable(false);
        txtMoedaCambio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMoedaCambioKeyReleased(evt);
            }
        });

        jLabel28.setForeground(new java.awt.Color(254, 254, 254));
        jLabel28.setText("Valor Entregue (Moeda Estrangeira )");

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

        jLabel29.setForeground(new java.awt.Color(254, 254, 254));
        jLabel29.setText("Valor Convertido em AOA");

        txtConvertido.setEnabled(false);

        jButton10.setBackground(new java.awt.Color(0, 102, 51));
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/prev.png"))); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        btnCalcularCambio.setBackground(new java.awt.Color(0, 102, 51));
        btnCalcularCambio.setForeground(new java.awt.Color(254, 254, 254));
        btnCalcularCambio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red.png"))); // NOI18N
        btnCalcularCambio.setText("Aplicar");
        btnCalcularCambio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularCambioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtConvertido)
                    .addComponent(cboMoeda, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtValorEntregueMoeda)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28)
                            .addComponent(jLabel26)
                            .addComponent(jLabel27)
                            .addComponent(txtMoedaCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29))
                        .addGap(0, 181, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCalcularCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26)
                .addGap(7, 7, 7)
                .addComponent(cboMoeda, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel27)
                .addGap(9, 9, 9)
                .addComponent(txtMoedaCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel28)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtValorEntregueMoeda, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtConvertido, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCalcularCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 39, Short.MAX_VALUE)
                .addGap(67, 67, 67))
        );

        tabulacao.addTab("Cambio", jPanel17);

        jPanel18.setBackground(new java.awt.Color(36, 36, 39));
        jPanel18.setPreferredSize(new java.awt.Dimension(740, 514));

        jPanel19.setBackground(new java.awt.Color(36, 36, 39));
        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel19.setForeground(new java.awt.Color(255, 255, 255));

        jLabel31.setForeground(new java.awt.Color(254, 254, 254));
        jLabel31.setText("Data");

        jLabel33.setForeground(new java.awt.Color(254, 254, 254));
        jLabel33.setText("Até");

        btnBuscar.setBackground(new java.awt.Color(0, 102, 51));
        btnBuscar.setForeground(new java.awt.Color(254, 254, 254));
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/spinner_frame_6_24px_1.png"))); // NOI18N
        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnImprimir.setBackground(new java.awt.Color(0, 102, 51));
        btnImprimir.setForeground(new java.awt.Color(254, 254, 254));
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/printer_error_24px.png"))); // NOI18N
        btnImprimir.setText("Reimprimir");
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdA4);
        rdA4.setForeground(new java.awt.Color(254, 254, 254));
        rdA4.setText("A4");

        buttonGroup2.add(rdTermica);
        rdTermica.setForeground(new java.awt.Color(254, 254, 254));
        rdTermica.setSelected(true);
        rdTermica.setText("Termica");

        buttonGroup2.add(rdA5);
        rdA5.setForeground(new java.awt.Color(254, 254, 254));
        rdA5.setText("A5");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addComponent(rdA4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdTermica)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(rdA5))
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(dataInicio, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                                    .addComponent(DataFim, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel19Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel31))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel19Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnBuscar, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dataInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(DataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnImprimir))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
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
        jScrollPane5.setViewportView(listaFactura);

        jButton13.setBackground(new java.awt.Color(0, 102, 51));
        jButton13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/prev.png"))); // NOI18N
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 39, Short.MAX_VALUE)
                .addGap(67, 67, 67))
        );

        tabulacao.addTab("Reemprimir - Factura", jPanel18);

        jPanel20.setBackground(new java.awt.Color(36, 36, 39));

        btnFecharCaixa.setBackground(new java.awt.Color(0, 102, 51));
        btnFecharCaixa.setForeground(new java.awt.Color(254, 254, 254));
        btnFecharCaixa.setText("Fechar o Caixa");
        btnFecharCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharCaixaActionPerformed(evt);
            }
        });

        btnReforcarCaixa.setBackground(new java.awt.Color(0, 102, 51));
        btnReforcarCaixa.setForeground(new java.awt.Color(254, 254, 254));
        btnReforcarCaixa.setText("Reforçar o Caixa");
        btnReforcarCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReforcarCaixaActionPerformed(evt);
            }
        });

        btnNovaTela.setBackground(new java.awt.Color(0, 102, 51));
        btnNovaTela.setForeground(new java.awt.Color(254, 254, 254));
        btnNovaTela.setText("+ Balcão");
        btnNovaTela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovaTelaActionPerformed(evt);
            }
        });

        btnAbrirCaixa.setBackground(new java.awt.Color(0, 102, 51));
        btnAbrirCaixa.setForeground(new java.awt.Color(254, 254, 254));
        btnAbrirCaixa.setText("Abrir o Caixa");
        btnAbrirCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirCaixaActionPerformed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(0, 102, 51));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/prev.png"))); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(btnFecharCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAbrirCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(btnReforcarCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNovaTela, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReforcarCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNovaTela, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFecharCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAbrirCaixa, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(191, 191, 191)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 39, Short.MAX_VALUE)
                .addGap(67, 67, 67))
        );

        tabulacao.addTab("Caixa", jPanel20);

        jPanel21.setBackground(new java.awt.Color(36, 36, 39));

        jPanel22.setBackground(new java.awt.Color(36, 36, 39));
        jPanel22.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informação da(s) Guia(s)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(254, 254, 254))); // NOI18N

        txtLocalEntrega.setEnabled(false);

        jLabel35.setForeground(new java.awt.Color(254, 254, 254));
        jLabel35.setText("Local Entrega");

        txtCarga.setEnabled(false);

        jLabel36.setForeground(new java.awt.Color(254, 254, 254));
        jLabel36.setText("Local de Carga");

        buttonGroup3.add(chkImprimirGuiaRemessa);
        chkImprimirGuiaRemessa.setForeground(new java.awt.Color(254, 254, 254));
        chkImprimirGuiaRemessa.setText("Imprimir  Remessa ?");
        chkImprimirGuiaRemessa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkImprimirGuiaRemessaActionPerformed(evt);
            }
        });

        jPanel23.setBackground(new java.awt.Color(36, 36, 39));
        jPanel23.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Transporte", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12), new java.awt.Color(254, 254, 254))); // NOI18N

        dataTransporte.setEnabled(false);

        hora.setModel(new javax.swing.SpinnerNumberModel(0, 0, 23, 1));
        hora.setEnabled(false);

        minuto.setModel(new javax.swing.SpinnerNumberModel(0, 0, 59, 1));
        minuto.setEnabled(false);

        jLabel37.setText(":");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dataTransporte, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hora, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(minuto, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dataTransporte, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(minuto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonGroup3.add(chkImprimirGuiaTransporte);
        chkImprimirGuiaTransporte.setForeground(new java.awt.Color(254, 254, 254));
        chkImprimirGuiaTransporte.setText("Imprimir Transporte ?");
        chkImprimirGuiaTransporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkImprimirGuiaTransporteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCarga, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtLocalEntrega)
                            .addGroup(jPanel22Layout.createSequentialGroup()
                                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel22Layout.createSequentialGroup()
                                        .addComponent(chkImprimirGuiaRemessa)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(chkImprimirGuiaTransporte))
                                    .addComponent(jLabel36)
                                    .addComponent(jLabel35))
                                .addGap(0, 57, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLocalEntrega, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkImprimirGuiaRemessa)
                    .addComponent(chkImprimirGuiaTransporte))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel38.setForeground(new java.awt.Color(254, 254, 254));
        jLabel38.setText("Observação da factura");

        jButton12.setBackground(new java.awt.Color(0, 102, 51));
        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/prev.png"))); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel21Layout.createSequentialGroup()
                        .addComponent(jLabel38)
                        .addGap(158, 158, 158))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtObservacao, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel21Layout.createSequentialGroup()
                                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 39, Short.MAX_VALUE)
                .addGap(68, 68, 68))
        );

        tabulacao.addTab("Outras Informações", jPanel21);

        jPanelBaixo3.add(tabulacao, java.awt.BorderLayout.CENTER);

        setName("BalcaoView"); // NOI18N
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(36, 36, 39));

        jPanelTopo.setBackground(new java.awt.Color(36, 36, 39));
        jPanelTopo.setLayout(new java.awt.BorderLayout());

        jPanelTopo1.setBackground(new java.awt.Color(36, 36, 39));
        jPanelTopo1.setName("jPanelTopo1"); // NOI18N

        jButton2.setBackground(new java.awt.Color(166, 166, 166));
        jButton2.setText("Imprimir Conta");

        jButton1.setBackground(new java.awt.Color(0, 102, 51));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Transferir Pedido");

        javax.swing.GroupLayout jPanelTopo1Layout = new javax.swing.GroupLayout(jPanelTopo1);
        jPanelTopo1.setLayout(jPanelTopo1Layout);
        jPanelTopo1Layout.setHorizontalGroup(
            jPanelTopo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelTopo1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );
        jPanelTopo1Layout.setVerticalGroup(
            jPanelTopo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelTopo1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanelTopo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27))
        );

        jPanelTopo.add(jPanelTopo1, java.awt.BorderLayout.CENTER);

        jPanelBaixo.setBackground(new java.awt.Color(36, 36, 39));
        jPanelBaixo.setLayout(new java.awt.BorderLayout());

        jPanelBaixo1.setBackground(new java.awt.Color(36, 36, 39));
        jPanelBaixo1.setName("jPanelBaixo1"); // NOI18N

        jPanel13.setBackground(new java.awt.Color(36, 36, 39));
        jPanel13.setLayout(new java.awt.GridLayout());

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Artigo");
        jLabel6.setPreferredSize(new java.awt.Dimension(68, 17));
        jPanel13.add(jLabel6);

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Qtd");
        jPanel13.add(jLabel7);

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Preço");
        jPanel13.add(jLabel8);

        jPanel12.setBackground(new java.awt.Color(36, 36, 39));
        jPanel12.setLayout(new java.awt.GridLayout(1, 0));

        jButton7.setBackground(new java.awt.Color(166, 166, 166));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/Administrative Tools_45px.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel12.add(jButton7);

        jButton8.setBackground(new java.awt.Color(234, 65, 10));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/imac_exit_100px.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel12.add(jButton8);

        jPanel11.setBackground(new java.awt.Color(36, 36, 39));
        jPanel11.setLayout(new java.awt.GridLayout(1, 0));

        jButton3.setBackground(new java.awt.Color(0, 102, 51));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/Cash Register_50px.png"))); // NOI18N
        jPanel11.add(jButton3);

        btnFacturar.setBackground(new java.awt.Color(0, 102, 51));
        btnFacturar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/Print_45px.png"))); // NOI18N
        btnFacturar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturarActionPerformed(evt);
            }
        });
        jPanel11.add(btnFacturar);

        btnPesquisar.setBackground(new java.awt.Color(0, 102, 51));
        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/Search_150px.png"))); // NOI18N
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });
        jPanel11.add(btnPesquisar);

        btnRemover.setBackground(new java.awt.Color(0, 102, 51));
        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/Checkout_45px.png"))); // NOI18N
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });
        jPanel11.add(btnRemover);

        jScrollPane4.setBackground(new java.awt.Color(217, 221, 234));
        jScrollPane4.setBorder(null);
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelItems1.setBackground(new java.awt.Color(36, 36, 39));
        panelItems1.setBorder(null);
        panelItems1.setLayout(new java.awt.GridLayout(10, 0, 0, 5));
        jScrollPane4.setViewportView(panelItems1);

        javax.swing.GroupLayout jPanelBaixo1Layout = new javax.swing.GroupLayout(jPanelBaixo1);
        jPanelBaixo1.setLayout(jPanelBaixo1Layout);
        jPanelBaixo1Layout.setHorizontalGroup(
            jPanelBaixo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBaixo1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelBaixo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelBaixo1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanelBaixo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4)
                            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(5, 5, 5)))
                .addContainerGap())
        );
        jPanelBaixo1Layout.setVerticalGroup(
            jPanelBaixo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBaixo1Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addGap(70, 70, 70))
        );

        jPanelBaixo.add(jPanelBaixo1, java.awt.BorderLayout.CENTER);

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("IVA");

        lblTroco.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblTroco.setForeground(new java.awt.Color(255, 255, 255));
        lblTroco.setText("0.0");

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Troco");

        lblTotal.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        lblTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblTotal.setText("0.0");

        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Total");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Sub.Total");

        lblSubTotal.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblSubTotal.setForeground(new java.awt.Color(255, 255, 255));
        lblSubTotal.setText("0.0");

        lblIVA.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblIVA.setForeground(new java.awt.Color(255, 255, 255));
        lblIVA.setText("0.0");

        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Desconto");

        lblDesconto.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblDesconto.setForeground(new java.awt.Color(255, 255, 255));
        lblDesconto.setText("0.0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelTopo, javax.swing.GroupLayout.DEFAULT_SIZE, 533, Short.MAX_VALUE)
            .addComponent(jPanelBaixo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbFotoProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTroco, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblIVA, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblDesconto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSubTotal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanelTopo, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblSubTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDesconto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblIVA)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTroco, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbFotoProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelBaixo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel1, java.awt.BorderLayout.LINE_END);

        jPanel2.setBackground(new java.awt.Color(166, 166, 166));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(166, 166, 166));

        jPanel6.setBackground(new java.awt.Color(166, 166, 166));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/prev.png"))); // NOI18N
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        jPanel7.setBackground(new java.awt.Color(166, 166, 166));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/next.png"))); // NOI18N
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
        );

        jScrollPane1.setBackground(new java.awt.Color(166, 166, 166));
        jScrollPane1.setBorder(null);

        jPanel15.setBackground(new java.awt.Color(166, 166, 166));
        jPanel15.setBorder(null);
        jPanel15.setLayout(new java.awt.BorderLayout());

        panelCategoria.setBackground(new java.awt.Color(166, 166, 166));
        panelCategoria.setBorder(null);

        javax.swing.GroupLayout panelCategoriaLayout = new javax.swing.GroupLayout(panelCategoria);
        panelCategoria.setLayout(panelCategoriaLayout);
        panelCategoriaLayout.setHorizontalGroup(
            panelCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 537, Short.MAX_VALUE)
        );
        panelCategoriaLayout.setVerticalGroup(
            panelCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel15.add(panelCategoria, java.awt.BorderLayout.CENTER);

        jScrollPane1.setViewportView(jPanel15);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1))
        );

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(193, 193, 193));

        jPanel5.setBackground(new java.awt.Color(193, 193, 193));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/4.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/3.png"))); // NOI18N
        jLabel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(77, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(27, 27, 27))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jPanel8.setBackground(new java.awt.Color(193, 193, 193));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/banner_rodape.png"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.add(jPanel4, java.awt.BorderLayout.PAGE_END);

        panelContainer.setBackground(new java.awt.Color(166, 166, 166));

        jPanel9.setBackground(new java.awt.Color(166, 166, 166));

        lblDescricaoProduto.setBackground(new java.awt.Color(36, 36, 39));
        lblDescricaoProduto.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblDescricaoProduto.setForeground(new java.awt.Color(255, 255, 255));
        lblDescricaoProduto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDescricaoProduto.setText("Bolo Rei  - 20.000,00 kz");
        lblDescricaoProduto.setOpaque(true);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDescricaoProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 595, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDescricaoProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addContainerGap())
        );

        panelProduto.setBackground(new java.awt.Color(166, 166, 166));
        panelProduto.setLayout(new java.awt.GridLayout(3, 4, 5, 5));

        javax.swing.GroupLayout panelContainerLayout = new javax.swing.GroupLayout(panelContainer);
        panelContainer.setLayout(panelContainerLayout);
        panelContainerLayout.setHorizontalGroup(
            panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelContainerLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(panelProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );
        panelContainerLayout.setVerticalGroup(
            panelContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(panelContainer, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked

        this.countClick++;
        next();

    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        this.countClick--;
        prev();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void txtPesquisarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtPesquisarCaretUpdate

        if (!txtPesquisar.getText().trim().isEmpty()) {
            getProduto(txtPesquisar.getText(), null);
        }
    }//GEN-LAST:event_txtPesquisarCaretUpdate

    private void txtPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPesquisarActionPerformed

        if (!txtPesquisar.getText().trim().isEmpty()) {

            EntradaStockItemController controller = new EntradaStockItemController();
            List<EntradaStockItemModel> stock = getStockProduto(txtPesquisar.getText(), null);

            if (stock != null) {

                if (stock.size() > 0) {
                    //EntradaStockItemModel entrada = stock.get(0);
                    mostraProduto(txtPesquisar.getText(), null, true);

                    quantidade.setValue(1);
                    txtPesquisar.setText("");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione o produto");
        }
    }//GEN-LAST:event_txtPesquisarActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        if (!txtPesquisar.getText().trim().isEmpty()) {
            mostraProduto(txtPesquisar.getText(), null, false);
        }
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void txtDescontoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescontoKeyReleased

        int index = txtDesconto.getText().trim().indexOf("%");
        String descontoString = txtDesconto.getText().trim().replaceAll("%", "");

        calcular();

        double descontoActual = Calculo.getValueNormal(lblDesconto.getText());
        double subTotal = Calculo.getValueNormal(lblSubTotal.getText());

        double total = 0;

        if (index >= 0) {

            descontoGlobal = (subTotal * Double.parseDouble(descontoString.trim())) / 100;

        } else {
            descontoGlobal = !txtDesconto.getText().isEmpty() ? Double.parseDouble(txtDesconto.getText().trim()) : 0;
        }
        double totalDesconto = descontoActual + descontoGlobal;
        total = subTotal - totalDesconto;
        lblTotal.setText(Calculo.converterCash(total));
        lblDesconto.setText(Calculo.converterCash(totalDesconto));

        txtValorEntregue.setText("0");
        txtMulticaixa.setText("0");
    }//GEN-LAST:event_txtDescontoKeyReleased

    private void txtRetencaoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtRetencaoKeyReleased
        // TODO add your handling code here:
//        for (int i = 0; i < tbPedido.getRowCount(); i++) {
//            recalcularRetencao(i);
//        }
//        calcular();
    }//GEN-LAST:event_txtRetencaoKeyReleased

    private void cboFormaPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFormaPagamentoActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        selecionadoForma();

    }//GEN-LAST:event_cboFormaPagamentoActionPerformed

    private void txtValorEntregueKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtValorEntregueKeyReleased
        // TODO add your handling code here:
        lblTroco.setText(Calculo.converterCash(calcularTroco()));
    }//GEN-LAST:event_txtValorEntregueKeyReleased

    private void txtMulticaixaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMulticaixaKeyReleased
        // TODO add your handling code here:
        lblTroco.setText(Calculo.converterCash(calcularTroco()));
    }//GEN-LAST:event_txtMulticaixaKeyReleased

    private void chkPerfomaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPerfomaActionPerformed
        // TODO add your handling code here:
        carrinho = new ArrayList<>();
        calcular();
        cboFormaPagamento.setSelectedIndex(0);
        limpar();
    }//GEN-LAST:event_chkPerfomaActionPerformed

    private void jLabel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel4MouseClicked
        // TODO add your handling code here:
        this.countClickProduto++;
        nextProduto(false);
    }//GEN-LAST:event_jLabel4MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        this.countClickProduto--;
        prevProduto(false);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        // TODO add your handling code here:
        selectPanelCima();
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // TODO add your handling code here:
        Object[] opcao = {"Sim", "Não"};
        if (removerCarrinho != null) {
            if (JOptionPane.showOptionDialog(null, " Remover>: " + removerCarrinho.getProduto().getDesignacao(), " Alerta ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcao, opcao[0]) == JOptionPane.YES_OPTION) {
                carrinho.remove(removerCarrinho);
                if (telaRest) {
                    removerPedidoItem(removerCarrinho);
                    
                }
                calcular();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione o produto a Remover!!");
        }
        removerCarrinho = null;
        focoTextPesquisar();

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

        if (carrinho.size() <= 0) {
            pedidoController.saveOrUpdate(pedido);
        } else {
            controller.saveOrUpdate(pedidoItem);
        }

    }

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        PedidoMesaVIew pedido = (PedidoMesaVIew) pedidoMesaView;
        pedido.init();
    }//GEN-LAST:event_jButton8ActionPerformed

    private void btnFacturarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturarActionPerformed
        // TODO add your handling code here:
        selectPanelBaixo(jPanelBaixo2);
    }//GEN-LAST:event_btnFacturarActionPerformed

    public void selectPanelBaixo(JPanel panelBaixo) {
        JPanel panel = (JPanel) jPanelBaixo.getComponent(0);
        String nome = panel.getName();

        jPanelBaixo.removeAll();
        if (nome.contains("1")) {

            jPanelBaixo.add(panelBaixo);
        } else {
            jPanelBaixo.add(jPanelBaixo1);
        }
        jPanelBaixo.updateUI();
    }

    public void selectPanelCima() {

        JPanel panel = (JPanel) jPanelTopo.getComponent(0);
        String nome = panel.getName();

        jPanelTopo.removeAll();
        if (nome.contains("1")) {
            jPanelTopo.add(jPanelTopo2);
        } else {
            jPanelTopo.add(jPanelTopo1);
        }
        jPanelTopo.updateUI();
    }

    private boolean verificaLicenca() {

        try {

            ArmazenamentoController<LicencaModel> ficheiro = new ArmazenamentoController<>("Licenca");
            LicencaModel modelo = ficheiro.getAll().get(0);

            String vectorData[] = modelo.getDataFimLicenca().split("-");
            String vectorDataActual[] = DataComponent.getData1().split("-");

            int anoActual = Integer.parseInt(vectorDataActual[0]);
            int mesActual = Integer.parseInt(vectorDataActual[1]);
            int diaActual = Integer.parseInt(vectorDataActual[2]);

            int anoLicenca = Integer.parseInt(vectorData[0]);
            int mesLicenca = Integer.parseInt(vectorData[1]);
            int diaLicenca = Integer.parseInt(vectorData[2]);

            if (anoActual == anoLicenca) {

                if (mesActual == mesLicenca) {

                    if (diaActual == diaLicenca) {
                        JOptionPane.showMessageDialog(this, "A licença do software expira a manhã .\n"
                                + "Entra em conctato com o fornecedor\n"
                                + " do software para activação da licença\nContacto: 938 393 669\nEmail:zetasoft100@gmail.com");
                        return true;
                    } else if (diaActual > diaLicenca) {

                        JOptionPane.showMessageDialog(this, "A licença do software expiro.\n"
                                + "Entra em conctato com o fornecedor\n"
                                + " do software para activação da licença\nContacto: 938 393 669\nEmail:zetasoft100@gmail.com");
                        ficheiro.removerArquivo();
                        return false;
                    } else if (diaActual < diaLicenca) {
                        if ((diaLicenca - diaActual) >= 0 && diaLicenca - diaActual <= 7) {
                            JOptionPane.showMessageDialog(this, "A licença do software Vai Expirar daqui a " + (diaLicenca - diaActual) + " dia(s)\n"
                                    + "Entra em conctato com o fornecedor\n"
                                    + " do software para solicitar nova licença\nContacto: 938 393 669\nEmail:zetasoft100@gmail.com ");
                        }
                        return true;
                    }
                } else if (mesActual < mesLicenca) {

                    return true;
                } else {
                    ficheiro.removerArquivo();
                    return false;
                }
            } else if (anoActual < anoLicenca) {

                return true;
            }

            JOptionPane.showMessageDialog(this, "A licença do software expiro.\n"
                    + "Entra em conctato com o fornecedor"
                    + " do software para activação da licença");
            ficheiro.removerArquivo();
            return false;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "A licença do software Invalida.\n"
                    + "Foi Modificado o arquivo de licença"
                    + "Entra em conctato com o fornecedor"
                    + " do software para activação da licença");
        }
        return false;
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
        facturaModel.setTotalRetencao(Calculo.getValueNormal("0"));

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

    public void alteraCarteiraCliente(FacturaModel facturaModel) {

        ClienteModel cliente = (ClienteModel) cboCliente.getSelectedItem();

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

    private void reduzirQtd(String codBarra, double qtd) {

        double qtdNova = qtd;
        List<EntradaStockItemModel> lista = getEntradaItem(codBarra);
        EntradaStockItemController controller = new EntradaStockItemController();
        for (EntradaStockItemModel e : lista) {
            System.out.println("produto " + e.getProduto() + "\ndata" + e.getDataExpiracao());
            if (idArmazem > 0) {
                if (idArmazem == e.getArmazem().getId()) {

                    if (qtdNova > e.getQtd()) {

                        qtdNova -= e.getQtd();
                        e.setQtd(0);

                        if (controller.updateItem(e)) {

                            definirEntraProdutoItemEmUso(e);
                        }

                    } else {

                        e.setQtd(e.getQtd() - qtdNova);
                        if (controller.updateItem(e)) {
                            definirEntraProdutoItemEmUso(e);
                        }
                        return;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Não foi selecionado armazem principal");
                return;
            }

        }

    }

    private List<EntradaStockItemModel> getEntradaItem(String codBarra) {

        EntradaStockItemController controller = new EntradaStockItemController();
        return controller.getQtd(codBarra, idArmazem);

    }

    public void definirEntraProdutoItemEmUso(EntradaStockItemModel e) {

        EntradaStockItemController entradaStockItemController = new EntradaStockItemController();
        EntradaStockItemModel entraNovaUso = new EntradaStockItemModel();
        entraNovaUso = getItemUso(entradaStockItemController.getPendentes(e.getProduto()), e);

        System.out.println("\nentrada: ----" + e.getQtd() + "estdo" + e.getEstado().getId() + "\nNOvo:---" + entraNovaUso.getQtd() + "estado:----" + entraNovaUso.getEstado().getId());

        if (entradaStockItemController.saveOrUpdate(e)) {

            if (entradaStockItemController.saveOrUpdate(entraNovaUso)) {

                if (e.getId() != entraNovaUso.getId()) {

                    if (e.getEstado().getId() == 11) { // significa que o produto comercializado expirou
                        JOptionPane.showMessageDialog(null, "O produto: " + e.getProduto().getDesignacao() + " anterior Expirou\n"
                                + "Passara a se vender outros Apartir de Deste Momento!\nOBS: Por-favor informe ao Administrador");

                    } else {
                        if (e.getEstado().getId() == 12) { // significa que esgotou 
                            JOptionPane.showMessageDialog(null, "O produto: " + e.getProduto().getDesignacao() + "  anterior Terminou\n"
                                    + "Passara a se vender outros Apartir de Deste Momento!\nOBS: Por-favor informe ao Administrador");

                        } else { // significa que devido a ordem passara para pendente

                            JOptionPane.showMessageDialog(null, "O produto: " + e.getProduto().getDesignacao() + "  anterior Estará Pendente Devido a forma de ordenação\n(" + e.getProduto().getOrganizacao()
                                    + ")\nPassara a se vender outros Apartir de Deste Momento!\nOBS: Por-favor informe ao Administrador");
                        }
                    }
                } else {

                    if (e.getEstado().getId() == 11) { // significa que o produto comercializado expirou
                        JOptionPane.showMessageDialog(null, "O produto: " + e.getProduto().getDesignacao() + " anterior Expirou\n\nOBS: Por-favor informe ao Administrador");

                    } else if (e.getEstado().getId() == 12) { // significa que o produto comercializado expirou
                        JOptionPane.showMessageDialog(null, "O produto: " + e.getProduto().getDesignacao() + " anterior Terminou\n\nOBS: Por-favor informe ao Administrador");
                    }
                }
            }
        }

    }

    public EntradaStockItemModel getItemUso(List<EntradaStockItemModel> items, EntradaStockItemModel e) {
        EntradaStockItemModel novo = new EntradaStockItemModel();
        System.out.println("entrada: ----" + e.getQtd() + "\nentradas:---" + items.size());
        if (e.getQtd() <= 0) {

            if (e.getProduto().isStocavel()) {// verifica se o produto é stocavel
                if (items.size() > 0) {

                    e.setEstado(new EstadoModel(12, ""));
                    items.get(0).setEstado(new EstadoModel(1, ""));
                    novo = items.get(0);

                } else {
                    e.setEstado(new EstadoModel(12, ""));
                    novo = e;

                }
            } else {
                e.setEstado(new EstadoModel(1, ""));
                novo = e;
            }

        } else {

            if (e.getEstado().getId() == 11) {// se expirar

                if (items.size() > 0) {

                    items.get(0).setEstado(new EstadoModel(1, ""));
                    novo = items.get(0);

                } else {
                    novo = e;
                }
            } else {

                novo = e;
            }
        }

        for (EntradaStockItemModel modelo : items) { // primeiro a entrar primeiro a sair
            if (e.getProduto().getOrganizacao().equalsIgnoreCase("ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)")) {
                if (DataComponent.compareData(novo.getEntrada().getData(), modelo.getEntrada().getData())) {

                    if (e.getEstado().getId() != 11) {
                        e.setEstado(new EstadoModel(13, ""));
                    }
                    modelo.setEstado(new EstadoModel(1, ""));

                    novo = modelo;
                }
            } else {

                if (e.getProduto().getOrganizacao().equalsIgnoreCase("ORDEM DE CHEGADA( ULTIMO A  ENTRAR PRIMEIRO A SAIR)")) { // ultimo a entrar primeiro a sair
                    if (DataComponent.compareData(modelo.getEntrada().getData(), novo.getEntrada().getData())) {

                        if (e.getEstado().getId() != 11) {
                            e.setEstado(new EstadoModel(13, ""));
                        }
                        modelo.setEstado(new EstadoModel(1, ""));

                        novo = modelo;
                    }
                } else { // pela data de expircao
                    if (DataComponent.compareData(novo.getDataExpiracao(), modelo.getDataExpiracao())) {

                        if (e.getEstado().getId() != 11) {
                            e.setEstado(new EstadoModel(13, ""));
                        }
                        modelo.setEstado(new EstadoModel(1, ""));

                        novo = modelo;
                    }
                }

            }

        }
        return novo;
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

    public void liquidarPedido() {
        PedidoTesteController pedido = new PedidoTesteController();
        if (pedido.liquidar(idPedido)) {
            idPedido = 0;
        }
    }

    private void btnTerminarFacturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTerminarFacturaActionPerformed
        // TODO add your handling code here:

        if (DataConforme() && verificaLicenca()) {

            if (carrinho.size() > 0) {

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

                        ClienteModel cliente = (ClienteModel) cboCliente.getSelectedItem();

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

                                    for (FacturaItemModel facturaItemModel : carrinho) {

                                        facturaItemModel.setFactura(facturaModel);

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
                                Logger.getLogger(BalcaoView.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InvalidKeyException ex) {
                                Logger.getLogger(BalcaoView.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SignatureException ex) {
                                Logger.getLogger(BalcaoView.class.getName()).log(Level.SEVERE, null, ex);
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
                            //JOptionPane.showMessageDialog(this, "Venda efectuada com sucesso");
                            if (chkTermica.isSelected()) {
                                FacturaIreport.termica(facturaModel, tipoFactura(), true);
                            } else if (chkA4.isSelected()) {
                                FacturaIreport.A4(facturaModel, tipoFactura(), true);
                            } else if (chkA5.isSelected()) {
                                FacturaIreport.A5(facturaModel, tipoFactura(), true);
                            }

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
                        mostraProduto(txtPesquisar.getText(), null, false);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Preencha a inofrmação da guia", "Alerta", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Adicione produto na factura", "Alerta", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Acerte a data do seu Computador Ou Verifique a data da sua Licença");
        }
    }//GEN-LAST:event_btnTerminarFacturaActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        selectPanelBaixo(jPanelBaixo2);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void cboMoedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMoedaActionPerformed
        // TODO add your handling code here:
        txtMoedaCambio.setText("" + getMoeda().getCambioDiario().getValor());
        this.calcularCambio();
    }//GEN-LAST:event_cboMoedaActionPerformed

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

    public boolean isFacturaProforma() throws Exception {

        try {
            DefaultListModel listDefault = (DefaultListModel) listaFactura.getModel();

            FacturaModel modelo = (FacturaModel) listDefault.getElementAt(listaFactura.getSelectedIndex());

            return modelo.getTipoFacturas().equalsIgnoreCase("Perfoma");

        } catch (Exception ex) {

            throw ex;
        }

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

    private String tipofacturaByTipo() {

        if (tipofactura.trim().equals("Venda")) {
            return "Factura/Recibo";
        } else if (tipofactura.trim().equals("Perfoma")) {
            return "Factura Pró-forma";
        } else {
            return "Factura Crédito";
        }
    }

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

            //fecha todas as telas de venda aberta por este usuário
            Frame[] frames = getFrames();
            for (int i = frames.length - 1; i > 1; i--) {

                if (frames[i].getName().equals("BalcaoView")) {
                    if (frames[i].hashCode() != pedidoMesaView.hashCode()) {
                        frames[i].hide();
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnFecharCaixaActionPerformed
    private void controladorCampo(boolean flag) {

        txtCliente.setEnabled(flag);
        txtConvertido.setEnabled(flag);
        txtDesconto.setEnabled(flag);
        txtMoedaCambio.setEnabled(flag);
        txtMulticaixa.setEnabled(flag);
        txtPesquisar.setEnabled(flag);
        txtValorEntregue.setEnabled(flag);
        txtValorEntregueMoeda.setEnabled(flag);

        btnFecharCaixa.setEnabled(flag);
        btnRemover.setEnabled(flag);
        btnFacturar.setEnabled(flag);
        btnTerminarFactura.setEnabled(flag);
        btnCalcularCambio.setEnabled(flag);
        btnReforcarCaixa.setEnabled(flag);
        btnAbrirCaixa.setEnabled(!flag);
        btnNovaTela.setEnabled(flag);

        cboCliente.setEnabled(flag);
        cboFormaPagamento.setEnabled(flag);
        cboMoeda.setEnabled(flag);

        quantidade.setEnabled(flag);

        chkPerfoma.setEnabled(flag);
        chkA4.setEnabled(flag);
        chkA5.setEnabled(flag);
        chkCarta.setEnabled(flag);

    }


    private void btnReforcarCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReforcarCaixaActionPerformed
        // TODO add your handling code here:
        new CaixaView(usuario, caixaModel, true).setVisible(true);
    }//GEN-LAST:event_btnReforcarCaixaActionPerformed

    private void btnNovaTelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovaTelaActionPerformed
        // TODO add your handling code here:
        new PedidoMesaVIew(usuario).setVisible(true);
    }//GEN-LAST:event_btnNovaTelaActionPerformed

    private void btnAbrirCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirCaixaActionPerformed
        // TODO add your handling code here:

        if (DataConforme()) {

            Object[] opcao = {"Sim", "Não"};

            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

            CaixaController caixaController = new CaixaController();

            CaixaModel caixaModel = caixaController.getByDateAndUsuario(dateFormat1.format(new Date()), usuario.getId(), "Aberto");

            if (caixaModel != null) {

                if (caixaModel.getId() > 0) {
                    new PedidoMesaVIew(usuario).setVisible(true);
                } else {
                    new CaixaView(usuario).setVisible(true);
                }
            } else {

                caixaModel = caixaController.getLastByUsuario(usuario.getId(), "Aberto");

                if (caixaModel != null) {

                    if (JOptionPane.showOptionDialog(null, "Estamos em um Novo Dia, Existem Caixa Aberto em:" + caixaModel.getDataAbertura()
                            + "\nDeseja Abrir Novo caixa?", " Alerta ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcao, opcao[0]) == JOptionPane.YES_OPTION) {
                        dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        caixaModel.setDataFecho(dateFormat1.format(new Date()));
                        caixaModel.setEstado("Fechado");

                        if (caixaController.saveOrUpdate(caixaModel)) {

                            FacturaIreport.fechoCaixa(caixaModel);
                            new CaixaView(usuario).setVisible(true);
                        }
                    } else {
                        new PedidoMesaVIew(usuario).setVisible(true);
                    }

                } else {
                    new CaixaView(usuario).setVisible(true);
                }

            }

        } else {
            JOptionPane.showMessageDialog(this, "Acerte a data do seu Computador");
        }
        pedidoMesaView.hide();
    }//GEN-LAST:event_btnAbrirCaixaActionPerformed

    public static boolean DataConforme() // verifica se a data actual é maior ou igual que a data da ultima factura
    {
        FacturaController facturaController = new FacturaController();
        return DataComponent.compareDataLastFactura(facturaController.getDataLastFactura());
    }

    private void chkImprimirGuiaRemessaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkImprimirGuiaRemessaActionPerformed
        // TODO add your handling code here:

        if (estadoRemessa) {

            buttonGroup3.clearSelection();
            estadoRemessa = false;
        } else {
            estadoRemessa = true;
            estadoTransport = false;

            dataTransporte.setEnabled(!chkImprimirGuiaRemessa.isSelected());
            hora.setEnabled(!chkImprimirGuiaRemessa.isSelected());
            minuto.setEnabled(!chkImprimirGuiaRemessa.isSelected());
        }

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

        if (estadoTransport) {
            buttonGroup3.clearSelection();
            estadoTransport = false;
        } else {

            estadoTransport = true;
            estadoRemessa = false;
        }

        dataTransporte.setEnabled(chkImprimirGuiaTransporte.isSelected());
        hora.setEnabled(chkImprimirGuiaTransporte.isSelected());
        minuto.setEnabled(chkImprimirGuiaTransporte.isSelected());
        txtCarga.setEnabled(chkImprimirGuiaTransporte.isSelected());
        txtLocalEntrega.setEnabled(chkImprimirGuiaTransporte.isSelected());

        limparGuia();
    }//GEN-LAST:event_chkImprimirGuiaTransporteActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        selectPanelBaixo(jPanelBaixo3);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        selectPanelBaixo(jPanelBaixo3);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        selectPanelBaixo(jPanelBaixo3);
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        selectPanelBaixo(jPanelBaixo3);
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        // TODO add your handling code here:
        selectPanelBaixo(jPanelBaixo3);
    }//GEN-LAST:event_jButton13ActionPerformed

    /*
    0 - 9
    10 - 19    =    10-(9+1) =0 / 19 - (9+2)
    20 - 29
    30 - 31
    
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    com.toedter.calendar.JDateChooser DataFim;
    javax.swing.JButton btnAbrirCaixa;
    javax.swing.JButton btnBuscar;
    javax.swing.JButton btnCalcularCambio;
    javax.swing.JButton btnFacturar;
    javax.swing.JButton btnFecharCaixa;
    javax.swing.JButton btnImprimir;
    javax.swing.JButton btnNovaTela;
    javax.swing.JButton btnPesquisar;
    javax.swing.JButton btnReforcarCaixa;
    javax.swing.JButton btnRemover;
    javax.swing.JButton btnTerminarFactura;
    javax.swing.ButtonGroup buttonGroup1;
    javax.swing.ButtonGroup buttonGroup2;
    javax.swing.ButtonGroup buttonGroup3;
    javax.swing.JComboBox<String> cboCliente;
    javax.swing.JComboBox<String> cboFormaPagamento;
    javax.swing.JComboBox<String> cboMoeda;
    javax.swing.JCheckBox chkA4;
    javax.swing.JCheckBox chkA5;
    javax.swing.JCheckBox chkCarta;
    javax.swing.JCheckBox chkImprimirGuiaRemessa;
    javax.swing.JCheckBox chkImprimirGuiaTransporte;
    javax.swing.JCheckBox chkPerfoma;
    javax.swing.JCheckBox chkTermica;
    com.toedter.calendar.JDateChooser dataInicio;
    com.toedter.calendar.JDateChooser dataTransporte;
    javax.swing.JSpinner hora;
    javax.swing.JButton jButton1;
    javax.swing.JButton jButton10;
    javax.swing.JButton jButton11;
    javax.swing.JButton jButton12;
    javax.swing.JButton jButton13;
    javax.swing.JButton jButton2;
    javax.swing.JButton jButton3;
    javax.swing.JButton jButton7;
    javax.swing.JButton jButton8;
    javax.swing.JButton jButton9;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel10;
    javax.swing.JLabel jLabel11;
    javax.swing.JLabel jLabel12;
    javax.swing.JLabel jLabel13;
    javax.swing.JLabel jLabel14;
    javax.swing.JLabel jLabel15;
    javax.swing.JLabel jLabel16;
    javax.swing.JLabel jLabel17;
    javax.swing.JLabel jLabel18;
    javax.swing.JLabel jLabel19;
    javax.swing.JLabel jLabel2;
    javax.swing.JLabel jLabel20;
    javax.swing.JLabel jLabel21;
    javax.swing.JLabel jLabel22;
    javax.swing.JLabel jLabel24;
    javax.swing.JLabel jLabel26;
    javax.swing.JLabel jLabel27;
    javax.swing.JLabel jLabel28;
    javax.swing.JLabel jLabel29;
    javax.swing.JLabel jLabel3;
    javax.swing.JLabel jLabel30;
    javax.swing.JLabel jLabel31;
    javax.swing.JLabel jLabel33;
    javax.swing.JLabel jLabel35;
    javax.swing.JLabel jLabel36;
    javax.swing.JLabel jLabel37;
    javax.swing.JLabel jLabel38;
    javax.swing.JLabel jLabel4;
    javax.swing.JLabel jLabel5;
    javax.swing.JLabel jLabel6;
    javax.swing.JLabel jLabel7;
    javax.swing.JLabel jLabel8;
    javax.swing.JLabel jLabel9;
    javax.swing.JPanel jPanel1;
    javax.swing.JPanel jPanel10;
    javax.swing.JPanel jPanel11;
    javax.swing.JPanel jPanel12;
    javax.swing.JPanel jPanel13;
    javax.swing.JPanel jPanel14;
    javax.swing.JPanel jPanel15;
    javax.swing.JPanel jPanel17;
    javax.swing.JPanel jPanel18;
    javax.swing.JPanel jPanel19;
    javax.swing.JPanel jPanel2;
    javax.swing.JPanel jPanel20;
    javax.swing.JPanel jPanel21;
    javax.swing.JPanel jPanel22;
    javax.swing.JPanel jPanel23;
    javax.swing.JPanel jPanel3;
    javax.swing.JPanel jPanel4;
    javax.swing.JPanel jPanel5;
    javax.swing.JPanel jPanel6;
    javax.swing.JPanel jPanel7;
    javax.swing.JPanel jPanel8;
    javax.swing.JPanel jPanel9;
    javax.swing.JPanel jPanelBaixo;
    javax.swing.JPanel jPanelBaixo1;
    javax.swing.JPanel jPanelBaixo2;
    javax.swing.JPanel jPanelBaixo3;
    javax.swing.JPanel jPanelTopo;
    javax.swing.JPanel jPanelTopo1;
    javax.swing.JPanel jPanelTopo2;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JScrollPane jScrollPane3;
    javax.swing.JScrollPane jScrollPane4;
    javax.swing.JScrollPane jScrollPane5;
    javax.swing.JSeparator jSeparator1;
    javax.swing.JLabel lbFotoProduto;
    javax.swing.JLabel lblDesconto;
    javax.swing.JLabel lblDescricaoProduto;
    javax.swing.JLabel lblIVA;
    javax.swing.JLabel lblSubTotal;
    javax.swing.JLabel lblTotal;
    javax.swing.JLabel lblTroco;
    javax.swing.JList<String> listaFactura;
    javax.swing.JSpinner minuto;
    javax.swing.JPanel panelCategoria;
    javax.swing.JPanel panelContainer;
    javax.swing.JPanel panelItems1;
    javax.swing.JPanel panelProduto;
    javax.swing.JSpinner quantidade;
    javax.swing.JCheckBox rdA4;
    javax.swing.JCheckBox rdA5;
    javax.swing.JCheckBox rdTermica;
    javax.swing.JTabbedPane tabulacao;
    javax.swing.JTextField txtCarga;
    javax.swing.JTextField txtCliente;
    javax.swing.JTextField txtConvertido;
    javax.swing.JTextField txtDesconto;
    javax.swing.JTextArea txtItemFactura;
    javax.swing.JTextField txtLocalEntrega;
    javax.swing.JTextField txtMoedaCambio;
    javax.swing.JTextField txtMulticaixa;
    javax.swing.JTextField txtObservacao;
    javax.swing.JTextField txtPesquisar;
    javax.swing.JTextField txtRetencao;
    javax.swing.JTextField txtValorEntregue;
    javax.swing.JTextField txtValorEntregueMoeda;
    // End of variables declaration//GEN-END:variables
}
