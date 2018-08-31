package memeries.memereviewversion2.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import memeries.memereviewversion2.AppController;
import memeries.memereviewversion2.R;

public class AddMeme extends AppCompatActivity {
    private static String TAG = AddMeme.class.getSimpleName();
    private Spinner spinnerCat;
    private EditText memeName, memeDesc,memePath;
    private Button add, back;
    private ArrayList<String> memeList = getMemeList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meme);
        Intent intent = getIntent();

        memeName = (EditText) findViewById(R.id.memeName);
        memeDesc = (EditText) findViewById(R.id.memeDesc);
        memePath = (EditText) findViewById(R.id.memePath);
        spinnerCat = (Spinner) findViewById(R.id.spinner);
        add = (Button)findViewById(R.id.submit);
        back = (Button)findViewById(R.id.back);

        spinnerCat.setAdapter(
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        memeList
                )

        );

    }
    private ArrayList<String> getMemeList() {

        ArrayList<String> memeList = new ArrayList<>();
        memeList.add("Wholesome");
        memeList.add("Nonsense");
        memeList.add("Dank");
        return memeList;
    }

    public void onClickAdd(View view) {
        if(validate()){
            addMeme();
            finish();
        }else{
            Toast.makeText(this, "Please Fill All The Fields", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickBack(View view){
        onBackPressed();
    }

    private boolean validate(){
        if(memePath.getText().toString().trim().isEmpty() || memeDesc.getText().toString().trim().isEmpty() || memeName.getText().toString().trim().isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    private void addMeme(){
        StringRequest strRequest = new StringRequest(Request.Method.POST, getString(R.string.addMemeURL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                Toast.makeText(getApplicationContext(), "Adding Success!", Toast.LENGTH_LONG).show();

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
                parameters.put("name", memeName.getText().toString());
                parameters.put("description", memeDesc.getText().toString());
                parameters.put("fullpath",memePath.getText().toString());
                parameters.put("category",spinnerCat.getSelectedItem().toString());
                return parameters;
            }
        };
        AppController.getInstance().addToRequestQueue(strRequest);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
