package potatotips.cdd;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import potatotips.cdd.service.ApiService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.plugins.RxJavaErrorHandler;
import rx.plugins.RxJavaPlugins;

/**
 * factory class for retrofit.
 */
public class RetrofitFactory {

    /**
     * instance of {@link RetrofitFactory}.
     */
    private static RetrofitFactory instance;

    /**
     * host names
     */
    private static final String GENYMOTION_LOCAL_HOST = "http://10.0.3.2:3000";

    /**
     * constants
     */
    private static final String OS_ANDROID = "android";
    private static final long TIME_OUT = 30;

    /**
     * http interceptors
     */
    private final static Interceptor NETWORK_REQUEST_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            // todo add accesstoken
            Request request = original.newBuilder()
                    .header("Authorization", "")
                    .header("App-Version", BuildConfig.VERSION_NAME)
                    .header("Client-OS", OS_ANDROID)
                    .method(original.method(), original.body())
                    .build();
            return chain.proceed(request);
        }
    };

    private final Context context;

    @NonNull
    public static synchronized RetrofitFactory getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new RetrofitFactory(context.getApplicationContext());
        }
        return instance;
    }

    private RetrofitFactory(@NonNull Context context) {
        this.context = context;
    }

    /**
     * get retrofit object of the host.
     *
     * @param host host for http connection
     * @return {@link Retrofit}.
     */
    private Retrofit buildRetrofit(@NonNull final String host) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(NETWORK_REQUEST_INTERCEPTOR)
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        RxJavaPlugins rxJavaPlugins = RxJavaPlugins.getInstance();
        if(RxJavaPlugins.getInstance().getErrorHandler() == null) {
            registerErrorHandler(rxJavaPlugins);
        }

        return new Retrofit.Builder()
                .baseUrl(host)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    /**
     * create {@link ApiService}.
     *
     * @return {@link ApiService}.
     */
    public ApiService createApiService() {
        return buildRetrofit(GENYMOTION_LOCAL_HOST).create(ApiService.class);
    }

    /**
     * register error {@link RxJavaErrorHandler}.
     *
     * @param rxJavaPlugins {@link RxJavaPlugins}
     */
    private void registerErrorHandler(@NonNull final RxJavaPlugins rxJavaPlugins) {
        rxJavaPlugins.registerErrorHandler(new RxJavaErrorHandler() {
            @Override
            public void handleError(Throwable e) {
                super.handleError(e);
                if (e instanceof HttpException) {
                    int errorCode = ((HttpException) e).response().code();
                    // todo define http error
                    switch (errorCode) {
                        case 500:
                            // server internal error
                            break;
                    }
                }
            }
        });
    }
}
