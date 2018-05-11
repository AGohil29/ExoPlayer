package com.example.exoplayer;

import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by arun.r on 30-04-2018.
 */

public class MediaItem {

    private String title;
    private String subtitle;
    private String studio;
    private String url;
    private String contentType;
    private int duration;
    private ArrayList<String> imageList = new ArrayList<>();

    public static final String KEY_TITLE = "title";
    public static final String KEY_SUBTITLE = "subtitle";
    public static final String KEY_STUDIO = "studio";
    public static final String KEY_URL = "movie_urls";
    public static final String KEY_IMAGES = "images";
    public static final String KEY_CONTENT_TYPE = "content-type";


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void addImage(String url) {
        imageList.add(url);
    }

    public void addImage(String url, int index) {
        if (index < imageList.size()) {
            imageList.set(index, url);
        }
    }

    public String getImage(int index) {
        if (index < imageList.size()) {
            return imageList.get(index);
        }
        return null;
    }

    public boolean hasImage() {
        return !imageList.isEmpty();
    }

    public ArrayList<String> getImages() {
        return imageList;
    }

    public Bundle toBundle() {
        Bundle wrapper = new Bundle();
        wrapper.putString(KEY_TITLE, title);
        wrapper.putString(KEY_SUBTITLE, subtitle);
        wrapper.putString(KEY_URL, url);
        wrapper.putString(KEY_STUDIO, studio);
        wrapper.putStringArrayList(KEY_IMAGES, imageList);
        wrapper.putString(KEY_CONTENT_TYPE, "video/mp4");
        return wrapper;
    }

    public static final MediaItem fromBundle(Bundle wrapper) {
        if (null == wrapper) {
            return null;
        }
        MediaItem media = new MediaItem();
        media.setUrl(wrapper.getString(KEY_URL));
        media.setTitle(wrapper.getString(KEY_TITLE));
        media.setSubtitle(wrapper.getString(KEY_SUBTITLE));
        media.setStudio(wrapper.getString(KEY_STUDIO));
        media.imageList.addAll(wrapper.getStringArrayList(KEY_IMAGES));
        media.setContentType(wrapper.getString(KEY_CONTENT_TYPE));
        return media;
    }
}
