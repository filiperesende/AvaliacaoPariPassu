package br.com.paripassu.avaliacao;

import java.io.File;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Executar {
	
	private final FirefoxDriver driver;
	
	public Executar(String pathFirefox) {
		File pathBinary = new File(pathFirefox);
        if (pathBinary.exists()) {
            FirefoxBinary binary = new FirefoxBinary(pathBinary);
            driver = new FirefoxDriver(binary, null);
        } else {
            driver = new FirefoxDriver();
        }
	}
	
	public void iniciar() {
		abrirPagina();
		efetuarLogin();
	}
	
	private void setText(String id, String texto) {
		WebElement element = getElement(id);
		element.sendKeys(texto);
	}

	private WebElement getElement(String id) {
		return driver.findElementById(id);
	}
	
	private void abrirPagina() {
		driver.get("http://clicq.paripassu.com.br");
	}
	
	private void efetuarLogin() {
		setText("usuario", "user");
		setText("password", "texto");
		click();
	}
	
	private void click() {
		WebElement element = getElement("button");
		element.click();
	}
	
	public void fecharDriver() {
		driver.close();
	}
}
