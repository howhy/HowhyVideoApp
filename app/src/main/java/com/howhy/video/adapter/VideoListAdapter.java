package com.howhy.video.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.howhy.video.R;
import com.howhy.video.model.VideoDetail;

import java.util.List;

public class VideoListAdapter extends BaseAdapter {
    private static final String TAG = "VideoListAdapter";
    private List<VideoDetail> videoList;
    private Context videoContext;
    public VideoListAdapter(Context context) {
        this.videoContext = context;
    }

    public void setVideoList(List<VideoDetail> videoList) {
        this.videoList = videoList;
        notifyDataSetChanged();
    }

    public final  class VideoViewHolder{
        TextView vdoName;
        TextView vdoType;
        TextView vdoTime;
    }
    @Override
    public int getCount() {
        return videoList!=null?videoList.size():0;
    }

    @Override
    public Object getItem(int i) {
        return videoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return videoList.get(i).getVod_id();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VideoViewHolder holder=null;
        if(view==null){
            holder=new VideoViewHolder();
            view= LayoutInflater.from(videoContext).inflate(R.layout.video_list_item,null);
            holder.vdoName=view.findViewById(R.id.vdoName);
            holder.vdoType=view.findViewById(R.id.vdoType);
            holder.vdoTime=view.findViewById(R.id.vdoTime);
            view.setTag(holder);
        }else{
            holder=(VideoViewHolder)view.getTag();
        }
        VideoDetail video=videoList.get(i);
        holder.vdoName.setText(video.getVod_name());
        holder.vdoType.setText(video.getType_name());
        holder.vdoTime.setText(video.getVod_time().split(" ")[0]);
        return view;
    }
}
