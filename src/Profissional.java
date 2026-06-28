public abstract class Profissional extends Pessoa {
    // atributos privados
    private String especialidade;
    private String registroProfissional;
    private double valorConsulta;
    private String[] diasDisponiveis;
        private int totalDias;

    // construtor simples
    public Profissional(String nome, String especialidade) {
        super(nome, "", "", ""); // passa o nome e preenche os outros 3 parametros obrigatorios de Pessoa
        this.especialidade = especialidade;
        this.registroProfissional = "";
        this.valorConsulta = 0;
        this.diasDisponiveis = new String[7];
        this.totalDias = 0;
    }

    // construtor intermediario
    public Profissional(String nome, String especialidade, String registroProfissional, double valorConsulta) {
        super(nome, "", "", ""); 
        this.especialidade = especialidade;
        setRegistroProfissional(registroProfissional);
        setValorConsulta(valorConsulta);
        this.diasDisponiveis = new String[7];
        this.totalDias = 0;
    }

    // construtor completo
    public Profissional(String nome, String especialidade, String registroProfissional,
                        double valorConsulta, String[] dias, int totalDias) {
        super(nome, "", "", "");
        this.especialidade = especialidade;
        setRegistroProfissional(registroProfissional);
        setValorConsulta(valorConsulta);
        this.diasDisponiveis = new String[7];
        this.totalDias = totalDias;
        for (int i = 0; i < totalDias; i++) {
            this.diasDisponiveis[i] = dias[i];
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

    // valida se o registro e vazio
    public void setRegistroProfissional(String registroProfissional) {
        if (registroProfissional == null || registroProfissional.trim().isEmpty()) {
            System.out.println("O registro profissional não pode ser vazio!");
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

    public String[] getDiasDisponiveis() {
        return diasDisponiveis;
    }

    public void setDiasDisponiveis(String[] diasDisponiveis) {
        this.diasDisponiveis = diasDisponiveis;
    }

    public int getTotalDias() {
        return totalDias;
    }

    public void setTotalDias(int totalDias) {
        this.totalDias = totalDias;
    }

    public void atualizar(String registro, double valor) {
        setRegistroProfissional(registro);
        setValorConsulta(valor);
    }

    public void atualizar(String registro, double valor, String[] dias, int totalDias) {
        setRegistroProfissional(registro);
        setValorConsulta(valor);
        this.totalDias = totalDias;
        for (int i = 0; i < totalDias; i++) {
            this.diasDisponiveis[i] = dias[i];
        }
    }

    // verifica atendimento no dia
    public boolean atendeNoDia(String dia) {
        for (int i = 0; i < totalDias; i++) {
            if (diasDisponiveis[i].equals(dia)) {
                return true;
            }
        }
        return false;
    }

    // valida especialidade
    public static boolean especialidadeValida(String esp) {
        if (esp.equalsIgnoreCase("clinica geral")) return true;
        if (esp.equalsIgnoreCase("fisioterapia")) return true;
        if (esp.equalsIgnoreCase("psicologia")) return true;
        if (esp.equalsIgnoreCase("nutricao")) return true;
        return false;
    }

    // resumo base
    protected String montarResumoBase() {
        String dias = "";
        for (int i = 0; i < totalDias; i++) {
            if (i > 0) dias = dias + ", ";
            dias = dias + diasDisponiveis[i];
        }
        return "Nome: " + getNome() + " | Espec: " + especialidade
                + " | Reg: " + registroProfissional
                + " | Valor: R$" + valorConsulta + " | Dias: " + dias;
    }

    // metodo abstrato especifico
    public abstract void registrarEspecifico(Atendimento atendimento);

    // exibe resumo
    @Override
    public abstract void exibirResumo();
}