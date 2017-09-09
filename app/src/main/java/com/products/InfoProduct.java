package com.products;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.products.app.AppConfig;
import com.squareup.picasso.Picasso;
import com.products.ViewPagerAdapter;

import java.util.ArrayList;

public class InfoProduct extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

    ImageView image_big_product;
    TextView detail_brief,detail_info,detail_price;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    private int dotsCount;
    private ImageView[] dots;
    ImageButton btnNext,btnBack;
    LinearLayout pager_indicator;
    CollapsingToolbarLayout toolbarLayout;


    ArrayList<String> images= new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        toolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.toolbar_layout);
        image_big_product = (ImageView) findViewById(R.id.image_big_product);
        btnNext = (ImageButton) findViewById(R.id.btn_next);
        btnBack = (ImageButton) findViewById(R.id.btn_back);
        detail_brief = (TextView) findViewById(R.id.detailbrief);
        detail_info = (TextView) findViewById(R.id.detailinfo);
        detail_price = (TextView) findViewById(R.id.detailprice);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);

        btnNext.setOnClickListener(this);
        btnBack.setOnClickListener(this);





        Intent intent = getIntent();

        String brief = null;
        String info = null;
        String price = null;


        String name = null;
        if (intent != null) {
            name = intent.getStringExtra("name");
            price = intent.getStringExtra("price");
            brief = intent.getStringExtra("brief");
            info = intent.getStringExtra("info");
        }

        images.add(AppConfig.URL_PARENT+"images/" + name.replace(" ","_") + "/1.jpg");
        images.add(AppConfig.URL_PARENT+"images/"+name.replace(" ","_")+"/2.jpg");
        images.add(AppConfig.URL_PARENT+"images/"+name.replace(" ","_")+"/3.jpg");
        images.add(AppConfig.URL_PARENT+"images/"+name.replace(" ","_")+"/4.jpg");
        images.add("http://blank.jpg");

        toolbarLayout.setTitle(name);
        toolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        detail_brief.setText(brief);
        detail_info.setText(info);
        detail_price.setText("Price :"+price + " Rs.");

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        viewPagerAdapter = new ViewPagerAdapter(InfoProduct.this, images);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(this);

        setUiPageViewController();

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_next:
                viewPager.setCurrentItem((viewPager.getCurrentItem() < dotsCount)
                        ? viewPager.getCurrentItem() + 1 : 0);

                break;

            case R.id.btn_back:

                    viewPager.setCurrentItem((viewPager.getCurrentItem() < dotsCount)
                            ? viewPager.getCurrentItem() -1 : 0);

                break;
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        int pos = 4;


        if(position==pos)
        {
            viewPager.setCurrentItem(viewPager.getCurrentItem()-4,true);
        }


        for (int i = 0; i < dotsCount-1; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.non_selected_dot));
        }

        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selected_dot));

        if (position + 1 == dotsCount) {
            btnNext.setVisibility(View.GONE);
            btnBack.setVisibility(View.VISIBLE);
        } else if(position==0) {
            btnNext.setVisibility(View.VISIBLE);
            btnBack.setVisibility(View.GONE);
        }
        else{
            btnNext.setVisibility(View.VISIBLE);
            btnBack.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onPageSelected(int position) {

        int pos = 4;
        int spos=0;


        if(position==pos)
        {
            viewPager.setCurrentItem(viewPager.getCurrentItem()-4,true);
        }


        for (int i = 0; i < dotsCount-1; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.non_selected_dot));
        }

        if(position<pos) {
            dots[position].setImageDrawable(getResources().getDrawable(R.drawable.selected_dot));
        }

        if (position + 1 == dotsCount) {
            btnNext.setVisibility(View.GONE);
            btnBack.setVisibility(View.VISIBLE);
        } else if(position==0) {
            btnNext.setVisibility(View.VISIBLE);
            btnBack.setVisibility(View.GONE);
        }
        else{
            btnNext.setVisibility(View.VISIBLE);
            btnBack.setVisibility(View.VISIBLE);

        }

    }


    @Override
    public void onPageScrollStateChanged(int state) {



    }

    private void setUiPageViewController() {

        dotsCount = viewPagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount-1; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.non_selected_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);

            pager_indicator.addView(dots[i], params);
            pager_indicator.setVisibility(View.VISIBLE);
        }

       dots[0].setImageDrawable(getResources().getDrawable(R.drawable.selected_dot));

    }
};
