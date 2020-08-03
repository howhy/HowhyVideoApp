package com.howhy.video.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.howhy.video.R;
import com.howhy.video.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.MyViewHolder> {
    private static final String TAG = "VideoRecyclerViewAdapte";
    private Context context;
    private List<String> itemVideos;
    public void setItemVideos(List<String> itemvideos) {
        this.itemVideos = itemvideos;
        notifyDataSetChanged();
    }



    public VideoRecyclerViewAdapter(Context context) {
        this.context = context;
        this.itemVideos=new ArrayList<String>();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        Button playBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            playBtn=itemView.findViewById(R.id.playBtn);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.videodetail_list_item,parent,false));
    }

    @Override
    public int getItemCount() {
        return itemVideos.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final String videoItem=itemVideos.get(position);
        final String[] videos=videoItem.split("\\$");
        holder.playBtn.setText(videos[0]);
        holder.playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, WebViewActivity.class);
                intent.putExtra("src",videoItem);
                context.startActivity(intent);
            }
        });
    }


}
