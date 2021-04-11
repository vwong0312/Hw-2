package processor;

import dto.ResponseDto;
import parser.ParsedUrl;
import dto.ListingDto;
import dao.ListingDao;
import java.util.Date;
import com.google.gson.Gson;

public class AddListingProcessor implements Processor{

  private static Gson gson = new Gson();

  @Override
  public ResponseDto process(ParsedUrl parsedUrl, String body) {
    ListingDto listingDto = gson.fromJson(body, ListingDto.class);
    ListingDao listingDao = ListingDao.getInstance();
    listingDao.put(listingDto);
    ResponseDto rDto = new ResponseDto(new Date(), listingDao.getItems(), true);

    return rDto;
  }
}
