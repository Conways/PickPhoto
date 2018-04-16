package com.conways.pickphoto;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Conways on 2018/4/13.
 */

public class PhotoPickedAdapter extends RecyclerView.Adapter<PhotoPickedAdapter.PhotoHolder> {

    private List<String> photoPaths;
    private Context context;
    public static final int MAX = 9;

    private OnItemClick onItemClick;

    public PhotoPickedAdapter(List<String> photoPaths, Context context) {
        this.photoPaths = photoPaths;
        this.context = context;
    }

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoHolder(LayoutInflater.from(context).inflate(R.layout.item_pick_photo, parent, false));
    }

    @Override
    public void onBindViewHolder(PhotoHolder holder, final int position) {
        holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick != null) {
                    if (photoPaths.size() < MAX || position == photoPaths.size()) {
                        onItemClick.itemClick(position, true);
                    } else {
                        onItemClick.itemClick(position, false);
                    }
                }
            }
        });
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoPaths.remove(position);
            }
        });
        //小于9张图片时的最后一章
        if (photoPaths.size() < MAX || position == photoPaths.size()) {
            holder.del.setVisibility(View.GONE);
        } else {
            holder.del.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return photoPaths.size() < MAX ? photoPaths.size() + 1 : photoPaths.size();

    }

    public static class PhotoHolder extends RecyclerView.ViewHolder {
        public PhotoHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.item_pick_photo_photo);
            del = itemView.findViewById(R.id.item_pick_photo_del);
        }

        ImageView photo;
        ImageView del;
    }

    public interface OnItemClick {
        void itemClick(int position, boolean canPick);
    }

}
