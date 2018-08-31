package memeries.memereviewversion2.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import memeries.memereviewversion2.AppController;
import memeries.memereviewversion2.MemeAdapter;
import memeries.memereviewversion2.Memes;
import memeries.memereviewversion2.R;
import memeries.memereviewversion2.Ratings;
import memeries.memereviewversion2.RatingsAdapter;

public class RatingsActivity extends AppCompatActivity {
    private static final String TAG = "RatingActivity";
    private String meme_id;

    private ListView ratingsListView;
    private RatingsAdapter adapter;
    private ArrayList<Ratings> ratingsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        Bundle i = getIntent().getExtras();
        meme_id = i.getString("meme_id");

        initResources();
    }

    private void initResources() {
        ratingsListView = (ListView)findViewById(R.id.ratingsListView);
        ratingsList = new ArrayList<>();
        adapter = new RatingsAdapter(getApplicationContext(), ratingsList);
        ratingsListView.setAdapter(adapter);

        getAllMemeRating();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_rating, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.back:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getAllMemeRating(){
        StringRequest strRequest = new StringRequest(Request.Method.POST, getString(R.string.getAllMemeRatingURL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                String status = "", message ="";
                try{
                    JSONObject obj = new JSONObject(response);
                    status = obj.getString("status");

                    if(status.equals("success")){
                        JSONArray jsonArray = obj.getJSONArray("result");
                        ratingsList.clear();
                        for(int x = 0; x < jsonArray.length(); x++){
                            String lastname = "", firstname = "", rating_id = "", rating_comment = "";
                            JSONObject info = jsonArray.getJSONObject(x); // first index since we expect only 1 result
                            lastname = info.getString("lastname");
                            firstname = info.getString("firstname");
                            rating_id = info.getString("rating_id");
                            rating_comment = info.getString("rating_comment");
                            ratingsList.add(new Ratings(lastname,firstname,rating_id,rating_comment));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }catch (Throwable t){
                    Log.e("App", "JSON error: " +response.toString());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("meme_id", meme_id);
                return parameters;
            }
        };

        AppController.getInstance().addToRequestQueue(strRequest);
    }
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
