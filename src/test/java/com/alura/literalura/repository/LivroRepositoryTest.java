package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LivroRepositoryTest {

    @Autowired
    private LivroRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deve buscar livros por idioma corretamente")
    void buscaPorIdioma() {
        var autor = new Autor();
        autor.setNome("Teste Autor");
        em.persist(autor);

        var livroEn = new Livro();
        livroEn.setTitulo("Book One");
        livroEn.setIdioma("en");
        livroEn.setAutor(autor);
        livroEn.setNumeroDownloads(100);
        em.persist(livroEn);

        var livroPt = new Livro();
        livroPt.setTitulo("Livro Dois");
        livroPt.setIdioma("pt");
        livroPt.setAutor(autor);
        livroPt.setNumeroDownloads(200);
        em.persist(livroPt);

        var resultados = repository.findByIdioma("en");

        assertThat(resultados).hasSize(1);
        assertThat(resultados.get(0).getTitulo()).isEqualTo("Book One");
    }

    @Test
    @DisplayName("Deve salvar livro com sucesso e persistir relacionamento")
    void persistenciaLivro() {
        var autor = new Autor();
        autor.setNome("George Orwell");
        em.persist(autor);

        var livro = new Livro();
        livro.setTitulo("1984");
        livro.setIdioma("en");
        livro.setAutor(autor);
        livro.setNumeroDownloads(5000);
        
        var livroSalvo = repository.save(livro);

        assertThat(livroSalvo.getId()).isNotNull();
        assertThat(livroSalvo.getAutor()).isEqualTo(autor);
    }
}
