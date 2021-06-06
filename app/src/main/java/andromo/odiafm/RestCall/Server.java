package andromo.odiafm.RestCall;
import andromo.odiafm.model.Fm10View;
import andromo.odiafm.model.FmView;
import andromo.odiafm.model.SongView;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Server {
    @GET("/fm.json")
    Call<SongView> getSplCal();
    @GET("/ati.json")
    Call<FmView> getNineBook();
    @GET("/newfm.json")
    Call<Fm10View> getFm();
}

