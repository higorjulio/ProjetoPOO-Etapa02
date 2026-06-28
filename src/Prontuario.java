import java.util.*;

class Prontuario {
    private String observacoes;
    private String diagnostico;
    private List<String> procedimentos;
    private String dataRegistro;

    Prontuario(String observacoes, String dataRegistro) {
        this.observacoes = observacoes;
        this.diagnostico = "";
        this.procedimentos = new ArrayList<>();
        this.dataRegistro = dataRegistro;
    }

    Prontuario(String observacoes, String diagnostico, String dataRegistro) {
        this.observacoes = observacoes;
        this.diagnostico = diagnostico;
        this.procedimentos = new ArrayList<>();
        this.dataRegistro = dataRegistro;
    }

    /*
    Getters e Setterss
     */

    public String getObservacoes() {
        return observacoes;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public String getDiagnostico() {
        return diagnostico;
    }
    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getDataRegistro() {
        return dataRegistro;
    }

    public List<String> getProcedimentos() {
        return procedimentos;
    }

    public void adicionarProcedimento(String procedimento) {
        procedimentos.add(procedimento);
    }

    public void exibirResumo() {
        System.out.println("Data:" + dataRegistro);
        System.out.println("Observacoes: " + observacoes);
        if (!diagnostico.equals("")) {
            System.out.println("Diagnostico: " + diagnostico);
        }
        if (!procedimentos.isEmpty()) {
            System.out.print("Procedimentos:b");
            for (int i = 0; i < procedimentos.size(); i++) {
                if (i > 0) System.out.print(", ");
                System.out.print(procedimentos.get(i));
            }
            System.out.println();
        }
    }
}