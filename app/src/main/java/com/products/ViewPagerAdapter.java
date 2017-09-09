package com.products;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.products.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Shubham on 18/5/2017.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private  Context context;
    private ArrayList<String> IMAGES = new ArrayList<>();



    public ViewPagerAdapter(Context context, ArrayList<String> IMAGES) {
        this.IMAGES = IMAGES;
        this.context = context;
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }


    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public Object instantiateItem(View collection, int position) {


        LayoutInflater inflater = (LayoutInflater) collection.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.image_slider_item,null);
        ((ViewPager) collection).addView(view);
        final ImageView img = (ImageView) view.findViewById(R.id.image_big_product);
        Picasso.with(context)
                .load(IMAGES.get(position))
                .noPlaceholder()
                .resizeDimen(R.dimen.app_bar_height,R.dimen.app_bar_height)
                .into(img);



    return view;
    }


}