package com.accenture.curso.repository;

import com.accenture.curso.model.Inscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
    // Métodos personalizados para nossas buscas específicas
    List<Inscricao> findByAlunoId(Long alunoId);
    List<Inscricao> findByCursoId(Long cursoId);
}
