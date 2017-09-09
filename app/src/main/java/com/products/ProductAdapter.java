package com.products;

/**
 * Created by Shubham on 5/11/2017.
 */
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.products.app.AppConfig;
import com.products.app.AppController;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder>  {


    public String name,price,brief,image,info,likes_count,is_like;
    private List<Product> productsList;
    private Context context;
    public String returnedcount=null;
    ImageButton btn_share,closebtn;
    MediaPlayer mplayer;
    int lastPosition;
    MyViewHolder holder2;
    boolean undoOn;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView p_name, p_price,tv_likes_count;
        public LinearLayout Linearlayout;
        public ImageView image_product;
        public ToggleButton toggle_like;
        FrameLayout frame;
        CardView card;


        public MyViewHolder(View view) {
            super(view);

            card = (CardView)view.findViewById(R.id.card);
            closebtn =(ImageButton)view.findViewById(R.id.closebtn);
            p_name = (TextView) view.findViewById(R.id.p_name);
            p_price = (TextView) view.findViewById(R.id.p_price);
            image_product = (ImageView)view.findViewById(R.id.image_product);
            Linearlayout = (LinearLayout)view.findViewById(R.id.linear);
            toggle_like = (ToggleButton)view.findViewById(R.id.toggle_like);
            tv_likes_count = (TextView)view.findViewById(R.id.likes_count);
            btn_share = (ImageButton)view.findViewById(R.id.btn_share);
            frame = (FrameLayout)view.findViewById(R.id.frame);
        }
    }






    public ProductAdapter(List<Product> productsList, Context context) {
        this.productsList = productsList;
        this.context=context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);



        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Product product = productsList.get(position);
        holder2=holder;
        holder.p_name.setText(product.getP_name());
        holder.p_price.setText(product.getP_price()+" Rs.");
        holder.tv_likes_count.setText(product.getLikes_count());

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sI = new Intent();
                sI.setAction(Intent.ACTION_SEND);
                sI.putExtra(Intent.EXTRA_SUBJECT, "Product Info");
                sI.putExtra(Intent.EXTRA_TEXT, "Hey, Check this product out \n"+product.getP_name()+"\n\n"+product.getP_brief()+
                        "\nPrice is :"+product.getP_price()+"\n\nFollow this link to View Image :\n"+
                        AppConfig.URL_PARENT+"images/" + product.getP_name().replace(" ","_") + "/index.html");
                sI.setType("text/plain");
                context.startActivity(Intent.createChooser(sI, "Share via..."));
            }
        });

        if(product.getIs_like().equals("1"))
        {
           holder.toggle_like.setChecked(true);
        }

        else
        {
            holder.toggle_like.setChecked(false);
        }

        holder.toggle_like.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                mplayer = MediaPlayer.create(context,R.raw.hangouts_message);
                if (isChecked)
                {
                    if (mplayer.isPlaying()) {
                        mplayer.stop();
                        mplayer.start();
                    }
                    else
                    {
                        mplayer.start();
                    }
                    likeset("1",product.getP_name());
                    String temp =holder.tv_likes_count.getText().toString();
                    holder.tv_likes_count.setText(String.valueOf(Integer.parseInt(temp)+1));

                }
                else {

                    likeset("0",product.getP_name());

                    String temp =holder.tv_likes_count.getText().toString();
                    holder.tv_likes_count.setText(String.valueOf(Integer.parseInt(temp)-1));

                }

            }
        });

        Picasso.with(context).load(product.getP_image()).into(holder.image_product);


        holder.Linearlayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                holder.frame.setVisibility(View.VISIBLE);
                return true;
            }
        });


        holder.Linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                name=product.getP_name();
                price=product.getP_price();
                brief=product.getP_brief();
                info=product.getP_info();

              //  Toast.makeText(context,name+"\n"+price+"\n"+brief+"\n"+image,Toast.LENGTH_LONG).show();
                Intent i = new Intent(context,InfoProduct.class);
                i.putExtra("name",name);
                i.putExtra("price",price);
                i.putExtra("info",info);
                i.putExtra("brief",brief);


                context.startActivity(i);


            }
        });



        closebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.frame.setVisibility(View.GONE);

            }
        });

        setAnimation(holder.itemView, position);
    }



    private void likeset(final String is_like, final String p_name)
    {
    // Tag used to cancel the request
    String tag_string_req = "req_login";


    StringRequest strReq = new StringRequest(Request.Method.POST,
            AppConfig.URL_Like, new Response.Listener<String>() {

        @Override
        public void onResponse(String response) {
            Log.d("Likes :", "Response: " + response.toString());


            try {
                JSONObject jObj = new JSONObject(response);
                boolean error = jObj.getBoolean("error");

                // Check for error node in json
                if (!error) {

                    returnedcount = jObj.getString("likes_count");



                } else {
                    // Error in login. Get the error message
                    String errorMsg = jObj.getString("error_msg");

                }
            } catch (JSONException e) {
                // JSON error
                e.printStackTrace();

            }

        }
    }, new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("Likes :", "Response: " + error.getMessage());
        }
    }) {

        @Override
        protected Map<String, String> getParams() {
            // Posting parameters to login url
            Map<String, String> params = new HashMap<String, String>();
              params.put("is_like", is_like);
            params.put("p_name", p_name);
            return params;
        }

    };


    // Adding request to request queue
    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
}
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
    @Override
    public int getItemCount() {
        return productsList.size();
    }




}
