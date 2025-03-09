package br.edu.utfpradroaldoferreira.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.Locale;

public final class UtilsLocalDate {
    private UtilsLocalDate() {
    }

    public static String formatLocalDateToString(LocalDate date) {
        if (date == null) {
            return null;
        }

        // DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
        //return date.format(formatter);
        //return String.format("%d/%d/%d",date.getDayOfMonth(),date.getMonthValue(),date.getYear());

        String formatPatern = DateTimeFormatterBuilder.getLocalizedDateTimePattern(FormatStyle.SHORT,
                null,
                IsoChronology.INSTANCE,
                Locale.getDefault());

        formatPatern = formatPatern.replaceAll("\\byy\\b", "yyyy");
        formatPatern = formatPatern.replaceAll("\\bM\\b", "MM");
        formatPatern = formatPatern.replaceAll("\\bd\\b", "dd");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatPatern, Locale.getDefault());
        return date.format(formatter);
    }

    public static long toMilissegundos(LocalDate date) {
        if (date == null) {
            return 0;
        }

        return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static int diferencaEmAnosParaHoje(LocalDate date) {
        return diferencaEmAnos(date, LocalDate.now());
    }

    public static int diferencaEmAnos(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        Period periodo = Period.between(date1, date2);

        return periodo.getYears();
    }
}
