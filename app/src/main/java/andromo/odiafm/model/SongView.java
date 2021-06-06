package andromo.odiafm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SongView {
    @SerializedName("ir")
    @Expose

    private List<SongModel> spllist;


    public List<SongModel> getSplCal(){
        return spllist;
    }

    public void setAlbums(List<SongModel> spllist){
        this.spllist = spllist;
    }
}
