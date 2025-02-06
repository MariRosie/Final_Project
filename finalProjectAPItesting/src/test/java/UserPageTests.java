
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import utils.BaseTest;
import DataObjects.UserPage;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserPageTests extends BaseTest {
    UserPage userPage = new UserPage();

    @Test(priority = 1)
    @Description("შექმენით მომხმარებელი POST მეთოდის გამოყენებით, მეთოდმა უნდა დააბრუნოს სტატუს კოდი 200")
    @Link("https://docs.google.com/document/d/1odFzuhoZBy1rgkwlPPpvl0Pb6VYXRhyH_J34O1tzYT0/edit?tab=t.0")
    public void testCreateUser() {
        String userDataBody = "[{\n" +
                "    \"id\": 12,\n" +
                "    \"username\": \"Mariamo12\",\n" +
                "    \"firstName\": \"Mariam\",\n" +
                "    \"lastName\": \"Rosie\",\n" +
                "    \"email\": \"m@gmail.com\",\n" +
                "    \"password\": \"PasPas@1\",\n" +
                "    \"phone\": \"598135365\",\n" +
                "    \"userStatus\": 3\n" +
                "}]";


        Response response = userPage.createUser(userDataBody);
        System.out.println(response.prettyPrint());
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");
    }


    @Test(priority = 2)
    @Description("გამოიძახეთ GET მეთოდი")
    @Link("https://docs.google.com/document/d/1odFzuhoZBy1rgkwlPPpvl0Pb6VYXRhyH_J34O1tzYT0/edit?tab=t.0")
    public void testGetUserByUsername() {
        Response response = userPage.getUserByUsername("Mariamo12");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("username"), "Mariamo12");
        Assert.assertEquals(response.jsonPath().getString("firstName"), "Mariam");
        Assert.assertEquals(response.jsonPath().getString("lastName"), "Rosie");
        Assert.assertEquals(response.jsonPath().getString("email"), "m@gmail.com");
        Assert.assertEquals(response.jsonPath().getString("password"), "PasPas@1");
        Assert.assertEquals(response.jsonPath().getString("phone"), "598135365");
        Assert.assertEquals(response.jsonPath().getInt("userStatus"), 3);

    }

    @Test(priority = 3)
    @Description("განაახლეთ თქვენს მიერ შექმნილი მომხმარებლის მონაცემები")
    public void testUpdateUser() {
        String updatedUserBody = "{\n" +
                "    \"id\": 12,\n" +
                "    \"username\": \"Mariamo12\",\n" +
                "    \"firstName\": \"UpdatedMariam\",\n" +
                "    \"lastName\": \"UpdatedRosie\",\n" +
                "    \"email\": \"updated@gmail.com\",\n" +
                "    \"password\": \"PasPas@1\",\n" +
                "    \"phone\": \"1234567890\",\n" +
                "    \"userStatus\": 3\n" +
                "}";

        Response response = userPage.updateUser("Mariamo12", updatedUserBody);
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200");

    }
    @Test(priority = 4)
    @Description("გამოიძახეთ Get მეთოდი თქვენს მიერ შექმნილი username - ის გამოყენებით.")
    public void testGetUser() {
        Response response = userPage.getUserByUsername("Mariamo12");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("username"), "Mariamo12");
        Assert.assertEquals(response.jsonPath().getString("firstName"), "UpdatedMariam");
        Assert.assertEquals(response.jsonPath().getString("lastName"), "UpdatedRosie");
        Assert.assertEquals(response.jsonPath().getString("email"), "updated@gmail.com");
        Assert.assertEquals(response.jsonPath().getString("phone"), "1234567890");

    }

    @Test(priority = 5)
    @Description("გამოიძახეთ GET მეთოდი, რომლის საშუალებითაც მომხმარებელს დაალოგინებთ სისტემაში")
    public void testGetLogin() {
        Response response = userPage.getUserLogin("Mariamo12","PasPas@1");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertNotNull(response.jsonPath().getString("message"),"message should be: logged in user session");
    }

    @Test(priority = 6)
    @Description("გამოიძახეთ GET მეთოდი, რომლის საშუალებითაც მომხმარებელს გამოიყვანთ სისტემიდან")
    public void testGetLogOut() {
        Response response = userPage.getUserLogOut();
        Assert.assertEquals(response.getStatusCode(), 200);
        String responceBody = response.getBody().asString();
        System.out.println(responceBody);
        Assert.assertEquals(response.jsonPath().getString("message"),"ok");
    }

    @Test(priority = 7)
    @Description("წაშალეთ თქვენს მიერ შექმნილი მომხმარებელი DELETE მეთოდის გამოძახებით")
    public void testDeleteUserByUsername() {
        Response response = userPage.getUserByUsername("Mariamo12");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.jsonPath().getString("username"),"Mariamo12"," deleted username");
        String responceBody = response.getBody().asString();
        System.out.println(responceBody);



    }
}

