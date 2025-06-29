package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AlunoController alunoController;

    @Autowired
    private ServiceAluno serviceAluno;

    @Autowired
    private AlunoRepository alunoRepository;


    @Test
    public void cadastrarAlunoComSucesso() throws Exception {
        mockMvc.perform(post("/InsertAlunos")
                        .param("nome", "Gabriel")
                        .param("turno", "MATUTINO")
                        .param("curso", "INFORMATICA")
                        .param("status", "ATIVO")
                        .param("matricula", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void cadastrarAlunoInvalido() throws Exception {
        mockMvc.perform(post("/InsertAlunos")
                        .param("nome", "Ronaldo")
                        .param("turno", "NOTURNO")
                        .param("curso", "INFORMATICA")
                        .param("status", "INATIVO"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/formAluno"))
                .andExpect(model().attributeExists("aluno"))
                .andExpect(model().hasErrors());
    }

    @Test
    public void pesquisarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setNome("Jose Fernando");
        aluno.setTurno(Turno.MATUTINO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("231324");
        serviceAluno.save(aluno);
        mockMvc.perform(post("/pesquisar-aluno")
                        .param("nome", "Jose Fernando"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/pesquisa-resultado"));
    }


    @Test
    public void testEndpointDeAlunoInexistente() throws Exception {
        mockMvc.perform(get("/alunos/detalhes/999"))
                .andExpect(status().isNotFound());
    }
}
