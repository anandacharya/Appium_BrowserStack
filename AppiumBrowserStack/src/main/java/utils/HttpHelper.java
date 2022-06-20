package utils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class HttpHelper {
    public static void resetRoute(String routeId){
        Unirest.setTimeouts(0, 0);
        try {
            HttpResponse<String> response = Unirest.get("https://xxxxxxxx.azurewebsites.net/api/ResetRoute?routeId="+routeId+"&resetFirebase=true")
                    .asString();
            System.out.println(response.getBody().toString());
        } catch (UnirestException e) {
            e.printStackTrace();
        }
    }
}
