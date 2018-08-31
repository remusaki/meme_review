package memeries.memereviewversion2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import memeries.memereviewversion2.AppController;
import memeries.memereviewversion2.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    private static String TAG = ForgotPasswordActivity.class.getSimpleName();
    private EditText txt_username;
    private Button btn_cancel, btn_reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initResources();
        initEvents();
    }

    private void initResources() {
        txt_username = (EditText)findViewById(R.id.txt_username);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        btn_reset = (Button)findViewById(R.id.btn_reset);

    }
    private void initEvents(){
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                forgotPassword();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void forgotPassword(){
        StringRequest strRequest = new StringRequest(Request.Method.POST, getString(R.string.forgotPasswordURL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                String status = "", message ="";
                try{
                    JSONObject obj = new JSONObject(response);
                    status = obj.getString("status");
                    message = obj.getString("message");
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
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

                String in_username, in_password;
                in_username = txt_username.getText().toString();
                parameters.put("username", in_username);
                return parameters;
            }
        };

        AppController.getInstance().addToRequestQueue(strRequest);
    }
}
