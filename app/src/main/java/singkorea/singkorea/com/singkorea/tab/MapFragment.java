package singkorea.singkorea.com.singkorea.tab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.shizhefei.fragment.LazyFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import singkorea.singkorea.com.singkorea.PopupAreaSelectActivity;
import singkorea.singkorea.com.singkorea.R;
import singkorea.singkorea.com.singkorea.api.PushSendUtil;
import singkorea.singkorea.com.singkorea.model.AreaCodeModel;
import singkorea.singkorea.com.singkorea.util.ImageviewClickArea;

public class MapFragment extends LazyFragment implements View.OnClickListener, View.OnTouchListener {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    List<AreaCodeModel> areaCodeList;
    private String category;

    @Override
    protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_preview, container, false);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_map);


        if (getArguments() != null) {
            category = getArguments().getString("category");
        }
        
        ImageviewClickArea imageviewClickArea1 = (ImageviewClickArea) findViewById(R.id.img_map1);
        ImageviewClickArea imageviewClickArea2 = (ImageviewClickArea) findViewById(R.id.img_map2);
        ImageviewClickArea imageviewClickArea3 = (ImageviewClickArea) findViewById(R.id.img_map3);
        ImageviewClickArea imageviewClickArea4 = (ImageviewClickArea) findViewById(R.id.img_map4);
        ImageviewClickArea imageviewClickArea5 = (ImageviewClickArea) findViewById(R.id.img_map5);
        ImageviewClickArea imageviewClickArea6 = (ImageviewClickArea) findViewById(R.id.img_map6);
        ImageviewClickArea imageviewClickArea7 = (ImageviewClickArea) findViewById(R.id.img_map7);
        ImageviewClickArea imageviewClickArea8 = (ImageviewClickArea) findViewById(R.id.img_map8);
        ImageviewClickArea imageviewClickArea9 = (ImageviewClickArea) findViewById(R.id.img_map9);
        ImageviewClickArea imageviewClickArea10 = (ImageviewClickArea) findViewById(R.id.img_map10);
        ImageviewClickArea imageviewClickArea11 = (ImageviewClickArea) findViewById(R.id.img_map11);
        ImageviewClickArea imageviewClickArea12 = (ImageviewClickArea) findViewById(R.id.img_map12);
        ImageviewClickArea imageviewClickArea13 = (ImageviewClickArea) findViewById(R.id.img_map13);
        ImageviewClickArea imageviewClickArea14 = (ImageviewClickArea) findViewById(R.id.img_map14);
        ImageviewClickArea imageviewClickArea15 = (ImageviewClickArea) findViewById(R.id.img_map15);
        ImageviewClickArea imageviewClickArea16 = (ImageviewClickArea) findViewById(R.id.img_map16);
        ImageviewClickArea imageviewClickArea17 = (ImageviewClickArea) findViewById(R.id.img_map17);
        ImageviewClickArea imageviewClickArea18 = (ImageviewClickArea) findViewById(R.id.img_map18);
        ImageviewClickArea imageviewClickArea19 = (ImageviewClickArea) findViewById(R.id.img_map19);
        ImageviewClickArea imageviewClickArea20 = (ImageviewClickArea) findViewById(R.id.img_map20);
        ImageviewClickArea imageviewClickArea21 = (ImageviewClickArea) findViewById(R.id.img_map21);
        ImageviewClickArea imageviewClickArea22 = (ImageviewClickArea) findViewById(R.id.img_map22);
        ImageviewClickArea imageviewClickArea23 = (ImageviewClickArea) findViewById(R.id.img_map23);
        ImageviewClickArea imageviewClickArea24 = (ImageviewClickArea) findViewById(R.id.img_map24);
        ImageviewClickArea imageviewClickArea25 = (ImageviewClickArea) findViewById(R.id.img_map25);
        ImageviewClickArea imageviewClickArea26 = (ImageviewClickArea) findViewById(R.id.img_map26);
        ImageviewClickArea imageviewClickArea27 = (ImageviewClickArea) findViewById(R.id.img_map27);
        ImageviewClickArea imageviewClickArea28 = (ImageviewClickArea) findViewById(R.id.img_map28);

        imageviewClickArea1.setMyOnClickListener(this);
        imageviewClickArea2.setMyOnClickListener(this);
        imageviewClickArea3.setMyOnClickListener(this);
        imageviewClickArea4.setMyOnClickListener(this);
        imageviewClickArea5.setMyOnClickListener(this);
        imageviewClickArea6.setMyOnClickListener(this);
        imageviewClickArea7.setMyOnClickListener(this);
        imageviewClickArea8.setMyOnClickListener(this);
        imageviewClickArea9.setMyOnClickListener(this);
        imageviewClickArea10.setMyOnClickListener(this);
        imageviewClickArea11.setMyOnClickListener(this);
        imageviewClickArea12.setMyOnClickListener(this);
        imageviewClickArea13.setMyOnClickListener(this);
        imageviewClickArea14.setMyOnClickListener(this);
        imageviewClickArea15.setMyOnClickListener(this);
        imageviewClickArea16.setMyOnClickListener(this);
        imageviewClickArea17.setMyOnClickListener(this);
        imageviewClickArea18.setMyOnClickListener(this);
        imageviewClickArea19.setMyOnClickListener(this);
        imageviewClickArea20.setMyOnClickListener(this);
        imageviewClickArea21.setMyOnClickListener(this);
        imageviewClickArea22.setMyOnClickListener(this);
        imageviewClickArea23.setMyOnClickListener(this);
        imageviewClickArea24.setMyOnClickListener(this);
        imageviewClickArea25.setMyOnClickListener(this);
        imageviewClickArea26.setMyOnClickListener(this);
        imageviewClickArea27.setMyOnClickListener(this);
        imageviewClickArea28.setMyOnClickListener(this);

        PushSendUtil.getSingApi().getAreaCode()
                .enqueue(new Callback<List<AreaCodeModel>>() {
                    @Override
                    public void onResponse(Call<List<AreaCodeModel>> call, Response<List<AreaCodeModel>> response) {
                        if (response.isSuccessful()) {
                            areaCodeList = response.body();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<AreaCodeModel>> call, Throwable t) {

                    }
                });

    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(getContext(), PopupAreaSelectActivity.class);
        intent.putExtra("category", category);
        if(areaCodeList == null) {
            return;
        }

        AreaCodeModel areaCode;
        String s = "1";
        switch (view.getId()) {
            case R.id.img_map1:
                areaCode = areaCodeList.get(0);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area1);
                s = "1";
                break;
            case R.id.img_map2:
                areaCode = areaCodeList.get(1);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area2);

                s = "2";
                break;
            case R.id.img_map3:
                areaCode = areaCodeList.get(2);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area3);
                s = "3";
                break;
            case R.id.img_map4:
                areaCode = areaCodeList.get(3);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area4);
                s = "4";
                break;
            case R.id.img_map5:
                areaCode = areaCodeList.get(4);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area5);
                s = "5";
                break;
            case R.id.img_map6:
                areaCode = areaCodeList.get(5);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area6);
                s = "6";
                break;
            case R.id.img_map7:
                areaCode = areaCodeList.get(6);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area7);
                s = "7";
                break;
            case R.id.img_map8:
                areaCode = areaCodeList.get(7);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area8);
                s = "8";
                break;
            case R.id.img_map9:
                areaCode = areaCodeList.get(8);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area9);
                s = "9";
                break;
            case R.id.img_map10:
                areaCode = areaCodeList.get(9);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area10);
                s = "10";
                break;
            case R.id.img_map11:
                areaCode = areaCodeList.get(10);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area11);
                s = "11";
                break;
            case R.id.img_map12:
                areaCode = areaCodeList.get(5);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area1);
                s = "12";
                break;
            case R.id.img_map13:
                areaCode = areaCodeList.get(12);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area13);
                s = "13";
                break;
            case R.id.img_map14:
                areaCode = areaCodeList.get(13);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area14);
                s = "14";
                break;
            case R.id.img_map15:
                areaCode = areaCodeList.get(14);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area15);
                s = "15";
                break;
            case R.id.img_map16:
                areaCode = areaCodeList.get(15);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area16);
                s = "16";
                break;
            case R.id.img_map17:
                areaCode = areaCodeList.get(16);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area17);
                s = "17";
                break;
            case R.id.img_map18:
                areaCode = areaCodeList.get(17);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area18);
                s = "18";
                break;
            case R.id.img_map19:
                areaCode = areaCodeList.get(18);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area19);
                s = "19";
                break;
            case R.id.img_map20:
                areaCode = areaCodeList.get(19);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area20);
                s = "20";
                break;
            case R.id.img_map21:
                areaCode = areaCodeList.get(20);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area21);
                s = "21";
                break;
            case R.id.img_map22:
                areaCode = areaCodeList.get(21);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area22);
                s = "22";
                break;
            case R.id.img_map23:
                areaCode = areaCodeList.get(22);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area23);
                s = "23";
                break;
            case R.id.img_map24:
                areaCode = areaCodeList.get(23);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area24);
                s = "24";
                break;
            case R.id.img_map25:
                areaCode = areaCodeList.get(24);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area25);
                s = "25";
                break;
            case R.id.img_map26:
                areaCode = areaCodeList.get(25);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area1);
                s = "26";
                break;
            case R.id.img_map27:
                areaCode = areaCodeList.get(26);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area26);
                s = "27";
                break;
            case R.id.img_map28:
                areaCode = areaCodeList.get(27);
                intent.putExtra("areaName", areaCode.getAreaName());
                intent.putExtra("areaCode", areaCode.getAreaCode());
                intent.putExtra("shopCnt", areaCode.getShopcnt());
                intent.putExtra("imgResId", R.drawable.area27);
                s = "28";
                break;
        }
        startActivity(intent);
        Log.e("test", s);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                Log.e("test", "motionEvent.getX() : "+motionEvent.getX());
//                Log.e("test", "motionEvent.getY() : "+motionEvent.getY());
//                Log.e("test", "motionEvent.getRawX() : "+motionEvent.getRawX());
//                Log.e("test", "motionEvent.getRawY() : "+motionEvent.getRawY());

//                int color = Bitmap.getPixel(motionEvent.getX(),motionEvent.getY()); // x and y are the location of the touch event in Bitmap space
//                Log.e("test", "color : " + color);
//                int alpha = Color.getAlpha(color);
//                boolean isTransparent = (alpha==0);

//                if (x >= xOfYourBitmap && x < (xOfYourBitmap + yourBitmap.getWidth())
//                        && y >= yOfYourBitmap && y < (yOfYourBitmap + yourBitmap.getHeight())) {
//                    //tada, if this is true, you've started your click inside your bitmap
//                }
                onClick(view);
                break;
        }

        return false;
    }
}
