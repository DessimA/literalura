package com.alura.literalura.model;

import com.alura.literalura.model.dto.LivroDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 5000)
    private String titulo;

    @ManyToOne
    private Autor autor;

    private String idioma;
    private Integer numeroDownloads;

    public Livro() {}

    public Livro(LivroDTO livroDTO) {
        this.titulo = livroDTO.titulo();
        this.idioma = !livroDTO.idiomas().isEmpty() ? livroDTO.idiomas().get(0) : null;
        this.numeroDownloads = livroDTO.numeroDownloads();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        return """
                ----- LIVRO -----
                Titulo: %s
                Autor: %s
                Idioma: %s
                Downloads: %d
                -----------------
                """.formatted(titulo, (autor != null ? autor.getNome() : "Desconhecido"), idioma, numeroDownloads);
    }
}