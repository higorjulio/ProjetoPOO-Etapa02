import java.util.ArrayList;
import java.util.List;

public class Atendimento {
    public int indiceConsulta;
    public String observacoes;
    public String diagnostico;
    public List<String> procedimentos; // antes era String[] procedimentos + int totalProcedimentos

    // construtor basico - so observacoes
    public Atendimento(int indiceConsulta, String observacoes) {
        this.indiceConsulta = indiceConsulta;
        this.observacoes = observacoes;
        this.diagnostico = "";
        this.procedimentos = new ArrayList<>();
    }

    // construtor com diagnostico
    public Atendimento(int indiceConsulta, String observacoes, String diagnostico) {
        this.indiceConsulta = indiceConsulta;
        this.observacoes = observacoes;
        this.diagnostico = diagnostico;
        this.procedimentos = new ArrayList<>();
    }

    // construtor completo com procedimentos ja definidos
    public Atendimento(int indiceConsulta, String observacoes, String diagnostico,
                       String[] procedimentos, int totalProcedimentos) {
        this.indiceConsulta = indiceConsulta;
        this.observacoes = observacoes;
        this.diagnostico = diagnostico;
        this.procedimentos = new ArrayList<>();
        for (int i = 0; i < totalProcedimentos; i++) {
            this.procedimentos.add(procedimentos[i]);
        }
    }

    // adiciona um por vez - sem precisar checar tamanho manualmente
    public void adicionarProcedimento(String procedimento) {
        procedimentos.add(procedimento);
    }

    // adiciona varios de uma vez
    public void adicionarProcedimento(String[] procs, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            procedimentos.add(procs[i]);
        }
    }

    public String exibirResumo() {
        String resumo = "Observacoes: " + observacoes;

        if (!diagnostico.equals("")) {
            resumo = resumo + "\nDiagnostico: " + diagnostico;
        }

        if (!procedimentos.isEmpty()) {
            resumo = resumo + "\nProcedimentos: " + String.join(", ", procedimentos);
        }
        return resumo;
    }
}
