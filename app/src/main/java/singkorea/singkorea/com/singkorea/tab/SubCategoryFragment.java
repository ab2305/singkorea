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
import android.widget.Toast;

import com.shizhefei.fragment.LazyFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import singkorea.singkorea.com.singkorea.R;
import singkorea.singkorea.com.singkorea.RecyclerListActivity;
import singkorea.singkorea.com.singkorea.api.PushSendUtil;
import singkorea.singkorea.com.singkorea.model.SubCategoryModel;

public class SubCategoryFragment extends LazyFragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;

    @Override
    protected View getPreviewLayout(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.layout_preview, container, false);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_subcatgory_view);

        String category = "";
        if (getArguments() != null) {
            category = getArguments().getString("category");
        }

        recyclerView = (RecyclerView) findViewById(R.id.recylerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        //recyclerView.addItemDecoration(new ListSpacingItemDecoration(this, getResources().getDimensionPixelSize(R.dimen.card_elevation)));
        progressBar = (ProgressBar) findViewById(R.id.fragment_mainTab_item_progressBar);

        PushSendUtil.getSingApi().getSubCategory(category)
                .enqueue(new retrofit2.Callback<List<SubCategoryModel>>() {
                    @Override
                    public void onResponse(Call<List<SubCategoryModel>> call, Response<List<SubCategoryModel>> response) {
                        if(response.isSuccessful()) {
                            final List<SubCategoryModel> subCategoryModels = response.body();
                            RecyclerSubCategoryAdapter adapter = new RecyclerSubCategoryAdapter(subCategoryModels, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                            adapter.setOnClickItemListener((position) -> {
                                SubCategoryModel subCategoryModel = subCategoryModels.get(position);
                                Intent intent = new Intent(getActivity(), RecyclerListActivity.class);
                                intent.putExtra("category", subCategoryModel.getCategoryCode());
                                intent.putExtra("title", subCategoryModel.getSubCategoryName());
                                startActivity(intent);
                            });

                            progressBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<SubCategoryModel>> call, Throwable t) {
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
