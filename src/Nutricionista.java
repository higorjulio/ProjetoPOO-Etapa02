public class Nutricionista extends Profissional {

    private String dieta;

    public Nutricionista(String nome) {
        super(nome, "nutricao");
        this.dieta = "";
    }

    public Nutricionista(String nome, String registro, double valorConsulta, String dieta) {
        super(nome, "nutricao", registro, valorConsulta);
        this.dieta = dieta;
    }

    public String getDieta() {
        return dieta;
    }

    public void setDieta(String dieta) {
        this.dieta = dieta;
    }

    @Override
    public void registrarEspecifico(Atendimento atendimento) {
        atendimento.adicionarProcedimento("Nutricao - Dieta: " + dieta);
    }

    @Override
    public void exibirResumo() {
        System.out.println(montarResumoBase() + " | Dieta: " + dieta);
    }
}