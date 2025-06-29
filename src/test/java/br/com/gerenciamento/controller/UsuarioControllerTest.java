package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import br.com.gerenciamento.service.ServiceUsuario;
import br.com.gerenciamento.util.Util;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void testaAcessoPaginaLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void testaAcessoPaginaCadastro() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cadastro"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void testaSalvarUsuarioComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/salvarUsuario")
                        .param("user", "testuser")
                        .param("senha", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        assertNotNull(usuarioRepository.buscarLogin("testuser", Util.md5("123456")));
    }

    @Test
    public void testaLogout() throws Exception {
        Usuario usuarioLogado = new Usuario();
        usuarioLogado.setUser("userlogout");
        usuarioLogado.setId(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/logout")
                        .sessionAttr("usuarioLogado", usuarioLogado))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(request().sessionAttribute("usuarioLogado", Matchers.nullValue()));
    }
}