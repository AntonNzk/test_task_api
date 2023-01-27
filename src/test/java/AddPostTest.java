import base.TestBase;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static data.TestSuiteTags.FUNCTIONAL;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


public class AddPostTest extends TestBase {


    @Test
    @Tag(FUNCTIONAL)
    @Severity(SeverityLevel.CRITICAL)
    @Feature("POST /posts")
    @DisplayName("Добавление поста")
    public void addPost() {
        String postBody = """
                {
                  "title": "Important post",
                  "body": "Safety First!",
                  "userId": "100"\s
                }""";

        given()
                .filter(new AllureRestAssured())
                .header("Content-type", "application/json")
                .and()
                .body(postBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)

        .body(matchesJsonSchemaInClasspath("schemas/add_post_schema.json"));
    }


    @Test
    @Tag(FUNCTIONAL)
    @Severity(SeverityLevel.CRITICAL)
    @Feature("POST /posts")
    @DisplayName("Добавление поста на неверный ресурс")
    public void addPostFailure() {
        String postBody = """
                {
                  "title": "Important post",
                  "body": "Safety First!",
                  "userId": "100"\s
                }""";
        given()
                .filter(new AllureRestAssured())
                .body(postBody)
                .when()
                .post("/post")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON);


    }

}
