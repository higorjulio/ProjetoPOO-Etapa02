public class PagamentoConvenio extends Pagamento {

    private String nomeConvenio;
    private double percentualCobertura;

    public PagamentoConvenio(int indiceConsulta, String nomeConvenio) {
        super(indiceConsulta, "convenio");
        this.nomeConvenio = nomeConvenio;
        this.percentualCobertura = definirPercentual(nomeConvenio);
    }
    private double definirPercentual(String convenio) {
        if (convenio.equalsIgnoreCase("SaudePlus")) return 0.40;
        if (convenio.equalsIgnoreCase("VidaMais")) return 0.30;
        if (convenio.equalsIgnoreCase("BemEstar")) return 0.50;
        return 0;
    }

    public String getNomeConvenio() {
        return nomeConvenio;
    }
    public double getPercentualCobertura() {
        return percentualCobertura;
    }

    @Override
    public double calcularValorFinal(double valorBase) {
        double cobertura = valorBase * percentualCobertura;
        double valor = valorBase - cobertura;
        setValorFinal(valor);
        return valor;
    }

    @Override
    public void exibirResumo() {
        System.out.print("[Convenio: " + nomeConvenio + " - " + (int)(percentualCobertura * 100) + "% cobertura] ");
        super.exibirResumo();
    }
}