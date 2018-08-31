package memeries.memereviewversion2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by djmurusaki on 14 May 2018.
 */

public class MemeAdapter extends BaseAdapter {
    private Context context;
    private List<Memes> memesList;
    ImageView memesImage;

    public MemeAdapter(Context context, List<Memes> memesList){
        this.context = context;
        this.memesList = memesList;
    }

    @Override
    public int getCount() {
        return  memesList.size();
    }

    @Override
    public Object getItem(int position) {
        return memesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.listview_meme, null);
        memesImage = (ImageView)view.findViewById(R.id.memeImage);
        TextView memesName = (TextView)view.findViewById(R.id.tv_raterName);
        TextView memesDescription = (TextView)view.findViewById(R.id.memesDescription);
        TextView memesID = (TextView)view.findViewById(R.id.memesID);

        memesName.setText(memesList.get(position).getName());
        memesDescription.setText(memesList.get(position).getDescription());
        memesID.setText(memesList.get(position).getMemes_id());

        //setting immage
        loadImage(memesList.get(position).getFullpath());

        return view;
    }
    public void loadImage(String url){
        Picasso.with(context).load(url).placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher)
        .into(memesImage, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });
    }
}
