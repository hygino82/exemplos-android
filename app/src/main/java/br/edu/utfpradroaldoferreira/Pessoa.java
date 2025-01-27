package br.edu.utfpradroaldoferreira;

public class Pessoa {
    private String nome;

    private int media;

    private boolean bolsista;

    private int tipo;

    private MaoUsada maoUsada;

    //não temos o construtor vazio obrigatoriamente teremos de usar esse construtor
    public Pessoa(String nome, int media, boolean bolsista, int tipo, MaoUsada maoUsada) {
        this.nome = nome;
        this.media = media;
        this.bolsista = bolsista;
        this.tipo = tipo;
        this.maoUsada = maoUsada;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getMedia() {
        return media;
    }

    public void setMedia(int media) {
        this.media = media;
    }

    public boolean isBolsista() {
        return bolsista;
    }

    public void setBolsista(boolean bolsista) {
        this.bolsista = bolsista;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public MaoUsada getMaoUsada() {
        return maoUsada;
    }

    public void setMaoUsada(MaoUsada maoUsada) {
        this.maoUsada = maoUsada;
    }

    @Override
    public String toString() {
        return  nome + '\n' +
                media + '\n' +
                bolsista + '\n' +
                tipo + '\n' +
                maoUsada + '\n';
    }
}
