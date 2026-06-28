public abstract class Pagamento implements Exportavel {

    private int indiceConsulta;
    private double valorFinal;
    private String tipoPagamento;
    private int parcelas;

    public Pagamento(int indiceConsulta, String tipoPagamento) {
        this.indiceConsulta = indiceConsulta;
        this.tipoPagamento = tipoPagamento;
        this.parcelas = 1;
        this.valorFinal = 0;
    }

    public Pagamento(int indiceConsulta, String tipoPagamento, int parcelas) {
        this.indiceConsulta = indiceConsulta;
        this.tipoPagamento = tipoPagamento;
        this.parcelas = parcelas;
        this.valorFinal = 0;
    }

    public int getIndiceConsulta() {
        return indiceConsulta;
    }
    public void setIndiceConsulta(int indiceConsulta) {
        this.indiceConsulta = indiceConsulta;
    }

    public double getValorFinal() {
        return valorFinal;
    }
    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }
    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public int getParcelas() {
        return parcelas;
    }
    public void setParcelas(int parcelas) {
        this.parcelas = parcelas;
    }

    // LIGACAO DINAMICA: o metodo chamado depende do tipo REAL do objeto, nao do tipo da referencia
    public abstract double calcularValorFinal(double valorBase);

    public void exibirResumo() {
        double valorArredondado = Math.round(valorFinal * 100.0) / 100.0;
        String resumo = "Consulta #" + indiceConsulta + " | Valor: R$" + valorArredondado
                + " | Tipo: " + tipoPagamento + " | Parcelas: " + parcelas;
        if (parcelas > 1) {
            double valorParcela = Math.round((valorFinal / parcelas) * 100.0) / 100.0;
            resumo = resumo + " (R$" + valorParcela + " cada)";
        }
        System.out.println(resumo);
    }

    @Override
    public void exportarDados() {
        System.out.println("Pagamento - Consulta #" + indiceConsulta
                + " | Tipo: " + tipoPagamento
                + " | Valor: R$" + Math.round(valorFinal * 100.0) / 100.0);
    }
}