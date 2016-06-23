package potatotips.cdd;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import potatotips.cdd.model.TimelineContentModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TimelineFragment extends Fragment {

    private OnLoadTimelineInterface onLoadTimelineInterface;

    private final Callback<List<TimelineContentModel>> apiCallback = new Callback<List<TimelineContentModel>>() {
        @Override
        public void onResponse(Call<List<TimelineContentModel>> call, Response<List<TimelineContentModel>> response) {
            // APIレスポンスを処理
        }

        @Override
        public void onFailure(Call<List<TimelineContentModel>> call, Throwable t) {

        }
    };

    public static TimelineFragment newInstance() {
        return new TimelineFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timeline, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onLoadTimelineInterface.onLoadTimeline(apiCallback);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoadTimelineInterface) {
            onLoadTimelineInterface = (OnLoadTimelineInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        onLoadTimelineInterface = null;
    }

    public interface OnLoadTimelineInterface {
        void onLoadTimeline(Callback<List<TimelineContentModel>> callback);
    }
}
