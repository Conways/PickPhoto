package com.conways.pickphoto;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Conways on 2018/4/16.
 */

public class PhotoAllAdapter extends RecyclerView.Adapter<PhotoAllAdapter.PhotoHolder> {


    private Context context;
    private List<String> photopaths;
    private DisplayImageOptions options;

    private List<ImageInfor> imagesPicked;

    public PhotoAllAdapter(Context context, List<String> photopaths) {
        this.context = context;
        this.photopaths = photopaths;
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher_background)
                .showImageForEmptyUri(R.drawable.ic_launcher_background)
                .showImageOnFail(R.drawable.ic_launcher_background)
                .cacheInMemory(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        imagesPicked = new ArrayList<>();
    }

    int i;

    @Override
    public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("zzz", "onCreateViewHolder: " + ++i);
        return new PhotoHolder(LayoutInflater.from(context).inflate(R.layout.item_photo,
                parent, false));
    }

    @Override
    public void onBindViewHolder(final PhotoHolder holder, final int position) {
        ImageLoader.getInstance().displayImage("file://" + photopaths.get(position), holder.photo, options);
        for (int i = 0; i < imagesPicked.size(); i++) {
            if (position == imagesPicked.get(i).getPostion()) {
                holder.select.setChecked(true);
                break;
            }
        }
        holder.select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ImageInfor imageInfor = new ImageInfor(position, photopaths.get(position));
                    imagesPicked.add(imageInfor);
                    PhotoAllAdapter.this.notifyItemChanged(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return photopaths.size();
    }

    public static class PhotoHolder extends RecyclerView.ViewHolder {
        public PhotoHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.item_photo_photo);
            select = itemView.findViewById(R.id.item_photo_select);
        }

        ImageView photo;
        CheckBox select;
    }

    public interface OnItemClick {
        void itemClick(int position);
    }

    class ImageInfor {

        public ImageInfor(int postion, String path) {
            this.postion = postion;
            this.path = path;
        }

        int postion;
        String path;

        public int getPostion() {
            return postion;
        }

        public void setPostion(int postion) {
            this.postion = postion;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }


}
