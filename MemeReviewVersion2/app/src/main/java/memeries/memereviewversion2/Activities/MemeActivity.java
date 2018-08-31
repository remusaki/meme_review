package memeries.memereviewversion2.Activities;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import memeries.memereviewversion2.Fragments.allMemes;
import memeries.memereviewversion2.Fragments.approvedMemes;
import memeries.memereviewversion2.Fragments.declinedMemes;
import memeries.memereviewversion2.Fragments.pendingMemes;
import memeries.memereviewversion2.Fragments.ratedMemes;
import memeries.memereviewversion2.R;
import memeries.memereviewversion2.SectionPageAdapter;

public class MemeActivity extends AppCompatActivity {

    private static final String TAG = "MemeActivity";

    public static final String SHARED_PREF_KEY = "LE_SHARED_PREF", LAST_NAME_KEY = "lastname", FIRST_NAME_KEY = "firstname", USER_ID_KEY = "user_id", ACCOUNT_TYPE_KEY = "account_type";
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private SectionPageAdapter sectionPageAdapter;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // prevent auto-pop up of keyboard

        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        initResources();
    }

    public void initResources(){
        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE);

        sectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());

        viewPager = (ViewPager)findViewById(R.id.container);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        if(!sharedPreferences.contains(USER_ID_KEY)){
            finish();
        }
    }

    @Override
    protected  void onResume(){
        super.onResume();
        initResources();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.logout:
                onBackPressed();
                break;
            case R.id.updateAccount:
                Intent i = new Intent(this, UpdateAccountActivity.class);
                startActivity(i);
                break;
            case R.id.addMeme:
                Intent add = new Intent(this, AddMeme.class);
                startActivity(add);
                break;
            default:
                Toast.makeText(this, "RIP", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager){
        if(sharedPreferences.getString(ACCOUNT_TYPE_KEY, "").equals("admin")){
            sectionPageAdapter.addFragment(new allMemes(), "All");
            sectionPageAdapter.addFragment(new approvedMemes(), "Approved");
            sectionPageAdapter.addFragment(new pendingMemes(), "Pending");
            sectionPageAdapter.addFragment(new declinedMemes(), "Declined");
            sectionPageAdapter.addFragment(new ratedMemes(), "Rated");
        }else{
            sectionPageAdapter.addFragment(new approvedMemes(), "Approved");
            sectionPageAdapter.addFragment(new ratedMemes(), "Rated");
        }
        viewPager.setAdapter(sectionPageAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        Toast.makeText(getApplicationContext(), "Logout Success", Toast.LENGTH_SHORT).show();
        finish();
    }
}
