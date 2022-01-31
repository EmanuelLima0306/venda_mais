/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author celso
 */
public class EmpresaModel {

    private int id;
    private String nome;
    private String nif;
    private String email;
    private String contacto;
    private String webSIte;
    private TipoRegimeModel regime;
    private String endereco;
    private String infoConta;
    private String local;
    private String logotipo;

    public EmpresaModel(int id, String nome, String nif, String email, String contacto, String webSIte, String endereco, String infoConta,  String logotipo) {
        this.id = id;
        this.nome = nome;
        this.nif = nif;
        this.email = email;
        this.contacto = contacto;
        this.webSIte = webSIte;

        this.endereco = endereco;
        this.infoConta = infoConta;
        this.logotipo = logotipo;
    }

    public EmpresaModel(String nome, String nif, String email, String contacto, String webSIte, String endereco, String infoConta,  String logotipo) {
        this.nome = nome;
        this.nif = nif;
        this.email = email;
        this.contacto = contacto;
        this.webSIte = webSIte;

        this.endereco = endereco;
        this.infoConta = infoConta;
         this.logotipo = logotipo;
    }

    public EmpresaModel() {
    }

    public TipoRegimeModel getRegime() {
        return regime;
    }

    public void setRegime(TipoRegimeModel regime) {
        this.regime = regime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContacto() {
        return contacto;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getWebSIte() {
        return webSIte;
    }

    public void setWebSIte(String webSIte) {
        this.webSIte = webSIte;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getInfoConta() {
        return infoConta;
    }

    public void setInfoConta(String infoConta) {
        this.infoConta = infoConta;
    }

    public String getLogotipo() {
        return logotipo;
    }

    public void setLogotipo(String logotipo) {
        this.logotipo = logotipo;
    }
    
    

    public boolean isEmpty() {
        return nome.isEmpty() || nif.isEmpty() || contacto.isEmpty() || local.isEmpty();
    }

}
