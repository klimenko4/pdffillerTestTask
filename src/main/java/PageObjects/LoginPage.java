package PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BasePage {


    public static final String loginPageUrl = "https://www.pdffiller.com/en/login.htm";


    @FindBy(id = "form-login-email")
    private WebElement userNameInput;

    @FindBy(id = "form-login-password")
    private WebElement passwordInput;

    @FindBy(id = "form-login-submit")
    private WebElement submitButton;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public LoginPage open() {
        driver.get(loginPageUrl);
        return new LoginPage(driver, wait);
    }

    public FormsPage loginAs(String login, String password) {
        this.userNameInput.sendKeys(login);
        this.passwordInput.sendKeys(password);
        this.submitButton.click();
        return new FormsPage(driver, wait);
    }
}
