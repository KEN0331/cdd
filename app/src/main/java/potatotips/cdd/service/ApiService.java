package potatotips.cdd.service;

import java.util.List;

import potatotips.cdd.model.TimelineContentModel;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("v1/general_timeline/timelines")
    Call<List<TimelineContentModel>> getGeneralTimelineContents();

    @GET("v1/profile_timeline/timelines")
    Call<List<TimelineContentModel>> getProfileTimelineContents();
}
