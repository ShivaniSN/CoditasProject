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

public class Adapter_RecyclerRepository extends RecyclerView.Adapter<Adapter_RecyclerRepository.MyViewHolder>{

    private List<List_Repository> channelsList;
    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewRepositoryName,textViewRepositoryDescription,
                textViewRepositoryURL,textViewLaguage,textViewCreatedOn;
        public CircleImageView circleImageViewProfile;

        public MyViewHolder(View view) {
            super(view);
            textViewRepositoryName = (TextView)view.findViewById(R.id.tv_reponame);
            textViewRepositoryDescription = (TextView)view.findViewById(R.id.tv_repodesc);
            textViewRepositoryURL = (TextView)view.findViewById(R.id.tv_repourl);
            textViewLaguage = (TextView)view.findViewById(R.id.tv_language);
            textViewCreatedOn = (TextView)view.findViewById(R.id.tv_createdon);
        }
    }

    public Adapter_RecyclerRepository(List<List_Repository> moviesList) {
        this.channelsList = moviesList;
    }

    @Override
    public Adapter_RecyclerRepository.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleritem_repository, parent, false);

        return new Adapter_RecyclerRepository.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final Adapter_RecyclerRepository.MyViewHolder holder, int position) {
        final List_Repository table = channelsList.get(position);
        holder.textViewRepositoryName.setText(table.getStringRepositoryName());
        holder.textViewRepositoryDescription.setText(table.getStringRepositoryDescription());
        holder.textViewRepositoryURL.setText(table.getStringRepositoryURL());
        holder.textViewLaguage.setText(table.getStringLanguage());
        holder.textViewCreatedOn.setText(table.getStringCreatedOn());
    }

    @Override
    public int getItemCount() {
        return channelsList.size();
    }

    public List<List_Repository> getChannelsList() {
        return channelsList;
    }
}




