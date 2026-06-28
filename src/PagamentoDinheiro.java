public class PagamentoDinheiro extends Pagamento {

    public PagamentoDinheiro(int indiceConsulta) {
        super(indiceConsulta, "dinheiro");
    }

    @Override
    public double calcularValorFinal(double valorBase) {
        double desconto = valorBase * 0.05;
        double valor = valorBase - desconto;
        setValorFinal(valor);
        return valor;
    }

    @Override
    public void exibirResumo() {
        System.out.print("[Dinheiro/Pix - 5% desconto] ");
        super.exibirResumo();
    }
}