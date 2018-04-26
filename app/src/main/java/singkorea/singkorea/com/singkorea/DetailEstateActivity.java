package singkorea.singkorea.com.singkorea;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import singkorea.singkorea.com.singkorea.api.PushSendUtil;
import singkorea.singkorea.com.singkorea.map.BasicMapActivity;
import singkorea.singkorea.com.singkorea.model.EstateDetailParentModel;
import singkorea.singkorea.com.singkorea.model.EstateDetailParentModel.EstateDetailModel;
import singkorea.singkorea.com.singkorea.model.EstateDetailParentModel.EstateDetailModel.ImageFileInfo;
import singkorea.singkorea.com.singkorea.util.Const;
import singkorea.singkorea.com.singkorea.util.TinyDB;


public class DetailEstateActivity extends AppCompatActivity {

    EstateDetailModel estateDetailModel;
    String lang = "kr";
    String shareUrl;

    ImageView topImagePanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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

        PushSendUtil.getSingApi().getEstateDetail(idx)
                .enqueue(new Callback<EstateDetailParentModel>() {
                    @Override
                    public void onResponse(Call<EstateDetailParentModel> call, Response<EstateDetailParentModel> response) {
                        if(response.isSuccessful()) {
                            initData(response.body().getLIST().get(0));
                        }
                    }

                    @Override
                    public void onFailure(Call<EstateDetailParentModel> call, Throwable t) {

                    }
                });
//                .enqueue(new retrofit2.Callback<ShopDetailListModel>() {
//                    @Override
//                    public void onResponse(Call<ShopDetailListModel> call, Response<ShopDetailListModel> response) {
//                        if(response.isSuccessful()) {
//                            initData(response.body().getLIST().get(0));
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ShopDetailListModel> call, Throwable t) {
//
//                    }
//                });
    }

    public void initData(EstateDetailModel estateDetailModel) {
        this.estateDetailModel = estateDetailModel;

        List<ImageFileInfo> imageFileInfos = estateDetailModel.getImgList();


        if(imageFileInfos != null && imageFileInfos.size() > 0) {

            ImageFileInfo imageFileInfo = imageFileInfos.get(0);

            Glide.with(this)
                    .load(imageFileInfo.getThumbPath_R().replace("https", "http"))
                    .into(topImagePanel);
        }


        TextView collTopbar = findViewById(R.id.collTopbar);
        TextView address = findViewById(R.id.address);
        TextView shopInfoTV = findViewById(R.id.shopInfoTV);
        TextView txtCall = findViewById(R.id.txtCall);
        if("kr".equals(lang)) {

            String price = estateDetailModel.getPrice();
            if(!price.contains("$")) {
                price = "$" + price;
            }

            collTopbar.setText(this.estateDetailModel.getTitle() + " / " + price);
            address.setText(this.estateDetailModel.getAddress());
            shopInfoTV.setText(this.estateDetailModel.getContent());
        }
//        else {
//            collTopbar.setText(this.marketDetailModel.getTitle() + " / " + this.marketDetailModel.getCategoryName_EN());
//            address.setText(this.marketDetailModel.getAddress_EN());
//            shopInfoTV.setText(this.marketDetailModel.getShopInfo_EN());
//        }
        txtCall.setText(this.estateDetailModel.getTel());

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layoutGroup = findViewById(R.id.layImageCotainer);
        layoutGroup.removeAllViews();


        for(ImageFileInfo imageFileInfo : imageFileInfos) {
            View customView = layoutInflater.inflate(R.layout.item_shop_image, null);
            ImageView shopImage = customView.findViewById(R.id.shopImg);

            Glide.with(this)
                    .load(imageFileInfo.getThumbPath_R().replace("https", "http"))
                    .into(shopImage);

//                setTopNewsClickEvent(textNews, topNewsModel);
            layoutGroup.addView(customView);
        }

        shareUrl = String.format(Const.APP_URL, "room", estateDetailModel.getIdx());
    }

    public void onClickBack(View view) {
        finish();
    }
    public void onClickShare(View view) {
        switch (view.getId()) {
            case R.id.facebookBtn:
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setQuote("singkorea")
                        .setContentUrl(Uri.parse(shareUrl))
                        .build();
                ShareDialog shareDialog = new ShareDialog(this);
                shareDialog.show(content, ShareDialog.Mode.AUTOMATIC);
                break;
            case R.id.twitterBtn:
                shareTwitter();
                break;
            case R.id.kakaoBtn:
                try {
                    KakaoLink link = KakaoLink.getKakaoLink(DetailEstateActivity.this);
                    KakaoTalkLinkMessageBuilder builder = link.createKakaoTalkLinkMessageBuilder();

                    //builder.addText(""+ estateDetailModel.getTitle());
                    builder.addText("" + shareUrl);
//                    builder.addInWebLink(shareUrl);
//                    builder.addWebButton("웹페이지 연결");
                    link.sendMessage(builder,DetailEstateActivity.this);


                } catch (KakaoParameterException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.whatsappBtn:
                whatsApp();
                break;
        }
    }
    public void onClickEvent(View view) {
        switch (view.getId()) {
            case R.id.btnMap:
                Intent intent = new Intent(DetailEstateActivity.this, BasicMapActivity.class);
//                if("kr".equals(lang)) {
                    intent.putExtra("title", estateDetailModel.getTitle());
//                } else {
//                    intent.putExtra("title", marketDetailModel.getShopName_EN());
//                }

                if ("".equals(estateDetailModel.getLat()) || "".equals(estateDetailModel.getLon())) {
                    break;
                }

                intent.putExtra("latitude", Double.parseDouble(estateDetailModel.getLat()));
                intent.putExtra("longitude", Double.parseDouble(estateDetailModel.getLon()));

                startActivity(intent);
                break;
            case R.id.btnCall:
                String tel = "tel:"+estateDetailModel.getTel();
                //startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
                break;
            case R.id.btnHomepage:
//                intent = new Intent(Main2Activity.this, WebViewActivity.class);
//                String url = "http://www.singkorea.com/shopdetail_app.html?shopidx=" + bannerModel.getLink() + "&lang="+ toolbarLang.getTag();
//                intent.putExtra("url", url);
//                String url = marketDetailModel.get();
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                startActivity(i);
                break;
            default:
                break;

        }

    }

    public void shareTwitter() {
        String strLink = null;
        try {
            strLink = String.format("http://twitter.com/intent/tweet?text=%s",
                    URLEncoder.encode(shareUrl, "utf-8"));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(strLink));
        startActivity(intent);
    }

    public void whatsApp() {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setPackage("com.whatsapp");
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, shareUrl);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        } catch (ActivityNotFoundException e) {
            //앱설치 여부 묻기
        } catch (Exception e) {
            //TODO
            //Log.e("test", e.toString());
        }
    }
}
