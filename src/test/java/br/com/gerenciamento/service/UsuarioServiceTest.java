package br.com.gerenciamento.service;

import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {
    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void emailDuplicado() throws Exception {
        Usuario usuario1 = new Usuario();
        usuario1.setUser("marta");
        usuario1.setSenha("12345");
        usuario1.setEmail("marta@futebol.com");
        this.serviceUsuario.salvarUsuario(usuario1);

        Usuario usuarioComEmailDuplicado = new Usuario();
        usuarioComEmailDuplicado.setUser("marta");
        usuarioComEmailDuplicado.setSenha("abcde");
        usuarioComEmailDuplicado.setEmail("marta@futebol.com");

        assertThrows(Exception.class, () -> {
            this.serviceUsuario.salvarUsuario(usuarioComEmailDuplicado);
        });
    }

    @Test
    public void salvarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("usuario.simples");
        usuario.setSenha("senha123");
        usuario.setEmail("simples@email.com");
        serviceUsuario.salvarUsuario(usuario);
        Assert.assertNotNull("Não retorna nulo pois o usuario foi salvo", usuario.getId());
    }

    @Test
    public void loginComSenhaErrada() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("Romario");
        usuario.setSenha("baixinho11");
        usuario.setEmail("peixe@vasco.com");
        this.serviceUsuario.salvarUsuario(usuario);

        assertNull(this.serviceUsuario.loginUser("Romario", "senha-incorreta"));
    }

    @Test
    public void logarUsuarioInexistente() {
        assertNull(this.serviceUsuario.loginUser("Ronaldinho","1234"));
    }
}
