package pointofsale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.ResponseDto;
import parser.ParsedUrl;
import processor.Processor;
import processor.ProcessorFactory;

public class EcommerceService {

  private ProcessorFactory processorFactory;
  private static EcommerceService instance;
  private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

  private EcommerceService(ProcessorFactory processorFactory) {
    this.processorFactory = processorFactory;
  }

  public static EcommerceService getInstance(){
    if(instance == null){
      instance = new EcommerceService(new ProcessorFactory());
    }
    return instance;
  }

  public String restApi(String url, String body){
    ParsedUrl parsedUrl = new ParsedUrl(url);
    Processor processor = processorFactory.getProcessor(parsedUrl);
    ResponseDto responseDto = processor.process(parsedUrl, body);
    return gson.toJson(responseDto);
  }

}
