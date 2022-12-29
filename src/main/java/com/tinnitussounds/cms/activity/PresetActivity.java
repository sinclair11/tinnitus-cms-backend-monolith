package com.tinnitussounds.cms.activity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("presets-activity")
public class PresetActivity extends Activity {
    @Id
    private String id;

    public PresetActivity() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
