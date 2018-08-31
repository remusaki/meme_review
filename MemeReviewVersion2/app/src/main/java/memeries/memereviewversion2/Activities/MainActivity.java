package memeries.memereviewversion2.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import memeries.memereviewversion2.AppController;
import memeries.memereviewversion2.R;

public class MainActivity extends AppCompatActivity {

    private EditText txt_username, txt_password;
    private Button btn_signUp, btn_signIn;
    private static String TAG = MainActivity.class.getSimpleName();

    public static final String SHARED_PREF_KEY = "LE_SHARED_PREF", LAST_NAME_KEY = "lastname", FIRST_NAME_KEY = "firstname", USER_ID_KEY = "user_id", ACCOUNT_TYPE_KEY = "account_type";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        initResources();
        initEvents();
    }

    @Override
    protected  void onResume(){
        super.onResume();
        initResources();
    }

    public void initResources(){
        txt_username = (EditText)findViewById(R.id.txt_username);
        txt_password = (EditText)findViewById(R.id.txt_password);
        btn_signUp = (Button)findViewById(R.id.btn_signUp);
        btn_signIn = (Button)findViewById(R.id.btn_signIn);

        txt_username.setText("");
        txt_password.setText("");
        txt_username.requestFocus();

        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE);
        if(sharedPreferences.contains("user_id")){
            Intent i = new Intent(getApplicationContext(), MemeActivity.class);
            startActivity(i);
        }
    }

    public void initEvents(){
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txt_username.getText().toString().isEmpty() || txt_password.getText().toString().isEmpty())
                    Toast.makeText(getApplicationContext(), "Please Fill All The Fields.", Toast.LENGTH_LONG).show();
                else
                    loginAccount();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.forgotPassword:
                Intent i = new Intent(this, ForgotPasswordActivity.class);
                startActivity(i);
                break;
            default:
                Toast.makeText(this, "RIP", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void loginAccount(){
        StringRequest strRequest = new StringRequest(Request.Method.POST, getString(R.string.loginAccountURL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                String res_lastname = "", res_firstname = "", res_user_id = "", res_account_type = "",status = "", message ="";
                try{
                    JSONObject obj = new JSONObject(response);
                    status = obj.getString("status");

                    if(status.equals("success")){
                        JSONArray jsonArray = obj.getJSONArray("message");
                        JSONObject info = jsonArray.getJSONObject(0); // first index since we expect only 1 result

                        res_lastname = info.getString("lastname");
                        res_firstname = info.getString("firstname");
                        res_user_id = info.getString("user_id");
                        res_account_type = info.getString("account_type");

                        editor = sharedPreferences.edit();
                        editor.putString(LAST_NAME_KEY, res_lastname);
                        editor.putString(FIRST_NAME_KEY, res_firstname);
                        editor.putString(USER_ID_KEY, res_user_id);
                        editor.putString(ACCOUNT_TYPE_KEY, res_account_type);
                        editor.commit();

                        Intent i = new Intent(getApplicationContext(), MemeActivity.class);
                        startActivity(i);
                        Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    }else{
                        message = (String)obj.get("message");
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
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

                String in_username, in_password;
                in_username = txt_username.getText().toString();
                in_password = txt_password.getText().toString();
                parameters.put("username", in_username);
                parameters.put("password", in_password);
                return parameters;
            }
        };

        AppController.getInstance().addToRequestQueue(strRequest);
    }
}
