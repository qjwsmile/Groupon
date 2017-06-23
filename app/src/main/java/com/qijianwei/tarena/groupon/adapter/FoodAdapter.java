package com.qijianwei.tarena.groupon.adapter;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qijianwei.tarena.groupon.R;
import com.qijianwei.tarena.groupon.entity.Food;
import com.qijianwei.tarena.groupon.util.HttpUtil;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tarena on 2017/6/23.
 */

public class FoodAdapter extends MyBasseAadapter<Food.BusinessesBean>{
    int[] ima={R.drawable.movie_star10,R.drawable.movie_star20,R.drawable.movie_star30,R.drawable.movie_star35,
            R.drawable.movie_star40,R.drawable.movie_star45,R.drawable.movie_star50};

    public FoodAdapter(Context context, List<Food.BusinessesBean> datas) {
        super(context, datas);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vh;
        if(view==null){
            view = inflater.inflate(R.layout.item_food_layout,viewGroup,false);
            vh = new ViewHolder(view);
            view.setTag(vh);
        }else{
            vh= (ViewHolder) view.getTag();
        }
        Food.BusinessesBean businessesBean = datas.get(i);
        HttpUtil.loadImageRetrogit(context,businessesBean.getS_photo_url(),vh.food_imageview_bitmap);
        vh.food_name.setText(businessesBean.getName());
        Random random = new Random();
        int count1 = random.nextInt(7);
        vh.food_imageview_start.setImageResource(ima[count1]);
        vh.food_textview_cuisine.setText(businessesBean.getCategories().get(0));
        int count2=random.nextInt(100)+50;
        vh.food_textview_bar.setText(String.valueOf(count2));
        int count3=random.nextInt(100)+50;
        vh.food_textview_money.setText(String.valueOf(count3));
        return view;
    }

    public class ViewHolder{
        @BindView(R.id.iv_item_food_image)
        ImageView food_imageview_bitmap;
        @BindView(R.id.tv_item_food_name)
        TextView food_name;
        @BindView(R.id.imageview_food_start)
        ImageView food_imageview_start;
        @BindView(R.id.textview_cuisine)
        TextView food_textview_cuisine;
        @BindView(R.id.tv_item_food_money)
        TextView food_textview_money;
        @BindView(R.id.tv_item_food_bar)
        TextView food_textview_bar;

        public ViewHolder(View view){
            ButterKnife.bind(this,view);
        }
    }
}
