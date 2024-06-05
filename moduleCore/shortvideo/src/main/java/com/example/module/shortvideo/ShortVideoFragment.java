package com.example.module.shortvideo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.dueeeke.videocontroller.StandardVideoController;
import com.dueeeke.videoplayer.player.IjkVideoView;
import com.example.module.shortvideo.Entity.Video;
import com.example.module.shortvideo.OkHttpUtils.OkHttpsUtils;
import com.example.module.shortvideo.Tool.IJKVideoPlayerAdapter;
import com.example.module.shortvideo.Tool.OnViewPagerListener;
import com.example.module.shortvideo.Tool.PageLayoutManager;
import com.example.tool.Util.SERVER_IP;
//import com.example.module.shortvideo.Tool.VideoAdapter;
//import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

@Route(path = "/module/shortvideo/ShortVideoFragment")
public class ShortVideoFragment extends Fragment{

    private String Server_IP = SERVER_IP.Server_IP;
    private String Server_Apply_LoginVideo = SERVER_IP.Server_Apply_Video;
    private String Server_Apply_UnLoginVideo = SERVER_IP.Server_Apply_UnLoginVideo;
    private List<Video> videoList;
    private RecyclerView recyclerView;
    View view;
    View viewRecycle;
    //private VideoAdapter adapter;
    IjkVideoView ijkVideoView;
    private IJKVideoPlayerAdapter adapter;
    private PageLayoutManager pageLayoutManager;

    private SharedPreferences sp;
    private boolean isLogin;
    private boolean isInternet = true;
    private String email;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.shortvideo_gragment,container,false);

        initVideo();
        initPlayer();

        //ARouter.getInstance().inject(this);


//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                int firstPosition = manager.findFirstVisibleItemPosition();
//                int lastPosition = manager.findLastVisibleItemPosition();
//                int playPosition = GSYVideoManager.instance().getPlayPosition();
//                if (playPosition >= 0){
//                    if (playPosition < firstPosition || playPosition > lastPosition){
//                        if (GSYVideoManager.isFullState(getActivity())){
//                            return;
//                        }
//                        GSYVideoManager.releaseAllVideos();
//                        adapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        });
        return view;
    }

    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    changeVideo();
                    break;
                case 2:
                    initNotInternetVideo();
                    break;
            }
        }
    };

//        urlList.add("https://v-cdn.zjol.com.cn/277001.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/280443.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/276982.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/276984.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/276985.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/277004.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/277003.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/277002.mp4");
//        urlList.add("https://v-cdn.zjol.com.cn/276996.mp4");

    public void changeVideo(){
        Log.d("changeVideo", "changeVideo: " + videoList.size());
        adapter.notifyDataSetChanged();
    }

    public void initVideo(){
        sp = getContext().getSharedPreferences("Information", Context.MODE_PRIVATE);
        isLogin = sp.getBoolean("isLogin",false);
        email = sp.getString("email",null);
        Log.d("initVideo", "onResume: " + isLogin);
        videoList = new ArrayList<>();
        if (isLogin){
            Log.d("initVideo", "initVideo: 111");
            Log.d("initVideo", "initVideo: " + Server_IP + Server_Apply_LoginVideo);
            ApplyLoginVideo();
        }else {
            Log.d("initVideo", "initVideo: 222");
            ApplyUnLoginVideo();
        }
    }

    public void initPlayer(){
        recyclerView = view.findViewById(R.id.video_recycle);
        //LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        pageLayoutManager = new PageLayoutManager(getContext(), OrientationHelper.VERTICAL);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isInternet){
                    if (!recyclerView.canScrollVertically(1)){
                        if (isLogin){
                            Log.d("changeVideo", "onScrolled: isLogin");
                            ApplyLoginVideo();
                        }else {
                            Log.d("changeVideo", "onScrolled: is not Login");
                            ApplyUnLoginVideo();
                        }
                    }
                }else {
                    Toast.makeText(getContext(),"服务器未开启！",Toast.LENGTH_SHORT).show();
                }
            }
        });

        pageLayoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete(View view) {
                playVideo(0,view);
            }

            @Override
            public void onPageRelease(boolean isNext, int position, View view) {
                releaseVideo(view);
            }

            @Override
            public void onPageSelected(int position, boolean isBottom, View view) {
                playVideo(position,view);
            }
        });

        recyclerView.setLayoutManager(pageLayoutManager);
        adapter = new IJKVideoPlayerAdapter(getContext(),videoList,isLogin,getActivity().getSupportFragmentManager(), getActivity());
        recyclerView.setAdapter(adapter);
    }
    private void playVideo(int position,View view){//播放视频\
        viewRecycle = view;
        ijkVideoView = view.findViewById(R.id.ijkVideoPlayer);
        //ijkVideoView = adapter.getViewHolder(view,recyclerView).ijkVideoView;
        StandardVideoController standardVideoController = new StandardVideoController(getContext());
        ijkVideoView.setVideoController(standardVideoController);
        //ijkVideoView.setUrl(urlList.get(position));
        ijkVideoView.start();
    }
    private void releaseVideo(View view){
        ijkVideoView = view.findViewById(R.id.ijkVideoPlayer);
//        //ijkVideoView = adapter.getViewHolder(view,recyclerView).ijkVideoView;
        StandardVideoController standardVideoController = new StandardVideoController(getContext());
        ijkVideoView.setVideoController(standardVideoController);
        ijkVideoView.stopPlayback();
        ijkVideoView.release();
    }


    @Override
    public void onPause() {
        super.onPause();
        Log.d("ShortVideo", "onPause: 111");
        if(viewRecycle != null){
            ijkVideoView = viewRecycle.findViewById(R.id.ijkVideoPlayer);
//        ijkVideoView = adapter.getViewHolder(viewRecycle,recyclerView).ijkVideoView;
            //ijkVideoView.pause();
            ijkVideoView.stopPlayback();
        }

        //ijkVideoView.release();
        //recyclerView.stopScroll();
        //GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("ShortVideo", "onResume: 444");
        sp = getContext().getSharedPreferences("Information", Context.MODE_PRIVATE);
        isLogin = sp.getBoolean("isLogin",false);
        adapter.setLogin(isLogin);
        adapter.getInformation();
        adapter.notifyDataSetChanged();
        //GSYVideoManager.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("ShortVideo", "onDestroy: 333");
//        VideoViewManager.instance().releaseVideoPlayer();
        //GSYVideoManager.releaseAllVideos();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("ShortVideo", "onStop: 222");

//        recyclerView.setAdapter(null);
//        adapter = null;
    }

    public void stopPlayVideo(){
        if(viewRecycle != null){
            ijkVideoView = viewRecycle.findViewById(R.id.ijkVideoPlayer);
//        ijkVideoView = adapter.getViewHolder(viewRecycle,recyclerView).ijkVideoView;
            ijkVideoView.pause();
            //ijkVideoView.stopPlayback();
        }
    }
    public void reStartPlayVideo(){
        if (viewRecycle != null){
//            ijkVideoView = adapter.getViewHolder(viewRecycle,recyclerView).ijkVideoView;
            ijkVideoView = viewRecycle.findViewById(R.id.ijkVideoPlayer);
            ijkVideoView.resume();
        }
    }

    public void initNotInternetVideo(){
        Log.d("NoNet", "initNotInternetVideo: ");
        Video video1 = new Video("13","鸣沙山月牙泉","http://sb474wnni.hb-bkt.clouddn.com/53b54a5ec4d304d6de31eed34f5247dd.mp4","美丽的月牙泉",false,false);
        Video video2 = new Video("14","传统婚礼","http://sb474wnni.hb-bkt.clouddn.com/5c68381b5f09074eaadcc6132906a38d.mp4","那个备婚两年刷爆全网的汉服婚礼正片来啦",false,false);
        Video video3 = new Video("15","舌尖上的中国——松茸","http://sb474wnni.hb-bkt.clouddn.com/6a8c2855848372ff211a36bfd13bf4e6.mp4","你知道松茸为什么这么贵吗？",false,false);
        Video video4 = new Video("16","手抓羊肉","http://sb474wnni.hb-bkt.clouddn.com/4b08357c4b20c84c57a6de96c1a912bd.mp4","煮羊肉时不放盐，出锅装盘才放盐。这羊肉好嫩，想吃！",false,false);
        Video video5 = new Video("17","风味人间--蟹","http://sb474wnni.hb-bkt.clouddn.com/75813cbd07d6468e00c0d9c38d804960.mp4","风味人间——蟹",false,false);
        Video video6 = new Video("18","中国吉祥年","http://sb474wnni.hb-bkt.clouddn.com/8baaef752e0854ae5051d96b8f13e98a.mp4","团圆年｜我们的文化中国年：龙行龘龘",false,false);
        videoList.add(video1);
        videoList.add(video2);
        videoList.add(video3);
        videoList.add(video4);
        videoList.add(video5);
        videoList.add(video6);
        adapter.notifyDataSetChanged();
    }

    public void ApplyLoginVideo(){
        Log.d("test", "ApplyLoginVideo: 111");
        OkHttpsUtils.sendApplyLoginVideoRequest(Server_IP + Server_Apply_LoginVideo,email, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("NoNet", "onFailure: ");
                isInternet = false;
                adapter.setIsInternet(false);
                handler.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String respondData = response.body().string();
                    JSONObject jsonObject = new JSONObject(respondData);
                    Log.d("ApplyLoginVideo", "onResponse: " + respondData);
                    Log.d("ApplyLoginVideo", "onResponse: " + jsonObject.getInt("code"));
                    if (jsonObject.getInt("code") == 200){
                        JSONObject object;
                        JSONArray array = jsonObject.getJSONArray("data");
                        Log.d("ApplyLoginVideo", "onResponse: " + array.length());
                        for (int i = 1; i < array.length(); i++) {
                            object = array.getJSONObject(i);
                            Video video = new Video();
                            video.id = object.getString("vedioId");
                            video.name = object.getString("name");
                            video.url = object.getString("url");
                            video.intro = object.getString("title");
                            video.isLike = object.getBoolean("like");
                            video.isCollect = object.getBoolean("collect");
                            videoList.add(video);
                        }
                        adapter.setIsInternet(true);
                        Log.d("ApplyLoginVideo", "onResponse: yes + " + videoList.size());
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void ApplyUnLoginVideo(){
        OkHttpsUtils.sendApplyUnLoginVideoRequest(Server_IP + Server_Apply_UnLoginVideo, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                isInternet = false;
                adapter.setIsInternet(false);
                handler.sendEmptyMessage(2);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String respondData = response.body().string();
                    JSONObject jsonObject = new JSONObject(respondData);
                    Log.d("ApplyUnLoginVideo", "onResponse: " + respondData);
                    if (jsonObject.getInt("code") == 200){
                        JSONObject object;
                        JSONArray array = jsonObject.getJSONArray("data");
                        for (int i = 1; i < array.length(); i++) {
                            object = array.getJSONObject(i);
                            Video vedio = new Video();
                            vedio.id = object.getString("vedioId");
                            vedio.name = object.getString("name");
                            vedio.url = object.getString("url");
                            vedio.intro = object.getString("title");
                            vedio.isLike = object.getBoolean("like");
                            vedio.isCollect = object.getBoolean("collect");
                            videoList.add(vedio);
                        }
                        Log.d("LoginActivity", "onResponse: yes");
                        adapter.setIsInternet(true);
                        handler.sendEmptyMessage(1);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
