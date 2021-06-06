package andromo.odiafm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;
import andromo.odiafm.R;
import andromo.odiafm.SongDisplay;
import andromo.odiafm.model.Fm10Model;

public class Top10Adp extends RecyclerView.Adapter<Top10Adp.MyViewHolder> {

    private Context mContext;
    private List<Fm10Model> fmlist;

    public Top10Adp(Context mContext, List<Fm10Model> fmlist) {
        this.mContext = mContext;
        this.fmlist = fmlist;
    }

    @Override
    public Top10Adp.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cv, viewGroup, false);

        return new Top10Adp.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Top10Adp.MyViewHolder viewHolder, int i) {
        viewHolder.title.setText(fmlist.get(i).getName());
        Picasso.get()
                .load(fmlist.get(i).getPic())
                .placeholder(R.drawable.as)
                .into(viewHolder.cpic);

    }

    @Override
    public int getItemCount() {
        return fmlist.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView cpic;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            // count = (TextView) view.findViewById(R.id.count);
            // Typeface ofont = Typeface.createFromAsset(itemView.getContext().getAssets(),"fonts/odia.ttf");
            //   title.setTypeface(ofont);
            cpic = (ImageView) view.findViewById(R.id.cpic);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Fm10Model clickedDataItem = fmlist.get(pos);
                        Intent intent = new Intent(mContext, SongDisplay.class);
                        String url = fmlist.get(pos).getUrl();
                        intent.putExtra("url", fmlist.get(pos).getUrl());
                        intent.putExtra("pic", fmlist.get(pos).getPic());
                        intent.putExtra("name", fmlist.get(pos).getName());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}