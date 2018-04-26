package singkorea.singkorea.com.singkorea.tab;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import singkorea.singkorea.com.singkorea.DetailActivity;
import singkorea.singkorea.com.singkorea.R;
import singkorea.singkorea.com.singkorea.adapter.EndlessRecyclerViewScrollListener;
import singkorea.singkorea.com.singkorea.api.PushSendUtil;
import singkorea.singkorea.com.singkorea.model.ShopListModel;
import singkorea.singkorea.com.singkorea.model.ShopMoreModel;
import singkorea.singkorea.com.singkorea.util.TinyDB;

public class ListFragment extends LazyFragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private String category;
    private String areaCode;
    private String lang;
    private String search;

    private List<ShopMoreModel> shopeList;

    @Override
    protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_preview, container, false);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_recycler_view);

        if (getArguments() != null) {
            category = getArguments().getString("category");
            areaCode = getArguments().getString("areaCode");
            search = getArguments().getString("search");
        }
        TinyDB tinyDB = new TinyDB(getContext());
        lang = tinyDB.getString("lang");
        boolean isKorea = "kr".equals(lang) ? true : false;

        recyclerView = (RecyclerView) findViewById(R.id.recylerView);

        //recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        //recyclerView.addItemDecoration(new ListSpacingItemDecoration(this, getResources().getDimensionPixelSize(R.dimen.card_elevation)));
        progressBar = (ProgressBar) findViewById(R.id.fragment_mainTab_item_progressBar);

        shopeList = new ArrayList<>();
        RecyclerGridAdapter adapter = new RecyclerGridAdapter(shopeList, getApplicationContext(), isKorea);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickItemListener((position) -> {

            ShopMoreModel shopItem = shopeList.get(position);

            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("idx", shopItem.getIdx());

            if("kr".equals(lang))
                intent.putExtra("toolbarTitle", shopItem.getShopName_EN());
            else {
                intent.putExtra("toolbarTitle", shopItem.getShopName());
            }
            getActivity().startActivity(intent);

        });

        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener((GridLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //Log.e("test", "page : "+page);
                loadData(page, view);
            }
        };

        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
        loadData(0, recyclerView);
    }

    public void loadData(int page, final RecyclerView view) {
        ++page;
        PushSendUtil.getSingApi().getShops(category, page, 10 , areaCode, search)
                .enqueue(new retrofit2.Callback<ShopListModel>() {
                    @Override
                    public void onResponse(Call<ShopListModel> call, Response<ShopListModel> response) {
                        if(response.isSuccessful()) {
                            shopeList.addAll(response.body().getLIST());
                            view.getAdapter().notifyDataSetChanged();
                            //scrollListener.resetState();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ShopListModel> call, Throwable t) {
                        Log.e("tag", t.toString());
                    }
                });

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
