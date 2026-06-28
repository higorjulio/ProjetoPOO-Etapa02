public class HorarioDisponivel {

    private String diaSemana;
    private String turno;

    public HorarioDisponivel(String diaSemana, String turno) {
        this.diaSemana = diaSemana;
        this.turno = turno;
    }

    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }

    public String getTurno() { return turno; }
    public void setTurno(String turno) { this.turno = turno; }

    public void exibirResumo() {
        System.out.println("Dia: " + diaSemana + " | Turno: " + turno);
    }
}