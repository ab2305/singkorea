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
import singkorea.singkorea.com.singkorea.model.EstateListModel;
import singkorea.singkorea.com.singkorea.model.MarketItemModel;


public class RecyclerMarketGridAdapter extends RecyclerView.Adapter<RecyclerMarketGridAdapter.ViewHolder>{

    private List<MarketItemModel> itemList;
    private Context context;
    private OnClickItemListener onClickItemListener;

    boolean isKorea;

    public RecyclerMarketGridAdapter(List<MarketItemModel> itemList, Context context, boolean isKorea) {
        this.itemList = itemList;
        this.context = context;
        this.isKorea = isKorea;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market, parent, false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        MarketItemModel items = itemList.get(position);

        String fileUrl = items.getThumbPath();

        if (isKorea) {
            holder.categoryPrice.setText("I".equals(items.getState()) ? "[판매중]" : "[판매완료]");
        } else {
            holder.categoryPrice.setText("I".equals(items.getState()) ? "[Selling]" : "[Sold]");
        }

        holder.title.setText(items.getProductName());
        holder.desc.setText(items.getSellAddress());

        String price = items.getPrice();
        if(price.contains("$")) {
            holder.price.setText(price);
        } else {
            holder.price.setText("$"+price);
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
        final TextView categoryPrice;
        final TextView title;
        final TextView desc;
        final TextView price;

        ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.img);
            categoryPrice = itemView.findViewById(R.id.categoryName);
            title = itemView.findViewById(R.id.titleName);
            desc = itemView.findViewById(R.id.desc);
            price = itemView.findViewById(R.id.price);
        }
    }
}