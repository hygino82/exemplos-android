package br.edu.utfpradroaldoferreira.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpradroaldoferreira.modelo.Anotacao;

@Dao
public interface AnotacaoDao {
    @Insert
    long insert(Anotacao anotacao);

    @Delete
    int delete(Anotacao anotacao);

    @Update
    int update(Anotacao anotacao);

    @Query("SELECT * FROM Anotacao WHERE id=:id")
    Anotacao queryForId(long id);

    @Query("SELECT * FROM Anotacao WHERE idPessoa=:idPessoa ORDER BY diaHoraCriacao DESC")
    List<Anotacao> queryForIdPessoa(long idPessoa);

    @Query("SELECT COUNT(*) FROM Anotacao WHERE idPessoa=:idPessoa")
    int totalIdPessoa(long idPessoa);
}
