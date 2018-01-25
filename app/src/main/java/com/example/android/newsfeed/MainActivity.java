package com.example.android.newsfeed;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    public static final String LOG_TAG = MainActivity.class.getName();

    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;

    private static final String MAIN_URL = "https://content.guardianapis.com/search?q=";

    private NewsAdapter mNewsAdapter;

    private EditText mTextEntered;
    private ImageButton mSearchButton;
    private ProgressBar mProgressBar;
    private TextView mMessage;

    private String modifiedUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchButton = (ImageButton) findViewById(R.id.searchButton);
        mTextEntered = (EditText) findViewById(R.id.searchBox);
        mMessage = (TextView) findViewById(R.id.message);
        mProgressBar = (ProgressBar) findViewById(R.id.loading_indicator);

        mNewsAdapter = new NewsAdapter(this, new ArrayList<News>());

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View v = getCurrentFocus();
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                mNewsAdapter.clear();

                if (mTextEntered.getText().toString().trim().matches("")) {
                    mMessage.setVisibility(View.VISIBLE);
                    mMessage.setText(R.string.searchBoxEmpty);
                } else {
                    if (isNetworkConnected()) {
                        modifiedUrl = MAIN_URL + mTextEntered.getText().toString().trim() + "&show-fields=thumbnail&api-key=test";
                        LoaderManager loaderManager = getLoaderManager();
                        loaderManager.initLoader(NEWS_LOADER_ID, null, MainActivity.this);
                        loaderManager.restartLoader(NEWS_LOADER_ID, null, MainActivity.this);
                        mNewsAdapter.clear();
                    } else {
                        mMessage.setVisibility(View.VISIBLE);
                        mMessage.setText(R.string.noInternet);
                    }
                }
            }
        });

        ListView newsListView = (ListView) findViewById(R.id.list);

        newsListView.setAdapter(mNewsAdapter);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                News currentNews = mNewsAdapter.getItem(position);

                Uri newsUri = Uri.parse(currentNews.getUrl());

                Intent newsIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                startActivity(newsIntent);
            }
        });

    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        mNewsAdapter.clear();
        mProgressBar.setVisibility(View.VISIBLE);
        // Create a new loader for the given URL
        return new NewsLoader(this, modifiedUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {

        mProgressBar.setVisibility(View.INVISIBLE);

        mNewsAdapter.clear();

        if (news == null) {
            mMessage.setVisibility(View.VISIBLE);
            mMessage.setText(R.string.noNewsFound);
        } else {
            mMessage.setVisibility(View.GONE);
            mNewsAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mNewsAdapter.clear();
    }

    public boolean isNetworkConnected() { //check network connection
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.isConnected()) //IMPORTANT as u can't call info.isConnected() if info is NULL. Will throw null point exception. So first check it's null or not
            return true;
        else
            return false;
    }

}
