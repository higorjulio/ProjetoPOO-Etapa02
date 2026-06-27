public interface Agendavel {
    void agendar(String data, String hora);
    void cancelar();
    void remarcar(String novaData, String novaHora);
}