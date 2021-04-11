package dao;

import dto.ListingDto;

import java.util.ArrayList;
import java.util.List;
import mongo.MongoConnection;
import org.bson.Document;
import org.bson.conversions.Bson;

public class ListingDao extends MongoDao<ListingDto>
implements DataAccessObject<ListingDto> {

  // use lazy loading for the singleton
  private static ListingDao instance;

  public static ListingDao getInstance(){
    if(instance == null){
      instance = new ListingDao(new MongoConnection());
    }
    return instance;
  }

  ListingDao(MongoConnection connection){
    super(connection);
    collection = mongoConnection.getCollection("MyCollection", ListingDto.class);
  }

  @Override
  public ListingDto put(ListingDto item) {
    // test requires insertOne to be called
    collection.insertOne(item);
    return item;
  }

  @Override
  public List<ListingDto> getItems() {
//     use .into
    List<ListingDto> items = collection.find().into(new ArrayList<>());
    return items;
  }

  @Override
  public void delete(String id) {
    collection.deleteOne(new Document("entryId", id));
  }
}
