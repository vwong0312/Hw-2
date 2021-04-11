package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import dto.ListingDto;
import dto.ResponseDto;
import java.util.ArrayList;
import java.util.List;
import mongo.MongoConnection;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.TestUtilities;

public class TestListItems {

  @Test
  public void testListItems(){
    String requestString = "GET /api/viewListings HTTP/1.1\n"
        + "Accept: application/json, text/plain, */*\n"
        + "Content-Type: application/json;charset=utf-8\n"
        + "User-Agent: axios/0.20.0\n"
        + "Content-Length: 43\n"
        + "Host: localhost:1299\n"
        + "Connection: close\n"
        + "\n";
    MongoConnection connection = Mockito.mock(MongoConnection.class);
    MongoCollection mongoCollection = Mockito.mock(MongoCollection.class);
    FindIterable findIterable = Mockito.mock(FindIterable.class);
    Mockito.doReturn(findIterable).when(mongoCollection).find();
    List<ListingDto> output = new ArrayList<>();
    Mockito.doReturn(output).when(findIterable).into(Mockito.any());
    Mockito.doReturn(mongoCollection).when(connection).getCollection(Mockito.any(), Mockito.any());
    Mockito.doNothing().when(mongoCollection).insertOne(Mockito.any());
    TestUtilities.setTestConnection(connection);
    Gson gson = new Gson();
    Server server = new Server();
    String responseString = server.processRequest(requestString);
    ResponseDto<ListingDto> responseDto = gson.fromJson(responseString, new TypeToken<ResponseDto<ListingDto>>(){}.getType());
    Assert.assertTrue(responseDto.success);
    Assert.assertNotNull(responseDto.date);
    Assert.assertEquals(responseDto.items.size(), 0);
    // expects .find()
    Mockito.verify(mongoCollection, Mockito.times(1)).find();
  }
}
