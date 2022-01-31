/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.AnoController;
import Controller.ArmazenamentoController;
import Controller.CertificacaoController;
import Controller.ParamentroController;
import Controller.SerieController;
import Model.AnoModel;
import Model.CertificacaoModel;
import Util.DataComponent;
import Model.ConnectioFactoryModel;
import Model.LicencaModel;
import Model.ParamentroModel;
import Model.SerieModel;
import Util.InfoComputadorUtil;
import static Util.InfoComputadorUtil.retornaMacAddress;
import excel.Excel;
import java.io.IOException;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 *
 * @author celso & Emanuel
 */
public class ConfigView extends javax.swing.JFrame {

    /**
     * Creates new form ConfigView
     */
    private String arquivo = "Connection";
    private SerieModel serieModel;
    private SerieController serieController;
    private AnoModel anoModel;
    private AnoController anoController;
    private ParamentroController parametroController;
    private CertificacaoController certificacaoController;
    private CertificacaoModel certificacaoModel;
    
    public ConfigView() {
        initComponents();
        carregarConnection();
//        carregarSerie();
//        carregarParamentros();
//        carregarCertificacao();
    }
    
    public void carregarCertificacao(){
        
       certificacaoController = new CertificacaoController();
       certificacaoModel = new CertificacaoModel();
       certificacaoModel = certificacaoController.getById(1);
        preencherCertificacao(certificacaoModel);
       
    }
    
    public void preencherCertificacao(CertificacaoModel certificacaoModel){
        if(certificacaoModel != null){
            
            txtNomeEmpresa.setText(certificacaoModel.getNomeEmpresa());
            txtNifProdutor.setText(certificacaoModel.getNifProdutorSistema());
            txtNifRepresentante.setText(certificacaoModel.getNifRepresentanteLegal());
            dataValidacao.setDate(DataComponent.stringParaData(certificacaoModel.getDataValidacao()));
            dataValidacaoExpira.setDate(DataComponent.stringParaData(certificacaoModel.getDataExpirar()));
            txtEdereco.setText(certificacaoModel.getEndereco());
            txtPontoReferenca.setText(certificacaoModel.getPontoReferenca());
            txtEmail.setText(certificacaoModel.getEmail());
            txtNomePrograma.setText(certificacaoModel.getNomePrograma());
            txtNumero.setText(certificacaoModel.getNumeroValidacao());
            txtVersao.setText(certificacaoModel.getVersaoPrograma());
        }
    }
    
    public void carregarSerie()
    {
        SerieController serieController = new SerieController();
        cmbSerie.setModel(new DefaultComboBoxModel(serieController.get().toArray()));
    }
    
    public void carregarParamentros(){
        parametroController = new ParamentroController();
        for(ParamentroModel paramentroModel : parametroController.get()){
            
            selecionarCheck(paramentroModel);
        }
    }
    
    public void selecionarCheck(ParamentroModel paramentroModel){
        
        if(paramentroModel.getDescricao().equalsIgnoreCase(ckAssistente.getText())){
            ckAssistente.setSelected(paramentroModel.getValor()==1);
        }
        if(paramentroModel.getDescricao().equalsIgnoreCase(ckMostrarAntesImprimir.getText())){
            ckMostrarAntesImprimir.setSelected(paramentroModel.getValor()==1);
        }
        if(paramentroModel.getDescricao().equalsIgnoreCase(ckModuloSistema.getText())){
            ckModuloSistema.setSelected(paramentroModel.getValor()==1);
        }
        if(paramentroModel.getDescricao().equalsIgnoreCase(ckConfigProduto.getText())){
            ckConfigProduto.setSelected(paramentroModel.getValor()==1);
        }
        if(paramentroModel.getDescricao().equalsIgnoreCase(ckPanelPrecoProduto.getText())){
            ckPanelPrecoProduto.setSelected(paramentroModel.getValor()==1);
        }
        if(paramentroModel.getDescricao().equalsIgnoreCase(ckArmazemPrincipal.getText())){
            ckArmazemPrincipal.setSelected(paramentroModel.getValor()==1);
        }
        if(paramentroModel.getDescricao().equalsIgnoreCase(ckFormacao.getText())){
            ckFormacao.setSelected(paramentroModel.getValor()==1);
        }
    }
    public void salvarParametro(){
        
        boolean salvo = false;
        parametroController = new ParamentroController();
        for(ParamentroModel paramentroModel : parametroController.get()){
        
            if(paramentroModel.getDescricao().equalsIgnoreCase(ckAssistente.getText())){
                paramentroModel.setValor(ckAssistente.isSelected()? 1: 0);
            }
            if(paramentroModel.getDescricao().equalsIgnoreCase(ckMostrarAntesImprimir.getText())){
                paramentroModel.setValor(ckMostrarAntesImprimir.isSelected()? 1: 2);
            }
            if(paramentroModel.getDescricao().equalsIgnoreCase(ckModuloSistema.getText())){
                paramentroModel.setValor(ckModuloSistema.isSelected()? 1: 2);
            }
            if(paramentroModel.getDescricao().equalsIgnoreCase(ckConfigProduto.getText())){
                paramentroModel.setValor(ckConfigProduto.isSelected()? 1: 2);
            }
            if(paramentroModel.getDescricao().equalsIgnoreCase(ckPanelPrecoProduto.getText())){
                paramentroModel.setValor(ckPanelPrecoProduto.isSelected()? 1: 2);
            }
            if(paramentroModel.getDescricao().equalsIgnoreCase(ckArmazemPrincipal.getText())){
                paramentroModel.setValor(ckArmazemPrincipal.isSelected()? 1: 2);
            }
            if(paramentroModel.getDescricao().equalsIgnoreCase(ckFormacao.getText())){
                paramentroModel.setValor(ckFormacao.isSelected()? 1: 2);
            }
            
            salvo = parametroController.saveOrUpdate(paramentroModel);
        }
        
        if(salvo){
            
            JOptionPane.showMessageDialog(this, "Registado com sucesso");
            carregarParamentros();
        }
        else{
            JOptionPane.showMessageDialog(this, "Erro ao Registar");
            carregarParamentros();
        }
            
    }
            

    private void carregarConnection() {

        try {
            ArmazenamentoController<ConnectioFactoryModel> ficheiro = new ArmazenamentoController<>(arquivo);
            
            List<ConnectioFactoryModel> lista = ficheiro.getAll();
            if (lista.size() > 0) {
                ConnectioFactoryModel modelo = lista.get(0);
                
                
                txtSenha.setText(modelo.getPassword());
                txtLocalizacao.setText((modelo.getUrl().replaceAll("jdbc:mysql://", "")).replaceAll(":3306/grest", "").trim());
                txtUsuario.setText(modelo.getUser()); 
            }
        } catch (IOException ex) {
            Logger.getLogger(ConfigView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void salvarCertificacao()
    {
            
            certificacaoModel.setNomeEmpresa(txtNomeEmpresa.getText());
            certificacaoModel.setNifProdutorSistema(txtNifProdutor.getText());
            certificacaoModel.setNifRepresentanteLegal(txtNifRepresentante.getText());
            certificacaoModel.setDataValidacao(DataComponent.getData(dataValidacao));
            certificacaoModel.setDataExpirar(DataComponent.getData(dataValidacaoExpira));
            certificacaoModel.setEndereco(txtEdereco.getText());
            certificacaoModel.setPontoReferenca(txtPontoReferenca.getText());
            certificacaoModel.setEmail(txtEmail.getText());
            certificacaoModel.setNomePrograma(txtNomePrograma.getText());
            certificacaoModel.setNumeroValidacao(txtNumero.getText());
            certificacaoModel.setVersaoPrograma(txtVersao.getText());
            
            certificacaoController = new CertificacaoController();
            if(certificacaoController.saveOrUpdate(certificacaoModel)){
                JOptionPane.showMessageDialog(null, "Registado com Sucesso");
                carregarCertificacao();
            }else{
                JOptionPane.showMessageDialog(null, "Erro ao Registar");
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

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtLocalizacao = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        btnGravar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        dataFimLicenca = new com.toedter.calendar.JDateChooser();
        btnGravarLicenca = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        cmbSerie = new javax.swing.JComboBox<>();
        txtSerie = new javax.swing.JTextField();
        btnAlterarSerie = new javax.swing.JButton();
        btnGravarSerie = new javax.swing.JButton();
        btnActivarSerie = new javax.swing.JButton();
        btnNumeracaoDocumentos = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        panel1 = new java.awt.Panel();
        ckAssistente = new javax.swing.JCheckBox();
        ckMostrarAntesImprimir = new javax.swing.JCheckBox();
        ckModuloSistema = new javax.swing.JCheckBox();
        ckConfigProduto = new javax.swing.JCheckBox();
        ckPanelPrecoProduto = new javax.swing.JCheckBox();
        ckArmazemPrincipal = new javax.swing.JCheckBox();
        ckFormacao = new javax.swing.JCheckBox();
        btnGravarLicenca1 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtNomeEmpresa = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtNifRepresentante = new javax.swing.JTextField();
        txtNifProdutor = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtEdereco = new javax.swing.JTextField();
        txtPontoReferenca = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtNomePrograma = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        txtVersao = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        btnGravarCertificacao = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        dataValidacao = new com.toedter.calendar.JDateChooser();
        dataValidacaoExpira = new com.toedter.calendar.JDateChooser();
        dataExpiracao = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configuração do Sistema");
        setIconImage(new ImageIcon(getClass().getResource("/IMAGUENS/icon.png")).getImage());
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Localização do servidor( BD )");

        jLabel2.setText("Usuário");

        jLabel3.setText("Senha");

        btnGravar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red.png"))); // NOI18N
        btnGravar.setText("Gravar");
        btnGravar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtUsuario, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                        .addComponent(txtLocalizacao, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(332, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtLocalizacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGravar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Servidor", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setText("Data de Fim da Licença");

        btnGravarLicenca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red.png"))); // NOI18N
        btnGravarLicenca.setText("Gravar");
        btnGravarLicenca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarLicencaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(btnGravarLicenca, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dataFimLicenca, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(469, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(dataFimLicenca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnGravarLicenca, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(217, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Licença Manual", jPanel3);

        jPanel4.setBackground(java.awt.Color.white);

        cmbSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSerieActionPerformed(evt);
            }
        });

        btnAlterarSerie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red.png"))); // NOI18N
        btnAlterarSerie.setText("Alterar");
        btnAlterarSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlterarSerieActionPerformed(evt);
            }
        });

        btnGravarSerie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red.png"))); // NOI18N
        btnGravarSerie.setText("Gravar");
        btnGravarSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarSerieActionPerformed(evt);
            }
        });

        btnActivarSerie.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red.png"))); // NOI18N
        btnActivarSerie.setText("Activar");
        btnActivarSerie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActivarSerieActionPerformed(evt);
            }
        });

        btnNumeracaoDocumentos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/relact3.png"))); // NOI18N
        btnNumeracaoDocumentos.setText("Numeração dos Documentos");
        btnNumeracaoDocumentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNumeracaoDocumentosActionPerformed(evt);
            }
        });

        jButton1.setText("Actualizar Lote");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnGravarSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnAlterarSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbSerie, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnActivarSerie))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(btnNumeracaoDocumentos)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)))
                .addContainerGap(306, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActivarSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtSerie, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNumeracaoDocumentos, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGravarSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAlterarSerie, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
        );

        jTabbedPane1.addTab("Série /Documentos", jPanel4);

        panel1.setBackground(java.awt.Color.white);

        ckAssistente.setText("IMPRIMIR MOSTRAR ASSISTENTE");
        ckAssistente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckAssistenteActionPerformed(evt);
            }
        });

        ckMostrarAntesImprimir.setText("MOSTRAR ANTES DE IMPRIMIR ");

        ckModuloSistema.setText("MODULO SISTEMA ");

        ckConfigProduto.setText("PANEL CONFIG - PRODUTO");
        ckConfigProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckConfigProdutoActionPerformed(evt);
            }
        });

        ckPanelPrecoProduto.setText("PANEL PRECO - PRODUTO");

        ckArmazemPrincipal.setText("ARMAZEM PRINCIPAL LOJA");

        ckFormacao.setText("FORMAÇÃO");

        btnGravarLicenca1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red.png"))); // NOI18N
        btnGravarLicenca1.setText("Gravar");
        btnGravarLicenca1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarLicenca1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(ckFormacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ckArmazemPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ckPanelPrecoProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ckConfigProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ckModuloSistema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ckAssistente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ckMostrarAntesImprimir, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 294, Short.MAX_VALUE)
                .addComponent(btnGravarLicenca1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(188, 188, 188)
                        .addComponent(btnGravarLicenca1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(ckAssistente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ckMostrarAntesImprimir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ckModuloSistema)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ckConfigProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ckPanelPrecoProduto)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ckArmazemPrincipal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ckFormacao)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Módulo/Parametro", panel1);

        jPanel5.setBackground(java.awt.Color.white);

        jLabel7.setText("Nome Empresa:");

        jLabel8.setText("Nif Produtor:");

        jLabel9.setText("Nif Representante:");

        jLabel10.setText("Endereço");

        jLabel11.setText("Ponto de Referênça:");

        jLabel12.setText("Email:");

        jLabel13.setText("Nome Sistema:");

        jLabel14.setText("Número:");

        jLabel15.setText("Versão:");

        btnGravarCertificacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/red.png"))); // NOI18N
        btnGravarCertificacao.setText("Gravar");
        btnGravarCertificacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGravarCertificacaoActionPerformed(evt);
            }
        });

        jLabel16.setText("Dat. Validação:");

        dataExpiracao.setText("Dat. Expiração:");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel13)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtNifRepresentante, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                            .addComponent(txtNifProdutor, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(dataExpiracao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dataValidacao, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                            .addComponent(dataValidacaoExpira, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnGravarCertificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtNomeEmpresa)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtNomePrograma, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNumero))
                    .addComponent(txtEdereco)
                    .addComponent(txtPontoReferenca)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtVersao, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtNomeEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNifProdutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(jLabel16))
                    .addComponent(dataValidacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtNifRepresentante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)
                        .addComponent(dataExpiracao))
                    .addComponent(dataValidacaoExpira, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEdereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(9, 9, 9)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPontoReferenca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomePrograma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtVersao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGravarCertificacao, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Certificação", jPanel5);

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/BANNE.png"))); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/ddd.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
private void removerConection() {

        try {
            ArmazenamentoController<ConnectioFactoryModel> ficheiro = new ArmazenamentoController<>(arquivo);
            if (ficheiro.getAll().size() > 0) {
                
                for (int i = 0; i < ficheiro.getAll().size(); i++) {
                    
                    ficheiro.delete(i);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ConfigView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
private void removerLicenca() {

        try {
            ArmazenamentoController<ConnectioFactoryModel> ficheiro = new ArmazenamentoController<>("Licenca");
            if (ficheiro.getAll().size() > 0) {
                
                for (int i = 0; i < ficheiro.getAll().size(); i++) {
                    
                    ficheiro.delete(i);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ConfigView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void btnGravarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarActionPerformed
        try {
            // TODO add your handling code here:
            
            String url = "jdbc:mysql://" + txtLocalizacao.getText() + ":3306/grest";
            ArmazenamentoController<ConnectioFactoryModel> ficheiro = new ArmazenamentoController<>(arquivo);
            removerConection();
            
            ConnectioFactoryModel modelo = new ConnectioFactoryModel(url,
                    txtUsuario.getText(),
                    String.valueOf(txtSenha.getPassword()),
                    "com.mysql.cj.jdbc.Driver");
            
            if (!modelo.isEmpty()) {
                
                if (ficheiro.create(modelo)) {
                    
                    JOptionPane.showMessageDialog(this, "Registado com sucesso");
                    
                }
                
            } else {
                JOptionPane.showMessageDialog(this, "Preencha o espaço em branco");
            }
        } catch (IOException ex) {
            Logger.getLogger(ConfigView.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnGravarActionPerformed

    private void btnGravarLicencaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarLicencaActionPerformed
        try {
            // TODO add your handling code here:
            ArmazenamentoController<LicencaModel> ficheiro = new ArmazenamentoController<>("Licenca");
            if(dataFimLicenca.getDate() != null){
                
                
                try {
                    removerLicenca();
                    
                    LicencaModel modelo = new LicencaModel();
                    modelo.setDataFimLicenca(DataComponent.getData(dataFimLicenca));
                  //  modelo.setHdSerial(InfoComputadorUtil.getHDSerial());
//                    modelo.setMacAddress(InfoComputadorUtil.retornaMacAddress());
                  //  modelo.setMotherboardSerial(InfoComputadorUtil.getMotherboardSerial());
                  //  modelo.setcPUSerial(InfoComputadorUtil.getCPUSerial());
                    if(ficheiro.create(modelo)){
                        JOptionPane.showMessageDialog(this, "Registado com sucesso");
                    }else{
                        JOptionPane.showMessageDialog(this, "Nao foi possivel registar a licenca consulte o admin","ERRO",JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ConfigView.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (IOException ex) {            
            Logger.getLogger(ConfigView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGravarLicencaActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
        if(jTabbedPane1.getSelectedIndex() == 1){
            
            try {
                ArmazenamentoController<LicencaModel> ficheiro = new ArmazenamentoController<>("Licenca");
                LicencaModel modelo = ficheiro.getAll().get(0);
                dataFimLicenca.setDate(DataComponent.stringParaData(modelo.getDataFimLicenca()));
            } catch (IOException ex) {
                Logger.getLogger(ConfigView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void btnAlterarSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlterarSerieActionPerformed
        // TODO add your handling code here:
        
        if(!txtSerie.getText().isEmpty()){
            
            serieModel = (SerieModel) cmbSerie.getSelectedItem();
            serieModel.setDesignacao(txtSerie.getText());
            serieController = new SerieController();
            
            if(!serieController.getExiste(serieModel)){

                if(serieController.saveOrUpdate(serieModel)){

                    JOptionPane.showMessageDialog(this, "Alterado com sucesso");
                    carregarSerie();
                }else{

                    JOptionPane.showMessageDialog(this, "Nao foi possivel Alterar a Serie consulte o admin","ERRO",JOptionPane.ERROR_MESSAGE);
                }
                
            }else{
                
                JOptionPane.showMessageDialog(this, "Nao foi possivel Alterar Serie Já Existente","ERRO",JOptionPane.ERROR_MESSAGE);
                carregarSerie();
            }
            
        }else{
            
            JOptionPane.showMessageDialog(this, "Nao foi possivel Alterar a Serie Preencha a Designação","ERRO",JOptionPane.ERROR_MESSAGE);
            carregarSerie();
        }
    }//GEN-LAST:event_btnAlterarSerieActionPerformed

    private void btnGravarSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarSerieActionPerformed
        // TODO add your handling code here:
        int anoActual = Integer.parseInt(DataComponent.getAnoActual());
        anoController = new AnoController();
        serieController = new SerieController();
        
        anoModel = anoController.getByAno(anoActual);
        
        if(!txtSerie.getText().isEmpty()){
            
        
            if(anoModel != null){// caso o ano já exista

                serieModel = new SerieModel();

                serieModel.setAno(anoModel);
                serieModel.setDesignacao(txtSerie.getText());
                serieModel.setStatus(2);

                if(!serieController.getExiste(serieModel)){
                    
                    if(serieController.saveOrUpdate(serieModel)){


                        JOptionPane.showMessageDialog(this, "Registado com sucesso");
                        carregarSerie();

                    }else{

                        JOptionPane.showMessageDialog(this, "Nao foi possivel registar a Serie consulte o admin","ERRO",JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    
                    JOptionPane.showMessageDialog(this, "Nao foi possivel registar Serie Já Existente","ERRO",JOptionPane.ERROR_MESSAGE);
                }
                

            }else{//Caso o ano ainda não existir

                anoModel = new AnoModel();
                anoModel.setAno(anoActual);

                anoController.saveOrUpdate(anoModel);

                anoModel = anoController.getLast();

                serieModel = new SerieModel();

                serieModel.setAno(anoModel);
                serieModel.setDesignacao(txtSerie.getText());
                serieModel.setStatus(2);
                
                if(!serieController.getExiste(serieModel)){
                    
                    if(serieController.saveOrUpdate(serieModel)){

                        JOptionPane.showMessageDialog(this, "Registado com sucesso");
                        carregarSerie();

                    }else{

                        JOptionPane.showMessageDialog(this, "Nao foi possivel registar a Serie consulte o admin","ERRO",JOptionPane.ERROR_MESSAGE);
                    }
                }else{
                    
                    JOptionPane.showMessageDialog(this, "Nao foi possivel registar Serie Já Existente","ERRO",JOptionPane.ERROR_MESSAGE);
                }
                    
            }
        }else{
            
            JOptionPane.showMessageDialog(this, "Nao foi possivel registar a Serie Preencha a Designação!","ERRO",JOptionPane.ERROR_MESSAGE);
            
        }
        
        
    }//GEN-LAST:event_btnGravarSerieActionPerformed

    private void btnActivarSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActivarSerieActionPerformed
        // TODO add your handling code here:
        serieModel = (SerieModel) cmbSerie.getSelectedItem();
        serieController = new SerieController();
        
        if(serieController.desactivarAll()){
            
            if(serieController.activar(serieModel))
                JOptionPane.showMessageDialog(this, "Activado com sucesso");
            else
                JOptionPane.showMessageDialog(this, "Nao foi possivel Activar","ERRO",JOptionPane.ERROR_MESSAGE);
        }else{
            
            JOptionPane.showMessageDialog(this, "Nao foi possivel Desactivar os Outros!","ERRO",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnActivarSerieActionPerformed

    private void cmbSerieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSerieActionPerformed
        // TODO add your handling code here:
        
        serieModel = (SerieModel) cmbSerie.getSelectedItem();
         
         serieController = new SerieController();
         if(serieController.getTemFactura(serieModel))
         {
             txtSerie.setText("");
             txtSerie.setEnabled(false);
             btnAlterarSerie.setEnabled(false);
         }else{
             
             txtSerie.setText(serieModel.getDesignacao());
             txtSerie.setEnabled(true);
             btnAlterarSerie.setEnabled(true);
         }
    }//GEN-LAST:event_cmbSerieActionPerformed

    private void ckAssistenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckAssistenteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ckAssistenteActionPerformed

    private void btnGravarLicenca1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarLicenca1ActionPerformed
        // TODO add your handling code here:
        salvarParametro();
    }//GEN-LAST:event_btnGravarLicenca1ActionPerformed

    private void ckConfigProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckConfigProdutoActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_ckConfigProdutoActionPerformed

    private void btnGravarCertificacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGravarCertificacaoActionPerformed
        // TODO add your handling code here:
        salvarCertificacao();
    }//GEN-LAST:event_btnGravarCertificacaoActionPerformed

    private void btnNumeracaoDocumentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumeracaoDocumentosActionPerformed
        // TODO add your handling code here:
        new FrmDocumento().setVisible(true);
    }//GEN-LAST:event_btnNumeracaoDocumentosActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Excel e = new Excel();
        e.updateLote();
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(ConfigView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConfigView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConfigView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConfigView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConfigView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActivarSerie;
    private javax.swing.JButton btnAlterarSerie;
    private javax.swing.JButton btnGravar;
    private javax.swing.JButton btnGravarCertificacao;
    private javax.swing.JButton btnGravarLicenca;
    private javax.swing.JButton btnGravarLicenca1;
    private javax.swing.JButton btnGravarSerie;
    private javax.swing.JButton btnNumeracaoDocumentos;
    private javax.swing.JCheckBox ckArmazemPrincipal;
    private javax.swing.JCheckBox ckAssistente;
    private javax.swing.JCheckBox ckConfigProduto;
    private javax.swing.JCheckBox ckFormacao;
    private javax.swing.JCheckBox ckModuloSistema;
    private javax.swing.JCheckBox ckMostrarAntesImprimir;
    private javax.swing.JCheckBox ckPanelPrecoProduto;
    private javax.swing.JComboBox<String> cmbSerie;
    private javax.swing.JLabel dataExpiracao;
    private com.toedter.calendar.JDateChooser dataFimLicenca;
    private com.toedter.calendar.JDateChooser dataValidacao;
    private com.toedter.calendar.JDateChooser dataValidacaoExpira;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
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
    private javax.swing.JTabbedPane jTabbedPane1;
    private java.awt.Panel panel1;
    private javax.swing.JTextField txtEdereco;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtLocalizacao;
    private javax.swing.JTextField txtNifProdutor;
    private javax.swing.JTextField txtNifRepresentante;
    private javax.swing.JTextField txtNomeEmpresa;
    private javax.swing.JTextField txtNomePrograma;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtPontoReferenca;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtSerie;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JTextField txtVersao;
    // End of variables declaration//GEN-END:variables
}
