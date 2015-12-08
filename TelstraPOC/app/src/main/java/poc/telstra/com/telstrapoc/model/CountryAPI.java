package poc.telstra.com.telstrapoc.model;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by admin on 08/12/15.
 */
public interface CountryAPI {
    @GET("/u/746330/facts.json")
    Call<Country> loadCountryDetail();
}
