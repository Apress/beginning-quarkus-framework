package org.acme.dao.nosql;

import org.acme.dao.reactive.AnagramReactiveDAO;
import org.acme.entity.Anagram;
import org.jboss.logging.Logger;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class DynamoDbDAO {

    @Inject
    DynamoDbClient dynamoDbClient;

    final Logger logger = Logger.getLogger(DynamoDbDAO.class.getName());

    public void dynamoAdd(Anagram anagram) {
        Map<String, AttributeValue> anagramItem = new HashMap<>();
        anagramItem.put("id", AttributeValue.builder().n("123").build());
        anagramItem.put("anagramText", AttributeValue.builder().s(anagram.anagramText).build());
        PutItemRequest request = PutItemRequest.builder().tableName("anagram").item(anagramItem).build();
        PutItemResponse putItemResponse = dynamoDbClient.putItem(request);
        logger.info("operation consumed " + putItemResponse.consumedCapacity().capacityUnits() + " capacity units");
    }

    public void dynamoGet(String anagramId) {
        Map<String, AttributeValue> anagramItem = new HashMap<>();
        anagramItem.put("id", AttributeValue.builder().n(anagramId).build());
        GetItemRequest getItemRequest = GetItemRequest.builder().tableName("anagram").key(anagramItem).attributesToGet("anagramText").build();
        GetItemResponse item = dynamoDbClient.getItem(getItemRequest);
        item.getValueForField("anagramText", String.class);
    }

    public void dynamoUpdate(String anagramText, String newAnagramText, String anagramId) {
        Map<String, AttributeValue> anagramItem = new HashMap<>();
        anagramItem.put("id", AttributeValue.builder().n(anagramId).build());
        Map<String, AttributeValueUpdate> newAnagramAttributes = new HashMap<>();
        AttributeValueUpdate updateAnagramText = AttributeValueUpdate.builder()
                .action(AttributeAction.PUT)
                .value(AttributeValue.builder().s(newAnagramText).build())
                .build();
        AttributeValueUpdate brandNewAttribute = AttributeValueUpdate.builder()
                .action(AttributeAction.ADD)
                .value(AttributeValue.builder().s(anagramText).build())
                .build();
        newAnagramAttributes.put("anagramText", updateAnagramText);
        newAnagramAttributes.put("oldAnagramText", brandNewAttribute);
        anagramItem.put("id", AttributeValue.builder().n(anagramId).build());
        anagramItem.put("anagramText", AttributeValue.builder().s(anagramText).build());
        UpdateItemRequest updateItemRequest = UpdateItemRequest.builder().attributeUpdates(newAnagramAttributes).tableName("anagram").key(anagramItem).build();
        UpdateItemResponse response = dynamoDbClient.updateItem(updateItemRequest);
        logger.info("Update successful: " + response.sdkHttpResponse().isSuccessful());
    }

}
