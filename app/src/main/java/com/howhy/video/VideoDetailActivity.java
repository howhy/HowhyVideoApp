package com.howhy.video;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.howhy.video.adapter.VideoRecyclerViewAdapter;
import com.howhy.video.model.VideoDetail;

import java.util.ArrayList;
import java.util.List;

public class VideoDetailActivity extends AppCompatActivity {
    private static final String TAG = "VideoDetailActivity";
    private ImageView detailImg;
    private TextView detailTitle;
    private TextView detailDirector;
    private TextView detailActor;
    private TextView detailClass;
    private TextView detailArea;
    private TextView detailLang;
    private TextView detailYear;
    private TextView detailContent;
    private RecyclerView recyclerview;
    private VideoRecyclerViewAdapter videoRecyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        setTitle("影视详情");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
        initData();
    }

    private void initData() {
        VideoDetail videoDetail=(VideoDetail)getIntent().getSerializableExtra("detailinfo");
        if(videoDetail!=null) {
            glideLoadImage(videoDetail.getVod_pic());
            detailTitle.setText(videoDetail.getVod_name());
            setTitle(videoDetail.getVod_name());
            detailDirector.setText("导演："+videoDetail.getVod_director());
            detailActor.setText("主演："+videoDetail.getVod_actor());
            detailClass.setText("类型："+videoDetail.getType_name());
            detailArea.setText("地区："+videoDetail.getVod_area());
            detailLang.setText("语言："+videoDetail.getVod_lang());
            detailYear.setText("上映："+videoDetail.getVod_year());
            detailContent.setText("剧情介绍：\n\t"+videoDetail.getVod_content());
            String playUrls=videoDetail.getVod_play_url();
            if(playUrls.contains("$$$")){
                playUrls=playUrls.substring(0,playUrls.indexOf("$$$"));
            }
            String[] playNums=playUrls.split("\\#");
            List<String> itemVideo=new ArrayList<>();
            for(int i=0;i<playNums.length;i++){
                itemVideo.add(playNums[i]);
            }
            GridLayoutManager gridLayoutManager=new GridLayoutManager(this,5);
            recyclerview.setLayoutManager(gridLayoutManager);
            videoRecyclerViewAdapter=new VideoRecyclerViewAdapter(this);
            recyclerview.setAdapter(videoRecyclerViewAdapter);
            videoRecyclerViewAdapter.setItemVideos(itemVideo);
        }
    }

    private void initView() {
        detailImg=findViewById(R.id.detailImg);
        detailTitle=findViewById(R.id.detailTitle);
        detailDirector=findViewById(R.id.detailDirector);
        detailActor=findViewById(R.id.detailActor);
        detailClass=findViewById(R.id.detailClass);
        detailArea=findViewById(R.id.detailArea);
        detailLang=findViewById(R.id.detailLang);
        detailYear=findViewById(R.id.detailYear);
        detailContent=findViewById(R.id.detailContent);
        recyclerview=findViewById(R.id.recyclerview);
    }
    private void glideLoadImage (String img) {
//      通过 RequestOptions 对象来设置Glide的配置
        Log.i(TAG, "glideLoadImage: "+img);
        RequestOptions options = new RequestOptions()
//                设置图片变换为圆角
//                .circleCrop()
//                设置站位图
                .placeholder(R.mipmap.timg_loading)
//                设置加载失败的错误图片
                .error(R.mipmap.timg_error);

//      Glide.with 会创建一个图片的实例，接收 Context、Activity、Fragment
        Glide.with(this)
//                指定需要加载的图片资源，接收 Drawable对象、网络图片地址、本地图片文件、资源文件、二进制流、Uri对象等等
                .load(img)
//                指定配置
                .apply(options)
//                用于展示图片的ImageView
                .into(detailImg);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}