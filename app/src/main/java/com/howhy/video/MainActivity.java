package com.howhy.video;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.howhy.video.adapter.VideoListAdapter;
import com.howhy.video.model.VideoDetail;

import org.jetbrains.annotations.NotNull;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private EditText searchEdit;
    private Button searchBtn;
//    private Button indexBtn;
//    private Button tvBtn;
//    private Button movieBtn;
//    private Button artBtn;
//    private Button animationBtn;
    private ListView videoListView;
    private List<VideoDetail> videoList;
    private VideoListAdapter videoListAdapter;
    private final  static String SEARCH_VIDEO_URL="https://api.okzy.tv/api.php/provide/vod/at/json/?ac=detail";//?ac=detail
    private final  static String VIDEO_LIST_URL="https://api.okzy.tv/api.php/provide/vod/at/json/?ac=list&";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("影视推荐");
        initView();
        initData();
        initListener();
        //startActivity(new Intent(this,VideoPlayerActivity.class));
    }

    private void initData() {
        videoListAdapter = new VideoListAdapter(this);
        videoListView.setAdapter(videoListAdapter);
        SharedPreferences sharedPreferences = getSharedPreferences("videoList",MODE_PRIVATE);
        String videoListStr = sharedPreferences.getString("videoList", "");
        Log.i(TAG, "initData:333333333333 "+videoListStr);
        if (!"null".equals(videoListStr) && !"[]".equals(videoListStr) && !videoListStr.isEmpty()) {
            Gson gson = new Gson();
            videoList = gson.fromJson(videoListStr, new TypeToken<List<VideoDetail>>() {
            }.getType());
            videoListAdapter.setVideoList(videoList);
        }else{
            Log.i(TAG, "initData: 555555555");
            getVideoList(VIDEO_LIST_URL + "limit=20&pg=1", true);
            sharedPreferences = getSharedPreferences("videoList",MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson=new Gson();
            Log.i(TAG, "onPause: "+videoList.toString());
            editor.putString("videoList",gson.toJson(videoList));
            editor.commit();
        }
    }
    private void initListener(){
        searchBtn.setOnClickListener(this);
//        indexBtn.setOnClickListener(this);
//        tvBtn.setOnClickListener(this);
//        movieBtn.setOnClickListener(this);
//        artBtn.setOnClickListener(this);
//        animationBtn.setOnClickListener(this);
        videoListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i(TAG, "onItemClick: "+videoList.toString());
                if(videoList!=null && videoList.size()>0){
                    VideoDetail video=videoList.get(i);
                    Log.i(TAG, "onItemClick: "+video.getVod_name());
                    getVideoList(SEARCH_VIDEO_URL+"&ids="+video.getVod_id().toString(),false);
                }
            }
        });
    }
    private void initView() {
        searchEdit=findViewById(R.id.searchEdit);
        searchBtn=findViewById(R.id.searchBtn);
//        indexBtn=findViewById(R.id.indexBtn);
//        tvBtn=findViewById(R.id.tvBtn);
//        movieBtn=findViewById(R.id.movieBtn);
//        artBtn=findViewById(R.id.artBtn);
//        animationBtn=findViewById(R.id.animationBtn);
        videoListView=findViewById(R.id.videoListView);
    }

    @Override
    public void onClick(View view) {
        int btnId=view.getId();
        switch (btnId){
            case R.id.searchBtn:
                String kw=searchEdit.getText().toString();
                getVideoList(SEARCH_VIDEO_URL+"&wd="+kw,true);
                break;
            default:
//            case R.id.indexBtn:
//                getVideoList(VIDEO_LIST_URL+"limit=20&pg=1",true);
//                Log.i(TAG, "onClick: "+videoList.toString());
//                break;
//            case R.id.tvBtn:
//                getVideoList(VIDEO_LIST_URL+"limit=20&pg=1&t=1",true);
//                break;
//            case R.id.movieBtn:
//                getVideoList(VIDEO_LIST_URL+"limit=20&pg=1&t=2",true);
//                break;
//            case R.id.artBtn:
//                getVideoList(VIDEO_LIST_URL+"limit=20&pg=1&t=3",true);
//                break;
//            case R.id.animationBtn:
//                getVideoList(VIDEO_LIST_URL+"limit=20&pg=1&t=4",true);
//                break;
        }
    }

    public void getVideoList(String url, final boolean isList){
        videoList=new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "onFailure: 搜索视频资源失败");
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                 if (response.isSuccessful()) {
                         String string = response.body().string();
                         JsonParser parser = new JsonParser();
                         JsonElement element = parser.parse(string);
                         JsonObject jsonObject=element.getAsJsonObject();
                         JsonArray jsonArray=jsonObject.getAsJsonArray("list");
                         Gson gson=new Gson();
                         if(isList){
                             for(int i=0;i<jsonArray.size();i++){
                                 VideoDetail video=gson.fromJson(jsonArray.get(i),VideoDetail.class);
                                 videoList.add(video);
                             }

                             MainActivity.this.runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     Log.i(TAG, "onResponse:11111111111111 "+videoList.toString());
                                     videoListAdapter.setVideoList(videoList);
                                 }
                             });
                         }else{
                             Intent detail=new Intent(MainActivity.this,VideoDetailActivity.class);
                             VideoDetail videoDetail=null;
                             if (jsonArray.size()>0){
                                 videoDetail=gson.fromJson(jsonArray.get(0), VideoDetail.class);
                             }else{
                                 Toast.makeText(MainActivity.this,"没有找到相关资源详情",Toast.LENGTH_LONG).show();
                             }
                             detail.putExtra("detailinfo",videoDetail);
                             startActivity(detail);
                         }
                 } else {
                     Log.e(TAG, "onFailure: 搜索视频资源失败");
                 }
            }
        });
    }


}