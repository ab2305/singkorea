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
import singkorea.singkorea.com.singkorea.model.ShopMoreModel;


public class RecyclerEstateGridAdapter extends RecyclerView.Adapter<RecyclerEstateGridAdapter.ViewHolder>{

    private List<EstateListModel.EstateModel> itemList;
    private Context context;
    private OnClickItemListener onClickItemListener;

    public RecyclerEstateGridAdapter(List<EstateListModel.EstateModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_estate, parent, false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        EstateListModel.EstateModel items = itemList.get(position);

        String fileUrl = items.getThumbPath();

        holder.title.setText(items.getTitle());
        holder.address.setText("["+items.getAddress()+"]");

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
        final TextView title;
        final TextView address;
        final TextView price;

        ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.img);
            title = itemView.findViewById(R.id.title);
            address = itemView.findViewById(R.id.address);
            price = itemView.findViewById(R.id.price);
        }
    }
}