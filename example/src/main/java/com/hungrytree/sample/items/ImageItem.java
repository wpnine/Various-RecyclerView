package com.hungrytree.sample.items;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hungrytree.varadapter.item.RecyclerItem;
import com.hungrytree.sample.R;

import java.util.List;

/**
 * Created by wp.nine on 2017/12/4.
 */

public class ImageItem extends RecyclerItem<int[],ImageItem.ImageViewHolder>{


    @Override
    public ImageViewHolder createChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder viewHolder, int position, int viewType) {
        Integer imageRes = getData()[position];
        viewHolder.imageView.setImageResource(imageRes);
        Log.i("haha","upatePosition " + position);
    }


    @Override
    public int getItemCount(int[] data) {
        if(data == null){
            return 0;
        }else{
            return data.length;
        }
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private ImageViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_pic);
        }
    }
}
