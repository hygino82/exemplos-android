package br.edu.utfpradroaldoferreira;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Factory {
    public static List<Pessoa> gerarListaPessoas() {
        return Stream.of(
                new Pessoa("Gorete", 87, false, 1, MaoUsada.Direita),
                new Pessoa("Juvenal", 68, true, 3, MaoUsada.Ambas),
                new Pessoa("Antonio", 57, false, 4, MaoUsada.Esquerda)
        ).collect(Collectors.toList());
    }
}
