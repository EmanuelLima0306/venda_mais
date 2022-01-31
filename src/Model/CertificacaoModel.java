/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author emanuellima
 */
public class CertificacaoModel {
    
    private int codigo;
    private String nomeEmpresa;
    private String nifProdutorSistema;
    private String nifRepresentanteLegal;
    private String endereco;
    private String pontoReferenca;
    private String email;
    private String nomePrograma;
    private String versaoPrograma;
    private String numeroValidacao;
    private String dataValidacao;
    private String dataExpirar;

    public CertificacaoModel() {
    }

    public CertificacaoModel(int codigo, String nomeEmpresa, String nifProdutorSistema, String nifRepresentanteLegal, String endereco, String pontoReferenca, String email, String nomePrograma, String versaoPrograma, String numeroValidacao, String dataValidacao, String dataExpirar) {
        this.codigo = codigo;
        this.nomeEmpresa = nomeEmpresa;
        this.nifProdutorSistema = nifProdutorSistema;
        this.nifRepresentanteLegal = nifRepresentanteLegal;
        this.endereco = endereco;
        this.pontoReferenca = pontoReferenca;
        this.email = email;
        this.nomePrograma = nomePrograma;
        this.versaoPrograma = versaoPrograma;
        this.numeroValidacao = numeroValidacao;
        this.dataValidacao = dataValidacao;
        this.dataExpirar = dataExpirar;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getNifProdutorSistema() {
        return nifProdutorSistema;
    }

    public void setNifProdutorSistema(String nifProdutorSistema) {
        this.nifProdutorSistema = nifProdutorSistema;
    }

    public String getNifRepresentanteLegal() {
        return nifRepresentanteLegal;
    }

    public void setNifRepresentanteLegal(String nifRepresentanteLegal) {
        this.nifRepresentanteLegal = nifRepresentanteLegal;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getPontoReferenca() {
        return pontoReferenca;
    }

    public void setPontoReferenca(String pontoReferenca) {
        this.pontoReferenca = pontoReferenca;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNomePrograma() {
        return nomePrograma;
    }

    public void setNomePrograma(String nomePrograma) {
        this.nomePrograma = nomePrograma;
    }

    public String getVersaoPrograma() {
        return versaoPrograma;
    }

    public void setVersaoPrograma(String versaoPrograma) {
        this.versaoPrograma = versaoPrograma;
    }

    public String getNumeroValidacao() {
        return numeroValidacao;
    }

    public void setNumeroValidacao(String numeroValidacao) {
        this.numeroValidacao = numeroValidacao;
    }

    public String getDataValidacao() {
        return dataValidacao;
    }

    public void setDataValidacao(String dataValidacao) {
        this.dataValidacao = dataValidacao;
    }

    public String getDataExpirar() {
        return dataExpirar;
    }

    public void setDataExpirar(String dataExpirar) {
        this.dataExpirar = dataExpirar;
    }
    
    public boolean isEmpty(){
        
        return nomeEmpresa.isEmpty() || nomePrograma.isEmpty() || email.isEmpty() || nifProdutorSistema.isEmpty();
    }

    @Override
    public String toString() {
        return numeroValidacao;
    }
    
    
    
    
    
}
