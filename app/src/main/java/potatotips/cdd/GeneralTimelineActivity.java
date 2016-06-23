package potatotips.cdd;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import potatotips.cdd.model.TimelineContentModel;
import retrofit2.Callback;

public class GeneralTimelineActivity extends AppCompatActivity implements TimelineFragment.OnLoadTimelineInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
    }

    @Override
    public void onLoadTimeline(Callback<List<TimelineContentModel>> callback) {
        // general timeline用のAPI通信
        RetrofitFactory.getInstance(GeneralTimelineActivity.this)
                .createApiService()
                .getGeneralTimelineContents()
                .enqueue(callback);
    }
}
