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


public class PopupAreaActivity extends AppCompatActivity {

    TinyDB tinyDB = null;
    private RecyclerView recyclerView;
    private List<AreaCodeModel> list;
    private AreaCodeModel lastCheckItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        setContentView(R.layout.activity_popup_list);
        tinyDB = new TinyDB(this);

        recyclerView = findViewById(R.id.recylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //recyclerView.addItemDecoration(new ListSpacingItemDecoration(this, getResources().getDimensionPixelSize(R.dimen.card_elevation)));

        list = new ArrayList<>();
        RecyclerAreaAdapter adapter = new RecyclerAreaAdapter(list, getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnClickItemListener((position) -> {
                    for(AreaCodeModel area : list) {
                        area.setChecked(false);
                    }
                    lastCheckItem = list.get(position);
                    lastCheckItem.setChecked(true);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
        );

        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener((LinearLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //
            }
        };

        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
        loadData(recyclerView);
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
                Intent intent = new Intent();
                intent.putExtra("areaName", lastCheckItem.getAreaName());
                intent.putExtra("areaCode", lastCheckItem.getAreaCode());
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    public void loadData(final RecyclerView view) {
        PushSendUtil.getSingApi().getAreaCode()
                .enqueue(new Callback<List<AreaCodeModel>>() {
                    @Override
                    public void onResponse(Call<List<AreaCodeModel>> call, Response<List<AreaCodeModel>> response) {
                        if(response.isSuccessful()) {
                            list.addAll(response.body());
                            view.getAdapter().notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AreaCodeModel>> call, Throwable t) {

                    }
                });
    }


}
