public class Fisioterapeuta extends Profissional {

    private int totalSessoesPrevistas;

    public Fisioterapeuta(String nome) {
        super(nome, "fisioterapia");
        this.totalSessoesPrevistas = 0;
    }

    public Fisioterapeuta(String nome, String registro, double valorConsulta, int totalSessoesPrevistas) {
        super(nome, "fisioterapia", registro, valorConsulta);
        this.totalSessoesPrevistas = totalSessoesPrevistas;
    }

    public int getTotalSessoesPrevistas() {
        return totalSessoesPrevistas;
    }

    public void setTotalSessoesPrevistas(int totalSessoesPrevistas) {
        this.totalSessoesPrevistas = totalSessoesPrevistas;
    }

    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        atendimento.adicionarProcedimento("Fisioterapia - Sessões previstas: " + totalSessoesPrevistas);
    }

    @Override
    public void exibirResumo() {
        System.out.println(montarResumoBase() + " | Sessões previstas: " + totalSessoesPrevistas);
    }
}