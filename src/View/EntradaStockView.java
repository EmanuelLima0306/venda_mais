/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.ArmazemController;
import Util.DataComponent;
import Controller.EntradaStockController;
import Controller.EntradaStockItemController;
import Controller.FormaPagamentoController;
import Controller.FornecedorController;
import Controller.ProdutoController;
import Controller.UnidadeMedidaController;
import Enum.TipoLog;
import Ireport.StockIreport;
import ItemTabela.ItemEntrada;
import Model.ArmazemModel;
import Model.EntradaStockItemModel;
import Model.EntradaStockModel;
import Model.EstadoModel;
import Model.FormaPagamentoModel;
import Model.FornecedorModel;
import Model.ProdutoModel;
import Model.UsuarioModel;
import Util.LogUtil;
import java.awt.Image;
import java.awt.ScrollPane;
import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;
import rsl.util.Data;

/**
 *
 * @author celso
 */
public class EntradaStockView extends javax.swing.JFrame {

    /**
     * Creates new form EntradaStockView
     */
    private UsuarioModel usuario;
    private DefaultTableModel tb;
    private JDesktopPane desktop;
    private double total = 0;
    private EntradaStockModel eModelo = null;

    public EntradaStockView() {
        initComponents();
    }

    public EntradaStockView(UsuarioModel usuario) {
        initComponents();
        this.usuario = usuario;
        inicializar();

    }

    private void inicializar() {
        carregarEntradas();
        fornecedor();
        produto();
        Armazem();
        formaPagamento();
        carregarUnidade();
        tb = (DefaultTableModel) tbItem.getModel();
        dataExpiracao.setMinSelectableDate(new Date());
        jScrollPane3.getVerticalScrollBar().setUnitIncrement(16);
    }

    public void carregarEntradas() {

        panelItems.removeAll();

        EntradaStockItemController controller = new EntradaStockItemController();
        List<EntradaStockItemModel> models = controller.getUsoAndPendentes(txtPesquisarEntrada.getText().isEmpty() ? "" : txtPesquisarEntrada.getText());

        panelItems.setLayout(new java.awt.GridLayout(models.size() >= 10 ? models.size() : models.size() + 10, 0, 0, 5));//definir o numero de linhas de acordo ao numero de entradas retornadas
        for (EntradaStockItemModel modelo : models) {

            JPanel itemModel = new ItemEntrada();
            JPanel item = (JPanel) itemModel.getComponent(0);
            javax.swing.JScrollPane scrolpane = (javax.swing.JScrollPane) item.getComponent(0);
            JViewport jView = (JViewport) scrolpane.getComponent(0);
            JLabel designacao = (JLabel) jView.getComponent(0);
            JLabel dataEntrada = (JLabel) item.getComponent(1);
            JLabel qtd = (JLabel) item.getComponent(2);
            JLabel qtdTotal = (JLabel) item.getComponent(3);
            JLabel estado = (JLabel) item.getComponent(4);

            designacao.setText(modelo.getProduto().getDesignacao());
            designacao.setToolTipText(modelo.getProduto().getDesignacao());
            
            dataEntrada.setText(modelo.getEntrada().getData());
            qtd.setText(String.valueOf(modelo.getQtd()));
            qtdTotal.setText(String.valueOf(modelo.getQtdTotal()));
            estado.setText(modelo.getEstado().getDesignacao());

            //**** alterar os tamanhos
            designacao.setSize(jLabel20.getSize());
            dataEntrada.setSize(jLabel21.getSize());
            qtd.setSize(jLabel22.getSize());
            qtdTotal.setSize(jLabel23.getSize());
            estado.setSize(jLabel24.getSize());
            //-------- botoes ------
            JPanel btns = (JPanel) item.getComponent(5);
            JButton btnActivar = (JButton) btns.getComponent(0);
            JButton btnEliminar = (JButton) btns.getComponent(1);

            btnActivar.addActionListener((e) -> {
                activar(modelo);
                LogUtil.log.salvarLog(TipoLog.INFO, btnActivar.getToolTipText()+"  ( "+modelo.getProduto().getDesignacao()+" )");
            });

            btnEliminar.addActionListener((e) -> {
                eliminar(modelo);
                LogUtil.log.salvarLog(TipoLog.INFO, " Eliminou Iitem de Entrada ( "+modelo.getProduto().getDesignacao()+" )");
            });

            btnActivar.setToolTipText("Ativar para ser Vendido");
            btnEliminar.setToolTipText("Elimina Item");

            if (modelo.getEstado().getId() == 1) {
                String pathFinal = "/IMAGUENS/"+ "red.png";
                //File file = new File(pathFinal);
                btnActivar.setIcon(new javax.swing.ImageIcon(getClass().getResource(pathFinal)));
                btnActivar.setToolTipText("Colocar Pendente das Vendas");
            }
            panelItems.add(item);
            panelItems.updateUI();
        }

    }

    public void activar(EntradaStockItemModel modelo) {
        EntradaStockItemController controller = new EntradaStockItemController();
        boolean salvo = false;
        if (modelo.getEstado().getId() == 1) { // desativar

            modelo.getEstado().setId(13);
            salvo = controller.saveOrUpdate(modelo);
        } else { // activar
            
            controller.updateAllEstadoProdutoActivo(modelo, new EstadoModel(13, "")); // altera para pendente as entradas do produto
            modelo.getEstado().setId(1); // Activa o item de entrada
            salvo = controller.saveOrUpdate(modelo);

        }
        if(salvo){
            JOptionPane.showMessageDialog(null, "Operação Realizada com Sucesso");
            carregarEntradas();
        }else{
            JOptionPane.showMessageDialog(null, "Erro ao Realizar a Operação");
        }
    }
    public void eliminar(EntradaStockItemModel modelo) {
        Object[] opcao = {"Sim", "Não"};
        if (JOptionPane.showOptionDialog(null, "Deseja Eliminar este Item de Entrada?", " Alerta ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcao, opcao[0]) == JOptionPane.YES_OPTION) {
                       
            EntradaStockItemController controller = new EntradaStockItemController();
            boolean salvo = true;

                modelo.getEstado().setId(3);
                salvo = controller.saveOrUpdate(modelo);

            if(salvo){
                JOptionPane.showMessageDialog(null, "Operação Realizada com Sucesso");
                carregarEntradas();
            }else{
                JOptionPane.showMessageDialog(null, "Erro ao Realizar a Operação");
            }
        }
    }

    private void carregarUnidade() {
        UnidadeMedidaController controller = new UnidadeMedidaController();
        // cboUnidadeMedida.setModel(new DefaultComboBoxModel(controller.get().toArray()));
    }

    private void formaPagamento() {

        FormaPagamentoController controller = new FormaPagamentoController();
        cboFormaPagamento.setModel(new DefaultComboBoxModel(controller.get().toArray()));

    }

    private void Armazem() {

        ArmazemController controller = new ArmazemController();
        cboArmazem.setModel(new DefaultComboBoxModel(controller.get().toArray()));

    }

    private void fornecedor() {

        FornecedorController controller = new FornecedorController();
        cboFornecedor.setModel(new DefaultComboBoxModel(controller.get().toArray()));
    }

    private void produto() {

        ProdutoController controller = new ProdutoController();
        cboProduto.setModel(new DefaultComboBoxModel(controller.getProdutosStock("").toArray()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        tabulacao = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNumFactura = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        DataFactura = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        cboFormaPagamento = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cboFornecedor = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cboProduto = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtCodBarra = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        dataExpiracao = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        txtPrecoCompra = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtPrecoVenda = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        cboArmazem = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        btnAdicionar = new javax.swing.JButton();
        btnRemoverItem = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnGravar = new javax.swing.JButton();
        btnProduto = new javax.swing.JButton();
        btnActualizarDados = new javax.swing.JButton();
        quantidade = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtPesquisar = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtLote = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        txtPreco_unitario = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtLucroPercent = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtPreco_Unitario_Venda = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtLucro = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        panelItems = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jLabel95 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jLabel102 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jButton25 = new javax.swing.JButton();
        jButton26 = new javax.swing.JButton();
        jLabel107 = new javax.swing.JLabel();
        jLabel108 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jLabel109 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jButton27 = new javax.swing.JButton();
        jButton28 = new javax.swing.JButton();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jLabel116 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jLabel117 = new javax.swing.JLabel();
        jLabel118 = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jButton29 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jLabel121 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jLabel123 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel124 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        jLabel127 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jLabel130 = new javax.swing.JLabel();
        txtPesquisarEntrada = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbItem = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Entrada de Stock");
        setIconImage(new ImageIcon(getClass().getResource("/IMAGUENS/icon.png")).getImage());
        setResizable(false);

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/ddd.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tabulacao.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Dado da Factura"));

        jLabel1.setText("Nº da Factura");

        jLabel2.setText("Data");

        jLabel3.setText("Forma de Pagamento");

        jLabel4.setText("Fornecedor");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNumFactura)
                    .addComponent(DataFactura, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                .addGap(10, 10, 10)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cboFormaPagamento, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cboFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNumFactura, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DataFactura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addComponent(jLabel4)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(cboFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Lote e Armazenamento"));

        jLabel5.setText("Produto");

        cboProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboProdutoActionPerformed(evt);
            }
        });

        jLabel6.setText("Código de Barra");

        txtCodBarra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCodBarraMouseClicked(evt);
            }
        });

        jLabel7.setText("Data de Exiração");

        dataExpiracao.setEnabled(false);

        jLabel8.setText("Preço de Compra");

        txtPrecoCompra.setText("0");
        txtPrecoCompra.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtPrecoCompraCaretUpdate(evt);
            }
        });
        txtPrecoCompra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPrecoCompraKeyReleased(evt);
            }
        });

        jLabel9.setText("Preço de Venda");

        jLabel14.setText("Armazém");

        jLabel15.setText("Quantidade");

        btnAdicionar.setBackground(new java.awt.Color(255, 255, 255));
        btnAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/addd2.png"))); // NOI18N
        btnAdicionar.setText("Adicionar");
        btnAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdicionarActionPerformed(evt);
            }
        });

        btnRemoverItem.setBackground(new java.awt.Color(255, 255, 255));
        btnRemoverItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/limp.png"))); // NOI18N
        btnRemoverItem.setText("Remover Item");
        btnRemoverItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverItemActionPerformed(evt);
            }
        });

        btnGravar.setBackground(new java.awt.Color(255, 255, 255));
        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red.png"))); // NOI18N
        btnGravar.setText("Gravar");
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });

        btnProduto.setBackground(new java.awt.Color(255, 255, 255));
        btnProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/entras.png"))); // NOI18N
        btnProduto.setText("Produto");
        btnProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProdutoActionPerformed(evt);
            }
        });

        btnActualizarDados.setBackground(new java.awt.Color(255, 255, 255));
        btnActualizarDados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/ggg.png"))); // NOI18N
        btnActualizarDados.setText("Actualizar Dados");
        btnActualizarDados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarDadosActionPerformed(evt);
            }
        });

        quantidade.setText("1");
        quantidade.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                quantidadeCaretUpdate(evt);
            }
        });

        jLabel19.setText("Pesquisar");

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });

        jLabel25.setText("Lote");

        txtLote.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtLoteMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addGap(341, 341, 341))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel8)
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel5)
                                                .addComponent(jLabel7)))
                                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(dataExpiracao, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(txtPesquisar)
                                                    .addComponent(cboProduto, 0, 248, Short.MAX_VALUE)))
                                            .addGroup(jPanel5Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cboArmazem, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(txtPrecoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                    .addComponent(jLabel19))
                                .addGap(20, 20, 20)))
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel15)
                            .addComponent(jLabel9)
                            .addComponent(jLabel25))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtLote, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                            .addComponent(txtCodBarra, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                            .addComponent(txtPrecoVenda)
                            .addComponent(quantidade)))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 771, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                            .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnRemoverItem, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnActualizarDados))))
                .addGap(0, 38, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtCodBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(txtPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel25)
                        .addComponent(txtLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(cboProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(dataExpiracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(txtPrecoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txtPrecoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cboArmazem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnActualizarDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnRemoverItem, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabulacao.addTab("Entrada", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel10.setText("P. Unitario Compra");

        txtPreco_unitario.setEditable(false);
        txtPreco_unitario.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtPreco_unitarioCaretUpdate(evt);
            }
        });

        jLabel11.setText("Lucro ( % )");

        txtLucroPercent.setText("0");
        txtLucroPercent.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtLucroPercentCaretUpdate(evt);
            }
        });

        jLabel12.setText("P.Unitario Venda");

        jLabel13.setText("Margem de Lucro");

        txtLucro.setEditable(false);
        txtLucro.setBackground(new java.awt.Color(255, 255, 255));

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red.png"))); // NOI18N
        jButton4.setText("Aplicar");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/gg.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPreco_Unitario_Venda)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 18, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(147, 147, 147))
                    .addComponent(txtLucro)
                    .addComponent(txtPreco_unitario)
                    .addComponent(txtLucroPercent))
                .addGap(46, 46, 46)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtPreco_unitario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(txtLucroPercent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtPreco_Unitario_Venda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(txtLucro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addComponent(jButton4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabulacao.addTab("Calculo de lucro e Venda", jPanel3);

        jPanel6.setBackground(java.awt.Color.white);

        jPanel7.setBackground(new java.awt.Color(217, 221, 234));

        jLabel20.setText("Designação");

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Data de Entrada");

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("Qtd em Stock");

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Qtd Entrada");

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setText("Estado");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 158, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel22, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jScrollPane3.setBackground(new java.awt.Color(217, 221, 234));
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelItems.setBackground(new java.awt.Color(217, 221, 234));
        panelItems.setLayout(new java.awt.GridLayout(10, 0, 0, 5));

        jPanel28.setBackground(new java.awt.Color(191, 195, 210));
        jPanel28.setDoubleBuffered(false);

        jLabel89.setForeground(new java.awt.Color(1, 1, 1));
        jLabel89.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel89.setText("2021-02-20");
        jLabel89.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel89.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel90.setForeground(new java.awt.Color(1, 1, 1));
        jLabel90.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel90.setText("50");
        jLabel90.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel90.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel91.setForeground(new java.awt.Color(1, 1, 1));
        jLabel91.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel91.setText("20");
        jLabel91.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel91.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel92.setForeground(new java.awt.Color(1, 1, 1));
        jLabel92.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel92.setText("Estado");
        jLabel92.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel92.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel29.setBackground(new java.awt.Color(191, 195, 210));
        jPanel29.setLayout(new java.awt.GridLayout(1, 4, 10, 0));

        jButton21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red1.png"))); // NOI18N
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        jPanel29.add(jButton21);

        jButton22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/limp.png"))); // NOI18N
        jPanel29.add(jButton22);
        jPanel29.add(jLabel93);
        jPanel29.add(jLabel94);

        jScrollPane5.setBackground(new java.awt.Color(191, 195, 210));
        jScrollPane5.setBorder(null);
        jScrollPane5.setForeground(new java.awt.Color(191, 195, 210));
        jScrollPane5.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane5.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane5.setViewportBorder(null);
        jScrollPane5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane5.setRequestFocusEnabled(false);
        jScrollPane5.setVerifyInputWhenFocusTarget(false);
        jScrollPane5.setWheelScrollingEnabled(false);

        jLabel95.setBackground(new java.awt.Color(191, 195, 210));
        jLabel95.setForeground(new java.awt.Color(1, 1, 1));
        jLabel95.setText("Designação");
        jLabel95.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel95.setOpaque(true);
        jScrollPane5.setViewportView(jLabel95);

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel91, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel90, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel89, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel92, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane5)
        );

        panelItems.add(jPanel28);

        jPanel30.setBackground(new java.awt.Color(191, 195, 210));
        jPanel30.setDoubleBuffered(false);

        jLabel96.setForeground(new java.awt.Color(1, 1, 1));
        jLabel96.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel96.setText("2021-02-20");
        jLabel96.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel96.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel97.setForeground(new java.awt.Color(1, 1, 1));
        jLabel97.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel97.setText("50");
        jLabel97.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel97.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel98.setForeground(new java.awt.Color(1, 1, 1));
        jLabel98.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel98.setText("20");
        jLabel98.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel98.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel99.setForeground(new java.awt.Color(1, 1, 1));
        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel99.setText("Estado");
        jLabel99.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel99.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel31.setBackground(new java.awt.Color(191, 195, 210));
        jPanel31.setLayout(new java.awt.GridLayout(1, 4, 10, 0));

        jButton23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red1.png"))); // NOI18N
        jPanel31.add(jButton23);

        jButton24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/limp.png"))); // NOI18N
        jPanel31.add(jButton24);
        jPanel31.add(jLabel100);
        jPanel31.add(jLabel101);

        jScrollPane6.setBackground(new java.awt.Color(191, 195, 210));
        jScrollPane6.setBorder(null);
        jScrollPane6.setForeground(new java.awt.Color(191, 195, 210));
        jScrollPane6.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane6.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane6.setViewportBorder(null);
        jScrollPane6.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane6.setRequestFocusEnabled(false);
        jScrollPane6.setVerifyInputWhenFocusTarget(false);
        jScrollPane6.setWheelScrollingEnabled(false);

        jLabel102.setBackground(new java.awt.Color(191, 195, 210));
        jLabel102.setForeground(new java.awt.Color(1, 1, 1));
        jLabel102.setText("Designação");
        jLabel102.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel102.setOpaque(true);
        jScrollPane6.setViewportView(jLabel102);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel30Layout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel99, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel98, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel97, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel96, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel99, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel30Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane6)
        );

        panelItems.add(jPanel30);

        jPanel32.setBackground(new java.awt.Color(191, 195, 210));
        jPanel32.setDoubleBuffered(false);

        jLabel103.setForeground(new java.awt.Color(1, 1, 1));
        jLabel103.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel103.setText("2021-02-20");
        jLabel103.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel103.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel104.setForeground(new java.awt.Color(1, 1, 1));
        jLabel104.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel104.setText("50");
        jLabel104.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel104.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel105.setForeground(new java.awt.Color(1, 1, 1));
        jLabel105.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel105.setText("20");
        jLabel105.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel105.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel106.setForeground(new java.awt.Color(1, 1, 1));
        jLabel106.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel106.setText("Estado");
        jLabel106.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel106.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel33.setBackground(new java.awt.Color(191, 195, 210));
        jPanel33.setLayout(new java.awt.GridLayout(1, 4, 10, 0));

        jButton25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red1.png"))); // NOI18N
        jPanel33.add(jButton25);

        jButton26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/limp.png"))); // NOI18N
        jPanel33.add(jButton26);
        jPanel33.add(jLabel107);
        jPanel33.add(jLabel108);

        jScrollPane7.setBackground(new java.awt.Color(191, 195, 210));
        jScrollPane7.setBorder(null);
        jScrollPane7.setForeground(new java.awt.Color(191, 195, 210));
        jScrollPane7.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane7.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane7.setViewportBorder(null);
        jScrollPane7.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane7.setRequestFocusEnabled(false);
        jScrollPane7.setVerifyInputWhenFocusTarget(false);
        jScrollPane7.setWheelScrollingEnabled(false);

        jLabel109.setBackground(new java.awt.Color(191, 195, 210));
        jLabel109.setForeground(new java.awt.Color(1, 1, 1));
        jLabel109.setText("Designação");
        jLabel109.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel109.setOpaque(true);
        jScrollPane7.setViewportView(jLabel109);

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel103, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel104, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel105, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel106, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel105, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel104, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel103, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel106, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel32Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane7)
        );

        panelItems.add(jPanel32);

        jPanel34.setBackground(new java.awt.Color(191, 195, 210));
        jPanel34.setDoubleBuffered(false);

        jLabel110.setForeground(new java.awt.Color(1, 1, 1));
        jLabel110.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel110.setText("2021-02-20");
        jLabel110.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel110.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel111.setForeground(new java.awt.Color(1, 1, 1));
        jLabel111.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel111.setText("50");
        jLabel111.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel111.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel112.setForeground(new java.awt.Color(1, 1, 1));
        jLabel112.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel112.setText("20");
        jLabel112.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel112.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel113.setForeground(new java.awt.Color(1, 1, 1));
        jLabel113.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel113.setText("Estado");
        jLabel113.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel113.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel35.setBackground(new java.awt.Color(191, 195, 210));
        jPanel35.setLayout(new java.awt.GridLayout(1, 4, 10, 0));

        jButton27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red1.png"))); // NOI18N
        jPanel35.add(jButton27);

        jButton28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/limp.png"))); // NOI18N
        jPanel35.add(jButton28);
        jPanel35.add(jLabel114);
        jPanel35.add(jLabel115);

        jScrollPane8.setBackground(new java.awt.Color(191, 195, 210));
        jScrollPane8.setBorder(null);
        jScrollPane8.setForeground(new java.awt.Color(191, 195, 210));
        jScrollPane8.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane8.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane8.setViewportBorder(null);
        jScrollPane8.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane8.setRequestFocusEnabled(false);
        jScrollPane8.setVerifyInputWhenFocusTarget(false);
        jScrollPane8.setWheelScrollingEnabled(false);

        jLabel116.setBackground(new java.awt.Color(191, 195, 210));
        jLabel116.setForeground(new java.awt.Color(1, 1, 1));
        jLabel116.setText("Designação");
        jLabel116.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel116.setOpaque(true);
        jScrollPane8.setViewportView(jLabel116);

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel34Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel110, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel111, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel112, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel113, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel112, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel111, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel110, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel113, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel34Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane8)
        );

        panelItems.add(jPanel34);

        jPanel36.setBackground(new java.awt.Color(191, 195, 210));
        jPanel36.setDoubleBuffered(false);

        jLabel117.setForeground(new java.awt.Color(1, 1, 1));
        jLabel117.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel117.setText("2021-02-20");
        jLabel117.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel117.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel118.setForeground(new java.awt.Color(1, 1, 1));
        jLabel118.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel118.setText("50");
        jLabel118.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel118.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel119.setForeground(new java.awt.Color(1, 1, 1));
        jLabel119.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel119.setText("20");
        jLabel119.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel119.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel120.setForeground(new java.awt.Color(1, 1, 1));
        jLabel120.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel120.setText("Estado");
        jLabel120.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel120.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel37.setBackground(new java.awt.Color(191, 195, 210));
        jPanel37.setLayout(new java.awt.GridLayout(1, 4, 10, 0));

        jButton29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red1.png"))); // NOI18N
        jPanel37.add(jButton29);

        jButton30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/limp.png"))); // NOI18N
        jPanel37.add(jButton30);
        jPanel37.add(jLabel121);
        jPanel37.add(jLabel122);

        jScrollPane9.setBackground(new java.awt.Color(191, 195, 210));
        jScrollPane9.setBorder(null);
        jScrollPane9.setForeground(new java.awt.Color(191, 195, 210));
        jScrollPane9.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane9.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane9.setViewportBorder(null);
        jScrollPane9.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane9.setRequestFocusEnabled(false);
        jScrollPane9.setVerifyInputWhenFocusTarget(false);
        jScrollPane9.setWheelScrollingEnabled(false);

        jLabel123.setBackground(new java.awt.Color(191, 195, 210));
        jLabel123.setForeground(new java.awt.Color(1, 1, 1));
        jLabel123.setText("Designação");
        jLabel123.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel123.setOpaque(true);
        jScrollPane9.setViewportView(jLabel123);

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel117, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel118, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel119, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel120, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel119, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel118, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel117, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel120, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel36Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane9)
        );

        panelItems.add(jPanel36);

        jPanel38.setBackground(new java.awt.Color(191, 195, 210));
        jPanel38.setDoubleBuffered(false);

        jLabel124.setForeground(new java.awt.Color(1, 1, 1));
        jLabel124.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel124.setText("2021-02-20");
        jLabel124.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel124.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel125.setForeground(new java.awt.Color(1, 1, 1));
        jLabel125.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel125.setText("50");
        jLabel125.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel125.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel126.setForeground(new java.awt.Color(1, 1, 1));
        jLabel126.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel126.setText("20");
        jLabel126.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel126.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel127.setForeground(new java.awt.Color(1, 1, 1));
        jLabel127.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel127.setText("Estado");
        jLabel127.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel127.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jPanel39.setBackground(new java.awt.Color(191, 195, 210));
        jPanel39.setLayout(new java.awt.GridLayout(1, 4, 10, 0));

        jButton31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red1.png"))); // NOI18N
        jPanel39.add(jButton31);

        jButton32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/limp.png"))); // NOI18N
        jPanel39.add(jButton32);
        jPanel39.add(jLabel128);
        jPanel39.add(jLabel129);

        jScrollPane10.setBackground(new java.awt.Color(191, 195, 210));
        jScrollPane10.setBorder(null);
        jScrollPane10.setForeground(new java.awt.Color(191, 195, 210));
        jScrollPane10.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane10.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane10.setViewportBorder(null);
        jScrollPane10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane10.setRequestFocusEnabled(false);
        jScrollPane10.setVerifyInputWhenFocusTarget(false);
        jScrollPane10.setWheelScrollingEnabled(false);

        jLabel130.setBackground(new java.awt.Color(191, 195, 210));
        jLabel130.setForeground(new java.awt.Color(1, 1, 1));
        jLabel130.setText("Designação");
        jLabel130.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel130.setOpaque(true);
        jScrollPane10.setViewportView(jLabel130);

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel124, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel125, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel126, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel127, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel126, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel125, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel124, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel127, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane10)
        );

        panelItems.add(jPanel38);

        jScrollPane3.setViewportView(panelItems);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtPesquisarEntrada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarEntradaKeyReleased(evt);
            }
        });

        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel74.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/circ.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel74)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPesquisarEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtPesquisarEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );

        tabulacao.addTab("Manipular Entradas", jPanel6);

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/BANNE.png"))); // NOI18N

        tbItem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Produto", "Armazém", "Código de Bara", "Data de Expiração", "QTD", "P.Compra", "P.Unit.Venda", "p.unit.Compra", "Lucro percent", "Margem Lucro", "Total Venda", "Lote"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbItem);
        if (tbItem.getColumnModel().getColumnCount() > 0) {
            tbItem.getColumnModel().getColumn(0).setResizable(false);
            tbItem.getColumnModel().getColumn(3).setResizable(false);
            tbItem.getColumnModel().getColumn(3).setPreferredWidth(80);
            tbItem.getColumnModel().getColumn(4).setResizable(false);
            tbItem.getColumnModel().getColumn(4).setPreferredWidth(50);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 856, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(5, 5, 5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(tabulacao, javax.swing.GroupLayout.PREFERRED_SIZE, 861, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabulacao, javax.swing.GroupLayout.PREFERRED_SIZE, 491, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarDadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarDadosActionPerformed
        // TODO add your handling code here:
        inicializar();
    }//GEN-LAST:event_btnActualizarDadosActionPerformed

    private void cboProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboProdutoActionPerformed
        // TODO add your handling code here:

        ProdutoModel modelo = (ProdutoModel) cboProduto.getSelectedItem();
        EntradaStockItemController controller = new EntradaStockItemController();
        EntradaStockItemModel entradaModel = controller.getLast(modelo);
        dataExpiracao.setDate(null);
        dataExpiracao.setEnabled(modelo.isExpira());
        txtCodBarra.setText(entradaModel.getCodBarra());
        double precoVenda = controller.getLotePrecoVenda(modelo.getId(), entradaModel.getCodBarra()).getPrecoVenda();
        txtPrecoVenda.setText(String.valueOf(precoVenda));
    }//GEN-LAST:event_cboProdutoActionPerformed
    private void limpar() {

        txtCodBarra.setText("");
        txtLucro.setText("");
        txtLucroPercent.setText("");
        txtNumFactura.setText("");
        txtPrecoCompra.setText("");
        txtPrecoVenda.setText("");
        txtPreco_Unitario_Venda.setText("");
        txtPreco_unitario.setText("");
        quantidade.setText("1");

        tb.setRowCount(0);
        inicializar();

    }

    private void limparNormal() {

        txtCodBarra.setText("");
        txtLucro.setText("");
        txtLucroPercent.setText("");
        //txtNumFactura.setText("");
        txtPrecoCompra.setText("");
        txtPrecoVenda.setText("");
        txtPreco_Unitario_Venda.setText("");
        txtPreco_unitario.setText("");
        quantidade.setText("1");
        txtLote.setText("");
        dataExpiracao.setDate(null);

        //tb.setRowCount(0);
        //inicializar();

    }

    private void addNewItem(ProdutoModel p, ArmazemModel a, double qtd, double p_compra, double p_venda, double p_unit_compra, double lucro, double margemLucro, String data, String codBarra, String lote) {

        tb.addRow(new Object[]{
            p,
            a,
            codBarra,
            p.isExpira() ? data : "",
            qtd,
            p_compra,
            p_venda,
            p_unit_compra,
            lucro,
            margemLucro,
            qtd * p_venda,
            lote

        });
    }

    private void addItemExistente(int qtd, int linha) {

        int qtd_tb = Integer.parseInt(tb.getValueAt(linha, 4).toString());
        double p_venda = Double.parseDouble(tb.getValueAt(linha, 5).toString());

        int qtd_nova = qtd + qtd_tb;
        if (qtd_nova > 0) {
            tb.setValueAt(qtd_nova, linha, 4);

            tb.setValueAt(qtd_nova * p_venda, linha, 7);
        } else {
            JOptionPane.showMessageDialog(this, "Quantidade final não pode ser menor ou igual a zero", "Alerta", JOptionPane.WARNING_MESSAGE);
        }

    }

    private int isProdutoExiste_tb(String coBarra) {

        for (int i = 0; i < tbItem.getRowCount(); i++) {

            if (tbItem.getValueAt(i, 2).toString().equalsIgnoreCase(coBarra)) {
                return i;
            }

        }

        return -1;
    }

    private void add(ProdutoModel p, ArmazemModel a, double qtd, double p_compra, double p_venda, double p_unit_compra, double lucro, double margemLucro, String data, String codBarra, String lote, EntradaStockItemController controller) {

        int linha = isProdutoExiste_tb(codBarra);
        //if (linha < 0) {
        //---------------------------------------------------------------     
        if (controller.getAll(codBarra)) {
            /*int op = JOptionPane.showConfirmDialog(this, "Já existe produto com este código de barra no sitema\n"
                        + "Se o lote for adicionado o produto\n"
                        + "todos os produtos com este Código de Barra vão assumir este preço de venda\n\n"
                        + "Deseja Adicionar", "Aviso", 0);
                if (op != JOptionPane.YES_OPTION) {
                    EntradaStockItemController eItemController = new EntradaStockItemController();
                    EntradaStockItemModel eItemModel = new EntradaStockItemModel();
                    eItemModel.setPrecoVenda(p_venda);
                    eItemModel.setCodBarra(codBarra);
                    eItemController.updatePreco(eItemModel);
                    return;
                }*/

            addNewItem(p, a, qtd, p_compra, p_venda, p_unit_compra, lucro, margemLucro, data, codBarra,lote);
        } else {
            addNewItem(p, a, qtd, p_compra, p_venda, p_unit_compra, lucro, margemLucro, data, codBarra,lote);
        }
        //---------------------------------------------------------------------          
        /* } else {

            int op = JOptionPane.showConfirmDialog(this,
                    "Este produto já existe na lista de itens\ndeseja aumenta a quantidade?",
                    "Alerta", 0);
            String qtd_string = JOptionPane.showInputDialog("Digite a quantidade ");
            if (!qtd_string.isEmpty()) {

                addItemExistente(Integer.parseInt(qtd_string), linha);
            }
        }*/
        limparNormal();
        //limparCampo();
    }

    private boolean isEmptyItem() {
        ProdutoModel produto = (ProdutoModel) cboProduto.getSelectedItem();
        return cboArmazem.getSelectedIndex() < 0
                || cboProduto.getSelectedIndex() < 0
                || txtCodBarra.getText().isEmpty()
                || txtPrecoCompra.getText().isEmpty()
                || txtPrecoVenda.getText().isEmpty()
                || (produto.isExpira() ? dataExpiracao.getDate() == null : false)
                || (verificarCodBarra(produto, txtCodBarra.getText()));
    }
    
    public boolean verificarCodBarra(ProdutoModel pm, String codBarra){
        EntradaStockItemController controller = new EntradaStockItemController();
        if(controller.getCodBarraExistOutroProduto(pm,codBarra)){
            JOptionPane.showMessageDialog(this, "Existe outro produto com este codigo de Barra");
            return true;
        }
        return false;
    }
    
    private void btnAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdicionarActionPerformed

        if (!isEmptyItem()) {

            try {

                ProdutoModel produto = (ProdutoModel) cboProduto.getSelectedItem();
                ArmazemModel armazem = (ArmazemModel) cboArmazem.getSelectedItem();
                String codBarra = txtCodBarra.getText();
                double p_compra = Double.parseDouble(txtPrecoCompra.getText());
                double p_venda = Double.parseDouble(txtPrecoVenda.getText());
                String data_expira = DataComponent.getData(dataExpiracao);
                double qtd = Double.parseDouble(quantidade.getText().toString());
                double p_unit_compra = Double.parseDouble(txtPreco_unitario.getText().isEmpty() ? "0" : txtPreco_unitario.getText());
                double lucro = Double.parseDouble(txtLucroPercent.getText().isEmpty() ? "0" : txtLucroPercent.getText());
                double margemLucro = Double.parseDouble(txtLucro.getText().isEmpty() ? "0" : txtLucro.getText());
                String lote = txtLote.getText().isEmpty()?codBarra:txtLote.getText();
                EntradaStockItemController controller = new EntradaStockItemController();

                add(produto, armazem, qtd, p_compra, p_venda, p_unit_compra, lucro, margemLucro, data_expira, codBarra, lote, controller);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Foi inserido um valor que viola a cláusula desta aplicação\nVerifica as informações antes de adicionar",
                        "ERRO 01/ASCEMIL-2019", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Preencha o espaço em branco",
                    "ALERTA 01-AL/ASCEMIL-2019", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnAdicionarActionPerformed

    private void calcPrecoUnitario() {

        if (!txtPrecoCompra.getText().trim().isEmpty() && !quantidade.getText().trim().isEmpty()) {
//        String preco_string = txtPrecoCompra.getText().replace(',', '.');
//            txtPrecoCompra.setText(preco_string);
            try {
                double preco = Double.parseDouble(txtPrecoCompra.getText());
                int qtd = Integer.parseInt(quantidade.getText().toString());
//                double novo = preco / qtd + preco;
                double novo = preco / qtd;
                txtPreco_unitario.setText(String.valueOf(novo));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Foi inserido um valor que viola a cláusula desta aplicação\nVerifica as informações antes de adicionar",
                        "ERRO 01/ZetaSoft-2019", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
    private void txtPrecoCompraCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtPrecoCompraCaretUpdate
        // TODO add your handling code here:
        calcPrecoUnitario();
        calculo_lucro_venda();
    }//GEN-LAST:event_txtPrecoCompraCaretUpdate

    private void txtPrecoCompraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecoCompraKeyReleased


    }//GEN-LAST:event_txtPrecoCompraKeyReleased

    private void btnRemoverItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverItemActionPerformed
        // TODO add your handling code here:

        int linhas[] = tbItem.getSelectedRows();
        if (linhas.length > 0) {

            for (int i = linhas.length - 1; i >= 0; i--) {

                tb.removeRow(linhas[i]);

            }

        } else {
            JOptionPane.showMessageDialog(this, "Nenhum item selecionado");
        }
    }//GEN-LAST:event_btnRemoverItemActionPerformed
    private void calculo_lucro_venda() {

        try {
            double preco_unitario_compra = Double.parseDouble(txtPreco_unitario.getText());
            double lucroPercent = Double.parseDouble(txtLucroPercent.getText());
            double p_venda = (preco_unitario_compra + (preco_unitario_compra * lucroPercent) / 100);
            double valor_lucro = (p_venda - preco_unitario_compra);
            valor_lucro = valor_lucro * Integer.parseInt(quantidade.getText().trim());

            txtPreco_Unitario_Venda.setText(String.valueOf(p_venda));
            txtLucro.setText(String.valueOf(valor_lucro));
        } catch (NumberFormatException ex) {

        }
    }
    private void txtPreco_unitarioCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtPreco_unitarioCaretUpdate
        // TODO add your handling code here:
        calculo_lucro_venda();

    }//GEN-LAST:event_txtPreco_unitarioCaretUpdate

    private void txtLucroPercentCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtLucroPercentCaretUpdate
        // TODO add your handling code here:
        calculo_lucro_venda();
    }//GEN-LAST:event_txtLucroPercentCaretUpdate

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        txtPrecoVenda.setText(txtPreco_Unitario_Venda.getText());
        tabulacao.setSelectedIndex(0);
    }//GEN-LAST:event_jButton4ActionPerformed
    private int codBarra(Random gerarNumero) {

        if (gerarNumero.nextInt() > 0) {
            return gerarNumero.nextInt();
        }
        return codBarra(new Random());

    }
    private void txtCodBarraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCodBarraMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {

            txtCodBarra.setText(getCodBarra());
        }
    }//GEN-LAST:event_txtCodBarraMouseClicked
    private String getCodBarra() {

        Random gerarNumero = new Random();
        EntradaStockItemController controller = new EntradaStockItemController();
        String codBarra = "17" + String.valueOf(gerarNumero.nextInt()).replaceFirst("-", "");

        if (!controller.getCodBarraExist(codBarra)) {
            return codBarra;
        }
        return getCodBarra();

    }

    private EntradaStockModel gravarEntrada() {

        FornecedorModel f = (FornecedorModel) cboFornecedor.getSelectedItem();
        FormaPagamentoModel fp = (FormaPagamentoModel) cboFormaPagamento.getSelectedItem();

        EntradaStockModel modelo = new EntradaStockModel();
        modelo.setData(DataComponent.getDataActual());
        modelo.setDataFactura(DataComponent.getData(DataFactura));
        modelo.setEstado(new EstadoModel(1, ""));
        modelo.setFormaPagamento(fp);
        modelo.setFornecedor(f);
        modelo.setNumFactura(txtNumFactura.getText());
        modelo.setTemDivida(!fp.isCash() && !fp.isMulticaixa());
        modelo.setTotal(0);
        modelo.setUsuario(usuario);

        if (!modelo.isEmpty()) {

            EntradaStockController controller = new EntradaStockController();

            if (controller.saveOrUpdate(modelo)) {

                return controller.getLastEntrada(usuario);
            } else {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro ao dar entrada do stock", "ERRO 02-ASCEMIL/20", JOptionPane.ERROR_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Verifica os dados da factura");
        }
        return new EntradaStockModel();
    }

    private boolean gravarEntradaItem(EntradaStockModel entrada) {

        boolean flag = false;

        if (entrada.getId() > 0) {
            eModelo = entrada;
            for (int i = 0; i < tbItem.getRowCount(); i++) {

                EntradaStockItemModel modelo = new EntradaStockItemModel();

                ProdutoModel produto = (ProdutoModel) tbItem.getValueAt(i, 0);
                
                modelo.setProduto(produto);
                modelo.setArmazem((ArmazemModel) tbItem.getValueAt(i, 1));
                modelo.setCodBarra(tbItem.getValueAt(i, 2).toString());
                modelo.setDataExpiracao(tbItem.getValueAt(i, 3).toString());
                modelo.setQtd((Double) tbItem.getValueAt(i, 4));
                modelo.setPrecoCompra((Double) tbItem.getValueAt(i, 5));
                modelo.setPrecoVenda((Double) tbItem.getValueAt(i, 6));
                modelo.setPrecoUnitarioCompra((Double) tbItem.getValueAt(i, 7));
                modelo.setLucro((Double) tbItem.getValueAt(i, 8));
                modelo.setMargemLucro((Double) tbItem.getValueAt(i, 9));
                modelo.setLote(tbItem.getValueAt(i, 11).toString());
                modelo.setEntrada(entrada);
                modelo.setEstado(new EstadoModel(1, ""));

                EntradaStockItemController controller = new EntradaStockItemController();

                EntradaStockItemModel eItemModel = new EntradaStockItemModel();
                eItemModel = controller.getUso(produto);

                //Define o item do produto que será usado de acordo a ordenação do produto
                if (definirItemUso(eItemModel, modelo, produto)) {

                    if (controller.saveOrUpdate(modelo)) {

                        total += modelo.getQtd() * modelo.getPrecoCompra();
                        flag = true;
                    } else {
                        JOptionPane.showMessageDialog(this, "Ocorreu um erro ao dar entrada do stock", "ERRO 02-ASCEMIL/20", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao Definir O Item a ser Usado");
                }

            }
        }
        return flag;
    }

    public boolean definirItemUso(EntradaStockItemModel eItemModelUso, EntradaStockItemModel modeloNovo, ProdutoModel produto) {

        EntradaStockItemController eItemController = new EntradaStockItemController();

        if (eItemModelUso != null) {

            if (produto.getOrganizacao().equalsIgnoreCase("ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)")) {
                modeloNovo.setEstado(new EstadoModel(13, ""));
                return true;

            } else {

                if (produto.getOrganizacao().equalsIgnoreCase("ORDEM DE CHEGADA( ULTIMO A  ENTRAR PRIMEIRO A SAIR)")) {

                    eItemModelUso.setEstado(new EstadoModel(13, ""));

                    if (eItemController.saveOrUpdate(eItemModelUso)) {
                        modeloNovo.setEstado(new EstadoModel(1, ""));
                        return true;
                    } else {
                        return false;
                    }

                } else {  //----------DATA DE EXPIRAÇÃO

                    if (DataComponent.compareData(eItemModelUso.getDataExpiracao(), modeloNovo.getDataExpiracao())) {

                        eItemModelUso.setEstado(new EstadoModel(13, ""));
                        modeloNovo.setEstado(new EstadoModel(1, ""));

                    } else {
                        eItemModelUso.setEstado(new EstadoModel(1, ""));
                        modeloNovo.setEstado(new EstadoModel(13, ""));
                    }

                    if (eItemController.saveOrUpdate(eItemModelUso)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else {
            modeloNovo.setEstado(new EstadoModel(1, ""));
            return true;
        }
    }

    private void gravar() {

        EntradaStockModel novaEntrada = gravarEntrada();
        if (gravarEntradaItem(novaEntrada)) {

            EntradaStockController eController = new EntradaStockController();

            FornecedorModel f = (FornecedorModel) cboFornecedor.getSelectedItem();
            FormaPagamentoModel fp = (FormaPagamentoModel) cboFormaPagamento.getSelectedItem();

            eModelo.setTotal(total);
            eModelo.setData(DataComponent.getDataActual());
            eModelo.setDataFactura(DataComponent.getData(DataFactura));
            eModelo.setEstado(new EstadoModel(1, ""));
            eModelo.setFormaPagamento((fp));
            eModelo.setFornecedor(f);
            eModelo.setNumFactura(txtNumFactura.getText());
            eModelo.setTemDivida(!fp.isCash() && !fp.isMulticaixa());
            eModelo.setTotal(0);
            eModelo.setUsuario(usuario);
            eController.update(eModelo);
            tb.setRowCount(0);
            limpar();
            limparCampo();
            JOptionPane.showMessageDialog(this, "Operação realizada com sucesso");
            LogUtil.log.salvarLog(TipoLog.INFO, " Salvou Entrada ( factura nº: "+eModelo.getNumFactura()+" )");
            StockIreport.listarEntrada(novaEntrada.getId());
        }

    }

    private void limparCampo() {

        txtCodBarra.setText("");
        txtLucro.setText("");
        txtLucroPercent.setText("");
        txtNumFactura.setText("");
        txtPrecoCompra.setText("");
        txtPrecoVenda.setText("");
        txtPreco_Unitario_Venda.setText("");
        txtPreco_unitario.setText("");

        DataFactura.setDate(null);
        dataExpiracao.setDate(null);

        quantidade.setText("1");

    }
    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        // TODO add your handling code here:

        if (tbItem.getRowCount() > 0) {

            gravar();
        } else {
            JOptionPane.showMessageDialog(this, "Não existe itens na lista");
        }
    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProdutoActionPerformed
        // TODO add your handling code here:
        new ProdutoView(usuario).setVisible(true);
    }//GEN-LAST:event_btnProdutoActionPerformed

    private void quantidadeCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_quantidadeCaretUpdate
        // TODO add your handling code here:
        calcPrecoUnitario();
        calculo_lucro_venda();
    }//GEN-LAST:event_quantidadeCaretUpdate

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        ProdutoController controller = new ProdutoController();
        cboProduto.setModel(new DefaultComboBoxModel(controller.getProdutosStock(txtPesquisar.getText()).toArray()));
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void txtPesquisarEntradaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarEntradaKeyReleased
        // TODO add your handling code here:
        carregarEntradas();
    }//GEN-LAST:event_txtPesquisarEntradaKeyReleased

    private void txtLoteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLoteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoteMouseClicked

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton21ActionPerformed

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
            java.util.logging.Logger.getLogger(EntradaStockView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EntradaStockView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EntradaStockView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EntradaStockView.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EntradaStockView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DataFactura;
    private javax.swing.JButton btnActualizarDados;
    private javax.swing.JButton btnAdicionar;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnProduto;
    private javax.swing.JButton btnRemoverItem;
    private javax.swing.JComboBox<String> cboArmazem;
    private javax.swing.JComboBox<String> cboFormaPagamento;
    private javax.swing.JComboBox<String> cboFornecedor;
    private javax.swing.JComboBox<String> cboProduto;
    private com.toedter.calendar.JDateChooser dataExpiracao;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel panelItems;
    private javax.swing.JTextField quantidade;
    private javax.swing.JTabbedPane tabulacao;
    private javax.swing.JTable tbItem;
    private javax.swing.JTextField txtCodBarra;
    private javax.swing.JTextField txtLote;
    private javax.swing.JTextField txtLucro;
    private javax.swing.JTextField txtLucroPercent;
    private javax.swing.JTextField txtNumFactura;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtPesquisarEntrada;
    private javax.swing.JTextField txtPrecoCompra;
    private javax.swing.JTextField txtPrecoVenda;
    private javax.swing.JTextField txtPreco_Unitario_Venda;
    private javax.swing.JTextField txtPreco_unitario;
    // End of variables declaration//GEN-END:variables
}
