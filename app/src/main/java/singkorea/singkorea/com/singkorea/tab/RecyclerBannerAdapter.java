package singkorea.singkorea.com.singkorea.tab;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import singkorea.singkorea.com.singkorea.R;
import singkorea.singkorea.com.singkorea.model.BannerModel;
import singkorea.singkorea.com.singkorea.model.PromotionModel;


public class RecyclerBannerAdapter extends RecyclerView.Adapter<RecyclerBannerAdapter.ViewHolder>{

    private List<PromotionModel> itemList;
    private Context context;
    private OnClickItemListener onClickItemListener;

    public RecyclerBannerAdapter(List<PromotionModel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        PromotionModel items = itemList.get(position);

        String fileUrl = items.getFilePath();

        if(fileUrl.endsWith(".gif")) {
            Glide.with(context).asGif()
                    .load("http://www.singkorea.com"+fileUrl)
                    .into(holder.image);
        } else {
            Glide.with(context)
                    .load("http://www.singkorea.com"+fileUrl)
                    .into(holder.image);
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

        final ImageView image;
        //TextView name;

        ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.bannerImg);
            //name = (TextView) itemView.findViewById(R.id.imageName);
        }
    }
}