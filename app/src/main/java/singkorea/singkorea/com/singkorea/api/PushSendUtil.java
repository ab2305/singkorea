package singkorea.singkorea.com.singkorea.api;

public class PushSendUtil {

    //Real
    private static final String BASE_URL = "http://www.singkorea.com/";

    public static PushApiClient getSingApi() {
        return RetrofitClient.getClientInstance(BASE_URL).create(PushApiClient.class);
    }
}
