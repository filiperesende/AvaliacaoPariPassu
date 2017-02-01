package br.com.paripassu.avaliacao;

import java.io.File;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class AppDriver {
	
	private static final int TIMEOUT_DOIS_MINUTOS = 2 * 60;
    private static final int AGUARDAR_MEIO_SEGUNDO = 500;
    private static final int AGUARDAR_DOIS_SEGUNDOS = 2000;

	private final FirefoxDriver driver;
	
	public AppDriver(String pathFirefox) {
		File pathBinary = new File(pathFirefox);
        if (pathBinary.exists()) {
            FirefoxBinary binary = new FirefoxBinary(pathBinary);
            driver = new FirefoxDriver(binary, null);
        } else {
            driver = new FirefoxDriver();
        }
	}
	
	public void abrirPagina(String pagina) {
		driver.get(pagina);
	}
	
	public void setText(String id, String texto) {
		WebElement element = getElement(id);
		element.clear();
		element.sendKeys(texto);
	}
	
	public void click(String id) {
		WebElement element = getElement(id);
		element.click();
	}

	public WebElement getElement(String id) {
        WebElement element = buscaPeloId(id);
        if (element != null) {
            return element;
        }
        element = buscaPeloClassName(id);
        if (element != null) {
            return element;
        }
        element = buscaPeloNgModel(id);
        if (element != null) {
        	return element;
        }
        throw new NoSuchElementException("Não foi possivel encontrar o elemento na página: " + id);
    }
	
	public List<WebElement> getElementsByXPath(String xPath) {
		return driver.findElementsByXPath(xPath);
	}

	public WebElement getElementByXPath(String xPath) {
		return driver.findElementByXPath(xPath);
	}
	
	private WebElement buscaPeloNgModel(String ngModel) {
		try {
            return driver.findElementByXPath("//*[@ng-model='" + ngModel + "']");
        } catch (NoSuchElementException e) {
            return null;
        }
	}
	
	public void aguardarElementoXPath(String xPath) {
		try {
            new WebDriverWait(driver, TIMEOUT_DOIS_MINUTOS, AGUARDAR_MEIO_SEGUNDO).until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    return carregouElemento(xPath);
                }

            });
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
	}

	public void aguardarElemento(String id) {
		try {
			new WebDriverWait(driver, TIMEOUT_DOIS_MINUTOS, AGUARDAR_MEIO_SEGUNDO).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					return getElement(id) != null;
				}
				
			});
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	public void aguardarDoisSegundos() {
		try {
			new WebDriverWait(driver, 2, AGUARDAR_DOIS_SEGUNDOS).until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver driver) {
					return false;
				}
				
			});
		} catch (TimeoutException e) {
//			e.printStackTrace();
		}
	}

	private Boolean carregouElemento(String xpPath) {
		try {
			WebElement menu = driver.findElementByXPath(xpPath);
			return menu != null; 
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void abrirQuestionario() {
		aguardarElementoXPath("//*[@ng-if='hasEmpresaCadastrada']");
		WebElement menu = driver.findElementByXPath("//*[@ng-if='hasEmpresaCadastrada']");
		menu.click();
		
		WebElement menuItem = driver.findElementByXPath("//*[@ui-sref='main.aplicacao_questionario-escolha-questionario']");
		menuItem.click();
		
		aguardarElemento("list-group-item");
		WebElement testeQA = getElement("list-group-item");
		testeQA.click();
		
		aguardarDoisSegundos();
		click("item-origem");
	}
	
	private WebElement buscaPeloId(String id) {
        try {
            return driver.findElementById(id);
        } catch (NoSuchElementException e) {
            return null;
        }
    }

	private WebElement buscaPeloClassName(String className) {
        try {
            return driver.findElementByClassName(className);
        } catch (NoSuchElementException e) {
            return null;
        }
    }
	
	public void close() {
		if (driver != null) {
			driver.close();
		}
	}
}