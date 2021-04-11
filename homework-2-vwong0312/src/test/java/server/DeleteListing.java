package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.MongoCollection;
import dto.ListingDto;
import dto.ResponseDto;
import mongo.MongoConnection;
import org.bson.Document;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.TestUtilities;

public class DeleteListing {

  @Test
  public void testDeleteListing(){
    String randomEntry = String.valueOf(Math.random());
    MongoConnection connection = Mockito.mock(MongoConnection.class);
    MongoCollection mongoCollection = Mockito.mock(MongoCollection.class);
    ArgumentCaptor<Document> argument = ArgumentCaptor.forClass(Document.class);
    Mockito.doReturn(null).when(mongoCollection).deleteOne(argument.capture());
    Mockito.doReturn(mongoCollection).when(connection).getCollection(Mockito.any(), Mockito.any());
    TestUtilities.setTestConnection(connection);
    Gson gson = new Gson();
    Server server = new Server();
    String requestString = "GET /api/deleteListing?id=" + randomEntry + " HTTP/1.1\n"
        + "Accept: application/json, text/plain, */*\n"
        + "Content-Type: application/json;charset=utf-8\n"
        + "User-Agent: axios/0.20.0\n"
        + "Content-Length: 43\n"
        + "Host: localhost:1299\n"
        + "Connection: close\n"
        + "\n";
    String responseString = server.processRequest(requestString);
    ResponseDto<ListingDto> responseDto = gson.fromJson(responseString, new TypeToken<ResponseDto<ListingDto>>(){}.getType());
    Assert.assertTrue(responseDto.success);
    Assert.assertNotNull(responseDto.date);
    Assert.assertEquals(responseDto.items.size(), 0);
    Assert.assertEquals(argument.getValue().get("entryId"), randomEntry);
  }
}
