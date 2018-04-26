package singkorea.singkorea.com.singkorea.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import singkorea.singkorea.com.singkorea.DetailActivity;
import singkorea.singkorea.com.singkorea.R;
import singkorea.singkorea.com.singkorea.model.ShopModel;

public class ViewPagerAdapter extends PagerAdapter {

    private static boolean isEnglish = false;

    private  Context context;
    private List<ShopModel> data;

    public ViewPagerAdapter(Context context, List<ShopModel> data){
        this.context = context;
        this.data = data;
    }

    @Override public int getCount() {
        if(data.size() <= 0)
            return 0;

        return data.size()/4 + (data.size() % 4 == 0 ? 0 : 1);
    }

    public static void setLang(boolean isEnglish) {
        ViewPagerAdapter.isEnglish = isEnglish;
    }

    @Override public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override public void destroyItem(@NonNull ViewGroup view, int position, @NonNull Object object) {
        view.removeView((View) object);
    }

    @NonNull
    @Override public Object instantiateItem(@NonNull ViewGroup container, int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_viewpager, null);


        int size = ( (position+1)*4 > data.size() ?  data.size() : (position+1)*4 );
        //data 조작
        for(int i=(position*4); i <  size; i++ ) {

            ShopModel shopModel = data.get(i);

            int resourceImage =  context.getResources().getIdentifier("iv0"+ (i%4+1), "id", context.getPackageName());
            int resourceText =  context.getResources().getIdentifier("tv0"+(i%4+1), "id", context.getPackageName());

            String title;
            String styledText = "This is <font color='#FBBB72'>simple</font>.";
            if(isEnglish) {
                title = "<font color='#FBBB72'>["+shopModel.getCategoryName_EN()+"]</font> "+shopModel.getShopName_EN();
                //title = "["+shopModel.getCategoryName_EN()+"]"+shopModel.getShopName_EN();
            } else {
                title = "<font color='#FBBB72'>["+shopModel.getCategoryName()+"]</font> "+shopModel.getShopName();
                //title = "["+shopModel.getCategoryName()+"]"+shopModel.getShopName();
            }
//            Spanned spanned = Html.fromHtml(styledText); android.widget.TextView.BufferType.SPANNABLE);
            TextView textView = view.findViewById(resourceText);
            textView.setText(Html.fromHtml(title), TextView.BufferType.SPANNABLE);

            ImageView imageView = view.findViewById(resourceImage);
            Glide.with(context)
                    .load("http://www.singkorea.com"+shopModel.getThumbPath())
                    .into(imageView);

            setOnClickEvent(textView, shopModel);
            setOnClickEvent(imageView, shopModel);


        }

        container.addView(view);
        return view;
    }

    private void setOnClickEvent(View view, final ShopModel shopModel) {
        view.setOnClickListener(v -> {
//            String suffixLang = "kr";
//            if(isEnglish) suffixLang = "en";
//            String url = "http://www.singkorea.com/shopdetail_app.html?shopidx="+ shopModel.getIdx() +"&lang="+suffixLang;
//            Intent intent = new Intent(context, WebViewActivity.class);
//            intent.putExtra("url", url);

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("idx", shopModel.getIdx());

            if(isEnglish)
                intent.putExtra("toolbarTitle", shopModel.getShopName_EN());
            else {
                intent.putExtra("toolbarTitle", shopModel.getShopName());
            }
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }
}