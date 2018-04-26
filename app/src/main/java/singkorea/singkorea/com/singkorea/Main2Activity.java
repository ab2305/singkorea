package singkorea.singkorea.com.singkorea;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;
import java.net.URLEncoder;
import java.io.UnsupportedEncodingException;

import es.dmoral.toasty.Toasty;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Response;
import singkorea.singkorea.com.singkorea.adapter.ViewPagerAdapter;
import singkorea.singkorea.com.singkorea.adapter.ViewPagerClipAdapter;
import singkorea.singkorea.com.singkorea.api.PushSendUtil;
import singkorea.singkorea.com.singkorea.model.BannerModel;
import singkorea.singkorea.com.singkorea.model.ClipModel;
import singkorea.singkorea.com.singkorea.model.ExchangeMoneyModel;
import singkorea.singkorea.com.singkorea.model.ShopModel;
import singkorea.singkorea.com.singkorea.model.TopNewsModel;
import singkorea.singkorea.com.singkorea.util.TinyDB;
import singkorea.singkorea.com.singkorea.util.Rounder;

//TODO 헤더처리 : https://www.singkorea.com/newsList_app.php?app=Y&lang=(en || kr)
//TODO

public class Main2Activity extends AppCompatActivity {

    TinyDB tinyDB;
    String lang;

    ImageView toolbarLang = null;
    private EditText findedt;
    ViewPagerAdapter bestAdapter, promotionAdapter, lifeAdapter, eventAdapter;
    ViewPagerClipAdapter clipAdapter;
    private TextView toolbarLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        // wifi 또는 모바일 네트워크 어느 하나라도 연결이 되어있다면,
        if (wifi.isConnected() || mobile.isConnected()) {
            Log.i("연결됨" , "연결이 되었습니다.");

        } else {
            Log.i("연결 안 됨" , "연결이 다시 한번 확인해주세요");
        }
        if(Build.VERSION.SDK_INT >=23 && (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(Main2Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1);
        }

        toolbarLang = findViewById(R.id.toolbar_lang);
        toolbarLogin = findViewById(R.id.toolbar_login);
        tinyDB = new TinyDB(this);
        lang = tinyDB.getString("lang");
        if("kr".equals(lang)) {
            toolbarLang.setTag("kr");
            toolbarLang.setImageResource(R.drawable.en_flag);
        } else {
            toolbarLang.setTag("en");
            toolbarLang.setImageResource(R.drawable.kor_flag);
        }

        toolbarLang.setOnClickListener(view -> {
            if("kr".equals( toolbarLang.getTag()) ) {
                toolbarLang.setImageResource(R.drawable.kor_flag);

                toolbarLang.setTag("en");
                tinyDB.putString("lang", "en");
                topNews(false);
                changeButton(false);

                ViewPagerAdapter.setLang(true);
            } else {

                toolbarLang.setImageResource(R.drawable.en_flag);

                toolbarLang.setTag("kr");
                tinyDB.putString("lang", "kr");
                topNews(true);
                changeButton(true);

                ViewPagerAdapter.setLang(false);
            }
            if(bestAdapter != null) bestAdapter.notifyDataSetChanged();
            if(promotionAdapter != null) promotionAdapter.notifyDataSetChanged();
            if(clipAdapter != null) clipAdapter.notifyDataSetChanged();
            if(lifeAdapter != null) lifeAdapter.notifyDataSetChanged();
            if(eventAdapter != null) eventAdapter.notifyDataSetChanged();
        });

//        final ViewPager viewpagerBest = findViewById(R.id.viewPagerBest);
//        final CircleIndicator indicatorBest = findViewById(R.id.indicatorBest);

        final ViewPager viewpagerPromotion = findViewById(R.id.viewPagerPromotion);
        final CircleIndicator indicatorPromotion = findViewById(R.id.indicatorPromotion);

        final ViewPager viewpagerLifeCycle = findViewById(R.id.viewPagerLifeCycle);
        final CircleIndicator indicatorLifeCycle = findViewById(R.id.indicatorLifeCycle);

        final ViewPager viewpagerEvent = findViewById(R.id.viewPagerEvent);
        final CircleIndicator indicatorEvent = findViewById(R.id.indicatorEvent);

        final ViewPager viewpagerClip = findViewById(R.id.viewPagerClip);
        final CircleIndicator indicatorClip = findViewById(R.id.indicatorClip);

        PushSendUtil.getSingApi().getBanner("main1", 2)
                .enqueue(new retrofit2.Callback<List<BannerModel>>() {
                    @Override
                    public void onResponse(Call<List<BannerModel>> call, Response<List<BannerModel>> response) {
                        if(response.isSuccessful()) {
                            bannerImageLoad(0, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<BannerModel>> call, Throwable t) {
                        Log.e("tag", t.toString());
                    }
                });

        PushSendUtil.getSingApi().getBanner("main2", 1)
                .enqueue(new retrofit2.Callback<List<BannerModel>>() {
                    @Override
                    public void onResponse(Call<List<BannerModel>> call, Response<List<BannerModel>> response) {
                        if(response.isSuccessful()) {
                            bannerImageLoad(2, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<BannerModel>> call, Throwable t) {
                        Log.e("tag", t.toString());
                    }
                });

        PushSendUtil.getSingApi().getExchangeMoney()
                .enqueue(new retrofit2.Callback<List<ExchangeMoneyModel>>() {
                    @Override
                    public void onResponse(Call<List<ExchangeMoneyModel>> call, Response<List<ExchangeMoneyModel>> response) {
                        if(response.isSuccessful()) {

                            List<ExchangeMoneyModel> list = response.body();

                            TextView btnMoney = findViewById(R.id.txtMoney);
                            for (ExchangeMoneyModel exchangeMoneyModel : list) {
                                if("D".equals( exchangeMoneyModel.getUpDown()) ) {
                                    btnMoney.setTextColor(Color.BLUE);
                                    btnMoney.setText("▼ " + exchangeMoneyModel.getMoney());
                                } else {
                                    btnMoney.setTextColor(Color.RED);
                                    btnMoney.setText("▲ " + exchangeMoneyModel.getMoney());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ExchangeMoneyModel>> call, Throwable t) {

                    }
                });

        topNews(true);


        PushSendUtil.getSingApi().getShop("best")
                .enqueue(new retrofit2.Callback<List<ShopModel>>() {
                    @Override
                    public void onResponse(Call<List<ShopModel>> call, Response<List<ShopModel>> response) {
                        if(response.isSuccessful()) {

                            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            LinearLayout layoutGroup = findViewById(R.id.layBest);
                            layoutGroup.removeAllViews();

                            if(response.isSuccessful()) {

                                for(final ShopModel item : response.body()) {
                                    View customView = layoutInflater.inflate(R.layout.item_best, null);
                                    TextView textCategory = customView.findViewById(R.id.textCategory);
                                    TextView textTitle = customView.findViewById(R.id.textTitle);
                                    ImageView img = customView.findViewById(R.id.img);

                                    if("kr".equals(lang)) {
                                        textCategory.setText(item.getCategoryName());
                                        textTitle.setText(item.getShopName());
                                    } else {
                                        textCategory.setText(item.getCategoryName_EN());
                                        textTitle.setText(item.getShopName_EN());
                                    }
                                    Glide.with(Main2Activity.this)
                                            .load("http://www.singkorea.com" + item.getThumbPath())
                                            .into(img);

                                    customView.setOnClickListener(View->{
                                        Intent intent = new Intent(Main2Activity.this, DetailActivity.class);
                                        intent.putExtra("idx", item.getIdx());
                                        intent.putExtra("toolbarTitle", item.getShopName());
                                        startActivity(intent);
                                    });

                                    layoutGroup.addView(customView);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ShopModel>> call, Throwable t) {
                        Log.e("tag", t.toString());
                    }
                });

        PushSendUtil.getSingApi().getShop("promotion")
                .enqueue(new retrofit2.Callback<List<ShopModel>>() {
                    @Override
                    public void onResponse(Call<List<ShopModel>> call, Response<List<ShopModel>> response) {
                        if(response.isSuccessful()) {
                            promotionAdapter = new ViewPagerAdapter(Main2Activity.this, response.body());
                            viewpagerPromotion.setAdapter(promotionAdapter);
                            indicatorPromotion.setViewPager(viewpagerPromotion);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ShopModel>> call, Throwable t) {
                        Log.e("tag", t.toString());
                    }
                });

        PushSendUtil.getSingApi().getClip()
                .enqueue(new retrofit2.Callback<List<ClipModel>>() {
                    @Override
                    public void onResponse(Call<List<ClipModel>> call, Response<List<ClipModel>> response) {
                        if(response.isSuccessful()) {
                            clipAdapter = new ViewPagerClipAdapter(Main2Activity.this, response.body());
                            viewpagerClip.setAdapter(clipAdapter);
                            indicatorClip.setViewPager(viewpagerClip);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ClipModel>> call, Throwable t) {
                        Log.e("tag", t.toString());
                    }
                });

        PushSendUtil.getSingApi().getShop("life")
                .enqueue(new retrofit2.Callback<List<ShopModel>>() {
                    @Override
                    public void onResponse(Call<List<ShopModel>> call, Response<List<ShopModel>> response) {
                        if(response.isSuccessful()) {
                            Log.e("tag", response.body().get(0).getCategoryName());
                            lifeAdapter = new ViewPagerAdapter(Main2Activity.this, response.body());
                            viewpagerLifeCycle.setAdapter(lifeAdapter);
                            indicatorLifeCycle.setViewPager(viewpagerLifeCycle);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ShopModel>> call, Throwable t) {
                        Log.e("tag", t.toString());
                    }
                });


        PushSendUtil.getSingApi().getShop("event")
                .enqueue(new retrofit2.Callback<List<ShopModel>>() {
                    @Override
                    public void onResponse(Call<List<ShopModel>> call, Response<List<ShopModel>> response) {
                        if(response.isSuccessful()) {
                            Log.e("tag", response.body().get(0).getCategoryName());
                            eventAdapter = new ViewPagerAdapter(Main2Activity.this, response.body());
                            viewpagerEvent.setAdapter(eventAdapter);
                            indicatorEvent.setViewPager(viewpagerEvent);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ShopModel>> call, Throwable t) {
                        Log.e("tag", t.toString());
                    }
                });



        PushSendUtil.getSingApi().getBanner("main3", 3)
                .enqueue(new retrofit2.Callback<List<BannerModel>>() {
                    @Override
                    public void onResponse(Call<List<BannerModel>> call, Response<List<BannerModel>> response) {
                        if(response.isSuccessful()) {
                            bannerImageLoad(3, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<BannerModel>> call, Throwable t) {
                        Log.e("tag", t.toString());
                    }
                });


        PushSendUtil.getSingApi().getBanner("main4", 3)
                .enqueue(new retrofit2.Callback<List<BannerModel>>() {
                    @Override
                    public void onResponse(Call<List<BannerModel>> call, Response<List<BannerModel>> response) {
                        if(response.isSuccessful()) {
                            bannerImageLoad(5, response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<BannerModel>> call, Throwable t) {
                        Log.e("tag", t.toString());
                    }
                });
    }

    public void bannerImageLoad(int startPos, List<BannerModel> bannerModels) {
        int i=startPos;
        for(BannerModel bannerModel : bannerModels) {
            String fileUrl = bannerModel.getFilePath();
            if(fileUrl == null)
                break;

            int resourceImage =  getResources().getIdentifier("banner0"+ (++i), "id", getPackageName());
            ImageView bannerView = findViewById(resourceImage);



            setBannerClickEvent(bannerView, bannerModel);

            if(fileUrl.endsWith(".gif")) {
                Glide.with(Main2Activity.this).asGif()
                        .load("http://www.singkorea.com"+bannerModel.getFilePath())
                        .into(bannerView);
            } else {
                Glide.with(Main2Activity.this)
                        .load("http://www.singkorea.com"+bannerModel.getFilePath())
                        .into(bannerView);
            }
        }
    }


    public void topNews(final boolean doChangeKoren) {
        PushSendUtil.getSingApi()
                .getTopNews()
                .enqueue(new retrofit2.Callback<List<TopNewsModel>>() {
                    @Override
                    public void onResponse(Call<List<TopNewsModel>> call, Response<List<TopNewsModel>> response) {
                        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        LinearLayout layoutGroup = findViewById(R.id.layTopNews);
                        layoutGroup.removeAllViews();

                        if(response.isSuccessful()) {

                            for(TopNewsModel topNewsModel : response.body()) {
                                View customView = layoutInflater.inflate(R.layout.item_topnews, null);
                                TextView textNews = customView.findViewById(R.id.textNews);

                                if(doChangeKoren) {
                                    textNews.setText(topNewsModel.getNewsTitle());
                                } else {
                                    textNews.setText(topNewsModel.getNewsTitle_EN());
                                }

                                setTopNewsClickEvent(textNews, topNewsModel);
                                layoutGroup.addView(customView);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<TopNewsModel>> call, Throwable t) {

                    }
                });
    }

    public void changeButton(boolean doChangeKoren) {

        ImageView img01 = findViewById(R.id.category01);
        ImageView img02 = findViewById(R.id.category02);
        ImageView img03 = findViewById(R.id.category03);
        ImageView img04 = findViewById(R.id.category04);
        ImageView img05 = findViewById(R.id.category05);
        ImageView img06 = findViewById(R.id.category06);
        ImageView img07 = findViewById(R.id.category07);
        ImageView img08 = findViewById(R.id.category08);
        ImageView img09 = findViewById(R.id.category09);
        ImageView img10 = findViewById(R.id.category10);

        if(doChangeKoren) {
            img01.setImageResource(R.drawable.korean_food_ko);
            img02.setImageResource(R.drawable.food_ko);
            img03.setImageResource(R.drawable.k_beauty_ko);
            img04.setImageResource(R.drawable.event_ko);
            img05.setImageResource(R.drawable.property_ko);
            img06.setImageResource(R.drawable.market_ko);
            img07.setImageResource(R.drawable.community_ko);
            img08.setImageResource(R.drawable.hospital_ko);
            img09.setImageResource(R.drawable.carrental_ko);
            img10.setImageResource(R.drawable.etc_ko);
        } else {
            img01.setImageResource(R.drawable.korean_food_en);
            img02.setImageResource(R.drawable.food_en);
            img03.setImageResource(R.drawable.k_beauty_en);
            img04.setImageResource(R.drawable.event_en);
            img05.setImageResource(R.drawable.property_en);
            img06.setImageResource(R.drawable.market_en);
            img07.setImageResource(R.drawable.community_en);
            img08.setImageResource(R.drawable.hospital_en);
            img09.setImageResource(R.drawable.carrental_en);
            img10.setImageResource(R.drawable.etc_en);
        }
    }

    public static String getURLEncode(String content){
        try {
//          return URLEncoder.encode(content, "utf-8");   // UTF-8
            return URLEncoder.encode(content, "euc-kr");  // EUC-KR
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void onClickEvent(View view) {

        String lang = (String)toolbarLang.getTag();
        Intent intent = new Intent(Main2Activity.this, WebViewActivity.class);
        //url = "http://www.singkorea.com/login_app.html";
        //intent.putExtra("url", url);
        //startActivityForResult(intent, SUCCESS_LOGIN);
        String url = "";
        switch (view.getId()) {

            case R.id.toolbar_login:
                if("LogOut".equals(toolbarLogin.getText().toString())) {
                    toolbarLogin.setText("LogIn");
                    tinyDB.remove(LoginActivity.KEY_USER_ID);
                    tinyDB.remove(LoginActivity.KEY_PASSWORD);
                    return;
                }

                Intent loginIntent = new Intent(Main2Activity.this, LoginActivity.class);
                startActivityForResult(loginIntent, SUCCESS_LOGIN);
                return;
            case R.id.find_btn:
                findedt = findViewById(R.id.edit_find);
                final String findstr = findedt.getText().toString();
                if("".equals(findstr)) {
                    Toasty.warning(getApplicationContext(), "Input Find String", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent11 = new Intent(Main2Activity.this, RecyclerListActivity.class);
                intent11.putExtra("search", findstr);

                intent11.putExtra("lang", lang);

                intent11.putExtra("title", findstr);
                intent11.putExtra( "type","search");

                startActivity(intent11);
                return;
            case R.id.headerNews:
                url = "http://www.singkorea.com/newsList_app.php?app=Y&lang="+lang;
                break;
            case R.id.category01:
                //url = "http://www.singkorea.com/shop_app.php?cate=CA04&lang="+lang;
                Intent intent01 = new Intent(Main2Activity.this, FoodActivity.class);
                intent01.putExtra("category", "CA04");

                intent01.putExtra("lang", lang);
                if("kr".equals(lang))
                    intent01.putExtra("title", "한국 음식점");
                else
                    intent01.putExtra("title", "Korean Food");

                startActivity(intent01);
                return;
            case R.id.category02:
                //url = "http://www.singkorea.com/shop_app.php?cate=CA01&lang="+lang;
                Intent intent02 = new Intent(Main2Activity.this, FoodActivity.class);
                intent02.putExtra("category", "CA01");
                intent02.putExtra("lang", lang);

                if("kr".equals(lang))
                    intent02.putExtra("title", "음식점");
               else
                    intent02.putExtra("title", "Food");

                startActivity(intent02);
                return;
            case R.id.category03:
//                url = "http://www.singkorea.com/shop_app.php?cate=CA13&lang="+lang;
                Intent intent03 = new Intent(Main2Activity.this, FoodActivity.class);
                intent03.putExtra("category", "CA13");
                intent03.putExtra("lang", lang);

                if("kr".equals(lang))
                    intent03.putExtra("title", "한국미용");
                else
                    intent03.putExtra("title", "K Beauty");

                startActivity(intent03);
                return;
            case R.id.category04:
//                url = "http://www.singkorea.com/shop_app.php?cate=CA08&lang="+lang;
                Intent intent04 = new Intent(Main2Activity.this, RecyclerListActivity.class);
                intent04.putExtra("category", "CA08");
                intent04.putExtra("lang", lang);

                if("kr".equals(lang))
                    intent04.putExtra("title", "공연이벤트");
                else
                    intent04.putExtra("title", "Event");

                startActivity(intent04);
                return;
            case R.id.category05:
                //url = "http://www.singkorea.com/shop_app.php?cate=CA05&lang="+lang;
                Intent intent05 = new Intent(Main2Activity.this, RecyclerListActivity.class);
                intent05.putExtra("category", "CA05");
                intent05.putExtra("lang", lang);

                if("kr".equals(lang))
                    intent05.putExtra("title", "부동산");
                else
                    intent05.putExtra("title", "Property");

                startActivity(intent05);
                return;
            case R.id.category06:
//                url = "http://www.singkorea.com/shop_app.php?cate=CA06&lang="+lang;
                Intent intent06 = new Intent(Main2Activity.this, MarketActivity.class);
                intent06.putExtra("category", "CA06");
                intent06.putExtra("lang", lang);

                if("kr".equals(lang))
                    intent06.putExtra("title", "중고장터");
                else
                    intent06.putExtra("title", "Market");

                startActivity(intent06);

                return;
            case R.id.category07:
//                url = "http://www.singkorea.com/shop_app.php?cate=CA07&lang="+lang;
                Intent intent07 = new Intent(Main2Activity.this, RecyclerListActivity.class);
                intent07.putExtra("category", "CA07");
                intent07.putExtra("lang", lang);

                if("kr".equals(lang))
                    intent07.putExtra("title", "동호회");
                else
                    intent07.putExtra("title", "Community");

                startActivity(intent07);
                return;
            case R.id.category08:
//                url = "http://www.singkorea.com/shop_app.php?cate=CA03&lang="+lang;
                Intent intent08 = new Intent(Main2Activity.this, RecyclerListActivity.class);
                intent08.putExtra("category", "CA03");
                intent08.putExtra("lang", lang);

                if("kr".equals(lang))
                    intent08.putExtra("title", "병원");
                else
                    intent08.putExtra("title", "Hospital");

                startActivity(intent08);
                return;
            case R.id.category09:
                //url = "http://www.singkorea.com/shop_app.php?cate=CA16&lang="+lang;
                Intent intent09 = new Intent(Main2Activity.this, RecyclerListActivity.class);
                intent09.putExtra("category", "CA16");
                intent09.putExtra("lang", lang);

                if("kr".equals(lang))
                    intent09.putExtra("title", "렌트카");
                else
                    intent09.putExtra("title", "Car Rental");

                startActivity(intent09);
                return;

            case R.id.category10: //etc
                url = "http://www.singkorea.com/shop_appphp?cate=CA04&lang="+lang;
                Intent intent10 = new Intent(Main2Activity.this, IntroActivity.class);
                intent10.putExtra("category", "CA01");
                intent10.putExtra("lang", lang);
                startActivity(intent10);
                return;
            case R.id.promotiondetail:
                Intent intent12 = new Intent(Main2Activity.this, RecyclerListActivity.class);
                intent12.putExtra("lang", lang);
                intent12.putExtra("title", "Promotion");
                intent12.putExtra( "type","promotion");
                startActivity(intent12);

                return;
            case R.id.clipdetail:
                Intent intent13 = new Intent(Main2Activity.this, RecyclerListActivity.class);
                intent13.putExtra("lang", lang);
                intent13.putExtra("title", "Shop New CLip");
                intent13.putExtra( "type","clip");
                startActivity(intent13);
                return;
            case R.id.lifeCycledetail:
                Intent intent14 = new Intent(Main2Activity.this, RecyclerListActivity.class);
                intent14.putExtra("lang", lang);
                intent14.putExtra("title", "LifeStyle");
                intent14.putExtra( "type","life");
                startActivity(intent14);
                return;
            case R.id.eventdetail:
                Intent intent15 = new Intent(Main2Activity.this, RecyclerListActivity.class);
                intent15.putExtra("lang", lang);
                intent15.putExtra("title", "Event");
                intent15.putExtra( "type","event");
                startActivity(intent15);
                return;
            default:
        }
        intent.putExtra("url", url);
        startActivity(intent);
    }

    public void setBannerClickEvent(View view, final BannerModel bannerModel) {
        //link:웹링크, shop : 매장페이지이동)

        view.setOnClickListener(v -> {
            Intent intent = null;
            if("link".equals(bannerModel.getLinkType() )) { //외부
                intent = new Intent (Intent.ACTION_VIEW, Uri.parse(bannerModel.getLink()));
            } else { //내부
                intent = new Intent(Main2Activity.this, DetailActivity.class);
                intent.putExtra("idx", bannerModel.getLink());

                String lang = (String) toolbarLang.getTag();
                if("kr".equals(lang))
                    intent.putExtra("toolbarTitle", bannerModel.getBannerName());
                else
                    intent.putExtra("toolbarTitle", bannerModel.getBannerName_EN());
            }
            startActivity(intent);
        });
    }


    public void setTopNewsClickEvent(View view, final TopNewsModel topNewsModel) {
        //link:웹링크, shop : 매장페이지이동)

        view.setOnClickListener(v -> {

            Intent intent = new Intent(Main2Activity.this, WebViewActivity.class);
            String url = "http://www.singkorea.com/newsdetail_app.php?idx="+ topNewsModel.getIdx() +"&app=Y" + "&lang="+ toolbarLang.getTag();
            intent.putExtra("url", url);

            startActivity(intent);
        });
    }

    private final static int SUCCESS_LOGIN = 0x0100;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SUCCESS_LOGIN  && resultCode == RESULT_OK) {
            toolbarLogin.setText("LogOut");
        }
    }
}
