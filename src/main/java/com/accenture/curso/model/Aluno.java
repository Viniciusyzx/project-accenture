package com.accenture.curso.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
public class Aluno {
    // Cada aluno tem um ID único
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Informações básicas do aluno
    private String nome;
    private String email;

    // Quando o aluno se cadastrou
    private LocalDateTime dataCadastro;

    // Lista de cursos em que está inscrito
    @OneToMany(mappedBy = "aluno")
    private Set<Inscricao> inscricoes;

    // Método útil para verificar se o email é válido (exemplo simples)
    public boolean temEmailValido() {
        return email != null && email.contains("@") && email.contains(".");
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}