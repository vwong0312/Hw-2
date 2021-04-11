package processor;

import dto.ResponseDto;
import parser.ParsedUrl;
import com.google.gson.Gson;
import dao.ListingDao;
import dto.ResponseDto;
import dto.ListingDto;
import java.util.Date;

public class DeleteListingProcessor implements Processor{
  private static Gson gson = new Gson();
  @Override
  public ResponseDto process(ParsedUrl parsedUrl, String body) {

    ListingDto listingDto = gson.fromJson(body, ListingDto.class);
    ListingDao listingDao = ListingDao.getInstance();

    String url = parsedUrl.getWholeUrl();
    String entryId = url.substring(url.lastIndexOf("=") + 1);

    if(entryId.contentEquals(listingDto.entryId)){
      String id = listingDao.getItems().get(0).entryId;
      listingDao.delete(id);
      ResponseDto responseDto = new ResponseDto(new Date(), listingDao.getItems(), true);
      return responseDto;
    }else{
      ResponseDto responseDto = new ResponseDto(new Date(), listingDao.getItems(), true);
      return responseDto;
    }
  }
}