package PageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class FormsPage extends BasePage{

    @FindBy(css = ".mf-doc__name span")
    private WebElement docNames;

    @FindBy(css = ".i-trash-bin")
    private WebElement trashIcon;

    public FormsPage(WebDriver driver, WebDriverWait wait) {
        super(driver,wait);
        PageFactory.initElements(driver, this);
    }

    public List<String> getListOfNames() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".react-contextmenu-wrapper")));


        List<WebElement> documentList = driver.findElements(By.cssSelector(".mf-doc__name span"));
        List<String> listOfNames = new ArrayList<String>();
        for(WebElement el: documentList){
            listOfNames.add(el.getText());
        }
        return listOfNames;
    }

    public String getNameOfFirstDoc(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".react-contextmenu-wrapper")));

        return driver.findElement(By.cssSelector(".react-contextmenu-wrapper:nth-child(1) .mf-doc__name")).getText();
    }

    public FormsPage moveToTrashSelectedDoc() {
        this.trashIcon.click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".g-loader__layer")));
        return new FormsPage(driver,wait);
    }

}
