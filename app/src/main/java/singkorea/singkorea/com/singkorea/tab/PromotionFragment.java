package singkorea.singkorea.com.singkorea.tab;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.shizhefei.fragment.LazyFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import singkorea.singkorea.com.singkorea.DetailActivity;
import singkorea.singkorea.com.singkorea.R;
import singkorea.singkorea.com.singkorea.api.PushSendUtil;
import singkorea.singkorea.com.singkorea.model.PromotionModel;
import singkorea.singkorea.com.singkorea.util.TinyDB;

public class PromotionFragment extends LazyFragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private String category;
    private String lang;

    @Override
    protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_preview, container, false);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_recycler_view);

        category = "";
        if (getArguments() != null) {
            category = getArguments().getString("category");
        }

        TinyDB tinyDB = new TinyDB(getApplicationContext());
        lang = tinyDB.getString("lang");

        recyclerView = (RecyclerView) findViewById(R.id.recylerView);

        //recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //recyclerView.addItemDecoration(new ListSpacingItemDecoration(this, getResources().getDimensionPixelSize(R.dimen.card_elevation)));
        progressBar = (ProgressBar) findViewById(R.id.fragment_mainTab_item_progressBar);

        PushSendUtil.getSingApi().getPromotion(category)
                .enqueue(new retrofit2.Callback<List<PromotionModel>>() {
                    @Override
                    public void onResponse(Call<List<PromotionModel>> call, Response<List<PromotionModel>> response) {
                        if(response.isSuccessful()) {
                            final List<PromotionModel> itemList = response.body();
                            RecyclerBannerAdapter adapter = new RecyclerBannerAdapter(itemList, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                            adapter.setOnClickItemListener((position) -> {
                                PromotionModel promotionModel = itemList.get(position);

                                Intent intent = new Intent(getActivity(), DetailActivity.class);
                                intent.putExtra("category", category);
                                intent.putExtra("idx", promotionModel.getLink());

                                if("kr".equals(lang))
                                    intent.putExtra("toolbarTitle", promotionModel.getProName());
                                else
                                    intent.putExtra("toolbarTitle", promotionModel.getProName_EN());

                                startActivity(intent);
                            });

                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PromotionModel>> call, Throwable t) {
                        Log.e("tag", t.toString());
                    }
                });

//        handler.sendEmptyMessageDelayed(1, 3000);
    }

    @Override
    protected void onDestroyViewLazy() {
        super.onDestroyViewLazy();
        handler.removeMessages(1);
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            recyclerView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        ;
    };
}
