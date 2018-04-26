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

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Response;
import singkorea.singkorea.com.singkorea.api.PushSendUtil;
import singkorea.singkorea.com.singkorea.map.BasicMapActivity;
import singkorea.singkorea.com.singkorea.model.ShopDetailListModel;
import singkorea.singkorea.com.singkorea.util.Const;
import singkorea.singkorea.com.singkorea.util.TinyDB;


public class DetailActivity extends AppCompatActivity {

    ShopDetailListModel.ShopDetailModel shopDetailModel;
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

        PushSendUtil.getSingApi().getShopDetail(idx)
                .enqueue(new retrofit2.Callback<ShopDetailListModel>() {
                    @Override
                    public void onResponse(Call<ShopDetailListModel> call, Response<ShopDetailListModel> response) {
                        if(response.isSuccessful()) {
                            initData(response.body().getLIST().get(0));
                        }
                    }

                    @Override
                    public void onFailure(Call<ShopDetailListModel> call, Throwable t) {

                    }
                });
    }

    public void initData(ShopDetailListModel.ShopDetailModel shopDetailModel) {
        this.shopDetailModel = shopDetailModel;

        List<ShopDetailListModel.ShopDetailModel.ImageFileInfo> imageFileInfos = shopDetailModel.getFILELIST();

        if(imageFileInfos != null && imageFileInfos.size() > 0) {

            ShopDetailListModel.ShopDetailModel.ImageFileInfo imageFileInfo = imageFileInfos.get(0);

            Glide.with(this)
                    .load("http://www.singkorea.com" + imageFileInfo.getThumbPath_R1())
                    .into(topImagePanel);
        }

        TextView collTopbar = findViewById(R.id.collTopbar);
        TextView address = findViewById(R.id.address);
        TextView shopInfoTV = findViewById(R.id.shopInfoTV);
        TextView txtCall = findViewById(R.id.txtCall);
        if("kr".equals(lang)) {
            collTopbar.setText(shopDetailModel.getShopName() + " / " + shopDetailModel.getCategoryName());
            address.setText(shopDetailModel.getAddress());
            shopInfoTV.setText(shopDetailModel.getShopInfo());
        } else {
            collTopbar.setText(shopDetailModel.getShopName_EN() + " / " + shopDetailModel.getCategoryName_EN());
            address.setText(shopDetailModel.getAddress_EN());
            shopInfoTV.setText(shopDetailModel.getShopInfo_EN());
        }
        txtCall.setText(shopDetailModel.getTel());

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layoutGroup = findViewById(R.id.layImageCotainer);
        layoutGroup.removeAllViews();


        for(ShopDetailListModel.ShopDetailModel.ImageFileInfo imageFileInfo : imageFileInfos) {
            View customView = layoutInflater.inflate(R.layout.item_shop_image, null);
            ImageView shopImage = customView.findViewById(R.id.shopImg);

            Glide.with(this)
                    .load("http://www.singkorea.com" + imageFileInfo.getThumbPath_R1())
                    .into(shopImage);

//                setTopNewsClickEvent(textNews, topNewsModel);
            layoutGroup.addView(customView);
        }

        shareUrl = String.format(Const.APP_URL, "shop", shopDetailModel.getIdx());
    }

    public void onClickBack(View view) {
        finish();
    }
    public void onClickShare(View view) {
        switch (view.getId()) {
            case R.id.facebookBtn:
                ShareLinkContent content = new ShareLinkContent.Builder()
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
                    KakaoLink link = KakaoLink.getKakaoLink(DetailActivity.this);
                    KakaoTalkLinkMessageBuilder builder = link.createKakaoTalkLinkMessageBuilder();

                    builder.addText(""+shopDetailModel.getShopName());
                    builder.addInWebLink(shareUrl);
                    builder.addWebButton("연결");
                    link.sendMessage(builder,DetailActivity.this);

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
                Intent intent = new Intent(DetailActivity.this, BasicMapActivity.class);
                if("kr".equals(lang)) {
                    intent.putExtra("title", shopDetailModel.getShopName());
                } else {
                    intent.putExtra("title", shopDetailModel.getShopName_EN());
                }
                intent.putExtra("latitude", Double.parseDouble(shopDetailModel.getLat()));
                intent.putExtra("longitude", Double.parseDouble(shopDetailModel.getLon()));

                startActivity(intent);
                break;
            case R.id.btnCall:
                String tel = "tel:"+shopDetailModel.getTel();
                //startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
                startActivity(new Intent("android.intent.action.DIAL", Uri.parse(tel)));
                break;
            case R.id.btnHomepage:
//                intent = new Intent(Main2Activity.this, WebViewActivity.class);
//                String url = "http://www.singkorea.com/shopdetail_app.html?shopidx=" + bannerModel.getLink() + "&lang="+ toolbarLang.getTag();
//                intent.putExtra("url", url);
                String url = shopDetailModel.getHomepage();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
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
            Toasty.info(getApplicationContext(), "앱이 설치 되지 않았습니다");
        } catch (Exception e) {
            //TODO
            //Log.e("test", e.toString());
        }
    }
}
