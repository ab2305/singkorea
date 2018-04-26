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
import singkorea.singkorea.com.singkorea.model.ShopMoreModel;


public class RecyclerGridAdapter extends RecyclerView.Adapter<RecyclerGridAdapter.ViewHolder>{

    private List<ShopMoreModel> itemList;
    private Context context;
    private OnClickItemListener onClickItemListener;
    private boolean isKorea;

    public RecyclerGridAdapter(List<ShopMoreModel> itemList, Context context, boolean isKorea) {
        this.itemList = itemList;
        this.context = context;
        this.isKorea = isKorea;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        ShopMoreModel items = itemList.get(position);

        String fileUrl = items.getThumbPath();


        if(isKorea) {
            holder.category.setText("["+items.getCategoryName()+"]");
            holder.title.setText(items.getShopName());
            holder.desc.setText(items.getComment());
        } else {
            holder.category.setText("["+items.getCategoryName_EN()+"]");
            holder.title.setText(items.getShopName_EN());
            holder.desc.setText(items.getComment_EN());
        }

        Glide.with(context)
                .load("http://www.singkorea.com"+fileUrl)
                .into(holder.image);

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
        final TextView category;
        final TextView title;
        final TextView desc;

        ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.img);
            category = itemView.findViewById(R.id.categoryName);
            title = itemView.findViewById(R.id.titleName);
            desc = itemView.findViewById(R.id.desc);
        }
    }
}