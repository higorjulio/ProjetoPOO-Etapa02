public class ClinicoGeral extends Profissional {

    private String encaminhamento;

    public ClinicoGeral(String nome) {
        super(nome, "clinica geral");
        this.encaminhamento = "";
    }

    public ClinicoGeral(String nome, String registro, double valorConsulta, String encaminhamento) {
        super(nome, "clinica geral", registro, valorConsulta);
        this.encaminhamento = encaminhamento;
    }

    public String getEncaminhamento() {
        return encaminhamento;
    }

    public void setEncaminhamento(String encaminhamento) {
        this.encaminhamento = encaminhamento;
    }

    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        atendimento.adicionarProcedimento("Clinica Geral - Encaminhamento: " + encaminhamento);
    }

    @Override
    public void exibirResumo() {
        System.out.println(montarResumoBase() + " | Encaminhamento: " + encaminhamento);
    }
}