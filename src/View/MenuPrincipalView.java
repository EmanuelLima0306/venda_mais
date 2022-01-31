/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
 * MenuPrincipalView.java
 *
 * Created on Mar 29, 2011, 7:29:03 PM
 */
package View;

import Controller.CaixaController;
import Controller.FacturaController;
import Controller.ParamentroController;
import Enum.TipoLog;
import Ireport.ArmazemIreport;
import Ireport.CategoriaIreport;
import Ireport.ClienteIreport;
import Ireport.FabricanteIreport;
import Ireport.FacturaIreport;
import Ireport.FornecedorIreport;
import Ireport.StockIreport;
import Ireport.UsuarioIreport;
import Model.CaixaModel;
import Model.MenuItemModel;
import Model.ParamentroModel;
import Model.UsuarioModel;
import Util.BackupAutomatico;
import Util.CopySecury;
import Util.DataComponent;
import Util.LogUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import static java.awt.Frame.getFrames;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

/**
 *
 * @author celso & Emanuel
 */
public final class MenuPrincipalView extends JFrame {

    private UsuarioModel usuario;
    private boolean activo = false;
    private Thread thread;

    public MenuPrincipalView() {
        initComponents();
        this.moduloSistema();
        mouseEvent();
        fecharTela();
    }

    public static boolean DataConforme() // verifica se a data actual é maior ou igual que a data da ultima factura
    {
        FacturaController facturaController = new FacturaController();
        return DataComponent.compareDataLastFactura(facturaController.getDataLastFactura());
    }

    private void moduloSistema() {

        ParamentroController controller = new ParamentroController();
        ParamentroModel modelo = controller.getById(3); //MODULO DO SISTEMA  / 
        
        
        if (modelo.getValor() == 1) { //(1) - VENDA MAS
            menuMesa.setVisible(false);
        } else {

            if (modelo.getValor() == 2){ //(2) - MREST

                    jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/Restaurante.png")));
            } else {
                if (modelo.getValor() == 3) { // - Barbearia
                    
                    jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/barbearia.png")));

                } else {
                    if(modelo.getValor() == 4){ // - Lavandaria
                        
                    }
                }
            }

        }
        
        /* Tratamento da imagem de fundo da tela Principal */
        
        Dimension dimensao = Toolkit.getDefaultToolkit().getScreenSize();
//        Dimension dimensao = desktop.getSize();
        ImageIcon imagem = (ImageIcon)jLabel5.getIcon();
        jLabel5.setSize(dimensao.width, dimensao.height);
        jLabel5.setIcon(new ImageIcon(imagem.getImage().getScaledInstance(dimensao.width, dimensao.height, Image.SCALE_DEFAULT)));

    }

    public MenuPrincipalView(UsuarioModel usuario) {
        initComponents();
        this.usuario = usuario;
        fecharTela();
        this.setTitle("SOFTWARE DE GESTAO COMERCIAL DESENVOLVIDO PELA ZETASOFT TECNOLOGIAS / USUARIO LOGADO ----  " + usuario.getNome());
        // setExtendedState(JFrame.MAXIMIZED_BOTH);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(MAXIMIZED_BOTH);
        mouseEvent();
        this.moduloSistema();

        permissao();

        camposOcultos();
    }

    public void fecharTela() { // evento ao clicar no botão fechar tela (x) do topo

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {

                Object[] opcao = {"Sim", "Não"};
                if (JOptionPane.showOptionDialog(null, " Deseja sair do Sistema?", " Alerta ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcao, opcao[0]) == JOptionPane.YES_OPTION) {
                    Frame[] frames = getFrames();
                    boolean telaMenuPrincipalAberto = false;
                    for (int i = 0; i < frames.length; i++) {

                        if (frames[i].getName().equals("BalcaoView")) {
                            telaMenuPrincipalAberto = frames[i].isVisible();
                        }
                    }

                    if (!telaMenuPrincipalAberto) {
                        hide();
                        backupBD();
                        System.exit(0);
                    } else {
                        hide();
                    }
                }

            }
        });
    }

    private void backupBD() {

        // TODO add your handling code here:
//            CopySecury.backup();
        ParamentroController controller = new ParamentroController();
        ParamentroModel modelo = controller.getById(9);

        if (modelo.getValor() == 1) {
            new BackupAutomatico().run();
        }

    }

    public void camposOcultos() {
        jMenuItem32.setVisible(false);
        jMenuItem42.setVisible(false);
    }

    public void permissao() {
        List<MenuItemModel> lista = new ArrayList<>();
        lista = usuario.getMenuItem();
        for (MenuItemModel object : lista) {

            if (object.getDesignacao().equals(jMenu1.getText())) {
                fichero(object);
            }

            if (object.getDesignacao().equals(jMenu2.getText())) {
                operacao(object);
            }

            if (object.getDesignacao().equals(jMenu3.getText())) {
                tabela(object);
            }

            if (object.getDesignacao().equals(jMenu4.getText())) {
                relatorio(object);
            }

            if (object.getDesignacao().equals(jMenu5.getText())) {
                grafico(object);
            }

            if (object.getDesignacao().equals(jMenu13.getText())) {
                sistema(object);
            }

            if (object.getDesignacao().equals(jMenu6.getText())) {
                ajuda(object);
            }

        }

    }

    /*------------------------------- Criação dos Menus Principais ---------------------*/
    public void fichero(MenuItemModel menuItemModel) {

        for (MenuItemModel object : menuItemModel.getItem()) {

            if (object.getDesignacao().equals(Produto.getText())) {
                Produto.setEnabled(object.getIdEstado() == 1);
                btnCadastrarProduto.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(Fornecedor.getText())) {

                Fornecedor.setEnabled(object.getIdEstado() == 1);
                btnFornecedor.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(cliente.getText())) {

                cliente.setEnabled(object.getIdEstado() == 1);
                btnCliente.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(entradaStock.getText())) {

                entradaStock.setEnabled(object.getIdEstado() == 1);
                btnActualizar.setEnabled(object.getIdEstado() == 1);
                btnEntrada.setEnabled(object.getIdEstado() == 1);
            }
        }
    }

    public void operacao(MenuItemModel menuItemModel) {

        for (MenuItemModel object : menuItemModel.getItem()) {

            if (object.getDesignacao().equals(menuTransferencia.getText())) {

                menuTransferencia.setEnabled(object.getIdEstado() == 1);
                btnTransferencia.setEnabled(object.getIdEstado() == 1);
            }
            
            if (object.getDesignacao().equals(fechoCaixas.getText())) {

                fechoCaixas.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenu7.getText())) {
                pagamentoDeDividas(object);
            }

//            if(object.getDesignacao().equals(eliminarFactura.getText()))
//                eliminarFactura.setEnabled(object.getIdEstado() == 1);
//            
            if (object.getDesignacao().equals(jMenuItem10.getText())) {
                encomenda(object);
            }

            if (object.getDesignacao().equals(menuActualizacao.getText())) {
                menuActualizacao.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(alterarDataExpiracao.getText())) {
                alterarDataExpiracao.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(txtAlterarPrecoVenda.getText())) {
                txtAlterarPrecoVenda.setEnabled(object.getIdEstado() == 1);
            }
            if (object.getDesignacao().equals(itemAdicionarQtdProduto.getText())) {
                itemAdicionarQtdProduto.setEnabled(object.getIdEstado() == 1);
            }
            if (object.getDesignacao().equals(itemAgruparEntradas.getText())) {
                itemAgruparEntradas.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(alterarCodBarra.getText())) {
                alterarCodBarra.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(devolucao.getText())) {
                devolucao.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenu17.getText())) {
                documentoRetificatico(object);
            }
        }

    }

    public void tabela(MenuItemModel menuItemModel) {

        for (MenuItemModel object : menuItemModel.getItem()) {

            if (object.getDesignacao().equals(Categoria.getText())) {
                Categoria.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(fabricante1.getText())) {
                fabricante1.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(MenuArmazem.getText())) {
                MenuArmazem.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(MenuTabelaFormaPagamento.getText())) {
                MenuTabelaFormaPagamento.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem5.getText())) {
                jMenuItem5.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(menuIpc.getText())) {
                menuIpc.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(fabricante.getText())) {
                fabricante.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(itemAgruparEntradas.getText())) {
                itemAgruparEntradas.setEnabled(object.getIdEstado() == 1);
            }
            
            if (object.getDesignacao().equals(itemAdicionarQtdProduto.getText())) {
                itemAdicionarQtdProduto.setEnabled(object.getIdEstado() == 1);
            }
            if (object.getDesignacao().equals(jMenu16.getText())) {
                iva(object);
            }

            if (object.getDesignacao().equals(menuMesa.getText())) {
                menuMesa.setEnabled(object.getIdEstado() == 1);
            }
            if (object.getDesignacao().equals(itemPermitirFechoCaixa.getText())) {
                itemPermitirFechoCaixa.setEnabled(object.getIdEstado() == 1);
            }
        }

    }

    public void relatorio(MenuItemModel menuItemModel) {

        for (MenuItemModel object : menuItemModel.getItem()) {

            if (object.getDesignacao().equals(jMenu8.getText())) {
                cliente(object);
            }

            if (object.getDesignacao().equals(jMenu10.getText())) {
                fornecedores(object);
            }

            if (object.getDesignacao().equals(jMenu12.getText())) {
                produto(object);
            }

            if (object.getDesignacao().equals(jMenuItem23.getText())) {
                jMenuItem23.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem24.getText())) {
                jMenuItem24.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem25.getText())) {
                jMenuItem25.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem30.getText())) {

                jMenuItem30.setEnabled(object.getIdEstado() == 1);
                btnListaMovimento.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem32.getText())) {
                jMenuItem32.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem42.getText())) {
                jMenuItem42.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem41.getText())) {
                jMenuItem41.setEnabled(object.getIdEstado() == 1);
            }
            if (object.getDesignacao().equals(jMenuItem46.getText())) {
                jMenuItem41.setEnabled(object.getIdEstado() == 1);
            }

        }

    }

    public void grafico(MenuItemModel menuItemModel) {

        for (MenuItemModel object : menuItemModel.getItem()) {

            if (object.getDesignacao().equals(jMenu14.getText())) {
                stock(object);
            }

            if (object.getDesignacao().equals(jMenu15.getText())) {
                venda(object);
            }
        }

    }

    public void sistema(MenuItemModel menuItemModel) {

        for (MenuItemModel object : menuItemModel.getItem()) {

            if (object.getDesignacao().equals(itemUsuario.getText())) {
                itemUsuario.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(permissao.getText())) {
                permissao.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(empresa.getText())) {
                empresa.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem15.getText())) {
                jMenuItem15.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(menuBackup.getText())) {
                menuBackup.setEnabled(object.getIdEstado() == 1);
            }

        }

    }

    public void ajuda(MenuItemModel menuItemModel) {

        for (MenuItemModel object : menuItemModel.getItem()) {

            if (object.getDesignacao().equals(jMenuItem37.getText())) {
                jMenuItem37.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem38.getText())) {
                jMenuItem38.setEnabled(object.getIdEstado() == 1);
            }

        }

    }

    /*------------------------------ Menus que estão por dentro de outros menus ou items*/
    public void pagamentoDeDividas(MenuItemModel menuItemModel) {

        for (MenuItemModel object : menuItemModel.getItem()) {

            if (object.getDesignacao().equals(pagamentoFornecedor.getText())) {
                pagamentoFornecedor.setEnabled(object.getIdEstado() == 1);
            }
        }
    }

    public void encomenda(MenuItemModel menuItemModel) {

        for (MenuItemModel object : menuItemModel.getItem()) {

            if (object.getDesignacao().equals(jMenuItem9.getText())) {
                jMenuItem9.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem10.getText())) {
                jMenuItem10.setEnabled(object.getIdEstado() == 1);
            }
        }
    }

    public void documentoRetificatico(MenuItemModel menuItemModel) {

        for (MenuItemModel object : menuItemModel.getItem()) {

            if (object.getDesignacao().equals(jMenuItem43.getText())) {
                jMenuItem43.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem44.getText())) {
                jMenuItem44.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem45.getText())) {
                jMenuItem45.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem39.getText())) {
                jMenuItem39.setEnabled(object.getIdEstado() == 1);
            }
        }
    }

    public void iva(MenuItemModel menuItemModel) {

        for (MenuItemModel object : menuItemModel.getItem()) {

            if (object.getDesignacao().equals(jMenuItem34.getText())) {
                jMenuItem34.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem35.getText())) {
                jMenuItem35.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem36.getText())) {
                jMenuItem36.setEnabled(object.getIdEstado() == 1);
            }

        }
    }

    public void produto(MenuItemModel menuItemModel) {

        for (MenuItemModel object : menuItemModel.getItem()) {

            if (object.getDesignacao().equals(jMenuItem16.getText())) {
                jMenuItem16.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem21.getText())) {
                jMenuItem21.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem22.getText())) {
                jMenuItem22.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem8.getText())) {
                jMenuItem8.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(itemExpirado.getText())) {
                itemExpirado.setEnabled(object.getIdEstado() == 1);
            }
            if (object.getDesignacao().equals(itemPresteExpirar.getText())) {
                itemPresteExpirar.setEnabled(object.getIdEstado() == 1);
            }
            if (object.getDesignacao().equals(itemPresteTerminar.getText())) {
                itemPresteTerminar.setEnabled(object.getIdEstado() == 1);
            }
        }
    }

    public void fornecedores(MenuItemModel menuItemModel) {

        for (MenuItemModel object : menuItemModel.getItem()) {

            if (object.getDesignacao().equals(jMenuItem7.getText())) {
                jMenuItem7.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(todosFornecedor.getText())) {
                todosFornecedor.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem29.getText())) {
                jMenuItem29.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem2.getText())) {
                jMenuItem2.setEnabled(object.getIdEstado() == 1);
            }
        }
    }

    public void stock(MenuItemModel menuItemModel) {

        for (MenuItemModel object : menuItemModel.getItem()) {

            if (object.getDesignacao().equals(jMenuItem14.getText())) {
                jMenuItem14.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem17.getText())) {
                jMenuItem17.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem18.getText())) {
                jMenuItem18.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem19.getText())) {
                jMenuItem19.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem20.getText())) {
                jMenuItem20.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem26.getText())) {
                jMenuItem26.setEnabled(object.getIdEstado() == 1);
            }
        }
    }

    public void venda(MenuItemModel menuItemModel) {

        for (MenuItemModel object : menuItemModel.getItem()) {

            if (object.getDesignacao().equals(jMenuItem27.getText())) {
                jMenuItem27.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem28.getText())) {
                jMenuItem28.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem31.getText())) {
                jMenuItem31.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem33.getText())) {
                jMenuItem33.setEnabled(object.getIdEstado() == 1);
            }
        }
    }

    public void cliente(MenuItemModel menuItemModel) {

        for (MenuItemModel object : menuItemModel.getItem()) {

            if (object.getDesignacao().equals(jMenu9.getText())) {
                factura(object);
            }

            if (object.getDesignacao().equals(jMenuItem11.getText())) {
                jMenuItem11.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(todosClientes.getText())) {
                todosClientes.setEnabled(object.getIdEstado() == 1);
            }
        }
    }

    public void factura(MenuItemModel menuItemModel) {

        for (MenuItemModel object : menuItemModel.getItem()) {

            if (object.getDesignacao().equals(jMenuItem3.getText())) {
                jMenuItem3.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem4.getText())) {
                jMenuItem4.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem6.getText())) {
                jMenuItem6.setEnabled(object.getIdEstado() == 1);
            }

            if (object.getDesignacao().equals(jMenuItem1.getText())) {
                jMenuItem1.setEnabled(object.getIdEstado() == 1);
            }
        }
    }

    /*------------------------------- fim de permições  ---------------------*/
    private void mouseEvent() {

        for (Component comp : panelMenuSide.getComponents()) {

            comp.addMouseListener(new MouseListener() {

                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                    comp.setBackground(new Color(204, 153, 0));
                }

                @Override
                public void mouseExited(MouseEvent e) {

                    comp.setBackground(new Color(51, 51, 51));

                }

            });
        }

    }

    private void backup() {
        try {
            // TODO add your handling code here:
            CopySecury.backup();
        } catch (IOException ex) {
            Logger.getLogger(MenuPrincipalView.class.getName()).log(Level.SEVERE, null, ex);
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

        panelBarSide = new javax.swing.JPanel();
        btnSair1 = new javax.swing.JButton();
        desktop = new javax.swing.JDesktopPane();
        panelMenuSide = new javax.swing.JPanel();
        btnFornecedor = new javax.swing.JButton();
        btnCliente = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEncomenda = new javax.swing.JButton();
        btnEntrada = new javax.swing.JButton();
        btnListaMovimento = new javax.swing.JButton();
        btnFacturacao = new javax.swing.JButton();
        btnTransferencia = new javax.swing.JButton();
        btnCadastrarProduto = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        btnTrocar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        Produto = new javax.swing.JMenuItem();
        Fornecedor = new javax.swing.JMenuItem();
        cliente = new javax.swing.JMenuItem();
        entradaStock = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuTransferencia = new javax.swing.JMenuItem();
        fechoCaixas = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        pagamentoFornecedor = new javax.swing.JMenuItem();
        jMenu11 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        menuActualizacao = new javax.swing.JMenuItem();
        alterarDataExpiracao = new javax.swing.JMenuItem();
        txtAlterarPrecoVenda = new javax.swing.JMenuItem();
        alterarCodBarra = new javax.swing.JMenuItem();
        devolucao = new javax.swing.JMenuItem();
        jMenu17 = new javax.swing.JMenu();
        jMenuItem43 = new javax.swing.JMenuItem();
        jMenuItem44 = new javax.swing.JMenuItem();
        jMenuItem45 = new javax.swing.JMenuItem();
        jMenuItem39 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        Categoria = new javax.swing.JMenuItem();
        fabricante1 = new javax.swing.JMenuItem();
        MenuArmazem = new javax.swing.JMenuItem();
        MenuTabelaFormaPagamento = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        menuIpc = new javax.swing.JMenuItem();
        fabricante = new javax.swing.JMenuItem();
        itemAgruparEntradas = new javax.swing.JMenuItem();
        itemAdicionarQtdProduto = new javax.swing.JMenuItem();
        itemPermitirFechoCaixa = new javax.swing.JMenuItem();
        jMenu16 = new javax.swing.JMenu();
        jMenuItem34 = new javax.swing.JMenuItem();
        jMenuItem35 = new javax.swing.JMenuItem();
        jMenuItem36 = new javax.swing.JMenuItem();
        menuMesa = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenu8 = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        todosClientes = new javax.swing.JMenuItem();
        jMenu10 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        todosFornecedor = new javax.swing.JMenuItem();
        jMenuItem29 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu12 = new javax.swing.JMenu();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem21 = new javax.swing.JMenuItem();
        jMenuItem22 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        itemExpirado = new javax.swing.JMenuItem();
        itemPresteExpirar = new javax.swing.JMenuItem();
        itemPresteTerminar = new javax.swing.JMenuItem();
        jMenuItem23 = new javax.swing.JMenuItem();
        jMenuItem24 = new javax.swing.JMenuItem();
        jMenuItem25 = new javax.swing.JMenuItem();
        jMenuItem30 = new javax.swing.JMenuItem();
        jMenuItem41 = new javax.swing.JMenuItem();
        jMenuItem46 = new javax.swing.JMenuItem();
        jMenuItem32 = new javax.swing.JMenuItem();
        jMenuItem42 = new javax.swing.JMenuItem();
        jMenuItem40 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenu14 = new javax.swing.JMenu();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem20 = new javax.swing.JMenuItem();
        jMenuItem26 = new javax.swing.JMenuItem();
        jMenu15 = new javax.swing.JMenu();
        jMenuItem27 = new javax.swing.JMenuItem();
        jMenuItem28 = new javax.swing.JMenuItem();
        jMenuItem31 = new javax.swing.JMenuItem();
        jMenuItem33 = new javax.swing.JMenuItem();
        jMenu13 = new javax.swing.JMenu();
        itemUsuario = new javax.swing.JMenuItem();
        permissao = new javax.swing.JMenuItem();
        empresa = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        menuBackup = new javax.swing.JMenuItem();
        menuBackup1 = new javax.swing.JMenuItem();
        Importacao = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem37 = new javax.swing.JMenuItem();
        jMenuItem38 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("ContSys Menu Principal");
        setBackground(new java.awt.Color(29, 37, 50));
        setName("MenuPrincipalView"); // NOI18N

        panelBarSide.setBackground(new java.awt.Color(51, 51, 51));
        panelBarSide.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnSair1.setBackground(new java.awt.Color(51, 51, 51));
        btnSair1.setForeground(new java.awt.Color(255, 255, 255));
        btnSair1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/menu_filled_40px.png"))); // NOI18N
        btnSair1.setText("Menu");
        btnSair1.setContentAreaFilled(false);
        btnSair1.setOpaque(true);
        btnSair1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSair1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBarSideLayout = new javax.swing.GroupLayout(panelBarSide);
        panelBarSide.setLayout(panelBarSideLayout);
        panelBarSideLayout.setHorizontalGroup(
            panelBarSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBarSideLayout.createSequentialGroup()
                .addComponent(btnSair1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelBarSideLayout.setVerticalGroup(
            panelBarSideLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnSair1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        desktop.setPreferredSize(new java.awt.Dimension(1339, 899));

        panelMenuSide.setBackground(new java.awt.Color(0, 0, 0));
        panelMenuSide.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panelMenuSide.setMinimumSize(new java.awt.Dimension(180, 581));
        panelMenuSide.setPreferredSize(new java.awt.Dimension(180, 581));
        panelMenuSide.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnFornecedor.setBackground(new java.awt.Color(51, 51, 51));
        btnFornecedor.setForeground(new java.awt.Color(255, 255, 255));
        btnFornecedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/fornecedores.png"))); // NOI18N
        btnFornecedor.setText("Fornecedor              ");
        btnFornecedor.setToolTipText("Fornecedor              ");
        btnFornecedor.setContentAreaFilled(false);
        btnFornecedor.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        btnFornecedor.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnFornecedor.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnFornecedor.setOpaque(true);
        btnFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFornecedorActionPerformed(evt);
            }
        });
        panelMenuSide.add(btnFornecedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 160, 40));

        btnCliente.setBackground(new java.awt.Color(51, 51, 51));
        btnCliente.setForeground(new java.awt.Color(255, 255, 255));
        btnCliente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/clientes.png"))); // NOI18N
        btnCliente.setText("Cliente                     ");
        btnCliente.setToolTipText("Cliente                     ");
        btnCliente.setContentAreaFilled(false);
        btnCliente.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        btnCliente.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCliente.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCliente.setOpaque(true);
        btnCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClienteActionPerformed(evt);
            }
        });
        panelMenuSide.add(btnCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 160, 40));

        btnActualizar.setBackground(new java.awt.Color(51, 51, 51));
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/actualizar stock.png"))); // NOI18N
        btnActualizar.setText("Actualizar Stock     ");
        btnActualizar.setToolTipText("Actualizar Stock     ");
        btnActualizar.setContentAreaFilled(false);
        btnActualizar.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        btnActualizar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnActualizar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnActualizar.setOpaque(true);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        panelMenuSide.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 160, 40));

        btnEncomenda.setBackground(new java.awt.Color(51, 51, 51));
        btnEncomenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/shopping_cart_loaded_26px.png"))); // NOI18N
        btnEncomenda.setText("Encomenda ");
        btnEncomenda.setToolTipText(" Encomenda            ");
        btnEncomenda.setContentAreaFilled(false);
        btnEncomenda.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        btnEncomenda.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEncomenda.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEncomenda.setOpaque(true);
        btnEncomenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEncomendaActionPerformed(evt);
            }
        });
        panelMenuSide.add(btnEncomenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, 160, 40));

        btnEntrada.setBackground(new java.awt.Color(51, 51, 51));
        btnEntrada.setForeground(new java.awt.Color(255, 255, 255));
        btnEntrada.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/entrada de s.png"))); // NOI18N
        btnEntrada.setText(" Entrada de Produto");
        btnEntrada.setToolTipText("Entrada de Produto");
        btnEntrada.setContentAreaFilled(false);
        btnEntrada.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        btnEntrada.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnEntrada.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnEntrada.setOpaque(true);
        btnEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntradaActionPerformed(evt);
            }
        });
        panelMenuSide.add(btnEntrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 160, 40));

        btnListaMovimento.setBackground(new java.awt.Color(51, 51, 51));
        btnListaMovimento.setForeground(new java.awt.Color(255, 255, 255));
        btnListaMovimento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/movimntos.png"))); // NOI18N
        btnListaMovimento.setText("Lista de Movimento");
        btnListaMovimento.setToolTipText("Lista de Movimento");
        btnListaMovimento.setContentAreaFilled(false);
        btnListaMovimento.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        btnListaMovimento.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnListaMovimento.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnListaMovimento.setOpaque(true);
        btnListaMovimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListaMovimentoActionPerformed(evt);
            }
        });
        panelMenuSide.add(btnListaMovimento, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, 160, 40));

        btnFacturacao.setBackground(new java.awt.Color(51, 51, 51));
        btnFacturacao.setForeground(new java.awt.Color(255, 255, 255));
        btnFacturacao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/printer.png"))); // NOI18N
        btnFacturacao.setText("Facturação      ");
        btnFacturacao.setToolTipText("Facturação      ");
        btnFacturacao.setContentAreaFilled(false);
        btnFacturacao.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        btnFacturacao.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnFacturacao.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnFacturacao.setOpaque(true);
        btnFacturacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFacturacaoActionPerformed(evt);
            }
        });
        panelMenuSide.add(btnFacturacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 380, 160, 40));

        btnTransferencia.setBackground(new java.awt.Color(51, 51, 51));
        btnTransferencia.setForeground(new java.awt.Color(255, 255, 255));
        btnTransferencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/tranfererir.png"))); // NOI18N
        btnTransferencia.setText("Transferência");
        btnTransferencia.setToolTipText("Transferência");
        btnTransferencia.setContentAreaFilled(false);
        btnTransferencia.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        btnTransferencia.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTransferencia.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTransferencia.setOpaque(true);
        btnTransferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransferenciaActionPerformed(evt);
            }
        });
        panelMenuSide.add(btnTransferencia, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 160, 40));

        btnCadastrarProduto.setBackground(new java.awt.Color(51, 51, 51));
        btnCadastrarProduto.setForeground(new java.awt.Color(255, 255, 255));
        btnCadastrarProduto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/shopping_cart_loaded_26px.png"))); // NOI18N
        btnCadastrarProduto.setText("Cadast. de Produto");
        btnCadastrarProduto.setToolTipText("Lista de Saida de Produto");
        btnCadastrarProduto.setContentAreaFilled(false);
        btnCadastrarProduto.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        btnCadastrarProduto.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnCadastrarProduto.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnCadastrarProduto.setOpaque(true);
        btnCadastrarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarProdutoActionPerformed(evt);
            }
        });
        panelMenuSide.add(btnCadastrarProduto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 160, 40));

        btnSair.setBackground(new java.awt.Color(51, 51, 51));
        btnSair.setForeground(new java.awt.Color(255, 255, 255));
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/shutdown_filled_26px.png"))); // NOI18N
        btnSair.setText("Sair                  ");
        btnSair.setContentAreaFilled(false);
        btnSair.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        btnSair.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSair.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnSair.setOpaque(true);
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });
        panelMenuSide.add(btnSair, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 160, 40));

        btnTrocar.setBackground(new java.awt.Color(51, 51, 51));
        btnTrocar.setForeground(new java.awt.Color(255, 255, 255));
        btnTrocar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/toca.png"))); // NOI18N
        btnTrocar.setText("Troca de Usuário");
        btnTrocar.setToolTipText("Troca de Usuário");
        btnTrocar.setContentAreaFilled(false);
        btnTrocar.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        btnTrocar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTrocar.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        btnTrocar.setOpaque(true);
        btnTrocar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrocarActionPerformed(evt);
            }
        });
        panelMenuSide.add(btnTrocar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, 160, 40));

        desktop.add(panelMenuSide);
        panelMenuSide.setBounds(-190, 0, 190, 581);

        jLabel5.setBackground(new java.awt.Color(51, 51, 51));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/hjhjhn.jpg"))); // NOI18N
        jLabel5.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        jLabel5.setMinimumSize(new java.awt.Dimension(1400, 800));
        jLabel5.setOpaque(true);
        jLabel5.setPreferredSize(new java.awt.Dimension(1400, 800));
        desktop.add(jLabel5);
        jLabel5.setBounds(0, 0, 1340, 920);
        jLabel5.getAccessibleContext().setAccessibleParent(jLabel5);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1364, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1035, Short.MAX_VALUE)
        );

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/nnn.png"))); // NOI18N
        jMenu1.setText("Ficheiro");

        Produto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        Produto.setText("Produto");
        Produto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProdutoActionPerformed(evt);
            }
        });
        jMenu1.add(Produto);

        Fornecedor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        Fornecedor.setText("Fornecedor");
        Fornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FornecedorActionPerformed(evt);
            }
        });
        jMenu1.add(Fornecedor);

        cliente.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        cliente.setText("Cliente");
        cliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clienteActionPerformed(evt);
            }
        });
        jMenu1.add(cliente);

        entradaStock.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        entradaStock.setText("Entrada de Produto");
        entradaStock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entradaStockActionPerformed(evt);
            }
        });
        jMenu1.add(entradaStock);

        jMenuItem12.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem12.setText("Terminar Sessão");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem12);

        jMenuItem13.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem13.setText("Sair do Sistema");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem13);

        jMenuBar1.add(jMenu1);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/circ.png"))); // NOI18N
        jMenu2.setText("Operação");

        menuTransferencia.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.SHIFT_MASK));
        menuTransferencia.setText("Transferência de Produto");
        menuTransferencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuTransferenciaActionPerformed(evt);
            }
        });
        jMenu2.add(menuTransferencia);

        fechoCaixas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.SHIFT_MASK));
        fechoCaixas.setText("Fecho de Caixas");
        fechoCaixas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fechoCaixasActionPerformed(evt);
            }
        });
        jMenu2.add(fechoCaixas);

        jMenu7.setText("Pagamento de Divida");

        pagamentoFornecedor.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.SHIFT_MASK));
        pagamentoFornecedor.setText("Fornecedor");
        pagamentoFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagamentoFornecedorActionPerformed(evt);
            }
        });
        jMenu7.add(pagamentoFornecedor);

        jMenu2.add(jMenu7);

        jMenu11.setText("Encomenda");
        jMenu11.setEnabled(false);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem9.setText("Fornecedor");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem9);

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem10.setText("Cliente");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu11.add(jMenuItem10);

        jMenu2.add(jMenu11);

        menuActualizacao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK));
        menuActualizacao.setText("Actualização de Stock");
        menuActualizacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuActualizacaoActionPerformed(evt);
            }
        });
        jMenu2.add(menuActualizacao);

        alterarDataExpiracao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK));
        alterarDataExpiracao.setText("Alterar data de Expiração");
        alterarDataExpiracao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alterarDataExpiracaoActionPerformed(evt);
            }
        });
        jMenu2.add(alterarDataExpiracao);

        txtAlterarPrecoVenda.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.SHIFT_MASK));
        txtAlterarPrecoVenda.setText("Alterar preço de venda");
        txtAlterarPrecoVenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAlterarPrecoVendaActionPerformed(evt);
            }
        });
        jMenu2.add(txtAlterarPrecoVenda);

        alterarCodBarra.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.SHIFT_MASK));
        alterarCodBarra.setText("Alterar Código de Barra");
        alterarCodBarra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alterarCodBarraActionPerformed(evt);
            }
        });
        jMenu2.add(alterarCodBarra);

        devolucao.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.SHIFT_MASK));
        devolucao.setText("Devolução");
        devolucao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                devolucaoActionPerformed(evt);
            }
        });
        jMenu2.add(devolucao);

        jMenu17.setText("Documentos Rectificativo");

        jMenuItem43.setText("Nota de Crédito");
        jMenuItem43.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem43ActionPerformed(evt);
            }
        });
        jMenu17.add(jMenuItem43);

        jMenuItem44.setText("Nota debito");
        jMenuItem44.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem44ActionPerformed(evt);
            }
        });
        jMenu17.add(jMenuItem44);

        jMenuItem45.setText("Anulação Documento");
        jMenuItem45.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem45ActionPerformed(evt);
            }
        });
        jMenu17.add(jMenuItem45);

        jMenuItem39.setText("Liquidar Divida");
        jMenuItem39.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem39ActionPerformed(evt);
            }
        });
        jMenu17.add(jMenuItem39);

        jMenu2.add(jMenu17);

        jMenuBar1.add(jMenu2);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/tabelas.png"))); // NOI18N
        jMenu3.setText("Tabela");

        Categoria.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        Categoria.setText("Categoria");
        Categoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CategoriaActionPerformed(evt);
            }
        });
        jMenu3.add(Categoria);

        fabricante1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK));
        fabricante1.setText("Cambio");
        fabricante1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fabricante1ActionPerformed(evt);
            }
        });
        jMenu3.add(fabricante1);

        MenuArmazem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        MenuArmazem.setText("Armazém");
        MenuArmazem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuArmazemActionPerformed(evt);
            }
        });
        jMenu3.add(MenuArmazem);

        MenuTabelaFormaPagamento.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        MenuTabelaFormaPagamento.setText("Forma de Pagamento");
        MenuTabelaFormaPagamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuTabelaFormaPagamentoActionPerformed(evt);
            }
        });
        jMenu3.add(MenuTabelaFormaPagamento);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem5.setText("Forma de Impressão");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem5);

        menuIpc.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.ALT_MASK));
        menuIpc.setText("IPC");
        menuIpc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuIpcActionPerformed(evt);
            }
        });
        jMenu3.add(menuIpc);

        fabricante.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK));
        fabricante.setText("Fabricante");
        fabricante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fabricanteActionPerformed(evt);
            }
        });
        jMenu3.add(fabricante);

        itemAgruparEntradas.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK));
        itemAgruparEntradas.setText("Agupar Entradas nas Vendas");
        itemAgruparEntradas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAgruparEntradasActionPerformed(evt);
            }
        });
        jMenu3.add(itemAgruparEntradas);

        itemAdicionarQtdProduto.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK));
        itemAdicionarQtdProduto.setText("Adicionar QTD Pela Tela de Produto");
        itemAdicionarQtdProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAdicionarQtdProdutoActionPerformed(evt);
            }
        });
        jMenu3.add(itemAdicionarQtdProduto);

        itemPermitirFechoCaixa.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_MASK));
        itemPermitirFechoCaixa.setText("Permitir Fecho de Caixa ao Operador");
        itemPermitirFechoCaixa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPermitirFechoCaixaActionPerformed(evt);
            }
        });
        jMenu3.add(itemPermitirFechoCaixa);

        jMenu16.setText("IVA");

        jMenuItem34.setText("Motivo de Isenção");
        jMenuItem34.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem34ActionPerformed(evt);
            }
        });
        jMenu16.add(jMenuItem34);

        jMenuItem35.setText("Taxa");
        jMenuItem35.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem35ActionPerformed(evt);
            }
        });
        jMenu16.add(jMenuItem35);

        jMenuItem36.setText("Saft");
        jMenuItem36.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem36ActionPerformed(evt);
            }
        });
        jMenu16.add(jMenuItem36);

        jMenu3.add(jMenu16);

        menuMesa.setText("Mesa");
        menuMesa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuMesaActionPerformed(evt);
            }
        });
        jMenu3.add(menuMesa);

        jMenuBar1.add(jMenu3);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/pdf.png"))); // NOI18N
        jMenu4.setText("Relatório");

        jMenu8.setText("Cliente");
        jMenu8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu8ActionPerformed(evt);
            }
        });

        jMenu9.setText("Factura");

        jMenuItem3.setText("Pagas");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem3);

        jMenuItem4.setText("Dividas");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem4);

        jMenuItem6.setText("Devolvidas");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem6);

        jMenuItem1.setText("Pagas Por Cliente");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu9.add(jMenuItem1);

        jMenu8.add(jMenu9);

        jMenuItem11.setText("Encomenda");
        jMenuItem11.setEnabled(false);
        jMenu8.add(jMenuItem11);

        todosClientes.setText("Todos");
        todosClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todosClientesActionPerformed(evt);
            }
        });
        jMenu8.add(todosClientes);

        jMenu4.add(jMenu8);

        jMenu10.setText("Fornecedor");
        jMenu10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu10ActionPerformed(evt);
            }
        });

        jMenuItem7.setText("Encomenda");
        jMenuItem7.setEnabled(false);
        jMenu10.add(jMenuItem7);

        todosFornecedor.setText("Todos");
        todosFornecedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todosFornecedorActionPerformed(evt);
            }
        });
        jMenu10.add(todosFornecedor);

        jMenuItem29.setText("Entrada");
        jMenuItem29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem29ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem29);

        jMenuItem2.setText("Entrada Por Fonecedor");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu10.add(jMenuItem2);

        jMenu4.add(jMenu10);

        jMenu12.setText("Produto");
        jMenu12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu12ActionPerformed(evt);
            }
        });

        jMenuItem16.setText("Stock");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem16);

        jMenuItem21.setText("Por Armazem");
        jMenuItem21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem21ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem21);

        jMenuItem22.setText("Stock em Baixa");
        jMenuItem22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem22ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem22);

        jMenuItem8.setText("Etiqueta");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu12.add(jMenuItem8);

        itemExpirado.setText("Expirado");
        itemExpirado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemExpiradoActionPerformed(evt);
            }
        });
        jMenu12.add(itemExpirado);

        itemPresteExpirar.setText("Preste a Expirar");
        itemPresteExpirar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPresteExpirarActionPerformed(evt);
            }
        });
        jMenu12.add(itemPresteExpirar);

        itemPresteTerminar.setText("Preste a Terminar");
        itemPresteTerminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPresteTerminarActionPerformed(evt);
            }
        });
        jMenu12.add(itemPresteTerminar);

        jMenu4.add(jMenu12);

        jMenuItem23.setText("Categoria");
        jMenuItem23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem23ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem23);

        jMenuItem24.setText("Armazém");
        jMenuItem24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem24ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem24);

        jMenuItem25.setText("Fabricante");
        jMenuItem25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem25ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem25);

        jMenuItem30.setText("Lista de Movimento");
        jMenuItem30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem30ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem30);

        jMenuItem41.setText("Caixa");
        jMenuItem41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem41ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem41);

        jMenuItem46.setText("Entrada e Stock");
        jMenuItem46.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem46ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem46);

        jMenuItem32.setText("Nota de Entrega de Encomenda");
        jMenuItem32.setEnabled(false);
        jMenu4.add(jMenuItem32);

        jMenuItem42.setText("Documentos Retificativos");
        jMenuItem42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem42ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem42);

        jMenuItem40.setText("Lista de Custos");
        jMenuItem40.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem40ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem40);

        jMenuBar1.add(jMenu4);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/graf.png"))); // NOI18N
        jMenu5.setText("Gráfico");

        jMenu14.setText("Stock");

        jMenuItem14.setText("Código de Barra");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem14);

        jMenuItem17.setText("Produto");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem17);

        jMenuItem18.setText("Categoria");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem18);

        jMenuItem19.setText("Fornecedor");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem19);

        jMenuItem20.setText("Fabricante");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem20);

        jMenuItem26.setText("Produto detalhado");
        jMenuItem26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem26ActionPerformed(evt);
            }
        });
        jMenu14.add(jMenuItem26);

        jMenu5.add(jMenu14);

        jMenu15.setText("Venda");

        jMenuItem27.setText("Produto");
        jMenuItem27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem27ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem27);

        jMenuItem28.setText("Cliente");
        jMenuItem28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem28ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem28);

        jMenuItem31.setText("Balanço Anual");
        jMenuItem31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem31ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem31);

        jMenuItem33.setText("Balanço Mensal");
        jMenuItem33.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem33ActionPerformed(evt);
            }
        });
        jMenu15.add(jMenuItem33);

        jMenu5.add(jMenu15);

        jMenuBar1.add(jMenu5);

        jMenu13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/saturation_filled_24px.png"))); // NOI18N
        jMenu13.setText("Sistema");

        itemUsuario.setText("Usuário");
        itemUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemUsuarioActionPerformed(evt);
            }
        });
        jMenu13.add(itemUsuario);

        permissao.setText("Permissão");
        permissao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                permissaoActionPerformed(evt);
            }
        });
        jMenu13.add(permissao);

        empresa.setText("Empresa");
        empresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                empresaActionPerformed(evt);
            }
        });
        jMenu13.add(empresa);

        jMenuItem15.setText("Lista de Usuario");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu13.add(jMenuItem15);

        menuBackup.setText("Backup do Sistema");
        menuBackup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBackupActionPerformed(evt);
            }
        });
        jMenu13.add(menuBackup);

        menuBackup1.setText("Log de Acesso");
        menuBackup1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuBackup1ActionPerformed(evt);
            }
        });
        jMenu13.add(menuBackup1);

        Importacao.setText("Importar Ficheiros");
        Importacao.setEnabled(false);
        Importacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ImportacaoActionPerformed(evt);
            }
        });
        jMenu13.add(Importacao);

        jMenuBar1.add(jMenu13);

        jMenu6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imag 2/help.png"))); // NOI18N
        jMenu6.setText("Ajuda");

        jMenuItem37.setText("Manual do SIstema");
        jMenuItem37.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem37ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem37);

        jMenuItem38.setText("Empresa");
        jMenuItem38.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem38ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem38);

        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop, javax.swing.GroupLayout.DEFAULT_SIZE, 1364, Short.MAX_VALUE)
            .addComponent(panelBarSide, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(panelBarSide, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(desktop, javax.swing.GroupLayout.DEFAULT_SIZE, 991, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 12, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProdutoActionPerformed
        // TODO add your handling code here:
        new ProdutoView(usuario).setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_ProdutoActionPerformed

    private void FornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FornecedorActionPerformed
        // TODO add your handling code here:
        new FornecedorView(usuario).setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_FornecedorActionPerformed

    private void clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clienteActionPerformed
        // TODO add your handling code here:
        new ClienteView(usuario).setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_clienteActionPerformed

    private void entradaStockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entradaStockActionPerformed
        // TODO add your handling code here:
        new EntradaStockView(usuario).setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_entradaStockActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        Frame[] frames = getFrames();
        boolean telaBalcaoAberto = false;
        for (int i = 0; i < frames.length; i++) {

            if (!frames[i].getName().equals("BalcaoView")) {
                frames[i].dispose();
            } else {
                telaBalcaoAberto = frames[i].isVisible();
            }
        }

        if (!telaBalcaoAberto) {
            new LoginView().setVisible(true);
        }
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:
        Object[] opcao = {"Sim", "Não"};
        if (JOptionPane.showOptionDialog(null, " Deseja sair do sistema?", " Alerta ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcao, opcao[0]) == JOptionPane.YES_OPTION) {

            backup();
            System.exit(0);
        }
        LogUtil.log.salvarLog(TipoLog.LOGOUT, " Fechar o Sistema ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void menuTransferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuTransferenciaActionPerformed
        // TODO add your handling code here:
        new TransferenciaProdutoView(usuario).setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_menuTransferenciaActionPerformed

    private void pagamentoFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagamentoFornecedorActionPerformed
        // TODO add your handling code here:
        new PagarDividaFornecedorView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_pagamentoFornecedorActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void menuActualizacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuActualizacaoActionPerformed
        // TODO add your handling code here:
        new ActualizacaoStockView(usuario).setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_menuActualizacaoActionPerformed

    private void alterarDataExpiracaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alterarDataExpiracaoActionPerformed
        // TODO add your handling code here:
        new AlterarDataExpiracao().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_alterarDataExpiracaoActionPerformed

    private void txtAlterarPrecoVendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAlterarPrecoVendaActionPerformed
        // TODO add your handling code here:
        new AlterarPrecoVenda().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_txtAlterarPrecoVendaActionPerformed

    private void alterarCodBarraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alterarCodBarraActionPerformed
        // TODO add your handling code here:
        new AlterarCodBarraView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_alterarCodBarraActionPerformed

    private void devolucaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_devolucaoActionPerformed
        // TODO add your handling code here:
        new DevolucaoView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_devolucaoActionPerformed

    private void jMenuItem43ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem43ActionPerformed
        // TODO add your handling code here:
        new NotaCredito(usuario).setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem43ActionPerformed

    private void jMenuItem44ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem44ActionPerformed
        // TODO add your handling code here:
        new NotaDebito(usuario).setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem44ActionPerformed

    private void jMenuItem45ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem45ActionPerformed
        // TODO add your handling code here:
        new AnularNotaView(usuario).setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem45ActionPerformed

    private void jMenuItem39ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem39ActionPerformed
        // TODO add your handling code here:
        new LiquidarFacturaView(usuario).setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem39ActionPerformed

    private void CategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CategoriaActionPerformed
        // TODO add your handling code here:
        new CategoriaView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_CategoriaActionPerformed

    private void fabricante1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fabricante1ActionPerformed
        // TODO add your handling code here:

        new CambioView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_fabricante1ActionPerformed

    private void MenuArmazemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuArmazemActionPerformed
        // TODO add your handling code here:
        new ArmazemView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_MenuArmazemActionPerformed

    private void MenuTabelaFormaPagamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuTabelaFormaPagamentoActionPerformed

        new FormaPagamentoView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_MenuTabelaFormaPagamentoActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO add your handling code here:
        Object[] opcao={"Sim","Não"};
        int op = JOptionPane.showOptionDialog(this, "Deseja Imprimir direito?", "Aviso",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opcao,opcao[0]);
        if (op == JOptionPane.YES_OPTION) {

            ParamentroModel modelo = new ParamentroModel(1, "IMPRIMIR DIREITO", 1);
            ParamentroController controller = new ParamentroController();
            controller.update(modelo);

        } else if (op == JOptionPane.NO_OPTION) {

            ParamentroModel modelo = new ParamentroModel(1, "IMPRIMIR MOSTRAR ASSISTENTE", 0);
            ParamentroController controller = new ParamentroController();
            controller.update(modelo);
        }
        LogUtil.log.salvarLog(TipoLog.INFO, " Alterou ( "+evt.getActionCommand()+" ( "+opcao[op].toString() +" ) )");
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void menuIpcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuIpcActionPerformed
        // TODO add your handling code here:
        new IPCView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_menuIpcActionPerformed

    private void fabricanteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fabricanteActionPerformed
        // TODO add your handling code here:
        new FabricanteView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_fabricanteActionPerformed

    private void jMenuItem34ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem34ActionPerformed
        // TODO add your handling code here:
        new MotivoView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem34ActionPerformed

    private void jMenuItem35ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem35ActionPerformed
        // TODO add your handling code here:
        new TaxaView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem35ActionPerformed

    private void jMenuItem36ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem36ActionPerformed
        // TODO add your handling code here:
        new SaftView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem36ActionPerformed

    private void menuMesaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuMesaActionPerformed
        // TODO add your handling code here:
        new MesaView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_menuMesaActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        FacturaIreport.pagasTodas();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Facturas de Cliente ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        ClienteIreport.Devedor();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Facturas de Cliente com ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        new ListaPagasClienteView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Factura de Cliente ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void todosClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_todosClientesActionPerformed
        // TODO add your handling code here:
        ClienteIreport.todos();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Factura de Cliente ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_todosClientesActionPerformed

    private void todosFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_todosFornecedorActionPerformed
        // TODO add your handling code here:
        FornecedorIreport.todos();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Fornecedor ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_todosFornecedorActionPerformed

    private void jMenuItem29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem29ActionPerformed
        // TODO add your handling code here:
        FornecedorIreport.entradaPorFornecedor();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Fornecedor ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem29ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        new ListaEntradaFornecedorView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        // TODO add your handling code here:
        Object[] opcao = {"PDF", "EXCEL"};
        int opc = JOptionPane.showOptionDialog(null, " Como quer Visualizar?", " Escolha ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcao, opcao[0]);
        if (opc == JOptionPane.YES_OPTION) {
            
            StockIreport.todos();
        }else{
            if(opc == 1)
                StockIreport.todosExcel();
        }
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Produto ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem21ActionPerformed
        // TODO add your handling code here:
        new ListaPordutoArmazemView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Produto ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem21ActionPerformed

    private void jMenuItem22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem22ActionPerformed
        // TODO add your handling code here:
        StockIreport.stctokEmBaixa();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Produto ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem22ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        new EtiquetaView(usuario).setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Produto ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void itemExpiradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemExpiradoActionPerformed
        // TODO add your handling code here:
        StockIreport.expirado();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Produto ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_itemExpiradoActionPerformed

    private void jMenuItem23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem23ActionPerformed
        // TODO add your handling code here:
        CategoriaIreport.todos();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem23ActionPerformed

    private void jMenuItem24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem24ActionPerformed
        // TODO add your handling code here:
        ArmazemIreport.todos();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem24ActionPerformed

    private void jMenuItem25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem25ActionPerformed
        // TODO add your handling code here:
        FabricanteIreport.todos();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem25ActionPerformed

    private void jMenuItem30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem30ActionPerformed
        // TODO add your handling code here:
        new FacturacaoView(usuario).setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de Listagem de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem30ActionPerformed

    private void jMenuItem42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem42ActionPerformed
        // TODO add your handling code here:
        new ListaMovimentoView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de Listagem de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem42ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        StockIreport.graficoBarraExistencia();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Gráfico de Stock Por ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
        StockIreport.graficoBarraProduto();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Gráfico de Stock Por ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
        StockIreport.graficoCategoriaExistenciaPIE();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Gráfico de Stock Por ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        // TODO add your handling code here:
        StockIreport.graficoFornecedorExistenciaPIE();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Gráfico de Stock Por  ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        // TODO add your handling code here:
        StockIreport.graficoFabricanteExistenciaPIE();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Gráfico de Stock Por ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void jMenuItem26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem26ActionPerformed
        // TODO add your handling code here:
        StockIreport.grafiProdutoPIEExistencia();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Gráfico de Stock Por ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem26ActionPerformed

    private void jMenuItem27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem27ActionPerformed
        // TODO add your handling code here:
        FacturaIreport.graficoVendaProduto();
    }//GEN-LAST:event_jMenuItem27ActionPerformed

    private void jMenuItem28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem28ActionPerformed
        // TODO add your handling code here:
        FacturaIreport.graficoVendaCliente();
    }//GEN-LAST:event_jMenuItem28ActionPerformed

    private void jMenuItem31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem31ActionPerformed
        // TODO add your handling code here:

        new BalancoAnual().setVisible(true);
    }//GEN-LAST:event_jMenuItem31ActionPerformed

    private void jMenuItem33ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem33ActionPerformed
        // TODO add your handling code here:
        new BalancoMensal().setVisible(true);
    }//GEN-LAST:event_jMenuItem33ActionPerformed

    private void itemUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemUsuarioActionPerformed
        // TODO add your handling code here:
        new UsuarioView(usuario).setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_itemUsuarioActionPerformed

    private void permissaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_permissaoActionPerformed
        // TODO add your handling code here:
        new PermissaView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_permissaoActionPerformed

    private void empresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_empresaActionPerformed
        // TODO add your handling code here:
        new EmpresaView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_empresaActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
        UsuarioIreport.todos();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void menuBackupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBackupActionPerformed

        CopySecury.backupJFrame();
        LogUtil.log.salvarLog(TipoLog.INFO, " Fez ( "+evt.getActionCommand()+" )");

    }//GEN-LAST:event_menuBackupActionPerformed

    private void jMenuItem38ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem38ActionPerformed
        // TODO add your handling code here:
        new InformacaoEmpresa().setVisible(true);
    }//GEN-LAST:event_jMenuItem38ActionPerformed

    private void btnSair1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSair1ActionPerformed
        // TODO add your handling code here:
        //
        if (!activo) {
            new Thread(new Runnable() {
                int i;

                @Override
                public void run() {
                    while ((i = panelMenuSide.getLocation().x) <= 0) {
                        int y = panelMenuSide.getLocation().y;
                        i += 10;
                        try {
                            Thread.currentThread().sleep(35);
                            panelMenuSide.setLocation(i, y);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("primeiro");
                    activo = !activo;
                }
            }
            ).start();
        } else {
            new Thread(new Runnable() {
                int i;

                @Override
                public void run() {
                    while ((i = panelMenuSide.getLocation().x) >= -200) {
                        int y = panelMenuSide.getLocation().y;
                        i -= 10;
                        try {
                            Thread.currentThread().sleep(35);
                            panelMenuSide.setLocation(i, y);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("segundo");
                    activo = !activo;
                }
            }
            ).start();

        }
    }//GEN-LAST:event_btnSair1ActionPerformed

    private void btnFornecedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFornecedorActionPerformed
        // TODO add your handling code here:
        new FornecedorView(usuario).setVisible(true);
    }//GEN-LAST:event_btnFornecedorActionPerformed

    private void btnClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClienteActionPerformed
        // TODO add your handling code here:
        new ClienteView(usuario).setVisible(true);
    }//GEN-LAST:event_btnClienteActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        new ActualizacaoStockView(usuario).setVisible(true);
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEncomendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEncomendaActionPerformed
        // TODO add your handling code here:
        //        new Encomenda(usuario).setVisible(true);
        new BuscarListaSaidaProdutoView().setVisible(true);
    }//GEN-LAST:event_btnEncomendaActionPerformed

    private void btnEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntradaActionPerformed
        // TODO add your handling code here:
        new EntradaStockView(usuario).setVisible(true);
    }//GEN-LAST:event_btnEntradaActionPerformed

    private void btnListaMovimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListaMovimentoActionPerformed
        // TODO add your handling code here:
        new FacturacaoView(usuario).setVisible(true);
    }//GEN-LAST:event_btnListaMovimentoActionPerformed

    private void btnFacturacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFacturacaoActionPerformed
        // TODO add your handling code here:
        //        new FrontOfficePequeno(usuario).setVisible(true);
        if (DataConforme()) {

            Object[] opcao = {"Sim", "Não"};
            if (JOptionPane.showOptionDialog(null, " Deseja Manter Está tela Aberta?", " Alerta ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcao, opcao[0]) != JOptionPane.YES_OPTION) {
                this.hide();
            }

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
                      new PedidoMesaVIew(usuario).setVisible(true);
//                    if (JOptionPane.showOptionDialog(null, "Estamos em um Novo Dia, Existem Caixa Aberto em:" + caixaModel.getDataAbertura()
//                            + "\nDeseja Abrir Novo caixa?", " Alerta ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcao, opcao[0]) == JOptionPane.YES_OPTION) {
//                        dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        caixaModel.setDataFecho(dateFormat1.format(new Date()));
//                        caixaModel.setEstado("Fechado");
//
//                        if (caixaController.saveOrUpdate(caixaModel)) {
//
//                            FacturaIreport.fechoCaixa(caixaModel);
//                            new CaixaView(usuario).setVisible(true);
//                        }
//                    } else {
//                        new PedidoMesaVIew(usuario).setVisible(true);
//                    }

                } else {
                    new CaixaView(usuario).setVisible(true);
                }

            }

        } else {
            JOptionPane.showMessageDialog(this, "Acerte a data do seu Computador");
        }
    }//GEN-LAST:event_btnFacturacaoActionPerformed

    private void btnTransferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTransferenciaActionPerformed
        // TODO add your handling code here:
        new TransferenciaProdutoView(usuario).setVisible(true);
    }//GEN-LAST:event_btnTransferenciaActionPerformed

    private void btnCadastrarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarProdutoActionPerformed
        // TODO add your handling code here:
        //        new BuscarListaSaidaProdutoView().setVisible(true);
        new ProdutoView(usuario).setVisible(true);
    }//GEN-LAST:event_btnCadastrarProdutoActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // TODO add your handling code here:

        Object[] opcao = {"Sim", "Não"};
        if (JOptionPane.showOptionDialog(null, " Deseja sair do sistema?", " Alerta ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcao, opcao[0]) == JOptionPane.YES_OPTION) {

            backup();
            System.exit(0);
        }
    }//GEN-LAST:event_btnSairActionPerformed

    private void btnTrocarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrocarActionPerformed
        // TODO add your handling code here:

        Frame[] frames = getFrames();
        boolean telaBalcaoAberto = false;

        for (int i = 0; i < frames.length; i++) {

            if (!frames[i].getName().equals("BalcaoView")) {
                frames[i].dispose();
            } else {
                telaBalcaoAberto = frames[i].isVisible();
            }
        }

        if (!telaBalcaoAberto) {
            new LoginView().setVisible(true);
        }
    }//GEN-LAST:event_btnTrocarActionPerformed

    private void jMenuItem41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem41ActionPerformed
        // TODO add your handling code here:
        new MovimentoCaixaView(usuario).setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de Listagem de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem41ActionPerformed

    private void jMenuItem46ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem46ActionPerformed
        // TODO add your handling code here:
        new MovimentoEntradaView(usuario).setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de Listagem de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem46ActionPerformed

    private void jMenuItem40ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem40ActionPerformed
        // TODO add your handling code here:
        new ListaDespesasView().setVisible(true);
        LogUtil.log.salvarLog(TipoLog.INFO, " Abrir tela de Listagem de ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem40ActionPerformed

    private void ImportacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ImportacaoActionPerformed
        // TODO add your handling code here:
        //new ImportarFicheirosView(usuario).setVisible(true);
    }//GEN-LAST:event_ImportacaoActionPerformed

    private void itemPresteExpirarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPresteExpirarActionPerformed
        // TODO add your handling code here:
        StockIreport.presteExpirar();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Produto ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_itemPresteExpirarActionPerformed

    private void itemPresteTerminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPresteTerminarActionPerformed
        // TODO add your handling code here:
        StockIreport.presteExpirar();
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Produto ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_itemPresteTerminarActionPerformed

    private void itemAgruparEntradasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAgruparEntradasActionPerformed
        // TODO add your handling code here:
        Object[] opcao={"Sim","Não"};
        int op = JOptionPane.showOptionDialog(this, "Deseja Agrupar Entradas na Tela de Facturação?", "Aviso",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opcao,opcao[0]);
        
        ParamentroModel modelo;
        ParamentroController controller = new ParamentroController();
        
        if (op == JOptionPane.YES_OPTION) {

            modelo = controller.getById(14);
            modelo.setValor(1);
            controller.update(modelo);

        } else if (op == JOptionPane.NO_OPTION) {

            modelo = controller.getById(14);
            modelo.setValor(2);
            controller.update(modelo);
        }
        LogUtil.log.salvarLog(TipoLog.INFO, " Alterou ( "+evt.getActionCommand()+" ("+opcao[op].toString()+") )");
    }//GEN-LAST:event_itemAgruparEntradasActionPerformed

    private void itemAdicionarQtdProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAdicionarQtdProdutoActionPerformed
        // TODO add your handling code here:
        Object[] opcao={"Sim","Não"};
        int op = JOptionPane.showOptionDialog(this, "Deseja Adicionar QTD Pela Tela de Produto?", "Aviso",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opcao,opcao[0]);
        
        ParamentroModel modelo;
        ParamentroController controller = new ParamentroController();
        
        if (op == JOptionPane.YES_OPTION) {

            modelo = controller.getById(8);
            modelo.setValor(1);
            controller.update(modelo);

        } else if (op == JOptionPane.NO_OPTION) {

            modelo = controller.getById(8);
            modelo.setValor(2);
            controller.update(modelo);
        }
        LogUtil.log.salvarLog(TipoLog.INFO, " Alterou ( "+evt.getActionCommand()+"( "+opcao[op].toString()+" ) )");
    }//GEN-LAST:event_itemAdicionarQtdProdutoActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        LogUtil.log.salvarLog(TipoLog.INFO, " Listou Facturas de Clientes ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenu8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu8ActionPerformed
        // TODO add your handling code here:
        //LogUtil.log.salvarLog(TipoLog.INFO, " Abriu Menu ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenu8ActionPerformed

    private void jMenu10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu10ActionPerformed
        // TODO add your handling code here:
        //LogUtil.log.salvarLog(TipoLog.INFO, " Abriu Menu ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenu10ActionPerformed

    private void jMenu12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu12ActionPerformed
        // TODO add your handling code here:
       // LogUtil.log.salvarLog(TipoLog.INFO, " Abriu Menu ( "+evt.getActionCommand()+" )");
    }//GEN-LAST:event_jMenu12ActionPerformed

    private void jMenuItem37ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem37ActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jMenuItem37ActionPerformed

    private void menuBackup1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuBackup1ActionPerformed
        // TODO add your handling code here:
        new LogAcessoView(usuario).setVisible(true);
    }//GEN-LAST:event_menuBackup1ActionPerformed

    private void fechoCaixasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fechoCaixasActionPerformed
        // TODO add your handling code here:
        new PermanenciaView(usuario).setVisible(true);
    }//GEN-LAST:event_fechoCaixasActionPerformed

    private void itemPermitirFechoCaixaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPermitirFechoCaixaActionPerformed
        // TODO add your handling code here:
        Object[] opcao={"Sim","Não"};
        int op = JOptionPane.showOptionDialog(this, "Permitir que os Operadores Fechem o Caixa?", "Aviso",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opcao,opcao[0]);
        
        ParamentroModel modelo;
        ParamentroController controller = new ParamentroController();
        
        if (op == JOptionPane.YES_OPTION) {

            modelo = controller.getById(10);
            modelo.setValor(1);
            controller.update(modelo);

        } else if (op == JOptionPane.NO_OPTION) {

            modelo = controller.getById(10);
            modelo.setValor(2);
            controller.update(modelo);
        }
        LogUtil.log.salvarLog(TipoLog.INFO, " Alterou ( "+evt.getActionCommand()+"( "+opcao[op].toString()+" ) )");
    }//GEN-LAST:event_itemPermitirFechoCaixaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Windowns".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MenuPrincipalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MenuPrincipalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MenuPrincipalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MenuPrincipalView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuPrincipalView().setVisible(true);

            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Categoria;
    private javax.swing.JMenuItem Fornecedor;
    private javax.swing.JMenuItem Importacao;
    private javax.swing.JMenuItem MenuArmazem;
    private javax.swing.JMenuItem MenuTabelaFormaPagamento;
    private javax.swing.JMenuItem Produto;
    private javax.swing.JMenuItem alterarCodBarra;
    private javax.swing.JMenuItem alterarDataExpiracao;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCadastrarProduto;
    private javax.swing.JButton btnCliente;
    private javax.swing.JButton btnEncomenda;
    private javax.swing.JButton btnEntrada;
    private javax.swing.JButton btnFacturacao;
    private javax.swing.JButton btnFornecedor;
    private javax.swing.JButton btnListaMovimento;
    private javax.swing.JButton btnSair;
    private javax.swing.JButton btnSair1;
    private javax.swing.JButton btnTransferencia;
    private javax.swing.JButton btnTrocar;
    private javax.swing.JMenuItem cliente;
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JMenuItem devolucao;
    private javax.swing.JMenuItem empresa;
    private javax.swing.JMenuItem entradaStock;
    private javax.swing.JMenuItem fabricante;
    private javax.swing.JMenuItem fabricante1;
    private javax.swing.JMenuItem fechoCaixas;
    private javax.swing.JMenuItem itemAdicionarQtdProduto;
    private javax.swing.JMenuItem itemAgruparEntradas;
    private javax.swing.JMenuItem itemExpirado;
    private javax.swing.JMenuItem itemPermitirFechoCaixa;
    private javax.swing.JMenuItem itemPresteExpirar;
    private javax.swing.JMenuItem itemPresteTerminar;
    private javax.swing.JMenuItem itemUsuario;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu10;
    private javax.swing.JMenu jMenu11;
    private javax.swing.JMenu jMenu12;
    private javax.swing.JMenu jMenu13;
    private javax.swing.JMenu jMenu14;
    private javax.swing.JMenu jMenu15;
    private javax.swing.JMenu jMenu16;
    private javax.swing.JMenu jMenu17;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem21;
    private javax.swing.JMenuItem jMenuItem22;
    private javax.swing.JMenuItem jMenuItem23;
    private javax.swing.JMenuItem jMenuItem24;
    private javax.swing.JMenuItem jMenuItem25;
    private javax.swing.JMenuItem jMenuItem26;
    private javax.swing.JMenuItem jMenuItem27;
    private javax.swing.JMenuItem jMenuItem28;
    private javax.swing.JMenuItem jMenuItem29;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem30;
    private javax.swing.JMenuItem jMenuItem31;
    private javax.swing.JMenuItem jMenuItem32;
    private javax.swing.JMenuItem jMenuItem33;
    private javax.swing.JMenuItem jMenuItem34;
    private javax.swing.JMenuItem jMenuItem35;
    private javax.swing.JMenuItem jMenuItem36;
    private javax.swing.JMenuItem jMenuItem37;
    private javax.swing.JMenuItem jMenuItem38;
    private javax.swing.JMenuItem jMenuItem39;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem40;
    private javax.swing.JMenuItem jMenuItem41;
    private javax.swing.JMenuItem jMenuItem42;
    private javax.swing.JMenuItem jMenuItem43;
    private javax.swing.JMenuItem jMenuItem44;
    private javax.swing.JMenuItem jMenuItem45;
    private javax.swing.JMenuItem jMenuItem46;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuItem menuActualizacao;
    private javax.swing.JMenuItem menuBackup;
    private javax.swing.JMenuItem menuBackup1;
    private javax.swing.JMenuItem menuIpc;
    private javax.swing.JMenuItem menuMesa;
    private javax.swing.JMenuItem menuTransferencia;
    private javax.swing.JMenuItem pagamentoFornecedor;
    private javax.swing.JPanel panelBarSide;
    private javax.swing.JPanel panelMenuSide;
    private javax.swing.JMenuItem permissao;
    private javax.swing.JMenuItem todosClientes;
    private javax.swing.JMenuItem todosFornecedor;
    private javax.swing.JMenuItem txtAlterarPrecoVenda;
    // End of variables declaration//GEN-END:variables

}
