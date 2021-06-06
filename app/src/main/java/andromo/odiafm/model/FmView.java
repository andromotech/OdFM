package andromo.odiafm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FmView
{
@SerializedName("at")
@Expose

        private List<FmModel> ninelist;

        public List<FmModel> getNineBook(){
            return ninelist;
        }

        public void setBhagCal(List<FmModel> ninelist){
            this.ninelist = ninelist;
        }
}