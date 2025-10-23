package com.trello.pages;

import com.utils.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BasePage {

    protected RequestSpecification requestSpec;

    public BasePage() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(ConfigManager.getProperty("baseUrl"))
                .addQueryParam("key", ConfigManager.getProperty("apiKey"))
                .addQueryParam("token", ConfigManager.getProperty("apiToken"))
                .setContentType(ContentType.JSON)
                .build();
    }

    protected RequestSpecification getRequestSpec() {
        return requestSpec;
    }
}
