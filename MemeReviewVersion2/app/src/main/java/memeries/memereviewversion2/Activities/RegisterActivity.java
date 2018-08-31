package memeries.memereviewversion2.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import memeries.memereviewversion2.AppController;
import memeries.memereviewversion2.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText txt_lastname, txt_firstname, txt_username, txt_password, txt_confirm;
    private Button btn_create, btn_cancel;
    private static String TAG = RegisterActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initResources();
        initEvents();
    }

    public void initResources(){
        txt_lastname = (EditText) findViewById(R.id.txt_lastname);
        txt_firstname = (EditText)findViewById(R.id.txt_firstname);
        txt_username = (EditText)findViewById(R.id.txt_username);
        txt_password = (EditText)findViewById(R.id.txt_password);
        txt_confirm = (EditText)findViewById(R.id.txt_confirm);

        txt_lastname.requestFocus();

        btn_create = (Button)findViewById(R.id.btn_create);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
    }

    public void initEvents(){
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    addAccount();
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


    public void addAccount(){
        StringRequest strRequest = new StringRequest(Request.Method.POST, getString(R.string.addAccountURL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String status = "", message = "";
                Log.d(TAG, response.toString());
                try{
                    JSONObject obj = new JSONObject(response);
                    status = (String)obj.get("status");
                    if(status.equals("success")){
                        Toast.makeText(getApplicationContext(), "Register Success", Toast.LENGTH_LONG).show();
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
