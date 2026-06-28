import java.util.HashSet;
import java.util.Set;

public abstract class Profissional extends Pessoa {
    private String especialidade;
    private String registroProfissional;
    private double valorConsulta;
    private Set<String> diasDisponiveis; // antes era String[] diasDisponiveis + int totalDias

    // construtor simples
    public Profissional(String nome, String especialidade) {
        super(nome, "", "", "");
        this.especialidade = especialidade;
        this.registroProfissional = "";
        this.valorConsulta = 0;
        this.diasDisponiveis = new HashSet<>();
    }

    // construtor intermediario
    public Profissional(String nome, String especialidade, String registroProfissional, double valorConsulta) {
        super(nome, "", "", "");
        this.especialidade = especialidade;
        setRegistroProfissional(registroProfissional);
        setValorConsulta(valorConsulta);
        this.diasDisponiveis = new HashSet<>();
    }

    // construtor completo
    public Profissional(String nome, String especialidade, String registroProfissional,
                        double valorConsulta, String[] dias, int totalDias) {
        super(nome, "", "", "");
        this.especialidade = especialidade;
        setRegistroProfissional(registroProfissional);
        setValorConsulta(valorConsulta);
        this.diasDisponiveis = new HashSet<>();
        for (int i = 0; i < totalDias; i++) {
            this.diasDisponiveis.add(dias[i]);
        }
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getRegistroProfissional() {
        return registroProfissional;
    }

    public void setRegistroProfissional(String registroProfissional) {
        if (registroProfissional == null || registroProfissional.trim().isEmpty()) {
            System.out.println("O registro profissional nao pode ser vazio!");
        }
        this.registroProfissional = registroProfissional;
    }

    public double getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(double valorConsulta) {
        if (valorConsulta < 0) {
            System.out.println("Valor invalido. Nao alterado.");
            return;
        }
        this.valorConsulta = valorConsulta;
    }

    public Set<String> getDiasDisponiveis() {
        return diasDisponiveis;
    }

    public void setDiasDisponiveis(Set<String> diasDisponiveis) {
        this.diasDisponiveis = diasDisponiveis;
    }

    public void atualizar(String registro, double valor) {
        setRegistroProfissional(registro);
        setValorConsulta(valor);
    }

    // agora recebe array mas adiciona no Set internamente
    public void atualizar(String registro, double valor, String[] dias, int totalDias) {
        setRegistroProfissional(registro);
        setValorConsulta(valor);
        diasDisponiveis.clear();
        for (int i = 0; i < totalDias; i++) {
            diasDisponiveis.add(dias[i]);
        }
    }

    // com Set, contains() faz isso em O(1) - sem precisar de loop
    public boolean atendeNoDia(String dia) {
        return diasDisponiveis.contains(dia);
    }

    public static boolean especialidadeValida(String esp) {
        if (esp.equalsIgnoreCase("clinica geral")) return true;
        if (esp.equalsIgnoreCase("fisioterapia")) return true;
        if (esp.equalsIgnoreCase("psicologia")) return true;
        if (esp.equalsIgnoreCase("nutricao")) return true;
        return false;
    }

    protected String montarResumoBase() {
        String dias = String.join(", ", diasDisponiveis);
        return "Nome: " + getNome() + " | Espec: " + especialidade
                + " | Reg: " + registroProfissional
                + " | Valor: R$" + valorConsulta + " | Dias: " + dias;
    }

    public abstract void registrarEspecifico(Atendimento atendimento);

    @Override
    public abstract void exibirResumo();
}
