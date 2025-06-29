package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertThrows;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void trocaDeCurso() {

        Aluno aluno = new Aluno();
        aluno.setNome("Joana");
        aluno.setMatricula("334455");
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setTurno(Turno.NOTURNO);
        this.serviceAluno.save(aluno);

        Long AlunoID = aluno.getId();

        Aluno alunoRetorno = this.serviceAluno.getById(AlunoID);

        Assert.assertTrue(alunoRetorno.getCurso().equals(Curso.ADMINISTRACAO));

        aluno.setCurso(Curso.DIREITO);
        this.serviceAluno.save(aluno);

        alunoRetorno = this.serviceAluno.getById(AlunoID);
        Assert.assertTrue(alunoRetorno.getCurso().equals((Curso.DIREITO)));
    }

    @Test
    public void deletarApenasUmAlunoEspecifico() {
        Aluno alunoParaDeletar = new Aluno();
        alunoParaDeletar.setNome("Gabriel");
        alunoParaDeletar.setMatricula("101010");
        alunoParaDeletar.setCurso(Curso.INFORMATICA);
        alunoParaDeletar.setStatus(Status.ATIVO);
        alunoParaDeletar.setTurno(Turno.MATUTINO);
        this.serviceAluno.save(alunoParaDeletar);

        Long idParaDeletar = alunoParaDeletar.getId();

        Assert.assertEquals(this.serviceAluno.getById(idParaDeletar).getNome(), alunoParaDeletar.getNome());


        this.serviceAluno.deleteById(idParaDeletar);

        assertThrows(Exception.class, () -> {
            this.serviceAluno.getById(idParaDeletar);
        });

    }

    @Test
    public void excecaoAoSalvarComNomeMuitoCurto() {
        Aluno alunoComNomeCurto = new Aluno();
        alunoComNomeCurto.setNome("Leo");
        alunoComNomeCurto.setMatricula("234567");
        alunoComNomeCurto.setCurso(Curso.BIOMEDICINA);
        alunoComNomeCurto.setStatus(Status.ATIVO);
        alunoComNomeCurto.setTurno(Turno.MATUTINO);

        assertThrows(Exception.class, () -> {
            this.serviceAluno.save(alunoComNomeCurto);
        });

    }

    @Test
    public void excecaoAoSalvarSemCursoDefinido() {

        Aluno alunoSemCurso = new Aluno();
        alunoSemCurso.setNome("Fernando");
        alunoSemCurso.setMatricula("887766");
        alunoSemCurso.setCurso(null);
        alunoSemCurso.setStatus(Status.ATIVO);
        alunoSemCurso.setTurno(Turno.NOTURNO);

        assertThrows(Exception.class, () -> {
            this.serviceAluno.save(alunoSemCurso);
        });
    }
}
