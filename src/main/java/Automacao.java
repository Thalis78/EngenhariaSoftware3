import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class Automacao {
    public static String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static void main(String[] args) {
        File geckoDriverPath = new File("C:\\Users\\thali\\IdeaProjects\\EngenhariaSoftware\\drive\\geckodriver.exe");
        if (!geckoDriverPath.exists()) {
            System.out.println("Erro: GeckoDriver não encontrado no caminho especificado.");
            return;
        }

        FirefoxOptions options = new FirefoxOptions();
        System.setProperty("webdriver.gecko.driver", geckoDriverPath.getAbsolutePath());
        WebDriver driver = new FirefoxDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            System.out.println(getTimestamp() + " - INÍCIO DO TESTE: Automatização com Selenium em Java");

            System.out.println("\nTeste de login (correto e incorreto): ");

            System.out.println("\nTeste 1: Login com dados incorretos...");
            driver.get("http://31.97.22.121:8080/login");

            WebElement usernameField = driver.findElement(By.id("username"));
            WebElement passwordField = driver.findElement(By.id("password"));
            WebElement loginButton = driver.findElement(By.className("btn-acessar"));

            usernameField.sendKeys("teste senha errada");
            passwordField.sendKeys("sdasdasdas");

            loginButton.click();
            Thread.sleep(2000);

            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-message")));
            if (errorMessage != null && errorMessage.isDisplayed()) {
                System.out.println("Teste 1 - Dados incorretos: Mensagem de erro: " + errorMessage.getText());
            } else {
                System.out.println("Teste 1 - Dados incorretos: Erro de autenticação não detectado.");
            }

            Thread.sleep(3000);

            System.out.println("Teste 2: Login com dados corretos...");
            driver.get("http://31.97.22.121:8080/login");

            usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username")));
            passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")));
            loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-acessar")));

            usernameField.sendKeys("IFPI");
            passwordField.sendKeys("admin@Ifpi");

            loginButton.click();
            Thread.sleep(2000);

            wait.until(ExpectedConditions.urlContains("/indexadm"));
            Thread.sleep(3000);

            driver.get("http://31.97.22.121:8080/indexadm");

            if (driver.getCurrentUrl().contains("/indexadm")) {
                System.out.println("Teste 2 - Login correto: Acesso bem-sucedido à página '/indexadm'.");
            } else {
                System.out.println("Teste 2 - Login correto: Não foi possível acessar '/indexadm'.");
            }


            System.out.println("\nTeste de cadastro de Aluno e Documento: s");

            System.out.println("\nTeste 1: Cadastro de Aluno...");
            driver.get("http://31.97.22.121:8080/cadastraradm");

            WebElement nomeAluno = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nome")));
            WebElement identificadorAluno = driver.findElement(By.id("identificador"));
            WebElement botaoCadastrarAluno = driver.findElement(By.xpath("//button[contains(text(), 'Cadastrar')]"));

            nomeAluno.sendKeys("João Silva");
            identificadorAluno.sendKeys("12345678900");

            botaoCadastrarAluno.click();
            Thread.sleep(3000);

            WebElement mensagemCadastroAluno = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-message")));
            System.out.println("Cadastro de Aluno: " + mensagemCadastroAluno.getText());

            Thread.sleep(3000);

            System.out.println("Teste 2: Cadastro de Documento...");
            driver.get("http://31.97.22.121:8080/cadastrarDocumento");

            WebElement selectAluno = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("alunoSelect")));
            WebElement tipoDocumento = driver.findElement(By.id("tipoDocumento"));
            WebElement dataDocumento = driver.findElement(By.id("data"));
            WebElement botaoCadastrarDocumento = driver.findElement(By.xpath("//button[contains(text(), 'Cadastrar Documento')]"));

            selectAluno.sendKeys("João Silva");
            tipoDocumento.sendKeys("CPF");
            dataDocumento.sendKeys("2023-12-01");

            botaoCadastrarDocumento.click();
            Thread.sleep(3000);

            WebElement mensagemCadastroDocumento = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("toast-message")));
            System.out.println("Cadastro de Documento: " + mensagemCadastroDocumento.getText());


            System.out.println("\nNavegação entre páginas: ");

            System.out.println("\nTeste 1: Navegação para a página /index");
            driver.get("http://31.97.22.121:8080/index");
            Thread.sleep(2000);

            System.out.println("Teste 2: Navegação para a página /indexadm");
            driver.get("http://31.97.22.121:8080/indexadm");
            Thread.sleep(2000);

            System.out.println("Teste 3: Navegação para a página /buscaradm");
            driver.get("http://31.97.22.121:8080/buscaradm");
            Thread.sleep(2000);

            System.out.println("Teste 4: Navegação para a página /cadastraradm");
            driver.get("http://31.97.22.121:8080/cadastraradm");
            Thread.sleep(2000);

            System.out.println("Teste 5: Navegação para a página /cadastrarDocumento");
            driver.get("http://31.97.22.121:8080/cadastrarDocumento");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}
