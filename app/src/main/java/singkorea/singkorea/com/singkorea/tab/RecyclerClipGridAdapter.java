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
import singkorea.singkorea.com.singkorea.model.ClipModel;


public class RecyclerClipGridAdapter extends RecyclerView.Adapter<RecyclerClipGridAdapter.ViewHolder>{

    private List<ClipModel> itemList;
    private Context context;
    private OnClickItemListener onClickItemListener;


    public RecyclerClipGridAdapter(List<ClipModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;

    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid, parent, false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        ClipModel items = itemList.get(position);

        String fileUrl = items.getThumbPath();



            holder.ClipName.setText("["+items.getClipName()+"]");
        holder.playerimg.setVisibility(View.VISIBLE);

        Glide.with(context)
                .load(fileUrl)
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
        final TextView ClipName;
        final ImageView playerimg;

        ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.img);
            ClipName = itemView.findViewById(R.id.titleName);
            playerimg = itemView.findViewById(R.id.playerimg);

        }
    }
}