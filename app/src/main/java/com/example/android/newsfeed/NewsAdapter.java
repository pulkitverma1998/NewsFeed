package com.example.android.newsfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list_item, parent, false);
        }

        // Find the earthquake at the given position in the list of earthquakes
        News currentNews = getItem(position);

        if (currentNews.getThumbnail() == null) {

        }

        TextView webTitleView = (TextView) listItemView.findViewById(R.id.webTitle);
        webTitleView.setText(currentNews.getWebTitle());

        TextView webPublicationDateView = (TextView) listItemView.findViewById(R.id.webPublicationDate);
        String dateObject = new String(currentNews.getWebPublicationDate());
        String formattedDate = formatDate(dateObject);
        webPublicationDateView.setText(formattedDate);

        TextView sectionName = (TextView) listItemView.findViewById(R.id.sectionName);
        sectionName.setText(currentNews.getSectionName());

        ImageView thumbnail = (ImageView) listItemView.findViewById(R.id.thumbnail);

        if (currentNews.hasThumbnail()) {
            thumbnail.setImageBitmap(currentNews.getThumbnail());
            thumbnail.setVisibility(View.VISIBLE);
        } else {
            thumbnail.setVisibility(View.GONE);
        }

        thumbnail.setImageBitmap(currentNews.getThumbnail());

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

    private String formatDate(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm:ss'Z'");
        Date dateObject = null;
        try {
            dateObject = simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        simpleDateFormat = new SimpleDateFormat("MMM dd,yyyy hh:mm a");
        String date = simpleDateFormat.format(dateObject);

        return date;
    }
}
