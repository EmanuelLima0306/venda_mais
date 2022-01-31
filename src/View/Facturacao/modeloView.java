/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View.Facturacao;

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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author emanuel
 */
public class modeloView extends javax.swing.JFrame {

    /**
     * Creates new form modeloView
     */
    private int fim;
    private int inicio;
    private int salto = 9;
    private HashMap<Integer, Integer> historico;

    private int countMaxClick = 0;
    private int countClick = 1;
    private int num_item = 10;
    private int resto = 0;
    private List<String> lista;

    public modeloView() {
        initComponents();

        File file = new File("prev.png");

        fim = salto;
        inicio = 0;
        panelCategoria.setLayout(new GridLayout(2, 5, 5, 5));
        calcClick();
        next();
        
        mostraProduto();
        carregarItem();
        setExtendedState(MAXIMIZED_BOTH);
//
//        createBtnCategoria(inicio, fim, true);

    }

    private List<ExemploProduto> getProduto() {

        List<ExemploProduto> lista = new ArrayList();
        lista.add(new ExemploProduto("P1", 20000.0, "azul.jpg",2));
        lista.add(new ExemploProduto("P1", 20000.0, "azul.jpg",1));
        lista.add(new ExemploProduto("P1", 20000.0, "azul.jpg",5));
        lista.add(new ExemploProduto("P1", 20000.0, "azul.jpg",7));
        
        
        
        lista.add(new ExemploProduto("P2", 5000.0, "laranja.jpg",1));
        lista.add(new ExemploProduto("P2", 5000.0, "laranja.jpg",1));
        lista.add(new ExemploProduto("P2", 5000.0, "laranja.jpg",3));
        lista.add(new ExemploProduto("P2", 5000.0, "laranja.jpg",4));
        
        
        
        lista.add(new ExemploProduto("P3", 15000.56, "verde.jpg",1));
        lista.add(new ExemploProduto("P3", 15000.56, "verde.jpg",1));
        lista.add(new ExemploProduto("P3", 15000.56, "verde.jpg",1));
        lista.add(new ExemploProduto("P3", 15000.56, "verde.jpg",1));
        
        
        
    
   

        return lista;

    }

    private void mostraProduto() {

        List<ExemploProduto> lista = getProduto();

        panelProduto.setLayout(new GridLayout(3,4,5,5));
        panelProduto.removeAll();

        for (ExemploProduto p : lista) {
            
            

            createLblProduto(p.getNome(), p.getPreco(), p.getImagem(), panelProduto);

        }
        
        panelProduto.updateUI();

    }

    private void createLblProduto(String nome, double preco, String urlImagem, JPanel panel) {

        JLabel lbl = new JLabel();
        lbl.setToolTipText(nome);

        File file = new File("Relatorio" + File.separator + "imagensProdutos" + File.separator + urlImagem);
        String pathFinal = file.getAbsolutePath();
        ImageIcon image = new ImageIcon(pathFinal);

        if (lbl.getWidth() == 0 || lbl.getHeight() == 0) {

            lbl.setIcon(new ImageIcon(image.getImage().getScaledInstance(200, 141, Image.SCALE_DEFAULT)));
        } else {
            lbl.setIcon(new ImageIcon(image.getImage().getScaledInstance(lbl.getWidth(), lbl.getHeight(), Image.SCALE_DEFAULT)));
        }

        lbl.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 1) {

                    DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
                    lblDescricaoProduto.setText(nome + " - " + decimalFormat.format(preco));

                } else {

                    //adicionar produto no carrinho
                    JOptionPane.showMessageDialog(panelProduto, "Produto Adicionado no carrinho");
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        
        
        JPanel panelItem = new JPanel();
        panelItem.setLayout(new BorderLayout());
        panelItem.add(lbl,BorderLayout.CENTER);
     
        panelItem.setBackground(new Color(166, 166, 166));
        
        panel.add(panelItem);

        panel.updateUI();

    }
    
    private void carregarItem(){
        
        
        List<ExemploProduto> lista = getProduto();
        
        for(ExemploProduto item : lista){
            
            addItem(item);
            
        }
        
         panelItemProduto.updateUI();
    }
    
    
    private void addItem(ExemploProduto produto){
        
        
        ItemFactura item = new ItemFactura(produto.getNome(),produto.getQtd(),produto.getPreco());
        panelItemProduto.add(item);
        panelItemProduto.updateUI();
        
        
    }
    
    

    private void calcClick() {

        this.lista = getCategoria();
        this.countMaxClick = lista.size() / 10;

        System.out.println(" this.countMaxClick >>> " + this.countMaxClick);

        this.resto = lista.size() % num_item;

        if (resto > 0) {

            this.countMaxClick += 1;
            System.out.println(" this.countMaxClick 1 >>> " + this.countMaxClick);

        }

    }

    private void next() {

        if (countMaxClick > 0 && countClick <= countMaxClick) {

            int indexFim = (num_item * countClick);
            int indexInicial = num_item * (countClick - 1);

            if (indexFim > this.lista.size()) {
                System.out.println("indexFim >>>" + indexFim);
                if (this.resto > 0) {
                    System.out.println("this.resto >>>" + this.resto);
                    indexFim = indexInicial + this.resto;
                }
            }

            createBtnCategoria(indexInicial, indexFim);

            System.out.println("countClick >>>" + countClick);

        } else {
            System.out.println("nao existe mas categorias");
        }

    }

    private void prev() {

        if (countMaxClick > 0 && countClick <= countMaxClick) {

            int indexFim = (num_item * countClick);
            int indexInicial = indexFim - num_item;

            createBtnCategoria(indexInicial, indexFim);

            System.out.println("countClick >>>" + countClick);

        } else {
            System.out.println("nao existe mas categorias");
        }

    }

    private void createBtnCategoria(int indexInicial, int indexFim) {

        List<String> lista = getCategoria();
        if (indexFim <= lista.size()) {
            panelCategoria.removeAll();
            while (indexInicial < indexFim) {

                createBtn(lista.get(indexInicial), this.panelCategoria);
                System.out.println("indexInicial >>>" + indexInicial);
                indexInicial++;

            }
            panelCategoria.updateUI();
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
                new modeloView().setVisible(true);
            }
        });
    }

    private void createBtn(String name, JPanel panel) {

        JButton btn = new JButton(name);
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JOptionPane.showMessageDialog(panel, name);
            }
        });
        panel.add(btn);
        panel.updateUI();

    }

    private List<String> getCategoria() {

        List<String> lista = new ArrayList();
        lista.add("Doce1");
        lista.add("Doce2");
        lista.add("Doce3");
        lista.add("Doce4");
        lista.add("Doce5");
        lista.add("Doce6");
        lista.add("Doce7");
        lista.add("Doce8");
        lista.add("Doce9");
        lista.add("Doce10");
        lista.add("Doce11");
        lista.add("Doce12");
        lista.add("Doce13");
        lista.add("Doce14");
        lista.add("Doce15");
        lista.add("Doce16");
        lista.add("Doce17");
        lista.add("Doce18");
        lista.add("Doce19");
        lista.add("Doce20");
        lista.add("Doce21");
        lista.add("Doce22");
        lista.add("Doce23");
        lista.add("Doce24");
        lista.add("Doce25");
        lista.add("Doce26");
        lista.add("Doce27");
        lista.add("Doce28");
        lista.add("Doce29");
        lista.add("Doce30");
        lista.add("Doce31");

        return lista;

    }

    private void createBtnCategoria(int inicio, int fim, boolean isNext) {

        List<String> lista = getCategoria();
        System.out.print("inicio >>>" + inicio);
        panelCategoria.setLayout(new GridLayout(2, 5, 5, 5));
//        panelCategoria.setLayout(new GridLayout(2, 5,5,5));

        if (lista != null) {

            if (inicio < lista.size()) {
                panelCategoria.removeAll();
            }
            //20 - 30
            while (inicio <= fim && inicio < lista.size()) {

                int index = isNext ? inicio : fim;

                createBtn(lista.get(index), panelCategoria);

                if (isNext) {
                    inicio++;
                } else {
                    fim--;
                }

            }

        }
        panelCategoria.updateUI();

        this.fim = inicio;

        if (!isNext) {

            this.inicio = this.fim - (this.salto + 1);
        }
        if (this.fim == lista.size()) {

            this.inicio = this.fim;
        }
        System.out.println("this.inicio >>>" + this.inicio);
        System.out.println(" fim >>" + this.fim);

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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        panelItemProduto = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
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

        jPanel1.setBackground(new java.awt.Color(36, 36, 39));

        jButton1.setBackground(new java.awt.Color(0, 102, 51));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Transferir Pedido");

        jButton2.setBackground(new java.awt.Color(166, 166, 166));
        jButton2.setText("Imprimir Conta");

        jScrollPane1.setBackground(new java.awt.Color(36, 36, 39));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        panelItemProduto.setBackground(new java.awt.Color(36, 36, 39));

        javax.swing.GroupLayout panelItemProdutoLayout = new javax.swing.GroupLayout(panelItemProduto);
        panelItemProduto.setLayout(panelItemProdutoLayout);
        panelItemProdutoLayout.setHorizontalGroup(
            panelItemProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 505, Short.MAX_VALUE)
        );
        panelItemProdutoLayout.setVerticalGroup(
            panelItemProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(panelItemProduto);

        jPanel11.setBackground(new java.awt.Color(36, 36, 39));
        jPanel11.setLayout(new java.awt.GridLayout());

        jButton3.setBackground(new java.awt.Color(0, 102, 51));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/Cash Register_50px.png"))); // NOI18N
        jPanel11.add(jButton3);

        jButton5.setBackground(new java.awt.Color(0, 102, 51));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/Print_45px.png"))); // NOI18N
        jPanel11.add(jButton5);

        jButton6.setBackground(new java.awt.Color(0, 102, 51));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/Search_150px.png"))); // NOI18N
        jPanel11.add(jButton6);

        jButton4.setBackground(new java.awt.Color(0, 102, 51));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/Checkout_45px.png"))); // NOI18N
        jPanel11.add(jButton4);

        jPanel12.setBackground(new java.awt.Color(36, 36, 39));
        jPanel12.setLayout(new java.awt.GridLayout());

        jButton7.setBackground(new java.awt.Color(166, 166, 166));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/Administrative Tools_45px.png"))); // NOI18N
        jPanel12.add(jButton7);

        jButton8.setBackground(new java.awt.Color(234, 65, 10));
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/imac_exit_100px.png"))); // NOI18N
        jPanel12.add(jButton8);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addGap(17, 17, 17))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_END);

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

        panelCategoria.setBackground(new java.awt.Color(166, 166, 166));

        javax.swing.GroupLayout panelCategoriaLayout = new javax.swing.GroupLayout(panelCategoria);
        panelCategoria.setLayout(panelCategoriaLayout);
        panelCategoriaLayout.setHorizontalGroup(
            panelCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 559, Short.MAX_VALUE)
        );
        panelCategoriaLayout.setVerticalGroup(
            panelCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(panelCategoria, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jPanel4.setBackground(new java.awt.Color(193, 193, 193));

        jPanel5.setBackground(new java.awt.Color(193, 193, 193));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/4.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/Facturacao/3.png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(97, Short.MAX_VALUE)
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
                .addComponent(lblDescricaoProduto, javax.swing.GroupLayout.DEFAULT_SIZE, 633, Short.MAX_VALUE)
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

        javax.swing.GroupLayout panelProdutoLayout = new javax.swing.GroupLayout(panelProduto);
        panelProduto.setLayout(panelProdutoLayout);
        panelProdutoLayout.setHorizontalGroup(
            panelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelProdutoLayout.setVerticalGroup(
            panelProdutoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 308, Short.MAX_VALUE)
        );

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
                .addComponent(panelProduto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.add(panelContainer, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        System.out.println("------------------- AVANCAR -------------");
//        inicio = fim;
//        createBtnCategoria(inicio, fim + salto, true);

        this.countClick++;
        next();

    }//GEN-LAST:event_jLabel1MouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        // TODO add your handling code here:
        System.out.println("------------------- VOLTAR -------------");
        //
        //        if (fim == inicio) {
        //
        //            System.out.println("------fim---------" + fim);
        //            int resto = fim % 10;
        //            System.out.println("------RESTO---------" + resto);
        //
        //            fim -= resto;
        //            System.out.println("------fim---------" + fim);
        //            createBtnCategoria(fim - (salto + 1), fim, false);
        //
        //        } else {
        //            createBtnCategoria(inicio - (salto + 1), fim - (salto + 2), false);
        //        }

        this.countClick--;
        prev();
    }//GEN-LAST:event_jLabel2MouseClicked
    /*
    0 - 9
    10 - 19    =    10-(9+1) =0 / 19 - (9+2)
    20 - 29
    30 - 31
    
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDescricaoProduto;
    private javax.swing.JPanel panelCategoria;
    private javax.swing.JPanel panelContainer;
    private javax.swing.JPanel panelItemProduto;
    private javax.swing.JPanel panelProduto;
    // End of variables declaration//GEN-END:variables
}
