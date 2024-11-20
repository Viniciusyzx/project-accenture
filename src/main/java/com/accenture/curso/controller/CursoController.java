package com.accenture.curso.controller;

import com.accenture.curso.populator.InscricaoPopulator;
import com.accenture.curso.repository.*;
import com.accenture.curso.model.*;
import com.accenture.curso.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CursoController {

    // Nossas ferramentas de trabalho
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private InscricaoRepository inscricaoRepository;
    @Autowired
    private InscricaoPopulator populator;

    // Boas-vindas! (Um toque pessoal)
    @GetMapping("/")
    public String boasVindas() {
        return "Bem-vindo ao nosso sistema de cursos online! Use nossa API para gerenciar alunos e cursos.";
    }

    // Cadastrar um novo aluno
    @PostMapping("/alunos")
    public ResponseEntity<?> cadastrarAluno(@RequestBody Aluno aluno) {
        // Vamos fazer algumas validações amigáveis
        if (!aluno.temEmailValido()) {
            return ResponseEntity.badRequest().body("Ops! Este email não parece válido.");
        }

        aluno.setDataCadastro(LocalDateTime.now());
        Aluno savedAluno = alunoRepository.save(aluno);
        return ResponseEntity.ok(populator.populateAlunoDTO(savedAluno));
    }

    // Cadastrar um novo curso
    @PostMapping("/cursos")
    public ResponseEntity<?> cadastrarCurso(@RequestBody Curso curso) {
        if (!curso.estaCompleto()) {
            return ResponseEntity.badRequest().body("Por favor, preencha todas as informações do curso!");
        }

        curso.setDataCriacao(LocalDateTime.now());
        Curso savedCurso = cursoRepository.save(curso);
        return ResponseEntity.ok(populator.populateCursoDTO(savedCurso));
    }

    // Inscrever aluno em um curso
    @PostMapping("/inscricoes")
    public ResponseEntity<?> inscreverAluno(@RequestParam Long alunoId, @RequestParam Long cursoId) {
        try {
            Aluno aluno = alunoRepository.findById(alunoId)
                    .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

            Curso curso = cursoRepository.findById(cursoId)
                    .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

            // Verificar se já existe inscrição
            boolean jaInscrito = inscricaoRepository.findByAlunoId(alunoId).stream()
                    .anyMatch(i -> i.getCurso().getId().equals(cursoId));

            if (jaInscrito) {
                return ResponseEntity.badRequest()
                        .body("Este aluno já está inscrito neste curso!");
            }

            Inscricao inscricao = new Inscricao();
            inscricao.setAluno(aluno);
            inscricao.setCurso(curso);
            inscricao.setDataInscricao(LocalDateTime.now());

            inscricaoRepository.save(inscricao);
            return ResponseEntity.ok("Inscrição realizada com sucesso!");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Listar cursos de um aluno
    @GetMapping("/alunos/{alunoId}/cursos")
    public ResponseEntity<?> listarCursosDoAluno(@PathVariable Long alunoId) {
        if (!alunoRepository.existsById(alunoId)) {
            return ResponseEntity.badRequest().body("Aluno não encontrado!");
        }

        List<Inscricao> inscricoes = inscricaoRepository.findByAlunoId(alunoId);
        List<CursoDTO> cursos = inscricoes.stream()
                .map(i -> populator.populateCursoDTO(i.getCurso()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(cursos);
    }

    // Listar alunos de um curso
    @GetMapping("/cursos/{cursoId}/alunos")
    public ResponseEntity<?> listarAlunosDoCurso(@PathVariable Long cursoId) {
        if (!cursoRepository.existsById(cursoId)) {
            return ResponseEntity.badRequest().body("Curso não encontrado!");
        }

        List<Inscricao> inscricoes = inscricaoRepository.findByCursoId(cursoId);
        List<AlunoDTO> alunos = inscricoes.stream()
                .map(i -> populator.populateAlunoDTO(i.getAluno()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(alunos);
    }
}
