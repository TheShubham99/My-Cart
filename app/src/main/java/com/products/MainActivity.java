package com.products;

/**
 * Created by Shubham on 5/12/2017.
 */


        import java.util.HashMap;
        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;
        import com.bumptech.glide.load.engine.DiskCacheStrategy;
        import com.products.Other.CircleTransform;
        import com.products.app.AppConfig;
        import com.products.helper.SessionManager;
        import com.squareup.picasso.Picasso;

public class MainActivity extends Activity {

    private TextView txtName,firstlater;
    private TextView txtEmail;
    private Button btnLogout,btn_product_activity;
    ImageView profileimageView;

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstlater=(TextView) findViewById(R.id.firstlater);
        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        profileimageView = (ImageView)findViewById(R.id.profileimageView);

        // SqLite database handler
       // db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());
       String stringname= session.getData(SessionManager.KEY_NAME,"No name");
        String stringemail = session.getData(SessionManager.KEY_EMAIL,"No email");

        char first = stringname.charAt(0);
        firstlater.setText(String.valueOf(first).toUpperCase());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite

        // Displaying the user details on the screen
        txtName.setText(stringname);
        txtEmail.setText(stringemail);
        Context context=this;

        Glide.with(context).load(AppConfig.URL_PARENT+"profile/"+stringemail.replace(".","_").toLowerCase()+".jpg")
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(profileimageView);
            profileimageView.bringToFront();

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

       // db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}