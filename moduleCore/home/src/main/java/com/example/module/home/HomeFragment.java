package com.example.module.home;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.module.home.OKHttp.OkHttpsUtils;
import com.example.tool.Entity.Fruit;
import com.example.tool.Util.SERVER_IP;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

@Route(path = "/moduleCore/home/HomeFragment")
public class HomeFragment extends Fragment implements View.OnClickListener{
    private String URL = SERVER_IP.Server_IP;
    private  RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private TextView first;
    private TextView second;
    private TextView third;
    private TextView new_one;
    private TextView new_two;
    private TextView new_three;
    private TextView new_four;
    private HomeView homeView = new HomeView();
    private ImageView collect_one;
    private ImageView collect_two;
    private ImageView collect_three;
    private ImageView collect_four;
    private ImageView collect_six;
    private ImageView collect_seven;
    private ImageView collect_eight;
    private  ImageView zhuanfa_one;
    private  ImageView zhuanfa_two;
    private  ImageView zhuanfa_three;
    private  ImageView zhuanfa_four;
    private  ImageView zhuanfa_six;
    private  ImageView zhuanfa_seven;
    private  ImageView zhuanfa_eight;
    private  ImageView like_one;
    private  ImageView like_two;
    private  ImageView like_three;
    private  ImageView like_four;
    private  ImageView like_six;
    private  ImageView like_seven;
    private  ImageView like_eight;

    SharedPreferences sp;
    String email;
    private MZBannerView galleryRecycle;
    private List<Integer> galleries = Arrays.asList(new Integer[]{R.drawable.begin, R.drawable.first,
            R.drawable.second,R.drawable.third,R.drawable.last});
    private List<Fruit> fruitList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        sp = getContext().getSharedPreferences("Information",Context.MODE_PRIVATE);
        email = sp.getString("email",null);

        ARouter.getInstance().inject(this);
        initFruits();
        getNew();
        getLifeNew();


        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(fruitList,getContext(),true);
        recyclerView.setAdapter(adapter);
        initWidget(view);
        galleryRecycle = (MZBannerView) view.findViewById(R.id.Gallery);

        galleryRecycle.setPages(galleries, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });


        return view;
    }
    public  class  BannerViewHolder implements MZViewHolder<Integer>{
        ImageView imageView;
        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.gallery_item,null);
            imageView = (ImageView) view.findViewById(R.id.gallery_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            imageView.setImageResource(data);
        }
    }

    private void initFruits() {
        Fruit GX = new Fruit(("你好,广西"), R.drawable.gx,"https://share.ccmapp.cn/shareiframe?thirdParams=eyJ0aXRsZSI6IuS9oOWlveW5v-ilvyIsImRlc2MiOiLoh6rkuqTotr7oh7PkvJrnqL3kuIPlhavljYPph4zvvIzlub_opb_lpITnmb7otormnYLlpITvvIw4MOS4h-W5tOWJjeeahOeBq-enjeW7tue7reiHs-S7iuOAguWxseawtOacieebuOmAou-8jOe7iOS8muWIsOa8k-axn-OAgua8k-axn-ahguael-iwt-WcsO-8jOWNg-WzsOernuengO-8jOeZvua5lueip--8jOS4gOaxn-a4heOAguWkqeexgeawkeatjO-8jOawtOS4iue7v-Wfju-8jOW_g-mGieW5v-ilv--8jOS4jeiZmuatpOihjOOAgiIsImlmcmFtZVVybCI6Imh0dHBzOi8vcmVzLmNjbWFwcC5jbi9tL3NwZWNpYWxTdWJqZWN0P2lkPTEwOTI4MzcyOTI4MzAxMDAmdG9rZW49NjVlOWI0MzQ3MmQ3MTUxZmIxN2QwMDQ3IiwiaW1nVXJsIjoiaHR0cHM6Ly9jdGRyLmNjbWFwcC5jbi9nYXRld2F5L2ZpbGUvc3RhdGljcy8yMDIzLzA3LzE5LzgwOTc2N2NlLTU1MjQtNGRiOS05MTZkLTRiOTFkMzliNTYzOS5qcGcifQ");
        Fruit JX = new Fruit(("你好,江西"), R.drawable.jx,"https://share.ccmapp.cn/shareiframe?thirdParams=eyJ0aXRsZSI6IuS9oOWlveaxn-ilvyIsImRlc2MiOiLov5nph4zmmK_msZ_opb_vvIzkuInpnaLnjq_lsbHkuIDpnaLkuLTmsZ_vvIzlsbHohInlm7TpmLvvvIzmsLTmtYHlpZTmtozvvIzkuIDlvKDkuIDlkIjpl7TvvIzlrZXogrLlh7rkuoblpJrlsJHor5fmg4XnlLvmhI_kuIDmraXkuIDmma_jgILouqvmnKrliqjvvIzlv4Plt7Lov5zvvIzmsZ_opb_po47mma_ni6zlpb3vvIzlv6vmnaXkuI7mlofml4XkuK3lm73otbDov5vmsZ_opb_vvIzmhJ_lj5fni6zlsZ7kuo7ov5nph4znmoTlnLDpgZPpo47niankuI7muZblhYnlsbHoibLjgIIiLCJpZnJhbWVVcmwiOiJodHRwczovL3Jlcy5jY21hcHAuY24vbS9zcGVjaWFsU3ViamVjdD9pZD0lMjAxMDg4NDAwODE0NDkwMTAwJnRva2VuPTY1ZTliNDM0NzJkNzE1MWZiMTdkMDA0NyIsImltZ1VybCI6Imh0dHBzOi8vY3Rkci5jY21hcHAuY24vZ2F0ZXdheS9maWxlL3N0YXRpY3MvMjAyMy8wNy8xNy8wMDc4YmY1ZS02ZjBhLTQ3M2UtYWZhYi04NDE0YjYyM2E3MTIucG5nIn0");
        Fruit NMG = new Fruit(("你好,内蒙古"), R.drawable.nmg,"https://share.ccmapp.cn/shareiframe?thirdParams=eyJ0aXRsZSI6IuS9oOWlveWGheiSmeWPpCIsImRlc2MiOiLmgqDmiaznmoTpqazlpLTnkLTlo7DmkpXoo4LlpJznqbrvvIzpqa_pub_ouI_nnYDmnpfpl7ToloTpm77kv6HmraXlvpzlvonvvIzliJrmjKTnmoTniZvlpbbmlaPlj5HnnYDpnZLojYnpppnvvIzoi6Xlhqzml6XnmoTpm77lh4flpYfmma_ov5jkuI3lpJ_lhrflhr3vvIzpgqPlhYXmu6HlvILln5_po47mg4XnmoTmu6HmtLLph4zkvr_mlLbol4_nnYDmlbTkuKrnp4vlpKnjgILngavng6fnmoTmtYHkupHvvIzov57lpKnnmoTnoqfojYnvvIzng63mg4XnmoTniafmsJHpqpHnnYDpqazlhL_vvIzlnKjlhoXokpnlj6TvvIzmnInnnYDmnIDnuq_lh4DnmoToh6rnlLHjgIIiLCJpZnJhbWVVcmwiOiJodHRwczovL3Jlcy5jY21hcHAuY24vbS9zcGVjaWFsU3ViamVjdD9pZD0lMjAxMTQ5ODkxNzE5MTEwMTAwJnRva2VuPTY1ZTliNDM0NzJkNzE1MWZiMTdkMDA0NyIsImltZ1VybCI6Imh0dHBzOi8vY3Rkci5jY21hcHAuY24vZ2F0ZXdheS9maWxlL3N0YXRpY3MvMjAyMy8wOS8yMy85MjJiZTUzZi04MjExLTQ5MmQtYThmOC0yNmUxMDFkZDFhNjQuanBnIn0");
        Fruit HLJ = new Fruit(("你好,黑龙江"), R.drawable.hlj,"https://share.ccmapp.cn/shareiframe?thirdParams=eyJ0aXRsZSI6IuS9oOWlvem7kem-meaxnyIsImRlc2MiOiLlpoLmnpzlhqzlpKnmnInmlYXkuaHvvIzpgqPkuIDlrprmmK_pu5HpvpnmsZ_jgILomb3nhLbmmK_lvojov5zlvojov5znmoTlnLDmlrnvvIzljbTmmK_lvojnvo7lvojnvo7nmoTmlYXkuaHjgILpm77lh4flhrDmsrPvvIzmnpfmtbfpm6rljp_vvIzmmK_lpKfoh6rnhLbotZDkuojnmoTnpLznianvvIzlnKjov5nph4zvvIzkvaDkvJrlj5HnjrDokL3pm6rmnInlo7DvvIzlo7Dlo7DmnInmg4XigKbigKYiLCJpZnJhbWVVcmwiOiJodHRwczovL3Jlcy5jY21hcHAuY24vbS9zcGVjaWFsU3ViamVjdD9pZD0lMjAxMTE1MzAwMzMyODQwMTAwJnRva2VuPTY1ZTliNDM0NzJkNzE1MWZiMTdkMDA0NyIsImltZ1VybCI6Imh0dHBzOi8vY3Rkci5jY21hcHAuY24vZ2F0ZXdheS9maWxlL3N0YXRpY3MvMjAyMy8wOS8xMS80MDllODc0YS1mNTIzLTRiYTctYTE5My02ZDZkMDdkZThkNmYucG5nIn0");
        Fruit BJ = new Fruit(("你好,北京"), R.drawable.bj,"https://share.ccmapp.cn/shareiframe?thirdParams=eyJ0aXRsZSI6IuS9oOWlveWMl-S6rCIsImRlc2MiOiLljJfkuqzvvIzpppbpg73vvIw4MDDkvZnlubTlu7rpg73lj7LvvIzmlYXlrqvln47lopnkuIvln4vol4_nmoTljoblj7LngbDng6zkvZnmuKnnirnlnKjvvIzmiqTln47msrPnoqfmsLTnu5Xln47lrojljavljYPlubTlubPlronvvIzlpKnlronpl6jorrDlvZXnmoTliJnmmK_kuK3lm73mnKzouqvjgILkuIDmnaHog6HlkIzvvIzljYrkuKrkuK3lm73vvIzplb_lronooZfkuIrnmoTmlYXkuovvvIzkuIDlubTmjqXkuIDlubTjgIIiLCJpZnJhbWVVcmwiOiJodHRwczovL3Jlcy5jY21hcHAuY24vbS9zcGVjaWFsU3ViamVjdD9pZD0lMjAxMDk2Mzc3MTE1ODIwMTAwJnRva2VuPTY1ZTliNDM0NzJkNzE1MWZiMTdkMDA0NyIsImltZ1VybCI6Imh0dHBzOi8vY3Rkci5jY21hcHAuY24vZ2F0ZXdheS9maWxlL3N0YXRpY3MvMjAyMy8wNy8yNS81NGI2NTE1MS1mNWRhLTQ3MWQtYWViNS0xNmQ1OTE1MTZjMGUucG5nIn0");
        Fruit HN = new Fruit(("你好,河南"), R.drawable.hn,"https://share.ccmapp.cn/shareiframe?thirdParams=eyJ0aXRsZSI6IuS9oOWlveays-WNlyIsImRlc2MiOiLkuKTljYPlubTljoblj7LnnIvopb_lronvvIzkupTljYPlubTljoblj7LnnIvmsrPljZfvvIHlnKjmsrPljZfop6bmkbjkuK3ljY7mlofmmI7nmoTohInliqjvvIzlk4HkuIDnopfmtYHkvKDljYPlubTnmoTog6HovqPmsaTvvIzlnKjmsrPljZfvvIzpgYfop4HmnIDml6nnmoTkuK3lm73igKbigKYiLCJpZnJhbWVVcmwiOiJodHRwczovL3Jlcy5jY21hcHAuY24vbS9zcGVjaWFsU3ViamVjdD9pZD0lMjAxMTI0NjM1MDAzMzMwMTAwJnRva2VuPTY1ZTliNDM0NzJkNzE1MWZiMTdkMDA0NyIsImltZ1VybCI6Imh0dHBzOi8vY3Rkci5jY21hcHAuY24vZ2F0ZXdheS9maWxlL3N0YXRpY3MvMjAyMy8wOC8yNS8xYjZhMzRlMi0zNzNhLTQxZWQtODA3ZS1mNWJiZjQ4NDc3NjIucG5nIn0");
        Fruit JS = new Fruit(("你好,江苏"), R.drawable.js,"https://share.ccmapp.cn/shareiframe?thirdParams=eyJ0aXRsZSI6IuS9oOWlveaxn-iLjyIsImRlc2MiOiLmsZ_oi4_vvIzlj5bmsZ_lroHlupzjgIHoi4_lt57lupzkuKTlupzkuYvpppblrZfogIzlvpflkI3vvIzmi6XmnInmsZ_ljZfpo47lhYnnmoTnvKDnu7XnvLHnu7vvvIzkuZ_mnInmpZrmsYnmlofljJbnmoToh6rlvLrkuI3mga_jgILlkLTotorkuYvlnLDvvIzoh6rlj6TlpJrmg4XjgILkuo7mmK_mmKXpo47pmr7op6PmhI_vvIzlsbHmsLTlhaXloqjmn5PvvIzkuIDmnJ3lhaXlp5Hoi4_vvIzmu6HnnLzmmK_msZ_ljZfjgIIiLCJpZnJhbWVVcmwiOiJodHRwczovL3Jlcy5jY21hcHAuY24vbS9zcGVjaWFsU3ViamVjdD9pZD0lMjAxMTI4MTMwODczODQwMTAwJnRva2VuPTY1ZTliNDM0NzJkNzE1MWZiMTdkMDA0NyIsImltZ1VybCI6Imh0dHBzOi8vY3Rkci5jY21hcHAuY24vZ2F0ZXdheS9maWxlL3N0YXRpY3MvMjAyMy8wOC8yOS9mOGNlYTNkZi1jMjM5LTQyYzAtOTg3Ny0zMWNlNzQyZDNhNDUuanBnIn0");
        Fruit GZ = new Fruit(("你好,贵州"), R.drawable.gz,"https://share.ccmapp.cn/shareiframe?thirdParams=eyJ0aXRsZSI6IuS9oOWlvei0teW3niIsImRlc2MiOiLlpKfoh6rnhLbprLzmlqfnpZ7lt6XvvIzlnKjotLXlt57nlZnkuIvkuobkuIDniYfku5nlooPjgILmsqfmtbfnlKgxNOS6v-W5tOW5u-WMluaIkOahkeeUsO-8jOWkqeeUn-ahpeeJtei_nui1t-S4pOW6p-WxseS9k--8jOefs-WktOiDveiusui_sOWcsOeQg-W-gOS6i-KApuKApuaOpeS4i-adpe-8jOivt-WSjOaIkeS7rOS4gOi1t-aOoue0ouS4h-eJqeelnuWlh-eahOi0teW3nuS5i-e-juWQp--8gSIsImlmcmFtZVVybCI6Imh0dHBzOi8vcmVzLmNjbWFwcC5jbi9tL3NwZWNpYWxTdWJqZWN0P2lkPSUyMDEwOTY5NjY4OTAwMTAxMDAmdG9rZW49NjVlOWI0MzQ3MmQ3MTUxZmIxN2QwMDQ3IiwiaW1nVXJsIjoiaHR0cHM6Ly9jdGRyLmNjbWFwcC5jbi9nYXRld2F5L2ZpbGUvc3RhdGljcy8yMDIzLzA3LzI0LzlmZjFlZDZmLTNjNDUtNDY2Yi1iNDNjLTViODQyNGUyZDIzMC5qcGcifQ");

        fruitList.add(GX);
        fruitList.add(JX);
        fruitList.add(NMG);
        fruitList.add(HLJ);
        fruitList.add(BJ);
        fruitList.add(HN);
        fruitList.add(JS);
        fruitList.add(GZ);
    }
    private void initWidget(View view){
        first = view.findViewById(R.id.first_name);
        first.setOnClickListener(this);
        second = view.findViewById(R.id.second_name);
        second.setOnClickListener(this);
        third = view.findViewById(R.id.third_name);
        third.setOnClickListener(this);
        new_one = view.findViewById(R.id.new_one);
        new_one.setOnClickListener(this);
        new_two = view.findViewById(R.id.new_two);
        new_two.setOnClickListener(this);
        new_three = view.findViewById(R.id.new_three);
        new_three.setOnClickListener(this);
        new_four = view.findViewById(R.id.new_four);
        new_four.setOnClickListener(this);

    }
    
    public void getNew(){
        OkHttpsUtils.getNew(URL, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("TAG", "onFailure: ");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseDate = response.body().string();
                homeView.updateNew(responseDate);
            }
        });
    }

    public void getLifeNew(){
        OkHttpsUtils.getLifeNew(URL, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("TAG", "onFailure: ");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String responseDate = response.body().string();
                homeView.updateLifeNew(responseDate);
            }
        });
    }
    
    public void onClick(View view){
        if(view.getId() == R.id.first_name){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url"," http://www.china.com.cn/lianghui/news/2024-03/11/content_117053163.shtml")
                    .withBoolean("isArticle",true)
                    .navigation();
        }
        if(view.getId() == R.id.second_name){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url"," http://www.china.com.cn/lianghui/news/2024-03/11/content_117054183.shtml")
                    .withBoolean("isArticle",true)
                    .navigation();
        }
        if(view.getId() == R.id.third_name){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url"," http://photo.china.com.cn/2024-03/11/content_117053662.shtml")
                    .withBoolean("isArticle",true)
                    .navigation();
        }
        if(view.getId() == R.id.new_one){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.chinanews.com.cn/life/2024/03-13/10179323.shtml")
                    .withBoolean("isArticle",true)
                    .navigation();
        }
        if(view.getId() == R.id.new_two){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.chinanews.com.cn/life/2024/03-13/10179345.shtml")
                    .withBoolean("isArticle",true)
                    .navigation();
        }
        if(view.getId() == R.id.new_three){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.chinanews.com.cn/sh/2024/03-13/10179610.shtml")
                    .withBoolean("isArticle",true)
                    .navigation();
        }
        if(view.getId() == R.id.new_four){
            ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                    .withString("url","https://www.chinanews.com.cn/life/2024/03-13/10179204.shtml")
                    .withBoolean("isArticle",true)
                    .navigation();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        galleryRecycle.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        galleryRecycle.start();
    }

    class HomeView{
        String data;
       public void updateNew(String data){

        }

        public void updateLifeNew(String data){

        }
    }
}
