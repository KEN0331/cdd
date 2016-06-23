package potatotips.cdd.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * model object for post detail.
 */
public class TimelineContentModel {

    @SerializedName("id")
    public final int id;

    @SerializedName("content")
    public String content;

    public TimelineContentModel(int id, @Nullable String content) {
        this.id = id;
        this.content = content;
    }
}
