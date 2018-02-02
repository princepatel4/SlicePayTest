package com.test.slicepay.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.slicepay.R;
import com.test.slicepay.model.ImageMainObject;
import com.test.slicepay.model.Photo;

import java.util.List;

/**
 * Created by pardypanda05 on 2/2/18.
 */

public class ImageListAdapter  extends RecyclerView.Adapter<ImageListAdapter.MyViewHolder>{

    private Context mContext;
    private List<Photo> imageList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
        }
    }


    public ImageListAdapter(Context mContext, List<Photo> imageList) {
        this.mContext = mContext;
        this.imageList = imageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Photo photo = imageList.get(position);
        holder.title.setText(photo.getTitle());

        // loading album cover using Glide library
        //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

        String imageURL = "https://farm"+photo.getFarm()+".staticflickr.com/"+photo.getServer()+"/"+photo.getId()+"_"+photo.getSecret()+"_b.jpg";
        Picasso.with(mContext).load(imageURL).into(holder.thumbnail);


    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public void updateList(List<Photo> arrayList) {
        imageList = arrayList;
        notifyDataSetChanged();
    }
}
