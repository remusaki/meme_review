package memeries.memereviewversion2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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

import memeries.memereviewversion2.Activities.MemeReviewActivity;
import memeries.memereviewversion2.AppController;
import memeries.memereviewversion2.MemeAdapter;
import memeries.memereviewversion2.Memes;
import memeries.memereviewversion2.R;


public class approvedMemes extends Fragment {
    private static final String TAG = "approvedMemes";

    //listview
    private ListView memesListView;
    private MemeAdapter adapter;
    private ArrayList<Memes> memesList;

    private TextView searchText;
    private Spinner spinner;
    final String[] items = new String[]{"None", "Nonsense", "Wholesome", "Dank"};
    String searchInput = "", searchCategory = "None";

    public approvedMemes(){}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_memes, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // prevent auto-pop up of keyboard

        initResources(view);
        initEvents(view);
        return view;
    }

    public void initResources(View view){
        memesListView = (ListView)view.findViewById(R.id.memeListView);
        memesList = new ArrayList<>();
        adapter = new MemeAdapter(view.getContext(), memesList);
        memesListView.setAdapter(adapter);

        searchText = (TextView)view.findViewById(R.id.txt_search);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner = (Spinner)view.findViewById(R.id.spinnerCategory);
        spinner.setAdapter(adapterSpinner);

        getAllMemes();
    }

    public void initEvents(View view){
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setSearchInput(charSequence.toString());
                getAllMemes();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setSearchCategory(items[position]);;
                getAllMemes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        memesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { // when an item from listview was clicked
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String memes_id = "", memes_name = "", memes_description = "", memes_fullpath = "";
                memes_id = memesList.get(position).getMemes_id();
                memes_name = memesList.get(position).getName();
                memes_description = memesList.get(position).getDescription();
                memes_fullpath = memesList.get(position).getFullpath();

                Intent i = new Intent(getContext(), MemeReviewActivity.class);

                i.putExtra("memes_id",memes_id);
                i.putExtra("memes_name",memes_name);
                i.putExtra("memes_description",memes_description);
                i.putExtra("memes_fullpath",memes_fullpath);

                startActivity(i);
            }
        });
    }

    public void getAllMemes(){
        StringRequest strRequest = new StringRequest(Request.Method.POST, getString(R.string.getAllMemesURL), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                String status = "", message ="";
                try{
                    JSONObject obj = new JSONObject(response);
                    status = obj.getString("status");

                    if(status.equals("success")){
                        JSONArray jsonArray = obj.getJSONArray("result");
                        memesList.clear();
                        for(int x = 0; x < jsonArray.length(); x++){
                            String memes_id = "", name = "", description = "", fullpath = "", timestamp = "", isApproved = "", category = "";
                            JSONObject info = jsonArray.getJSONObject(x); // first index since we expect only 1 result

                            memes_id = info.getString("memes_id");
                            name = info.getString("name");
                            description = info.getString("description");
                            fullpath = info.getString("fullpath");
                            timestamp = info.getString("timestamp");
                            isApproved = info.getString("isApproved");
                            category = info.getString("category");
                            if(isApproved.equals("approved")){
                                if((category.equals(getSearchCategory()) || getSearchCategory().equals("None")) && (name.toLowerCase().contains(getSearchInput().toLowerCase()) || getSearchInput().equals(""))){
                                    memesList.add(new Memes(memes_id, name, description, fullpath, timestamp, isApproved, category, "0"));
                                }
                            }
                            //Toast.makeText(getApplicationContext(), memes_id + "--" + memes_name + "--" + memes_description + "--" + memes_fullpath, Toast.LENGTH_LONG).show();
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
                return parameters;
            }
        };

        AppController.getInstance().addToRequestQueue(strRequest);
    }
    public String getSearchInput() {
        return searchInput;
    }

    public void setSearchInput(String searchInput) {
        this.searchInput = searchInput;
    }

    public String getSearchCategory() {
        return searchCategory;
    }

    public void setSearchCategory(String searchCategory) {
        this.searchCategory = searchCategory;
    }
}
