package br.edu.utfpradroaldoferreira.persistencia;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public final class ConverterLocalDateTime {

    private ConverterLocalDateTime() {
    }

    @TypeConverter
    public static Long fromLocalDateTimeToLong(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        return dateTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
    }

    @TypeConverter
    public static LocalDateTime fromLongToLocalDateTime(Long epochMilli) {
        if (epochMilli == null) {
            return null;
        }
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneOffset.UTC);
    }
}
