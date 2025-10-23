package com.trello;

import com.trello.pages.BoardPage;
import com.trello.pages.CardPage;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;
import org.testng.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrelloAutomationTest {

    private static final Logger logger = LogManager.getLogger(TrelloAutomationTest.class);
    private BoardPage boardPage;
    private CardPage cardPage;
    private String boardId;
    private String listId;
    private List<String> cardIds;

    @BeforeClass
    public void setup() {
        boardPage = new BoardPage();
        cardPage = new CardPage();
        cardIds = new ArrayList<>();
    }

    @Test(priority = 1)
    public void createTrelloBoard() {
        String boardName = "Trello Board - " + System.currentTimeMillis();
        Response response = boardPage.createBoard(boardName);

        Assert.assertEquals(response.getStatusCode(), 200, "Board creation failed.");
        boardId = response.jsonPath().getString("id");
        listId = boardPage.getFirstListId(boardId);

        logger.info("Board created: {} (ID: {})", boardName, boardId);
        logger.info("Default List ID: {}", listId);
    }

    @Test(priority = 2, dependsOnMethods = {"createTrelloBoard"})
    public void createTwoCards() {
        // Card 1
        String cardName1 = "Card 1 - To be updated";
        Response response1 = cardPage.createCard(cardName1, listId);
        Assert.assertEquals(response1.getStatusCode(), 200, "Card 1 creation failed.");
        cardIds.add(response1.jsonPath().getString("id"));
        logger.info("Card 1 created: {}", cardName1);

        // Card 2
        String cardName2 = "Card 2 - To be deleted";
        Response response2 = cardPage.createCard(cardName2, listId);
        Assert.assertEquals(response2.getStatusCode(), 200, "Card 2 creation failed.");
        cardIds.add(response2.jsonPath().getString("id"));
        logger.info("Card 2 created: {}", cardName2);
    }

    @Test(priority = 3, dependsOnMethods = {"createTwoCards"})
    public void updateRandomCard() {
        Random random = new Random();
        String cardIdToUpdate = cardIds.get(random.nextInt(cardIds.size()));
        String newCardName = "UPDATED CARD - " + System.currentTimeMillis();

        Response response = cardPage.updateCardName(cardIdToUpdate, newCardName);
        Assert.assertEquals(response.getStatusCode(), 200, "Card update failed.");

        logger.info("Random card updated. ID: {}, New Name: {}", cardIdToUpdate, newCardName);
    }

    @Test(priority = 4, dependsOnMethods = {"updateRandomCard"})
    public void deleteCards() {
        for (String cardId : cardIds) {
            Response response = cardPage.deleteCard(cardId);
            Assert.assertEquals(response.getStatusCode(), 200, "Card deletion failed for ID: " + cardId);
            logger.info("Card deleted: {}", cardId);
        }
    }

    @AfterClass
    public void deleteTrelloBoard() {
        if (boardId != null) {
            Response response = boardPage.deleteBoard(boardId);
            Assert.assertEquals(response.getStatusCode(), 200, "Board deletion failed.");
            logger.info("Board deleted: {}", boardId);
        }
    }
}
