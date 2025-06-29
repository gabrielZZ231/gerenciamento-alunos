package br.com.gerenciamento.acceptance;

import br.com.gerenciamento.repository.UsuarioRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoginAcceptanceTest {

    private WebDriver driver;
    private WebDriverWait wait;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Before
    public void setUp() {
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @Test
    public void navegarParaPaginaDeCadastroCorretamente() {
        driver.get("http://localhost:8080/");

        WebElement linkCadastro = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Clique aqui para se cadastrar")));
        linkCadastro.click();

        wait.until(ExpectedConditions.urlContains("/cadastro"));
        assertEquals("http://localhost:8080/cadastro", driver.getCurrentUrl());
    }

    @Test
    public void cadastrarUsuarioComSucesso() {
        driver.get("http://localhost:8080/cadastro");

        WebElement inputEmail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("email")));
        WebElement inputUser = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("user")));
        WebElement inputSenha = driver.findElement(By.name("senha"));
        WebElement botaoCadastrar = driver.findElement(By.cssSelector("button[type='submit']"));

        inputEmail.sendKeys("gabriel@email");
        inputUser.sendKeys("gabriel");
        inputSenha.sendKeys("gabriel123");
        botaoCadastrar.click();

        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/"));
        assertEquals("http://localhost:8080/", driver.getCurrentUrl());
    }

    @Test
    public void deveCadastrarUsuarioLogarEInserirNovoAluno() {
        driver.get("http://localhost:8080/cadastro");
        String user = "admin";
        String senha = "admin1234";
        String email = user + "@email.com";

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("user"))).sendKeys(user);
        driver.findElement(By.name("senha")).sendKeys(senha);
        driver.findElement(By.name("email")).sendKeys(email);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlToBe("http://localhost:8080/"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("user"))).sendKeys(user);
        driver.findElement(By.name("senha")).sendKeys(senha);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement linkCadastrarAluno = wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Cadastrar Aluno")));
        linkCadastrarAluno.click();

        String nomeAluno = "Isaac Newton";
        String matriculaAluno = "123456";

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nome"))).sendKeys(nomeAluno);
        new Select(driver.findElement(By.id("curso"))).selectByValue("INFORMATICA");
        driver.findElement(By.id("matricula")).sendKeys(matriculaAluno);
        new Select(driver.findElement(By.id("turno"))).selectByValue("MATUTINO");
        new Select(driver.findElement(By.id("status"))).selectByValue("ATIVO");
        driver.findElement(By.xpath("//button[text()='Salvar']")).click();

        wait.until(ExpectedConditions.urlContains("/alunos-adicionados"));
        String pageSource = driver.getPageSource();
        assertTrue("nome do aluno cadastrado.", pageSource.contains(nomeAluno));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}