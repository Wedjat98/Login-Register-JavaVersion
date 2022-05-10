package cn.lionlemon.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;

import java.util.Arrays;
import java.util.List;


//simple Dgs things
@DgsComponent
public class EventDataFetcher {
    @DgsQuery
    public List<String> events(){
        return Arrays.asList("Reading Book","Watching Movie","Eating Foods");
    }
    //type and name must same to schema filed
    @DgsMutation
    public String creatEvent(@InputArgument String name){
        return name+" is Created";
    }
}
