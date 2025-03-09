package br.edu.utfpradroaldoferreira.modelo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;

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

    private LocalDate dataNascimento;

    //não temos o construtor vazio obrigatoriamente teremos de usar esse construtor
    public Pessoa(String nome, int media, boolean bolsista, int tipo, MaoUsada maoUsada, LocalDate dataNascimento) {
        this.nome = nome;
        this.media = media;
        this.bolsista = bolsista;
        this.tipo = tipo;
        this.maoUsada = maoUsada;
        this.dataNascimento = dataNascimento;
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return nome + '\n' +
                media + '\n' +
                bolsista + '\n' +
                tipo + '\n' +
                maoUsada + '\n' +
                dataNascimento;
    }

    @NonNull
    @Override
    public Object clone() throws CloneNotSupportedException {
        /* Como esta classe só tem atributos primitivos ou imutáveis,
           o clone da classe pai já resolve */
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;

        if (dataNascimento == null && pessoa.dataNascimento != null) {
            return false;
        }

        if (dataNascimento != null && dataNascimento.equals(pessoa.dataNascimento) == false) {
            return false;
        }

        return media == pessoa.media &&
                bolsista == pessoa.bolsista &&
                tipo == pessoa.tipo &&
                nome.equals(pessoa.nome) &&//foi modificado para compara usando equals de string
                maoUsada == pessoa.maoUsada;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, media, bolsista, tipo, maoUsada, dataNascimento);
    }
}
