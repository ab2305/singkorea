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

import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import singkorea.singkorea.com.singkorea.R;
import singkorea.singkorea.com.singkorea.WebViewActivity;
import singkorea.singkorea.com.singkorea.adapter.EndlessRecyclerViewScrollListener;
import singkorea.singkorea.com.singkorea.api.PushSendUtil;
import singkorea.singkorea.com.singkorea.model.ClipListModel;
import singkorea.singkorea.com.singkorea.model.ClipModel;

public class ListMainClipFragment extends LazyFragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    private String shoptype;

    private List<ClipModel> clipList;


    @Override
    protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_preview, container, false);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_recycler_view);

        if (getArguments() != null) {
            shoptype = getArguments().getString("shoptype");

        }

        recyclerView = (RecyclerView) findViewById(R.id.recylerView);

        //recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        //recyclerView.addItemDecoration(new ListSpacingItemDecoration(this, getResources().getDimensionPixelSize(R.dimen.card_elevation)));
        progressBar = (ProgressBar) findViewById(R.id.fragment_mainTab_item_progressBar);

        clipList = new ArrayList<>();
        RecyclerClipGridAdapter adapter = new RecyclerClipGridAdapter(clipList, getApplicationContext());
        recyclerView.setAdapter(adapter);
        adapter.setOnClickItemListener((position) -> {

            ClipModel shopItem = clipList.get(position);
            String url = shopItem.getLink();
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("url", url);

            getActivity().startActivity(intent);

        });

        EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener((GridLayoutManager) recyclerView.getLayoutManager()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                //Log.e("test", "page : "+page);
                //loadData(page, view);
            }
        };

        recyclerView.addOnScrollListener(endlessRecyclerViewScrollListener);
        loadData(0, recyclerView);
    }

    public void loadData(int page, final RecyclerView view) {
        ++page;
        PushSendUtil.getSingApi().getDetailClip(shoptype, page, 10 )
                .enqueue(new retrofit2.Callback<ClipListModel>() {
                    @Override
                    public void onResponse(Call<ClipListModel> call, Response<ClipListModel> response) {
                        if(response.isSuccessful()) {
                            clipList.addAll(response.body().getLIST());
                            view.getAdapter().notifyDataSetChanged();
                            //scrollListener.resetState();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ClipListModel> call, Throwable t) {
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
