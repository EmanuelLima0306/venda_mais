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
public class ProdutoModel {

    private int id;
    private String designacao;
    private String descricao;
    private FabricanteModel fabricante;
    private CategoriaModel categoria;
    private String referencia;
    private boolean stocavel;
    private boolean expira;
    private boolean ipc;
    private boolean isMenuDia;
    private EstadoModel estado;
    private UsuarioModel usuario;
    private String data;
    private int stockMinimo;
    private int diaAlerta;
    private int quantCritica;
    private int diaDevolucao;
    private int garantia;
    private String urlImage;
    private double valorVenda = 0;
    private String organizacao;
    private Motivo motivo;
    private Taxa taxa;
    private ServicoModel servico;
    private boolean isCozinha = false;

    public ProdutoModel(int id, String designacao) {
        this.id = id;
        this.designacao = designacao;
    }

    public ProdutoModel(int id, String designacao, String descricao, 
            FabricanteModel fabricante, CategoriaModel categoria,
            String referencia, boolean stocavel, boolean expira,
            boolean ipc, EstadoModel estado, UsuarioModel usuario,
            String data, int stockMinimo, int diaAlerta,
            int quantCritica, int diaDevolucao,Motivo motivo,Taxa taxa) {
        this.id = id;
        this.designacao = designacao;
        this.descricao = descricao;
        this.fabricante = fabricante;
        this.categoria = categoria;
        this.referencia = referencia;
        this.stocavel = stocavel;
        this.expira = expira;
        this.ipc = ipc;
        this.estado = estado;
        this.usuario = usuario;
        this.data = data;
        this.stockMinimo = stockMinimo;
        this.diaAlerta = diaAlerta;
        this.quantCritica = quantCritica;
        this.diaDevolucao = diaDevolucao;
    }

    public ProdutoModel(String designacao, String descricao, FabricanteModel fabricante, 
            CategoriaModel categoria, String referencia, boolean stocavel,
            boolean expira, boolean ipc, EstadoModel estado, UsuarioModel usuario,
            String data, int stockMinimo, int diaAlerta, int quantCritica, 
            int diaDevolucao,Motivo motivo,Taxa taxa) {
        this.designacao = designacao;
        this.descricao = descricao;
        this.fabricante = fabricante;
        this.categoria = categoria;
        this.referencia = referencia;
        this.stocavel = stocavel;
        this.expira = expira;
        this.ipc = ipc;
        this.estado = estado;
        this.usuario = usuario;
        this.data = data;
        this.stockMinimo = stockMinimo;
        this.diaAlerta = diaAlerta;
        this.quantCritica = quantCritica;
        this.diaDevolucao = diaDevolucao;
        this.motivo = motivo;
        this.taxa = taxa;
    }

    public boolean isIsMenuDia() {
        return isMenuDia;
    }

    public void setIsMenuDia(boolean isMenuDia) {
        this.isMenuDia = isMenuDia;
    }

    public double getValorVenda() {
        return valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public ProdutoModel() {

    }

    public int getId() {
        return id;
    }

    public Motivo getMotivo() {
        return motivo;
    }

    public void setMotivo(Motivo motivo) {
        this.motivo = motivo;
    }

    public Taxa getTaxa() {
        return taxa;
    }

    public void setTaxa(Taxa taxa) {
        this.taxa = taxa;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignacao() {
        return designacao;
    }

    public void setDesignacao(String designacao) {
        this.designacao = designacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public FabricanteModel getFabricante() {
        return fabricante;
    }

    public void setFabricante(FabricanteModel fabricante) {
        this.fabricante = fabricante;
    }

    public CategoriaModel getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaModel categoria) {
        this.categoria = categoria;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public boolean isStocavel() {
        return stocavel;
    }

    public void setStocavel(boolean stocavel) {
        this.stocavel = stocavel;
    }

    public boolean isExpira() {
        return expira;
    }

    public void setExpira(boolean expira) {
        this.expira = expira;
    }

    public boolean isIpc() {
        return ipc;
    }

    public void setIpc(boolean ipc) {
        this.ipc = ipc;
    }

    public EstadoModel getEstado() {
        return estado;
    }

    public void setEstado(EstadoModel estado) {
        this.estado = estado;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return designacao;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public int getDiaAlerta() {
        return diaAlerta;
    }

    public void setDiaAlerta(int diaAlerta) {
        this.diaAlerta = diaAlerta;
    }

    public int getQuantCritica() {
        return quantCritica;
    }

    public void setQuantCritica(int quantCritica) {
        this.quantCritica = quantCritica;
    }

    public int getDiaDevolucao() {
        return diaDevolucao;
    }

    public void setDiaDevolucao(int diaDevolucao) {
        this.diaDevolucao = diaDevolucao;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getOrganizacao() {
        return organizacao;
    }

    public void setOrganizacao(String organizacao) {
        this.organizacao = organizacao;
    }

    public boolean isEmpty() {
        
        return designacao.isEmpty() || categoria.isEmpty() || fabricante.isEmpty();
        
    }

    public int getGarantia() {
        return garantia;
    }

    public void setGarantia(int garantia) {
        this.garantia = garantia;
    }

    public ServicoModel getSerieModel() {
        return servico;
    }

    public void setSerieModel(ServicoModel serieModel) {
        this.servico = serieModel;
    }

    public boolean isIsCozinha() {
        return isCozinha;
    }

    public void setIsCozinha(boolean isCozinha) {
        this.isCozinha = isCozinha;
    }
    
    
    
    

}
