import base.TestBase;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static data.TestSuiteTags.FUNCTIONAL;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalToIgnoringCase;


public class GetPostsTest extends TestBase {

    @Test
    @Tag(FUNCTIONAL)
    @Severity(SeverityLevel.NORMAL)
    @Feature("GET /posts")
    @DisplayName("Получение списка постов")
    public void getAllPostsInfo(){
        String firstPostTitle = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit";
        String secondPostTitle = "qui est esse";

        given()
                .filter(new AllureRestAssured())
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .assertThat()
                .log().ifValidationFails()
                .body("title[0]", equalToIgnoringCase(firstPostTitle))
                .body("title[1]", equalToIgnoringCase(secondPostTitle))

        .body(matchesJsonSchemaInClasspath("schemas/get_posts_schema.json"));
    }

}
