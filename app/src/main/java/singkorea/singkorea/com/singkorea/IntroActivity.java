package singkorea.singkorea.com.singkorea;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.q42.android.scrollingimageview.ScrollingImageView;

import singkorea.singkorea.com.singkorea.util.TinyDB;

import static singkorea.singkorea.com.singkorea.LoginActivity.KEY_AUTO_LOGIN;
import static singkorea.singkorea.com.singkorea.LoginActivity.KEY_PASSWORD;
import static singkorea.singkorea.com.singkorea.LoginActivity.KEY_USER_ID;

public class IntroActivity extends AppCompatActivity {

    TinyDB tinyDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        tinyDB = new TinyDB(this);
        if(!tinyDB.getBoolean(KEY_AUTO_LOGIN)) {
            tinyDB.remove(KEY_USER_ID);
            tinyDB.remove(KEY_PASSWORD);
        }

        ImageView introImg = findViewById(R.id.intro_gif_img);

        Glide.with(this).asGif()
                .load(R.drawable.intro)
                .into(introImg);


//        ScrollingImageView scrollingImageView = findViewById(R.id.scrollImageView);

        Handler handler = new Handler();
        //handler.postDelayed(() -> scrollingImageView.setSpeed(-18), 800);
        handler.postDelayed(() -> {
            finish();
            Intent intent = new Intent(IntroActivity.this, Main2Activity.class);
            startActivity(intent);

        }, 3200);
    }
}
