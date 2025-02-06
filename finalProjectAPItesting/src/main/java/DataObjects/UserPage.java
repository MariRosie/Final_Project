
package DataObjects;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserPage {
    public Response createUser(String userDataBody) {
        return given()
                .contentType(ContentType.JSON)
                .body(userDataBody)
                .when()
                .post("/user/createWithList")
                .then()
                .log().all()
                .extract().response();
    }


    public Response getUserByUsername(String username){
        return given()
                .contentType(ContentType.JSON)
                .pathParam("username", username)
                .when()
                .get("/user/{username}")
                .then()
                .log().all()
                .extract().response();
    }

    public Response updateUser(String username, String updatedUserBody) {
        return given()
                .contentType(ContentType.JSON)
                .pathParam("username", username)
                .body(updatedUserBody)
                .when()
                .put("/user/{username}")
                .then()
                .log().all()
                .extract().response();
    }

    public Response getUserLogin(String username, String password){
        return given()
                .contentType(ContentType.JSON)
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get("/user/login")
                .then()
                .log().all()
                .extract().response();
    }

    public Response getUserLogOut(){
        return given()
                .contentType(ContentType.JSON)
                .when()
                .get("/user/logout")
                .then()
                .log().all()
                .extract().response();
    }

    public Response deleteUserByUsername(String username){
        return given()
                .contentType(ContentType.JSON)
                .pathParam("username", username)
                .when()
                .delete("/user/{username}")
                .then()
                .log().all()
                .extract().response();
    }



}

