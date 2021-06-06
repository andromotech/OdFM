package andromo.odiafm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Fm10View {
    @SerializedName("at")
    @Expose

    private List<Fm10Model> fmlist;

    public List<Fm10Model> getFm(){
        return fmlist;
    }

    public void setFm(List<Fm10Model> fmlist){
        this.fmlist = fmlist;
    }
}