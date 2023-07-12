package com.rit.NewsApplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rit.NewsApplication.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickInterface {

// API Key :    f67af28449f045f799457d0e21aee633
//1st:    https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=f67af28449f045f799457d0e21aee633
//2nd: https://newsapi.org/v2/top-headlines/sources?category=science&apiKey=f67af28449f045f799457d0e21aee633
//3rd: https://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=f67af28449f045f799457d0e21aee633
//4th:  https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=f67af28449f045f799457d0e21aee633


    private RecyclerView newsRV, categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private  ArrayList<CategoryModal> categoryModalArrayList;
    private CategoryAdapter categoryAdapter;
    private  NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar cd = Calendar.getInstance();
        TextView TVCurrdate = findViewById(R.id.TVDate);
        String currdate = DateFormat.getDateInstance(DateFormat.FULL).format(cd.getTime());
        TVCurrdate.setText(currdate);

        newsRV = findViewById(R.id.RVNews);
        categoryRV = findViewById(R.id.RVCaategories);
        loadingPB = findViewById(R.id.PBLoading);

        articlesArrayList = new ArrayList<>();
        categoryModalArrayList = new ArrayList<>();
        newsAdapter = new NewsAdapter(articlesArrayList, this);
        categoryAdapter = new CategoryAdapter(categoryModalArrayList, this, this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsAdapter);
        categoryRV.setAdapter(categoryAdapter);

        getCategories();
        getNews("All");
        newsAdapter.notifyDataSetChanged();

    }

    private void getCategories(){
        categoryModalArrayList.add(new CategoryModal("All", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiNLKDXstWkMfJYGg8-2U6sjYo1Pu18jcPYn1vcvH8ycWEyeNtip1A21GRzezYHUAotVUhylB64bo4d3F8OuWHhPq1YCctrXuJ-Qt4elED8xuv2SqH6RXv2NDJEuDkXvI6GG3_3M3w2hv44Q9PWVYllbNLoawcSDYvO_Z8hWNfByzDpxQlU23VWc2y_jnZW/s4846/ALL%20(1).png"));
        categoryModalArrayList.add(new CategoryModal("Technology", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjbfBe4MTdX32mCau5wGgk82B1-Svpc4Ip51ukzCJ3oGFfNv5xbS_1zmFahclNKePjGp2pynOYY3RAeeu3BCtB1HHweYgTLVuZQEAJfdJHsPCaJiUr0ZOLhIFhKJZGYL-T9n7wyY7IYIfdjhwaRMjlQ38hj12jdh-yRCRVG19PLWrMjoqxzth9HoC6ktuKW/s320/ALL%20(6).png"));
        categoryModalArrayList.add(new CategoryModal("Science", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgTk6dzcFPfoONvFS6XpkEmeBxc7TYemfsW63va11fxlk6PpzXnk_wGMBgNewE4pLIkfXfwmV_iPpxt4GC-gFjBkGaI3VgJjB4NK6Wce8TDPAtg9IWZdT7hK4WxK3Mx_4YXiMOzQlNARJnxTUxSX2aPO0nQanaQFJvcENZbBtRPNVR_zTZpJHnXKP29vgDY/s320/ALL%20(7).png"));
        categoryModalArrayList.add(new CategoryModal("Sports", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEiVe9-rRJuEKK6CLWBURx6VpUq-JAX4fzPnf0hCNam_eTK3_w3r46ADFV6eZXWenkoJB3DGJlv1YgKA6wavvvXtdPLLn8V1FUD4Pr1W0DKsoRM7YevaQUcVSt7WqYiviZZmQaFhOPrOZ3qPRPv29LBFdPUraoWm_LI71Sys_eRXnQXdMn3g5Wn1HVUm-Psa/s320/ALL%20(3).png"));
        categoryModalArrayList.add(new CategoryModal("General", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEg1xFWwi2f53sazBi0nVhTvPGavIDK3YdyHVyUQvhMmiDTzfPhYU_uTQUHysN1icna6Mrq_OMSp2BC6kkV7xpdmNBVJVDCtTca6aNEigWtquflmmOMWfEY2zOVh9PcoyouL4Nk9Xl-st--JWbZ7Ojzmjk_XR9G71HMIvVIW58nT_0cmZc-yloE50crayKdA/s320/ALL%20(4).png"));
        categoryModalArrayList.add(new CategoryModal("Entertainment", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgHYIQujKMtCa20F0YFyCxu0K1zQLtfVoYamkI-JHxKwHY-DQRDidK5WdcsJQQN5vJVYmR75F2ikwodpXGovaADbyoeugj34zSc9eem8EEmrOtqemHOc1MfvmjMhRlEbZXDwBNCC8ZIyJyNCX1fqZUfdtbzOEBOgK6IWjLSevQypS_UmLZ8DfukJjZfYqiz/s320/ALL%20(2).png"));
        categoryModalArrayList.add(new CategoryModal("Business", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjGTWCt_hsilYQipwgHzsDxWZnASY8cZ2UFW8hsjE73C2P6yz1icZkhqdJA8zEf1W4zDneJNtBiyMQH7v8mlEQhWGRN21aCi_o13SM1bG57ltsA5FPF7dKokv7iu3w9EuZ5BJ3NQBvj8RQucwiQSEkeOp6urmp0Sf8JKaUgo7yzgux-yN4x71GeEMnvN6mA/s320/ALL.png"));
        categoryModalArrayList.add(new CategoryModal("Health", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEhsIXUfaExmohw6u0JfawuUvZzgAwbKm7LNsVf4yot3Df5fUxhqPidOL7ay3JiPlZ-5OvPNrN4AO67EThUO54xSFDL0m_GFM7KKYKt4Di0utxIizJRxPSnkQthqVqUfuegW_HvF480rFOiYz6dEoo7qd9AzwwBJ89EszYVVT8upw4RBOyKOcxC9bp7H0YjH/s320/ALL%20(5).png"));

        categoryAdapter.notifyDataSetChanged();
    }

    private void getNews(String category){

        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryUrl = "https://newsapi.org/v2/top-headlines?country=in&category="+ category +"&apiKey=f67af28449f045f799457d0e21aee633";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=f67af28449f045f799457d0e21aee633";
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        Call<NewsModal> call;

        if(category.equals("All")){
            call = retrofitApi.getallNews(url);
        }else{
            call = retrofitApi.getNewsByCategory(categoryUrl);
        }

        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {

                NewsModal newsModal = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModal.getArticles();
                for(int i=0; i<articles.size(); i++)
                {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(), articles.get(i).getUrlToImage(),articles.get(i).getUrl(), articles.get(i).getContent()));
                }

                newsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCategoryClick(int position) {

        String category = categoryModalArrayList.get(position).getCategory();
        getNews(category);
    }
}