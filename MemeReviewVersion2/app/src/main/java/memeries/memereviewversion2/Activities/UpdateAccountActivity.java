package memeries.memereviewversion2.Activities;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class UpdateAccountActivity extends AppCompatActivity {

    private TextView txt_username;
    private EditText txt_lastname, txt_firstname, txt_password, txt_confirm;
    private Button btn_update, btn_cancel;

    public static final String SHARED_PREF_KEY = "LE_SHARED_PREF", LAST_NAME_KEY = "lastname", FIRST_NAME_KEY = "firstname", USER_ID_KEY = "user_id", ACCOUNT_TYPE_KEY = "account_type";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static String TAG = RegisterActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        initResources();
        initEvents();
    }

    public void initResources(){
        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE);

        txt_lastname = (EditText) findViewById(R.id.txt_lastname);
        txt_firstname = (EditText)findViewById(R.id.txt_firstname);
        txt_username = (TextView) findViewById(R.id.tv_username);
        txt_password = (EditText)findViewById(R.id.txt_password);
        txt_confirm = (EditText)findViewById(R.id.txt_confirm);

        txt_lastname.requestFocus();

        btn_update = (Button)findViewById(R.id.btn_update);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);

        getUserDetails();
    }

    public void initEvents(){
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    updateAccount();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMenu();
            }
        });
    }

    public boolean validate(){
        String lastname, firstname, username, password, confirm_password;
        lastname = txt_lastname.getText().toString().trim();
        firstname = txt_firstname.getText().toString().trim();
        username = txt_username.getText().toString().trim();
        password = txt_password.getText().toString().trim();
        confirm_password = txt_confirm.getText().toString().trim();

        if(lastname.isEmpty() || firstname.isEmpty() || firstname.isEmpty() || username.isEmpty() || password.isEmpty() || confirm_password.isEmpty()){
            Toast.makeText(getApplicationContext(), "Please Fill All The Fields.", Toast.LENGTH_LONG).show();
        }else{
            if(password.equals(confirm_password)){
                return true;
            }else{
                Toast.makeText(getApplicationContext(), "Password Does Not Match.", Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }


    public void getUserDetails(){
        StringRequest strRequest = new StringRequest(Request.Method.POST, getString(R.string.getUserDetailsURL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String res_lastname = "", res_firstname = "", res_username = "", status = "", message ="";
                Log.d(TAG, response.toString());
                try{
                    JSONObject obj = new JSONObject(response);
                    status = (String)obj.get("status");
                    if(status.equals("success")){
                        JSONArray jsonArray = obj.getJSONArray("message");
                        JSONObject info = jsonArray.getJSONObject(0); // first index since we expect only 1 result

                        res_lastname = info.getString("lastname");
                        res_firstname = info.getString("firstname");
                        res_username = info.getString("username");

                        txt_lastname.setText(res_lastname);
                        txt_firstname.setText(res_firstname);
                        txt_username.setText(res_username);

                    }else{
                        message = (String)obj.get("message");
                        Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_LONG).show();
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

                String in_user_id;
                in_user_id = sharedPreferences.getString(USER_ID_KEY, "");
                parameters.put("user_id", in_user_id);

                return parameters;
            }
        };
        AppController.getInstance().addToRequestQueue(strRequest);
    }

    public void updateAccount(){
        StringRequest strRequest = new StringRequest(Request.Method.POST, getString(R.string.updateAccountURL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String status = "", message = "";
                Log.d(TAG, response.toString());
                try{
                    JSONObject obj = new JSONObject(response);
                    status = (String)obj.get("status");
                    if(status.equals("success")){
                        Toast.makeText(getApplicationContext(), "Update Success, Please Re-login!", Toast.LENGTH_LONG).show();
                        editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        returnToMenu();
                    }else{
                        message = (String)obj.get("message");
                        Toast.makeText(getApplicationContext(), message.toString(), Toast.LENGTH_LONG).show();
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

                String in_lastname, in_firstname, in_username, in_password;
                in_lastname = txt_lastname.getText().toString().trim();
                in_firstname = txt_firstname.getText().toString().trim();
                in_username = txt_username.getText().toString().trim();
                in_password = txt_password.getText().toString().trim();

                parameters.put("last_name", in_lastname);
                parameters.put("first_name", in_firstname);
                parameters.put("username", in_username);
                parameters.put("password", in_password);
                return parameters;
            }
        };
        AppController.getInstance().addToRequestQueue(strRequest);
    }

    public void returnToMenu(){
        finish();
    }
}
