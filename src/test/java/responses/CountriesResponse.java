package responses;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import static io.restassured.RestAssured.given;

public class CountriesResponse extends BaseResponse {
    public static Response getCountries() {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("https://api.covid19api.com/countries");
    }

    public static Response postWebhook(JSONObject webhookRequest) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(webhookRequest.toString())
                .when()
                .post("https://api.covid19api.com/webhook");
    }

    public static Response getDayOneConfirmed(String country) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("country", country)
                .when()
                .get("https://api.covid19api.com/dayone/country/{country}/status/confirmed");
    }

    public static Response getByCountryConfirmed(String country) {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("country", country)
                .when()
                .get("https://api.covid19api.com/country/{country}/status/confirmed");
    }

    public static Response getStats() {
        return given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("https://api.covid19api.com/stats");
    }

}
