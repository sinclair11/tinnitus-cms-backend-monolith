package com.tinnitussounds.cms.activity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("samples-activity")
public class SampleActivity extends Activity {
    @Id
    private String id;

    public SampleActivity() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
