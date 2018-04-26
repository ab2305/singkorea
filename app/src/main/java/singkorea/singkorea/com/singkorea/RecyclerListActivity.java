package singkorea.singkorea.com.singkorea;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import singkorea.singkorea.com.singkorea.tab.ListEstateFragment;
import singkorea.singkorea.com.singkorea.tab.ListFragment;
import singkorea.singkorea.com.singkorea.tab.ListMainClipFragment;
import singkorea.singkorea.com.singkorea.tab.ListMainFragment;
import singkorea.singkorea.com.singkorea.util.TinyDB;

public class RecyclerListActivity extends AppCompatActivity {

    private String category;
    private String type;
    private String search;
    private TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_list);

        tinyDB = new TinyDB(this);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView textView = findViewById(R.id.toolbar_title);

        String areaCode = null;
        Intent intent = getIntent();
        if(intent != null) {
            type = intent.getStringExtra("type");
            search = intent.getStringExtra("search");
            category = intent.getStringExtra("category");
            String title = intent.getStringExtra("title");
            areaCode = intent.getStringExtra("areaCode");
            textView.setText(title);
        }
        
        //ListFragment listFragment = new ListFragment();

        Fragment fragment;
        if("CA05".equals(category)) {
            fragment = new ListEstateFragment();
            String userId = tinyDB.getString(LoginActivity.KEY_USER_ID);
            if (userId != null && !"".equals(userId)) {
                findViewById(R.id.toolbar_add).setVisibility(View.VISIBLE);
            }
        } else if("promotion".equals(type)) {
            fragment = new ListMainFragment();
        } else if("clip".equals(type)) {
            fragment = new ListMainClipFragment();
        } else if ("life".equals(type)) {
            fragment = new ListMainFragment();
        } else if ("event".equals(type)) {
            fragment = new ListMainFragment();
        } else {
            fragment = new ListFragment();
        }

        Bundle bundle = new Bundle();
        if ("search".equals(type)) {
            bundle.putString("search", search);
            //if(areaCode != null && "".equals(areaCode))

            fragment.setArguments(bundle);
        } else if ("promotion".equals(type) || "clip".equals(type) || "life".equals(type) || "event".equals(type)) {
            bundle.putString("shoptype",type);
            fragment.setArguments(bundle);
        } else {
            bundle.putString("category", category);
            //if(areaCode != null && "".equals(areaCode))
            bundle.putString("areaCode", areaCode);
            fragment.setArguments(bundle);
        }

        
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add( R.id.fragment_container, fragment );
        fragmentTransaction.commit();
    }

    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_add:
                Intent intent = new Intent(RecyclerListActivity.this, EstateWriteActivity.class);
                startActivity(intent);
                break;
        }
    }
}
