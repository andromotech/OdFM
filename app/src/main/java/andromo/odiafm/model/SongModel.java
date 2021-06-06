package andromo.odiafm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SongModel {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("pic")
    @Expose
    private String pic;

    @SerializedName("url")
    @Expose
    private String url;


    public SongModel(String name, String url, String pic) {
        this.name = name;
        this.url = url;
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    //public void setBenifits(String benifits)
    public void setUrl(String url) {
        this.url = url;
    }


    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
