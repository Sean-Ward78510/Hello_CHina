package com.example.module.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.tool.Entity.Fruit;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private List<Fruit> mFruitList;
    private Context context;
    private boolean isX5;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fruitImage;
        TextView fruitName;
        public ViewHolder(View view) {
            super(view);
            fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            fruitName = (TextView) view.findViewById(R.id.fruit_name);
        }
    }
    public FruitAdapter(List<Fruit> fruitList,Context context,boolean isX5){
        mFruitList = fruitList;
        this.context = context;
        this.isX5 = isX5;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fruit_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = mFruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getImage());
        holder.fruitName.setText(fruit.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isX5){
                    ARouter.getInstance().build("/modulePublic/showwebview/GeckoWebViewActivity")
                            .withString("url",fruit.getUrl())
                            .navigation();
                }else {
                    ARouter.getInstance().build("/modulePublic/showwebview/ShowWebViewActivity")
                            .withString("url",fruit.getUrl())
                            .navigation();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
