import DataObjects.LombokOrder;
import DataObjects.OrderPage;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.BaseTest;

import static io.restassured.RestAssured.given;

public class OrderTests extends BaseTest {
    OrderPage orderPage = new OrderPage();

    @DataProvider(name = "OrderData")
    public Object[][] createOrderData(){
        return  new Object[][]{
                {"5", "3", "1", "placed", true},
                {"2", "3", "5", "placed", true}
        };
    }



    @Test(priority = 1, dataProvider = "OrderData")
    @Description("განათავსეთ ახალი შეკვეთა POST მეთოდის გამოყენებით, გადაამოწმეთ რომ მეთოდმა დააბრუნა სტატუს კოდი 200 თქვენს მიერ გადაცემული მონაცემებით.")
    @Severity(SeverityLevel.BLOCKER)
    public void testPlaceOrder(String id, String petId, String quantity, String status, boolean complete) {

        LombokOrder order = new LombokOrder();
        order.setId(id);
        order.setPetId(petId);
        order.setQuantity(quantity);
        order.setShipDate("2025-01-29T17:54:57.776Z");
        order.setStatus(status);
        order.setComplete(complete);

        Response response = orderPage.placeOrder(order);
        Assert.assertEquals(response.statusCode(), 200, "Status code should be 200");
        Assert.assertEquals(response.jsonPath().getString("id"), order.getId(), "id should match");
        Assert.assertEquals(response.jsonPath().getString("petId"), order.getPetId(), "petId should match");
        Assert.assertEquals(response.jsonPath().getString("quantity"), order.getQuantity(), "quantity should match");
        Assert.assertEquals(response.jsonPath().getString("status"), order.getStatus(), "status should match");
    }

    @Test(priority = 2)
    @Description("გამოიძახეთ GET მეთოდი, რომელიც დაგიბრუნებთ თქვენს მიერ გაკეთებული შეკვეთის დეტალებს.")
    @Severity(SeverityLevel.CRITICAL)
    @Link("https://docs.google.com/document/d/1odFzuhoZBy1rgkwlPPpvl0Pb6VYXRhyH_J34O1tzYT0/edit?tab=t.0")
    public void testGetID() {

        Response response = orderPage.findOrderByID(5);
        Assert.assertEquals(response.statusCode(), 200, "Status code should be 200");
        Assert.assertEquals(response.jsonPath().getString("id"), "5", "id should be 5");
        Assert.assertEquals(response.jsonPath().getString("petId"), "3", "petID should be 3");
        Assert.assertEquals(response.jsonPath().getString("quantity"), "1", "quantity should be 1");
        String status = response.jsonPath().getString("status");
        Assert.assertEquals(status, "placed", "status should be - placed");

    }

    @Test(priority = 3)
    @Description("წაშალეთ თქვენს მიერ გაკეთებული შეკვეთა და გადაამოწმეთ რომ მეთოდმა დააბრუნა სტატუს კოდი 200")
    @Severity(SeverityLevel.MINOR)
    public void testDeleteOrder() {

        Response response = orderPage.deleteOrder(5);
        Assert.assertEquals(response.statusCode(), 200, "Status code should be 200");
    }

    @Test(priority = 4)
    @Description("ისევ სცადეთ იგივე შეკვეთის წაშლა")
    public void testDeleteOrderAgain() {

        Response response = orderPage.deleteOrder(5);
        Assert.assertEquals(response.statusCode(), 404, "Status code should be 404 because Order not found");
    }

    @Test(priority = 5)
    @Description("GET მეთოდი, გადაამოწმეთ რომ მეთოდმა დააბრუნა სტატუს კოდი 404 და მესიჯი Order not found")
    public void testGetIDAgain() {

        Response response = orderPage.findOrderByID(5);
        Assert.assertEquals(response.statusCode(), 404, "Status code should be 404 because Order not found");
        System.out.println(response.getBody().asString());
        Assert.assertEquals(response.jsonPath().getString("message"), "Order not found");

    }

}
