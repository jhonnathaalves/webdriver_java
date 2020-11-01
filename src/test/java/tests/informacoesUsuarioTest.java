package tests;

import static org.junit.Assert.*;

import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import suporte.Generator;
import suporte.Screenshot;
import suporte.Web;

import java.util.concurrent.TimeUnit;

@RunWith(DataDrivenTestRunner.class)
@DataLoader(filePaths = "informacoesUsuarioTest.csv")
public class informacoesUsuarioTest {

    private WebDriver navegador;

    @Rule
    public TestName test = new TestName();

    @Before
    public void setup(){
        navegador = Web.createChrome();
        //Clicar no link que possui o texto "Sign in"
        navegador.findElement(By.linkText("Sign in")).click();
        //Identificando o campo login
        //Clicar no campo com o nome "login" que esta dentro do formulario de id "signBox"
        WebElement formularioSignInBox = navegador.findElement(By.id("signinbox"));
        //Digitar no campo com o nome "login" que esta dentro do formulario signBox o texto "julio0001"
        formularioSignInBox.findElement(By.name("login")).sendKeys( "julio0001");
        //Clicar no campo com o nome "password" que esta dentro do formulario de id "signBox"
        //Digitar no campo com o nome "password" que esta dentro do formulario signBox o texto "123456"
        formularioSignInBox.findElement(By.name("password")).sendKeys( "123456");
        //Clicar no link com o texto "SIGN IN"
        formularioSignInBox.findElement(By.linkText("SIGN IN")).click();
        //Clicar em um link que possui a class "me"
        navegador.findElement(By.className("me")).click();
        //Clicar em um link que possui o texto "MORE DATA ABOUT YOU".
        navegador.findElement(By.linkText("MORE DATA ABOUT YOU")).click();
    }
    @Test
    public void testAdicionarUmaInformacaoDoUsuario(@Param(name="tipo")String tipo,@Param(name="contato")String contato,@Param(name="mensagem")String mensagemEsperada) {

        //Clicar no botao atraves do xpath '//button|@data-targe="addmoredata"]'
        navegador.findElement(By.xpath("//button[@data-target=\"addmoredata\"]")).click();
        //Identificar a popup onde esta o formulario de id addmoredata
        WebElement popupAddMoreData = navegador.findElement(By.id("addmoredata"));
        //No campo de nome "type" escolher a opcao "phone"
        WebElement campoType = popupAddMoreData.findElement(By.name("type"));
        new Select(campoType).selectByVisibleText(tipo);
        //No combo de name "contact" digitar "+551199999999"
        popupAddMoreData.findElement(By.name("contact")).sendKeys(contato);
        //Clicar no link de text "SAVE" que esta no popup
        popupAddMoreData.findElement(By.linkText("SAVE")).click();
        //Na mensagem de id "toast-container" validar que o texto e "Your contact has been added!"
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals(mensagemEsperada,mensagem);
    }

    @Test
    public void removerUmContatoDeUmUsuario() {
        //Clicar no elemento pelo seu xpath '//span[text()="+1133334444"]/following-sibling::a'
        navegador.findElement(By.xpath("//span[text()=\"+1133334444\"]/following-sibling::a")).click();
        //Confirmar a janelascript
        navegador.switchTo().alert().accept();
        //Validar que a mensagem apresentada foi "Rest in peace, dear phone!"
        WebElement mensagemPop = navegador.findElement(By.id("toast-container"));
        String mensagem = mensagemPop.getText();
        assertEquals("Rest in peace, dear phone!",mensagem);

        String screenshotArquivo = "C:\\Users\\Jhonnatha\\Documents\\Jhonnatha\\Cursos\\WebDriver\\" + Generator.dataHoraParaArquivo() + test.getMethodName() + ".png";

        Screenshot.tirar(navegador,screenshotArquivo);
        //Aguardar ate 10 segundos para que a janela desapareca
        WebDriverWait aguardar = new WebDriverWait( navegador, 10);
        aguardar.until(ExpectedConditions.stalenessOf(mensagemPop));
        //Clicar no link com texto "logout"
        navegador.findElement(By.linkText("Logout")).click();

    }

    @After
    public void tearDown(){
        //Fechar o navegador
        navegador.quit();
    }
}
