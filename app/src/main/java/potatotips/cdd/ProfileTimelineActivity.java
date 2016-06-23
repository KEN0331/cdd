package potatotips.cdd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import potatotips.cdd.model.TimelineContentModel;
import retrofit2.Callback;

public class ProfileTimelineActivity extends AppCompatActivity implements TimelineFragment.OnLoadTimelineInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
    }

    @Override
    public void onLoadTimeline(Callback<List<TimelineContentModel>> callback) {
        RetrofitFactory.getInstance(ProfileTimelineActivity.this)
                .createApiService()
                .getProfileTimelineContents()
                .enqueue(callback);
    }
}
