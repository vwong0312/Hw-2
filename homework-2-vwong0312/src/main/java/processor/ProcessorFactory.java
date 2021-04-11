package processor;

import parser.ParsedUrl;

public class ProcessorFactory {

  public Processor getProcessor(ParsedUrl parsedUrl){
    if(parsedUrl.getWholeUrl().contains("deleteListing")){
      return new DeleteListingProcessor();
    }
    if(parsedUrl.hasQueryArgs()){
      return new FilterProcessor();
    }
    if(parsedUrl.getPath().contains("createListing")){
      return new AddListingProcessor();
    }
    if(parsedUrl.getPath().contains("viewItems")){
      return new ViewListingsProcessor();
    }
    return new ErrorProcessor();
  }
}
