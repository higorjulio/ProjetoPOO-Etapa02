public class ResultadoOperacao<T> {
    private boolean sucesso;
    private String mensagem;
    private T dado;

    public ResultadoOperacao(boolean sucesso, String mensagem, T dado) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.dado = dado;
    }

    public static <T> ResultadoOperacao<T> sucesso(String mensagem, T dado) {
        return new ResultadoOperacao<>(true, mensagem, dado);
    }

    public static <T> ResultadoOperacao<T> erro(String mensagem) {
        return new ResultadoOperacao<>(false, mensagem, null);
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public T getDado() {
        return dado;
    }
}
