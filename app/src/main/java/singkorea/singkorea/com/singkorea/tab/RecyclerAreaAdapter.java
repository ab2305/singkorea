package singkorea.singkorea.com.singkorea.tab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import singkorea.singkorea.com.singkorea.R;
import singkorea.singkorea.com.singkorea.model.AreaCodeModel;


public class RecyclerAreaAdapter extends RecyclerView.Adapter<RecyclerAreaAdapter.ViewHolder>{

    private List<AreaCodeModel> itemList;
    private Context context;
    private OnClickItemListener onClickItemListener;

    public RecyclerAreaAdapter(List<AreaCodeModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_check_list, parent, false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        AreaCodeModel item = itemList.get(position);

        holder.itemName.setText(item.getAreaName());
        if(item.isChecked()) {
            holder.image.setVisibility(View.VISIBLE);
        } else {
            holder.image.setVisibility(View.GONE);
        }

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

        final TextView itemName;
        final ImageView image;

        ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.checkImg);
            itemName = itemView.findViewById(R.id.item_name);
        }
    }
}