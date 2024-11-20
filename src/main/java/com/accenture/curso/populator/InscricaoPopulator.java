package com.accenture.curso.populator;

import com.accenture.curso.dto.AlunoDTO;
import com.accenture.curso.dto.CursoDTO;
import com.accenture.curso.model.Aluno;
import com.accenture.curso.model.Curso;
import org.springframework.stereotype.Component;

@Component
public class InscricaoPopulator {

    public AlunoDTO populateAlunoDTO(Aluno aluno) {
        if (aluno == null) {
            return null; // Sempre bom verificar!
        }

        AlunoDTO dto = new AlunoDTO();
        dto.setId(aluno.getId());
        dto.setNome(aluno.getNome());
        dto.setEmail(aluno.getEmail());
        dto.setDataCadastro(aluno.getDataCadastro());
        return dto;
    }

    public CursoDTO populateCursoDTO(Curso curso) {
        if (curso == null) {
            return null;
        }

        CursoDTO dto = new CursoDTO();
        dto.setId(curso.getId());
        dto.setNome(curso.getNome());
        dto.setDescricao(curso.getDescricao());
        dto.setDataCriacao(curso.getDataCriacao());
        return dto;
    }

}