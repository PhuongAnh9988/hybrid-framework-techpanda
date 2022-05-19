package commons;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	private static final long longTimeout = 0;

	/* Web browser */
	// 1 Access Modifier: public
	// 2 Kiểu trả về của hàm
	// 2.1 void: Action (Click/clear/sendkey/open/...)
	// 2.2 Lấy dữ liệu ra: #void: String/int/boolean/Object/...
	// getXXX: getCurrentUrl/getTitle/getCssValue/getText/getAttribute/getSize/...
	// 3 Tên hàm:
	// 3.1 Tính năng này dùng làm gì -> tên
	// 3.2 Coverntion (Camel Case)
	// homNayMinhDiNhau()
	// 4 Tham số truyền vào
	// Khai báo 1 biến bên trong: Kiểu dữ liệu - tên dữ liệu: String addressName, String pageUrl
	// 5 Kiểu dữ liệu trả về trong hàm - tương ứng với kiểu trả về của hàm
	// 5.1 void: ko cần return
	// 5.2 #void thì return đúng capacity 
	
	// Note:
	// 1 Tham số đầu tiên bắt buộc của 1 hàm tương tác với Web Browser là "WebDriver driver"
	 /**
	  * Open any page Url 
	  * 
	  * @author Phuong Anh - TECH
	  * @param driver
	  * @param pageUrl
	  */
	public void openPageUrl(WebDriver driver, String pageUrl) {
		driver.get(pageUrl);
	}
	
	public String getPageUrl(WebDriver driver) {
		return driver.getTitle();
	}
	
	public String getPageTitle(WebDriver driver) {
		return driver.getPageSource();
	}
	
	public String getPageSourceCode(WebDriver driver) {
		return driver.getPageSource(); 
	}
	
	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}
	
	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}
	
	public void refreshCurrentPage(WebDriver driver) {
		driver.navigate().refresh();
	}
	
	public Alert waitForAlertPreaence(WebDriver driver) {
		return new WebDriverWait(driver, longTimeout).until(ExpectedConditions.alertIsPresent());
		
	}
	
	public void acceptAlert(WebDriver driver) {
		waitForAlertPreaence(driver).accept();
	}
	
	public void cancelAlert(WebDriver driver) {
		waitForAlertPresence(driver).dismiss();
	}
	
	private Alert waitForAlertPresence(WebDriver driver) {
		// TODO Auto-generated method stub
		return null;
	}

	public void sendkeyToAlert(WebDriver driver, String valueToSendkey) {
		waitForAlertPresence(driver).sendKeys(valueToSendkey);
	}
	
	public String getAlertText(WebDriver driver) {
		return waitForAlertPresence(driver).getText();
	}
	
	public void switchTpWindowByID(WebDriver driver, String expectedID) {
		Set<String> allTabIDs = driver.getWindowHandles();
		for (String id : allTabIDs) {
			if (!id.equals(expectedID)) {
				driver.switchTo().window(id);
				break;
			}
		}
	}
	
	public void switchToWindowByTitle(WebDriver driver, String expectedTitle) {
		Set<String> allTabIDs = driver.getWindowHandles();
		for (String id : allTabIDs) {
			driver.switchTo().window(id);
			String actualTitle = driver.getTitle();
			if (actualTitle.equals(expectedTitle)) {
				break;
			}
		}
	}
	
	
	/* Web Element */
	// Note:
	// 1 Tham số đầu tiên bắt buộc của 1 hàm tương tác với Web Browser là "WebDriver driver"
	// 2 Tham số thứ hai bắt buộc của 1 hàm tương tác với Web Element là "String locator"
	// Locator: Thao tác với element nào
	// Xpath/Css/Id/Name/Class/...
	// Text/Xpath Axes 
	// 3 Những step nào có dùng element lại >= 2 lần trở lên -> Khai báo 1 biến local
	// 4 Verify true/false
	// Các hàm trả về kiểu boolean luôn có tiền tố là is
	// isDisplayed/ isEnabled/ isSelected/isMultile
	
	public By getByXpath(String locator) {
		return By.xpath(locator);
	}
	
	public WebElement getWebElement(WebDriver driver, String locator) {
		return driver.findElement(getByXpath(locator));
	}
	
	public List<WebElement> getListElement(WebDriver driver, String locator) {
		return driver.findElements(getByXpath(locator));
	}
	
	public void clickToElement(WebDriver driver, String locator) {
		getWebElement(driver, locator).click();
	}
	
	public void sendkeyToElement(WebDriver driver, String locator, String valueToInput) {
		WebElement element = getWebElement(driver, locator);
		element.clear();
		element.sendKeys(valueToInput);
	}
	
	public void selectItemInDefaultDropdown(WebDriver driver, String locator, String itemText) {
		Select select = new Select(getWebElement(driver, locator));
		select.selectByVisibleText(itemText);
	}
	
	public String getFirstSelectedTextItem(WebDriver driver, String locator) {
		Select select = new Select(getWebElement(driver, locator));
		return select.getFirstSelectedOption().getText();
	}
	
	public boolean isDropdownMultiple(WebDriver driver, String locator) {
		Select select = new Select(getWebElement(driver, locator));
		return select .isMultiple();
	}
	
	public void selectItemInCustomDropdown(WebDriver driver, String parentXpath, String childXpath, String expectedItemText) {
		getWebElement(driver, parentXpath).click();
		sleepInsecond(2);
		
		List<WebElement> childItems = new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childXpath)));
		for (WebElement tempElement : childItems) {
			if (tempElement.getText().trim().equals(expectedItemText)) {
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", tempElement);
				sleepInsecond(1);
				tempElement.click();
				sleepInsecond(1);
				break;
			}	
		}
	}
	public void sleepInsecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}










