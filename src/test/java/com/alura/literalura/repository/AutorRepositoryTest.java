package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AutorRepositoryTest {

    @Autowired
    private AutorRepository repository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deve retornar autor que estava vivo no ano pesquisado")
    void buscaAutoresVivosCenario1() {
        var autor = new Autor();
        autor.setNome("Machado de Assis");
        autor.setAnoNascimento(1839);
        autor.setAnoFalecimento(1908);
        em.persist(autor);

        var autores = repository.autoresVivosNoAno(1900);

        assertThat(autores).isNotEmpty();
        assertThat(autores.get(0).getNome()).isEqualTo("Machado de Assis");
    }

    @Test
    @DisplayName("Nao deve retornar autor que nasceu depois do ano pesquisado")
    void buscaAutoresVivosCenario2() {
        var autor = new Autor();
        autor.setNome("J.K. Rowling");
        autor.setAnoNascimento(1965);
        em.persist(autor);

        var autores = repository.autoresVivosNoAno(1900);

        assertThat(autores).isEmpty();
    }

    @Test
    @DisplayName("Nao deve retornar autor que morreu antes do ano pesquisado")
    void buscaAutoresVivosCenario3() {
        var autor = new Autor();
        autor.setNome("Jane Austen");
        autor.setAnoNascimento(1775);
        autor.setAnoFalecimento(1817);
        em.persist(autor);

        var autores = repository.autoresVivosNoAno(1900);

        assertThat(autores).isEmpty();
    }
}
