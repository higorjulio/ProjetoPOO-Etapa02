public class PagamentoCartao extends Pagamento {

    public PagamentoCartao(int indiceConsulta, int parcelas) {
        super(indiceConsulta, "cartao", parcelas);
    }

    @Override
    public double calcularValorFinal(double valorBase) {
        int parcelas = getParcelas();
        if (parcelas < 1 || parcelas > 6) {
            System.out.println("Parcelas invalidas. Deve ser entre 1 e 6.");
            setValorFinal(valorBase);
            return valorBase;
        }
        double valor = valorBase;
        if (parcelas > 3) {
            int parcelasExtras = parcelas - 3;
            double taxa = parcelasExtras * 0.025;
            valor = valorBase + (valorBase * taxa);
        }
        setValorFinal(valor);
        return valor;
    }

    @Override
    public void exibirResumo() {
        System.out.print("[Cartao] ");
        super.exibirResumo();
    }
}