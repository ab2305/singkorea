package singkorea.singkorea.com.singkorea.tab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import singkorea.singkorea.com.singkorea.R;
import singkorea.singkorea.com.singkorea.model.SubCategoryModel;
import singkorea.singkorea.com.singkorea.model.SubCategoryModel;


public class RecyclerSubCategoryAdapter extends RecyclerView.Adapter<RecyclerSubCategoryAdapter.ViewHolder>{

    private List<SubCategoryModel> itemList;
    private Context context;
    private OnClickItemListener onClickItemListener;

    public RecyclerSubCategoryAdapter(List<SubCategoryModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subcategory, parent, false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        SubCategoryModel item = itemList.get(position);

        holder.subCategoryName.setText(item.getSubCategoryName());

        holder.itemView.setOnClickListener((v) -> {
            if (onClickItemListener != null) {
                onClickItemListener.onClick(holder.getAdapterPosition());
            }
        });
    }


    @Override public int getItemCount() {
        return itemList.size();
    }

    public interface OnClickItemListener {
        void onClick(int position);
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        final ImageView image;
        final TextView subCategoryName;

        ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.bannerImg);
            subCategoryName = itemView.findViewById(R.id.subCategoryName);
        }
    }
}