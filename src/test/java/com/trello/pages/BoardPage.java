package com.trello.pages;

import com.utils.ConfigManager;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class BoardPage extends BasePage {

    public Response createBoard(String boardName) {
        return given().spec(getRequestSpec())
                .queryParam("name", boardName)
                .when()
                .post(ConfigManager.getProperty("boardsEndpoint"));
    }

    public Response deleteBoard(String boardId) {
        return given().spec(getRequestSpec())
                .when()
                .delete(ConfigManager.getProperty("boardsEndpoint") + "/" + boardId);
    }

    public String getFirstListId(String boardId) {
        Response response = given().spec(getRequestSpec())
                .when()
                .get(ConfigManager.getProperty("boardsEndpoint") + "/" + boardId + ConfigManager.getProperty("listsEndpoint"));

        return response.jsonPath().getString("[0].id");
    }
}
