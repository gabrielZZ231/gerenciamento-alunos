package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void contagemDeAlunosAtivos() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Felipe Luis");
        aluno1.setTurno(Turno.MATUTINO);
        aluno1.setCurso(Curso.DIREITO);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("111");
        this.alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Beto Jamaica");
        aluno2.setTurno(Turno.NOTURNO);
        aluno2.setCurso(Curso.DIREITO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("222");
        this.alunoRepository.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setNome("Jacaré");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.DIREITO);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("333");
        this.alunoRepository.save(aluno3);

        assertEquals(3, alunoRepository.findByStatusAtivo().size());
    }

    @Test
    public void contagemDeAlunosInativos() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Fausto Silva");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.ADMINISTRACAO);
        aluno1.setStatus(Status.INATIVO);
        aluno1.setMatricula("1010");
        this.alunoRepository.save(aluno1);

        assertEquals(1, alunoRepository.findByStatusInativo().size());
    }

    @Test
    public void procurarAlunosPeloSobrenome() {
        Aluno aluno1 = new Aluno();
        aluno1.setNome("Ronaldinho Ronaldo");
        aluno1.setTurno(Turno.NOTURNO);
        aluno1.setCurso(Curso.INFORMATICA);
        aluno1.setStatus(Status.ATIVO);
        aluno1.setMatricula("SOUZA01");
        this.alunoRepository.save(aluno1);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Roberto Carlos");
        aluno2.setTurno(Turno.MATUTINO);
        aluno2.setCurso(Curso.INFORMATICA);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("CARLOS01");
        this.alunoRepository.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setNome("Cristiano Ronaldo");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setStatus(Status.INATIVO);
        aluno3.setMatricula("SOUZA02");
        this.alunoRepository.save(aluno3);

        assertEquals(2, alunoRepository.findByNomeContainingIgnoreCase("ronaldo").size());
    }

    @Test
    public void testeSalvarAlunoSemTurno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Aluno Fantasma");
        aluno.setMatricula("FANTASMA01");
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        // aluno.setTurno(null); // O Turno fica nulo

        assertThrows(Exception.class, () -> {
            this.alunoRepository.save(aluno);
        });
    }
}
