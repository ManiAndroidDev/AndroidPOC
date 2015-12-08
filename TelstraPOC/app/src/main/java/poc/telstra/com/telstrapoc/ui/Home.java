package poc.telstra.com.telstrapoc.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import poc.telstra.com.telstrapoc.R;
import poc.telstra.com.telstrapoc.model.Country;
import poc.telstra.com.telstrapoc.model.CountryAPI;
import poc.telstra.com.telstrapoc.model.Facility;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Home extends Activity implements Callback<Country> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Initializing the Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dl.dropboxusercontent.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        CountryAPI stackOverflowAPI = retrofit.create(CountryAPI.class);
        Call<Country> call = stackOverflowAPI.loadCountryDetail();
        //asynchronous call
        call.enqueue(this);


        //Binding data to UI
        arrayAdapter =
                new ArrayAdapter<Facility>(this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1,
                        new ArrayList<Facility>());
        ListView listView = (ListView) findViewById(R.id.country_detail);
        listView.setAdapter(arrayAdapter);

    }

    ArrayAdapter<Facility> arrayAdapter;

    @Override
    public void onResponse(Response<Country> response, Retrofit retrofit) {
        Toast.makeText(this, "Success: ", Toast.LENGTH_SHORT).show();

        arrayAdapter.clear();
        arrayAdapter.addAll(response.body().getRows());

    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

}
