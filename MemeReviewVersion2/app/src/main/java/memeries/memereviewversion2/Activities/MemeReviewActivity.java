package memeries.memereviewversion2.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import memeries.memereviewversion2.AppController;
import memeries.memereviewversion2.R;

public class MemeReviewActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    public static final String SHARED_PREF_KEY = "LE_SHARED_PREF", LAST_NAME_KEY = "lastname", FIRST_NAME_KEY = "firstname", USER_ID_KEY = "user_id", ACCOUNT_TYPE_KEY = "account_type";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public TextView memeName, memeDescription, ratingDescription;
    public RatingBar ratingBar;
    public ImageView memesImage;
    public EditText ratingComment;
    public Button submitRating, backButton;
    //rating variables
    public String user_id, meme_id, rating_id, rating_comment;
    // meme variables
    public String memes_name, memes_description, memes_fullpath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_review);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // prevent auto-pop up of keyboard

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        Intent i = getIntent();
        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE);

        user_id = sharedPreferences.getString(USER_ID_KEY, "");
        meme_id = i.getStringExtra("memes_id");
        memes_name = i.getStringExtra("memes_name");
        memes_description = i.getStringExtra("memes_description");
        memes_fullpath = i.getStringExtra("memes_fullpath");

        initResources();
        initEvents();
    }

    public void initResources(){
        memesImage = (ImageView)findViewById(R.id.memeImage);
        memeName = (TextView)findViewById(R.id.memeName);
        memeDescription = (TextView)findViewById(R.id.memeDescription);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingComment = (EditText)findViewById(R.id.ratingComment);
        submitRating = (Button)findViewById(R.id.btn_submit);
        backButton = (Button)findViewById(R.id.btn_back);
        ratingDescription = (TextView)findViewById(R.id.ratingDescription);

        ratingBar.setRating(0);
    }
    public void initEvents(){
        memeDescription.setMovementMethod(new ScrollingMovementMethod());
        memesImage.requestFocus();
        loadImage(memes_fullpath);
        memeName.setText(memes_name);
        memeDescription.setText(memes_description);

        getOneUserRating();

        submitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrUpdateRating();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                switch(String.valueOf(v)){
                    case "1.0":
                        ratingDescription.setText("Meh");
                        break;
                    case "2.0":
                        ratingDescription.setText("Hmmm");
                        break;
                    case "3.0":
                        ratingDescription.setText("Dope");
                        break;
                    case "4.0":
                        ratingDescription.setText("Dank");
                        break;
                    case "5.0":
                        ratingDescription.setText("Lit");
                        break;
                    default:
                        ratingDescription.setText("No Rating");
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(sharedPreferences.getString(ACCOUNT_TYPE_KEY, "").equals("admin")){
            inflater.inflate(R.menu.menu_admin, menu);
        }else if(sharedPreferences.getString(ACCOUNT_TYPE_KEY, "").equals("user")){
            inflater.inflate(R.menu.menu_user, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.approveMeme:
                updateMemeApproval("approved");
                break;
            case R.id.disableMeme:
                updateMemeApproval("pending");
                break;
            case R.id.declineMeme:
                updateMemeApproval("declined");
                break;
            case R.id.deleteMeme:
                deleteMeme();
                break;
            case R.id.viewRatings:
                Intent i = new Intent(this, RatingsActivity.class);
                i.putExtra("meme_id", meme_id);
                startActivity(i);
                break;
            default:
                Toast.makeText(this, "RIP", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadImage(String url){
        Picasso.with(getApplicationContext()).load(url).placeholder(R.mipmap.ic_launcher)
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


    public void getOneUserRating(){
        StringRequest strRequest = new StringRequest(Request.Method.POST, getString(R.string.getOneRatingURL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                String status = "", message ="";

                try{
                    JSONObject obj = new JSONObject(response);
                    status = obj.getString("status");
                    if(status.equals("success")){
                        String rating_id = "", rating_comment = "";
                        JSONArray jsonArray = obj.getJSONArray("result");
                        JSONObject info = jsonArray.getJSONObject(0);

                        rating_id = info.getString("rating_id");
                        rating_comment = info.getString("rating_comment");

                        ratingBar.setRating(Integer.valueOf(rating_id));
                        ratingComment.setText(rating_comment);
                    }
                }catch (Throwable t){
                    Log.e("App", "JSON error: " +response.toString());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();

                int rating = (int)ratingBar.getRating();
                String string_rating = String.valueOf(rating);
                rating_comment = ratingComment.getText().toString();
                parameters.put("user_id", user_id);
                parameters.put("meme_id", meme_id);
                return parameters;
            }
        };

        AppController.getInstance().addToRequestQueue(strRequest);
    }

    public void addOrUpdateRating(){
        StringRequest strRequest = new StringRequest(Request.Method.POST, getString(R.string.addRatingURL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                String status = "", message ="";

                try{
                    JSONObject obj = new JSONObject(response);
                    status = obj.getString("status");
                    message = obj.getString("message");
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    if(status.equals("success")){
                        finish();
                    }
                }catch (Throwable t){
                    Log.e("App", "JSON error: " +response.toString());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();

                int rating = (int)ratingBar.getRating();
                String string_rating = String.valueOf(rating);
                rating_comment = ratingComment.getText().toString();
                parameters.put("user_id", user_id);
                parameters.put("meme_id", meme_id);
                parameters.put("rating_id", string_rating);
                parameters.put("rating_comment", rating_comment);
                return parameters;
            }
        };

        AppController.getInstance().addToRequestQueue(strRequest);
    }

    public void updateMemeApproval(final String newApproval){
        StringRequest strRequest = new StringRequest(Request.Method.POST, getString(R.string.updateMemeApprovalURL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                String status = "", message ="";

                try{
                    JSONObject obj = new JSONObject(response);
                    status = obj.getString("status");
                    if(status.equals("success")){
                        message = obj.getString("message");
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                        finish();
                    }
                }catch (Throwable t){
                    Log.e("App", "JSON error: " +response.toString());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("meme_id", meme_id);
                parameters.put("approval", newApproval);
                return parameters;
            }
        };

        AppController.getInstance().addToRequestQueue(strRequest);
    }

    public void deleteMeme(){
        StringRequest strRequest = new StringRequest(Request.Method.POST, getString(R.string.deleteMemeURL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                String status = "", message ="";

                try{
                    JSONObject obj = new JSONObject(response);
                    status = obj.getString("status");
                    message = obj.getString("message");
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    if(status.equals("success")){
                        finish();
                    }
                }catch (Throwable t){
                    Log.e("App", "JSON error: " +response.toString());
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
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
}
