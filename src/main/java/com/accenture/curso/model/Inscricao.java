package com.accenture.curso.model;

import com.accenture.curso.dto.CursoDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Quem se inscreveu
    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    // Em qual curso
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    // Quando aconteceu a inscrição
    private LocalDateTime dataInscricao;

    // Método para verificar se a inscrição é recente (últimas 24h)
    public boolean isInscricaoRecente() {
        return dataInscricao.plusDays(1).isAfter(LocalDateTime.now());
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public Curso getCurso() {
        return curso;
    }

    public LocalDateTime getDataInscricao() {
        return dataInscricao;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setDataInscricao(LocalDateTime dataInscricao) {
        this.dataInscricao = dataInscricao;
    }
}
