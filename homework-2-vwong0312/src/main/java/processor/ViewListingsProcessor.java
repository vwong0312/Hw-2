package processor;

import dto.ResponseDto;
import parser.ParsedUrl;
import dto.ListingDto;
import dao.ListingDao;
import java.util.Date;
import com.google.gson.Gson;

public class ViewListingsProcessor implements Processor{

  @Override
  public ResponseDto process(ParsedUrl parsedUrl, String body) {
  ListingDao listingDao = ListingDao.getInstance();
  ResponseDto rDto = new ResponseDto(new Date(), listingDao.getItems(), true);
  return rDto;
  }
}
