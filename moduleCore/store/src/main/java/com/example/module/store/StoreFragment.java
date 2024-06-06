package com.example.module.store;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.store.tool.BannerAdapter;
import com.example.module.store.tool.Product;
import com.example.module.store.tool.ProductAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;

@Route(path = "/module/store/StoreFragment")
public class StoreFragment extends Fragment implements View.OnClickListener {
    private ScrollView scrollView;
    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private int position0;
    private int position1;
    private int position11;
    private int position2;
    private int position22;
    private int position3;
    private int position33;
    private int position4;
    private int position44;
    private Banner banner;
    private BannerAdapter bannerAdapter;
    private ArrayList<Product> bannerList;
    private ArrayList<String> titlesList;
    private CardView wenChuang;
    private CardView chayun;
    private CardView shuiguo;
    private CardView jianiang;
    private ImageView gu_pic;
    private ImageView cha_pic;
    private ImageView guo_pic;
    private ImageView jiu_pic;
    private boolean isBanned;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.store_fragment,container,false);

        ARouter.getInstance().inject(this);

        initWidget(view);
        initList();
        banner = view.findViewById(R.id.banner_brand);
        bannerAdapter = new BannerAdapter(getContext(),bannerList);
        banner.setAdapter(bannerAdapter);
        banner.setIndicator(new CircleIndicator(getContext()));
        banner.setIndicatorNormalColor(Color.parseColor("#C0C0C0"));
        banner.start();

        return view;
    }
    public void initList(){
        bannerList = new ArrayList<>();
        bannerList.add(new Product("狗不理","http://www.chinagoubuli.com/",R.drawable.gou));
        bannerList.add(new Product("六必居","https://baike.baidu.com/item/%E5%85%AD%E5%BF%85%E5%B1%85/2302909",R.drawable.liu));
        bannerList.add(new Product("全聚德","https://www.quanjude.com.cn/",R.drawable.quan));
        bannerList.add(new Product("信远斋","http://www.xinyuanzhai.cn/brand.html",R.drawable.xin));
        bannerList.add(new Product("同春园","http://www.bjhuatian.cn/product/118.html",R.drawable.tongchun));
        bannerList.add(new Product("王致和","https://baike.baidu.com/item/%E7%8E%8B%E8%87%B4%E5%92%8C/80849",R.drawable.wang));
        bannerList.add(new Product("白云山","https://www.gybys.com.cn/",R.drawable.bai));
        bannerList.add(new Product("同仁堂","https://www.tongrentang.com/",R.drawable.tongren));

        titlesList = new ArrayList<>();
        titlesList.add("故宫文创");
        titlesList.add("茶韵醇香");
        titlesList.add("瓜果飘香");
        titlesList.add("佳酿醇香");
        for(String title : titlesList){
            tabLayout.addTab(tabLayout.newTab().setText(title));
        }
    }
    public void initWidget(View view){
        appBarLayout = view.findViewById(R.id.appbar);
        scrollView = view.findViewById(R.id.ScrollView);
        tabLayout = view.findViewById(R.id.tablayout);
        wenChuang = view.findViewById(R.id.wenchuang);
        chayun = view.findViewById(R.id.chayun);
        shuiguo = view.findViewById(R.id.shuiguo);
        jianiang = view.findViewById(R.id.jianiang);

        gu_pic = view.findViewById(R.id.gugong_pic);
        cha_pic = view.findViewById(R.id.cha_pic);
        guo_pic = view.findViewById(R.id.gua_pic);
        jiu_pic = view.findViewById(R.id.jiu_pic);


        wenChuang.setOnClickListener(this);
        chayun.setOnClickListener(this);
        shuiguo.setOnClickListener(this);
        jianiang.setOnClickListener(this);

        gu_pic.setOnClickListener(this);
        cha_pic.setOnClickListener(this);
        guo_pic.setOnClickListener(this);
        jiu_pic.setOnClickListener(this);

        scrollView.setEnabled(false);
        getCardHeight();

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                float collapseRatio = Math.abs(verticalOffset) / (float)appBarLayout.getTotalScrollRange();
                if (collapseRatio >= 0 && collapseRatio < 0.95){
                    tabLayout.setVisibility(View.GONE);
                    scrollView.setEnabled(false);
                }
                if (collapseRatio >= 0.95){
                    tabLayout.setVisibility(View.VISIBLE);
                    scrollView.setEnabled(true);
                }
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int position = tab.getPosition();
                Log.d("Tab", "onTabSelected: "+position);
                Log.d("Tab", "onTabSelected: " + position1 + " "  + position2 + " "  + position3 + " "  + position4 + " ");
                if (!isBanned) {
                    isBanned = true;
                    switch (position){
                        case 0:
                            scrollView.smoothScrollTo(0,position1);
                            isBanned = false;
                            break;
                        case 1:
                            scrollView.smoothScrollTo(0,position2);
                            isBanned = false;
                            break;
                        case 2:
                            scrollView.smoothScrollTo(0,position3);
                            isBanned = false;
                            break;
                        case 3:
                            scrollView.smoothScrollTo(0,position4);
                            isBanned = false;
                            break;
                    }
                }

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                scrollView.setSaveEnabled(true);
                if (!isBanned){
                    isBanned = true;
                    position0 = tabLayout.getTop();
                    int scrollY = scrollView.getScrollY();
                    if (scrollY > position1 && scrollY < position11){
                        tabLayout.getTabAt(0).select();
                    }
                    if (scrollY >= position2 && scrollY < position22){
                        tabLayout.getTabAt(1).select();
                    }
                    if (scrollY >= position3 && scrollY < position33){
                        tabLayout.getTabAt(2).select();
                    }
                    if (scrollY >= position4 && scrollY < position44){
                        tabLayout.getTabAt(3).select();
                    }
                    isBanned = false;
                }
            }
        });
    }

    public void getCardHeight(){

        tabLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                position0 = tabLayout.getTop();
            }
        });
        wenChuang.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                position1 = wenChuang.getTop();
                position11 = wenChuang.getBottom();
            }
        });
        chayun.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                position2 = chayun.getTop();
                position22 = chayun.getBottom();
            }
        });
        shuiguo.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                position3 = shuiguo.getTop();
                position33 = shuiguo.getBottom();
            }
        });
        jianiang.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                position4 = jianiang.getTop();
                position44 = jianiang.getBottom();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.wenchuang || view.getId() == R.id.gugong_pic){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://m.tb.cn/h.gVNbrrzY91otjSm  ") //https://www.shidianguji.com/  https://m.tb.cn/h.5tF0z7GqcwT5v0K
                    .navigation();
        }
        if (view.getId() == R.id.chayun || view.getId() == R.id.cha_pic){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url"," https://m.tb.cn/h.g4LMHvK8jzoTmxH")
                    .navigation();
        }
        if (view.getId() == R.id.shuiguo || view.getId() == R.id.gua_pic){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://m.tb.cn/h.g4LLy5CoUuD8obM ")
                    .navigation();
        }
        if (view.getId() == R.id.jianiang || view.getId() == R.id.jiu_pic){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://m.tb.cn/h.gVBuZ4MqwffC0Tx")
                    .navigation();
        }
    }
}
