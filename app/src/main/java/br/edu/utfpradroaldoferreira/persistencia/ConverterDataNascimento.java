package br.edu.utfpradroaldoferreira.persistencia;

import androidx.room.TypeConverter;

import java.time.LocalDate;

public class ConverterDataNascimento {
    @TypeConverter
    public static Long fromLocalDateToLong(LocalDate date) {
        if (date == null) {
            return null;
        }

        return date.toEpochDay();
    }

    @TypeConverter
    public static LocalDate fromLongToLocalDate(Long epochDay) {
        if (epochDay == null) {
            return null;
        }

        return LocalDate.ofEpochDay(epochDay);
    }
}
