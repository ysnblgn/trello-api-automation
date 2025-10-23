package com.trello.pages;

import com.utils.ConfigManager;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class CardPage extends BasePage {

    public Response createCard(String cardName, String listId) {
        return given().spec(getRequestSpec())
                .queryParam("name", cardName)
                .queryParam("idList", listId)
                .when()
                .post(ConfigManager.getProperty("cardsEndpoint"));
    }

    public Response updateCardName(String cardId, String newCardName) {
        return given().spec(getRequestSpec())
                .queryParam("name", newCardName)
                .when()
                .put(ConfigManager.getProperty("cardsEndpoint") + "/" + cardId);
    }

    public Response deleteCard(String cardId) {
        return given().spec(getRequestSpec())
                .when()
                .delete(ConfigManager.getProperty("cardsEndpoint") + "/" + cardId);
    }
}
