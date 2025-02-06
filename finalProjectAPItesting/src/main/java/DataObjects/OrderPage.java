package DataObjects;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

import static io.restassured.RestAssured.given;

public class OrderPage {

    public Response placeOrder(LombokOrder order) {
        return given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("store/order")
                .then()
                .extract().response();
    }

    public Response findOrderByID(int orderId){
        return given()
                .contentType(ContentType.JSON)
                .pathParam("orderId", orderId)
                .when()
                .get("store/order/{orderId}")
                .then()
                .extract().response();

    }

    public Response deleteOrder(int id){
        return given()
                .contentType(ContentType.JSON)
                .pathParam("orderId", id)
                .when()
                .delete("store/order/{orderId}")
                .then()
                .extract().response();
    }



}
