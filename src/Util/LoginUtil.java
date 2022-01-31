/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import Controller.AnoController;
import Controller.CaixaController;
import Controller.DocumentoController12;
import Controller.EntradaStockItemController;
import Controller.FacturaController;
import Controller.ParamentroController;
import Controller.SerieController;
import Enum.TipoLog;
import Ireport.FacturaIreport;
import Model.CaixaModel;
import Model.Documento;
import Model.ParamentroModel;
import Model.SerieModel;
import Model.UsuarioModel;
import View.CaixaView;
import View.MenuPrincipalView;
import View.PedidoMesaVIew;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author celso & Emanuel
 */
public class LoginUtil {

    
    
    public static void loginAcesso(UsuarioModel usuarioSystem, JFrame frm) {
        
//        if (isDataActualizada()) {
        if (usuarioSystem.getEstado().getId() == 1) {
            
            if (usuarioSystem.getTipoUsuario().getId() == 1) {//ADMINISTRADOR

                frm.dispose();
                new MenuPrincipalView(usuarioSystem).setVisible(true);
                EntradaStockItemController controller1 = new EntradaStockItemController();

                if (controller1.getLoteExpirado()) {
                    JOptionPane.showMessageDialog(frm, "Existe produto expirado no sistema", "Alerta", JOptionPane.WARNING_MESSAGE);
                }
                if (controller1.getLotePresteExpirar()) {
                    JOptionPane.showMessageDialog(frm, "Existe produto Preste a Expirar no sistema", "Alerta", JOptionPane.WARNING_MESSAGE);
                }
                if (controller1.getQtdCritica()) {
                    JOptionPane.showMessageDialog(frm, "Existe produto Com o stock baixo", "Alerta", JOptionPane.WARNING_MESSAGE);
                }
                updateSerie();
                backup();

            } else if (usuarioSystem.getTipoUsuario().getId() == 2) {//OPERADOR-CAIXA
                
                frm.dispose();
                
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            //            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            CaixaController caixaController = new CaixaController();

                ParamentroController controller = new ParamentroController();
             
                ParamentroModel paramentroModel = controller.getById(10); //pegar a permissão de fechar e abrir caixa
                int totalCaixa = caixaController.getTotal(usuarioSystem.getId());
                
//                if (paramentroModel.getValor() == 1 || totalCaixa == 0) {

                     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                CaixaModel caixaModel = caixaController.getByDateAndUsuario(dateFormat.format(new Date()), usuarioSystem.getId(), "Aberto");

                if (caixaModel != null) {

                    if (caixaModel.getId() > 0) {
                        
                        if (paramentroModel.getValor() == 1) {
                            new PedidoMesaVIew(usuarioSystem).setVisible(true);
                        }else{
                                    new PedidoMesaVIew(usuarioSystem,true).setVisible(true);
                                }
                    } else {
                        
                        if (paramentroModel.getValor() == 1) {
                            new PedidoMesaVIew(usuarioSystem).setVisible(true);
                        }else{
                                    new PedidoMesaVIew(usuarioSystem,true).setVisible(true);
                                }
                    }
                } else {
                
                        Object[] opcao={"Sim","Não"};
                        caixaModel = caixaController.getLastByUsuario(usuarioSystem.getId(), "Aberto");

                        if(caixaModel != null){
                            
//                            if (paramentroModel.getValor() == 1) {
//
//                                if (JOptionPane.showOptionDialog(null, "Estamos em um Novo Dia, Existem Caixa Aberto em:"+caixaModel.getDataAbertura()+
//                                        "\nDeseja Abrir Novo caixa?"," Alerta ", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opcao,opcao[0])==JOptionPane.YES_OPTION) 
//                                {
//                                    dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                    caixaModel.setDataFecho(dateFormat1.format(new Date()));
//                                    caixaModel.setEstado("Fechado");
//
//                                    if(caixaController.saveOrUpdate(caixaModel)){
//
//                                        //FacturaIreport.fechoCaixa(caixaModel);
//                                        new CaixaView(usuarioSystem).setVisible(true);
//                                    }
//                                }else{
//                                    new PedidoMesaVIew(usuarioSystem).setVisible(true);
//                                }
//                            }else{
//                                    new PedidoMesaVIew(usuarioSystem,true).setVisible(true);
//                                }
                        if (paramentroModel.getValor() == 1) {
                            new PedidoMesaVIew(usuarioSystem).setVisible(true);
                        }else{
                                    new PedidoMesaVIew(usuarioSystem,true).setVisible(true);
                                }

                        }else{
                            if (paramentroModel.getValor() == 1) {
                                new PedidoMesaVIew(usuarioSystem).setVisible(true);
                            }else{
                                        new PedidoMesaVIew(usuarioSystem,true).setVisible(true);
                                    }
                        }
                        
                        backup();

                    }
                
//                } else {
//                    new PedidoMesaVIew(usuarioSystem,true).setVisible(true);
//                }
//                                    new FrontOfficePequeno(usuarioSystem).setVisible(true);
            }
//                                 else if (usuarioSystem.getTipoUsuario().getId() == 3) {//OPERADOR-SUPERVISOR
//
//                                } else if (usuarioSystem.getTipoUsuario().getId() == 4) {//OPERADOR-DEPOSITO
//
//                                }
        } else if (usuarioSystem.getEstado().getId() == 2) {
            JOptionPane.showMessageDialog(frm, "O seu Acesso foi suspenso\nEntre em contacto com o adminitrador",
                    "Alerta", JOptionPane.WARNING_MESSAGE);
        }
//        } else {
//            JOptionPane.showMessageDialog(frm, "Hora do computador não está certa.\n"
//                    + "Porque não bate certo com a data do ultimo documento emitido.\n"
//                    + "entre em contacto com o administrador do sistema.", "Hora não está actualizada", JOptionPane.ERROR_MESSAGE);
//        }
    }
    
    public static void backup() {

        // TODO add your handling code here:
//            CopySecury.backup();
        ParamentroController controller = new ParamentroController();
        ParamentroModel modelo = controller.getById(9);

        if (modelo.getValor() == 1) {
            new BackupAutomatico().run();
        }

    }
    
    public static void updateSerie(){
        SerieController serieControlelr = new SerieController();
        SerieModel serieModel = new SerieModel();
        serieModel = serieControlelr.getById(9);
        
        if(serieModel.getAno().getAno() != DataComponent.getAnoActualInteito()){
            Object[] opcao={"Sim","Não"};
            
            if (JOptionPane.showOptionDialog(null, "Tem a Certeza que Estamos em Novo Ano? :::: "+DataComponent.getAnoActual()+
                                        ""," Alerta ", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opcao,opcao[0])==JOptionPane.YES_OPTION) 
            {

                if (JOptionPane.showOptionDialog(null, "Estamos em um novo ano deseja manter a Serie das Facturas: "+serieModel.getDesignacao()+
                                            "?"," Alerta ", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,opcao,opcao[0])==JOptionPane.YES_OPTION) 
                {
                    serieModel.getAno().setAno(DataComponent.getAnoActualInteito());
                    if(new AnoController().update(serieModel.getAno()))
                    {

                        if(reporNumeracaoDocumento())
                            JOptionPane.showMessageDialog(null, "Numeração dos documentos reiniciada com sucesso");
                        else
                            JOptionPane.showMessageDialog(null, "Erro ao reiniciar numeração dos documentos");

                    }

                }else{
                    String serie = (String) JOptionPane.showInputDialog(null, "Digite a Serie","NOVA SERIE",JOptionPane.PLAIN_MESSAGE);
                    
                    if(serie != null){

                        serieModel.setDesignacao(serie);
                        serieControlelr.update(serieModel);
                    }
                    serieModel.getAno().setAno(DataComponent.getAnoActualInteito());
                    if(new AnoController().update(serieModel.getAno()))
                    {


                        if(reporNumeracaoDocumento())
                            JOptionPane.showMessageDialog(null, "Numeração dos documentos reiniciada com sucesso");
                        else
                            JOptionPane.showMessageDialog(null, "Erro ao reiniciar numeração dos documentos");

                    }
                }
            }
        }
        
    }
    
    public static boolean reporNumeracaoDocumento(){
        DocumentoController12 controller = new DocumentoController12();
                    
        boolean resultado = false;
        for(Documento documento : controller.get()){
            documento.setNext("1");
            resultado = controller.update(documento);
        }
        return resultado;
    }


    public static boolean isEqualsSenha_confirm(String senha, String senhaConfirm, JFrame frm) {

        if (!isEmpty(senha) && !isEmpty(senhaConfirm)) {
            return senha.equals(senhaConfirm);
        } else {
            JOptionPane.showMessageDialog(frm, "Preencha o espaço em branco");
        }
        return false;
    }

    public static boolean isDataActualizada() {

        FacturaController controller = new FacturaController();
        String dataLastFactura = controller.getDataLastFactura();

        if (dataLastFactura != null) {

            return DataComponent.compareDataLastFactura(dataLastFactura);

        }
        return true;
    }

    public static boolean isEmpty(String txt) {

        return txt.trim().isEmpty();
    }
}
