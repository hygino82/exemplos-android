package br.edu.utfpradroaldoferreira.persistencia;

import androidx.room.TypeConverter;

import br.edu.utfpradroaldoferreira.modelo.MaoUsada;

public class ConverterMaoUsada {

    public static MaoUsada[] maoUsadas = MaoUsada.values();

    @TypeConverter
    public static int fromEnumToInt(MaoUsada maoUsada) {
        if (maoUsada == null) {
            return -1;
        }
        return maoUsada.ordinal();
    }

    @TypeConverter
    public static MaoUsada fromIntToEnum(int ordinal) {
        if (ordinal < 0) {
            return null;
        }
        return maoUsadas[ordinal];
    }
}
