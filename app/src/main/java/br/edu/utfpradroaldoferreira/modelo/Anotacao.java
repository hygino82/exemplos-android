package br.edu.utfpradroaldoferreira.modelo;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(foreignKeys = {@ForeignKey(entity = Pessoa.class,
        parentColumns = "id",
        childColumns = "idPessoa",
        onDelete = CASCADE)})
public final class Anotacao {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long idPessoa;
    @NonNull
    @ColumnInfo(index = true)
    private LocalDateTime diaHoraCriacao;

    @NonNull
    private String texto;


    public Anotacao(long idPessoa, @NonNull LocalDateTime diaHoraCriacao, @NonNull String texto) {
        this.idPessoa = idPessoa;
        this.diaHoraCriacao = diaHoraCriacao;
        this.texto = texto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(long idPessoa) {
        this.idPessoa = idPessoa;
    }

    @NonNull
    public LocalDateTime getDiaHoraCriacao() {
        return diaHoraCriacao;
    }

    public void setDiaHoraCriacao(@NonNull LocalDateTime diaHoraCriacao) {
        this.diaHoraCriacao = diaHoraCriacao;
    }

    @NonNull
    public String getTexto() {
        return texto;
    }

    public void setTexto(@NonNull String texto) {
        this.texto = texto;
    }
}
