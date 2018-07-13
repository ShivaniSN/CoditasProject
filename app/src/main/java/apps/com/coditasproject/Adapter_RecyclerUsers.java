package apps.com.coditasproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_RecyclerUsers extends RecyclerView.Adapter<Adapter_RecyclerUsers.MyViewHolder>{

    private List<List_Users> channelsList;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewUsername,textViewScore,textViewViwDetails;
        public CircleImageView circleImageViewProfile;

        public MyViewHolder(View view) {
            super(view);
            textViewUsername = (TextView) view.findViewById(R.id.tv_username);
            textViewScore = (TextView) view.findViewById(R.id.tv_score);
            textViewViwDetails = (TextView) view.findViewById(R.id.tv_details);
            circleImageViewProfile = (CircleImageView)view.findViewById(R.id.profile_image);
        }
    }

    public Adapter_RecyclerUsers(List<List_Users> moviesList, Context context) {
        this.channelsList = moviesList;
        this.context = context;
    }

    @Override
    public Adapter_RecyclerUsers.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleritem_users, parent, false);

        return new Adapter_RecyclerUsers.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Adapter_RecyclerUsers.MyViewHolder holder, int position) {
        final List_Users table = channelsList.get(position);
        holder.textViewUsername.setText(table.getStringUserName());
        holder.textViewScore.setText(table.getStringScore());
        Picasso.with(context).load(table.getStringProfileImage()).into(holder.circleImageViewProfile);
        holder.textViewViwDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return channelsList.size();
    }

    public List<List_Users> getChannelsList() {
        return channelsList;
    }
}



