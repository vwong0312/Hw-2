package utilities;

import dao.ListingDao;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import mongo.MongoConnection;

public class TestUtilities {

  public static void setTestConnection(MongoConnection connection) {
    try {
      Field field = ListingDao.class.getDeclaredField("instance");
      Constructor<ListingDao> constructor = (Constructor<ListingDao>) ListingDao.class
          .getDeclaredConstructors()[0];
      constructor.setAccessible(true);
      ListingDao obj = constructor.newInstance(connection);
      field.setAccessible(true);
      field.set(ListingDao.class, obj);
    } catch (Exception e) {
      System.out.println("Failed to set testing connection");
    }
  }
}
