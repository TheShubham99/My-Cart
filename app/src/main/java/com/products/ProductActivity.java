package com.products;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.products.ProductAdapter;
import com.products.app.AppConfig;
import com.products.app.AppController;
import com.products.helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {

    private static final String TAG = "Product Responce";
    private Button btnGet;
    private ProgressDialog pDialog;
    private SessionManager session;
    private RecyclerView recyclerView;
    SwipeRefreshLayout swipelayout;
    ArrayList<Product> productList;
    CardView card;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        recyclerView = (RecyclerView)findViewById(R.id.list);
        // Progress dialog
        pDialog = new ProgressDialog(ProductActivity.this);
        pDialog.setCancelable(false);
        swipelayout = (SwipeRefreshLayout)findViewById(R.id.swipelayout);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar_products);

        getSupportActionBar();
        setSupportActionBar(toolbar);


        productList = new ArrayList<>();


        swipelayout.setColorSchemeResources(android.R.color.white);
        swipelayout.setDistanceToTriggerSync(100);
        swipelayout.setProgressBackgroundColorSchemeResource(R.color.colorPrimary);

        swipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                productList = new ArrayList<>();
                fetchProduct();
            }
        });


        fetchProduct();

    }
    @Override
   public void onBackPressed()
   {
       finish();

   }

    private void fetchProduct() {
        // Tag used to cancel the request
        String tag_string_req = "req_Product";

        pDialog.setMessage("Fetching Data from Server ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_PRODUCT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {

                        JSONArray products = jObj.getJSONArray("PRODUCTS");

                        String product_name = null;
                        String product_brief = null;
                        String product_price = null;
                        String product_image = null;
                        String product_info = null;
                        String likes_count = null;
                        String is_like = null;

                        for (int i = 0; i < products.length(); i++) {
                            JSONObject jsonObject = products.getJSONObject(i);

                            product_name = jsonObject.getString("product_name");
                            product_price = jsonObject.getString("product_price");
                            product_brief = jsonObject.getString("product_brief");
                            product_image = jsonObject.getString("product_image");
                            product_info = jsonObject.getString("product_info");
                            likes_count = jsonObject.getString("likes_count");
                            is_like = jsonObject.getString("is_like");


                            Product product = new Product(product_name,product_price,product_brief,product_image,product_info,likes_count,is_like);
                            productList.add(product);
                        }

                        ProductAdapter adapter ;
                        adapter = new ProductAdapter(productList,ProductActivity.this);

                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                        recyclerView.setLayoutManager(mLayoutManager);
                        // recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(adapter);






                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
              //  params.put("p_name", pname);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

        swipelayout.setRefreshing(false);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profile) {
            startActivityForResult(new Intent(getApplicationContext(),MainActivity.class),0);

            //return true;
        }


        return super.onOptionsItemSelected(item);

    }
}