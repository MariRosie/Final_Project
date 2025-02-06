import DataObjects.LombokOrder;
import DataObjects.OrderPage;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.BaseTest;

public class NegativeTestCases extends BaseTest {

    OrderPage orderPage = new OrderPage();

    @Test(priority = 1)
    @Description("გადაეცით ყველა ბოდი პარამეტრი სწორად გარდა id-ისა, მეთოდმა დააბრუნოს სტატუს კოდი 500 და მესიჯის მნიშვნელობა something bad happened")
    @Step("შეიქმნას მოთხოვნა და დაესეტოს არასწორი ID")
    public void testIncorrectID() {

        LombokOrder requestBody = new LombokOrder();
        requestBody.setId("test");
        requestBody.setPetId("3");
        requestBody.setQuantity("1");
        requestBody.setShipDate("2025-01-29T17:54:57.776Z");
        requestBody.setStatus("placed");
        requestBody.setComplete(true);

        Response response = orderPage.placeOrder(requestBody);
        Allure.step("გაიგზავნა post მოთხოვნა შეუსაბამო ID მნიშვნელობით -test");

        Assert.assertEquals(response.statusCode(), 500, "Status code should be 200");
        Allure.step("შემოწმდა რომ სტატუსკოდი დაბრუნდა 500");
        Assert.assertEquals(response.jsonPath().getString("message"),"something bad happened");
        Allure.step("შემოწმდა რომ მესიჯი დაბრუნდა: something bad happened ");

    }

    @Test(priority = 2)
    @Description("გადაეცით ყველა ბოდი პარამეტრი სწორად გარდა petId-ისა, მეთოდმა დააბრუნოს სტატუს კოდი 500 და მესიჯის მნიშვნელობა something bad happened")
    public void testIncorrectPetID() {

        LombokOrder requestBody = new LombokOrder();
        requestBody.setId("5");
        requestBody.setPetId("test");
        requestBody.setQuantity("1");
        requestBody.setShipDate("2025-01-29T17:54:57.776Z");
        requestBody.setStatus("placed");
        requestBody.setComplete(true);

        Response response = orderPage.placeOrder(requestBody);

        Assert.assertEquals(response.statusCode(), 500, "Status code should be 500");
        Assert.assertEquals(response.jsonPath().getString("message"), "something bad happened", "Message should be 'something bad happened'");
    }

    @Test(priority = 3)
    @Description("გადაეცით ყველა ბოდი პარამეტრი სწორად გარდა quantity-ისა, დააბრუნოს სტატუს კოდი 500 და მესიჯის მნიშვნელობა something bad happened")
    public void testIncorrectQuantity() {

        LombokOrder requestBody = new LombokOrder();
        requestBody.setId("5");
        requestBody.setPetId("2");
        requestBody.setQuantity("test");
        requestBody.setShipDate("2025-01-29T17:54:57.776Z");
        requestBody.setStatus("placed");
        requestBody.setComplete(true);

        Response response = orderPage.placeOrder(requestBody);

        Assert.assertEquals(response.statusCode(), 500, "Status code should be 500");
        Assert.assertEquals(response.jsonPath().getString("message"), "something bad happened", "Message should be 'something bad happened'");
    }
}
