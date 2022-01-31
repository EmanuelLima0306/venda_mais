/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.MesaController;
import Controller.ParamentroController;
import Controller.PedidoTesteController;
import Enum.TipoLog;
import Model.MesaModel;
import Model.ParamentroModel;
import Model.UsuarioModel;
import Util.LogUtil;
import View.Facturacao.ModeloView;
import java.awt.Color;
import java.awt.Frame;
import static java.awt.Frame.getFrames;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author celso & Emanuel
 */
public class PedidoMesaVIew extends javax.swing.JFrame {

    /**
     * Creates new form MesaView
     */
    private UsuarioModel usuario;
    private boolean permitirFecho;
    public static JTabbedPane tabPanel;
    private JFrame pediFrame;

    public PedidoMesaVIew() {
        initComponents();

        this.setTitle("SOFTWARE DE GESTAO COMERCIAL DESENVOLVIDO PELA ZETASOFT TECNOLOGIAS / Usuário: " + usuario.getNome());
        fecharTela();

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        init();
    }

    public PedidoMesaVIew(UsuarioModel usuario) {

        initComponents();

        //alterar configurações da tela
        this.setTitle("SOFTWARE DE GESTAO COMERCIAL DESENVOLVIDO PELA ZETASOFT TECNOLOGIAS / Usuário: " + usuario.getNome());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        fecharTela();

        //---------------------------
        init();
        this.usuario = usuario;
        this.moduloSistema();
        tabPanel = jTabbedPane1;
    }

    public void fecharTela() { // evento ao clicar no botão fechar tela (x) do topo

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {

                Object[] opcao = {"Sim", "Não"};
                if (JOptionPane.showOptionDialog(null, " Deseja sair?", " Alerta ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcao, opcao[0]) == JOptionPane.YES_OPTION) 
                {
                    Frame[] frames = getFrames();
                    boolean telaMenuPrincipalAberto = false;
                    for (int i = 0; i < frames.length; i++) {

                        if (frames[i].getName().equals("MenuPrincipalView")) {
                            telaMenuPrincipalAberto = frames[i].isVisible();
                        }
                    }

                    if (!telaMenuPrincipalAberto) {
                        eventoFecharTela();
                    } else {
                        hide();
                    }
                    LogUtil.log.salvarLog(TipoLog.LOGOUT, " Fechar o Sistema");
                }
            }
        });
    }

    public void eventoFecharTela() {
        new LoginView().setVisible(true);
        this.setVisible(false);
    }

    public PedidoMesaVIew(UsuarioModel usuario, boolean permitirFecho) {

        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        init();
        this.usuario = usuario;
        this.permitirFecho = permitirFecho;
        this.moduloSistema();
        fecharTela();

    }

    private void moduloSistema() {

        ParamentroController controller = new ParamentroController();
        ParamentroModel modelo = controller.getById(3); //MODULO DO SISTEMA  / 

        if (modelo.getValor() == 1) {//(1) - VENDA MAS

            jTabbedPane1.remove(0);
            if (permitirFecho) {
                panelBalcao.add(new BalcaoView(usuario, permitirFecho, this));
            } else {
                panelBalcao.add(new BalcaoView(usuario, this));
            }

        } else {
            if (modelo.getValor() == 2)//(2) - MREST
            {

            } else if (modelo.getValor() == 3)//(3) - Barbearia
            {
                jTabbedPane1.remove(0);
                if (permitirFecho) {
                    panelBalcao.add(new BalcaoBarbeariaView(usuario, permitirFecho));
                } else {
                    panelBalcao.add(new BalcaoBarbeariaView(usuario));
                }
            }
        }

    }

    private void processar(List<MesaModel> lista) {

        int col = 6;
        int row = lista.size() / col;
        panel_corpo.setLayout(new GridLayout(row, col));
        for (MesaModel mesa : lista) {
            createBtn(mesa);
        }
        //  scroll.add(panel);
    }

    public void init() {

        panel_corpo.removeAll();
        panel_corpo.updateUI();
        jTabbedPane1.setComponentAt(0, jScrollPane1);

        MesaController controller = new MesaController();
        List<MesaModel> lista = controller.getAll();
        processar(lista);
        pediFrame = this;
        jTabbedPane1.updateUI();
    }

    private boolean isPedido(MesaModel mesa) {

        PedidoTesteController controller = new PedidoTesteController();
        return controller.getLastId(mesa.getId()) > 0;
    }

    private void createBtn(MesaModel mesa) {

        JButton btn = new JButton();
        btn.setText(mesa.getDesignacao());
        btn.setToolTipText("Click na mesa para abrir mesa.Para poder adicionar os pedidos");
        btn.setEnabled(mesa.getEstado().getId() == 1);
        Icon bug = new ImageIcon(getClass().getResource("mesaAberta.png"));
        Color cor = Color.WHITE;
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tabPanel.setTitleAt(0, "Faturação da " + mesa);
                ParamentroController paramentroController = new ParamentroController();
                ParamentroModel paramentroModel = paramentroController.getById(12); // pega o tipo de tela a ser usada touch ou não
                if (permitirFecho) {
                    if (paramentroModel.getValor() == 1 && usuario.getTipoUsuario().getId() == 2) //tabPanel.setComponentAt(0,new BalcaoRestauranteTelaTouchView(usuario, mesa, permitirFecho, pediFrame));
                    {
                        tabPanel.setComponentAt(0, new ModeloView(usuario, mesa, permitirFecho, pediFrame));
                    } else {
                        tabPanel.setComponentAt(0, new BalcaoRestauranteView(usuario, mesa, permitirFecho, pediFrame));
                    }
                } else {
                    if (paramentroModel.getValor() == 1 && usuario.getTipoUsuario().getId() == 2) //                        tabPanel.setComponentAt(0,new BalcaoRestauranteTelaTouchView(usuario, mesa, pediFrame));
                    {
                        tabPanel.setComponentAt(0, new ModeloView(usuario, mesa, pediFrame));
                    } else {
                        tabPanel.setComponentAt(0, new BalcaoRestauranteView(usuario, mesa, pediFrame));
                    }
                }
                //                new FacturacaoNovaView(usuario, mesa).setVisible(true);
                //                dispose();
            }
        });

        if (isPedido(mesa)) {
            cor = new Color(30, 19, 0);
            btn.setForeground(Color.WHITE);
            bug = new ImageIcon(getClass().getResource("mesaAberta.png"));
        }

        btn.setBackground(cor);
        btn.setIcon(bug);
        panel_corpo.add(btn);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        panel_corpo = new javax.swing.JPanel();
        panelBalcao = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setName("BalcaoView"); // NOI18N

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jPanel1.setLayout(new java.awt.BorderLayout());

        panel.setBackground(new java.awt.Color(255, 255, 255));
        panel.setLayout(new java.awt.BorderLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/IMAGUENS/BANNE.png"))); // NOI18N
        panel.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        panel_corpo.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.add(panel_corpo, java.awt.BorderLayout.CENTER);

        panel.add(jPanel2, java.awt.BorderLayout.CENTER);

        jScrollPane1.setViewportView(panel);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Mesas", jPanel1);

        panelBalcao.setLayout(new java.awt.BorderLayout());
        jTabbedPane1.addTab("Balcão", panelBalcao);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
        // TODO add your handling code here:
        if (jTabbedPane1.getSelectedIndex() == 1) {
            ParamentroController paramentroController = new ParamentroController();
            ParamentroModel paramentroModel = paramentroController.getById(12); // pega o tipo de tela a ser usada touch ou não
            //panelBalcao.removeAll();
            if (paramentroModel.getValor() == 1) {
                panelBalcao.add(new ModeloView(usuario, this,false));
            } else {
                panelBalcao.add(new BalcaoView(usuario, this));
                
            }
            
            panelBalcao.updateUI();
        }
    }//GEN-LAST:event_jTabbedPane1MouseClicked

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
            java.util.logging.Logger.getLogger(PedidoMesaVIew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PedidoMesaVIew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PedidoMesaVIew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PedidoMesaVIew.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PedidoMesaVIew().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panelBalcao;
    private javax.swing.JPanel panel_corpo;
    // End of variables declaration//GEN-END:variables
}
