import java.util.ArrayList;
import java.util.List;

public class Convenio {

    private String nome;
    private double percentualCobertura;
    private List<String> especialidadesCobertas;

    public Convenio(String nome, double percentualCobertura) {
        this.nome = nome;
        this.percentualCobertura = percentualCobertura;
        this.especialidadesCobertas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPercentualCobertura() {
        return percentualCobertura;
    }
    public void setPercentualCobertura(double percentualCobertura) {
        this.percentualCobertura = percentualCobertura;
    }

    public List<String> getEspecialidadesCobertas() {
        return especialidadesCobertas;
    }

    public void adicionarEspecialidade(String especialidade) {
        especialidadesCobertas.add(especialidade);
    }

    public boolean cobreEspecialidade(String especialidade) {
        return especialidadesCobertas.contains(especialidade);
    }

    public void exibirResumo() {
        System.out.println("Convenio: " + nome + " | Cobertura: " + (int)(percentualCobertura * 100) + "%");
        System.out.print("Especialidades cobertas: ");
        for (String esp : especialidadesCobertas) {
            System.out.print(esp + " ");
        }
        System.out.println();
    }
}