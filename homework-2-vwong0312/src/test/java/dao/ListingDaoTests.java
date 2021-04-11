package dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import dto.ListingDto;
import java.util.ArrayList;
import java.util.List;
import mongo.MongoConnection;
import org.bson.Document;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;
import utilities.TestUtilities;

public class ListingDaoTests {

  @Test
  public void testPutItem(){
    MongoConnection connection = Mockito.mock(MongoConnection.class);
    MongoCollection mongoCollection = Mockito.mock(MongoCollection.class);
    Mockito.doReturn(mongoCollection).when(connection).getCollection(Mockito.any(), Mockito.any());
    Mockito.doNothing().when(mongoCollection).insertOne(Mockito.any());
    TestUtilities.setTestConnection(connection);
    ListingDao listingDao = ListingDao.getInstance();
    ListingDto dto = new ListingDto(null, null, null, null);
    ListingDto insertedItem = listingDao.put(dto);
    Mockito.verify(mongoCollection).insertOne(Mockito.eq(insertedItem));
  }

  @Test
  public void testGetItems(){
    MongoConnection connection = Mockito.mock(MongoConnection.class);
    MongoCollection mongoCollection = Mockito.mock(MongoCollection.class);
    Mockito.doReturn(mongoCollection).when(connection).getCollection(Mockito.any(), Mockito.any());
    TestUtilities.setTestConnection(connection);
    ListingDao listingDao = ListingDao.getInstance();
    FindIterable findIterable = Mockito.mock(FindIterable.class);
    Mockito.doReturn(findIterable).when(mongoCollection).find();
    ListingDto dto = new ListingDto(null, null, null, null);
    List<ListingDto> output = new ArrayList<>();
    Mockito.doReturn(output).when(findIterable).into(Mockito.any());
    List<ListingDto> insertedItems = listingDao.getItems();
    Assert.assertEquals(insertedItems.size(), 0);
    output.add(dto);
    Mockito.doReturn(output).when(findIterable).into(Mockito.any());
    insertedItems = listingDao.getItems();
    Assert.assertEquals(insertedItems.size(), 1);
  }

  @Test
  public void testDeleteItem(){
    MongoConnection connection = Mockito.mock(MongoConnection.class);
    MongoCollection mongoCollection = Mockito.mock(MongoCollection.class);
    Mockito.doReturn(mongoCollection).when(connection).getCollection(Mockito.any(), Mockito.any());
    TestUtilities.setTestConnection(connection);
    ListingDao listingDao = ListingDao.getInstance();
    ArgumentCaptor<Document> argument = ArgumentCaptor.forClass(Document.class);
    Mockito.doReturn(null).when(mongoCollection).deleteOne(argument.capture());
    listingDao.delete("abc");
    Assert.assertEquals(argument.getValue().get("entryId"), "abc");
  }

}
