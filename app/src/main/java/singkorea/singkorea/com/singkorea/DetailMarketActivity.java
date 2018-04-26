package singkorea.singkorea.com.singkorea;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import singkorea.singkorea.com.singkorea.api.PushSendUtil;
import singkorea.singkorea.com.singkorea.model.MarketDetailParentModel;
import singkorea.singkorea.com.singkorea.model.MarketDetailParentModel.MaketDetailModel;
import singkorea.singkorea.com.singkorea.util.TinyDB;


public class DetailMarketActivity extends AppCompatActivity {

    MaketDetailModel marketDetailModel;
    String lang = "kr";

    ImageView topImagePanel;
    ViewGroup containerImg;
    ViewGroup containerInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_market);

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        //TextView collTopbarText = findViewById(R.id.toolbar_title);

        collapsingToolbarLayout.setTitle(getTitle());
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
//        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        topImagePanel = findViewById(R.id.topImagePanel);

        TinyDB tinyDB = new TinyDB(getApplicationContext());
        lang = tinyDB.getString("lang");

        Intent intent = getIntent();
        String idx = "0";
//        String category = "";
        if(intent != null) {
            idx = intent.getStringExtra("idx");
//            category = intent.getStringExtra("category");
            String toolbarTitle = intent.getStringExtra("toolbarTitle");

            TextView toolbarTitleView = findViewById(R.id.toolbar_title);
            toolbarTitleView.setText(toolbarTitle);
        }

        PushSendUtil.getSingApi().getMarketDetail(idx)
                .enqueue(new Callback<MarketDetailParentModel>() {
                    @Override
                    public void onResponse(Call<MarketDetailParentModel> call, Response<MarketDetailParentModel> response) {
                        if (response.isSuccessful()) {
                            initData(response.body().getLIST().get(0));
                        }
                    }

                    @Override
                    public void onFailure(Call<MarketDetailParentModel> call, Throwable t) {

                    }
                });
    }

    public void initData(MaketDetailModel detailModel) {
        this.marketDetailModel = detailModel;

        List<MaketDetailModel.ImageFileInfo> imageFileInfos = detailModel.getImgList();


        if(imageFileInfos != null && imageFileInfos.size() > 0) {

            MaketDetailModel.ImageFileInfo imageFileInfo = imageFileInfos.get(0);

            Glide.with(this)
                    .load(imageFileInfo.getThumbPath_R().replace("https", "http"))
                    .into(topImagePanel);
        }


        TextView collTopbar = findViewById(R.id.collTopbar);
        TextView title = findViewById(R.id.title);
        TextView price = findViewById(R.id.price);
        TextView address = findViewById(R.id.address);
        TextView txtId = findViewById(R.id.txt_id);
        TextView txtDate = findViewById(R.id.txt_date);

        title.setText(detailModel.getProductName());

        String priceTxt = detailModel.getPrice();
        if(priceTxt.contains("$")) {
            price.setText(priceTxt);
        } else {
            price.setText("$"+ priceTxt);
        }

        address.setText(detailModel.getSellAddress());
        txtId.setText(detailModel.getUserID());
        txtDate.setText(detailModel.getRegDate());

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layoutGroup = findViewById(R.id.layImageCotainer);
        layoutGroup.removeAllViews();

        containerImg = layoutGroup;
        containerInfo = findViewById(R.id.container_info);

        for(MaketDetailModel.ImageFileInfo imageFileInfo : imageFileInfos) {
            View customView = layoutInflater.inflate(R.layout.item_shop_image, null);
            ImageView shopImage = customView.findViewById(R.id.shopImg);

            Glide.with(this)
                    .load(imageFileInfo.getThumbPath_R().replace("https", "http"))
                    .into(shopImage);

            layoutGroup.addView(customView);
        }
    }

    public void onClickBack(View view) {
        finish();
    }

    public void onClickEvent(View view) {
        switch (view.getId()) {

            case R.id.btnCall:
                String tel = "tel:"+ marketDetailModel.getSellTel();
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
                break;
            case R.id.detail_info:
                containerImg.setVisibility(View.VISIBLE);
                containerInfo.setVisibility(View.GONE);
                break;
            case R.id.product_info:
                containerImg.setVisibility(View.GONE);
                containerInfo.setVisibility(View.VISIBLE);
                break;
        }

    }
}
