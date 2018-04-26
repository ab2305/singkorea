package singkorea.singkorea.com.singkorea;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import singkorea.singkorea.com.singkorea.adapter.EndlessRecyclerViewScrollListener;
import singkorea.singkorea.com.singkorea.api.PushSendUtil;
import singkorea.singkorea.com.singkorea.model.MarketItemModel;
import singkorea.singkorea.com.singkorea.model.MarketListModel;
import singkorea.singkorea.com.singkorea.tab.RecyclerMarketGridAdapter;
import singkorea.singkorea.com.singkorea.util.TinyDB;

public class MarketActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String category;
    private String currentType;
    private List<MarketItemModel> marketItemModelList;

    private ImageButton btnAll;
    private ImageButton btnEletronic;
    private ImageButton btnFurniture;
    private ImageButton btnFashion;
    private ImageButton btnLuxury;
    private ImageButton btnBaby;
    private ImageButton btnComputer;
    private ImageButton btnMusic;
    private ImageButton btnLife;
    private ImageButton btnHobby;
    private ImageButton btnCar;
    private ImageButton btnSprots;

    boolean isKorea = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = getIntent();
        if(intent != null) {
            category = intent.getStringExtra("category");
            String title = intent.getStringExtra("title");
            TextView toolbarTitleTV = findViewById(R.id.toolbar_title);
            toolbarTitleTV.setText(title);

            isKorea = "kr".equals(intent.getStringExtra("lang")) ? true : false;
        }

        recyclerView = findViewById(R.id.recylerMarket);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        marketItemModelList = new ArrayList<>();
        RecyclerMarketGridAdapter adapter = new RecyclerMarketGridAdapter(marketItemModelList, getApplicationContext(), isKorea);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickItemListener((position) -> {
            Intent intentDetail = new Intent(MarketActivity.this, DetailMarketActivity.class);
            intentDetail.putExtra("idx", "" + marketItemModelList.get(position).getIdx());
            intentDetail.putExtra("toolbarTitle", "" + marketItemModelList.get(position).getIdx());
            startActivity(intentDetail);
        });

        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener((GridLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadData(currentType, page, view);
            }
        };

        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
        loadData(currentType="99", 0, recyclerView);

        btnAll = findViewById(R.id.btn_all);
        btnAll.setSelected(true);
        btnEletronic = findViewById(R.id.btn_electronic);
        btnFurniture = findViewById(R.id.btn_furniture);
        btnFashion = findViewById(R.id.btn_fashion);
        btnLuxury = findViewById(R.id.btn_luxury);
        btnBaby = findViewById(R.id.btn_baby);
        btnComputer = findViewById(R.id.btn_computer);
        btnMusic = findViewById(R.id.btn_music);
        btnLife = findViewById(R.id.btn_life);
        btnHobby = findViewById(R.id.btn_hobby);
        btnCar = findViewById(R.id.btn_car);
        btnSprots = findViewById(R.id.btn_sports);

        String userID = (new TinyDB(this)).getString(LoginActivity.KEY_USER_ID);
        if(!"".equals(userID)) {
            findViewById(R.id.toolbar_add).setVisibility(View.VISIBLE);
        }
    }

    public void loadData(String type, int page, final RecyclerView view) {
        ++page;
        PushSendUtil.getSingApi().getMarketList(type, page)
                .enqueue(new retrofit2.Callback<MarketListModel>() {
                    @Override
                    public void onResponse(Call<MarketListModel> call, Response<MarketListModel> response) {
                        if (response.isSuccessful()) {
                            marketItemModelList.addAll(response.body().getLIST());
                            view.getAdapter().notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<MarketListModel> call, Throwable t) {
                        Log.e("tag", t.toString());
                    }
                });
    }

    public void onClickEventMarketTop(View view) {
        switch (view.getId()) {
            case R.id.toolbar_back:
                finish();
                break;
            case R.id.toolbar_add:
                Intent intent = new Intent(MarketActivity.this, MarketRegisterActivity.class );
                startActivity(intent); //todo 리프레시 로직?
                break;
            default:
                break;
        }
    }

    public void onClickEventMarket(View view) {
        
        btnAll.setSelected(false);
        btnEletronic.setSelected(false);
        btnFurniture.setSelected(false);
        btnFashion.setSelected(false);
        btnLuxury.setSelected(false);
        btnBaby.setSelected(false);
        btnComputer.setSelected(false);
        btnMusic.setSelected(false);
        btnLife.setSelected(false);
        btnHobby.setSelected(false);
        btnCar.setSelected(false);
        btnSprots.setSelected(false);

        switch (view.getId()) {
            case R.id.btn_all:
                btnAll.setSelected(true);
                currentType = "99";
                break;
            case R.id.btn_electronic:
                btnEletronic.setSelected(true);
                currentType = "M01";
                break;
            case R.id.btn_furniture:
                btnFurniture.setSelected(true);
                currentType = "M02";
                break;
            case R.id.btn_fashion:
                btnFashion.setSelected(true);
                currentType = "M03";
                break;
            case R.id.btn_luxury:
                btnLuxury.setSelected(true);
                currentType = "M12";
                break;
            case R.id.btn_baby:
                btnBaby.setSelected(true);
                currentType = "M06";
                break;
            case R.id.btn_computer:
                btnComputer.setSelected(true);
                currentType = "M04";
                break;
            case R.id.btn_music:
                btnMusic.setSelected(true);
                currentType = "M07";
                break;
            case R.id.btn_life:
                btnLife.setSelected(true);
                currentType = "M08";
                break;
            case R.id.btn_hobby:
                btnHobby.setSelected(true);
                currentType = "M09";
                break;
            case R.id.btn_car:
                btnCar.setSelected(true);
                currentType = "M10";
                break;
            case R.id.btn_sports:
                btnSprots.setSelected(true);
                currentType = "M11";
                break;
            default:
                break;
        }
        marketItemModelList.clear();
        loadData(currentType, 0, recyclerView);
    }
}
