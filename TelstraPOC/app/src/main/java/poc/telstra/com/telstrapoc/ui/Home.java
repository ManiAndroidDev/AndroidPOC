package poc.telstra.com.telstrapoc.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import poc.telstra.com.telstrapoc.R;
import poc.telstra.com.telstrapoc.adapter.FacilityAdapter;
import poc.telstra.com.telstrapoc.model.Country;
import poc.telstra.com.telstrapoc.model.CountryAPI;
import poc.telstra.com.telstrapoc.model.Facility;
import poc.telstra.com.telstrapoc.util.Constant;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;

public class Home extends Activity implements Callback<Country> ,OnRefreshListener{

    FacilityAdapter arrayAdapter;
    PullToRefreshLayout mPullToRefreshLayout;
    ProgressBar progressBar;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestData();
    }

    private void initialize() {
        mPullToRefreshLayout= (PullToRefreshLayout)findViewById(R.id.tab_1);
        ActionBarPullToRefresh.from(this)
                // Mark All Children as pullable
                .allChildrenArePullable()
                        // Set the OnRefreshListener
                .listener(this)
                        // Finally commit the setup to our PullToRefreshLayout
                .setup(mPullToRefreshLayout);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        listView = (ListView) findViewById(R.id.country_detail);

    }

    private void requestData() {

        toggleLoading(true);

        //Initializing the Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // prepare call in Retrofit 2.0
        CountryAPI stackOverflowAPI = retrofit.create(CountryAPI.class);
        Call<Country> call = stackOverflowAPI.loadCountryDetail();
        //asynchronous call
        call.enqueue(this);
    }


    @Override
    public void onResponse(Response<Country> response, Retrofit retrofit) {
        mPullToRefreshLayout.setRefreshComplete();
        toggleLoading(false);

        if(response!=null && response.body()!=null) {
            if(!TextUtils.isEmpty(response.body().getTitle())) {
                getActionBar().setTitle(response.body().getTitle());
            }
            if(response.body().getRows()!=null && response.body().getRows().size()>0) {
                arrayAdapter = new FacilityAdapter(Home.this, response.body().getRows());
                listView.setAdapter(arrayAdapter);
            } else {
                //No data available
            }
        } else {
            //No data from server
        }
    }

    @Override
    public void onFailure(Throwable t) {
        toggleLoading(false);
        mPullToRefreshLayout.setRefreshComplete();
        //Error Occurred
    }

    @Override
    public void onRefreshStarted(View view) {
        requestData();
    }

    public void toggleLoading(boolean isLoading) {
        if(isLoading) {
        progressBar.setVisibility(View.VISIBLE);
        listView.setVisibility(View.INVISIBLE);
    } else {
            progressBar.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
        }
    }
}
