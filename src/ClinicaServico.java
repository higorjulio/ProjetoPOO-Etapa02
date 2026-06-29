import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClinicaServico {

    private List<Paciente> pacientes;
    private List<Profissional> profissionais;
    private List<Consulta> consultas;
    private List<Atendimento> atendimentos;
    private List<Pagamento> pagamentos;

    private Map<String, Double> multasPorCpf;
    private Set<String> cpfsCadastrados;
    private Map<String, Paciente> indicePacientes;
    private Map<String, Profissional> indiceProfissionais;

    public ClinicaServico() {
        pacientes = new ArrayList<>();
        profissionais = new ArrayList<>();
        consultas = new ArrayList<>();
        atendimentos = new ArrayList<>();
        pagamentos = new ArrayList<>();

        multasPorCpf = new HashMap<>();
        cpfsCadastrados = new HashSet<>();
        indicePacientes = new HashMap<>();
        indiceProfissionais = new HashMap<>();
    }

    // ---- ACESSOS PARA RELATORIOS E LISTAGENS ----

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Profissional> getProfissionais() {
        return profissionais;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public List<Atendimento> getAtendimentos() {
        return atendimentos;
    }

    public List<Pagamento> getPagamentos() {
        return pagamentos;
    }

    public Map<String, Double> getMultasPorCpf() {
        return multasPorCpf;
    }

    public List<Pessoa> getPessoas() {
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.addAll(pacientes);
        pessoas.addAll(profissionais);
        return pessoas;
    }

    // ---- PACIENTES ----

    public ResultadoOperacao<Paciente> cadastrarPaciente(Paciente paciente) {
        if (paciente == null) {
            return ResultadoOperacao.erro("Paciente invalido.");
        }
        if (cpfsCadastrados.contains(paciente.getCpf())) {
            return ResultadoOperacao.erro("CPF ja cadastrado!");
        }

        pacientes.add(paciente);
        cpfsCadastrados.add(paciente.getCpf());
        indicePacientes.put(paciente.getCpf(), paciente);
        return ResultadoOperacao.sucesso("Paciente cadastrado com sucesso!", paciente);
    }

    public Paciente buscarPacientePorCpf(String cpf) {
        return indicePacientes.get(cpf);
    }

    public ResultadoOperacao<Paciente> complementarPaciente(String cpf, int idade, String telefone, String convenio) {
        Paciente paciente = buscarPacientePorCpf(cpf);
        if (paciente == null) {
            return ResultadoOperacao.erro("Paciente nao encontrado.");
        }

        if (convenio == null || convenio.trim().isEmpty()) {
            paciente.complementar(idade, telefone);
        } else {
            paciente.complementar(idade, telefone, convenio);
        }
        return ResultadoOperacao.sucesso("Cadastro atualizado!", paciente);
    }

    public ResultadoOperacao<Paciente> desativarPaciente(String cpf) {
        Paciente paciente = buscarPacientePorCpf(cpf);
        if (paciente == null) {
            return ResultadoOperacao.erro("Paciente nao encontrado.");
        }
        paciente.desativar();
        return ResultadoOperacao.sucesso("Paciente desativado.", paciente);
    }

    // ---- PROFISSIONAIS ----

    public Profissional criarProfissionalMinimo(String nome, String especialidade) {
        if (especialidade.equalsIgnoreCase("fisioterapia")) return new Fisioterapeuta(nome);
        if (especialidade.equalsIgnoreCase("psicologia")) return new Psicologo(nome);
        if (especialidade.equalsIgnoreCase("nutricao")) return new Nutricionista(nome);
        return new ClinicoGeral(nome);
    }

    public ResultadoOperacao<Profissional> criarProfissionalCompleto(String nome, String especialidade,
                                                                      String registro, double valor,
                                                                      String informacaoEspecifica) {
        Profissional profissional;

        if (especialidade.equalsIgnoreCase("fisioterapia")) {
            try {
                int sessoes = Integer.parseInt(informacaoEspecifica);
                profissional = new Fisioterapeuta(nome, registro, valor, sessoes);
            } catch (NumberFormatException e) {
                return ResultadoOperacao.erro("Total de sessoes invalido.");
            }
        } else if (especialidade.equalsIgnoreCase("psicologia")) {
            profissional = new Psicologo(nome, registro, valor, informacaoEspecifica);
        } else if (especialidade.equalsIgnoreCase("nutricao")) {
            profissional = new Nutricionista(nome, registro, valor, informacaoEspecifica);
        } else {
            profissional = new ClinicoGeral(nome, registro, valor, informacaoEspecifica);
        }

        return ResultadoOperacao.sucesso("Profissional criado.", profissional);
    }

    public ResultadoOperacao<Profissional> cadastrarProfissional(Profissional profissional) {
        if (profissional == null) {
            return ResultadoOperacao.erro("Profissional invalido.");
        }
        if (!Profissional.especialidadeValida(profissional.getEspecialidade())) {
            return ResultadoOperacao.erro("Especialidade invalida!");
        }
        if (indiceProfissionais.containsKey(profissional.getNome())) {
            return ResultadoOperacao.erro("Ja existe profissional cadastrado com esse nome.");
        }

        profissionais.add(profissional);
        indiceProfissionais.put(profissional.getNome(), profissional);
        return ResultadoOperacao.sucesso("Profissional cadastrado!", profissional);
    }

    public Profissional buscarProfissionalPorNome(String nome) {
        return indiceProfissionais.get(nome);
    }

    public ResultadoOperacao<Profissional> atualizarProfissional(String nome, String registro, double valor,
                                                                 String[] dias, int quantidadeDias) {
        Profissional profissional = buscarProfissionalPorNome(nome);
        if (profissional == null) {
            return ResultadoOperacao.erro("Profissional nao encontrado.");
        }

        if (dias == null || quantidadeDias == 0) {
            profissional.atualizar(registro, valor);
        } else {
            profissional.atualizar(registro, valor, dias, quantidadeDias);
        }
        return ResultadoOperacao.sucesso("Profissional atualizado!", profissional);
    }

    public List<Profissional> filtrarProfissionaisPorEspecialidade(String especialidade) {
        List<Profissional> filtrados = new ArrayList<>();
        for (Profissional profissional : profissionais) {
            if (profissional.getEspecialidade().equalsIgnoreCase(especialidade)) {
                filtrados.add(profissional);
            }
        }
        return filtrados;
    }

    // ---- CONSULTAS ----

    public ResultadoOperacao<String> validarAgendamentoComProfissional(String cpf, String nomeProfissional,
                                                                       String data, String horario) {
        Paciente paciente = buscarPacientePorCpf(cpf);
        if (paciente == null) {
            return ResultadoOperacao.erro("Paciente nao encontrado.");
        }
        if (!paciente.isAtivo()) {
            return ResultadoOperacao.erro("Paciente inativo. Nao e possivel agendar.");
        }

        Profissional profissional = buscarProfissionalPorNome(nomeProfissional);
        if (profissional == null) {
            return ResultadoOperacao.erro("Profissional nao encontrado.");
        }
        if (profissional.getValorConsulta() == 0) {
            return ResultadoOperacao.erro("Profissional sem valor definido. Nao pode agendar.");
        }

        String diaSemana = descobrirDiaSemana(data);
        if (!profissional.atendeNoDia(diaSemana)) {
            return ResultadoOperacao.erro("Profissional nao atende nesse dia.");
        }

        if (temConflito(nomeProfissional, data, horario)) {
            String sugestao = sugerirHorario(nomeProfissional, data);
            if (sugestao.equals("")) {
                return ResultadoOperacao.erro("Horario ocupado. Nenhum horario disponivel nesse dia.");
            }
            return ResultadoOperacao.sucesso("Horario ocupado.", sugestao);
        }

        return ResultadoOperacao.sucesso("Horario disponivel.", "");
    }

    public ResultadoOperacao<Consulta> agendarConsulta(String cpf, String nomeProfissional,
                                                       String data, String horario, String tipo) {
        ResultadoOperacao<String> validacao = validarAgendamentoComProfissional(cpf, nomeProfissional, data, horario);
        if (!validacao.isSucesso()) {
            return ResultadoOperacao.erro(validacao.getMensagem());
        }
        if (validacao.getDado() != null && !validacao.getDado().equals("")) {
            return ResultadoOperacao.erro("Horario ocupado. Use a sugestao antes de confirmar.");
        }

        Consulta consulta;
        if (tipo == null || tipo.trim().isEmpty()) {
            consulta = new Consulta(cpf, nomeProfissional, data, horario);
        } else {
            consulta = new Consulta(cpf, nomeProfissional, data, horario, tipo);
        }
        consultas.add(consulta);
        return ResultadoOperacao.sucesso("Consulta agendada com sucesso!", consulta);
    }

    public ResultadoOperacao<Consulta> agendarConsultaIgnorandoSugestaoValidada(String cpf, String nomeProfissional,
                                                                                 String data, String horario,
                                                                                 String tipo) {
        Consulta consulta;
        if (tipo == null || tipo.trim().isEmpty()) {
            consulta = new Consulta(cpf, nomeProfissional, data, horario);
        } else {
            consulta = new Consulta(cpf, nomeProfissional, data, horario, tipo);
        }
        consultas.add(consulta);
        return ResultadoOperacao.sucesso("Consulta agendada com sucesso!", consulta);
    }

    public ResultadoOperacao<Consulta> agendarConsultaPorEspecialidade(String cpf, String especialidade,
                                                                       String data, String horario) {
        Paciente paciente = buscarPacientePorCpf(cpf);
        if (paciente == null) {
            return ResultadoOperacao.erro("Paciente nao encontrado.");
        }
        if (!paciente.isAtivo()) {
            return ResultadoOperacao.erro("Paciente inativo. Nao e possivel agendar.");
        }

        String diaSemana = descobrirDiaSemana(data);
        Profissional escolhido = null;
        for (Profissional profissional : profissionais) {
            if (profissional.getEspecialidade().equalsIgnoreCase(especialidade)
                    && profissional.getValorConsulta() > 0
                    && profissional.atendeNoDia(diaSemana)
                    && !temConflito(profissional.getNome(), data, horario)) {
                escolhido = profissional;
                break;
            }
        }

        if (escolhido == null) {
            return ResultadoOperacao.erro("Nenhum profissional disponivel.");
        }

        Consulta consulta = new Consulta(cpf, escolhido.getNome(), data, horario);
        consultas.add(consulta);
        return ResultadoOperacao.sucesso("Consulta agendada com " + escolhido.getNome() + "!", consulta);
    }

    public ResultadoOperacao<Consulta> cancelarConsulta(String cpf, String data, String horario,
                                                        String horaAtual, String motivo) {
        Consulta consulta = localizarConsulta(cpf, data, horario);
        if (consulta == null) {
            return ResultadoOperacao.erro("Consulta nao encontrada.");
        }
        if (consulta.getStatus().equals("realizada")) {
            return ResultadoOperacao.erro("Consulta ja realizada. Nao pode cancelar.");
        }
        if (consulta.getStatus().equals("cancelada")) {
            return ResultadoOperacao.erro("Consulta ja cancelada.");
        }

        boolean multaAplicada = deveAplicarMulta(horario, horaAtual);
        if (multaAplicada) {
            multasPorCpf.merge(cpf, 50.0, Double::sum);
        }

        String mensagemMotivo = "";
        if (motivo == null || motivo.trim().isEmpty()) {
            consulta.cancelar();
        } else {
            mensagemMotivo = consulta.cancelar(motivo);
        }

        String mensagem = "Consulta cancelada.";
        if (multaAplicada) {
            mensagem = "Multa de R$50.00 aplicada!\n" + mensagem;
        }
        if (!mensagemMotivo.equals("")) {
            mensagem = mensagemMotivo + "\n" + mensagem;
        }
        return ResultadoOperacao.sucesso(mensagem, consulta);
    }

    public ResultadoOperacao<Consulta> remarcarConsulta(String cpf, String dataOriginal, String horarioOriginal,
                                                        String novaData, String novoHorario) {
        Consulta consulta = localizarConsulta(cpf, dataOriginal, horarioOriginal);
        if (consulta == null || !consulta.getStatus().equals("agendada")) {
            return ResultadoOperacao.erro("Consulta agendada nao encontrada.");
        }

        Profissional profissional = buscarProfissionalPorNome(consulta.getNomeProfissional());
        if (profissional == null) {
            return ResultadoOperacao.erro("Profissional nao encontrado.");
        }

        String dia = descobrirDiaSemana(novaData);
        if (!profissional.atendeNoDia(dia)) {
            return ResultadoOperacao.erro("Profissional nao atende nesse dia.");
        }

        if (temConflito(consulta.getNomeProfissional(), novaData, novoHorario)) {
            return ResultadoOperacao.erro("Horario ocupado. Nao foi possivel remarcar.");
        }

        consulta.remarcar();
        Consulta novaConsulta = new Consulta(cpf, consulta.getNomeProfissional(), novaData, novoHorario, consulta.getTipo());
        consultas.add(novaConsulta);
        return ResultadoOperacao.sucesso("Consulta remarcada com sucesso!", novaConsulta);
    }

    public List<Consulta> buscarConsultasPorPaciente(String cpf) {
        List<Consulta> encontradas = new ArrayList<>();
        for (Consulta consulta : consultas) {
            if (consulta.getCpfPaciente().equals(cpf)) {
                encontradas.add(consulta);
            }
        }
        return encontradas;
    }

    public Consulta localizarConsulta(String cpf, String data, String horario) {
        for (Consulta consulta : consultas) {
            if (consulta.getCpfPaciente().equals(cpf)
                    && consulta.getData().equals(data)
                    && consulta.getHorario().equals(horario)) {
                return consulta;
            }
        }
        return null;
    }

    public boolean temConflito(String nomeProfissional, String data, String horario) {
        for (Consulta consulta : consultas) {
            if (consulta.getNomeProfissional().equals(nomeProfissional)
                    && consulta.getData().equals(data)
                    && consulta.getHorario().equals(horario)
                    && consulta.getStatus().equals("agendada")) {
                return true;
            }
        }
        return false;
    }

    public String sugerirHorario(String nomeProfissional, String data) {
        for (int h = 8; h <= 18; h++) {
            String teste;
            if (h < 10) {
                teste = "0" + h + ":00";
            } else {
                teste = h + ":00";
            }
            if (!temConflito(nomeProfissional, data, teste)) {
                return teste;
            }
        }
        return "";
    }

    public String descobrirDiaSemana(String data) {
        int dia = Integer.parseInt(data.substring(0, 2));
        int mes = Integer.parseInt(data.substring(3, 5));
        int ano = Integer.parseInt(data.substring(6, 10));

        if (mes < 3) {
            mes = mes + 12;
            ano = ano - 1;
        }

        int k = ano % 100;
        int j = ano / 100;

        int resultado = (dia + (13 * (mes + 1)) / 5 + k + k / 4 + j / 4 - 2 * j) % 7;
        if (resultado < 0) resultado = resultado + 7;

        String[] nomes = {"sabado", "domingo", "segunda", "terca", "quarta", "quinta", "sexta"};
        return nomes[resultado];
    }

    private boolean deveAplicarMulta(String horarioConsulta, String horaAtual) {
        int hConsulta = Integer.parseInt(horarioConsulta.substring(0, 2));
        int hAgora = Integer.parseInt(horaAtual.substring(0, 2));
        int diferenca = hConsulta - hAgora;
        return diferenca < 2;
    }

    // ---- ATENDIMENTOS ----

    public ResultadoOperacao<Atendimento> registrarAtendimento(int indiceConsulta, Atendimento atendimento) {
        if (indiceConsulta < 0 || indiceConsulta >= consultas.size()) {
            return ResultadoOperacao.erro("Indice invalido.");
        }
        if (!consultas.get(indiceConsulta).getStatus().equals("agendada")) {
            return ResultadoOperacao.erro("So pode registrar atendimento em consulta agendada.");
        }

        consultas.get(indiceConsulta).realizar();
        atendimentos.add(atendimento);

        Profissional profissional = buscarProfissionalPorNome(consultas.get(indiceConsulta).getNomeProfissional());
        if (profissional != null) {
            profissional.registrarEspecifico(atendimento);
        }

        return ResultadoOperacao.sucesso("Atendimento registrado. Consulta marcada como realizada.", atendimento);
    }

    // ---- PAGAMENTOS ----

    public ResultadoOperacao<Pagamento> registrarPagamentoDireto(int indiceConsulta, double valorBase,
                                                                 String tipoPagamento, int parcelas,
                                                                 String convenio) {
        if (indiceConsulta < 0 || indiceConsulta >= consultas.size()) {
            return ResultadoOperacao.erro("Indice invalido.");
        }

        ResultadoOperacao<Pagamento> criacao = criarPagamento(indiceConsulta, tipoPagamento, parcelas, convenio);
        if (!criacao.isSucesso()) {
            return criacao;
        }

        Pagamento pagamento = criacao.getDado();
        pagamento.calcularValorFinal(valorBase);
        pagamentos.add(pagamento);
        return ResultadoOperacao.sucesso("Pagamento registrado!", pagamento);
    }

    public ResultadoOperacao<Pagamento> registrarPagamentoAutomatico(int indiceConsulta, String tipoPagamento,
                                                                     int parcelas) {
        if (indiceConsulta < 0 || indiceConsulta >= consultas.size()) {
            return ResultadoOperacao.erro("Indice invalido.");
        }

        Consulta consulta = consultas.get(indiceConsulta);
        Profissional profissional = buscarProfissionalPorNome(consulta.getNomeProfissional());
        if (profissional == null) {
            return ResultadoOperacao.erro("Profissional nao encontrado.");
        }

        double valorBase = profissional.getValorConsulta();
        String convenio = "";
        if (tipoPagamento.equalsIgnoreCase("convenio")) {
            Paciente paciente = buscarPacientePorCpf(consulta.getCpfPaciente());
            if (paciente == null) {
                return ResultadoOperacao.erro("Paciente nao encontrado.");
            }
            convenio = paciente.getConvenioNome();
            if (convenio.equals("")) {
                return ResultadoOperacao.erro("Paciente sem convenio.");
            }
        }

        ResultadoOperacao<Pagamento> criacao = criarPagamento(indiceConsulta, tipoPagamento, parcelas, convenio);
        if (!criacao.isSucesso()) {
            return criacao;
        }

        Pagamento pagamento = criacao.getDado();
        pagamento.calcularValorFinal(valorBase);
        pagamentos.add(pagamento);
        return ResultadoOperacao.sucesso("Pagamento registrado! Valor base: R$" + valorBase, pagamento);
    }

    private ResultadoOperacao<Pagamento> criarPagamento(int indiceConsulta, String tipoPagamento,
                                                        int parcelas, String convenio) {
        Pagamento pagamento;
        if (tipoPagamento.equalsIgnoreCase("dinheiro")) {
            pagamento = new PagamentoDinheiro(indiceConsulta);
        } else if (tipoPagamento.equalsIgnoreCase("cartao")) {
            pagamento = new PagamentoCartao(indiceConsulta, parcelas);
        } else if (tipoPagamento.equalsIgnoreCase("convenio")) {
            pagamento = new PagamentoConvenio(indiceConsulta, convenio);
        } else {
            return ResultadoOperacao.erro("Tipo de pagamento invalido.");
        }
        return ResultadoOperacao.sucesso("Pagamento criado.", pagamento);
    }
}
