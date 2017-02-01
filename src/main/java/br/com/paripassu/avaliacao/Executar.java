package br.com.paripassu.avaliacao;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

public class Executar {
	
	private final AppDriver driver;
	
	public Executar(String pathFirefox) {
		driver = new AppDriver(pathFirefox);
}
	
	public void iniciar() {
		efetuarLogin();
		driver.abrirQuestionario();
		preencherIdentificacao();
		proximoPasso();
		preencherQuestoes();
		salvarEFinalizar();
	}
	
	private void salvarEFinalizar() {
		WebElement salvar = driver.getElementByXPath("//*[@ng-click='saveAndFinish()']");
		salvar.click();
	}

	private void preencherQuestoes() {
		String xPath = "//*[@ng-model='questao.value']";
		driver.aguardarElementoXPath(xPath);
		List<WebElement> elements = driver.getElementsByXPath(xPath);
		elements.get(0).sendKeys("Foi indicação de um colaborador da empresa e antigo colega. Pelo que conversamos, a PariPassu é uma excelente empresa para trabalhar.");
		elements.get(1).sendKeys("Estou dividido entre o desenvolvimento e qualidade de software, porém, a garantia de que o software não apresenta problemas o torna mais confiável.");
		elements.get(2).sendKeys("Acredito que eles são o pontapé inicial para o aprimoramento, tanto no desenvolvimento de software quanto no dia a dia de uma pessoa.");
	}

	private void proximoPasso() {
		WebElement container = driver.getElement("pull-right");
		WebElement next = container.findElement(By.xpath("//*[@ng-click='next()']"));
		next.click();
	}

	private void preencherIdentificacao() {
		String xPath = "//*[@ng-model='questao.value']";
		driver.aguardarElementoXPath(xPath);
		List<WebElement> elements = driver.getElementsByXPath(xPath);
		elements.get(0).sendKeys("Filipe Teixeira Resende");
		elements.get(1).sendKeys("filipetr@gmail.com");
	}

	private void efetuarLogin() {
		if (estaLogado()) {
			return;
		}
		driver.abrirPagina("http://clicq.paripassu.com.br");
		driver.aguardarElemento("usuario");
		driver.setText("usuario", "filipetr@gmail.com");
		driver.setText("password", "abcd1234");
		driver.click("submit-login");
	}
	
	private boolean estaLogado() {
		try {
			WebElement element = driver.getElementByXPath("//*[@ng-if='hasEmpresaCadastrada']");
			return element != null;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void fecharDriver() {
		driver.close();
	}
}
