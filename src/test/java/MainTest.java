import PageObjects.FormsPage;
import PageObjects.LoginPage;
import com.jayway.jsonpath.JsonPath;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.jayway.restassured.RestAssured.*;

public class MainTest extends BaseTest {

    public final static String userEmail = "mumqrgsh@emlhub.com";
    public final static String userPassword = "reg123456";
    public final static String apiKey = "e5pK6TSzpwIx173vcVTXIBDiI03xRRfc38KXG60e";

    final String getListUri = "/document";

    @Test
    public void compareListFromApiAndFromSelenium() {
        Response res = given().header("Authorization", String.format("Bearer %s", apiKey))
                .contentType("application/json")
                .when()
                .get(getListUri)
                .then()
                .contentType(ContentType.JSON)
                .extract().response();
        String bodyText = res.getBody().asString();
        List<String> unformattedNamesFromApi = JsonPath.read(bodyText, "$.items[*].name");
        List<String> namesFromApi = new ArrayList<String>();
        for (String name : unformattedNamesFromApi) {
            namesFromApi.add(name.replaceAll("/\"/", "")
                    .replaceAll("\\.[^.]*$", ""));
        }

        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open()
                .loginAs(userEmail, userPassword);
        FormsPage formsPage = new FormsPage(driver, wait);
        List<String> namesFromSelenium = formsPage.getListOfNames();

        Assert.assertTrue(namesFromApi.containsAll(namesFromSelenium) && namesFromSelenium.containsAll(namesFromApi));
    }

    @Test
    public void checkUploadedDocumentAppeared() {
        String urlForPostMetod = "https://osp.od.nih.gov/wp-content/uploads/2014/01/Protocol_Template_05Feb2016_508.pdf";
        Response res = given().header("Authorization", String.format("Bearer %s", apiKey))
                .contentType("application/json")
                .body(String.format("{\"file\":\"%s\"}", urlForPostMetod))
                .when()
                .post(getListUri);
        String expectedLatestDocName = "Protocol_Template_05Feb2016_508";
        LoginPage loginPage = new LoginPage(driver, wait);

        String actualLatestDocName = loginPage.open()
                .loginAs(userEmail, userPassword)
                .getNameOfFirstDoc();
        Assert.assertEquals(actualLatestDocName, expectedLatestDocName);

        FormsPage formsPage = new FormsPage(driver, wait);
        formsPage.moveToTrashSelectedDoc();
    }
}
