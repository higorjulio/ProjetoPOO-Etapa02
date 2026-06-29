import java.util.List;
import java.util.Scanner;

public class Main {

    static ClinicaServico servico = new ClinicaServico();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n=== CLINICA VIDAPLENA ===");
            System.out.println("1 - Pacientes");
            System.out.println("2 - Profissionais");
            System.out.println("3 - Consultas");
            System.out.println("4 - Atendimentos");
            System.out.println("5 - Pagamentos");
            System.out.println("6 - Relatorios");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {
                case 1: menuPacientes(); break;
                case 2: menuProfissionais(); break;
                case 3: menuConsultas(); break;
                case 4: menuAtendimentos(); break;
                case 5: menuPagamentos(); break;
                case 6: menuRelatorios(); break;
                case 0: break;
                default: System.out.println("Opcao invalida!"); break;
            }
        }
        System.out.println("Sistema encerrado.");
    }

    // ---- PACIENTES ----

    public static void menuPacientes() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- PACIENTES ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Complementar cadastro");
            System.out.println("3 - Buscar por CPF");
            System.out.println("4 - Listar todos");
            System.out.println("5 - Desativar");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1: cadastrarPaciente(); break;
                case 2: complementarPaciente(); break;
                case 3: buscarPaciente(); break;
                case 4: listarPacientes(); break;
                case 5: desativarPaciente(); break;
                case 0: break;
                default: System.out.println("Opcao invalida!"); break;
            }
        }
    }

    public static void cadastrarPaciente() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Tipo (1-Minimo / 2-Com idade e tel / 3-Completo): ");
        int tipo = Integer.parseInt(sc.nextLine());

        Paciente paciente;
        if (tipo == 1) {
            paciente = new Paciente(nome, cpf);
        } else if (tipo == 2) {
            System.out.print("Idade: ");
            int idade = Integer.parseInt(sc.nextLine());
            System.out.print("Telefone: ");
            String telefone = sc.nextLine();
            paciente = new Paciente(nome, cpf, idade, telefone);
        } else {
            System.out.print("Idade: ");
            int idade = Integer.parseInt(sc.nextLine());
            System.out.print("Telefone: ");
            String telefone = sc.nextLine();
            System.out.print("Convenio: ");
            String convenio = sc.nextLine();
            paciente = new Paciente(nome, cpf, idade, telefone, convenio);
        }

        ResultadoOperacao<Paciente> resultado = servico.cadastrarPaciente(paciente);
        System.out.println(resultado.getMensagem());
    }

    public static void complementarPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Vai informar convenio? (1-Nao / 2-Sim): ");
        int tipo = Integer.parseInt(sc.nextLine());
        System.out.print("Idade: ");
        int idade = Integer.parseInt(sc.nextLine());
        System.out.print("Telefone: ");
        String telefone = sc.nextLine();

        String convenio = "";
        if (tipo == 2) {
            System.out.print("Convenio: ");
            convenio = sc.nextLine();
        }

        ResultadoOperacao<Paciente> resultado = servico.complementarPaciente(cpf, idade, telefone, convenio);
        System.out.println(resultado.getMensagem());
    }

    public static void buscarPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        Paciente paciente = servico.buscarPacientePorCpf(cpf);
        if (paciente == null) {
            System.out.println("Paciente nao encontrado.");
        } else {
            paciente.exibirResumo();
        }
    }

    public static void listarPacientes() {
        if (servico.getPacientes().isEmpty()) {
            System.out.println("Nenhum paciente cadastrado.");
            return;
        }
        for (Paciente paciente : servico.getPacientes()) {
            paciente.exibirResumo();
        }
    }

    public static void desativarPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        ResultadoOperacao<Paciente> resultado = servico.desativarPaciente(cpf);
        System.out.println(resultado.getMensagem());
    }

    // ---- PROFISSIONAIS ----

    public static void menuProfissionais() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- PROFISSIONAIS ---");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Atualizar cadastro");
            System.out.println("3 - Listar todos");
            System.out.println("4 - Filtrar por especialidade");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1: cadastrarProfissional(); break;
                case 2: atualizarProfissional(); break;
                case 3: listarProfissionais(); break;
                case 4: filtrarProfissionais(); break;
                case 0: break;
                default: System.out.println("Opcao invalida!"); break;
            }
        }
    }

    public static void cadastrarProfissional() {
        System.out.print("Nome: ");
        String nome = sc.nextLine();
        System.out.print("Especialidade (clinica geral/fisioterapia/psicologia/nutricao): ");
        String especialidade = sc.nextLine();

        if (!Profissional.especialidadeValida(especialidade)) {
            System.out.println("Especialidade invalida!");
            return;
        }

        System.out.print("Tipo (1-Minimo / 2-Com registro e valor): ");
        int tipo = Integer.parseInt(sc.nextLine());

        Profissional profissional;
        if (tipo == 1) {
            profissional = servico.criarProfissionalMinimo(nome, especialidade);
        } else {
            System.out.print("Registro: ");
            String registro = sc.nextLine();
            System.out.print("Valor consulta: ");
            double valor = Double.parseDouble(sc.nextLine());

            String informacaoEspecifica;
            if (especialidade.equalsIgnoreCase("fisioterapia")) {
                System.out.print("Total de sessoes previstas: ");
                informacaoEspecifica = sc.nextLine();
            } else if (especialidade.equalsIgnoreCase("psicologia")) {
                System.out.print("Abordagem terapeutica: ");
                informacaoEspecifica = sc.nextLine();
            } else if (especialidade.equalsIgnoreCase("nutricao")) {
                System.out.print("Dieta: ");
                informacaoEspecifica = sc.nextLine();
            } else {
                System.out.print("Encaminhamento: ");
                informacaoEspecifica = sc.nextLine();
            }

            ResultadoOperacao<Profissional> criacao = servico.criarProfissionalCompleto(
                    nome, especialidade, registro, valor, informacaoEspecifica);
            if (!criacao.isSucesso()) {
                System.out.println(criacao.getMensagem());
                return;
            }
            profissional = criacao.getDado();
        }

        ResultadoOperacao<Profissional> resultado = servico.cadastrarProfissional(profissional);
        System.out.println(resultado.getMensagem());
    }

    public static void atualizarProfissional() {
        System.out.print("Nome do profissional: ");
        String nome = sc.nextLine();
        System.out.print("Vai informar dias? (1-Nao / 2-Sim): ");
        int tipo = Integer.parseInt(sc.nextLine());
        System.out.print("Registro: ");
        String registro = sc.nextLine();
        System.out.print("Valor consulta: ");
        double valor = Double.parseDouble(sc.nextLine());

        String[] dias = null;
        int quantidadeDias = 0;
        if (tipo == 2) {
            System.out.print("Quantos dias? ");
            quantidadeDias = Integer.parseInt(sc.nextLine());
            dias = new String[quantidadeDias];
            for (int i = 0; i < quantidadeDias; i++) {
                System.out.print("Dia " + (i + 1) + ": ");
                dias[i] = sc.nextLine();
            }
        }

        ResultadoOperacao<Profissional> resultado = servico.atualizarProfissional(
                nome, registro, valor, dias, quantidadeDias);
        System.out.println(resultado.getMensagem());
    }

    public static void listarProfissionais() {
        if (servico.getProfissionais().isEmpty()) {
            System.out.println("Nenhum profissional cadastrado.");
            return;
        }
        for (Profissional profissional : servico.getProfissionais()) {
            profissional.exibirResumo();
        }
    }

    public static void filtrarProfissionais() {
        System.out.print("Especialidade: ");
        String especialidade = sc.nextLine();
        List<Profissional> encontrados = servico.filtrarProfissionaisPorEspecialidade(especialidade);

        if (encontrados.isEmpty()) {
            System.out.println("Nenhum profissional com essa especialidade.");
            return;
        }
        for (Profissional profissional : encontrados) {
            profissional.exibirResumo();
        }
    }

    // ---- CONSULTAS ----

    public static void menuConsultas() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- CONSULTAS ---");
            System.out.println("1 - Agendar (escolher profissional)");
            System.out.println("2 - Agendar (busca por especialidade)");
            System.out.println("3 - Cancelar");
            System.out.println("4 - Remarcar");
            System.out.println("5 - Listar todas");
            System.out.println("6 - Buscar por CPF");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1: agendarComProfissional(); break;
                case 2: agendarPorEspecialidade(); break;
                case 3: cancelarConsulta(); break;
                case 4: remarcarConsulta(); break;
                case 5: listarConsultas(); break;
                case 6: buscarConsultasPorPaciente(); break;
                case 0: break;
                default: System.out.println("Opcao invalida!"); break;
            }
        }
    }

    public static void agendarComProfissional() {
        System.out.print("CPF do paciente: ");
        String cpf = sc.nextLine();
        System.out.print("Nome do profissional: ");
        String nomeProfissional = sc.nextLine();
        System.out.print("Data (DD/MM/AAAA): ");
        String data = sc.nextLine();
        System.out.print("Horario (HH:MM): ");
        String horario = sc.nextLine();

        ResultadoOperacao<String> validacao = servico.validarAgendamentoComProfissional(
                cpf, nomeProfissional, data, horario);

        if (!validacao.isSucesso()) {
            System.out.println(validacao.getMensagem());
            return;
        }

        if (validacao.getDado() != null && !validacao.getDado().equals("")) {
            System.out.println(validacao.getMensagem());
            System.out.println("Sugestao: " + validacao.getDado());
            System.out.print("Aceita? (1-Sim / 2-Nao): ");
            int aceita = Integer.parseInt(sc.nextLine());
            if (aceita == 1) {
                horario = validacao.getDado();
            } else {
                return;
            }
        }

        String tipoConsulta = "";
        System.out.print("Informar tipo? (1-Nao / 2-Sim): ");
        int infoTipo = Integer.parseInt(sc.nextLine());
        if (infoTipo == 2) {
            System.out.print("Tipo (inicial/retorno/avaliacao): ");
            tipoConsulta = sc.nextLine();
        }

        ResultadoOperacao<Consulta> resultado = servico.agendarConsulta(
                cpf, nomeProfissional, data, horario, tipoConsulta);
        System.out.println(resultado.getMensagem());
    }

    public static void agendarPorEspecialidade() {
        System.out.print("CPF do paciente: ");
        String cpf = sc.nextLine();
        System.out.print("Especialidade: ");
        String especialidade = sc.nextLine();
        System.out.print("Data (DD/MM/AAAA): ");
        String data = sc.nextLine();
        System.out.print("Horario (HH:MM): ");
        String horario = sc.nextLine();

        ResultadoOperacao<Consulta> resultado = servico.agendarConsultaPorEspecialidade(
                cpf, especialidade, data, horario);
        System.out.println(resultado.getMensagem());
    }

    public static void cancelarConsulta() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Data (DD/MM/AAAA): ");
        String data = sc.nextLine();
        System.out.print("Horario (HH:MM): ");
        String horario = sc.nextLine();
        System.out.print("Horario atual (HH:MM): ");
        String horaAtual = sc.nextLine();

        String motivo = "";
        System.out.print("Informar motivo? (1-Nao / 2-Sim): ");
        int temMotivo = Integer.parseInt(sc.nextLine());
        if (temMotivo == 2) {
            System.out.print("Motivo: ");
            motivo = sc.nextLine();
        }

        ResultadoOperacao<Consulta> resultado = servico.cancelarConsulta(cpf, data, horario, horaAtual, motivo);
        System.out.println(resultado.getMensagem());
    }

    public static void remarcarConsulta() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        System.out.print("Data original (DD/MM/AAAA): ");
        String dataOriginal = sc.nextLine();
        System.out.print("Horario original (HH:MM): ");
        String horarioOriginal = sc.nextLine();
        System.out.print("Apenas trocar horario no mesmo dia? (1-Sim / 2-Nao): ");
        int tipo = Integer.parseInt(sc.nextLine());

        String novaData;
        if (tipo == 1) {
            novaData = dataOriginal;
        } else {
            System.out.print("Nova data (DD/MM/AAAA): ");
            novaData = sc.nextLine();
        }
        System.out.print("Novo horario: ");
        String novoHorario = sc.nextLine();

        ResultadoOperacao<Consulta> resultado = servico.remarcarConsulta(
                cpf, dataOriginal, horarioOriginal, novaData, novoHorario);
        System.out.println(resultado.getMensagem());
    }

    public static void listarConsultas() {
        if (servico.getConsultas().isEmpty()) {
            System.out.println("Nenhuma consulta.");
            return;
        }
        for (int i = 0; i < servico.getConsultas().size(); i++) {
            System.out.println("[" + i + "] " + servico.getConsultas().get(i).exibirResumo());
        }
    }

    public static void buscarConsultasPorPaciente() {
        System.out.print("CPF: ");
        String cpf = sc.nextLine();
        boolean achou = false;
        for (int i = 0; i < servico.getConsultas().size(); i++) {
            Consulta consulta = servico.getConsultas().get(i);
            if (consulta.getCpfPaciente().equals(cpf)) {
                System.out.println("[" + i + "] " + consulta.exibirResumo());
                achou = true;
            }
        }
        if (!achou) System.out.println("Nenhuma consulta encontrada.");
    }

    // ---- ATENDIMENTOS ----

    public static void menuAtendimentos() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- ATENDIMENTOS ---");
            System.out.println("1 - Registrar atendimento");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine());

            if (op == 1) registrarAtendimento();
        }
    }

    public static void registrarAtendimento() {
        System.out.print("Indice da consulta: ");
        int indiceConsulta = Integer.parseInt(sc.nextLine());
        System.out.print("Observacoes: ");
        String observacoes = sc.nextLine();
        System.out.print("Tipo de registro (1-So obs / 2-Com diagnostico / 3-Completo): ");
        int tipo = Integer.parseInt(sc.nextLine());

        Atendimento atendimento;
        if (tipo == 1) {
            atendimento = new Atendimento(indiceConsulta, observacoes);
        } else if (tipo == 2) {
            System.out.print("Diagnostico: ");
            String diagnostico = sc.nextLine();
            atendimento = new Atendimento(indiceConsulta, observacoes, diagnostico);
        } else {
            System.out.print("Diagnostico: ");
            String diagnostico = sc.nextLine();
            String[] procedimentos = new String[10];
            int quantidadeProcedimentos = 0;

            System.out.print("Como informar procedimentos? (1-Um por vez / 2-Todos de uma vez): ");
            int forma = Integer.parseInt(sc.nextLine());
            if (forma == 1) {
                String procedimento = "";
                while (!procedimento.equals("fim") && quantidadeProcedimentos < 10) {
                    System.out.print("Procedimento (ou 'fim'): ");
                    procedimento = sc.nextLine();
                    if (!procedimento.equals("fim")) {
                        procedimentos[quantidadeProcedimentos] = procedimento;
                        quantidadeProcedimentos++;
                    }
                }
            } else {
                System.out.print("Quantos? ");
                quantidadeProcedimentos = Integer.parseInt(sc.nextLine());
                if (quantidadeProcedimentos > 10) quantidadeProcedimentos = 10;
                for (int i = 0; i < quantidadeProcedimentos; i++) {
                    System.out.print("Proc " + (i + 1) + ": ");
                    procedimentos[i] = sc.nextLine();
                }
            }
            atendimento = new Atendimento(indiceConsulta, observacoes, diagnostico,
                    procedimentos, quantidadeProcedimentos);
        }

        ResultadoOperacao<Atendimento> resultado = servico.registrarAtendimento(indiceConsulta, atendimento);
        if (resultado.isSucesso()) {
            System.out.println("\n--- RESUMO ---");
            System.out.println(resultado.getDado().exibirResumo());
        }
        System.out.println(resultado.getMensagem());
    }

    // ---- PAGAMENTOS ----

    public static void menuPagamentos() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- PAGAMENTOS ---");
            System.out.println("1 - Pagamento direto");
            System.out.println("2 - Pagamento automatico");
            System.out.println("3 - Listar pagamentos");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1: pagamentoDireto(); break;
                case 2: pagamentoAutomatico(); break;
                case 3: listarPagamentos(); break;
                case 0: break;
                default: System.out.println("Opcao invalida!"); break;
            }
        }
    }

    public static void pagamentoDireto() {
        System.out.print("Indice da consulta: ");
        int indiceConsulta = Integer.parseInt(sc.nextLine());
        System.out.print("Valor base: ");
        double valorBase = Double.parseDouble(sc.nextLine());
        System.out.print("Tipo (dinheiro/cartao/convenio): ");
        String tipoPagamento = sc.nextLine();

        int parcelas = 1;
        String convenio = "";
        if (tipoPagamento.equalsIgnoreCase("cartao")) {
            System.out.print("Parcelas (1 a 6): ");
            parcelas = Integer.parseInt(sc.nextLine());
        } else if (tipoPagamento.equalsIgnoreCase("convenio")) {
            System.out.print("Nome do convenio (SaudePlus/VidaMais/BemEstar): ");
            convenio = sc.nextLine();
        }

        ResultadoOperacao<Pagamento> resultado = servico.registrarPagamentoDireto(
                indiceConsulta, valorBase, tipoPagamento, parcelas, convenio);
        exibirResultadoPagamento(resultado);
    }

    public static void pagamentoAutomatico() {
        System.out.print("Indice da consulta: ");
        int indiceConsulta = Integer.parseInt(sc.nextLine());
        System.out.print("Tipo (dinheiro/cartao/convenio): ");
        String tipoPagamento = sc.nextLine();

        int parcelas = 1;
        if (tipoPagamento.equalsIgnoreCase("cartao")) {
            System.out.print("Parcelas (1 a 6): ");
            parcelas = Integer.parseInt(sc.nextLine());
        }

        ResultadoOperacao<Pagamento> resultado = servico.registrarPagamentoAutomatico(
                indiceConsulta, tipoPagamento, parcelas);
        exibirResultadoPagamento(resultado);
    }

    public static void exibirResultadoPagamento(ResultadoOperacao<Pagamento> resultado) {
        System.out.println(resultado.getMensagem());
        if (resultado.isSucesso()) {
            double valorFinal = resultado.getDado().getValorFinal();
            double valorArredondado = Math.round(valorFinal * 100.0) / 100.0;
            System.out.println("Valor final: R$" + valorArredondado);
        }
    }

    public static void listarPagamentos() {
        if (servico.getPagamentos().isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
            return;
        }
        for (Pagamento pagamento : servico.getPagamentos()) {
            pagamento.exibirResumo();
        }
    }

    // ---- RELATORIOS ----

    public static void menuRelatorios() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- RELATORIOS ---");
            System.out.println("1 - Geral");
            System.out.println("2 - Por profissional");
            System.out.println("3 - Por periodo");
            System.out.println("4 - Resumo financeiro");
            System.out.println("5 - Relatorio unificado de pessoas");
            System.out.println("6 - Relatorio de pagamentos");
            System.out.println("0 - Voltar");
            System.out.print("Opcao: ");
            op = Integer.parseInt(sc.nextLine());

            switch (op) {
                case 1:
                    Relatorio.gerarRelatorio(servico.getConsultas(), servico.getAtendimentos());
                    break;
                case 2:
                    System.out.print("Nome do profissional: ");
                    String nome = sc.nextLine();
                    Relatorio.gerarRelatorio(servico.getConsultas(), servico.getAtendimentos(), nome);
                    break;
                case 3:
                    System.out.print("Data inicio (DD/MM/AAAA): ");
                    String inicio = sc.nextLine();
                    System.out.print("Data fim (DD/MM/AAAA): ");
                    String fim = sc.nextLine();
                    Relatorio.gerarRelatorio(servico.getConsultas(), servico.getAtendimentos(), inicio, fim);
                    break;
                case 4:
                    Relatorio.gerarResumoFinanceiro(servico.getConsultas(), servico.getPagamentos(), servico.getMultasPorCpf());
                    break;
                case 5:
                    Relatorio.gerarRelatorioUnificado(servico.getPessoas());
                    break;
                case 6:
                    Relatorio.gerarRelatorioPagamentos(servico.getPagamentos());
                    break;
                case 0: break;
                default: System.out.println("Opcao invalida!"); break;
            }
        }
    }
}
