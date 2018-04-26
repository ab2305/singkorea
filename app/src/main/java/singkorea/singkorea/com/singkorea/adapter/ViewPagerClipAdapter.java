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

import singkorea.singkorea.com.singkorea.R;
import singkorea.singkorea.com.singkorea.WebViewActivity;
import singkorea.singkorea.com.singkorea.model.ClipModel;


public class ViewPagerClipAdapter extends PagerAdapter {

    private Context context;
    private List<ClipModel> data;

    public ViewPagerClipAdapter(Context context, List<ClipModel> data){
        this.context = context;
        this.data = data;
    }

    @Override public int getCount() {
        if(data.size() <= 0)
            return 0;

        return data.size()/2 + (data.size() % 2 == 0 ? 0 : 1);
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


        int size = ( (position+1)*2 > data.size() ?  data.size() : (position+1)*2 );
        //data 조작
        for(int i=(position*2); i <  size; i++ ) {

            ClipModel clipModel = data.get(i);

            int resourceImage =  context.getResources().getIdentifier("iv0"+ (i%2+1), "id", context.getPackageName());
            int resourceText =  context.getResources().getIdentifier("tv0"+(i%2+1), "id", context.getPackageName());
            int playerbtn = context.getResources().getIdentifier("playerimg0" + (i%2+1),"id",context.getPackageName());

            String title;
            String styledText = "This is <font color='#FBBB72'>simple</font>.";

            title = clipModel.getClipName();
                //title = "["+shopModel.getCategoryName()+"]"+shopModel.getShopName();

//            Spanned spanned = Html.fromHtml(styledText); android.widget.TextView.BufferType.SPANNABLE);
            TextView textView = view.findViewById(resourceText);
            textView.setText(Html.fromHtml(title), TextView.BufferType.SPANNABLE);

            ImageView imageView = view.findViewById(resourceImage);
            ImageView playerView = view.findViewById(playerbtn);
            playerView.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(clipModel.getThumbPath())
                    .into(imageView);

            setOnClickEvent(textView, clipModel);
            setOnClickEvent(imageView, clipModel);


        }

        container.addView(view);
        return view;
    }

    private void setOnClickEvent(View view, final ClipModel clipModel) {
        view.setOnClickListener(v -> {
//            String suffixLang = "kr";
//            if(isEnglish) suffixLang = "en";
            String url = clipModel.getLink();
            Intent intent = new Intent(context, WebViewActivity.class);
            intent.putExtra("url", url);


            context.startActivity(intent);

        });
    }

}
