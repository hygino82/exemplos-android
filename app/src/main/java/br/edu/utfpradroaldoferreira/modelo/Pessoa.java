package br.edu.utfpradroaldoferreira.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Comparator;

@Entity
public class Pessoa implements Cloneable {
    public static Comparator<Pessoa> ordenacaoCrescente = new Comparator<Pessoa>() {
        @Override
        public int compare(Pessoa pessoa1, Pessoa pessoa2) {
            return pessoa1.getNome().compareToIgnoreCase(pessoa2.getNome());
        }
    };

    public static Comparator<Pessoa> ordenacaoDecrescente = new Comparator<Pessoa>() {
        @Override
        public int compare(Pessoa pessoa1, Pessoa pessoa2) {
            return -1 * pessoa1.getNome().compareToIgnoreCase(pessoa2.getNome());
        }
    };
    @PrimaryKey(autoGenerate = true)
    private long id;

    @NonNull
    @ColumnInfo(index = true)
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
        return nome + '\n' +
                media + '\n' +
                bolsista + '\n' +
                tipo + '\n' +
                maoUsada;
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        /* Como esta classe só tem atributos primitivos ou imutáveis,
           o clone da classe pai já resolve */
        return super.clone();
    }
}
