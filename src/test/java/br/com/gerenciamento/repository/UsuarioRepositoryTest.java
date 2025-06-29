package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testBuscarLoginFalha() {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setUser("usuario.valido");
        usuarioExistente.setSenha("senhaValida");
        usuarioExistente.setEmail("valido@email.com");
        this.usuarioRepository.save(usuarioExistente);

        Usuario usuarioEncontrado = this.usuarioRepository.buscarLogin("usuario.fantasma", "12345");

        Assert.assertNull(usuarioEncontrado);
    }

    @Test
    public void buscarUsuarioPorId() {
        Usuario usuario = new Usuario();
        usuario.setUser("Gabriel");
        usuario.setSenha("senha123");
        usuario.setEmail("gabriel@email.com.br");
        this.usuarioRepository.save(usuario);
        Long id = usuario.getId();

        Usuario usuarioEncontrado = this.usuarioRepository.findById(id).get();

        assertNotNull(usuarioEncontrado);
        assertEquals(usuario.getEmail(), usuarioEncontrado.getEmail());
    }


    @Test
    public void buscarUsuarioPorEmail() {
        Usuario usuario = new Usuario();
        usuario.setId(2L);
        usuario.setUser("Jose");
        usuario.setSenha("1234");
        usuario.setEmail("Jose@gmail.com");
        this.usuarioRepository.save(usuario);

        Usuario usuarioPorEmail = this.usuarioRepository.findByEmail("Jose@gmail.com");

        assertNotNull(usuarioPorEmail);
        assertEquals(usuario.getUser(), usuarioPorEmail.getUser());
    }

    @Test
    public void buscarUsuarioInexistente() {
        Usuario usuario = usuarioRepository.buscarLogin("Roger", "1234");
        assertNull(usuario);

    }
}
