package br.edu.utfpradroaldoferreira.persistencia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import br.edu.utfpradroaldoferreira.modelo.Pessoa;

@Database(entities = {Pessoa.class}, version = 2)
@TypeConverters({ConverterMaoUsada.class})
public abstract class PessoasDatabase extends RoomDatabase {

    /* O Room é uma biblioteca de persistência, que faz o papel de uma ferramenta
       ORM (Object Relational Mapping).
       Um ORM permite mapear os objetos utilizados em uma aplicação orientada a objetos,
       com os dados armazenados em um banco de dados relacional, neste caso o SQLite. */

    public abstract PessoaDao getPessoaDao();

    private static PessoasDatabase INSTANCE;

    public static PessoasDatabase getInstance(final Context context) {

        /* O uso do padrão Singleton garante que apenas uma instância da classe seja criada,
           e define um ponto único e global de acesso a ela */

        /* A Verificação Dupla de Bloqueio otimiza o desempenho no caso
           de várias threads usarem uma instância desta classe */

        if (INSTANCE == null) {

            synchronized (PessoasDatabase.class) {

                if (INSTANCE == null) {

                    /*INSTANCE = Room.databaseBuilder(context,
                            PessoasDatabase.class,
                            "pessoas.db").allowMainThreadQueries().build();*/
                    Builder builder = Room.databaseBuilder(context, PessoasDatabase.class, "pessoas.db");

                    builder.allowMainThreadQueries();

                    builder.addMigrations(new Migrar_1_2());

                    //builder.fallbackToDestructiveMigration();
                    //destrói o banco de dados caso a versão seja diferente

                    INSTANCE = (PessoasDatabase) builder.build();
                }
            }
        }

        return INSTANCE;
    }
}