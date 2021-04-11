package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.MongoCollection;
import dto.ListingDto;
import dto.ResponseDto;
import mongo.MongoConnection;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.TestUtilities;

public class AddListing {

  @Test
  public void testInsertItem(){
    String requestString = "POST /api/createListing HTTP/1.1\n"
        + "Accept: application/json, text/plain, */*\n"
        + "Content-Type: application/json;charset=utf-8\n"
        + "User-Agent: axios/0.20.0\n"
        + "Content-Length: 43\n"
        + "Host: localhost:1299\n"
        + "Connection: close\n"
        + "\n"
        + "{\"description\":\"This is my item for sale.\"}";
    MongoConnection connection = Mockito.mock(MongoConnection.class);
    MongoCollection mongoCollection = Mockito.mock(MongoCollection.class);
    Mockito.doReturn(mongoCollection).when(connection).getCollection(Mockito.any(), Mockito.any());
    Mockito.doNothing().when(mongoCollection).insertOne(Mockito.any());
    TestUtilities.setTestConnection(connection);
    Gson gson = new Gson();
    Server server = new Server();
    String responseString = server.processRequest(requestString);
    ResponseDto<ListingDto> responseDto = gson.fromJson(responseString, new TypeToken<ResponseDto<ListingDto>>(){}.getType());
    Assert.assertTrue(responseDto.success);
    Assert.assertNotNull(responseDto.date);
    Assert.assertEquals(responseDto.items.size(), 1);
    Mockito.verify(mongoCollection, Mockito.times(1)).insertOne(Mockito.any());
  }

}
