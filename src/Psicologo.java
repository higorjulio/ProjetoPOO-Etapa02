public class Psicologo extends Profissional {

    private String abordagem;

    public Psicologo(String nome) {
        super(nome, "psicologia");
        this.abordagem = "";
    }

    public Psicologo(String nome, String registro, double valorConsulta, String abordagem) {
        super(nome, "psicologia", registro, valorConsulta);
        this.abordagem = abordagem;
    }

    public String getAbordagem() {
        return abordagem;
    }

    public void setAbordagem(String abordagem) {
        this.abordagem = abordagem;
    }

    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        atendimento.adicionarProcedimento("Psicologia - Abordagem: " + abordagem);
    }

    @Override
    public void exibirResumo() {
        System.out.println(montarResumoBase() + " | Abordagem: " + abordagem);
    }
}