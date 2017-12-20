package ServiceInterface;

import model.JsonData;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by piyush on 18/12/17.
 */

public interface ApiInterface {


    @GET("data.json")
    Call<JsonData> apiCall();

}
