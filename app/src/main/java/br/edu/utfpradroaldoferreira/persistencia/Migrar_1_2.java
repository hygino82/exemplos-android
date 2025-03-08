package br.edu.utfpradroaldoferreira.persistencia;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class Migrar_1_2 extends Migration {

    public Migrar_1_2() {
        super(1, 2);
    }

    @Override
    public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL("CREATE TABLE IF NOT EXISTS `Pessoa_provisorio` (" +
                "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "`nome` TEXT NOT NULL, " +
                "`media` INTEGER NOT NULL, " +
                "`bolsista` INTEGER NOT NULL, " +
                "`tipo` INTEGER NOT NULL, " +
                "`maoUsada` INTEGER)");

        database.execSQL("INSERT INTO Pessoa_provisorio(id, nome, media, bolsista, tipo, maoUsada) " +
                "SELECT id, nome, media, bolsista, tipo," +
                "CASE " +
                "WHEN maoUsada = 'Direita' THEN 0 " +
                "WHEN maoUsada = 'Esquerda' THEN 1 " +
                "WHEN maoUsada = 'Ambas' THEN 2 " +
                "ELSE -1 " +
                "END " +
                "FROM Pessoa");

        database.execSQL("DROP TABLE Pessoa");

        database.execSQL("ALTER TABLE Pessoa_provisorio RENAME TO Pessoa");

        database.execSQL("CREATE INDEX IF NOT EXISTS `index_Pessoa_nome` ON `Pessoa` (`nome`)");
    }
}
