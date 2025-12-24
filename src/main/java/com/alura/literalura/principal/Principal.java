package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.model.dto.LivroDTO;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.GutendexService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {

    private final Scanner leitura = new Scanner(System.in);
    private final GutendexService gutendexService = new GutendexService();

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;

    public Principal(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    
                    --------------------------------
                    Escolha o numero de sua opcao:
                    --------------------------------
                    1 - Buscar livro pelo titulo
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    
                    0 - Sair                                 
                    """;

            System.out.println(menu);
            if (leitura.hasNextInt()) {
                opcao = leitura.nextInt();
                leitura.nextLine();

                switch (opcao) {
                    case 1 -> buscarLivroWeb();
                    case 2 -> listarLivrosRegistrados();
                    case 3 -> listarAutoresRegistrados();
                    case 4 -> listarAutoresVivos();
                    case 5 -> listarLivrosPorIdioma();
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("Opcao invalida");
                }
            } else {
                System.out.println("Por favor, digite um numero.");
                leitura.next();
            }
        }
    }

    private void buscarLivroWeb() {
        System.out.println("Digite o nome do livro para busca:");
        var nomeLivro = leitura.nextLine();

        Optional<LivroDTO> livroBusca = gutendexService.buscarLivro(nomeLivro);

        if (livroBusca.isPresent()) {
            var livroDTO = livroBusca.get();
            var livro = new Livro(livroDTO);
            
            if (!livroDTO.autores().isEmpty()) {
                var autorDTO = livroDTO.autores().get(0);
                var autorExistente = autorRepository.findByNome(autorDTO.nome());
                
                if (autorExistente.isPresent()) {
                    livro.setAutor(autorExistente.get());
                } else {
                    var novoAutor = new Autor(autorDTO);
                    autorRepository.save(novoAutor);
                    livro.setAutor(novoAutor);
                }
            }

            try {
                livroRepository.save(livro);
                System.out.println(livro);
            } catch (Exception e) {
                System.out.println("Nao foi possivel salvar o livro. Pode ser que ele ja exista no banco.");
            }
            
        } else {
            System.out.println("Livro nao encontrado.");
        }
    }

    private void listarLivrosRegistrados() {
        var livros = livroRepository.findAll();
        livros.stream()
                .sorted(Comparator.comparing(Livro::getTitulo))
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        var autores = autorRepository.findAll();
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNome))
                .forEach(a -> {
                    System.out.println("Autor: " + a.getNome());
                    System.out.println("Ano de nascimento: " + a.getAnoNascimento());
                    System.out.println("Ano de falecimento: " + a.getAnoFalecimento());
                    var listaLivros = a.getLivros().stream()
                            .map(Livro::getTitulo)
                            .collect(Collectors.toList());
                    System.out.println("Livros: " + listaLivros + "\n");
                });
    }

    private void listarAutoresVivos() {
        System.out.println("Insira o ano que deseja pesquisar:");
        if (leitura.hasNextInt()) {
            var ano = leitura.nextInt();
            leitura.nextLine();

            var autores = autorRepository.autoresVivosNoAno(ano);
            if (autores.isEmpty()) {
                System.out.println("Nenhum autor encontrado vivo neste ano.");
            } else {
                autores.forEach(System.out::println);
            }
        } else {
            System.out.println("Ano invalido.");
            leitura.next();
        }
    }

    private void listarLivrosPorIdioma() {
        var menuIdiomas = """
                Insira o idioma para realizar a busca:
                es - espanhol
                en - ingles
                fr - frances
                pt - portugues
                """;
        System.out.println(menuIdiomas);
        var idioma = leitura.nextLine();

        var livros = livroRepository.findByIdioma(idioma);
        if (livros.isEmpty()) {
            System.out.println("Nao existem livros nesse idioma no banco de dados.");
        } else {
            livros.forEach(System.out::println);
        }
    }
}