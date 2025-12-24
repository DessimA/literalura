package com.alura.literalura.service;

import com.alura.literalura.model.dto.DadosGutendex;
import com.alura.literalura.model.dto.LivroDTO;

import java.util.Arrays;
import java.util.Optional;

public class GutendexService {

    private final ConsumoApi consumo;
    private final ConverteDados conversor;
    private final String ENDERECO = "https://gutendex.com/books/?search=";

    public GutendexService() {
        this.consumo = new ConsumoApi();
        this.conversor = new ConverteDados();
    }
    
    // Construtor para testes (Injecao de Dependencia)
    public GutendexService(ConsumoApi consumo, ConverteDados conversor) {
        this.consumo = consumo;
        this.conversor = conversor;
    }

    public Optional<LivroDTO> buscarLivro(String nomeLivro) {
        var json = consumo.obterDados(ENDERECO + nomeLivro.replace(" ", "%20"));
        var dados = conversor.obterDados(json, DadosGutendex.class);

        if (dados.resultados().isEmpty()) {
            return Optional.empty();
        }

        var matchExato = dados.resultados().stream()
                .filter(l -> l.titulo().equalsIgnoreCase(nomeLivro))
                .findFirst();
        if (matchExato.isPresent()) return matchExato;

        var matchFrase = dados.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nomeLivro.toUpperCase()))
                .findFirst();
        if (matchFrase.isPresent()) return matchFrase;

        var palavrasChave = nomeLivro.toUpperCase().split(" ");
        var matchPalavras = dados.resultados().stream()
                .filter(l -> {
                    var tituloUpper = l.titulo().toUpperCase();
                    return Arrays.stream(palavrasChave).allMatch(palavra -> 
                        tituloUpper.matches(".*\\b" + palavra + "\\b.*")
                    );
                })
                .findFirst();
        
        return matchPalavras;
    }
}