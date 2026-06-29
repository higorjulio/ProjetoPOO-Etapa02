import java.util.List;
import java.util.Map;

public class Relatorio implements Exportavel {

    // mostra todas as consultas - agora recebe List em vez de array + contador
    public static void gerarRelatorio(List<Consulta> consultas, List<Atendimento> atendimentos) {
        System.out.println("\n=== RELATORIO GERAL ===");
        for (int i = 0; i < consultas.size(); i++) {
            System.out.println(consultas.get(i).exibirResumo());
            String diag = buscarDiagnostico(i, atendimentos);
            if (!diag.equals("")) {
                System.out.println("  Diagnostico: " + diag);
            }
            System.out.println("---");
        }
    }

    // filtra por profissional
    public static void gerarRelatorio(List<Consulta> consultas, List<Atendimento> atendimentos,
                                      String nomeProfissional) {
        System.out.println("\n=== RELATORIO - " + nomeProfissional + " ===");
        boolean achou = false;
        for (int i = 0; i < consultas.size(); i++) {
            if (consultas.get(i).getNomeProfissional().equals(nomeProfissional)) {
                System.out.println(consultas.get(i).exibirResumo());
                String diag = buscarDiagnostico(i, atendimentos);
                if (!diag.equals("")) {
                    System.out.println("  Diagnostico: " + diag);
                }
                System.out.println("---");
                achou = true;
            }
        }
        if (!achou) {
            System.out.println("Nenhuma consulta encontrada para esse profissional.");
        }
    }

    // filtra por periodo
    public static void gerarRelatorio(List<Consulta> consultas, List<Atendimento> atendimentos,
                                      String dataInicio, String dataFim) {
        System.out.println("\n=== RELATORIO - " + dataInicio + " a " + dataFim + " ===");
        for (int i = 0; i < consultas.size(); i++) {
            if (estaNoIntervalo(consultas.get(i).getData(), dataInicio, dataFim)) {
                System.out.println(consultas.get(i).exibirResumo());
                String diag = buscarDiagnostico(i, atendimentos);
                if (!diag.equals("")) {
                    System.out.println("  Diagnostico: " + diag);
                }
                System.out.println("---");
            }
        }
    }

    // resumo financeiro - multas agora vem do Map<String, Double>
    public static void gerarResumoFinanceiro(List<Consulta> consultas, List<Pagamento> pagamentos,
                                             Map<String, Double> multasPorCpf) {
        int realizadas = 0;
        int canceladas = 0;
        double totalFaturado = 0;
        double totalEmMultas = 0;

        for (Consulta c : consultas) {
            if (c.getStatus().equals("realizada")) realizadas++;
            if (c.getStatus().equals("cancelada")) canceladas++;
        }

        for (Pagamento p : pagamentos) {
            totalFaturado += p.getValorFinal();
        }

        // soma as multas guardadas no HashMap
        for (double multa : multasPorCpf.values()) {
            totalEmMultas += multa;
        }

        System.out.println("\n=== RESUMO FINANCEIRO ===");
        System.out.println("Atendimentos realizados: " + realizadas);
        System.out.println("Total faturado: R$" + Math.round(totalFaturado * 100.0) / 100.0);
        System.out.println("Cancelamentos: " + canceladas);
        System.out.println("Total em multas: R$" + Math.round(totalEmMultas * 100.0) / 100.0);

        // bonus: mostra quem tem multa pendente
        if (!multasPorCpf.isEmpty()) {
            System.out.println("\nMultas por paciente (CPF):");
            for (Map.Entry<String, Double> entrada : multasPorCpf.entrySet()) {
                System.out.println("  CPF " + entrada.getKey() + " -> R$" + entrada.getValue());
            }
        }
    }

    // agora busca na List de atendimentos
    public static String buscarDiagnostico(int indiceConsulta, List<Atendimento> atendimentos) {
        for (Atendimento a : atendimentos) {
            if (a.indiceConsulta == indiceConsulta) {
                return a.diagnostico;
            }
        }
        return "";
    }

    public static boolean estaNoIntervalo(String data, String inicio, String fim) {
        int valorData = converterDataParaNumero(data);
        int valorInicio = converterDataParaNumero(inicio);
        int valorFim = converterDataParaNumero(fim);
        return valorData >= valorInicio && valorData <= valorFim;
    }

    private static int converterDataParaNumero(String data) {
        int dia = Integer.parseInt(data.substring(0, 2));
        int mes = Integer.parseInt(data.substring(3, 5));
        int ano = Integer.parseInt(data.substring(6, 10));
        return ano * 10000 + mes * 100 + dia;
    }


    // ETAPA 14: relatorio unificado usando polimorfismo com List<Pessoa>
    public static void gerarRelatorioUnificado(List<Pessoa> pessoas) {
        System.out.println("\n=== RELATORIO UNIFICADO DE PESSOAS ===");
        if (pessoas.isEmpty()) {
            System.out.println("Nenhuma pessoa cadastrada.");
            return;
        }

        for (Pessoa pessoa : pessoas) {
            // Ligacao dinamica: o Java executa o exibirResumo() da classe real
            // do objeto, seja Paciente, ClinicoGeral, Psicologo etc.
            pessoa.exibirResumo();

            // Casting seguro: antes de acessar membros especificos, usamos instanceof.
            if (pessoa instanceof Paciente) {
                Paciente paciente = (Paciente) pessoa;
                String status = paciente.isAtivo() ? "ativo" : "inativo";
                System.out.println("  Categoria: Paciente | Status cadastral: " + status);
            } else if (pessoa instanceof Profissional) {
                Profissional profissional = (Profissional) pessoa;
                System.out.println("  Categoria: Profissional | Especialidade: "
                        + profissional.getEspecialidade());
            }
            System.out.println("---");
        }
    }

    // ETAPA 14: relatorio especifico usando List<Pagamento>
    public static void gerarRelatorioPagamentos(List<Pagamento> pagamentos) {
        System.out.println("\n=== RELATORIO DE PAGAMENTOS ===");
        if (pagamentos.isEmpty()) {
            System.out.println("Nenhum pagamento registrado.");
            return;
        }

        double total = 0;
        for (Pagamento pagamento : pagamentos) {
            // Ligacao dinamica: chama exibirResumo() conforme o tipo real
            // PagamentoDinheiro, PagamentoCartao ou PagamentoConvenio.
            pagamento.exibirResumo();
            total += pagamento.getValorFinal();

            // Casting seguro com instanceof para detalhes especificos.
            if (pagamento instanceof PagamentoConvenio) {
                PagamentoConvenio convenio = (PagamentoConvenio) pagamento;
                System.out.println("  Detalhe: convenio " + convenio.getNomeConvenio()
                        + " com " + (int)(convenio.getPercentualCobertura() * 100)
                        + "% de cobertura.");
            } else if (pagamento instanceof PagamentoCartao) {
                PagamentoCartao cartao = (PagamentoCartao) pagamento;
                System.out.println("  Detalhe: pagamento em " + cartao.getParcelas() + " parcela(s).");
            } else if (pagamento instanceof PagamentoDinheiro) {
                System.out.println("  Detalhe: pagamento com desconto de dinheiro/pix.");
            }
            System.out.println("---");
        }

        double totalArredondado = Math.round(total * 100.0) / 100.0;
        System.out.println("Total recebido: R$" + totalArredondado);
    }

    @Override
    public void exportarDados() {
        System.out.println("Dados do relatorio exportados.");
    }
}
