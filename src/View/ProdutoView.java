/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.CategoriaABController;
import Controller.EmpresaController;
import Util.DataComponent;
import Controller.EntradaStockController;
import Controller.EntradaStockItemController;
import Controller.FabricanteController;
import Controller.FacturaController;
import Controller.FormaPagamentoController;
import Controller.FornecedorController;
import Controller.ProdutoController;
import Model.CategoriaModel;
import Model.EstadoModel;
import Model.FabricanteModel;
import Model.ProdutoModel;
import Model.UsuarioModel;
import Util.JComboBoxComponent;
import Controller.MotivoController;
import Controller.ParamentroController;
import Controller.TaxaController;
import Controller.UnidadeMedidaController;
import Enum.TipoLog;
import Model.ArmazemModel;
import Model.EmpresaModel;
import Model.EntradaStockItemModel;
import Model.EntradaStockModel;
import Model.FormaPagamentoModel;
import Model.FornecedorModel;
import Model.Motivo;
import Model.ParamentroModel;
import Model.Taxa;
import Util.CopyFileUtil;
import Util.LogUtil;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import rsl.util.Data;

/**
 *
 * @author celso & Emanuel
 */
public class ProdutoView extends javax.swing.JFrame {

    /**
     * Creates new form ProdutoView
     */
    private UsuarioModel usuario;
    private int id = 0;
    private String urlIMagem = "null.png";
    private boolean flag = false, moduloSistemaDefault = false;
    private int idArmazem = 0;
    private EntradaStockItemModel entradaStockItemModel;
    private EmpresaModel empresaModel;
    private Component tab_preco;

    public ProdutoView() {
        initComponents();
    }

    ProdutoView(UsuarioModel usuario) {
        initComponents();
        this.usuario = usuario;
        carregarCategoria();
        carregarFabricante();
        carregadoTaxa();
        carregadoMotivo();
        this.moduloSistema();
        this.panelPreco();
        this.panelConfig();
        this.armazemPrincipal();
        this.addCampo();
        this.getEmpresa();
        dataExpiracao.setDate(new Date());
        dataExpiracao.setMinSelectableDate(new Date());

        chkIVA.setVisible(empresaModel.getRegime().isCobraImposto());
        entradaStockItemModel = new EntradaStockItemModel();

        ParamentroController paramentroController = new ParamentroController();
        ParamentroModel paramentroModel = paramentroController.getById(8);
        if (paramentroModel != null) {

            if (paramentroModel.getValor() != 1) {

                txtCodBarra.setEnabled(false);
                txtLote.setEnabled(false);
            }
        }

        if (tabulacao.getComponents().length >= 2) {
            tab_preco = tabulacao.getComponents()[2];

            if (tab_preco != null) {

                tabulacao.remove(tab_preco);
            }

        }
        verificarStocavel(chkProdutoStocavel.isSelected());
    }

    private void getEmpresa() {

        EmpresaController controller = new EmpresaController();
        empresaModel = controller.getById(1);
    }

    private void moduloSistema() {

        ParamentroController controller = new ParamentroController();
        ParamentroModel modelo = controller.getById(3); //MODULO DO SISTEMA  / 

        if (modelo.getValor() == 1) {//(1) - VENDA MAS

            chkMenuDia.setVisible(false);
            chkCuzinha.setVisible(false);
            moduloSistemaDefault = true;

        } else if (modelo.getValor() == 2) {//(2) - MREST

        } else {
            if (modelo.getValor() == 3) {// (3) - barbearia

                chkMenuDia.setVisible(false);
                chkCuzinha.setVisible(false);
                moduloSistemaDefault = true;
            }
        }

    }

    private void panelConfig() {

        ParamentroController controller = new ParamentroController();
        ParamentroModel modelo = controller.getById(4);

        if (modelo.getValor() == 0) {//remover

            tabulacao.remove(1);

        }

    }

    private void addCampo() {

        ParamentroController controller = new ParamentroController();
        ParamentroModel modelo = controller.getById(8);

        quantidade.setVisible(modelo.getValor() == 1);
        lblQuantidade.setVisible(modelo.getValor() == 1);

        if (modelo.getValor() == 1) {

            if (!chkProdutoStocavel.isSelected()) {

            }

        }

//        lblPreco.setVisible(modelo.getValor() == 1 ? true : !chkProdutoStocavel.isSelected());
//        txtValorVenda.setVisible(modelo.getValor() == 1 ? true : !chkProdutoStocavel.isSelected());
        dataExpiracao.setVisible(modelo.getValor() == 1 ? true : !chkProdutoStocavel.isSelected());

//        jLabel10.setVisible(modelo.getValor() == 1 ? true : !chkProdutoStocavel.isSelected());
//        txtPrecoCompra.setVisible(modelo.getValor() == 1 ? true : !chkProdutoStocavel.isSelected());
    }

    private void armazemPrincipal() {

        ParamentroController controller = new ParamentroController();
        ParamentroModel modelo = controller.getById(6);

        if (modelo.getValor() > 0) {//remover

            idArmazem = modelo.getValor();
//            txtExistencia.setVisible(true);
//            lblExistencia.setVisible(true);

        } else {
//            txtExistencia.setVisible(false);
//            lblExistencia.setVisible(false);
        }

    }

    private void panelPreco() {

        ParamentroController controller = new ParamentroController();
        ParamentroModel modelo = controller.getById(5);

        if (modelo.getValor() == 0) {//remover

            tabulacao.remove(2);

        }

    }

    private void carregadoMotivo() {

        MotivoController controller = new MotivoController();
        cboMotivo.setModel(new DefaultComboBoxModel(controller.get().toArray()));
        EmpresaController ec = new EmpresaController();
        if(ec.getById(1).getRegime().getId() == 5)
            cboMotivo.setSelectedIndex(1);
        else
            cboMotivo.setSelectedIndex(4);

    }

    private void carregadoTaxa() {

        TaxaController controller = new TaxaController();
        List<Taxa> listaTaxa = new ArrayList();
        listaTaxa.add(controller.getById(1));
        cboTaxa.setModel(new DefaultComboBoxModel(listaTaxa.toArray()));

    }

    private void carregarFabricante() {
        FabricanteController controller = new FabricanteController();
        cboFabricante.setModel(new DefaultComboBoxModel(controller.get().toArray()));
    }

    private void carregarCategoria() {
        CategoriaABController controller = new CategoriaABController();
        cboCategoria.setModel(new DefaultComboBoxModel(controller.get().toArray()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        tabulacao = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtDesignacao = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        cboFabricante = new javax.swing.JComboBox<>();
        btnAddFabricante = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cboCategoria = new javax.swing.JComboBox<>();
        btnAddCategoria = new javax.swing.JButton();
        chkProdutoStocavel = new javax.swing.JCheckBox();
        chkExpira = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        txtReferencia = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        listProduto = new javax.swing.JList<>();
        jPanel4 = new javax.swing.JPanel();
        btnGravar = new javax.swing.JButton();
        btnEntradaStock = new javax.swing.JButton();
        btnListar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnCarregaDado = new javax.swing.JButton();
        btnReutilizar = new javax.swing.JButton();
        chkIVA = new javax.swing.JCheckBox();
        btnLimpar = new javax.swing.JButton();
        chkMenuDia = new javax.swing.JCheckBox();
        quantidade = new javax.swing.JSpinner();
        lblQuantidade = new javax.swing.JLabel();
        txtValorVenda = new javax.swing.JTextField();
        lblPreco = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtPrecoCompra = new javax.swing.JTextField();
        txtCodBarra = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        dataExpiracao = new com.toedter.calendar.JDateChooser();
        jLabel16 = new javax.swing.JLabel();
        chkCuzinha = new javax.swing.JCheckBox();
        txtLote = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        stoctMinimo = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        alertaExpiracao = new javax.swing.JSpinner();
        jLabel8 = new javax.swing.JLabel();
        qtdCritica = new javax.swing.JSpinner();
        jPanel5 = new javax.swing.JPanel();
        btnImagem = new javax.swing.JButton();
        lbImagem = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        diaDevolucao = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        cboOrganizacao = new javax.swing.JComboBox<>();
        spGarantia = new javax.swing.JSpinner();
        jLabel17 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        cboTaxa = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        cboMotivo = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Produto");
        setIconImage(new ImageIcon(getClass().getResource("/IMAGUENS/icon.png")).getImage());
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Designação");

        txtDesignacao.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtDesignacaoCaretUpdate(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Descrição");

        txtDescricao.setColumns(10);
        txtDescricao.setRows(4);
        jScrollPane1.setViewportView(txtDescricao);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Fabricante");

        btnAddFabricante.setBackground(new java.awt.Color(255, 255, 255));
        btnAddFabricante.setText("+");
        btnAddFabricante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFabricanteActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Categoria");

        btnAddCategoria.setBackground(new java.awt.Color(255, 255, 255));
        btnAddCategoria.setText("+");
        btnAddCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCategoriaActionPerformed(evt);
            }
        });

        chkProdutoStocavel.setSelected(true);
        chkProdutoStocavel.setText("Produto Stocavel");
        chkProdutoStocavel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkProdutoStocavelMouseClicked(evt);
            }
        });
        chkProdutoStocavel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkProdutoStocavelActionPerformed(evt);
            }
        });

        chkExpira.setSelected(true);
        chkExpira.setText("Expira");
        chkExpira.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chkExpiraMouseClicked(evt);
            }
        });
        chkExpira.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkExpiraActionPerformed(evt);
            }
        });

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Referência");

        txtReferencia.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtReferenciaCaretUpdate(evt);
            }
        });

        listProduto.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(listProduto);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        btnGravar.setBackground(new java.awt.Color(255, 255, 255));
        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red.png"))); // NOI18N
        btnGravar.setText("Gravar");
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });

        btnEntradaStock.setBackground(new java.awt.Color(255, 255, 255));
        btnEntradaStock.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/entras.png"))); // NOI18N
        btnEntradaStock.setText("Entrada de Stock");
        btnEntradaStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntradaStockActionPerformed(evt);
            }
        });

        btnListar.setBackground(new java.awt.Color(255, 255, 255));
        btnListar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/relact.png"))); // NOI18N
        btnListar.setText("Listar");
        btnListar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(255, 255, 255));
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/sair sim.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnGravar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEntradaStock)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(btnListar, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEntradaStock, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGravar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnListar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        btnCarregaDado.setBackground(new java.awt.Color(255, 255, 255));
        btnCarregaDado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red.png"))); // NOI18N
        btnCarregaDado.setText("Carregar Dados");
        btnCarregaDado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCarregaDadoActionPerformed(evt);
            }
        });

        btnReutilizar.setBackground(new java.awt.Color(255, 255, 255));
        btnReutilizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/act dados.png"))); // NOI18N
        btnReutilizar.setText("Reutilizar Dados");
        btnReutilizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReutilizarActionPerformed(evt);
            }
        });

        chkIVA.setText("IVA");
        chkIVA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIVAActionPerformed(evt);
            }
        });

        btnLimpar.setBackground(new java.awt.Color(255, 255, 255));
        btnLimpar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/limp.png"))); // NOI18N
        btnLimpar.setText("Limpar Informação");
        btnLimpar.setToolTipText("Informação");
        btnLimpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparActionPerformed(evt);
            }
        });

        chkMenuDia.setText("Menu Dia");

        quantidade.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        lblQuantidade.setText("Quantidade");

        txtValorVenda.setText("0");

        lblPreco.setText("Preço de Venda");

        jLabel10.setText("Preço de Compra");

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

        txtCodBarra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBarraFocusLost(evt);
            }
        });
        txtCodBarra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCodBarraMouseClicked(evt);
            }
        });
        txtCodBarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodBarraKeyReleased(evt);
            }
        });

        jLabel18.setText("Código Barra");

        jLabel16.setText("Data de Exiração");

        chkCuzinha.setText("Cozinha");

        txtLote.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLoteFocusLost(evt);
            }
        });
        txtLote.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtLoteMouseClicked(evt);
            }
        });
        txtLote.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLoteKeyReleased(evt);
            }
        });

        jLabel19.setText("Lote");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel4)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(0, 26, Short.MAX_VALUE)
                                        .addComponent(jLabel3)
                                        .addGap(69, 69, 69))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2)
                                            .addComponent(lblQuantidade)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1)
                                    .addComponent(txtDesignacao)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(dataExpiracao, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txtLote))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(txtReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(cboFabricante, 0, 391, Short.MAX_VALUE)
                                                        .addComponent(cboCategoria, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                .addGap(20, 20, 20)
                                                .addComponent(btnAddFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(chkProdutoStocavel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(chkExpira)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(chkIVA, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(txtPrecoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(lblPreco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(12, 12, 12)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtValorVenda)
                                                    .addComponent(txtCodBarra))))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 493, Short.MAX_VALUE)
                                .addComponent(btnAddCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(22, 22, 22))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(chkCuzinha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(chkMenuDia, javax.swing.GroupLayout.Alignment.TRAILING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(btnReutilizar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addComponent(btnCarregaDado, javax.swing.GroupLayout.Alignment.LEADING))))))
                        .addGap(23, 23, 23))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDesignacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtValorVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPrecoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10)
                        .addComponent(lblPreco)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(txtCodBarra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(quantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(dataExpiracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel19)
                        .addComponent(txtLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboFabricante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddFabricante))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel4)
                        .addComponent(cboCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAddCategoria))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtReferencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkProdutoStocavel)
                    .addComponent(chkExpira)
                    .addComponent(chkIVA)
                    .addComponent(chkMenuDia)
                    .addComponent(btnCarregaDado, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnReutilizar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(btnLimpar, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chkCuzinha)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabulacao.addTab("Produto", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setText("Stock Minimo");

        stoctMinimo.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        jLabel7.setText("Alerta de Produto Expirado( dia )");

        alertaExpiracao.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        jLabel8.setText("Quantidade critica");

        qtdCritica.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Foto / Imagem"));

        btnImagem.setBackground(new java.awt.Color(204, 0, 0));
        btnImagem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/meu novo v.png"))); // NOI18N
        btnImagem.setText("Buscar Imagem");
        btnImagem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImagemActionPerformed(evt);
            }
        });

        lbImagem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                .addComponent(btnImagem)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnImagem, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        jLabel9.setText("Praso devolução( dia )");

        diaDevolucao.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        diaDevolucao.setEnabled(false);

        jLabel13.setText("Organização ");

        cboOrganizacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ORDEM DE CHEGADA( PRIMEIRO A  ENTRAR PRIMEIRO A SAIR)", "ORDEM DE CHEGADA( ULTIMO A  ENTRAR PRIMEIRO A SAIR)", "DATA DE EXPIRAÇÃO", " " }));

        spGarantia.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        jLabel17.setText("Garantia");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(cboOrganizacao, javax.swing.GroupLayout.Alignment.TRAILING, 0, 491, Short.MAX_VALUE)
                                        .addComponent(diaDevolucao))
                                    .addComponent(qtdCritica, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel17))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(stoctMinimo)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(alertaExpiracao, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(spGarantia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(28, 28, 28))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(spGarantia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stoctMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(19, 19, 19)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(alertaExpiracao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(qtdCritica, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(diaDevolucao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(cboOrganizacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        tabulacao.addTab("Configuração", jPanel3);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 729, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 583, Short.MAX_VALUE)
        );

        tabulacao.addTab("Outas", jPanel7);

        jLabel14.setText("Taxa");

        cboTaxa.setEnabled(false);

        jLabel15.setText("Motivo de Isenção");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14)
                    .addComponent(cboTaxa, 0, 296, Short.MAX_VALUE)
                    .addComponent(cboMotivo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(394, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboTaxa, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cboMotivo, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(396, Short.MAX_VALUE))
        );

        tabulacao.addTab("IVA", jPanel8);

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/BANNE.png"))); // NOI18N

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/ddd.png"))); // NOI18N
        jLabel11.setText("jLabel11");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabulacao)
            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tabulacao, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddFabricanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFabricanteActionPerformed
        // TODO add your handling code here:
        new FabricanteView(true).setVisible(true);
    }//GEN-LAST:event_btnAddFabricanteActionPerformed

    private void chkExpiraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkExpiraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkExpiraActionPerformed

    private void chkProdutoStocavelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkProdutoStocavelActionPerformed
        // TODO add your handling code here:

        verificarStocavel(chkProdutoStocavel.isSelected());
    }//GEN-LAST:event_chkProdutoStocavelActionPerformed

    private void verificarStocavel(boolean stocavel) {

        ParamentroController paramentroController = new ParamentroController();
        ParamentroModel paramentroModel = paramentroController.getById(8);
        if (paramentroModel != null) {

            if (paramentroModel.getValor() != 1) {

                txtValorVenda.setEditable(!stocavel);
                txtLote.setEnabled(false);
            }
        }

        if (stocavel) {

            txtValorVenda.setText("0");

            if (paramentroModel != null) {

                if (paramentroModel.getValor() == 1) {

//                    tabulacao.remove(tab_preco);
                    txtCodBarra.setText("");
                    txtCodBarra.setEnabled(true);
                    dataExpiracao.setEnabled(true);
                    quantidade.setValue(0);
                    quantidade.setEnabled(true);
                } else {
                    txtCodBarra.setText("");
                    txtCodBarra.setEnabled(false);
                }
            }

        } else {

            if (paramentroModel != null) {

                if (paramentroModel.getValor() == 1) {

                    dataExpiracao.setEnabled(false);
                    quantidade.setValue(0);
                    quantidade.setEnabled(false);
                    dataExpiracao.setEnabled(false);

                }
            }

            txtCodBarra.setText("");
            txtCodBarra.setEnabled(true);

//            tabulacao.add("Outras Opções", tab_preco);
        }
        chkExpira.setSelected(stocavel);
        chkExpira.setEnabled(stocavel);

        lblPreco.setVisible(true);
        txtValorVenda.setVisible(true);
        txtPrecoCompra.setText("0");
    }

    private void gravarEntradaDEfault() {

        gravarEntradaItem(gravarEntrada());
    }

    private ProdutoModel getLastProduto() {

        ProdutoController controller = new ProdutoController();
        return controller.getLast();
    }

    private boolean gravarEntradaItem(EntradaStockModel entrada) {

        boolean flag = false;

        if (entrada.getId() > 0) {

            EntradaStockModel eModelo = entrada;

            ParamentroController paramentroController = new ParamentroController();

            EntradaStockItemModel modelo = new EntradaStockItemModel();
            ProdutoModel produto = this.getLastProduto();

            if (entradaStockItemModel.getId() > 0) { // verificamos se já tem itens de entrada

                modelo = entradaStockItemModel;

                produto = new ProdutoModel();
                if (id > 0) {
                    produto.setId(id);
                }

                modelo.setProduto(produto);
            } else {

                modelo.setProduto(produto);
            }

            ParamentroModel paramentroModel = paramentroController.getById(6);

            modelo.setArmazem(new ArmazemModel(paramentroModel.getValor()));

            if (txtCodBarra.getText().trim().isEmpty()) {
                modelo.setCodBarra("" + modelo.getProduto().getId() + "974");
            } else {
                modelo.setCodBarra(txtCodBarra.getText().trim());
            }

            modelo.setQtd(!chkProdutoStocavel.isSelected() ? 0 : Double.parseDouble(quantidade.getValue().toString()));

            modelo.setPrecoCompra(Double.parseDouble(txtPrecoCompra.getText()));

            if (txtValorVenda.getText().trim().isEmpty()) {
                modelo.setPrecoVenda(0);
            } else {
                modelo.setPrecoVenda(Double.parseDouble(txtValorVenda.getText()));
            }
            modelo.setLote(txtLote.getText().isEmpty()?modelo.getCodBarra():txtLote.getText());
            modelo.setEntrada(entrada);
            modelo.setDataExpiracao(modelo.getProduto().isExpira() ? DataComponent.getData(dataExpiracao) : "");
            modelo.setEstado(new EstadoModel(1, ""));
            
            
            EntradaStockItemController controller = new EntradaStockItemController();

            if (controller.saveOrUpdate(modelo)) {
                controller.updatePreco(modelo);

                modelo = controller.getUso(produto);
                definirEntraProdutoItemEmUso(modelo);

                flag = true;
            } else {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro ao dar entrada do stock", "ERRO 02-ASCEMIL/20", JOptionPane.ERROR_MESSAGE);
            }

        } else {

            flag = gravarEntradaItem();
        }

        return flag;
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
//                            JOptionPane.showMessageDialog(null, "O produto anterior Expirou\n"
//                                + "Passara a se vender outros Apartir de Deste Momento!\nOBS: Por-favor informe ao Administrador");

                    } else {
                        if (e.getEstado().getId() == 12) { // significa que esgotou 
//                                JOptionPane.showMessageDialog(null, "O produto anterior Terminou\n"
//                                + "Passara a se vender outros Apartir de Deste Momento!\nOBS: Por-favor informe ao Administrador");

                        } else { // significa que devido a ordem passara para pendente

//                                JOptionPane.showMessageDialog(null, "O produto anterior Estará Pendente Devido a forma de ordenação\n("+e.getProduto().getOrganizacao()
//                                + ")\nPassara a se vender outros Apartir de Deste Momento!\nOBS: Por-favor informe ao Administrador");
                        }
                    }
                } else {

                    if (e.getEstado().getId() == 11) { // significa que o produto comercializado expirou
                        //JOptionPane.showMessageDialog(null, "O produto anterior Expirou\n\nOBS: Por-favor informe ao Administrador");

                    } else if (e.getEstado().getId() == 12) { // significa que o produto comercializado expirou
                        //JOptionPane.showMessageDialog(null, "O produto anterior Terminou\n\nOBS: Por-favor informe ao Administrador");
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

    private boolean gravarEntradaItem() {

        boolean flag = false;

        if (id > 0) {

            EntradaStockItemModel modelo = new EntradaStockItemModel();

            ParamentroController paramentroController = new ParamentroController();
            ParamentroModel paramentroModel = paramentroController.getById(6);

            modelo.setProduto(this.getLastProduto());
            modelo.setArmazem(new ArmazemModel(paramentroModel.getValor()));

            if (txtCodBarra.getText().trim().isEmpty()) {
                modelo.setCodBarra("" + modelo.getProduto().getId() + "974");
            } else {
                modelo.setCodBarra(txtCodBarra.getText().trim());
            }

            modelo.setQtd(!chkProdutoStocavel.isSelected() ? 0 : (Double) quantidade.getValue());

            modelo.setPrecoCompra(0);

            if (txtValorVenda.getText().trim().isEmpty()) {
                modelo.setPrecoVenda(0);
            } else {
                modelo.setPrecoVenda(Double.parseDouble(txtValorVenda.getText()));
            }

            modelo.setDataExpiracao(modelo.getProduto().isExpira() ? DataComponent.getData(dataExpiracao) : "");
            EntradaStockItemController controller = new EntradaStockItemController();
            modelo.setId(controller.getId(modelo.getProduto().getId(), paramentroModel.getValor()));

            if (controller.updateExistenciaPreco(modelo)) {
                //controller.updatePreco(modelo);

                flag = true;
            } else {
                JOptionPane.showMessageDialog(this, "Ocorreu um erro ao dar entrada do stock", "ERRO 02-ASCEMIL/20", JOptionPane.ERROR_MESSAGE);
            }

        }
        return flag;
    }

    private FornecedorModel getFornecedorDefault() {

        FornecedorController controller = new FornecedorController();
        return controller.getById(1);
    }

    private FormaPagamentoModel getFormaPagamentoDefault() {

        FormaPagamentoController controller = new FormaPagamentoController();
        return controller.getById(1);
    }

    private EntradaStockModel gravarEntrada() {

        if (id <= 0) {

            entradaStockItemModel = new EntradaStockItemModel(); // dizemos que ainda não tem item de entrada

            FornecedorModel f = getFornecedorDefault();

            FormaPagamentoModel fp = getFormaPagamentoDefault();

            EntradaStockModel modelo = new EntradaStockModel();
            modelo.setData(DataComponent.getDataActual());
            modelo.setDataFactura(DataComponent.getDataActual());
            modelo.setEstado(new EstadoModel(1, ""));
            modelo.setFormaPagamento(fp);
            modelo.setFornecedor(f);
            modelo.setNumFactura("0000D");
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
        }

        EntradaStockModel modelo = new EntradaStockModel();
        modelo.setId(1);
        return modelo;

    }

    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed

        ProdutoModel modelo = new ProdutoModel();
        modelo.setId(id);
        modelo.setDesignacao(txtDesignacao.getText());
        modelo.setDescricao(txtDescricao.getText());
        modelo.setReferencia(txtReferencia.getText());

        CategoriaModel categoria = (CategoriaModel) cboCategoria.getSelectedItem();
        FabricanteModel fabricante = (FabricanteModel) cboFabricante.getSelectedItem();

//        if (!chkIVA.isSelected()) {
//            cboMotivo.setSelectedIndex(21);
////            cboTaxa.setSelectedIndex(1);
//
//        } else {
//            cboTaxa.setSelectedIndex(0);
//            cboMotivo.setSelectedIndex(0);
//        }
        modelo.setMotivo((Motivo) cboMotivo.getSelectedItem());
        modelo.setIsMenuDia(chkMenuDia.isSelected());
        modelo.setIsCozinha(chkCuzinha.isSelected());
        modelo.setTaxa((Taxa) cboTaxa.getSelectedItem());
        modelo.setCategoria(categoria);
        modelo.setFabricante(fabricante);
        modelo.setEstado(new EstadoModel(1, ""));

        modelo.setStocavel(chkProdutoStocavel.isSelected());
        modelo.setExpira(chkExpira.isSelected());
        modelo.setIpc(chkIVA.isSelected());
        modelo.setOrganizacao(cboOrganizacao.getSelectedItem().toString());
        modelo.setUsuario(usuario);
        modelo.setData(DataComponent.getDataActual());

        modelo.setStockMinimo((Integer) stoctMinimo.getValue());
        modelo.setDiaAlerta((Integer) alertaExpiracao.getValue());
        modelo.setQuantCritica((Integer) qtdCritica.getValue());
        modelo.setDiaDevolucao((Integer) diaDevolucao.getValue());
        modelo.setUrlImage(urlIMagem);
        modelo.setValorVenda(Double.parseDouble(txtValorVenda.getText()));
        modelo.setGarantia(Integer.parseInt(spGarantia.getValue().toString()));

        if (moduloSistemaDefault) {

            modelo.setIsMenuDia(true);
//            if(Integer.parseInt(qtdCritica.getValue().toString()) <= Integer.parseInt(stoctMinimo.getValue().toString())){
//                JOptionPane.showMessageDialog(this, "A quantidade critica deve ser maior do que a quantidade minima"
//                        ,"Alerta",JOptionPane.WARNING_MESSAGE);
//                tabulacao.setSelectedIndex(1);
//                return;
//            }
        }
        try {
            boolean flag = true;
            if (modelo.getValorVenda() != 0) {

                if (modelo.getValorVenda() <= 0) {
                    flag = false;
                }
            }
            ParamentroController parametroController = new ParamentroController();
            ParamentroModel parametroModelo = parametroController.getById(8); //MODULO DO SISTEMA  /
            if (modelo.isExpira()) {

                if (parametroModelo.getValor() == 1) {
                    if (Data.getData(dataExpiracao).isEmpty()) {
                        flag = false;
                    }
                }
            }

            if (flag) {

                if (!modelo.isEmpty()) {

                    ProdutoController controller = new ProdutoController();
                    ProdutoModel modeloAux = controller.getAll(modelo.getDesignacao());
                    if (id == 0 && modeloAux.getId() > 0) {
                        JOptionPane.showMessageDialog(this, "Este Produto ja existe no sistema");
                        id = modeloAux.getId();

                    } else if (modeloAux.getId() > 0 && modeloAux.getEstado().getId() == 3) {
                        modelo.setId(modeloAux.getId());
                        flag = true;
                    } else {
                        flag = true;
                    }
                    if (flag) {
                        if (controller.saveOrUpdate(modelo)) {

                            if (chkProdutoStocavel.isSelected()) {
                                ParamentroController paramentroController = new ParamentroController();
                                ParamentroModel paramentroModel = paramentroController.getById(8);
                                if (paramentroModel != null) {

                                    if (paramentroModel.getValor() == 1) {
                                        System.out.println("salvar Entrada");
                                        gravarEntradaDEfault();
                                    }
                                }
                            } else if (!chkProdutoStocavel.isSelected()) {
                                gravarEntradaDEfault();
                            }
                            JOptionPane.showMessageDialog(this, "Operacao realizada com sucesso");
                            LogUtil.log.salvarLog(TipoLog.INFO, " Salvou Produto ( "+modelo.getDesignacao()+" )");
                            limpar();
                            this.dispose();
                            new ProdutoView(usuario).setVisible(true);

                        } else {
                            JOptionPane.showMessageDialog(this, "Ocorreu um erro ao "
                                    + "realizar esta operacao\n"
                                    + "Causa:\n\t1-Campo mal preenchido;\n\t"
                                    + "2-quantidade carater excedido;", "Aviso", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    id = 0;
                    entradaStockItemModel = new EntradaStockItemModel();
                } else {
                    JOptionPane.showMessageDialog(this, "Preencha o espaço em branco");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preço de venda Invalido ou Data de Expiração Inválida");
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Verifica se existe categoria ou fabricante", "Alerta", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnGravarActionPerformed

    private void txtDesignacaoCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtDesignacaoCaretUpdate
        // TODO add your handling code here:
        if (!txtDesignacao.getText().trim().isEmpty()) {
            ProdutoController controller = new ProdutoController();
            List<ProdutoModel> lista = controller.get(txtDesignacao.getText());

            DefaultListModel model = new DefaultListModel();
            for (ProdutoModel m : lista) {
                model.addElement(m);
            }
            listProduto.setModel(model);
        }
    }//GEN-LAST:event_txtDesignacaoCaretUpdate

    private void btnAddCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCategoriaActionPerformed
        // TODO add your handling code here:
        new CategoriaView(true).setVisible(true);
    }//GEN-LAST:event_btnAddCategoriaActionPerformed

    private void btnListarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarActionPerformed
        // TODO add your handling code here:
        ProdutoController controller = new ProdutoController();
        controller.ireportProduto();
        LogUtil.log.salvarLog(TipoLog.INFO, " Produto ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_btnListarActionPerformed

    private void carregarInfo(ProdutoModel modelo, boolean op) {

        id = modelo.getId();

        txtDescricao.setText(modelo.getDescricao());
        txtDesignacao.setText(modelo.getDesignacao());
        txtReferencia.setText(modelo.getReferencia());

        JComboBoxComponent.selectItem(cboCategoria, modelo.getCategoria());
        JComboBoxComponent.selectItem(cboFabricante, modelo.getFabricante());
        JComboBoxComponent.selectItem(cboOrganizacao, modelo.getOrganizacao());
        JComboBoxComponent.selectItem(cboMotivo, modelo.getMotivo());
        JComboBoxComponent.selectItem(cboTaxa, modelo.getTaxa());

        stoctMinimo.setValue(modelo.getStockMinimo());
        alertaExpiracao.setValue(modelo.getDiaAlerta());
        qtdCritica.setValue(modelo.getQuantCritica());
        diaDevolucao.setValue(modelo.getDiaDevolucao());

        flag = true;
        chkExpira.setSelected(modelo.isExpira());
        chkIVA.setSelected(modelo.isIpc());
        chkProdutoStocavel.setSelected(modelo.isStocavel());
        chkExpira.setEnabled(modelo.isStocavel());
        chkMenuDia.setSelected(modelo.isIsMenuDia());
        chkCuzinha.setSelected(modelo.isIsCozinha());

        spGarantia.setValue(modelo.getGarantia());

        // preencher a imagem do produto
        urlIMagem = modelo.getUrlImage();
        //aux = "BannerFarmaciaSaudePopular.jpg";
        String pathFinal;
        File file = new File("Relatorio" + File.separator + "imagensProdutos" + File.separator + urlIMagem);
        pathFinal = file.getAbsolutePath();
        ImageIcon imagem = new ImageIcon(pathFinal);
        lbImagem.setIcon(new ImageIcon(imagem.getImage().getScaledInstance(lbImagem.getWidth(), lbImagem.getHeight(), Image.SCALE_DEFAULT)));

        ParamentroController paramentroController = new ParamentroController();
        ParamentroModel paramentroModel = paramentroController.getById(8);

        if (paramentroModel != null) {

            if (paramentroModel.getValor() == 1) {

                ParamentroModel paramentroModelArmazem = paramentroController.getById(6);

//                EntradaStockItemController itemController = new EntradaStockItemController();
                EntradaStockItemModel itemModel = new EntradaStockItemModel();

                EntradaStockItemController itemController = new EntradaStockItemController();
                List<EntradaStockItemModel> itemModelos = itemController.getByProdutoArmazems(id, paramentroModelArmazem.getValor());

                // pegamos as informações do item de entrada a serem alterados...
                if (itemModelos.size() > 1) {

                    JPanel panel = new JPanel();
                    JLabel label = new JLabel("Código de Barra");
                    JComboBox cboCodigoBarra = new JComboBox();
                    cboCodigoBarra.setModel(new DefaultComboBoxModel(itemController.getLoteCodigoBarra(id).toArray()));
                    panel.add(label);
                    panel.add(cboCodigoBarra);
                    JOptionPane.showMessageDialog(null, panel, "Escolha o Código de Barra", JOptionPane.INFORMATION_MESSAGE);

                    itemModel = (EntradaStockItemModel) cboCodigoBarra.getSelectedItem();
                    itemModel = itemController.getByProdutoArmazemCodBarra(id, paramentroModelArmazem.getValor(), itemModel.getCodBarra());

                } else {

                    itemModel = itemModelos.get(0);

                }

                entradaStockItemModel = itemModel;

                System.out.println("quantidade" + itemModel.getQtd());
                quantidade.setValue(itemModel.getQtd());
                quantidade.setEnabled(modelo.isStocavel());

                dataExpiracao.setDate(DataComponent.stringParaData(itemModel.getDataExpiracao()));
                dataExpiracao.setEnabled(modelo.isExpira());
                txtCodBarra.setText(itemModel.getCodBarra());

                txtPrecoCompra.setText(String.valueOf(itemModel.getPrecoCompra()));
                txtValorVenda.setText(String.valueOf(itemModel.getPrecoVenda()));
                if (!modelo.isStocavel()) {
                    quantidade.setValue(0);

                }

            }
        }

        enableComponent(op, modelo.isIpc());
    }

    private void enableComponent(boolean flag, boolean isIpc) {

        txtDescricao.setEnabled(!flag);
        txtDesignacao.setEnabled(!flag);
        if (!flag) {

            cboMotivo.setEnabled(isIpc ? false : true);
            cboTaxa.setEnabled(isIpc);
        } else {

            cboMotivo.setEnabled(!flag);
            cboTaxa.setEnabled(!flag);
        }

        chkIVA.setEnabled(!flag);
        chkProdutoStocavel.setEnabled(!flag);
        if (chkProdutoStocavel.isEnabled()) {
            if (!flag && chkProdutoStocavel.isSelected()) {
                chkExpira.setEnabled(!flag);
            }
        }else{
            chkExpira.setEnabled(!flag);
        }

        txtReferencia.setEnabled(!flag);
        txtCodBarra.setEnabled(!flag);

    }

    private boolean isFacturaEmitida(ProdutoModel modelo) {

        FacturaController controller = new FacturaController();
        return controller.isFacturaEmitidaByProduto(modelo.getId());

    }
    private void btnCarregaDadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCarregaDadoActionPerformed
        // TODO add your handling code here:
        DefaultListModel model = (DefaultListModel) listProduto.getModel();
        if (listProduto.getSelectedIndex() >= 0) {
            ProdutoModel modelo = (ProdutoModel) model.get(listProduto.getSelectedIndex());

            if (isFacturaEmitida(modelo)) {
                carregarInfo(modelo, true);
            } else {
                carregarInfo(modelo, false);
            }

        }
    }//GEN-LAST:event_btnCarregaDadoActionPerformed
    private void limpar() {

        txtDescricao.setText("");
        txtDesignacao.setText("");
        txtReferencia.setText("");
        txtPrecoCompra.setText("0");
        txtValorVenda.setText("0");
        txtLote.setText("");
        DefaultListModel model = new DefaultListModel();
        listProduto.setModel(model);
        chkExpira.setSelected(true);
        chkIVA.setSelected(true);
        chkProdutoStocavel.setSelected(true);

        stoctMinimo.setValue(0);
        alertaExpiracao.setValue(0);
        qtdCritica.setValue(0);
        diaDevolucao.setValue(0);
        quantidade.setValue(0);

        dataExpiracao.cleanup();
        carregarCategoria();
        carregarFabricante();
    }
    private void btnReutilizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReutilizarActionPerformed
        // TODO add your handling code here:
        DefaultListModel model = (DefaultListModel) listProduto.getModel();
        if (listProduto.getSelectedIndex() >= 0) {
            ProdutoModel modelo = (ProdutoModel) model.get(listProduto.getSelectedIndex());
            txtDescricao.setText(modelo.getDescricao());
            txtDesignacao.setText(modelo.getDesignacao());
            txtReferencia.setText(modelo.getReferencia());

            JComboBoxComponent.selectItem(cboCategoria, modelo.getCategoria());
            JComboBoxComponent.selectItem(cboFabricante, modelo.getFabricante());

            stoctMinimo.setValue(modelo.getStockMinimo());
            alertaExpiracao.setValue(modelo.getDiaAlerta());
            qtdCritica.setValue(modelo.getQuantCritica());
            diaDevolucao.setValue(modelo.getDiaDevolucao());

            chkExpira.setSelected(modelo.isExpira());
            chkIVA.setSelected(modelo.isIpc());
            chkProdutoStocavel.setSelected(modelo.isStocavel());

        }
    }//GEN-LAST:event_btnReutilizarActionPerformed

    private void btnLimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparActionPerformed
        // TODO add your handling code here:
//        limpar();
        this.dispose();
        new ProdutoView(usuario).setVisible(true);
    }//GEN-LAST:event_btnLimparActionPerformed

    private void chkProdutoStocavelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkProdutoStocavelMouseClicked
        // TODO add your handling code here:
        stoctMinimo.setEnabled(chkProdutoStocavel.isSelected());
//        alertaExpiracao.setEnabled(chkProdutoStocavel.isSelected());
        qtdCritica.setEnabled(chkProdutoStocavel.isSelected());
    }//GEN-LAST:event_chkProdutoStocavelMouseClicked

    private void chkExpiraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chkExpiraMouseClicked
        // TODO add your handling code here:
        alertaExpiracao.setEnabled(chkExpira.isSelected());
        dataExpiracao.setEnabled(chkExpira.isSelected());
    }//GEN-LAST:event_chkExpiraMouseClicked

    private void btnEntradaStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntradaStockActionPerformed
        // TODO add your handling code here:
        this.dispose();
        new EntradaStockView(usuario).setVisible(true);
    }//GEN-LAST:event_btnEntradaStockActionPerformed

    private void txtReferenciaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtReferenciaCaretUpdate
        // TODO add your handling code here:
        if (!txtReferencia.getText().trim().isEmpty()) {
            ProdutoController controller = new ProdutoController();
            List<ProdutoModel> lista = controller.get(txtReferencia.getText());

            DefaultListModel model = new DefaultListModel();
            for (ProdutoModel m : lista) {
                model.addElement(m);
            }
            listProduto.setModel(model);
        }
    }//GEN-LAST:event_txtReferenciaCaretUpdate

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        DefaultListModel model = (DefaultListModel) listProduto.getModel();
        if (listProduto.getSelectedIndex() >= 0) {

            int op = JOptionPane.showConfirmDialog(this, "Deseja eliminar este produto", "Alerta", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_OPTION) {
                ProdutoModel modelo = (ProdutoModel) model.get(listProduto.getSelectedIndex());
                ProdutoController controller = new ProdutoController();
                modelo.setEstado(new EstadoModel(3, ""));
                if(controller.update(modelo)){
                    JOptionPane.showMessageDialog(this, "Produto Eliminado com Sucesso");
                    LogUtil.log.salvarLog(TipoLog.INFO, " Eliminou Produto ( "+modelo.getDesignacao()+" )");
                    limpar();
                }

            }

        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void chkIVAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIVAActionPerformed
        // TODO add your handling code here:

        cboMotivo.setEnabled(!chkIVA.isSelected());
        cboTaxa.setEnabled(chkIVA.isSelected());

        if (chkIVA.isSelected()) {
            TaxaController controller = new TaxaController();
            List<Taxa> listaTaxa = controller.getExecluir(1);
            cboTaxa.setModel(new DefaultComboBoxModel(listaTaxa.toArray()));
            cboMotivo.setSelectedIndex(0);

        } else {

            TaxaController controller = new TaxaController();
            List<Taxa> listaTaxa = new ArrayList();
            listaTaxa.add(controller.getById(1));
            cboTaxa.setModel(new DefaultComboBoxModel(listaTaxa.toArray()));
            cboMotivo.setSelectedIndex(4);

        }

//        if (!chkIVA.isSelected()) {
//            cboMotivo.setSelectedIndex(4);
//            cboTaxa.setSelectedIndex(1);
//
//        } else {
//            cboTaxa.setSelectedIndex(0);
//            cboMotivo.setSelectedIndex(0);
//        }
    }//GEN-LAST:event_chkIVAActionPerformed

    private void txtPrecoCompraCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtPrecoCompraCaretUpdate
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecoCompraCaretUpdate

    private void txtPrecoCompraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecoCompraKeyReleased

    }//GEN-LAST:event_txtPrecoCompraKeyReleased

    private void txtCodBarraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCodBarraMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2 && txtCodBarra.isEnabled()) {

            txtCodBarra.setText(getCodBarra());
        }
    }//GEN-LAST:event_txtCodBarraMouseClicked

    private void btnImagemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImagemActionPerformed
        // TODO add your handling code here:
        selecionarImagem();
    }//GEN-LAST:event_btnImagemActionPerformed

    private void txtCodBarraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodBarraKeyReleased
        
    }//GEN-LAST:event_txtCodBarraKeyReleased

    private void txtCodBarraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBarraFocusLost

        EntradaStockItemController controller = new EntradaStockItemController();
        if(!txtCodBarra.getText().isEmpty())
            if (controller.getCodBarraExist(txtCodBarra.getText())) {
                JOptionPane.showMessageDialog(this, "Codigo de Barra Existente em outro produto");
                txtCodBarra.setText("");
            }
    }//GEN-LAST:event_txtCodBarraFocusLost

    private void txtLoteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLoteFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoteFocusLost

    private void txtLoteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLoteMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoteMouseClicked

    private void txtLoteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLoteKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoteKeyReleased

    public boolean verificarCodBarra(ProdutoModel pm, String codBarra){
        EntradaStockItemController controller = new EntradaStockItemController();
        if(controller.getCodBarraExistOutroProduto(pm,codBarra)){
            JOptionPane.showMessageDialog(this, "Existe outro produto com este codigo de Barra");
            return true;
        }
        return false;
    }
    public void selecionarImagem() {

        addLogotipo();

    }

    public void addLogotipo() {
        String correspon = "";

//        String logotipo = "";
        try {

            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "PDF & PNG Images", "pdf", "png", "jpg", "gif", "tif");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(this);
            String pathFinal;

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                pathFinal = f.getAbsolutePath();

                correspon = f.getName();

                String aux = f.getName();
                urlIMagem = aux;
                //aux = "BannerFarmaciaSaudePopular.jpg";
                
                CopyFileUtil.copiarArquivos(f, new File("Relatorio" + File.separator + "imagensProdutos" + File.separator + aux));
                ImageIcon imagem = new ImageIcon(pathFinal);
                lbImagem.setIcon(new ImageIcon(imagem.getImage().getScaledInstance(lbImagem.getWidth(), lbImagem.getHeight(), Image.SCALE_DEFAULT)));

            }
        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }

    private String getCodBarra() {

        Random gerarNumero = new Random();
        EntradaStockItemController controller = new EntradaStockItemController();
        String codBarra = "17" + String.valueOf(gerarNumero.nextInt()).replaceFirst("-", "");

        if (!controller.getCodBarraExist(codBarra)) {
            return codBarra;
        }
        return getCodBarra();

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
            java.util.logging.Logger.getLogger(ProdutoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProdutoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProdutoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProdutoView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProdutoView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner alertaExpiracao;
    private javax.swing.JButton btnAddCategoria;
    private javax.swing.JButton btnAddFabricante;
    private javax.swing.JButton btnCarregaDado;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnEntradaStock;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnImagem;
    private javax.swing.JButton btnLimpar;
    private javax.swing.JButton btnListar;
    private javax.swing.JButton btnReutilizar;
    public static javax.swing.JComboBox<String> cboCategoria;
    public static javax.swing.JComboBox<String> cboFabricante;
    private javax.swing.JComboBox<String> cboMotivo;
    private javax.swing.JComboBox<String> cboOrganizacao;
    private javax.swing.JComboBox<String> cboTaxa;
    private javax.swing.JCheckBox chkCuzinha;
    private javax.swing.JCheckBox chkExpira;
    private javax.swing.JCheckBox chkIVA;
    private javax.swing.JCheckBox chkMenuDia;
    private javax.swing.JCheckBox chkProdutoStocavel;
    private com.toedter.calendar.JDateChooser dataExpiracao;
    private javax.swing.JSpinner diaDevolucao;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbImagem;
    private javax.swing.JLabel lblPreco;
    private javax.swing.JLabel lblQuantidade;
    private javax.swing.JList<String> listProduto;
    private javax.swing.JSpinner qtdCritica;
    private javax.swing.JSpinner quantidade;
    private javax.swing.JSpinner spGarantia;
    private javax.swing.JSpinner stoctMinimo;
    private javax.swing.JTabbedPane tabulacao;
    private javax.swing.JTextField txtCodBarra;
    private javax.swing.JTextArea txtDescricao;
    private javax.swing.JTextField txtDesignacao;
    private javax.swing.JTextField txtLote;
    private javax.swing.JTextField txtPrecoCompra;
    private javax.swing.JTextField txtReferencia;
    private javax.swing.JTextField txtValorVenda;
    // End of variables declaration//GEN-END:variables
}
