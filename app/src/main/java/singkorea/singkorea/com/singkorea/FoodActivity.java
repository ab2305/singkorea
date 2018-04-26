package singkorea.singkorea.com.singkorea;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.shizhefei.view.viewpager.SViewPager;

import singkorea.singkorea.com.singkorea.tab.ListFragment;
import singkorea.singkorea.com.singkorea.tab.MapFragment;
import singkorea.singkorea.com.singkorea.tab.PromotionFragment;
import singkorea.singkorea.com.singkorea.tab.SubCategoryFragment;
import singkorea.singkorea.com.singkorea.tab.TestFragment;
import singkorea.singkorea.com.singkorea.util.TinyDB;


public class FoodActivity extends AppCompatActivity implements TestFragment.OnFragmentInteractionListener {

    private IndicatorViewPager indicatorViewPager;
    private FixedIndicatorView indicator;
    private String category;

    TinyDB tinyDB;
    private String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        tinyDB = new TinyDB(this);
        lang = tinyDB.getString("lang");

        TextView textView = findViewById(R.id.toolbar_title);
        int selectedColor = Color.parseColor("#34498D");

        SViewPager viewPager = findViewById(R.id.tabmain_viewPager);
        indicator = findViewById(R.id.tabmain_indicator);
        indicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectedColor, Color.BLACK));
        indicator.setScrollBar(new ColorBar(getApplicationContext(), selectedColor, 5));

        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        viewPager.setCanScroll(true);
        viewPager.setOffscreenPageLimit(4);

        Intent intent = getIntent();
        if(intent != null) {
            category = intent.getStringExtra("category");
            String title = intent.getStringExtra("title");
            textView.setText(title);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private String[] tabNames = {"프로모션", "리스트", "지역별", "카테고리"};
        private String[] tabNamesEng = {"Promotion", "List", "Area", "Category"};
        private LayoutInflater inflater;

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.tab_top, container, false);
            }
            TextView textView = (TextView) convertView;
            if ("kr".equals(lang)) {
                textView.setText(tabNames[position]);
            } else {
                textView.setText(tabNamesEng[position]);
            }
            return textView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            Fragment mainFragment = null;
            switch (position) {
                case 0: //promotion banner
                    mainFragment = new PromotionFragment();
                    break;
                case 1: //grid
                    mainFragment = new ListFragment();
                    break;
                case 2: //map style
                    mainFragment = new MapFragment();
                    break;
                case 3: //list style
                    mainFragment = new SubCategoryFragment();
                    break;
                default:
                    mainFragment = null;
                    break;
            }

            Bundle bundle = new Bundle();
            bundle.putString("category", category);
            mainFragment.setArguments(bundle);
            return mainFragment;
        }
    }

    public void onClickEvent(View view) {
        finish();
    }
}
