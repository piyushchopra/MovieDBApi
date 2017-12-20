package adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.piyush.moviedbapi.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import helper.CustomClickListener;
import model.Datum;

/**
 * Created by piyush on 18/12/17.
 */

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.CustomViewHolder> {

    private Context context;
    private List<Datum> dataList;
    private CustomClickListener listener;

    public VerticalAdapter(Context context, List<Datum> dataList, CustomClickListener listener) {
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_single_item,parent, false);
        final CustomViewHolder viewHolder = new CustomViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        Datum datum = dataList.get(position);

        Picasso.with(context)
                .load(datum.getImage())
                .into(holder.fullImage);

        holder.nameTextView.setText(datum.getTitle());

        Picasso.with(context)
                .load(datum.getNameImage())
                .into(holder.profileImage);

        holder.profileName.setText(datum.getName());

    }



    @Override
    public int getItemCount() {
        return (dataList!=null ? dataList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        ImageView fullImage;
        ImageView profileImage;
        TextView nameTextView;
        TextView profileName;
        TextView timeStamp;
        CardView cardView;


        public CustomViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.name_text_view);
            profileName = itemView.findViewById(R.id.profile_name);
            timeStamp = itemView.findViewById(R.id.time_stamp);
            fullImage = itemView.findViewById(R.id.full_image);
            profileImage = itemView.findViewById(R.id.profile_image);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
