package memeries.memereviewversion2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Remusaki on Jun 2 2018.
 */

public class RatingsAdapter extends BaseAdapter {
    private Context context;
    private List<Ratings> ratingsList;

    public RatingsAdapter(Context context, List<Ratings> ratingsList){
        this.context = context;
        this.ratingsList = ratingsList;
    }

    @Override
    public int getCount() {
        return ratingsList.size();
    }

    @Override
    public Object getItem(int i) {
        return ratingsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(context, R.layout.listview_ratings, null);

        TextView raterName = (TextView)view.findViewById(R.id.tv_raterName);
        TextView ratingComment = (TextView)view.findViewById(R.id.tv_ratingComment);
        TextView ratingScore = (TextView)view.findViewById(R.id.tv_ratingScore);

        raterName.setText(ratingsList.get(position).getLastname() + ", " + ratingsList.get(position).getFirstname().charAt(0));
        ratingComment.setText(ratingsList.get(position).getRating_comment());
        ratingScore.setText(ratingsList.get(position).getRating());

        return view;
    }
}
