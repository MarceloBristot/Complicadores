package analisadorSemantico;

public class Identificador {
    String nome;
    int categoria;
    int tipo;
    int nivel;

    public int getDeclarado() {
        return declarado;
    }

    public void setDeclarado(int declarado) {
        this.declarado = declarado;
    }

    int declarado;

    public int getLinha() {
        return linha;
    }

    public void setLinha(int linha) {
        this.linha = linha;
    }

    int linha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
}
