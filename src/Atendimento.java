public class Atendimento implements Exportavel {
    private int indiceConsulta;
    private Prontuario prontuario;

    public Atendimento(int indiceConsulta, String observacoes, String dataRegistro) {
        this.indiceConsulta = indiceConsulta;
        this.prontuario = new Prontuario(observacoes, dataRegistro);
    }

    public Atendimento(int indiceConsulta, String observacoes, String diagnostico, String dataRegistro) {
        this.indiceConsulta = indiceConsulta;
        this.prontuario = new Prontuario(observacoes, diagnostico, dataRegistro);
    }

    public int getIndiceConsulta() {
        return indiceConsulta;
    }

    public String getDiagnostico() {
        return prontuario.getDiagnostico();
    }

    public Prontuario getProntuario() {
        return prontuario;
    }

    public void adicionarProcedimento(String procedimento) {
        prontuario.adicionarProcedimento(procedimento);
    }

    public void exibirResumo() {
        System.out.println("Atendimento - Consulta #" + indiceConsulta);
        prontuario.exibirResumo();
    }

    @Override
    public void exportarDados() {
        System.out.println("Atendimento #" + indiceConsulta
                + " | Diagnostico: " + prontuario.getDiagnostico()
                + " | Data: " + prontuario.getDataRegistro());
    }
}