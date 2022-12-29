package com.tinnitussounds.cms.activity;


import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("albums-activity")
public class AlbumActivity extends Activity {
    @Id
    private String id;
    private ArrayList<SongActivity> songsActivity;

    public AlbumActivity() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<SongActivity> getSongsActivity() {
        return songsActivity;
    }

    public void setSongsActivity(ArrayList<SongActivity> songsActivity) {
        this.songsActivity = songsActivity;
    }

}