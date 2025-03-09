package br.edu.utfpradroaldoferreira.modelo;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Factory {
    public static List<Pessoa> gerarListaPessoas() {
        return Stream.of(
                new Pessoa("Gorete", 87, false, 1, MaoUsada.Direita, LocalDate.of(2003, 7, 19)),
                new Pessoa("Juvenal", 68, true, 3, MaoUsada.Ambas, LocalDate.of(1990, 4, 21)),
                new Pessoa("Antonio", 57, false, 4, MaoUsada.Esquerda, LocalDate.of(1978, 2, 26))
        ).collect(Collectors.toList());
    }
}
