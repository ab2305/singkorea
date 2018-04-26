package singkorea.singkorea.com.singkorea;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import singkorea.singkorea.com.singkorea.adapter.EndlessRecyclerViewScrollListener;
import singkorea.singkorea.com.singkorea.api.PushSendUtil;
import singkorea.singkorea.com.singkorea.model.AreaCodeModel;
import singkorea.singkorea.com.singkorea.tab.RecyclerAreaAdapter;
import singkorea.singkorea.com.singkorea.util.TinyDB;


public class PopupAreaSelectActivity extends AppCompatActivity {

    TinyDB tinyDB = null;
    String areaCode;
    String category;
    private String areaName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_popup_area_select);
        tinyDB = new TinyDB(this);

        Intent intent = getIntent();
        if(intent != null) {
            TextView textTitle = findViewById(R.id.textTitle);
            TextView shopCntTxt = findViewById(R.id.shopCntTxt);

            areaName = intent.getStringExtra("areaName");
            textTitle.setText(areaName);
            shopCntTxt.setText(intent.getStringExtra("shopCnt"));

            areaCode = intent.getStringExtra("areaCode");
            category = intent.getStringExtra("category");

            ImageView imgMap = findViewById(R.id.img_map);

            Glide.with(this)
                    .load(intent.getIntExtra("imgResId", R.drawable.area1))
                    .into(imgMap);
        }


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
    }

    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.popup_cancel:
                finish();
                break;
            case R.id.popup_ok:
                Intent intent = new Intent(PopupAreaSelectActivity.this, RecyclerListActivity.class);
                intent.putExtra("title", areaName);
                intent.putExtra("areaCode", areaCode);
                intent.putExtra("category", category);
                startActivity(intent);
                finish();
                break;
        }
    }

//    public void loadData(final RecyclerView view) {
//        PushSendUtil.getSingApi().getAreaCode()
//                .enqueue(new Callback<List<AreaCodeModel>>() {
//                    @Override
//                    public void onResponse(Call<List<AreaCodeModel>> call, Response<List<AreaCodeModel>> response) {
//                        if(response.isSuccessful()) {
//                            list.addAll(response.body());
//                            view.getAdapter().notifyDataSetChanged();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<AreaCodeModel>> call, Throwable t) {
//
//                    }
//                });
//    }


}
