package com.rit.NewsApplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.rit.NewsApplication.R;
import com.squareup.picasso.Picasso;

public class NewsdetailActivity extends AppCompatActivity {

    String title, desc, content, imageUrl, url;
    private TextView titletv, subtitleTV, contentTV;
    private ImageView newsIV2;
    private Button readnewsBtn;

    public NewsdetailActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);

        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        content = getIntent().getStringExtra("content");
        imageUrl = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");

        titletv = findViewById(R.id.TVTitle2);
        subtitleTV = findViewById(R.id.TVSubdesc2);
        contentTV = findViewById(R.id.TVContent2);
        newsIV2 = findViewById(R.id.IVNews2);
        readnewsBtn = findViewById(R.id.BTReadfullnews);

        titletv.setText(title);
        subtitleTV.setText(desc);
        contentTV.setText(content);
        Picasso.get().load(imageUrl).into(newsIV2);
        readnewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}