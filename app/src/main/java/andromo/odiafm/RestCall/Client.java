package andromo.odiafm.RestCall;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    public static final String URL = "http://sm05.co.in";
    public static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}