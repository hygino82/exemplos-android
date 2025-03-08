package br.edu.utfpradroaldoferreira.persistencia;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.edu.utfpradroaldoferreira.modelo.Pessoa;

@Dao
public interface PessoaDao {

    /* Padrão DAO (Data Access Object)
       Neste padrão a classe DAO é a responsável por ler e gravar dados de
       uma origem de dados, neste caso uma tabela de um database no SQLite,
       e por relacionar estes valores com objetos da Entidade (Classe) a
       ser persistida. */

    @Insert
    long insert(Pessoa pessoa);

    @Delete
    int delete(Pessoa pessoa);

    @Update
    int update(Pessoa pessoa);

    @Query("SELECT * FROM Pessoa WHERE id=:id")
    Pessoa queryForId(long id);

    @Query("SELECT * FROM Pessoa ORDER BY nome ASC")
    List<Pessoa> queryAllAscending();

    @Query("SELECT * FROM Pessoa ORDER BY nome DESC")
    List<Pessoa> queryAllDownward();
}
