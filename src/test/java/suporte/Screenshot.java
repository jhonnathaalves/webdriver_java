package suporte;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class Screenshot {
    public static void tirar(WebDriver navegador, String arquivo){
        File screeshot = ((TakesScreenshot)navegador).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screeshot, new File(arquivo));
        } catch (Exception e){
            System.out.println("Houveram problema ao copiar o arquivo para a pasta! : " + e.getMessage());
        }

    }
}
