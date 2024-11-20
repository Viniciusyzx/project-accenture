package com.accenture.curso.repository;

import com.accenture.curso.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    // O Spring Data JPA já nos dá vários métodos úteis!
}
